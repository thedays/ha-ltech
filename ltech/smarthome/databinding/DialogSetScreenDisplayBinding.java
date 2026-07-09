package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogSetScreenDisplayBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView33;
    public final AppCompatEditText inputEdtTxt;
    public final AppCompatImageView ivClean;
    public final LinearLayout layoutBg;
    public final ConstraintLayout layoutContent;
    public final LinearLayout layoutInput;
    public final LinearLayout layoutTop;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final AppCompatTextView saveBtn;
    public final TabLayout tabsPattern;
    public final TabLayout tabsType;
    public final ViewPager2 viewpager;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogSetScreenDisplayBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView33, AppCompatEditText inputEdtTxt, AppCompatImageView ivClean, LinearLayout layoutBg, ConstraintLayout layoutContent, LinearLayout layoutInput, LinearLayout layoutTop, AppCompatTextView saveBtn, TabLayout tabsPattern, TabLayout tabsType, ViewPager2 viewpager) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView33 = appCompatTextView33;
        this.inputEdtTxt = inputEdtTxt;
        this.ivClean = ivClean;
        this.layoutBg = layoutBg;
        this.layoutContent = layoutContent;
        this.layoutInput = layoutInput;
        this.layoutTop = layoutTop;
        this.saveBtn = saveBtn;
        this.tabsPattern = tabsPattern;
        this.tabsType = tabsType;
        this.viewpager = viewpager;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogSetScreenDisplayBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSetScreenDisplayBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogSetScreenDisplayBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_set_screen_display, root, attachToRoot, component);
    }

    public static DialogSetScreenDisplayBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSetScreenDisplayBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogSetScreenDisplayBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_set_screen_display, null, false, component);
    }

    public static DialogSetScreenDisplayBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSetScreenDisplayBinding bind(View view, Object component) {
        return (DialogSetScreenDisplayBinding) bind(component, view, R.layout.dialog_set_screen_display);
    }
}