package mkt.client;

import dto.CustomerDTO;
import dto.OrderDTO;
import dto.ProductDTO;
import interfaces.AbstractHttpClient;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Client for marketing data service operations.
 * Handles product, order, and customer data synchronization.
 */
public class MktDataClient extends AbstractHttpClient
{

    protected static final String BASE_URL = "https://api.mkt-service.com";

    protected static final String DATA_RESOURCE_URI = "/data/v1";

    protected static final String AUTH_URL = "https://auth.mkt-service.com/OAuth2/Token";

    protected static final String PRODUCT_URI = "/Product";

    protected static final String ORDER_URI = "/Order";

    protected static final String CUSTOMER_URI = "/Customer";

    protected String token = "";

    protected String tokenType = "";

    protected int tokenExpirationInSeconds = 0;

    /**
     * Constructs a new MktDataClient and authenticates.
     */
    public MktDataClient()
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

    /**
     * Gets the base URL for the marketing service.
     *
     * @return base URL
     */
    protected static String getBaseURL()
    {
        return BASE_URL;
    }

    /**
     * Gets the data resource URL.
     *
     * @param resourceURI resource URI
     * @return full data resource URL
     */
    protected static String getDataResource(String resourceURI)
    {
        return getBaseURL() + DATA_RESOURCE_URI + resourceURI;
    }

    /**
     * Gets the authentication URL.
     *
     * @return authentication URL
     */
    protected static String getAuthUrl()
    {
        return AUTH_URL;
    }

    /**
     * Gets the authentication token.
     *
     * @return authentication token
     */
    protected String getToken()
    {
        return token;
    }

    /**
     * Authenticates with the marketing service.
     */
    protected void auth()
    {
        String clientId = System.getenv("MKT_DATA_API_CLIENT_ID");
        String clientSecret = System.getenv("MKT_DATA_API_CLIENT_SECRET");

        if (clientId == null || clientSecret == null)
        {
            System.err.println("[MktDataClient] :: [auth] - Missing credentials");
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
            System.err.println("[MktDataClient] :: [auth] - " + e.getMessage());
        }
    }

    /**
     * Handles the token response from authentication.
     *
     * @param authResponse authentication response map
     */
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

    /**
     * Checks if the client is authenticated.
     *
     * @return true if authenticated
     */
    protected boolean isAuthenticated()
    {
        return !getToken().isEmpty();
    }

    /**
     * Posts a product to the marketing service.
     *
     * @param productDTO product DTO
     * @return true if successful
     * @throws Exception if request fails
     */
    public boolean postProduct(ProductDTO productDTO) throws Exception
    {
        String body = "[" + productDTO.toJSON() + "]";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());
        headers.put("Content-Type", "application/json");

        HttpResponse response = post(getDataResource(PRODUCT_URI), body, headers);

        if (response.getStatusCode() != 201)
        {
            throw new Exception("Product importation request did not succeed");
        }

        return true;
    }

    /**
     * Posts a collection of products to the marketing service.
     *
     * @param productDTOCollection list of product DTOs
     * @return true if successful
     * @throws Exception if request fails
     */
    public boolean postProductCollection(List<ProductDTO> productDTOCollection) throws Exception
    {
        Gson gson = new Gson();
        String body = gson.toJson(productDTOCollection);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());
        headers.put("Content-Type", "application/json");

        HttpResponse response = post(getDataResource(PRODUCT_URI), body, headers);

        if (response.getStatusCode() != 201)
        {
            throw new Exception("Product Collection importation request did not succeed");
        }

        return true;
    }

    /**
     * Posts an order to the marketing service.
     *
     * @param orderDTO order DTO
     * @return true if successful
     * @throws Exception if request fails
     */
    public boolean postOrder(OrderDTO orderDTO) throws Exception
    {
        String body = "[" + orderDTO.toJSON() + "]";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());
        headers.put("Content-Type", "application/json");

        HttpResponse response = post(getDataResource(ORDER_URI), body, headers);

        if (response.getStatusCode() != 201)
        {
            throw new Exception("Order importation request did not succeed");
        }

        return true;
    }

    /**
     * Posts a collection of orders to the marketing service.
     *
     * @param orderDTOCollection list of order DTOs
     * @return true if successful
     * @throws Exception if request fails
     */
    public boolean postOrderCollection(List<OrderDTO> orderDTOCollection) throws Exception
    {
        Gson gson = new Gson();
        String body = gson.toJson(orderDTOCollection);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());
        headers.put("Content-Type", "application/json");

        HttpResponse response = post(getDataResource(ORDER_URI), body, headers);

        if (response.getStatusCode() != 201)
        {
            throw new Exception("Order Collection importation request did not succeed");
        }

        return true;
    }

    /**
     * Posts a customer to the marketing service.
     *
     * @param customerDTO customer DTO
     * @return true if successful
     * @throws Exception if request fails
     */
    public boolean postCustomer(CustomerDTO customerDTO) throws Exception
    {
        String body = "[" + customerDTO.toJSON() + "]";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());
        headers.put("Content-Type", "application/json");

        HttpResponse response = post(getDataResource(CUSTOMER_URI), body, headers);

        if (response.getStatusCode() != 201)
        {
            throw new Exception("Customer importation request did not succeed");
        }

        return true;
    }

    /**
     * Posts a collection of customers to the marketing service.
     *
     * @param customerDTOCollection list of customer DTOs
     * @return true if successful
     * @throws Exception if request fails
     */
    public boolean postCustomerCollection(List<CustomerDTO> customerDTOCollection) throws Exception
    {
        Gson gson = new Gson();
        String body = gson.toJson(customerDTOCollection);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());
        headers.put("Content-Type", "application/json");

        HttpResponse response = post(getDataResource(CUSTOMER_URI), body, headers);

        if (response.getStatusCode() != 201)
        {
            throw new Exception("Customer Collection importation request did not succeed");
        }

        return true;
    }
}

