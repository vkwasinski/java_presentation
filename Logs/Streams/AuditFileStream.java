package app.logs.streams;

import app.logs.interfaces.StreamInterface;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Audit file stream implementation for logging to daily audit files.
 * Creates audit files with format: audit-YYYY-MM-DD.log
 */
public class AuditFileStream extends AbstractStream implements StreamInterface
{

    private static final String DEFAULT_AUDIT_PATH = "logs/audit/";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Constructs a new AuditFileStream and loads the stream.
     */
    public AuditFileStream()
    {
        loadStream();
    }

    /**
     * Loads the audit file stream path.
     * Creates the audit file if it doesn't exist.
     */
    @Override
    public void loadStream()
    {
        String auditPath = System.getenv("AUDIT_PATH");
        if (auditPath == null || auditPath.isEmpty())
        {
            auditPath = DEFAULT_AUDIT_PATH;
        }

        if (!auditPath.endsWith("/") && !auditPath.endsWith("\\"))
        {
            auditPath += "/";
        }

        String dateStr = LocalDate.now().format(DATE_FORMATTER);
        String filename = auditPath + "audit-" + dateStr + ".log";

        try
        {
            File auditDir = new File(auditPath);
            if (!auditDir.exists())
            {
                auditDir.mkdirs();
            }

            File auditFile = new File(filename);
            if (!auditFile.exists())
            {
                auditFile.createNewFile();
            }

            this.stream = filename;
        }
        catch (IOException e)
        {
            System.err.println("[AuditFileStream] :: [loadStream] - Failed to create audit file: " + e.getMessage());
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

