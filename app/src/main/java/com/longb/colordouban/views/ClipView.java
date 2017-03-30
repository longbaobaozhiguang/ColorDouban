package com.longb.colordouban.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.longb.colordouban.views.tool.EmptyAnimatorListener;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;



public class ClipView extends FrameLayout {

    private Path mPath;

    public ClipView(Context context) {
        super(context);
        init(context);
    }

    public ClipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ClipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPath = new Path();
    }

    public void startRaveAnim(final float cx,final float cy) {
        int width = getWidth();
        int height = getHeight();
        final float radius = (float) Math.sqrt(width * width + height * height);
        ValueAnimator animator = ValueAnimator.ofFloat(0, radius);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                mPath.reset();
                mPath.addCircle(cx, cy, value.floatValue(), Path.Direction.CW);
                postInvalidate();
            }
        });
        animator.addListener(new EmptyAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setVisibility(INVISIBLE);
            }
        });
        animator.setDuration(500);
        animator.start();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.clipPath(mPath);
        canvas.clipRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), Region.Op.REVERSE_DIFFERENCE);
        super.dispatchDraw(canvas);
        canvas.restore();
    }

}
