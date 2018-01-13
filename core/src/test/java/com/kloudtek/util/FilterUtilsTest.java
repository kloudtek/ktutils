package com.kloudtek.util;

import org.junit.Test;
import org.testng.Assert;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class FilterUtilsTest {
    public static final String HELLO = "hello";
    public static final String WORLD = "world";
    public static final String CHICKEN = "chicken";
    private List<String> list = Arrays.asList(HELLO, WORLD, CHICKEN);

    @Test
    public void regexFilterExcludeWordsWithO() {
        assertEquals(FilterUtils.regexFilter(list, null, Collections.singletonList(".*o.*")),
                CHICKEN);
    }

    @Test
    public void regexFilterIncludeWordsWithO() {
        assertEquals(FilterUtils.regexFilter(list, Collections.singletonList(".*o.*"), null),
                HELLO, WORLD);
    }

    @Test
    public void regexFilterIncludeHelloOnly() {
        assertEquals(FilterUtils.regexFilter(list, Collections.singletonList(HELLO), null),
                HELLO);
    }

    @Test
    public void regexFilterIncludeWordsWithOExcludeWorld() {
        assertEquals(FilterUtils.regexFilter(list, Collections.singletonList(".*o.*"), Collections.singletonList(".*wo.*")),
                HELLO);
    }


    private void assertEquals(List<String> strings, String... expected) {
        Assert.assertEquals(strings,Arrays.asList(expected));
    }
}