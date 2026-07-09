package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActEngineeringModeBinding extends ViewDataBinding {
    public final LinearLayout layoutBottom;
    public final ConstraintLayout layoutLoad;
    public final LinearLayout layoutTab;
    public final View leftLine;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final View rightLine;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBottom;
    public final AppCompatTextView tvGateway;
    public final AppCompatTextView tvLight;
    public final AppCompatTextView tvPanel;
    public final AppCompatTextView tvTips;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActEngineeringModeBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout layoutBottom, ConstraintLayout layoutLoad, LinearLayout layoutTab, View leftLine, View rightLine, RecyclerView rvContent, LayoutTitleDefaultBinding title, AppCompatTextView tvBottom, AppCompatTextView tvGateway, AppCompatTextView tvLight, AppCompatTextView tvPanel, AppCompatTextView tvTips) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutBottom = layoutBottom;
        this.layoutLoad = layoutLoad;
        this.layoutTab = layoutTab;
        this.leftLine = leftLine;
        this.rightLine = rightLine;
        this.rvContent = rvContent;
        this.title = title;
        this.tvBottom = tvBottom;
        this.tvGateway = tvGateway;
        this.tvLight = tvLight;
        this.tvPanel = tvPanel;
        this.tvTips = tvTips;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActEngineeringModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEngineeringModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActEngineeringModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_engineering_mode, root, attachToRoot, component);
    }

    public static ActEngineeringModeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEngineeringModeBinding inflate(LayoutInflater inflater, Object component) {
        return (ActEngineeringModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_engineering_mode, null, false, component);
    }

    public static ActEngineeringModeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEngineeringModeBinding bind(View view, Object component) {
        return (ActEngineeringModeBinding) bind(component, view, R.layout.act_engineering_mode);
    }
}