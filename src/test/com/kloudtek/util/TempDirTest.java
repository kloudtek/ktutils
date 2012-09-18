/*
 * Copyright (c) Kloudtek Ltd 2012.
 */

package com.kloudtek.util;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TempDirTest {
    @Test
    public void test() throws IOException {
        TempDir temp = new TempDir("tmpdirtest");
        final FileWriter fw = new FileWriter(new File(temp, "test.txt"));
        fw.write("TEST");
        fw.close();
        temp.close();
        Assert.assertFalse(temp.exists());
    }
}
