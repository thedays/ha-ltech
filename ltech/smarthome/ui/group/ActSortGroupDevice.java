package com.ltech.smarthome.ui.group;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseSortActivity;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSortGroupDevice extends BaseSortActivity<Role> {
    private long groupId;
    public List<Role> roleData = new ArrayList();
    public List<Long> selectRoleIds = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseSortActivity
    protected int itemLayout() {
        return R.layout.item_group_device_sort;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        setEditString(getString(R.string.save));
        setTitle(getString(R.string.sort_device));
        long[] longArrayExtra = getIntent().getLongArrayExtra(Constants.ROLE_ID_ARRAY);
        if (longArrayExtra != null) {
            for (long j : longArrayExtra) {
                this.selectRoleIds.add(Long.valueOf(j));
            }
        }
        if (this.selectRoleIds.size() > 0) {
            for (int i = 0; i < this.selectRoleIds.size(); i++) {
                this.roleData.add(getRoleByRoleId(this.selectRoleIds.get(i).longValue()));
            }
            setDataList(this.roleData);
        }
        this.groupId = getIntent().getLongExtra(Constants.GROUP_ID, -1L);
    }

    @Override // com.ltech.smarthome.base.BaseSortActivity
    protected List<Role> getItemList() {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseSortActivity
    public void convertView(BaseViewHolder helper, final Role role) {
        if (role instanceof Device) {
            Device device = (Device) role;
            helper.setText(R.id.tv_device_name, device.getDeviceName());
            if (!TextUtils.isEmpty(device.getFloorName())) {
                helper.setText(R.id.tv_place_info, device.getFloorName() + device.getRoomName());
            } else {
                helper.setText(R.id.tv_place_info, "");
            }
            helper.setBackgroundRes(R.id.iv_icon, ProductRepository.getProductIcon(device));
            ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
        } else {
            Group group = (Group) role;
            helper.setText(R.id.tv_device_name, group.getGroupName());
            if (!TextUtils.isEmpty(group.getFloorName())) {
                helper.setText(R.id.tv_place_info, group.getFloorName() + group.getRoomName());
            } else {
                helper.setText(R.id.tv_place_info, "");
            }
            helper.setBackgroundRes(R.id.iv_icon, ProductRepository.getProductIcon(group));
            ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
        }
        helper.getView(R.id.iv_location).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.group.ActSortGroupDevice.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                CmdAssistant.getSettingCmdAssistant(role, new int[0]).testDeviceLocation(ActSortGroupDevice.this.activity);
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseSortActivity
    protected void saveData() {
        final long[] jArr = new long[this.dataList.size()];
        for (int i = 0; i < this.dataList.size(); i++) {
            jArr[i] = ((Role) this.dataList.get(i)).getObjectId();
        }
        ((ObservableSubscribeProxy) Injection.net().updateGroupDeviceIndex(this.groupId, jArr).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSortGroupDevice$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSortGroupDevice.this.lambda$saveData$0(jArr, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveData$0(long[] jArr, Object obj) throws Exception {
        Intent intent = new Intent();
        intent.putExtra("data", jArr);
        setResult(5012, intent);
        SmartToast.showCenterShort(getString(R.string.save_success));
        finishActivity();
    }

    private Role getRoleByRoleId(long roleId) {
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(roleId);
        return deviceByDeviceId != null ? deviceByDeviceId : Injection.repo().group().getGroupByGroupId(roleId);
    }
}