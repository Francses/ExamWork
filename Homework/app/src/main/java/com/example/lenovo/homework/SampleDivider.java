package com.example.lenovo.homework;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lenovo on 2015/5/15.
 */
public class SampleDivider extends RecyclerView.ItemDecoration {
    //默认分割条Drawable的ID
    public static final int[] ATTRS = {android.R.attr.listDivider};

    //创建分割条Drawable的对象
    private Drawable mDivider;

    public SampleDivider(Context context){
        TypedArray a = context.obtainStyledAttributes(ATTRS);

        //获取系统提供的分割条对象
        mDivider = a.getDrawable(0);

        //TypedArray所占的空间
        a.recycle();
    }

    //绘制所有列表项之间的分割条
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        //获取列表项总数
        int childCount = parent.getChildCount();

        //开始绘制所有列表项的分割线
        for (int i=0; i< childCount; i++){
            //获得当前列表项
            View child = parent.getChildAt(i);

            //获取当前列表项的布局参数信息
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            
            //计算分割条左上角的纵坐标
            int top = child.getBottom() + params.bottomMargin;

            //计算分割条右下角的纵坐标
            int bottom = top + mDivider.getIntrinsicHeight();
            
            //设置分割条绘制的位置
            mDivider.setBounds(left,top,right,bottom);

            //开始绘制当前列表项下方的分割条
            mDivider.draw(c);

        }
    }
}