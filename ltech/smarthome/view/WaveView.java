package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import com.ltech.smarthome.R;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class WaveView extends View {
    short Max;
    short Min;
    private Paint baseLinePaint;
    private List<Short> datas;
    private long drawTime;
    boolean goUp;
    private List<Boolean> goUps;
    private int invalidateTime;
    private boolean isMaxConstant;
    private int mBaseLineColor;
    private float mHeight;
    private int mWaveColor;
    private Paint mWavePaint;
    private float mWidth;
    private short max;
    private Handler myHandler;
    private Thread myThread;
    Runnable runnable;
    short s;
    private float space;
    private float waveStrokeWidth;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.s = (short) 0;
        this.Max = (short) 150;
        this.Min = (short) 10;
        this.goUp = true;
        this.datas = Arrays.asList((short) 50, (short) 100, Short.valueOf(this.Max), (short) 100, (short) 50);
        this.goUps = Arrays.asList(false, false, false, false, false);
        this.max = (short) 300;
        this.space = 16.0f;
        this.mWaveColor = getResources().getColor(R.color.music_icon_red);
        this.mBaseLineColor = getResources().getColor(R.color.music_icon_red);
        this.waveStrokeWidth = 6.0f;
        this.invalidateTime = 10;
        this.isMaxConstant = false;
        this.runnable = new Runnable() { // from class: com.ltech.smarthome.view.WaveView.1
            @Override // java.lang.Runnable
            public void run() {
                for (int i = 0; i < WaveView.this.datas.size(); i++) {
                    short shortValue = ((Short) WaveView.this.datas.get(i)).shortValue();
                    boolean booleanValue = ((Boolean) WaveView.this.goUps.get(i)).booleanValue();
                    if (shortValue == WaveView.this.Max) {
                        booleanValue = false;
                    } else if (shortValue == WaveView.this.Min) {
                        booleanValue = true;
                    }
                    WaveView.this.datas.set(i, Short.valueOf((short) (booleanValue ? shortValue + 10 : shortValue - 10)));
                    WaveView.this.goUps.set(i, Boolean.valueOf(booleanValue));
                }
                WaveView.this.invalidate();
                WaveView waveView = WaveView.this;
                waveView.postRunnableDelay(waveView.runnable, 40);
            }
        };
        this.myHandler = new Handler() { // from class: com.ltech.smarthome.view.WaveView.2
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                WaveView.this.invalidate();
            }
        };
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.WaveView, defStyle, 0);
        this.mWaveColor = obtainStyledAttributes.getColor(4, this.mWaveColor);
        this.mBaseLineColor = obtainStyledAttributes.getColor(0, this.mBaseLineColor);
        this.waveStrokeWidth = obtainStyledAttributes.getDimension(5, this.waveStrokeWidth);
        this.max = (short) obtainStyledAttributes.getInt(2, this.max);
        this.invalidateTime = obtainStyledAttributes.getInt(1, this.invalidateTime);
        this.space = obtainStyledAttributes.getDimension(3, this.space);
        obtainStyledAttributes.recycle();
        initPainters();
    }

    private void initPainters() {
        Paint paint = new Paint();
        this.mWavePaint = paint;
        paint.setColor(this.mWaveColor);
        this.mWavePaint.setStrokeWidth(this.waveStrokeWidth);
        this.mWavePaint.setAntiAlias(true);
        this.mWavePaint.setFilterBitmap(true);
        this.mWavePaint.setStrokeCap(Paint.Cap.ROUND);
        this.mWavePaint.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint();
        this.baseLinePaint = paint2;
        paint2.setColor(this.mBaseLineColor);
        this.baseLinePaint.setStrokeWidth(1.0f);
        this.baseLinePaint.setAntiAlias(true);
        this.baseLinePaint.setFilterBitmap(true);
        this.baseLinePaint.setStyle(Paint.Style.FILL);
    }

    public void invalidateNow() {
        initPainters();
        invalidate();
    }

    public void addData(short data) {
        if (data < 0) {
            data = (short) (-data);
        }
        if (data > this.max && !this.isMaxConstant) {
            this.max = data;
        }
        if (this.datas.size() > this.mWidth / this.space) {
            synchronized (this) {
                this.datas.remove(0);
                this.datas.add(Short.valueOf(data));
            }
        } else {
            this.datas.add(Short.valueOf(data));
        }
        if (System.currentTimeMillis() - this.drawTime > this.invalidateTime) {
            invalidate();
            this.drawTime = System.currentTimeMillis();
        }
    }

    public void start() {
        this.myHandler.removeCallbacks(this.runnable);
        if (this.myThread == null) {
            Thread thread = new Thread(this.runnable);
            this.myThread = thread;
            thread.start();
        }
        setVisibility(0);
        postRunnableDelay(this.runnable, 40);
    }

    public void stop() {
        setVisibility(8);
        this.myHandler.removeCallbacks(this.runnable);
    }

    public void pause() {
        setVisibility(0);
        this.myHandler.removeCallbacks(this.runnable);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.translate(0.0f, this.mHeight / 2.0f);
        drawWave(canvas);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.mWidth = w;
        this.mHeight = h;
    }

    private void drawWave(Canvas mCanvas) {
        for (int i = 0; i < this.datas.size(); i++) {
            float f = i * this.space;
            float shortValue = ((this.datas.get(i).shortValue() / this.max) * this.mHeight) / 2.0f;
            float f2 = f + 6.0f;
            mCanvas.drawLine(f2, -shortValue, f2, shortValue, this.mWavePaint);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postRunnableDelay(Runnable runnable, int delayTime) {
        this.myHandler.postDelayed(runnable, delayTime);
    }
}