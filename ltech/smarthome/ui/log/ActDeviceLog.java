package com.ltech.smarthome.ui.log;

import android.view.View;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActDeviceLogBinding;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.response.log.DeviceLog;
import com.ltech.smarthome.ui.log.ActDeviceLog;
import com.ltech.smarthome.ui.log.LogHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.DateUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.LogCalendarDialog;
import java.util.Date;
import java.util.List;

/* loaded from: classes4.dex */
public class ActDeviceLog extends BaseNormalActivity<ActDeviceLogBinding> {
    private long deviceId;
    private BaseQuickAdapter<DeviceLog, BaseViewHolder> mAdapter;
    private String productId;
    private String today;
    private MutableLiveData<Date> queryDate = new MutableLiveData<>();
    private LogHelper.DataReceiveListener listener = new LogHelper.DataReceiveListener() { // from class: com.ltech.smarthome.ui.log.ActDeviceLog.2
        @Override // com.ltech.smarthome.ui.log.LogHelper.DataReceiveListener
        public void onReceiveError() {
        }

        @Override // com.ltech.smarthome.ui.log.LogHelper.DataReceiveListener
        public void onDataReceive(List<DeviceLog> list) {
            if (list.isEmpty()) {
                ((ActDeviceLogBinding) ActDeviceLog.this.mViewBinding).rvContent.setVisibility(8);
                SmartToast.showCenterShort(ActDeviceLog.this.getString(R.string.log_empty));
            } else {
                ((ActDeviceLogBinding) ActDeviceLog.this.mViewBinding).rvContent.setVisibility(0);
                ActDeviceLog.this.mAdapter.replaceData(list);
            }
        }
    };

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_device_log;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.device_log));
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
        String stringExtra = getIntent().getStringExtra(Constants.PRODUCT_ID);
        this.productId = stringExtra;
        if (ProductId.ID_SENSOR_MS03.equals(stringExtra)) {
            setTitle(getString(R.string.sense_record));
        } else {
            setTitle(getString(R.string.device_log));
        }
        initAdapter();
        ((ActDeviceLogBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.log.ActDeviceLog$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActDeviceLog.this.lambda$initView$0((View) obj);
            }
        }));
        this.today = DateUtils.getFormatToday("yyyy-MM-dd");
        this.queryDate.setValue(new Date());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        int id = view.getId();
        if (id == R.id.iv_left) {
            this.queryDate.setValue(LogHelper.instance().getDayAfter(this.queryDate.getValue(), -1));
            return;
        }
        if (id == R.id.iv_right) {
            if (this.today.equals(DateUtils.dateToString(this.queryDate.getValue(), "yyyy-MM-dd"))) {
                return;
            }
            this.queryDate.setValue(LogHelper.instance().getDayAfter(this.queryDate.getValue(), 1));
        } else {
            if (id != R.id.tv_date) {
                return;
            }
            showLoadingDialog("");
            String str = ProductId.ID_SENSOR_MS03.equals(this.productId) ? "sensorState" : "";
            LogHelper.instance().queryLogDate(this.deviceId, str, DateUtils.dateToString(new Date(), "yyyy-MM"), new AnonymousClass1(str));
        }
    }

    /* renamed from: com.ltech.smarthome.ui.log.ActDeviceLog$1, reason: invalid class name */
    class AnonymousClass1 implements LogHelper.LogDateReceiveListener {
        final /* synthetic */ String val$property;

        AnonymousClass1(final String val$property) {
            this.val$property = val$property;
        }

        @Override // com.ltech.smarthome.ui.log.LogHelper.LogDateReceiveListener
        public void onDataReceive(List<String> list) {
            LogCalendarDialog.asDefault().setDayList(list).setProperty(this.val$property).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.log.ActDeviceLog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActDeviceLog.AnonymousClass1.this.lambda$onDataReceive$0((Date) obj);
                }
            }).showDialog(ActDeviceLog.this.activity);
            ActDeviceLog.this.dismissLoadingDialog();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataReceive$0(Date date) {
            ActDeviceLog.this.queryDate.setValue(date);
        }

        @Override // com.ltech.smarthome.ui.log.LogHelper.LogDateReceiveListener
        public void onReceiveError() {
            ActDeviceLog.this.dismissLoadingDialog();
        }
    }

    private void initAdapter() {
        BaseQuickAdapter<DeviceLog, BaseViewHolder> logAdapter = LogHelper.instance().getLogAdapter();
        this.mAdapter = logAdapter;
        logAdapter.bindToRecyclerView(((ActDeviceLogBinding) this.mViewBinding).rvContent);
        ((ActDeviceLogBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        this.queryDate.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.log.ActDeviceLog$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDeviceLog.this.lambda$startObserve$1((Date) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Date date) {
        ((ActDeviceLogBinding) this.mViewBinding).tvDate.setText(LogHelper.instance().getDateString(date));
        String dateToString = DateUtils.dateToString(this.queryDate.getValue(), "yyyy-MM-dd");
        if (ProductId.ID_SENSOR_MS03.equals(this.productId)) {
            LogHelper.instance().queryLog(this.deviceId, new String[]{"sensorOnoff", "sensorState"}, dateToString, this.listener);
        } else {
            LogHelper.instance().queryLog(this.deviceId, dateToString, this.listener);
        }
    }
}