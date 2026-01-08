package app.mkt.dtos.sms;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Transfer Object for SMS contact information.
 */
public class SMSContactDTO
{

    public static final String PHONE_NUMBER = "phoneNumber";

    public static final String EMAIL_ADDRESS = "emailAddress";

    public static final String FIRST_NAME = "firstName";

    public static final String LAST_NAME = "lastName";

    public static final String BIRTHDAY = "birthday";

    public static final String POSTAL_CODE = "postalCode";

    public static final String OPTED_OUT = "optedOut";

    public static final String SEGMENTATION_FIELD_VALUES = "segmentationFieldValues";

    private String phoneNumber = "";

    private String emailAddress = "";

    private String firstName = "";

    private String lastName = "";

    private String birthday = "";

    private String postalCode = "";

    private boolean optedOut = false;

    private List<Map<String, Object>> segmentationFieldValues = new ArrayList<>();

    /**
     * Constructs an SMSContactDTO from a map of data.
     *
     * @param data map containing SMS contact data
     */
    @SuppressWarnings("unchecked")
    public SMSContactDTO(Map<String, Object> data)
    {
        if (data == null)
        {
            return;
        }

        this.phoneNumber = String.valueOf(data.getOrDefault(PHONE_NUMBER, ""));
        this.emailAddress = String.valueOf(data.getOrDefault(EMAIL_ADDRESS, ""));
        this.firstName = String.valueOf(data.getOrDefault(FIRST_NAME, ""));
        this.lastName = String.valueOf(data.getOrDefault(LAST_NAME, ""));
        this.birthday = String.valueOf(data.getOrDefault(BIRTHDAY, ""));
        this.postalCode = String.valueOf(data.getOrDefault(POSTAL_CODE, ""));
        this.optedOut = (Boolean) data.getOrDefault(OPTED_OUT, false);
        this.segmentationFieldValues = (List<Map<String, Object>>) data.getOrDefault(SEGMENTATION_FIELD_VALUES, new ArrayList<>());
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public SMSContactDTO setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public SMSContactDTO setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
        return this;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public SMSContactDTO setFirstName(String firstName)
    {
        this.firstName = firstName;
        return this;
    }

    public String getLastName()
    {
        return lastName;
    }

    public SMSContactDTO setLastName(String lastName)
    {
        this.lastName = lastName;
        return this;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public SMSContactDTO setBirthday(String birthday)
    {
        this.birthday = birthday;
        return this;
    }

    public String getPostalCode()
    {
        return postalCode;
    }

    public SMSContactDTO setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
        return this;
    }

    public boolean isOptedOut()
    {
        return optedOut;
    }

    public SMSContactDTO setOptedOut(boolean optedOut)
    {
        this.optedOut = optedOut;
        return this;
    }

    public List<Map<String, Object>> getSegmentationFieldValues()
    {
        return segmentationFieldValues;
    }

    public SMSContactDTO setSegmentationFieldValues(List<Map<String, Object>> segmentationFieldValues)
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
        map.put(PHONE_NUMBER, phoneNumber);
        map.put(EMAIL_ADDRESS, emailAddress);
        map.put(FIRST_NAME, firstName);
        map.put(LAST_NAME, lastName);
        map.put(BIRTHDAY, birthday);
        map.put(POSTAL_CODE, postalCode);
        map.put(OPTED_OUT, optedOut);
        map.put(SEGMENTATION_FIELD_VALUES, segmentationFieldValues);

        return new Gson().toJson(map);
    }
}

