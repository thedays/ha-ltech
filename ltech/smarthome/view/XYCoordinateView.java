package com.ltech.smarthome.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import com.ltech.smarthome.R;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.W5bUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

/* loaded from: classes4.dex */
public class XYCoordinateView extends View {
    private Bitmap mBitmap;
    private OnColorChangedListener mOnColorChangedListener;
    private Paint mOuterPickerPaint;
    private Paint mPaint;
    private Point mPickPoint;
    private Paint mPickerPaint;
    private int mPickerRadius;
    private float mTickSpacing;
    private float mTickSpacingY;
    private int selectColor;
    private int xBottom;
    private int yLeft;

    public interface OnColorChangedListener {
        void onColorChanged(int color, float xPercent, float yPercent, boolean finish);
    }

    public XYCoordinateView(Context context) {
        super(context);
        init();
    }

    public XYCoordinateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setColor(-13619152);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setTextSize(22.0f);
        this.mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pic_rgbcw_xyy);
        this.yLeft = dp2px(30);
        this.xBottom = dp2px(30);
        this.mPickerRadius = dp2px(6);
        this.mTickSpacing = dp2px(29);
        this.mTickSpacingY = dp2px(29);
        Paint paint2 = new Paint();
        this.mOuterPickerPaint = paint2;
        paint2.setAntiAlias(true);
        this.mOuterPickerPaint.setDither(true);
        this.mOuterPickerPaint.setStrokeWidth(this.mPickerRadius * 0.3f);
        this.mOuterPickerPaint.setColor(-1);
        this.mOuterPickerPaint.setStyle(Paint.Style.STROKE);
        this.mOuterPickerPaint.setShadowLayer(15.0f, 0.0f, 0.0f, -7829368);
        Paint paint3 = new Paint();
        this.mPickerPaint = paint3;
        paint3.setAntiAlias(true);
        this.mPickerPaint.setAlpha(255);
        this.mPickerPaint.setDither(true);
        this.mPickerPaint.setStyle(Paint.Style.FILL);
        this.mPickPoint = new Point(-100, -100);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawAxes(canvas);
        drawTicksAndLabels(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        canvas.drawBitmap(this.mBitmap, this.yLeft, (getHeight() - this.xBottom) - this.mBitmap.getHeight(), paint);
        canvas.drawCircle(this.mPickPoint.x, this.mPickPoint.y, this.mPickerRadius, this.mOuterPickerPaint);
        this.mPickerPaint.setColor(this.selectColor);
        canvas.drawCircle(this.mPickPoint.x, this.mPickPoint.y, this.mPickerRadius * 0.85f, this.mPickerPaint);
    }

    private void drawAxes(Canvas canvas) {
        this.mPaint.setStrokeWidth(2.0f);
        canvas.drawLine(this.yLeft, getHeight() - this.xBottom, getWidth() - 50, getHeight() - this.xBottom, this.mPaint);
        int i = this.yLeft;
        canvas.drawLine(i, 30.0f, i, (getHeight() - this.xBottom) + 10, this.mPaint);
        this.mPaint.setTextSize(32.0f);
        canvas.drawText("x", getWidth() - 55, (getHeight() - this.xBottom) + 10, this.mPaint);
        canvas.drawText(Constants.Y, this.yLeft - 10, 20.0f, this.mPaint);
        this.mPaint.setTextSize(22.0f);
    }

    private void drawTicksAndLabels(Canvas canvas) {
        for (int i = 1; i <= 10; i++) {
            float height = getHeight() - this.xBottom;
            int i2 = this.yLeft;
            float f = i;
            float f2 = this.mTickSpacing;
            canvas.drawLine(i2 + (f * f2), height, (f2 * f) + i2, height + 20.0f, this.mPaint);
            String format = String.format(Locale.US, "%.1f", Float.valueOf(0.1f * f));
            canvas.drawText(format, (this.yLeft + (f * this.mTickSpacing)) - (this.mPaint.measureText(format) / 2.0f), getHeight() - 20.0f, this.mPaint);
        }
        for (int i3 = 1; i3 <= 9; i3++) {
            float height2 = getHeight() - this.xBottom;
            int i4 = this.yLeft;
            float f3 = i3;
            float f4 = this.mTickSpacingY;
            canvas.drawLine(i4 - 20.0f, height2 - (f3 * f4), i4, height2 - (f4 * f3), this.mPaint);
            canvas.drawText(String.format(Locale.US, "%.1f", Float.valueOf(f3 * 0.1f)), 10.0f, (height2 - (f3 * this.mTickSpacingY)) + 10.0f, this.mPaint);
        }
    }

    private float getXPercent(int x) {
        return Math.max(0.0f, BigDecimal.valueOf((x - this.yLeft) / (this.mTickSpacing * 10.0f)).setScale(3, RoundingMode.HALF_UP).floatValue());
    }

    private float getYPercent(int y) {
        return Math.max(0.0f, BigDecimal.valueOf(((getHeight() - this.xBottom) - y) / (this.mTickSpacingY * 10.0f)).setScale(3, RoundingMode.HALF_UP).floatValue());
    }

    private float getXPoint(float x) {
        return (x * this.mTickSpacing * 10.0f) + this.yLeft;
    }

    private float getYPoint(float y) {
        return (getHeight() - this.xBottom) - ((y * this.mTickSpacingY) * 10.0f);
    }

    public boolean changePoint(float x, float y) {
        int xPoint = (int) getXPoint(x);
        int yPoint = (int) getYPoint(y);
        if (getPickerColor(xPoint, yPoint) == 0) {
            return false;
        }
        int[] xyToRgb2 = W5bUtils.xyToRgb2(x, y);
        this.selectColor = Color.rgb(xyToRgb2[0], xyToRgb2[1], xyToRgb2[2]);
        this.mPickPoint.set(xPoint, yPoint);
        postInvalidate();
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0010, code lost:
    
        if (r0 != 3) goto L12;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r7) {
        /*
            r6 = this;
            r6.attemptClaimDrag()
            int r0 = r7.getAction()
            r1 = 2
            r2 = 1
            if (r0 == 0) goto L31
            if (r0 == r2) goto L13
            if (r0 == r1) goto L31
            r1 = 3
            if (r0 == r1) goto L13
            goto L2c
        L13:
            com.ltech.smarthome.view.XYCoordinateView$OnColorChangedListener r0 = r6.mOnColorChangedListener
            if (r0 == 0) goto L2c
            int r1 = r6.selectColor
            android.graphics.Point r3 = r6.mPickPoint
            int r3 = r3.x
            float r3 = r6.getXPercent(r3)
            android.graphics.Point r4 = r6.mPickPoint
            int r4 = r4.y
            float r4 = r6.getYPercent(r4)
            r0.onColorChanged(r1, r3, r4, r2)
        L2c:
            boolean r7 = super.onTouchEvent(r7)
            return r7
        L31:
            float r0 = r7.getX()
            int r0 = (int) r0
            float r3 = r7.getY()
            int r3 = (int) r3
            int r0 = r6.getPickerColor(r0, r3)
            if (r0 != 0) goto L42
            return r2
        L42:
            float r0 = r7.getX()
            int r0 = (int) r0
            float r0 = r6.getXPercent(r0)
            float r3 = r7.getY()
            int r3 = (int) r3
            float r3 = r6.getYPercent(r3)
            int[] r0 = com.ltech.smarthome.utils.W5bUtils.xyToRgb2(r0, r3)
            r3 = 0
            r4 = r0[r3]
            r5 = r0[r2]
            r0 = r0[r1]
            int r0 = android.graphics.Color.rgb(r4, r5, r0)
            r6.selectColor = r0
            android.graphics.Point r0 = r6.mPickPoint
            float r1 = r7.getX()
            int r1 = (int) r1
            float r7 = r7.getY()
            int r7 = (int) r7
            r0.set(r1, r7)
            r6.postInvalidate()
            com.ltech.smarthome.view.XYCoordinateView$OnColorChangedListener r7 = r6.mOnColorChangedListener
            if (r7 == 0) goto L90
            int r0 = r6.selectColor
            android.graphics.Point r1 = r6.mPickPoint
            int r1 = r1.x
            float r1 = r6.getXPercent(r1)
            android.graphics.Point r4 = r6.mPickPoint
            int r4 = r4.y
            float r4 = r6.getYPercent(r4)
            r7.onColorChanged(r0, r1, r4, r3)
        L90:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.view.XYCoordinateView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private int getPickerColor(int x, int y) {
        int i = x - this.yLeft;
        int height = y - ((getHeight() - this.xBottom) - this.mBitmap.getHeight());
        if (i < 0 || i >= this.mBitmap.getWidth() || height < 0 || height >= this.mBitmap.getHeight()) {
            return 0;
        }
        return this.mBitmap.getPixel(i, height);
    }

    private void attemptClaimDrag() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(1, dp, getResources().getDisplayMetrics());
    }

    public void setOnColorChangedListener(OnColorChangedListener onColorChangedListener) {
        this.mOnColorChangedListener = onColorChangedListener;
    }
}