package app.logs.strategies;

import app.logs.interfaces.ChannelInterface;
import app.logs.interfaces.LoggerStrategyInterface;
import org.slf4j.Logger;

/**
 * Logger strategy for web application logging.
 * Uses file channel by default, configurable via LOG_CHANNEL environment variable.
 */
public class LoggerWebStrategy implements LoggerStrategyInterface, ChannelInterface
{

    private Logger loggerWeb = null;

    /**
     * Sets the logger instance.
     *
     * @param logger logger instance
     * @return this instance for method chaining
     */
    @Override
    public LoggerStrategyInterface setInstance(Logger logger)
    {
        this.loggerWeb = logger;
        return this;
    }

    /**
     * Gets the logger instance.
     *
     * @return logger instance or null if not set
     */
    @Override
    public Logger getInstance()
    {
        return loggerWeb;
    }

    /**
     * Gets the channel name from environment variable.
     *
     * @return channel name, defaults to FILE_CHANNEL
     */
    @Override
    public String getChannel()
    {
        String channel = System.getenv("LOG_CHANNEL");
        return channel != null && !channel.isEmpty() ? channel : FILE_CHANNEL;
    }
}

