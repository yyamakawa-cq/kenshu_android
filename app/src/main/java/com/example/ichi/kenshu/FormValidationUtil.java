package com.example.ichi.kenshu;

import android.content.res.Resources;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

public class FormValidationUtil {

    public static List<String> validateAccountValues(String email, String password) {
        List<String> errorText = new ArrayList<>();
        if (TextUtils.isEmpty(email)) {
            errorText.add(Resources.getSystem().getString(R.string.form_email) + Resources.getSystem().getString(R.string.validation_isEmpty));
        }
        if (TextUtils.isEmpty(password)) {
            errorText.add(Resources.getSystem().getString(R.string.form_password) + Resources.getSystem().getString(R.string.validation_isEmpty));
        }
        return errorText;
    }

    public static List<String> validateAccountValues(String email, String password, String confirmPass) {
        List<String> errorText = new ArrayList<>();
        if (TextUtils.isEmpty(email)) {
            errorText.add(Resources.getSystem().getString(R.string.form_email) + Resources.getSystem().getString(R.string.validation_isEmpty));
        }
        if (TextUtils.isEmpty(password)) {
            errorText.add(Resources.getSystem().getString(R.string.form_password) + Resources.getSystem().getString(R.string.validation_isEmpty));
        } else if (!TextUtils.equals(password, confirmPass)) {
            errorText.add(Resources.getSystem().getString(R.string.form_confirm_password) + Resources.getSystem().getString(R.string.validation_notEqual));
        }
        return errorText;
    }

    public static List<String> validateBookValues(String name, String price, String date){
        List<String> errorText = new ArrayList<>();
        if (TextUtils.isEmpty(name)) {
            errorText.add(Resources.getSystem().getString(R.string.form_name) + Resources.getSystem().getString(R.string.validation_isEmpty));
        }
        if (TextUtils.isEmpty(price)) {
            errorText.add(Resources.getSystem().getString(R.string.form_price) + Resources.getSystem().getString(R.string.validation_isEmpty));
        }
        if (TextUtils.isEmpty(date)) {
            errorText.add(Resources.getSystem().getString(R.string.form_purchase_date) + Resources.getSystem().getString(R.string.validation_isEmpty));
        }
        return errorText;
    }
}
