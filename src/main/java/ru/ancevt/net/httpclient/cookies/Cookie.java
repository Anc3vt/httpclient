package ru.ancevt.net.httpclient.cookies;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ancevt
 */
public class Cookie extends LinkedHashMap<String, String> {

    private String name;
    private String value;

    public Cookie(String line) {

        final String[] pares = line.split(";");
        for (final String pare : pares) {
            final String[] kv = pare.split("=");
            final String key = kv[0].trim();
            final String value = kv.length > 1 ? kv[1] : "true";

            put(key, value);

            if (name == null) {
                this.name = key;
                this.value = value;
            }

        }
    }

    public final String getAttribute(String attr) {
        for (Map.Entry<String, String> entry : entrySet()) {
            if(attr.trim().equalsIgnoreCase(entry.getKey().trim())) return entry.getValue();
        }
        return null;
    }
    
    public final boolean isExpired() {
        try {
            final String expiresString = getAttribute("expires");
            if(expiresString == null) return false;
            
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
            
            Date date = sdf.parse(expiresString);
            Date now = new Date();
            
            Calendar cal = new GregorianCalendar();
            cal.setTime(date);
            cal.add(Calendar.HOUR, 3);
            
            date = cal.getTime();
            
            if(now.after(date)) {
                return true;
            }
            
            
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        
        return false;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name + "=" + value;
    }

    public String toStringFull() {
        final StringBuilder stringBuilder = new StringBuilder();

        for (Map.Entry<String, String> entry : entrySet()) {
            final String key = entry.getKey();
            final String value = entry.getValue();

            stringBuilder.append(key).append('=');
            stringBuilder.append(value);
            stringBuilder.append(';');
        }

        String result = stringBuilder.toString();
        result = result.substring(0, result.length() - 1);

        return result;
    }

}
