package ru.ancevt.net.httpclient;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import ru.ancevt.util.fs.SimpleFileReader;
import ru.ancevt.util.string.StringUtil;

/**
 *
 * @author ancevt
 */
public class MultipartBodyMaker {

    private final String boundary;
    private final List<BodyPart> parts;

    public MultipartBodyMaker() {
        boundary = "Boundary" + StringUtil.generateRandomString(20);
        parts = new ArrayList<>();
    }

    public String getBoundary() {
        return boundary;
    }

    public final void add(String name, String contentType, String data) {
        final BodyPart bodyPart = new BodyPart(boundary, name);
        bodyPart.setContentType(contentType);
        bodyPart.setTextData(data);
        parts.add(bodyPart);
    }

    public final void add(String name, String contentType, byte[] data) {
        final BodyPart bodyPart = new BodyPart(boundary, name);
        bodyPart.setContentType(contentType);
        bodyPart.setBytes(data);
        parts.add(bodyPart);
    }

    public final void add(String name, String contentType, String fileName, File file) {
        try {
            final BodyPart bodyPart = new BodyPart(boundary, name);
            bodyPart.setContentType(contentType);
            bodyPart.setFileName(fileName);
            final byte[] b = SimpleFileReader.readAllBytes(file);
            bodyPart.setBytes(b);
            parts.add(bodyPart);
        } catch (IOException ex) {
            HttpClient.logger.error(ex, ex);
        }
    }

    public final void add(File file) {
        try {
            final BodyPart bodyPart = new BodyPart(boundary, "file");

            final byte[] b = SimpleFileReader.readAllBytes(file);

            InputStream is = new BufferedInputStream(new ByteArrayInputStream(b));
            String mimeType = URLConnection.guessContentTypeFromStream(is);
            bodyPart.setContentType(mimeType);
            bodyPart.setFileName(file.getName());
            bodyPart.setBytes(b);
            parts.add(bodyPart);
        } catch (IOException ex) {
            HttpClient.logger.error(ex, ex);
        }
    }

    public final byte[] getResult() {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        parts.stream().forEach((part) -> {
            try {
                baos.write(part.getBodyPart());
            } catch (IOException ex) {
                HttpClient.logger.error(ex, ex);
            }
        });

        final String b = "--" + boundary + "--";

        try {
            baos.write(b.getBytes());
        } catch (IOException ex) {
        }
        return baos.toByteArray();
    }

    @Override
    public String toString() {
        return new String(getResult());
    }

}

class BodyPart {

    private final String boundary;
    private final String name;
    private String contentType;
    private String fileName;
    private String textData;
    private byte[] bytes;

    public BodyPart(String boundary, String name) {
        this.boundary = boundary;
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getTextData() {
        return textData;
    }

    public void setTextData(String textData) {
        this.textData = textData;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] getBodyPart() {
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();

            final String b = "--" + boundary;

            baos.write(b.getBytes());
            baos.write("\r\n".getBytes());

            final StringBuilder s = new StringBuilder();
            s.append("Content-Disposition: form-data; name=\"").append(name).append("\"");
            if (fileName != null) {
                s.append(';').append(" filename=\"").append(fileName).append("\"");
            }
            s.append("\r\n");
            if (contentType != null) {
                s.append("Content-Type: ").append(contentType).append("\r\n");
            }
            
            s.append("\r\n");

            baos.write(s.toString().getBytes());

            if (textData != null) {
                baos.write(textData.getBytes());
            } else if (bytes != null) {
                baos.write(bytes);
            }

            baos.write("\r\n".getBytes());

            return baos.toByteArray();

        } catch (IOException ex) {
            HttpClient.logger.error(ex, ex);
        }
        return null;
    }

}
