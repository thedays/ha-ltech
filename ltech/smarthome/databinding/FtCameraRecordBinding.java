package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.videogo.widget.PinnedHeaderListView;

/* loaded from: classes3.dex */
public abstract class FtCameraRecordBinding extends ViewDataBinding {
    public final AppCompatImageView ivLeft;
    public final AppCompatImageView ivNoSdcard;
    public final AppCompatImageView ivRight;
    public final ScrollView layoutEmpty;
    public final ConstraintLayout layoutLoad;
    public final PinnedHeaderListView listRecord;
    public final RelativeLayout listviewLine;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected String mDate;
    public final LinearLayout title;
    public final AppCompatTextView tvDate;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setDate(String date);

    protected FtCameraRecordBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivLeft, AppCompatImageView ivNoSdcard, AppCompatImageView ivRight, ScrollView layoutEmpty, ConstraintLayout layoutLoad, PinnedHeaderListView listRecord, RelativeLayout listviewLine, LinearLayout title, AppCompatTextView tvDate) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivLeft = ivLeft;
        this.ivNoSdcard = ivNoSdcard;
        this.ivRight = ivRight;
        this.layoutEmpty = layoutEmpty;
        this.layoutLoad = layoutLoad;
        this.listRecord = listRecord;
        this.listviewLine = listviewLine;
        this.title = title;
        this.tvDate = tvDate;
    }

    public String getDate() {
        return this.mDate;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static FtCameraRecordBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtCameraRecordBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtCameraRecordBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_camera_record, root, attachToRoot, component);
    }

    public static FtCameraRecordBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtCameraRecordBinding inflate(LayoutInflater inflater, Object component) {
        return (FtCameraRecordBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_camera_record, null, false, component);
    }

    public static FtCameraRecordBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtCameraRecordBinding bind(View view, Object component) {
        return (FtCameraRecordBinding) bind(component, view, R.layout.ft_camera_record);
    }
}