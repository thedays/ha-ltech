package com.ltech.smarthome.view;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/* loaded from: classes4.dex */
public class NoSpaceTextView extends TextView {
    private boolean refreshMeasure;

    public NoSpaceTextView(Context context) {
        super(context);
        this.refreshMeasure = false;
    }

    public NoSpaceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.refreshMeasure = false;
    }

    public NoSpaceTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.refreshMeasure = false;
    }

    @Override // android.widget.TextView, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        removeSpace(widthMeasureSpec, heightMeasureSpec);
    }

    @Override // android.widget.TextView
    public void setText(CharSequence text, TextView.BufferType type) {
        super.setText(text, type);
        this.refreshMeasure = true;
    }

    private void removeSpace(int widthspc, int heightspc) {
        String[] linesText = getLinesText();
        TextPaint paint = getPaint();
        Rect rect = new Rect();
        String str = linesText[0];
        paint.getTextBounds(str, 0, str.length(), rect);
        Paint.FontMetricsInt fontMetricsInt = new Paint.FontMetricsInt();
        paint.getFontMetricsInt(fontMetricsInt);
        setPadding(getLeftPaddingOffset() - rect.left, (fontMetricsInt.top - rect.top) + getTopPaddingOffset(), getRightPaddingOffset(), getBottomPaddingOffset());
        String str2 = linesText[linesText.length - 1];
        paint.getTextBounds(str2, 0, str2.length(), rect);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() - (fontMetricsInt.bottom - rect.bottom));
        if (this.refreshMeasure) {
            this.refreshMeasure = false;
            measure(widthspc, heightspc);
        }
    }

    private String[] getLinesText() {
        String[] strArr = new String[getLineCount()];
        String charSequence = getText().toString();
        Layout layout = getLayout();
        int i = 0;
        int i2 = 0;
        while (i < getLineCount()) {
            int lineEnd = layout.getLineEnd(i);
            strArr[i] = charSequence.substring(i2, lineEnd);
            i++;
            i2 = lineEnd;
        }
        return strArr;
    }
}