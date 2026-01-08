package app.mkt.client;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Abstract base class for HTTP clients.
 * Provides common HTTP functionality using OkHttp library.
 */
public abstract class AbstractHttpClient
{

    protected static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    protected static final MediaType FORM_URLENCODED = MediaType.parse("application/x-www-form-urlencoded");

    protected OkHttpClient httpClient;

    protected Gson gson;

    protected Map<String, String> defaultHeaders;

    /**
     * Constructs a new AbstractHttpClient instance.
     */
    public AbstractHttpClient()
    {
        this.httpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();
        this.gson = new Gson();
        this.defaultHeaders = new HashMap<>();
    }

    /**
     * Sets default headers for all requests.
     *
     * @param headers map of header names to values
     */
    protected void setDefaultHeaders(Map<String, String> headers)
    {
        this.defaultHeaders = headers != null ? new HashMap<>(headers) : new HashMap<>();
    }

    /**
     * Performs a POST request.
     *
     * @param url the URL to post to
     * @param body the request body
     * @param headers additional headers
     * @return HttpResponse containing status code and body
     * @throws IOException if request fails
     */
    protected HttpResponse post(String url, String body, Map<String, String> headers) throws IOException
    {
        RequestBody requestBody = RequestBody.create(body != null ? body : "", JSON);

        Request.Builder requestBuilder = new Request.Builder()
            .url(url)
            .post(requestBody);

        for (Map.Entry<String, String> header : defaultHeaders.entrySet())
        {
            requestBuilder.addHeader(header.getKey(), header.getValue());
        }

        if (headers != null)
        {
            for (Map.Entry<String, String> header : headers.entrySet())
            {
                requestBuilder.addHeader(header.getKey(), header.getValue());
            }
        }

        Request request = requestBuilder.build();

        try (Response response = httpClient.newCall(request).execute())
        {
            String responseBody = response.body() != null ? response.body().string() : "";
            return new HttpResponse(response.code(), responseBody);
        }
    }

    /**
     * Performs a POST request with form data.
     *
     * @param url the URL to post to
     * @param formData map of form parameters
     * @param headers additional headers
     * @return HttpResponse containing status code and body
     * @throws IOException if request fails
     */
    protected HttpResponse postForm(String url, Map<String, String> formData, Map<String, String> headers) throws IOException
    {
        FormBody.Builder formBuilder = new FormBody.Builder();

        if (formData != null)
        {
            for (Map.Entry<String, String> entry : formData.entrySet())
            {
                formBuilder.add(entry.getKey(), entry.getValue() != null ? entry.getValue() : "");
            }
        }

        RequestBody requestBody = formBuilder.build();

        Request.Builder requestBuilder = new Request.Builder()
            .url(url)
            .post(requestBody);

        for (Map.Entry<String, String> header : defaultHeaders.entrySet())
        {
            requestBuilder.addHeader(header.getKey(), header.getValue());
        }

        if (headers != null)
        {
            for (Map.Entry<String, String> header : headers.entrySet())
            {
                requestBuilder.addHeader(header.getKey(), header.getValue());
            }
        }

        Request request = requestBuilder.build();

        try (Response response = httpClient.newCall(request).execute())
        {
            String responseBody = response.body() != null ? response.body().string() : "";
            return new HttpResponse(response.code(), responseBody);
        }
    }
}

