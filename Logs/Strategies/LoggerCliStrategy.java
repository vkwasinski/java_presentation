package app.logs.strategies;

import app.logs.interfaces.ChannelInterface;
import app.logs.interfaces.LoggerStrategyInterface;
import org.slf4j.Logger;

/**
 * Logger strategy for CLI application logging.
 * Uses file channel by default, configurable via LOG_CHANNEL_CLI environment variable.
 */
public class LoggerCliStrategy implements LoggerStrategyInterface, ChannelInterface
{

    private Logger loggerCli = null;

    /**
     * Sets the logger instance.
     *
     * @param logger logger instance
     * @return this instance for method chaining
     */
    @Override
    public LoggerStrategyInterface setInstance(Logger logger)
    {
        this.loggerCli = logger;
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
        return loggerCli;
    }

    /**
     * Gets the channel name from environment variable.
     *
     * @return channel name, defaults to FILE_CHANNEL
     */
    @Override
    public String getChannel()
    {
        String channel = System.getenv("LOG_CHANNEL_CLI");
        return channel != null && !channel.isEmpty() ? channel : FILE_CHANNEL;
    }
}

