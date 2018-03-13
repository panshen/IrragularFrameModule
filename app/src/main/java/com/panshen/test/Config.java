package com.panshen.test;

import android.graphics.Point;

public abstract class Config {
    private Point coordinate = new Point();
    private Point leftTop = new Point();
    private Point rightTop = new Point();
    private Point leftBottom = new Point();
    private Point rightBottom = new Point();

    public Point getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Point bottomRight) {
        this.coordinate = bottomRight;
    }

    public Point getLeftTop() {
        return leftTop;
    }

    public void setLeftTop(Point leftTop) {
        this.leftTop = leftTop;
    }

    public Point getRightTop() {
        return rightTop;
    }

    public void setRightTop(Point rightTop) {
        this.rightTop = rightTop;
    }

    public Point getLeftBottom() {
        return leftBottom;
    }

    public void setLeftBottom(Point leftBottom) {
        this.leftBottom = leftBottom;
    }

    public Point getRightBottom() {
        return rightBottom;
    }

    public void setRightBottom(Point rightBottom) {
        this.rightBottom = rightBottom;
    }
}
