package com.ltech.smarthome.ui.device.smartpanel;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.device.screenpanel.ActSetScreenDisplay;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.ui.scene.ActCtSelectColor;
import com.ltech.smarthome.ui.scene.ActDimSelectColor;
import com.ltech.smarthome.ui.scene.ActSelectColor;
import com.ltech.smarthome.ui.scene.ActSelectDaliColor;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.ltech.smarthome.utils.cmd_assistant.SettingAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.param.SettingCmdParam;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSmartPanelSelectActionVM extends BaseViewModel implements ISelectAction {
    public int clickType;
    public Device controlDevice;
    public Group controlGroup;
    public long controlId;
    private boolean goModifyScreen;
    public boolean groupControl;
    public boolean isNightUp;
    public String productId;
    public RelateInfoAssistant relateInfoAssistant;
    public Object relateObject;
    public int relatePosition;
    public int relateType;
    public BindValue selectBindValue;
    public ImageTipDialog tipDialog;

    static /* synthetic */ boolean lambda$showSetScreenDialog$13(BaseDialog baseDialog, View view) {
        return false;
    }

    static /* synthetic */ boolean lambda$subscribeScene$4(BaseDialog baseDialog, View view) {
        return false;
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

    /* JADX INFO: Access modifiers changed from: private */
    public BaseNormalActivity getActivity() {
        return (BaseNormalActivity) ActivityUtils.getTopActivity();
    }

    public void initRelateAssistant(long controlId) {
        this.controlId = controlId;
        if (this.groupControl) {
            this.controlGroup = Injection.repo().group().getGroupById(controlId);
            this.relateInfoAssistant = new RelateInfoAssistant(this.controlGroup);
        } else {
            this.controlDevice = Injection.repo().device().getDeviceById(controlId);
            this.relateInfoAssistant = new RelateInfoAssistant(this.controlDevice);
        }
    }

    public int getRelateType() {
        int i = this.relateType;
        if (i == 2) {
            return Integer.parseInt(((Group) this.relateObject).getControlType());
        }
        if (i == 1) {
            Device device = (Device) this.relateObject;
            if (device.getParam(BleParam.class) != null) {
                return ((BleParam) device.getParam(BleParam.class)).getDeviceType();
            }
        }
        return 0;
    }

    public static List<BindValue> getActionValueList(Object object, int lightType) {
        if (lightType == 1) {
            return LightBindUtilsKt.getDimList();
        }
        if (lightType == 2) {
            if (ProductRepository.isEbSupportLight(object)) {
                return LightBindUtilsKt.getB5CtList();
            }
            return LightBindUtilsKt.getCtList();
        }
        if (lightType == 3) {
            return LightBindUtilsKt.getRgbList();
        }
        if (lightType == 4) {
            return LightBindUtilsKt.getRgbwList();
        }
        if (lightType == 7) {
            return LightBindUtilsKt.getSwitchList();
        }
        if (lightType == 17) {
            return LightBindUtilsKt.getSpiList();
        }
        if (lightType == 20) {
            return LightBindUtilsKt.getRgbcwCcList();
        }
        return LightBindUtilsKt.getRgbcwList();
    }

    public static List<BindValue> getDaliActionValueList(int lightType, Role role) {
        ArrayList arrayList = new ArrayList();
        if (lightType == 1) {
            arrayList.addAll(LightBindUtilsKt.getDimList());
            arrayList.remove(0);
            arrayList.remove(0);
            return arrayList;
        }
        if (lightType == 3) {
            if (DaliProHelper.isSupportDim(role)) {
                arrayList.addAll(LightBindUtilsKt.getDimRgbList());
                return arrayList;
            }
            arrayList.addAll(LightBindUtilsKt.getRgbList());
            arrayList.remove(0);
            arrayList.remove(0);
            arrayList.remove(0);
            return arrayList;
        }
        if (lightType == 5) {
            if (DaliProHelper.isSupportDim(role)) {
                arrayList.addAll(LightBindUtilsKt.getDimCtRgbList());
                return arrayList;
            }
            arrayList.addAll(LightBindUtilsKt.getCtRgbList());
            return arrayList;
        }
        if (DaliProHelper.isSupportDim(role)) {
            arrayList.addAll(LightBindUtilsKt.getDimCtList());
            return arrayList;
        }
        arrayList.addAll(LightBindUtilsKt.getCtList());
        arrayList.remove(0);
        arrayList.remove(0);
        return arrayList;
    }

    public static String getKeyActionName(Context context, Object object, int lightType, int action) {
        for (BindValue bindValue : getActionValueList(object, lightType)) {
            if (bindValue.getKey() == action) {
                return context.getString(bindValue.getNameRes());
            }
        }
        return "";
    }

    public static String getDaliKeyActionName(Context context, int lightType, Role role, int action) {
        for (BindValue bindValue : getDaliActionValueList(DaliProHelper.convertDaliType(role), role)) {
            if (bindValue.getKey() == action) {
                if (bindValue.getLightType() == 0) {
                    return context.getString(bindValue.getNameRes());
                }
                if (bindValue.getLightType() == lightType) {
                    return context.getString(bindValue.getNameRes());
                }
            }
        }
        return "";
    }

    public void goSelection(int lightType) {
        NavUtils.Builder destination;
        if (lightType == 1) {
            destination = NavUtils.destination(ActDimSelectColor.class);
        } else if (lightType == 2) {
            destination = NavUtils.destination(ActCtSelectColor.class).withLong(Constants.CONTROL_ID, ProductRepository.getControlId(SceneHelper.instance().controlObject)).withBoolean(Constants.GROUP_CONTROL, ProductRepository.isGroup(SceneHelper.instance().controlObject));
        } else {
            destination = NavUtils.destination(ActSelectColor.class);
        }
        navigation(destination.withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(SceneHelper.instance().controlObject)).withString(Constants.PRODUCT_ID, this.productId).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode());
    }

    public void goDaliSelection(int lightType) {
        navigation(NavUtils.destination(ActSelectDaliColor.class).withInt(Constants.LIGHT_TYPE, lightType).withLong(Constants.CONTROL_ID, ProductRepository.getControlId(SceneHelper.instance().controlObject)).withBoolean(Constants.GROUP_CONTROL, ProductRepository.isGroup(SceneHelper.instance().controlObject)).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode());
    }

    public void subscribe() {
        int unicastAddress;
        int groupAddress;
        int groupAgreementId;
        if (this.groupControl) {
            unicastAddress = this.controlGroup.getGroupAddress();
        } else {
            unicastAddress = ((BleParam) this.controlDevice.getParam(BleParam.class)).getUnicastAddress();
        }
        int i = unicastAddress;
        Object obj = this.relateObject;
        if (obj instanceof Device) {
            Device device = (Device) obj;
            groupAddress = ((BleParam) device.getParam(BleParam.class)).getUnicastAddress();
            groupAgreementId = ProductRepository.getAgreementIdByPid(device.getProductId());
        } else {
            Group group = (Group) obj;
            groupAddress = group.getGroupAddress();
            groupAgreementId = ProductRepository.getGroupAgreementId(group.getModuleType(), group.getControlType());
        }
        int i2 = groupAgreementId;
        final int i3 = groupAddress;
        int relationLightType = getRelationLightType(this.relateObject);
        final int subscribeZone = getSubscribeZone();
        final ArrayList arrayList = new ArrayList();
        if (this.relateInfoAssistant.getRelateInfo(this.relatePosition).action == 16 || this.relateInfoAssistant.getRelateInfo(this.relatePosition).action == 17 || this.relateInfoAssistant.getRelateInfo(this.relatePosition).action == 18 || this.relateInfoAssistant.getRelateInfo(this.relatePosition).action == 19 || this.relateInfoAssistant.getRelateInfo(this.relatePosition).action == 20 || this.relateInfoAssistant.getRelateInfo(this.relatePosition).action == 34 || this.relateInfoAssistant.getRelateInfo(this.relatePosition).action == 35 || ProductRepository.isCgdPro(this.relateObject)) {
            for (byte b2 : StringUtils.hexStringToByte(this.relateInfoAssistant.getRelateInfo(this.relatePosition).wholeDataExtra)) {
                arrayList.add(Integer.valueOf(b2));
            }
        } else {
            arrayList.add(Integer.valueOf(relationLightType));
            this.relateInfoAssistant.getRelateInfo(this.relatePosition).keyActionExtra = arrayList.get(0).intValue();
            this.relateInfoAssistant.getRelateInfo(this.relatePosition).wholeDataExtra = StringUtils.toHexString(arrayList.get(0).intValue());
        }
        SettingAssistant settingCmdHelper = getSettingCmdHelper();
        BaseNormalActivity activity = getActivity();
        int i4 = this.relatePosition;
        settingCmdHelper.subscribeInSwitchPanel(activity, 1 << i4, i, i3, i2, subscribeZone, this.relateInfoAssistant.getRelateInfo(i4).action, this.relateInfoAssistant.getRelateInfo(this.relatePosition).type, arrayList, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectActionVM$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj2) {
                ActSmartPanelSelectActionVM.this.lambda$subscribe$0(i3, subscribeZone, arrayList, (Boolean) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$subscribe$0(int i, int i2, List list, Boolean bool) {
        if (bool.booleanValue()) {
            if (!this.relateInfoAssistant.isProPanel()) {
                if (this.groupControl) {
                    uploadGroupData(true);
                } else {
                    uploadDeviceData(true);
                }
            } else {
                dismissLoadingDialog();
                showSetScreenDialog(this.groupControl ? this.controlGroup : this.controlDevice);
            }
            if (!this.groupControl) {
                ReplaceHelper instance = ReplaceHelper.instance();
                SettingAssistant settingCmdHelper = getSettingCmdHelper();
                int i3 = this.relatePosition;
                instance.addBackupData(UpdateBackDataRequest.BIND, settingCmdHelper.subscribeInSwitchPanel(1 << i3, i, i2, this.relateInfoAssistant.getRelateInfo(i3).action, this.relateInfoAssistant.getRelateInfo(this.relatePosition).type, (List<Integer>) list));
            }
            adjustKRange(this.groupControl ? this.controlGroup : this.controlDevice);
            if (this.groupControl) {
                return;
            }
            ReplaceHelper.instance().backupIndexData(getLifecycleOwner(), this.controlDevice.getDeviceId(), this.relatePosition + 1);
            return;
        }
        dismissLoadingDialog();
        showFailDialog();
    }

    public int getRelationLightType(Object object) {
        if (!ProductRepository.isDaliLightGroup(object)) {
            return getRelateDeviceType();
        }
        BindValue bindValue = this.selectBindValue;
        if (bindValue != null && bindValue.getLightType() != 0) {
            return this.selectBindValue.getLightType();
        }
        return DaliProHelper.convertDaliType(object);
    }

    public void subscribe(final int type, final int keyActionExtra) {
        int unicastAddress;
        int groupAddress;
        int groupAgreementId;
        if (this.groupControl) {
            unicastAddress = this.controlGroup.getGroupAddress();
        } else {
            unicastAddress = ((BleParam) this.controlDevice.getParam(BleParam.class)).getUnicastAddress();
        }
        int i = unicastAddress;
        Object obj = this.relateObject;
        if (obj instanceof Device) {
            Device device = (Device) obj;
            groupAddress = ((BleParam) device.getParam(BleParam.class)).getUnicastAddress();
            groupAgreementId = ProductRepository.getAgreementIdByPid(device.getProductId());
        } else {
            Group group = (Group) obj;
            groupAddress = group.getGroupAddress();
            groupAgreementId = ProductRepository.getGroupAgreementId(group.getModuleType(), group.getControlType());
        }
        int i2 = groupAgreementId;
        final int i3 = groupAddress;
        final int subscribeZone = getSubscribeZone();
        SettingAssistant settingCmdHelper = getSettingCmdHelper();
        BaseNormalActivity activity = getActivity();
        int i4 = this.relatePosition;
        settingCmdHelper.subscribeInSwitchPanel(activity, 1 << i4, i, i3, i2, subscribeZone, this.relateInfoAssistant.getRelateInfo(i4).action, type, keyActionExtra, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectActionVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj2) {
                ActSmartPanelSelectActionVM.this.lambda$subscribe$1(i3, subscribeZone, type, keyActionExtra, (Boolean) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$subscribe$1(int i, int i2, int i3, int i4, Boolean bool) {
        if (bool.booleanValue()) {
            if (!this.relateInfoAssistant.isProPanel()) {
                if (this.groupControl) {
                    uploadGroupData(true);
                } else {
                    uploadDeviceData(true);
                }
            } else {
                dismissLoadingDialog();
                showSetScreenDialog(this.groupControl ? this.controlGroup : this.controlDevice);
            }
            if (!this.groupControl) {
                ReplaceHelper instance = ReplaceHelper.instance();
                SettingAssistant settingCmdHelper = getSettingCmdHelper();
                int i5 = this.relatePosition;
                instance.addBackupData(UpdateBackDataRequest.BIND, settingCmdHelper.subscribeInSwitchPanel(1 << i5, i, i2, this.relateInfoAssistant.getRelateInfo(i5).action, i3, i4));
            }
            adjustKRange(this.groupControl ? this.controlGroup : this.controlDevice);
            if (this.groupControl) {
                return;
            }
            ReplaceHelper.instance().backupIndexData(getLifecycleOwner(), this.controlDevice.getDeviceId(), this.relatePosition + 1);
            return;
        }
        dismissLoadingDialog();
        showFailDialog();
    }

    public void subscribe(int position) {
        int unicastAddress;
        int groupAddress;
        int groupAgreementId;
        if (this.groupControl) {
            unicastAddress = this.controlGroup.getGroupAddress();
        } else {
            unicastAddress = ((BleParam) this.controlDevice.getParam(BleParam.class)).getUnicastAddress();
        }
        int i = unicastAddress;
        Object obj = this.relateObject;
        if (obj instanceof Device) {
            Device device = (Device) obj;
            groupAddress = ((BleParam) device.getParam(BleParam.class)).getUnicastAddress();
            groupAgreementId = ProductRepository.getAgreementIdByPid(device.getProductId());
        } else {
            Group group = (Group) obj;
            groupAddress = group.getGroupAddress();
            groupAgreementId = ProductRepository.getGroupAgreementId(group.getModuleType(), group.getControlType());
        }
        final int i2 = groupAddress;
        final int relateDeviceType = getRelateDeviceType();
        final int i3 = 1 << position;
        SettingAssistant settingCmdHelper = getSettingCmdHelper();
        BaseNormalActivity activity = getActivity();
        int i4 = this.relatePosition;
        settingCmdHelper.subscribeInSwitchPanel(activity, 1 << i4, i, i2, groupAgreementId, i3, this.relateInfoAssistant.getRelateInfo(i4).action, this.relateInfoAssistant.getRelateInfo(this.relatePosition).type, relateDeviceType, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectActionVM$$ExternalSyntheticLambda14
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj2) {
                ActSmartPanelSelectActionVM.this.lambda$subscribe$2(i2, i3, relateDeviceType, (Boolean) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$subscribe$2(int i, int i2, int i3, Boolean bool) {
        if (bool.booleanValue()) {
            if (!this.relateInfoAssistant.isProPanel()) {
                if (this.groupControl) {
                    uploadGroupData(true);
                } else {
                    uploadDeviceData(true);
                }
            } else {
                dismissLoadingDialog();
                showSetScreenDialog(this.groupControl ? this.controlGroup : this.controlDevice);
            }
            if (!this.groupControl) {
                ReplaceHelper instance = ReplaceHelper.instance();
                SettingAssistant settingCmdHelper = getSettingCmdHelper();
                int i4 = this.relatePosition;
                instance.addBackupData(UpdateBackDataRequest.BIND, settingCmdHelper.subscribeInSwitchPanel(1 << i4, i, i2, this.relateInfoAssistant.getRelateInfo(i4).action, this.relateInfoAssistant.getRelateInfo(this.relatePosition).type, i3));
            }
            adjustKRange(this.groupControl ? this.controlGroup : this.controlDevice);
            if (this.groupControl) {
                return;
            }
            ReplaceHelper.instance().backupIndexData(getLifecycleOwner(), this.controlDevice.getDeviceId(), this.relatePosition + 1);
            return;
        }
        dismissLoadingDialog();
        showFailDialog();
    }

    public void rcSubscribe(int position) {
        int unicastAddress;
        int agreementIdByPid;
        int publicationAddress = ((BleParam) this.controlDevice.getParam(BleParam.class)).getPublicationAddress();
        if (this.relateType == 2) {
            Group group = (Group) this.relateObject;
            unicastAddress = group.getGroupAddress();
            agreementIdByPid = ProductRepository.getGroupAgreementId(group.getModuleType(), group.getControlType());
        } else {
            Device device = (Device) this.relateObject;
            unicastAddress = ((BleParam) device.getParam(BleParam.class)).getUnicastAddress();
            agreementIdByPid = ProductRepository.getAgreementIdByPid(device.getProductId());
        }
        int i = unicastAddress;
        SettingAssistant settingCmdAssistant = CmdAssistant.getSettingCmdAssistant(null, new int[0]);
        BaseNormalActivity activity = getActivity();
        int i2 = 1 << position;
        int i3 = this.relatePosition;
        settingCmdAssistant.subscribe1(activity, i, publicationAddress, agreementIdByPid, i2, 1 << i3, this.relateInfoAssistant.getRelateInfo(i3).action, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectActionVM$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelSelectActionVM.this.lambda$rcSubscribe$3((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$rcSubscribe$3(Boolean bool) {
        if (bool.booleanValue()) {
            uploadDeviceData(false);
        } else {
            dismissLoadingDialog();
            showFailDialog();
        }
    }

    public void subscribeScene(boolean isLocalScene) {
        int unicastAddress;
        if (this.groupControl) {
            unicastAddress = this.controlGroup.getGroupAddress();
        } else {
            unicastAddress = ((BleParam) this.controlDevice.getParam(BleParam.class)).getUnicastAddress();
        }
        int i = unicastAddress;
        int unicastAddress2 = isLocalScene ? 65025 : ((BleParam) ((Device) this.relateObject).getParam(BleParam.class)).getUnicastAddress();
        final int subscribeZone = getSubscribeZone();
        int i2 = this.relateInfoAssistant.getRelateInfo(this.relatePosition).action;
        int i3 = this.relateInfoAssistant.getRelateInfo(this.relatePosition).type;
        int i4 = this.relateInfoAssistant.getRelateInfo(this.relatePosition).keyActionExtra;
        if (this.clickType == 2) {
            i2 = this.relateInfoAssistant.getRelateLongClickInfo(this.relatePosition).action;
            i3 = this.relateInfoAssistant.getRelateLongClickInfo(this.relatePosition).type;
            i4 = this.relateInfoAssistant.getRelateLongClickInfo(this.relatePosition).keyActionExtra;
        }
        final int i5 = i2;
        final int i6 = i3;
        final int i7 = i4;
        final int i8 = unicastAddress2;
        getSettingCmdHelper().subscribeInSwitchPanelScene(getActivity(), this.isNightUp ? 0 : 1 << this.relatePosition, i, i8, 2, subscribeZone, i5, i6, i7, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectActionVM$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelSelectActionVM.this.lambda$subscribeScene$5(i8, subscribeZone, i5, i6, i7, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$subscribeScene$5(int i, int i2, int i3, int i4, int i5, ResponseMsg responseMsg) {
        ImageTipDialog imageTipDialog = this.tipDialog;
        if (imageTipDialog != null) {
            imageTipDialog.dismissDialog();
            this.tipDialog = null;
        }
        if (responseMsg != null) {
            if (responseMsg.getStateCode() == 0) {
                if (!this.relateInfoAssistant.isProPanel()) {
                    if (this.groupControl) {
                        uploadGroupData(true);
                    } else {
                        uploadDeviceData(true);
                    }
                } else {
                    dismissLoadingDialog();
                    if (this.clickType == 2) {
                        setScreenDefault(this.groupControl ? this.controlGroup : this.controlDevice, true);
                    } else {
                        showSetScreenDialog(this.groupControl ? this.controlGroup : this.controlDevice);
                    }
                }
                if (this.groupControl) {
                    return;
                }
                if (this.clickType == 2) {
                    ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.SWITCH_ACTION_DELAY, null);
                }
                ReplaceHelper.instance().addBackupData(this.clickType == 2 ? UpdateBackDataRequest.LONG_BIND : UpdateBackDataRequest.BIND, getSettingCmdHelper().subscribeInSwitchPanelScene(1 << this.relatePosition, i, i2, i3, i4, i5));
                ReplaceHelper.instance().backupIndexData(getLifecycleOwner(), this.controlDevice.getDeviceId(), this.relatePosition + 1);
                return;
            }
            if (responseMsg.getStateCode() == 3) {
                dismissLoadingDialog();
                MessageDialog.show(getActivity(), getActivity().getString(R.string.app_str_operation_failure), getActivity().getString(R.string.local_scene_error_03)).setOkButton(getActivity().getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectActionVM$$ExternalSyntheticLambda16
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        return ActSmartPanelSelectActionVM.lambda$subscribeScene$4(baseDialog, view);
                    }
                });
                return;
            }
            return;
        }
        dismissLoadingDialog();
        showFailDialog();
    }

    public void eurSubscribeScene(boolean isLocalScene) {
        int unicastAddress;
        if (this.groupControl) {
            unicastAddress = this.controlGroup.getGroupAddress();
        } else {
            unicastAddress = ((BleParam) this.controlDevice.getParam(BleParam.class)).getUnicastAddress();
        }
        int i = unicastAddress;
        final int unicastAddress2 = isLocalScene ? 65025 : ((BleParam) ((Device) this.relateObject).getParam(BleParam.class)).getUnicastAddress();
        final int subscribeZone = getSubscribeZone();
        final ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(isLocalScene ? 3 : 2));
        arrayList.add(Integer.valueOf(this.relateInfoAssistant.getRelateSceneInfo(this.relatePosition).keyActionExtra));
        SettingAssistant settingCmdAssistant = CmdAssistant.getSettingCmdAssistant(null, new int[0]);
        BaseNormalActivity activity = getActivity();
        int i2 = this.relatePosition;
        settingCmdAssistant.subscribeInEurPanel(activity, 1 << i2, i, unicastAddress2, 2, subscribeZone, this.relateInfoAssistant.getRelateSceneInfo(i2).action, 3, arrayList, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectActionVM$$ExternalSyntheticLambda15
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelSelectActionVM.this.lambda$eurSubscribeScene$6(unicastAddress2, subscribeZone, arrayList, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$eurSubscribeScene$6(int i, int i2, List list, ResponseMsg responseMsg) {
        ImageTipDialog imageTipDialog = this.tipDialog;
        if (imageTipDialog != null) {
            imageTipDialog.dismissDialog();
            this.tipDialog = null;
        }
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            if (responseMsg.getStateCode() == 0) {
                if (this.groupControl) {
                    uploadGroupData(true);
                    return;
                }
                ReplaceHelper instance = ReplaceHelper.instance();
                LifecycleOwner lifecycleOwner = getLifecycleOwner();
                long deviceId = this.controlDevice.getDeviceId();
                SettingAssistant settingCmdAssistant = CmdAssistant.getSettingCmdAssistant(null, new int[0]);
                int i3 = this.relatePosition;
                instance.backupIndexData(lifecycleOwner, deviceId, UpdateBackDataRequest.SCENE_BIND, settingCmdAssistant.subscribeInEurPanel(1 << i3, i, i2, this.relateInfoAssistant.getRelateSceneInfo(i3).action, 3, list), this.relatePosition + 1);
                uploadDeviceData(true);
                return;
            }
            if (responseMsg.getStateCode() == 3) {
                dismissLoadingDialog();
                MessageDialog.show(getActivity(), getActivity().getString(R.string.app_str_operation_failure), getActivity().getString(R.string.local_scene_error_03)).setOkButton(getActivity().getString(R.string.ok));
                return;
            }
            return;
        }
        dismissLoadingDialog();
        showFailDialog();
    }

    private int getRelateDeviceType() {
        int i = this.relateType;
        if (i == 2) {
            return Integer.parseInt(((Group) this.relateObject).getControlType());
        }
        if (i == 1) {
            Device device = (Device) this.relateObject;
            if (device.getParam(BleParam.class) != null) {
                int deviceType = ((BleParam) device.getParam(BleParam.class)).getDeviceType();
                if (deviceType == 20) {
                    return 5;
                }
                return deviceType;
            }
        }
        return 0;
    }

    private int getSubscribeZone() {
        if (RelaySeparationHelper.isRelaySeparationSub(this.relateObject)) {
            return RelaySeparationHelper.getZoneNum(this.relateObject);
        }
        if (ProductRepository.isCgdPro(this.relateObject)) {
            return DaliProHelper.BROADCAST_ADD;
        }
        if (ProductRepository.isDaliLightGroup(this.relateObject)) {
            return DaliProHelper.getZoneNum(this.relateObject);
        }
        return 1;
    }

    public void unBindRelateInfo() {
        int groupAddress;
        int groupAgreementId;
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.unsubscribing));
        Object obj = this.relateObject;
        if (obj instanceof Device) {
            Device device = (Device) obj;
            groupAddress = ((BleParam) device.getParam(BleParam.class)).getUnicastAddress();
            groupAgreementId = ProductRepository.getAgreementIdByPid(device.getProductId());
        } else {
            Group group = (Group) obj;
            groupAddress = group.getGroupAddress();
            groupAgreementId = ProductRepository.getGroupAgreementId(group.getModuleType(), group.getControlType());
        }
        SettingAssistant settingCmdHelper = getSettingCmdHelper();
        Activity topActivity = ActivityUtils.getTopActivity();
        settingCmdHelper.unSubscribeInSwitchPanel(topActivity, groupAddress, 1 << this.relatePosition, groupAgreementId, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectActionVM$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj2) {
                ActSmartPanelSelectActionVM.this.lambda$unBindRelateInfo$7((Boolean) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unBindRelateInfo$7(Boolean bool) {
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

    private void uploadGroupData(final boolean finish) {
        final String extParamString = this.relateInfoAssistant.getExtParamString();
        ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(this.controlGroup.getGroupId(), extParamString).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectActionVM$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSelectActionVM.this.lambda$uploadGroupData$8((Disposable) obj);
            }
        }).doFinally(new ActSmartPanelSelectActionVM$$ExternalSyntheticLambda10(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectActionVM$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSelectActionVM.this.lambda$uploadGroupData$9(extParamString, finish, obj);
            }
        }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectActionVM.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                ActSmartPanelSelectActionVM.this.unBindRelateInfo();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadGroupData$8(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadGroupData$9(String str, boolean z, Object obj) throws Exception {
        this.controlGroup.setExtParam(str);
        Injection.repo().group().saveGroup(this.controlGroup);
        SmartToast.showShort(R.string.save_success);
        if (this.goModifyScreen) {
            if (ProductRepository.getLightColorType((Object) this.controlGroup) != 21 && ProductRepository.getLightColorType((Object) this.controlGroup) != 19) {
                NavUtils.destination(ActSetScreenDisplay.class).withLong(Constants.CONTROL_ID, this.controlGroup.getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) this.controlGroup)).withString(Constants.PRODUCT_ID, this.controlGroup.getGroupKey()).withBoolean(Constants.GROUP_CONTROL, true).navigation(ActivityUtils.getTopActivity());
            }
            this.goModifyScreen = false;
        }
        if (z) {
            lambda$edit$1(getActivity());
        }
    }

    private void uploadDeviceData(final boolean finish) {
        final String extParamString = this.relateInfoAssistant.getExtParamString();
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.controlDevice.getDeviceId(), extParamString).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectActionVM$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSelectActionVM.this.lambda$uploadDeviceData$10((Disposable) obj);
            }
        }).doFinally(new ActSmartPanelSelectActionVM$$ExternalSyntheticLambda10(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectActionVM$$ExternalSyntheticLambda11
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSelectActionVM.this.lambda$uploadDeviceData$11(extParamString, finish, obj);
            }
        }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectActionVM.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                ActSmartPanelSelectActionVM.this.unBindRelateInfo();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDeviceData$10(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDeviceData$11(String str, boolean z, Object obj) throws Exception {
        this.controlDevice.setExtParam(str);
        Injection.repo().device().saveDevice(this.controlDevice);
        SmartToast.showShort(R.string.save_success);
        if (this.goModifyScreen) {
            if (!this.controlDevice.getProductId().equals(ProductId.ID_SMART_PANEL_G4) && !this.controlDevice.getProductId().equals(ProductId.ID_SMART_PANEL_G4_PRO) && !this.controlDevice.getProductId().equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                NavUtils.destination(ActSetScreenDisplay.class).withLong(Constants.CONTROL_ID, this.controlDevice.getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) this.controlDevice)).withString(Constants.PRODUCT_ID, this.controlDevice.getProductId()).navigation(ActivityUtils.getTopActivity());
            }
            this.goModifyScreen = false;
        }
        if (z) {
            lambda$edit$1(getActivity());
        }
    }

    private void showSetScreenDialog(final Object controlObject) {
        if (RelaySeparationHelper.isRelaySeparationDevice(controlObject)) {
            setScreenDefault(controlObject, true);
        } else {
            setScreenDefault(controlObject, false);
            MessageDialog.show(getActivity(), getActivity().getString(R.string.save_success), getActivity().getString(R.string.set_screen_tip)).setCancelable(false).setOkButton(getActivity().getString(R.string.modify_now), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectActionVM$$ExternalSyntheticLambda12
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$showSetScreenDialog$12;
                    lambda$showSetScreenDialog$12 = ActSmartPanelSelectActionVM.this.lambda$showSetScreenDialog$12(controlObject, baseDialog, view);
                    return lambda$showSetScreenDialog$12;
                }
            }).setCancelButton(getActivity().getString(R.string.not_modify), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectActionVM$$ExternalSyntheticLambda13
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    return ActSmartPanelSelectActionVM.lambda$showSetScreenDialog$13(baseDialog, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showSetScreenDialog$12(Object obj, BaseDialog baseDialog, View view) {
        this.goModifyScreen = true;
        setScreenDefault(obj, true);
        return false;
    }

    private void setScreenDefault(final Object controlObject, final boolean finish) {
        RelatedInfoExtParam.RelateInfo relateInfo;
        this.relateInfoAssistant.getRelateInfo(this.relatePosition);
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
            CmdAssistant.getDeviceAssistant(controlObject, new int[0]).setPanelScreenLongData(getActivity(), this.relatePosition, realString, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectActionVM$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSmartPanelSelectActionVM.this.lambda$setScreenDefault$14(controlObject, finish, realString, (ResponseMsg) obj);
                }
            });
        } else {
            this.relateInfoAssistant.setRelateInfo(this.relatePosition, relateInfo);
            CmdAssistant.getDeviceAssistant(controlObject, new int[0]).setPanelScreenData(getActivity(), this.relatePosition, realString, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectActionVM$$ExternalSyntheticLambda3
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSmartPanelSelectActionVM.this.lambda$setScreenDefault$15(controlObject, finish, realString, (ResponseMsg) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setScreenDefault$14(Object obj, boolean z, String str, ResponseMsg responseMsg) {
        if (responseMsg != null && (responseMsg.getStateCode() == 0 || responseMsg.getStateCode() == 153)) {
            if (obj instanceof Device) {
                uploadDeviceData(z);
                ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.LONG_TEXT, CmdAssistant.getDeviceAssistant(obj, new int[0]).setPanelScreenLongData(this.relatePosition, str));
                ReplaceHelper.instance().backupIndexData(getLifecycleOwner(), this.controlDevice.getDeviceId(), this.relatePosition + 1);
                return;
            }
            uploadGroupData(z);
            return;
        }
        getActivity().showErrorDialog(getActivity().getString(R.string.save_fail));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setScreenDefault$15(Object obj, boolean z, String str, ResponseMsg responseMsg) {
        if (responseMsg != null && (responseMsg.getStateCode() == 0 || responseMsg.getStateCode() == 153)) {
            if (obj instanceof Device) {
                uploadDeviceData(z);
                ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.TEXT, CmdAssistant.getDeviceAssistant(obj, new int[0]).setPanelScreenData(this.relatePosition, str));
                ReplaceHelper.instance().backupIndexData(getLifecycleOwner(), this.controlDevice.getDeviceId(), this.relatePosition + 1);
                return;
            }
            uploadGroupData(z);
            return;
        }
        getActivity().showErrorDialog(getActivity().getString(R.string.save_fail));
    }

    private void adjustKRange(Object controlObject) {
        Group groupByGroupId;
        ArrayList arrayList = new ArrayList();
        RelatedInfoExtParam.RelateInfo relateInfo = this.relateInfoAssistant.getRelateInfo(this.relatePosition);
        if (this.relateInfoAssistant.isShowKRange(relateInfo)) {
            SettingCmdParam.KInfo kInfo = new SettingCmdParam.KInfo();
            if (relateInfo != null) {
                if (relateInfo.type == 1) {
                    Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(relateInfo.objectId);
                    if (deviceByDeviceId != null) {
                        kInfo.setMinK(deviceByDeviceId.getMinkelvin());
                        kInfo.setMaxK(deviceByDeviceId.getMaxkelvin());
                    }
                } else if (relateInfo.type == 2 && (groupByGroupId = Injection.repo().group().getGroupByGroupId(relateInfo.objectId)) != null) {
                    kInfo.setMinK(groupByGroupId.getMinkelvin());
                    kInfo.setMaxK(groupByGroupId.getMaxkelvin());
                }
            }
            arrayList.add(kInfo);
            CmdAssistant.getSettingCmdAssistant(controlObject, new int[0]).setKInfo(getActivity(), 1 << this.relatePosition, arrayList, new IAction<Boolean>(this) { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectActionVM.3
                @Override // com.ltech.smarthome.base.IAction
                public void act(Boolean aBoolean) {
                }
            });
            if (this.groupControl) {
                return;
            }
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.K_VALUE, CmdAssistant.getSettingCmdAssistant(controlObject, new int[0]).setKInfo(1 << this.relatePosition, arrayList));
        }
    }

    private void showFailDialog() {
        getActivity().runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectActionVM.4
            @Override // java.lang.Runnable
            public void run() {
                ViewHelpUtil.showBindFailDialog(ActSmartPanelSelectActionVM.this.getActivity());
            }
        });
    }

    public LightAssistant getLightCmdHelper() {
        return CmdAssistant.getLightCmdAssistant(this.relateObject, new int[0]);
    }

    private SettingAssistant getSettingCmdHelper() {
        return CmdAssistant.getSettingCmdAssistant(this.groupControl ? this.controlGroup : this.controlDevice, new int[0]);
    }
}