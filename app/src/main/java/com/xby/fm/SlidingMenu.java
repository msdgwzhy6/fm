package com.xby.fm;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.BoringLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by Eddie on 16/1/30.
 */
public class SlidingMenu extends HorizontalScrollView {


    private LinearLayout mWapper;
    private ViewGroup mMenu;
    private ViewGroup mContent;
    private int mScreenWidth;
    private int mRigtPadding = 100;
    private boolean isOnce;

    private int mMenuWidth;
    private boolean isOpen = true;

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);


    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.SlidingMenu,defStyleAttr,0);
        int n = a.getIndexCount();
        for (int i = 0;i<n;i++){
            int attr = a.getIndex(i);
            if (attr == R.styleable.SlidingMenu_rightPadding){
                mRigtPadding = a.getDimensionPixelSize(attr,(int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,50,
                        context.getResources().getDisplayMetrics()));
            }
        }

        a.recycle();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isOnce){
            mWapper = (LinearLayout) this.getChildAt(0);
            mMenu = (ViewGroup) mWapper.getChildAt(0);
            mContent = (ViewGroup) mWapper.getChildAt(1);
            mMenuWidth =  mMenu.getLayoutParams().width = mScreenWidth - mRigtPadding;
            mContent.getLayoutParams().width = mScreenWidth;
            isOnce = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed){
            this.scrollTo(mMenuWidth,0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_UP:
                int scollX = this.getScrollX();
                if (scollX >= mScreenWidth/2){
                    this.smoothScrollTo(mMenuWidth, 0);
                    isOpen = false;
                }else {
                    this.smoothScrollTo(0,0);
                    isOpen = true;
                }
                return true;
        }


        return super.onTouchEvent(ev);
    }

    public void toggle(){
        if (isOpen){
            this.smoothScrollTo(mMenuWidth,0);
            isOpen = false;
        }else{
            this.smoothScrollTo(0,0);
            isOpen = true;
        }
    }
}
