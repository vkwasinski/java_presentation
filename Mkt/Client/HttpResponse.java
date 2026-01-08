package app.mkt.client;

/**
 * Simple HTTP response wrapper.
 * Contains status code and response body from HTTP requests.
 */
public class HttpResponse
{

    private final int statusCode;

    private final String body;

    /**
     * Constructs a new HttpResponse.
     *
     * @param statusCode HTTP status code
     * @param body response body
     */
    public HttpResponse(int statusCode, String body)
    {
        this.statusCode = statusCode;
        this.body = body;
    }

    /**
     * Gets the HTTP status code.
     *
     * @return status code
     */
    public int getStatusCode()
    {
        return statusCode;
    }

    /**
     * Gets the response body.
     *
     * @return response body
     */
    public String getBody()
    {
        return body;
    }
}

