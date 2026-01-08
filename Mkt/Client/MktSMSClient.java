package app.mkt.client;

import app.mkt.dtos.sms.MessageDTO;
import app.mkt.dtos.sms.SMSContactDTO;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Client for marketing SMS service operations.
 * Handles SMS contact subscriptions and transactional messages.
 */
public class MktSMSClient extends AbstractHttpClient
{

    protected static final String BASE_URL = "https://api.mkt-service.com";

    protected static final String RESOURCE_URI = "/sms/v1";

    protected static final String AUTH_URL = "https://auth.mkt-service.com/OAuth2/Token";

    protected static final String SMS_CREATE_CONTACT = "/ShortCode/{shortCodeId}/PhoneList/{phoneListId}/Contact";

    protected static final String SEND_SMS = "/ShortCode/{shortCodeId}/PhoneList/{phoneListId}/TransactionalMessage/{transactionalMessageId}/Message";

    protected String token = "";

    protected String tokenType = "";

    protected int tokenExpirationInSeconds = 0;

    /**
     * Constructs a new MktSMSClient and authenticates.
     */
    public MktSMSClient()
    {
        super();
        auth();

        if (!isAuthenticated())
        {
            return;
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());
        headers.put("Content-Type", "application/json");
        setDefaultHeaders(headers);
    }

    protected static String getBaseURL()
    {
        return BASE_URL;
    }

    protected String getResource(String resourceURI)
    {
        return getBaseURL() + RESOURCE_URI + resourceURI;
    }

    protected static String getAuthUrl()
    {
        return AUTH_URL;
    }

    protected String getToken()
    {
        return token;
    }

    protected void auth()
    {
        String clientId = System.getenv("MKT_SMS_API_CLIENT_ID");
        String clientSecret = System.getenv("MKT_SMS_API_CLIENT_SECRET");

        if (clientId == null || clientSecret == null)
        {
            System.err.println("[MktSMSClient] :: [auth] - Missing credentials");
            return;
        }

        try
        {
            Map<String, String> formData = new HashMap<>();
            formData.put("grant_type", "client_credentials");
            formData.put("client_id", clientId);
            formData.put("client_secret", clientSecret);

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/x-www-form-urlencoded");

            HttpResponse authResponse = postForm(getAuthUrl(), formData, headers);

            if (authResponse.getStatusCode() != 200)
            {
                throw new Exception("Auth request did not return 200");
            }

            Gson gson = new Gson();
            Map<String, Object> authResponseObj = gson.fromJson(authResponse.getBody(), Map.class);
            handleTokenResponse(authResponseObj);
        }
        catch (Exception e)
        {
            System.err.println("[MktSMSClient] :: [auth] - " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    protected void handleTokenResponse(Map<String, Object> authResponse)
    {
        this.token = String.valueOf(authResponse.getOrDefault("access_token", ""));
        this.tokenType = String.valueOf(authResponse.getOrDefault("token_type", ""));
        Object expiresIn = authResponse.get("expires_in");
        if (expiresIn instanceof Number)
        {
            this.tokenExpirationInSeconds = ((Number) expiresIn).intValue();
        }
    }

    protected boolean isAuthenticated()
    {
        return !getToken().isEmpty();
    }

    /**
     * Gets the SMS URI with IDs from environment.
     *
     * @return SMS URI
     * @throws Exception if environment variables are missing
     */
    public String getSMSURI() throws Exception
    {
        String smsListId = System.getenv("MKT_TRANSACTIONAL_SMS_LIST_ID");
        if (smsListId == null || smsListId.isEmpty())
        {
            throw new Exception("[MktSMSClient] :: [getSMSURI] :: undefined env var MKT_TRANSACTIONAL_SMS_LIST_ID");
        }

        String shortCodeId = System.getenv("MKT_TRANSACTIONAL_SMS_SHORTCODE_ID");
        if (shortCodeId == null || shortCodeId.isEmpty())
        {
            throw new Exception("[MktSMSClient] :: [getSMSURI] :: undefined env var MKT_TRANSACTIONAL_SMS_SHORTCODE_ID");
        }

        String smsBaseUrl = getResource(SMS_CREATE_CONTACT);
        smsBaseUrl = smsBaseUrl.replace("{phoneListId}", smsListId);
        smsBaseUrl = smsBaseUrl.replace("{shortCodeId}", shortCodeId);

        return smsBaseUrl;
    }

    /**
     * Gets the send SMS URI.
     *
     * @param messageId message ID
     * @return send SMS URI
     * @throws Exception if environment variables are missing
     */
    public String getSendSMSURI(String messageId) throws Exception
    {
        String smsListId = System.getenv("MKT_TRANSACTIONAL_SMS_LIST_ID");
        if (smsListId == null || smsListId.isEmpty())
        {
            throw new Exception("[MktSMSClient] :: [getSMSURI] :: undefined env var MKT_TRANSACTIONAL_SMS_LIST_ID");
        }

        String shortCodeId = System.getenv("MKT_TRANSACTIONAL_SMS_SHORTCODE_ID");
        if (shortCodeId == null || shortCodeId.isEmpty())
        {
            throw new Exception("[MktSMSClient] :: [getSMSURI] :: undefined env var MKT_TRANSACTIONAL_SMS_SHORTCODE_ID");
        }

        String smsBaseUrl = getResource(SEND_SMS);
        smsBaseUrl = smsBaseUrl.replace("{phoneListId}", smsListId);
        smsBaseUrl = smsBaseUrl.replace("{shortCodeId}", shortCodeId);
        smsBaseUrl = smsBaseUrl.replace("{transactionalMessageId}", messageId);

        return smsBaseUrl;
    }

    /**
     * Creates an SMS contact.
     *
     * @param contactDto SMS contact DTO
     * @return true if successful
     * @throws Exception if request fails
     */
    public boolean smsCreateContact(SMSContactDTO contactDto) throws Exception
    {
        String body = contactDto.toJSON();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());
        headers.put("Content-Type", "application/json");

        HttpResponse response = post(getSMSURI(), body, headers);

        if (response.getStatusCode() != 201)
        {
            throw new Exception("Customer sms contact was not created");
        }

        return true;
    }

    /**
     * Sends an SMS message.
     *
     * @param messageId message ID
     * @param message message DTO
     * @return true if successful
     * @throws Exception if request fails
     */
    public boolean sendSMS(int messageId, MessageDTO message) throws Exception
    {
        String body = message.toJSON();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());
        headers.put("Content-Type", "application/json");

        HttpResponse response = post(getSendSMSURI(String.valueOf(messageId)), body, headers);

        if (response.getStatusCode() != 201)
        {
            throw new Exception("Message to " + message.getObscuredPhoneNumber() + " failed to be queued with vendor");
        }

        return true;
    }
}

