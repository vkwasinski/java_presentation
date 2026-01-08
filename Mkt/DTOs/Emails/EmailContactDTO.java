package app.mkt.dtos.emails;

import app.mkt.interfaces.EmailContactDTOInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Data Transfer Object for email contact information.
 * Implements EmailContactDTOInterface.
 */
public class EmailContactDTO implements EmailContactDTOInterface
{

    private static final Map<Boolean, String> STATE_TRANSLATION = new HashMap<>();

    static
    {
        STATE_TRANSLATION.put(true, "Subscribed");
        STATE_TRANSLATION.put(false, "Unsubscribed");
    }

    private String emailAddress = "";

    private String subscriptionState = "";

    private String externalContactID = "";

    private List<Map<String, Object>> segmentationFieldValues = new ArrayList<>();

    /**
     * Constructs an EmailContactDTO from a map of data.
     *
     * @param data map containing email contact data
     */
    @SuppressWarnings("unchecked")
    public EmailContactDTO(Map<String, Object> data)
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

    @Override
    public String getEmailAddress()
    {
        return emailAddress;
    }

    @Override
    public String getSubscriptionState()
    {
        return subscriptionState;
    }

    @Override
    public String getExternalContactID()
    {
        return externalContactID;
    }

    @Override
    public List<Map<String, Object>> getSegmentationFieldValues()
    {
        return segmentationFieldValues;
    }

    @Override
    public EmailContactDTOInterface setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
        return this;
    }

    @Override
    public EmailContactDTOInterface setSubscriptionState(String subscriptionState)
    {
        this.subscriptionState = subscriptionState;
        return this;
    }

    @Override
    public EmailContactDTOInterface setExternalContactID(String externalContactID)
    {
        this.externalContactID = externalContactID;
        return this;
    }

    @Override
    public EmailContactDTOInterface setSegmentationFieldValues(List<Map<String, Object>> segmentationFieldValues)
    {
        this.segmentationFieldValues = segmentationFieldValues != null ? segmentationFieldValues : new ArrayList<>();
        return this;
    }

    /**
     * Gets an obscured version of the email for logging.
     *
     * @return obscured email address
     */
    public String getObscuredEmail()
    {
        return emailAddress.replaceAll("(\\w{2}).+?@(\\w{2}).+$", "$1******@$2*****");
    }

    @Override
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

