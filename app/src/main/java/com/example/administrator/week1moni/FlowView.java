package com.example.administrator.week1moni;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018.05.12.
 */

public class FlowView extends ViewGroup{

    public FlowView(Context context) {
        this(context, null);
    }

    public FlowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = 0;
        int measureHeight = 0;

        int childCount = getChildCount();

        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureHeightSize = MeasureSpec.getSize(heightMeasureSpec);

        /**note: 原本写法先判断 Mode 都是Exactly的情况下不去测量                childView,从而提高效率。
         *   针对onMeasure是可以的,但是onLayout中需要获取子控件width和height    时
         *       还是得测量,所以此处统一测量了。
         *
         */

        //if (measureHeightMode == MeasureSpec.EXACTLY
        //&& measureWidthMode == MeasureSpec.EXACTLY) {
        //  measureWidth = measureWidthSize;
        //  measureHeight = measureHeightSize;
        //} else {
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams layoutParam = (MarginLayoutParams) child.getLayoutParams();
            int cWidth = child.getMeasuredWidth() + layoutParam.leftMargin + layoutParam.rightMargin;
            int cHeight = child.getHeight() + layoutParam.topMargin + layoutParam.bottomMargin;

            /**
             * 思路:不换行--> width累加  height取max
             *      换行 --> width = 上一行累加值与当前控件width的 max值
             *               height = 上一行的max值 + 当前控件的height
             */
            if (measureWidth + cWidth > measureWidthSize) {
                measureWidth = Math.max(measureWidth, cWidth);
                measureHeight += cHeight;
            } else {
                measureHeight = Math.max(measureHeight, cHeight);
                measureWidth += cWidth;
            }
        }

        setMeasuredDimension(measureWidthMode == MeasureSpec.EXACTLY ? measureWidthSize : measureWidth,
                measureHeightMode == MeasureSpec.EXACTLY ? measureHeightSize : measureHeight);
    }

    @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int lineWidth = 0;
        int lineHeight = 0;
        int left = 0;
        int top = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams layoutParam = (MarginLayoutParams) child.getLayoutParams();
            int cWidth = child.getWidth() + layoutParam.leftMargin + layoutParam.rightMargin;
            int cHeight = child.getHeight() + layoutParam.topMargin + layoutParam.bottomMargin;

            //换行
            if (cWidth + lineWidth > getWidth()) {
                if (child.getVisibility() != View.GONE) {
                    left = 0;
                    top = lineHeight;

                    child.layout(left + layoutParam.leftMargin, top + layoutParam.topMargin,
                            left + layoutParam.leftMargin + child.getMeasuredWidth(),
                            top + layoutParam.topMargin + child.getMeasuredHeight());
                    left += layoutParam.leftMargin + child.getMeasuredWidth();
                }

                lineWidth = cWidth;
                lineHeight += cHeight;
            } else {
                lineWidth += cWidth;
                lineHeight = Math.max(lineHeight, cHeight);

                if (child.getVisibility() != View.GONE) {
                    child.layout(left + layoutParam.leftMargin, top + layoutParam.topMargin,
                            left + layoutParam.leftMargin + child.getMeasuredWidth(),
                            top + layoutParam.topMargin + child.getMeasuredHeight());
                    left += layoutParam.leftMargin + child.getMeasuredWidth();
                }
            }
        }
    }

    @Override public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
