package app.mkt.client;

import app.mkt.dtos.emails.EmailContactDTO;
import app.mkt.dtos.emails.WaitingListSubscriptionDTO;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Client for marketing email service operations.
 * Handles email contact subscriptions and transactional messages.
 */
public class MktEmailClient extends AbstractHttpClient
{

    protected static final String BASE_URL = "https://api.mkt-service.com";

    protected static final String RESOURCE_URI = "/email/v1";

    protected static final String AUTH_URL = "https://auth.mkt-service.com/OAuth2/Token";

    protected static final String EMAIL_CREATE_CONTACT = "/List/{listId}/Contact";

    protected static final String KEY_MKT_TRANSACTIONAL_EMAIL_LIST_ID = "MKT_TRANSACTIONAL_EMAIL_LIST_ID";

    protected static final String KEY_MKT_SEGMENTATION_FIELD_GROUP_ID = "MKT_SEGMENTATION_FIELD_GROUP_ID";

    protected String token = "";

    protected String tokenType = "";

    protected int tokenExpirationInSeconds = 0;

    /**
     * Constructs a new MktEmailClient and authenticates.
     */
    public MktEmailClient()
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
        String clientId = System.getenv("MKT_EMAIL_API_CLIENT_ID");
        String clientSecret = System.getenv("MKT_EMAIL_API_CLIENT_SECRET");

        if (clientId == null || clientSecret == null)
        {
            System.err.println("[MktEmailClient] :: [auth] - Missing credentials");
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
            System.err.println("[MktEmailClient] :: [auth] - " + e.getMessage());
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
     * Gets the email URI with list ID from environment.
     *
     * @return email URI
     */
    public String getEmailURI()
    {
        String listId = System.getenv(KEY_MKT_TRANSACTIONAL_EMAIL_LIST_ID);
        if (listId == null)
        {
            listId = "";
        }
        return getResource(EMAIL_CREATE_CONTACT.replace("{listId}", listId));
    }

    /**
     * Gets the subscription list URI.
     *
     * @param listId list ID
     * @return subscription list URI
     */
    public String getSubscriptionListIdURI(String listId)
    {
        return getResource(EMAIL_CREATE_CONTACT.replace("{listId}", listId));
    }

    /**
     * Creates an email contact.
     *
     * @param contactDto email contact DTO
     * @return true if successful
     * @throws Exception if request fails
     */
    public boolean emailCreateContact(EmailContactDTO contactDto) throws Exception
    {
        String body = contactDto.toJSON();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());
        headers.put("Content-Type", "application/json");

        HttpResponse response = post(getEmailURI(), body, headers);

        if (response.getStatusCode() != 201)
        {
            throw new Exception("Email create contact did not succeed.");
        }

        return true;
    }

    /**
     * Handles user subscription to a list.
     *
     * @param contactDTO email contact DTO
     * @param suppressionListId suppression list ID
     * @return true if successful
     * @throws Exception if request fails
     */
    public boolean handleUserSubscriptionToList(EmailContactDTO contactDTO, int suppressionListId) throws Exception
    {
        String body = contactDTO.toJSON();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());
        headers.put("Content-Type", "application/json");

        HttpResponse response = post(getSubscriptionListIdURI(String.valueOf(suppressionListId)), body, headers);

        if (response.getStatusCode() != 201)
        {
            throw new Exception("Subscription to list did not succeed.");
        }

        return true;
    }

    /**
     * Updates a waitlist subscription.
     *
     * @param waitingListSubscriptionDTO waiting list subscription DTO
     * @param waitlistId waitlist ID
     * @return true if successful
     * @throws Exception if request fails
     */
    public boolean updateWaitlist(WaitingListSubscriptionDTO waitingListSubscriptionDTO, int waitlistId) throws Exception
    {
        String body = waitingListSubscriptionDTO.toJSON();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());
        headers.put("Content-Type", "application/json");

        HttpResponse response = post(getSubscriptionListIdURI(String.valueOf(waitlistId)), body, headers);

        if (response.getStatusCode() != 200)
        {
            throw new Exception("Subscription to list did not succeed.");
        }

        return true;
    }
}

