package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogCalendarBinding extends ViewDataBinding {
    public final CalendarView calendarView;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvConfirm;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogCalendarBinding(Object _bindingComponent, View _root, int _localFieldCount, CalendarView calendarView, AppCompatTextView tvCancel, AppCompatTextView tvConfirm, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.calendarView = calendarView;
        this.tvCancel = tvCancel;
        this.tvConfirm = tvConfirm;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogCalendarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogCalendarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogCalendarBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_calendar, root, attachToRoot, component);
    }

    public static DialogCalendarBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogCalendarBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogCalendarBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_calendar, null, false, component);
    }

    public static DialogCalendarBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogCalendarBinding bind(View view, Object component) {
        return (DialogCalendarBinding) bind(component, view, R.layout.dialog_calendar);
    }
}