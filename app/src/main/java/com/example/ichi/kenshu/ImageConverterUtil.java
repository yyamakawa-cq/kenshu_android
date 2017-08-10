package com.example.ichi.kenshu;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class ImageConverterUtil {

    public static String convertToString(ImageView imageView) {
        Bitmap bitmapImage = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

        Bitmap resizedImage = Bitmap.createScaledBitmap(bitmapImage, 100, 100, false);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        resizedImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, android.util.Base64.DEFAULT);
    }
}
