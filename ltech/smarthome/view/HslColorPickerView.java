package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class HslColorPickerView extends View {
    private static final int PICKER_RADIUS = 100;
    private int bgHeight;
    private int bgWidth;
    private int bitmapBackResId;
    private int flag;
    private Paint mBgPaint;
    private Bitmap mBitmapBack;
    private BitmapShader mBitmapShader;
    private Point mCenterPoint;
    private Context mContext;
    private final RectF mDrawableRect;
    private OnColorChangedListener mOnColorChangedListener;
    private Point mPickPoint;
    private Paint mPickerPaint;
    private Paint mPickerPaintOuter;
    private int mPickerRadius;
    private final Matrix mShaderMatrix;

    public interface OnColorChangedListener {
        void onColorChanged(int color, float hValue, float sValue, boolean finish);
    }

    public HslColorPickerView(Context context) {
        this(context, null);
    }

    public HslColorPickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HslColorPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mShaderMatrix = new Matrix();
        this.mDrawableRect = new RectF();
        this.mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(attrs, R.styleable.CircleColorPickerView);
        this.bitmapBackResId = obtainStyledAttributes.getResourceId(0, R.mipmap.bg_ct);
        obtainStyledAttributes.recycle();
        Paint paint = new Paint();
        this.mBgPaint = paint;
        paint.setAntiAlias(true);
        this.mBgPaint.setDither(true);
        Paint paint2 = new Paint();
        this.mPickerPaint = paint2;
        paint2.setAntiAlias(true);
        this.mPickerPaint.setDither(true);
        this.mPickerPaint.setStyle(Paint.Style.FILL);
        Paint paint3 = new Paint();
        this.mPickerPaintOuter = paint3;
        paint3.setAntiAlias(true);
        this.mPickerPaintOuter.setDither(true);
        this.mPickerPaintOuter.setColor(-1);
        this.mPickerPaintOuter.setShadowLayer(15.0f, 0.0f, 0.0f, -7829368);
        this.mPickPoint = new Point(-100, -100);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (getMeasuredHeight() > getMeasuredWidth()) {
            this.flag = 1;
        } else if (getMeasuredWidth() > getMeasuredHeight()) {
            this.flag = -1;
        }
        int min = Math.min(getMeasuredHeight(), getMeasuredWidth());
        this.bgHeight = min;
        this.bgWidth = min;
        int i = min / 30;
        this.mPickerRadius = i;
        if (i > 100) {
            this.mPickerRadius = 100;
        }
        this.mPickerPaint.setStrokeWidth(this.mPickerRadius >> 2);
        Point point = new Point(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        this.mCenterPoint = point;
        this.mPickPoint.x = point.x;
        this.mPickPoint.y = this.mCenterPoint.y;
        int measuredWidth = getMeasuredWidth();
        float f = ((measuredWidth - r2) / 2.0f) + this.bgWidth;
        int measuredHeight = getMeasuredHeight();
        this.mDrawableRect.set(new RectF((getMeasuredWidth() - this.bgWidth) / 2.0f, (getMeasuredHeight() - this.bgHeight) / 2.0f, f, ((measuredHeight - r3) / 2.0f) + this.bgHeight));
        if (this.bitmapBackResId != 0) {
            this.mBitmapBack = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), this.bitmapBackResId), this.bgWidth, this.bgHeight, false);
        }
        if (this.mBitmapBack != null) {
            BitmapShader bitmapShader = new BitmapShader(this.mBitmapBack, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            this.mBitmapShader = bitmapShader;
            this.mBgPaint.setShader(bitmapShader);
            updateShaderMatrix();
        }
    }

    private void updateShaderMatrix() {
        float width;
        float height;
        this.mShaderMatrix.set(null);
        float f = 0.0f;
        if (this.bgWidth * this.mDrawableRect.height() > this.mDrawableRect.width() * this.bgHeight) {
            width = this.mDrawableRect.height() / this.bgHeight;
            f = (this.mDrawableRect.width() - (this.bgWidth * width)) * 0.5f;
            height = 0.0f;
        } else {
            width = this.mDrawableRect.width() / this.bgWidth;
            height = (this.mDrawableRect.height() - (this.bgHeight * width)) * 0.5f;
        }
        this.mShaderMatrix.setScale(width, width);
        this.mShaderMatrix.postTranslate(((int) (f + 0.5f)) + this.mDrawableRect.left, ((int) (height + 0.5f)) + this.mDrawableRect.top);
        this.mBitmapShader.setLocalMatrix(this.mShaderMatrix);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mBitmapBack != null) {
            canvas.drawCircle(this.mCenterPoint.x, this.mCenterPoint.y, (this.bgWidth / 2) - this.mPickerRadius, this.mBgPaint);
        }
        canvas.drawCircle(this.mPickPoint.x, this.mPickPoint.y, this.mPickerRadius, this.mPickerPaintOuter);
        if (this.mPickPoint.x < 0 || this.mPickPoint.y < 0) {
            return;
        }
        this.mPickerPaint.setColor(getBitmapPixel());
        canvas.drawCircle(this.mPickPoint.x, this.mPickPoint.y, this.mPickerRadius - this.mPickerPaint.getStrokeWidth(), this.mPickerPaint);
    }

    private int getBitmapPixel() {
        int i = this.flag;
        if (i > 0) {
            return this.mBitmapBack.getPixel(this.mPickPoint.x, this.mPickPoint.y - (this.mCenterPoint.y - (this.bgHeight / 2)));
        }
        if (i < 0) {
            return this.mBitmapBack.getPixel(this.mPickPoint.x - (this.mCenterPoint.x - (this.bgWidth / 2)), this.mPickPoint.y);
        }
        return this.mBitmapBack.getPixel(this.mPickPoint.x, this.mPickPoint.y);
    }

    private float getH(float x, float y) {
        float degrees = ((float) Math.toDegrees((float) Math.atan2(y - this.mCenterPoint.y, x - this.mCenterPoint.x))) + 90.0f;
        if (degrees < 0.0f) {
            degrees += 360.0f;
        }
        return Math.min(degrees, 360.0f);
    }

    private float getS(float x, float y) {
        return (((float) Math.sqrt(Math.pow(x - this.mCenterPoint.x, 2.0d) + Math.pow(y - this.mCenterPoint.y, 2.0d))) / ((this.bgWidth / 2.0f) - this.mPickerRadius)) * 100.0f;
    }

    public void setPoint(float h, float s) {
        double radians = Math.toRadians(h - 90.0f);
        double d2 = (((this.bgWidth / 2.0f) - this.mPickerRadius) * s) / 100.0f;
        this.mPickPoint.set((int) (this.mCenterPoint.x + (Math.cos(radians) * d2)), (int) (this.mCenterPoint.y + (d2 * Math.sin(radians))));
        postInvalidate();
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0012, code lost:
    
        if (r0 != 3) goto L27;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r8) {
        /*
            Method dump skipped, instructions count: 266
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.view.HslColorPickerView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private void attemptClaimDrag() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    public void setOnColorChangedListener(OnColorChangedListener onColorChangedListener) {
        this.mOnColorChangedListener = onColorChangedListener;
    }
}