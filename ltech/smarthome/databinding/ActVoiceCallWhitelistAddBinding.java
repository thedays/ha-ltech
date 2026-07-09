package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActVoiceCallWhitelistAddBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rv;
    public final LayoutTitleDefaultBinding title;
    public final TextView tvSave;

    public abstract void setTitle(TitleDefault title);

    protected ActVoiceCallWhitelistAddBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView rv, LayoutTitleDefaultBinding title, TextView tvSave) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rv = rv;
        this.title = title;
        this.tvSave = tvSave;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActVoiceCallWhitelistAddBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActVoiceCallWhitelistAddBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActVoiceCallWhitelistAddBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_voice_call_whitelist_add, root, attachToRoot, component);
    }

    public static ActVoiceCallWhitelistAddBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActVoiceCallWhitelistAddBinding inflate(LayoutInflater inflater, Object component) {
        return (ActVoiceCallWhitelistAddBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_voice_call_whitelist_add, null, false, component);
    }

    public static ActVoiceCallWhitelistAddBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActVoiceCallWhitelistAddBinding bind(View view, Object component) {
        return (ActVoiceCallWhitelistAddBinding) bind(component, view, R.layout.act_voice_call_whitelist_add);
    }
}