package com.aeontronix.common;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TestObj1 {
    public static final String BINARYDATA_STR = "binarydata";
    public static final byte[] BINARYDATA = BINARYDATA_STR.getBytes(StandardCharsets.UTF_8);
    private String text = "text";
    private String varNull;
    private int nb;
    private TestObj2 obj2NonNull = new TestObj2(this);
    private TestObj2 obj2null;
    private Optional<String> optVar = Optional.of("foo");
    private Optional<String> optVarNull = Optional.ofNullable(null);
    private Map<String,String> map = new HashMap<>();
    private String[] strarr = new String[] {"one","two"};
    private byte[] binary = BINARYDATA;

    public TestObj1() {
        map.put("v1","agh");
        map.put("v2","ogh");
    }

    public String getText() {
        return text;
    }

    public int getNb() {
        return nb;
    }

    public TestObj2 getObj2NonNull() {
        return obj2NonNull;
    }

    public TestObj2 getObj2null() {
        return obj2null;
    }

    public Optional<String> getOptVar() {
        return optVar;
    }

    public Optional<String> getOptVarNull() {
        return optVarNull;
    }

    public String getVarNull() {
        return varNull;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public String[] getStrarr() {
        return strarr;
    }

    public byte[] getBinary() {
        return binary;
    }
}
