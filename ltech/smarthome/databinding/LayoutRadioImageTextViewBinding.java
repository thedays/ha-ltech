package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class LayoutRadioImageTextViewBinding extends ViewDataBinding {
    public final AppCompatImageView ivImage;
    public final AppCompatTextView tvText;

    protected LayoutRadioImageTextViewBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivImage, AppCompatTextView tvText) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivImage = ivImage;
        this.tvText = tvText;
    }

    public static LayoutRadioImageTextViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutRadioImageTextViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (LayoutRadioImageTextViewBinding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_radio_image_text_view, root, attachToRoot, component);
    }

    public static LayoutRadioImageTextViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutRadioImageTextViewBinding inflate(LayoutInflater inflater, Object component) {
        return (LayoutRadioImageTextViewBinding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_radio_image_text_view, null, false, component);
    }

    public static LayoutRadioImageTextViewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutRadioImageTextViewBinding bind(View view, Object component) {
        return (LayoutRadioImageTextViewBinding) bind(component, view, R.layout.layout_radio_image_text_view);
    }
}