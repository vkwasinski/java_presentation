package app.logs.streams;

import app.logs.interfaces.ChannelInterface;
import app.logs.interfaces.StreamInterface;

/**
 * Factory for creating stream instances based on channel names.
 */
public class StreamFactory implements ChannelInterface
{

    /**
     * Creates a stream instance for the given channel.
     *
     * @param channel channel name
     * @return stream instance or null if channel not found
     */
    public static StreamInterface make(String channel)
    {
        try
        {
            Class<? extends StreamInterface> streamClass = LOG_CHANNELS.get(channel);

            if (streamClass == null)
            {
                throw new Exception("[StreamFactory] :: [make] :: stream *" + channel + "* does not exist");
            }

            return streamClass.newInstance();
        }
        catch (Exception e)
        {
            System.err.println("[StreamFactory] :: [make] - " + e.getMessage());
            return null;
        }
    }
}

