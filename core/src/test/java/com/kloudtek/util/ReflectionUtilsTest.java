package com.kloudtek.util;

import org.junit.Assert;
import org.junit.Test;
import org.omg.CORBA.OBJ_ADAPTER;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ReflectionUtilsTest {
    @Test
    public void testConvertObjectToMap() throws InvocationTargetException, IllegalAccessException {
        Map<String, Object> map = ReflectionUtils.objectToMap(new TestObj1());
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
        Assert.assertEquals(expected,map);
    }
}