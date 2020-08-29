package com.aeontronix.commons.io;

import com.aeontronix.commons.StringUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.*;

public class IOUtilsTest {
    public static final byte[] TESTDATA = StringUtils.utf8("foo");

    @Test
    public void toByteArrayWithGenerator() throws IOException {
        byte[] data = IOUtils.toByteArray(new IOUtils.DataGenerator() {
            @Override
            public void generateData(OutputStream os) throws IOException {
                os.write(TESTDATA);
            }
        });
        assertArrayEquals(TESTDATA,data);
    }
}
