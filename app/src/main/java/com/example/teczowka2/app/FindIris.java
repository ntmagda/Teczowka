package com.example.teczowka2.app;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by Magda on 2014-06-05.
 */
public class FindIris {
    static int Irisx;
    static int Irisy;
    class Point
    {
        private int x;
        private int y;
        Point(int x,int y)
        {
            this.x = x;
            this.y = y;
        }
        Point()
        {
            x = 0;
            y = 0;
        }
    }

    class MColor
    {
        int R;
        int G;
        int B;
        MColor()
        {
            R = 0 ;
            G = 0;
            B =0 ;
        }
        MColor(int r, int b, int g)
        {
            R = r;
            G = g;
            B = b;
        }
        int [] getRGB()
        {
            int tablica[] = new int[3];
            tablica[0]=R;
            tablica[1] =G;
            tablica[2]=B;
            return tablica;
        }
    }
    public Bitmap Find(Bitmap src) {
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        int height = src.getHeight();
        int width = src.getWidth();
        // scan through every pixel
        int sumy = 255 * width;
        int miny = 0;
        for (int y = 0; y < height - 5; y++) {
            int tempy = 0;
            for (int x = 0; x < width - 5; x++) {
                int pixel = src.getPixel(x, y);
                tempy += Color.red(pixel);
            }
            if (tempy < sumy) {
                sumy = tempy;
                miny = y;
            }
        }
        int sumx = 255 * height;
        int minx = 0;
        for (int x = 0; x < width - 5; x++) {
            int temp = 0;
            for (int y = 0; y < height - 5; y++) {
                int pixel = src.getPixel(x, y);
                temp += Color.red(pixel);
            }
            if (temp < sumx) {
                sumx = temp;
                minx = x;
            }
        }

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                int pixel = src.getPixel(x, y);
                int A = Color.alpha(pixel);
                int R = Color.red(pixel);
                int G = Color.green(pixel);
                int B = Color.blue(pixel);
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }

        Irisx = minx;
        Irisy = miny;
        for (int y = miny - 4, l = 0; l < 9; y++, l++) {
            for (int x = minx - 4, k = 0; k < 9; x++, k++) {
                bmOut.setPixel(x, y, Color.argb(255, 255, 0, 0));
            }
        }

        MColor black = new MColor(0, 0, 0);
        MColor red = new MColor(255, 0, 0);
        //floodFill(bmOut, new Point(Irisx, Irisy), red, black);

        return bmOut;
    }


    /*
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void floodFill(Bitmap image, Point node, MColor targetColor, MColor replacementColor) {
        int height = image.getHeight();
        int width = image.getWidth();

        int[] target = targetColor.getRGB();
        int[] replacement = replacementColor.getRGB();
        if (target != replacement) {
            Deque<Point> queue = new LinkedList<Point>();
            do {
                int x = node.x;
                int y = node.y;
                int pixel = image.getPixel(x-1,y);
                while (x > 5 && Color.red(pixel) == target[0]
                        && Color.green(pixel) == target[1]
                        && Color.blue(pixel) == target[2]) {
                    x--;
                }
                boolean spanUp = false;
                boolean spanDown = false;
                int pixel2 = image.getPixel(x,y);
                while (x < width-5 && Color.red(pixel2) == target[0]
                        && Color.green(pixel2) == target[1]
                        && Color.blue(pixel2) == target[2]) {
                    image.setPixel(x,y,Color.argb(255,replacement[0],replacement[1],replacement[2]));
                    int pixel3 = image.getPixel(x,y-1);
                    if (!spanUp && y > 5 && Color.red(pixel3) == target[0]
                            &&Color.green(pixel3) == target[1]
                            &&Color.blue(pixel3) == target[2])

                    {
                        queue.add(new Point(x, y - 1));
                        spanUp = true;
                    } else if (spanUp && y > 5 && Color.red(pixel3) != target[0]
                            && Color.green(pixel3)!= target[1]
                            && Color.blue(pixel3) != target[2]) {
                        spanUp = false;
                    }
                    int pixel4 = image.getPixel(x,y+1);
                    if (!spanDown && y < height - 5 && Color.red(pixel4) == target[0]
                            &&Color.green(pixel4) == target[1]
                            && Color.blue(pixel4) == target[2]){
                        queue.add(new Point(x, y + 1));
                        spanDown = true;
                    } else if (spanDown && y < height - 5 && Color.red(pixel4) != target[0]
                            && Color.green(pixel4) != target[1]
                            && Color.blue(pixel4)!= target[2]){
                        spanDown = false;
                    }
                    x++;
                }
            } while ((node = queue.pollFirst()) != null);
        }
    }
*/
    /*
    public Bitmap floodFill(Bitmap src)
    {
        Point position= new Point(Irisx,Irisy);
        System.out.println("Irisx" + Irisx + "Irisy" + Irisy);
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        int height = src.getHeight();
        int width = src.getWidth();

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                int pixel = src.getPixel(x,y);
                int A = Color.alpha(pixel);
                int R = Color.red(pixel);
                int G = Color.green(pixel);
                int B = Color.blue(pixel);
                bmOut.setPixel(x, y, Color.argb(A,R, G, B));
            }

        Queue<Point> queue = new LinkedList<Point>();
        queue.add(position);
        while(queue.size()>0)
        {
            Point new_position = new Point(queue.poll());
            int pixel = src.getPixel(new_position.x,new_position.y);
            if(Color.red(pixel)==0)
            {
                bmOut.setPixel(new_position.x,new_position.y,Color.RED);
                queue.add(new Point(new_position.x,new_position.y+1));
                queue.add(new Point(new_position.x,new_position.y-1));
                queue.add(new Point(new_position.x+1,new_position.y));
                queue.add(new Point(new_position.x-1,new_position.y));
            }
        }
        return bmOut;

    }*/



}
