package com.ltech.smarthome.ui.scene;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import androidx.databinding.DataBindingUtil;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
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
import com.ltech.smarthome.view.dialog.SelectListDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectDaliLightGroupForAction extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> implements ISelectAction {
    private long deviceId;
    private int selectType;
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
    public /* synthetic */ void lambda$initActionView$3(BaseNormalActivity baseNormalActivity) {
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

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void initData() {
        ((ActSelect3Binding) this.mViewBinding).headView.setVisibility(this.allRoleData.size() > 0 ? 0 : 8);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void convertView(BaseViewHolder helper, final Role item) {
        helper.setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item));
        helper.setGone(R.id.tv_place_info, !TextUtils.isEmpty(r0)).setText(R.id.tv_place_info, ((FtDeviceGroupVM) this.mViewModel).getPlaceInfo(item.getFloorId(), item.getRoomId())).setText(R.id.tv_device_name, item.getName()).setText(R.id.tv_num, String.valueOf(DaliProHelper.getDaliAddress(item)));
        helper.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectDaliLightGroupForAction$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActSelectDaliLightGroupForAction.this.lambda$convertView$0(item, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertView$0(Role role, View view) {
        if (SceneHelper.instance().bindingType == 2) {
            NavUtils.Builder goSelectAction = SceneHelper.instance().goSelectAction(role, true);
            if (role instanceof Device) {
                goSelectAction.withInt(Constants.ADDRESS, ((Device) role).getUnicastAddress());
            } else {
                goSelectAction.withInt(Constants.ADDRESS, ((Group) role).getGroupAddress());
            }
            goSelectAction.withLong(Constants.CONTROL_ID, role.getId()).withDefaultRequestCode().navigation(this);
            return;
        }
        if (SceneHelper.instance().bindingType == 3) {
            SceneHelper.instance().controlObject = role;
            if ((role instanceof Group) && DaliProHelper.isMultiTypeGroup(role)) {
                showKnobBindDialog((Group) role);
                return;
            }
            Intent intent = new Intent();
            intent.putExtra(Constants.LIGHT_TYPE, DaliProHelper.convertDaliType(role));
            setResult(3001, intent);
            finishActivity();
            return;
        }
        NavUtils.Builder goSelectLocalAction = SceneHelper.instance().goSelectLocalAction(role);
        if (goSelectLocalAction != null) {
            goSelectLocalAction.withLong(Constants.CONTROL_ID, role.getId()).withDefaultRequestCode().navigation(this);
        }
    }

    private void showKnobBindDialog(Group group) {
        final ArrayList arrayList = new ArrayList();
        if (DaliProHelper.isSupportDim(group)) {
            arrayList.add("DIM");
        }
        if (DaliProHelper.isSupportCt(group)) {
            arrayList.add("CT");
        }
        if (DaliProHelper.isSupportColor(group)) {
            arrayList.add("RGB");
        }
        SelectListDialog.asDefault(true).setTitle(getString(R.string.please_choose)).setConfirmString(getString(R.string.confirm)).setCancelString(getString(R.string.cancel)).setSelectPosition(0).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectDaliLightGroupForAction$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectDaliLightGroupForAction.this.lambda$showKnobBindDialog$1(arrayList, (Integer) obj);
            }
        }).setSelectList(arrayList).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showKnobBindDialog$1(List list, Integer num) {
        if ("DIM".equals(list.get(num.intValue()))) {
            this.selectType = 1;
        } else if ("CT".equals(list.get(num.intValue()))) {
            this.selectType = 2;
        } else {
            this.selectType = 3;
        }
        Intent intent = new Intent();
        intent.putExtra(Constants.LIGHT_TYPE, this.selectType);
        setResult(3001, intent);
        finishActivity();
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        return this.showType == 2 && device.getMacdeviceid() == this.deviceId;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterGroup(Group group) {
        return this.showType == 1 && group.getMacdeviceid() == this.deviceId;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            lambda$initActionView$3(this);
        }
    }
}