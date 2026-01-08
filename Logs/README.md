# Log Service - Java 8 Port

The service provides a flexible logging system using the Strategy pattern for different logger types (Web, CLI, Audit) and Stream pattern for different output destinations.

## Features

- **Strategy Pattern**: Different logger strategies (Web, CLI, Audit)
- **Stream Pattern**: Different output streams (File, AuditFile, NewRelic)
- **Singleton Pattern**: Thread-safe singleton implementation
- **Debug Mode**: Conditional debug logging based on environment variable
- **Daily Log Files**: Automatic daily log file rotation
- **Channel Configuration**: Environment-based channel selection

## Requirements

- Java 8 or higher
- Maven 3.x

## Dependencies

- SLF4J 1.7.36 (Logging facade)
- Logback 1.2.12 (Logging implementation)

## Environment Variables

- `LOG_CHANNEL`: Channel for web logging (default: "file")
- `LOG_CHANNEL_CLI`: Channel for CLI logging (default: "file")
- `AUDIT_FILE_CHANNEL`: Channel for audit logging (default: "audit-file")
- `LOG_PATH`: Path for log files (default: "logs/")
- `AUDIT_PATH`: Path for audit files (default: "logs/audit/")
- `DEBUG`: Enable debug logging ("true" or "1")

## Usage

### Basic Usage

```java
import app.logs.Log;
import org.slf4j.Logger;

// Get web logger
Logger webLogger = Log.web();
webLogger.info("Application started");
webLogger.error("An error occurred");

// Get CLI logger
Logger cliLogger = Log.cli();
cliLogger.info("CLI operation completed");

// Get audit logger
Logger auditLogger = Log.audit();
auditLogger.info("User action logged");
```

### Custom Logger

```java
import app.logs.CustomLogger;

CustomLogger logger = new CustomLogger("my-component");
logger.info("Info message");
logger.debug("Debug message (only if DEBUG=true)");
logger.warning("Warning message");
logger.error("Error message");
```

## Log Channels

- **file**: File-based logging (default)
- **audit-file**: Audit file logging
- **newrelic**: NewRelic integration (placeholder)
- **newrelic-cli**: NewRelic CLI integration (placeholder)

## Package Structure

- `app.logs`: Main logging classes
  - `Log.java`: Main logging service with static methods
  - `AbstractLogger.java`: Abstract base logger
  - `CustomLogger.java`: Custom logger with debug mode support
- `app.logs.strategies`: Logger strategies
  - `LoggerWebStrategy.java`: Web application logging strategy
  - `LoggerCliStrategy.java`: CLI application logging strategy
  - `LoggerAuditStrategy.java`: Audit logging strategy
- `app.logs.streams`: Stream implementations
  - `FileStream.java`: File-based stream
  - `AuditFileStream.java`: Audit file stream
  - `NewRelicStream.java`: NewRelic stream (placeholder)
  - `StreamFactory.java`: Factory for creating streams
- `app.logs.interfaces`: Interface definitions
  - `LoggerStrategyInterface.java`: Logger strategy interface
  - `StreamInterface.java`: Stream interface
  - `ChannelInterface.java`: Channel constants interface

## Building

```bash
mvn clean compile
```

## Logback Configuration

For advanced configuration, create a `logback.xml` file in your resources directory:

```xml
<configuration>
    <appender name="FILE" class="ch.qos.logback.core.FileAppender">
        <file>logs/application.log</file>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    
    <root level="INFO">
        <appender-ref ref="FILE" />
    </root>
</configuration>
```

## Notes

- The service uses SLF4J as the logging facade for flexibility
- Logback is used as the default implementation
- Debug logging is controlled by the `DEBUG` environment variable
- Daily log files are created automatically
- Stream handlers and processors are configured via logback.xml in Java
- NewRelic integration is a placeholder and would require additional dependencies

