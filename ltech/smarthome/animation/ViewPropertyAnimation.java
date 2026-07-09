package com.ltech.smarthome.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/* loaded from: classes3.dex */
public class ViewPropertyAnimation extends Animation {
    private final Camera mCamera = new Camera();
    protected int mWidth = 0;
    protected int mHeight = 0;
    protected float mAlpha = 1.0f;
    protected float mPivotX = 0.0f;
    protected float mPivotY = 0.0f;
    protected float mScaleX = 1.0f;
    protected float mScaleY = 1.0f;
    protected float mRotationX = 0.0f;
    protected float mRotationY = 0.0f;
    protected float mRotationZ = 0.0f;
    protected float mTranslationX = 0.0f;
    protected float mTranslationY = 0.0f;
    protected float mTranslationZ = 0.0f;
    protected float mCameraX = 0.0f;
    protected float mCameraY = 0.0f;
    protected float mCameraZ = -8.0f;
    private float mFromAlpha = -1.0f;
    private float mToAlpha = -1.0f;

    public ViewPropertyAnimation fading(float fromAlpha, float toAlpha) {
        this.mFromAlpha = fromAlpha;
        this.mToAlpha = toAlpha;
        return this;
    }

    @Override // android.view.animation.Animation
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        this.mWidth = width;
        this.mHeight = height;
    }

    @Override // android.view.animation.Animation
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float f = this.mFromAlpha;
        if (f >= 0.0f) {
            float f2 = this.mToAlpha;
            if (f2 >= 0.0f) {
                this.mAlpha = f + ((f2 - f) * interpolatedTime);
            }
        }
    }

    protected void applyTransformation(Transformation t) {
        Matrix matrix = t.getMatrix();
        float f = this.mWidth;
        float f2 = this.mHeight;
        float f3 = this.mPivotX;
        float f4 = this.mPivotY;
        float f5 = this.mRotationX;
        float f6 = this.mRotationY;
        float f7 = this.mRotationZ;
        if (f5 != 0.0f || f6 != 0.0f || f7 != 0.0f) {
            Camera camera = this.mCamera;
            camera.save();
            camera.setLocation(this.mCameraX, this.mCameraY, this.mCameraZ);
            float f8 = this.mTranslationZ;
            if (f8 != 0.0f) {
                camera.translate(0.0f, 0.0f, f8);
            }
            camera.rotateX(f5);
            camera.rotateY(f6);
            camera.rotateZ(-f7);
            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate(-f3, -f4);
            matrix.postTranslate(f3, f4);
        }
        float f9 = this.mScaleX;
        float f10 = this.mScaleY;
        if (f9 != 1.0f || f10 != 1.0f) {
            matrix.postScale(f9, f10);
            matrix.postTranslate((-(f3 / f)) * ((f9 * f) - f), (-(f4 / f2)) * ((f10 * f2) - f2));
        }
        matrix.postTranslate(this.mTranslationX, this.mTranslationY);
        t.setAlpha(this.mAlpha);
    }
}