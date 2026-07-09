package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.light.ActDiyLightNameVM;

/* loaded from: classes3.dex */
public abstract class ActDiyLightNameBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView3;

    @Bindable
    protected String mNameText;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActDiyLightNameVM mViewmodel;
    public final LayoutTitleDefaultBinding title;
    public final View view3;
    public final View view4;
    public final AppCompatTextView view7;

    public abstract void setNameText(String nameText);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActDiyLightNameVM viewmodel);

    protected ActDiyLightNameBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView3, LayoutTitleDefaultBinding title, View view3, View view4, AppCompatTextView view7) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView3 = appCompatTextView3;
        this.title = title;
        this.view3 = view3;
        this.view4 = view4;
        this.view7 = view7;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public String getNameText() {
        return this.mNameText;
    }

    public ActDiyLightNameVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActDiyLightNameBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDiyLightNameBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActDiyLightNameBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_diy_light_name, root, attachToRoot, component);
    }

    public static ActDiyLightNameBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDiyLightNameBinding inflate(LayoutInflater inflater, Object component) {
        return (ActDiyLightNameBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_diy_light_name, null, false, component);
    }

    public static ActDiyLightNameBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDiyLightNameBinding bind(View view, Object component) {
        return (ActDiyLightNameBinding) bind(component, view, R.layout.act_diy_light_name);
    }
}