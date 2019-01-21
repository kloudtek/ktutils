package com.kloudtek.util;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ReflectionUtilsTest {
    @Test
    public void testConvertObjectToMap() throws InvocationTargetException, IllegalAccessException {
        Map<String, Object> map = ReflectionUtils.objectToJsonMap(new TestObj1());
        HashMap<String, Object> expected = new HashMap<>();
        expected.put("text","text");
        expected.put("nb", 0);
        expected.put("varNull",null);
        expected.put("optVar","foo");
        expected.put("optVarNull",null);
        expected.put("obj2null",null);
        HashMap<String, Object> expectedSubMap = new HashMap<>();
        expectedSubMap.put("var1",false);
        expectedSubMap.put("var2","ccc");
        expected.put("obj2NonNull",expectedSubMap);
        HashMap<String, Object> expectedSubMap2 = new HashMap<>();
        expectedSubMap2.put("v1","agh");
        expectedSubMap2.put("v2","ogh");
        expected.put("map",expectedSubMap2);
        Assert.assertEquals(expected,map);
    }
}