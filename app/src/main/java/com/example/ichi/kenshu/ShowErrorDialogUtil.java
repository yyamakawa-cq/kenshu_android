package com.example.ichi.kenshu;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import java.util.List;


public class ShowErrorDialogUtil {

    public void showError(List error, Context context) {
        StringBuilder messages = new StringBuilder();
        for (Object e : error) {
            messages.append("\n" + e);
        }
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.validation_error))
                .setPositiveButton(context.getString(R.string.button_ok), null)
                .setMessage(messages);
        alertDialog.show();
    }
}
