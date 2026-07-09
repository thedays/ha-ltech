package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

/* loaded from: classes3.dex */
public abstract class ActRemoteBatteryBinding extends ViewDataBinding {
    public final RelativeLayout addPanel;
    public final AppCompatImageView ivAction;
    public final AppCompatImageView ivAddIcon;
    public final AppCompatImageView ivDevicePic;
    public final AppCompatImageView ivDoubt;
    public final AppCompatImageView ivMode;
    public final ConstraintLayout layoutContent;
    public final LinearLayout layoutEditAction;
    public final ConstraintLayout layoutPanel;
    public final ConstraintLayout layoutScene;
    public final ConstraintLayout layoutZone;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvMultiBind;
    public final SwipeRecyclerView rvPanel;
    public final RecyclerView rvScene;
    public final NestedScrollView scrollView;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvAction;
    public final AppCompatTextView tvMultiBindTitle;
    public final AppCompatTextView tvPanel;
    public final AppCompatTextView tvScene;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActRemoteBatteryBinding(Object _bindingComponent, View _root, int _localFieldCount, RelativeLayout addPanel, AppCompatImageView ivAction, AppCompatImageView ivAddIcon, AppCompatImageView ivDevicePic, AppCompatImageView ivDoubt, AppCompatImageView ivMode, ConstraintLayout layoutContent, LinearLayout layoutEditAction, ConstraintLayout layoutPanel, ConstraintLayout layoutScene, ConstraintLayout layoutZone, RecyclerView rvMultiBind, SwipeRecyclerView rvPanel, RecyclerView rvScene, NestedScrollView scrollView, LayoutTitleDefaultBinding title, AppCompatTextView tvAction, AppCompatTextView tvMultiBindTitle, AppCompatTextView tvPanel, AppCompatTextView tvScene) {
        super(_bindingComponent, _root, _localFieldCount);
        this.addPanel = addPanel;
        this.ivAction = ivAction;
        this.ivAddIcon = ivAddIcon;
        this.ivDevicePic = ivDevicePic;
        this.ivDoubt = ivDoubt;
        this.ivMode = ivMode;
        this.layoutContent = layoutContent;
        this.layoutEditAction = layoutEditAction;
        this.layoutPanel = layoutPanel;
        this.layoutScene = layoutScene;
        this.layoutZone = layoutZone;
        this.rvMultiBind = rvMultiBind;
        this.rvPanel = rvPanel;
        this.rvScene = rvScene;
        this.scrollView = scrollView;
        this.title = title;
        this.tvAction = tvAction;
        this.tvMultiBindTitle = tvMultiBindTitle;
        this.tvPanel = tvPanel;
        this.tvScene = tvScene;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActRemoteBatteryBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRemoteBatteryBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActRemoteBatteryBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_remote_battery, root, attachToRoot, component);
    }

    public static ActRemoteBatteryBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRemoteBatteryBinding inflate(LayoutInflater inflater, Object component) {
        return (ActRemoteBatteryBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_remote_battery, null, false, component);
    }

    public static ActRemoteBatteryBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRemoteBatteryBinding bind(View view, Object component) {
        return (ActRemoteBatteryBinding) bind(component, view, R.layout.act_remote_battery);
    }
}