package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.ltech.smarthome.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class SpreadView extends View {
    private static final int MESSAGE_REFRESH = 0;
    private int alpha;
    private List<Integer> alphaList;
    private int bitmapBackResId;
    private Paint centerPaint;
    private Point centerPoint;
    private int delayTime;
    private int length;
    private Bitmap mBitmapBack;
    private Paint mSpreadPaint;
    private Handler mainHandler;
    private int maxAlpha;
    private int minAlpha;
    private int minRadius;
    private int radius;
    private int ringCount;
    private int speed;
    private int spreadAlpha;
    private int spreadColor;
    private int spreadDistance;
    private List<Integer> spreadRadiusList;
    private boolean startAnimate;

    public SpreadView(Context context) {
        this(context, null);
    }

    public SpreadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpreadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.spreadRadiusList = new ArrayList();
        this.alphaList = new ArrayList();
        this.mainHandler = new Handler(Looper.getMainLooper()) { // from class: com.ltech.smarthome.view.SpreadView.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                SpreadView.this.postInvalidate();
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.SpreadView);
        this.bitmapBackResId = obtainStyledAttributes.getResourceId(0, 0);
        this.spreadColor = obtainStyledAttributes.getColor(4, ContextCompat.getColor(context, R.color.color_text_red));
        this.speed = obtainStyledAttributes.getInt(3, 10);
        this.ringCount = obtainStyledAttributes.getInt(2, 2);
        this.delayTime = obtainStyledAttributes.getInt(1, 60);
        obtainStyledAttributes.recycle();
        Paint paint = new Paint();
        this.mSpreadPaint = paint;
        paint.setAntiAlias(true);
        this.mSpreadPaint.setColor(this.spreadColor);
        Paint paint2 = new Paint();
        this.centerPaint = paint2;
        paint2.setAntiAlias(true);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.length = Math.min(getMeasuredWidth(), getMeasuredHeight());
        this.centerPoint = new Point(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        this.minRadius = (int) (((this.length / 8.0f) * 3.0f) / 2.0f);
        if (this.bitmapBackResId != 0) {
            Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), this.bitmapBackResId);
            int i = this.minRadius;
            this.mBitmapBack = Bitmap.createScaledBitmap(decodeResource, i * 2, i * 2, false);
        }
        this.maxAlpha = 204;
        this.minAlpha = 7;
        int i2 = this.ringCount;
        this.spreadDistance = (int) (((this.length / 2.0f) - this.minRadius) / i2);
        this.spreadAlpha = (int) (((204 - 7) * 1.0f) / i2);
        this.spreadRadiusList.clear();
        this.alphaList.clear();
        this.spreadRadiusList.add(Integer.valueOf(this.minRadius));
        this.alphaList.add(Integer.valueOf(this.maxAlpha));
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < this.spreadRadiusList.size(); i++) {
            int intValue = this.alphaList.get(i).intValue();
            this.alpha = intValue;
            this.mSpreadPaint.setAlpha(intValue);
            this.radius = this.spreadRadiusList.get(i).intValue();
            canvas.drawCircle(this.centerPoint.x, this.centerPoint.y, this.radius, this.mSpreadPaint);
            if (this.radius < this.length / 2) {
                this.alphaList.set(i, Integer.valueOf(Math.max((int) (this.alpha - ((this.spreadAlpha * 1.0f) / this.speed)), this.minAlpha)));
                this.spreadRadiusList.set(i, Integer.valueOf(this.radius + ((int) ((this.spreadDistance * 1.0f) / this.speed))));
            } else {
                this.alphaList.set(i, Integer.valueOf(this.maxAlpha));
                this.spreadRadiusList.set(i, Integer.valueOf(this.minRadius));
            }
        }
        if (!this.spreadRadiusList.isEmpty()) {
            if (this.spreadRadiusList.get(r0.size() - 1).intValue() >= this.minRadius + this.spreadDistance && this.spreadRadiusList.size() < this.ringCount) {
                this.spreadRadiusList.add(Integer.valueOf(this.minRadius));
                this.alphaList.add(Integer.valueOf(this.maxAlpha));
            }
        }
        Bitmap bitmap = this.mBitmapBack;
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, this.centerPoint.x - this.minRadius, this.centerPoint.y - this.minRadius, this.centerPaint);
        }
        refreshView();
    }

    private void refreshView() {
        if (!this.startAnimate || this.mainHandler.hasMessages(0)) {
            return;
        }
        this.mainHandler.sendEmptyMessageDelayed(0, this.delayTime);
    }

    public void setAnimate(boolean start) {
        this.startAnimate = start;
        if (start) {
            refreshView();
        } else {
            this.mainHandler.removeMessages(0);
        }
    }

    public void clear() {
        this.startAnimate = false;
        this.mainHandler.removeCallbacksAndMessages(null);
    }

    public void changeSpreadColor(int spreadColor) {
        this.mSpreadPaint.setColor(spreadColor);
        invalidate();
    }
}