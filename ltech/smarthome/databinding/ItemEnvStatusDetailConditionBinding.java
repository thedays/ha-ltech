package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemEnvStatusDetailConditionBinding extends ViewDataBinding {
    public final AppCompatImageView ivGo;
    public final AppCompatImageView ivSelect;
    public final ConstraintLayout layoutCurValue;
    public final RelativeLayout layoutTop;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvSubName;
    public final AppCompatTextView tvValue;

    protected ItemEnvStatusDetailConditionBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivGo, AppCompatImageView ivSelect, ConstraintLayout layoutCurValue, RelativeLayout layoutTop, AppCompatTextView tvName, AppCompatTextView tvSubName, AppCompatTextView tvValue) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivGo = ivGo;
        this.ivSelect = ivSelect;
        this.layoutCurValue = layoutCurValue;
        this.layoutTop = layoutTop;
        this.tvName = tvName;
        this.tvSubName = tvSubName;
        this.tvValue = tvValue;
    }

    public static ItemEnvStatusDetailConditionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemEnvStatusDetailConditionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemEnvStatusDetailConditionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_env_status_detail_condition, root, attachToRoot, component);
    }

    public static ItemEnvStatusDetailConditionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemEnvStatusDetailConditionBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemEnvStatusDetailConditionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_env_status_detail_condition, null, false, component);
    }

    public static ItemEnvStatusDetailConditionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemEnvStatusDetailConditionBinding bind(View view, Object component) {
        return (ItemEnvStatusDetailConditionBinding) bind(component, view, R.layout.item_env_status_detail_condition);
    }
}