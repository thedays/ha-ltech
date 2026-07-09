package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSmartSpeakerBinding extends ViewDataBinding {
    public final ConstraintLayout layoutLoad;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvSmartSpeaker;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActSmartSpeakerBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout layoutLoad, RecyclerView rvSmartSpeaker) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutLoad = layoutLoad;
        this.rvSmartSpeaker = rvSmartSpeaker;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActSmartSpeakerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSmartSpeakerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSmartSpeakerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_smart_speaker, root, attachToRoot, component);
    }

    public static ActSmartSpeakerBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSmartSpeakerBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSmartSpeakerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_smart_speaker, null, false, component);
    }

    public static ActSmartSpeakerBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSmartSpeakerBinding bind(View view, Object component) {
        return (ActSmartSpeakerBinding) bind(component, view, R.layout.act_smart_speaker);
    }
}