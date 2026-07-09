package com.ltech.smarthome.ui.device.light;

import android.view.View;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActLightOnOffTimeBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import java.util.ArrayList;
import java.util.Locale;

/* loaded from: classes4.dex */
public class ActLightOnOffTime extends BaseNormalActivity<ActLightOnOffTimeBinding> {
    private Object controlObject;
    private int onSecPos = 0;
    private int onMsPos = 0;
    private int offSecPos = 0;
    private int offMsPos = 0;
    private int powerOnSecPos = 0;
    private int powerOnMsPos = 0;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_light_on_off_time;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.light_on_off_time));
        setEditString(getString(R.string.save));
        ((ActLightOnOffTimeBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.light.ActLightOnOffTime$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActLightOnOffTime.this.lambda$initView$1((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view) {
        switch (view.getId()) {
            case R.id.layout_light_off /* 2131297517 */:
                showPickTimeDialog(false);
                break;
            case R.id.layout_light_on /* 2131297518 */:
                showPickTimeDialog(true);
                break;
            case R.id.layout_light_power_on /* 2131297520 */:
                showSelectTimeDialog();
                break;
            case R.id.tv_reset /* 2131298919 */:
                MessageDialog.show(this, R.string.tips, R.string.reset_factory_defaults, R.string.ok, R.string.cancel).setOnOkButtonClickListener(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActLightOnOffTime$$ExternalSyntheticLambda4
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view2) {
                        boolean lambda$initView$0;
                        lambda$initView$0 = ActLightOnOffTime.this.lambda$initView$0(baseDialog, view2);
                        return lambda$initView$0;
                    }
                });
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initView$0(BaseDialog baseDialog, View view) {
        this.onSecPos = 0;
        this.onMsPos = 5;
        this.offSecPos = 1;
        this.offMsPos = 5;
        refreshTimeView();
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        long longExtra = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        if (booleanExtra) {
            Injection.repo().group().getGroupFromDb(longExtra).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActLightOnOffTime$$ExternalSyntheticLambda2
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActLightOnOffTime.this.lambda$startObserve$2((Group) obj);
                }
            });
        } else {
            Injection.repo().device().getDeviceFromDb(longExtra).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActLightOnOffTime$$ExternalSyntheticLambda3
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActLightOnOffTime.this.lambda$startObserve$3((Device) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Group group) {
        if (this.controlObject == null) {
            this.controlObject = group;
            queryOnOffTime();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Device device) {
        if (this.controlObject == null) {
            this.controlObject = device;
            queryOnOffTime();
        }
    }

    private void queryOnOffTime() {
        showLoading();
        CmdAssistant.getLightCmdAssistant(this.controlObject, new int[0]).queryOnOffTime(this, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightOnOffTime$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightOnOffTime.this.lambda$queryOnOffTime$4((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryOnOffTime$4(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getResData() != null && responseMsg.getResData().length() >= 24) {
                LHomeLog.i(getClass(), "queryState=" + responseMsg.getResData());
                int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 20), 16) * 100;
                this.onSecPos = parseInt / 1000;
                this.onMsPos = (parseInt % 1000) / 100;
                int parseInt2 = Integer.parseInt(responseMsg.getResData().substring(20, 24), 16) * 100;
                this.offSecPos = parseInt2 / 1000;
                this.offMsPos = (parseInt2 % 1000) / 100;
                refreshTimeView();
                queryLightPowerOnTime();
                return;
            }
            showError();
            return;
        }
        showError();
    }

    private void queryLightPowerOnTime() {
        CmdAssistant.getLightCmdAssistant(this.controlObject, new int[0]).queryPowerOnTime(this, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightOnOffTime$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightOnOffTime.this.lambda$queryLightPowerOnTime$5((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryLightPowerOnTime$5(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getResData() != null && responseMsg.getResFunCode() == 192 && responseMsg.getResData().substring(12, 14).equalsIgnoreCase(ProductId.BLE_SMART_PANEL_SUB_TYPE_S2PRO)) {
                int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 20), 16) * 100;
                this.powerOnSecPos = parseInt / 1000;
                this.powerOnMsPos = (parseInt % 1000) / 100;
                refreshTimeView();
                showContent();
                return;
            }
            showContent();
            return;
        }
        showContent();
    }

    private void refreshTimeView() {
        if (this.mViewBinding != 0) {
            ((ActLightOnOffTimeBinding) this.mViewBinding).tvOnTime.setText(String.format(Locale.US, "%.1f%s", Float.valueOf(this.onSecPos + ((this.onMsPos * 100.0f) / 1000.0f)), getString(R.string.sec)));
            ((ActLightOnOffTimeBinding) this.mViewBinding).tvOffTime.setText(String.format(Locale.US, "%.1f%s", Float.valueOf(this.offSecPos + ((this.offMsPos * 100.0f) / 1000.0f)), getString(R.string.sec)));
            ((ActLightOnOffTimeBinding) this.mViewBinding).tvPowerOnTime.setText(String.format(Locale.US, "%.1f%s", Float.valueOf(this.powerOnSecPos + ((this.powerOnMsPos * 100.0f) / 1000.0f)), getString(R.string.sec)));
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        CmdAssistant.getLightCmdAssistant(this.controlObject, new int[0]).setOnOffTime(this, (this.onSecPos * 1000) + (this.onMsPos * 100), (this.offSecPos * 1000) + (this.offMsPos * 100), new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightOnOffTime$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightOnOffTime.this.lambda$edit$7((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$7(Boolean bool) {
        if (bool.booleanValue()) {
            CmdAssistant.getLightCmdAssistant(this.controlObject, new int[0]).setPowerOnTime(this, (this.powerOnSecPos * 1000) + (this.powerOnMsPos * 100), new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightOnOffTime$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActLightOnOffTime.this.lambda$edit$6((Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$6(Boolean bool) {
        if (bool.booleanValue()) {
            back();
        }
    }

    private void showPickTimeDialog(final boolean on) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (int i2 = 0; i2 < 100; i2++) {
            arrayList.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
        }
        ArrayList arrayList2 = new ArrayList();
        while (i < 10) {
            arrayList2.add(i == 0 ? "000" : String.valueOf(i * 100));
            i++;
        }
        TimeSelectDialog.asDefault().setTitle(getString(R.string.please_select_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(true).setMinUnit(getString(R.string.sec)).setSecUnit(getString(R.string.ms)).setSelectMinPosition(on ? this.onSecPos : this.offSecPos).setSelectSecPosition(on ? this.onMsPos : this.offMsPos).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.light.ActLightOnOffTime$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i3, int i4) {
                ActLightOnOffTime.this.lambda$showPickTimeDialog$8(on, i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPickTimeDialog$8(boolean z, int i, int i2) {
        if (z) {
            this.onSecPos = i;
            this.onMsPos = i2;
        } else {
            this.offSecPos = i;
            this.offMsPos = i2;
        }
        refreshTimeView();
    }

    private void showSelectTimeDialog() {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (int i2 = 0; i2 < 100; i2++) {
            arrayList.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
        }
        ArrayList arrayList2 = new ArrayList();
        while (i < 10) {
            arrayList2.add(i == 0 ? "000" : String.valueOf(i * 100));
            i++;
        }
        TimeSelectDialog.asDefault().setTitle(getString(R.string.please_select_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(true).setMinUnit(getString(R.string.sec)).setSecUnit(getString(R.string.ms)).setSelectMinPosition(this.powerOnSecPos).setSelectSecPosition(this.powerOnMsPos).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.light.ActLightOnOffTime$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i3, int i4) {
                ActLightOnOffTime.this.lambda$showSelectTimeDialog$9(i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectTimeDialog$9(int i, int i2) {
        this.powerOnSecPos = i;
        this.powerOnMsPos = i2;
        refreshTimeView();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        queryOnOffTime();
    }
}