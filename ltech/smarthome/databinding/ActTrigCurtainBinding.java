package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.CurtainBar;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.ReverseLightBrtBar;

/* loaded from: classes3.dex */
public abstract class ActTrigCurtainBinding extends ViewDataBinding {
    public final LightBrtBar curtainLeft;
    public final LightBrtBar curtainLeftOnly;
    public final ReverseLightBrtBar curtainRight;
    public final CurtainBar curtainUpDown;
    public final AppCompatImageView ivDevice;
    public final ConstraintLayout layoutLoad;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    protected ActTrigCurtainBinding(Object _bindingComponent, View _root, int _localFieldCount, LightBrtBar curtainLeft, LightBrtBar curtainLeftOnly, ReverseLightBrtBar curtainRight, CurtainBar curtainUpDown, AppCompatImageView ivDevice, ConstraintLayout layoutLoad, RecyclerView rvContent, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.curtainLeft = curtainLeft;
        this.curtainLeftOnly = curtainLeftOnly;
        this.curtainRight = curtainRight;
        this.curtainUpDown = curtainUpDown;
        this.ivDevice = ivDevice;
        this.layoutLoad = layoutLoad;
        this.rvContent = rvContent;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActTrigCurtainBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTrigCurtainBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActTrigCurtainBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_trig_curtain, root, attachToRoot, component);
    }

    public static ActTrigCurtainBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTrigCurtainBinding inflate(LayoutInflater inflater, Object component) {
        return (ActTrigCurtainBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_trig_curtain, null, false, component);
    }

    public static ActTrigCurtainBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTrigCurtainBinding bind(View view, Object component) {
        return (ActTrigCurtainBinding) bind(component, view, R.layout.act_trig_curtain);
    }
}