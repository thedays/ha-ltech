package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActNfcRestoreBinding extends ViewDataBinding {
    public final AppCompatTextView btNext;
    public final AppCompatImageView ivEditorRead;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvNotice;
    public final AppCompatTextView tvTipDetail;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActNfcRestoreBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView btNext, AppCompatImageView ivEditorRead, LayoutTitleDefaultBinding title, AppCompatTextView tvNotice, AppCompatTextView tvTipDetail) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btNext = btNext;
        this.ivEditorRead = ivEditorRead;
        this.title = title;
        this.tvNotice = tvNotice;
        this.tvTipDetail = tvTipDetail;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActNfcRestoreBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActNfcRestoreBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActNfcRestoreBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_nfc_restore, root, attachToRoot, component);
    }

    public static ActNfcRestoreBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActNfcRestoreBinding inflate(LayoutInflater inflater, Object component) {
        return (ActNfcRestoreBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_nfc_restore, null, false, component);
    }

    public static ActNfcRestoreBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActNfcRestoreBinding bind(View view, Object component) {
        return (ActNfcRestoreBinding) bind(component, view, R.layout.act_nfc_restore);
    }
}