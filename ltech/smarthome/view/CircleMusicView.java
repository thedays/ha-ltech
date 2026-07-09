package com.ltech.smarthome.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import androidx.appcompat.widget.AppCompatImageView;

/* loaded from: classes4.dex */
public class CircleMusicView extends AppCompatImageView {
    public static final int STATE_INIT = 1;
    public static final int STATE_PAUSE = 2;
    private ObjectAnimator objectAnimator;
    private Paint paint;
    public int state;

    public CircleMusicView(Context context) {
        this(context, null);
        init();
    }

    public CircleMusicView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public CircleMusicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.paint = new Paint();
        init();
    }

    public void init() {
        this.state = 1;
        ObjectAnimator objectAnimator = this.objectAnimator;
        if (objectAnimator != null) {
            objectAnimator.end();
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "rotation", 0.0f, 360.0f);
        this.objectAnimator = ofFloat;
        ofFloat.setDuration(24000L);
        this.objectAnimator.setInterpolator(new LinearInterpolator());
        this.objectAnimator.setRepeatCount(-1);
        this.objectAnimator.setRepeatMode(1);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            Bitmap circleBitmap = getCircleBitmap(((BitmapDrawable) drawable).getBitmap());
            Rect rect = new Rect(0, 0, circleBitmap.getWidth(), circleBitmap.getHeight());
            Rect rect2 = new Rect(0, 0, getWidth(), getHeight());
            this.paint.reset();
            canvas.drawBitmap(circleBitmap, rect, rect2, this.paint);
            return;
        }
        super.onDraw(canvas);
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        this.paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        this.paint.setColor(-12434878);
        float width = bitmap.getWidth() / 2;
        canvas.drawCircle(width, width, width, this.paint);
        this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, this.paint);
        return createBitmap;
    }

    public void playMusic() {
        if (this.state == 2) {
            this.objectAnimator.resume();
        } else {
            this.objectAnimator.start();
        }
    }

    public void pauseMusic() {
        this.state = 2;
        this.objectAnimator.pause();
    }

    public void stopMusic() {
        this.objectAnimator.end();
    }
}