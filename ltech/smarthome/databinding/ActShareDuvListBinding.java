package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActShareDuvListBinding extends ViewDataBinding {
    public final LinearLayout layoutBottom;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvShare;

    public abstract void setTitle(TitleDefault title);

    protected ActShareDuvListBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout layoutBottom, RecyclerView rvContent, LayoutTitleDefaultBinding title, AppCompatTextView tvShare) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutBottom = layoutBottom;
        this.rvContent = rvContent;
        this.title = title;
        this.tvShare = tvShare;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActShareDuvListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActShareDuvListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActShareDuvListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_share_duv_list, root, attachToRoot, component);
    }

    public static ActShareDuvListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActShareDuvListBinding inflate(LayoutInflater inflater, Object component) {
        return (ActShareDuvListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_share_duv_list, null, false, component);
    }

    public static ActShareDuvListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActShareDuvListBinding bind(View view, Object component) {
        return (ActShareDuvListBinding) bind(component, view, R.layout.act_share_duv_list);
    }
}