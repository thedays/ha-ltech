package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.github.mikephil.charting.charts.LineChart;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActEnvironmentLogBinding extends ViewDataBinding {
    public final RelativeLayout layoutChart;
    public final ConstraintLayout layoutLog;
    public final RadioGroup layoutTab;
    public final LineChart lineChart;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final RadioButton radioCt;
    public final RadioButton radioLux;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvDate;
    public final AppCompatTextView tvUnit;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActEnvironmentLogBinding(Object _bindingComponent, View _root, int _localFieldCount, RelativeLayout layoutChart, ConstraintLayout layoutLog, RadioGroup layoutTab, LineChart lineChart, RadioButton radioCt, RadioButton radioLux, RecyclerView rvContent, LayoutTitleDefaultBinding title, AppCompatTextView tvDate, AppCompatTextView tvUnit) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutChart = layoutChart;
        this.layoutLog = layoutLog;
        this.layoutTab = layoutTab;
        this.lineChart = lineChart;
        this.radioCt = radioCt;
        this.radioLux = radioLux;
        this.rvContent = rvContent;
        this.title = title;
        this.tvDate = tvDate;
        this.tvUnit = tvUnit;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActEnvironmentLogBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEnvironmentLogBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActEnvironmentLogBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_environment_log, root, attachToRoot, component);
    }

    public static ActEnvironmentLogBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEnvironmentLogBinding inflate(LayoutInflater inflater, Object component) {
        return (ActEnvironmentLogBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_environment_log, null, false, component);
    }

    public static ActEnvironmentLogBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEnvironmentLogBinding bind(View view, Object component) {
        return (ActEnvironmentLogBinding) bind(component, view, R.layout.act_environment_log);
    }
}