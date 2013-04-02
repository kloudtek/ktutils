/*
 * Copyright (c) Kloudtek Ltd 2013.
 */

package com.kloudtek.util;

/**
 * Thrown when a launched process's return code isn't zero.
 */
public class ProcessExecutionFailedException extends Exception {
    private Process process;
    private String stdout;

    public ProcessExecutionFailedException(Process process, String stdout) {
        super(stdout);
        this.process = process;
        this.stdout = stdout;
    }

    public Process getProcess() {
        return process;
    }

    public String getStdout() {
        return stdout;
    }
}
