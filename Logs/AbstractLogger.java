package app.logs;

import app.logs.interfaces.ChannelInterface;
import app.logs.interfaces.LoggerStrategyInterface;
import app.logs.interfaces.StreamInterface;
import app.logs.streams.StreamFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract base class for logger implementations.
 * Provides common functionality for logger initialization and channel validation.
 */
public abstract class AbstractLogger extends CustomLogger implements ChannelInterface
{

    /**
     * Constructs a new AbstractLogger with the given name.
     *
     * @param name logger name
     */
    public AbstractLogger(String name)
    {
        super(name);
    }

    /**
     * Checks if the given channel is valid.
     *
     * @param channel channel name to validate
     * @return true if channel is valid, false otherwise
     */
    protected boolean checkForValidLogChannels(String channel)
    {
        return LOG_CHANNELS.containsKey(channel);
    }

    /**
     * Initializes a logger with the given strategy.
     *
     * @param loggerStrategy logger strategy
     * @return initialized logger instance
     */
    protected static CustomLogger initLogger(LoggerStrategyInterface loggerStrategy)
    {
        String channel = loggerStrategy.getChannel();
        CustomLogger loggerInstance = new CustomLogger(channel);

        loggerStrategy.setInstance(loggerInstance.getLogger());

        StreamInterface streamObj = StreamFactory.make(channel);
        if (streamObj != null)
        {
            streamObj.reflectHandlers(loggerInstance.getLogger());
            streamObj.reflectProcessors(loggerInstance.getLogger());
        }

        return loggerInstance;
    }
}

