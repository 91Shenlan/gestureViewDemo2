package com.example.a10341.gestureviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by 10341 on 2017/10/14.
 */

public class MyGestureView extends View {

    private Config config = new Config();
    private GestureLineStore gestureLineStore = new GestureLineStore();
    private Point selectCircle;
    private Point lineStartPoint;
    private Point lineStopPoint;
    private Point secondToLastSelectCircle;
    private Point secondToLastSelectLinePoint;
    private Point fingerPoint;
    private Point tempPoint;
    private int defCircleColor = Color.GRAY;
    private float lastX;
    private float lastY;
    private float initX;
    private float initY;
    private float lastSelectPointX;
    private float lastSelectPointY;
    private float outCircleRadius = -1;
    private float interCircleRadius = -1;
    private int select_color = Color.RED;
    private int circle_count = 9;
    private Point newPoint;
    private Runnable resetRunnable;
    private Drawer drawer;
    private int row;
    private int column;
    private int rowBlock;
    private int columnBlock;
    private int circleX;
    private int circleY;


    public MyGestureView(Context context) {
        super(context);
        initView(context, null);
    }

    public MyGestureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);

    }


    public MyGestureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public MyGestureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray tr = context.obtainStyledAttributes(attrs, R.styleable.MyGestureView);
            config.setSelect_color(tr.getColor(R.styleable.MyGestureView_select_color, select_color));
            config.setCircle_count(tr.getInteger(R.styleable.MyGestureView_circle_conut, circle_count));
            config.setDefCircleColor(tr.getColor(R.styleable.MyGestureView_defCirCleColor, defCircleColor));
            config.setOuterCircleRadius(tr.getFloat(R.styleable.MyGestureView_outCircleRadius, outCircleRadius));
            config.setInterCircleRadius(tr.getFloat(R.styleable.MyGestureView_interCircleRadius, interCircleRadius));
        }

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        lastX = (int) event.getX();
        lastY = (int) event.getY();
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                if (resetRunnable != null) {
                    removeCallbacks(resetRunnable);
                }
                initX = lastX;
                initY = lastY;
                gestureLineStore.resetSelectCircle();
                gestureLineStore.resetSelectLinePoint();
                if (isInCircle(initX, initY)) {
                    gestureLineStore.addSelectPoint(newPoint);
                    gestureLineStore.addSelectLinePoint(newPoint);
                    tempPoint = new Point(outCircleRadius, lastSelectPointX + 1, lastSelectPointY);
                    gestureLineStore.addSelectLinePoint(tempPoint);
                    //添加一个临时点是为了站一个位，后面set的时候不回因为index位置元素为空而报错
                    // Log.i(TAG, "Down事件时selectLinePointCount的值为: "+LinePointCount);
                } else {
                    Point fingerNoOnCirclePoint = new Point(outCircleRadius, initX + 10, initY);
                    gestureLineStore.addSelectLinePoint(fingerNoOnCirclePoint);
                }
                break;

            case MotionEvent.ACTION_MOVE:
                //判断手势点是否在圆内，以及是否有中间点
                if (isInCircle(lastX, lastY)) {
                    gestureLineStore.addSelectPoint(newPoint);
                    if (gestureLineStore.getSelectCircleCount() >= 2) {
                        //                        Log.i(TAG, "getSelectPointCoount()="+gestureLineStore.getSelectPointCoount());
                        secondToLastSelectCircle = gestureLineStore.getSelectCircle(gestureLineStore.getSelectCircleCount() - 2);
                        if (isHaveMidPoint(secondToLastSelectCircle)) {
                            gestureLineStore.addMidCircle(newPoint);
                        }
                    }

                }
                // 判断手势点在圆内，以及更新存储的直线端点
                fingerPoint = new Point(outCircleRadius, lastX, lastY);
                // --LinePointCount;
                // int b=LinePointCount;
                //                int b = gestureLineStore.selectLinePointNumber - 1;
                //                Log.i(TAG, "selectLinePointCount-1=" + b);
                gestureLineStore.setSelectLinePoint(gestureLineStore.selectLinePointNumber - 1, fingerPoint);


                if (isInCircle(lastX, lastY)) {
                    gestureLineStore.addSelectLinePointToIndex(gestureLineStore.selectLinePointNumber - 1, newPoint);
                    if (gestureLineStore.getSelectLinePointCount() >= 3) {
                        secondToLastSelectLinePoint = gestureLineStore.getSelectLinePoint(gestureLineStore.getSelectLinePointCount() - 3);
                        if (isHaveMidPoint(secondToLastSelectLinePoint)) {
                            gestureLineStore.addMidLinePoint(newPoint);
                        }

                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                if (isInCircle(lastX, lastY)) {
                    gestureLineStore.addSelectPoint(newPoint);
                }

                gestureLineStore.removeTempPoint(gestureLineStore.getSelectLinePointCount());
                resetRunnable = new Runnable() {
                    @Override
                    public void run() {
                        gestureLineStore.resetSelectCircle();
                        gestureLineStore.resetSelectLinePoint();
                        invalidate();
                    }
                };
                postDelayed(resetRunnable, 500);
                break;

        }
        invalidate();
        return true;
    }

    private boolean isHaveMidPoint(Point secondTolastSelectPoint) {
        float midPointX;
        float midPointY;
        if (Math.abs(secondTolastSelectPoint.cx - lastSelectPointX) > rowBlock || Math.abs(secondTolastSelectPoint.cy - lastSelectPointY) > rowBlock) {
            midPointX = (secondTolastSelectPoint.cx + lastSelectPointX) / 2;
            midPointY = (secondTolastSelectPoint.cy + lastSelectPointY) / 2;
            if (isInCircle(midPointX, midPointY)) {
                return true;
            }
        }
        return false;
    }


    private boolean isInCircle(float lastX, float lastY) {

        for (int i = 0; i < row; i++) {
            float X = circleX + i * rowBlock;
            for (int j = 0; j < column; j++) {
                float Y = circleY + j * columnBlock;
                if (Math.sqrt((lastX - X) * (lastX - X) + (lastY - Y) * (lastY - Y)) <= config.getOuterCircleRadius()) {
                    newPoint = new Point(config.getOuterCircleRadius(), X, Y);
                    lastSelectPointX = X;
                    lastSelectPointY = Y;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int width = getWidth();
        int height = getHeight();
        row = (int) Math.sqrt(config.getCircle_count());
        column = row;
        rowBlock = width / row;
        columnBlock = height / column;
        circleX = rowBlock / 2;
        circleY = columnBlock / 2;
        drawer = new Drawer(config, width, height);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画九宫格小圆点
        drawer.drawInitCircle(canvas);
        //画选中的大圆、小圆
        for (int i = 0; i < gestureLineStore.getSelectCircleCount(); i++) {
            selectCircle = gestureLineStore.getSelectCircle(i);
            drawer.drawSelectCircle(canvas, selectCircle);

        }
        //画直线
        for (int i = 0; i < gestureLineStore.getSelectLinePointCount() - 1; i++) {
            lineStartPoint = gestureLineStore.getSelectLinePoint(i);
            lineStopPoint = gestureLineStore.getSelectLinePoint(i + 1);
            drawer.drawLine(canvas, lineStartPoint, lineStopPoint);
        }

    }


}









