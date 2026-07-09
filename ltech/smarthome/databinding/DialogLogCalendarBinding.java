package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.haibin.calendarview.CalendarView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogLogCalendarBinding extends ViewDataBinding {
    public final CalendarView calendarView;
    public final AppCompatImageView ivLeft;
    public final AppCompatImageView ivRight;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final AppCompatTextView tvConfirm;
    public final AppCompatTextView tvMonth;
    public final AppCompatTextView tvReset;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogLogCalendarBinding(Object _bindingComponent, View _root, int _localFieldCount, CalendarView calendarView, AppCompatImageView ivLeft, AppCompatImageView ivRight, AppCompatTextView tvConfirm, AppCompatTextView tvMonth, AppCompatTextView tvReset) {
        super(_bindingComponent, _root, _localFieldCount);
        this.calendarView = calendarView;
        this.ivLeft = ivLeft;
        this.ivRight = ivRight;
        this.tvConfirm = tvConfirm;
        this.tvMonth = tvMonth;
        this.tvReset = tvReset;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogLogCalendarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogLogCalendarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogLogCalendarBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_log_calendar, root, attachToRoot, component);
    }

    public static DialogLogCalendarBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogLogCalendarBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogLogCalendarBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_log_calendar, null, false, component);
    }

    public static DialogLogCalendarBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogLogCalendarBinding bind(View view, Object component) {
        return (DialogLogCalendarBinding) bind(component, view, R.layout.dialog_log_calendar);
    }
}