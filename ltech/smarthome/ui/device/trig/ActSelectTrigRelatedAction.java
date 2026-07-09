package com.ltech.smarthome.ui.device.trig;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.BaseSingleSelectActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
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
public class ActSelectTrigRelatedAction extends BaseSingleSelectActivity<String> implements ISelectAction {
    private Device controlDevice;
    private boolean groupControl;
    private boolean isScene;
    private RelatedInfoExtParam.RelateInfo relateInfo;
    private RelateInfoAssistant relateInfoAssistant;
    private Object relateObject;
    private int relatePosition;
    private int subType;

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
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<String> getList() {
        this.isScene = getIntent().getBooleanExtra(Constants.IS_SCENE, true);
        int intExtra = getIntent().getIntExtra(Constants.SUB_TYPE, 2);
        this.subType = intExtra;
        if (this.isScene) {
            if (intExtra == 2) {
                return Arrays.asList(NameRepository.getTrigScene8ActionName(this));
            }
            return Arrays.asList(NameRepository.getTrigSceneActionName(this));
        }
        if (intExtra == 0) {
            return Arrays.asList(NameRepository.getTrigCurtainActionName(this));
        }
        return Arrays.asList(NameRepository.getTrigDreamCurtainActionName(this));
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
            Injection.repo().group().getGroupFromDb(getIntent().getLongExtra(Constants.RELATE_ID, -1L)).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.trig.ActSelectTrigRelatedAction$$ExternalSyntheticLambda3
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActSelectTrigRelatedAction.this.lambda$startObserve$0((Group) obj);
                }
            });
        } else {
            Injection.repo().device().getDeviceFromDb(getIntent().getLongExtra(Constants.RELATE_ID, -1L)).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.trig.ActSelectTrigRelatedAction$$ExternalSyntheticLambda4
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActSelectTrigRelatedAction.this.lambda$startObserve$1((Device) obj);
                }
            });
        }
        Injection.repo().device().getDeviceFromDb(getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.trig.ActSelectTrigRelatedAction$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectTrigRelatedAction.this.lambda$startObserve$2((Device) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Group group) {
        this.relateObject = group;
        this.relateInfo = RelatedInfoExtParam.RelateInfo.RelatedGroupInfo(group.getGroupId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        this.relateObject = device;
        this.relateInfo = RelatedInfoExtParam.RelateInfo.RelatedDeviceInfo(device.getDeviceId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Device device) {
        this.controlDevice = device;
        if (this.relateInfoAssistant == null) {
            this.relateInfoAssistant = new RelateInfoAssistant(device);
        }
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity
    protected void onItemClick(int position) {
        super.onItemClick(position);
        if (this.isScene) {
            this.relateInfo.action = position + 1;
        } else if (this.subType == 0) {
            this.relateInfo.action = TrigRepository.getDefaultCurtainItemList().get(position).spanCount;
        } else {
            this.relateInfo.action = TrigRepository.getDefaultDreamCurtainItemList().get(position).spanCount;
        }
        this.relateInfoAssistant.setRelateInfo(this.relatePosition, this.relateInfo);
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity
    protected void save() {
        subscribe();
    }

    private void subscribe() {
        int groupAddress;
        int publicationAddress;
        int groupAgreementId;
        Object obj = this.relateObject;
        if (obj instanceof Device) {
            Device device = (Device) obj;
            groupAddress = ((BleParam) device.getParam(BleParam.class)).getUnicastAddress();
            publicationAddress = ((BleParam) this.controlDevice.getParam(BleParam.class)).getPublicationAddress();
            groupAgreementId = ProductRepository.getAgreementIdByPid(device.getProductId());
        } else {
            Group group = (Group) obj;
            groupAddress = group.getGroupAddress();
            publicationAddress = ((BleParam) this.controlDevice.getParam(BleParam.class)).getPublicationAddress();
            groupAgreementId = ProductRepository.getGroupAgreementId(group.getModuleType(), group.getControlType());
        }
        int i = groupAgreementId;
        showLoadingDialog(getString(R.string.subscribing), 8000);
        SettingAssistant settingCmdAssistant = CmdAssistant.getSettingCmdAssistant(null, new int[0]);
        int i2 = this.relatePosition;
        settingCmdAssistant.subscribe(this, groupAddress, publicationAddress, i, 1 << i2, this.relateInfoAssistant.getRelateInfo(i2).action, new IAction() { // from class: com.ltech.smarthome.ui.device.trig.ActSelectTrigRelatedAction$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj2) {
                ActSelectTrigRelatedAction.this.lambda$subscribe$3((Boolean) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$subscribe$3(Boolean bool) {
        if (bool.booleanValue()) {
            uploadData();
        } else {
            dismissLoadingDialog();
            showFailDialog();
        }
    }

    private void uploadData() {
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.controlDevice.getDeviceId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.trig.ActSelectTrigRelatedAction$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectTrigRelatedAction.this.lambda$uploadData$4((Disposable) obj);
            }
        }).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.trig.ActSelectTrigRelatedAction$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSelectTrigRelatedAction.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.trig.ActSelectTrigRelatedAction$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectTrigRelatedAction.this.lambda$uploadData$5(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$4(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$5(Object obj) throws Exception {
        this.controlDevice.setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().device().saveDevice(this.controlDevice);
        SmartToast.showShort(R.string.save_success);
        lambda$edit$1(this);
    }

    private void showFailDialog() {
        runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.device.trig.ActSelectTrigRelatedAction.1
            @Override // java.lang.Runnable
            public void run() {
                ViewHelpUtil.showBindFailDialog(ActSelectTrigRelatedAction.this);
            }
        });
    }
}