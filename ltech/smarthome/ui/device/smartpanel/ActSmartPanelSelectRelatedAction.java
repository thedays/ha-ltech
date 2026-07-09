package com.ltech.smarthome.ui.device.smartpanel;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.databinding.ActSelectLightBinding;
import com.ltech.smarthome.databinding.ItemTextHeadBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.screenpanel.ActNewSetScreenDisplay;
import com.ltech.smarthome.ui.device.screenpanel.ActSetScreenDisplay;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.ui.scene.ActSelectDefaultMode;
import com.ltech.smarthome.ui.scene.ActSelectDiyMode;
import com.ltech.smarthome.ui.scene.ActSelectThemeMode;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NameRepository;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.SettingAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.param.ModeCmdParam;
import com.smart.product_agreement.param.SettingCmdParam;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSmartPanelSelectRelatedAction extends BaseNormalActivity<ActSelectLightBinding> implements ISelectAction {
    private Device controlDevice;
    private Group controlGroup;
    private int controlType;
    private boolean goModifyScreen;
    private boolean groupControl;
    private BaseQuickAdapter<GoItem, BaseViewHolder> mActionAdapter;
    private BaseQuickAdapter<String, BaseViewHolder> mKeyAdapter;
    private String productId;
    private RelatedInfoExtParam.RelateInfo relateInfo;
    private RelateInfoAssistant relateInfoAssistant;
    private Object relateObject;
    private int relatePosition;
    private int relateType;
    protected List<String> dataList = new ArrayList();
    protected int selectPosition = -1;

    private int getSubscribeZone() {
        return 1;
    }

    static /* synthetic */ boolean lambda$showSetScreenDialog$17(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_light;
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

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_action));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
    }

    private void initKeyView(int lightType) {
        setEditString(getString(R.string.save));
        if (lightType == 1) {
            this.dataList = Arrays.asList(NameRepository.getDimSwitchActionName(this));
        } else if (lightType == 2) {
            this.dataList = Arrays.asList(NameRepository.getCtSwitchActionName(this));
        } else {
            this.dataList = Arrays.asList(NameRepository.getKeySwitchActionName(this));
        }
        BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_select, this.dataList) { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_name, item).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == ActSmartPanelSelectRelatedAction.this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
                ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
            }
        };
        this.mKeyAdapter = baseQuickAdapter;
        baseQuickAdapter.bindToRecyclerView(((ActSelectLightBinding) this.mViewBinding).rvKey);
        this.mKeyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction$$ExternalSyntheticLambda16
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActSmartPanelSelectRelatedAction.this.lambda$initKeyView$0(baseQuickAdapter2, view, i);
            }
        });
        ((ActSelectLightBinding) this.mViewBinding).rvKey.setLayoutManager(new LinearLayoutManager(this, this) { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction.2
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean canScrollVertically() {
                return false;
            }
        });
        ((ActSelectLightBinding) this.mViewBinding).rvKey.setHasFixedSize(true);
        ItemTextHeadBinding itemTextHeadBinding = (ItemTextHeadBinding) DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_text_head, (ViewGroup) ((ActSelectLightBinding) this.mViewBinding).rvKey.getParent(), false);
        itemTextHeadBinding.setItem(getString(R.string.select_key_action_tip));
        itemTextHeadBinding.setTextColor(Integer.valueOf(ContextCompat.getColor(this, R.color.color_text_black)));
        ((ActSelectLightBinding) this.mViewBinding).rvKey.addHeaderView(itemTextHeadBinding.getRoot());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initKeyView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (this.selectPosition != i) {
            this.selectPosition = i;
            baseQuickAdapter.notifyDataSetChanged();
        }
        this.relateInfo.action = i;
        this.relateInfoAssistant.setRelateInfo(this.relatePosition, this.relateInfo);
    }

    private void initActionView(final int lightType) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new GoItem().setMainText(getString(R.string.theme)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSmartPanelSelectRelatedAction.this.lambda$initActionView$1(lightType);
            }
        })));
        if (lightType > 2) {
            arrayList.add(new GoItem().setMainText(getString(R.string.general_mode)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction$$ExternalSyntheticLambda8
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActSmartPanelSelectRelatedAction.this.lambda$initActionView$2(lightType);
                }
            })));
        }
        arrayList.add(new GoItem().setMainText(getString(R.string.diy_mode)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSmartPanelSelectRelatedAction.this.lambda$initActionView$3(lightType);
            }
        })));
        BaseQuickAdapter<GoItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<GoItem, BaseViewHolder>(this, R.layout.item_go_1, arrayList) { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, GoItem item) {
                helper.setText(R.id.tv_main, item.getMainText());
                ((AppCompatTextView) helper.getView(R.id.tv_main)).getPaint().setFakeBoldText(true);
            }
        };
        this.mActionAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction$$ExternalSyntheticLambda10
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActSmartPanelSelectRelatedAction.this.lambda$initActionView$4(baseQuickAdapter2, view, i);
            }
        });
        this.mActionAdapter.bindToRecyclerView(((ActSelectLightBinding) this.mViewBinding).rvAction);
        ((ActSelectLightBinding) this.mViewBinding).rvAction.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectLightBinding) this.mViewBinding).rvAction.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionView$1(int i) {
        SceneHelper.instance().controlObject = this.relateObject;
        NavUtils.destination(ActSelectThemeMode.class).withInt(Constants.LIGHT_TYPE, i).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionView$2(int i) {
        NavUtils.destination(ActSelectDefaultMode.class).withInt(Constants.LIGHT_TYPE, i).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionView$3(int i) {
        NavUtils.destination(ActSelectDiyMode.class).withInt(Constants.LIGHT_TYPE, i).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionView$4(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.mActionAdapter.getData().get(i).getAction().execute();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        this.relatePosition = getIntent().getIntExtra(Constants.RELATED_POSITION, -1);
        this.groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        this.relateType = getIntent().getIntExtra(Constants.GROUP_RELATE, -1);
        this.controlType = getIntent().getIntExtra(Constants.LIGHT_TYPE, 0);
        int i = this.relateType;
        if (i == 2) {
            Group groupById = Injection.repo().group().getGroupById(getIntent().getLongExtra(Constants.RELATE_ID, -1L));
            this.relateObject = groupById;
            this.relateInfo = RelatedInfoExtParam.RelateInfo.RelatedGroupInfo(groupById.getGroupId());
        } else if (i == 1) {
            Device deviceById = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.RELATE_ID, -1L));
            this.relateObject = deviceById;
            this.relateInfo = RelatedInfoExtParam.RelateInfo.RelatedDeviceInfo(deviceById.getDeviceId());
        }
        if (this.groupControl) {
            this.controlGroup = Injection.repo().group().getGroupById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
            this.relateInfoAssistant = new RelateInfoAssistant(this.controlGroup);
        } else {
            this.controlDevice = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
            this.relateInfoAssistant = new RelateInfoAssistant(this.controlDevice);
        }
        int relateType = getRelateType();
        initKeyView(relateType);
        initActionView(relateType);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (this.selectPosition == -1) {
            SmartToast.showShort(R.string.please_choose);
        } else if (Injection.state().isConnectOuterNet()) {
            subscribe();
        } else {
            SmartToast.showShort(R.string.no_network);
        }
    }

    private void subscribe() {
        if (Injection.state().isConnectOuterNet()) {
            showLoadingDialog(getString(R.string.subscribing));
            if (this.groupControl) {
                groupSubscribe();
                return;
            } else {
                panelSubscribe();
                return;
            }
        }
        SmartToast.showShort(R.string.no_network);
    }

    public void unBindRelateInfo() {
        int groupAddress;
        int groupAgreementId;
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.unsubscribing));
        if (!this.groupControl) {
            Device device = (Device) this.relateObject;
            groupAddress = ((BleParam) device.getParam(BleParam.class)).getUnicastAddress();
            groupAgreementId = ProductRepository.getAgreementIdByPid(device.getProductId());
        } else {
            Group group = (Group) this.relateObject;
            groupAddress = group.getGroupAddress();
            groupAgreementId = ProductRepository.getGroupAgreementId(group.getModuleType(), group.getControlType());
        }
        SettingAssistant settingCmdAssistant = CmdAssistant.getSettingCmdAssistant(null, new int[0]);
        Activity topActivity = ActivityUtils.getTopActivity();
        settingCmdAssistant.unSubscribeInSwitchPanel(topActivity, groupAddress, 1 << this.relatePosition, groupAgreementId, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction$$ExternalSyntheticLambda19
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelSelectRelatedAction.this.lambda$unBindRelateInfo$5((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unBindRelateInfo$5(Boolean bool) {
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
        Object obj = this.relateObject;
        if (obj instanceof Device) {
            Device device = (Device) obj;
            int i = this.controlType;
            if (i != 18) {
                switch (i) {
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                        break;
                    default:
                        LHomeLog.i(getClass(), "groupSubscribe unKnowType");
                        break;
                }
                return;
            }
            int groupAddress = this.controlGroup.getGroupAddress();
            int unicastAddress = ((BleParam) device.getParam(BleParam.class)).getUnicastAddress();
            int agreementIdByPid = ProductRepository.getAgreementIdByPid(device.getProductId());
            int relateDeviceType = getRelateDeviceType();
            int subscribeZone = getSubscribeZone();
            SettingAssistant settingCmdAssistant = CmdAssistant.getSettingCmdAssistant(null, new int[0]);
            int i2 = this.relatePosition;
            settingCmdAssistant.subscribeInSwitchPanel(this, 1 << i2, groupAddress, unicastAddress, agreementIdByPid, subscribeZone, this.relateInfoAssistant.getRelateInfo(i2).action, this.relateInfoAssistant.getRelateInfo(this.relatePosition).type, relateDeviceType, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction$$ExternalSyntheticLambda17
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj2) {
                    ActSmartPanelSelectRelatedAction.this.lambda$groupSubscribe$6((Boolean) obj2);
                }
            });
            return;
        }
        if (obj instanceof Group) {
            Group group = (Group) obj;
            int i3 = this.controlType;
            if (i3 != 18) {
                switch (i3) {
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                        break;
                    default:
                        LHomeLog.i(getClass(), "groupSubscribe unKnowType");
                        break;
                }
                return;
            }
            int groupAddress2 = this.controlGroup.getGroupAddress();
            int groupAddress3 = group.getGroupAddress();
            int groupAgreementId = ProductRepository.getGroupAgreementId(group.getModuleType(), group.getControlType());
            int relateDeviceType2 = getRelateDeviceType();
            int subscribeZone2 = getSubscribeZone();
            SettingAssistant settingCmdAssistant2 = CmdAssistant.getSettingCmdAssistant(null, new int[0]);
            int i4 = this.relatePosition;
            settingCmdAssistant2.subscribeInSwitchPanel(this, 1 << i4, groupAddress2, groupAddress3, groupAgreementId, subscribeZone2, this.relateInfoAssistant.getRelateInfo(i4).action, this.relateInfoAssistant.getRelateInfo(this.relatePosition).type, relateDeviceType2, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction$$ExternalSyntheticLambda18
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj2) {
                    ActSmartPanelSelectRelatedAction.this.lambda$groupSubscribe$7((Boolean) obj2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$groupSubscribe$6(Boolean bool) {
        if (bool.booleanValue()) {
            if (!this.relateInfoAssistant.isProPanel()) {
                uploadGroupData(true);
            } else {
                dismissLoadingDialog();
                showSetScreenDialog(this.controlGroup);
            }
            adjustKRange(this.relateInfoAssistant, this.controlGroup);
            return;
        }
        dismissLoadingDialog();
        showFailDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$groupSubscribe$7(Boolean bool) {
        if (bool.booleanValue()) {
            if (!this.relateInfoAssistant.isProPanel()) {
                uploadGroupData(true);
            } else {
                dismissLoadingDialog();
                showSetScreenDialog(this.controlGroup);
            }
            adjustKRange(this.relateInfoAssistant, this.controlGroup);
            return;
        }
        dismissLoadingDialog();
        showFailDialog();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void panelSubscribe() {
        char c2;
        char c3;
        Object obj = this.relateObject;
        if (obj instanceof Device) {
            Device device = (Device) obj;
            String str = this.productId;
            str.hashCode();
            switch (str.hashCode()) {
                case -1819630261:
                    if (str.equals(ProductId.ID_SMART_SWITCH_S1_PRO)) {
                        c3 = 0;
                        break;
                    }
                    c3 = 65535;
                    break;
                case -1817691924:
                    if (str.equals(ProductId.ID_SMART_SWITCH_S2_PRO)) {
                        c3 = 1;
                        break;
                    }
                    c3 = 65535;
                    break;
                case -1796419228:
                    if (str.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                        c3 = 2;
                        break;
                    }
                    c3 = 65535;
                    break;
                case -835060954:
                    if (str.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                        c3 = 3;
                        break;
                    }
                    c3 = 65535;
                    break;
                case -732569219:
                    if (str.equals(ProductId.ID_SMART_SWITCH_S4)) {
                        c3 = 4;
                        break;
                    }
                    c3 = 65535;
                    break;
                case 225641606:
                    if (str.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                        c3 = 5;
                        break;
                    }
                    c3 = 65535;
                    break;
                case 1097035898:
                    if (str.equals(ProductId.ID_SCENE_PANEL_S8)) {
                        c3 = 6;
                        break;
                    }
                    c3 = 65535;
                    break;
                case 1951402182:
                    if (str.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                        c3 = 7;
                        break;
                    }
                    c3 = 65535;
                    break;
                case 1951547293:
                    if (str.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                        c3 = '\b';
                        break;
                    }
                    c3 = 65535;
                    break;
                default:
                    c3 = 65535;
                    break;
            }
            switch (c3) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case '\b':
                    int unicastAddress = ((BleParam) this.controlDevice.getParam(BleParam.class)).getUnicastAddress();
                    int unicastAddress2 = ((BleParam) device.getParam(BleParam.class)).getUnicastAddress();
                    int agreementIdByPid = ProductRepository.getAgreementIdByPid(device.getProductId());
                    int relateDeviceType = getRelateDeviceType();
                    int subscribeZone = getSubscribeZone();
                    SettingAssistant settingCmdAssistant = CmdAssistant.getSettingCmdAssistant(null, new int[0]);
                    int i = this.relatePosition;
                    settingCmdAssistant.subscribeInSwitchPanel(this, 1 << i, unicastAddress, unicastAddress2, agreementIdByPid, subscribeZone, this.relateInfoAssistant.getRelateInfo(i).action, this.relateInfoAssistant.getRelateInfo(this.relatePosition).type, relateDeviceType, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction$$ExternalSyntheticLambda3
                        @Override // com.ltech.smarthome.base.IAction
                        public final void act(Object obj2) {
                            ActSmartPanelSelectRelatedAction.this.lambda$panelSubscribe$8((Boolean) obj2);
                        }
                    });
                    break;
                default:
                    int unicastAddress3 = ((BleParam) device.getParam(BleParam.class)).getUnicastAddress();
                    int publicationAddress = ((BleParam) this.controlDevice.getParam(BleParam.class)).getPublicationAddress();
                    int agreementIdByPid2 = ProductRepository.getAgreementIdByPid(device.getProductId());
                    SettingAssistant settingCmdAssistant2 = CmdAssistant.getSettingCmdAssistant(null, new int[0]);
                    int i2 = this.relatePosition;
                    settingCmdAssistant2.subscribe(this, unicastAddress3, publicationAddress, agreementIdByPid2, 1 << i2, this.relateInfoAssistant.getRelateInfo(i2).action, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction$$ExternalSyntheticLambda4
                        @Override // com.ltech.smarthome.base.IAction
                        public final void act(Object obj2) {
                            ActSmartPanelSelectRelatedAction.this.lambda$panelSubscribe$9((Boolean) obj2);
                        }
                    });
                    break;
            }
        }
        if (obj instanceof Group) {
            Group group = (Group) obj;
            String str2 = this.productId;
            str2.hashCode();
            switch (str2.hashCode()) {
                case -1819630261:
                    if (str2.equals(ProductId.ID_SMART_SWITCH_S1_PRO)) {
                        c2 = 0;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1817691924:
                    if (str2.equals(ProductId.ID_SMART_SWITCH_S2_PRO)) {
                        c2 = 1;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1796419228:
                    if (str2.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                        c2 = 2;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -835060954:
                    if (str2.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                        c2 = 3;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -732569219:
                    if (str2.equals(ProductId.ID_SMART_SWITCH_S4)) {
                        c2 = 4;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 225641606:
                    if (str2.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                        c2 = 5;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 1097035898:
                    if (str2.equals(ProductId.ID_SCENE_PANEL_S8)) {
                        c2 = 6;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 1951402182:
                    if (str2.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                        c2 = 7;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 1951547293:
                    if (str2.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                        c2 = '\b';
                        break;
                    }
                    c2 = 65535;
                    break;
                default:
                    c2 = 65535;
                    break;
            }
            switch (c2) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case '\b':
                    int unicastAddress4 = ((BleParam) this.controlDevice.getParam(BleParam.class)).getUnicastAddress();
                    int groupAddress = group.getGroupAddress();
                    int groupAgreementId = ProductRepository.getGroupAgreementId(group.getModuleType(), group.getControlType());
                    int relateDeviceType2 = getRelateDeviceType();
                    int subscribeZone2 = getSubscribeZone();
                    SettingAssistant settingCmdAssistant3 = CmdAssistant.getSettingCmdAssistant(null, new int[0]);
                    int i3 = this.relatePosition;
                    settingCmdAssistant3.subscribeInSwitchPanel(this, 1 << i3, unicastAddress4, groupAddress, groupAgreementId, subscribeZone2, this.relateInfoAssistant.getRelateInfo(i3).action, this.relateInfoAssistant.getRelateInfo(this.relatePosition).type, relateDeviceType2, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction$$ExternalSyntheticLambda5
                        @Override // com.ltech.smarthome.base.IAction
                        public final void act(Object obj2) {
                            ActSmartPanelSelectRelatedAction.this.lambda$panelSubscribe$10((Boolean) obj2);
                        }
                    });
                    break;
                default:
                    int groupAddress2 = group.getGroupAddress();
                    int publicationAddress2 = ((BleParam) this.controlDevice.getParam(BleParam.class)).getPublicationAddress();
                    int groupAgreementId2 = ProductRepository.getGroupAgreementId(group.getModuleType(), group.getControlType());
                    SettingAssistant settingCmdAssistant4 = CmdAssistant.getSettingCmdAssistant(null, new int[0]);
                    int i4 = this.relatePosition;
                    settingCmdAssistant4.subscribe(this, groupAddress2, publicationAddress2, groupAgreementId2, 1 << i4, this.relateInfoAssistant.getRelateInfo(i4).action, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction$$ExternalSyntheticLambda6
                        @Override // com.ltech.smarthome.base.IAction
                        public final void act(Object obj2) {
                            ActSmartPanelSelectRelatedAction.this.lambda$panelSubscribe$11((Boolean) obj2);
                        }
                    });
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$panelSubscribe$8(Boolean bool) {
        if (bool.booleanValue()) {
            if (!this.relateInfoAssistant.isProPanel()) {
                uploadDeviceData(true);
            } else {
                dismissLoadingDialog();
                showSetScreenDialog(this.controlDevice);
            }
            adjustKRange(this.relateInfoAssistant, this.controlDevice);
            return;
        }
        dismissLoadingDialog();
        showFailDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$panelSubscribe$9(Boolean bool) {
        if (bool.booleanValue()) {
            uploadDeviceData(true);
        } else {
            dismissLoadingDialog();
            showFailDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$panelSubscribe$10(Boolean bool) {
        if (bool.booleanValue()) {
            if (!this.relateInfoAssistant.isProPanel()) {
                uploadDeviceData(true);
            } else {
                dismissLoadingDialog();
                showSetScreenDialog(this.controlDevice);
            }
            adjustKRange(this.relateInfoAssistant, this.controlDevice);
            return;
        }
        dismissLoadingDialog();
        showFailDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$panelSubscribe$11(Boolean bool) {
        if (bool.booleanValue()) {
            uploadDeviceData(true);
        } else {
            dismissLoadingDialog();
            showFailDialog();
        }
    }

    private int getRelateType() {
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

    private int getRelateDeviceType() {
        if (this.relateInfoAssistant.getRelateInfo(this.relatePosition).action >= 16) {
            return 1 << this.relateInfoAssistant.getRelateInfo(this.relatePosition).keyActionExtra;
        }
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

    private void uploadDeviceData(final boolean finish) {
        final String extParamString = this.relateInfoAssistant.getExtParamString();
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.controlDevice.getDeviceId(), extParamString).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSelectRelatedAction.this.lambda$uploadDeviceData$12((Disposable) obj);
            }
        }).doFinally(new ActSmartPanelSelectRelatedAction$$ExternalSyntheticLambda14(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSelectRelatedAction.this.lambda$uploadDeviceData$13(extParamString, finish, obj);
            }
        }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction.4
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                ActSmartPanelSelectRelatedAction.this.unBindRelateInfo();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDeviceData$12(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDeviceData$13(String str, boolean z, Object obj) throws Exception {
        this.controlDevice.setExtParam(str);
        Injection.repo().device().saveDevice(this.controlDevice);
        SmartToast.showShort(R.string.save_success);
        if (this.goModifyScreen) {
            if (!this.controlDevice.getProductId().equals(ProductId.ID_SMART_PANEL_G4) && !this.controlDevice.getProductId().equals(ProductId.ID_SMART_PANEL_G4_PRO) && !this.controlDevice.getProductId().equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                NavUtils.destination(ActNewSetScreenDisplay.class).withLong(Constants.CONTROL_ID, this.controlDevice.getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) this.controlDevice)).withString(Constants.PRODUCT_ID, this.controlDevice.getProductId()).navigation(this);
            }
            this.goModifyScreen = false;
        }
        if (z) {
            lambda$edit$1(this);
        }
    }

    private void uploadGroupData(final boolean finish) {
        final String extParamString = this.relateInfoAssistant.getExtParamString();
        ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(this.controlGroup.getGroupId(), extParamString).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSelectRelatedAction.this.lambda$uploadGroupData$14((Disposable) obj);
            }
        }).doFinally(new ActSmartPanelSelectRelatedAction$$ExternalSyntheticLambda14(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction$$ExternalSyntheticLambda15
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSelectRelatedAction.this.lambda$uploadGroupData$15(extParamString, finish, obj);
            }
        }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction.5
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                ActSmartPanelSelectRelatedAction.this.unBindRelateInfo();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadGroupData$14(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadGroupData$15(String str, boolean z, Object obj) throws Exception {
        this.controlGroup.setExtParam(str);
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
        runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction.6
            @Override // java.lang.Runnable
            public void run() {
                ViewHelpUtil.showBindFailDialog(ActSmartPanelSelectRelatedAction.this);
            }
        });
    }

    public void showSetScreenDialog(final Object controlObject) {
        if (RelaySeparationHelper.isRelaySeparationDevice(controlObject)) {
            setScreenDefault(controlObject, true);
        } else {
            setScreenDefault(controlObject, false);
            MessageDialog.show(this, getString(R.string.save_success), getString(R.string.set_screen_tip)).setCancelable(false).setOkButton(getString(R.string.modify_now), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction$$ExternalSyntheticLambda11
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$showSetScreenDialog$16;
                    lambda$showSetScreenDialog$16 = ActSmartPanelSelectRelatedAction.this.lambda$showSetScreenDialog$16(controlObject, baseDialog, view);
                    return lambda$showSetScreenDialog$16;
                }
            }).setCancelButton(this.activity.getString(R.string.not_modify), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction$$ExternalSyntheticLambda12
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    return ActSmartPanelSelectRelatedAction.lambda$showSetScreenDialog$17(baseDialog, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showSetScreenDialog$16(Object obj, BaseDialog baseDialog, View view) {
        this.goModifyScreen = true;
        setScreenDefault(obj, true);
        return false;
    }

    private void setScreenDefault(final Object controlObject, final boolean finish) {
        RelatedInfoExtParam.RelateInfo relateInfo = this.relateInfoAssistant.getRelateInfo(this.relatePosition);
        relateInfo.screenType = RelatedInfoExtParam.ScreenType.ScreenTypeWord.getValue();
        final String realString = StringUtils.getRealString(this.relateInfoAssistant.getSwitchScreenBigIcon() == 2, RelateInfoUtils.getRelateInfoString(relateInfo));
        relateInfo.screenStr = realString;
        this.relateInfoAssistant.setRelateInfo(this.relatePosition, relateInfo);
        CmdAssistant.getDeviceAssistant(controlObject, new int[0]).setPanelScreenData(this, this.relatePosition, realString, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelSelectRelatedAction.this.lambda$setScreenDefault$18(controlObject, finish, realString, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setScreenDefault$18(Object obj, boolean z, String str, ResponseMsg responseMsg) {
        if (responseMsg != null && (responseMsg.getStateCode() == 0 || responseMsg.getStateCode() == 153)) {
            if (obj instanceof Device) {
                uploadDeviceData(z);
                ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.TEXT, CmdAssistant.getDeviceAssistant(obj, new int[0]).setPanelScreenLongData(this.relatePosition, str));
                ReplaceHelper.instance().backupIndexData(this.activity, this.controlDevice.getDeviceId(), this.relatePosition + 1);
                return;
            }
            uploadGroupData(z);
            return;
        }
        showErrorDialog(this.activity.getString(R.string.save_fail));
    }

    public void adjustKRange(RelateInfoAssistant relateInfoAssistant, Object controlObject) {
        Group groupByGroupId;
        if (relateInfoAssistant.isShowKRange()) {
            ArrayList arrayList = new ArrayList();
            RelatedInfoExtParam.RelateInfo relateInfo = relateInfoAssistant.getRelateInfo(this.relatePosition);
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
            CmdAssistant.getSettingCmdAssistant(controlObject, new int[0]).setKInfo(this, 1 << this.relatePosition, arrayList, new IAction<Boolean>(this) { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedAction.7
                @Override // com.ltech.smarthome.base.IAction
                public void act(Boolean aBoolean) {
                }
            });
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            if (SceneHelper.instance().cmdParam instanceof ModeCmdParam) {
                ModeCmdParam modeCmdParam = (ModeCmdParam) SceneHelper.instance().cmdParam;
                if (modeCmdParam.getCmdType() == 1) {
                    this.relateInfo.action = 16;
                } else if (modeCmdParam.getCmdType() == 14) {
                    this.relateInfo.action = 17;
                } else {
                    this.relateInfo.action = 18;
                }
                this.relateInfo.keyActionExtra = modeCmdParam.getPlayModeList().get(0).intValue();
                this.relateInfoAssistant.setRelateInfo(this.relatePosition, this.relateInfo);
            }
            if (Injection.state().isConnectOuterNet()) {
                subscribe();
            } else {
                SmartToast.showShort(R.string.no_network);
            }
        }
    }
}