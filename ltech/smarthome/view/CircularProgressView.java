package com.ltech.smarthome.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class CircularProgressView extends View {
    public static final int PROGRESS_FACTOR = -360;
    public static final String PROGRESS_PROPERTY = "progress";
    protected final RectF arcElements;
    protected final Paint paint;
    protected float progress;
    protected int progressColor;
    protected String progressTitle;
    private Rect rec;
    protected int ringColor;
    protected int ringWidth;
    private LinearGradient shader;

    public CircularProgressView(Context context) {
        this(context, null);
    }

    public CircularProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.CircularProgressView);
        this.ringColor = obtainStyledAttributes.getColor(2, 0);
        this.progressColor = obtainStyledAttributes.getColor(0, 0);
        this.ringWidth = (int) obtainStyledAttributes.getDimension(3, 20.0f);
        this.progressTitle = obtainStyledAttributes.getString(1);
        obtainStyledAttributes.recycle();
        this.progress = 0.0f;
        Paint paint = new Paint();
        this.paint = paint;
        paint.setAntiAlias(true);
        this.arcElements = new RectF();
        this.rec = new Rect();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float min = ((Math.min(canvas.getHeight(), canvas.getWidth()) / 2) - (this.ringWidth / 2)) * 2.0f;
        float width = (canvas.getWidth() - min) / 2.0f;
        float height = (canvas.getHeight() - min) / 2.0f;
        float f = this.ringWidth / 2;
        float f2 = height + min;
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(this.ringWidth);
        this.paint.setStrokeCap(Paint.Cap.ROUND);
        this.arcElements.set(width + f, height + f, (width + min) - f, f2 - f);
        int i = this.ringColor;
        if (i != 0) {
            this.paint.setColor(i);
        } else {
            this.paint.setColor(-7829368);
        }
        canvas.drawArc(this.arcElements, 0.0f, 360.0f, false, this.paint);
        int i2 = this.progressColor;
        if (i2 != 0) {
            this.paint.setColor(i2);
            canvas.drawArc(this.arcElements, -90.0f, -this.progress, false, this.paint);
        } else {
            if (this.shader == null) {
                this.shader = new LinearGradient(0.0f, height, 0.0f, f2, new int[]{Color.parseColor("#B4ED50"), Color.parseColor("#429321")}, (float[]) null, Shader.TileMode.CLAMP);
            }
            this.paint.setShader(this.shader);
            canvas.drawArc(this.arcElements, -90.0f, -this.progress, false, this.paint);
        }
        String str = (-((int) (this.progress / 3.6d))) + "%";
        this.paint.setShader(null);
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(-1);
        this.paint.setTextSize(spToPx(30));
        this.paint.getTextBounds(str, 0, str.length(), this.rec);
        int width2 = this.rec.width();
        int height2 = this.rec.height();
        String str2 = this.progressTitle;
        if (str2 == null || str2.isEmpty()) {
            return;
        }
        canvas.drawText(str, (canvas.getWidth() - width2) / 2, ((canvas.getHeight() + height2) / 2) - dpToPx(20), this.paint);
        this.paint.setTextSize(spToPx(16));
        Paint paint = this.paint;
        String str3 = this.progressTitle;
        paint.getTextBounds(str3, 0, str3.length(), this.rec);
        canvas.drawText(this.progressTitle, (canvas.getWidth() - this.rec.width()) / 2, ((canvas.getHeight() + this.rec.height()) / 2) + dpToPx(20), this.paint);
    }

    private float spToPx(int sp) {
        return TypedValue.applyDimension(2, sp, getContext().getResources().getDisplayMetrics());
    }

    private float dpToPx(int dp) {
        return TypedValue.applyDimension(1, dp, getContext().getResources().getDisplayMetrics());
    }

    public float getProgress() {
        return this.progress / (-360.0f);
    }

    public void setProgress(float progress) {
        this.progress = progress * (-360.0f);
        invalidate();
    }

    public int getRingColor() {
        return this.ringColor;
    }

    public void setRingColor(int ringColor) {
        this.ringColor = ringColor;
        invalidate();
    }

    public void setRingWidth(int ringWidth) {
        this.ringWidth = ringWidth;
        invalidate();
    }

    public int getRingWidth() {
        return this.ringWidth;
    }

    public void setProgressTitle(String progressTitle) {
        this.progressTitle = progressTitle;
        invalidate();
    }

    public void startAnim(float progress, boolean isAnim) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "progress", 0.0f, progress);
        ofFloat.setDuration(isAnim ? 1000L : 0L);
        ofFloat.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.play(ofFloat);
        animatorSet.start();
    }
}