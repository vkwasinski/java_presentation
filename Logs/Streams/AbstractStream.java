package app.logs.streams;

import app.logs.interfaces.StreamInterface;

/**
 * Abstract base class for log stream implementations.
 * Provides common functionality for stream management.
 */
public abstract class AbstractStream implements StreamInterface
{

    protected String stream = "";

    /**
     * Gets the stream identifier.
     *
     * @return stream identifier
     */
    @Override
    public String getStream()
    {
        return stream;
    }
}

