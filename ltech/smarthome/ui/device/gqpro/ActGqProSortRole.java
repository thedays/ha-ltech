package com.ltech.smarthome.ui.device.gqpro;

import android.text.TextUtils;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseSortActivity;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.bean.SuperPanelInfo;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.PanelSortInfoRequest;
import com.ltech.smarthome.ui.scene.SceneHelper;
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
public class ActGqProSortRole extends BaseSortActivity<Role> {
    private long deviceId;
    public List<Role> roleData = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseSortActivity
    protected int itemLayout() {
        return R.layout.item_device_sort;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        setEditString(getString(R.string.save));
        setTitle(getString(R.string.sort_device));
        for (SuperPanelInfo.SortInfo sortInfo : (List) GsonUtils.fromJson(getIntent().getStringExtra(Constants.ROLE_ID_ARRAY), new TypeToken<List<SuperPanelInfo.SortInfo>>(this) { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProSortRole.1
        }.getType())) {
            Role roleByRoleId = getRoleByRoleId(sortInfo.getSortId(), sortInfo.getObjectType());
            if (roleByRoleId != null) {
                this.roleData.add(roleByRoleId);
            }
        }
        List<Role> list = this.roleData;
        if (list != null) {
            setDataList(list);
        }
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
    }

    private Role getRoleByRoleId(long roleId, int objectType) {
        if (objectType == 1) {
            return Injection.repo().device().getDeviceByDeviceId(roleId);
        }
        if (objectType == 2) {
            return Injection.repo().group().getGroupByGroupId(roleId);
        }
        if (objectType == 3) {
            return Injection.repo().scene().getSceneBySceneId(roleId);
        }
        return null;
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
        if (role instanceof Group) {
            Group group = (Group) role;
            helper.setText(R.id.tv_device_name, group.getGroupName());
            if (!TextUtils.isEmpty(group.getFloorName())) {
                helper.setText(R.id.tv_place_info, group.getFloorName() + group.getRoomName());
            } else {
                helper.setText(R.id.tv_place_info, "");
            }
            helper.setBackgroundRes(R.id.iv_icon, ProductRepository.getProductIcon(group));
            ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
            return;
        }
        if (role instanceof Scene) {
            Scene scene = (Scene) role;
            helper.setText(R.id.tv_device_name, scene.getName());
            if (!TextUtils.isEmpty(scene.getFloorName())) {
                helper.setText(R.id.tv_place_info, scene.getFloorName() + scene.getRoomName());
            } else {
                helper.setText(R.id.tv_place_info, "");
            }
            helper.setBackgroundRes(R.id.iv_icon, SceneHelper.getSceneIcon(ActivityUtils.getTopActivity(), scene.getIconPos()));
            ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
        }
    }

    @Override // com.ltech.smarthome.base.BaseSortActivity
    protected void saveData() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (int i = 0; i < this.dataList.size(); i++) {
            Role role = (Role) this.dataList.get(i);
            if (role instanceof Device) {
                PanelSortInfoRequest.DeviceSortBean deviceSortBean = new PanelSortInfoRequest.DeviceSortBean();
                deviceSortBean.setDeviceid(role.getObjectId());
                deviceSortBean.setSorting(i);
                arrayList.add(deviceSortBean);
            } else if (role instanceof Group) {
                PanelSortInfoRequest.GroupSortBean groupSortBean = new PanelSortInfoRequest.GroupSortBean();
                groupSortBean.setGroupid(role.getObjectId());
                groupSortBean.setSorting(i);
                arrayList2.add(groupSortBean);
            } else if (role instanceof Scene) {
                PanelSortInfoRequest.SceneSortBean sceneSortBean = new PanelSortInfoRequest.SceneSortBean();
                sceneSortBean.setSceneid(role.getObjectId());
                sceneSortBean.setSorting(i);
                arrayList3.add(sceneSortBean);
            }
        }
        ((ObservableSubscribeProxy) Injection.net().panelSortRole(this.deviceId, arrayList, arrayList2, arrayList3).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProSortRole$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGqProSortRole.this.lambda$saveData$0((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProSortRole$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActGqProSortRole.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProSortRole$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGqProSortRole.this.lambda$saveData$1(obj);
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
        setResult(-1);
        finishActivity();
    }
}