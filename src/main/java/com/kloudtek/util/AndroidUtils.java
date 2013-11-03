/*
 * Copyright (c) 2013 KloudTek Ltd
 */

package com.kloudtek.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by yannick on 03/11/13.
 */
public class AndroidUtils {
    public static void showErrorDialog(Context context, Exception e) {
        createErrorDialogBuilder(context, e).create().show();
    }

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
