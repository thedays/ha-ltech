package com.ltech.smarthome.ui.device.remote_controller;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.BaseSingleSelectActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NameRepository;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.SettingAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectRelatedAction extends BaseSingleSelectActivity<String> implements ISelectAction {
    private MutableLiveData<Device> controlDevice = new MutableLiveData<>();
    private boolean groupControl;
    private int lightType;
    private RelateInfoAssistant relateInfoAssistant;
    private Object relateObject;
    private int relatePosition;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select;
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

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_action));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        ((ActSelectBinding) this.mViewBinding).tvTip.setText(getString(R.string.select_key_action_tip));
        ((ActSelectBinding) this.mViewBinding).tvTip.setVisibility(0);
        this.lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<String> getList() {
        return Arrays.asList(NameRepository.getKeySwitchActionName(this));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, item).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        this.relatePosition = getIntent().getIntExtra(Constants.RELATED_POSITION, -1);
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        this.groupControl = booleanExtra;
        if (booleanExtra) {
            Injection.repo().group().getGroupFromDb(getIntent().getLongExtra(Constants.RELATE_ID, -1L)).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActSelectRelatedAction$$ExternalSyntheticLambda4
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActSelectRelatedAction.this.lambda$startObserve$0((Group) obj);
                }
            });
        } else {
            Injection.repo().device().getDeviceFromDb(getIntent().getLongExtra(Constants.RELATE_ID, -1L)).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActSelectRelatedAction$$ExternalSyntheticLambda5
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActSelectRelatedAction.this.lambda$startObserve$1((Device) obj);
                }
            });
        }
        this.controlDevice.setValue(Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L)));
        this.controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActSelectRelatedAction$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectRelatedAction.this.lambda$startObserve$2((Device) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Group group) {
        this.relateObject = group;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        this.relateObject = device;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Device device) {
        this.relateInfoAssistant = new RelateInfoAssistant(device);
        if (device.getProductId().equals(ProductId.ID_RC4)) {
            int i = this.lightType;
            if (i == 1) {
                setDataList(Arrays.asList(NameRepository.getRc4DimActionName(this)));
                return;
            }
            if (i == 2 || i == 3 || i == 4 || i == 5) {
                setDataList(Arrays.asList(NameRepository.getRc4CtRgbActionName(this)));
                return;
            } else {
                if (i != 7) {
                    return;
                }
                setDataList(Arrays.asList(NameRepository.getBleSwitchActionName(this)));
                return;
            }
        }
        int i2 = this.lightType;
        if (i2 == 1) {
            setDataList(Arrays.asList(NameRepository.getDimSwitchActionName(this)));
        } else if (i2 == 2) {
            setDataList(Arrays.asList(NameRepository.getCtSwitchActionName(this)));
        } else if (i2 == 7) {
            setDataList(Arrays.asList(NameRepository.getBleSwitchActionName(this)));
        }
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity
    protected void onItemClick(int position) {
        super.onItemClick(position);
        if (this.controlDevice.getValue().getProductId().equals(ProductId.ID_RC4)) {
            if (this.groupControl) {
                RelatedInfoExtParam.RelateInfo RelatedGroupInfo = RelatedInfoExtParam.RelateInfo.RelatedGroupInfo(((Group) this.relateObject).getGroupId());
                RelatedGroupInfo.action = 0;
                this.relateInfoAssistant.setRelateInfo(this.relatePosition, RelatedGroupInfo);
                return;
            } else {
                RelatedInfoExtParam.RelateInfo RelatedDeviceInfo = RelatedInfoExtParam.RelateInfo.RelatedDeviceInfo(((Device) this.relateObject).getDeviceId());
                RelatedDeviceInfo.action = 0;
                this.relateInfoAssistant.setRelateInfo(this.relatePosition, RelatedDeviceInfo);
                return;
            }
        }
        if (this.groupControl) {
            RelatedInfoExtParam.RelateInfo RelatedGroupInfo2 = RelatedInfoExtParam.RelateInfo.RelatedGroupInfo(((Group) this.relateObject).getGroupId());
            if (Integer.parseInt(Injection.repo().group().getGroupById(getIntent().getLongExtra(Constants.RELATE_ID, -1L)).getControlType()) != 7) {
                RelatedGroupInfo2.action = position + 1;
            } else if (position == 0) {
                RelatedGroupInfo2.action = 4;
            } else if (position == 1) {
                RelatedGroupInfo2.action = 5;
            } else if (position == 2) {
                RelatedGroupInfo2.action = 6;
            }
            this.relateInfoAssistant.setRelateInfo(this.relatePosition, RelatedGroupInfo2);
            return;
        }
        RelatedInfoExtParam.RelateInfo RelatedDeviceInfo2 = RelatedInfoExtParam.RelateInfo.RelatedDeviceInfo(((Device) this.relateObject).getDeviceId());
        if (!ProductId.ID_BLE_SWITCH.equals(Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.RELATE_ID, -1L)).getProductId())) {
            RelatedDeviceInfo2.action = position + 1;
        } else if (position == 0) {
            RelatedDeviceInfo2.action = 4;
        } else if (position == 1) {
            RelatedDeviceInfo2.action = 5;
        } else if (position == 2) {
            RelatedDeviceInfo2.action = 6;
        }
        this.relateInfoAssistant.setRelateInfo(this.relatePosition, RelatedDeviceInfo2);
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity
    protected void save() {
        if (Injection.state().isConnectOuterNet()) {
            subscribe();
        } else {
            SmartToast.showShort(R.string.no_network);
        }
    }

    private void subscribe() {
        int unicastAddress;
        int publicationAddress;
        int agreementIdByPid;
        if (Injection.state().isConnectOuterNet()) {
            if (this.groupControl) {
                Group group = (Group) this.relateObject;
                unicastAddress = group.getGroupAddress();
                publicationAddress = ((BleParam) this.controlDevice.getValue().getParam(BleParam.class)).getPublicationAddress();
                agreementIdByPid = ProductRepository.getGroupAgreementId(group.getModuleType(), group.getControlType());
            } else {
                Device device = (Device) this.relateObject;
                unicastAddress = ((BleParam) device.getParam(BleParam.class)).getUnicastAddress();
                publicationAddress = ((BleParam) this.controlDevice.getValue().getParam(BleParam.class)).getPublicationAddress();
                agreementIdByPid = ProductRepository.getAgreementIdByPid(device.getProductId());
            }
            final int i = agreementIdByPid;
            final int i2 = unicastAddress;
            final int i3 = publicationAddress;
            showLoadingDialog(getString(R.string.subscribing), 8000);
            SettingAssistant settingCmdAssistant = CmdAssistant.getSettingCmdAssistant(null, new int[0]);
            int i4 = this.relatePosition;
            settingCmdAssistant.subscribe(this, i2, i3, i, 1 << i4, this.relateInfoAssistant.getRelateInfo(i4).action, new IAction() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActSelectRelatedAction$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectRelatedAction.this.lambda$subscribe$3(i2, i3, i, (Boolean) obj);
                }
            });
            return;
        }
        SmartToast.showShort(R.string.no_network);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$subscribe$3(int i, int i2, int i3, Boolean bool) {
        if (bool.booleanValue()) {
            uploadData(i, i2, i3);
            return;
        }
        unSubscribe(i, i2, i3);
        dismissLoadingDialog();
        ViewHelpUtil.showBindFailDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unSubscribe(int dstAddress, int subscribeAddress, int agreementId) {
        CmdAssistant.getSettingCmdAssistant(null, new int[0]).unSubscribe(ActivityUtils.getTopActivity(), dstAddress, subscribeAddress, agreementId, 1 << this.relatePosition, new IAction() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActSelectRelatedAction$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ((Boolean) obj).booleanValue();
            }
        });
    }

    private void uploadData(final int dstAddress, final int subscribeAddress, final int agreementId) {
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.controlDevice.getValue().getDeviceId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActSelectRelatedAction$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectRelatedAction.this.lambda$uploadData$5((Disposable) obj);
            }
        }).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActSelectRelatedAction$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSelectRelatedAction.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActSelectRelatedAction$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectRelatedAction.this.lambda$uploadData$6(obj);
            }
        }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActSelectRelatedAction.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                SmartToast.showShort(R.string.save_fail);
                ActSelectRelatedAction.this.unSubscribe(dstAddress, subscribeAddress, agreementId);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$5(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$6(Object obj) throws Exception {
        this.controlDevice.getValue().setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().device().saveDevice(this.controlDevice.getValue());
        SmartToast.showShort(R.string.save_success);
        lambda$edit$1(this);
    }
}