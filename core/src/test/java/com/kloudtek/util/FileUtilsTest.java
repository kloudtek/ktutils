package com.kloudtek.util;

import org.jetbrains.annotations.NotNull;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

public class FileUtilsTest {
    @Test
    public void testListFileNamesRecursiveFiles() throws Exception {
        Set<String> list = createTmpDirAndListFilenames(true, true, false);
        assertEquals(asSet("f1.txt","subdir/f2.txt"),list);
    }

    @Test
    public void testListFileNamesRecursiveFilesAndDirs() throws Exception {
        Set<String> list = createTmpDirAndListFilenames(true, true, true);
        assertEquals(asSet("f1.txt","subdir","subdir/f2.txt"),list);
    }

    @Test
    public void testListFileNamesRecursiveDirs() throws Exception {
        Set<String> list = createTmpDirAndListFilenames(true, false, true);
        assertEquals(asSet("subdir"),list);
    }

    private Set<String> createTmpDirAndListFilenames(boolean recursive, boolean includeFiles, boolean includeDirs) throws IOException {
        TempDir tmpDir = TempDir.createMavenTmpDir("core");
        try {
            File subDir = new File(tmpDir, "subdir");
            if( ! subDir.mkdir() ) {
                fail("Failed to create subDir");
            }
            FileUtils.write(new File(tmpDir,"f1.txt"),"foo");
            FileUtils.write(new File(subDir,"f2.txt"),"bar");
            return FileUtils.listAllFilesNames(tmpDir, recursive, includeFiles, includeDirs);
        } finally {
            tmpDir.close();
        }
    }


    @NotNull
    private static HashSet<String> asSet(String... filenames) {
        return new HashSet<String>(Arrays.asList(filenames));
    }
}