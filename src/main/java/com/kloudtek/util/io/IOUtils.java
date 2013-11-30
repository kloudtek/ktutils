/*
 * Copyright (c) 2013 KloudTek Ltd
 */

package com.kloudtek.util.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class IOUtils {
    public static void writeString(DataOutput out, String str) throws IOException {
        out.writeBoolean(str != null);
        if (str != null) {
            out.writeUTF(str);
        }
    }

    public static String readString(DataInput in) throws IOException {
        if (in.readBoolean()) {
            return in.readUTF();
        } else {
            return null;
        }
    }

    public static void writeData(DataOutput out, byte[] data) throws IOException {
        int len = data != null ? data.length : -1;
        out.writeInt(len);
        if (len > -1) {
            out.write(data);
        }
    }

    public static byte[] readData(DataInput in) throws IOException {
        int len = in.readInt();
        if (len > 0) {
            byte[] data = new byte[len];
            in.readFully(data);
            return data;
        } else if (len == 0) {
            return new byte[0];
        } else {
            return null;
        }
    }
}
