package com.example.ichi.kenshu;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

public class ConverterImageUtil {

    public static String convertToString(ImageView imageView) {
        Bitmap bitmapImage = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

        Bitmap resizedImage = Bitmap.createScaledBitmap(bitmapImage, 100, 100, false);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        resizedImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        String base64 = null;
        try {
            String utf8 = new String(bytes,"UTF-8");
            byte[] byteUtf8 = utf8.getBytes();
            base64 = Base64.encodeToString(byteUtf8, android.util.Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return base64;
    }
}
