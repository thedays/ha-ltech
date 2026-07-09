package com.ltech.smarthome.ui.device.remote_controller;

import android.graphics.Rect;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.databinding.ActKeySwitchBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActKeySwitch extends BaseControlActivity<ActKeySwitchBinding, ActKeySwitchVM> {
    private BaseQuickAdapter<RelateInfoItem, BaseViewHolder> infoAdapter;
    public SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();
    int[] picArray = {R.mipmap.pic_ps_num1, R.mipmap.pic_ps_num2, R.mipmap.pic_ps_num3, R.mipmap.pic_ps_num4};

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.layout_content;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_key_switch;
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
    protected void onRetry() {
        super.onRetry();
        if (((ActKeySwitchVM) this.mViewModel).request != null) {
            ((ActKeySwitchVM) this.mViewModel).request.retry();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActKeySwitchVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActKeySwitchVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActKeySwitchVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActKeySwitchVM) this.mViewModel).groupList.setValue(Injection.repo().group().getGroupListByPlaceId(((ActKeySwitchVM) this.mViewModel).placeId));
        ((ActKeySwitchVM) this.mViewModel).deviceList.setValue(Injection.repo().device().getDeviceListByPlaceId(((ActKeySwitchVM) this.mViewModel).placeId));
        ((ActKeySwitchVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActKeySwitch$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActKeySwitch.this.lambda$startObserve$0((Device) obj);
            }
        });
        this.showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActKeySwitch$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActKeySwitch.this.lambda$startObserve$1((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        ((ActKeySwitchVM) this.mViewModel).controlDevice = device;
        setTitle(device.getDeviceName());
        String productId = device.getProductId();
        productId.hashCode();
        switch (productId) {
            case "120042314364601":
                ((ActKeySwitchBinding) this.mViewBinding).ivDevicePic.setImageResource(R.mipmap.rc4_pic);
                break;
            case "120042314375001":
                ((ActKeySwitchBinding) this.mViewBinding).ivDevicePic.setImageResource(R.mipmap.ps1_pic);
                break;
            case "120042314380701":
                ((ActKeySwitchBinding) this.mViewBinding).ivDevicePic.setImageResource(R.mipmap.ps2_pic);
                break;
            case "120042314382401":
                ((ActKeySwitchBinding) this.mViewBinding).ivDevicePic.setImageResource(R.mipmap.pic_key_switch_3);
                break;
            case "120042314384101":
                ((ActKeySwitchBinding) this.mViewBinding).ivDevicePic.setImageResource(R.mipmap.ps4_pic);
                break;
        }
        initRelatedInfoView(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r1) {
        showNoPermissionDialog();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((ActKeySwitchVM) this.mViewModel).controlObject.setValue(Injection.repo().device().getDeviceById(((ActKeySwitchVM) this.mViewModel).controlId));
    }

    private void initRelatedInfoView(Device device) {
        ((ActKeySwitchVM) this.mViewModel).initRelateInfoList(device);
        BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = this.infoAdapter;
        if (baseQuickAdapter == null) {
            BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_relate_info, ((ActKeySwitchVM) this.mViewModel).relatedInfoList) { // from class: com.ltech.smarthome.ui.device.remote_controller.ActKeySwitch.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, RelateInfoItem item) {
                    helper.setImageResource(R.id.iv_icon, item.iconRes).setBackgroundRes(R.id.layout_item_bg, R.drawable.selector_bg_round_pressed_10);
                    if (item.infoName != null) {
                        helper.setText(R.id.tv_device_name, item.infoName).setText(R.id.tv_action_info, item.actionInfo);
                    } else {
                        helper.setText(R.id.tv_device_name, R.string.no_bind_object).setText(R.id.tv_action_info, "");
                    }
                    helper.setImageResource(R.id.iv_num, ActKeySwitch.this.picArray[item.index]);
                    ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
                }
            };
            this.infoAdapter = baseQuickAdapter2;
            baseQuickAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActKeySwitch$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    ActKeySwitch.this.lambda$initRelatedInfoView$2(baseQuickAdapter3, view, i);
                }
            });
            this.infoAdapter.bindToRecyclerView(((ActKeySwitchBinding) this.mViewBinding).rvRelatedInfo);
            ((ActKeySwitchBinding) this.mViewBinding).rvRelatedInfo.setLayoutManager(new LinearLayoutManager(this));
            ((ActKeySwitchBinding) this.mViewBinding).rvRelatedInfo.setHasFixedSize(true);
            ((ActKeySwitchBinding) this.mViewBinding).rvRelatedInfo.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.remote_controller.ActKeySwitch.2
                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(0, 0, 0, SizeUtils.dp2px(16.0f));
                }
            });
            ((DefaultItemAnimator) ((ActKeySwitchBinding) this.mViewBinding).rvRelatedInfo.getItemAnimator()).setSupportsChangeAnimations(false);
            return;
        }
        baseQuickAdapter.setNewData(((ActKeySwitchVM) this.mViewModel).relatedInfoList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRelatedInfoView$2(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (getCurrentPlace().isOwner() || getCurrentPlace().isManager()) {
            showRelatedDialog(i);
        } else {
            this.showNoPermissionDialogEvent.call();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavHelper.goSetting(((ActKeySwitchVM) this.mViewModel).controlObject.getValue());
    }

    private void showRelatedDialog(final int position) {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        if (this.infoAdapter.getData().get(position).infoName != null) {
            arrayList.add(getString(R.string.reset_relate));
            selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActKeySwitch$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActKeySwitch.this.lambda$showRelatedDialog$3(position, (Integer) obj);
                }
            }).setSelectList(arrayList);
        } else {
            arrayList.add(getString(R.string.bind_device));
            selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActKeySwitch$$ExternalSyntheticLambda3
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActKeySwitch.this.lambda$showRelatedDialog$4(position, (Integer) obj);
                }
            }).setSelectList(arrayList);
        }
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showRelatedDialog$3(int i, Integer num) {
        showUnbindDialog(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showRelatedDialog$4(int i, Integer num) {
        int intValue = num.intValue();
        if (intValue == 0) {
            NavUtils.destination(ActSelectBleLightDeviceAndGroup.class).withLong(Constants.PLACE_ID, ((ActKeySwitchVM) this.mViewModel).controlObject.getValue().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActKeySwitchVM) this.mViewModel).controlObject.getValue().getId()).withInt(Constants.RELATED_POSITION, i).withString(Constants.PRODUCT_ID, ((ActKeySwitchVM) this.mViewModel).productId).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) ((ActKeySwitchVM) this.mViewModel).controlDevice)).withInt(Constants.GROUP_RELATE, getIntent().getIntExtra(Constants.GROUP_RELATE, 1)).withDefaultRequestCode().navigation(this);
        } else {
            if (intValue != 1) {
                return;
            }
            NavUtils.destination(ActSelectPanel.class).withLong(Constants.PLACE_ID, ((ActKeySwitchVM) this.mViewModel).controlObject.getValue().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActKeySwitchVM) this.mViewModel).controlObject.getValue().getId()).withInt(Constants.RELATED_POSITION, i).withDefaultRequestCode().navigation(this);
        }
    }

    private void showUnbindDialog(final int position) {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.unbind_tip)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActKeySwitch$$ExternalSyntheticLambda1
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showUnbindDialog$5;
                lambda$showUnbindDialog$5 = ActKeySwitch.this.lambda$showUnbindDialog$5(position, baseDialog, view);
                return lambda$showUnbindDialog$5;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showUnbindDialog$5(int i, BaseDialog baseDialog, View view) {
        ((ActKeySwitchVM) this.mViewModel).unBindRelateInfo(i);
        return false;
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }
}