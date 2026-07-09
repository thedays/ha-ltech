package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.HorizontalSeekBar;

/* loaded from: classes3.dex */
public abstract class DialogDimDepthSelectorBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView33;
    public final ConstraintLayout layoutBg;
    public final ConstraintLayout layoutDepthNum;
    public final ConstraintLayout layoutDepthTip;
    public final ConstraintLayout layoutDepthView;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected ObservableField<String> mContent;
    public final RecyclerView rvContent;
    public final HorizontalSeekBar sbDimDepth;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvNum1;
    public final AppCompatTextView tvNum2;
    public final AppCompatTextView tvNum3;
    public final AppCompatTextView tvNum4;
    public final AppCompatTextView tvNum5;
    public final AppCompatTextView tvSave;
    public final AppCompatTextView tvTip1;
    public final AppCompatTextView tvTip2;
    public final AppCompatTextView tvTip3;
    public final AppCompatTextView tvTip4;
    public final AppCompatTextView tvTip5;
    public final View viewDepth1;
    public final View viewDepth10;
    public final View viewDepth11;
    public final View viewDepth12;
    public final View viewDepth13;
    public final View viewDepth14;
    public final View viewDepth15;
    public final View viewDepth16;
    public final View viewDepth17;
    public final View viewDepth18;
    public final View viewDepth19;
    public final View viewDepth2;
    public final View viewDepth20;
    public final View viewDepth21;
    public final View viewDepth3;
    public final View viewDepth4;
    public final View viewDepth5;
    public final View viewDepth6;
    public final View viewDepth7;
    public final View viewDepth8;
    public final View viewDepth9;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setContent(ObservableField<String> content);

    protected DialogDimDepthSelectorBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView33, ConstraintLayout layoutBg, ConstraintLayout layoutDepthNum, ConstraintLayout layoutDepthTip, ConstraintLayout layoutDepthView, RecyclerView rvContent, HorizontalSeekBar sbDimDepth, AppCompatTextView tvCancel, AppCompatTextView tvNum1, AppCompatTextView tvNum2, AppCompatTextView tvNum3, AppCompatTextView tvNum4, AppCompatTextView tvNum5, AppCompatTextView tvSave, AppCompatTextView tvTip1, AppCompatTextView tvTip2, AppCompatTextView tvTip3, AppCompatTextView tvTip4, AppCompatTextView tvTip5, View viewDepth1, View viewDepth10, View viewDepth11, View viewDepth12, View viewDepth13, View viewDepth14, View viewDepth15, View viewDepth16, View viewDepth17, View viewDepth18, View viewDepth19, View viewDepth2, View viewDepth20, View viewDepth21, View viewDepth3, View viewDepth4, View viewDepth5, View viewDepth6, View viewDepth7, View viewDepth8, View viewDepth9) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView33 = appCompatTextView33;
        this.layoutBg = layoutBg;
        this.layoutDepthNum = layoutDepthNum;
        this.layoutDepthTip = layoutDepthTip;
        this.layoutDepthView = layoutDepthView;
        this.rvContent = rvContent;
        this.sbDimDepth = sbDimDepth;
        this.tvCancel = tvCancel;
        this.tvNum1 = tvNum1;
        this.tvNum2 = tvNum2;
        this.tvNum3 = tvNum3;
        this.tvNum4 = tvNum4;
        this.tvNum5 = tvNum5;
        this.tvSave = tvSave;
        this.tvTip1 = tvTip1;
        this.tvTip2 = tvTip2;
        this.tvTip3 = tvTip3;
        this.tvTip4 = tvTip4;
        this.tvTip5 = tvTip5;
        this.viewDepth1 = viewDepth1;
        this.viewDepth10 = viewDepth10;
        this.viewDepth11 = viewDepth11;
        this.viewDepth12 = viewDepth12;
        this.viewDepth13 = viewDepth13;
        this.viewDepth14 = viewDepth14;
        this.viewDepth15 = viewDepth15;
        this.viewDepth16 = viewDepth16;
        this.viewDepth17 = viewDepth17;
        this.viewDepth18 = viewDepth18;
        this.viewDepth19 = viewDepth19;
        this.viewDepth2 = viewDepth2;
        this.viewDepth20 = viewDepth20;
        this.viewDepth21 = viewDepth21;
        this.viewDepth3 = viewDepth3;
        this.viewDepth4 = viewDepth4;
        this.viewDepth5 = viewDepth5;
        this.viewDepth6 = viewDepth6;
        this.viewDepth7 = viewDepth7;
        this.viewDepth8 = viewDepth8;
        this.viewDepth9 = viewDepth9;
    }

    public ObservableField<String> getContent() {
        return this.mContent;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogDimDepthSelectorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogDimDepthSelectorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogDimDepthSelectorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_dim_depth_selector, root, attachToRoot, component);
    }

    public static DialogDimDepthSelectorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogDimDepthSelectorBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogDimDepthSelectorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_dim_depth_selector, null, false, component);
    }

    public static DialogDimDepthSelectorBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogDimDepthSelectorBinding bind(View view, Object component) {
        return (DialogDimDepthSelectorBinding) bind(component, view, R.layout.dialog_dim_depth_selector);
    }
}