package com.panshen.test;

/**
 * Created by Administrator on 2018/1/25.
 */

public class PointSimple {
    int x = 0;
    int y = 0;

    public PointSimple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "PointSimple{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
