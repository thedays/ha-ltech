package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.NestedScrollParentView;

/* loaded from: classes3.dex */
public abstract class FtRoomDaliAddBinding extends ViewDataBinding {
    public final LinearLayout layoutLight;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Boolean mSceneEdit;
    public final RadioButton radio0;
    public final RadioButton radio20;
    public final RadioButton radio40;
    public final RadioGroup radioPosition;
    public final RecyclerView rvGroup;
    public final RecyclerView rvLight;
    public final RecyclerView rvScene;
    public final NestedScrollParentView scrollLayout;
    public final AppCompatTextView tvGroupTip;
    public final AppCompatTextView tvLightTip;
    public final AppCompatTextView tvSceneTip;
    public final AppCompatTextView tvShow;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setSceneEdit(Boolean sceneEdit);

    protected FtRoomDaliAddBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout layoutLight, RadioButton radio0, RadioButton radio20, RadioButton radio40, RadioGroup radioPosition, RecyclerView rvGroup, RecyclerView rvLight, RecyclerView rvScene, NestedScrollParentView scrollLayout, AppCompatTextView tvGroupTip, AppCompatTextView tvLightTip, AppCompatTextView tvSceneTip, AppCompatTextView tvShow) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutLight = layoutLight;
        this.radio0 = radio0;
        this.radio20 = radio20;
        this.radio40 = radio40;
        this.radioPosition = radioPosition;
        this.rvGroup = rvGroup;
        this.rvLight = rvLight;
        this.rvScene = rvScene;
        this.scrollLayout = scrollLayout;
        this.tvGroupTip = tvGroupTip;
        this.tvLightTip = tvLightTip;
        this.tvSceneTip = tvSceneTip;
        this.tvShow = tvShow;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public Boolean getSceneEdit() {
        return this.mSceneEdit;
    }

    public static FtRoomDaliAddBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtRoomDaliAddBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtRoomDaliAddBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_room_dali_add, root, attachToRoot, component);
    }

    public static FtRoomDaliAddBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtRoomDaliAddBinding inflate(LayoutInflater inflater, Object component) {
        return (FtRoomDaliAddBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_room_dali_add, null, false, component);
    }

    public static FtRoomDaliAddBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtRoomDaliAddBinding bind(View view, Object component) {
        return (FtRoomDaliAddBinding) bind(component, view, R.layout.ft_room_dali_add);
    }
}