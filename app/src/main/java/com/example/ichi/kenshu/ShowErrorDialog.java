package com.example.ichi.kenshu;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;


public class ShowErrorDialog {

    public ShowErrorDialog() {}

    public void showError(ArrayList error, Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(context.getString(R.string.validation_error));
        alertDialog.setPositiveButton(context.getString(R.string.button_ok), null);
        StringBuilder messages = new StringBuilder();
        for (int i = 0; i < error.size(); i++) {
            messages.append("\n");
            String text = (String) error.get(i);
            messages.append(text);
        }
        alertDialog.setMessage(messages);
        alertDialog.show();
    }
}
