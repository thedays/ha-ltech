package com.ltech.smarthome.view.dialog;

import android.view.View;
import android.widget.CalendarView;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogCalendarBinding;
import com.ltech.smarthome.view.dialog.CalendarDialog;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/* loaded from: classes4.dex */
public class CalendarDialog extends SmartDialog<DialogCalendarBinding> {
    private String cancelString;
    private IAction<Date> confirmAction;
    private String confirmString;
    private Date date;
    private String title;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_calendar;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.CalendarDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogCalendarBinding, CalendarDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogCalendarBinding viewBinding, final CalendarDialog dialog) {
            viewBinding.tvTitle.setText(dialog.title);
            if (dialog.cancelString != null) {
                viewBinding.tvCancel.setText(dialog.cancelString);
            }
            if (dialog.confirmString != null) {
                viewBinding.tvConfirm.setText(dialog.confirmString);
            }
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.CalendarDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    CalendarDialog.AnonymousClass1.lambda$convertView$0(CalendarDialog.this, (View) obj);
                }
            }));
            viewBinding.calendarView.setFocusedMonthDateColor(ActivityUtils.getTopActivity().getResources().getColor(R.color.color_blue));
            viewBinding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(this) { // from class: com.ltech.smarthome.view.dialog.CalendarDialog.1.1
                @Override // android.widget.CalendarView.OnDateChangeListener
                public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                    Calendar gregorianCalendar = GregorianCalendar.getInstance();
                    gregorianCalendar.set(year, month, dayOfMonth);
                    dialog.date = gregorianCalendar.getTime();
                }
            });
        }

        static /* synthetic */ void lambda$convertView$0(CalendarDialog calendarDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                calendarDialog.dismissDialog();
                return;
            }
            if (id != R.id.tv_confirm) {
                return;
            }
            if (calendarDialog.date == null) {
                calendarDialog.date = new Date();
            }
            if (calendarDialog.confirmAction != null) {
                calendarDialog.confirmAction.act(calendarDialog.date);
            }
            calendarDialog.dismissDialog();
        }
    }

    public static CalendarDialog asDefault() {
        return (CalendarDialog) new CalendarDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setGravity(80);
    }

    public CalendarDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CalendarDialog setCancelString(String cancelString) {
        this.cancelString = cancelString;
        return this;
    }

    public CalendarDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public CalendarDialog setConfirmAction(IAction<Date> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "dialog_calendar";
    }
}