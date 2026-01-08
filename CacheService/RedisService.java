package app.cache;

import app.exceptions.RedisServiceException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.function.Supplier;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Redis service implementation providing caching functionality.
 * Implements singleton pattern for instance management.
 * Supports key-value storage, hash operations, compression, and serialization.
 */
public class RedisService
{

    public static final String HOST = "127.0.0.1";

    public static final int PORT = 6379;

    private JedisPool jedisPool;

    private static volatile RedisService instance = null;

    private static final Object lock = new Object();

    private Stack<String> keyBuffer = new Stack<>();

    private String envPrefix = "";

    /**
     * Constructs a new RedisService instance.
     *
     * @param host Redis server host
     * @param port Redis server port
     * @throws RedisServiceException if connection fails
     */
    public RedisService(String host, int port) throws RedisServiceException
    {
        try
        {
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(10);
            poolConfig.setMaxIdle(5);
            poolConfig.setMinIdle(1);
            poolConfig.setTestOnBorrow(true);
            poolConfig.setTestOnReturn(true);

            String redisHost = System.getenv("REDIS_HOST");
            int redisPort = PORT;
            try
            {
                String portEnv = System.getenv("REDIS_PORT");
                if (portEnv != null)
                {
                    redisPort = Integer.parseInt(portEnv);
                }
            }
            catch (NumberFormatException e)
            {
                redisPort = port;
            }

            if (redisHost == null || redisHost.isEmpty())
            {
                redisHost = host;
            }

            this.jedisPool = new JedisPool(poolConfig, redisHost, redisPort);
            
            String env = System.getenv("ENV");
            String siteEnv = System.getenv("SITE_ENV");
            if (env != null && siteEnv != null)
            {
                this.envPrefix = (env + "-" + siteEnv + "-").toLowerCase();
            }
        }
        catch (Exception e)
        {
            throw RedisServiceException.connectionFailed(e.getMessage());
        }
    }

    /**
     * Default constructor using default host and port.
     *
     * @throws RedisServiceException if connection fails
     */
    public RedisService() throws RedisServiceException
    {
        this(HOST, PORT);
    }

    /**
     * Closes the Redis connection pool.
     */
    public void close()
    {
        if (jedisPool != null && !jedisPool.isClosed())
        {
            jedisPool.close();
        }
    }

    /**
     * Gets the environment prefix for keys.
     *
     * @param key the base key
     * @return prefixed key
     */
    public String envPrefix(String key)
    {
        return envPrefix + key.toLowerCase();
    }

    /**
     * Gets the singleton instance of RedisService.
     * Thread-safe double-checked locking implementation.
     *
     * @return RedisService instance
     * @throws RedisServiceException if connection fails
     */
    public static RedisService instance() throws RedisServiceException
    {
        if (instance != null)
        {
            return instance;
        }

        synchronized (lock)
        {
            if (instance == null)
            {
                instance = new RedisService();
            }
        }

        return instance;
    }

    /**
     * Sets a key-value pair in Redis.
     *
     * @param key the key
     * @param value the value
     * @return this instance for method chaining
     */
    public RedisService set(String key, String value)
    {
        key = envPrefix(key);
        try (Jedis jedis = jedisPool.getResource())
        {
            jedis.set(key, value);
            setKeyBuffer(key);
        }
        return this;
    }

    /**
     * Sets a key-value pair with object serialization and compression.
     *
     * @param key the key
     * @param value the object value
     * @return this instance for method chaining
     */
    public RedisService set(String key, Object value)
    {
        try
        {
            byte[] serialized = serialize(value);
            byte[] compressed = compress(serialized);
            key = envPrefix(key);
            try (Jedis jedis = jedisPool.getResource())
            {
                jedis.set(key.getBytes(), compressed);
                setKeyBuffer(key);
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException("Failed to serialize object", e);
        }
        return this;
    }

    /**
     * Deletes a key from Redis.
     *
     * @param key the key to delete
     * @return this instance for method chaining
     */
    public RedisService delete(String key)
    {
        key = envPrefix(key);
        try (Jedis jedis = jedisPool.getResource())
        {
            jedis.del(key);
            clearKeyBuffer(key);
        }
        return this;
    }

    /**
     * Gets a value from Redis by key.
     *
     * @param key the key
     * @return Optional containing the value if found
     */
    public Optional<String> get(String key)
    {
        key = envPrefix(key);
        try (Jedis jedis = jedisPool.getResource())
        {
            String value = jedis.get(key);
            return Optional.ofNullable(value);
        }
    }

    /**
     * Gets a value from Redis and deserializes it.
     *
     * @param key the key
     * @param clazz the class type to deserialize to
     * @param <T> the type
     * @return Optional containing the deserialized object if found
     */
    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(String key, Class<T> clazz)
    {
        key = envPrefix(key);
        try (Jedis jedis = jedisPool.getResource())
        {
            byte[] compressed = jedis.get(key.getBytes());
            if (compressed == null)
            {
                return Optional.empty();
            }

            byte[] decompressed = decompress(compressed);
            Object obj = deserialize(decompressed);
            
            if (obj != null && clazz.isInstance(obj))
            {
                return Optional.of((T) obj);
            }
            return Optional.empty();
        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException("Failed to deserialize object", e);
        }
    }

    /**
     * Gets all fields and values from a hash.
     *
     * @param key the hash key
     * @return Map containing all hash fields and values
     */
    public Map<String, Object> getHash(String key)
    {
        key = envPrefix(key);
        try (Jedis jedis = jedisPool.getResource())
        {
            Map<String, String> serializedHash = jedis.hgetAll(key);
            Map<String, Object> storedHash = new HashMap<>();

            for (Map.Entry<String, String> entry : serializedHash.entrySet())
            {
                try
                {
                    byte[] compressed = entry.getValue().getBytes();
                    byte[] decompressed = decompress(compressed);
                    Object value = deserialize(decompressed);
                    storedHash.put(entry.getKey(), value);
                }
                catch (IOException | ClassNotFoundException e)
                {
                    throw new RuntimeException("Failed to deserialize hash value", e);
                }
            }

            return storedHash;
        }
    }

    /**
     * Checks if a key exists in Redis.
     *
     * @param key the key to check
     * @return true if key exists, false otherwise
     */
    public boolean exists(String key)
    {
        key = envPrefix(key);
        try (Jedis jedis = jedisPool.getResource())
        {
            return jedis.exists(key);
        }
    }

    /**
     * Sets a hash from a map of key-value pairs.
     * Values are serialized and compressed before storage.
     *
     * @param hashKey the hash key
     * @param dataArray the map of data to store
     * @return this instance for method chaining
     */
    public RedisService setHashFromArray(String hashKey, Map<String, Object> dataArray)
    {
        hashKey = envPrefix(hashKey);
        try (Jedis jedis = jedisPool.getResource())
        {
            for (Map.Entry<String, Object> entry : dataArray.entrySet())
            {
                try
                {
                    byte[] serialized = serialize(entry.getValue());
                    byte[] compressed = compress(serialized);
                    jedis.hset(hashKey.getBytes(), entry.getKey().getBytes(), compressed);
                }
                catch (IOException e)
                {
                    throw new RuntimeException("Failed to serialize hash value", e);
                }
            }
            setKeyBuffer(hashKey);
        }
        return this;
    }

    /**
     * Checks if a field exists in a hash.
     *
     * @param hashKey the hash key
     * @param key the field key
     * @return true if field exists, false otherwise
     */
    public boolean isMemberOfHash(String hashKey, String key)
    {
        hashKey = envPrefix(hashKey);
        try (Jedis jedis = jedisPool.getResource())
        {
            return jedis.hexists(hashKey, key);
        }
    }

    /**
     * Sets expiration time for the last buffered key.
     *
     * @param seconds expiration time in seconds
     * @return this instance for method chaining
     */
    public RedisService expires(int seconds)
    {
        String lastKey = getLastKeyBuffered();
        if (lastKey == null)
        {
            return this;
        }

        try (Jedis jedis = jedisPool.getResource())
        {
            jedis.expire(lastKey, seconds);
            clearKeyBuffer(lastKey);
        }
        return this;
    }

    /**
     * Gets a value from Redis as byte array.
     *
     * @param key the key
     * @return Optional containing the byte array if found
     */
    private Optional<byte[]> getBytes(String key)
    {
        key = envPrefix(key);
        try (Jedis jedis = jedisPool.getResource())
        {
            byte[] value = jedis.get(key.getBytes());
            return Optional.ofNullable(value);
        }
    }

    /**
     * Remembers a value by key, using cache if available or computing via supplier.
     * Values are compressed and serialized before storage.
     *
     * @param cachedKey the cache key
     * @param expiration expiration time in seconds
     * @param closure the supplier function to compute value if not cached
     * @param <T> the value type
     * @return the cached or computed value
     */
    @SuppressWarnings("unchecked")
    public <T> T remember(String cachedKey, int expiration, Supplier<T> closure)
    {
        try
        {
            cachedKey = envPrefix(cachedKey);
            RedisService service = instance();

            Optional<byte[]> cached = service.getBytes(cachedKey);
            if (cached.isPresent() && cached.get().length > 0)
            {
                try
                {
                    byte[] decompressed = decompress(cached.get());
                    Object value = deserialize(decompressed);
                    
                    if (value != null)
                    {
                        return (T) value;
                    }
                }
                catch (Exception e)
                {
                    try
                    {
                        Object value = deserialize(cached.get());
                        if (value != null)
                        {
                            return (T) value;
                        }
                    }
                    catch (Exception ex)
                    {
                    }
                }
            }

            T value = closure.get();
            service.set(cachedKey, value).expires(expiration);
            return value;
        }
        catch (RedisServiceException e)
        {
            throw new RuntimeException("Failed to get RedisService instance", e);
        }
    }

    /**
     * Deletes multiple keys from Redis.
     *
     * @param cacheKeys array of keys to delete
     */
    public void clearKeys(String[] cacheKeys)
    {
        String[] prefixedKeys = new String[cacheKeys.length];
        for (int i = 0; i < cacheKeys.length; i++)
        {
            prefixedKeys[i] = envPrefix(cacheKeys[i]);
        }

        try (Jedis jedis = jedisPool.getResource())
        {
            jedis.del(prefixedKeys);
        }
    }

    /**
     * Gets a specific field value from a hash.
     *
     * @param hashKey the hash key
     * @param key the field key
     * @return the deserialized field value, or empty string if not found
     */
    public Object getHashFieldValue(String hashKey, String key)
    {
        hashKey = envPrefix(hashKey);
        try (Jedis jedis = jedisPool.getResource())
        {
            byte[] cached = jedis.hget(hashKey.getBytes(), key.getBytes());
            if (cached == null)
            {
                return "";
            }

            byte[] uncompressed = decompress(cached);
            return deserialize(uncompressed);
        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException("Failed to deserialize hash field value", e);
        }
    }

    private void setKeyBuffer(String key)
    {
        keyBuffer.push(key);
    }

    private String getLastKeyBuffered()
    {
        return keyBuffer.isEmpty() ? null : keyBuffer.peek();
    }

    private void clearKeyBuffer(String key)
    {
        if (!keyBuffer.isEmpty() && keyBuffer.peek().equals(key))
        {
            keyBuffer.pop();
        }
    }

    private byte[] serialize(Object obj) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos))
        {
            oos.writeObject(obj);
        }
        return baos.toByteArray();
    }

    private Object deserialize(byte[] data) throws IOException, ClassNotFoundException
    {
        if (data == null || data.length == 0)
        {
            return null;
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try (ObjectInputStream ois = new ObjectInputStream(bais))
        {
            return ois.readObject();
        }
    }

    private byte[] compress(byte[] data) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (GZIPOutputStream gzos = new GZIPOutputStream(baos))
        {
            gzos.write(data);
        }
        return baos.toByteArray();
    }

    private byte[] decompress(byte[] compressed) throws IOException
    {
        if (compressed == null || compressed.length == 0)
        {
            return compressed;
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(compressed);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (GZIPInputStream gzis = new GZIPInputStream(bais))
        {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzis.read(buffer)) != -1)
            {
                baos.write(buffer, 0, len);
            }
        }
        return baos.toByteArray();
    }
}

