package com.xby.fm.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

/**
 * Created by Eddie on 16/2/1.
 */
public class StackMenu extends FrameLayout implements View.OnClickListener{


    private int distance = 0;
    private int offset = 0;
    private boolean isOpen;

    private enum State{
        OPEN,CLOSE
    }
    private State currentState = State.CLOSE;

    public StackMenu(Context context) {
        this(context, null);
    }

    public StackMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StackMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int count = getChildCount();
        for (int i = 0; i<count;i++){
            View viewChild  = getChildAt(i);
            measureChild(viewChild,widthMeasureSpec,heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View btn_main = getChildAt(getChildCount()-1);
        int left = 0;int top = 0;
        if (changed){
            for (int i = getChildCount()-1 ;i >= 0;i--){
                View viewChild  = getChildAt(i);
                int width = viewChild.getMeasuredWidth();
                int height = viewChild.getMeasuredHeight();
                left = getMeasuredWidth()- width;
                top = getMeasuredHeight() - height-distance;
                viewChild.layout(left,top,getMeasuredWidth(),getMeasuredHeight());
                distance += getDisension(100);
            }
            btn_main.setOnClickListener(this);
            changeState(currentState);
        }
    }


    /**
     * 转化dip
     * @param value
     * @return
     */
    public int getDisension(int value){
        return (int)TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,value,
                getResources().getDisplayMetrics());
    }

    @Override
    public void onClick(View v) {
        v.clearAnimation();
        offset = 0;
        if (!isOpen){
            openTranslateAnimation(v);
            isOpen = true;
        }else{
            closeTranslateAnimation(v);
            isOpen = false;
        }
    }

    private void changeState(State currentState) {
        switch (currentState){
            case OPEN:
                break;
            case CLOSE:
                for (int i = 0;i < getChildCount()-1;i++){
                    View view = getChildAt(i);
                    view.setVisibility(View.GONE);
                }
                break;
        }
    }

    public void openTranslateAnimation(View view){
        distance = 0;
        RotateAnimation animation = new RotateAnimation(0f,45f, Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(500);
        animation.setFillAfter(true);
        view.startAnimation(animation);
        for (int i = getChildCount()-2;i>= 0; i--){
            View childView = getChildAt(i);
            view.setVisibility(View.VISIBLE);
            TranslateAnimation translate = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, 0.4f, Animation.RELATIVE_TO_SELF, 0);
            translate.setDuration(100);
            translate.setStartOffset(10*offset);
            translate.setFillAfter(true);
            childView.startAnimation(translate);
            offset++;
        }
    }

    public void closeTranslateAnimation(View view){
        distance = 0;
        RotateAnimation animation = new RotateAnimation(45f,0, Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(500);
        animation.setFillAfter(true);
        view.startAnimation(animation);
        for (int i = getChildCount()-2;i>= 0; i--){
            View childView = getChildAt(i);
            view.setVisibility(View.VISIBLE);
            TranslateAnimation translate = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.4f);
            translate.setDuration(100);
            translate.setStartOffset(50*offset);
            childView.startAnimation(translate);
            offset++;
        }
    }

    public void openScaleAnimation(View view){

        RotateAnimation animation = new RotateAnimation(0f,45f, Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(500);
        animation.setFillAfter(true);
        view.startAnimation(animation);
        for (int i = getChildCount()-2;i>= 0; i--){
            View childView = getChildAt(i);
            view.setVisibility(View.VISIBLE);

            AnimationSet set = new AnimationSet(true);

            ScaleAnimation scaleAnimation = new ScaleAnimation(0,1.0f,0,1.0f,
                    Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            scaleAnimation.setDuration(500);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0,1.0f);
            alphaAnimation.setDuration(500);
            scaleAnimation.setStartOffset(100*offset);
            scaleAnimation.setStartOffset(100*offset);
            set.addAnimation(scaleAnimation);
            set.addAnimation(alphaAnimation);
            childView.startAnimation(set);
            offset++;
        }
    }

    public void closeScaleAnimation(View view){
        RotateAnimation animation = new RotateAnimation(45f,0f, Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(500);
        animation.setFillAfter(true);
        view.startAnimation(animation);

        for (int i = 0;i < getChildCount()-1;i++){
            View viewChild = getChildAt(i);
            view.setVisibility(View.GONE);

            AnimationSet set = new AnimationSet(true);

            ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f,0,1.0f,0,
                    Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            scaleAnimation.setDuration(500);
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0);
            alphaAnimation.setDuration(500);
            scaleAnimation.setStartOffset(100*offset);
            set.addAnimation(scaleAnimation);
            set.addAnimation(alphaAnimation);
            viewChild.startAnimation(set);
            offset++;
        }
    }
}
