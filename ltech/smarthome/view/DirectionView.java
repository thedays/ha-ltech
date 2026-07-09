package com.ltech.smarthome.view;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import com.ltech.smarthome.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class DirectionView extends LinearLayout {
    private Point centerPoint;
    private int circleWidth;
    private IDirectionClickListener mIDirectionClickListener;
    private AppCompatImageView mIvClick;

    public interface IDirectionClickListener {

        @Retention(RetentionPolicy.SOURCE)
        public @interface Direction {
            public static final int CENTER = 0;
            public static final int DOWN = 2;
            public static final int LEFT = 3;
            public static final int RIGHT = 4;
            public static final int UP = 1;
        }

        void onDirection(int direction);
    }

    public DirectionView(Context context) {
        this(context, null);
    }

    public DirectionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DirectionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mIvClick = (AppCompatImageView) LayoutInflater.from(context).inflate(R.layout.layout_hand_click, (ViewGroup) this, true).findViewById(R.id.iv_click);
        this.centerPoint = new Point();
        setDefault();
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.centerPoint.x = getMeasuredWidth() / 2;
        this.centerPoint.y = getMeasuredHeight() / 2;
        this.circleWidth = getMeasuredWidth() / 6;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == 0) {
            setImageAction((int) event.getX(), (int) event.getY());
            return true;
        }
        if (action == 1) {
            setDefault();
            return true;
        }
        return super.onTouchEvent(event);
    }

    private void setDefault() {
        this.mIvClick.setBackgroundResource(R.mipmap.icon_direction);
    }

    private void setImageAction(int x, int y) {
        float f = this.centerPoint.x - x;
        float f2 = this.centerPoint.y - y;
        if (((float) Math.sqrt((f * f) + (f2 * f2))) <= this.circleWidth) {
            this.mIvClick.setBackgroundResource(R.mipmap.icon_direction_ok);
            action(0);
            return;
        }
        float acos = (float) (((((float) Math.acos(f / r0)) * 180.0f) / 3.141592653589793d) * (this.centerPoint.y < y ? -1 : 1));
        if (acos >= -60.0f && acos < 60.0f) {
            this.mIvClick.setBackgroundResource(R.mipmap.icon_direction_left);
            action(3);
            return;
        }
        if (acos >= 60.0f && acos < 120.0f) {
            this.mIvClick.setBackgroundResource(R.mipmap.icon_direction_up);
            action(1);
        } else if (acos >= -120.0f && acos < -60.0f) {
            this.mIvClick.setBackgroundResource(R.mipmap.icon_direction_down);
            action(2);
        } else {
            this.mIvClick.setBackgroundResource(R.mipmap.icon_direction_right);
            action(4);
        }
    }

    private void action(int direction) {
        IDirectionClickListener iDirectionClickListener = this.mIDirectionClickListener;
        if (iDirectionClickListener != null) {
            iDirectionClickListener.onDirection(direction);
        }
    }

    public void setDirectionClickListener(IDirectionClickListener mIDirectionClickListener) {
        this.mIDirectionClickListener = mIDirectionClickListener;
    }
}