package com.kloudtek.util;

import java.util.Optional;

public class TestObj2 {
    private boolean var1 = false;
    private String var2 = "ccc";
    private TestObj1 parent;

    public TestObj2(TestObj1 parent) {
        this.parent = parent;
    }

    public TestObj1 getParent() {
        return parent;
    }

    public boolean isVar1() {
        return var1;
    }

    public String getVar2() {
        return var2;
    }
}
