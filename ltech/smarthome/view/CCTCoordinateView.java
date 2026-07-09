package com.ltech.smarthome.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.W5bUtils;
import java.util.Locale;

/* loaded from: classes4.dex */
public class CCTCoordinateView extends View {
    private OnColorChangedListener mOnColorChangedListener;
    private Paint mOuterPickerPaint;
    private Paint mPaint;
    private Path mPath;
    private Point mPickPoint;
    private Paint mPickerPaint;
    private int mPickerRadius;
    private float mTickSpacing;
    private float mTickSpacingY;
    private int showK;
    private int xBottom;
    private float xStart;
    private int yLeft;
    private float yStart;

    public interface OnColorChangedListener {
        void onColorChanged(float x, float y, boolean finish);
    }

    public CCTCoordinateView(Context context) {
        super(context);
        init();
    }

    public CCTCoordinateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setColor(-13619152);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setTextSize(24.0f);
        this.yLeft = dp2px(30);
        this.xBottom = dp2px(30);
        this.mPickerRadius = dp2px(7);
        this.mTickSpacing = dp2px(33);
        this.mTickSpacingY = dp2px(45);
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
        this.mPickerPaint.setColor(-201277);
        this.mPickerPaint.setDither(true);
        this.mPickerPaint.setStyle(Paint.Style.FILL);
        this.mPickPoint = new Point(-100, -100);
        this.mPath = new Path();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawAxes(canvas);
        drawTicksAndLabels(canvas);
        drawFunctionLine(canvas);
        canvas.drawCircle(this.mPickPoint.x, this.mPickPoint.y, this.mPickerRadius, this.mOuterPickerPaint);
        float xValue = getXValue(this.mPickPoint.x);
        int[] xyToRgb2 = W5bUtils.xyToRgb2(xValue, W5bUtils.convertXtoY(xValue));
        this.mPickerPaint.setColor(Color.rgb(xyToRgb2[0], xyToRgb2[1], xyToRgb2[2]));
        canvas.drawCircle(this.mPickPoint.x, this.mPickPoint.y, this.mPickerRadius * 0.85f, this.mPickerPaint);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(-16777216);
        paint.setTextSize(30.0f);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        if (this.showK == 0) {
            int convertXtoK = W5bUtils.convertXtoK(xValue);
            this.showK = ((convertXtoK / 50) + (convertXtoK % 50 <= 24 ? 0 : 1)) * 50;
        }
        if (this.showK > 12000) {
            canvas.drawText(this.showK + "K", this.mPickPoint.x - 5, this.mPickPoint.y + 70, paint);
        } else {
            canvas.drawText(this.showK + "K", this.mPickPoint.x + 5, this.mPickPoint.y + 90, paint);
        }
        this.showK = 0;
    }

    private void drawAxes(Canvas canvas) {
        this.mPaint.setStrokeWidth(2.0f);
        canvas.drawLine(this.yLeft, getHeight() - this.xBottom, getWidth() - dp2px(30), getHeight() - this.xBottom, this.mPaint);
        int i = this.yLeft;
        canvas.drawLine(i, 30.0f, i, getHeight() - this.xBottom, this.mPaint);
        this.mPaint.setTextSize(32.0f);
        canvas.drawText("x", getWidth() - 55, (getHeight() - this.xBottom) + 10, this.mPaint);
        canvas.drawText(Constants.Y, this.yLeft - 10, 20.0f, this.mPaint);
        this.mPaint.setTextSize(24.0f);
    }

    private void drawTicksAndLabels(Canvas canvas) {
        this.xStart = this.yLeft + (this.mTickSpacing * 0.5f);
        for (int i = 0; i <= 8; i++) {
            float f = i;
            canvas.drawLine((this.mTickSpacing * f) + this.xStart, getHeight() - this.xBottom, (this.mTickSpacing * f) + this.xStart, (getHeight() - this.xBottom) + 20.0f, this.mPaint);
            String format = String.format(Locale.US, "%.2f", Double.valueOf((0.05f * f) + 0.25d));
            canvas.drawText(format, (this.xStart + (f * this.mTickSpacing)) - (this.mPaint.measureText(format) / 2.0f), getHeight() - 20.0f, this.mPaint);
        }
        this.yStart = ((getHeight() - this.xBottom) - 60) - this.mTickSpacingY;
        for (int i2 = 0; i2 <= 3; i2++) {
            int i3 = this.yLeft;
            float f2 = this.yStart;
            float f3 = i2;
            float f4 = this.mTickSpacingY;
            canvas.drawLine(i3 - 20.0f, f2 - (f3 * f4), i3, f2 - (f4 * f3), this.mPaint);
            canvas.drawText(String.format(Locale.US, "%.2f", Double.valueOf((f3 * 0.05f) + 0.3d)), 10.0f, (this.yStart - (f3 * this.mTickSpacingY)) + 10.0f, this.mPaint);
        }
    }

    private void drawFunctionLine(Canvas canvas) {
        this.mPath.reset();
        this.mPath.moveTo(getXPoint(0.2564f), getYPoint(W5bUtils.convertXtoY(0.2564f)));
        for (float f = 0.2564f; f <= 0.6614f; f += 0.001f) {
            float convertXtoY = W5bUtils.convertXtoY(f);
            this.mPath.lineTo(getXPoint(f), getYPoint(convertXtoY));
        }
        this.mPaint.setStrokeWidth(10.0f);
        canvas.drawPath(this.mPath, this.mPaint);
    }

    private float getXPoint(float x) {
        return this.xStart + (((x - 0.25f) / 0.05f) * this.mTickSpacing);
    }

    private float getYPoint(float y) {
        return this.yStart - (((y - 0.3f) / 0.05f) * this.mTickSpacingY);
    }

    private float getXValue(float x) {
        return (((x - this.xStart) / this.mTickSpacing) * 0.05f) + 0.25f;
    }

    public void changePoint(float x, float y, int k) {
        this.mPickPoint.set((int) getXPoint(x), (int) getYPoint(y));
        this.showK = k;
        postInvalidate();
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0010, code lost:
    
        if (r0 != 3) goto L13;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            r5.attemptClaimDrag()
            int r0 = r6.getAction()
            r1 = 1
            if (r0 == 0) goto L2c
            if (r0 == r1) goto L13
            r2 = 2
            if (r0 == r2) goto L2c
            r2 = 3
            if (r0 == r2) goto L13
            goto L27
        L13:
            android.graphics.Point r0 = r5.mPickPoint
            int r0 = r0.x
            float r0 = (float) r0
            float r0 = r5.getXValue(r0)
            float r2 = com.ltech.smarthome.utils.W5bUtils.convertXtoY(r0)
            com.ltech.smarthome.view.CCTCoordinateView$OnColorChangedListener r3 = r5.mOnColorChangedListener
            if (r3 == 0) goto L27
            r3.onColorChanged(r0, r2, r1)
        L27:
            boolean r6 = super.onTouchEvent(r6)
            return r6
        L2c:
            float r6 = r6.getX()
            float r6 = r5.getXValue(r6)
            r0 = 1048790748(0x3e8346dc, float:0.2564)
            r2 = 1059655673(0x3f290ff9, float:0.6604)
            float r6 = com.ltech.smarthome.utils.W5bUtils.clamp(r6, r0, r2)
            float r0 = com.ltech.smarthome.utils.W5bUtils.convertXtoY(r6)
            android.graphics.Point r2 = r5.mPickPoint
            float r3 = r5.getXPoint(r6)
            int r3 = (int) r3
            float r4 = r5.getYPoint(r0)
            int r4 = (int) r4
            r2.set(r3, r4)
            r5.postInvalidate()
            com.ltech.smarthome.view.CCTCoordinateView$OnColorChangedListener r2 = r5.mOnColorChangedListener
            if (r2 == 0) goto L5c
            r3 = 0
            r2.onColorChanged(r6, r0, r3)
        L5c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.view.CCTCoordinateView.onTouchEvent(android.view.MotionEvent):boolean");
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