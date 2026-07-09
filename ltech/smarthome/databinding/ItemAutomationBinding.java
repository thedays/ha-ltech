package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.Automation;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public abstract class ItemAutomationBinding extends ViewDataBinding {
    public final ConstraintLayout layoutItemBg;

    @Bindable
    protected Integer mBgRes;

    @Bindable
    protected SwitchButton.OnCheckedChangeListener mCheckedChangeListener;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Automation mItem;

    @Bindable
    protected String mType;

    @Bindable
    protected Integer mTypeBg;
    public final SwitchButton sb;
    public final AppCompatTextView tvModeName;
    public final AppCompatTextView tvModeType;

    public abstract void setBgRes(Integer bgRes);

    public abstract void setCheckedChangeListener(SwitchButton.OnCheckedChangeListener checkedChangeListener);

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setItem(Automation item);

    public abstract void setType(String type);

    public abstract void setTypeBg(Integer typeBg);

    protected ItemAutomationBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout layoutItemBg, SwitchButton sb, AppCompatTextView tvModeName, AppCompatTextView tvModeType) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutItemBg = layoutItemBg;
        this.sb = sb;
        this.tvModeName = tvModeName;
        this.tvModeType = tvModeType;
    }

    public Automation getItem() {
        return this.mItem;
    }

    public Integer getBgRes() {
        return this.mBgRes;
    }

    public String getType() {
        return this.mType;
    }

    public Integer getTypeBg() {
        return this.mTypeBg;
    }

    public SwitchButton.OnCheckedChangeListener getCheckedChangeListener() {
        return this.mCheckedChangeListener;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ItemAutomationBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAutomationBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemAutomationBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_automation, root, attachToRoot, component);
    }

    public static ItemAutomationBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAutomationBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemAutomationBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_automation, null, false, component);
    }

    public static ItemAutomationBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAutomationBinding bind(View view, Object component) {
        return (ItemAutomationBinding) bind(component, view, R.layout.item_automation);
    }
}