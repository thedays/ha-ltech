package com.ltech.smarthome.ui.device.knobpanel;

import android.content.Intent;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.DryContactBleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.product.ProductInfo;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.device.screenpanel.ActSetScreenDisplay;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
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
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.StringUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActKnobPanelSelectBleDeviceAndGroup extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> implements ISelectAction {
    private Device controlDevice;
    private long controlId;
    private int daliLightType;
    private boolean goModifyScreen;
    private RelateInfoAssistant relateInfoAssistant;
    private Object relateObject;
    private int relatePosition;

    static /* synthetic */ boolean lambda$showSetScreenDialog$3(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$edit$2(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_device));
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.setVisibility(0);
        ((ActSelect3Binding) this.mViewBinding).layoutSort.setVisibility(8);
        ((ActSelect3Binding) this.mViewBinding).layoutSortAndType.setVisibility(0);
        this.listType = 4;
        this.controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        this.relatePosition = getIntent().getIntExtra(Constants.RELATED_POSITION, -1);
        this.controlDevice = Injection.repo().device().getDeviceById(this.controlId);
        this.relateInfoAssistant = new RelateInfoAssistant(this.controlDevice);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void roleClick(Role role) {
        RelatedInfoExtParam.RelateInfo RelatedDeviceInfo;
        this.relateObject = role;
        if (role instanceof Group) {
            Group group = (Group) role;
            RelatedDeviceInfo = RelatedInfoExtParam.RelateInfo.RelatedGroupInfo(group.getGroupId());
            RelatedDeviceInfo.action = 1;
            if (ProductRepository.getLightColorType((Object) group) == 14) {
                RelatedDeviceInfo.type = 5;
            } else if (ProductRepository.getLightColorType((Object) group) == 101) {
                RelatedDeviceInfo.type = 8;
                if (DaliProHelper.isMultiTypeGroup(group)) {
                    this.relateInfoAssistant.setRelateInfo(this.relatePosition, RelatedDeviceInfo);
                    showKnobBindDialog(group);
                    return;
                }
                this.daliLightType = DaliProHelper.convertDaliType(group);
            } else if (ProductRepository.getLightColorType((Object) group) == 16) {
                RelatedDeviceInfo.type = 7;
            }
        } else {
            Device device = (Device) role;
            RelatedDeviceInfo = RelatedInfoExtParam.RelateInfo.RelatedDeviceInfo(device.getDeviceId());
            RelatedDeviceInfo.action = 1;
            if (device.getProductId().equals(ProductId.ID_BLE_LIGHT_CGD_PRO)) {
                RelatedDeviceInfo.type = 8;
                this.relateInfoAssistant.setRelateInfo(this.relatePosition, RelatedDeviceInfo);
                NavUtils.destination(ActKnobPanelSelectCgdProAction.class).withLong(Constants.CONTROL_ID, this.controlId).withLong(Constants.RELATE_ID, device.getId()).withLong("device_id", device.getDeviceId()).withInt(Constants.GROUP_RELATE, 1).withInt(Constants.LIGHT_TYPE, getIntent().getIntExtra(Constants.LIGHT_TYPE, 0)).withBoolean(Constants.GROUP_CONTROL, false).withDefaultRequestCode().navigation(this);
                return;
            } else if (ProductRepository.isDaliLightGroup(device)) {
                RelatedDeviceInfo.type = 8;
                this.daliLightType = DaliProHelper.convertDaliType(device);
            } else if (device.getProductId().equals(ProductId.ID_BLE_DRY_CONTACT)) {
                if (device.getParam(DryContactBleParam.class) != null && (((DryContactBleParam) device.getParam(DryContactBleParam.class)).getSubType() == 0 || ((DryContactBleParam) device.getParam(DryContactBleParam.class)).getSubType() == 3)) {
                    RelatedDeviceInfo.type = 7;
                }
            } else if (device.getProductId().equals(ProductId.ID_BLE_CURTAIN) || device.getProductId().equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
                RelatedDeviceInfo.type = 4;
            } else if (device.getProductId().equals(ProductId.ID_BLE_MUSIC_PLAYER)) {
                RelatedDeviceInfo.type = 6;
            }
        }
        this.relateInfoAssistant.setRelateInfo(this.relatePosition, RelatedDeviceInfo);
        subscribe(role);
    }

    private void subscribe(final Role role) {
        if (this.controlDevice.getProductId().equals(ProductId.ID_SMART_SWITCH_SQB)) {
            RelateInfoUtils.showImageTipDialog(getString(R.string.sq_click_tip), R.mipmap.pic_click_tip, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSelectBleDeviceAndGroup$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                    ActKnobPanelSelectBleDeviceAndGroup.this.lambda$subscribe$0(role, imageTipDialog);
                }
            });
        } else {
            lambda$subscribe$0(role, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: panelSubscribe, reason: merged with bridge method [inline-methods] */
    public void lambda$subscribe$0(Object relateObject, final ImageTipDialog dialog) {
        int groupAddress;
        int groupAgreementId;
        int i;
        int i2;
        showLoadingDialog(getString(R.string.subscribing));
        int unicastAddress = ((BleParam) this.controlDevice.getParam(BleParam.class)).getUnicastAddress();
        if (relateObject instanceof Device) {
            Device device = (Device) relateObject;
            groupAddress = ((BleParam) device.getParam(BleParam.class)).getUnicastAddress();
            groupAgreementId = ProductRepository.getAgreementIdByPid(device.getProductId());
        } else {
            Group group = (Group) relateObject;
            groupAddress = group.getGroupAddress();
            groupAgreementId = ProductRepository.getGroupAgreementId(group.getModuleType(), group.getControlType());
        }
        int i3 = groupAgreementId;
        int i4 = groupAddress;
        if (this.relateInfoAssistant.getRelateInfo(this.relatePosition).type == 7) {
            this.relateInfoAssistant.getRelateInfo(this.relatePosition).keyActionExtra = 4;
            i2 = 4;
        } else {
            if (!ProductRepository.isDaliLightGroup(relateObject)) {
                i = getRelateDeviceType();
                this.relateInfoAssistant.getRelateInfo(this.relatePosition).keyActionExtra = i;
            } else {
                i = this.daliLightType;
                this.relateInfoAssistant.getRelateInfo(this.relatePosition).keyActionExtra = this.daliLightType + 100;
            }
            i2 = i;
        }
        int subscribeZone = getSubscribeZone();
        SettingAssistant settingCmdAssistant = CmdAssistant.getSettingCmdAssistant(null, new int[0]);
        int i5 = this.relatePosition;
        settingCmdAssistant.subscribeInSwitchPanel(this, 1 << i5, unicastAddress, i4, i3, subscribeZone, this.relateInfoAssistant.getRelateInfo(i5).action, this.relateInfoAssistant.getRelateInfo(this.relatePosition).type, i2, new IAction() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSelectBleDeviceAndGroup$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActKnobPanelSelectBleDeviceAndGroup.this.lambda$panelSubscribe$1(dialog, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$panelSubscribe$1(ImageTipDialog imageTipDialog, Boolean bool) {
        if (bool.booleanValue()) {
            if (!this.relateInfoAssistant.isProPanel()) {
                uploadDeviceData(false);
            } else {
                dismissLoadingDialog();
                showSetScreenDialog(this.controlDevice);
            }
        } else {
            dismissLoadingDialog();
            showFailDialog();
        }
        if (imageTipDialog != null) {
            imageTipDialog.dismissDialog();
        }
    }

    public void showSetScreenDialog(final Object controlObject) {
        if (RelaySeparationHelper.isRelaySeparationDevice(controlObject)) {
            setScreenDefault(controlObject, true);
        } else {
            setScreenDefault(controlObject, false);
            MessageDialog.show(this, getString(R.string.save_success), getString(R.string.set_screen_tip)).setCancelable(false).setOkButton(getString(R.string.modify_now), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSelectBleDeviceAndGroup$$ExternalSyntheticLambda8
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$showSetScreenDialog$2;
                    lambda$showSetScreenDialog$2 = ActKnobPanelSelectBleDeviceAndGroup.this.lambda$showSetScreenDialog$2(controlObject, baseDialog, view);
                    return lambda$showSetScreenDialog$2;
                }
            }).setCancelButton(this.activity.getString(R.string.not_modify), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSelectBleDeviceAndGroup$$ExternalSyntheticLambda9
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    return ActKnobPanelSelectBleDeviceAndGroup.lambda$showSetScreenDialog$3(baseDialog, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showSetScreenDialog$2(Object obj, BaseDialog baseDialog, View view) {
        this.goModifyScreen = true;
        setScreenDefault(obj, true);
        return false;
    }

    private void setScreenDefault(Object controlObject, final boolean finish) {
        RelatedInfoExtParam.RelateInfo relateInfo = this.relateInfoAssistant.getRelateInfo(this.relatePosition);
        relateInfo.screenType = RelatedInfoExtParam.ScreenType.ScreenTypeWord.getValue();
        String realString = StringUtils.getRealString(this.relateInfoAssistant.getSwitchScreenBigIcon() == 2, RelateInfoUtils.getRelateInfoString(relateInfo));
        relateInfo.screenStr = realString;
        this.relateInfoAssistant.setRelateInfo(this.relatePosition, relateInfo);
        CmdAssistant.getDeviceAssistant(controlObject, new int[0]).setPanelScreenData(this, this.relatePosition, realString, new IAction() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSelectBleDeviceAndGroup$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActKnobPanelSelectBleDeviceAndGroup.this.lambda$setScreenDefault$4(finish, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setScreenDefault$4(boolean z, ResponseMsg responseMsg) {
        if (responseMsg != null && (responseMsg.getStateCode() == 0 || responseMsg.getStateCode() == 153)) {
            uploadDeviceData(z);
        } else {
            showErrorDialog(this.activity.getString(R.string.save_fail));
        }
    }

    private void uploadDeviceData(final boolean finish) {
        final String extParamString = this.relateInfoAssistant.getExtParamString();
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.controlDevice.getDeviceId(), extParamString).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSelectBleDeviceAndGroup$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActKnobPanelSelectBleDeviceAndGroup.this.lambda$uploadDeviceData$5((Disposable) obj);
            }
        }).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSelectBleDeviceAndGroup$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActKnobPanelSelectBleDeviceAndGroup.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSelectBleDeviceAndGroup$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActKnobPanelSelectBleDeviceAndGroup.this.lambda$uploadDeviceData$6(extParamString, finish, obj);
            }
        }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSelectBleDeviceAndGroup.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                ActKnobPanelSelectBleDeviceAndGroup.this.unBindRelateInfo();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDeviceData$5(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDeviceData$6(String str, boolean z, Object obj) throws Exception {
        this.controlDevice.setExtParam(str);
        Injection.repo().device().saveDevice(this.controlDevice);
        SmartToast.showShort(R.string.save_success);
        if (this.goModifyScreen) {
            NavUtils.destination(ActSetScreenDisplay.class).withLong(Constants.CONTROL_ID, this.controlDevice.getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) this.controlDevice)).withString(Constants.PRODUCT_ID, this.controlDevice.getProductId()).navigation(this);
            this.goModifyScreen = false;
        }
        if (z) {
            lambda$edit$2(this);
        }
    }

    public void unBindRelateInfo() {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.unsubscribing));
        Device device = (Device) this.relateObject;
        CmdAssistant.getSettingCmdAssistant(null, new int[0]).unSubscribeInSwitchPanel(ActivityUtils.getTopActivity(), ((BleParam) device.getParam(BleParam.class)).getUnicastAddress(), 1 << this.relatePosition, ProductRepository.getAgreementIdByPid(device.getProductId()), new IAction() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSelectBleDeviceAndGroup$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActKnobPanelSelectBleDeviceAndGroup.this.lambda$unBindRelateInfo$7((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unBindRelateInfo$7(Boolean bool) {
        if (bool.booleanValue()) {
            this.relateInfoAssistant.resetSmartPanelRelateInfo(this.relatePosition);
        } else {
            dismissLoadingDialog();
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        ProductInfo productInfoByPid = ProductRepository.getProductInfoByPid(device.getProductId());
        if (productInfoByPid != null) {
            if ("02".equalsIgnoreCase(productInfoByPid.getProductKey())) {
                return ("09".equals(productInfoByPid.getSubProductKey()) || "0C".equals(productInfoByPid.getSubProductKey()) || "0D".equals(productInfoByPid.getSubProductKey())) ? false : true;
            }
            if ("07".equalsIgnoreCase(productInfoByPid.getProductKey()) || "08".equalsIgnoreCase(productInfoByPid.getProductKey())) {
                return true;
            }
        }
        return (device.getProductId().equals(ProductId.ID_BLE_DRY_CONTACT) && device.getParam(DryContactBleParam.class) != null && ((DryContactBleParam) device.getParam(DryContactBleParam.class)).getSubType() == 3) || ProductRepository.isDaliLightGroup(device);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterGroup(Group group) {
        int lightColorType;
        return "3".equalsIgnoreCase(group.getModuleType()) && ((lightColorType = ProductRepository.getLightColorType((Object) group)) == 1 || lightColorType == 2 || lightColorType == 3 || lightColorType == 4 || lightColorType == 5 || lightColorType == 14 || lightColorType == 16 || lightColorType == 101) && group.getDeviceIds() != null;
    }

    private int getRelateDeviceType() {
        Object obj = this.relateObject;
        if (obj instanceof Group) {
            return Integer.parseInt(((Group) obj).getControlType());
        }
        Device device = (Device) obj;
        if (device.getParam(BleParam.class) != null) {
            return ((BleParam) device.getParam(BleParam.class)).getDeviceType();
        }
        return 0;
    }

    private int getSubscribeZone() {
        if (ProductRepository.isDaliLightGroup(this.relateObject)) {
            return DaliProHelper.getZoneNum(this.relateObject);
        }
        return 1;
    }

    private void showFailDialog() {
        runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSelectBleDeviceAndGroup.2
            @Override // java.lang.Runnable
            public void run() {
                ViewHelpUtil.showBindFailDialog(ActKnobPanelSelectBleDeviceAndGroup.this);
            }
        });
    }

    private void showKnobBindDialog(Group group) {
        final ArrayList arrayList = new ArrayList();
        if (DaliProHelper.isSupportDim(group)) {
            arrayList.add("DIM");
        }
        if (DaliProHelper.isSupportCt(group)) {
            arrayList.add("CT");
        }
        if (DaliProHelper.isSupportColor(group)) {
            arrayList.add("RGB");
        }
        SelectListDialog.asDefault(true).setTitle(getString(R.string.please_choose)).setConfirmString(getString(R.string.confirm)).setCancelString(getString(R.string.cancel)).setSelectPosition(0).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSelectBleDeviceAndGroup$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActKnobPanelSelectBleDeviceAndGroup.this.lambda$showKnobBindDialog$8(arrayList, (Integer) obj);
            }
        }).setSelectList(arrayList).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showKnobBindDialog$8(List list, Integer num) {
        if ("DIM".equals(list.get(num.intValue()))) {
            this.daliLightType = 1;
        } else if ("CT".equals(list.get(num.intValue()))) {
            this.daliLightType = 2;
        } else {
            this.daliLightType = 3;
        }
        subscribe((Role) this.relateObject);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            this.daliLightType = data.getIntExtra(Constants.LIGHT_TYPE, 0);
            this.relateObject = SceneHelper.instance().controlObject;
            this.relateInfoAssistant.getRelateInfo(this.relatePosition).objectId = ((Role) this.relateObject).getObjectId();
            subscribe((Role) this.relateObject);
        }
    }
}