package ru.ancevt.net.httpclient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import ru.ancevt.util.string.StringUtil;

/**
 * @author ancevt
 */
public class LineReader {
    
    public static void main(String[] args) throws IOException {
        byte[] b1 = "hello ".getBytes();
        byte[] b2 = "world akdsna;jd saw;lkdaw;ldnaw;d\r\nnawjdna;kwdawkj;dnawj;kdnawkj;dnawdjawndja;wjdnaj;wdnnd as;d a;sdjasjdad ".getBytes();
        byte[] b3 = "foo bar".getBytes();

        byte[] s1 = Integer.toHexString(b1.length).getBytes();
        byte[] s2 = Integer.toHexString(b2.length).getBytes();
        byte[] s3 = Integer.toHexString(b3.length).getBytes();
        
        byte[] rn = new byte[] { 13, 10 };
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(s1);
        baos.write(rn);
        baos.write(b1);
        baos.write(rn);
        
        baos.write(s2);
        baos.write(rn);
        baos.write(b2);
        baos.write(rn);
        
        baos.write(s3);
        baos.write(rn);
        baos.write(b3);
        baos.write(rn);
        
        baos.write(Integer.toHexString(0).getBytes());
        
        baos.write(rn);
        
        //System.out.println(new String(baos.toByteArray()));
        
        ByteArrayInputStream in = new ByteArrayInputStream(baos.toByteArray());
        
//        LineReader lineReader = new LineReader(bais);
//        System.out.println(lineReader.readLine()); // 5
//        System.out.println(lineReader.readLine()); // hello
//        System.out.println(lineReader.readLine()); // 5
//        System.out.println(lineReader.readLine()); // world
//        System.out.println(lineReader.readLine()); // 7
//        System.out.println(lineReader.readLine()); // foo bar
//        System.out.println(lineReader.readLine()); // 0
        
        LineReader lineReader = new LineReader(in);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        int chunkSize;
        while ((chunkSize = lineReader.readIntLine(16)) != 0) {
            final byte[] bytes = new byte[chunkSize];
            in.read(bytes);
            
            out.write(bytes);
            
            in.skip(2);
        }

        final byte[] result = out.toByteArray();
        baos.close();
        
    }

    private final InputStream inputStream;

    LineReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public int readIntLine(int radix) {
        final String line = readLine();
        if(line == null || line.equals(StringUtil.EMPTY)) return 0;
        return Integer.valueOf(line, radix);
    }

    public String readLine() {
        try {
            final byte[] bytes;
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                int b;
                //while ((b = inputStream.read()) != -1) {
                while (true) {
                    b = inputStream.read();
                    if(b == -1) break;
                    
                    if (b == 13 && inputStream.read() == 10) {
                        break;
                    }
                    baos.write(b);
                    
                }   bytes = baos.toByteArray();
            }

            String result = new String(bytes);

            return result;

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
