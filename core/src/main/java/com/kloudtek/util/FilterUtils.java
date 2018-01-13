package com.kloudtek.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Various utilities for filtering
 */
public class FilterUtils {
    /**
     * Filter a list of string by include and excludes
     *
     * @param list     List of strings
     * @param includes Include regex strings (ignored if null or empty)
     * @param excludes Exclude regex strings
     * @return new filtered string list
     */
    public static List<String> regexFilter(@NotNull List<String> list, @Nullable List<String> includes, @Nullable List<String> excludes) {
        ArrayList<String> filtered = new ArrayList<String>();
        for (String val : list) {
            if (regexMatch(val, true, includes) && !regexMatch(val, false, excludes)) {
                filtered.add(val);
            }
        }
        return filtered;
    }

    public static boolean regexMatch(String val, boolean matchOnNull, List<String> regexList) {
        if( regexList == null ) {
            return matchOnNull;
        }
        for (String include : regexList) {
            if (val.matches(include)) {
                return true;
            }
        }
        return false;
    }
}
