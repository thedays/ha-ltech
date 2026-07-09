package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.StepSetView;

/* loaded from: classes3.dex */
public abstract class ItemSelectLuxCtBinding extends ViewDataBinding {
    public final AppCompatEditText etValue;
    public final AppCompatImageView ivSelect;
    public final ConstraintLayout layoutSelectDiy;

    @Bindable
    protected TitleDefault mTitle;
    public final RadioGroup radioGroup;
    public final RadioButton radioLess;
    public final RadioButton radioMore;
    public final StepSetView seekbarCt;
    public final StepSetView seekbarLux;
    public final AppCompatTextView tvMin;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvUnit;

    public abstract void setTitle(TitleDefault title);

    protected ItemSelectLuxCtBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatEditText etValue, AppCompatImageView ivSelect, ConstraintLayout layoutSelectDiy, RadioGroup radioGroup, RadioButton radioLess, RadioButton radioMore, StepSetView seekbarCt, StepSetView seekbarLux, AppCompatTextView tvMin, AppCompatTextView tvName, AppCompatTextView tvUnit) {
        super(_bindingComponent, _root, _localFieldCount);
        this.etValue = etValue;
        this.ivSelect = ivSelect;
        this.layoutSelectDiy = layoutSelectDiy;
        this.radioGroup = radioGroup;
        this.radioLess = radioLess;
        this.radioMore = radioMore;
        this.seekbarCt = seekbarCt;
        this.seekbarLux = seekbarLux;
        this.tvMin = tvMin;
        this.tvName = tvName;
        this.tvUnit = tvUnit;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ItemSelectLuxCtBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectLuxCtBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectLuxCtBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_lux_ct, root, attachToRoot, component);
    }

    public static ItemSelectLuxCtBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectLuxCtBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectLuxCtBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_lux_ct, null, false, component);
    }

    public static ItemSelectLuxCtBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectLuxCtBinding bind(View view, Object component) {
        return (ItemSelectLuxCtBinding) bind(component, view, R.layout.item_select_lux_ct);
    }
}