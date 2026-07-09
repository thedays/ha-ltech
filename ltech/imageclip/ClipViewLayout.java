package com.ltech.imageclip;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.qw.curtain.lib.GuideView$$ExternalSyntheticApiModelOutline0;
import java.io.FileDescriptor;
import java.io.IOException;

/* loaded from: classes3.dex */
public class ClipViewLayout extends RelativeLayout {
    private static final int DRAG = 1;
    private static final int NONE = 0;
    private static final String TAG = "ClipViewLayout";
    private static final int ZOOM = 2;
    private float clipRatio;
    private int clipType;
    private ClipView mClipView;
    private int mHeightPixels;
    private float mHorizontalPadding;
    private ImageView mImageView;
    private Matrix mMatrix;
    private final float[] mMatrixValues;
    private float mMaxScale;
    private PointF mMid;
    private float mMinScale;
    private float mOldDist;
    private int mPreViewW;
    private Matrix mSavedMatrix;
    private PointF mStart;
    private float mVerticalPadding;
    private int mWidthPixels;
    private int mode;

    public ClipViewLayout(Context context) {
        this(context, null);
    }

    public ClipViewLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ClipViewLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMatrix = new Matrix();
        this.mSavedMatrix = new Matrix();
        this.mode = 0;
        this.mStart = new PointF();
        this.mMid = new PointF();
        this.mOldDist = 1.0f;
        this.mMatrixValues = new float[9];
        this.mMaxScale = 4.0f;
        init(context, attributeSet);
    }

    public void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ClipViewLayout);
        this.mHorizontalPadding = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ClipViewLayout_horizontalPadding, (int) TypedValue.applyDimension(1, 50.0f, getResources().getDisplayMetrics()));
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ClipViewLayout_clipBorderWidth, (int) TypedValue.applyDimension(1, 1.0f, getResources().getDisplayMetrics()));
        this.clipType = obtainStyledAttributes.getInt(R.styleable.ClipViewLayout_clipType, 2);
        boolean z = obtainStyledAttributes.getBoolean(R.styleable.ClipViewLayout_needDrawBorder, true);
        this.clipRatio = obtainStyledAttributes.getFloat(R.styleable.ClipViewLayout_clipRatio, 1.0f);
        boolean z2 = obtainStyledAttributes.getBoolean(R.styleable.ClipViewLayout_needClip, true);
        int i = obtainStyledAttributes.getInt(R.styleable.ClipViewLayout_clipType, 2);
        int i2 = obtainStyledAttributes.getInt(R.styleable.ClipViewLayout_clipBackground, Color.parseColor("#a8000000"));
        obtainStyledAttributes.recycle();
        ClipView clipView = new ClipView(context);
        this.mClipView = clipView;
        clipView.setClipType(i);
        this.mClipView.setNeedDrawBorder(z);
        this.mClipView.setClipRatio(this.clipRatio);
        this.mClipView.setNeedClip(z2);
        this.mClipView.setCanvasColor(i2);
        this.mClipView.setClipBorderWidth(dimensionPixelSize);
        this.mClipView.setmHorizontalPadding(this.mHorizontalPadding);
        this.mImageView = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        addView(this.mImageView, layoutParams);
        addView(this.mClipView, layoutParams);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.mWidthPixels = displayMetrics.widthPixels;
        this.mHeightPixels = displayMetrics.heightPixels;
        this.mPreViewW = (int) (this.mWidthPixels - (this.mHorizontalPadding * 2.0f));
    }

    public void setClipRatio(float f) {
        this.clipRatio = f;
        ClipView clipView = this.mClipView;
        if (clipView != null) {
            clipView.setClipRatio(f);
        }
    }

    public void setImageSrc(final Uri uri) {
        this.mImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.ltech.imageclip.ClipViewLayout.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                ClipViewLayout.this.initSrcPic(uri);
                ClipViewLayout.this.mImageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    public void setImageSrc(final Bitmap bitmap) {
        this.mImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.ltech.imageclip.ClipViewLayout.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                ClipViewLayout.this.initSrcPic(bitmap);
                ClipViewLayout.this.mImageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x010b, code lost:
    
        if (r1 < r0) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void initSrcPic(android.net.Uri r12) {
        /*
            Method dump skipped, instructions count: 340
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.imageclip.ClipViewLayout.initSrcPic(android.net.Uri):void");
    }

    public void initSrcPic(Bitmap bitmap) {
        float height;
        if (bitmap.getWidth() >= bitmap.getHeight()) {
            height = this.mImageView.getWidth() / bitmap.getWidth();
            this.mMinScale = this.mClipView.getClipRect().width() / bitmap.getWidth();
        } else {
            height = this.mImageView.getHeight() / bitmap.getHeight();
            this.mMinScale = this.mClipView.getClipRect().width() / bitmap.getWidth();
        }
        this.mMatrix.postScale(height, height);
        int width = this.mImageView.getWidth() / 2;
        int height2 = this.mImageView.getHeight() / 2;
        this.mMatrix.postTranslate(width - ((int) ((bitmap.getWidth() * height) / 2.0f)), height2 - ((int) ((bitmap.getHeight() * height) / 2.0f)));
        this.mImageView.setScaleType(ImageView.ScaleType.MATRIX);
        this.mImageView.setImageMatrix(this.mMatrix);
        this.mImageView.setImageBitmap(bitmap);
    }

    public static int getExifOrientation(String str) {
        ExifInterface exifInterface;
        int attributeInt;
        try {
            exifInterface = new ExifInterface(str);
        } catch (IOException e) {
            e.printStackTrace();
            exifInterface = null;
        }
        if (exifInterface != null && (attributeInt = exifInterface.getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, -1)) != -1) {
            if (attributeInt == 3) {
                return 180;
            }
            if (attributeInt == 6) {
                return 90;
            }
            if (attributeInt == 8) {
                return 270;
            }
        }
        return 0;
    }

    public static int getExifOrientation(FileDescriptor fileDescriptor) {
        ExifInterface exifInterface;
        int attributeInt;
        try {
            GuideView$$ExternalSyntheticApiModelOutline0.m4242m();
            exifInterface = GuideView$$ExternalSyntheticApiModelOutline0.m(fileDescriptor);
        } catch (IOException e) {
            e.printStackTrace();
            exifInterface = null;
        }
        if (exifInterface != null && (attributeInt = exifInterface.getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, -1)) != -1) {
            if (attributeInt == 3) {
                return 180;
            }
            if (attributeInt == 6) {
                return 90;
            }
            if (attributeInt == 8) {
                return 270;
            }
        }
        return 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0014, code lost:
    
        if (r0 != 6) goto L36;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r7) {
        /*
            Method dump skipped, instructions count: 307
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.imageclip.ClipViewLayout.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private RectF getMatrixRectF(Matrix matrix) {
        RectF rectF = new RectF();
        if (this.mImageView.getDrawable() != null) {
            rectF.set(0.0f, 0.0f, r1.getIntrinsicWidth(), r1.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }
        return rectF;
    }

    private void checkBorder() {
        float f;
        RectF matrixRectF = getMatrixRectF(this.mMatrix);
        int width = this.mImageView.getWidth();
        int height = this.mImageView.getHeight();
        float f2 = width;
        if (matrixRectF.width() + 0.01d >= f2 - (this.mHorizontalPadding * 2.0f)) {
            f = matrixRectF.left > this.mHorizontalPadding ? (-matrixRectF.left) + this.mHorizontalPadding : 0.0f;
            float f3 = matrixRectF.right;
            float f4 = this.mHorizontalPadding;
            if (f3 < f2 - f4) {
                f = (f2 - f4) - matrixRectF.right;
            }
        } else {
            f = 0.0f;
        }
        float f5 = height;
        if (matrixRectF.height() + 0.01d >= f5 - (this.mVerticalPadding * 2.0f)) {
            r7 = matrixRectF.top > this.mVerticalPadding ? (-matrixRectF.top) + this.mVerticalPadding : 0.0f;
            float f6 = matrixRectF.bottom;
            float f7 = this.mVerticalPadding;
            if (f6 < f5 - f7) {
                r7 = (f5 - f7) - matrixRectF.bottom;
            }
        }
        Log.i(getClass().getSimpleName(), "checkBorder: deltaX=" + f + " deltaY = " + r7);
        this.mMatrix.postTranslate(f, r7);
    }

    public final float getScale() {
        this.mMatrix.getValues(this.mMatrixValues);
        return this.mMatrixValues[0];
    }

    public void setClipType(int i) {
        ClipView clipView = this.mClipView;
        if (clipView != null) {
            clipView.setClipType(i);
        }
    }

    private float spacing(MotionEvent motionEvent) {
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return (float) Math.sqrt((x * x) + (y * y));
    }

    private void midPoint(PointF pointF, MotionEvent motionEvent) {
        pointF.set((motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f, (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x004a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.graphics.Bitmap clip() {
        /*
            r6 = this;
            android.widget.ImageView r0 = r6.mImageView
            r1 = 1
            r0.setDrawingCacheEnabled(r1)
            android.widget.ImageView r0 = r6.mImageView
            r0.buildDrawingCache()
            com.ltech.imageclip.ClipView r0 = r6.mClipView
            android.graphics.Rect r0 = r0.getClipRect()
            r1 = 0
            android.widget.ImageView r2 = r6.mImageView     // Catch: java.lang.Exception -> L43
            android.graphics.Bitmap r2 = r2.getDrawingCache()     // Catch: java.lang.Exception -> L43
            int r3 = r0.left     // Catch: java.lang.Exception -> L43
            int r4 = r0.top     // Catch: java.lang.Exception -> L43
            int r5 = r0.width()     // Catch: java.lang.Exception -> L43
            int r0 = r0.height()     // Catch: java.lang.Exception -> L43
            android.graphics.Bitmap r0 = android.graphics.Bitmap.createBitmap(r2, r3, r4, r5, r0)     // Catch: java.lang.Exception -> L43
            int r2 = r6.clipType     // Catch: java.lang.Exception -> L41
            r3 = 2
            if (r2 != r3) goto L3a
            int r2 = r6.mPreViewW     // Catch: java.lang.Exception -> L41
            float r3 = (float) r2     // Catch: java.lang.Exception -> L41
            float r4 = r6.clipRatio     // Catch: java.lang.Exception -> L41
            float r3 = r3 * r4
            int r3 = (int) r3     // Catch: java.lang.Exception -> L41
            android.graphics.Bitmap r1 = com.ltech.imageclip.util.BitmapUtil.zoomBitmap(r0, r2, r3)     // Catch: java.lang.Exception -> L41
            goto L48
        L3a:
            int r2 = r6.mPreViewW     // Catch: java.lang.Exception -> L41
            android.graphics.Bitmap r1 = com.ltech.imageclip.util.BitmapUtil.zoomBitmap(r0, r2, r2)     // Catch: java.lang.Exception -> L41
            goto L48
        L41:
            r2 = move-exception
            goto L45
        L43:
            r2 = move-exception
            r0 = r1
        L45:
            r2.printStackTrace()
        L48:
            if (r0 == 0) goto L4d
            r0.recycle()
        L4d:
            android.widget.ImageView r0 = r6.mImageView
            r0.destroyDrawingCache()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.imageclip.ClipViewLayout.clip():android.graphics.Bitmap");
    }
}