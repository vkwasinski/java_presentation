package app.logs.interfaces;

import org.slf4j.Logger;

/**
 * Interface for logger strategies.
 * Defines methods for managing logger instances and channels.
 */
public interface LoggerStrategyInterface
{

    /**
     * Gets the channel name for this logger strategy.
     *
     * @return channel name
     */
    String getChannel();

    /**
     * Sets the logger instance.
     *
     * @param logger logger instance
     * @return this instance for method chaining
     */
    LoggerStrategyInterface setInstance(Logger logger);

    /**
     * Gets the logger instance.
     *
     * @return logger instance or null if not set
     */
    Logger getInstance();
}

