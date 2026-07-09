package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.MultiColorView;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public abstract class ItemAdvancedModeColorTimeBinding extends ViewDataBinding {
    public final CircleImageView circleImageView2;
    public final CircleImageView circleImageView21;
    public final AppCompatImageView ivTime;
    public final AppCompatImageView ivTime1;
    public final FrameLayout layoutNum;
    public final FrameLayout layoutNum1;
    public final View lineBottom;
    public final View lineBottom1;
    public final View lineTop;
    public final View lineTop1;
    public final MultiColorView multiColorView;
    public final MultiColorView multiColorView1;
    public final AppCompatTextView tvNum;
    public final AppCompatTextView tvNum1;
    public final AppCompatTextView tvTime;
    public final AppCompatTextView tvTime1;
    public final AppCompatTextView tvTimeTip;
    public final AppCompatTextView tvTimeTip1;
    public final View view26;
    public final View view261;
    public final View view27;
    public final View view271;

    protected ItemAdvancedModeColorTimeBinding(Object _bindingComponent, View _root, int _localFieldCount, CircleImageView circleImageView2, CircleImageView circleImageView21, AppCompatImageView ivTime, AppCompatImageView ivTime1, FrameLayout layoutNum, FrameLayout layoutNum1, View lineBottom, View lineBottom1, View lineTop, View lineTop1, MultiColorView multiColorView, MultiColorView multiColorView1, AppCompatTextView tvNum, AppCompatTextView tvNum1, AppCompatTextView tvTime, AppCompatTextView tvTime1, AppCompatTextView tvTimeTip, AppCompatTextView tvTimeTip1, View view26, View view261, View view27, View view271) {
        super(_bindingComponent, _root, _localFieldCount);
        this.circleImageView2 = circleImageView2;
        this.circleImageView21 = circleImageView21;
        this.ivTime = ivTime;
        this.ivTime1 = ivTime1;
        this.layoutNum = layoutNum;
        this.layoutNum1 = layoutNum1;
        this.lineBottom = lineBottom;
        this.lineBottom1 = lineBottom1;
        this.lineTop = lineTop;
        this.lineTop1 = lineTop1;
        this.multiColorView = multiColorView;
        this.multiColorView1 = multiColorView1;
        this.tvNum = tvNum;
        this.tvNum1 = tvNum1;
        this.tvTime = tvTime;
        this.tvTime1 = tvTime1;
        this.tvTimeTip = tvTimeTip;
        this.tvTimeTip1 = tvTimeTip1;
        this.view26 = view26;
        this.view261 = view261;
        this.view27 = view27;
        this.view271 = view271;
    }

    public static ItemAdvancedModeColorTimeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAdvancedModeColorTimeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemAdvancedModeColorTimeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_advanced_mode_color_time, root, attachToRoot, component);
    }

    public static ItemAdvancedModeColorTimeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAdvancedModeColorTimeBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemAdvancedModeColorTimeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_advanced_mode_color_time, null, false, component);
    }

    public static ItemAdvancedModeColorTimeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAdvancedModeColorTimeBinding bind(View view, Object component) {
        return (ItemAdvancedModeColorTimeBinding) bind(component, view, R.layout.item_advanced_mode_color_time);
    }
}