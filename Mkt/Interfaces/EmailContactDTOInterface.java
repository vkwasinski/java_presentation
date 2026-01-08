package app.mkt.interfaces;

import java.util.List;
import java.util.Map;

/**
 * Interface for email contact DTO operations.
 */
public interface EmailContactDTOInterface
{

    String EMAIL_ADDRESS = "emailAddress";

    String SUBSCRIPTION_STATE = "subscriptionState";

    String EXTERNAL_CONTACT_ID = "externalContactID";

    String SEGMENTATION_FIELD_VALUES = "segmentationFieldValues";

    /**
     * Gets the email address.
     *
     * @return email address
     */
    String getEmailAddress();

    /**
     * Gets the subscription state.
     *
     * @return subscription state
     */
    String getSubscriptionState();

    /**
     * Gets the external contact ID.
     *
     * @return external contact ID
     */
    String getExternalContactID();

    /**
     * Gets the segmentation field values.
     *
     * @return segmentation field values
     */
    List<Map<String, Object>> getSegmentationFieldValues();

    /**
     * Sets the email address.
     *
     * @param emailAddress email address
     * @return this instance for method chaining
     */
    EmailContactDTOInterface setEmailAddress(String emailAddress);

    /**
     * Sets the subscription state.
     *
     * @param subscriptionState subscription state
     * @return this instance for method chaining
     */
    EmailContactDTOInterface setSubscriptionState(String subscriptionState);

    /**
     * Sets the external contact ID.
     *
     * @param externalContactID external contact ID
     * @return this instance for method chaining
     */
    EmailContactDTOInterface setExternalContactID(String externalContactID);

    /**
     * Sets the segmentation field values.
     *
     * @param segmentationFieldValues segmentation field values
     * @return this instance for method chaining
     */
    EmailContactDTOInterface setSegmentationFieldValues(List<Map<String, Object>> segmentationFieldValues);

    /**
     * Converts the DTO to JSON string.
     *
     * @return JSON string representation
     */
    String toJSON();
}

