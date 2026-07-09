package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.intercom.ActIntercomSettingVM;

/* loaded from: classes3.dex */
public abstract class ActIntercomSetOpenKeyBinding extends ViewDataBinding {
    public final AppCompatEditText etInputKey;
    public final LinearLayout layoutSetKey;
    public final LinearLayout layoutShowKey;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActIntercomSettingVM mViewmodel;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvErrorTip;
    public final AppCompatTextView tvKey;
    public final AppCompatTextView tvTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActIntercomSettingVM viewmodel);

    protected ActIntercomSetOpenKeyBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatEditText etInputKey, LinearLayout layoutSetKey, LinearLayout layoutShowKey, LayoutTitleDefaultBinding title, AppCompatTextView tvErrorTip, AppCompatTextView tvKey, AppCompatTextView tvTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.etInputKey = etInputKey;
        this.layoutSetKey = layoutSetKey;
        this.layoutShowKey = layoutShowKey;
        this.title = title;
        this.tvErrorTip = tvErrorTip;
        this.tvKey = tvKey;
        this.tvTip = tvTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActIntercomSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActIntercomSetOpenKeyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomSetOpenKeyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActIntercomSetOpenKeyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_intercom_set_open_key, root, attachToRoot, component);
    }

    public static ActIntercomSetOpenKeyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomSetOpenKeyBinding inflate(LayoutInflater inflater, Object component) {
        return (ActIntercomSetOpenKeyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_intercom_set_open_key, null, false, component);
    }

    public static ActIntercomSetOpenKeyBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomSetOpenKeyBinding bind(View view, Object component) {
        return (ActIntercomSetOpenKeyBinding) bind(component, view, R.layout.act_intercom_set_open_key);
    }
}