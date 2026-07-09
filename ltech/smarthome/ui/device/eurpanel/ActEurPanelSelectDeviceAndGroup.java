package com.ltech.smarthome.ui.device.eurpanel;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ConvertUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.aspanel.AsHelper;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.smart.message.ResponseMsg;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActEurPanelSelectDeviceAndGroup extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> implements ISelectAction {
    private long controlId;
    private Object controlObject;
    private boolean groupControl;
    private boolean isBindEb;
    private int lightType;
    private TextView okTv;
    private int position;
    private RelateInfoAssistant relateInfoAssistant;
    private Object relateObject;

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
        this.listType = 8;
        this.controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        this.lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        this.position = getIntent().getIntExtra(Constants.RELATED_POSITION, -1);
        this.isBindEb = getIntent().getBooleanExtra(Constants.IS_BIND_EB, false);
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        this.groupControl = booleanExtra;
        if (booleanExtra) {
            this.controlObject = Injection.repo().group().getGroupById(this.controlId);
        } else {
            this.controlObject = Injection.repo().device().getDeviceById(this.controlId);
        }
        TextView textView = new TextView(this);
        this.okTv = textView;
        textView.setTextColor(getResources().getColor(R.color.color_text_red));
        this.okTv.setTextSize(14.0f);
        this.okTv.setBackgroundColor(getResources().getColor(R.color.white));
        this.okTv.setGravity(17);
        this.okTv.setText(getString(R.string.finish));
        ((ActSelect3Binding) this.mViewBinding).footerView.addView(this.okTv, new RelativeLayout.LayoutParams(-1, ConvertUtils.dp2px(60.0f)));
        this.okTv.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSelectDeviceAndGroup.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (ActEurPanelSelectDeviceAndGroup.this.selectRoleIds.size() == 0) {
                    return;
                }
                ActEurPanelSelectDeviceAndGroup.this.saveDataAndSubscribe(((FtDeviceGroupVM) ActEurPanelSelectDeviceAndGroup.this.mViewModel).getRoleByRoleId(ActEurPanelSelectDeviceAndGroup.this.selectRoleIds.get(0).longValue()));
            }
        });
        RelateInfoUtils.initRelateInfoList(this.controlObject);
        this.relateInfoAssistant = RelateInfoUtils.relateInfoAssistant;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveDataAndSubscribe(final Role relateObject) {
        RelatedInfoExtParam.RelateInfo RelatedDeviceInfo;
        if (relateObject instanceof Group) {
            RelatedDeviceInfo = RelatedInfoExtParam.RelateInfo.RelatedGroupInfo(((Group) relateObject).getGroupId());
            RelatedDeviceInfo.action = 10;
        } else {
            RelatedDeviceInfo = RelatedInfoExtParam.RelateInfo.RelatedDeviceInfo(((Device) relateObject).getDeviceId());
            RelatedDeviceInfo.action = 10;
        }
        this.relateInfoAssistant.setEurRelateInfo(this.position, RelatedDeviceInfo);
        if (!this.groupControl && ProductRepository.isRcB(((Device) this.controlObject).getProductId())) {
            RelateInfoUtils.showImageTipDialog((Device) this.controlObject, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSelectDeviceAndGroup$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                    ActEurPanelSelectDeviceAndGroup.this.lambda$saveDataAndSubscribe$0(relateObject, imageTipDialog);
                }
            });
        } else {
            subscribe(relateObject);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveDataAndSubscribe$0(Role role, ImageTipDialog imageTipDialog) {
        imageTipDialog.dismiss();
        if (this.isBindEb) {
            subscribeEurPanel(role);
        } else {
            subscribe(role);
        }
    }

    private void subscribe(final Role relateObject) {
        int groupAddress;
        showLoadingDialog(getString(R.string.subscribing));
        this.relateObject = relateObject;
        int unicastAddress = EurHelper.getUnicastAddress(this.controlObject);
        boolean z = relateObject instanceof Device;
        if (z) {
            groupAddress = ((BleParam) ((Device) relateObject).getParam(BleParam.class)).getUnicastAddress();
        } else {
            groupAddress = ((Group) relateObject).getGroupAddress();
        }
        final int i = groupAddress;
        final ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(this.lightType));
        CmdAssistant.getSettingCmdAssistant(null, new int[0]).subscribeInEurPanel(this, 1 << this.position, unicastAddress, i, 2, 1, 1, z ? 1 : 2, arrayList, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSelectDeviceAndGroup$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelSelectDeviceAndGroup.this.lambda$subscribe$1(i, relateObject, arrayList, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$subscribe$1(int i, Role role, List list, ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            Object obj = this.controlObject;
            if (obj instanceof Device) {
                ReplaceHelper.instance().backupIndexData(this, ((Device) obj).getDeviceId(), UpdateBackDataRequest.BIND, CmdAssistant.getSettingCmdAssistant(null, new int[0]).subscribeInEurPanel(1 << this.position, i, 1, 1, role instanceof Device ? 1 : 2, list), this.position + 1);
            }
            uploadData();
            return;
        }
        dismissLoadingDialog();
        showFailDialog();
    }

    private void subscribeEurPanel(final Role relateObject) {
        showLoadingDialog(getString(R.string.subscribing));
        if (ProductRepository.isAsPanel(this.controlObject)) {
            AsHelper.inGroup(relateObject, (Device) this.controlObject, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSelectDeviceAndGroup$$ExternalSyntheticLambda12
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActEurPanelSelectDeviceAndGroup.this.lambda$subscribeEurPanel$3(relateObject, (Boolean) obj);
                }
            });
        } else {
            EurHelper.inGroup(relateObject, (Device) this.controlObject, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSelectDeviceAndGroup$$ExternalSyntheticLambda13
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActEurPanelSelectDeviceAndGroup.this.lambda$subscribeEurPanel$5(relateObject, (Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$subscribeEurPanel$3(final Role role, Boolean bool) {
        if (bool.booleanValue()) {
            AsHelper.clearPublishAddress((Device) this.controlObject, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSelectDeviceAndGroup$$ExternalSyntheticLambda4
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActEurPanelSelectDeviceAndGroup.this.lambda$subscribeEurPanel$2(role, (Boolean) obj);
                }
            });
        } else {
            dismissLoadingDialog();
            showFailDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$subscribeEurPanel$2(Role role, Boolean bool) {
        if (bool.booleanValue()) {
            if (AsHelper.relateInfoAssistant != null) {
                for (int i = 0; i < 4; i++) {
                    RelatedInfoExtParam.RelateInfo relateSceneInfo = AsHelper.relateInfoAssistant.getRelateSceneInfo(i);
                    if (relateSceneInfo != null && relateSceneInfo.objectId != 0) {
                        this.relateInfoAssistant.setRelateSceneInfo(i, relateSceneInfo);
                    }
                }
            }
            this.relateInfoAssistant.setPanelId(role.getObjectId());
            uploadData();
            return;
        }
        dismissLoadingDialog();
        showFailDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$subscribeEurPanel$5(final Role role, Boolean bool) {
        if (bool.booleanValue()) {
            EurHelper.clearPublishAddress((Device) this.controlObject, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSelectDeviceAndGroup$$ExternalSyntheticLambda14
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActEurPanelSelectDeviceAndGroup.this.lambda$subscribeEurPanel$4(role, (Boolean) obj);
                }
            });
        } else {
            dismissLoadingDialog();
            showFailDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$subscribeEurPanel$4(Role role, Boolean bool) {
        if (bool.booleanValue()) {
            if (EurHelper.relateInfoAssistant != null) {
                for (int i = 0; i < 4; i++) {
                    RelatedInfoExtParam.RelateInfo relateSceneInfo = EurHelper.relateInfoAssistant.getRelateSceneInfo(i);
                    if (relateSceneInfo != null && relateSceneInfo.objectId != 0) {
                        this.relateInfoAssistant.setRelateSceneInfo(i, relateSceneInfo);
                    }
                }
            }
            this.relateInfoAssistant.setPanelId(role.getObjectId());
            uploadData();
            return;
        }
        dismissLoadingDialog();
        showFailDialog();
    }

    private void uploadData() {
        final String extParamString = this.relateInfoAssistant.getExtParamString();
        if (!this.groupControl) {
            final Device device = (Device) this.controlObject;
            ((ObservableSubscribeProxy) Injection.net().updateParamExt(device.getDeviceId(), extParamString).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSelectDeviceAndGroup$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActEurPanelSelectDeviceAndGroup.this.lambda$uploadData$6((Disposable) obj);
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSelectDeviceAndGroup$$ExternalSyntheticLambda6
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActEurPanelSelectDeviceAndGroup.this.lambda$uploadData$8(device, extParamString, obj);
                }
            }, new Consumer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSelectDeviceAndGroup$$ExternalSyntheticLambda7
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActEurPanelSelectDeviceAndGroup.this.lambda$uploadData$9((Throwable) obj);
                }
            });
        } else {
            final Group group = (Group) this.controlObject;
            ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(group.getGroupId(), extParamString).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSelectDeviceAndGroup$$ExternalSyntheticLambda8
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActEurPanelSelectDeviceAndGroup.this.lambda$uploadData$10((Disposable) obj);
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSelectDeviceAndGroup$$ExternalSyntheticLambda9
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActEurPanelSelectDeviceAndGroup.this.lambda$uploadData$12(group, extParamString, obj);
                }
            }, new Consumer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSelectDeviceAndGroup$$ExternalSyntheticLambda10
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActEurPanelSelectDeviceAndGroup.this.lambda$uploadData$13((Throwable) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$6(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$8(Device device, String str, Object obj) throws Exception {
        device.setExtParam(str);
        Injection.repo().device().saveDevice(device);
        if (!this.isBindEb && ProductRepository.needPublishAddress(this.controlObject)) {
            EurHelper.subscribePublishAdd(this, this.controlObject, this.relateObject, this.position, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSelectDeviceAndGroup$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj2) {
                    ActEurPanelSelectDeviceAndGroup.this.lambda$uploadData$7((Boolean) obj2);
                }
            });
            return;
        }
        dismissLoadingDialog();
        SmartToast.showShort(R.string.save_success);
        lambda$initView$0(this.activity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$7(Boolean bool) {
        dismissLoadingDialog();
        SmartToast.showShort(R.string.save_success);
        lambda$initView$0(this.activity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$9(Throwable th) throws Exception {
        unBindRelateInfo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$10(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$12(Group group, String str, Object obj) throws Exception {
        group.setExtParam(str);
        Injection.repo().group().saveGroup(group);
        if (!this.isBindEb && ProductRepository.needPublishAddress(this.controlObject)) {
            showLoadingDialog(getString(R.string.saving));
            EurHelper.subscribePublishAdd(this, this.controlObject, this.relateObject, this.position, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSelectDeviceAndGroup$$ExternalSyntheticLambda3
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj2) {
                    ActEurPanelSelectDeviceAndGroup.this.lambda$uploadData$11((Boolean) obj2);
                }
            });
        } else {
            dismissLoadingDialog();
            SmartToast.showShort(R.string.save_success);
            lambda$initView$0(this.activity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$11(Boolean bool) {
        dismissLoadingDialog();
        SmartToast.showShort(R.string.save_success);
        lambda$initView$0(this.activity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$13(Throwable th) throws Exception {
        unBindRelateInfo();
    }

    private void showFailDialog() {
        runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSelectDeviceAndGroup.2
            @Override // java.lang.Runnable
            public void run() {
                ViewHelpUtil.showBindFailDialog(ActEurPanelSelectDeviceAndGroup.this.activity);
            }
        });
    }

    public void unBindRelateInfo() {
        int groupAddress;
        showLoadingDialog(getString(R.string.unsubscribing));
        Object obj = this.relateObject;
        if (obj instanceof Device) {
            groupAddress = ((BleParam) ((Device) obj).getParam(BleParam.class)).getUnicastAddress();
        } else {
            groupAddress = ((Group) obj).getGroupAddress();
        }
        CmdAssistant.getSettingCmdAssistant(null, new int[0]).unSubscribeInEurPanel(this, groupAddress, 1 << this.position, 2, 1, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSelectDeviceAndGroup$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj2) {
                ActEurPanelSelectDeviceAndGroup.this.lambda$unBindRelateInfo$14((Boolean) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unBindRelateInfo$14(Boolean bool) {
        if (bool.booleanValue()) {
            this.relateInfoAssistant.resetRelateInfo(this.position);
        } else {
            dismissLoadingDialog();
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        if (this.isBindEb) {
            if (EurHelper.isEb125(device) && ((BleParam) device.getParam(BleParam.class)).getGroupId() == 0) {
                int convertType = EurHelper.convertType(device);
                return convertType < 3 ? convertType == this.lightType : this.lightType >= 3;
            }
            if (AsHelper.isNewUb(device) && ((BleParam) device.getParam(BleParam.class)).getGroupId() == 0) {
                int convertType2 = AsHelper.convertType(device);
                return convertType2 < 3 ? convertType2 == this.lightType : this.lightType >= 3;
            }
        } else if ((!ProductRepository.isEurPanel(this.controlObject) || ProductRepository.isEbSupportLight(device)) && ProductRepository.isBleLight(device.getProductId()) && ProductRepository.getLightColorType((Object) device) == this.lightType) {
            return true;
        }
        return false;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterGroup(Group group) {
        int lightColorType = ProductRepository.getLightColorType((Object) group);
        if (!this.isBindEb) {
            return (!ProductRepository.isEurPanel(this.controlObject) || ProductRepository.isEbSupportLight(group)) && lightColorType == this.lightType;
        }
        if (lightColorType == 22 || lightColorType == 23 || lightColorType == 24) {
            int convertType = EurHelper.convertType(group);
            return convertType < 3 ? convertType == this.lightType : this.lightType >= 3;
        }
        if (lightColorType != 28 && lightColorType != 27 && lightColorType != 29 && lightColorType != 30) {
            return false;
        }
        int convertType2 = AsHelper.convertType(group);
        return convertType2 < 3 ? convertType2 == this.lightType : this.lightType >= 3;
    }
}