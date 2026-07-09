package com.ltech.smarthome.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.blankj.utilcode.util.SizeUtils;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class SimpleMonthView extends MonthView {
    private Paint disablePaint;
    private int mRadius;

    @Override // com.haibin.calendarview.BaseMonthView
    protected void onLoopStart(int x, int y) {
    }

    public SimpleMonthView(Context context) {
        super(context);
        this.disablePaint = new Paint();
    }

    @Override // com.haibin.calendarview.BaseMonthView, com.haibin.calendarview.BaseView
    protected void onPreviewHook() {
        this.mRadius = (Math.min(this.mItemWidth, this.mItemHeight) / 5) * 2;
        this.mSchemePaint.setStyle(Paint.Style.STROKE);
        this.mSelectedPaint.setColor(getResources().getColor(R.color.color_blue));
        this.mSelectTextPaint.setColor(-1);
        this.mCurDayTextPaint = this.mCurMonthTextPaint;
        this.disablePaint.setAntiAlias(true);
        this.disablePaint.setTextAlign(Paint.Align.CENTER);
        this.disablePaint.setColor(getResources().getColor(R.color.color_text_dark_gray));
        this.disablePaint.setFakeBoldText(true);
        this.disablePaint.setTextSize(SizeUtils.dp2px(14.0f));
    }

    @Override // com.haibin.calendarview.MonthView
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        canvas.drawCircle(x + (this.mItemWidth / 2), y + (this.mItemHeight / 2), this.mRadius, this.mSelectedPaint);
        return false;
    }

    @Override // com.haibin.calendarview.MonthView
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
        canvas.drawCircle(x + (this.mItemWidth / 2), y + (this.mItemHeight / 2), this.mRadius, this.mSchemePaint);
    }

    @Override // com.haibin.calendarview.MonthView
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        Paint paint;
        Paint paint2;
        float f = this.mTextBaseLine + y;
        int i = x + (this.mItemWidth / 2);
        if (onCalendarIntercept(calendar)) {
            canvas.drawText(String.valueOf(calendar.getDay()), i, f, this.disablePaint);
            return;
        }
        if (isSelected) {
            canvas.drawText(String.valueOf(calendar.getDay()), i, f, this.mSelectTextPaint);
        } else if (hasScheme) {
            String valueOf = String.valueOf(calendar.getDay());
            float f2 = i;
            if (calendar.isCurrentDay()) {
                paint2 = this.mCurDayTextPaint;
            } else {
                paint2 = calendar.isCurrentMonth() ? this.mSchemeTextPaint : this.mOtherMonthTextPaint;
            }
            canvas.drawText(valueOf, f2, f, paint2);
        } else {
            String valueOf2 = String.valueOf(calendar.getDay());
            float f3 = i;
            if (calendar.isCurrentDay()) {
                paint = this.mCurDayTextPaint;
            } else {
                paint = calendar.isCurrentMonth() ? this.mCurMonthTextPaint : this.mOtherMonthTextPaint;
            }
            canvas.drawText(valueOf2, f3, f, paint);
        }
        if (!calendar.isCurrentDay() || isSelected) {
            return;
        }
        canvas.drawCircle(i, (f + (this.mItemHeight / 2.0f)) - SizeUtils.dp2px(10.0f), this.mRadius / 6.0f, this.mSelectedPaint);
    }
}