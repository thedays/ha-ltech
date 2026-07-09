package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class ActMatterSubListBinding extends ViewDataBinding {
    public final FrameLayout fragmentContainer;
    public final AppCompatImageView ivGo;
    public final AppCompatImageView ivGo1;
    public final ImageView ivSync;
    public final FrameLayout layoutLoad;
    public final View layoutNormal;
    public final RelativeLayout layoutNum;
    public final View layoutPlatform;
    public final View layoutQrcode;
    public final LinearLayout layoutTab;
    public final View line;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final SmartRefreshLayout refreshLayout;
    public final RecyclerView rv;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvDevice;
    public final AppCompatTextView tvDeviceNum;
    public final AppCompatTextView tvMain;
    public final AppCompatTextView tvPlatform;
    public final AppCompatTextView tvPlatformNum;
    public final AppCompatTextView tvScene;
    public final AppCompatTextView tvSync;
    public final TextView tvSyncNum;
    public final AppCompatTextView tvTopTip;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActMatterSubListBinding(Object _bindingComponent, View _root, int _localFieldCount, FrameLayout fragmentContainer, AppCompatImageView ivGo, AppCompatImageView ivGo1, ImageView ivSync, FrameLayout layoutLoad, View layoutNormal, RelativeLayout layoutNum, View layoutPlatform, View layoutQrcode, LinearLayout layoutTab, View line, SmartRefreshLayout refreshLayout, RecyclerView rv, LayoutTitleDefaultBinding title, AppCompatTextView tvDevice, AppCompatTextView tvDeviceNum, AppCompatTextView tvMain, AppCompatTextView tvPlatform, AppCompatTextView tvPlatformNum, AppCompatTextView tvScene, AppCompatTextView tvSync, TextView tvSyncNum, AppCompatTextView tvTopTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.fragmentContainer = fragmentContainer;
        this.ivGo = ivGo;
        this.ivGo1 = ivGo1;
        this.ivSync = ivSync;
        this.layoutLoad = layoutLoad;
        this.layoutNormal = layoutNormal;
        this.layoutNum = layoutNum;
        this.layoutPlatform = layoutPlatform;
        this.layoutQrcode = layoutQrcode;
        this.layoutTab = layoutTab;
        this.line = line;
        this.refreshLayout = refreshLayout;
        this.rv = rv;
        this.title = title;
        this.tvDevice = tvDevice;
        this.tvDeviceNum = tvDeviceNum;
        this.tvMain = tvMain;
        this.tvPlatform = tvPlatform;
        this.tvPlatformNum = tvPlatformNum;
        this.tvScene = tvScene;
        this.tvSync = tvSync;
        this.tvSyncNum = tvSyncNum;
        this.tvTopTip = tvTopTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActMatterSubListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMatterSubListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActMatterSubListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_matter_sub_list, root, attachToRoot, component);
    }

    public static ActMatterSubListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMatterSubListBinding inflate(LayoutInflater inflater, Object component) {
        return (ActMatterSubListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_matter_sub_list, null, false, component);
    }

    public static ActMatterSubListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMatterSubListBinding bind(View view, Object component) {
        return (ActMatterSubListBinding) bind(component, view, R.layout.act_matter_sub_list);
    }
}