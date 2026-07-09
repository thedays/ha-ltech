package com.ltech.smarthome.ui.device.knobpanel;

import android.graphics.Rect;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActKnobPanelBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.automation.ActAddAutomation;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActKnobPanel extends BaseControlActivity<ActKnobPanelBinding, ActKnobPanelVM> {
    private BaseQuickAdapter<ActKnobPanelVM.ActionItem, BaseViewHolder> infoAdapter;
    public SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.layout_content;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_knob_panel;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        ((ActKnobPanelBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanel$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActKnobPanel.this.lambda$initView$0((View) obj);
            }
        }));
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
        ((ActKnobPanelVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanel$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActKnobPanel.this.lambda$startObserve$1((Device) obj);
            }
        });
        this.showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanel$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActKnobPanel.this.lambda$startObserve$2((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        ((ActKnobPanelVM) this.mViewModel).controlDevice = device;
        if (device != null) {
            setTitle(device.getDeviceName());
            initActionInfoView(device);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        showNoPermissionDialog();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((ActKnobPanelVM) this.mViewModel).controlObject.setValue(Injection.repo().device().getDeviceById(((ActKnobPanelVM) this.mViewModel).controlId));
    }

    private void initActionInfoView(Device device) {
        ((ActKnobPanelVM) this.mViewModel).initRelateInfoList(device);
        ((ActKnobPanelBinding) this.mViewBinding).tvInstruction.setText(((ActKnobPanelVM) this.mViewModel).getInstructionName(this, ((ActKnobPanelVM) this.mViewModel).relateInfo));
        ((ActKnobPanelBinding) this.mViewBinding).tvBindName.setText(((ActKnobPanelVM) this.mViewModel).getRelateInfoString(((ActKnobPanelVM) this.mViewModel).relateInfo));
        BaseQuickAdapter<ActKnobPanelVM.ActionItem, BaseViewHolder> baseQuickAdapter = this.infoAdapter;
        if (baseQuickAdapter == null) {
            BaseQuickAdapter<ActKnobPanelVM.ActionItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<ActKnobPanelVM.ActionItem, BaseViewHolder>(this, R.layout.item_sq_panel_action, ((ActKnobPanelVM) this.mViewModel).actionItemList) { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanel.1
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
            baseQuickAdapter2.bindToRecyclerView(((ActKnobPanelBinding) this.mViewBinding).rvKnobAction);
            ((ActKnobPanelBinding) this.mViewBinding).rvKnobAction.setHasFixedSize(true);
            ((ActKnobPanelBinding) this.mViewBinding).rvKnobAction.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanel.2
                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(SizeUtils.dp2px(5.0f), SizeUtils.dp2px(8.0f), SizeUtils.dp2px(5.0f), SizeUtils.dp2px(8.0f));
                }
            });
            ((DefaultItemAnimator) ((ActKnobPanelBinding) this.mViewBinding).rvKnobAction.getItemAnimator()).setSupportsChangeAnimations(false);
        } else {
            baseQuickAdapter.setNewData(((ActKnobPanelVM) this.mViewModel).actionItemList);
        }
        if (((ActKnobPanelVM) this.mViewModel).getRelateInfoString(((ActKnobPanelVM) this.mViewModel).relateInfo) == null || ((ActKnobPanelVM) this.mViewModel).relateInfo.type == 0) {
            ((ActKnobPanelBinding) this.mViewBinding).tvTipMessage.setVisibility(0);
            ((ActKnobPanelBinding) this.mViewBinding).rvKnobAction.setLayoutManager(new GridLayoutManager(this, 4));
        } else {
            ((ActKnobPanelBinding) this.mViewBinding).tvTipMessage.setVisibility(8);
            ((ActKnobPanelBinding) this.mViewBinding).rvKnobAction.setLayoutManager(new GridLayoutManager(this, 2));
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavHelper.goSetting(((ActKnobPanelVM) this.mViewModel).controlObject.getValue());
    }

    private void showBindDialog() {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.device));
        arrayList.add(getString(R.string.link_automation));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanel$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActKnobPanel.this.lambda$showBindDialog$3((Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBindDialog$3(Integer num) {
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
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanel$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActKnobPanel.this.lambda$showUnbindDialog$5((Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnbindDialog$5(Integer num) {
        if (((ActKnobPanelVM) this.mViewModel).controlDevice.getProductId().equals(ProductId.ID_SMART_SWITCH_SQB)) {
            RelateInfoUtils.showImageTipDialog(getString(R.string.sq_click_tip), R.mipmap.pic_click_tip, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanel$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                    ActKnobPanel.this.lambda$showUnbindDialog$4(imageTipDialog);
                }
            });
        } else {
            ((ActKnobPanelVM) this.mViewModel).unBindRelateInfo(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnbindDialog$4(ImageTipDialog imageTipDialog) {
        imageTipDialog.dismissDialog();
        ((ActKnobPanelVM) this.mViewModel).unBindRelateInfo(0);
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }
}