package com.ltech.smarthome.view.dialog;

import android.view.View;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogLogCalendarBinding;
import com.ltech.smarthome.ui.log.LogHelper;
import com.ltech.smarthome.utils.DateUtils;
import com.ltech.smarthome.view.dialog.LogCalendarDialog;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* loaded from: classes4.dex */
public class LogCalendarDialog extends SmartDialog<DialogLogCalendarBinding> {
    private IAction<Date> confirmAction;
    private Date date;
    private long deviceId;
    private String title;
    private List<String> dayHasLog = new ArrayList();
    private String property = "";

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_log_calendar;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return getClass().getSimpleName();
    }

    /* renamed from: com.ltech.smarthome.view.dialog.LogCalendarDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogLogCalendarBinding, LogCalendarDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogLogCalendarBinding viewBinding, final LogCalendarDialog dialog) {
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.LogCalendarDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    LogCalendarDialog.AnonymousClass1.lambda$convertView$0(LogCalendarDialog.this, viewBinding, (View) obj);
                }
            }));
            viewBinding.tvMonth.setText(dialog.getContext().getString(R.string.cur_month, Integer.valueOf(viewBinding.calendarView.getCurYear()), Integer.valueOf(viewBinding.calendarView.getCurMonth())));
            viewBinding.calendarView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() { // from class: com.ltech.smarthome.view.dialog.LogCalendarDialog$1$$ExternalSyntheticLambda1
                @Override // com.haibin.calendarview.CalendarView.OnMonthChangeListener
                public final void onMonthChange(int i, int i2) {
                    LogCalendarDialog.AnonymousClass1.this.lambda$convertView$1(viewBinding, dialog, i, i2);
                }
            });
            final int curYear = viewBinding.calendarView.getCurYear();
            final int curMonth = viewBinding.calendarView.getCurMonth();
            final int curDay = viewBinding.calendarView.getCurDay();
            viewBinding.calendarView.setOnCalendarInterceptListener(new CalendarView.OnCalendarInterceptListener(this) { // from class: com.ltech.smarthome.view.dialog.LogCalendarDialog.1.2
                @Override // com.haibin.calendarview.CalendarView.OnCalendarInterceptListener
                public void onCalendarInterceptClick(Calendar calendar, boolean isClick) {
                }

                @Override // com.haibin.calendarview.CalendarView.OnCalendarInterceptListener
                public boolean onCalendarIntercept(Calendar calendar) {
                    if (calendar.getYear() < curYear || ((calendar.getYear() == curYear && calendar.getMonth() < curMonth) || (calendar.getYear() == curYear && calendar.getMonth() == curMonth && calendar.getDay() <= curDay))) {
                        java.util.Calendar calendar2 = java.util.Calendar.getInstance();
                        calendar2.set(calendar.getYear(), calendar.getMonth() - 1, calendar.getDay());
                        if (dialog.dayHasLog.contains(DateUtils.dateToString(calendar2.getTime(), "yyyy-MM-dd 00:00:00"))) {
                            return false;
                        }
                    }
                    return true;
                }
            });
            viewBinding.calendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener(this) { // from class: com.ltech.smarthome.view.dialog.LogCalendarDialog.1.3
                @Override // com.haibin.calendarview.CalendarView.OnCalendarSelectListener
                public void onCalendarOutOfRange(Calendar calendar) {
                }

                @Override // com.haibin.calendarview.CalendarView.OnCalendarSelectListener
                public void onCalendarSelect(Calendar calendar, boolean isClick) {
                    java.util.Calendar calendar2 = java.util.Calendar.getInstance();
                    calendar2.set(calendar.getYear(), calendar.getMonth() - 1, calendar.getDay());
                    dialog.date = calendar2.getTime();
                }
            });
            viewBinding.calendarView.scrollToCalendar(curYear, curMonth, curDay);
        }

        static /* synthetic */ void lambda$convertView$0(LogCalendarDialog logCalendarDialog, DialogLogCalendarBinding dialogLogCalendarBinding, View view) {
            int id = view.getId();
            if (id == R.id.iv_left) {
                dialogLogCalendarBinding.calendarView.scrollToPre();
                return;
            }
            if (id == R.id.iv_right) {
                dialogLogCalendarBinding.calendarView.scrollToNext();
                return;
            }
            if (id != R.id.tv_confirm) {
                return;
            }
            if (logCalendarDialog.date == null) {
                logCalendarDialog.date = new Date();
            }
            if (logCalendarDialog.confirmAction != null) {
                logCalendarDialog.confirmAction.act(logCalendarDialog.date);
            }
            logCalendarDialog.dismissDialog();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convertView$1(final DialogLogCalendarBinding dialogLogCalendarBinding, final LogCalendarDialog logCalendarDialog, int i, int i2) {
            dialogLogCalendarBinding.tvMonth.setText(logCalendarDialog.getContext().getString(R.string.cur_month, Integer.valueOf(i), Integer.valueOf(i2)));
            LogHelper.instance().queryLogDate(logCalendarDialog.deviceId, logCalendarDialog.property, DateUtils.dateToString(new Date(i, i2, 1), "MM-dd"), new LogHelper.LogDateReceiveListener(this) { // from class: com.ltech.smarthome.view.dialog.LogCalendarDialog.1.1
                @Override // com.ltech.smarthome.ui.log.LogHelper.LogDateReceiveListener
                public void onReceiveError() {
                }

                @Override // com.ltech.smarthome.ui.log.LogHelper.LogDateReceiveListener
                public void onDataReceive(List<String> list) {
                    logCalendarDialog.dayHasLog.clear();
                    logCalendarDialog.dayHasLog.addAll(list);
                    dialogLogCalendarBinding.calendarView.update();
                }
            });
        }
    }

    public static LogCalendarDialog asDefault() {
        return (LogCalendarDialog) new LogCalendarDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setGravity(80);
    }

    public LogCalendarDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public LogCalendarDialog setDayList(List<String> dayList) {
        this.dayHasLog = dayList;
        return this;
    }

    public LogCalendarDialog setDeviceId(long deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public LogCalendarDialog setProperty(String property) {
        this.property = property;
        return this;
    }

    public LogCalendarDialog setConfirmAction(IAction<Date> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }
}