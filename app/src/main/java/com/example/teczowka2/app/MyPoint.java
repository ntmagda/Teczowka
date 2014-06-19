package com.example.teczowka2.app;

        import java.util.Arrays;

class MyPoint{
    private Integer x;
    private Integer y;

    MyPoint(int x,int y)
    {
        this.x = x;
        this.y = y;
    }
    MyPoint(MyPoint a)
    {
        this.x = a.getX();
        this.y = a.getY();
    }
    static int CountDistance( MyPoint a, MyPoint b)
    {
        return (int)(Math.hypot(b.getX()-a.getX(), b.getY()-a.getY()));

    }

    @Override
    public String toString()
    {
        return "X = " + this.x + "Y = " +this.y;
    }
    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{x,y});
    }

    @Override
    public boolean equals(Object other)
    {
        if(this == other) return true;
        if(other == null) return false;
        if(getClass() != other.getClass()) return false;

        MyPoint test = (MyPoint)other;
        if(this.x == test.getX() && this.y == test.getY())
            return true;
        return false;
    }


    int getX(){return this.x; }
    int getY(){return this.y; }
}

