package com.aeontronix.commons.io;

import com.aeontronix.commons.StringUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class InMemInputFilterStreamTest {
    @Test
    public void testTransform() throws IOException {
        InMemInputFilterStream is = new InMemInputFilterStream(new ByteArrayInputStream(StringUtils.utf8("foo"))) {
            @Override
            protected byte[] transform(byte[] data) {
                assertEquals("foo", StringUtils.utf8(data));
                return "bar".getBytes();
            }
        };
        assertEquals("bar", IOUtils.toString(is, "UTF-8"));
    }
}
