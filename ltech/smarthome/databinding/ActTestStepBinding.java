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
public abstract class ActTestStepBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;
    public final ViewPager2 viewpager;

    public abstract void setTitle(TitleDefault title);

    protected ActTestStepBinding(Object _bindingComponent, View _root, int _localFieldCount, ViewPager2 viewpager) {
        super(_bindingComponent, _root, _localFieldCount);
        this.viewpager = viewpager;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActTestStepBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTestStepBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActTestStepBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_test_step, root, attachToRoot, component);
    }

    public static ActTestStepBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTestStepBinding inflate(LayoutInflater inflater, Object component) {
        return (ActTestStepBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_test_step, null, false, component);
    }

    public static ActTestStepBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTestStepBinding bind(View view, Object component) {
        return (ActTestStepBinding) bind(component, view, R.layout.act_test_step);
    }
}