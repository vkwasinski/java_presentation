package app.logs.strategies;

import app.logs.interfaces.ChannelInterface;
import app.logs.interfaces.LoggerStrategyInterface;
import org.slf4j.Logger;

/**
 * Logger strategy for audit logging.
 * Uses audit file channel by default, configurable via AUDIT_FILE_CHANNEL environment variable.
 */
public class LoggerAuditStrategy implements LoggerStrategyInterface, ChannelInterface
{

    private Logger loggerAudit = null;

    /**
     * Sets the logger instance.
     *
     * @param logger logger instance
     * @return this instance for method chaining
     */
    @Override
    public LoggerStrategyInterface setInstance(Logger logger)
    {
        this.loggerAudit = logger;
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
        return loggerAudit;
    }

    /**
     * Gets the channel name from environment variable.
     *
     * @return channel name, defaults to AUDIT_FILE_CHANNEL
     */
    @Override
    public String getChannel()
    {
        String channel = System.getenv("AUDIT_FILE_CHANNEL");
        return channel != null && !channel.isEmpty() ? channel : AUDIT_FILE_CHANNEL;
    }
}

