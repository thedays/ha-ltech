package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.device.light.ActMusicVM;

/* loaded from: classes3.dex */
public abstract class ActMusicBinding extends ViewDataBinding {

    @Bindable
    protected ActMusicVM mViewmodel;
    public final ViewPager2 viewpager;

    public abstract void setViewmodel(ActMusicVM viewmodel);

    protected ActMusicBinding(Object _bindingComponent, View _root, int _localFieldCount, ViewPager2 viewpager) {
        super(_bindingComponent, _root, _localFieldCount);
        this.viewpager = viewpager;
    }

    public ActMusicVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActMusicBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMusicBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActMusicBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_music, root, attachToRoot, component);
    }

    public static ActMusicBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMusicBinding inflate(LayoutInflater inflater, Object component) {
        return (ActMusicBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_music, null, false, component);
    }

    public static ActMusicBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMusicBinding bind(View view, Object component) {
        return (ActMusicBinding) bind(component, view, R.layout.act_music);
    }
}