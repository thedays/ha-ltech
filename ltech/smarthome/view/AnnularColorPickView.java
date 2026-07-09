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
public class AnnularColorPickView extends View {
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

    public AnnularColorPickView(Context context) {
        this(context, null);
    }

    public AnnularColorPickView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnnularColorPickView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        Paint paint2 = new Paint();
        this.mPaint = paint2;
        paint2.setAntiAlias(true);
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
        this.mCenterPoint = new Point(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        if (this.annularWidth == 0) {
            this.annularWidth = this.bgWidth >> 3;
        }
        int measuredWidth = getMeasuredWidth();
        float f = ((measuredWidth - r2) / 2.0f) + this.bgWidth;
        int measuredHeight = getMeasuredHeight();
        this.mDrawableRect.set(new RectF((getMeasuredWidth() - this.bgWidth) / 2.0f, (getMeasuredHeight() - this.bgHeight) / 2.0f, f, ((measuredHeight - r3) / 2.0f) + this.bgHeight));
        if (this.bitmapBackResId != 0) {
            this.mBitmapBack = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), this.bitmapBackResId), this.bgWidth, this.bgHeight, false);
        }
        BitmapShader bitmapShader = new BitmapShader(this.mBitmapBack, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        this.mBitmapShader = bitmapShader;
        this.mBgPaint.setShader(bitmapShader);
        updateShaderMatrix();
        this.mPickPoint = new Point(-100, -100);
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
        drawBg(canvas);
        drawCenter(canvas);
        drawPicker(canvas);
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0010, code lost:
    
        if (r0 != 3) goto L30;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            r5.attemptClaimDrag()
            int r0 = r6.getAction()
            r1 = 1
            if (r0 == 0) goto L77
            if (r0 == r1) goto L46
            r2 = 2
            if (r0 == r2) goto L14
            r2 = 3
            if (r0 == r2) goto L46
            goto Lca
        L14:
            android.graphics.Point r0 = r5.mCenterPoint
            android.graphics.Point r2 = new android.graphics.Point
            float r3 = r6.getX()
            int r3 = (int) r3
            float r6 = r6.getY()
            int r6 = (int) r6
            r2.<init>(r3, r6)
            int r6 = r5.bgWidth
            int r3 = r5.annularWidth
            int r6 = r6 - r3
            int r6 = r6 >> r1
            android.graphics.Point r6 = com.ltech.smarthome.utils.ExtUtils.getBorderPoint(r0, r2, r6)
            r5.mPickPoint = r6
            com.ltech.smarthome.view.AnnularColorPickView$IColorChangeListener r6 = r5.mOnColorChangedListener
            if (r6 == 0) goto Lca
            android.graphics.Bitmap r0 = r5.mBitmapBack
            if (r0 == 0) goto Lca
            int r0 = r5.getBitmapPixel()
            float r2 = r5.getCurProgress()
            r6.onColorChanged(r0, r2)
            goto Lca
        L46:
            android.graphics.Point r0 = r5.mCenterPoint
            android.graphics.Point r2 = new android.graphics.Point
            float r3 = r6.getX()
            int r3 = (int) r3
            float r6 = r6.getY()
            int r6 = (int) r6
            r2.<init>(r3, r6)
            int r6 = r5.bgWidth
            int r3 = r5.annularWidth
            int r6 = r6 - r3
            int r6 = r6 >> r1
            android.graphics.Point r6 = com.ltech.smarthome.utils.ExtUtils.getBorderPoint(r0, r2, r6)
            r5.mPickPoint = r6
            com.ltech.smarthome.view.AnnularColorPickView$IColorChangeListener r6 = r5.mOnColorChangedListener
            if (r6 == 0) goto Lca
            android.graphics.Bitmap r0 = r5.mBitmapBack
            if (r0 == 0) goto Lca
            int r0 = r5.getBitmapPixel()
            float r2 = r5.getCurProgress()
            r6.onColorChangedFinish(r0, r2)
            goto Lca
        L77:
            float r0 = r6.getX()
            float r2 = r6.getY()
            android.graphics.Point r3 = r5.mCenterPoint
            int r3 = r3.x
            float r3 = (float) r3
            android.graphics.Point r4 = r5.mCenterPoint
            int r4 = r4.y
            float r4 = (float) r4
            int r0 = com.ltech.smarthome.utils.ExtUtils.getLength(r0, r2, r3, r4)
            int r2 = r5.bgWidth
            int r3 = r2 >> 1
            if (r0 >= r3) goto Lce
            int r2 = r2 >> r1
            int r3 = r5.annularWidth
            int r2 = r2 - r3
            if (r0 > r2) goto L9a
            goto Lce
        L9a:
            android.graphics.Point r0 = r5.mCenterPoint
            android.graphics.Point r2 = new android.graphics.Point
            float r3 = r6.getX()
            int r3 = (int) r3
            float r6 = r6.getY()
            int r6 = (int) r6
            r2.<init>(r3, r6)
            int r6 = r5.bgWidth
            int r3 = r5.annularWidth
            int r6 = r6 - r3
            int r6 = r6 >> r1
            android.graphics.Point r6 = com.ltech.smarthome.utils.ExtUtils.getBorderPoint(r0, r2, r6)
            r5.mPickPoint = r6
            com.ltech.smarthome.view.AnnularColorPickView$IColorChangeListener r6 = r5.mOnColorChangedListener
            if (r6 == 0) goto Lca
            android.graphics.Bitmap r0 = r5.mBitmapBack
            if (r0 == 0) goto Lca
            int r0 = r5.getBitmapPixel()
            float r2 = r5.getCurProgress()
            r6.onColorChanged(r0, r2)
        Lca:
            r5.postInvalidate()
            return r1
        Lce:
            r6 = 0
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.view.AnnularColorPickView.onTouchEvent(android.view.MotionEvent):boolean");
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
        this.mPaint.setColor(this.pickerColor);
        this.mPaint.setShadowLayer(5.0f, 0.0f, 0.0f, -7829368);
        canvas.drawCircle(this.mPickPoint.x, this.mPickPoint.y, this.annularWidth >> 1, this.mPaint);
        this.mPaint.setColor(getBitmapPixel());
        this.mPaint.setShadowLayer(0.0f, 0.0f, 0.0f, -7829368);
        canvas.drawCircle(this.mPickPoint.x, this.mPickPoint.y, (this.annularWidth >> 3) * 3, this.mPaint);
    }

    private void drawCenter(Canvas canvas) {
        this.mPaint.setColor(this.centerColor);
        canvas.drawCircle(this.mCenterPoint.x, this.mCenterPoint.y, (this.bgWidth >> 1) - this.annularWidth, this.mPaint);
    }

    private void drawBg(Canvas canvas) {
        if (this.mBitmapBack != null) {
            canvas.drawCircle(this.mCenterPoint.x, this.mCenterPoint.y, this.bgWidth >> 1, this.mBgPaint);
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
        postDelayed(new Runnable() { // from class: com.ltech.smarthome.view.AnnularColorPickView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AnnularColorPickView.this.lambda$setBitmapBack$0(bitmap);
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