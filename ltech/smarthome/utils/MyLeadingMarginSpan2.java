package com.ltech.smarthome.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.LeadingMarginSpan;

/* loaded from: classes4.dex */
public class MyLeadingMarginSpan2 implements LeadingMarginSpan.LeadingMarginSpan2 {
    private int mLines;
    private int mMargin;

    @Override // android.text.style.LeadingMarginSpan
    public void drawLeadingMargin(Canvas c2, Paint p, int x, int dir, int top, int baseline, int bottom, CharSequence text, int start, int end, boolean first, Layout layout) {
    }

    public MyLeadingMarginSpan2(int margin, int line) {
        this.mMargin = margin;
        this.mLines = line;
    }

    @Override // android.text.style.LeadingMarginSpan
    public int getLeadingMargin(boolean first) {
        if (first) {
            return this.mMargin;
        }
        return 0;
    }

    @Override // android.text.style.LeadingMarginSpan.LeadingMarginSpan2
    public int getLeadingMarginLineCount() {
        return this.mLines;
    }
}