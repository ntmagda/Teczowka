package com.example.teczowka2.app;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by Magda on 2014-06-04.
 */
public class Binarization {

    public Bitmap Binarize(Bitmap src, String IrisOrPupil) {
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        int height = src.getHeight();
        int width = src.getWidth();

        double P=0;
        // wyznaczanie progu binaryzacji
        for ( int i = 0 ; i < width-1 ;i++) {
            for (int j = 0; j < height - 1; j++) {
                int pixel = src.getPixel(i,j);
                P = P + Color.red(pixel);
            }
        }
        P = P/(width*height);
        double P1 = P/4.5; // dla zrenicy
        double P2 = P/1.5; // dla teczowki

        // scan through every pixel
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                int pixel = src.getPixel(x,y);
                int A = Color.alpha(pixel);
                if(IrisOrPupil == "Iris") {
                    if (Color.red(pixel) > P2) {
                        bmOut.setPixel(x, y, Color.argb(A, 255, 255, 255));
                    } else {
                        bmOut.setPixel(x, y, Color.argb(A, 0, 0, 0));
                    }
                }
                else if(IrisOrPupil== "Pupil")
                {
                    if (Color.red(pixel) > P1) {
                        bmOut.setPixel(x, y, Color.argb(A, 255, 255, 255));
                    } else {
                        bmOut.setPixel(x, y, Color.argb(A, 0, 0, 0));
                    }
                }
                else{
                    System.out.println("Podano zły parametr");
                }
            }

        return bmOut;

    }
}