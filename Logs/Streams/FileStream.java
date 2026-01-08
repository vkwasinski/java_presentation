package app.logs.streams;

import app.logs.interfaces.StreamInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * File stream implementation for logging to daily log files.
 * Creates log files with format: log-YYYY-MM-DD.log
 */
public class FileStream extends AbstractStream implements StreamInterface
{

    private static final String DEFAULT_LOG_PATH = "logs/";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Constructs a new FileStream and loads the stream.
     */
    public FileStream()
    {
        loadStream();
    }

    /**
     * Loads the file stream path.
     * Creates the log file if it doesn't exist.
     */
    @Override
    public void loadStream()
    {
        String logPath = System.getenv("LOG_PATH");
        if (logPath == null || logPath.isEmpty())
        {
            logPath = DEFAULT_LOG_PATH;
        }

        if (!logPath.endsWith("/") && !logPath.endsWith("\\"))
        {
            logPath += "/";
        }

        String dateStr = LocalDate.now().format(DATE_FORMATTER);
        String filename = logPath + "log-" + dateStr + ".log";

        try
        {
            File logDir = new File(logPath);
            if (!logDir.exists())
            {
                logDir.mkdirs();
            }

            File logFile = new File(filename);
            if (!logFile.exists())
            {
                logFile.createNewFile();
            }

            this.stream = filename;
        }
        catch (IOException e)
        {
            System.err.println("[FileStream] :: [loadStream] - Failed to create log file: " + e.getMessage());
            this.stream = filename;
        }
    }

    /**
     * Reflects handlers to the logger instance.
     * Note: In Java with SLF4J, handlers are configured via logback.xml.
     * This method is kept for interface compatibility.
     *
     * @param logger logger instance
     */
    @Override
    public void reflectHandlers(Logger logger)
    {
    }

    /**
     * Reflects processors to the logger instance.
     * Note: In Java with SLF4J, processors are configured via logback.xml.
     * This method is kept for interface compatibility.
     *
     * @param logger logger instance
     */
    @Override
    public void reflectProcessors(Logger logger)
    {
    }
}

