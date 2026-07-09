package com.ltech.imageclip;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/* loaded from: classes3.dex */
public class ClipView extends View {
    private static final String TAG = "ClipView";
    public static final int TYPE_PALACE = 3;
    public static final int TYPE_RECT = 2;
    public static final int TYPE_ROUND = 1;
    private Paint borderPaint;
    private int canvasColor;
    private int clipBorderWidth;
    private int clipRadiusWidth;
    private float clipRatio;
    private ClipType clipType;
    private int clipWidth;
    private Context context;
    private float mBorderThickness;
    private float mCornerLength;
    private Paint mCornerPaint;
    private float mCornerThickness;
    private Paint mGuidelinePaint;
    private float mHorizontalPadding;
    private Paint mPalaceBorderPaint;
    private float mScaleRadius;
    private boolean needClip;
    private boolean needDrawBorder;
    private Paint paint;
    private Xfermode xfermode;

    public ClipView(Context context) {
        this(context, null);
    }

    public ClipView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ClipView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.paint = new Paint();
        this.borderPaint = new Paint();
        this.clipRatio = 1.0f;
        this.clipType = ClipType.CIRCLE;
        this.needDrawBorder = true;
        this.needClip = true;
        this.paint.setAntiAlias(true);
        this.paint.setStyle(Paint.Style.FILL);
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setColor(-1);
        this.borderPaint.setStrokeWidth(this.clipBorderWidth);
        this.borderPaint.setAntiAlias(true);
        this.xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        this.context = context;
        initData();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.needClip) {
            Log.i(getClass().getSimpleName(), "onDraw: clipType =" + this.clipType);
            canvas.saveLayer(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), null, 31);
            int i = this.canvasColor;
            if (i == 0) {
                i = Color.parseColor("#a8000000");
            }
            canvas.drawColor(i);
            this.paint.setXfermode(this.xfermode);
            if (this.clipType == ClipType.CIRCLE) {
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, this.clipRadiusWidth, this.paint);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, this.clipRadiusWidth, this.borderPaint);
            } else if (this.clipType == ClipType.RECTANGLE) {
                int i2 = ((int) (this.clipWidth * this.clipRatio)) / 2;
                canvas.drawRect(this.mHorizontalPadding, (getHeight() / 2) - i2, getWidth() - this.mHorizontalPadding, (getHeight() / 2) + i2, this.paint);
                if (this.needDrawBorder) {
                    canvas.drawRect(this.mHorizontalPadding, (getHeight() / 2) - (this.clipWidth / 2), getWidth() - this.mHorizontalPadding, (getHeight() / 2) + (this.clipWidth / 2), this.borderPaint);
                }
            } else if (this.clipType == ClipType.PALACE) {
                canvas.drawRect(this.mHorizontalPadding, (getHeight() / 2) - (this.clipWidth / 2), getWidth() - this.mHorizontalPadding, (getHeight() / 2) + (this.clipWidth / 2), this.paint);
                Rect clipRect = getClipRect();
                drawBorder(canvas, clipRect);
                drawGuidelines(canvas, clipRect);
                drawCorners(canvas, clipRect);
            }
            canvas.restore();
        }
    }

    public Rect getClipRect() {
        Rect rect = new Rect();
        if (this.clipType == ClipType.RECTANGLE) {
            int i = (int) (this.clipRadiusWidth * this.clipRatio);
            rect.left = (getWidth() / 2) - this.clipRadiusWidth;
            rect.right = (getWidth() / 2) + this.clipRadiusWidth;
            rect.top = (getHeight() / 2) - i;
            rect.bottom = (getHeight() / 2) + i;
            return rect;
        }
        rect.left = (getWidth() / 2) - this.clipRadiusWidth;
        rect.right = (getWidth() / 2) + this.clipRadiusWidth;
        rect.top = (getHeight() / 2) - this.clipRadiusWidth;
        rect.bottom = (getHeight() / 2) + this.clipRadiusWidth;
        return rect;
    }

    public void setClipBorderWidth(int i) {
        this.clipBorderWidth = i;
        this.borderPaint.setStrokeWidth(i);
        invalidate();
    }

    public void setCanvasColor(int i) {
        this.canvasColor = i;
        invalidate();
    }

    public void setmHorizontalPadding(float f) {
        this.mHorizontalPadding = f;
        int screenWidth = ((int) (getScreenWidth(getContext()) - (f * 2.0f))) / 2;
        this.clipRadiusWidth = screenWidth;
        this.clipWidth = screenWidth * 2;
    }

    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public void setClipType(int i) {
        if (i == 1) {
            setClipType(ClipType.CIRCLE);
        } else if (i == 2) {
            setClipType(ClipType.RECTANGLE);
        } else if (i == 3) {
            setClipType(ClipType.PALACE);
        } else {
            setClipType(ClipType.PALACE);
        }
        invalidate();
    }

    public void setNeedDrawBorder(boolean z) {
        this.needDrawBorder = z;
    }

    public void setClipRatio(float f) {
        this.clipRatio = f;
    }

    public void setNeedClip(boolean z) {
        this.needClip = z;
    }

    public void setClipType(ClipType clipType) {
        this.clipType = clipType;
    }

    public enum ClipType {
        CIRCLE(1),
        RECTANGLE(2),
        PALACE(3);

        private int value;

        ClipType(int i) {
        }
    }

    private void initData() {
        Paint paint = new Paint();
        this.mPalaceBorderPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.mPalaceBorderPaint.setStrokeWidth(dip2px(this.context, 1.0f));
        this.mPalaceBorderPaint.setColor(Color.parseColor("#FFFFFF"));
        Paint paint2 = new Paint();
        this.mGuidelinePaint = paint2;
        paint2.setStyle(Paint.Style.STROKE);
        this.mGuidelinePaint.setStrokeWidth(dip2px(this.context, 1.0f));
        this.mGuidelinePaint.setColor(Color.parseColor("#FFFFFF"));
        Paint paint3 = new Paint();
        this.mCornerPaint = paint3;
        paint3.setStyle(Paint.Style.STROKE);
        this.mCornerPaint.setStrokeWidth(dip2px(this.context, 3.5f));
        this.mCornerPaint.setColor(-1);
        this.mScaleRadius = dip2px(this.context, 24.0f);
        this.mBorderThickness = this.mPalaceBorderPaint.getStrokeWidth();
        this.mCornerThickness = this.mCornerPaint.getStrokeWidth();
        this.mCornerLength = dip2px(this.context, 25.0f);
    }

    private void drawGuidelines(Canvas canvas, Rect rect) {
        float f = rect.left;
        float f2 = rect.top;
        float f3 = rect.right;
        float f4 = rect.bottom;
        float f5 = (f3 - f) / 3.0f;
        float f6 = f + f5;
        canvas.drawLine(f6, f2, f6, f4, this.mGuidelinePaint);
        float f7 = f3 - f5;
        canvas.drawLine(f7, f2, f7, f4, this.mGuidelinePaint);
        float f8 = (f4 - f2) / 3.0f;
        float f9 = f2 + f8;
        canvas.drawLine(f, f9, f3, f9, this.mGuidelinePaint);
        float f10 = f4 - f8;
        canvas.drawLine(f, f10, f3, f10, this.mGuidelinePaint);
    }

    private void drawBorder(Canvas canvas, Rect rect) {
        canvas.drawRect(rect.left, rect.top, rect.right, rect.bottom, this.mPalaceBorderPaint);
    }

    private void drawCorners(Canvas canvas, Rect rect) {
        float f = rect.left;
        float f2 = rect.top;
        float f3 = rect.right;
        float f4 = rect.bottom;
        float f5 = this.mCornerThickness;
        float f6 = this.mBorderThickness;
        float f7 = (f5 - f6) / 2.0f;
        float f8 = f5 - (f6 / 2.0f);
        float f9 = f - f7;
        float f10 = f2 - f8;
        canvas.drawLine(f9, f10, f9, f2 + this.mCornerLength, this.mCornerPaint);
        float f11 = f - f8;
        float f12 = f2 - f7;
        canvas.drawLine(f11, f12, f + this.mCornerLength, f12, this.mCornerPaint);
        float f13 = f3 + f7;
        canvas.drawLine(f13, f10, f13, f2 + this.mCornerLength, this.mCornerPaint);
        float f14 = f3 + f8;
        canvas.drawLine(f14, f12, f3 - this.mCornerLength, f12, this.mCornerPaint);
        float f15 = f4 + f8;
        canvas.drawLine(f9, f15, f9, f4 - this.mCornerLength, this.mCornerPaint);
        float f16 = f4 + f7;
        canvas.drawLine(f11, f16, f + this.mCornerLength, f16, this.mCornerPaint);
        canvas.drawLine(f13, f15, f13, f4 - this.mCornerLength, this.mCornerPaint);
        canvas.drawLine(f14, f16, f3 - this.mCornerLength, f16, this.mCornerPaint);
    }

    public static int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}