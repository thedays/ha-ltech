package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.internal.view.SupportMenu;
import com.blankj.utilcode.util.SizeUtils;
import com.ltech.smarthome.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class VoiceView extends View {
    private float amplitude;
    private int beginWidth;
    private int circleStrokeWidth;
    private int circlecolor;
    private boolean haveLeftInterval;
    private boolean haveRightInterval;
    private int height;
    private boolean isSet;
    private boolean isSpeaking;
    private long lastTime;
    private boolean leftWavyDirection;
    private int lineSpeed;
    private Paint paint;
    private Paint paintCircle;
    private List<Path> pathList;
    private float targetVolume;
    private int totalWidth;
    private float translateX;
    private int voiceLineColor;
    private float volume;
    private int width;

    public VoiceView(Context context) {
        this(context, null);
    }

    public VoiceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VoiceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.lastTime = 0L;
        this.lineSpeed = 100;
        this.translateX = 0.0f;
        this.amplitude = 10.0f;
        this.volume = 10.0f;
        this.targetVolume = 1.0f;
        this.isSet = false;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.VoiceView);
        this.haveLeftInterval = obtainStyledAttributes.getBoolean(2, false);
        this.haveRightInterval = obtainStyledAttributes.getBoolean(3, false);
        this.leftWavyDirection = obtainStyledAttributes.getBoolean(4, false);
        this.circleStrokeWidth = (int) obtainStyledAttributes.getDimension(1, SizeUtils.dp2px(2.0f));
        this.circlecolor = obtainStyledAttributes.getColor(0, SupportMenu.CATEGORY_MASK);
        this.voiceLineColor = obtainStyledAttributes.getColor(5, -7829368);
        obtainStyledAttributes.recycle();
        init();
    }

    private void init() {
        Paint paint = new Paint();
        this.paint = paint;
        paint.setAntiAlias(true);
        this.paint.setDither(true);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setColor(this.voiceLineColor);
        this.paint.setStrokeWidth(1.0f);
        this.pathList = new ArrayList();
        for (int i = 0; i < 4; i++) {
            this.pathList.add(new Path());
        }
        Paint paint2 = new Paint();
        this.paintCircle = paint2;
        paint2.setAntiAlias(true);
        this.paintCircle.setDither(true);
        this.paintCircle.setStyle(Paint.Style.STROKE);
        this.paintCircle.setStrokeWidth(this.circleStrokeWidth);
        this.paintCircle.setColor(this.circlecolor);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int measuredWidth = getMeasuredWidth();
        this.width = measuredWidth;
        this.height = measuredWidth;
        int i = measuredWidth - (this.circleStrokeWidth * 2);
        this.totalWidth = i;
        if (this.haveLeftInterval) {
            int i2 = measuredWidth / 12;
            this.beginWidth = i2;
            this.totalWidth = i - i2;
        }
        if (this.haveRightInterval) {
            int i3 = measuredWidth / 12;
            this.beginWidth = i3;
            this.totalWidth -= i3;
        }
        this.volume = measuredWidth / 2;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        lineChange();
        int i = 0;
        while (i < this.pathList.size()) {
            this.pathList.get(i).reset();
            this.pathList.get(i).moveTo(this.circleStrokeWidth, this.height / 2);
            if (this.haveLeftInterval) {
                this.pathList.get(i).lineTo(this.beginWidth, this.height / 2);
            }
            float f = 0.0f;
            while (true) {
                int i2 = this.totalWidth;
                if (f >= i2) {
                    break;
                }
                float size = (((((f / i2) - (((f * f) / i2) / i2)) * this.volume) * 3.0f) * (i + 1)) / this.pathList.size();
                this.amplitude = size;
                float f2 = this.height / 2.0f;
                int i3 = this.totalWidth;
                this.pathList.get(i).lineTo(this.haveLeftInterval ? this.beginWidth + f + this.circleStrokeWidth : this.circleStrokeWidth + f, f2 + (size * ((float) Math.sin(((8.0f * f) / i3) - (this.translateX + (i3 / 5.0f))))));
                f += 1.0f;
            }
            if (this.haveRightInterval) {
                this.pathList.get(i).lineTo(this.width - this.circleStrokeWidth, this.height / 2);
            }
            int i4 = i + 1;
            this.paint.setAlpha(i4 * 63);
            canvas.drawPath(this.pathList.get(i), this.paint);
            i = i4;
        }
        int i5 = this.width;
        canvas.drawCircle(i5 / 2, this.height / 2, (i5 / 2) - this.circleStrokeWidth, this.paintCircle);
        if (this.isSpeaking) {
            postInvalidateDelayed(30L);
        }
    }

    private void lineChange() {
        if (this.lastTime == 0) {
            this.lastTime = System.currentTimeMillis();
            if (this.leftWavyDirection) {
                this.translateX = (float) (this.translateX + 1.5d);
            } else {
                this.translateX = (float) (this.translateX - 1.5d);
            }
        } else {
            if (System.currentTimeMillis() - this.lastTime <= this.lineSpeed) {
                return;
            }
            this.lastTime = System.currentTimeMillis();
            if (this.leftWavyDirection) {
                this.translateX = (float) (this.translateX + 1.5d);
            } else {
                this.translateX = (float) (this.translateX - 1.5d);
            }
        }
        float f = this.volume;
        if (f < this.targetVolume && this.isSet) {
            this.volume = f + (this.height / 30);
            return;
        }
        this.isSet = false;
        if (f <= 10.0f) {
            this.volume = 10.0f;
            return;
        }
        if (f < this.height / 30.0f) {
            this.volume = f - (r1 / 60);
        } else {
            this.volume = f - (r1 / 30);
        }
    }

    public void setVolume(float percent) {
        if (percent >= 0.0f) {
            this.isSet = true;
            if (percent > 1.0f) {
                this.targetVolume = this.height / 2.0f;
            } else {
                this.targetVolume = (this.height / 2.0f) * percent;
            }
        }
    }

    public void setSpeaking(boolean isSpeaking) {
        this.isSpeaking = isSpeaking;
        this.targetVolume = 1.0f;
        this.volume = this.height / 2;
        postInvalidate();
    }

    public boolean isSpeaking() {
        return this.isSpeaking;
    }
}