package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.HorizontalSeekBar;

/* loaded from: classes3.dex */
public abstract class DialogMusicVolumeBinding extends ViewDataBinding {
    public final LinearLayout layoutBg;
    public final LinearLayout layoutProgress;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final HorizontalSeekBar sbMusicVolume;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogMusicVolumeBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout layoutBg, LinearLayout layoutProgress, HorizontalSeekBar sbMusicVolume, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutBg = layoutBg;
        this.layoutProgress = layoutProgress;
        this.sbMusicVolume = sbMusicVolume;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogMusicVolumeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogMusicVolumeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogMusicVolumeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_music_volume, root, attachToRoot, component);
    }

    public static DialogMusicVolumeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogMusicVolumeBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogMusicVolumeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_music_volume, null, false, component);
    }

    public static DialogMusicVolumeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogMusicVolumeBinding bind(View view, Object component) {
        return (DialogMusicVolumeBinding) bind(component, view, R.layout.dialog_music_volume);
    }
}