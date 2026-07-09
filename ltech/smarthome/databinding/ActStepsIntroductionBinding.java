package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActStepsIntroductionBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;
    public final ViewPager2 viewpager;

    public abstract void setTitle(TitleDefault title);

    protected ActStepsIntroductionBinding(Object _bindingComponent, View _root, int _localFieldCount, ViewPager2 viewpager) {
        super(_bindingComponent, _root, _localFieldCount);
        this.viewpager = viewpager;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActStepsIntroductionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActStepsIntroductionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActStepsIntroductionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_steps_introduction, root, attachToRoot, component);
    }

    public static ActStepsIntroductionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActStepsIntroductionBinding inflate(LayoutInflater inflater, Object component) {
        return (ActStepsIntroductionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_steps_introduction, null, false, component);
    }

    public static ActStepsIntroductionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActStepsIntroductionBinding bind(View view, Object component) {
        return (ActStepsIntroductionBinding) bind(component, view, R.layout.act_steps_introduction);
    }
}