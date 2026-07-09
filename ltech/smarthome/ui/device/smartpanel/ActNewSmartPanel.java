package com.ltech.smarthome.ui.device.smartpanel;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActNewSmartPanelBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.LanguageUtils;
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
public class ActNewSmartPanel extends BaseControlActivity<ActNewSmartPanelBinding, ActSmartPanelVM> {
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
        return R.layout.act_new_smart_panel;
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
        ((ActSmartPanelVM) this.mViewModel).panelZoneStateLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel$$ExternalSyntheticLambda15
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActNewSmartPanel.this.lambda$startObserve$0((List) obj);
            }
        });
        ((ActSmartPanelVM) this.mViewModel).updateUIEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel$$ExternalSyntheticLambda16
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActNewSmartPanel.this.lambda$startObserve$1(obj);
            }
        });
        this.checkVersionFinish.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActNewSmartPanel.this.lambda$startObserve$2((Void) obj);
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
            ((ActSmartPanelVM) this.mViewModel).isPro = ((ActSmartPanelVM) this.mViewModel).getProPanelCount(groupById) > 0;
            if (!RelaySeparationHelper.isRelaySeparationPanelDevice(groupById)) {
                NavUtils.destination(ActSmartPanel.class).withLong(Constants.PLACE_ID, ((ActSmartPanelVM) this.mViewModel).placeId).withLong(Constants.CONTROL_ID, ((ActSmartPanelVM) this.mViewModel).controlId).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) groupById)).withBoolean(Constants.GROUP_CONTROL, true).navigation(this);
                finishActivity();
                return;
            } else {
                setTitle(groupById.getGroupName());
                checkVersionForUpdate(groupById, "SVer000.010.000");
                return;
            }
        }
        Device deviceById = Injection.repo().device().getDeviceById(((ActSmartPanelVM) this.mViewModel).controlId);
        if (!ProductId.ID_SMART_SWITCH_S1_PRO.equals(deviceById.getProductId()) && !ProductId.ID_SMART_SWITCH_S2_PRO.equals(deviceById.getProductId()) && !ProductId.ID_SMART_SWITCH_S3_PRO.equals(deviceById.getProductId()) && !ProductId.ID_SMART_SWITCH_S6_PRO.equals(deviceById.getProductId()) && !ProductId.ID_SMART_PANEL_G4.equals(deviceById.getProductId()) && !ProductId.ID_SMART_PANEL_G4_PRO.equals(deviceById.getProductId())) {
            ((ActSmartPanelVM) this.mViewModel).isPro = false;
        }
        try {
            setTitle(deviceById.getDeviceName());
            ((ActSmartPanelVM) this.mViewModel).controlObject.setValue(deviceById);
        } catch (Exception e) {
            LHomeLog.i(getClass(), "message_send (device)getDeviceFromDb Exception" + e.toString());
            e.printStackTrace();
        }
    }

    private void startDeviceObserve() {
        ((ActSmartPanelVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel$$ExternalSyntheticLambda12
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActNewSmartPanel.this.lambda$startDeviceObserve$4(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public /* synthetic */ void lambda$startDeviceObserve$4(Object obj) {
        char c2;
        LHomeLog.i(getClass(), "message_send (device)mViewModel.controlObject enter");
        if (obj instanceof Device) {
            final Device device = (Device) obj;
            BleParam bleParam = (BleParam) device.getParam(BleParam.class);
            ((ActNewSmartPanelBinding) this.mViewBinding).ivSwitch5.setVisibility(0);
            String productId = device.getProductId();
            switch (productId.hashCode()) {
                case -1082613022:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_S6)) {
                        c2 = 5;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -835060954:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                        c2 = 6;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -732569219:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_S4)) {
                        c2 = 2;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 225641606:
                    if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                        c2 = 1;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 1097035898:
                    if (productId.equals(ProductId.ID_SCENE_PANEL_S8)) {
                        c2 = 3;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 1951402182:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                        c2 = 4;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 1951547293:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                        c2 = 0;
                        break;
                    }
                    c2 = 65535;
                    break;
                default:
                    c2 = 65535;
                    break;
            }
            if (c2 == 0) {
                this.spanCount = 2;
                this.zoneCount = 2;
                if (bleParam.getBleType() == 1065) {
                    this.isNewPanel = true;
                    this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s2);
                } else {
                    this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s2c);
                }
                ((ActNewSmartPanelBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s2c_normal_1);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s1c_shadown_1);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s1c_shadown_3);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s1c_shadown_2);
            } else if (c2 == 1) {
                this.spanCount = 2;
                this.zoneCount = 4;
                this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s4m);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s4m_pic_1);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s4m_pic_3);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s4m_pic_4);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s4m_pic_2);
            } else if (c2 == 2) {
                this.spanCount = 4;
                this.zoneCount = 4;
                this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s4);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s4_normal_1);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s4_shadown_1);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s4_shadown_3);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s4_shadown_2);
            } else if (c2 == 3) {
                this.spanCount = 2;
            } else if (c2 == 4) {
                this.spanCount = 3;
                this.zoneCount = 3;
                if (bleParam.getBleType() == 1066) {
                    this.isNewPanel = true;
                    this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s3);
                } else {
                    this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s3c);
                }
                ((ActNewSmartPanelBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s3c_normal_1);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s1c_shadown_1);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s1c_shadown_3);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s1c_shadown_2);
            } else if (c2 == 5) {
                ((ActSmartPanelVM) this.mViewModel).screenCount = 1;
                this.spanCount = 3;
                this.zoneCount = 3;
                this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s6);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivSwitch5.setVisibility(0);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s1c_shadown_2);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s1c_shadown_1);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s1c_shadown_3);
            } else {
                this.spanCount = 1;
                this.zoneCount = 1;
                if (bleParam.getBleType() == 1064) {
                    this.isNewPanel = true;
                    this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s1);
                } else {
                    this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s1c);
                }
                ((ActNewSmartPanelBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s1c_normal_1);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s1c_shadown_1);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s1c_shadown_3);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s1c_shadown_2);
            }
            initRelatedInfoView(device);
            ((ActSmartPanelVM) this.mViewModel).queryPanelState(obj, 4, this.zoneCount);
            MessageManager.getInstance().setPanelSwitchStatusCallBack(new MessageManager.SmartPanelSwitchStatusCallBack() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel$$ExternalSyntheticLambda11
                @Override // com.smart.message.MessageManager.SmartPanelSwitchStatusCallBack
                public final void onDataReceive(ResponseMsg responseMsg) {
                    ActNewSmartPanel.this.lambda$startDeviceObserve$3(device, responseMsg);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDeviceObserve$3(Device device, ResponseMsg responseMsg) {
        ((ActSmartPanelVM) this.mViewModel).refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserSwitchPanelState(responseMsg.getAgreementId(), ((BleParam) device.getParam(BleParam.class)).getUnicastAddress(), this.zoneCount, responseMsg.getResData()));
    }

    private void startGroupObserve() {
        ((ActSmartPanelVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActNewSmartPanel.this.lambda$startGroupObserve$6(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startGroupObserve$6(Object obj) {
        LHomeLog.i(getClass(), "message_send (group)mViewModel.controlObject enter");
        if (obj instanceof Group) {
            final Group group = (Group) obj;
            ((ActNewSmartPanelBinding) this.mViewBinding).ivSwitch5.setVisibility(0);
            int lightColorType = ProductRepository.getLightColorType((Object) group);
            if (lightColorType == 18) {
                this.spanCount = 4;
                this.zoneCount = 4;
                ((ActNewSmartPanelBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s4_shadown_1);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s4_shadown_3);
                this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s4);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s4_normal_1);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s4_shadown_2);
            } else if (lightColorType != 19) {
                switch (lightColorType) {
                    case 9:
                        this.spanCount = 2;
                        this.zoneCount = 2;
                        this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s2);
                        ((ActNewSmartPanelBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s2c_normal_1);
                        ((ActNewSmartPanelBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s1c_shadown_1);
                        ((ActNewSmartPanelBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s1c_shadown_3);
                        ((ActNewSmartPanelBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s1c_shadown_2);
                        break;
                    case 10:
                        this.spanCount = 3;
                        this.zoneCount = 3;
                        this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s3);
                        ((ActNewSmartPanelBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s3c_normal_1);
                        ((ActNewSmartPanelBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s1c_shadown_1);
                        ((ActNewSmartPanelBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s1c_shadown_3);
                        ((ActNewSmartPanelBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s1c_shadown_2);
                        break;
                    case 11:
                        this.spanCount = 2;
                        this.zoneCount = 4;
                        ((ActNewSmartPanelBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s4m_pic_3);
                        ((ActNewSmartPanelBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s4m_pic_4);
                        this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s4m);
                        ((ActNewSmartPanelBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s4m_pic_1);
                        ((ActNewSmartPanelBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s4m_pic_2);
                        break;
                    default:
                        this.spanCount = 1;
                        this.zoneCount = 1;
                        this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s1);
                        ((ActNewSmartPanelBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s1c_normal_1);
                        ((ActNewSmartPanelBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s1c_shadown_1);
                        ((ActNewSmartPanelBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s1c_shadown_3);
                        ((ActNewSmartPanelBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s1c_shadown_2);
                        break;
                }
            } else {
                ((ActSmartPanelVM) this.mViewModel).screenCount = 1;
                this.spanCount = 3;
                this.zoneCount = 3;
                this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s6);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivSwitch5.setVisibility(0);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s1c_shadown_2);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s1c_shadown_1);
                ((ActNewSmartPanelBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s1c_shadown_3);
            }
            if (group.getDeviceIds().isEmpty()) {
                initRelatedInfoView(group);
                return;
            }
            initRelatedInfoView(group);
            ((ActSmartPanelVM) this.mViewModel).queryPanelState(obj, 4, this.zoneCount);
            MessageManager.getInstance().setPanelSwitchStatusCallBack(new MessageManager.SmartPanelSwitchStatusCallBack() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel$$ExternalSyntheticLambda8
                @Override // com.smart.message.MessageManager.SmartPanelSwitchStatusCallBack
                public final void onDataReceive(ResponseMsg responseMsg) {
                    ActNewSmartPanel.this.lambda$startGroupObserve$5(group, responseMsg);
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
                MessageDialog.show(this, getString(R.string.tips), String.format(getString(R.string.group_switch_tip), Integer.valueOf(this.zoneCount))).setCancelable(false).setOkButton(getString(R.string.change_style), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel$$ExternalSyntheticLambda3
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        boolean lambda$initRelatedInfoView$7;
                        lambda$initRelatedInfoView$7 = ActNewSmartPanel.this.lambda$initRelatedInfoView$7(group, baseDialog, view);
                        return lambda$initRelatedInfoView$7;
                    }
                }).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.not_change_style), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel$$ExternalSyntheticLambda4
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        boolean lambda$initRelatedInfoView$8;
                        lambda$initRelatedInfoView$8 = ActNewSmartPanel.this.lambda$initRelatedInfoView$8(group, baseDialog, view);
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
            BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_smart_panel_key, ((ActSmartPanelVM) this.mViewModel).relatedInfoList) { // from class: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel.1
                /* JADX INFO: Access modifiers changed from: protected */
                /* JADX WARN: Removed duplicated region for block: B:14:0x00f4  */
                /* JADX WARN: Removed duplicated region for block: B:43:0x01e2  */
                /* JADX WARN: Removed duplicated region for block: B:53:0x00c3  */
                /* JADX WARN: Removed duplicated region for block: B:59:0x00e0  */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public void convert(com.chad.library.adapter.base.BaseViewHolder r10, com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem r11) {
                    /*
                        Method dump skipped, instructions count: 612
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel.AnonymousClass1.convert(com.chad.library.adapter.base.BaseViewHolder, com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem):void");
                }
            };
            this.infoAdapter = baseQuickAdapter2;
            baseQuickAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel$$ExternalSyntheticLambda5
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    ActNewSmartPanel.this.lambda$initRelatedInfoView$9(baseQuickAdapter3, view, i);
                }
            });
            this.infoAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel$$ExternalSyntheticLambda6
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemLongClickListener
                public final boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    boolean lambda$initRelatedInfoView$11;
                    lambda$initRelatedInfoView$11 = ActNewSmartPanel.this.lambda$initRelatedInfoView$11(baseQuickAdapter3, view, i);
                    return lambda$initRelatedInfoView$11;
                }
            });
            this.infoAdapter.bindToRecyclerView(((ActNewSmartPanelBinding) this.mViewBinding).rvKeyInfo);
            int i = this.spanCount;
            if (i == 2 || i == 3 || i == 4) {
                RecyclerView recyclerView = ((ActNewSmartPanelBinding) this.mViewBinding).rvKeyInfo;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, this.spanCount, 1, 19 == ProductRepository.getLightColorType((Object) group));
                this.mGridLayoutManager = gridLayoutManager;
                recyclerView.setLayoutManager(gridLayoutManager);
            } else {
                RecyclerView recyclerView2 = ((ActNewSmartPanelBinding) this.mViewBinding).rvKeyInfo;
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                this.mLinearLayoutManager = linearLayoutManager;
                recyclerView2.setLayoutManager(linearLayoutManager);
                ((ActNewSmartPanelBinding) this.mViewBinding).rvKeyInfo.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel.3
                    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);
                        outRect.set(0, 0, 0, 0);
                    }
                });
            }
            ((ActNewSmartPanelBinding) this.mViewBinding).rvKeyInfo.setHasFixedSize(true);
            ((DefaultItemAnimator) ((ActNewSmartPanelBinding) this.mViewBinding).rvKeyInfo.getItemAnimator()).setSupportsChangeAnimations(false);
        } else {
            baseQuickAdapter.replaceData(((ActSmartPanelVM) this.mViewModel).relatedInfoList);
        }
        initTab();
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
        RelateInfoItem relateInfoItem = this.infoAdapter.getData().get(i);
        RelatedInfoExtParam.RelateInfo relateInfo = relateInfoItem.relateInfo;
        if (relateInfo != null) {
            if (relateInfoItem.infoName == null || relateInfoItem.type == 0) {
                if (((ActSmartPanelVM) this.mViewModel).relayObjectMap != null && ((ActSmartPanelVM) this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(relateInfo.switchIndex))) {
                    this.selectArray[i] = !r4[i];
                    baseQuickAdapter.notifyItemChanged(i);
                    ((ActSmartPanelVM) this.mViewModel).sendSingleOnOff(relateInfo.switchIndex - 1, this.selectArray[i]);
                    return;
                }
                ((ActSmartPanelVM) this.mViewModel).showBindDialog(i);
                return;
            }
            ((ActSmartPanelVM) this.mViewModel).sendBindCommand(i, this.selectArray[i]);
            return;
        }
        if (((ActSmartPanelVM) this.mViewModel).relayObjectMap != null && ((ActSmartPanelVM) this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(i + 1))) {
            this.selectArray[i] = !r4[i];
            baseQuickAdapter.notifyItemChanged(i);
            ((ActSmartPanelVM) this.mViewModel).sendSingleOnOff(i, this.selectArray[i]);
            return;
        }
        ((ActSmartPanelVM) this.mViewModel).showBindDialog(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initRelatedInfoView$11(BaseQuickAdapter baseQuickAdapter, View view, final int i) {
        if (Injection.repo().home().getSelectPlace().getValue().isMember()) {
            showNoPermissionDialog();
            return false;
        }
        RelateInfoItem relateInfoItem = this.infoAdapter.getData().get(i);
        RelatedInfoExtParam.RelateInfo relateInfo = relateInfoItem.relateInfo;
        if (relateInfoItem.infoName == null || relateInfoItem.type == 0) {
            int i2 = relateInfo == null ? i + 1 : relateInfo.switchIndex;
            if (((ActSmartPanelVM) this.mViewModel).relayObjectMap != null && ((ActSmartPanelVM) this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(i2))) {
                MessageDialog.show(this, getString(R.string.app_str_relay_bind_tip), String.format(getString(R.string.app_str_relay_bind_content), Integer.valueOf(i2))).setCancelable(false).setOkButton(getString(R.string.app_str_continue_to_bind), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel.2
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        ((ActSmartPanelVM) ActNewSmartPanel.this.mViewModel).showBindDialog(i);
                        return false;
                    }
                }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel$$ExternalSyntheticLambda13
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view2) {
                        return ActNewSmartPanel.lambda$initRelatedInfoView$10(baseDialog, view2);
                    }
                });
            }
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
            BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_smart_panel_key, ((ActSmartPanelVM) this.mViewModel).relatedInfoList) { // from class: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel.4
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, RelateInfoItem item) {
                    String[] stringArray;
                    LinearLayout linearLayout;
                    ViewGroup.LayoutParams layoutParams;
                    stringArray = ActNewSmartPanel.this.getResources().getStringArray(R.array.smart_panel_g4_key_select);
                    linearLayout = (LinearLayout) helper.getView(R.id.layout_content);
                    layoutParams = helper.itemView.getLayoutParams();
                    String productId = device.getProductId();
                    productId.hashCode();
                    switch (productId) {
                        case "4249823578721536":
                            layoutParams.height = ActNewSmartPanel.this.mGridLayoutManager.getHeight() / 2;
                            linearLayout.setGravity(17);
                            stringArray = ActNewSmartPanel.this.getResources().getStringArray(R.array.smart_panel_s6_pro_key_select);
                            break;
                        case "123072510445601":
                        case "121042516340801":
                        case "121042516345401":
                            linearLayout.setGravity(81);
                            linearLayout.setPadding(0, 0, 0, Utils.dip2px(this.mContext, 30.0f));
                            layoutParams.height = ActNewSmartPanel.this.mGridLayoutManager.getHeight();
                            break;
                        case "221030816330401":
                            linearLayout.setGravity(17);
                            layoutParams.height = ActNewSmartPanel.this.mGridLayoutManager.getHeight() / 2;
                            linearLayout.setPadding(0, 0, 0, 0);
                            break;
                        default:
                            linearLayout.setGravity(81);
                            layoutParams.height = ActNewSmartPanel.this.mLinearLayoutManager.getHeight();
                            linearLayout.setPadding(0, 0, 0, Utils.dip2px(this.mContext, 30.0f));
                            break;
                    }
                    helper.itemView.setBackgroundResource(ActNewSmartPanel.this.backgroundResArray[helper.getBindingAdapterPosition()]);
                    RelatedInfoExtParam.RelateInfo relateInfo = item.relateInfo;
                    if (item.infoName != null && !TextUtils.isEmpty(item.infoName)) {
                        helper.setText(R.id.tv_device_name, item.infoName);
                    } else if (relateInfo != null) {
                        if (relateInfo.name == null || relateInfo.name.isEmpty()) {
                            helper.setText(R.id.tv_device_name, stringArray[helper.getAdapterPosition()]);
                        } else {
                            helper.setText(R.id.tv_device_name, relateInfo.name);
                        }
                    } else {
                        helper.setText(R.id.tv_device_name, stringArray[helper.getAdapterPosition()]);
                    }
                    int i = R.color.colorPrimary;
                    if (relateInfo == null) {
                        if (((ActSmartPanelVM) ActNewSmartPanel.this.mViewModel).relayObjectMap != null && ((ActSmartPanelVM) ActNewSmartPanel.this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(helper.getAdapterPosition()))) {
                            Object obj = ((ActSmartPanelVM) ActNewSmartPanel.this.mViewModel).relayObjectMap.get(Integer.valueOf(helper.getAdapterPosition()));
                            if (obj instanceof Device) {
                                helper.setText(R.id.tv_sub_text, ((Device) obj).getName());
                            } else {
                                helper.setText(R.id.tv_sub_text, ActNewSmartPanel.this.getString(R.string.no_bind));
                            }
                        } else {
                            helper.setText(R.id.tv_sub_text, ActNewSmartPanel.this.getString(R.string.no_bind));
                            helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(ActNewSmartPanel.this.activity, R.color.colorPrimary));
                        }
                    } else {
                        if (item.infoName == null || item.type == 0) {
                            if (((ActSmartPanelVM) ActNewSmartPanel.this.mViewModel).relayObjectMap != null && ((ActSmartPanelVM) ActNewSmartPanel.this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(relateInfo.switchIndex))) {
                                Object obj2 = ((ActSmartPanelVM) ActNewSmartPanel.this.mViewModel).relayObjectMap.get(Integer.valueOf(relateInfo.switchIndex));
                                if (obj2 instanceof Device) {
                                    helper.setText(R.id.tv_sub_text, ((Device) obj2).getName());
                                } else {
                                    helper.setText(R.id.tv_sub_text, ActNewSmartPanel.this.getString(R.string.no_bind));
                                }
                            } else {
                                helper.setText(R.id.tv_sub_text, ActNewSmartPanel.this.getString(R.string.no_bind));
                            }
                        } else {
                            helper.setText(R.id.tv_sub_text, item.actionInfo);
                        }
                        helper.setTextColor(R.id.tv_sub_text, ContextCompat.getColor(ActNewSmartPanel.this.activity, R.color.color_border_gray));
                        if (((ActSmartPanelVM) ActNewSmartPanel.this.mViewModel).relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()).objectId != 0) {
                            helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(ActNewSmartPanel.this.activity, R.color.colorPrimary));
                        } else if (((ActSmartPanelVM) ActNewSmartPanel.this.mViewModel).relayObjectMap == null || ((ActSmartPanelVM) ActNewSmartPanel.this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(relateInfo.switchIndex))) {
                            BaseNormalActivity baseNormalActivity = ActNewSmartPanel.this.activity;
                            if (!ActNewSmartPanel.this.selectArray[helper.getAdapterPosition()]) {
                                i = R.color.color_text_screen_panel_unselect;
                            }
                            helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(baseNormalActivity, i));
                        } else {
                            helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(ActNewSmartPanel.this.activity, R.color.colorPrimary));
                        }
                    }
                    ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
                }
            };
            this.infoAdapter = baseQuickAdapter2;
            baseQuickAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel$$ExternalSyntheticLambda9
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    ActNewSmartPanel.this.lambda$initRelatedInfoView$12(baseQuickAdapter3, view, i);
                }
            });
            this.infoAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel$$ExternalSyntheticLambda10
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemLongClickListener
                public final boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    boolean lambda$initRelatedInfoView$14;
                    lambda$initRelatedInfoView$14 = ActNewSmartPanel.this.lambda$initRelatedInfoView$14(baseQuickAdapter3, view, i);
                    return lambda$initRelatedInfoView$14;
                }
            });
            this.infoAdapter.bindToRecyclerView(((ActNewSmartPanelBinding) this.mViewBinding).rvKeyInfo);
            int i = this.spanCount;
            if (i == 2 || i == 3 || i == 4) {
                RecyclerView recyclerView = ((ActNewSmartPanelBinding) this.mViewBinding).rvKeyInfo;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, this.spanCount, 1, ProductId.ID_SMART_SWITCH_S6.equalsIgnoreCase(device.getProductId()));
                this.mGridLayoutManager = gridLayoutManager;
                recyclerView.setLayoutManager(gridLayoutManager);
            } else {
                RecyclerView recyclerView2 = ((ActNewSmartPanelBinding) this.mViewBinding).rvKeyInfo;
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                this.mLinearLayoutManager = linearLayoutManager;
                recyclerView2.setLayoutManager(linearLayoutManager);
                ((ActNewSmartPanelBinding) this.mViewBinding).rvKeyInfo.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel.6
                    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);
                        outRect.set(0, 0, 0, 0);
                    }
                });
            }
            ((ActNewSmartPanelBinding) this.mViewBinding).rvKeyInfo.setHasFixedSize(true);
            ((DefaultItemAnimator) ((ActNewSmartPanelBinding) this.mViewBinding).rvKeyInfo.getItemAnimator()).setSupportsChangeAnimations(false);
        } else {
            baseQuickAdapter.replaceData(((ActSmartPanelVM) this.mViewModel).relatedInfoList);
        }
        initTab();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRelatedInfoView$12(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        RelateInfoItem relateInfoItem = this.infoAdapter.getData().get(i);
        RelatedInfoExtParam.RelateInfo relateInfo = relateInfoItem.relateInfo;
        if (relateInfo != null) {
            if (relateInfoItem.infoName == null || relateInfoItem.type == 0) {
                if (((ActSmartPanelVM) this.mViewModel).relayObjectMap != null && ((ActSmartPanelVM) this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(relateInfo.switchIndex))) {
                    this.selectArray[i] = !r4[i];
                    baseQuickAdapter.notifyItemChanged(i);
                    ((ActSmartPanelVM) this.mViewModel).sendSingleOnOff(relateInfo.switchIndex - 1, this.selectArray[i]);
                    return;
                }
                ((ActSmartPanelVM) this.mViewModel).showBindDialog(i);
                return;
            }
            ((ActSmartPanelVM) this.mViewModel).sendBindCommand(i, this.selectArray[i]);
            return;
        }
        if (((ActSmartPanelVM) this.mViewModel).relayObjectMap != null && ((ActSmartPanelVM) this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(i + 1))) {
            this.selectArray[i] = !r4[i];
            baseQuickAdapter.notifyItemChanged(i);
            ((ActSmartPanelVM) this.mViewModel).sendSingleOnOff(i, this.selectArray[i]);
            return;
        }
        ((ActSmartPanelVM) this.mViewModel).showBindDialog(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initRelatedInfoView$14(BaseQuickAdapter baseQuickAdapter, View view, final int i) {
        if (Injection.repo().home().getSelectPlace().getValue().isMember()) {
            showNoPermissionDialog();
            return false;
        }
        RelateInfoItem relateInfoItem = this.infoAdapter.getData().get(i);
        RelatedInfoExtParam.RelateInfo relateInfo = relateInfoItem.relateInfo;
        if (relateInfoItem.infoName == null || relateInfoItem.type == 0) {
            int i2 = relateInfo == null ? i + 1 : relateInfo.switchIndex;
            if (((ActSmartPanelVM) this.mViewModel).relayObjectMap != null && ((ActSmartPanelVM) this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(i2))) {
                MessageDialog.show(this, getString(R.string.app_str_relay_bind_tip), String.format(getString(R.string.app_str_relay_bind_content), Integer.valueOf(i2))).setCancelable(false).setOkButton(getString(R.string.app_str_continue_to_bind), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel.5
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        ((ActSmartPanelVM) ActNewSmartPanel.this.mViewModel).showBindDialog(i);
                        return false;
                    }
                }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel$$ExternalSyntheticLambda2
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view2) {
                        return ActNewSmartPanel.lambda$initRelatedInfoView$13(baseDialog, view2);
                    }
                });
            }
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
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel$$ExternalSyntheticLambda14
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActNewSmartPanel.this.lambda$showUnbindDialog$15(position, (Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnbindDialog$15(int i, Integer num) {
        ((ActSmartPanelVM) this.mViewModel).unBindRelateInfo(i);
    }

    private void initTab() {
        if (((ActSmartPanelVM) this.mViewModel).tabs.isEmpty()) {
            ((ActSmartPanelVM) this.mViewModel).initTabList();
            ((ActNewSmartPanelBinding) this.mViewBinding).vp.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel.7
                @Override // androidx.viewpager2.adapter.FragmentStateAdapter
                public Fragment createFragment(int position) {
                    return ((ActSmartPanelVM) ActNewSmartPanel.this.mViewModel).tabs.get(position).getFragment();
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public int getItemCount() {
                    return ((ActSmartPanelVM) ActNewSmartPanel.this.mViewModel).tabs.size();
                }
            });
            ((ActNewSmartPanelBinding) this.mViewBinding).tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel.8
                @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
                public void onTabSelected(TabLayout.Tab tab) {
                    ActNewSmartPanel actNewSmartPanel = ActNewSmartPanel.this;
                    actNewSmartPanel.createTabCustomView(actNewSmartPanel.activity, tab, ((ActSmartPanelVM) ActNewSmartPanel.this.mViewModel).tabs.get(tab.getPosition()).getTitle(), true);
                }

                @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
                public void onTabUnselected(TabLayout.Tab tab) {
                    ActNewSmartPanel actNewSmartPanel = ActNewSmartPanel.this;
                    actNewSmartPanel.createTabCustomView(actNewSmartPanel.activity, tab, ((ActSmartPanelVM) ActNewSmartPanel.this.mViewModel).tabs.get(tab.getPosition()).getTitle(), false);
                }

                @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
                public void onTabReselected(TabLayout.Tab tab) {
                    ActNewSmartPanel actNewSmartPanel = ActNewSmartPanel.this;
                    actNewSmartPanel.createTabCustomView(actNewSmartPanel.activity, tab, ((ActSmartPanelVM) ActNewSmartPanel.this.mViewModel).tabs.get(tab.getPosition()).getTitle(), true);
                }
            });
            new TabLayoutMediator(((ActNewSmartPanelBinding) this.mViewBinding).tabLayout, ((ActNewSmartPanelBinding) this.mViewBinding).vp, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel$$ExternalSyntheticLambda7
                @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
                public final void onConfigureTab(TabLayout.Tab tab, int i) {
                    ActNewSmartPanel.this.lambda$initTab$16(tab, i);
                }
            }).attach();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTab$16(TabLayout.Tab tab, int i) {
        createTabCustomView(this.activity, tab, ((ActSmartPanelVM) this.mViewModel).tabs.get(i).getTitle(), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createTabCustomView(Context context, TabLayout.Tab tab, String text, boolean select) {
        View customView = tab.getCustomView();
        if (customView == null) {
            customView = LayoutInflater.from(context).inflate(R.layout.tab_text, (ViewGroup) null);
            tab.setCustomView(customView);
        }
        AppCompatTextView appCompatTextView = (AppCompatTextView) customView.findViewById(R.id.tv_tab);
        appCompatTextView.setText(text);
        if (select) {
            appCompatTextView.setTextColor(ContextCompat.getColor(context, R.color.color_text_black));
            appCompatTextView.getPaint().setFakeBoldText(true);
            if (LanguageUtils.isRussian(context)) {
                appCompatTextView.setTextSize(15.0f);
                return;
            } else {
                appCompatTextView.setTextSize(16.0f);
                return;
            }
        }
        appCompatTextView.setTextColor(ContextCompat.getColor(context, R.color.color_text_gray));
        appCompatTextView.getPaint().setFakeBoldText(false);
        if (LanguageUtils.isRussian(context)) {
            appCompatTextView.setTextSize(15.0f);
        } else {
            appCompatTextView.setTextSize(16.0f);
        }
    }
}