package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public abstract class ActSelectSuperPanelMusicBinding extends ViewDataBinding {
    public final AppCompatImageView ivCollectMusic;
    public final AppCompatImageView ivLocalMusic;
    public final AppCompatImageView ivOnlineMusic;
    public final AppCompatImageView ivPlayPause;
    public final AppCompatImageView ivPlayStart;
    public final LinearLayout layoutBar;
    public final LinearLayout layoutCollectMusic;
    public final LinearLayout layoutLocalMusic;
    public final RelativeLayout layoutLoop;
    public final LinearLayout layoutOnlineMusic;
    public final RelativeLayout layoutPlayPause;
    public final RelativeLayout layoutPlayStart;
    public final RelativeLayout layoutVolume;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Boolean mDiyVolume;

    @Bindable
    protected TitleDefault mTitle;
    public final SwitchButton sbDiyVolume;
    public final SwitchButton sbLoopMode;
    public final LightBrtBar sbVolume;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvCollect;
    public final AppCompatTextView tvLocalMusic;
    public final AppCompatTextView tvOnlineMusic;
    public final AppCompatTextView tvVolume;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setDiyVolume(Boolean diyVolume);

    public abstract void setTitle(TitleDefault title);

    protected ActSelectSuperPanelMusicBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivCollectMusic, AppCompatImageView ivLocalMusic, AppCompatImageView ivOnlineMusic, AppCompatImageView ivPlayPause, AppCompatImageView ivPlayStart, LinearLayout layoutBar, LinearLayout layoutCollectMusic, LinearLayout layoutLocalMusic, RelativeLayout layoutLoop, LinearLayout layoutOnlineMusic, RelativeLayout layoutPlayPause, RelativeLayout layoutPlayStart, RelativeLayout layoutVolume, SwitchButton sbDiyVolume, SwitchButton sbLoopMode, LightBrtBar sbVolume, LayoutTitleDefaultBinding title, AppCompatTextView tvCollect, AppCompatTextView tvLocalMusic, AppCompatTextView tvOnlineMusic, AppCompatTextView tvVolume) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivCollectMusic = ivCollectMusic;
        this.ivLocalMusic = ivLocalMusic;
        this.ivOnlineMusic = ivOnlineMusic;
        this.ivPlayPause = ivPlayPause;
        this.ivPlayStart = ivPlayStart;
        this.layoutBar = layoutBar;
        this.layoutCollectMusic = layoutCollectMusic;
        this.layoutLocalMusic = layoutLocalMusic;
        this.layoutLoop = layoutLoop;
        this.layoutOnlineMusic = layoutOnlineMusic;
        this.layoutPlayPause = layoutPlayPause;
        this.layoutPlayStart = layoutPlayStart;
        this.layoutVolume = layoutVolume;
        this.sbDiyVolume = sbDiyVolume;
        this.sbLoopMode = sbLoopMode;
        this.sbVolume = sbVolume;
        this.title = title;
        this.tvCollect = tvCollect;
        this.tvLocalMusic = tvLocalMusic;
        this.tvOnlineMusic = tvOnlineMusic;
        this.tvVolume = tvVolume;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public Boolean getDiyVolume() {
        return this.mDiyVolume;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActSelectSuperPanelMusicBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectSuperPanelMusicBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelectSuperPanelMusicBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_super_panel_music, root, attachToRoot, component);
    }

    public static ActSelectSuperPanelMusicBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectSuperPanelMusicBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSelectSuperPanelMusicBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_super_panel_music, null, false, component);
    }

    public static ActSelectSuperPanelMusicBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectSuperPanelMusicBinding bind(View view, Object component) {
        return (ActSelectSuperPanelMusicBinding) bind(component, view, R.layout.act_select_super_panel_music);
    }
}