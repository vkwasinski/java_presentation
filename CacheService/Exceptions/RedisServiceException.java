package app.exceptions;

/**
 * Custom exception for Redis service operations.
 * Provides factory methods for common Redis-related error scenarios.
 */
public class RedisServiceException extends Exception
{

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new RedisServiceException with the specified message.
     *
     * @param message the detail message
     */
    public RedisServiceException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new RedisServiceException with the specified message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public RedisServiceException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Creates an exception for connection failures.
     *
     * @param reason optional reason for the failure
     * @return RedisServiceException instance
     */
    public static RedisServiceException connectionFailed(String reason)
    {
        String message = "Failed to connect to Redis.";
        if (reason != null && !reason.isEmpty())
        {
            message += " " + reason;
        }
        return new RedisServiceException(message);
    }

    /**
     * Creates an exception for authentication failures.
     *
     * @param reason optional reason for the failure
     * @return RedisServiceException instance
     */
    public static RedisServiceException authenticationFailed(String reason)
    {
        String message = "Failed to authenticate with Redis.";
        if (reason != null && !reason.isEmpty())
        {
            message += " " + reason;
        }
        return new RedisServiceException(message);
    }

    /**
     * Creates an exception when a key is not found.
     *
     * @param key the key that was not found
     * @return RedisServiceException instance
     */
    public static RedisServiceException keyNotFound(String key)
    {
        return new RedisServiceException("Key '" + key + "' not found in Redis.");
    }

    /**
     * Creates an exception for operation timeouts.
     *
     * @param operation optional operation name that timed out
     * @return RedisServiceException instance
     */
    public static RedisServiceException operationTimedOut(String operation)
    {
        String message = "Redis operation timed out";
        if (operation != null && !operation.isEmpty())
        {
            message += " during '" + operation + "'";
        }
        return new RedisServiceException(message + ".");
    }

    /**
     * Creates an exception for unsupported operations.
     *
     * @param operation the operation that is not supported
     * @return RedisServiceException instance
     */
    public static RedisServiceException unsupportedOperation(String operation)
    {
        return new RedisServiceException(
            "The '" + operation + "' operation is not supported in the current Redis setup."
        );
    }
}

