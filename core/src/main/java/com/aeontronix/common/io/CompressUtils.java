package com.aeontronix.common.io;

import com.aeontronix.common.UnexpectedException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by yannick on 29/4/16.
 */
public class CompressUtils {
    public static byte[] gzip(byte[] data) {
        try {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            GZIPOutputStream os = new GZIPOutputStream(buf);
            os.write(data);
            os.close();
            return buf.toByteArray();
        } catch (IOException e) {
            throw new UnexpectedException(e);
        }
    }

    public static byte[] gunzip(byte[] gzippedData) throws IOException {
        GZIPInputStream is = new GZIPInputStream(new ByteArrayInputStream(gzippedData));
        byte[] data = IOUtils.toByteArray(is);
        is.close();
        return data;
    }
}
