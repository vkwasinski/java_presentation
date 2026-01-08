package app.logs;

import app.logs.interfaces.LoggerStrategyInterface;
import app.logs.strategies.LoggerAuditStrategy;
import app.logs.strategies.LoggerCliStrategy;
import app.logs.strategies.LoggerWebStrategy;
import org.slf4j.Logger;

/**
 * Main logging service with singleton pattern.
 * Provides static methods for accessing different logger types (web, cli, audit).
 */
public class Log extends AbstractLogger
{

    protected static volatile Log instance = null;

    protected static final Object lock = new Object();

    /**
     * Constructs a new Log instance.
     */
    public Log()
    {
        super("app");
    }

    /**
     * Gets the singleton instance of Log.
     * Thread-safe double-checked locking implementation.
     *
     * @return Log instance
     */
    public static Log instance()
    {
        if (instance != null)
        {
            return instance;
        }

        synchronized (lock)
        {
            if (instance == null)
            {
                instance = new Log();
            }
        }

        return instance;
    }

    /**
     * Gets a web logger instance.
     *
     * @return Logger instance for web logging
     */
    public static Logger web()
    {
        return setup(new LoggerWebStrategy());
    }

    /**
     * Gets a CLI logger instance.
     *
     * @return Logger instance for CLI logging
     */
    public static Logger cli()
    {
        return setup(new LoggerCliStrategy());
    }

    /**
     * Gets an audit logger instance.
     *
     * @return Logger instance for audit logging
     */
    public static Logger audit()
    {
        return setup(new LoggerAuditStrategy());
    }

    /**
     * Sets up a logger with the given strategy.
     *
     * @param loggerStrategy logger strategy
     * @return Logger instance or null if setup fails
     */
    private static Logger setup(LoggerStrategyInterface loggerStrategy)
    {
        if (loggerStrategy.getInstance() != null)
        {
            return loggerStrategy.getInstance();
        }

        try
        {
            if (!instance().checkForValidLogChannels(loggerStrategy.getChannel()))
            {
                throw new Exception("Invalid channel: " + loggerStrategy.getChannel());
            }

            CustomLogger loggerInstance = initLogger(loggerStrategy);
            return loggerInstance.getLogger();
        }
        catch (Exception e)
        {
            Logger fallbackLogger = org.slf4j.LoggerFactory.getLogger("app");
            fallbackLogger.warn("[app] :: [Log] - using FILE fallback: " + e.getMessage());
            return fallbackLogger;
        }
    }
}

