package app.mkt.interfaces;

/**
 * Interface for message DTO operations.
 */
public interface MessageDTOInterface
{

    String PHONE_NUMBER = "phoneNumber";

    String MESSAGE_CONTENT = "messageContent";

    /**
     * Gets the phone number.
     *
     * @return phone number
     */
    String getPhoneNumber();

    /**
     * Gets the message content.
     *
     * @return message content
     */
    String getMessageContent();

    /**
     * Sets the phone number.
     *
     * @param phoneNumber phone number
     * @return this instance for method chaining
     */
    MessageDTOInterface setPhoneNumber(String phoneNumber);

    /**
     * Sets the message content.
     *
     * @param messageContent message content
     * @return this instance for method chaining
     */
    MessageDTOInterface setMessageContent(String messageContent);

    /**
     * Gets an obscured version of the phone number for logging.
     *
     * @return obscured phone number
     */
    String getObscuredPhoneNumber();

    /**
     * Converts the DTO to JSON string.
     *
     * @return JSON string representation
     */
    String toJSON();
}

