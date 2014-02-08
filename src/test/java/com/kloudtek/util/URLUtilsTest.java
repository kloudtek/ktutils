/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class URLUtilsTest {
    @Test
    public void testConcat1() {
        final String expected = "/fafdsa/fsadfsafdsa";
        assertEquals(URLUtils.concatPaths("/fafdsa/", null, "fsadfsafdsa"), expected);
        assertEquals(URLUtils.concatPaths("/fafdsa/", null, "/fsadfsafdsa"), expected);
        assertEquals(URLUtils.concatPaths("/fafdsa/", null, "fsadfsafdsa"), expected);
        assertEquals(URLUtils.concatPaths("/fafdsa", null, "fsadfsafdsa"), expected);
        assertEquals(URLUtils.concatPaths("/fafdsa/", "fsadfsafdsa"), expected);
        assertEquals(URLUtils.concatPaths("/fafdsa/", "/fsadfsafdsa"), expected);
        assertEquals(URLUtils.concatPaths("/fafdsa/", "fsadfsafdsa"), expected);
        assertEquals(URLUtils.concatPaths("/fafdsa", "fsadfsafdsa"), expected);
    }
}
