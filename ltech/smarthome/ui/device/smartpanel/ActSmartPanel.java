package com.ltech.smarthome.ui.device.smartpanel;

import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSmartPanelBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.bean.SwitchPanelState;
import com.smart.product_agreement.parser.IPanelParser;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSmartPanel extends BaseControlActivity<ActSmartPanelBinding, ActSmartPanelVM> {
    private int[] backgroundResArray;
    protected List<RelateInfoItem> dataList;
    protected BaseQuickAdapter<RelateInfoItem, BaseViewHolder> infoAdapter;
    private boolean isNewPanel;
    private GridLayoutManager mGridLayoutManager;
    private LinearLayoutManager mLinearLayoutManager;
    protected boolean[] selectArray = new boolean[8];
    private int spanCount;
    private int zoneCount;

    static /* synthetic */ boolean lambda$initRelatedInfoView$10(BaseDialog baseDialog, View view) {
        return false;
    }

    static /* synthetic */ boolean lambda$initRelatedInfoView$13(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.layout_load;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_smart_panel;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavHelper.goSetting(((ActSmartPanelVM) this.mViewModel).controlObject.getValue());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        LHomeLog.i(getClass(), "message_send startObserve() enter");
        ((ActSmartPanelVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActSmartPanelVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActSmartPanelVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActSmartPanelVM) this.mViewModel).controlType = getIntent().getIntExtra(Constants.LIGHT_TYPE, 0);
        ((ActSmartPanelVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        if (((ActSmartPanelVM) this.mViewModel).groupControl) {
            startGroupObserve();
        } else {
            startDeviceObserve();
        }
        ((ActSmartPanelVM) this.mViewModel).panelZoneStateLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel$$ExternalSyntheticLambda11
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSmartPanel.this.lambda$startObserve$0((List) obj);
            }
        });
        ((ActSmartPanelVM) this.mViewModel).updateUIEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel$$ExternalSyntheticLambda12
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSmartPanel.this.lambda$startObserve$1(obj);
            }
        });
        this.checkVersionFinish.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel$$ExternalSyntheticLambda13
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSmartPanel.this.lambda$startObserve$2((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(List list) {
        for (int i = 0; i < list.size(); i++) {
            this.selectArray[i] = ((SwitchPanelState.SwitchPanelZoneState) list.get(i)).isSwitchOnOff();
            BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = this.infoAdapter;
            if (baseQuickAdapter != null) {
                baseQuickAdapter.notifyItemChanged(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Object obj) {
        ((ActSmartPanelVM) this.mViewModel).controlObject.setValue(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r3) {
        if (((ActSmartPanelVM) this.mViewModel).groupControl) {
            ((ActSmartPanelVM) this.mViewModel).controlObject.setValue(Injection.repo().group().getGroupById(((ActSmartPanelVM) this.mViewModel).controlId));
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(this.defaultAdapter);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        if (this.needUpgrade) {
            if (isOwnerOrManager()) {
                goUpgrade(((ActSmartPanelVM) this.mViewModel).groupControl ? 9999L : ((ActSmartPanelVM) this.mViewModel).controlId);
            } else {
                showNoPermissionDialog();
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        startObjectObserve();
    }

    private void startObjectObserve() {
        if (((ActSmartPanelVM) this.mViewModel).groupControl) {
            Group groupById = Injection.repo().group().getGroupById(((ActSmartPanelVM) this.mViewModel).controlId);
            if (RelaySeparationHelper.isRelaySeparationPanelDevice(groupById)) {
                NavUtils.destination(ActNewSmartPanel.class).withLong(Constants.PLACE_ID, ((ActSmartPanelVM) this.mViewModel).placeId).withLong(Constants.CONTROL_ID, ((ActSmartPanelVM) this.mViewModel).controlId).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) groupById)).withBoolean(Constants.GROUP_CONTROL, true).navigation(this);
                finishActivity();
                return;
            } else {
                setTitle(groupById.getGroupName());
                checkVersionForUpdate(groupById, "SVer000.010.000");
                return;
            }
        }
        Device deviceById = Injection.repo().device().getDeviceById(((ActSmartPanelVM) this.mViewModel).controlId);
        try {
            setTitle(deviceById.getDeviceName());
            if (((ActSmartPanelVM) this.mViewModel).controlObject.getValue() == null) {
                LHomeLog.i(getClass(), "message_send (device)getDeviceFromDb mViewModel.controlObject.getValue()==null");
                ((ActSmartPanelVM) this.mViewModel).controlObject.setValue(deviceById);
            } else {
                if (HelpUtils.compareObject((Device) ((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), deviceById)) {
                    return;
                }
                LHomeLog.i(getClass(), "message_send device changed");
                ((ActSmartPanelVM) this.mViewModel).controlObject.setValue(deviceById);
            }
        } catch (Exception e) {
            LHomeLog.i(getClass(), "message_send (device)getDeviceFromDb Exception" + e.toString());
            e.printStackTrace();
        }
    }

    private void startDeviceObserve() {
        ((ActSmartPanelVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSmartPanel.this.lambda$startDeviceObserve$4(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0038, code lost:
    
        if (r2.equals(com.ltech.smarthome.model.product.ProductId.ID_SMART_SWITCH_S2C) != false) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$startDeviceObserve$4(java.lang.Object r11) {
        /*
            Method dump skipped, instructions count: 530
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel.lambda$startDeviceObserve$4(java.lang.Object):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDeviceObserve$3(Device device, ResponseMsg responseMsg) {
        ((ActSmartPanelVM) this.mViewModel).refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserSwitchPanelState(responseMsg.getAgreementId(), ((BleParam) device.getParam(BleParam.class)).getUnicastAddress(), this.zoneCount, responseMsg.getResData()));
    }

    private void startGroupObserve() {
        ((ActSmartPanelVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel$$ExternalSyntheticLambda14
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSmartPanel.this.lambda$startGroupObserve$6(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startGroupObserve$6(Object obj) {
        LHomeLog.i(getClass(), "message_send (group)mViewModel.controlObject enter");
        if (obj instanceof Group) {
            final Group group = (Group) obj;
            ((ActSmartPanelBinding) this.mViewBinding).ivSwitch5.setVisibility(0);
            int lightColorType = ProductRepository.getLightColorType((Object) group);
            if (lightColorType != 18) {
                switch (lightColorType) {
                    case 9:
                        this.spanCount = 2;
                        this.zoneCount = 2;
                        this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s2);
                        ((ActSmartPanelBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s2c_normal_1);
                        ((ActSmartPanelBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s1c_shadown_1);
                        ((ActSmartPanelBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s1c_shadown_3);
                        ((ActSmartPanelBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s1c_shadown_2);
                        break;
                    case 10:
                        this.spanCount = 3;
                        this.zoneCount = 3;
                        this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s3);
                        ((ActSmartPanelBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s3c_normal_1);
                        ((ActSmartPanelBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s1c_shadown_1);
                        ((ActSmartPanelBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s1c_shadown_3);
                        ((ActSmartPanelBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s1c_shadown_2);
                        break;
                    case 11:
                        this.spanCount = 2;
                        this.zoneCount = 4;
                        ((ActSmartPanelBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s4m_pic_3);
                        ((ActSmartPanelBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s4m_pic_4);
                        this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s4m);
                        ((ActSmartPanelBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s4m_pic_1);
                        ((ActSmartPanelBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s4m_pic_2);
                        break;
                    default:
                        this.spanCount = 1;
                        this.zoneCount = 1;
                        this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s1);
                        ((ActSmartPanelBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s1c_normal_1);
                        ((ActSmartPanelBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s1c_shadown_1);
                        ((ActSmartPanelBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s1c_shadown_3);
                        ((ActSmartPanelBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s1c_shadown_2);
                        break;
                }
            } else {
                this.spanCount = 4;
                this.zoneCount = 4;
                ((ActSmartPanelBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s4_shadown_1);
                ((ActSmartPanelBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s4_shadown_3);
                this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s4);
                ((ActSmartPanelBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s4_normal_1);
                ((ActSmartPanelBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s4_shadown_2);
            }
            if (group.getDeviceIds().isEmpty()) {
                initRelatedInfoView(group);
                return;
            }
            initRelatedInfoView(group);
            ((ActSmartPanelVM) this.mViewModel).queryPanelState(obj, 4, this.zoneCount);
            MessageManager.getInstance().setPanelSwitchStatusCallBack(new MessageManager.SmartPanelSwitchStatusCallBack() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel$$ExternalSyntheticLambda5
                @Override // com.smart.message.MessageManager.SmartPanelSwitchStatusCallBack
                public final void onDataReceive(ResponseMsg responseMsg) {
                    ActSmartPanel.this.lambda$startGroupObserve$5(group, responseMsg);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startGroupObserve$5(Group group, ResponseMsg responseMsg) {
        ((ActSmartPanelVM) this.mViewModel).refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserSwitchPanelState(responseMsg.getAgreementId(), group.getGroupAddress(), this.zoneCount, responseMsg.getResData()));
    }

    private void initRelatedInfoView(final Group group) {
        ((ActSmartPanelVM) this.mViewModel).initPanelState(group);
        ((ActSmartPanelVM) this.mViewModel).initRelateInfoList(group);
        int proPanelCount = ((ActSmartPanelVM) this.mViewModel).getProPanelCount(group);
        if (proPanelCount > 0) {
            if (((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getSwitchShowType() == 0) {
                MessageDialog.show(this, getString(R.string.tips), String.format(getString(R.string.group_switch_tip), Integer.valueOf(this.zoneCount))).setCancelable(false).setOkButton(getString(R.string.change_style), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel$$ExternalSyntheticLambda15
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        boolean lambda$initRelatedInfoView$7;
                        lambda$initRelatedInfoView$7 = ActSmartPanel.this.lambda$initRelatedInfoView$7(group, baseDialog, view);
                        return lambda$initRelatedInfoView$7;
                    }
                }).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.not_change_style), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel$$ExternalSyntheticLambda1
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        boolean lambda$initRelatedInfoView$8;
                        lambda$initRelatedInfoView$8 = ActSmartPanel.this.lambda$initRelatedInfoView$8(group, baseDialog, view);
                        return lambda$initRelatedInfoView$8;
                    }
                });
            } else if (((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getSwitchShowType() == 1 && proPanelCount == group.getDeviceIds().size()) {
                ((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.setSwitchShowType(2);
                group.setExtParam(((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getExtParamString());
                Injection.repo().group().saveGroup(group);
            }
        }
        if (((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getSwitchShowType() == 2) {
            NavUtils.destination(ActScreenPanel.class).withLong(Constants.PLACE_ID, ((ActSmartPanelVM) this.mViewModel).placeId).withLong(Constants.CONTROL_ID, ((ActSmartPanelVM) this.mViewModel).controlId).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) group)).withBoolean(Constants.GROUP_CONTROL, true).navigation(this);
            finishActivity();
        }
        this.selectArray = new boolean[((ActSmartPanelVM) this.mViewModel).relatedInfoList.size()];
        BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = this.infoAdapter;
        if (baseQuickAdapter == null) {
            BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_smart_panel_key, ((ActSmartPanelVM) this.mViewModel).relatedInfoList) { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel.1
                /* JADX INFO: Access modifiers changed from: protected */
                /* JADX WARN: Removed duplicated region for block: B:27:0x0183  */
                /* JADX WARN: Removed duplicated region for block: B:32:0x00a7  */
                /* JADX WARN: Removed duplicated region for block: B:38:0x0106  */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public void convert(com.chad.library.adapter.base.BaseViewHolder r8, com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem r9) {
                    /*
                        Method dump skipped, instructions count: 422
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel.AnonymousClass1.convert(com.chad.library.adapter.base.BaseViewHolder, com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem):void");
                }
            };
            this.infoAdapter = baseQuickAdapter2;
            baseQuickAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel$$ExternalSyntheticLambda2
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    ActSmartPanel.this.lambda$initRelatedInfoView$9(baseQuickAdapter3, view, i);
                }
            });
            this.infoAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel$$ExternalSyntheticLambda3
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemLongClickListener
                public final boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    boolean lambda$initRelatedInfoView$11;
                    lambda$initRelatedInfoView$11 = ActSmartPanel.this.lambda$initRelatedInfoView$11(baseQuickAdapter3, view, i);
                    return lambda$initRelatedInfoView$11;
                }
            });
            this.infoAdapter.bindToRecyclerView(((ActSmartPanelBinding) this.mViewBinding).rvKeyInfo);
            int i = this.spanCount;
            if (i == 2 || i == 3 || i == 4) {
                RecyclerView recyclerView = ((ActSmartPanelBinding) this.mViewBinding).rvKeyInfo;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, this.spanCount);
                this.mGridLayoutManager = gridLayoutManager;
                recyclerView.setLayoutManager(gridLayoutManager);
            } else {
                RecyclerView recyclerView2 = ((ActSmartPanelBinding) this.mViewBinding).rvKeyInfo;
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                this.mLinearLayoutManager = linearLayoutManager;
                recyclerView2.setLayoutManager(linearLayoutManager);
                ((ActSmartPanelBinding) this.mViewBinding).rvKeyInfo.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel.3
                    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);
                        outRect.set(0, 0, 0, 0);
                    }
                });
            }
            ((ActSmartPanelBinding) this.mViewBinding).rvKeyInfo.setHasFixedSize(true);
            ((DefaultItemAnimator) ((ActSmartPanelBinding) this.mViewBinding).rvKeyInfo.getItemAnimator()).setSupportsChangeAnimations(false);
            return;
        }
        baseQuickAdapter.replaceData(((ActSmartPanelVM) this.mViewModel).relatedInfoList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initRelatedInfoView$7(Group group, BaseDialog baseDialog, View view) {
        NavUtils.destination(ActScreenPanel.class).withLong(Constants.PLACE_ID, ((ActSmartPanelVM) this.mViewModel).placeId).withLong(Constants.CONTROL_ID, ((ActSmartPanelVM) this.mViewModel).controlId).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) group)).withBoolean(Constants.GROUP_CONTROL, true).navigation(this);
        finishActivity();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initRelatedInfoView$8(Group group, BaseDialog baseDialog, View view) {
        ((ActSmartPanelVM) this.mViewModel).changeShowType(group, 1);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRelatedInfoView$9(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.infoAdapter.getData().get(i);
        this.selectArray[i] = !r3[i];
        baseQuickAdapter.notifyItemChanged(i);
        if (((ActSmartPanelVM) this.mViewModel).groupControl) {
            ((ActSmartPanelVM) this.mViewModel).updateGroupStates(this.selectArray, (Group) ((ActSmartPanelVM) this.mViewModel).controlObject.getValue());
        }
        ((ActSmartPanelVM) this.mViewModel).sendSingleOnOff(i, this.selectArray[i]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initRelatedInfoView$11(BaseQuickAdapter baseQuickAdapter, View view, final int i) {
        if (Injection.repo().home().getSelectPlace().getValue().isMember()) {
            showNoPermissionDialog();
            return false;
        }
        if (this.infoAdapter.getData().get(i).infoName == null || this.infoAdapter.getData().get(i).type == 0) {
            MessageDialog.show(this, getString(R.string.tips), getString(R.string.app_str_smart_panel_bind_tip)).setCancelable(false).setOkButton(getString(R.string.app_str_continue_to_bind), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel.2
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public boolean onClick(BaseDialog baseDialog, View v) {
                    ((ActSmartPanelVM) ActSmartPanel.this.mViewModel).showBindDialog(i);
                    return false;
                }
            }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel$$ExternalSyntheticLambda6
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view2) {
                    return ActSmartPanel.lambda$initRelatedInfoView$10(baseDialog, view2);
                }
            });
        } else {
            showUnbindDialog(i);
        }
        return false;
    }

    private void initRelatedInfoView(final Device device) {
        ((ActSmartPanelVM) this.mViewModel).initPanelState(device);
        ((ActSmartPanelVM) this.mViewModel).initRelateInfoList(device);
        this.selectArray = new boolean[((ActSmartPanelVM) this.mViewModel).relatedInfoList.size()];
        BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = this.infoAdapter;
        if (baseQuickAdapter == null) {
            BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_smart_panel_key, ((ActSmartPanelVM) this.mViewModel).relatedInfoList) { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel.4
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, RelateInfoItem item) {
                    LinearLayout linearLayout;
                    ViewGroup.LayoutParams layoutParams;
                    linearLayout = (LinearLayout) helper.getView(R.id.layout_content);
                    layoutParams = helper.itemView.getLayoutParams();
                    String productId = device.getProductId();
                    productId.hashCode();
                    switch (productId) {
                        case "123072510445601":
                        case "121042516340801":
                        case "121042516345401":
                            linearLayout.setGravity(81);
                            linearLayout.setPadding(0, 0, 0, Utils.dip2px(this.mContext, 30.0f));
                            layoutParams.height = ActSmartPanel.this.mGridLayoutManager.getHeight();
                            break;
                        case "221030816330401":
                            linearLayout.setGravity(17);
                            layoutParams.height = ActSmartPanel.this.mGridLayoutManager.getHeight() / 2;
                            linearLayout.setPadding(0, 0, 0, 0);
                            break;
                        default:
                            linearLayout.setGravity(81);
                            layoutParams.height = ActSmartPanel.this.mLinearLayoutManager.getHeight();
                            linearLayout.setPadding(0, 0, 0, Utils.dip2px(this.mContext, 30.0f));
                            break;
                    }
                    helper.itemView.setBackgroundResource(ActSmartPanel.this.backgroundResArray[helper.getBindingAdapterPosition()]);
                    if (item.infoName == null || TextUtils.isEmpty(item.infoName)) {
                        if (((ActSmartPanelVM) ActSmartPanel.this.mViewModel).relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()) != null) {
                            if (((ActSmartPanelVM) ActSmartPanel.this.mViewModel).relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()).name == null || ((ActSmartPanelVM) ActSmartPanel.this.mViewModel).relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()).name.equals("")) {
                                helper.setText(R.id.tv_device_name, ActSmartPanel.this.getResources().getStringArray(R.array.smart_panel_s4_key_select)[helper.getAdapterPosition()]);
                            } else {
                                helper.setText(R.id.tv_device_name, ((ActSmartPanelVM) ActSmartPanel.this.mViewModel).relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()).name);
                            }
                        } else {
                            helper.setText(R.id.tv_device_name, ActSmartPanel.this.getResources().getStringArray(R.array.smart_panel_s4_key_select)[helper.getAdapterPosition()]);
                        }
                    } else {
                        helper.setText(R.id.tv_device_name, item.infoName);
                    }
                    if (item.infoName == null || item.type == 0) {
                        helper.setText(R.id.tv_sub_text, ActSmartPanel.this.getString(R.string.no_bind_object));
                    } else {
                        helper.setText(R.id.tv_sub_text, item.actionInfo);
                    }
                    RelatedInfoExtParam.RelateInfo relateInfo = ((ActSmartPanelVM) ActSmartPanel.this.mViewModel).relateInfoAssistant.getRelateInfo(helper.getAdapterPosition());
                    int i = R.color.colorPrimary;
                    if (relateInfo != null && ((ActSmartPanelVM) ActSmartPanel.this.mViewModel).relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()).objectId != 0 && item.infoName != null) {
                        helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(ActSmartPanel.this, R.color.colorPrimary));
                    } else {
                        ActSmartPanel actSmartPanel = ActSmartPanel.this;
                        if (!actSmartPanel.selectArray[helper.getAdapterPosition()]) {
                            i = ActSmartPanel.this.isNewPanel ? R.color.color_text_screen_panel_unselect : R.color.color_text_smart_panel_unselect;
                        }
                        helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(actSmartPanel, i));
                    }
                    ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
                }
            };
            this.infoAdapter = baseQuickAdapter2;
            baseQuickAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel$$ExternalSyntheticLambda9
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    ActSmartPanel.this.lambda$initRelatedInfoView$12(baseQuickAdapter3, view, i);
                }
            });
            this.infoAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel$$ExternalSyntheticLambda10
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemLongClickListener
                public final boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    boolean lambda$initRelatedInfoView$14;
                    lambda$initRelatedInfoView$14 = ActSmartPanel.this.lambda$initRelatedInfoView$14(baseQuickAdapter3, view, i);
                    return lambda$initRelatedInfoView$14;
                }
            });
            this.infoAdapter.bindToRecyclerView(((ActSmartPanelBinding) this.mViewBinding).rvKeyInfo);
            int i = this.spanCount;
            if (i == 2 || i == 3 || i == 4) {
                RecyclerView recyclerView = ((ActSmartPanelBinding) this.mViewBinding).rvKeyInfo;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, this.spanCount);
                this.mGridLayoutManager = gridLayoutManager;
                recyclerView.setLayoutManager(gridLayoutManager);
            } else {
                RecyclerView recyclerView2 = ((ActSmartPanelBinding) this.mViewBinding).rvKeyInfo;
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                this.mLinearLayoutManager = linearLayoutManager;
                recyclerView2.setLayoutManager(linearLayoutManager);
                ((ActSmartPanelBinding) this.mViewBinding).rvKeyInfo.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel.6
                    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);
                        outRect.set(0, 0, 0, 0);
                    }
                });
            }
            ((ActSmartPanelBinding) this.mViewBinding).rvKeyInfo.setHasFixedSize(true);
            ((DefaultItemAnimator) ((ActSmartPanelBinding) this.mViewBinding).rvKeyInfo.getItemAnimator()).setSupportsChangeAnimations(false);
            return;
        }
        baseQuickAdapter.replaceData(((ActSmartPanelVM) this.mViewModel).relatedInfoList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRelatedInfoView$12(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.infoAdapter.getData().get(i);
        this.selectArray[i] = !r3[i];
        baseQuickAdapter.notifyItemChanged(i);
        ((ActSmartPanelVM) this.mViewModel).sendSingleOnOff(i, this.selectArray[i]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initRelatedInfoView$14(BaseQuickAdapter baseQuickAdapter, View view, final int i) {
        if (Injection.repo().home().getSelectPlace().getValue().isMember()) {
            showNoPermissionDialog();
            return false;
        }
        if (this.infoAdapter.getData().get(i).infoName == null || this.infoAdapter.getData().get(i).type == 0) {
            MessageDialog.show(this, getString(R.string.tips), getString(R.string.app_str_smart_panel_bind_tip)).setCancelable(false).setOkButton(getString(R.string.app_str_continue_to_bind), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel.5
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public boolean onClick(BaseDialog baseDialog, View v) {
                    ((ActSmartPanelVM) ActSmartPanel.this.mViewModel).showBindDialog(i);
                    return false;
                }
            }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel$$ExternalSyntheticLambda4
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view2) {
                    return ActSmartPanel.lambda$initRelatedInfoView$13(baseDialog, view2);
                }
            });
        } else {
            showUnbindDialog(i);
        }
        return false;
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    private void showUnbindDialog(final int position) {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.reset_relate));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanel.this.lambda$showUnbindDialog$15(position, (Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnbindDialog$15(int i, Integer num) {
        ((ActSmartPanelVM) this.mViewModel).unBindRelateInfo(i);
    }
}