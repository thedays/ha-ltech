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
import com.ltech.smarthome.utils.ExtUtils;

/* loaded from: classes4.dex */
public class AnnularColorPickView2 extends View {
    private int annularWidth;
    private int bgHeight;
    private int bgWidth;
    private int bitmapBackResId;
    private int centerColor;
    private int flag;
    private Paint mBgPaint;
    private Bitmap mBitmapBack;
    private BitmapShader mBitmapShader;
    private Point mCenterPoint;
    private final RectF mDrawableRect;
    private IColorChangeListener mOnColorChangedListener;
    private Paint mPaint;
    private Point mPickPoint;
    private final Matrix mShaderMatrix;
    private int pickerColor;

    public interface IColorChangeListener {
        void onColorChanged(int color, float progress);

        void onColorChangedFinish(int color, float progress);
    }

    public AnnularColorPickView2(Context context) {
        this(context, null);
    }

    public AnnularColorPickView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnnularColorPickView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mShaderMatrix = new Matrix();
        this.mDrawableRect = new RectF();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.AnnularColorPickView);
        this.bitmapBackResId = obtainStyledAttributes.getResourceId(2, R.mipmap.bg_ct);
        this.centerColor = obtainStyledAttributes.getColor(1, -16777216);
        this.pickerColor = obtainStyledAttributes.getColor(3, -1);
        this.annularWidth = (int) obtainStyledAttributes.getDimension(0, 0.0f);
        obtainStyledAttributes.recycle();
        Paint paint = new Paint();
        this.mBgPaint = paint;
        paint.setAntiAlias(true);
        this.mPickPoint = new Point(-100, -100);
    }

    private void drawBitmap() {
        if (getHeight() > getWidth()) {
            this.flag = 1;
        } else if (getWidth() > getHeight()) {
            this.flag = -1;
        }
        int min = Math.min(getHeight(), getWidth());
        this.bgHeight = min;
        this.bgWidth = min;
        this.mCenterPoint = new Point(getWidth() / 2, getHeight() / 2);
        if (this.annularWidth == 0) {
            this.annularWidth = this.bgWidth >> 3;
        }
        int width = getWidth();
        float f = ((width - r6) / 2.0f) + this.bgWidth;
        int height = getHeight();
        this.mDrawableRect.set(new RectF((getWidth() - this.bgWidth) / 2.0f, (getHeight() - this.bgHeight) / 2.0f, f, ((height - r7) / 2.0f) + this.bgHeight));
        if (this.bitmapBackResId != 0 && this.mBitmapBack == null) {
            this.mBitmapBack = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), this.bitmapBackResId), this.bgWidth, this.bgHeight, false);
        }
        BitmapShader bitmapShader = new BitmapShader(this.mBitmapBack, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        this.mBitmapShader = bitmapShader;
        this.mBgPaint.setShader(bitmapShader);
        updateShaderMatrix();
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
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        drawBitmap();
        drawBg(canvas);
        drawCenter(canvas);
        drawPicker(canvas);
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0010, code lost:
    
        if (r0 != 3) goto L29;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r7) {
        /*
            r6 = this;
            r6.attemptClaimDrag()
            int r0 = r7.getAction()
            r1 = 1
            r2 = 2
            if (r0 == 0) goto L77
            if (r0 == r1) goto L46
            if (r0 == r2) goto L14
            r3 = 3
            if (r0 == r3) goto L46
            goto Ld3
        L14:
            android.graphics.Point r0 = r6.mCenterPoint
            android.graphics.Point r3 = new android.graphics.Point
            float r4 = r7.getX()
            int r4 = (int) r4
            float r7 = r7.getY()
            int r7 = (int) r7
            r3.<init>(r4, r7)
            int r7 = r6.bgWidth
            int r4 = r6.annularWidth
            int r7 = r7 - r4
            int r7 = r7 / r2
            android.graphics.Point r7 = com.ltech.smarthome.utils.ExtUtils.getBorderPoint(r0, r3, r7)
            r6.mPickPoint = r7
            com.ltech.smarthome.view.AnnularColorPickView2$IColorChangeListener r7 = r6.mOnColorChangedListener
            if (r7 == 0) goto Ld3
            android.graphics.Bitmap r0 = r6.mBitmapBack
            if (r0 == 0) goto Ld3
            int r0 = r6.getBitmapPixel()
            float r2 = r6.getCurProgress()
            r7.onColorChanged(r0, r2)
            goto Ld3
        L46:
            android.graphics.Point r0 = r6.mCenterPoint
            android.graphics.Point r3 = new android.graphics.Point
            float r4 = r7.getX()
            int r4 = (int) r4
            float r7 = r7.getY()
            int r7 = (int) r7
            r3.<init>(r4, r7)
            int r7 = r6.bgWidth
            int r4 = r6.annularWidth
            int r7 = r7 - r4
            int r7 = r7 / r2
            android.graphics.Point r7 = com.ltech.smarthome.utils.ExtUtils.getBorderPoint(r0, r3, r7)
            r6.mPickPoint = r7
            com.ltech.smarthome.view.AnnularColorPickView2$IColorChangeListener r7 = r6.mOnColorChangedListener
            if (r7 == 0) goto Ld3
            android.graphics.Bitmap r0 = r6.mBitmapBack
            if (r0 == 0) goto Ld3
            int r0 = r6.getBitmapPixel()
            float r2 = r6.getCurProgress()
            r7.onColorChangedFinish(r0, r2)
            goto Ld3
        L77:
            float r0 = r7.getX()
            float r3 = r7.getY()
            android.graphics.Point r4 = r6.mCenterPoint
            int r4 = r4.x
            float r4 = (float) r4
            android.graphics.Point r5 = r6.mCenterPoint
            int r5 = r5.y
            float r5 = (float) r5
            int r0 = com.ltech.smarthome.utils.ExtUtils.getLength(r0, r3, r4, r5)
            float r0 = (float) r0
            int r3 = r6.bgWidth
            float r4 = (float) r3
            r5 = 1073741824(0x40000000, float:2.0)
            float r4 = r4 / r5
            int r4 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r4 >= 0) goto Ld7
            float r3 = (float) r3
            float r3 = r3 / r5
            int r4 = r6.annularWidth
            float r4 = (float) r4
            float r3 = r3 - r4
            int r0 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r0 > 0) goto La3
            goto Ld7
        La3:
            android.graphics.Point r0 = r6.mCenterPoint
            android.graphics.Point r3 = new android.graphics.Point
            float r4 = r7.getX()
            int r4 = (int) r4
            float r7 = r7.getY()
            int r7 = (int) r7
            r3.<init>(r4, r7)
            int r7 = r6.bgWidth
            int r4 = r6.annularWidth
            int r7 = r7 - r4
            int r7 = r7 / r2
            android.graphics.Point r7 = com.ltech.smarthome.utils.ExtUtils.getBorderPoint(r0, r3, r7)
            r6.mPickPoint = r7
            com.ltech.smarthome.view.AnnularColorPickView2$IColorChangeListener r7 = r6.mOnColorChangedListener
            if (r7 == 0) goto Ld3
            android.graphics.Bitmap r0 = r6.mBitmapBack
            if (r0 == 0) goto Ld3
            int r0 = r6.getBitmapPixel()
            float r2 = r6.getCurProgress()
            r7.onColorChanged(r0, r2)
        Ld3:
            r6.postInvalidate()
            return r1
        Ld7:
            r7 = 0
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.view.AnnularColorPickView2.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private void attemptClaimDrag() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    private void drawPicker(Canvas canvas) {
        if (this.mPickPoint.x < 0 || this.mPickPoint.y < 0) {
            return;
        }
        this.mPaint.setShadowLayer(2.0f, 0.0f, 0.0f, -7829368);
        this.mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(this.mPickPoint.x, this.mPickPoint.y, (this.annularWidth / 2.0f) * 0.9f, this.mPaint);
        this.mPaint.setStrokeWidth(12.0f);
        this.mPaint.setColor(this.pickerColor);
        canvas.drawCircle(this.mPickPoint.x, this.mPickPoint.y, (this.annularWidth / 8.0f) * 3.0f, this.mPaint);
    }

    private void drawCenter(Canvas canvas) {
        this.mPaint.setColor(this.centerColor);
        canvas.drawCircle(this.mCenterPoint.x, this.mCenterPoint.y, (this.bgWidth / 2.0f) - this.annularWidth, this.mPaint);
    }

    private void drawBg(Canvas canvas) {
        if (this.mBitmapBack != null) {
            canvas.drawCircle(this.mCenterPoint.x, this.mCenterPoint.y, this.bgWidth / 2.0f, this.mBgPaint);
        }
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

    public float getCurProgress() {
        return (float) ((((float) ((ExtUtils.getRadian(this.mCenterPoint, this.mPickPoint) >= 0.0f && this.mCenterPoint.x >= this.mPickPoint.x) ? r0 - 1.5707963267948966d : r0 + 4.71238898038469d)) * 100.0f) / 6.283185307179586d);
    }

    public void setBitmapBack(final Bitmap bitmap) {
        postDelayed(new Runnable() { // from class: com.ltech.smarthome.view.AnnularColorPickView2$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AnnularColorPickView2.this.lambda$setBitmapBack$0(bitmap);
            }
        }, (this.bgWidth == 0 || this.bgHeight == 0) ? 100L : 0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setBitmapBack$0(Bitmap bitmap) {
        if (bitmap != null) {
            if (bitmap.getWidth() == this.bgWidth && bitmap.getHeight() == this.bgHeight) {
                this.mBitmapBack = bitmap;
            } else {
                this.mBitmapBack = Bitmap.createScaledBitmap(bitmap, this.bgWidth, this.bgHeight, true);
            }
            BitmapShader bitmapShader = new BitmapShader(this.mBitmapBack, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            this.mBitmapShader = bitmapShader;
            this.mBgPaint.setShader(bitmapShader);
            updateShaderMatrix();
            postInvalidate();
        }
    }

    public void setOnColorChangedListener(IColorChangeListener mOnColorChangedListener) {
        this.mOnColorChangedListener = mOnColorChangedListener;
    }
}