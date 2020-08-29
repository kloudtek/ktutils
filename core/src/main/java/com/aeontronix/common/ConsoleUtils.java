package com.aeontronix.common;

import java.io.Console;
import java.util.Scanner;

public class ConsoleUtils {
    private static final Console console;
    private static final Scanner scanner;

    static {
        console = System.console();
        if (console == null) {
            scanner = new Scanner(System.in);
        } else {
            scanner = null;
        }
    }

    public static String readLine() {
        if (console != null) {
            return console.readLine();
        } else {
            return scanner.nextLine();
        }
    }

    public static String readPassword() {
        if (console != null) {
            return new String(console.readPassword());
        } else {
            return scanner.nextLine();
        }
    }

    public static boolean confirm(String txt) {
        return confirm(txt, null);
    }

    public static boolean confirm(String txt, Boolean defaultValue) {
        for (; ; ) {
            String defValStr = null;
            if (defaultValue != null && defaultValue) {
                defValStr = "yes";
            } else if (defaultValue != null && !defaultValue) {
                defValStr = "no";
            }
            String val = read(txt, defValStr);
            if (val != null) {
                val = val.trim().toLowerCase();
                if (val.equals("yes") || val.equals("y") || val.equals("true")) {
                    return true;
                } else if (val.equals("no") || val.equals("n") || val.equals("false")) {
                    return false;
                } else {
                    System.out.println("Response must be either: yes, no, n, y, true, false");
                }
            }
        }
    }

    public static String read(String txt, String defVal) {
        return read(txt, defVal, false);
    }

    public static String read(String txt, String defVal, boolean password) {
        for (; ; ) {
            System.out.print(txt);
            if (defVal != null) {
                System.out.print(" [" + (password ? "********" : defVal) + "]");
            }
            System.out.print(": ");
            System.out.flush();
            String val = password ? readPassword() : readLine();
            if (val != null) {
                val = val.trim();
                if (!val.isEmpty()) {
                    return val;
                }
                if (defVal != null) {
                    return defVal;
                }
            }
        }
    }
}
