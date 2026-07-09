package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.central.tepanel.ActTePanelVM;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class ActTePanelBinding extends ViewDataBinding {
    public final FrameLayout fragmentContainer;
    public final LinearLayout layoutTab;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActTePanelVM mViewmodel;
    public final SmartRefreshLayout refreshLayout;
    public final LayoutTitleIrBinding title;
    public final AppCompatTextView tvAc;
    public final AppCompatTextView tvAir;
    public final LinearLayout tvOfflineTip;
    public final View vOpen;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActTePanelVM viewmodel);

    protected ActTePanelBinding(Object _bindingComponent, View _root, int _localFieldCount, FrameLayout fragmentContainer, LinearLayout layoutTab, SmartRefreshLayout refreshLayout, LayoutTitleIrBinding title, AppCompatTextView tvAc, AppCompatTextView tvAir, LinearLayout tvOfflineTip, View vOpen) {
        super(_bindingComponent, _root, _localFieldCount);
        this.fragmentContainer = fragmentContainer;
        this.layoutTab = layoutTab;
        this.refreshLayout = refreshLayout;
        this.title = title;
        this.tvAc = tvAc;
        this.tvAir = tvAir;
        this.tvOfflineTip = tvOfflineTip;
        this.vOpen = vOpen;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public ActTePanelVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActTePanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTePanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActTePanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_te_panel, root, attachToRoot, component);
    }

    public static ActTePanelBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTePanelBinding inflate(LayoutInflater inflater, Object component) {
        return (ActTePanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_te_panel, null, false, component);
    }

    public static ActTePanelBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTePanelBinding bind(View view, Object component) {
        return (ActTePanelBinding) bind(component, view, R.layout.act_te_panel);
    }
}