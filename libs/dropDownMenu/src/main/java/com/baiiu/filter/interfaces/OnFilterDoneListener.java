package com.baiiu.filter.interfaces;

/**
 * author: baiiu
 * date: on 16/1/21 23:30
 * description:
 */
public interface OnFilterDoneListener {
//    void onFilterDone(int position, String positionTitle, String urlValue);
    //如果是双ListView或双GridView,dataPosition要存放两个位置
    void onFilterDone(int titlePosition, String positionTitle,int[] dataPositions);
}