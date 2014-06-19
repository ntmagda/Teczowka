/*package com.example.teczowka2.app;


import android.graphics.Bitmap;
import android.graphics.Color;

import org.jblas.DoubleMatrix;


class Grad{
    double kier;
    double wart;

    Grad(double kier, double wart)
    {
        this.kier = kier;
        this.wart = wart;
    }

    Grad()
    {
        kier = 0 ;
        wart = 0;
    }
}
public class Canny  {

    public Bitmap Sobel(Bitmap imageIn, Grad[][] tablica)
    {
        Bitmap image= Bitmap.createBitmap(imageIn.getWidth(), imageIn.getHeight(), imageIn.getConfig());
        Bitmap bmOut= Bitmap.createBitmap(imageIn.getWidth(), imageIn.getHeight(), imageIn.getConfig());
        int height = imageIn.getHeight();
        int width = imageIn.getWidth();

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                int pixel = imageIn.getPixel(x,y);
                int A = Color.alpha(pixel);
                int R = Color.red(pixel);
                int G = Color.green(pixel);
                int B = Color.blue(pixel);
                image.setPixel(x, y, Color.argb(A,R, G, B));
            }

        // DoubleMatrix tablica = new DoubleMatrix(imageIn.getWidth(),imageIn.getHeight());
        for (int x = 3; x < width-3; x++) {
            for (int y = 3; y < height-3; y++) {

                int A = Color.alpha(image.getPixel(x,y));
                int p0=Color.red(image.getPixel(x-1,y-1));
                int p1=Color.red(image.getPixel(x,y-1));
                int p2=Color.red(image.getPixel(x+1,y-1));
                int p3=Color.red(image.getPixel(x+1,y));
                int p4=Color.red(image.getPixel(x+1,y+1));
                int p5=Color.red(image.getPixel(x,y+1));
                int p6=Color.red(image.getPixel(x-1,y+1));
                int p7=Color.red(image.getPixel(x-1,y));
                int xxg = ((p2+2*p3+p4)-(p0+2*p7+p6));
                int yyg = ((p6+2*p5+p4)-(p0+2*p1+p2));


                int g = (int) Math.hypot(xxg,yyg);

                if(g > 255) g = 255;
                else if(g<0) g = 0;
                bmOut.setPixel(x, y, Color.argb(A, g, g, g));

                if(xxg==0)
                {
                    tablica[x][y] = new Grad(Math.toRadians(90),Math.hypot(xxg,yyg));
                }
                else
                {
                    tablica[x][y] = new Grad(Math.atan2(yyg,xxg),Math.hypot(xxg,yyg));
                }
            }
        }
        return bmOut;
    }



    Grad[][] Gradienty(Grad[][] tablica, Bitmap imageIn)
    {
        for (int x = 3; x < imageIn.getWidth()-3; x++) {
            for (int y = 3; y < imageIn.getHeight()-3; y++) {
                double a = (Math.PI)/8;
                if(tablica[x][y].wart>20){

                    // kolor żółty dla pikseli o kierunku [0,22.5] i dla pikseli od [157.5,180]
                    if((tablica[x][y].kier>=-8*a && tablica[x][y].kier<-7*a )||(tablica[x][y].kier>=-a && tablica[x][y].kier<a ||(tablica[x][y].kier>7*a && tablica[x][y].kier<=8*a )))
                    {
                        //imageOut.setIntColor(x,y,255, 255, 0);
                        tablica[x][y].kier=0;
                    }
                    else if((tablica[x][y].kier>=-7*a && tablica[x][y].kier<-5*a )|| (tablica[x][y].kier>=a && tablica[x][y].kier<3*a))
                    {
                        // imageOut.setIntColor(x,y,0, 255, 0);
                        tablica[x][y].kier=45;
                    }
                    else if((tablica[x][y].kier>=-5*a && tablica[x][y].kier<-3*a )||(tablica[x][y].kier>=3*a && tablica[x][y].kier<5*a))
                    {
                        //  imageOut.setIntColor(x,y,0, 0, 255);
                        tablica[x][y].kier=90;
                    }
                    else if((tablica[x][y].kier>=-3*a && tablica[x][y].kier<-a )||(tablica[x][y].kier>=5*a && tablica[x][y].kier<7*a))
                    {
                        //   imageOut.setIntColor(x,y,255, 0, 0);
                        tablica[x][y].kier=135;
                    }
                }

            }
        }
        return tablica;
    }

    DoubleMatrix kasowanie(Bitmap imageIn, Grad[][] tablica, DoubleMatrix kasowanie)
    {

        Bitmap bmOut= Bitmap.createBitmap(imageIn.getWidth(), imageIn.getHeight(), imageIn.getConfig());
        int height = imageIn.getHeight();
        int width = imageIn.getWidth();

        for (int x = 3; x < imageIn.getWidth()-3; x++) {
            for (int y = 3; y < imageIn.getHeight()-3; y++) {

                //kolor zołty 0 stopni
                if(tablica[x][y].kier==0)
                {
                    if(tablica[x][y].wart!=Math.max(tablica[x][y].wart,( Math.max(tablica[x-1][y].wart,tablica[x+1][y].wart))))
                    {
                        kasowanie.put(x,y,1);
                    }
                    else
                    {
                        kasowanie.put(x,y,0);
                    }
                }
                // kolor zielony 45 stopni
                if(tablica[x][y].kier==135)
                {
                    if(tablica[x][y].wart!=Math.max(tablica[x][y].wart,(Math.max(tablica[x+1][y-1].wart, tablica[x-1][y+1].wart))))
                    {
                        kasowanie.put(x,y,1);
                    }
                    else
                    {
                        kasowanie.put(x,y,0);
                    }
                }

                // kolor niebieski 90 stopni
                if(tablica[x][y].kier==90)
                {
                    if(tablica[x][y].wart!=Math.max(tablica[x][y].wart,(Math.max(tablica[x][y-1].wart,tablica[x][y+1].wart))))
                    {
                        kasowanie.put(x,y,1);
                    }

                    else
                    {
                        kasowanie.put(x,y,0);
                    }
                }

                if(tablica[x][y].kier==45)
                {
                    if(tablica[x][y].wart!=Math.max(tablica[x][y].wart,(Math.max(tablica[x-1][y-1].wart,tablica[x+1][y+1].wart))))
                    {
                        kasowanie.put(x,y,1);
                    }
                    else
                    {
                        kasowanie.put(x,y,0);
                    }
                }
            }
        }
        return kasowanie;
    }



    public Bitmap EdgeTracking(Bitmap imageIn)
    {

        Bitmap bmOut= Bitmap.createBitmap(imageIn.getWidth(), imageIn.getHeight(), imageIn.getConfig());
        int height = imageIn.getHeight();
        int width = imageIn.getWidth();
        for (int x = 2; x < imageIn.getWidth()-2; x++) {
        for (int y = 2; y < imageIn.getHeight()-2; y++) {

            int A = Color.alpha(imageIn.getPixel(x,y));
            if(Color.red(imageIn.getPixel(x-1,y))!=0 && // rozne niz czarny
                    Color.red(imageIn.getPixel(x-1,y))!=255) // rozne niz bialy
            {

                check1:
                for( int i = x-1, k = 0; k <3;k++,i++)
                {
                    for ( int j = y-1,l=0; l <3; l++,j++)
                    {

                        if(Color.red(imageIn.getPixel(i,j))==255)
                        {
                            bmOut.setPixel(x, y, Color.argb(A, 255, 255, 255));
                            break check1;
                        }

                        else
                        {
                            for( int ii = x-2,m=0; m <5;m++,ii++)
                            {
                                for ( int jj = y-2,n=0; n <5; n++,jj++)
                                {
                                    if(Color.red(imageIn.getPixel(i,j))==255)// czy znajdzie bialy?

                                    {
                                        bmOut.setPixel(x, y, Color.argb(A, 255, 255, 255));;
                                        break check1;
                                    }
                                    else
                                    {
                                        bmOut.setPixel(x, y, Color.argb(A, 0, 0, 0));
                                    }
                                }


                            }
                        }
                    }
                }
            }
        }
    }


        return bmOut;
    }
    public Bitmap process(Bitmap imageIn) {

        Bitmap bmOut= Bitmap.createBitmap(imageIn.getWidth(), imageIn.getHeight(), imageIn.getConfig());
        int height = imageIn.getHeight();
        int width = imageIn.getWidth();

        Grad[][] tablica = new Grad[imageIn.getWidth()][imageIn.getHeight()];
        for ( int i = 0 ; i < imageIn.getWidth(); i++)
            for ( int j = 0; j < imageIn.getHeight();j++)
            {
                tablica[i][j]= new Grad();
            }

        Sobel(imageIn,tablica); // zaimplementowac
        Gradienty(tablica,imageIn);
        DoubleMatrix kasowanie = new DoubleMatrix(imageIn.getWidth(), imageIn.getHeight());
        kasowanie(imageIn,tablica, kasowanie);


        for (int x = 2; x < imageIn.getWidth()-2; x++) {
            for (int y = 2; y < imageIn.getHeight()-2; y++) {
                int A = Color.alpha(imageIn.getPixel(x,y));
                if(!(kasowanie.get(x,y)==0))
                {
                    bmOut.setPixel(x, y, Color.argb(A, 0, 0, 0));
                }
                else
                {
                    if(tablica[x][y].wart<20)
                    {
                        bmOut.setPixel(x, y, Color.argb(A, 0, 0, 0));
                    }
                    else if(tablica[x][y].wart>45)
                    {
                        bmOut.setPixel(x, y, Color.argb(A, 255, 255, 255));
                    }

                }
            }
        }

        return EdgeTracking(bmOut);
    }
}
*/