/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.commons;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProcessUtils {
    public static ProcessExecutionResult exec(String... command) throws IOException, ProcessExecutionFailedException {
        StreamReader streamReader = new StreamReader();
        streamReader.start();
        Process process = new ProcessBuilder(command)
                .redirectErrorStream(true).start();
        streamReader.setInputStream(process.getInputStream());
        try {
            process.waitFor();
        } catch (InterruptedException var4) {
        }
        streamReader.close();

        if (process.exitValue() != 0) {
            throw new ProcessExecutionFailedException(process, streamReader.getStdOut());
        } else {
            return new ProcessExecutionResult(process, streamReader.getStdOut());
        }
    }

    static class StreamReader extends Thread {
        private InputStream inputStream;
        private volatile ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        private boolean running;

        StreamReader() {
        }

        public void setInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
            synchronized (this) {
                running = true;
                notify();
            }
        }

        public String getStdOut() {
            return this.stdout.toString();
        }

        public void run() {
            try {
                synchronized (this) {
                    wait();
                }
                byte[] buf = new byte[1024];
                for (int i = this.inputStream.read(buf); this.inputStream.available() > 0 || i != -1; i = this.inputStream.read(buf)) {
                    this.stdout.write(buf, 0, i);
                }

            } catch (Exception var6) {
            } finally {
                synchronized (this) {
                    running = false;
                    notifyAll();
                }
            }
        }

        public void close() {
            try {
                synchronized (this) {
                    while (running) {
                        wait();
                    }
                }
                inputStream.close();
                this.stdout.flush();
            } catch (Exception e) {
            } finally {
            }
        }
    }
}
