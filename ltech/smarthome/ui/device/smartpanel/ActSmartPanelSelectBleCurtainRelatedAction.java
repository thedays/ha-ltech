package com.ltech.smarthome.ui.device.smartpanel;

import android.view.View;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSelectBleCurtainActionBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.curtain_motor.CurtainMotorInfoExtParam;
import com.ltech.smarthome.ui.device.curtain_motor.CurtainRepository;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.smart.message.utils.LHomeLog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSmartPanelSelectBleCurtainRelatedAction extends VMActivity<ActSelectBleCurtainActionBinding, ActSmartPanelSelectActionVM> implements ISelectAction {
    private List<CurtainAction> dataList;
    private MultipleItemRvAdapter<CurtainAction, BaseViewHolder> mAdapter;
    private int progress;
    RelatedInfoExtParam.RelateInfo relateInfo;
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
    public /* synthetic */ void lambda$edit$1(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (this.selectPosition == -1) {
            SmartToast.showShort(R.string.please_choose);
            return;
        }
        if (Injection.state().isConnectOuterNet()) {
            if (((ActSmartPanelSelectActionVM) this.mViewModel).groupControl) {
                showLoadingDialog(getString(R.string.subscribing));
                ((ActSmartPanelSelectActionVM) this.mViewModel).subscribe(this.relateInfo.type, this.relateInfo.keyActionExtra);
                return;
            } else if (((ActSmartPanelSelectActionVM) this.mViewModel).controlDevice != null && ((ActSmartPanelSelectActionVM) this.mViewModel).controlDevice.getProductId().equals(ProductId.ID_SMART_PANEL_S6B) && RelateInfoUtils.needShowTipDialog()) {
                RelateInfoUtils.showImageTipDialog(getString(R.string.s6b_click_tip), R.mipmap.pic_click_tip_s6b, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectBleCurtainRelatedAction$$ExternalSyntheticLambda1
                    @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                    public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                        ActSmartPanelSelectBleCurtainRelatedAction.this.lambda$edit$0(imageTipDialog);
                    }
                });
                return;
            } else {
                showLoadingDialog(getString(R.string.subscribing));
                ((ActSmartPanelSelectActionVM) this.mViewModel).subscribe(this.relateInfo.type, this.relateInfo.keyActionExtra);
                return;
            }
        }
        SmartToast.showShort(R.string.no_network);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$0(ImageTipDialog imageTipDialog) {
        showLoadingDialog(getString(R.string.subscribing));
        ((ActSmartPanelSelectActionVM) this.mViewModel).subscribe(this.relateInfo.type, this.relateInfo.keyActionExtra);
        imageTipDialog.dismissDialog();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_action));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        ((ActSmartPanelSelectActionVM) this.mViewModel).relatePosition = getIntent().getIntExtra(Constants.RELATED_POSITION, -1);
        ((ActSmartPanelSelectActionVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        ((ActSmartPanelSelectActionVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActSmartPanelSelectActionVM) this.mViewModel).relateType = getIntent().getIntExtra(Constants.GROUP_RELATE, 1);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        Device deviceByDeviceId;
        super.startObserve();
        ((ActSmartPanelSelectActionVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        if (((ActSmartPanelSelectActionVM) this.mViewModel).relateType == 2) {
            Group groupById = Injection.repo().group().getGroupById(getIntent().getLongExtra(Constants.RELATE_ID, -1L));
            ((ActSmartPanelSelectActionVM) this.mViewModel).relateObject = groupById;
            RelatedInfoExtParam.RelateInfo RelatedGroupInfo = RelatedInfoExtParam.RelateInfo.RelatedGroupInfo(((Group) ((ActSmartPanelSelectActionVM) this.mViewModel).relateObject).getGroupId());
            this.relateInfo = RelatedGroupInfo;
            RelatedGroupInfo.type = 5;
            if (groupById.getDeviceIds() != null && groupById.getDeviceIds().size() > 0 && (deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(groupById.getDeviceIds().get(0).longValue())) != null && deviceByDeviceId.getDeviceState().getCurtainMotorState() != null) {
                this.progress = deviceByDeviceId.getDeviceState().getCurtainMotorState().getTravelPercent();
            }
        } else {
            Device deviceById = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.RELATE_ID, -1L));
            ((ActSmartPanelSelectActionVM) this.mViewModel).relateObject = deviceById;
            RelatedInfoExtParam.RelateInfo RelateCurtainDeviceInfo = RelatedInfoExtParam.RelateInfo.RelateCurtainDeviceInfo(((Device) ((ActSmartPanelSelectActionVM) this.mViewModel).relateObject).getDeviceId());
            this.relateInfo = RelateCurtainDeviceInfo;
            RelateCurtainDeviceInfo.type = 4;
            if (deviceById.getDeviceState().getCurtainMotorState() != null) {
                this.progress = deviceById.getDeviceState().getCurtainMotorState().getTravelPercent();
            }
        }
        if (((ActSmartPanelSelectActionVM) this.mViewModel).groupControl) {
            ((ActSmartPanelSelectActionVM) this.mViewModel).controlGroup = Injection.repo().group().getGroupById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
            ((ActSmartPanelSelectActionVM) this.mViewModel).relateInfoAssistant = new RelateInfoAssistant(((ActSmartPanelSelectActionVM) this.mViewModel).controlGroup);
        } else {
            ((ActSmartPanelSelectActionVM) this.mViewModel).controlDevice = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
            ((ActSmartPanelSelectActionVM) this.mViewModel).relateInfoAssistant = new RelateInfoAssistant(((ActSmartPanelSelectActionVM) this.mViewModel).controlDevice);
        }
        initRv();
    }

    private void initRv() {
        List<CurtainAction> contentList = getContentList();
        this.dataList = contentList;
        this.selectArray = new boolean[contentList.size()];
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.dataList);
        this.mAdapter = anonymousClass1;
        anonymousClass1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectBleCurtainRelatedAction$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSmartPanelSelectBleCurtainRelatedAction.this.lambda$initRv$1(baseQuickAdapter, view, i);
            }
        });
        ((ActSelectBleCurtainActionBinding) this.mViewBinding).rvContent.addOnItemTouchListener(new OnItemChildClickListener(this) { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectBleCurtainRelatedAction.2
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

    /* renamed from: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectBleCurtainRelatedAction$1, reason: invalid class name */
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
            this.mProviderDelegate.registerProvider(new BaseItemProvider<CurtainAction, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectBleCurtainRelatedAction.1.1
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
                    if (helper.getAdapterPosition() == ActSmartPanelSelectBleCurtainRelatedAction.this.selectPosition) {
                        helper.setImageResource(R.id.iv_select, R.mipmap.ic_tick_sel).setGone(R.id.iv_select, true);
                    } else {
                        helper.setGone(R.id.iv_select, false);
                    }
                    helper.setText(R.id.tv_name, data.getTitleName());
                }
            });
            this.mProviderDelegate.registerProvider(new BaseItemProvider<CurtainAction, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectBleCurtainRelatedAction.1.2
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
                    if (helper.getAdapterPosition() == ActSmartPanelSelectBleCurtainRelatedAction.this.selectPosition) {
                        helper.setImageResource(R.id.iv_select, R.mipmap.ic_tick_sel).setGone(R.id.iv_select, true);
                        helper.setGone(R.id.layout_change_curtain_open_percent, true);
                        ((LightBrtBar) helper.getView(R.id.sb_brt)).setIncludeZero(true);
                        ((LightBrtBar) helper.getView(R.id.sb_brt)).setProgress(ActSmartPanelSelectBleCurtainRelatedAction.this.progress);
                        if (ActSmartPanelSelectBleCurtainRelatedAction.this.progress == 0) {
                            helper.setText(R.id.tv_brt, ActSmartPanelSelectBleCurtainRelatedAction.this.getString(R.string.app_str_all_close));
                        } else if (ActSmartPanelSelectBleCurtainRelatedAction.this.progress == 100) {
                            helper.setText(R.id.tv_brt, ActSmartPanelSelectBleCurtainRelatedAction.this.getString(R.string.app_str_all_open));
                        } else {
                            helper.setText(R.id.tv_brt, ActSmartPanelSelectBleCurtainRelatedAction.this.progress + "%");
                        }
                        data.setCurtainPercent(ActSmartPanelSelectBleCurtainRelatedAction.this.progress);
                        ActSmartPanelSelectBleCurtainRelatedAction.this.setCurtainRelateInfo(position, ActSmartPanelSelectBleCurtainRelatedAction.this.relateInfo);
                        ((ActSmartPanelSelectActionVM) ActSmartPanelSelectBleCurtainRelatedAction.this.mViewModel).relateInfoAssistant.setRelateInfo(((ActSmartPanelSelectActionVM) ActSmartPanelSelectBleCurtainRelatedAction.this.mViewModel).relatePosition, ActSmartPanelSelectBleCurtainRelatedAction.this.relateInfo);
                        ((LightBrtBar) helper.getView(R.id.sb_brt)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectBleCurtainRelatedAction.1.2.1
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
                                    helper.setText(R.id.tv_brt, ActSmartPanelSelectBleCurtainRelatedAction.this.getString(R.string.app_str_all_close));
                                } else if (lightBrtBar.getProgress() == 100) {
                                    helper.setText(R.id.tv_brt, ActSmartPanelSelectBleCurtainRelatedAction.this.getString(R.string.app_str_all_open));
                                } else {
                                    helper.setText(R.id.tv_brt, lightBrtBar.getProgress() + "%");
                                }
                                data.setCurtainPercent(lightBrtBar.getProgress());
                                ActSmartPanelSelectBleCurtainRelatedAction.this.setCurtainRelateInfo(position, ActSmartPanelSelectBleCurtainRelatedAction.this.relateInfo);
                                ((ActSmartPanelSelectActionVM) ActSmartPanelSelectBleCurtainRelatedAction.this.mViewModel).relateInfoAssistant.setRelateInfo(((ActSmartPanelSelectActionVM) ActSmartPanelSelectBleCurtainRelatedAction.this.mViewModel).relatePosition, ActSmartPanelSelectBleCurtainRelatedAction.this.relateInfo);
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
        if (((ActSmartPanelSelectActionVM) this.mViewModel).groupControl) {
            setCurtainRelateInfo(position, this.relateInfo);
        } else {
            String productId = ((ActSmartPanelSelectActionVM) this.mViewModel).controlDevice.getProductId();
            productId.hashCode();
            switch (productId) {
                case "122041818260301":
                case "122041818283501":
                case "122041818304701":
                case "3683369128495808":
                case "4249823578721536":
                case "3701704216101056":
                case "123031312002001":
                case "221042516351701":
                case "123072510445601":
                case "221030816330401":
                case "3701703750123712":
                case "121031814513301":
                case "121042516340801":
                case "121042516345401":
                    setCurtainRelateInfo(position, this.relateInfo);
                    break;
            }
        }
        ((ActSmartPanelSelectActionVM) this.mViewModel).relateInfoAssistant.setRelateInfo(((ActSmartPanelSelectActionVM) this.mViewModel).relatePosition, this.relateInfo);
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
                relateInfo.action = position + 3;
                break;
            case 7:
            case 8:
            case 9:
            case 10:
                if (((ActSmartPanelSelectActionVM) this.mViewModel).relateType == 1) {
                    relateInfo.action = 4;
                    relateInfo.keyActionExtra = position - 6;
                    break;
                } else {
                    relateInfo.action = 5;
                    relateInfo.keyActionExtra = 100 - this.mAdapter.getData().get(position).getCurtainPercent();
                    break;
                }
            case 11:
                relateInfo.action = 5;
                relateInfo.keyActionExtra = 100 - this.mAdapter.getData().get(position).getCurtainPercent();
                break;
        }
    }

    private List<CurtainAction> getContentList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, CurtainRepository.getMotorKeyItem(CurtainRepository.BLE_MOTOR_KEY_NAME_UP).getName()));
        arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, CurtainRepository.getMotorKeyItem(CurtainRepository.BLE_MOTOR_KEY_NAME_DOWN).getName()));
        arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, CurtainRepository.getMotorKeyItem(CurtainRepository.BLE_MOTOR_KEY_NAME_STOP).getName()));
        arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, CurtainRepository.getMotorKeyItem(CurtainRepository.BLE_MOTOR_KEY_NAME_UP_DOWN).getName()));
        arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, CurtainRepository.getMotorKeyItem(CurtainRepository.BLE_MOTOR_KEY_NAME_UP_STOP_DOWN_STOP).getName()));
        arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, CurtainRepository.getMotorKeyItem(CurtainRepository.BLE_MOTOR_KEY_NAME_OPEN_STOP).getName()));
        arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, CurtainRepository.getMotorKeyItem(CurtainRepository.BLE_MOTOR_KEY_NAME_OFF_STOP).getName()));
        if (((ActSmartPanelSelectActionVM) this.mViewModel).relateType != 2) {
            arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, CurtainRepository.getMotorKeyModeItem(CurtainRepository.BLE_MODE_WAKE_UP).getName()));
            arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, CurtainRepository.getMotorKeyModeItem(CurtainRepository.BLE_MODE_SLEEP).getName()));
            arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, CurtainRepository.getMotorKeyModeItem(CurtainRepository.BLE_MODE_GLOW).getName()));
            arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, CurtainRepository.getMotorKeyModeItem(CurtainRepository.BLE_MODE_RECEIVE_VISITOR).getName()));
        }
        arrayList.add(new CurtainAction(CurtainAction.TYPE_DIY, getString(R.string.app_str_curtain_locatedin)));
        if (((ActSmartPanelSelectActionVM) this.mViewModel).relateObject instanceof Device) {
            Device device = (Device) ((ActSmartPanelSelectActionVM) this.mViewModel).relateObject;
            if (device.getExtParam() != null) {
                CurtainMotorInfoExtParam curtainMotorInfoExtParam = new CurtainMotorInfoExtParam();
                curtainMotorInfoExtParam.fillMapWithString(device.getExtParam());
                for (int i = 0; i < 4; i++) {
                    if (curtainMotorInfoExtParam.getModeInfo(i) != null) {
                        ((CurtainAction) arrayList.get(i + 7)).setTitleName(curtainMotorInfoExtParam.getModeInfo(i));
                    }
                }
            }
        }
        return arrayList;
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
}