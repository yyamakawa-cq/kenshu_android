package com.example.ichi.kenshu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;


public class ErrorDialogFragment extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        List<String> errorArray;
        String error;
        String messages = null;
        if (getArguments().get("errorList") != null) {
            errorArray = getArguments().getStringArrayList("errorList");
            messages = TextUtils.join("\n", errorArray);
        } else if (getArguments().get("error") != null) {
            error = getArguments().getString("error");
            messages = error;
        }
        AlertDialog.Builder errorDialog = new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.title_error))
                .setPositiveButton(getString(R.string.button_ok), null)
                .setMessage(messages);
        return errorDialog.create();
    }

    public static ErrorDialogFragment newInstance(List<String> error) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("errorList", (ArrayList<String>) error);
        ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment();
        errorDialogFragment.setArguments(bundle);
        return errorDialogFragment;
    }

    public static ErrorDialogFragment newInstance(int errorCode, Context context) {
        Bundle bundle = new Bundle();
        bundle.putString("error", context.getString(R.string.api_error) + ":"+ String.valueOf(errorCode));
        ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment();
        errorDialogFragment.setArguments(bundle);
        return errorDialogFragment;
    }
}