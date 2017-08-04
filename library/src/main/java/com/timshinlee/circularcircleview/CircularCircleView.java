package com.timshinlee.circularcircleview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Tim on 2017/8/4.
 */
public class CircularCircleView extends View {
    private static final String TAG = "CircularCircleView";

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mCurrentAngle;
    private ValueAnimator mValueAnimator;

    private static final int DEFAULT_RADIUS = 150;
    private int mRadius;
    private static final int DEFAULT_STROKE_WIDTH = 12;
    private int mStrokeWidth;
    private static final int DEFAULT_DURATION = 2000;
    private int mDuration;

    private RectF mViewBounds;
    private int[] mColors;
    private int mColorCount = 0;
    private boolean mNeedUpdateColor;

    public CircularCircleView(Context context) {
        super(context);
    }

    public CircularCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CircularCircleView);
        mRadius = (int) attributes.getDimension(R.styleable.CircularCircleView_radius, DEFAULT_RADIUS);
        mStrokeWidth = (int) attributes.getDimension(R.styleable.CircularCircleView_strokeWidth, DEFAULT_STROKE_WIDTH);
        mDuration = attributes.getInt(R.styleable.CircularCircleView_duration, DEFAULT_DURATION);
        mColors = context.getResources().getIntArray(
                attributes.getResourceId(R.styleable.CircularCircleView_colors, R.array.circular_colors));
        attributes.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mViewBounds = new RectF(mStrokeWidth / 2, mStrokeWidth / 2, 2 * mRadius, 2 * mRadius);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(mColors[mColorCount]);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mValueAnimator = new ValueAnimator();
        mValueAnimator.setFloatValues(0f, 720f);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setDuration(mDuration);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                mNeedUpdateColor = true;
            }
        });
        mValueAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            width = (int) mViewBounds.width() + mStrokeWidth;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            height = (int) mViewBounds.height() + mStrokeWidth;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mNeedUpdateColor) {
            mColorCount++;
            mPaint.setColor(mColors[(mColorCount % mColors.length)]);
            mNeedUpdateColor = false;
        }
        if (mCurrentAngle <= 360) { // 从-90度开始转
            canvas.drawArc(mViewBounds, -90, mCurrentAngle, false, mPaint);
        } else { // 从-90度偏移超过360度的量，覆盖角度也减少超过360度的量
            canvas.drawArc(mViewBounds, -90 + (mCurrentAngle - 360), 360 - (mCurrentAngle - 360), false, mPaint);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mValueAnimator.cancel();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == View.GONE) {
            mValueAnimator.cancel();
        }
    }
}
