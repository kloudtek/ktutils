package com.aeontronix.commons;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JsonUtilsTests {
    @Test
    public void testConvertObjectToJsonObject() throws Exception {
        Map<String,Object> map = (Map<String, Object>) JsonUtils.toJsonObject(new TestObj1());
        HashMap<String, Object> expected = new HashMap<>();
        expected.put("text", "text");
        expected.put("nb", 0);
        expected.put("varNull", null);
        expected.put("optVar", "foo");
        expected.put("optVarNull", null);
        expected.put("obj2null", null);
        expected.put("strarr", Arrays.asList("one", "two"));
        expected.put("binary", StringUtils.base64Encode(TestObj1.BINARYDATA));
//        expected.put("stream", StringUtils.base64Encode(TestObj1.BINARYDATA));
        HashMap<String, Object> expectedSubMap = new HashMap<>();
        expectedSubMap.put("var1", false);
        expectedSubMap.put("var2", "ccc");
        expected.put("obj2NonNull", expectedSubMap);
        HashMap<String, Object> expectedSubMap2 = new HashMap<>();
        expectedSubMap2.put("v1", "agh");
        expectedSubMap2.put("v2", "ogh");
        expected.put("map", expectedSubMap2);
        Assert.assertEquals(expected, map);
    }
}
