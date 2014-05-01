/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Various utility functions for android operation system
 */
public class AndroidUtils {
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
     */
    public static AlertDialog.Builder createErrorDialogBuilder(Context context, Exception e) {
        return new AlertDialog.Builder(context)
                .setTitle("Unexpected error")
                .setMessage("An unexpected error has occured: " + e.getMessage())
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }
}
