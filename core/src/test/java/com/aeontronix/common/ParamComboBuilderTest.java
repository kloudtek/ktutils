/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.common;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

public class ParamComboBuilderTest {
    @Test
    public void testToArray() throws Exception {
        final ParamComboBuilder paramComboBuilder = new ParamComboBuilder(TestEnum.values())
                .add(new Object[]{"111", "222", "333"}).add(new Object[]{"xxx", "yyy", "zzz"})
                .add(Arrays.asList("OO", "UU", "@@")).addBoolean()
                .addAll(55, 88, 11).addBoolean().add(TestEnum.values());
        final Object[][] r = paramComboBuilder.build();
        System.out.println(Arrays.deepToString(r));
        for (int i = 0; i < r.length; i++) {
            for (int f = 0; f < r.length; f++) {
                if (f != i) {
                    Assert.assertFalse(Arrays.equals(r[i], r[f]), "Two arrays are the same: " + i + " and " + f + " = " + Arrays.toString(r[i]));
                }
            }
        }
    }

    public enum TestEnum {
        BLA, BOF, GAG
    }
}
