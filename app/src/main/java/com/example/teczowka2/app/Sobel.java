package com.example.teczowka2.app;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Sobel {

    public static Bitmap process(Bitmap src) {
        // create new bitmap with the same settings as source bitmap
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        // image size
        int height = src.getHeight();
        int width = src.getWidth();
        // scan through every pixel
        for (int y = 3; y < height - 3; y++) {
            for (int x = 3; x < width - 3; x++) {
                // get one pixel
                int p0 = Color.red(src.getPixel(x - 1, y - 1));
                int p1 = Color.red(src.getPixel(x, y - 1));
                int p2 = Color.red(src.getPixel(x + 1, y - 1));
                int p3 = Color.red(src.getPixel(x + 1, y));
                int p4 = Color.red(src.getPixel(x + 1, y + 1));
                int p5 = Color.red(src.getPixel(x, y + 1));
                int p6 = Color.red(src.getPixel(x - 1, y + 1));
                int p7 = Color.red(src.getPixel(x - 1, y));
                int xxg = ((p2 + 2 * p3 + p4) - (p0 + 2 * p7 + p6));
                int yyg = ((p6 + 2 * p5 + p4) - (p0 + 2 * p1 + p2));
                int g = (int) Math.hypot(xxg, yyg);
                if (g > 255) g = 255;
                bmOut.setPixel(x, y, Color.rgb(g, g, g));
            }
        }
        // return final bitmap
        return bmOut;
    }
}