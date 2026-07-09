package com.ltech.smarthome.ui.device.curtain_motor;

import android.view.View;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelectBleCurtainActionBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.SettingAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.view.LightBrtBar;
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
public class ActSelectBleCurtainRelatedAction extends BaseNormalActivity<ActSelectBleCurtainActionBinding> implements ISelectAction {
    private Device controlDevice;
    private Group controlGroup;
    private long controlId;
    private int controlType;
    private List<CurtainAction> dataList;
    private boolean groupControl;
    private MultipleItemRvAdapter<CurtainAction, BaseViewHolder> mAdapter;
    private String productId;
    private int progress;
    RelatedInfoExtParam.RelateInfo relateInfo;
    private RelateInfoAssistant relateInfoAssistant;
    private Object relateObject;
    private int relatePosition;
    private int relateType;
    protected boolean[] selectArray;
    private int selectPosition = -1;
    private int lastSelectPosition = -1;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_ble_curtain_action;
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

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (Injection.state().isConnectOuterNet()) {
            subscribe();
        } else {
            SmartToast.showShort(R.string.no_network);
        }
    }

    private void subscribe() {
        if (Injection.state().isConnectOuterNet()) {
            showLoadingDialog(getString(R.string.subscribing), 8000);
            String productId = this.controlDevice.getProductId();
            productId.hashCode();
            switch (productId) {
                case "122041818260301":
                case "122041818283501":
                case "122041818304701":
                case "221042516351701":
                case "123072510445601":
                case "221030816330401":
                case "121042516340801":
                case "121042516345401":
                    break;
                default:
                    panelSubscribe();
                    break;
            }
            return;
        }
        SmartToast.showShort(R.string.no_network);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_action));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        this.relatePosition = getIntent().getIntExtra(Constants.RELATED_POSITION, -1);
        this.controlType = getIntent().getIntExtra(Constants.LIGHT_TYPE, 0);
        this.groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        this.relateType = getIntent().getIntExtra(Constants.GROUP_RELATE, 1);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        this.controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        if (this.groupControl) {
            Group groupById = Injection.repo().group().getGroupById(getIntent().getLongExtra(Constants.RELATE_ID, -1L));
            this.relateObject = groupById;
            this.relateInfo = RelatedInfoExtParam.RelateInfo.RelatedGroupInfo(groupById.getGroupId());
        } else {
            Device deviceById = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.RELATE_ID, -1L));
            this.relateObject = deviceById;
            this.relateInfo = RelatedInfoExtParam.RelateInfo.RelateCurtainDeviceInfo(deviceById.getDeviceId());
            if (deviceById.getDeviceState().getCurtainMotorState() != null) {
                this.progress = deviceById.getDeviceState().getCurtainMotorState().getTravelPercent();
            }
        }
        Injection.repo().device().getDeviceFromDb(getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActSelectBleCurtainRelatedAction$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectBleCurtainRelatedAction.this.lambda$startObserve$0((Device) obj);
            }
        });
        initRv();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        try {
            Device device2 = this.controlDevice;
            if (device2 == null) {
                LHomeLog.i(getClass(), "message_send (device)getDeviceFromDb mViewModel.controlObject.getValue()==null");
                this.controlDevice = device;
                this.relateInfoAssistant = new RelateInfoAssistant(device);
            } else {
                if (HelpUtils.compareObject(device2, device)) {
                    return;
                }
                LHomeLog.i(getClass(), "message_send device changed");
                this.controlDevice = device;
                this.relateInfoAssistant = new RelateInfoAssistant(device);
            }
        } catch (Exception e) {
            LHomeLog.i(getClass(), "message_send (device)getDeviceFromDb Exception" + e.toString());
            e.printStackTrace();
        }
    }

    private void initRv() {
        List<CurtainAction> contentList = getContentList();
        this.dataList = contentList;
        this.selectArray = new boolean[contentList.size()];
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.dataList);
        this.mAdapter = anonymousClass1;
        anonymousClass1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActSelectBleCurtainRelatedAction$$ExternalSyntheticLambda4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSelectBleCurtainRelatedAction.this.lambda$initRv$1(baseQuickAdapter, view, i);
            }
        });
        ((ActSelectBleCurtainActionBinding) this.mViewBinding).rvContent.addOnItemTouchListener(new OnItemChildClickListener(this) { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActSelectBleCurtainRelatedAction.2
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
        this.mAdapter.finishInitialize();
        this.mAdapter.bindToRecyclerView(((ActSelectBleCurtainActionBinding) this.mViewBinding).rvContent);
        ((ActSelectBleCurtainActionBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectBleCurtainActionBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActSelectBleCurtainActionBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(true);
    }

    /* renamed from: com.ltech.smarthome.ui.device.curtain_motor.ActSelectBleCurtainRelatedAction$1, reason: invalid class name */
    class AnonymousClass1 extends MultipleItemRvAdapter<CurtainAction, BaseViewHolder> {
        AnonymousClass1(List data) {
            super(data);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
        public int getViewType(CurtainAction CurtainAction) {
            return CurtainAction.getType();
        }

        @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
        public void registerItemProvider() {
            this.mProviderDelegate.registerProvider(new BaseItemProvider<CurtainAction, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActSelectBleCurtainRelatedAction.1.1
                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int layout() {
                    return R.layout.item_select;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int viewType() {
                    return CurtainAction.TYPE_DEFAULT;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void convert(BaseViewHolder helper, CurtainAction data, int position) {
                    ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                    if (helper.getAdapterPosition() == ActSelectBleCurtainRelatedAction.this.selectPosition) {
                        helper.setImageResource(R.id.iv_select, R.mipmap.ic_tick_sel).setGone(R.id.iv_select, true);
                    } else {
                        helper.setGone(R.id.iv_select, false);
                    }
                    helper.setText(R.id.tv_name, data.getTitleName());
                }
            });
            this.mProviderDelegate.registerProvider(new BaseItemProvider<CurtainAction, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActSelectBleCurtainRelatedAction.1.2
                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int layout() {
                    return R.layout.item_select_diy;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int viewType() {
                    return CurtainAction.TYPE_DIY;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void convert(final BaseViewHolder helper, final CurtainAction data, final int position) {
                    helper.setText(R.id.tv_name, data.getTitleName());
                    ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                    if (helper.getAdapterPosition() == ActSelectBleCurtainRelatedAction.this.selectPosition) {
                        helper.setImageResource(R.id.iv_select, R.mipmap.ic_tick_sel).setGone(R.id.iv_select, true);
                        helper.setGone(R.id.layout_change_curtain_open_percent, true);
                        ((LightBrtBar) helper.getView(R.id.sb_brt)).setIncludeZero(true);
                        ((LightBrtBar) helper.getView(R.id.sb_brt)).setProgress(ActSelectBleCurtainRelatedAction.this.progress);
                        if (ActSelectBleCurtainRelatedAction.this.progress == 0) {
                            helper.setText(R.id.tv_brt, ActSelectBleCurtainRelatedAction.this.getString(R.string.app_str_all_close));
                        } else if (ActSelectBleCurtainRelatedAction.this.progress == 100) {
                            helper.setText(R.id.tv_brt, ActSelectBleCurtainRelatedAction.this.getString(R.string.app_str_all_open));
                        } else {
                            helper.setText(R.id.tv_brt, ActSelectBleCurtainRelatedAction.this.progress + "%");
                        }
                        data.setCurtainPercent(ActSelectBleCurtainRelatedAction.this.progress);
                        ActSelectBleCurtainRelatedAction.this.setCurtainRelateInfo(position, ActSelectBleCurtainRelatedAction.this.relateInfo);
                        ActSelectBleCurtainRelatedAction.this.relateInfoAssistant.setRelateInfo(position, ActSelectBleCurtainRelatedAction.this.relateInfo);
                        ((LightBrtBar) helper.getView(R.id.sb_brt)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActSelectBleCurtainRelatedAction.1.2.1
                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                LHomeLog.i(getClass(), "progress-->" + progress);
                                helper.setText(R.id.tv_brt, ((LightBrtBar) seekBar).getProgress() + "%");
                            }

                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public void onStopTrackingTouch(SeekBar seekBar) {
                                Class<?> cls = getClass();
                                StringBuilder sb = new StringBuilder("progress-->");
                                LightBrtBar lightBrtBar = (LightBrtBar) seekBar;
                                sb.append(lightBrtBar.getProgress());
                                LHomeLog.i(cls, sb.toString());
                                if (lightBrtBar.getProgress() == 0) {
                                    helper.setText(R.id.tv_brt, ActSelectBleCurtainRelatedAction.this.getString(R.string.app_str_all_close));
                                } else if (lightBrtBar.getProgress() == 100) {
                                    helper.setText(R.id.tv_brt, ActSelectBleCurtainRelatedAction.this.getString(R.string.app_str_all_open));
                                } else {
                                    helper.setText(R.id.tv_brt, lightBrtBar.getProgress() + "%");
                                }
                                data.setCurtainPercent(lightBrtBar.getProgress());
                                ActSelectBleCurtainRelatedAction.this.setCurtainRelateInfo(position, ActSelectBleCurtainRelatedAction.this.relateInfo);
                                ActSelectBleCurtainRelatedAction.this.relateInfoAssistant.setRelateInfo(position, ActSelectBleCurtainRelatedAction.this.relateInfo);
                            }
                        });
                    } else {
                        helper.setGone(R.id.iv_select, false).setGone(R.id.iv_select, false);
                        helper.setGone(R.id.layout_change_curtain_open_percent, false);
                    }
                    helper.setText(R.id.tv_name, data.getTitleName());
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$1(BaseQuickAdapter baseQuickAdapter, View view, int i) {
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
        baseQuickAdapter.notifyDataSetChanged();
        onItemClick(baseQuickAdapter, i);
    }

    private void onItemClick(BaseQuickAdapter<CurtainAction, BaseViewHolder> adapter, int position) {
        String productId = this.controlDevice.getProductId();
        productId.hashCode();
        switch (productId) {
            case "122041818260301":
            case "122041818283501":
            case "122041818304701":
            case "221042516351701":
            case "123072510445601":
            case "221030816330401":
            case "121042516340801":
            case "121042516345401":
                this.relateInfo.action = 4;
                break;
            default:
                setCurtainRelateInfo(position, this.relateInfo);
                break;
        }
        this.relateInfoAssistant.setRelateInfo(this.relatePosition, this.relateInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCurtainRelateInfo(int position, RelatedInfoExtParam.RelateInfo relateInfo) {
        switch (position) {
            case 0:
            case 1:
            case 2:
                relateInfo.action = position + 1;
                break;
            case 3:
            case 4:
            case 5:
            case 6:
                if (this.relateType == 1) {
                    relateInfo.action = 4;
                    relateInfo.keyActionExtra = position - 2;
                    break;
                } else {
                    relateInfo.action = 5;
                    relateInfo.keyActionExtra = 100 - this.mAdapter.getData().get(position).getCurtainPercent();
                    break;
                }
            case 7:
                relateInfo.action = 5;
                relateInfo.keyActionExtra = 100 - this.mAdapter.getData().get(position).getCurtainPercent();
                break;
        }
    }

    private void panelSubscribe() {
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
        SettingAssistant settingCmdAssistant = CmdAssistant.getSettingCmdAssistant(null, new int[0]);
        int i2 = this.relatePosition;
        settingCmdAssistant.subscribeBleCurtain(this, groupAddress, publicationAddress, i, 1 << i2, this.relateInfoAssistant.getRelateInfo(i2).action, this.relateInfo.keyActionExtra, new IAction() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActSelectBleCurtainRelatedAction$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj2) {
                ActSelectBleCurtainRelatedAction.this.lambda$panelSubscribe$2((Boolean) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$panelSubscribe$2(Boolean bool) {
        if (bool.booleanValue()) {
            uploadData();
        } else {
            dismissLoadingDialog();
            showFailDialog();
        }
    }

    private List<CurtainAction> getContentList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, CurtainRepository.getMotorKeyItem(CurtainRepository.BLE_MOTOR_KEY_NAME_UP).getName()));
        arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, CurtainRepository.getMotorKeyItem(CurtainRepository.BLE_MOTOR_KEY_NAME_DOWN).getName()));
        arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, CurtainRepository.getMotorKeyItem(CurtainRepository.BLE_MOTOR_KEY_NAME_STOP).getName()));
        if (!this.groupControl) {
            arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, CurtainRepository.getMotorKeyModeItem(CurtainRepository.BLE_MODE_WAKE_UP).getName()));
            arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, CurtainRepository.getMotorKeyModeItem(CurtainRepository.BLE_MODE_SLEEP).getName()));
            arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, CurtainRepository.getMotorKeyModeItem(CurtainRepository.BLE_MODE_GLOW).getName()));
            arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, CurtainRepository.getMotorKeyModeItem(CurtainRepository.BLE_MODE_RECEIVE_VISITOR).getName()));
        }
        arrayList.add(new CurtainAction(CurtainAction.TYPE_DIY, getString(R.string.app_str_curtain_locatedin)));
        Object obj = this.relateObject;
        if (obj instanceof Device) {
            Device device = (Device) obj;
            if (device.getExtParam() != null) {
                CurtainMotorInfoExtParam curtainMotorInfoExtParam = new CurtainMotorInfoExtParam();
                curtainMotorInfoExtParam.fillMapWithString(device.getExtParam());
                for (int i = 0; i < 4; i++) {
                    if (curtainMotorInfoExtParam.getModeInfo(i) != null) {
                        ((CurtainAction) arrayList.get(i + 3)).setTitleName(curtainMotorInfoExtParam.getModeInfo(i));
                    }
                }
            }
        }
        return arrayList;
    }

    private void uploadData() {
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.controlDevice.getDeviceId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActSelectBleCurtainRelatedAction$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectBleCurtainRelatedAction.this.lambda$uploadData$3((Disposable) obj);
            }
        }).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActSelectBleCurtainRelatedAction$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSelectBleCurtainRelatedAction.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActSelectBleCurtainRelatedAction$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectBleCurtainRelatedAction.this.lambda$uploadData$4(obj);
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
        lambda$initView$0(this);
    }

    public static final class CurtainAction {
        private static int TYPE_DEFAULT = 1;
        private static int TYPE_DIY = 2;
        private int curtainPercent;
        private String name;
        private String subName;
        private String titleName;
        private int type;

        CurtainAction(int type, String titleName) {
            this.type = type;
            this.titleName = titleName;
        }

        CurtainAction(int type, String titleName, String name, String subName) {
            this.type = type;
            this.titleName = titleName;
            this.name = name;
            this.subName = subName;
        }

        public int getCurtainPercent() {
            return this.curtainPercent;
        }

        public void setCurtainPercent(int curtainPercent) {
            this.curtainPercent = curtainPercent;
        }

        int getType() {
            return this.type;
        }

        String getName() {
            return this.name;
        }

        public String getSubName() {
            return this.subName;
        }

        public void setSubName(String subName) {
            this.subName = subName;
        }

        public String getTitleName() {
            return this.titleName;
        }

        public void setTitleName(String titleName) {
            this.titleName = titleName;
        }
    }

    private void showFailDialog() {
        runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActSelectBleCurtainRelatedAction.3
            @Override // java.lang.Runnable
            public void run() {
                ViewHelpUtil.showBindFailDialog(ActSelectBleCurtainRelatedAction.this);
            }
        });
    }
}