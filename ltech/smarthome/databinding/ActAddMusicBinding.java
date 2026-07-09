package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.light.ActAddMusicVM;

/* loaded from: classes3.dex */
public abstract class ActAddMusicBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActAddMusicVM mViewmodel;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActAddMusicVM viewmodel);

    protected ActAddMusicBinding(Object _bindingComponent, View _root, int _localFieldCount, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActAddMusicVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActAddMusicBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddMusicBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActAddMusicBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_music, root, attachToRoot, component);
    }

    public static ActAddMusicBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddMusicBinding inflate(LayoutInflater inflater, Object component) {
        return (ActAddMusicBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_music, null, false, component);
    }

    public static ActAddMusicBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddMusicBinding bind(View view, Object component) {
        return (ActAddMusicBinding) bind(component, view, R.layout.act_add_music);
    }
}