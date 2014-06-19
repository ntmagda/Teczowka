package com.example.teczowka2.app;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.ArrayList;

public class HoughWersjaElipsa {

    public Circle H(Bitmap src, int amin, int amax) {

        int aMin = amin;
        int aMax = amax;
        Bitmap bmOut = src;
        int maxVotes=0;
        Circle bestCircle = null;
        int height = src.getHeight();
        int width = src.getWidth();
        // 1. Store all edge pixels in a one dimensional array.
        ArrayList<MyPoint> WHITEPIX = new ArrayList<MyPoint>(); // sprawdzone na bank dobrze
        for (int i = width/5; i < width - (width/5); i++) {
            for (int j = height/5; j < height -height/5; j++) {
                int pixel = src.getPixel(i, j);
                if (Color.red(pixel) == 255) // jezeli jest bialy
                {
                    WHITEPIX.add(new MyPoint(i, j));
                }
            }
        }

        System.out.println("Dodalam biale do tablicy, rozmiar tablicy to: " + WHITEPIX.size());

        int[] acc = new int[aMax+1];
        for (int k = 0; k < WHITEPIX.size(); k++) { // dla kazdego bialego piksela
            for (int p = 0; p < WHITEPIX.size(); p++) {
                MyPoint temp = WHITEPIX.get(k);
                MyPoint candidate = WHITEPIX.get(p);
                final double a = MyPoint.CountDistance(temp, candidate) / 2;
                if (temp.getX() + 2 * aMin <= candidate.getX() && Math.abs(temp.getY() - candidate.getY()) < 1) // tak zeby byly mniej wiecej na tym samym poziomie
                {
                    if (temp != candidate && (int) a < aMax && (int) a > aMin) {
                        for (int i = 0; i < acc.length; i++) {
                            acc[i] = 0;
                        }
                        final int x0 = (candidate.getX() + temp.getX()) / 2; //srodek elipsy
                        final int y0 = (candidate.getY() + temp.getY()) / 2;
                        MyPoint center = new MyPoint(x0, y0);
                        for (int i = 0; i < WHITEPIX.size(); i++) { // count how many points are on the ellipse
                            if (WHITEPIX.get(i) != temp && WHITEPIX.get(i) != candidate) {
                                int d = MyPoint.CountDistance(WHITEPIX.get(i), center);
                                if (d > aMin && d < aMax) {
                                    if (Math.abs(a - d) < 3) {
                                        acc[(int) d]++; // licze dla ilu pikseli udalo sie policzyc takie samo b.*/
                                    }
                                }
                            }
                        }

                        int max = max(acc);
                        maxVotes = max + (int) a;
                        if (acc[max] > maxVotes) {
                            maxVotes = acc[max];
                            bestCircle = new Circle(center, (int) a);
                        }

                    }
                }
            }
        }
            return bestCircle;
        }

    public Bitmap[] IrisBow(Bitmap bmOut, Circle Pupil,Circle Iris)
    {
        int width = bmOut.getWidth();
        int height = bmOut.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height ; y++) {
                if ((MyPoint.CountDistance(new MyPoint(x, y), Iris.getCenter()) > Iris.getR()) ||
                        MyPoint.CountDistance(new MyPoint(x, y), Iris.getCenter())<Pupil.getR()) {
                    bmOut.setPixel(x, y, Color.argb(0, 0, 0, 0));
                }
            }
        }

        Bitmap bmOut1 = Normalization.process(bmOut, Iris.getX0(), Iris.getY0(), Pupil.getR(), Iris.getR());
        Bitmap[] bitmaps = new Bitmap[2];
        bitmaps[0] = bmOut;
        bitmaps[1] = bmOut1;
        return bitmaps;
    }

    public int max(int [] tab){
        int m = 0;
        for(int i =0; i < tab.length; ++i)
            if(tab[i] > tab[m])
                m = i;
        return m;
    }

    public void drawCircle(Circle temp, Bitmap bmOut) {
        int width = bmOut.getWidth();
        int height = bmOut.getHeight();
        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                if (MyPoint.CountDistance(new MyPoint(x, y), temp.getCenter()) == temp.getR()) {
                    bmOut.setPixel(x, y, Color.argb(255, 255, 0, 0));
                }
            }
        }
    }

    public void removeRest(Circle temp, Bitmap bmOut) {
        int width = bmOut.getWidth();
        int height = bmOut.getHeight();
        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                if (MyPoint.CountDistance(new MyPoint(x, y), temp.getCenter()) > temp.getR()) {
                    bmOut.setPixel(x, y, Color.argb(0, 0, 0, 0));
                }
            }
        }
    }
}