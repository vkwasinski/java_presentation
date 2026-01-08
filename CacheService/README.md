# Cache Service - Java 8 Port

The service provides a singleton Redis client with support for key-value storage, hash operations, compression, and serialization.

## Features

- **Singleton Pattern**: Thread-safe singleton implementation for RedisService
- **Key-Value Operations**: Set, get, delete, and check existence of keys
- **Hash Operations**: Store and retrieve hash structures with field-level access
- **Compression**: Automatic GZIP compression/decompression for stored values
- **Serialization**: Java object serialization support
- **Environment Prefixing**: Automatic key prefixing based on environment variables
- **Expiration**: Set TTL (time-to-live) for cached values
- **Remember Pattern**: Cache-or-compute pattern with automatic expiration

## Requirements

- Java 8 or higher
- Maven 3.x
- Redis server running (default: localhost:6379)

## Dependencies

- Jedis 3.9.0 (Redis Java client)

## Environment Variables

The service uses the following environment variables (optional):

- `REDIS_HOST`: Redis server host (default: 127.0.0.1)
- `REDIS_PORT`: Redis server port (default: 6379)
- `ENV`: Environment name (used for key prefixing)
- `SITE_ENV`: Site environment name (used for key prefixing)

## Usage

### Basic Usage

```java
import app.cache.RedisService;
import app.exceptions.RedisServiceException;

// Get singleton instance
RedisService redis = RedisService.instance();

// Set a simple string value
redis.set("mykey", "myvalue");

// Get a value
Optional<String> value = redis.get("mykey");

// Set with expiration (30 seconds)
redis.set("tempkey", "tempvalue").expires(30);

// Delete a key
redis.delete("mykey");

// Check if key exists
boolean exists = redis.exists("mykey");
```

### Object Storage

```java
// Store an object (automatically serialized and compressed)
MyObject obj = new MyObject();
redis.set("objectkey", obj);

// Retrieve an object
Optional<MyObject> retrieved = redis.get("objectkey", MyObject.class);
```

### Hash Operations

```java
// Store a hash from a map
Map<String, Object> data = new HashMap<>();
data.put("field1", "value1");
data.put("field2", "value2");
redis.setHashFromArray("myhash", data);

// Get all hash fields
Map<String, Object> hash = redis.getHash("myhash");

// Get specific hash field
Object fieldValue = redis.getHashFieldValue("myhash", "field1");

// Check if field exists in hash
boolean isMember = redis.isMemberOfHash("myhash", "field1");
```

### Remember Pattern

```java
// Cache or compute pattern
String result = redis.remember("expensive-operation", 3600, () -> {
    // Expensive computation
    return performExpensiveOperation();
});
```

### Using the WithRedisService Interface

```java
import app.cache.WithRedisService;

public class MyService implements WithRedisService
{
    public void doSomething() throws RedisServiceException
    {
        RedisService redis = redis();
        redis.set("key", "value");
    }
}
```

## Building

```bash
mvn clean compile
```

## Package Structure

- `app.cache`: Main cache service classes
  - `RedisService.java`: Main service implementation
  - `WithRedisService.java`: Interface for easy Redis access
- `app.exceptions`: Exception classes
  - `RedisServiceException.java`: Custom Redis exceptions


- All stored objects must implement `Serializable`
- Keys are automatically lowercased and prefixed with environment variables if set
- Compression uses GZIP algorithm
- The service uses a connection pool for better performance

