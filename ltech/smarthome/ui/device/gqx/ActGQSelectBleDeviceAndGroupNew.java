package com.ltech.smarthome.ui.device.gqx;

import android.content.Intent;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.DryContactBleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.product.ProductInfo;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.singleton.ComboCmdHelper;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectCgdProAction;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes4.dex */
public class ActGQSelectBleDeviceAndGroupNew extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> implements ISelectAction {
    private long controlId;
    private boolean groupControl;
    protected String productId;
    private long roleId;

    static /* synthetic */ boolean lambda$showFailBindDialog$2(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$initView$0(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_device));
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.setVisibility(0);
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.getLayoutParams().width = Utils.dip2px(this, 30.0f);
        ((ActSelect3Binding) this.mViewBinding).layoutSort.setVisibility(8);
        ((ActSelect3Binding) this.mViewBinding).layoutSortAndType.setVisibility(0);
        this.listType = 4;
        this.controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        this.groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        this.roleId = getIntent().getLongExtra(Constants.ROLE_ID, 0L);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void groupClick(Group group) {
        super.groupClick(group);
        bindData(group.getId(), 2);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void deviceClick(Device device) {
        super.deviceClick(device);
        if (ProductId.ID_BLE_LIGHT_CGD_PRO.equalsIgnoreCase(device.getProductId())) {
            NavUtils.destination(ActSmartPanelSelectCgdProAction.class).withLong(Constants.CONTROL_ID, this.controlId).withLong(Constants.RELATE_ID, device.getId()).withLong("device_id", device.getDeviceId()).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withInt(Constants.GROUP_RELATE, 1).withBoolean(Constants.IS_GQ, true).withInt(Constants.LIGHT_TYPE, getIntent().getIntExtra(Constants.LIGHT_TYPE, 0)).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withString(Constants.PRODUCT_ID, this.productId).withDefaultRequestCode().navigation(this);
        } else {
            bindData(device.getId(), 1);
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterGroup(Group group) {
        if (isRc4s() && ProductRepository.getLightColorType((Object) group) == 7) {
            return true;
        }
        if (isGq() && ProductRepository.getLightColorType((Object) group) == 7) {
            return false;
        }
        if (group.getMaingroupid() != 0 && ProductRepository.getLightColorType((Object) group) == 7 && group.getMaingroupid() == this.roleId) {
            return false;
        }
        if (RelaySeparationHelper.isRelaySeparationSub(group)) {
            return group.getSubhide() != 1;
        }
        if (RelaySeparationHelper.isRelaySeparationDevice(group)) {
            return isRc4s();
        }
        if ("3".equalsIgnoreCase(group.getModuleType()) && !group.getGroupKey().equals(ProductId.BLE_GROUP_WAVE_SENSOR_MR03) && !group.getGroupKey().equals(ProductId.BLE_GROUP_WAVE_SENSOR_MR04) && !group.getGroupKey().equals(ProductId.BLE_GROUP_WAVE_SENSOR_MS03) && !group.getGroupKey().equals(ProductId.BLE_GROUP_EUR_PANEL_EB1) && !group.getGroupKey().equals(ProductId.BLE_GROUP_EUR_PANEL_EB2) && !group.getGroupKey().equals(ProductId.BLE_GROUP_EUR_PANEL_EB5) && !group.getGroupKey().equals(ProductId.BLE_GROUP_AS_PANEL_UB1) && !group.getGroupKey().equals(ProductId.BLE_GROUP_AS_PANEL_UB2) && !group.getGroupKey().equals(ProductId.BLE_GROUP_AS_PANEL_UB4) && !group.getGroupKey().equals(ProductId.BLE_GROUP_AS_PANEL_UB5)) {
            int lightColorType = ProductRepository.getLightColorType((Object) group);
            if (ProductRepository.isDaliLightGroup(group)) {
                lightColorType = DaliProHelper.convertDaliType(group);
            }
            if (lightColorType != 16 && lightColorType != 12 && lightColorType != 14 && lightColorType != 2 && lightColorType != 1 && lightColorType != 3 && lightColorType != 4 && lightColorType != 5 && lightColorType != 20) {
                return false;
            }
            if (this.groupControl) {
                return (group.getDeviceIds() == null || group.getId() == this.controlId) ? false : true;
            }
            if (group.getDeviceIds() != null) {
                return true;
            }
        }
        return false;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        DryContactBleParam dryContactBleParam;
        if (isRc4s() && ProductId.ID_BLE_SWITCH.equals(device.getProductId())) {
            return true;
        }
        if (isGq() && ProductId.ID_BLE_SWITCH.equals(device.getProductId())) {
            return false;
        }
        if (isRc4s() && ProductId.ID_BLE_LIGHT_CGD_PRO.equalsIgnoreCase(device.getProductId())) {
            return false;
        }
        if ((device.getProductId().equalsIgnoreCase(ProductId.ID_BLE_SWITCH) && device.isSubDevice() && device.getMacdeviceid() == this.roleId) || RelaySeparationHelper.isRelaySeparationDevice(device)) {
            return false;
        }
        if (device.getProductId().equalsIgnoreCase(ProductId.ID_BLE_SWITCH) && RelaySeparationHelper.isRelaySeparationSub(device)) {
            if (device.getSubhide() == 1) {
                return false;
            }
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getMacdeviceid());
            return deviceByDeviceId == null || deviceByDeviceId.getParam() == null || deviceByDeviceId.getParam(BleParam.class) == null || ((BleParam) deviceByDeviceId.getParam(BleParam.class)).getGroupId() == 0;
        }
        ProductInfo productInfoByPid = ProductRepository.getProductInfoByPid(device.getProductId());
        if (productInfoByPid != null) {
            if ("02".equalsIgnoreCase(productInfoByPid.getProductKey())) {
                return ("0C".equals(productInfoByPid.getSubProductKey()) || "0D".equals(productInfoByPid.getSubProductKey())) ? false : true;
            }
            if ("01".equalsIgnoreCase(productInfoByPid.getProductKey())) {
                return false;
            }
            if ("08".equalsIgnoreCase(productInfoByPid.getProductKey())) {
                return true;
            }
            if ((ProductId.ID_BLE_DRY_CONTACT.equalsIgnoreCase(productInfoByPid.getProductId()) && (dryContactBleParam = (DryContactBleParam) device.getParam(DryContactBleParam.class)) != null && (dryContactBleParam.getSubType() == 0 || dryContactBleParam.getSubType() == 3)) || "07".equalsIgnoreCase(productInfoByPid.getProductKey())) {
                return true;
            }
            if ("01".equalsIgnoreCase(productInfoByPid.getProductKey())) {
                return ProductRepository.isDcaSuperPanel(device.getProductId());
            }
            if ("08".equalsIgnoreCase(productInfoByPid.getProductKey()) || "04".equalsIgnoreCase(productInfoByPid.getProductKey())) {
                return false;
            }
        }
        return ProductRepository.isDaliLightGroup(device);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3001) {
            bindData(data.getLongExtra(Constants.RELATE_ID, -1L), data.getIntExtra(Constants.GROUP_RELATE, -1));
        }
    }

    public boolean isGq() {
        return getIntent().getBooleanExtra(Constants.IS_GQ, false);
    }

    public boolean isRc4s() {
        return getIntent().getBooleanExtra(Constants.IS_RC4S, false);
    }

    private void bindData(final long relateId, final int relateType) {
        final int intExtra = getIntent().getIntExtra(Constants.SELECT_POSITION, 0);
        final Device deviceById = Injection.repo().device().getDeviceById(this.controlId);
        ImageTipDialog.asDefault().setTitle(getString(R.string.app_str_rc4s_activate_tip)).setConfirmString(getString(R.string.get_it)).setImageRes(R.mipmap.rc4sble_2).setCallback(new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.gqx.ActGQSelectBleDeviceAndGroupNew$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
            public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                ActGQSelectBleDeviceAndGroupNew.this.lambda$bindData$0(relateType, relateId, deviceById, intExtra, imageTipDialog);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:9:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$bindData$0(final int r12, final long r13, final com.ltech.smarthome.model.bean.Device r15, final int r16, final com.ltech.smarthome.view.dialog.ImageTipDialog r17) {
        /*
            r11 = this;
            r0 = 2
            if (r12 != r0) goto L20
            com.ltech.smarthome.model.Repository r0 = com.ltech.smarthome.model.Injection.repo()
            com.ltech.smarthome.model.repo.ifun.IGroup r0 = r0.group()
            com.ltech.smarthome.model.bean.Group r0 = r0.getGroupById(r13)
            r1 = r0
            com.ltech.smarthome.model.bean.Group r1 = (com.ltech.smarthome.model.bean.Group) r1
            r0.getName()
            com.ltech.smarthome.model.IComboCmd r1 = com.ltech.smarthome.singleton.ComboCmdHelper.getInstance()
            java.util.List r1 = r1.getDefaultSelfActions(r0)
        L1d:
            r4 = r0
            r0 = r1
            goto L62
        L20:
            r0 = 1
            if (r12 != r0) goto L3e
            com.ltech.smarthome.model.Repository r0 = com.ltech.smarthome.model.Injection.repo()
            com.ltech.smarthome.model.repo.ifun.IDevice r0 = r0.device()
            com.ltech.smarthome.model.bean.Device r0 = r0.getDeviceById(r13)
            r1 = r0
            com.ltech.smarthome.model.bean.Device r1 = (com.ltech.smarthome.model.bean.Device) r1
            r0.getName()
            com.ltech.smarthome.model.IComboCmd r1 = com.ltech.smarthome.singleton.ComboCmdHelper.getInstance()
            java.util.List r1 = r1.getDefaultSelfActions(r0)
            goto L1d
        L3e:
            r0 = 3
            if (r12 != r0) goto L60
            com.ltech.smarthome.model.Repository r0 = com.ltech.smarthome.model.Injection.repo()
            com.ltech.smarthome.model.repo.ifun.IScene r0 = r0.scene()
            com.ltech.smarthome.model.bean.Scene r0 = r0.getSceneBySceneId(r13)
            r1 = r0
            com.ltech.smarthome.model.bean.Scene r1 = (com.ltech.smarthome.model.bean.Scene) r1
            r0.getName()
            com.ltech.smarthome.model.IComboCmd r1 = com.ltech.smarthome.singleton.ComboCmdHelper.getInstance()
            int r2 = r0.getSceneNum()
            java.util.List r1 = r1.getSceneDefaultComboCmdActions(r0, r2)
            goto L1d
        L60:
            r0 = 0
            r4 = r0
        L62:
            if (r0 == 0) goto L81
            java.lang.String r1 = ""
            r11.showLoadingDialog(r1)
            com.ltech.smarthome.model.IComboCmd r10 = com.ltech.smarthome.singleton.ComboCmdHelper.getInstance()
            com.ltech.smarthome.ui.device.gqx.ActGQSelectBleDeviceAndGroupNew$1 r1 = new com.ltech.smarthome.ui.device.gqx.ActGQSelectBleDeviceAndGroupNew$1
            r2 = r11
            r9 = r12
            r7 = r13
            r3 = r15
            r5 = r16
            r6 = r17
            r1.<init>()
            r7 = r1
            r6 = r5
            r1 = r10
            r5 = r0
            r1.subscribeCmd(r2, r3, r4, r5, r6, r7)
        L81:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.gqx.ActGQSelectBleDeviceAndGroupNew.lambda$bindData$0(int, long, com.ltech.smarthome.model.bean.Device, int, com.ltech.smarthome.view.dialog.ImageTipDialog):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showFailBindDialog(final long relateId, final int relateType) {
        MessageDialog.show(this, getString(R.string.app_str_operation_failure), getString(R.string.app_str_binding_fail_tip)).setCancelable(false).setOkButton(getString(R.string.go_continue), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.gqx.ActGQSelectBleDeviceAndGroupNew$$ExternalSyntheticLambda4
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showFailBindDialog$1;
                lambda$showFailBindDialog$1 = ActGQSelectBleDeviceAndGroupNew.this.lambda$showFailBindDialog$1(relateId, relateType, baseDialog, view);
                return lambda$showFailBindDialog$1;
            }
        }).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.gqx.ActGQSelectBleDeviceAndGroupNew$$ExternalSyntheticLambda5
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return ActGQSelectBleDeviceAndGroupNew.lambda$showFailBindDialog$2(baseDialog, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showFailBindDialog$1(long j, int i, BaseDialog baseDialog, View view) {
        bindData(j, i);
        return false;
    }

    public void uploadData(Device device, final int selIndex, final long relateId, final int relateType) {
        ((ObservableSubscribeProxy) Injection.net().bindKeyInfo(device.getDeviceId(), ComboCmdHelper.getInstance().getKeysByZone(selIndex + 1)).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.gqx.ActGQSelectBleDeviceAndGroupNew$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGQSelectBleDeviceAndGroupNew.this.lambda$uploadData$3((Disposable) obj);
            }
        }).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.gqx.ActGQSelectBleDeviceAndGroupNew$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActGQSelectBleDeviceAndGroupNew.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.gqx.ActGQSelectBleDeviceAndGroupNew$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGQSelectBleDeviceAndGroupNew.this.lambda$uploadData$4(selIndex, relateId, relateType, (List) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$3(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$4(int i, long j, int i2, List list) throws Exception {
        ComboCmdHelper.getInstance().setKeyData(i + 1, list);
        Intent intent = new Intent();
        intent.putExtra(Constants.RELATE_ID, j);
        intent.putExtra(Constants.GROUP_RELATE, i2);
        setResult(3001, intent);
        finishActivity();
    }
}