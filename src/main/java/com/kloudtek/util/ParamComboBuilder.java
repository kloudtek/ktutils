/*
 * Copyright (c) 2013 KloudTek Ltd
 */

package com.kloudtek.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ParamComboBuilder {
    public List<Object[]> params = new ArrayList<Object[]>();

    public ParamComboBuilder() {
    }

    public ParamComboBuilder(Object obj) {
        add(obj);
    }

    @SuppressWarnings({"unchecked"})
    public ParamComboBuilder add(Object p) {
        if (p instanceof Object[]) {
            params.add((Object[]) p);
        } else if (p instanceof Collection<?>) {
            params.add(((Collection) p).toArray(new Object[((Collection) p).size()]));
        } else {
            throw new IllegalArgumentException("Invalid param type: " + p.getClass());
        }
        return this;
    }

    public ParamComboBuilder addAll(Object... p) {
        params.add(p);
        return this;
    }

    /**
     * Add all the parameters specified in the array, and a null object
     *
     * @param p
     * @return
     */
    public ParamComboBuilder addAllAndNull(Object... p) {
        Object[] copy = Arrays.copyOf(p, p.length + 1);
        copy[p.length] = null;
        params.add(copy);
        return this;
    }

    public ParamComboBuilder addBoolean() {
        params.add(new Object[]{true, false});
        return this;
    }

    public Object[][] build() {
        final int plen = params.size();
        Object[][] r = new Object[getTotalCombinations()][plen];
        int size = 0;
        int processed = 0;
        for (Object[] param : params) {
            final int len = param.length;
            if (size == 0) {
                for (int f = 0; f < len; f++) {
                    r[f][0] = param[f];
                }
                size += len;
            } else {
                int newSize = size * param.length;
                int oc = 0;
                int oy = 0;
                for (int y = 0; y < newSize; y++) {
                    if (y >= size) {
                        assert r[y][0] == null && r[y][processed - 1] == null;
                        System.arraycopy(r[oy], 0, r[y], 0, processed);
                    }
                    r[y][processed] = param[oc];
                    oy++;
                    if (oy == size) {
                        oy = 0;
                        oc++;
                    }
                }
                size = newSize;
            }
            processed++;
        }
        return r;
    }

    private int getTotalCombinations() {
        int total = 0;
        for (Object[] p : params) {
            if (total == 0) {
                total += p.length;
            } else {
                total *= p.length;
            }
        }
        return total;
    }
}
