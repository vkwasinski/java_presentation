package app.mkt.dtos.sms;

import app.mkt.interfaces.MessageDTOInterface;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Data Transfer Object for SMS message information.
 * Implements MessageDTOInterface.
 */
public class MessageDTO implements MessageDTOInterface
{

    private String phoneNumber = "";

    private String messageContent = "";

    /**
     * Constructs a MessageDTO from a map of data.
     *
     * @param data map containing message data
     */
    public MessageDTO(Map<String, Object> data)
    {
        if (data == null)
        {
            return;
        }

        this.phoneNumber = String.valueOf(data.getOrDefault(PHONE_NUMBER, ""));
        this.messageContent = String.valueOf(data.getOrDefault(MESSAGE_CONTENT, ""));
    }

    @Override
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    @Override
    public MessageDTOInterface setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public String getMessageContent()
    {
        return messageContent;
    }

    @Override
    public MessageDTOInterface setMessageContent(String messageContent)
    {
        this.messageContent = messageContent;
        return this;
    }

    @Override
    public String getObscuredPhoneNumber()
    {
        if (phoneNumber == null || phoneNumber.length() < 4)
        {
            return "****";
        }
        return phoneNumber.substring(0, phoneNumber.length() - 4) + "****";
    }

    @Override
    public String toJSON()
    {
        Map<String, Object> map = new HashMap<>();
        map.put(PHONE_NUMBER, phoneNumber);
        map.put(MESSAGE_CONTENT, messageContent);

        return new Gson().toJson(map);
    }
}

