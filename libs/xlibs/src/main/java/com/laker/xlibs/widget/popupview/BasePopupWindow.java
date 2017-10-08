package com.laker.xlibs.widget.popupview;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;


public abstract class BasePopupWindow extends PopupWindow {


    protected Activity mContext;
    private LayoutInflater mInflater;
    private float mShowAlpha = 0.88f;
    private Drawable mBackgroundDrawable;

    public BasePopupWindow(Activity context) {
        super(context);
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        setOutsideTouchable(true);
        setFocusable(true);
        setTouchable(true);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);

        setContentView(mInflater.inflate(getLayoutId(), null));
        initViews();
        getContentView().setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dismiss();
                return false;
            }
        });

    }


    protected abstract int getLayoutId();

    protected abstract void initViews();

    public <T extends View> T findView(int id) {
        return (T) getContentView().findViewById(id);
    }

    @Override
    public void setOutsideTouchable(boolean touchable) {
        super.setOutsideTouchable(touchable);
        if(touchable) {
            if(mBackgroundDrawable == null) {
                mBackgroundDrawable = new ColorDrawable(Color.TRANSPARENT);
            }
            super.setBackgroundDrawable(mBackgroundDrawable);
        } else {
            super.setBackgroundDrawable(null);
        }
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        mBackgroundDrawable = background;
        setOutsideTouchable(isOutsideTouchable());
    }

    public void showBottom(View v, int x, int y) {
        this.showAtLocation(v, Gravity.BOTTOM, 0, 0);
//        showAnimator().start();
    }

    public void showBottom(View v) {
        this.showAtLocation(v, Gravity.BOTTOM, 0, 0);
//        showAnimator().start();
    }

    public void showCenter(View v) {
        this.showAtLocation(v, Gravity.CENTER, 0, 0);
//        showAnimator().start();
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
//        showAnimator().start();
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
//        showAnimator().start();
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
//        showAnimator().start();
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
//        showAnimator().start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
//        dismissAnimator().start();
    }

    public ValueAnimator showAnimator(){

        ValueAnimator animator = ValueAnimator.ofFloat(1.0f, mShowAlpha);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                setWindowBackgroundAlpha(alpha);
            }
        });
        animator.setDuration(360);
        return animator;
    }

    public ValueAnimator dismissAnimator(){
        ValueAnimator animator = ValueAnimator.ofFloat(mShowAlpha, 1.0f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                setWindowBackgroundAlpha(alpha);
            }
        });
        animator.setDuration(320);
        return animator;
    }

    public void setWindowBackgroundAlpha(float alpha){

        Window window = mContext.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.alpha = alpha;
        window.setAttributes(params);
    }
}
