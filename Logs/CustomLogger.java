package app.logs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom logger implementation with debug mode support.
 * Extends SLF4J Logger functionality with conditional debug logging.
 */
public class CustomLogger
{

    private final Logger logger;

    private final boolean debugEnabled;

    /**
     * Constructs a new CustomLogger with the given name.
     *
     * @param name logger name
     */
    public CustomLogger(String name)
    {
        this.logger = LoggerFactory.getLogger(name);
        String debugEnv = System.getenv("DEBUG");
        this.debugEnabled = "true".equalsIgnoreCase(debugEnv) || "1".equals(debugEnv);
    }

    /**
     * Logs a debug message if debug mode is enabled.
     *
     * @param message debug message
     */
    public void debug(String message)
    {
        if (debugEnabled)
        {
            logger.debug(message);
        }
    }

    /**
     * Logs a debug message with format and arguments if debug mode is enabled.
     *
     * @param format message format
     * @param args format arguments
     */
    public void debug(String format, Object... args)
    {
        if (debugEnabled)
        {
            logger.debug(format, args);
        }
    }

    /**
     * Logs an info message.
     *
     * @param message info message
     */
    public void info(String message)
    {
        logger.info(message);
    }

    /**
     * Logs an info message with format and arguments.
     *
     * @param format message format
     * @param args format arguments
     */
    public void info(String format, Object... args)
    {
        logger.info(format, args);
    }

    /**
     * Logs a warning message.
     *
     * @param message warning message
     */
    public void warning(String message)
    {
        logger.warn(message);
    }

    /**
     * Logs a warning message with format and arguments.
     *
     * @param format message format
     * @param args format arguments
     */
    public void warning(String format, Object... args)
    {
        logger.warn(format, args);
    }

    /**
     * Logs an error message.
     *
     * @param message error message
     */
    public void error(String message)
    {
        logger.error(message);
    }

    /**
     * Logs an error message with format and arguments.
     *
     * @param format message format
     * @param args format arguments
     */
    public void error(String format, Object... args)
    {
        logger.error(format, args);
    }

    /**
     * Gets the underlying SLF4J logger.
     *
     * @return SLF4J logger instance
     */
    public Logger getLogger()
    {
        return logger;
    }
}

