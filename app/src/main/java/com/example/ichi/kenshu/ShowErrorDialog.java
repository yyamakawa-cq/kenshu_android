package com.example.ichi.kenshu;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;


public class ShowErrorDialog {

    public ShowErrorDialog() {}

    public void showError(ArrayList error, Context context) {
        StringBuilder messages = new StringBuilder();
        for (Object e : error) {
            messages.append("\n");
            messages.append(e);
        }
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.validation_error))
                .setPositiveButton(context.getString(R.string.button_ok), null)
                .setMessage(messages);
        alertDialog.show();
    }
}
