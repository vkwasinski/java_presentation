package app.interfaces;

import app.exceptions.RedisServiceException;

/**
 * Interface providing access to RedisService instance.
 * Java equivalent of the WithRedisServiceTrait from PHP.
 * Classes implementing this interface can easily access RedisService.
 */
public interface WithRedisService
{

    /**
     * Gets the RedisService singleton instance.
     *
     * @return RedisService instance
     * @throws RedisServiceException if connection fails
     */
    default RedisService redis() throws RedisServiceException
    {
        return RedisService.instance();
    }
}

