package ru.ancevt.net.httpclient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author ancevt
 */
public class GZIPUtil {

    public static byte[] compress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream obj = new ByteArrayOutputStream();

        try (final GZIPOutputStream gzip = new GZIPOutputStream(obj)) {
            gzip.write(str.getBytes("UTF-8"));
        }

        return obj.toByteArray();
    }

    public static String decompress(byte[] str) throws IOException {
        if (str == null) {
            return null;
        }

        ByteArrayInputStream in = new ByteArrayInputStream(str);
        GZIPInputStream gin = new GZIPInputStream(in);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int b;
        while ((b = gin.read()) != -1) {
            baos.write(b);
        }

        final byte[] bytes = baos.toByteArray();
        return new String(bytes);
    }

}
