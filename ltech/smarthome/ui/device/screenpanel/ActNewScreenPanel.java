package com.ltech.smarthome.ui.device.screenpanel;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActNewScreenPanelBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.ScreenIconUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.qw.curtain.lib.Curtain;
import com.qw.curtain.lib.IGuide;
import com.qw.curtain.lib.OnViewInTopClickListener;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.bean.SwitchPanelState;
import com.smart.product_agreement.parser.IPanelParser;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActNewScreenPanel extends BaseControlActivity<ActNewScreenPanelBinding, ActSmartPanelVM> {
    private int[] backgroundResArray;
    protected List<RelateInfoItem> dataList;
    protected BaseQuickAdapter<RelateInfoItem, BaseViewHolder> infoAdapter;
    private GridLayoutManager mGridLayoutManager;
    private GridLayoutManager mScreenGridLayoutManager;
    protected BaseQuickAdapter<RelateInfoItem, BaseViewHolder> screenAdapter;
    protected boolean[] selectArray = new boolean[8];
    private int spanCount;
    private int zoneCount;

    static /* synthetic */ boolean lambda$initRelatedInfoView$10(BaseDialog baseDialog, View view) {
        return false;
    }

    static /* synthetic */ boolean lambda$initRelatedInfoView$7(BaseDialog baseDialog, View view) {
        return false;
    }

    static /* synthetic */ void lambda$initScreenAdapter$12(BaseQuickAdapter baseQuickAdapter, View view, int i) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.layout_load;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_new_screen_panel;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        super.showContent();
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
        ((ActSmartPanelVM) this.mViewModel).panelZoneStateLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel$$ExternalSyntheticLambda9
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActNewScreenPanel.this.lambda$startObserve$0((List) obj);
            }
        });
        ((ActSmartPanelVM) this.mViewModel).updateUIEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel$$ExternalSyntheticLambda10
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActNewScreenPanel.this.lambda$startObserve$1(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(List list) {
        int i = 0;
        while (i < list.size()) {
            int i2 = i + 1;
            if (((ActSmartPanelVM) this.mViewModel).relayPositiontMap != null && ((ActSmartPanelVM) this.mViewModel).relayPositiontMap.containsKey(Integer.valueOf(i2))) {
                Integer num = ((ActSmartPanelVM) this.mViewModel).relayPositiontMap.get(Integer.valueOf(i2));
                int byteValue = num == null ? i : num.byteValue();
                this.selectArray[byteValue] = ((SwitchPanelState.SwitchPanelZoneState) list.get(i)).isSwitchOnOff();
                BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = this.infoAdapter;
                if (baseQuickAdapter != null) {
                    baseQuickAdapter.notifyItemChanged(byteValue);
                }
            }
            i = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Object obj) {
        ((ActSmartPanelVM) this.mViewModel).controlObject.setValue(obj);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        startObjectObserve();
    }

    private void startObjectObserve() {
        if (((ActSmartPanelVM) this.mViewModel).groupControl) {
            Group groupById = Injection.repo().group().getGroupById(((ActSmartPanelVM) this.mViewModel).controlId);
            if (!RelaySeparationHelper.isRelaySeparationPanelDevice(groupById)) {
                NavUtils.destination(ActScreenPanel.class).withLong(Constants.PLACE_ID, ((ActSmartPanelVM) this.mViewModel).placeId).withLong(Constants.CONTROL_ID, ((ActSmartPanelVM) this.mViewModel).controlId).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) groupById)).withBoolean(Constants.GROUP_CONTROL, true).navigation(this);
                finishActivity();
                return;
            } else {
                ((ActSmartPanelVM) this.mViewModel).controlObject.setValue(groupById);
                return;
            }
        }
        Device deviceById = Injection.repo().device().getDeviceById(((ActSmartPanelVM) this.mViewModel).controlId);
        LHomeLog.i(getClass(), "message_send (device)getDeviceFromDb enter");
        try {
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
        ((ActSmartPanelVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel$$ExternalSyntheticLambda12
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActNewScreenPanel.this.lambda$startDeviceObserve$3(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x007c, code lost:
    
        if (r1.equals(com.ltech.smarthome.model.product.ProductId.ID_SMART_SWITCH_S2_PRO) == false) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$startDeviceObserve$3(java.lang.Object r8) {
        /*
            r7 = this;
            java.lang.Class r0 = r7.getClass()
            java.lang.String r1 = "message_send (device)mViewModel.controlObject enter"
            com.smart.message.utils.LHomeLog.i(r0, r1)
            boolean r0 = r8 instanceof com.ltech.smarthome.model.bean.Device
            if (r0 == 0) goto Le2
            r0 = r8
            com.ltech.smarthome.model.bean.Device r0 = (com.ltech.smarthome.model.bean.Device) r0
            java.lang.Class<com.ltech.smarthome.model.device_param.BleParam> r1 = com.ltech.smarthome.model.device_param.BleParam.class
            java.lang.Object r1 = r0.getParam(r1)
            com.ltech.smarthome.model.device_param.BleParam r1 = (com.ltech.smarthome.model.device_param.BleParam) r1
            java.lang.String r1 = r0.getDeviceName()
            r7.setTitle(r1)
            V extends androidx.databinding.ViewDataBinding r1 = r7.mViewBinding
            com.ltech.smarthome.databinding.ActNewScreenPanelBinding r1 = (com.ltech.smarthome.databinding.ActNewScreenPanelBinding) r1
            androidx.appcompat.widget.AppCompatImageView r1 = r1.ivSwitch5
            r2 = 0
            r1.setVisibility(r2)
            V extends androidx.databinding.ViewDataBinding r1 = r7.mViewBinding
            com.ltech.smarthome.databinding.ActNewScreenPanelBinding r1 = (com.ltech.smarthome.databinding.ActNewScreenPanelBinding) r1
            androidx.appcompat.widget.AppCompatImageView r1 = r1.ivSwitch5
            r3 = 2131624072(0x7f0e0088, float:1.8875313E38)
            r1.setImageResource(r3)
            V extends androidx.databinding.ViewDataBinding r1 = r7.mViewBinding
            com.ltech.smarthome.databinding.ActNewScreenPanelBinding r1 = (com.ltech.smarthome.databinding.ActNewScreenPanelBinding) r1
            androidx.appcompat.widget.AppCompatImageView r1 = r1.ivShadowLeft
            r3 = 2131624071(0x7f0e0087, float:1.8875311E38)
            r1.setImageResource(r3)
            V extends androidx.databinding.ViewDataBinding r1 = r7.mViewBinding
            com.ltech.smarthome.databinding.ActNewScreenPanelBinding r1 = (com.ltech.smarthome.databinding.ActNewScreenPanelBinding) r1
            androidx.appcompat.widget.AppCompatImageView r1 = r1.ivShadowRight
            r3 = 2131624073(0x7f0e0089, float:1.8875315E38)
            r1.setImageResource(r3)
            java.lang.String r1 = r0.getProductId()
            r1.hashCode()
            int r3 = r1.hashCode()
            r4 = 2
            r5 = 1
            r6 = -1
            switch(r3) {
                case -1817691924: goto L76;
                case -1796419228: goto L6b;
                case -1084555505: goto L60;
                default: goto L5e;
            }
        L5e:
            r2 = -1
            goto L7f
        L60:
            java.lang.String r2 = "3683369128495808"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L69
            goto L5e
        L69:
            r2 = 2
            goto L7f
        L6b:
            java.lang.String r2 = "122041818304701"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L74
            goto L5e
        L74:
            r2 = 1
            goto L7f
        L76:
            java.lang.String r3 = "122041818283501"
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L7f
            goto L5e
        L7f:
            r1 = 3
            switch(r2) {
                case 0: goto Lb9;
                case 1: goto Lab;
                case 2: goto L97;
                default: goto L83;
            }
        L83:
            VM extends com.ltech.smarthome.base.BaseViewModel r1 = r7.mViewModel
            com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM r1 = (com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM) r1
            r1.screenCount = r5
            r7.spanCount = r5
            r7.zoneCount = r5
            r1 = 2130903193(0x7f030099, float:1.7413197E38)
            int[] r1 = com.ltech.smarthome.utils.HelpUtils.getDrawableResourceArray(r7, r1)
            r7.backgroundResArray = r1
            goto Lc6
        L97:
            VM extends com.ltech.smarthome.base.BaseViewModel r2 = r7.mViewModel
            com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM r2 = (com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM) r2
            r2.screenCount = r5
            r7.spanCount = r1
            r7.zoneCount = r1
            r1 = 2130903204(0x7f0300a4, float:1.741322E38)
            int[] r1 = com.ltech.smarthome.utils.HelpUtils.getDrawableResourceArray(r7, r1)
            r7.backgroundResArray = r1
            goto Lc6
        Lab:
            r7.spanCount = r1
            r7.zoneCount = r1
            r1 = 2130903199(0x7f03009f, float:1.741321E38)
            int[] r1 = com.ltech.smarthome.utils.HelpUtils.getDrawableResourceArray(r7, r1)
            r7.backgroundResArray = r1
            goto Lc6
        Lb9:
            r7.spanCount = r4
            r7.zoneCount = r4
            r1 = 2130903196(0x7f03009c, float:1.7413203E38)
            int[] r1 = com.ltech.smarthome.utils.HelpUtils.getDrawableResourceArray(r7, r1)
            r7.backgroundResArray = r1
        Lc6:
            r7.showInitGuide()
            r7.initRelatedInfoView(r0)
            VM extends com.ltech.smarthome.base.BaseViewModel r1 = r7.mViewModel
            com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM r1 = (com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM) r1
            r2 = 4
            int r3 = r7.zoneCount
            r1.queryPanelState(r8, r2, r3)
            com.smart.message.MessageManager r8 = com.smart.message.MessageManager.getInstance()
            com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel$$ExternalSyntheticLambda5 r1 = new com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel$$ExternalSyntheticLambda5
            r1.<init>()
            r8.setPanelSwitchStatusCallBack(r1)
        Le2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel.lambda$startDeviceObserve$3(java.lang.Object):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDeviceObserve$2(Device device, ResponseMsg responseMsg) {
        ((ActSmartPanelVM) this.mViewModel).refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserSwitchPanelState(responseMsg.getAgreementId(), ((BleParam) device.getParam(BleParam.class)).getUnicastAddress(), this.zoneCount, responseMsg.getResData()));
    }

    private void startGroupObserve() {
        ((ActSmartPanelVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActNewScreenPanel.this.lambda$startGroupObserve$5(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startGroupObserve$5(Object obj) {
        LHomeLog.i(getClass(), "message_send (group)mViewModel.controlObject enter");
        if (obj instanceof Group) {
            final Group group = (Group) obj;
            setTitle(group.getGroupName());
            ((ActNewScreenPanelBinding) this.mViewBinding).ivSwitch5.setVisibility(0);
            ((ActNewScreenPanelBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s1c_shadown_2);
            ((ActNewScreenPanelBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s1c_shadown_1);
            ((ActNewScreenPanelBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s1c_shadown_3);
            int lightColorType = ProductRepository.getLightColorType((Object) group);
            if (lightColorType == 9) {
                this.spanCount = 2;
                this.zoneCount = 2;
                this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s2pro);
            } else if (lightColorType == 10) {
                this.spanCount = 3;
                this.zoneCount = 3;
                this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s3pro);
            } else if (lightColorType == 19) {
                ((ActSmartPanelVM) this.mViewModel).screenCount = 1;
                this.spanCount = 3;
                this.zoneCount = 3;
                this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s6pro);
            } else {
                ((ActSmartPanelVM) this.mViewModel).screenCount = 1;
                this.spanCount = 1;
                this.zoneCount = 1;
                this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s1pro);
            }
            showInitGuide();
            if (group.getDeviceIds().isEmpty()) {
                initRelatedInfoView(group);
                return;
            }
            initRelatedInfoView(group);
            ((ActSmartPanelVM) this.mViewModel).queryPanelState(obj, 4, this.zoneCount);
            MessageManager.getInstance().setPanelSwitchStatusCallBack(new MessageManager.SmartPanelSwitchStatusCallBack() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel$$ExternalSyntheticLambda13
                @Override // com.smart.message.MessageManager.SmartPanelSwitchStatusCallBack
                public final void onDataReceive(ResponseMsg responseMsg) {
                    ActNewScreenPanel.this.lambda$startGroupObserve$4(group, responseMsg);
                }
            });
            ((ActSmartPanelVM) this.mViewModel).loadSettingStatus(group);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startGroupObserve$4(Group group, ResponseMsg responseMsg) {
        ((ActSmartPanelVM) this.mViewModel).refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserSwitchPanelState(responseMsg.getAgreementId(), group.getGroupAddress(), this.zoneCount, responseMsg.getResData()));
    }

    private void initRelatedInfoView(final Group group) {
        ((ActSmartPanelVM) this.mViewModel).initPanelState(group);
        ((ActSmartPanelVM) this.mViewModel).initRelateInfoList(group);
        int proPanelCount = ((ActSmartPanelVM) this.mViewModel).getProPanelCount(group);
        if (ProductRepository.getLightColorType((Object) group) != 19 && proPanelCount == 0 && ((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getSwitchShowType() == 2) {
            ((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.setSwitchShowType(1);
            group.setExtParam(((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getExtParamString());
            Injection.repo().group().saveGroup(group);
            ((ActSmartPanelVM) this.mViewModel).changeShowType(group, 1);
        }
        if (((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getSwitchShowType() == 1) {
            RelateInfoUtils.relateInfoAssistant.setSwitchShowType(1);
            NavUtils.destination(ActSmartPanel.class).withLong(Constants.PLACE_ID, ((ActSmartPanelVM) this.mViewModel).placeId).withLong(Constants.CONTROL_ID, ((ActSmartPanelVM) this.mViewModel).controlId).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) group)).withBoolean(Constants.GROUP_CONTROL, true).navigation(this);
            finishActivity();
        } else {
            ((ActSmartPanelVM) this.mViewModel).changeShowType(group, 2);
        }
        this.selectArray = new boolean[((ActSmartPanelVM) this.mViewModel).relatedInfoList.size()];
        BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = this.infoAdapter;
        if (baseQuickAdapter == null) {
            BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_smart_panel_key, ((ActSmartPanelVM) this.mViewModel).relatedInfoList) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, RelateInfoItem item) {
                    String[] stringArray;
                    LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.layout_content);
                    ViewGroup.LayoutParams layoutParams = helper.itemView.getLayoutParams();
                    int lightColorType = ProductRepository.getLightColorType((Object) group);
                    if (lightColorType == 11) {
                        layoutParams.height = ActNewScreenPanel.this.mGridLayoutManager.getHeight() / 2;
                        linearLayout.setGravity(17);
                        stringArray = ActNewScreenPanel.this.getResources().getStringArray(R.array.smart_panel_g4_key_select);
                    } else if (lightColorType == 19) {
                        layoutParams.height = ActNewScreenPanel.this.mGridLayoutManager.getHeight() / 2;
                        linearLayout.setGravity(17);
                        stringArray = ActNewScreenPanel.this.getResources().getStringArray(R.array.smart_panel_s6_pro_key_select);
                    } else {
                        layoutParams.height = ActNewScreenPanel.this.mGridLayoutManager.getHeight();
                        linearLayout.setGravity(81);
                        linearLayout.setPadding(0, 0, 0, Utils.dip2px(this.mContext, 30.0f));
                        stringArray = ActNewScreenPanel.this.getResources().getStringArray(R.array.smart_panel_g4_key_select);
                    }
                    helper.itemView.setBackgroundResource(ActNewScreenPanel.this.backgroundResArray[helper.getBindingAdapterPosition()]);
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
                        if (((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).relayObjectMap != null && ((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(helper.getAdapterPosition()))) {
                            Object obj = ((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).relayObjectMap.get(Integer.valueOf(helper.getAdapterPosition()));
                            if (obj instanceof Device) {
                                helper.setText(R.id.tv_sub_text, ((Device) obj).getName());
                            } else {
                                helper.setText(R.id.tv_sub_text, ActNewScreenPanel.this.getString(R.string.no_bind));
                            }
                        } else {
                            helper.setText(R.id.tv_sub_text, ActNewScreenPanel.this.getString(R.string.no_bind));
                            helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(ActNewScreenPanel.this.activity, R.color.colorPrimary));
                        }
                    } else {
                        if (item.infoName == null || item.type == 0) {
                            if (((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).relayObjectMap != null && ((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(relateInfo.switchIndex))) {
                                Object obj2 = ((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).relayObjectMap.get(Integer.valueOf(relateInfo.switchIndex));
                                if (obj2 instanceof Group) {
                                    helper.setText(R.id.tv_sub_text, ((Group) obj2).getName());
                                } else {
                                    helper.setText(R.id.tv_sub_text, ActNewScreenPanel.this.getString(R.string.no_bind));
                                }
                            } else {
                                helper.setText(R.id.tv_sub_text, ActNewScreenPanel.this.getString(R.string.no_bind));
                            }
                        } else {
                            helper.setText(R.id.tv_sub_text, item.actionInfo);
                        }
                        helper.setTextColor(R.id.tv_sub_text, ContextCompat.getColor(ActNewScreenPanel.this, R.color.color_border_gray));
                        if (((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()).objectId == 0) {
                            if (((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).relayObjectMap != null && !((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(relateInfo.switchIndex))) {
                                helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(ActNewScreenPanel.this.activity, R.color.colorPrimary));
                            } else {
                                ActNewScreenPanel actNewScreenPanel = ActNewScreenPanel.this;
                                if (!actNewScreenPanel.selectArray[helper.getAdapterPosition()]) {
                                    i = R.color.color_text_screen_panel_unselect;
                                }
                                helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(actNewScreenPanel, i));
                            }
                        } else {
                            helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(ActNewScreenPanel.this, R.color.colorPrimary));
                        }
                    }
                    ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
                }
            };
            this.infoAdapter = baseQuickAdapter2;
            baseQuickAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel$$ExternalSyntheticLambda6
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    ActNewScreenPanel.this.lambda$initRelatedInfoView$6(baseQuickAdapter3, view, i);
                }
            });
            this.infoAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel$$ExternalSyntheticLambda7
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemLongClickListener
                public final boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    boolean lambda$initRelatedInfoView$8;
                    lambda$initRelatedInfoView$8 = ActNewScreenPanel.this.lambda$initRelatedInfoView$8(baseQuickAdapter3, view, i);
                    return lambda$initRelatedInfoView$8;
                }
            });
            this.infoAdapter.bindToRecyclerView(((ActNewScreenPanelBinding) this.mViewBinding).rvKeyInfo);
            RecyclerView recyclerView = ((ActNewScreenPanelBinding) this.mViewBinding).rvKeyInfo;
            GridLayoutManager gridLayoutManager = new GridLayoutManager((Context) this, this.spanCount, 1, true);
            this.mGridLayoutManager = gridLayoutManager;
            recyclerView.setLayoutManager(gridLayoutManager);
            ((ActNewScreenPanelBinding) this.mViewBinding).rvKeyInfo.setHasFixedSize(true);
            ((DefaultItemAnimator) ((ActNewScreenPanelBinding) this.mViewBinding).rvKeyInfo.getItemAnimator()).setSupportsChangeAnimations(false);
        } else {
            baseQuickAdapter.replaceData(((ActSmartPanelVM) this.mViewModel).relatedInfoList);
        }
        initScreenAdapter(group);
        initTab();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRelatedInfoView$6(BaseQuickAdapter baseQuickAdapter, View view, int i) {
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
    public /* synthetic */ boolean lambda$initRelatedInfoView$8(BaseQuickAdapter baseQuickAdapter, View view, final int i) {
        if (Injection.repo().home().getSelectPlace().getValue().isMember()) {
            showNoPermissionDialog();
            return false;
        }
        RelateInfoItem relateInfoItem = this.infoAdapter.getData().get(i);
        RelatedInfoExtParam.RelateInfo relateInfo = relateInfoItem.relateInfo;
        if (relateInfoItem.infoName == null || relateInfoItem.type == 0) {
            int i2 = relateInfo == null ? i + 1 : relateInfo.switchIndex;
            if (((ActSmartPanelVM) this.mViewModel).relayObjectMap != null && ((ActSmartPanelVM) this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(i2))) {
                MessageDialog.show(this, getString(R.string.app_str_relay_bind_tip), String.format(getString(R.string.app_str_relay_bind_content), Integer.valueOf(i2))).setCancelable(false).setOkButton(getString(R.string.app_str_continue_to_bind), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel.2
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        ((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).showBindDialog(i);
                        return false;
                    }
                }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel$$ExternalSyntheticLambda8
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view2) {
                        return ActNewScreenPanel.lambda$initRelatedInfoView$7(baseDialog, view2);
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
            BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_smart_panel_key, ((ActSmartPanelVM) this.mViewModel).relatedInfoList) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel.3
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, RelateInfoItem item) {
                    String[] stringArray;
                    LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.layout_content);
                    ViewGroup.LayoutParams layoutParams = helper.itemView.getLayoutParams();
                    String productId = device.getProductId();
                    productId.hashCode();
                    if (productId.equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                        layoutParams.height = ActNewScreenPanel.this.mGridLayoutManager.getHeight() / 2;
                        linearLayout.setGravity(17);
                        stringArray = ActNewScreenPanel.this.getResources().getStringArray(R.array.smart_panel_s6_pro_key_select);
                    } else if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                        layoutParams.height = ActNewScreenPanel.this.mGridLayoutManager.getHeight() / 2;
                        linearLayout.setGravity(17);
                        stringArray = ActNewScreenPanel.this.getResources().getStringArray(R.array.smart_panel_g4_key_select);
                    } else {
                        linearLayout.setGravity(81);
                        linearLayout.setPadding(0, 0, 0, Utils.dip2px(this.mContext, 30.0f));
                        layoutParams.height = ActNewScreenPanel.this.mGridLayoutManager.getHeight();
                        stringArray = ActNewScreenPanel.this.getResources().getStringArray(R.array.smart_panel_g4_key_select);
                    }
                    helper.itemView.setBackgroundResource(ActNewScreenPanel.this.backgroundResArray[helper.getBindingAdapterPosition()]);
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
                        if (((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).relayObjectMap != null && ((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(helper.getAdapterPosition()))) {
                            Object obj = ((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).relayObjectMap.get(Integer.valueOf(helper.getAdapterPosition()));
                            if (obj instanceof Device) {
                                helper.setText(R.id.tv_sub_text, ((Device) obj).getName());
                            } else {
                                helper.setText(R.id.tv_sub_text, ActNewScreenPanel.this.getString(R.string.no_bind));
                            }
                        } else {
                            helper.setText(R.id.tv_sub_text, ActNewScreenPanel.this.getString(R.string.no_bind));
                            helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(ActNewScreenPanel.this.activity, R.color.colorPrimary));
                        }
                    } else {
                        if (item.infoName == null || item.type == 0) {
                            if (((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).relayObjectMap != null && ((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(relateInfo.switchIndex))) {
                                Object obj2 = ((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).relayObjectMap.get(Integer.valueOf(relateInfo.switchIndex));
                                if (obj2 instanceof Device) {
                                    helper.setText(R.id.tv_sub_text, ((Device) obj2).getName());
                                } else {
                                    helper.setText(R.id.tv_sub_text, ActNewScreenPanel.this.getString(R.string.no_bind));
                                }
                            } else {
                                helper.setText(R.id.tv_sub_text, ActNewScreenPanel.this.getString(R.string.no_bind));
                            }
                        } else {
                            helper.setText(R.id.tv_sub_text, item.actionInfo);
                        }
                        helper.setTextColor(R.id.tv_sub_text, ContextCompat.getColor(ActNewScreenPanel.this, R.color.color_border_gray));
                        if (((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()).objectId == 0) {
                            if (((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).relayObjectMap != null && !((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(relateInfo.switchIndex))) {
                                helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(ActNewScreenPanel.this.activity, R.color.colorPrimary));
                            } else {
                                ActNewScreenPanel actNewScreenPanel = ActNewScreenPanel.this;
                                if (!actNewScreenPanel.selectArray[helper.getAdapterPosition()]) {
                                    i = R.color.color_text_screen_panel_unselect;
                                }
                                helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(actNewScreenPanel, i));
                            }
                        } else {
                            helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(ActNewScreenPanel.this, R.color.colorPrimary));
                        }
                    }
                    ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
                }
            };
            this.infoAdapter = baseQuickAdapter2;
            baseQuickAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel$$ExternalSyntheticLambda3
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    ActNewScreenPanel.this.lambda$initRelatedInfoView$9(baseQuickAdapter3, view, i);
                }
            });
            this.infoAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel$$ExternalSyntheticLambda4
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemLongClickListener
                public final boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    boolean lambda$initRelatedInfoView$11;
                    lambda$initRelatedInfoView$11 = ActNewScreenPanel.this.lambda$initRelatedInfoView$11(baseQuickAdapter3, view, i);
                    return lambda$initRelatedInfoView$11;
                }
            });
            this.infoAdapter.bindToRecyclerView(((ActNewScreenPanelBinding) this.mViewBinding).rvKeyInfo);
            RecyclerView recyclerView = ((ActNewScreenPanelBinding) this.mViewBinding).rvKeyInfo;
            GridLayoutManager gridLayoutManager = new GridLayoutManager((Context) this, this.spanCount, 1, true);
            this.mGridLayoutManager = gridLayoutManager;
            recyclerView.setLayoutManager(gridLayoutManager);
            ((ActNewScreenPanelBinding) this.mViewBinding).rvKeyInfo.setHasFixedSize(true);
            ((DefaultItemAnimator) ((ActNewScreenPanelBinding) this.mViewBinding).rvKeyInfo.getItemAnimator()).setSupportsChangeAnimations(false);
        } else {
            baseQuickAdapter.replaceData(((ActSmartPanelVM) this.mViewModel).relatedInfoList);
        }
        initScreenAdapter(device);
        initTab();
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
                MessageDialog.show(this, getString(R.string.app_str_relay_bind_tip), String.format(getString(R.string.app_str_relay_bind_content), Integer.valueOf(i2))).setCancelable(false).setOkButton(getString(R.string.app_str_continue_to_bind), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel.4
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        ((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).showBindDialog(i);
                        return false;
                    }
                }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel$$ExternalSyntheticLambda2
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view2) {
                        return ActNewScreenPanel.lambda$initRelatedInfoView$10(baseDialog, view2);
                    }
                });
            }
        } else {
            showUnbindDialog(i);
        }
        return false;
    }

    private void initScreenAdapter(final Object object) {
        BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = this.screenAdapter;
        if (baseQuickAdapter == null) {
            BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_screen_panel_mult_line, ((ActSmartPanelVM) this.mViewModel).relatedInfoList) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel.5
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, RelateInfoItem item) {
                    ViewGroup.LayoutParams layoutParams = helper.itemView.getLayoutParams();
                    LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.layout_item_bg);
                    int lightColorType = ProductRepository.getLightColorType(object);
                    if (lightColorType == 19) {
                        layoutParams.height = ActNewScreenPanel.this.mScreenGridLayoutManager.getHeight() / 2;
                        linearLayout.setGravity(helper.getAdapterPosition() < ActNewScreenPanel.this.spanCount ? 49 : 81);
                    } else {
                        layoutParams.height = ActNewScreenPanel.this.mScreenGridLayoutManager.getHeight();
                        linearLayout.setGravity(16);
                    }
                    helper.setGone(R.id.imaginary_line, helper.getAdapterPosition() >= ActNewScreenPanel.this.spanCount);
                    boolean z = ((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).relateInfoAssistant.getSwitchScreenBigIcon() == 2;
                    ViewGroup.LayoutParams layoutParams2 = ((ImageView) helper.getView(R.id.iv_screen)).getLayoutParams();
                    TextView textView = (TextView) helper.getView(R.id.tv_screen);
                    if (z) {
                        layoutParams2.width = SizeUtils.dp2px(35.0f);
                        layoutParams2.height = SizeUtils.dp2px(35.0f);
                        textView.setTextSize(18.0f);
                    } else {
                        layoutParams2.width = SizeUtils.dp2px(25.0f);
                        layoutParams2.height = SizeUtils.dp2px(25.0f);
                        textView.setTextSize(13.0f);
                    }
                    if (LanguageUtils.isRussian(ActNewScreenPanel.this)) {
                        if (lightColorType != 19 && lightColorType != 21) {
                            textView.setMaxLines(3);
                        } else {
                            textView.setMaxLines(1);
                        }
                    } else if (lightColorType != 19 && lightColorType != 21) {
                        textView.setMaxLines(2);
                    } else {
                        textView.setMaxLines(1);
                    }
                    RelatedInfoExtParam.RelateInfo relateInfo = item.relateInfo;
                    boolean z2 = (item.infoName == null || item.type == 0) ? false : true;
                    if (relateInfo != null) {
                        if (relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeNone.getValue() && !z2) {
                            helper.setGone(R.id.iv_screen, false);
                            helper.setGone(R.id.tv_screen, true);
                            helper.setText(R.id.tv_screen, R.string.no_bind);
                            if (((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).relayObjectMap == null || !((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(relateInfo.switchIndex))) {
                                return;
                            }
                            Object obj = ((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).relayObjectMap.get(Integer.valueOf(relateInfo.switchIndex));
                            if (obj instanceof Device) {
                                helper.setText(R.id.tv_screen, ((Device) obj).getName());
                                return;
                            } else {
                                if (obj instanceof Group) {
                                    helper.setText(R.id.tv_screen, ((Group) obj).getName());
                                    return;
                                }
                                return;
                            }
                        }
                        if (relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeIcon.getValue()) {
                            helper.setGone(R.id.iv_screen, true);
                            helper.setGone(R.id.tv_screen, false);
                            if (ScreenIconUtils.getScreenIconRes(relateInfo.iconIndex) != 0) {
                                helper.setImageResource(R.id.iv_screen, ScreenIconUtils.getScreenIconRes(relateInfo.iconIndex));
                                return;
                            }
                            return;
                        }
                        if (relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeIconWord.getValue()) {
                            helper.setGone(R.id.iv_screen, true);
                            helper.setGone(R.id.tv_screen, true);
                            if (z) {
                                helper.setGone(R.id.iv_screen, false);
                                helper.setText(R.id.tv_screen, StringUtils.replaceString(relateInfo.screenStr, true));
                            } else {
                                helper.setText(R.id.tv_screen, relateInfo.screenStr);
                            }
                            if (ScreenIconUtils.getScreenIconRes(relateInfo.iconIndex) != 0) {
                                helper.setImageResource(R.id.iv_screen, ScreenIconUtils.getScreenIconRes(relateInfo.iconIndex));
                                return;
                            }
                            return;
                        }
                        helper.setGone(R.id.iv_screen, false);
                        helper.setGone(R.id.tv_screen, true);
                        if (z) {
                            helper.setText(R.id.tv_screen, StringUtils.replaceString(relateInfo.screenStr, false));
                            return;
                        } else {
                            helper.setText(R.id.tv_screen, relateInfo.screenStr);
                            return;
                        }
                    }
                    helper.setGone(R.id.iv_screen, false);
                    helper.setGone(R.id.tv_screen, true);
                    helper.setText(R.id.tv_screen, R.string.click_to_change);
                    if (((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).relayObjectMap == null || !((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(helper.getAdapterPosition() + 1))) {
                        return;
                    }
                    Object obj2 = ((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).relayObjectMap.get(Integer.valueOf(helper.getAdapterPosition() + 1));
                    if (obj2 instanceof Device) {
                        helper.setText(R.id.tv_screen, ((Device) obj2).getName());
                    } else if (obj2 instanceof Group) {
                        helper.setText(R.id.tv_screen, ((Group) obj2).getName());
                    }
                }
            };
            this.screenAdapter = baseQuickAdapter2;
            baseQuickAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    ActNewScreenPanel.lambda$initScreenAdapter$12(baseQuickAdapter3, view, i);
                }
            });
            this.screenAdapter.bindToRecyclerView(((ActNewScreenPanelBinding) this.mViewBinding).rvScreenInfo);
            RecyclerView recyclerView = ((ActNewScreenPanelBinding) this.mViewBinding).rvScreenInfo;
            GridLayoutManager gridLayoutManager = new GridLayoutManager((Context) this, this.spanCount, 1, true);
            this.mScreenGridLayoutManager = gridLayoutManager;
            recyclerView.setLayoutManager(gridLayoutManager);
            ((ActNewScreenPanelBinding) this.mViewBinding).rvScreenInfo.setHasFixedSize(true);
            ((DefaultItemAnimator) ((ActNewScreenPanelBinding) this.mViewBinding).rvScreenInfo.getItemAnimator()).setSupportsChangeAnimations(false);
            return;
        }
        baseQuickAdapter.replaceData(((ActSmartPanelVM) this.mViewModel).relatedInfoList);
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    private void showUnbindDialog(final int position) {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.reset_relate));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel$$ExternalSyntheticLambda14
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActNewScreenPanel.this.lambda$showUnbindDialog$13(position, (Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnbindDialog$13(int i, Integer num) {
        ((ActSmartPanelVM) this.mViewModel).unBindRelateInfo(i);
    }

    private void showInitGuide() {
        int i;
        if (SharedPreferenceUtil.queryBooleanValue(Constants.NEED_SCREEN_GUIDE, true)) {
            int i2 = this.zoneCount;
            if (i2 == 2) {
                i = LanguageUtils.isChinese(this.activity) ? R.layout.view_s2_pro_guide : R.layout.view_s2_pro_guide_en;
            } else if (i2 == 3) {
                i = LanguageUtils.isChinese(this.activity) ? R.layout.view_s3_pro_guide : R.layout.view_s3_pro_guide_en;
            } else {
                i = LanguageUtils.isChinese(this.activity) ? R.layout.view_s1_pro_guide : R.layout.view_s1_pro_guide_en;
            }
            SharedPreferenceUtil.edit().keepShared(Constants.NEED_SCREEN_GUIDE, false);
            new Curtain(this).with(((ActNewScreenPanelBinding) this.mViewBinding).viewGuide).setTopView(i).addOnTopViewClickListener(R.id.iv_close, new OnViewInTopClickListener<IGuide>(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel.6
                @Override // com.qw.curtain.lib.OnViewInTopClickListener
                public void onClick(View current, IGuide currentHost) {
                    currentHost.dismissGuide();
                }
            }).show();
        }
    }

    private void initTab() {
        if (((ActSmartPanelVM) this.mViewModel).tabs.isEmpty()) {
            ((ActSmartPanelVM) this.mViewModel).initTabList();
            ((ActNewScreenPanelBinding) this.mViewBinding).vp.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel.7
                @Override // androidx.viewpager2.adapter.FragmentStateAdapter
                public Fragment createFragment(int position) {
                    return ((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).tabs.get(position).getFragment();
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public int getItemCount() {
                    return ((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).tabs.size();
                }
            });
            ((ActNewScreenPanelBinding) this.mViewBinding).tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel.8
                @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
                public void onTabSelected(TabLayout.Tab tab) {
                    ActNewScreenPanel actNewScreenPanel = ActNewScreenPanel.this;
                    actNewScreenPanel.createTabCustomView(actNewScreenPanel.activity, tab, ((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).tabs.get(tab.getPosition()).getTitle(), true);
                }

                @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
                public void onTabUnselected(TabLayout.Tab tab) {
                    ActNewScreenPanel actNewScreenPanel = ActNewScreenPanel.this;
                    actNewScreenPanel.createTabCustomView(actNewScreenPanel.activity, tab, ((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).tabs.get(tab.getPosition()).getTitle(), false);
                }

                @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
                public void onTabReselected(TabLayout.Tab tab) {
                    ActNewScreenPanel actNewScreenPanel = ActNewScreenPanel.this;
                    actNewScreenPanel.createTabCustomView(actNewScreenPanel.activity, tab, ((ActSmartPanelVM) ActNewScreenPanel.this.mViewModel).tabs.get(tab.getPosition()).getTitle(), true);
                }
            });
            new TabLayoutMediator(((ActNewScreenPanelBinding) this.mViewBinding).tabLayout, ((ActNewScreenPanelBinding) this.mViewBinding).vp, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel$$ExternalSyntheticLambda11
                @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
                public final void onConfigureTab(TabLayout.Tab tab, int i) {
                    ActNewScreenPanel.this.lambda$initTab$14(tab, i);
                }
            }).attach();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTab$14(TabLayout.Tab tab, int i) {
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