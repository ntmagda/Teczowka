package com.example.teczowka2.app;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by Magda on 2014-06-19.
 */
public class GaussianFilter {

    public static Bitmap process(Bitmap src)
    {
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        int height = src.getHeight();
        int width = src.getWidth();
        double[][] gauss_matrix = new double[][]{
                {0.037, 0.039, 0.04, 0.039, 0.037},
                {0.039, 0.042, 0.042, 0.042, 0.039},
                {0.04, 0.042, 0.043, 0.042, 0.04},
                {0.039, 0.042, 0.042, 0.042, 0.039},
                {0.037, 0.039, 0.04, 0.039, 0.037}
        };

        for (int y = 2; y < height-2; y++)
            for (int x = 2; x < width-2; x++)
            {
                double ret = 0;
                for ( int i =x-2, k = 0; k <5; k++ ,i++)
                    for ( int j = y-2, l = 0; l <5; l++,j++)
                    {
                        ret = ret+ Color.red(src.getPixel(i,j))*gauss_matrix[k][l];
                    }
                bmOut.setPixel(x,y,Color.argb(255,(int)ret,(int)ret,(int)ret));

            }

                return bmOut;
    }

}
