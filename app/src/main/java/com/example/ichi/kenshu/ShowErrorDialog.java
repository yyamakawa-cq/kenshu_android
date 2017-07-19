package com.example.ichi.kenshu;

import android.app.Activity;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;


public class ShowErrorDialog {

    public ShowErrorDialog() {}

    public void showError(ArrayList error, Activity activity) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setTitle("エラー");
        alertDialog.setPositiveButton("OK", null);
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
