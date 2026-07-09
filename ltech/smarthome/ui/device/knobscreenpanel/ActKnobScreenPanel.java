package com.ltech.smarthome.ui.device.knobscreenpanel;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActKnobScreenPanelBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.ui.automation.ActAddAutomation;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSelectBleDeviceAndGroup;
import com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelVM;
import com.ltech.smarthome.ui.device.screenpanel.ActSetScreenDisplay;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.ScreenIconUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.qw.curtain.lib.Curtain;
import com.qw.curtain.lib.IGuide;
import com.qw.curtain.lib.OnViewInTopClickListener;
import com.smart.message.utils.StringUtils;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActKnobScreenPanel extends BaseControlActivity<ActKnobScreenPanelBinding, ActKnobPanelVM> {
    private BaseQuickAdapter<ActKnobPanelVM.ActionItem, BaseViewHolder> infoAdapter;
    private LinearLayoutManager mScreenLinearLayoutManager;
    private BaseQuickAdapter<RelateInfoItem, BaseViewHolder> screenAdapter;
    public SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.layout_content;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_knob_screen_panel;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        ((ActKnobScreenPanelBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.knobscreenpanel.ActKnobScreenPanel$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActKnobScreenPanel.this.lambda$initView$0((View) obj);
            }
        }));
        showInitGuide();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (((ActKnobPanelVM) this.mViewModel).getRelateInfoString(((ActKnobPanelVM) this.mViewModel).relateInfo) == null || ((ActKnobPanelVM) this.mViewModel).relateInfo.type == 0) {
            showBindDialog();
        } else {
            showUnbindDialog();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActKnobPanelVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActKnobPanelVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActKnobPanelVM) this.mViewModel).groupList.setValue(Injection.repo().group().getGroupListByPlaceId(((ActKnobPanelVM) this.mViewModel).placeId));
        ((ActKnobPanelVM) this.mViewModel).deviceList.setValue(Injection.repo().device().getDeviceListByPlaceId(((ActKnobPanelVM) this.mViewModel).placeId));
        ((ActKnobPanelVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.knobscreenpanel.ActKnobScreenPanel$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActKnobScreenPanel.this.lambda$startObserve$1((Device) obj);
            }
        });
        this.showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.knobscreenpanel.ActKnobScreenPanel$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActKnobScreenPanel.this.lambda$startObserve$2((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        ((ActKnobPanelVM) this.mViewModel).controlDevice = device;
        setTitle(device.getDeviceName());
        ((ActKnobScreenPanelBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s1c_shadown_1);
        ((ActKnobScreenPanelBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s1c_shadown_3);
        initActionInfoView(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        showNoPermissionDialog();
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
                goUpgrade(((ActKnobPanelVM) this.mViewModel).controlId);
                return;
            } else {
                showNoPermissionDialog();
                return;
            }
        }
        ((ActKnobPanelVM) this.mViewModel).controlObject.setValue(Injection.repo().device().getDeviceById(((ActKnobPanelVM) this.mViewModel).controlId));
        checkVersionForUpdate(((ActKnobPanelVM) this.mViewModel).controlObject.getValue(), "SVer000.003.000");
    }

    private void initScreenAdapter() {
        BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = this.screenAdapter;
        if (baseQuickAdapter == null) {
            BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_screen_panel, ((ActKnobPanelVM) this.mViewModel).relatedInfoList) { // from class: com.ltech.smarthome.ui.device.knobscreenpanel.ActKnobScreenPanel.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, RelateInfoItem item) {
                    helper.itemView.getLayoutParams().height = ActKnobScreenPanel.this.mScreenLinearLayoutManager.getHeight();
                    boolean z = ((ActKnobPanelVM) ActKnobScreenPanel.this.mViewModel).relateInfoAssistant.getSwitchScreenBigIcon() == 2;
                    ViewGroup.LayoutParams layoutParams = ((ImageView) helper.getView(R.id.iv_screen)).getLayoutParams();
                    TextView textView = (TextView) helper.getView(R.id.tv_screen);
                    if (z) {
                        layoutParams.width = SizeUtils.dp2px(35.0f);
                        layoutParams.height = SizeUtils.dp2px(35.0f);
                        textView.setTextSize(18.0f);
                    } else {
                        layoutParams.width = SizeUtils.dp2px(25.0f);
                        layoutParams.height = SizeUtils.dp2px(25.0f);
                        textView.setTextSize(13.0f);
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
            baseQuickAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.knobscreenpanel.ActKnobScreenPanel$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    ActKnobScreenPanel.this.lambda$initScreenAdapter$3(baseQuickAdapter3, view, i);
                }
            });
            this.screenAdapter.bindToRecyclerView(((ActKnobScreenPanelBinding) this.mViewBinding).rvScreenInfo);
            RecyclerView recyclerView = ((ActKnobScreenPanelBinding) this.mViewBinding).rvScreenInfo;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            this.mScreenLinearLayoutManager = linearLayoutManager;
            recyclerView.setLayoutManager(linearLayoutManager);
            ((ActKnobScreenPanelBinding) this.mViewBinding).rvScreenInfo.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.knobscreenpanel.ActKnobScreenPanel.2
                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(0, 0, 0, 0);
                }
            });
            ((ActKnobScreenPanelBinding) this.mViewBinding).rvScreenInfo.setHasFixedSize(true);
            ((DefaultItemAnimator) ((ActKnobScreenPanelBinding) this.mViewBinding).rvScreenInfo.getItemAnimator()).setSupportsChangeAnimations(false);
            return;
        }
        baseQuickAdapter.replaceData(((ActKnobPanelVM) this.mViewModel).relatedInfoList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initScreenAdapter$3(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        NavUtils.destination(ActSetScreenDisplay.class).withLong(Constants.PLACE_ID, ((ActKnobPanelVM) this.mViewModel).placeId).withLong(Constants.CONTROL_ID, ((ActKnobPanelVM) this.mViewModel).controlId).withString(Constants.PRODUCT_ID, ((ActKnobPanelVM) this.mViewModel).controlDevice.getProductId()).withBoolean(Constants.GROUP_CONTROL, false).navigation(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((ActKnobPanelVM) this.mViewModel).controlObject.setValue(Injection.repo().device().getDeviceById(((ActKnobPanelVM) this.mViewModel).controlId));
        checkVersionForUpdate(((ActKnobPanelVM) this.mViewModel).controlObject.getValue(), "SVer000.003.000");
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavHelper.goSetting(((ActKnobPanelVM) this.mViewModel).controlObject.getValue());
    }

    private void initActionInfoView(Device device) {
        ((ActKnobPanelVM) this.mViewModel).initRelateInfoList(device);
        ((ActKnobPanelVM) this.mViewModel).initScreenRelateInfoList(device);
        ((ActKnobScreenPanelBinding) this.mViewBinding).tvInstruction.setText(((ActKnobPanelVM) this.mViewModel).getInstructionName(this, ((ActKnobPanelVM) this.mViewModel).relateInfo));
        ((ActKnobScreenPanelBinding) this.mViewBinding).tvBindName.setText(((ActKnobPanelVM) this.mViewModel).getRelateInfoString(((ActKnobPanelVM) this.mViewModel).relateInfo));
        BaseQuickAdapter<ActKnobPanelVM.ActionItem, BaseViewHolder> baseQuickAdapter = this.infoAdapter;
        if (baseQuickAdapter == null) {
            BaseQuickAdapter<ActKnobPanelVM.ActionItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<ActKnobPanelVM.ActionItem, BaseViewHolder>(this, R.layout.item_sq_panel_action, ((ActKnobPanelVM) this.mViewModel).actionItemList) { // from class: com.ltech.smarthome.ui.device.knobscreenpanel.ActKnobScreenPanel.3
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, ActKnobPanelVM.ActionItem item) {
                    helper.setImageResource(R.id.iv_action, item.iconRes);
                    if (item.actionInfo != null) {
                        helper.setText(R.id.tv_action, item.actionName).setGone(R.id.tv_action, true);
                        helper.setText(R.id.tv_action_tip, item.actionInfo).setGone(R.id.tv_action_tip, true);
                        helper.setGone(R.id.tv_action_1, false);
                    } else {
                        helper.setGone(R.id.tv_action, false);
                        helper.setGone(R.id.tv_action_tip, false);
                        helper.setText(R.id.tv_action_1, item.actionName).setGone(R.id.tv_action_1, true);
                    }
                }
            };
            this.infoAdapter = baseQuickAdapter2;
            baseQuickAdapter2.bindToRecyclerView(((ActKnobScreenPanelBinding) this.mViewBinding).rvKnobAction);
            ((ActKnobScreenPanelBinding) this.mViewBinding).rvKnobAction.setHasFixedSize(true);
            ((ActKnobScreenPanelBinding) this.mViewBinding).rvKnobAction.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.knobscreenpanel.ActKnobScreenPanel.4
                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(SizeUtils.dp2px(5.0f), SizeUtils.dp2px(8.0f), SizeUtils.dp2px(5.0f), SizeUtils.dp2px(8.0f));
                }
            });
            ((DefaultItemAnimator) ((ActKnobScreenPanelBinding) this.mViewBinding).rvKnobAction.getItemAnimator()).setSupportsChangeAnimations(false);
        } else {
            baseQuickAdapter.setNewData(((ActKnobPanelVM) this.mViewModel).actionItemList);
        }
        if (((ActKnobPanelVM) this.mViewModel).getRelateInfoString(((ActKnobPanelVM) this.mViewModel).relateInfo) == null || ((ActKnobPanelVM) this.mViewModel).relateInfo.type == 0) {
            ((ActKnobScreenPanelBinding) this.mViewBinding).tvTipMessage.setVisibility(0);
            ((ActKnobScreenPanelBinding) this.mViewBinding).rvKnobAction.setLayoutManager(new GridLayoutManager(this, 4));
        } else {
            ((ActKnobScreenPanelBinding) this.mViewBinding).tvTipMessage.setVisibility(8);
            ((ActKnobScreenPanelBinding) this.mViewBinding).rvKnobAction.setLayoutManager(new GridLayoutManager(this, 2));
        }
        initScreenAdapter();
    }

    private void showBindDialog() {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.device));
        arrayList.add(getString(R.string.link_automation));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.knobscreenpanel.ActKnobScreenPanel$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActKnobScreenPanel.this.lambda$showBindDialog$4((Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBindDialog$4(Integer num) {
        int intValue = num.intValue();
        if (intValue == 0) {
            NavUtils.destination(ActKnobPanelSelectBleDeviceAndGroup.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActKnobPanelVM) this.mViewModel).controlId).withInt(Constants.RELATED_POSITION, 0).withInt(Constants.GROUP_RELATE, 1).withDefaultRequestCode().navigation(this);
        } else {
            if (intValue != 1) {
                return;
            }
            NavUtils.destination(ActAddAutomation.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActKnobPanelVM) this.mViewModel).controlId).navigation(this);
        }
    }

    private void showUnbindDialog() {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.reset_relate));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.knobscreenpanel.ActKnobScreenPanel$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActKnobScreenPanel.this.lambda$showUnbindDialog$5((Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnbindDialog$5(Integer num) {
        ((ActKnobPanelVM) this.mViewModel).unBindRelateInfo(0);
    }

    private void showInitGuide() {
        if (SharedPreferenceUtil.queryBooleanValue(Constants.KNOB_NEED_SCREEN_GUIDE, true)) {
            int i = LanguageUtils.isChinese(this.activity) ? R.layout.view_sq_pro_guide : R.layout.view_sq_pro_guide_en;
            SharedPreferenceUtil.edit().keepShared(Constants.KNOB_NEED_SCREEN_GUIDE, false);
            new Curtain(this).with(((ActKnobScreenPanelBinding) this.mViewBinding).viewGuide).setTopView(i).addOnTopViewClickListener(R.id.iv_close, new OnViewInTopClickListener<IGuide>(this) { // from class: com.ltech.smarthome.ui.device.knobscreenpanel.ActKnobScreenPanel.5
                @Override // com.qw.curtain.lib.OnViewInTopClickListener
                public void onClick(View current, IGuide currentHost) {
                    currentHost.dismissGuide();
                }
            }).show();
        }
    }
}