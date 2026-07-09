package com.ltech.smarthome.ui.device.screenpanel;

import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActScreenPanelBinding;
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
public class ActScreenPanel extends BaseControlActivity<ActScreenPanelBinding, ActSmartPanelVM> {
    private static final boolean NEED_CHECK_VERSION = true;
    private int[] backgroundResArray;
    protected List<RelateInfoItem> dataList;
    protected BaseQuickAdapter<RelateInfoItem, BaseViewHolder> infoAdapter;
    private GridLayoutManager mGridLayoutManager;
    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mScreenGridLayoutManager;
    private LinearLayoutManager mScreenLinearLayoutManager;
    protected BaseQuickAdapter<RelateInfoItem, BaseViewHolder> screenAdapter;
    private int[] screenBgRes;
    private int spanCount;
    private int zoneCount;
    protected boolean[] selectArray = new boolean[8];
    private final String BASE_VERSION = "SVer000.010.000";

    static /* synthetic */ boolean lambda$initRelatedInfoView$11(BaseDialog baseDialog, View view) {
        return false;
    }

    static /* synthetic */ boolean lambda$initRelatedInfoView$8(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.layout_load;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_screen_panel;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
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
        ((ActSmartPanelVM) this.mViewModel).panelZoneStateLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActScreenPanel.this.lambda$startObserve$0((List) obj);
            }
        });
        ((ActSmartPanelVM) this.mViewModel).updateUIEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActScreenPanel.this.lambda$startObserve$1(obj);
            }
        });
        this.checkVersionFinish.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActScreenPanel.this.lambda$startObserve$2((Void) obj);
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
    public /* synthetic */ void lambda$startObserve$2(Void r4) {
        ((ActSmartPanelVM) this.mViewModel).queryPanelState(((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), 4, this.zoneCount);
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
                return;
            } else {
                showNoPermissionDialog();
                return;
            }
        }
        startObjectObserve();
        if (((ActSmartPanelVM) this.mViewModel).groupControl) {
            checkVersionForUpdate((Group) ((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), "SVer000.010.000");
        } else {
            checkVersionForUpdate((Device) ((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), "SVer000.010.000");
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        startObjectObserve();
        if (((ActSmartPanelVM) this.mViewModel).groupControl) {
            checkVersionForUpdate((Group) ((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), "SVer000.010.000");
        } else {
            checkVersionForUpdate((Device) ((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), "SVer000.010.000");
        }
    }

    private void startObjectObserve() {
        if (((ActSmartPanelVM) this.mViewModel).groupControl) {
            ((ActSmartPanelVM) this.mViewModel).controlObject.setValue(Injection.repo().group().getGroupById(((ActSmartPanelVM) this.mViewModel).controlId));
            return;
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
        ((ActSmartPanelVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel$$ExternalSyntheticLambda10
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActScreenPanel.this.lambda$startDeviceObserve$4(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDeviceObserve$4(Object obj) {
        LHomeLog.i(getClass(), "message_send (device)mViewModel.controlObject enter");
        if (obj instanceof Device) {
            final Device device = (Device) obj;
            setTitle(device.getDeviceName());
            ((ActScreenPanelBinding) this.mViewBinding).ivSwitch5.setVisibility(0);
            ((ActScreenPanelBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s1c_shadown_2);
            ((ActScreenPanelBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s1c_shadown_1);
            ((ActScreenPanelBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s1c_shadown_3);
            String productId = device.getProductId();
            productId.hashCode();
            if (productId.equals(ProductId.ID_SMART_SWITCH_S2_PRO)) {
                this.spanCount = 2;
                this.zoneCount = 2;
                this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s2pro);
                this.screenBgRes = new int[]{R.mipmap.s2_screen2_1, R.mipmap.s2_screen2_2};
            } else if (productId.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                this.spanCount = 3;
                this.zoneCount = 3;
                this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s3pro);
                this.screenBgRes = new int[]{R.mipmap.s3_screen3_1, R.mipmap.s3_screen3_2, R.mipmap.s3_screen3_3};
            } else {
                this.spanCount = 1;
                this.zoneCount = 1;
                this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s1pro);
                this.screenBgRes = new int[]{R.mipmap.s1_screen};
            }
            showInitGuide();
            initRelatedInfoView(device);
            MessageManager.getInstance().setPanelSwitchStatusCallBack(new MessageManager.SmartPanelSwitchStatusCallBack() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel$$ExternalSyntheticLambda9
                @Override // com.smart.message.MessageManager.SmartPanelSwitchStatusCallBack
                public final void onDataReceive(ResponseMsg responseMsg) {
                    ActScreenPanel.this.lambda$startDeviceObserve$3(device, responseMsg);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDeviceObserve$3(Device device, ResponseMsg responseMsg) {
        ((ActSmartPanelVM) this.mViewModel).refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserSwitchPanelState(responseMsg.getAgreementId(), ((BleParam) device.getParam(BleParam.class)).getUnicastAddress(), this.zoneCount, responseMsg.getResData()));
    }

    private void startGroupObserve() {
        ((ActSmartPanelVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel$$ExternalSyntheticLambda11
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActScreenPanel.this.lambda$startGroupObserve$6(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startGroupObserve$6(Object obj) {
        LHomeLog.i(getClass(), "message_send (group)mViewModel.controlObject enter");
        if (obj instanceof Group) {
            final Group group = (Group) obj;
            if (RelaySeparationHelper.isRelaySeparationPanelDevice(group)) {
                NavUtils.destination(ActNewScreenPanel.class).withLong(Constants.PLACE_ID, ((ActSmartPanelVM) this.mViewModel).placeId).withLong(Constants.CONTROL_ID, ((ActSmartPanelVM) this.mViewModel).controlId).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) group)).withBoolean(Constants.GROUP_CONTROL, true).navigation(this);
                finishActivity();
                return;
            }
            setTitle(group.getGroupName());
            ((ActScreenPanelBinding) this.mViewBinding).ivSwitch5.setVisibility(0);
            ((ActScreenPanelBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s1c_shadown_2);
            ((ActScreenPanelBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s1c_shadown_1);
            ((ActScreenPanelBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s1c_shadown_3);
            int lightColorType = ProductRepository.getLightColorType((Object) group);
            if (lightColorType == 9) {
                this.spanCount = 2;
                this.zoneCount = 2;
                this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s2pro);
                this.screenBgRes = new int[]{R.mipmap.s2_screen2_1, R.mipmap.s2_screen2_2};
            } else if (lightColorType == 10) {
                this.spanCount = 3;
                this.zoneCount = 3;
                this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s3pro);
                this.screenBgRes = new int[]{R.mipmap.s3_screen3_1, R.mipmap.s3_screen3_2, R.mipmap.s3_screen3_3};
            } else {
                this.spanCount = 1;
                this.zoneCount = 1;
                this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s1pro);
                this.screenBgRes = new int[]{R.mipmap.s1_screen};
            }
            showInitGuide();
            if (group.getDeviceIds().isEmpty()) {
                initRelatedInfoView(group);
            } else {
                initRelatedInfoView(group);
                MessageManager.getInstance().setPanelSwitchStatusCallBack(new MessageManager.SmartPanelSwitchStatusCallBack() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel$$ExternalSyntheticLambda0
                    @Override // com.smart.message.MessageManager.SmartPanelSwitchStatusCallBack
                    public final void onDataReceive(ResponseMsg responseMsg) {
                        ActScreenPanel.this.lambda$startGroupObserve$5(group, responseMsg);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startGroupObserve$5(Group group, ResponseMsg responseMsg) {
        ((ActSmartPanelVM) this.mViewModel).refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserSwitchPanelState(responseMsg.getAgreementId(), group.getGroupAddress(), this.zoneCount, responseMsg.getResData()));
    }

    private void initRelatedInfoView(final Group group) {
        ((ActSmartPanelVM) this.mViewModel).initPanelState(group);
        ((ActSmartPanelVM) this.mViewModel).initRelateInfoList(group);
        if (((ActSmartPanelVM) this.mViewModel).getProPanelCount(group) == 0 && ((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getSwitchShowType() == 2) {
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
            BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_smart_panel_key, ((ActSmartPanelVM) this.mViewModel).relatedInfoList) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, RelateInfoItem item) {
                    LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.layout_content);
                    ViewGroup.LayoutParams layoutParams = helper.itemView.getLayoutParams();
                    int lightColorType = ProductRepository.getLightColorType((Object) group);
                    if (lightColorType == 9 || lightColorType == 10) {
                        layoutParams.height = ActScreenPanel.this.mGridLayoutManager.getHeight();
                        linearLayout.setGravity(81);
                        linearLayout.setPadding(0, 0, 0, Utils.dip2px(this.mContext, 30.0f));
                    } else {
                        layoutParams.height = ActScreenPanel.this.mLinearLayoutManager.getHeight();
                        linearLayout.setGravity(81);
                        linearLayout.setPadding(0, 0, 0, Utils.dip2px(this.mContext, 30.0f));
                    }
                    helper.itemView.setBackgroundResource(ActScreenPanel.this.backgroundResArray[helper.getBindingAdapterPosition()]);
                    if (item.infoName == null || TextUtils.isEmpty(item.infoName)) {
                        if (((ActSmartPanelVM) ActScreenPanel.this.mViewModel).relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()) != null) {
                            if (((ActSmartPanelVM) ActScreenPanel.this.mViewModel).relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()).name == null || ((ActSmartPanelVM) ActScreenPanel.this.mViewModel).relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()).name.equals("")) {
                                helper.setText(R.id.tv_device_name, ActScreenPanel.this.getResources().getStringArray(R.array.smart_panel_s4_key_select)[helper.getAdapterPosition()]);
                            } else {
                                helper.setText(R.id.tv_device_name, ((ActSmartPanelVM) ActScreenPanel.this.mViewModel).relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()).name);
                            }
                        } else {
                            helper.setText(R.id.tv_device_name, ActScreenPanel.this.getResources().getStringArray(R.array.smart_panel_s4_key_select)[helper.getAdapterPosition()]);
                        }
                    } else {
                        helper.setText(R.id.tv_device_name, item.infoName);
                    }
                    if (item.infoName == null || item.type == 0) {
                        helper.setText(R.id.tv_sub_text, ActScreenPanel.this.getString(R.string.no_bind_object));
                    } else {
                        helper.setText(R.id.tv_sub_text, item.actionInfo);
                    }
                    helper.setTextColor(R.id.tv_sub_text, ContextCompat.getColor(ActScreenPanel.this, R.color.color_border_gray));
                    RelatedInfoExtParam.RelateInfo relateInfo = ((ActSmartPanelVM) ActScreenPanel.this.mViewModel).relateInfoAssistant.getRelateInfo(helper.getAdapterPosition());
                    int i = R.color.colorPrimary;
                    if (relateInfo != null && ((ActSmartPanelVM) ActScreenPanel.this.mViewModel).relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()).objectId != 0 && item.infoName != null) {
                        helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(ActScreenPanel.this, R.color.colorPrimary));
                    } else {
                        ActScreenPanel actScreenPanel = ActScreenPanel.this;
                        if (!actScreenPanel.selectArray[helper.getAdapterPosition()]) {
                            i = R.color.color_text_screen_panel_unselect;
                        }
                        helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(actScreenPanel, i));
                    }
                    ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
                }
            };
            this.infoAdapter = baseQuickAdapter2;
            baseQuickAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel$$ExternalSyntheticLambda12
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    ActScreenPanel.this.lambda$initRelatedInfoView$7(baseQuickAdapter3, view, i);
                }
            });
            this.infoAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel$$ExternalSyntheticLambda13
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemLongClickListener
                public final boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    boolean lambda$initRelatedInfoView$9;
                    lambda$initRelatedInfoView$9 = ActScreenPanel.this.lambda$initRelatedInfoView$9(baseQuickAdapter3, view, i);
                    return lambda$initRelatedInfoView$9;
                }
            });
            this.infoAdapter.bindToRecyclerView(((ActScreenPanelBinding) this.mViewBinding).rvKeyInfo);
            int i = this.spanCount;
            if (i == 2 || i == 3) {
                RecyclerView recyclerView = ((ActScreenPanelBinding) this.mViewBinding).rvKeyInfo;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, this.spanCount);
                this.mGridLayoutManager = gridLayoutManager;
                recyclerView.setLayoutManager(gridLayoutManager);
            } else {
                RecyclerView recyclerView2 = ((ActScreenPanelBinding) this.mViewBinding).rvKeyInfo;
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                this.mLinearLayoutManager = linearLayoutManager;
                recyclerView2.setLayoutManager(linearLayoutManager);
                ((ActScreenPanelBinding) this.mViewBinding).rvKeyInfo.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel.3
                    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);
                        outRect.set(0, 0, 0, 0);
                    }
                });
            }
            ((ActScreenPanelBinding) this.mViewBinding).rvKeyInfo.setHasFixedSize(true);
            ((DefaultItemAnimator) ((ActScreenPanelBinding) this.mViewBinding).rvKeyInfo.getItemAnimator()).setSupportsChangeAnimations(false);
        } else {
            baseQuickAdapter.replaceData(((ActSmartPanelVM) this.mViewModel).relatedInfoList);
        }
        initScreenAdapter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRelatedInfoView$7(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.infoAdapter.getData().get(i);
        this.selectArray[i] = !r3[i];
        baseQuickAdapter.notifyItemChanged(i);
        if (((ActSmartPanelVM) this.mViewModel).groupControl) {
            ((ActSmartPanelVM) this.mViewModel).updateGroupStates(this.selectArray, (Group) ((ActSmartPanelVM) this.mViewModel).controlObject.getValue());
        }
        ((ActSmartPanelVM) this.mViewModel).sendSingleOnOff(i, this.selectArray[i]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initRelatedInfoView$9(BaseQuickAdapter baseQuickAdapter, View view, final int i) {
        if (Injection.repo().home().getSelectPlace().getValue().isMember()) {
            showNoPermissionDialog();
            return false;
        }
        if (this.infoAdapter.getData().get(i).infoName == null || this.infoAdapter.getData().get(i).type == 0) {
            MessageDialog.show(this, getString(R.string.tips), getString(R.string.app_str_smart_panel_bind_tip)).setCancelable(false).setOkButton(getString(R.string.app_str_continue_to_bind), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel.2
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public boolean onClick(BaseDialog baseDialog, View v) {
                    ((ActSmartPanelVM) ActScreenPanel.this.mViewModel).showBindDialog(i);
                    return false;
                }
            }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel$$ExternalSyntheticLambda14
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view2) {
                    return ActScreenPanel.lambda$initRelatedInfoView$8(baseDialog, view2);
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
            BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_smart_panel_key, ((ActSmartPanelVM) this.mViewModel).relatedInfoList) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel.4
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, RelateInfoItem item) {
                    LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.layout_content);
                    ViewGroup.LayoutParams layoutParams = helper.itemView.getLayoutParams();
                    String productId = device.getProductId();
                    productId.hashCode();
                    if (productId.equals(ProductId.ID_SMART_SWITCH_S2_PRO) || productId.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                        linearLayout.setGravity(81);
                        linearLayout.setPadding(0, 0, 0, Utils.dip2px(this.mContext, 30.0f));
                        layoutParams.height = ActScreenPanel.this.mGridLayoutManager.getHeight();
                    } else {
                        linearLayout.setGravity(81);
                        linearLayout.setPadding(0, 0, 0, Utils.dip2px(this.mContext, 30.0f));
                        layoutParams.height = ActScreenPanel.this.mLinearLayoutManager.getHeight();
                    }
                    helper.itemView.setBackgroundResource(ActScreenPanel.this.backgroundResArray[helper.getBindingAdapterPosition()]);
                    if (item.infoName == null || TextUtils.isEmpty(item.infoName)) {
                        if (((ActSmartPanelVM) ActScreenPanel.this.mViewModel).relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()) != null) {
                            if (((ActSmartPanelVM) ActScreenPanel.this.mViewModel).relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()).name == null || ((ActSmartPanelVM) ActScreenPanel.this.mViewModel).relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()).name.equals("")) {
                                helper.setText(R.id.tv_device_name, ActScreenPanel.this.getResources().getStringArray(R.array.smart_panel_s4_key_select)[helper.getAdapterPosition()]);
                            } else {
                                helper.setText(R.id.tv_device_name, ((ActSmartPanelVM) ActScreenPanel.this.mViewModel).relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()).name);
                            }
                        } else {
                            helper.setText(R.id.tv_device_name, ActScreenPanel.this.getResources().getStringArray(R.array.smart_panel_s4_key_select)[helper.getAdapterPosition()]);
                        }
                    } else {
                        helper.setText(R.id.tv_device_name, item.infoName);
                    }
                    if (item.infoName == null || item.type == 0) {
                        helper.setText(R.id.tv_sub_text, ActScreenPanel.this.getString(R.string.no_bind_object));
                    } else {
                        helper.setText(R.id.tv_sub_text, item.actionInfo);
                    }
                    helper.setTextColor(R.id.tv_sub_text, ContextCompat.getColor(ActScreenPanel.this, R.color.color_border_gray));
                    RelatedInfoExtParam.RelateInfo relateInfo = ((ActSmartPanelVM) ActScreenPanel.this.mViewModel).relateInfoAssistant.getRelateInfo(helper.getAdapterPosition());
                    int i = R.color.colorPrimary;
                    if (relateInfo != null && ((ActSmartPanelVM) ActScreenPanel.this.mViewModel).relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()).objectId != 0 && item.infoName != null) {
                        helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(ActScreenPanel.this, R.color.colorPrimary));
                    } else {
                        ActScreenPanel actScreenPanel = ActScreenPanel.this;
                        if (!actScreenPanel.selectArray[helper.getAdapterPosition()]) {
                            i = R.color.color_text_screen_panel_unselect;
                        }
                        helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(actScreenPanel, i));
                    }
                    ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
                }
            };
            this.infoAdapter = baseQuickAdapter2;
            baseQuickAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel$$ExternalSyntheticLambda7
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    ActScreenPanel.this.lambda$initRelatedInfoView$10(baseQuickAdapter3, view, i);
                }
            });
            this.infoAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel$$ExternalSyntheticLambda8
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemLongClickListener
                public final boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    boolean lambda$initRelatedInfoView$12;
                    lambda$initRelatedInfoView$12 = ActScreenPanel.this.lambda$initRelatedInfoView$12(baseQuickAdapter3, view, i);
                    return lambda$initRelatedInfoView$12;
                }
            });
            this.infoAdapter.bindToRecyclerView(((ActScreenPanelBinding) this.mViewBinding).rvKeyInfo);
            int i = this.spanCount;
            if (i == 2 || i == 3) {
                RecyclerView recyclerView = ((ActScreenPanelBinding) this.mViewBinding).rvKeyInfo;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, this.spanCount);
                this.mGridLayoutManager = gridLayoutManager;
                recyclerView.setLayoutManager(gridLayoutManager);
            } else {
                RecyclerView recyclerView2 = ((ActScreenPanelBinding) this.mViewBinding).rvKeyInfo;
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                this.mLinearLayoutManager = linearLayoutManager;
                recyclerView2.setLayoutManager(linearLayoutManager);
                ((ActScreenPanelBinding) this.mViewBinding).rvKeyInfo.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel.6
                    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);
                        outRect.set(0, 0, 0, 0);
                    }
                });
            }
            ((ActScreenPanelBinding) this.mViewBinding).rvKeyInfo.setHasFixedSize(true);
            ((DefaultItemAnimator) ((ActScreenPanelBinding) this.mViewBinding).rvKeyInfo.getItemAnimator()).setSupportsChangeAnimations(false);
        } else {
            baseQuickAdapter.replaceData(((ActSmartPanelVM) this.mViewModel).relatedInfoList);
        }
        initScreenAdapter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRelatedInfoView$10(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.infoAdapter.getData().get(i);
        this.selectArray[i] = !r3[i];
        baseQuickAdapter.notifyItemChanged(i);
        if (((ActSmartPanelVM) this.mViewModel).groupControl) {
            ((ActSmartPanelVM) this.mViewModel).updateGroupStates(this.selectArray, (Group) ((ActSmartPanelVM) this.mViewModel).controlObject.getValue());
        }
        ((ActSmartPanelVM) this.mViewModel).sendSingleOnOff(i, this.selectArray[i]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initRelatedInfoView$12(BaseQuickAdapter baseQuickAdapter, View view, final int i) {
        if (Injection.repo().home().getSelectPlace().getValue().isMember()) {
            showNoPermissionDialog();
            return false;
        }
        if (this.infoAdapter.getData().get(i).infoName == null || this.infoAdapter.getData().get(i).type == 0) {
            MessageDialog.show(this, getString(R.string.tips), getString(R.string.app_str_smart_panel_bind_tip)).setCancelable(false).setOkButton(getString(R.string.app_str_continue_to_bind), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel.5
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public boolean onClick(BaseDialog baseDialog, View v) {
                    ((ActSmartPanelVM) ActScreenPanel.this.mViewModel).showBindDialog(i);
                    return false;
                }
            }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel$$ExternalSyntheticLambda6
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view2) {
                    return ActScreenPanel.lambda$initRelatedInfoView$11(baseDialog, view2);
                }
            });
        } else {
            showUnbindDialog(i);
        }
        return false;
    }

    private void initScreenAdapter() {
        BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = this.screenAdapter;
        if (baseQuickAdapter == null) {
            BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_screen_panel, ((ActSmartPanelVM) this.mViewModel).relatedInfoList) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel.7
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, RelateInfoItem item) {
                    ViewGroup.LayoutParams layoutParams = helper.itemView.getLayoutParams();
                    int i = ActScreenPanel.this.spanCount;
                    if (i == 2 || i == 3) {
                        layoutParams.height = ActScreenPanel.this.mScreenGridLayoutManager.getHeight();
                    } else {
                        layoutParams.height = ActScreenPanel.this.mScreenLinearLayoutManager.getHeight();
                    }
                    helper.itemView.setBackgroundResource(ActScreenPanel.this.screenBgRes[helper.getBindingAdapterPosition()]);
                    boolean z = ((ActSmartPanelVM) ActScreenPanel.this.mViewModel).relateInfoAssistant.getSwitchScreenBigIcon() == 2;
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
                    if (LanguageUtils.isRussian(ActScreenPanel.this)) {
                        textView.setMaxLines(3);
                    }
                    RelatedInfoExtParam.RelateInfo relateInfo = item.relateInfo;
                    if (relateInfo == null || relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeNone.getValue() || (item.relateInfo.objectId != 0 && item.infoName == null)) {
                        helper.setGone(R.id.iv_screen, false);
                        helper.setGone(R.id.tv_screen, true);
                        helper.setText(R.id.tv_screen, R.string.click_to_change);
                        return;
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
                    } else {
                        helper.setText(R.id.tv_screen, relateInfo.screenStr);
                    }
                }
            };
            this.screenAdapter = baseQuickAdapter2;
            baseQuickAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel$$ExternalSyntheticLambda4
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    ActScreenPanel.this.lambda$initScreenAdapter$13(baseQuickAdapter3, view, i);
                }
            });
            this.screenAdapter.bindToRecyclerView(((ActScreenPanelBinding) this.mViewBinding).rvScreenInfo);
            int i = this.spanCount;
            if (i == 2 || i == 3) {
                RecyclerView recyclerView = ((ActScreenPanelBinding) this.mViewBinding).rvScreenInfo;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, this.spanCount);
                this.mScreenGridLayoutManager = gridLayoutManager;
                recyclerView.setLayoutManager(gridLayoutManager);
            } else {
                RecyclerView recyclerView2 = ((ActScreenPanelBinding) this.mViewBinding).rvScreenInfo;
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                this.mScreenLinearLayoutManager = linearLayoutManager;
                recyclerView2.setLayoutManager(linearLayoutManager);
                ((ActScreenPanelBinding) this.mViewBinding).rvScreenInfo.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel.8
                    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);
                        outRect.set(0, 0, 0, 0);
                    }
                });
            }
            ((ActScreenPanelBinding) this.mViewBinding).rvScreenInfo.setHasFixedSize(true);
            ((DefaultItemAnimator) ((ActScreenPanelBinding) this.mViewBinding).rvScreenInfo.getItemAnimator()).setSupportsChangeAnimations(false);
            return;
        }
        baseQuickAdapter.replaceData(((ActSmartPanelVM) this.mViewModel).relatedInfoList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initScreenAdapter$13(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        NavUtils.destination(ActSetScreenDisplay.class).withLong(Constants.PLACE_ID, ((ActSmartPanelVM) this.mViewModel).placeId).withLong(Constants.CONTROL_ID, ((ActSmartPanelVM) this.mViewModel).controlId).withInt(Constants.LIGHT_TYPE, ((ActSmartPanelVM) this.mViewModel).controlType).withString(Constants.PRODUCT_ID, ((ActSmartPanelVM) this.mViewModel).productId).withBoolean(Constants.GROUP_CONTROL, ((ActSmartPanelVM) this.mViewModel).groupControl).navigation(this);
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    private void showUnbindDialog(final int position) {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.reset_relate));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActScreenPanel.this.lambda$showUnbindDialog$14(position, (Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnbindDialog$14(int i, Integer num) {
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
            new Curtain(this).with(((ActScreenPanelBinding) this.mViewBinding).viewGuide).setTopView(i).addOnTopViewClickListener(R.id.iv_close, new OnViewInTopClickListener<IGuide>(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel.9
                @Override // com.qw.curtain.lib.OnViewInTopClickListener
                public void onClick(View current, IGuide currentHost) {
                    currentHost.dismissGuide();
                }
            }).show();
        }
    }
}