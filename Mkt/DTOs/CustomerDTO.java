package app.mkt.dtos;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Transfer Object for customer information.
 */
public class CustomerDTO
{

    public static final String ADDRESS = "address";

    public static final String BIRTHDAY = "birthday";

    public static final String CUSTOMER_NUMBER = "customerNumber";

    public static final String EMAIL = "email";

    public static final String FIRST_NAME = "firstName";

    public static final String GENDER = "gender";

    public static final String HOME_PHONE = "homePhone";

    public static final String LAST_NAME = "lastName";

    public static final String LOYALTY = "loyalty";

    public static final String META1 = "meta1";

    public static final String META2 = "meta2";

    public static final String META3 = "meta3";

    public static final String META4 = "meta4";

    public static final String META5 = "meta5";

    public static final String MOBILE_PHONE = "mobilePhone";

    public static final String PREFERRED_STORE_NUMBER = "preferredStoreNumber";

    public static final String REGISTERED = "registered";

    public static final String SOCIAL = "social";

    public static final String WORK_PHONE = "workPhone";

    public static final String ZIP_CODE = "zipCode";

    private Map<String, Object> address = new HashMap<>();

    private String birthday = "";

    private String customerNumber = "";

    private String email = "";

    private String firstName = "";

    private String gender = "";

    private String homePhone = "";

    private String lastName = "";

    private List<Object> loyalty = new ArrayList<>();

    private String meta1 = "";

    private String meta2 = "";

    private String meta3 = "";

    private String meta4 = "";

    private String meta5 = "";

    private String mobilePhone = "";

    private String preferredStoreNumber = "";

    private String registered = "";

    private List<Object> social = new ArrayList<>();

    private String workPhone = "";

    private String zipCode = "";

    /**
     * Constructs a CustomerDTO from a map of data.
     *
     * @param data map containing customer data
     */
    @SuppressWarnings("unchecked")
    public CustomerDTO(Map<String, Object> data)
    {
        if (data == null)
        {
            return;
        }

        this.address = (Map<String, Object>) data.getOrDefault(ADDRESS, new HashMap<>());
        this.birthday = String.valueOf(data.getOrDefault(BIRTHDAY, ""));
        this.customerNumber = String.valueOf(data.getOrDefault(CUSTOMER_NUMBER, ""));
        this.email = String.valueOf(data.getOrDefault(EMAIL, ""));
        this.firstName = String.valueOf(data.getOrDefault(FIRST_NAME, ""));
        this.gender = String.valueOf(data.getOrDefault(GENDER, ""));
        this.homePhone = String.valueOf(data.getOrDefault(HOME_PHONE, ""));
        this.lastName = String.valueOf(data.getOrDefault(LAST_NAME, ""));
        this.loyalty = (List<Object>) data.getOrDefault(LOYALTY, new ArrayList<>());
        this.meta1 = String.valueOf(data.getOrDefault(META1, ""));
        this.meta2 = String.valueOf(data.getOrDefault(META2, ""));
        this.meta3 = String.valueOf(data.getOrDefault(META3, ""));
        this.meta4 = String.valueOf(data.getOrDefault(META4, ""));
        this.meta5 = String.valueOf(data.getOrDefault(META5, ""));
        this.mobilePhone = String.valueOf(data.getOrDefault(MOBILE_PHONE, ""));
        this.preferredStoreNumber = String.valueOf(data.getOrDefault(PREFERRED_STORE_NUMBER, ""));
        this.registered = String.valueOf(data.getOrDefault(REGISTERED, ""));
        this.social = (List<Object>) data.getOrDefault(SOCIAL, new ArrayList<>());
        this.workPhone = String.valueOf(data.getOrDefault(WORK_PHONE, ""));
        this.zipCode = String.valueOf(data.getOrDefault(ZIP_CODE, ""));
    }

    /**
     * Converts the DTO to JSON string.
     *
     * @return JSON string representation
     */
    public String toJSON()
    {
        Map<String, Object> map = new HashMap<>();
        map.put(ADDRESS, address);
        map.put(BIRTHDAY, birthday);
        map.put(CUSTOMER_NUMBER, customerNumber);
        map.put(EMAIL, email);
        map.put(FIRST_NAME, firstName);
        map.put(GENDER, gender);
        map.put(HOME_PHONE, homePhone);
        map.put(LAST_NAME, lastName);
        map.put(LOYALTY, loyalty);
        map.put(META1, meta1);
        map.put(META2, meta2);
        map.put(META3, meta3);
        map.put(META4, meta4);
        map.put(META5, meta5);
        map.put(MOBILE_PHONE, mobilePhone);
        map.put(PREFERRED_STORE_NUMBER, preferredStoreNumber);
        map.put(REGISTERED, registered);
        map.put(SOCIAL, social);
        map.put(WORK_PHONE, workPhone);
        map.put(ZIP_CODE, zipCode);

        return new Gson().toJson(map);
    }
}

