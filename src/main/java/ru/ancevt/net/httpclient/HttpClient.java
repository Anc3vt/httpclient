package ru.ancevt.net.httpclient;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.apache.log4j.Logger;
import ru.ancevt.util.fs.SimpleFileWriter;
import ru.ancevt.util.string.StringUtil;

/**
 *
 * @author ancevt
 */
public final class HttpClient implements Closeable {

    public static void main(String[] args) throws IOException, TimeoutException {
        try (HttpClient client = new HttpClient() //connection.connect(new URL("http://192.168.10.201:8080/test"));
            ) {
            //HttpRequest req = new HttpRequest("http://localhost:8080/stream?count=10&chunkSize=500");
            HttpRequest req = new HttpRequest("http://localhost:8080/test1?count=10&chunkSize=500");
            //HttpRequest req = new HttpRequest("https://google.com");

            req.setHeader(HttpHeader.HOST, "localhost:8080");
            req.getHeaders().add(new HttpHeader("Connection", "close"));
            //req.getHeaders().add(new HttpHeader("Accept-Encoding", "gzip"));

            //req.setTimeout(2000);
            client.setChunkFunction((chunkNum, data) -> {
                System.out.println("???? chunk num: " + chunkNum);
                System.out.println("???? data: " + StringUtil.cut(new String(data), 100));
            });

            client.connect(req);
            System.out.println("Status: " + client.getStatus());

            System.out.println("> " + client.readUtf8());

            //client.readBytes();
        }
    }
    
    public static final String END_LINE = "\r\n";
    
    public static final byte[] END_LINE_BYTES = END_LINE.getBytes();

    public static final Logger logger = Logger.getLogger(HttpClient.class);

    private static final int BODY_LIMIT = 500;
    private static final String PROTOCOL_HTTP = "http";
    private static final String PROTOCOL_HTTPS = "https";

    private final int DEFAULT_TIMEOUT = 120000;

    private static int idCounter;

    private URL url;
    private Socket socket;

    private int status;
    private String statusString;

    private final List<HttpHeader> headers;
    private InputStream inputStream;

    private String httpVersion;
    private long startTime;
    private HttpRequest request;

    private static int simultaneouslyLoading;

    private final int id;

    private ChunkFunction chunkFunction;

    public HttpClient() {
        id = ++idCounter;
        headers = new ArrayList<>();

        if (logger.isTraceEnabled()) {
            logger.trace("[" + id + "] HttpClient created");
        }

    }

    public ChunkFunction getChunkFunction() {
        return chunkFunction;
    }

    public void setChunkFunction(ChunkFunction chunkFunction) {
        this.chunkFunction = chunkFunction;
    }

    public int getId() {
        return id;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public URL getUrl() {
        return url;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusText() {
        return statusString;
    }

    public final void connect(HttpRequest request) throws IOException, TimeoutException {
        this.request = request;
        this.url = new URL(request.getUrl());

        final String protocol = url.getProtocol().toLowerCase();
        final String host = url.getHost();
        int port = url.getPort();

        if (port == -1) {
            if (protocol.equals(PROTOCOL_HTTP)) {
                port = 80;
            }
            if (protocol.equals(PROTOCOL_HTTPS)) {
                port = 443;
            }
        }

        switch (protocol) {
            case PROTOCOL_HTTP:
                socket = new Socket(host, port);
                break;

            case PROTOCOL_HTTPS:
                final SSLSocketFactory factory
                    = (SSLSocketFactory) SSLSocketFactory.getDefault();
                final SSLSocket sslSocket
                    = (SSLSocket) factory.createSocket(host, port);
                sslSocket.startHandshake();
                this.socket = sslSocket;
        }

        String path = url.getPath().isEmpty() ? "/" : url.getPath();
        if (url.getQuery() != null) {
            path += "?" + url.getQuery();
        }

        if (logger.isInfoEnabled()) {
            logger.info("[" + id + "] request " + request.getUrl() + ", port " + port);
        }

        final OutputStream outputStream = socket.getOutputStream();

        //PrintWriter pw = new PrintWriter(outputStream);
        final String top = request.getMethod() + " " + path + " " + request.getHttpVersion();
        outputStream.write(top.getBytes());
        outputStream.write(END_LINE_BYTES);
        outputStream.flush();

        //pw.println(top);
        final List<HttpHeader> requestHeaders = request.getHeaders();

        if (request.getBody() != null) {
            requestHeaders.add(new HttpHeader(HttpHeader.CONTENT_LENGTH, "" + request.getBody().length));
        }
        
        requestHeaders.stream().forEach((h) -> {
            try {
                outputStream.write(h.toString().getBytes());
                outputStream.write(END_LINE_BYTES);
                outputStream.flush();
            } catch (IOException ex) {
                HttpClient.logger.error(ex, ex);
            }
        });

        if (logger.isTraceEnabled()) {
            final StringBuilder sb = new StringBuilder("\n");
            sb.append("[").append(id).append("] ? ").append(top).append('\n');
            requestHeaders.stream().forEach((c) -> {
                sb.append("[").append(id).append("] ? ").append(c.toString()).append('\n');
            });

            logger.trace(sb.toString());
        }

        outputStream.write(END_LINE_BYTES);
        outputStream.flush();

        if (request.getBody() != null) {

            outputStream.write(request.getBody());
            outputStream.flush();

            if (logger.isDebugEnabled()) {
                logger.trace("[" + id + "] body: " + limitString(new String(request.getBody()), BODY_LIMIT));
            }
        }

        inputStream = socket.getInputStream();

        final LineReader lineReader = new LineReader(inputStream);

        String inputLine;
        int line = 0;

        StringBuilder sb = null;
        if (logger.isTraceEnabled()) {
            sb = new StringBuilder("\n");
        }

        while ((inputLine = lineReader.readLine()) != null) {
            if (logger.isTraceEnabled()) {
                sb.append("[").append(id).append("] ").append(inputLine).append('\n');
            }

            if (inputLine.length() == 0) {
                break;
            } else {

                if (line == 0) {
                    parseStatus(inputLine);
                    if (logger.isInfoEnabled()) {
                        logger.info("[" + id + "] status " + status + " " + statusString);
                    }
                } else {
                    final HttpHeader header = new HttpHeader(inputLine);
                    headers.add(header);
                }

            }
            line++;
        }

        if (logger.isTraceEnabled()) {
            logger.trace(sb.toString());
        }

        simultaneouslyLoading++;

        if (logger.isTraceEnabled()) {
            logger.trace("sim. loading: " + simultaneouslyLoading);
        }
    }

    private static String limitString(String string, int limit) {
        if (string.length() > limit) {
            string = string.substring(0, limit) + "...";
        }
        return string;
    }

    private byte[] readBytesChunked() throws IOException, TimeoutException {
        final LineReader lineReader = new LineReader(inputStream);

        final byte[] result;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            int chunkSize;
            int chunkNum = 0;
            boolean timeoutOccured = false;
            final int timeout = request.getTimeout();
            while ((chunkSize = lineReader.readIntLine(16)) != 0) {

                final ByteArrayOutputStream chunkOutputBaos = chunkFunction != null ? new ByteArrayOutputStream() : null;

                for (int i = 0; i < chunkSize; i++) {
                    int b = inputStream.read();

                    if (chunkOutputBaos != null) {
                        chunkOutputBaos.write(b);
                    }

                    baos.write(b);
                }

                inputStream.skip(2);

                if (timeout != 0 && System.currentTimeMillis() - startTime > timeout) {
                    timeoutOccured = true;
                    SimpleFileWriter.println(new File("http-timeout.txt"), url + " " + timeout);
                    break;
                }

                if (chunkFunction != null) {
                    chunkFunction.chunk(chunkNum++, chunkOutputBaos.toByteArray());
                    chunkOutputBaos.close();
                }
            }
            if (timeoutOccured) {
                throw new TimeoutException("timeout " + timeout);
            }

            result = baos.toByteArray();
        }

        if (logger.isTraceEnabled()) {
            logger.trace(limitString(new String(result), 100));
        }

        return result;
    }

    public final byte[] readBytes() throws TimeoutException {
        startTime = System.currentTimeMillis();

        try {
            if (isChunked()) {
                return readBytesChunked();
            }

            if (getContentLength() == 0) {
                return new byte[]{};
            }

            final long contentLength = getContentLength();

            boolean timeoutOccured = false;
            final int timeout = request.getTimeout();

            final byte[] result;
            try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[8192];
                int len;
                int total = 0;

                //System.out.println("\n" + url);
                //SimpleFileWriter.print(new File("httpclient-watch.txt"), "\n\n" + url + "\n");
                while ((len = inputStream.read(buffer)) != -1) {

                    os.write(buffer, 0, len);
                    total += len;

                    //final String string = total + "/" + contentLength + " av:" + inputStream.available() + " " + url;
                    //SimpleFileWriter.println(new File("httpclient-watch.txt"), string);
                    //System.out.println(string);
                    if (total >= contentLength) {
                        break;
                    }

                    if (timeout != 0 && System.currentTimeMillis() - startTime > timeout) {
                        timeoutOccured = true;
                        SimpleFileWriter.println(new File("http-timeout.txt"), url + " " + timeout);
                        break;
                    }
                }

                if (timeoutOccured) {
                    throw new TimeoutException("timeout " + timeout);
                }

                result = os.toByteArray();
            }

            if (logger.isTraceEnabled()) {
                logger.trace(limitString(new String(result), BODY_LIMIT));
            }

            return result;

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public final String readUtf8() throws TimeoutException {
        return new String(readBytes());
    }

    public final long getContentLength() {
        final String lengthString = getHeaderValue(HttpHeader.CONTENT_LENGTH);
        if (lengthString == null) {
            return 0;
        }
        return Long.valueOf(lengthString);
    }

    public final boolean containsHeader(String headerName) {
        return headers.stream().anyMatch((h) -> (h.getKey().toLowerCase().equals(headerName.toLowerCase())));
    }

    private boolean isChunked() {
        final String value = getHeaderValue(HttpHeader.TRANSFER_ENCODING);
        return value != null && value.equalsIgnoreCase("chunked");
    }

    public final String getHeaderValue(String headerName) {
        for (final HttpHeader h : headers) {
            if (h.getKey().toLowerCase().equals(headerName.toLowerCase())) {
                return h.getValue();
            }
        }

        return null;
    }

    public final List<HttpHeader> getHeaders() {
        return headers;
    }

    private void parseStatus(String statusLine) {
        final String[] splitted = statusLine.split(" ", 3);
        httpVersion = splitted[0];
        status = Integer.valueOf(splitted[1]);
        statusString = splitted[2];
    }

    @Override
    public void close() throws IOException {
        simultaneouslyLoading--;
        socket.close();

        if (logger.isDebugEnabled()) {
            logger.debug("[" + id + "] closed, sim. loading " + simultaneouslyLoading);
        }
    }

}
