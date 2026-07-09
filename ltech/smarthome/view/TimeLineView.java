package com.ltech.smarthome.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/* loaded from: classes4.dex */
public class TimeLineView extends View {
    private Paint circlePaint;
    private Paint linePaint;
    private boolean showBottomLine;
    private boolean showTopLine;

    public TimeLineView(Context context) {
        super(context);
        this.showTopLine = true;
        this.showBottomLine = true;
        init();
    }

    public TimeLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.showTopLine = true;
        this.showBottomLine = true;
        init();
    }

    public TimeLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.showTopLine = true;
        this.showBottomLine = true;
        init();
    }

    private void init() {
        Paint paint = new Paint();
        this.circlePaint = paint;
        paint.setColor(Color.parseColor("#CCCCCC"));
        this.circlePaint.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint();
        this.linePaint = paint2;
        paint2.setColor(Color.parseColor("#CCCCCC"));
        this.linePaint.setStrokeWidth(3.0f);
    }

    public void setShowTopLine(boolean showTopLine) {
        this.showTopLine = showTopLine;
        invalidate();
    }

    public void setShowBottomLine(boolean showBottomLine) {
        this.showBottomLine = showBottomLine;
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int min = Math.min(width, height) / 2;
        if (this.showTopLine) {
            float f = width / 2;
            canvas.drawLine(f, 0.0f, f, (height / 2) - min, this.linePaint);
        }
        float f2 = width / 2;
        canvas.drawCircle(f2, height / 2, min, this.circlePaint);
        if (this.showBottomLine) {
            canvas.drawLine(f2, r1 + min, f2, height, this.linePaint);
        }
    }
}