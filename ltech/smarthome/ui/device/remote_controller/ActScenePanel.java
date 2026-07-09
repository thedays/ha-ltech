package com.ltech.smarthome.ui.device.remote_controller;

import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.databinding.ActScenePanelBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.automation.ActAddAutomation;
import com.ltech.smarthome.ui.config.ActMeshScan;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectBleDeviceAndGroupNew;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.ui.scene.ActSelectCgdPro;
import com.ltech.smarthome.upgrade.UpgradeFactory;
import com.ltech.smarthome.upgrade.UpgradeInfo;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.TextClickUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.ButtonTipDialog;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.bean.ProductVersionInfo;
import com.smart.product_agreement.parser.IUpgradeParser;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActScenePanel extends BaseControlActivity<ActScenePanelBinding, ActSmartPanelVM> {
    private int[] backgroundResArray;
    private int controlType;
    protected List<RelateInfoItem> dataList;
    protected BaseQuickAdapter<RelateInfoItem, BaseViewHolder> infoAdapter;
    private int spanCount;
    private UpgradeInfo upgradeInfo;
    private ProductVersionInfo versionInfo;
    private DefaultAdapter mDefaultAdapter = new DefaultAdapter();
    boolean needUpgrade = false;
    protected boolean[] selectArray = new boolean[8];
    public SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();
    private boolean isFirst = true;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.layout_load;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_scene_panel;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        super.showContent();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        SharedPreferenceUtil.edit().keepShared(Constants.FIRM_NEED_UPGRADE, this.needUpgrade);
        NavHelper.goSetting(((ActSmartPanelVM) this.mViewModel).controlObject.getValue());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(this.mDefaultAdapter);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        startObjectObserve();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        if (this.needUpgrade) {
            if (((Place) SharedPreferenceUtil.getBean(Constants.SELECT_PLACE, Place.class)).isManager() || ((Place) SharedPreferenceUtil.getBean(Constants.SELECT_PLACE, Place.class)).isOwner()) {
                NavUtils.destination(ActMeshScan.class).withLong(Constants.CONTROL_ID, 9999L).navigation(ActivityUtils.getTopActivity());
                return;
            } else {
                this.showNoPermissionDialogEvent.call();
                return;
            }
        }
        startObjectObserve();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActSmartPanelVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActSmartPanelVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActSmartPanelVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        this.controlType = getIntent().getIntExtra(Constants.LIGHT_TYPE, 0);
        ((ActSmartPanelVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        ((ActSmartPanelVM) this.mViewModel).updateUIEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActScenePanel$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActScenePanel.this.lambda$startObserve$0(obj);
            }
        });
        this.showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActScenePanel$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActScenePanel.this.lambda$startObserve$1((Void) obj);
            }
        });
        startDeviceObserve();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Object obj) {
        ((ActSmartPanelVM) this.mViewModel).controlObject.setValue(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r1) {
        showNoPermissionDialog();
    }

    private void startObjectObserve() {
        Injection.repo().device().getDeviceFromDb(((ActSmartPanelVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActScenePanel$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActScenePanel.this.lambda$startObjectObserve$2((Device) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObjectObserve$2(Device device) {
        LHomeLog.i(getClass(), "message_send (device)getDeviceFromDb enter");
        ((ActSmartPanelVM) this.mViewModel).controlObject.setValue(device);
    }

    private void startDeviceObserve() {
        ((ActSmartPanelVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActScenePanel$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActScenePanel.this.lambda$startDeviceObserve$3(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDeviceObserve$3(Object obj) {
        LHomeLog.i(getClass(), "message_send (device)mViewModel.controlObject enter");
        if (obj instanceof Device) {
            Device device = (Device) obj;
            setTitle(device.getDeviceName());
            if (this.mViewBinding != 0) {
                setEditImage(R.mipmap.ic_setting);
            }
            String productId = device.getProductId();
            productId.hashCode();
            if (productId.equals(ProductId.ID_SMART_PANEL_S6B)) {
                TextClickUtil.setTextClick(this, ((ActScenePanelBinding) this.mViewBinding).tvTipMessage, new ClickableSpan() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActScenePanel.1
                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(ActScenePanel.this.getResources().getColor(R.color.color_text_blue));
                        ds.setUnderlineText(false);
                        ds.clearShadowLayer();
                    }

                    @Override // android.text.style.ClickableSpan
                    public void onClick(View view) {
                        ButtonTipDialog.asDefault().setTitle(ActScenePanel.this.getString(R.string.button_tip_title)).setConfirmString(ActScenePanel.this.getString(R.string.i_know)).showDialog(ActScenePanel.this.activity);
                    }
                }, getString(R.string.s6b_button_tip), getString(R.string.app_str_smart_panel_tip_msg_s6b, new Object[]{getString(R.string.s6b_button_tip)}));
                ((ActScenePanelBinding) this.mViewBinding).ivLogo.setVisibility(8);
                ((ActScenePanelBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s6b_normal);
                this.spanCount = 2;
            } else if (productId.equals(ProductId.ID_SCENE_PANEL_S8)) {
                ((ActScenePanelBinding) this.mViewBinding).ivLogo.setVisibility(0);
                ((ActScenePanelBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s4m_pic_1);
                this.spanCount = 2;
            }
            initRelatedInfoView(device);
        }
    }

    private void initRelatedInfoView(final Device device) {
        ((ActSmartPanelVM) this.mViewModel).initRelateInfoList(device);
        this.selectArray = new boolean[((ActSmartPanelVM) this.mViewModel).relatedInfoList.size()];
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, this.spanCount);
        BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = this.infoAdapter;
        if (baseQuickAdapter == null) {
            BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_scene_panel_key, ((ActSmartPanelVM) this.mViewModel).relatedInfoList) { // from class: com.ltech.smarthome.ui.device.remote_controller.ActScenePanel.2
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, RelateInfoItem item) {
                    if (item.infoName != null && !TextUtils.isEmpty(item.infoName)) {
                        helper.setText(R.id.tv_device_name, item.infoName);
                    } else {
                        helper.setText(R.id.tv_device_name, ActScenePanel.this.getResources().getStringArray(R.array.smart_scene_panel_s8_key_select)[helper.getAdapterPosition()]);
                    }
                    if (item.infoName == null || item.type == 0) {
                        helper.setText(R.id.tv_sub_text, ActScenePanel.this.getString(R.string.no_bind_object));
                    } else {
                        helper.setText(R.id.tv_sub_text, item.actionInfo);
                    }
                    ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
                    ViewGroup.LayoutParams layoutParams = helper.itemView.getLayoutParams();
                    if (device.getProductId().equals(ProductId.ID_SMART_PANEL_S6B)) {
                        layoutParams.height = gridLayoutManager.getHeight() / 3;
                    } else if (device.getProductId().equals(ProductId.ID_SCENE_PANEL_S8)) {
                        layoutParams.height = gridLayoutManager.getHeight() / 4;
                    }
                }
            };
            this.infoAdapter = baseQuickAdapter2;
            baseQuickAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActScenePanel$$ExternalSyntheticLambda1
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    ActScenePanel.this.lambda$initRelatedInfoView$4(baseQuickAdapter3, view, i);
                }
            });
            if (this.infoAdapter == null || this.mViewBinding == 0) {
                return;
            }
            this.infoAdapter.bindToRecyclerView(((ActScenePanelBinding) this.mViewBinding).rvKeyInfo);
            ((ActScenePanelBinding) this.mViewBinding).rvKeyInfo.setLayoutManager(gridLayoutManager);
            ((ActScenePanelBinding) this.mViewBinding).rvKeyInfo.setHasFixedSize(true);
            ((DefaultItemAnimator) ((ActScenePanelBinding) this.mViewBinding).rvKeyInfo.getItemAnimator()).setSupportsChangeAnimations(false);
            return;
        }
        baseQuickAdapter.setNewData(((ActSmartPanelVM) this.mViewModel).relatedInfoList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRelatedInfoView$4(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (getCurrentPlace().isOwner() || getCurrentPlace().isManager()) {
            if (this.infoAdapter.getData().get(i).infoName == null || this.infoAdapter.getData().get(i).type == 0) {
                showBindDialog(i);
                return;
            } else {
                showUnbindDialog(i);
                return;
            }
        }
        this.showNoPermissionDialogEvent.call();
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    private void showBindDialog(final int position) {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.device));
        arrayList.add(getString(R.string.local_scene));
        arrayList.add(getString(R.string.dali_scene));
        arrayList.add(getString(R.string.link_automation));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActScenePanel$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActScenePanel.this.lambda$showBindDialog$5(position, (Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBindDialog$5(int i, Integer num) {
        int intValue = num.intValue();
        if (intValue == 0) {
            NavUtils.destination(ActSmartPanelSelectBleDeviceAndGroupNew.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActSmartPanelVM) this.mViewModel).controlId).withInt(Constants.RELATED_POSITION, i).withString(Constants.PRODUCT_ID, ((ActSmartPanelVM) this.mViewModel).productId).withBoolean(Constants.GROUP_CONTROL, ((ActSmartPanelVM) this.mViewModel).groupControl).withInt(Constants.GROUP_RELATE, 1).withInt(Constants.LIGHT_TYPE, this.controlType).withDefaultRequestCode().navigation(this);
            return;
        }
        if (intValue == 1) {
            NavUtils.destination(ActSmartPanelSelectScene.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActSmartPanelVM) this.mViewModel).controlId).withInt(Constants.RELATED_POSITION, i).withDefaultRequestCode().navigation(this);
        } else if (intValue == 2) {
            NavUtils.destination(ActSelectCgdPro.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActSmartPanelVM) this.mViewModel).controlId).withBoolean(Constants.GROUP_CONTROL, false).withInt(Constants.RELATED_POSITION, i).withDefaultRequestCode().navigation(this);
        } else {
            if (intValue != 3) {
                return;
            }
            NavUtils.destination(ActAddAutomation.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withInt(Constants.RELATED_POSITION, i).withLong(Constants.CONTROL_ID, ((ActSmartPanelVM) this.mViewModel).controlId).navigation(this);
        }
    }

    private void showUnbindDialog(final int position) {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.reset_relate));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActScenePanel$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActScenePanel.this.lambda$showUnbindDialog$7(position, (Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnbindDialog$7(final int i, Integer num) {
        if (ProductId.ID_SMART_PANEL_S6B.equals(((ActSmartPanelVM) this.mViewModel).productId) && RelateInfoUtils.needShowTipDialog()) {
            RelateInfoUtils.showImageTipDialog(getString(R.string.s6b_click_tip), R.mipmap.pic_click_tip_s6b, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActScenePanel$$ExternalSyntheticLambda6
                @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                    ActScenePanel.this.lambda$showUnbindDialog$6(i, imageTipDialog);
                }
            });
        } else {
            ((ActSmartPanelVM) this.mViewModel).unBindRelateInfo(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnbindDialog$6(int i, ImageTipDialog imageTipDialog) {
        imageTipDialog.dismissDialog();
        ((ActSmartPanelVM) this.mViewModel).unBindRelateInfo(i);
    }

    private void queryBleDeviceVersion(final Device device) {
        if (!this.isFirst) {
            this.spanCount = 2;
            initRelatedInfoView(device);
            showContent();
        } else {
            showLoading();
            CmdAssistant.getQueryCmdAssistant(device, new int[0]).queryProductVersion(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActScenePanel$$ExternalSyntheticLambda7
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActScenePanel.this.lambda$queryBleDeviceVersion$8(device, (ResponseMsg) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryBleDeviceVersion$8(Device device, ResponseMsg responseMsg) {
        if (responseMsg == null) {
            this.mDefaultAdapter.errorTryStringRes(R.string.retry);
            showError();
            return;
        }
        this.isFirst = false;
        if (responseMsg.getResData().length() >= 16) {
            this.versionInfo = ((IUpgradeParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserUpgradeInfo(new String(StringUtils.hexStringToByte(responseMsg.getResData().substring(16))));
        }
        ProductVersionInfo productVersionInfo = this.versionInfo;
        if (productVersionInfo == null) {
            this.mDefaultAdapter.errorTryStringRes(R.string.retry);
            showError();
            return;
        }
        UpgradeInfo upgradeInfo = UpgradeFactory.getUpgradeInfo(productVersionInfo);
        this.upgradeInfo = upgradeInfo;
        if (upgradeInfo == null || "SVer000.002.000".compareTo(this.versionInfo.getsVer()) < 0) {
            LHomeLog.i("Test", getClass(), "当前固件版本为最新版本");
            if (this.mViewBinding != 0) {
                setEditImage(R.mipmap.ic_setting);
            }
            this.spanCount = 2;
            initRelatedInfoView(device);
            showContent();
            return;
        }
        LHomeLog.i("Test", getClass(), "固件版本过低，需要升级后才能使用");
        CmdAssistant.getSettingCmdAssistant(null, new int[0]).unSubscribePublishAddress(ActivityUtils.getTopActivity(), ((BleParam) device.getParam(BleParam.class)).getPublicationAddress(), ProductRepository.getAgreementIdByPid(device.getProductId()), new int[0]);
        ((ActSmartPanelVM) this.mViewModel).uploadClearDataS8();
        this.mDefaultAdapter.loadFailStringRes(R.string.app_str_s8_upgrade_tips).errorTryStringRes(R.string.firmware_upgrade);
        this.needUpgrade = true;
        showError();
        setEditImage(0);
    }
}