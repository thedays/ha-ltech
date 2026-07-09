package com.ltech.smarthome.ui.device.dalipro;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.SeekBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ViewCgdActionBinding;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.ui.device.light.CtControlHelper;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.view.ColorSeekBar;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes4.dex */
public class CgdActionSelectView extends ConstraintLayout {
    private CtControlHelper ctHelper;
    private OnDialogCallback mCallback;
    private Role mRole;
    ViewCgdActionBinding mViewBinding;
    private int rgbBrt;
    private int rgbColor;
    private int wy;
    private int wyBrt;

    public interface OnDialogCallback {
        void onBrtChanged(int progress, boolean finish);

        void onColorChanged(float xProgress, int color, boolean finish);

        void onCtChanged(int v, boolean finish);
    }

    public CgdActionSelectView(Context context) {
        this(context, null);
    }

    public CgdActionSelectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CgdActionSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.rgbColor = Integer.MIN_VALUE;
        this.mViewBinding = (ViewCgdActionBinding) DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_cgd_action, this, true);
        initBrt();
    }

    public void initAll(Role role) {
        initCt(role);
        initColor();
    }

    private void initBrt() {
        this.mViewBinding.sbBrt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.device.dalipro.CgdActionSelectView.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (CgdActionSelectView.this.mViewBinding == null) {
                    return;
                }
                CgdActionSelectView.this.wyBrt = LightUtils.progress2BrtHasBelowOne(progress);
                CgdActionSelectView.this.rgbBrt = LightUtils.progress2BrtHasBelowOne(progress);
                CgdActionSelectView.this.mViewBinding.tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                if (!fromUser || CgdActionSelectView.this.mCallback == null) {
                    return;
                }
                CgdActionSelectView.this.mCallback.onBrtChanged(progress, false);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (CgdActionSelectView.this.mViewBinding == null) {
                    return;
                }
                CgdActionSelectView.this.wyBrt = LightUtils.progress2BrtHasBelowOne(seekBar.getProgress());
                CgdActionSelectView.this.rgbBrt = LightUtils.progress2BrtHasBelowOne(seekBar.getProgress());
                CgdActionSelectView.this.mViewBinding.tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                if (CgdActionSelectView.this.mCallback != null) {
                    CgdActionSelectView.this.mCallback.onBrtChanged(seekBar.getProgress(), true);
                }
            }
        });
        this.mViewBinding.tvBrt.setText(this.mViewBinding.sbBrt.getProgressHasBelowOne());
    }

    private void initCt(Role role) {
        this.mViewBinding.groupCt.setVisibility(0);
        this.ctHelper = new CtControlHelper(this.mViewBinding.sbCt, this.mViewBinding.tvCtPercent, role, new CtControlHelper.OnCtChangedListener() { // from class: com.ltech.smarthome.ui.device.dalipro.CgdActionSelectView.2
            @Override // com.ltech.smarthome.ui.device.light.CtControlHelper.OnCtChangedListener
            public void onRangeChanged(int v, boolean finish) {
                CgdActionSelectView.this.wy = 255 - v;
                if (CgdActionSelectView.this.mCallback != null) {
                    CgdActionSelectView.this.mCallback.onCtChanged(v, finish);
                }
            }
        });
    }

    private void initColor() {
        this.mViewBinding.groupColor.setVisibility(0);
        this.mViewBinding.colorSeekbar.setOnColorChangedListener(new ColorSeekBar.OnColorChangedListener() { // from class: com.ltech.smarthome.ui.device.dalipro.CgdActionSelectView.3
            @Override // com.ltech.smarthome.view.ColorSeekBar.OnColorChangedListener
            public void onColorChangedStart() {
            }

            @Override // com.ltech.smarthome.view.ColorSeekBar.OnColorChangedListener
            public void onColorChanged(float xProgress, int color, boolean isFromUser) {
                CgdActionSelectView.this.mViewBinding.civColor.setImageDrawable(new ColorDrawable(color));
                CgdActionSelectView.this.rgbColor = color;
                if (!isFromUser || CgdActionSelectView.this.mCallback == null) {
                    return;
                }
                CgdActionSelectView.this.mCallback.onColorChanged(xProgress, color, false);
            }

            @Override // com.ltech.smarthome.view.ColorSeekBar.OnColorChangedListener
            public void onColorChangedFinish(float xProgress, int color, boolean isFromUser) {
                CgdActionSelectView.this.mViewBinding.civColor.setImageDrawable(new ColorDrawable(color));
                CgdActionSelectView.this.rgbColor = color;
                if (!isFromUser || CgdActionSelectView.this.mCallback == null) {
                    return;
                }
                CgdActionSelectView.this.mCallback.onColorChanged(xProgress, color, true);
            }
        });
        this.mViewBinding.civColor.setImageDrawable(new ColorDrawable(this.mViewBinding.colorSeekbar.getSelectColor()));
    }

    public void setProgress(int progress) {
        this.mViewBinding.sbBrt.setProgress(progress);
    }

    @Override // android.view.View
    public void setEnabled(boolean enable) {
        setBrtEnable(enable);
        setCtEnable(enable);
        setColorEnable(enable);
    }

    private void setBrtEnable(boolean enable) {
        this.mViewBinding.sbBrt.setEnabled(enable);
        Rect bounds = this.mViewBinding.sbBrt.getProgressDrawable().getBounds();
        this.mViewBinding.sbBrt.setProgressDrawable(ContextCompat.getDrawable(getContext(), enable ? R.drawable.style_seekbar_red : R.drawable.style_seekbar_gray));
        this.mViewBinding.sbBrt.getProgressDrawable().setBounds(bounds);
        this.mViewBinding.sbBrt.setThumb(ContextCompat.getDrawable(getContext(), enable ? R.mipmap.icon_thumb_default : R.mipmap.icon_thumb_disabled));
    }

    private void setCtEnable(boolean enable) {
        this.mViewBinding.sbCt.setEnabled(enable);
        RangeSeekBar rangeSeekBar = this.mViewBinding.sbCt;
        int i = R.mipmap.pic_ct;
        rangeSeekBar.setProgressDrawableId(enable ? R.mipmap.pic_ct : R.drawable.style_seekbar_gray);
        RangeSeekBar rangeSeekBar2 = this.mViewBinding.sbCt;
        if (!enable) {
            i = R.drawable.style_seekbar_gray;
        }
        rangeSeekBar2.setProgressDefaultDrawableId(i);
        this.mViewBinding.sbCt.getLeftSeekBar().setThumbDrawableId(enable ? R.mipmap.icon_thumb_default : R.mipmap.icon_thumb_disabled);
    }

    private void setColorEnable(boolean enable) {
        this.mViewBinding.colorSeekbar.setEnabled(enable);
        this.mViewBinding.colorSeekbar.changeThumb(BitmapFactory.decodeResource(getContext().getResources(), enable ? R.mipmap.icon_thumb_default : R.mipmap.icon_thumb_disabled));
        if (enable) {
            if (this.rgbColor != Integer.MIN_VALUE) {
                this.mViewBinding.civColor.setImageDrawable(new ColorDrawable(this.rgbColor));
                return;
            } else {
                this.mViewBinding.civColor.setImageDrawable(new ColorDrawable(this.mViewBinding.colorSeekbar.getSelectColor()));
                return;
            }
        }
        this.mViewBinding.civColor.setImageDrawable(new ColorDrawable(getResources().getColor(R.color.color_line_gray)));
    }

    public void setRole(Role role) {
        this.mRole = role;
        if (DaliProHelper.isSupportCt(role)) {
            initCt(this.mRole);
        }
        if (DaliProHelper.isSupportColor(this.mRole)) {
            initColor();
        }
    }

    public int getColor() {
        return this.rgbColor;
    }

    public int getRgbBrt() {
        return this.rgbBrt;
    }

    public int getWy() {
        return this.wy;
    }

    public int getWyBrt() {
        return this.wyBrt;
    }

    public void setBrt(int brt) {
        this.wyBrt = brt;
        this.mViewBinding.sbBrt.setProgress(brt);
    }

    public void setCt(int ct) {
        CtControlHelper ctControlHelper = this.ctHelper;
        if (ctControlHelper != null) {
            this.wy = 255 - ct;
            ctControlHelper.setProgress(ct);
        }
    }

    public void setColor(int color) {
        this.rgbColor = color;
        this.mViewBinding.civColor.setImageDrawable(new ColorDrawable(color));
    }

    public void setCallback(OnDialogCallback mCallback) {
        this.mCallback = mCallback;
    }
}