package app.logs.interfaces;

import app.logs.streams.AuditFileStream;
import app.logs.streams.FileStream;
import app.logs.streams.NewRelicStream;

import java.util.HashMap;
import java.util.Map;

/**
 * Interface defining log channel constants and mappings.
 */
public interface ChannelInterface
{

    String FILE_CHANNEL = "file";

    String AUDIT_FILE_CHANNEL = "audit-file";

    String NEWRELIC_CHANNEL = "newrelic";

    String NEWRELIC_CLI_CHANNEL = "newrelic-cli";

    Map<String, Class<? extends StreamInterface>> LOG_CHANNELS = createLogChannels();

    static Map<String, Class<? extends StreamInterface>> createLogChannels()
    {
        Map<String, Class<? extends StreamInterface>> channels = new HashMap<>();
        channels.put(FILE_CHANNEL, FileStream.class);
        channels.put(AUDIT_FILE_CHANNEL, AuditFileStream.class);
        channels.put(NEWRELIC_CHANNEL, NewRelicStream.class);
        channels.put(NEWRELIC_CLI_CHANNEL, NewRelicStream.class);
        return channels;
    }
}

