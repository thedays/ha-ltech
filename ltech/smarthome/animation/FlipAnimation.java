package com.ltech.smarthome.animation;

import android.view.animation.Transformation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public class FlipAnimation extends ViewPropertyAnimation {
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;
    public static final int UP = 1;
    protected final int mDirection;
    protected final boolean mEnter;

    @Retention(RetentionPolicy.SOURCE)
    @interface Direction {
    }

    public static FlipAnimation create(int direction, boolean enter, long duration) {
        if (direction == 1 || direction == 2) {
            return new VerticalFlipAnimation(direction, enter, duration);
        }
        return new HorizontalFlipAnimation(direction, enter, duration);
    }

    private FlipAnimation(int direction, boolean enter, long duration) {
        this.mDirection = direction;
        this.mEnter = enter;
        setDuration(duration);
    }

    private static class VerticalFlipAnimation extends FlipAnimation {
        public VerticalFlipAnimation(int direction, boolean enter, long duration) {
            super(direction, enter, duration);
        }

        @Override // com.ltech.smarthome.animation.ViewPropertyAnimation, android.view.animation.Animation
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            this.mPivotX = width * 0.5f;
            this.mPivotY = this.mEnter == (this.mDirection == 1) ? 0.0f : height;
            this.mCameraZ = (-height) * 0.015f;
        }

        @Override // com.ltech.smarthome.animation.ViewPropertyAnimation, android.view.animation.Animation
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            float f = this.mEnter ? interpolatedTime - 1.0f : interpolatedTime;
            if (this.mDirection == 2) {
                f *= -1.0f;
            }
            this.mRotationX = 180.0f * f;
            this.mTranslationY = (-f) * this.mHeight;
            super.applyTransformation(interpolatedTime, t);
            if (this.mEnter) {
                this.mAlpha = interpolatedTime <= 0.5f ? 0.0f : 1.0f;
            } else {
                this.mAlpha = interpolatedTime > 0.5f ? 0.0f : 1.0f;
            }
            applyTransformation(t);
        }
    }

    private static class HorizontalFlipAnimation extends FlipAnimation {
        public HorizontalFlipAnimation(int direction, boolean enter, long duration) {
            super(direction, enter, duration);
        }

        @Override // com.ltech.smarthome.animation.ViewPropertyAnimation, android.view.animation.Animation
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            this.mPivotX = this.mEnter == (this.mDirection == 3) ? 0.0f : width;
            this.mPivotY = height * 0.5f;
            this.mCameraZ = (-width) * 0.015f;
        }

        @Override // com.ltech.smarthome.animation.ViewPropertyAnimation, android.view.animation.Animation
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            float f = this.mEnter ? interpolatedTime - 1.0f : interpolatedTime;
            if (this.mDirection == 4) {
                f *= -1.0f;
            }
            float f2 = -f;
            this.mRotationY = 180.0f * f2;
            this.mTranslationX = f2 * this.mWidth;
            super.applyTransformation(interpolatedTime, t);
            if (this.mEnter) {
                this.mAlpha = interpolatedTime <= 0.5f ? 0.0f : 1.0f;
            } else {
                this.mAlpha = interpolatedTime > 0.5f ? 0.0f : 1.0f;
            }
            applyTransformation(t);
        }
    }
}