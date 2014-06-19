package com.example.teczowka2.app;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by Magda on 2014-06-04.
 */
public class GreyScale {

    public Bitmap Grey(Bitmap src) {
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        int height = src.getHeight();
        int width = src.getWidth();

        // scan through every pixel
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {

                int pixel = src.getPixel(x,y);
                int A = Color.alpha(pixel);
                int R = Color.red(pixel);
                int G = Color.green(pixel);
                int B = Color.blue(pixel);
                int Z = (R+G+B)/3;
                bmOut.setPixel(x,y,Color.argb(A,Z,Z,Z));
             }

        return bmOut;

    }
}