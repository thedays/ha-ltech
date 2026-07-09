package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogMusicTimeBinding extends ViewDataBinding {
    public final LinearLayout layoutBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RecyclerView rvTime;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvSetTime;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogMusicTimeBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout layoutBg, RecyclerView rvTime, AppCompatTextView tvCancel, AppCompatTextView tvSetTime) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutBg = layoutBg;
        this.rvTime = rvTime;
        this.tvCancel = tvCancel;
        this.tvSetTime = tvSetTime;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogMusicTimeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogMusicTimeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogMusicTimeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_music_time, root, attachToRoot, component);
    }

    public static DialogMusicTimeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogMusicTimeBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogMusicTimeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_music_time, null, false, component);
    }

    public static DialogMusicTimeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogMusicTimeBinding bind(View view, Object component) {
        return (DialogMusicTimeBinding) bind(component, view, R.layout.dialog_music_time);
    }
}