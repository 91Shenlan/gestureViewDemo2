package com.example.a10341.gestureviewdemo;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by 10341 on 2017/10/15.
 */

public class GestureLineStore {
    private static final String TAG = "GestureLineStore";
    private ArrayList<Point> selectCircleList = new ArrayList<>();
    private ArrayList<Point> selectLinePointList = new ArrayList<>();
    public int selectLinePointNumber = 0;
    public int selectCircleNumber = 0;


    public void addSelectPoint(Point point) {
        if (!selectCircleList.contains(point)) {
            selectCircleList.add(point);
            selectCircleNumber++;
        }
    }

    public void addMidCircle(Point point) {
        if (!selectCircleList.contains(point)) {
            selectCircleList.add(getSelectCircleCount() - 1, point);
            selectCircleNumber++;
        }

    }

    public void addMidLinePoint(Point point) {
        if (!selectLinePointList.contains(point)) {
            selectLinePointList.add(getSelectLinePointCount() - 2, point);
            selectLinePointNumber++;
            Log.i(TAG, "add进去的Point个数为："+selectLinePointNumber);
        }
    }


    public void setSelectLinePoint(int index, Point point) {
        if (index < 0 || index > selectLinePointList.size()) {
            Log.i(TAG, "传给setSelectLinePoint的index值不符合，其值为：" + index);
        }
        selectLinePointList.set(index, point);



    }

    public void addSelectLinePoint(Point point) {
        if (selectLinePointList.contains(point)) {
            Log.i(TAG, "addSelectLinePoint时，出现了list中已经存在这个point的情况，这时selectLinePointNumber="+selectLinePointNumber);
        }
        if (!selectLinePointList.contains(point)) {
            selectLinePointList.add(point);
            //            Log.i(TAG, "添加的pointX坐标"+point.cx);
            //            Log.i(TAG, "添加的pointY坐标"+point.cy);
            selectLinePointNumber++;
            Log.i(TAG, "add进去的Point个数为："+selectLinePointNumber);
        }

    }

    public void addSelectLinePointToIndex(int index, Point point) {
        if ((index > selectLinePointList.size()) || index < 0) {
            Log.i(TAG, "传给addSelectLinePointToIndex的index值非法，其值为："+index);
        }
        if (!selectLinePointList.contains(point)) {
            selectLinePointList.add(index, point);
            selectLinePointNumber++;
          Log.i(TAG, "add进去的Point个数为："+selectLinePointNumber);
        }
    }

    public void removeTempPoint(int index) {
        selectLinePointList.remove(getSelectLinePointCount()-1);
    }


    public void resetSelectCircle() {
        // TODO: 2017/10/16 为什么remove方法不能达到clear的效果
        //        Iterator<Point> iterator = selectCircleList.iterator();
        //        while (iterator.hasNext()) {
        //            iterator.next();
        //            iterator.remove();
        //        }
        selectCircleList.clear();

    }

    public void resetSelectLinePoint() {
        selectLinePointList.clear();
        Log.i(TAG, "resetSelectLinePoint被触发后的selectLinePointList.size="+selectLinePointList.size());
        selectLinePointNumber = 0;
        //        Log.i(TAG, "resetSelectLinePoint被触发了");
        Log.i(TAG, "resetSelectLinePoint被触发后selectLinePointNumber的值"+selectLinePointNumber);
    }

    public Point getSelectCircle(int index) {
        return selectCircleList.get(index);
    }

    public Point getSelectLinePoint(int index) {
        return selectLinePointList.get(index);
    }

    public int getSelectCircleCount() {
        return selectCircleList.size();
    }

    public int getSelectLinePointCount() {
        return selectLinePointList.size();
    }


}
