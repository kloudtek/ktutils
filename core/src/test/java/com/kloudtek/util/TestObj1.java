package com.kloudtek.util;

import java.util.Optional;

public class TestObj1 {
    private String text = "text";
    private String varNull;
    private int nb;
    private TestObj2 obj2NonNull = new TestObj2(this);
    private TestObj2 obj2null;
    private Optional<String> optVar = Optional.of("foo");
    private Optional<String> optVarNull = Optional.ofNullable(null);

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
}
