package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.ltech.smarthome.R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes4.dex */
public class MyTimePickerView extends View {
    public static final float MARGIN_ALPHA = 2.8f;
    public static final float SPEED = 10.0f;
    public static final String TAG = "PickerView";
    private boolean isInit;
    private int mColorText;
    private int mColorText2;
    private int mCurrentSelected;
    private List<SelectItem> mDataList;
    private float mLastDownY;
    private float mMaxTextAlpha;
    private float mMaxTextSize;
    private float mMinTextAlpha;
    private float mMinTextSize;
    private float mMoveLen;
    private Paint mPaint;
    private Paint mPaint2;
    private onSelectListener mSelectListener;
    private MyTimerTask mTask;
    private int mViewHeight;
    private int mViewWidth;
    private float maxTextSizePercent;
    private float minTextSizePercent;
    private Timer timer;
    private Handler updateHandler;
    private String value;

    public interface onSelectListener {
        void onSelect(int position, String text);
    }

    public MyTimePickerView(Context context) {
        super(context);
        this.mMaxTextSize = 50.0f;
        this.mMinTextSize = 45.0f;
        this.mMaxTextAlpha = 255.0f;
        this.mMinTextAlpha = 120.0f;
        this.mColorText = 16670515;
        this.mColorText2 = -6184797;
        this.mMoveLen = 0.0f;
        this.isInit = false;
        this.maxTextSizePercent = 0.125f;
        this.minTextSizePercent = 0.0f;
        this.updateHandler = new Handler(Looper.getMainLooper()) { // from class: com.ltech.smarthome.view.MyTimePickerView.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (Math.abs(MyTimePickerView.this.mMoveLen) < 10.0f) {
                    MyTimePickerView.this.mMoveLen = 0.0f;
                    if (MyTimePickerView.this.mTask != null) {
                        MyTimePickerView.this.mTask.cancel();
                        MyTimePickerView.this.mTask = null;
                        MyTimePickerView.this.performSelect();
                    }
                } else {
                    MyTimePickerView.this.mMoveLen -= (MyTimePickerView.this.mMoveLen / Math.abs(MyTimePickerView.this.mMoveLen)) * 10.0f;
                }
                MyTimePickerView.this.invalidate();
            }
        };
        init();
    }

    public MyTimePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mMaxTextSize = 50.0f;
        this.mMinTextSize = 45.0f;
        this.mMaxTextAlpha = 255.0f;
        this.mMinTextAlpha = 120.0f;
        this.mColorText = 16670515;
        this.mColorText2 = -6184797;
        this.mMoveLen = 0.0f;
        this.isInit = false;
        this.maxTextSizePercent = 0.125f;
        this.minTextSizePercent = 0.0f;
        this.updateHandler = new Handler(Looper.getMainLooper()) { // from class: com.ltech.smarthome.view.MyTimePickerView.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (Math.abs(MyTimePickerView.this.mMoveLen) < 10.0f) {
                    MyTimePickerView.this.mMoveLen = 0.0f;
                    if (MyTimePickerView.this.mTask != null) {
                        MyTimePickerView.this.mTask.cancel();
                        MyTimePickerView.this.mTask = null;
                        MyTimePickerView.this.performSelect();
                    }
                } else {
                    MyTimePickerView.this.mMoveLen -= (MyTimePickerView.this.mMoveLen / Math.abs(MyTimePickerView.this.mMoveLen)) * 10.0f;
                }
                MyTimePickerView.this.invalidate();
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.MyTimePickerView);
        this.maxTextSizePercent = obtainStyledAttributes.getFloat(0, 0.125f);
        this.minTextSizePercent = obtainStyledAttributes.getFloat(1, 0.0f);
        obtainStyledAttributes.recycle();
        init();
    }

    public void setOnSelectListener(onSelectListener listener) {
        this.mSelectListener = listener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performSelect() {
        onSelectListener onselectlistener = this.mSelectListener;
        if (onselectlistener != null) {
            onselectlistener.onSelect(this.mDataList.get(this.mCurrentSelected).position, this.mDataList.get(this.mCurrentSelected).itemString);
        }
    }

    public void setData(List<String> datas) {
        this.mDataList.clear();
        int size = datas.size();
        for (int i = 0; i < size; i++) {
            this.mDataList.add(new SelectItem(i, datas.get(i)));
        }
        this.mCurrentSelected = datas.size() / 2;
        invalidate();
    }

    private void setSelected(int selected) {
        this.mCurrentSelected = selected;
        int size = (this.mDataList.size() / 2) - this.mCurrentSelected;
        int i = 0;
        if (size < 0) {
            while (i < (-size)) {
                moveHeadToTail();
                this.mCurrentSelected--;
                i++;
            }
        } else if (size > 0) {
            while (i < size) {
                moveTailToHead();
                this.mCurrentSelected++;
                i++;
            }
        }
        invalidate();
    }

    public void setSelectedPosition(int position) {
        for (int i = 0; i < this.mDataList.size(); i++) {
            if (this.mDataList.get(i).position == position) {
                setSelected(i);
                return;
            }
        }
    }

    private void moveHeadToTail() {
        SelectItem selectItem = this.mDataList.get(0);
        this.mDataList.remove(0);
        this.mDataList.add(selectItem);
    }

    private void moveTailToHead() {
        SelectItem selectItem = this.mDataList.get(r0.size() - 1);
        this.mDataList.remove(r1.size() - 1);
        this.mDataList.add(0, selectItem);
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mViewHeight = getMeasuredHeight();
        this.mViewWidth = getMeasuredWidth();
        int i = this.mViewHeight;
        float f = this.maxTextSizePercent;
        this.mMaxTextSize = i * f;
        if (this.minTextSizePercent == 0.0f) {
            this.minTextSizePercent = f / 2.0f;
        }
        this.mMinTextSize = i * this.minTextSizePercent;
        this.isInit = true;
        invalidate();
    }

    private void init() {
        this.timer = new Timer();
        this.mDataList = new ArrayList();
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mPaint.setTextAlign(Paint.Align.CENTER);
        this.mPaint.setColor(this.mColorText2);
        Paint paint2 = new Paint(1);
        this.mPaint2 = paint2;
        paint2.setStyle(Paint.Style.FILL);
        this.mPaint2.setTextAlign(Paint.Align.CENTER);
        this.mPaint2.setColor(this.mColorText);
    }

    public void setMainTextColor(int color) {
        this.mPaint2.setColor(color);
        invalidate();
    }

    public void setSecTextColor(int color) {
        this.mPaint.setColor(color);
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.isInit) {
            drawData(canvas);
        }
    }

    public String getValue() {
        return this.value;
    }

    private void drawData(Canvas canvas) {
        if (!isInEditMode() && this.mCurrentSelected < this.mDataList.size()) {
            float parabola = parabola(this.mViewHeight / 4.0f, this.mMoveLen);
            float f = this.mMaxTextSize;
            float f2 = this.mMinTextSize;
            float f3 = ((f - f2) * parabola) + f2;
            this.mPaint.setTextSize(f3);
            Paint paint = this.mPaint;
            float f4 = this.mMaxTextAlpha;
            float f5 = this.mMinTextAlpha;
            paint.setAlpha((int) (((f4 - f5) * parabola) + f5));
            this.mPaint2.setTextSize(f3);
            Paint paint2 = this.mPaint2;
            float f6 = this.mMaxTextAlpha;
            float f7 = this.mMinTextAlpha;
            paint2.setAlpha((int) (((f6 - f7) * parabola) + f7));
            this.mPaint2.setFakeBoldText(true);
            Paint.FontMetricsInt fontMetricsInt = this.mPaint.getFontMetricsInt();
            float f8 = (float) (((float) ((this.mViewHeight / 2.0d) + this.mMoveLen)) - ((fontMetricsInt.bottom / 2.0d) + (fontMetricsInt.top / 2.0d)));
            String str = this.mDataList.get(this.mCurrentSelected).itemString;
            this.value = str;
            canvas.drawText(str, (float) (this.mViewWidth / 2.0d), f8, this.mPaint2);
            for (int i = 1; this.mCurrentSelected - i >= 0; i++) {
                drawOtherText(canvas, i, -1);
            }
            for (int i2 = 1; this.mCurrentSelected + i2 < this.mDataList.size(); i2++) {
                drawOtherText(canvas, i2, 1);
            }
        }
    }

    private void drawOtherText(Canvas canvas, int position, int type) {
        float parabola = parabola(this.mViewHeight / 5.0f, (this.mMinTextSize * 2.8f * position) + (this.mMoveLen * type));
        float f = this.mMaxTextSize;
        float f2 = this.mMinTextSize;
        this.mPaint.setTextSize(((f - f2) * parabola) + f2);
        Paint paint = this.mPaint;
        float f3 = this.mMaxTextAlpha;
        float f4 = this.mMinTextAlpha;
        paint.setAlpha((int) (((f3 - f4) * parabola) + f4));
        float f5 = (float) ((this.mViewHeight / 2.0d) + (r0 * r1));
        Paint.FontMetricsInt fontMetricsInt = this.mPaint.getFontMetricsInt();
        canvas.drawText(this.mDataList.get(this.mCurrentSelected + (type * position)).itemString, (float) (this.mViewWidth / 2.0d), (float) (f5 - ((fontMetricsInt.bottom / 2.0d) + (fontMetricsInt.top / 2.0d))), this.mPaint);
    }

    private float parabola(float zero, float x) {
        float pow = (float) (1.0d - Math.pow(x / zero, 2.0d));
        if (pow < 0.0f) {
            return 0.0f;
        }
        return pow;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        List<SelectItem> list = this.mDataList;
        if (list == null || list.isEmpty()) {
            return false;
        }
        int actionMasked = event.getActionMasked();
        if (actionMasked == 0) {
            doDown(event);
        } else if (actionMasked == 1) {
            doUp(event);
        } else if (actionMasked == 2) {
            doMove(event);
        }
        return true;
    }

    private void doDown(MotionEvent event) {
        MyTimerTask myTimerTask = this.mTask;
        if (myTimerTask != null) {
            myTimerTask.cancel();
            this.mTask = null;
        }
        this.mLastDownY = event.getY();
    }

    private void doMove(MotionEvent event) {
        float y = this.mMoveLen + ((event.getY() - this.mLastDownY) * 2.0f);
        this.mMoveLen = y;
        float f = this.mMinTextSize;
        if (y > (f * 2.8f) / 2.0f) {
            moveTailToHead();
            this.mMoveLen -= this.mMinTextSize * 2.8f;
        } else if (y < (f * (-2.8f)) / 2.0f) {
            moveHeadToTail();
            this.mMoveLen += this.mMinTextSize * 2.8f;
        }
        this.mLastDownY = event.getY();
        invalidate();
    }

    private void doUp(MotionEvent event) {
        if (Math.abs(this.mMoveLen) < 1.0E-4d) {
            this.mMoveLen = 0.0f;
            return;
        }
        MyTimerTask myTimerTask = this.mTask;
        if (myTimerTask != null) {
            myTimerTask.cancel();
            this.mTask = null;
        }
        MyTimerTask myTimerTask2 = new MyTimerTask(this.updateHandler);
        this.mTask = myTimerTask2;
        this.timer.schedule(myTimerTask2, 0L, 10L);
    }

    static class MyTimerTask extends TimerTask {
        WeakReference<Handler> mWeakReference;

        MyTimerTask(Handler handler) {
            this.mWeakReference = new WeakReference<>(handler);
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            WeakReference<Handler> weakReference = this.mWeakReference;
            if (weakReference == null || weakReference.get() == null) {
                return;
            }
            this.mWeakReference.get().sendMessage(this.mWeakReference.get().obtainMessage());
        }
    }

    public void clearData() {
        Handler handler = this.updateHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    private static final class SelectItem {
        String itemString;
        int position;

        public SelectItem(int position, String itemString) {
            this.position = position;
            this.itemString = itemString;
        }
    }
}