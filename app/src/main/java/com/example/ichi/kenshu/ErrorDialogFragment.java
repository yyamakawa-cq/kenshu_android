package com.example.ichi.kenshu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;


public class ErrorDialogFragment extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ArrayList error = getArguments().getStringArrayList("error");
        String messages = TextUtils.join("\n",error);
        AlertDialog.Builder errorDialog = new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.validation_error))
                .setPositiveButton(getString(R.string.button_ok), null)
                .setMessage(messages);
        return errorDialog.create();
    }

    public static ErrorDialogFragment newInstance(List<String> error) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("error", (ArrayList<String>) error);
        ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment();
        errorDialogFragment.setArguments(bundle);
        return errorDialogFragment;
    }
}