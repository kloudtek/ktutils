/*
 * Copyright (c) 2015 Kloudtek Ltd
 */

package com.kloudtek.util;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.StrictMode;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Various utility functions for android operation system
 */
public class AndroidUtils {
    private static final Logger logger = Logger.getLogger(AndroidUtils.class.getName());
    /**
     * Apply any appropriate fixes and workarounds (like for example android RNG fix)
     */
    public static void applyFixes() {
        AndroidPRNFix.apply();
    }

    /**
     * Create and display an error dialog for an unexpected exception
     *
     * @param context Context
     * @param title   Title
     * @param message Message to display
     */
    public static void showErrorDialog(Context context, String title, String message) {
        createDialogBuilder(context, title, message).create().show();
    }

    /**
     * Create and display an error dialog for an unexpected exception
     *
     * @param context Context
     * @param e       Exception to display
     */
    public static void showErrorDialog(Context context, Exception e) {
        createErrorDialogBuilder(context, e).create().show();
    }

    /**
     * Create an error dialog for an unexpected exception
     *
     * @param context Context
     * @param e       Exception to display
     * @return Dialog builder
     */
    public static AlertDialog.Builder createErrorDialogBuilder(Context context, Exception e) {
        return createDialogBuilder(context, "Unexpected error", "An unexpected error has occurred: " + e.getMessage());
    }

    /**
     * Create an error dialog for an unexpected exception
     *
     * @param context Context
     * @param title   dialog title
     * @param message Message to display
     * @return Dialog builder
     */
    public static AlertDialog.Builder createDialogBuilder(Context context, String title, String message) {
        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void enableDevelopmentMode() {
        enableDevelopmentMode(false);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void enableDevelopmentMode(boolean harsh) {
        try {
            StrictMode.ThreadPolicy.Builder threadPolicyBuilder = new StrictMode.ThreadPolicy.Builder()
                    .detectAll();
            StrictMode.VmPolicy.Builder vmPolicyBuilder = new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects();
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                vmPolicyBuilder.detectLeakedClosableObjects();
            }
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                vmPolicyBuilder.detectLeakedRegistrationObjects();
            }
            threadPolicyBuilder.penaltyLog();
            vmPolicyBuilder.penaltyLog();
            if (harsh) {
                threadPolicyBuilder.penaltyDeath();
                vmPolicyBuilder.penaltyDeath();
            }
            StrictMode.setThreadPolicy(threadPolicyBuilder.build());
            StrictMode.setVmPolicy(vmPolicyBuilder.build());
        } catch (Exception e) {
            logger.log(Level.WARNING, "Unable to enable development mode: " + e.getMessage(), e);
        }
    }
}
