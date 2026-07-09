package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.ltech.smarthome.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ColorPickerPointView extends View {
    private static final int LONG_PRESS_TIMEOUT = 500;
    private Bitmap bpDropBlackContent;
    private Bitmap bpDropBlackContentReverse;
    private Bitmap bpDropframe;
    private Bitmap bpDropframeRervese;
    private float centerX;
    private float centerY;
    private Bitmap circleBitmap;
    private OnColorChangedListener colorChangedListener;
    private float color_wheel_radius;
    private Paint dashLinePaint;
    Paint dropBlackContentPaint;
    Paint dropFramPaint;
    private float drop_height;
    private float drop_width;
    private Paint fillDropPaint;
    private GestureDetector gestureDetector;
    private Handler handler;
    private Paint hollowDropPaint;
    private Paint hollowDropPaint1;
    private Paint hollowDropPaint2;
    private Bitmap iconBitmap;
    private float iconHeight;
    private Paint iconPaint;
    private float iconWidth;
    private boolean isDragging;
    private Boolean isTouchPointCircle;
    private Runnable longPressRunnable;
    private float point_line_interval;
    private float point_line_radius;
    private float point_radius;
    private float point_radius_click_offsize;
    private List<PointInfo> points;
    private int potentialSelectedIndex;
    private int selectedPointIndex;
    private Paint solidCirclePaint;
    private Bitmap srcBgbitmap;

    public interface OnColorChangedListener {
        void onChangedFinish(int color, PointInfo pointInfo, int selectedPointIndex);

        void onColorChanged(int color, PointInfo pointInfo, int selectedPointIndex);

        void onColorStarted(int color, PointInfo pointInfo, int selectedIndex);

        void onSelectedPosition(PointInfo pointInfo, int selectedPointIndex);
    }

    static /* synthetic */ void lambda$init$0() {
    }

    public ColorPickerPointView(Context context) {
        super(context, null);
        this.points = new ArrayList();
        this.selectedPointIndex = -1;
        this.isDragging = false;
        this.isTouchPointCircle = false;
        this.handler = new Handler(Looper.getMainLooper());
        this.potentialSelectedIndex = -1;
        this.point_radius_click_offsize = 10.0f;
        init(context);
        initGestureDetector(context);
    }

    public ColorPickerPointView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        this.points = new ArrayList();
        this.selectedPointIndex = -1;
        this.isDragging = false;
        this.isTouchPointCircle = false;
        this.handler = new Handler(Looper.getMainLooper());
        this.potentialSelectedIndex = -1;
        this.point_radius_click_offsize = 10.0f;
        initConfig(context, attrs);
        init(context);
        initGestureDetector(context);
    }

    public ColorPickerPointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.points = new ArrayList();
        this.selectedPointIndex = -1;
        this.isDragging = false;
        this.isTouchPointCircle = false;
        this.handler = new Handler(Looper.getMainLooper());
        this.potentialSelectedIndex = -1;
        this.point_radius_click_offsize = 10.0f;
        initConfig(context, attrs);
        init(context);
        initGestureDetector(context);
    }

    private void initGestureDetector(Context context) {
        this.gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(this) { // from class: com.ltech.smarthome.view.ColorPickerPointView.1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public void onLongPress(MotionEvent e) {
            }
        });
    }

    private void initConfig(Context context, AttributeSet attrs) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ColorPickerPointView);
        try {
            this.point_radius = obtainStyledAttributes.getDimension(6, 16.0f);
            this.drop_width = obtainStyledAttributes.getDimension(1, 100.0f);
            this.drop_height = obtainStyledAttributes.getDimension(0, 130.0f);
            this.point_line_radius = obtainStyledAttributes.getDimension(5, 4.0f);
            this.point_line_interval = obtainStyledAttributes.getDimension(4, 12.0f);
            this.iconWidth = obtainStyledAttributes.getDimension(3, 24.0f);
            this.iconHeight = obtainStyledAttributes.getDimension(2, 24.0f);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    private void init(Context context) {
        this.srcBgbitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_palette_1);
        Paint paint = new Paint(1);
        this.solidCirclePaint = paint;
        paint.setColor(-1);
        this.solidCirclePaint.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint(1);
        this.hollowDropPaint = paint2;
        paint2.setColor(-16777216);
        this.hollowDropPaint.setStyle(Paint.Style.STROKE);
        this.hollowDropPaint.setStrokeWidth(2.0f);
        this.hollowDropPaint.setAlpha(3);
        Paint paint3 = new Paint(1);
        this.hollowDropPaint1 = paint3;
        paint3.setColor(-16777216);
        this.hollowDropPaint1.setStyle(Paint.Style.STROKE);
        this.hollowDropPaint1.setStrokeWidth(4.0f);
        this.hollowDropPaint1.setAlpha(3);
        Paint paint4 = new Paint(1);
        this.hollowDropPaint2 = paint4;
        paint4.setColor(-16777216);
        this.hollowDropPaint2.setStyle(Paint.Style.STROKE);
        this.hollowDropPaint2.setStrokeWidth(6.0f);
        this.hollowDropPaint2.setAlpha(3);
        Paint paint5 = new Paint(1);
        this.fillDropPaint = paint5;
        paint5.setStyle(Paint.Style.FILL);
        this.iconPaint = new Paint(1);
        Paint paint6 = new Paint(1);
        this.dashLinePaint = paint6;
        paint6.setColor(-1);
        this.dashLinePaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.addCircle(0.0f, 0.0f, this.point_line_radius, Path.Direction.CW);
        this.dashLinePaint.setPathEffect(new PathDashPathEffect(path, this.point_line_interval, 0.0f, PathDashPathEffect.Style.TRANSLATE));
        this.longPressRunnable = new Runnable() { // from class: com.ltech.smarthome.view.ColorPickerPointView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ColorPickerPointView.lambda$init$0();
            }
        };
        this.dropFramPaint = new Paint(1);
        this.dropBlackContentPaint = new Paint(1);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float min = Math.min(w, h) / 2;
        this.color_wheel_radius = min;
        this.centerX = min;
        this.centerY = min;
        createCircleBitmap();
    }

    public void createCircleBitmap() {
        if (this.srcBgbitmap == null) {
            return;
        }
        float f = this.color_wheel_radius;
        this.circleBitmap = Bitmap.createBitmap((int) (f * 2.0f), (int) (f * 2.0f), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(this.circleBitmap);
        Paint paint = new Paint(1);
        BitmapShader bitmapShader = new BitmapShader(this.srcBgbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix matrix = new Matrix();
        float min = Math.min((this.color_wheel_radius * 2.0f) / this.srcBgbitmap.getWidth(), (this.color_wheel_radius * 2.0f) / this.srcBgbitmap.getHeight());
        matrix.setScale(min, min);
        bitmapShader.setLocalMatrix(matrix);
        paint.setShader(bitmapShader);
        float f2 = this.color_wheel_radius;
        canvas.drawCircle(f2, f2, f2, paint);
        initData(this.srcBgbitmap, this.points);
    }

    public float getPoint_radius() {
        return this.point_radius;
    }

    public void setPoint_radius(float point_radius) {
        this.point_radius = point_radius;
    }

    public float getDrop_width() {
        return this.drop_width;
    }

    public void setDrop_width(float drop_width) {
        this.drop_width = drop_width;
    }

    public float getDrop_height() {
        return this.drop_height;
    }

    public void setDrop_height(float drop_height) {
        this.drop_height = drop_height;
    }

    public float getPoint_line_radius() {
        return this.point_line_radius;
    }

    public void setPoint_line_radius(float point_line_radius) {
        this.point_line_radius = point_line_radius;
    }

    public float getPoint_line_interval() {
        return this.point_line_interval;
    }

    public void setPoint_line_interval(float point_line_interval) {
        this.point_line_interval = point_line_interval;
    }

    public float getIconWidth() {
        return this.iconWidth;
    }

    public void setIconWidth(float iconWidth) {
        this.iconWidth = iconWidth;
    }

    public float getIconHeight() {
        return this.iconHeight;
    }

    public void setIconHeight(float iconHeight) {
        this.iconHeight = iconHeight;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = this.circleBitmap;
        if (bitmap != null) {
            float f = this.centerX;
            float f2 = this.color_wheel_radius;
            canvas.drawBitmap(bitmap, f - f2, this.centerY - f2, (Paint) null);
        }
        for (int i = 0; i < this.points.size(); i++) {
            PointInfo pointInfo = this.points.get(i);
            if (i != this.selectedPointIndex) {
                canvas.drawCircle(pointInfo.x, pointInfo.y, this.point_radius, this.solidCirclePaint);
                canvas.drawCircle(pointInfo.x, pointInfo.y, this.point_radius, this.hollowDropPaint);
                canvas.drawCircle(pointInfo.x, pointInfo.y, this.point_radius, this.hollowDropPaint1);
                canvas.drawCircle(pointInfo.x, pointInfo.y, this.point_radius, this.hollowDropPaint2);
                Log.e("onDraw", "x=" + pointInfo.x + "__y=" + pointInfo.y + "___radius:" + this.color_wheel_radius + "__centrx:" + this.centerX + "__centy:" + this.centerY);
            }
        }
        drawDashLines(canvas);
        int i2 = this.selectedPointIndex;
        if (i2 < 0 || i2 >= this.points.size()) {
            return;
        }
        PointInfo pointInfo2 = this.points.get(this.selectedPointIndex);
        int colorAtPoint = getColorAtPoint(pointInfo2);
        if (this.isTouchPointCircle.booleanValue()) {
            drawColorCircle(canvas, pointInfo2.x, pointInfo2.y, colorAtPoint);
        } else {
            drawIcon(canvas, pointInfo2.x, pointInfo2.y, drawColoredDropIcon(canvas, pointInfo2.x, pointInfo2.y, colorAtPoint));
        }
    }

    public void setTouchPointCircle(Boolean b2) {
        this.isTouchPointCircle = b2;
    }

    private void drawIcon(Canvas canvas, float x, float y, int dirction) {
        Bitmap bitmap = this.iconBitmap;
        if (bitmap != null) {
            Bitmap resizedBitmap = getResizedBitmap(bitmap, this.iconWidth, this.iconHeight);
            float height = this.bpDropframe.getHeight() * 0.45f;
            float width = x - (resizedBitmap.getWidth() / 2.0f);
            float height2 = (y - (resizedBitmap.getHeight() / 2.0f)) - height;
            if (dirction == 1) {
                height2 = (y - (resizedBitmap.getHeight() / 2.0f)) + height;
            }
            canvas.drawBitmap(resizedBitmap, width, height2, this.iconPaint);
        }
    }

    private Bitmap getResizedBitmap(Bitmap bm, float newWidth, float newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(newWidth / width, newHeight / height);
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    }

    private int getColorAtPoint(PointInfo point) {
        if (this.srcBgbitmap != null && this.circleBitmap != null) {
            float f = point.x;
            float f2 = this.centerX;
            float f3 = this.color_wheel_radius;
            float width = ((f - (f2 - f3)) / (f3 * 2.0f)) * this.srcBgbitmap.getWidth();
            float f4 = point.y;
            float f5 = this.centerY;
            float f6 = this.color_wheel_radius;
            float height = ((f4 - (f5 - f6)) / (f6 * 2.0f)) * this.srcBgbitmap.getHeight();
            if (width >= 0.0f && width < this.srcBgbitmap.getWidth() && height >= 0.0f && height < this.srcBgbitmap.getHeight()) {
                return this.srcBgbitmap.getPixel((int) width, (int) height);
            }
        }
        return -1;
    }

    private void drawDashLines(Canvas canvas) {
        int i = 0;
        while (i < this.points.size() - 1) {
            PointInfo pointInfo = this.points.get(i);
            i++;
            drawCurvedLine(canvas, pointInfo, this.points.get(i));
        }
    }

    private void drawCurvedLine(Canvas canvas, PointInfo p1, PointInfo p2) {
        Path path = new Path();
        path.moveTo(p1.x, p1.y);
        float f = this.color_wheel_radius;
        float[] calculateIntersectionPoint = calculateIntersectionPoint(f, f, f, p1.x, p1.y, p2.x, p2.y);
        if (calculateIntersectionPoint != null) {
            path.quadTo(calculateIntersectionPoint[0], calculateIntersectionPoint[1], p2.x, p2.y);
            canvas.drawPath(path, this.dashLinePaint);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        int i;
        this.gestureDetector.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        if (action == 0) {
            this.isDragging = true;
            int findPointIndex = findPointIndex(x, y);
            this.potentialSelectedIndex = findPointIndex;
            if (findPointIndex != -1) {
                setSelectedPointIndex(findPointIndex);
                OnColorChangedListener onColorChangedListener = this.colorChangedListener;
                if (onColorChangedListener != null) {
                    onColorChangedListener.onSelectedPosition(this.points.get(this.potentialSelectedIndex), this.potentialSelectedIndex);
                }
            }
            if (this.potentialSelectedIndex == -1 && (i = this.selectedPointIndex) > -1) {
                this.potentialSelectedIndex = i;
                updatePointPosition(i, x, y);
                invalidate();
                onColorChange(event.getAction());
            }
            return true;
        }
        if (action != 1) {
            if (action == 2) {
                if (this.isDragging) {
                    this.handler.removeCallbacks(this.longPressRunnable);
                    int i2 = this.potentialSelectedIndex;
                    if (i2 != -1) {
                        updatePointPosition(i2, x, y);
                        invalidate();
                        onColorChange(event.getAction());
                    }
                }
                return true;
            }
            if (action != 3) {
                return super.onTouchEvent(event);
            }
        }
        this.handler.removeCallbacks(this.longPressRunnable);
        if (this.isDragging) {
            this.isDragging = false;
            if (this.potentialSelectedIndex != -1) {
                onColorChange(event.getAction());
            }
        }
        this.potentialSelectedIndex = -1;
        return true;
    }

    private int findPointIndex(float x, float y) {
        for (int i = 0; i < this.points.size(); i++) {
            PointInfo pointInfo = this.points.get(i);
            float f = x - pointInfo.x;
            float f2 = y - pointInfo.y;
            if (((float) Math.sqrt((f * f) + (f2 * f2))) <= this.point_radius + this.point_radius_click_offsize) {
                return i;
            }
        }
        return -1;
    }

    private void onColorChange(int action) {
        int i;
        if (this.colorChangedListener == null || (i = this.potentialSelectedIndex) == -1) {
            return;
        }
        PointInfo pointInfo = this.points.get(i);
        int colorAtPoint = getColorAtPoint(pointInfo);
        if (action == 0) {
            this.colorChangedListener.onColorStarted(colorAtPoint, pointInfo, this.potentialSelectedIndex);
        } else if (action == 2) {
            this.colorChangedListener.onColorChanged(colorAtPoint, pointInfo, this.potentialSelectedIndex);
        } else {
            this.colorChangedListener.onChangedFinish(colorAtPoint, pointInfo, this.potentialSelectedIndex);
        }
    }

    private void updatePointPosition(int index, float touchX, float touchY) {
        PointInfo pointInfo = this.points.get(index);
        float f = touchX - this.centerX;
        float f2 = touchY - this.centerY;
        float sqrt = (float) Math.sqrt((f * f) + (f2 * f2));
        float f3 = this.color_wheel_radius;
        if (sqrt > f3 * 0.95f) {
            float f4 = (f3 * 0.95f) / sqrt;
            f *= f4;
            f2 *= f4;
        }
        pointInfo.x = this.centerX + f;
        pointInfo.y = this.centerY + f2;
    }

    public void setSelectedPointIndex(int index) {
        if (index < 0 || index >= this.points.size()) {
            return;
        }
        this.selectedPointIndex = index;
        this.iconBitmap = BitmapFactory.decodeResource(getResources(), this.points.get(index).selectIcon);
        invalidate();
    }

    public int getSelectedColor() {
        int i = this.selectedPointIndex;
        if (i < 0 || i >= this.points.size()) {
            return -1;
        }
        return getColorAtPoint(this.points.get(this.selectedPointIndex));
    }

    public void setOnColorChangedListener(OnColorChangedListener listener) {
        this.colorChangedListener = listener;
    }

    public static class PointInfo {
        public boolean isSelected;
        public int selectIcon;
        public float x;
        public float y;

        public PointInfo(float x, float y, int selectIcon, boolean isSelected) {
            this.x = x;
            this.y = y;
            this.isSelected = isSelected;
            this.selectIcon = selectIcon;
        }
    }

    private int drawColoredDrop(Canvas canvas, float x, float y, int color) {
        int i;
        this.fillDropPaint.setColor(color);
        float f = this.drop_width;
        float f2 = this.drop_height;
        Path path = new Path();
        float f3 = y - f2;
        if (f3 > 0.0f) {
            path.moveTo(x, f3);
            float f4 = 0.72f * f;
            float f5 = y - (0.9f * f2);
            float f6 = f * 0.25f;
            float f7 = x - f6;
            float f8 = y - (f2 * 0.25f);
            path.quadTo(x - f4, f5, f7, f8);
            path.quadTo(f7, f8, x, y);
            path.quadTo(x, y, f6 + x, f8);
            path.quadTo(f4 + x, f5, x, f3);
            i = 0;
        } else {
            float f9 = y + f2;
            path.moveTo(x, f9);
            float f10 = 0.72f * f;
            float f11 = (0.9f * f2) + y;
            float f12 = f * 0.25f;
            float f13 = x - f12;
            float f14 = (f2 * 0.25f) + y;
            path.quadTo(x - f10, f11, f13, f14);
            path.quadTo(f13, f14, x, y);
            path.quadTo(x, y, f12 + x, f14);
            path.quadTo(f10 + x, f11, x, f9);
            i = 1;
        }
        path.close();
        canvas.drawPath(path, this.fillDropPaint);
        drawShade(canvas, path);
        return i;
    }

    private void drawColorCircle(Canvas canvas, float x, float y, int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(-1);
        paint.setShadowLayer(15.0f, 0.0f, 0.0f, -7829368);
        this.fillDropPaint.setColor(color);
        canvas.drawCircle(x, y, getWidth() / 30.0f, paint);
        if (x < 0.0f || y < 0.0f) {
            return;
        }
        canvas.drawCircle(x, y, (getWidth() / 30.0f) - 4.0f, this.fillDropPaint);
    }

    private void drawShade(Canvas canvas, Path path) {
        canvas.drawPath(path, this.hollowDropPaint);
        canvas.drawPath(path, this.hollowDropPaint1);
        canvas.drawPath(path, this.hollowDropPaint2);
    }

    public void updateCircleBg(Bitmap colorBg) {
        this.srcBgbitmap = colorBg;
        createCircleBitmap();
        invalidate();
    }

    public void initData(List<PointInfo> points) {
        this.points = points;
        for (int i = 0; i < points.size(); i++) {
            PointInfo pointInfo = points.get(i);
            if (pointInfo.x == 0.0f || pointInfo.y == 0.0f) {
                pointInfo.x = this.color_wheel_radius;
                pointInfo.y = this.color_wheel_radius;
            }
            if (pointInfo.isSelected) {
                this.selectedPointIndex = i;
                this.iconBitmap = BitmapFactory.decodeResource(getResources(), pointInfo.selectIcon);
            }
        }
        invalidate();
    }

    public void initData(Bitmap colorBg, List<PointInfo> points) {
        if (colorBg == null) {
            return;
        }
        this.srcBgbitmap = colorBg;
        this.points = points;
        for (int i = 0; i < points.size(); i++) {
            PointInfo pointInfo = points.get(i);
            if (pointInfo.x == 0.0f || pointInfo.y == 0.0f) {
                pointInfo.x = this.color_wheel_radius;
                pointInfo.y = this.color_wheel_radius;
            }
            if (pointInfo.isSelected) {
                this.selectedPointIndex = i;
                this.iconBitmap = BitmapFactory.decodeResource(getResources(), pointInfo.selectIcon);
            }
        }
        invalidate();
    }

    public void updateSelectedCard(int index) {
        List<PointInfo> list = this.points;
        if (list == null || index > list.size() - 1) {
            return;
        }
        for (int i = 0; i < this.points.size(); i++) {
            if (i == index) {
                this.points.get(i).isSelected = true;
                this.iconBitmap = BitmapFactory.decodeResource(getResources(), this.points.get(i).selectIcon);
                this.selectedPointIndex = i;
            } else {
                this.points.get(i).isSelected = false;
            }
        }
        invalidate();
    }

    public List<PointInfo> getPoints() {
        return this.points;
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View.MeasureSpec.getMode(widthMeasureSpec);
        super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(widthMeasureSpec), 1073741824));
    }

    public static float[] calculateIntersectionPoint(float cx, float cy, float radius, float p1X, float p1Y, float p2X, float p2Y) {
        if (calculateDistance(p1X, p1Y, p2X, p2Y) < 1.0E-8d) {
            return null;
        }
        float f = (p1X + p2X) / 2.0f;
        float f2 = (p1Y + p2Y) / 2.0f;
        float f3 = p2X - p1X;
        float f4 = p2Y - p1Y;
        float hypot = (float) Math.hypot(f3, f4);
        if (hypot > 1000.0d) {
            f3 /= hypot;
            f4 /= hypot;
            System.out.println("警告：垂直中线向量过长，已归一化处理");
        }
        float f5 = f3;
        float f6 = f4;
        System.out.printf("参数: p1=(%.5f, %.5f), p2=(%.5f, %.5f), mid=(%.5f, %.5f)\n", Float.valueOf(p1X), Float.valueOf(p1Y), Float.valueOf(p2X), Float.valueOf(p2Y), Float.valueOf(f), Float.valueOf(f2));
        System.out.printf("直线参数: A=%.10f, B=%.10f\n", Float.valueOf(f5), Float.valueOf(f6));
        float[] calculateLineCircleIntersectionGeometry = calculateLineCircleIntersectionGeometry(cx, cy, radius, f, f2, f5, f6);
        if (calculateLineCircleIntersectionGeometry == null) {
            return null;
        }
        float calculateDistance = calculateDistance(f, f2, calculateLineCircleIntersectionGeometry[0], calculateLineCircleIntersectionGeometry[1]);
        float calculateDistance2 = calculateDistance(f, f2, calculateLineCircleIntersectionGeometry[2], calculateLineCircleIntersectionGeometry[3]);
        if (Math.abs(calculateDistance - calculateDistance2) < 1.0f) {
            System.out.printf("控制点距离相同", new Object[0]);
            if (p1Y >= p2Y) {
                float f7 = calculateLineCircleIntersectionGeometry[1];
                float f8 = calculateLineCircleIntersectionGeometry[3];
                return f7 > f8 ? new float[]{calculateLineCircleIntersectionGeometry[0], f7} : new float[]{calculateLineCircleIntersectionGeometry[2], f8};
            }
            float f9 = calculateLineCircleIntersectionGeometry[1];
            float f10 = calculateLineCircleIntersectionGeometry[3];
            return f9 < f10 ? new float[]{calculateLineCircleIntersectionGeometry[0], f9} : new float[]{calculateLineCircleIntersectionGeometry[2], f10};
        }
        System.out.printf("交点1=(%.5f, %.5f), 距离=%.5f\n", Float.valueOf(calculateLineCircleIntersectionGeometry[0]), Float.valueOf(calculateLineCircleIntersectionGeometry[1]), Float.valueOf(calculateDistance));
        System.out.printf("交点2=(%.5f, %.5f), 距离=%.5f\n", Float.valueOf(calculateLineCircleIntersectionGeometry[2]), Float.valueOf(calculateLineCircleIntersectionGeometry[3]), Float.valueOf(calculateDistance2));
        if (calculateDistance <= calculateDistance2) {
            return new float[]{calculateLineCircleIntersectionGeometry[0], calculateLineCircleIntersectionGeometry[1]};
        }
        return new float[]{calculateLineCircleIntersectionGeometry[2], calculateLineCircleIntersectionGeometry[3]};
    }

    private static float[] calculateLineCircleIntersectionGeometry(float cx, float cy, float radius, float lineX, float lineY, float A, float B) {
        float hypot = (float) Math.hypot(A, B);
        float f = A / hypot;
        float f2 = B / hypot;
        float f3 = ((lineX - cx) * f) + ((lineY - cy) * f2);
        if (Math.abs(f3) > radius + 1.0E-6d) {
            System.out.printf("圆心到直线距离: %.10f > 半径=%.10f\n", Float.valueOf(f3), Float.valueOf(radius));
            return null;
        }
        float f4 = (f3 * f) + cx;
        float f5 = cy + (f2 * f3);
        float sqrt = (float) Math.sqrt(Math.max(0.0f, (radius * radius) - (f3 * f3)));
        float f6 = ((-B) / hypot) * sqrt;
        float f7 = f4 + f6;
        float f8 = sqrt * f;
        float f9 = f5 + f8;
        float f10 = f4 - f6;
        float f11 = f5 - f8;
        float hypot2 = ((float) Math.hypot(f7 - cx, f9 - cy)) - radius;
        float hypot3 = ((float) Math.hypot(f10 - cx, f11 - cy)) - radius;
        System.out.printf("交点1距离圆误差: %.10f\n", Float.valueOf(hypot2));
        System.out.printf("交点2距离圆误差: %.10f\n", Float.valueOf(hypot3));
        return new float[]{f7, f9, f10, f11};
    }

    private static float calculateDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.hypot(x2 - x1, y2 - y1);
    }

    private int drawColoredDropIcon(Canvas canvas, float x, float y, int color) {
        if (this.bpDropframe == null) {
            this.bpDropframe = BitmapFactory.decodeResource(getResources(), R.mipmap.gradient_drop);
        }
        if (this.bpDropBlackContent == null) {
            this.bpDropBlackContent = BitmapFactory.decodeResource(getResources(), R.mipmap.gradient_drop_black);
        }
        if (this.bpDropframeRervese == null) {
            this.bpDropframeRervese = BitmapFactory.decodeResource(getResources(), R.mipmap.gradient_drop_reverse);
        }
        if (this.bpDropBlackContentReverse == null) {
            this.bpDropBlackContentReverse = BitmapFactory.decodeResource(getResources(), R.mipmap.gradient_drop_black_reverse);
        }
        if (y - this.bpDropframe.getHeight() > 0.0f) {
            canvas.drawBitmap(this.bpDropframe, x - (r0.getWidth() / 2), (y - this.bpDropframe.getHeight()) + 10.0f, this.dropFramPaint);
            canvas.drawBitmap(replaceWithColorFilter(this.bpDropBlackContent, -16777216, color), x - (this.bpDropBlackContent.getWidth() / 2), (y - this.bpDropBlackContent.getHeight()) + 10.0f, this.dropBlackContentPaint);
            return 0;
        }
        float f = y - 10.0f;
        canvas.drawBitmap(this.bpDropframeRervese, x - (r0.getWidth() / 2), f, this.dropFramPaint);
        canvas.drawBitmap(replaceWithColorFilter(this.bpDropBlackContentReverse, -16777216, color), x - (this.bpDropBlackContentReverse.getWidth() / 2), f, this.dropBlackContentPaint);
        return 1;
    }

    public Bitmap replaceWithColorFilter(Bitmap source, int blackColor, int replacementColor) {
        float[] fArr = {1.0f, 0.0f, 0.0f, 0.0f, Color.red(replacementColor) - Color.red(blackColor), 0.0f, 1.0f, 0.0f, 0.0f, Color.green(replacementColor) - Color.green(blackColor), 0.0f, 0.0f, 1.0f, 0.0f, Color.blue(replacementColor) - Color.blue(blackColor), 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(fArr));
        Bitmap createBitmap = Bitmap.createBitmap(source.getWidth(), source.getHeight(), source.getConfig());
        new Canvas(createBitmap).drawBitmap(source, 0.0f, 0.0f, paint);
        return createBitmap;
    }
}