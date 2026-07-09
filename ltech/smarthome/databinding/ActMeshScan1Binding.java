package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ActMeshScan1Binding extends ViewDataBinding {
    public final ConstraintLayout constraintlayout;
    public final RecyclerView rvContent;
    public final AppCompatImageView spreadviewScan;
    public final AppCompatTextView tvHelp;
    public final AppCompatTextView tvNoDevice;
    public final AppCompatTextView tvScanTip1;
    public final AppCompatTextView tvScanTip2;
    public final AppCompatTextView tvTotal;
    public final View vLine;
    public final View view24;

    protected ActMeshScan1Binding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout constraintlayout, RecyclerView rvContent, AppCompatImageView spreadviewScan, AppCompatTextView tvHelp, AppCompatTextView tvNoDevice, AppCompatTextView tvScanTip1, AppCompatTextView tvScanTip2, AppCompatTextView tvTotal, View vLine, View view24) {
        super(_bindingComponent, _root, _localFieldCount);
        this.constraintlayout = constraintlayout;
        this.rvContent = rvContent;
        this.spreadviewScan = spreadviewScan;
        this.tvHelp = tvHelp;
        this.tvNoDevice = tvNoDevice;
        this.tvScanTip1 = tvScanTip1;
        this.tvScanTip2 = tvScanTip2;
        this.tvTotal = tvTotal;
        this.vLine = vLine;
        this.view24 = view24;
    }

    public static ActMeshScan1Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMeshScan1Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActMeshScan1Binding) ViewDataBinding.inflateInternal(inflater, R.layout.act_mesh_scan_1, root, attachToRoot, component);
    }

    public static ActMeshScan1Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMeshScan1Binding inflate(LayoutInflater inflater, Object component) {
        return (ActMeshScan1Binding) ViewDataBinding.inflateInternal(inflater, R.layout.act_mesh_scan_1, null, false, component);
    }

    public static ActMeshScan1Binding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMeshScan1Binding bind(View view, Object component) {
        return (ActMeshScan1Binding) bind(component, view, R.layout.act_mesh_scan_1);
    }
}