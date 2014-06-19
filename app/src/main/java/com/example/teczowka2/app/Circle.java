package com.example.teczowka2.app;

/**
 * Created by Magda on 2014-06-17.
 */
public  class Circle{

    int x0;
    int y0;
    int r;
    Circle(int x, int y, int r)
    {
        x0 =x;
        y0 = y;
        this.r = r;
    }
    Circle(MyPoint center, int r)
    {
        x0 = center.getX();
        y0 = center.getY();
        this.r = r;
    }
    Circle()
    {
        x0 =0;
        y0 = 0;
        r = 0;
    }
    int getX0(){ return x0;}
    int getY0(){ return y0;}
    MyPoint getCenter(){ return new MyPoint(x0,y0);}
    int getR(){ return r;}
}
