package com.example.ichi.kenshu;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import java.util.List;


public class ErrorDialogUtil {

    public static void showError(List<String> error, Context context) {
        String messages = TextUtils.join("\n",error);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.validation_error))
                .setPositiveButton(context.getString(R.string.button_ok), null)
                .setMessage(messages);
        alertDialog.show();
    }
}