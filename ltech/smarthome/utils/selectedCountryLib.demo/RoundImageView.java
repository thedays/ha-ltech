package com.ltech.smarthome.utils.selectedCountryLib.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;

/* loaded from: classes4.dex */
public class RoundImageView extends AppCompatImageView {
    float height;
    float width;

    private void init(Context context, AttributeSet attrs) {
    }

    public RoundImageView(Context context) {
        this(context, null);
        init(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override // android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.width = getWidth();
        this.height = getHeight();
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.width >= 12.0f && this.height > 12.0f) {
            Path path = new Path();
            path.moveTo(12.0f, 0.0f);
            path.lineTo(this.width - 12.0f, 0.0f);
            float f = this.width;
            path.quadTo(f, 0.0f, f, 12.0f);
            path.lineTo(this.width, this.height - 12.0f);
            float f2 = this.width;
            float f3 = this.height;
            path.quadTo(f2, f3, f2 - 12.0f, f3);
            path.lineTo(12.0f, this.height);
            float f4 = this.height;
            path.quadTo(0.0f, f4, 0.0f, f4 - 12.0f);
            path.lineTo(0.0f, 12.0f);
            path.quadTo(0.0f, 0.0f, 12.0f, 0.0f);
            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }
}