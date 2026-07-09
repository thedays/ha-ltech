package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class StateButton extends LinearLayout {
    private Context mContext;
    private OnClickCallback mOnClickCallback;
    TextView mTvOff;
    TextView mTvOn;
    private String offText;
    private String onText;
    private int textOffDefaultBg;
    private int textOffDefaultColor;
    private int textOffSelectBg;
    private int textOffSelectColor;
    private int textOnDefaultBg;
    private int textOnDefaultColor;
    private int textOnSelectBg;
    private int textOnSelectColor;

    public interface OnClickCallback {
        void off();

        void on();
    }

    public StateButton(Context context) {
        super(context, null);
    }

    public StateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.StateButton);
        this.textOffSelectColor = obtainStyledAttributes.getColor(4, ContextCompat.getColor(this.mContext, R.color.white));
        this.textOffDefaultColor = obtainStyledAttributes.getColor(3, ContextCompat.getColor(this.mContext, R.color.color_blue));
        this.textOnSelectColor = obtainStyledAttributes.getColor(9, ContextCompat.getColor(this.mContext, R.color.white));
        this.textOnDefaultColor = obtainStyledAttributes.getColor(8, ContextCompat.getColor(this.mContext, R.color.color_blue));
        this.textOffDefaultBg = obtainStyledAttributes.getResourceId(0, R.drawable.shape_state_button_4);
        this.textOffSelectBg = obtainStyledAttributes.getResourceId(1, R.drawable.shape_state_button_3);
        this.textOnDefaultBg = obtainStyledAttributes.getResourceId(5, R.drawable.shape_state_button_2);
        this.textOnSelectBg = obtainStyledAttributes.getResourceId(6, R.drawable.shape_state_button_1);
        this.onText = obtainStyledAttributes.getString(7);
        this.offText = obtainStyledAttributes.getString(2);
        obtainStyledAttributes.recycle();
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_state_button, (ViewGroup) this, true);
        this.mTvOff = (TextView) inflate.findViewById(R.id.tv_off);
        this.mTvOn = (TextView) inflate.findViewById(R.id.tv_on);
        this.mTvOff.setText(this.offText);
        this.mTvOn.setText(this.onText);
        this.mTvOn.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.view.StateButton$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                StateButton.this.lambda$new$0(view);
            }
        });
        this.mTvOff.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.view.StateButton$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                StateButton.this.lambda$new$1(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        setOnOffState(true);
        OnClickCallback onClickCallback = this.mOnClickCallback;
        if (onClickCallback != null) {
            onClickCallback.on();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(View view) {
        setOnOffState(false);
        OnClickCallback onClickCallback = this.mOnClickCallback;
        if (onClickCallback != null) {
            onClickCallback.off();
        }
    }

    public void setOnOffState(boolean isOn) {
        if (isOn) {
            this.mTvOff.setTextColor(this.textOffDefaultColor);
            this.mTvOn.setTextColor(this.textOnSelectColor);
            this.mTvOff.setBackgroundResource(this.textOffDefaultBg);
            this.mTvOn.setBackgroundResource(this.textOnSelectBg);
            return;
        }
        this.mTvOff.setTextColor(this.textOffSelectColor);
        this.mTvOn.setTextColor(this.textOnDefaultColor);
        this.mTvOff.setBackgroundResource(this.textOffSelectBg);
        this.mTvOn.setBackgroundResource(this.textOnDefaultBg);
    }

    public void setOnClickCallback(OnClickCallback onClickCallback) {
        this.mOnClickCallback = onClickCallback;
    }
}