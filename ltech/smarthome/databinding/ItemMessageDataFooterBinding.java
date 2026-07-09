package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public abstract class ItemMessageDataFooterBinding extends ViewDataBinding {
    public final CircleImageView circle;
    public final ConstraintLayout layoutFooterView;

    @Bindable
    protected String mFooter;
    public final AppCompatTextView tvFooterTip;

    public abstract void setFooter(String footer);

    protected ItemMessageDataFooterBinding(Object _bindingComponent, View _root, int _localFieldCount, CircleImageView circle, ConstraintLayout layoutFooterView, AppCompatTextView tvFooterTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.circle = circle;
        this.layoutFooterView = layoutFooterView;
        this.tvFooterTip = tvFooterTip;
    }

    public String getFooter() {
        return this.mFooter;
    }

    public static ItemMessageDataFooterBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMessageDataFooterBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemMessageDataFooterBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_message_data_footer, root, attachToRoot, component);
    }

    public static ItemMessageDataFooterBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMessageDataFooterBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemMessageDataFooterBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_message_data_footer, null, false, component);
    }

    public static ItemMessageDataFooterBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMessageDataFooterBinding bind(View view, Object component) {
        return (ItemMessageDataFooterBinding) bind(component, view, R.layout.item_message_data_footer);
    }
}