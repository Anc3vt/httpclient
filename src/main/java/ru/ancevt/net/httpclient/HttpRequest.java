package ru.ancevt.net.httpclient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ancevt
 */
public final class HttpRequest {

    public static final int DEFAULT_TIMEOUT = 60000;
    public static final String HTTP_1_0 = "HTTP/1.0";
    public static final String HTTP_1_1 = "HTTP/1.1";
    public static final String HTTP_2_0 = "HTTP/2.0";

    private String method;
    private List<HttpHeader> headers;
    private int timeout;
    private byte[] body;
    private String url;
    private String httpVersion;

    public HttpRequest() {
        method = HttpMethod.GET;
        headers = new ArrayList<>();
        timeout = DEFAULT_TIMEOUT;
        httpVersion = HTTP_1_1;
    }

    public HttpRequest(String url) {
        this();
        setUrl(url);
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setUrl(String url) {
        this.url = url;
        
        try {
            final URL mUrl = new URL(url);
            setHeader("Host", mUrl.getHost());
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public byte[] getRequestBody() {
        return body;
    }

    public void setBody(byte[] requestBody) {
        this.body = requestBody;
    }

    public void setBody(HttpVariables vars) {
        this.body = vars.toString().getBytes();
    }

    public byte[] getBody() {
        return this.body;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<HttpHeader> getHeaders() {
        return headers;
    }

    public HttpHeader getHeader(String key) {
        for (final HttpHeader h : headers) {
            if (h.getKey().equalsIgnoreCase(key)) {
                return h;
            }
        }

        return null;
    }
    
    public void setHeader(HttpHeader httpHeader) {
        headers.add(httpHeader);
    }

    public void setHeader(String key, String value) {
        HttpHeader header = getHeader(key);
        if (header == null) {
            header = new HttpHeader(key, value);
            setHeader(header);
        } else {
            header.setValue(value);
        }
    }
    
    public void removeHeader(String key) {
        for(final HttpHeader h : headers) {
            if(h.getKey().equalsIgnoreCase(key)) {
                headers.remove(h);
                removeHeader(key);
                break;
            }
        }
    }

    public void setHeaders(List<HttpHeader> headers) {
        this.headers = headers;
    }

    public void setHeaders(String rawHeaders) {
        final String[] lines = rawHeaders.split("\n");
        for (final String line : lines) {
            getHeaders().add(new HttpHeader(line));
        }
    }

    public void computeContentLength() {
        getHeaders().add(new HttpHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(body.length)));
    }

}
