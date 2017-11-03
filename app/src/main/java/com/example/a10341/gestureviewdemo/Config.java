package com.example.a10341.gestureviewdemo;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by jasoncai on 2017/11/1.
 */

public class Config {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int select_color;
    private int defCircleColor = Color.GRAY;
    private float interCircleRadius;
    private float OuterCircleRadius;
    private int circle_count;

    public Paint getPaint(int type) {
        switch (type) {
            case 0://初始化界面是的九宫格小圆点
                paint.reset();
                paint.setColor(getDefCircleColor());
                break;
            case 1://选中的大圆
                paint.reset();
                paint.setColor(getSelect_color());
                paint.setStrokeWidth((float) 2.0);
                paint.setStyle(Paint.Style.STROKE);
                break;
            case 2://选中的小圆
                paint.reset();
                paint.setColor(getSelect_color());
                paint.setStrokeWidth((float) 10.0);
                break;
            case 3://直线
                paint.reset();
                paint.setColor(getSelect_color());
            default:
                break;
        }
        return paint;
    }

    public int getSelect_color() {
        return select_color;
    }

    public void setSelect_color(int select_color) {
        this.select_color = select_color;
    }


    public int getDefCircleColor() {
        return defCircleColor;
    }

    public void setDefCircleColor(int defCircleColor) {
        this.defCircleColor = defCircleColor;
    }

    public float getInterCircleRadius() {
        return interCircleRadius;
    }

    public void setInterCircleRadius(float interCircleRadius) {
        this.interCircleRadius = interCircleRadius;
    }

    public float getOuterCircleRadius() {
        return OuterCircleRadius;
    }

    public void setOuterCircleRadius(float outerCircleRadius) {
        this.OuterCircleRadius = outerCircleRadius;
    }

    public int getCircle_count() {
        return circle_count;
    }

    public void setCircle_count(int circle_count) {
        this.circle_count = circle_count;
    }


}
