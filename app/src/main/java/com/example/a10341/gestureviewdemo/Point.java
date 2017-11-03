package com.example.a10341.gestureviewdemo;

/**
 * Created by 10341 on 2017/10/14.
 */

public class Point {

    public float r;
    public float cx;
    public float cy;



    public Point(float r, float cx, float cy) {
        this.r = r;
        this.cx = cx;
        this.cy = cy;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Point point = (Point) o;

        if (Float.compare(point.cx, cx) != 0)
            return false;
        return Float.compare(point.cy, cy) == 0;

    }

    @Override
    public String toString() {
        return "Point{" +
                "r=" + r +
                ", cx=" + cx +
                ", cy=" + cy +
                '}';
    }

    @Override
    public int hashCode() {
        int result = (cx != +0.0f ? Float.floatToIntBits(cx) : 0);
        result = 31 * result + (cy != +0.0f ? Float.floatToIntBits(cy) : 0);
        return result;
    }


}
