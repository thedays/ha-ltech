package com.ltech.smarthome.ui.device.super_panel;

import android.text.TextUtils;
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
import com.ltech.smarthome.net.request.device.PanelSortInfoRequest;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSortRole extends BaseSortActivity<Role> {
    private long deviceId;
    public List<Role> roleData = new ArrayList();
    public List<Long> selectRoleIds = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseSortActivity
    protected int itemLayout() {
        return R.layout.item_device_sort;
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
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
    }

    @Override // com.ltech.smarthome.base.BaseSortActivity
    protected List<Role> getItemList() {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseSortActivity
    public void convertView(BaseViewHolder helper, Role role) {
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
            return;
        }
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

    @Override // com.ltech.smarthome.base.BaseSortActivity
    protected void saveData() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < this.dataList.size(); i++) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(((Role) this.dataList.get(i)).getObjectId());
            if (deviceByDeviceId != null) {
                PanelSortInfoRequest.DeviceSortBean deviceSortBean = new PanelSortInfoRequest.DeviceSortBean();
                deviceSortBean.setDeviceid(deviceByDeviceId.getDeviceId());
                deviceSortBean.setSorting(i);
                arrayList.add(deviceSortBean);
            } else {
                PanelSortInfoRequest.GroupSortBean groupSortBean = new PanelSortInfoRequest.GroupSortBean();
                groupSortBean.setGroupid(((Role) this.dataList.get(i)).getObjectId());
                groupSortBean.setSorting(i);
                arrayList2.add(groupSortBean);
            }
        }
        ((ObservableSubscribeProxy) Injection.net().panelSortRole(this.deviceId, arrayList, arrayList2).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSortRole$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSortRole.this.lambda$saveData$0((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSortRole$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSortRole.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSortRole$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSortRole.this.lambda$saveData$1(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveData$0(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveData$1(Object obj) throws Exception {
        SmartToast.showShort(R.string.save_success);
        finishActivity();
    }

    private Role getRoleByRoleId(long roleId) {
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(roleId);
        return deviceByDeviceId != null ? deviceByDeviceId : Injection.repo().group().getGroupByGroupId(roleId);
    }
}