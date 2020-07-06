package ru.ancevt.net.httpclient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ancevt
 */
public class HttpRequestMaker {
    
    private List<HttpHeader> defaultHeaders;
    
    public HttpRequestMaker() {
        defaultHeaders = new ArrayList<>();
    }
    
    public void addDefaultHttpHeader(HttpHeader header) {
        defaultHeaders.add(header);
    }
    
    public void removeDefaultHttpHeader(HttpHeader header) {
        defaultHeaders.remove(header);
    }
    
    public HttpHeader getDefaultHttpHeader(String headerName) {
        for (final HttpHeader header : defaultHeaders) {
            if (header.getKey().equalsIgnoreCase(headerName)) {
                return header;
            }
        }
        
        return null;
    }
    
    public final void addStandardDefaultHeaders() {
        addDefaultHttpHeader(new HttpHeader("User-Agent", "Mozilla/5.2 (X11; Linux x86_64; rv:68.0) Gecko/20100101 Firefox/68.0"));
        addDefaultHttpHeader(new HttpHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
        addDefaultHttpHeader(new HttpHeader("Accept-Language", "ru,en-US;q=0.7,en;q=0.3"));
        addDefaultHttpHeader(new HttpHeader("DNT", "1"));
        addDefaultHttpHeader(new HttpHeader("Connection", "keep-alive"));
        addDefaultHttpHeader(new HttpHeader("Upgrade-Insecure-Requests", "1"));
        addDefaultHttpHeader(new HttpHeader("Pragma", "no-cache"));
        addDefaultHttpHeader(new HttpHeader("Cache-Control", "no-cache"));
    }
    
    public HttpRequest create(String url, String method, HttpHeader[] headers) {
        final HttpRequest req = new HttpRequest(url);
        req.setMethod(method);
        defaultHeaders.stream().forEach((header) -> {
            req.getHeaders().add(header);
        });
        
        if (headers != null) {
            for (final HttpHeader header : headers) {
                req.getHeaders().add(header);
            }
        }
        
        try {
            final URL urlObj = new URL(url);
            final String host = urlObj.getHost();
            req.setHeader(HttpHeader.HOST, host);
        } catch (MalformedURLException ex) {
            HttpClient.logger.error(ex, ex);
        }
        
        return req;
    }
}
