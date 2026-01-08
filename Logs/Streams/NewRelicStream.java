package app.logs.streams;

import app.logs.interfaces.StreamInterface;
import org.slf4j.Logger;

/**
 * NewRelic stream implementation for logging to stderr.
 * Note: NewRelic integration would require additional dependencies.
 * This is a placeholder implementation.
 */
public class NewRelicStream extends AbstractStream implements StreamInterface
{

    /**
     * Constructs a new NewRelicStream and loads the stream.
     */
    public NewRelicStream()
    {
        loadStream();
    }

    /**
     * Loads the stream to stderr.
     */
    @Override
    public void loadStream()
    {
        this.stream = "stderr";
    }

    /**
     * Reflects handlers to the logger instance.
     * Note: NewRelic integration would be configured here.
     *
     * @param logger logger instance
     */
    @Override
    public void reflectHandlers(Logger logger)
    {
    }

    /**
     * Reflects processors to the logger instance.
     * Note: NewRelic integration would be configured here.
     *
     * @param logger logger instance
     */
    @Override
    public void reflectProcessors(Logger logger)
    {
    }
}

