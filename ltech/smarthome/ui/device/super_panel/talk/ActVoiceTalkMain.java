package com.ltech.smarthome.ui.device.super_panel.talk;

import android.os.Build;
import android.view.View;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Lifecycle;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSuperPanelVoiceTalkBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.net.api.ApiConstants;
import com.ltech.smarthome.net.response.super_panel.DetailSuperPanelResponse;
import com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager;
import com.ltech.smarthome.utils.JumpPermissionManagement;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.yanzhenjie.permission.runtime.Permission;
import com.zhuhai.ltech.lt_voice_call_api.bean.QuerySettingResponse;
import com.zhuhai.ltech.lt_voice_call_api.bean.VoiceCallMember;
import io.reactivex.functions.Consumer;

/* loaded from: classes4.dex */
public class ActVoiceTalkMain extends BaseNormalActivity<ActSuperPanelVoiceTalkBinding> {
    public static final int REQUEST_WRITE_STORAGE = 3;
    private Device device;
    private long deviceId;
    private long panelUserId;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_super_panel_voice_talk;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.voice_call_str));
        if (getCurPlace() != null && (getCurPlace().isManager() || getCurPlace().isOwner())) {
            setEditString(getString(R.string.play_manage));
        }
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
        this.device = Injection.repo().device().getDeviceByDeviceId(getIntent().getLongExtra("device_id", -1L));
        ((ActSuperPanelVoiceTalkBinding) this.mViewBinding).imgTip1.setImageResource(LanguageUtils.isChinese(this) ? R.mipmap.intercom_img_1_cn : R.mipmap.intercom_img_1_en);
        ((ActSuperPanelVoiceTalkBinding) this.mViewBinding).imgTip2.setImageResource(LanguageUtils.isChinese(this) ? R.mipmap.intercom_img_2_cn : R.mipmap.intercom_img_2_en);
        ((ActSuperPanelVoiceTalkBinding) this.mViewBinding).setVoiceVisible(Boolean.valueOf(ApiConstants.isChinaNode()));
        ((ActSuperPanelVoiceTalkBinding) this.mViewBinding).tvCall.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.talk.ActVoiceTalkMain$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActVoiceTalkMain.this.lambda$initView$0(view);
            }
        });
        ((ActSuperPanelVoiceTalkBinding) this.mViewBinding).tvActive.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.talk.ActVoiceTalkMain$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActVoiceTalkMain.this.lambda$initView$2(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (checkWriteStoragePermission(3) && checkPermission()) {
            startCall(this.device);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(View view) {
        MessageDialog.show(this, R.string.free_activate, R.string.activate_tip).setOkButton(R.string.cancel).setCancelButton(R.string.activate, new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.talk.ActVoiceTalkMain$$ExternalSyntheticLambda2
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view2) {
                boolean lambda$initView$1;
                lambda$initView$1 = ActVoiceTalkMain.this.lambda$initView$1(baseDialog, view2);
                return lambda$initView$1;
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initView$1(BaseDialog baseDialog, View view) {
        ((ObservableSubscribeProxy) Injection.net().activeVoiceCall(this.device.getWifiMac()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<Object>() { // from class: com.ltech.smarthome.ui.device.super_panel.talk.ActVoiceTalkMain.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Object o) throws Exception {
                ActVoiceTalkMain actVoiceTalkMain = ActVoiceTalkMain.this;
                actVoiceTalkMain.showSuccessDialog(actVoiceTalkMain.getString(R.string.activate_success));
                ((ActSuperPanelVoiceTalkBinding) ActVoiceTalkMain.this.mViewBinding).tvCall.setVisibility(0);
                ((ActSuperPanelVoiceTalkBinding) ActVoiceTalkMain.this.mViewBinding).tvActive.setVisibility(8);
                ActVoiceTalkMain.this.syncVoiceSetting();
            }
        }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.device.super_panel.talk.ActVoiceTalkMain.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                ActVoiceTalkMain actVoiceTalkMain = ActVoiceTalkMain.this;
                actVoiceTalkMain.showErrorDialog(actVoiceTalkMain.getString(R.string.activate_fail));
            }
        });
        return false;
    }

    private void startCall(Device device) {
        int i;
        VoiceCallManager.getInstance().init();
        if (this.panelUserId > 0) {
            VoiceCallMember voiceCallMember = new VoiceCallMember();
            voiceCallMember.setUserid(this.panelUserId);
            voiceCallMember.setFloorname(device.getFloorName());
            voiceCallMember.setRoomname(device.getRoomName());
            String productId = device.getProductId();
            productId.hashCode();
            i = 4;
            switch (productId) {
                case "123050811340901":
                    i = 6;
                    break;
                case "3683388245101248":
                    i = 7;
                    break;
                case "122042815485901":
                    break;
                case "122080911090801":
                    i = 3;
                    break;
                case "121052512023201":
                    i = 2;
                    break;
                default:
                    i = 5;
                    break;
            }
            voiceCallMember.setPaneltype(i);
            voiceCallMember.setType(2);
            VoiceCallManager.getInstance().callByVoice(voiceCallMember);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ObservableSubscribeProxy) Injection.net().detailSuperPanel(this.deviceId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<DetailSuperPanelResponse>() { // from class: com.ltech.smarthome.ui.device.super_panel.talk.ActVoiceTalkMain.3
            @Override // io.reactivex.functions.Consumer
            public void accept(DetailSuperPanelResponse response) throws Exception {
                if (response.getInfo().getVoiceactive() == 0) {
                    ((ActSuperPanelVoiceTalkBinding) ActVoiceTalkMain.this.mViewBinding).tvCall.setVisibility(8);
                    ((ActSuperPanelVoiceTalkBinding) ActVoiceTalkMain.this.mViewBinding).tvActive.setVisibility(0);
                } else {
                    ((ActSuperPanelVoiceTalkBinding) ActVoiceTalkMain.this.mViewBinding).tvCall.setVisibility(0);
                    ((ActSuperPanelVoiceTalkBinding) ActVoiceTalkMain.this.mViewBinding).tvActive.setVisibility(8);
                    ActVoiceTalkMain.this.syncVoiceSetting();
                }
            }
        });
        syncVoiceSetting();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void syncVoiceSetting() {
        ((ObservableSubscribeProxy) VoiceCallManager.getInstance().syncVoiceCallSetting(this.deviceId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<QuerySettingResponse>() { // from class: com.ltech.smarthome.ui.device.super_panel.talk.ActVoiceTalkMain.4
            @Override // io.reactivex.functions.Consumer
            public void accept(QuerySettingResponse r) throws Exception {
                ActVoiceTalkMain.this.panelUserId = r.getVoicesetting().getPaneluserid();
            }
        }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.device.super_panel.talk.ActVoiceTalkMain.5
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                ActVoiceTalkMain.this.showErrorDialog("同步失败" + throwable.getMessage());
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        VoiceCallManager.getInstance().showVoiceSetting(this, this.deviceId);
    }

    private void showPermissionDialog() {
        MessageDialog.show(this, getString(R.string.app_str_voice_call_record), getString(R.string.app_str_voice_call_record_tip)).setCancelable(false).setOkButton(getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.talk.ActVoiceTalkMain$$ExternalSyntheticLambda1
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showPermissionDialog$3;
                lambda$showPermissionDialog$3 = ActVoiceTalkMain.this.lambda$showPermissionDialog$3(baseDialog, view);
                return lambda$showPermissionDialog$3;
            }
        }).setCancelButton(getString(R.string.app_str_refuse), new OnDialogButtonClickListener(this) { // from class: com.ltech.smarthome.ui.device.super_panel.talk.ActVoiceTalkMain.6
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                return false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showPermissionDialog$3(BaseDialog baseDialog, View view) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.RECORD_AUDIO)) {
            ActivityCompat.requestPermissions(this, new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.RECORD_AUDIO}, 1000);
            return false;
        }
        JumpPermissionManagement.GoToSetting(this, 900);
        return false;
    }

    private boolean checkPermission() {
        int checkSelfPermission;
        if (Build.VERSION.SDK_INT >= 23) {
            checkSelfPermission = checkSelfPermission(Permission.RECORD_AUDIO);
            if (checkSelfPermission != 0) {
                showPermissionDialog();
                return false;
            }
        }
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 3 && checkPermission()) {
            startCall(this.device);
        }
    }

    public Place getCurPlace() {
        return Injection.repo().home().getSelPlace();
    }
}