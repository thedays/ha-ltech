package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.NumberSetView;

/* loaded from: classes3.dex */
public abstract class ViewFiveCurrentItemBinding extends ViewDataBinding {
    public final CardView cardview;
    public final NumberSetView currentSetView;
    public final RelativeLayout layoutCurrentItem;
    public final AppCompatTextView tvCard;
    public final AppCompatTextView tvOutput;

    protected ViewFiveCurrentItemBinding(Object _bindingComponent, View _root, int _localFieldCount, CardView cardview, NumberSetView currentSetView, RelativeLayout layoutCurrentItem, AppCompatTextView tvCard, AppCompatTextView tvOutput) {
        super(_bindingComponent, _root, _localFieldCount);
        this.cardview = cardview;
        this.currentSetView = currentSetView;
        this.layoutCurrentItem = layoutCurrentItem;
        this.tvCard = tvCard;
        this.tvOutput = tvOutput;
    }

    public static ViewFiveCurrentItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewFiveCurrentItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewFiveCurrentItemBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_five_current_item, root, attachToRoot, component);
    }

    public static ViewFiveCurrentItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewFiveCurrentItemBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewFiveCurrentItemBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_five_current_item, null, false, component);
    }

    public static ViewFiveCurrentItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewFiveCurrentItemBinding bind(View view, Object component) {
        return (ViewFiveCurrentItemBinding) bind(component, view, R.layout.view_five_current_item);
    }
}