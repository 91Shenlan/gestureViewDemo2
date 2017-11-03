package com.example.a10341.gestureviewdemo;

import android.graphics.Canvas;

/**
 * Created by jasoncai on 2017/11/1.
 */

public class Drawer {

    private int width;
    private int height;
    private Config config;
    private float outCircleRadius;
    private float interCircleRadius;
    private static final int TYPE_INIT_CIRCLE = 0;
    private static final int TYPE_OUTER_CIRCLE = 1;
    private static final int TYPE_INNER_CIRCLE = 2;
    private static final int TYPE_SELECT_LINE = 3;
    private int columnBlock;
    private int rowBlock;
    private int column;
    private int circleY;
    private int circleX;
    private int row;


    public Drawer(Config config, int width, int height) {
        this.config = config;
        this.width = width;
        this.height = height;
        initCircleSize();
    }

    private void initCircleSize() {
        if (config.getOuterCircleRadius() == -1) {
            config.setOuterCircleRadius(Math.min(width, height) / 8);
            if (config.getInterCircleRadius() == -1) {
                config.setInterCircleRadius(config.getOuterCircleRadius() / 3);
            }
        }
        outCircleRadius = config.getOuterCircleRadius();
        interCircleRadius = config.getInterCircleRadius();
        row = (int) Math.sqrt(config.getCircle_count());
        column = row;
        rowBlock = width / row;
        columnBlock = height / column;
        circleX = rowBlock / 2;
        circleY = columnBlock / 2;
    }


    public void drawInitCircle(Canvas canvas) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                canvas.drawCircle(circleX + i * rowBlock, circleY + j * columnBlock, interCircleRadius, config.getPaint(TYPE_INIT_CIRCLE));
            }
        }
    }

    public void drawSelectCircle(Canvas canvas, Point point) {
        canvas.drawCircle(point.cx, point.cy, interCircleRadius, config.getPaint(TYPE_INNER_CIRCLE));
        canvas.drawCircle(point.cx, point.cy, outCircleRadius, config.getPaint(TYPE_OUTER_CIRCLE));
    }


    public void drawLine(Canvas canvas, Point lineStartPoint, Point lineStopPoint) {
        canvas.drawLine(lineStartPoint.cx, lineStartPoint.cy, lineStopPoint.cx, lineStopPoint.cy, config.getPaint(TYPE_SELECT_LINE));
    }
}
