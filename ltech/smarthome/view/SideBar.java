package com.ltech.smarthome.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import androidx.exifinterface.media.ExifInterface;

/* loaded from: classes4.dex */
public class SideBar extends View {

    /* renamed from: b, reason: collision with root package name */
    public static String[] f6280b = {ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "B", "C", "D", ExifInterface.LONGITUDE_EAST, "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", ExifInterface.LATITUDE_SOUTH, ExifInterface.GPS_DIRECTION_TRUE, "U", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, ExifInterface.LONGITUDE_WEST, "X", "Y", "Z"};
    private int choose;
    private TextView mTextDialog;
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    private Paint paint;
    private int textSize;
    private int verticalPadding;

    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
    }

    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.choose = -1;
        this.paint = new Paint();
        this.textSize = 34;
        this.verticalPadding = 10;
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.choose = -1;
        this.paint = new Paint();
        this.textSize = 34;
        this.verticalPadding = 10;
    }

    public SideBar(Context context) {
        super(context);
        this.choose = -1;
        this.paint = new Paint();
        this.textSize = 34;
        this.verticalPadding = 10;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        int length = height / f6280b.length;
        for (int i = 0; i < f6280b.length; i++) {
            this.paint.setColor(Color.parseColor("#4D8DF2"));
            this.paint.setTypeface(Typeface.DEFAULT_BOLD);
            this.paint.setAntiAlias(true);
            this.paint.setTextSize(this.textSize);
            if (i == this.choose) {
                this.paint.setColor(Color.parseColor("#3399ff"));
                this.paint.setFakeBoldText(true);
            }
            canvas.drawText(f6280b[i], (width / 2) - (this.paint.measureText(f6280b[i]) / 2.0f), (length * i) + length, this.paint);
            this.paint.reset();
        }
    }

    @Override // android.view.View
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY();
        int i = this.choose;
        OnTouchingLetterChangedListener onTouchingLetterChangedListener = this.onTouchingLetterChangedListener;
        String[] strArr = f6280b;
        int height = (int) ((y / getHeight()) * strArr.length);
        if (action == 1) {
            setBackgroundDrawable(new ColorDrawable(0));
            this.choose = -1;
            invalidate();
            TextView textView = this.mTextDialog;
            if (textView != null) {
                textView.setVisibility(4);
            }
        } else if (i != height && height >= 0 && height < strArr.length) {
            if (onTouchingLetterChangedListener != null) {
                onTouchingLetterChangedListener.onTouchingLetterChanged(strArr[height]);
            }
            TextView textView2 = this.mTextDialog;
            if (textView2 != null) {
                textView2.setText(f6280b[height]);
                this.mTextDialog.setVisibility(0);
            }
            this.choose = height;
            invalidate();
        }
        return true;
    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }
}