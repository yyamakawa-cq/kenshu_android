package com.example.ichi.kenshu;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.TextView;

public class PurchaseDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datepickerDialog = new DatePickerDialog(getActivity(), this, year, month, dayOfMonth);
        return datepickerDialog;
    }

    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        TextView textView = getActivity().findViewById(R.id.textView_purchaseDate);
        textView.setText(String.valueOf(year) + "/" + String.valueOf(monthOfYear) + "/" + String.valueOf(dayOfMonth));
    }
}
