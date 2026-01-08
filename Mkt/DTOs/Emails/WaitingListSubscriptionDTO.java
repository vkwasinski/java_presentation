package app.mkt.dtos.emails;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Transfer Object for waiting list subscription information.
 */
public class WaitingListSubscriptionDTO
{

    private static final Map<Boolean, String> STATE_TRANSLATION = new HashMap<>();

    static
    {
        STATE_TRANSLATION.put(true, "Subscribed");
        STATE_TRANSLATION.put(false, "Unsubscribed");
    }

    public static final String EMAIL_ADDRESS = "emailAddress";

    public static final String SUBSCRIPTION_STATE = "subscriptionState";

    public static final String EXTERNAL_CONTACT_ID = "externalContactID";

    public static final String SEGMENTATION_FIELD_VALUES = "segmentationFieldValues";

    private String emailAddress = "";

    private String subscriptionState = "";

    private String externalContactID = "";

    private List<Map<String, Object>> segmentationFieldValues = new ArrayList<>();

    /**
     * Constructs a WaitingListSubscriptionDTO from a map of data.
     *
     * @param data map containing waiting list subscription data
     */
    @SuppressWarnings("unchecked")
    public WaitingListSubscriptionDTO(Map<String, Object> data)
    {
        if (data == null)
        {
            return;
        }

        this.emailAddress = String.valueOf(data.getOrDefault(EMAIL_ADDRESS, ""));
        Object subscriptionStateObj = data.get(SUBSCRIPTION_STATE);
        if (subscriptionStateObj instanceof Boolean)
        {
            this.subscriptionState = STATE_TRANSLATION.getOrDefault(subscriptionStateObj, "Unsubscribed");
        }
        else
        {
            this.subscriptionState = String.valueOf(data.getOrDefault(SUBSCRIPTION_STATE, "Unsubscribed"));
        }
        this.externalContactID = String.valueOf(data.getOrDefault(EXTERNAL_CONTACT_ID, ""));
        this.segmentationFieldValues = (List<Map<String, Object>>) data.getOrDefault(SEGMENTATION_FIELD_VALUES, new ArrayList<>());
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public WaitingListSubscriptionDTO setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
        return this;
    }

    public String getSubscriptionState()
    {
        return subscriptionState;
    }

    public WaitingListSubscriptionDTO setSubscriptionState(String subscriptionState)
    {
        this.subscriptionState = subscriptionState;
        return this;
    }

    public String getExternalContactID()
    {
        return externalContactID;
    }

    public WaitingListSubscriptionDTO setExternalContactID(String externalContactID)
    {
        this.externalContactID = externalContactID;
        return this;
    }

    public List<Map<String, Object>> getSegmentationFieldValues()
    {
        return segmentationFieldValues;
    }

    public WaitingListSubscriptionDTO setSegmentationFieldValues(List<Map<String, Object>> segmentationFieldValues)
    {
        this.segmentationFieldValues = segmentationFieldValues != null ? segmentationFieldValues : new ArrayList<>();
        return this;
    }

    /**
     * Converts the DTO to JSON string.
     *
     * @return JSON string representation
     */
    public String toJSON()
    {
        Map<String, Object> map = new HashMap<>();
        map.put(EMAIL_ADDRESS, emailAddress);
        map.put(SUBSCRIPTION_STATE, subscriptionState);
        map.put(EXTERNAL_CONTACT_ID, externalContactID);
        map.put(SEGMENTATION_FIELD_VALUES, segmentationFieldValues);

        return new Gson().toJson(map);
    }
}

