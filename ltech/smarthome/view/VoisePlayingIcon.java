package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.internal.view.SupportMenu;
import com.ltech.smarthome.R;
import com.ltech.smarthome.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* loaded from: classes4.dex */
public class VoisePlayingIcon extends View {
    private float basePointX;
    private float basePointY;
    private boolean isPlaying;
    private Handler myHandler;
    private Thread myThread;
    private Paint paint;
    private int pointerColor;
    private int pointerNum;
    private float pointerPadding;
    private int pointerSpeed;
    private float pointerWidth;
    private List<Pointer> pointers;

    public VoisePlayingIcon(Context context) {
        super(context);
        this.pointerColor = SupportMenu.CATEGORY_MASK;
        this.isPlaying = false;
        this.myHandler = new Handler() { // from class: com.ltech.smarthome.view.VoisePlayingIcon.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                VoisePlayingIcon.this.invalidate();
            }
        };
        init();
    }

    public VoisePlayingIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.pointerColor = SupportMenu.CATEGORY_MASK;
        this.isPlaying = false;
        this.myHandler = new Handler() { // from class: com.ltech.smarthome.view.VoisePlayingIcon.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                VoisePlayingIcon.this.invalidate();
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.voisePlayingIconAttr);
        this.pointerColor = obtainStyledAttributes.getColor(0, SupportMenu.CATEGORY_MASK);
        this.pointerNum = obtainStyledAttributes.getInt(1, 4);
        this.pointerWidth = Utils.dip2px(getContext(), obtainStyledAttributes.getFloat(3, 2.0f));
        this.pointerSpeed = obtainStyledAttributes.getInt(2, 40);
        init();
    }

    public VoisePlayingIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.pointerColor = SupportMenu.CATEGORY_MASK;
        this.isPlaying = false;
        this.myHandler = new Handler() { // from class: com.ltech.smarthome.view.VoisePlayingIcon.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                VoisePlayingIcon.this.invalidate();
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.voisePlayingIconAttr);
        this.pointerColor = obtainStyledAttributes.getColor(0, SupportMenu.CATEGORY_MASK);
        this.pointerNum = obtainStyledAttributes.getInt(1, 4);
        this.pointerWidth = Utils.dip2px(getContext(), obtainStyledAttributes.getFloat(3, 2.0f));
        this.pointerSpeed = obtainStyledAttributes.getInt(2, 40);
        init();
    }

    private void init() {
        Paint paint = new Paint();
        this.paint = paint;
        paint.setAntiAlias(true);
        this.paint.setColor(this.pointerColor);
        this.pointers = new ArrayList();
    }

    @Override // android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.basePointY = getHeight() - getPaddingBottom();
        Random random = new Random();
        List<Pointer> list = this.pointers;
        if (list != null) {
            list.clear();
        }
        for (int i = 0; i < this.pointerNum; i++) {
            this.pointers.add(new Pointer(this, (float) ((random.nextInt(10) + 1) * 0.1d * ((getHeight() - getPaddingBottom()) - getPaddingTop()))));
        }
        this.pointerPadding = (((getWidth() - getPaddingLeft()) - getPaddingRight()) - (this.pointerWidth * this.pointerNum)) / (r8 - 1);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.basePointX = getPaddingLeft() + 0.0f;
        int i = 0;
        while (i < this.pointers.size()) {
            Canvas canvas2 = canvas;
            canvas2.drawRect(this.basePointX, this.basePointY - this.pointers.get(i).getHeight(), this.basePointX + this.pointerWidth, this.basePointY, this.paint);
            this.basePointX += this.pointerPadding + this.pointerWidth;
            i++;
            canvas = canvas2;
        }
    }

    public void start() {
        if (this.isPlaying) {
            return;
        }
        if (this.myThread == null) {
            Thread thread = new Thread(new MyRunnable());
            this.myThread = thread;
            thread.start();
        }
        this.isPlaying = true;
    }

    public void stop() {
        this.isPlaying = false;
        invalidate();
    }

    public class MyRunnable implements Runnable {
        public MyRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            float f = 0.0f;
            while (f < 2.14748365E9f) {
                for (int i = 0; i < VoisePlayingIcon.this.pointers.size(); i++) {
                    try {
                        ((Pointer) VoisePlayingIcon.this.pointers.get(i)).setHeight((VoisePlayingIcon.this.basePointY - VoisePlayingIcon.this.getPaddingTop()) * ((float) Math.abs(Math.sin(i + f))));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Thread.sleep(VoisePlayingIcon.this.pointerSpeed);
                if (VoisePlayingIcon.this.isPlaying) {
                    VoisePlayingIcon.this.myHandler.sendEmptyMessage(0);
                    f = (float) (f + 0.1d);
                }
            }
        }
    }

    public class Pointer {
        private float height;

        public Pointer(final VoisePlayingIcon this$0, float height) {
            this.height = height;
        }

        public float getHeight() {
            return this.height;
        }

        public void setHeight(float height) {
            this.height = height;
        }
    }
}