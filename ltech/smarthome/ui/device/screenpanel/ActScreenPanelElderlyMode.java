package com.ltech.smarthome.ui.device.screenpanel;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActScreenPanelElderlyModeBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.ResponseMsg;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ActScreenPanelElderlyMode extends BaseControlActivity<ActScreenPanelElderlyModeBinding, ActSmartPanelVM> {
    boolean isOpenElderlyMode = false;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_screen_panel_elderly_mode;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.elderly_mode));
        setBackImage(R.mipmap.icon_back);
        ((ActSmartPanelVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        this.isOpenElderlyMode = getIntent().getIntExtra(Constants.ELDERLY_MODE, 0) == 2;
        ((ActSmartPanelVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        Device deviceById = Injection.repo().device().getDeviceById(((ActSmartPanelVM) this.mViewModel).controlId);
        int i = LanguageUtils.isChinese(this) ? R.mipmap.s123pro_set_edlerlymode_jpg : R.mipmap.s123pro_set_elderlymode_jpg_en;
        if (deviceById != null) {
            String productId = deviceById.getProductId();
            productId.hashCode();
            if (productId.equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                i = LanguageUtils.isChinese(this) ? R.mipmap.s6pro_set_caringmode_jpg : R.mipmap.s6pro_set_caringmode_jpg_en;
            }
        }
        ((ActScreenPanelElderlyModeBinding) this.mViewBinding).ivTip.setImageResource(i);
        if (this.isOpenElderlyMode) {
            ((ActScreenPanelElderlyModeBinding) this.mViewBinding).btnEnableOrDisableElderlyMode.setText(R.string.disable_elderly_mode);
            ((ActScreenPanelElderlyModeBinding) this.mViewBinding).btnEnableOrDisableElderlyMode.setBackgroundDrawable(getDrawable(R.drawable.shape_blue_bt_2));
        } else {
            ((ActScreenPanelElderlyModeBinding) this.mViewBinding).btnEnableOrDisableElderlyMode.setText(R.string.enable_elderly_mode);
            ((ActScreenPanelElderlyModeBinding) this.mViewBinding).btnEnableOrDisableElderlyMode.setBackgroundDrawable(getDrawable(R.drawable.shape_red_bt));
        }
        ((ActScreenPanelElderlyModeBinding) this.mViewBinding).btnEnableOrDisableElderlyMode.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanelElderlyMode$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActScreenPanelElderlyMode.this.lambda$initView$0(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        setElderlyMode(this.isOpenElderlyMode ? 1 : 2);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        startObjectObserve();
    }

    private void startObjectObserve() {
        if (((ActSmartPanelVM) this.mViewModel).groupControl) {
            ((ActSmartPanelVM) this.mViewModel).controlObject.setValue(Injection.repo().group().getGroupById(((ActSmartPanelVM) this.mViewModel).controlId));
            return;
        }
        Device deviceById = Injection.repo().device().getDeviceById(((ActSmartPanelVM) this.mViewModel).controlId);
        try {
            if (((ActSmartPanelVM) this.mViewModel).controlObject.getValue() == null) {
                ((ActSmartPanelVM) this.mViewModel).controlObject.setValue(deviceById);
            } else {
                if (HelpUtils.compareObject((Device) ((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), deviceById)) {
                    return;
                }
                ((ActSmartPanelVM) this.mViewModel).controlObject.setValue(deviceById);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setElderlyMode(final int displayModeType) {
        showLoadingDialog(getString(R.string.saving));
        CmdAssistant.getDeviceAssistant(((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), new int[0]).setPanelScreenElderlyMode(this, displayModeType, new IAction() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanelElderlyMode$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActScreenPanelElderlyMode.this.lambda$setElderlyMode$3(displayModeType, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setElderlyMode$3(int i, ResponseMsg responseMsg) {
        dismissLoadingDialog();
        if (responseMsg != null && (responseMsg.getStateCode() == 0 || responseMsg.getStateCode() == 153)) {
            int i2 = 1;
            if (!((ActSmartPanelVM) this.mViewModel).groupControl) {
                final Device deviceById = Injection.repo().device().getDeviceById(((ActSmartPanelVM) this.mViewModel).controlId);
                try {
                    JSONObject jSONObject = deviceById.getExtParam() != null ? new JSONObject(deviceById.getExtParam()) : new JSONObject();
                    if (!this.isOpenElderlyMode) {
                        i2 = 2;
                    }
                    jSONObject.put("switchScreenBigIcon", i2);
                    deviceById.setExtParam(jSONObject.toString());
                    ((ObservableSubscribeProxy) Injection.net().updateParamExt(deviceById.getDeviceId(), jSONObject.toString()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanelElderlyMode$$ExternalSyntheticLambda2
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) {
                            ActScreenPanelElderlyMode.this.lambda$setElderlyMode$1(deviceById, obj);
                        }
                    }, new SmartErrorComsumer());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ReplaceHelper.instance().backupData(this, deviceById.getDeviceId(), UpdateBackDataRequest.ICON_MODE, CmdAssistant.getDeviceAssistant(deviceById, new int[0]).setPanelScreenElderlyMode(i));
                return;
            }
            final Group groupById = Injection.repo().group().getGroupById(((ActSmartPanelVM) this.mViewModel).controlId);
            try {
                JSONObject jSONObject2 = groupById.getExtParam() != null ? new JSONObject(groupById.getExtParam()) : new JSONObject();
                if (!this.isOpenElderlyMode) {
                    i2 = 2;
                }
                jSONObject2.put("switchScreenBigIcon", i2);
                groupById.setExtParam(jSONObject2.toString());
                ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(groupById.getGroupId(), jSONObject2.toString()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanelElderlyMode$$ExternalSyntheticLambda3
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActScreenPanelElderlyMode.this.lambda$setElderlyMode$2(groupById, obj);
                    }
                }, new SmartErrorComsumer());
                return;
            } catch (JSONException e2) {
                e2.printStackTrace();
                return;
            }
        }
        showErrorDialog(getString(R.string.save_fail));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setElderlyMode$1(Device device, Object obj) throws Exception {
        Injection.repo().device().saveDevice(device);
        if (this.isOpenElderlyMode) {
            SmartToast.showCenterShort(getString(R.string.app_str_turned_off));
        } else {
            SmartToast.showCenterShort(getString(R.string.app_str_turned_on));
        }
        finishActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setElderlyMode$2(Group group, Object obj) throws Exception {
        Injection.repo().group().saveGroup(group);
        if (this.isOpenElderlyMode) {
            SmartToast.showCenterShort(getString(R.string.app_str_turned_off));
        } else {
            SmartToast.showCenterShort(getString(R.string.app_str_turned_on));
        }
        finishActivity();
    }
}