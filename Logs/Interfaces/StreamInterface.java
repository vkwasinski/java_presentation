package app.logs.interfaces;

import org.slf4j.Logger;

/**
 * Interface for log stream implementations.
 * Defines methods for configuring logger handlers and processors.
 */
public interface StreamInterface
{

    /**
     * Loads the stream configuration.
     */
    void loadStream();

    /**
     * Reflects handlers to the logger instance.
     *
     * @param logger logger instance to configure
     */
    void reflectHandlers(Logger logger);

    /**
     * Reflects processors to the logger instance.
     *
     * @param logger logger instance to configure
     */
    void reflectProcessors(Logger logger);

    /**
     * Gets the stream identifier.
     *
     * @return stream identifier
     */
    String getStream();
}

