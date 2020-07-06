package ru.ancevt.net.httpclient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * @author ancevt
 */
public final class HttpVariables extends ArrayList<Pare<String, Object>> {

    private static final String CHARSET = "UTF-8";

    public static void main(String[] args) {
        final String test = "a=a&b=2&a=3&c=%D1%80%D1%83%D1%81%D1%81%D0%BA%D0%B8%D0%B9";

        final HttpVariables vars = new HttpVariables(test);
        System.out.println(vars.toString(true));
        System.out.println(vars.toString(false));
    }

    public HttpVariables() {
        super();
    }

    public HttpVariables(String source) {
        this();

        final String[] splitted = source.split("&");
        for (final String line : splitted) {
            try {
                final String[] kv = line.split("=");
                final String key = kv[0];
                final String value = URLDecoder.decode(kv[1], CHARSET);
                add(key, value);
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void add(String key, Object value) {
        add(new Pare(key, value));
    }
    
    public void remove(String key) {
        for(final Pare<String, Object> p : this) {
            if(p.getKey().equals(key)) {
                remove(p);
                return;
            }
        }
    }

    @Override
    public String toString() {
        return toString(true);
    }

    public String toString(boolean urlEncoded) {
        if(isEmpty()) return "";
        
        try {
            final StringBuilder stringBuilder = new StringBuilder();
            for (final Pare<String, Object> p : this) {

                final String key = p.getKey();
                final Object value = p.getValue();

                stringBuilder.append(key).append('=');
                stringBuilder.append(urlEncoded ? URLEncoder.encode("" + value, CHARSET) : value);
                stringBuilder.append(urlEncoded ? '&' : '\n');
            }

            String result = stringBuilder.toString();
            result = result.substring(0, result.length() - 1);

            return result;

        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }

        return null;
    }

}

class Pare<T1, T2> {
    private T1 key;
    private T2 value;
    
    public Pare(T1 key, T2 value) {
        this.key = key;
        this.value = value;
    }

    public T1 getKey() {
        return key;
    }

    public void setKey(T1 key) {
        this.key = key;
    }

    public T2 getValue() {
        return value;
    }

    public void setValue(T2 value) {
        this.value = value;
    }
    
    
} 