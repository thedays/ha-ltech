package com.ltech.smarthome.ui.device.smartpanel;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import androidx.databinding.DataBindingUtil;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.databinding.ViewCgdLightTitleDaliBinding;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.Utils;

/* loaded from: classes4.dex */
public class ActSmartPanelSelectDaliLightGroup extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> implements ISelectAction {
    private long deviceId;
    private int showType = 1;

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDaliSubDeviceGroup(Role role) {
        return true;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected int itemLayout() {
        return R.layout.item_dali_manage;
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

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        this.showType = getIntent().getIntExtra(Constants.SHOW_TYPE, 1);
        this.deviceId = getIntent().getLongExtra("device_id", 0L);
        setTitle(getString(this.showType == 1 ? R.string.select_dali_group : R.string.select_dali_add));
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.setVisibility(0);
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.getLayoutParams().width = Utils.dip2px(this, 30.0f);
        ((ActSelect3Binding) this.mViewBinding).layoutSort.setVisibility(8);
        this.listType = 5;
        this.selectSortType = 11;
        ((ViewCgdLightTitleDaliBinding) DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.view_cgd_light_title_dali, ((ActSelect3Binding) this.mViewBinding).headView, true)).tvTip.setText(getString(this.showType == 1 ? R.string.group_number : R.string.address_number));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$0() {
        ((ActSelect3Binding) this.mViewBinding).headView.setVisibility(this.allRoleData.size() > 0 ? 0 : 8);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void initData() {
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectDaliLightGroup$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ActSmartPanelSelectDaliLightGroup.this.lambda$initData$0();
            }
        }, 100L);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void convertView(BaseViewHolder helper, final Role item) {
        helper.setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item));
        helper.setGone(R.id.tv_place_info, !TextUtils.isEmpty(r0)).setText(R.id.tv_place_info, ((FtDeviceGroupVM) this.mViewModel).getPlaceInfo(item.getFloorId(), item.getRoomId())).setText(R.id.tv_device_name, item.getName()).setText(R.id.tv_num, String.valueOf(DaliProHelper.getDaliAddress(item)));
        helper.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectDaliLightGroup$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActSmartPanelSelectDaliLightGroup.this.lambda$convertView$1(item, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertView$1(Role role, View view) {
        if (getIntent().getBooleanExtra(Constants.IS_GQ, false)) {
            Intent intent = new Intent();
            intent.putExtra(Constants.RELATE_ID, role.getId());
            intent.putExtra(Constants.GROUP_RELATE, role instanceof Device ? 1 : 2);
            setResult(3001, intent);
            finishActivity();
            return;
        }
        NavUtils.destination(ActSmartPanelSelectRelatedActionNew.class).withLong("device_id", this.deviceId).withLong(Constants.RELATE_ID, role.getId()).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withBoolean(Constants.GROUP_CONTROL, getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false)).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withInt(Constants.GROUP_RELATE, role instanceof Device ? 1 : 2).withLong(Constants.PLACE_ID, this.placeId).withInt(Constants.SHOW_TYPE, 1).withDefaultRequestCode().navigation(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3001) {
            lambda$edit$1(this);
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        return this.showType == 2 && device.getMacdeviceid() == this.deviceId;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterGroup(Group group) {
        return this.showType == 1 && group.getMacdeviceid() == this.deviceId;
    }
}