package com.ltech.smarthome.ui.log;

import android.content.Context;
import android.text.TextUtils;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.MyApplication;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.log.DeviceLog;
import com.ltech.smarthome.net.response.log.ListLogDateResponse;
import com.ltech.smarthome.net.response.log.ListLogResponse;
import com.ltech.smarthome.singleton.Singleton;
import com.ltech.smarthome.ui.log.LogHelper;
import com.ltech.smarthome.utils.DateUtils;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class LogHelper {
    private static final String DAY_END = " 23:59:59";
    private static final String DAY_START = " 00:00:00";

    public interface DataReceiveListener {
        void onDataReceive(List<DeviceLog> list);

        void onReceiveError();
    }

    public interface LogDateReceiveListener {
        void onDataReceive(List<String> list);

        void onReceiveError();
    }

    public static LogHelper instance() {
        return (LogHelper) Singleton.getSingleton(LogHelper.class);
    }

    public String getDateString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String format = simpleDateFormat.format(new Date());
        String format2 = simpleDateFormat.format(date);
        if (!format2.equals(format)) {
            return format2;
        }
        return format2 + "(" + MyApplication.getContext().getString(R.string.today) + ")";
    }

    public Date getDayAfter(Date date, int dayAfter) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(date);
            calendar.add(5, dayAfter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendar.getTime();
    }

    public Date getMinAfter(Date date, int minAfter) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(date);
            calendar.add(12, minAfter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendar.getTime();
    }

    public String convertDate(String time, String format) {
        try {
            return new SimpleDateFormat(format, Locale.US).format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(time));
        } catch (ParseException unused) {
            return time;
        }
    }

    public BaseQuickAdapter<DeviceLog, BaseViewHolder> getLogAdapter() {
        return new BaseQuickAdapter<DeviceLog, BaseViewHolder>(R.layout.item_device_log, new ArrayList()) { // from class: com.ltech.smarthome.ui.log.LogHelper.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, DeviceLog deviceLog) {
                helper.setGone(R.id.time_line, helper.getBindingAdapterPosition() != getItemCount() - 1).setText(R.id.tv_time, deviceLog.getShortCreatetime()).setText(R.id.tv_log, LogHelper.this.getLogMessage(deviceLog));
            }
        };
    }

    public String getLogMessage(DeviceLog deviceLog) {
        Context context = MyApplication.getContext();
        if (!TextUtils.isEmpty(deviceLog.getCnmessage())) {
            return LanguageUtils.isChinese(context) ? deviceLog.getCnmessage() : deviceLog.getEnmessage();
        }
        if (!TextUtils.isEmpty(deviceLog.getPropertycode())) {
            String propertycode = deviceLog.getPropertycode();
            propertycode.hashCode();
            if (propertycode.equals("sensorOnoff")) {
                return Integer.parseInt(deviceLog.getPropertyvalue()) == 0 ? context.getString(R.string.disable_motion_detect) : context.getString(R.string.enable_motion_detect);
            }
            if (propertycode.equals("sensorState")) {
                int parseInt = Integer.parseInt(deviceLog.getPropertyvalue());
                if (parseInt == 0) {
                    return context.getString(R.string.mr_state_value_1);
                }
                if (parseInt == 1) {
                    return context.getString(R.string.mr_state_value_2);
                }
                if (parseInt == 2) {
                    return context.getString(R.string.mr_state_value_3);
                }
                return context.getString(R.string.mr_state_value_4);
            }
            return "";
        }
        return "";
    }

    public void queryLog(long deviceId, DataReceiveListener dataListener) {
        queryLog(deviceId, "", "", dataListener);
    }

    public void queryLog(long deviceId, String date, DataReceiveListener dataListener) {
        queryLog(deviceId, date + DAY_START, date + DAY_END, dataListener);
    }

    public void queryLog(long deviceId, String[] codeList, String date, DataReceiveListener dataListener) {
        queryLog(deviceId, codeList, date + DAY_START, date + DAY_END, dataListener);
    }

    public void queryLog(long deviceId, String beginTime, String endTime, final DataReceiveListener dataListener) {
        ((ObservableSubscribeProxy) Injection.net().queryLog(deviceId, beginTime, endTime).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.log.LogHelper$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                LogHelper.lambda$queryLog$0(LogHelper.DataReceiveListener.this, (ListLogResponse) obj);
            }
        }, new SmartErrorComsumer(this) { // from class: com.ltech.smarthome.ui.log.LogHelper.2
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                DataReceiveListener dataReceiveListener = dataListener;
                if (dataReceiveListener != null) {
                    dataReceiveListener.onReceiveError();
                }
            }
        });
    }

    static /* synthetic */ void lambda$queryLog$0(DataReceiveListener dataReceiveListener, ListLogResponse listLogResponse) throws Exception {
        if (dataReceiveListener != null) {
            dataReceiveListener.onDataReceive(listLogResponse.getRows());
        }
    }

    public void queryLog(long deviceId, String propertyCode, String beginTime, String endTime, final DataReceiveListener dataListener) {
        ((ObservableSubscribeProxy) Injection.net().queryLog(deviceId, propertyCode, beginTime, endTime).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.log.LogHelper$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                LogHelper.lambda$queryLog$1(LogHelper.DataReceiveListener.this, (ListLogResponse) obj);
            }
        }, new SmartErrorComsumer(this) { // from class: com.ltech.smarthome.ui.log.LogHelper.3
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                DataReceiveListener dataReceiveListener = dataListener;
                if (dataReceiveListener != null) {
                    dataReceiveListener.onReceiveError();
                }
            }
        });
    }

    static /* synthetic */ void lambda$queryLog$1(DataReceiveListener dataReceiveListener, ListLogResponse listLogResponse) throws Exception {
        if (dataReceiveListener != null) {
            dataReceiveListener.onDataReceive(listLogResponse.getRows());
        }
    }

    public void queryLog(long deviceId, String[] codeList, String beginTime, String endTime, final DataReceiveListener dataListener) {
        ((ObservableSubscribeProxy) Injection.net().queryLog(deviceId, codeList, beginTime, endTime).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.log.LogHelper$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                LogHelper.lambda$queryLog$2(LogHelper.DataReceiveListener.this, (ListLogResponse) obj);
            }
        }, new SmartErrorComsumer(this) { // from class: com.ltech.smarthome.ui.log.LogHelper.4
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                DataReceiveListener dataReceiveListener = dataListener;
                if (dataReceiveListener != null) {
                    dataReceiveListener.onReceiveError();
                }
            }
        });
    }

    static /* synthetic */ void lambda$queryLog$2(DataReceiveListener dataReceiveListener, ListLogResponse listLogResponse) throws Exception {
        if (dataReceiveListener != null) {
            dataReceiveListener.onDataReceive(listLogResponse.getRows());
        }
    }

    public void queryPropertyLog(long deviceId, String propertyCode, DataReceiveListener dataListener) {
        queryLog(deviceId, propertyCode, DateUtils.dateToString(getDayAfter(new Date(), -1), "yyyy-MM-dd HH:mm:ss"), DateUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"), dataListener);
    }

    public void queryLogDate(long deviceId, String propertyCode, String month, final LogDateReceiveListener dataListener) {
        ((ObservableSubscribeProxy) Injection.net().queryLogDate(deviceId, propertyCode, month).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.log.LogHelper$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                LogHelper.lambda$queryLogDate$3(LogHelper.LogDateReceiveListener.this, (ListLogDateResponse) obj);
            }
        }, new SmartErrorComsumer(this) { // from class: com.ltech.smarthome.ui.log.LogHelper.5
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                LogDateReceiveListener logDateReceiveListener = dataListener;
                if (logDateReceiveListener != null) {
                    logDateReceiveListener.onReceiveError();
                }
            }
        });
    }

    static /* synthetic */ void lambda$queryLogDate$3(LogDateReceiveListener logDateReceiveListener, ListLogDateResponse listLogDateResponse) throws Exception {
        if (logDateReceiveListener != null) {
            logDateReceiveListener.onDataReceive(listLogDateResponse.getRows());
        }
    }
}