package com.example.teczowka2.app;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by Magda on 2014-06-19.
 */
public class Contrast {

    public static Bitmap process(Bitmap src, double wsp) {
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        for (int x = 0;x < src.getWidth(); x++)
        {
            for (int y = 0; y < src.getHeight(); y++) {
                int pixel = src.getPixel(x,y);
                double a = Color.alpha(pixel);
                double r = Color.red(pixel);
                double g = Color.green(pixel);
                double b = Color.blue(pixel);

                if ((wsp * (r - 255 / 2) + 255 / 2) < 0) {
                    r = 0;
                }
                if ((wsp * (g - 255 / 2) + 255 / 2) < 0) {
                    g = 0;
                }
                if ((wsp * (b - 255 / 2) + 255 / 2) < 0) {
                    b = 0;
                }
                if ((wsp * (r - 255 / 2) + 255 / 2) < 255 && (wsp * (r - 255 / 2) + 255 / 2) > 0) {
                    r = wsp * (r - 255 / 2) + 255 / 2;
                }
                if ((wsp * (g - 255 / 2) + 255 / 2) < 255 && (wsp * (g - 255 / 2) + 255 / 2) > 0) {
                    g = wsp * (g - 255 / 2) + 255 / 2;
                }
                if ((wsp * (b - 255 / 2) + 255 / 2) < 255 && (wsp * (b - 255 / 2) + 255 / 2) > 0) {
                    b = wsp * (b - 255 / 2) + 255 / 2;
                }
                if ((wsp * (r - 255 / 2) + 255 / 2) > 255) {
                    r = 255;
                }
                if ((wsp * (g - 255 / 2) + 255 / 2) > 255) {
                    g = 255;
                }
                if ((wsp * (b - 255 / 2) + 255 / 2) > 255) {
                    b = 255;
                }

                bmOut.setPixel(x,y,Color.argb((int)a,(int) r,(int) g,(int)b));
            }
        }
        return bmOut;
    }
}