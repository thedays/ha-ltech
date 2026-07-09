package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActVoiceCallGroupAddBinding extends ViewDataBinding {
    public final AppCompatImageView ivMore;

    @Bindable
    protected TitleDefault mTitle;
    public final RelativeLayout rl1Layout;
    public final RelativeLayout rlLayout;
    public final RecyclerView rv;
    public final LayoutTitleDefaultBinding title;
    public final TextView tvDel;
    public final AppCompatTextView tvEdit;
    public final AppCompatTextView tvSub;

    public abstract void setTitle(TitleDefault title);

    protected ActVoiceCallGroupAddBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivMore, RelativeLayout rl1Layout, RelativeLayout rlLayout, RecyclerView rv, LayoutTitleDefaultBinding title, TextView tvDel, AppCompatTextView tvEdit, AppCompatTextView tvSub) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivMore = ivMore;
        this.rl1Layout = rl1Layout;
        this.rlLayout = rlLayout;
        this.rv = rv;
        this.title = title;
        this.tvDel = tvDel;
        this.tvEdit = tvEdit;
        this.tvSub = tvSub;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActVoiceCallGroupAddBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActVoiceCallGroupAddBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActVoiceCallGroupAddBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_voice_call_group_add, root, attachToRoot, component);
    }

    public static ActVoiceCallGroupAddBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActVoiceCallGroupAddBinding inflate(LayoutInflater inflater, Object component) {
        return (ActVoiceCallGroupAddBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_voice_call_group_add, null, false, component);
    }

    public static ActVoiceCallGroupAddBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActVoiceCallGroupAddBinding bind(View view, Object component) {
        return (ActVoiceCallGroupAddBinding) bind(component, view, R.layout.act_voice_call_group_add);
    }
}