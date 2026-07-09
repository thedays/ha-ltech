package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.control.ActFeedBackVM;

/* loaded from: classes3.dex */
public abstract class ActFeedBackBinding extends ViewDataBinding {
    public final Button btCommit;
    public final EditText etContactWay;
    public final EditText etOpinion;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActFeedBackVM mViewmodel;
    public final LayoutTitleDefaultBinding title;
    public final TextView tvInputWords;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActFeedBackVM viewmodel);

    protected ActFeedBackBinding(Object _bindingComponent, View _root, int _localFieldCount, Button btCommit, EditText etContactWay, EditText etOpinion, LayoutTitleDefaultBinding title, TextView tvInputWords) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btCommit = btCommit;
        this.etContactWay = etContactWay;
        this.etOpinion = etOpinion;
        this.title = title;
        this.tvInputWords = tvInputWords;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActFeedBackVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActFeedBackBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActFeedBackBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActFeedBackBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_feed_back, root, attachToRoot, component);
    }

    public static ActFeedBackBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActFeedBackBinding inflate(LayoutInflater inflater, Object component) {
        return (ActFeedBackBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_feed_back, null, false, component);
    }

    public static ActFeedBackBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActFeedBackBinding bind(View view, Object component) {
        return (ActFeedBackBinding) bind(component, view, R.layout.act_feed_back);
    }
}