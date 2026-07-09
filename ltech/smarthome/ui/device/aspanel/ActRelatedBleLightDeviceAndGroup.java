package com.ltech.smarthome.ui.device.aspanel;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseSelectDeviceGroupActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.product.ProductInfo;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActRelatedBleLightDeviceAndGroup extends BaseSelectDeviceGroupActivity {
    private Device controlDevice;
    private long controlId;
    private int lightType;
    private String productId;
    private RelatedInfoExtParam.RelateInfo relateInfo;
    private RelateInfoAssistant relateInfoAssistant;
    private int relatePosition;
    private boolean needReset = false;
    private int selectPosition = -1;
    private int lastSelectPosition = -1;

    @Override // com.ltech.smarthome.base.BaseList2Activity
    protected int itemLayout() {
        return R.layout.item_select_device_with_place;
    }

    @Override // com.ltech.smarthome.base.BaseSelectDeviceGroupActivity, com.ltech.smarthome.base.BaseList2Activity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.aspanel.ActRelatedBleLightDeviceAndGroup$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActRelatedBleLightDeviceAndGroup.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int i2 = this.selectPosition;
        if (i2 != i) {
            this.lastSelectPosition = i2;
            this.selectPosition = i;
            if (i != -1) {
                baseQuickAdapter.notifyItemChanged(i);
            }
            int i3 = this.lastSelectPosition;
            if (i3 != -1) {
                baseQuickAdapter.notifyItemChanged(i3);
            }
        }
        Role role = (Role) this.mAdapter.getData().get(i);
        if (role instanceof Device) {
            this.relateInfo = RelatedInfoExtParam.RelateInfo.RelatedDeviceInfo(role.getObjectId());
        } else if (role instanceof Group) {
            this.relateInfo = RelatedInfoExtParam.RelateInfo.RelatedGroupInfo(role.getObjectId());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseList2Activity
    public void convertView(BaseViewHolder helper, Role item) {
        helper.setText(R.id.tv_device_name, item.getName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item)).setText(R.id.tv_place_info, getPlaceInfo(item.getFloorId(), item.getRoomId())).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
        ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseSelectDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        this.controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        this.lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        this.relatePosition = getIntent().getIntExtra(Constants.RELATED_POSITION, -1);
        this.needReset = getIntent().getBooleanExtra(Constants.NEED_RESET, false);
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        if (this.relateInfoAssistant == null) {
            Device deviceById = Injection.repo().device().getDeviceById(this.controlId);
            this.controlDevice = deviceById;
            this.relateInfoAssistant = new RelateInfoAssistant(deviceById);
        }
        handleData(this.roleList, new IAction<List<Role>>() { // from class: com.ltech.smarthome.ui.device.aspanel.ActRelatedBleLightDeviceAndGroup.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(List<Role> roles) {
                ArrayList arrayList = new ArrayList();
                for (Role role : roles) {
                    if (role instanceof Device) {
                        ActRelatedBleLightDeviceAndGroup.this.addDevice((Device) role, arrayList);
                    } else if (role instanceof Group) {
                        ActRelatedBleLightDeviceAndGroup.this.addGroup((Group) role, arrayList);
                    }
                }
                if (!arrayList.isEmpty()) {
                    ActRelatedBleLightDeviceAndGroup.this.setDataList(arrayList);
                } else {
                    ActRelatedBleLightDeviceAndGroup.this.showEmpty();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addGroup(Group group, List<Role> validDeviceList) {
        if ("3".equalsIgnoreCase(group.getModuleType())) {
            if (this.lightType == -1) {
                validDeviceList.add(group);
                return;
            }
            int parseInt = Integer.parseInt(group.getControlType());
            if (!ProductId.ID_AS_PANEL_U4S.equals(this.productId)) {
                if (parseInt == this.lightType) {
                    validDeviceList.add(group);
                }
            } else if (parseInt == 3 || parseInt == 4) {
                validDeviceList.add(group);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addDevice(Device device, List<Role> validDeviceList) {
        ProductInfo productInfoByPid = ProductRepository.getProductInfoByPid(device.getProductId());
        if (productInfoByPid != null) {
            if ("02".equalsIgnoreCase(productInfoByPid.getProductKey()) || ProductId.ID_BLE_SWITCH.equals(productInfoByPid.getProductId())) {
                if (this.lightType == -1) {
                    validDeviceList.add(device);
                    return;
                }
                int lightColorType = ProductRepository.getLightColorType((Object) device);
                if (!ProductId.ID_AS_PANEL_U4S.equals(this.productId)) {
                    if (lightColorType == this.lightType) {
                        validDeviceList.add(device);
                    }
                } else if (lightColorType == 3 || lightColorType == 4) {
                    validDeviceList.add(device);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showContent() {
        super.showContent();
        setEditString(getString(R.string.save));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        super.showEmpty();
        setEditString("");
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (this.selectPosition == -1) {
            SmartToast.showShort(R.string.please_choose);
            return;
        }
        if (Injection.state().isConnectOuterNet()) {
            if (this.needReset) {
                this.relateInfoAssistant.resetRelateInfo(this.relatePosition, this.relateInfo);
            } else {
                this.relateInfoAssistant.setRelateInfo(this.relatePosition, this.relateInfo);
            }
            Role role = (Role) this.mAdapter.getData().get(this.selectPosition);
            if (role instanceof Device) {
                Device device = (Device) role;
                int unicastAddress = ((BleParam) device.getParam(BleParam.class)).getUnicastAddress();
                int publicationAddress = ((BleParam) this.controlDevice.getParam(BleParam.class)).getPublicationAddress();
                showLoadingDialog(getString(R.string.subscribing), 8000);
                CmdAssistant.getSettingCmdAssistant(null, new int[0]).subscribe(this, unicastAddress, publicationAddress, ProductRepository.getAgreementIdByPid(device.getProductId()), 1 << this.relatePosition, 0, new IAction() { // from class: com.ltech.smarthome.ui.device.aspanel.ActRelatedBleLightDeviceAndGroup$$ExternalSyntheticLambda4
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActRelatedBleLightDeviceAndGroup.this.lambda$edit$1((Boolean) obj);
                    }
                });
                return;
            }
            if (role instanceof Group) {
                Group group = (Group) role;
                int groupAddress = group.getGroupAddress();
                int publicationAddress2 = ((BleParam) this.controlDevice.getParam(BleParam.class)).getPublicationAddress();
                showLoadingDialog(getString(R.string.subscribing), 8000);
                CmdAssistant.getSettingCmdAssistant(null, new int[0]).subscribe(this, groupAddress, publicationAddress2, ProductRepository.getGroupAgreementId(group.getModuleType(), group.getControlType()), 1 << this.relatePosition, 0, new IAction() { // from class: com.ltech.smarthome.ui.device.aspanel.ActRelatedBleLightDeviceAndGroup$$ExternalSyntheticLambda5
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActRelatedBleLightDeviceAndGroup.this.lambda$edit$2((Boolean) obj);
                    }
                });
                return;
            }
            return;
        }
        SmartToast.showShort(R.string.no_network);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$1(Boolean bool) {
        if (bool.booleanValue()) {
            uploadData();
        } else {
            dismissLoadingDialog();
            showFailDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$2(Boolean bool) {
        if (bool.booleanValue()) {
            uploadData();
        } else {
            dismissLoadingDialog();
            showFailDialog();
        }
    }

    private void uploadData() {
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.controlDevice.getDeviceId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.aspanel.ActRelatedBleLightDeviceAndGroup$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActRelatedBleLightDeviceAndGroup.this.lambda$uploadData$3((Disposable) obj);
            }
        }).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.aspanel.ActRelatedBleLightDeviceAndGroup$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActRelatedBleLightDeviceAndGroup.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.aspanel.ActRelatedBleLightDeviceAndGroup$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActRelatedBleLightDeviceAndGroup.this.lambda$uploadData$4(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$3(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$4(Object obj) throws Exception {
        this.controlDevice.setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().device().saveDevice(this.controlDevice);
        SmartToast.showShort(R.string.save_success);
        LHomeLog.i(getClass(), "bind.controlDevice=" + this.controlDevice.getId());
        setResult(5003);
        finishActivity();
    }

    private void showFailDialog() {
        runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.device.aspanel.ActRelatedBleLightDeviceAndGroup.2
            @Override // java.lang.Runnable
            public void run() {
                ViewHelpUtil.showBindFailDialog(ActRelatedBleLightDeviceAndGroup.this);
            }
        });
    }
}