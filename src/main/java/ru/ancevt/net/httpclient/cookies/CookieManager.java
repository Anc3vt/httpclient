package ru.ancevt.net.httpclient.cookies;

import java.util.ArrayList;
import java.util.List;
import ru.ancevt.net.httpclient.HttpHeader;

/**
 * @author ancevt
 */
public class CookieManager {

    private List<Cookie> cookies;

    public CookieManager() {
        cookies = new ArrayList<>();
    }

    public void addCookie(Cookie cookie) {
        Cookie old = getCookie(cookie.getName());

        if (old == null) {
            cookies.add(cookie);
        } else {
            cookies.remove(old);
            cookies.add(cookie);
        }
        
        
    }

    public void removeCookie(Cookie cookie) {
        cookies.remove(cookie);
    }

    public Cookie getCookie(String name) {
        for (final Cookie c : cookies) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    public void fromHeaders(List<HttpHeader> headers) {
        for (final HttpHeader h : headers) {
            if (h.getKey().toLowerCase().equals("set-cookie")) {
                addCookie(new Cookie(h.getValue()));
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        cookies.stream().forEach((c) -> {
            stringBuilder.append(c.toString()).append(';');
        });
        String result = stringBuilder.toString();
        result = result.substring(0, result.length());
        return result;
    }

    public String list() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (final Cookie c : cookies) {
            stringBuilder.append(c.toStringFull()).append('\n');
        }
        return stringBuilder.toString();
    }

    public final void validate() {
        for (final Cookie c : cookies) {
            if (c.isExpired()) {
                cookies.remove(c);
                validate();
                break;
            }
        }
    }

}
