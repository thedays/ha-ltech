package com.ltech.smarthome.ui.device.smartpanel;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelectSceneBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.screenpanel.ActSetScreenDisplay;
import com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity;
import com.ltech.smarthome.ui.newselect.FtRoomSceneVM;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.SettingAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.StringUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSmartPanelSelectScene extends BaseRoomSceneActivity<ActSelectSceneBinding, FtRoomSceneVM> implements ISelectAction {
    private int clickType;
    private Device controlDevice;
    private Group controlGroup;
    private boolean goModifyScreen;
    private boolean groupControl;
    private boolean isEurPanelBinding;
    private boolean isNightUp;
    private boolean isPowerScene;
    private RelateInfoAssistant relateInfoAssistant;
    private int relatePosition;
    private int zone;

    private int getSubscribeZone() {
        return 1;
    }

    static /* synthetic */ boolean lambda$eurPanelSubscribe$8(BaseDialog baseDialog, View view) {
        return false;
    }

    static /* synthetic */ boolean lambda$groupSubscribe$4(BaseDialog baseDialog, View view) {
        return false;
    }

    static /* synthetic */ boolean lambda$panelSubscribe$6(BaseDialog baseDialog, View view) {
        return false;
    }

    static /* synthetic */ boolean lambda$setting$18(BaseDialog baseDialog, View view) {
        return false;
    }

    static /* synthetic */ boolean lambda$showSetScreenDialog$15(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity
    protected int getSceneType() {
        return 2;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$edit$1(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setEditImage(R.mipmap.icon_search);
        this.listType = 2;
        this.clickType = getIntent().getIntExtra(Constants.CLICK_TYPE, 1);
        ((ActSelectSceneBinding) this.mViewBinding).setBottomTip(getString(R.string.finish));
        ((ActSelectSceneBinding) this.mViewBinding).tvBottom.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActSmartPanelSelectScene.this.lambda$initView$0(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (this.selectSceneList.isEmpty()) {
            SmartToast.showShort(R.string.please_choose);
            return;
        }
        if (this.isNightUp || this.isPowerScene) {
            bindScene(getIntent().getBooleanExtra(Constants.IS_EXC, false), ((FtRoomSceneVM) this.mViewModel).scene.getSceneNum());
            return;
        }
        if (getIntent().getBooleanExtra(Constants.IS_GQ, false)) {
            Intent intent = new Intent();
            intent.putExtra(Constants.RELATE_ID, ((FtRoomSceneVM) this.mViewModel).scene.getSceneId());
            intent.putExtra(Constants.GROUP_RELATE, 3);
            setResult(3001, intent);
            finishActivity();
            return;
        }
        if (getIntent().getBooleanExtra(Constants.BATCH_SET_SCENE, false)) {
            Intent intent2 = new Intent();
            intent2.putExtra(Constants.SCENE_ID, ((FtRoomSceneVM) this.mViewModel).scene.getSceneId());
            setResult(6004, intent2);
            finishActivity();
            return;
        }
        subscribe();
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity
    protected void sceneClick(Scene scene) {
        ((FtRoomSceneVM) this.mViewModel).scene = scene;
        RelatedInfoExtParam.RelateInfo RelatedSceneInfo = RelatedInfoExtParam.RelateInfo.RelatedSceneInfo(scene.getSceneId());
        RelatedSceneInfo.keyActionExtra = scene.getSceneNum();
        if (this.isEurPanelBinding) {
            RelatedSceneInfo.action = 3;
            this.relateInfoAssistant.setRelateSceneInfo(this.relatePosition, RelatedSceneInfo);
            return;
        }
        int i = this.clickType;
        if (i == 2) {
            RelatedSceneInfo.action = 4;
            this.relateInfoAssistant.setRelateLongClickInfo(this.relatePosition, RelatedSceneInfo);
        } else if (i == 1) {
            this.relateInfoAssistant.setRelateInfo(this.relatePosition, RelatedSceneInfo);
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        this.relatePosition = getIntent().getIntExtra(Constants.RELATED_POSITION, -1);
        this.groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        this.isNightUp = getIntent().getBooleanExtra(Constants.IS_NIGHT_UP, false);
        this.isPowerScene = getIntent().getBooleanExtra(Constants.IS_POWER_SCENE, false);
        this.zone = getIntent().getIntExtra(Constants.ZONE_NUM, 1);
        if (this.isNightUp || this.isPowerScene) {
            this.relatePosition = -1;
        }
        if (this.groupControl) {
            this.controlGroup = Injection.repo().group().getGroupById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
            this.relateInfoAssistant = new RelateInfoAssistant(this.controlGroup);
            if (ProductRepository.isEurPanel(this.controlGroup) || ProductRepository.isAsPanel(this.controlGroup)) {
                this.isEurPanelBinding = true;
                return;
            }
            return;
        }
        this.controlDevice = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
        this.relateInfoAssistant = new RelateInfoAssistant(this.controlDevice);
        if (ProductRepository.isRcB(this.controlDevice.getProductId()) || ProductRepository.isEurPanel(this.controlDevice.getProductId()) || ProductRepository.isAsPanel(this.controlDevice.getProductId())) {
            this.isEurPanelBinding = true;
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        ((ActSelectSceneBinding) this.mViewBinding).setTitleGone(true);
        ((ActSelectSceneBinding) this.mViewBinding).layoutSearch.setVisibility(0);
        ((ActSelectSceneBinding) this.mViewBinding).searchBar.cancelBtn.setVisibility(0);
    }

    private void subscribe() {
        if (Injection.state().isConnectOuterNet()) {
            if (this.groupControl) {
                if (this.isEurPanelBinding) {
                    lambda$subscribe$1(null);
                    return;
                } else {
                    showLoadingDialog(getString(R.string.subscribing));
                    groupSubscribe();
                    return;
                }
            }
            if (ProductRepository.isRcB(this.controlDevice.getProductId())) {
                RelateInfoUtils.showImageTipDialog(this.controlDevice, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene$$ExternalSyntheticLambda6
                    @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                    public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                        ActSmartPanelSelectScene.this.lambda$subscribe$1(imageTipDialog);
                    }
                });
                return;
            }
            if (ProductId.ID_SMART_SWITCH_SQB.equals(this.controlDevice.getProductId()) || (ProductId.ID_SMART_PANEL_S6B.equals(this.controlDevice.getProductId()) && RelateInfoUtils.needShowTipDialog())) {
                RelateInfoUtils.showImageTipDialog(this.controlDevice, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene$$ExternalSyntheticLambda7
                    @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                    public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                        ActSmartPanelSelectScene.this.lambda$subscribe$2(imageTipDialog);
                    }
                });
                return;
            } else if (ProductRepository.isEurPanel(this.controlDevice.getProductId()) || ProductRepository.isAsPanel(this.controlDevice.getProductId())) {
                lambda$subscribe$1(null);
                return;
            } else {
                lambda$subscribe$2(null);
                return;
            }
        }
        SmartToast.showShort(R.string.no_network);
    }

    public void unBindRelateInfo() {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.unsubscribing));
        getSettingCmdHelper().unSubscribeInSwitchPanel(ActivityUtils.getTopActivity(), 0, this.isNightUp ? 0 : 1 << this.relatePosition, 2, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelSelectScene.this.lambda$unBindRelateInfo$3((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unBindRelateInfo$3(Boolean bool) {
        if (bool.booleanValue()) {
            if (this.groupControl) {
                this.relateInfoAssistant.resetSmartPanelRelateInfo(this.relatePosition);
                return;
            } else {
                this.relateInfoAssistant.resetSmartPanelRelateInfo(this.controlDevice.getProductId(), this.relatePosition);
                return;
            }
        }
        dismissLoadingDialog();
    }

    private void groupSubscribe() {
        int groupAddress = this.controlGroup.getGroupAddress();
        int subscribeZone = getSubscribeZone();
        int i = this.relateInfoAssistant.getRelateInfo(this.relatePosition).action;
        int i2 = this.relateInfoAssistant.getRelateInfo(this.relatePosition).keyActionExtra;
        if (this.clickType == 2) {
            i = this.relateInfoAssistant.getRelateLongClickInfo(this.relatePosition).action;
            i2 = this.relateInfoAssistant.getRelateLongClickInfo(this.relatePosition).keyActionExtra;
        }
        getSettingCmdHelper().subscribeInSwitchPanelScene(this, this.isNightUp ? 0 : 1 << this.relatePosition, groupAddress, 65025, 2, subscribeZone, i, 3, i2, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelSelectScene.this.lambda$groupSubscribe$5((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$groupSubscribe$5(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getStateCode() == 0) {
                if (!this.relateInfoAssistant.isProPanel()) {
                    uploadGroupData(true);
                    return;
                }
                dismissLoadingDialog();
                if (this.clickType != 2) {
                    showSetScreenDialog(this.controlGroup);
                    return;
                } else {
                    setScreenDefault(this.controlGroup, true);
                    return;
                }
            }
            if (responseMsg.getStateCode() == 3) {
                dismissLoadingDialog();
                MessageDialog.show(this, getString(R.string.app_str_operation_failure), getString(R.string.local_scene_error_03)).setOkButton(getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene$$ExternalSyntheticLambda14
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        return ActSmartPanelSelectScene.lambda$groupSubscribe$4(baseDialog, view);
                    }
                });
                return;
            }
            return;
        }
        dismissLoadingDialog();
        showFailDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: panelSubscribe, reason: merged with bridge method [inline-methods] */
    public void lambda$subscribe$2(final ImageTipDialog dialog) {
        showLoadingDialog(getString(R.string.subscribing));
        int unicastAddress = ((BleParam) this.controlDevice.getParam(BleParam.class)).getUnicastAddress();
        final int subscribeZone = getSubscribeZone();
        int i = this.relateInfoAssistant.getRelateInfo(this.relatePosition).action;
        int i2 = this.relateInfoAssistant.getRelateInfo(this.relatePosition).keyActionExtra;
        if (this.clickType == 2) {
            i = this.relateInfoAssistant.getRelateLongClickInfo(this.relatePosition).action;
            i2 = this.relateInfoAssistant.getRelateLongClickInfo(this.relatePosition).keyActionExtra;
        }
        final int i3 = i;
        final int i4 = i2;
        final int i5 = 65025;
        getSettingCmdHelper().subscribeInSwitchPanelScene(this, this.isNightUp ? 0 : 1 << this.relatePosition, unicastAddress, 65025, 2, subscribeZone, i3, 3, i4, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelSelectScene.this.lambda$panelSubscribe$7(dialog, i5, subscribeZone, i3, i4, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$panelSubscribe$7(ImageTipDialog imageTipDialog, int i, int i2, int i3, int i4, ResponseMsg responseMsg) {
        if (imageTipDialog != null) {
            imageTipDialog.dismissDialog();
        }
        if (responseMsg != null) {
            if (responseMsg.getStateCode() == 0) {
                if (!this.relateInfoAssistant.isProPanel()) {
                    uploadDeviceData(true);
                } else {
                    dismissLoadingDialog();
                    if (this.clickType != 2) {
                        showSetScreenDialog(this.controlDevice);
                    } else {
                        setScreenDefault(this.controlDevice, true);
                    }
                }
                if (this.clickType == 2) {
                    ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.SWITCH_ACTION_DELAY, null);
                }
                ReplaceHelper.instance().addBackupData(this.clickType == 2 ? UpdateBackDataRequest.LONG_BIND : UpdateBackDataRequest.BIND, getSettingCmdHelper().subscribeInSwitchPanelScene(this.isNightUp ? 0 : 1 << this.relatePosition, i, i2, i3, 3, i4));
                ReplaceHelper.instance().backupIndexData(this, this.controlDevice.getDeviceId(), this.relatePosition + 1);
                return;
            }
            if (responseMsg.getStateCode() == 3) {
                dismissLoadingDialog();
                MessageDialog.show(this, getString(R.string.app_str_operation_failure), getString(R.string.local_scene_error_03)).setOkButton(getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene$$ExternalSyntheticLambda5
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        return ActSmartPanelSelectScene.lambda$panelSubscribe$6(baseDialog, view);
                    }
                });
                return;
            }
            return;
        }
        dismissLoadingDialog();
        showFailDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: eurPanelSubscribe, reason: merged with bridge method [inline-methods] */
    public void lambda$subscribe$1(final ImageTipDialog dialog) {
        int unicastAddress;
        showLoadingDialog(getString(R.string.subscribing));
        if (this.groupControl) {
            unicastAddress = this.controlGroup.getGroupAddress();
        } else {
            unicastAddress = ((BleParam) this.controlDevice.getParam(BleParam.class)).getUnicastAddress();
        }
        final int subscribeZone = getSubscribeZone();
        final ArrayList arrayList = new ArrayList();
        arrayList.add(3);
        arrayList.add(Integer.valueOf(this.relateInfoAssistant.getRelateSceneInfo(this.relatePosition).keyActionExtra));
        final int i = 65025;
        getSettingCmdHelper().subscribeInEurPanel(this, 1 << this.relatePosition, unicastAddress, 65025, 2, subscribeZone, 2, 3, arrayList, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene$$ExternalSyntheticLambda10
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelSelectScene.this.lambda$eurPanelSubscribe$9(dialog, i, subscribeZone, arrayList, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$eurPanelSubscribe$9(ImageTipDialog imageTipDialog, int i, int i2, List list, ResponseMsg responseMsg) {
        if (imageTipDialog != null) {
            imageTipDialog.dismissDialog();
        }
        if (responseMsg != null) {
            if (responseMsg.getStateCode() == 0) {
                if (this.groupControl) {
                    uploadGroupData(true);
                    return;
                } else {
                    ReplaceHelper.instance().backupIndexData(this, this.controlDevice.getDeviceId(), UpdateBackDataRequest.SCENE_BIND, getSettingCmdHelper().subscribeInEurPanel(1 << this.relatePosition, i, i2, 2, 3, list), this.relatePosition + 1);
                    uploadDeviceData(true);
                    return;
                }
            }
            if (responseMsg.getStateCode() == 3) {
                dismissLoadingDialog();
                MessageDialog.show(this, getString(R.string.app_str_operation_failure), getString(R.string.local_scene_error_03)).setOkButton(getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene$$ExternalSyntheticLambda9
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        return ActSmartPanelSelectScene.lambda$eurPanelSubscribe$8(baseDialog, view);
                    }
                });
                return;
            }
            return;
        }
        dismissLoadingDialog();
        showFailDialog();
    }

    private void uploadDeviceData(final boolean finish) {
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.controlDevice.getDeviceId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene$$ExternalSyntheticLambda20
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSelectScene.this.lambda$uploadDeviceData$10((Disposable) obj);
            }
        }).doFinally(new ActSmartPanelSelectScene$$ExternalSyntheticLambda18(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSelectScene.this.lambda$uploadDeviceData$11(finish, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDeviceData$10(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDeviceData$11(boolean z, Object obj) throws Exception {
        this.controlDevice.setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().device().saveDevice(this.controlDevice);
        SmartToast.showShort(R.string.save_success);
        if (this.goModifyScreen) {
            if (!this.controlDevice.getProductId().equals(ProductId.ID_SMART_PANEL_G4) && !this.controlDevice.getProductId().equals(ProductId.ID_SMART_PANEL_G4_PRO) && !this.controlDevice.getProductId().equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                NavUtils.destination(ActSetScreenDisplay.class).withLong(Constants.CONTROL_ID, this.controlDevice.getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) this.controlDevice)).withString(Constants.PRODUCT_ID, this.controlDevice.getProductId()).navigation(this);
            }
            this.goModifyScreen = false;
        }
        if (z) {
            lambda$edit$1(this);
        }
    }

    private void uploadGroupData(final boolean finish) {
        ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(this.controlGroup.getGroupId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene$$ExternalSyntheticLambda17
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSelectScene.this.lambda$uploadGroupData$12((Disposable) obj);
            }
        }).doFinally(new ActSmartPanelSelectScene$$ExternalSyntheticLambda18(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene$$ExternalSyntheticLambda19
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSelectScene.this.lambda$uploadGroupData$13(finish, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadGroupData$12(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadGroupData$13(boolean z, Object obj) throws Exception {
        this.controlGroup.setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().group().saveGroup(this.controlGroup);
        SmartToast.showShort(R.string.save_success);
        if (this.goModifyScreen) {
            if (ProductRepository.getLightColorType((Object) this.controlGroup) != 21 && ProductRepository.getLightColorType((Object) this.controlGroup) != 19) {
                NavUtils.destination(ActSetScreenDisplay.class).withLong(Constants.CONTROL_ID, this.controlGroup.getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) this.controlGroup)).withString(Constants.PRODUCT_ID, this.controlGroup.getGroupKey()).withBoolean(Constants.GROUP_CONTROL, true).navigation(this);
            }
            this.goModifyScreen = false;
        }
        if (z) {
            lambda$edit$1(this);
        }
    }

    private void showFailDialog() {
        runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene.1
            @Override // java.lang.Runnable
            public void run() {
                ViewHelpUtil.showBindFailDialog(ActSmartPanelSelectScene.this);
            }
        });
    }

    public void showSetScreenDialog(final Object controlObject) {
        if (this.relatePosition == -1) {
            return;
        }
        if (RelaySeparationHelper.isRelaySeparationDevice(controlObject)) {
            setScreenDefault(controlObject, true);
        } else {
            setScreenDefault(controlObject, false);
            MessageDialog.show(this, getString(R.string.save_success), getString(R.string.set_screen_tip)).setCancelable(false).setOkButton(getString(R.string.modify_now), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene$$ExternalSyntheticLambda15
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$showSetScreenDialog$14;
                    lambda$showSetScreenDialog$14 = ActSmartPanelSelectScene.this.lambda$showSetScreenDialog$14(controlObject, baseDialog, view);
                    return lambda$showSetScreenDialog$14;
                }
            }).setCancelButton(this.activity.getString(R.string.not_modify), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene$$ExternalSyntheticLambda16
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    return ActSmartPanelSelectScene.lambda$showSetScreenDialog$15(baseDialog, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showSetScreenDialog$14(Object obj, BaseDialog baseDialog, View view) {
        this.goModifyScreen = true;
        setScreenDefault(obj, true);
        return false;
    }

    private void setScreenDefault(final Object controlObject, final boolean finish) {
        RelatedInfoExtParam.RelateInfo relateInfo;
        if (this.clickType == 2) {
            relateInfo = this.relateInfoAssistant.getRelateLongClickInfo(this.relatePosition);
        } else {
            relateInfo = this.relateInfoAssistant.getRelateInfo(this.relatePosition);
        }
        relateInfo.screenType = RelatedInfoExtParam.ScreenType.ScreenTypeWord.getValue();
        final String realString = StringUtils.getRealString(this.relateInfoAssistant.getSwitchScreenBigIcon() == 2, RelateInfoUtils.getRelateInfoString(relateInfo));
        relateInfo.screenStr = realString;
        if (this.clickType == 2) {
            this.relateInfoAssistant.setRelateLongClickInfo(this.relatePosition, relateInfo);
        } else {
            this.relateInfoAssistant.setRelateInfo(this.relatePosition, relateInfo);
        }
        if (this.clickType == 2) {
            CmdAssistant.getDeviceAssistant(controlObject, new int[0]).setPanelScreenLongData(this, this.relatePosition, realString, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSmartPanelSelectScene.this.lambda$setScreenDefault$16(controlObject, finish, realString, (ResponseMsg) obj);
                }
            });
        } else {
            CmdAssistant.getDeviceAssistant(controlObject, new int[0]).setPanelScreenData(this, this.relatePosition, realString, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene$$ExternalSyntheticLambda11
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSmartPanelSelectScene.this.lambda$setScreenDefault$17(controlObject, finish, realString, (ResponseMsg) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setScreenDefault$16(Object obj, boolean z, String str, ResponseMsg responseMsg) {
        if (responseMsg != null && (responseMsg.getStateCode() == 0 || responseMsg.getStateCode() == 153)) {
            if (obj instanceof Device) {
                uploadDeviceData(z);
                ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.LONG_TEXT, CmdAssistant.getDeviceAssistant(obj, new int[0]).setPanelScreenLongData(this.relatePosition, str));
                ReplaceHelper.instance().backupIndexData(this, this.controlDevice.getDeviceId(), this.relatePosition + 1);
                return;
            }
            uploadGroupData(z);
            return;
        }
        showErrorDialog(this.activity.getString(R.string.save_fail));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setScreenDefault$17(Object obj, boolean z, String str, ResponseMsg responseMsg) {
        if (responseMsg != null && (responseMsg.getStateCode() == 0 || responseMsg.getStateCode() == 153)) {
            if (obj instanceof Device) {
                uploadDeviceData(z);
                ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.TEXT, CmdAssistant.getDeviceAssistant(obj, new int[0]).setPanelScreenData(this.relatePosition, str));
                ReplaceHelper.instance().backupIndexData(this, this.controlDevice.getDeviceId(), this.relatePosition + 1);
                return;
            }
            uploadGroupData(z);
            return;
        }
        showErrorDialog(this.activity.getString(R.string.save_fail));
    }

    public void bindScene(boolean isExc, int sceneNum) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(isExc ? 1 : 2));
        arrayList.add(Integer.valueOf(sceneNum));
        setting(3, arrayList, isExc);
    }

    private void setting(final int cmd, final List<Integer> data, final boolean isExc) {
        getSettingCmdHelper().setSmartPanelNightUpMode(ActivityUtils.getTopActivity(), cmd, data, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelSelectScene.this.lambda$setting$19(isExc, cmd, data, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setting$19(boolean z, int i, List list, ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            if (!this.groupControl) {
                if (z) {
                    if (this.isPowerScene) {
                        ReplaceHelper.instance().addBackupIndexAndTypeData(UpdateBackDataRequest.TRIGGER_SCENE, getSettingCmdHelper().setSmartPanelNightUpMode(i, list), this.zone, 1);
                        ReplaceHelper.instance().backupData(this, this.controlDevice.getDeviceId());
                    } else {
                        ReplaceHelper.instance().backupData(this, this.controlDevice.getDeviceId(), UpdateBackDataRequest.NIGHT_UP_MODE_EXC_SCENE, getSettingCmdHelper().setSmartPanelNightUpMode(i, list));
                    }
                } else if (this.isPowerScene) {
                    ReplaceHelper.instance().addBackupIndexAndTypeData(UpdateBackDataRequest.TRIGGER_SCENE, getSettingCmdHelper().setSmartPanelNightUpMode(i, list), this.zone, 2);
                    ReplaceHelper.instance().backupData(this, this.controlDevice.getDeviceId());
                } else {
                    ReplaceHelper.instance().backupData(this, this.controlDevice.getDeviceId(), UpdateBackDataRequest.NIGHT_UP_MODE_RESET_SCENE, getSettingCmdHelper().setSmartPanelNightUpMode(i, list));
                }
            }
            Intent intent = new Intent();
            intent.putExtra(Constants.RELATE_ID, ((FtRoomSceneVM) this.mViewModel).scene.getSceneId());
            intent.putExtra(Constants.GROUP_RELATE, 3);
            setResult(3001, intent);
            finishActivity();
            return;
        }
        if (responseMsg == null || responseMsg.getStateCode() != 3) {
            return;
        }
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), getString(R.string.app_str_operation_failure), getString(R.string.local_scene_error_03)).setOkButton(getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene$$ExternalSyntheticLambda12
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return ActSmartPanelSelectScene.lambda$setting$18(baseDialog, view);
            }
        });
    }

    private SettingAssistant getSettingCmdHelper() {
        return CmdAssistant.getSettingCmdAssistant(this.groupControl ? this.controlGroup : this.controlDevice, new int[0]);
    }
}