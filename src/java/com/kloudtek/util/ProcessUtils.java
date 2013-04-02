/*
 * Copyright (c) Kloudtek Ltd 2013.
 */

package com.kloudtek.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProcessUtils {
    public static ProcessExecutionResult exec(String... command) throws IOException, ProcessExecutionFailedException {
        final StreamReader streamReader = new StreamReader();
        streamReader.start();
        final Process process = new ProcessBuilder(command).redirectErrorStream(true).start();
        streamReader.setInputStream(process.getInputStream());
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            //
        }
        if (process.exitValue() != 0) {
            throw new ProcessExecutionFailedException(process, streamReader.getStdOut());
        }
        return new ProcessExecutionResult(process, streamReader.getStdOut());
    }

    static class StreamReader extends Thread {
        private InputStream inputStream;
        private volatile ByteArrayOutputStream stdout = new ByteArrayOutputStream();

        public void setInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
            synchronized (this) {
                notify();
            }
        }

        public String getStdOut() {
            return stdout.toString();
        }

        @Override
        public void run() {
            try {
                synchronized (this) {
                    wait();
                    byte[] buf = new byte[1024];
                    for (int i = inputStream.read(buf); i != -1; i = inputStream.read(buf)) {
                        stdout.write(buf, 0, i);
                    }
                }
            } catch (Exception e) {
                //
            }
        }
    }
}
