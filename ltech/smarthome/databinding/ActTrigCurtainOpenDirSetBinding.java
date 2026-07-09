package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.trig.ActTrigCurtainOpenDirSettingVM;

/* loaded from: classes3.dex */
public abstract class ActTrigCurtainOpenDirSetBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActTrigCurtainOpenDirSettingVM mViewmodel;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActTrigCurtainOpenDirSettingVM viewmodel);

    protected ActTrigCurtainOpenDirSetBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView rvContent, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rvContent = rvContent;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActTrigCurtainOpenDirSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActTrigCurtainOpenDirSetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTrigCurtainOpenDirSetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActTrigCurtainOpenDirSetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_trig_curtain_open_dir_set, root, attachToRoot, component);
    }

    public static ActTrigCurtainOpenDirSetBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTrigCurtainOpenDirSetBinding inflate(LayoutInflater inflater, Object component) {
        return (ActTrigCurtainOpenDirSetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_trig_curtain_open_dir_set, null, false, component);
    }

    public static ActTrigCurtainOpenDirSetBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTrigCurtainOpenDirSetBinding bind(View view, Object component) {
        return (ActTrigCurtainOpenDirSetBinding) bind(component, view, R.layout.act_trig_curtain_open_dir_set);
    }
}