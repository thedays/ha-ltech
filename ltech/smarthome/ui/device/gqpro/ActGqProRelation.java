package com.ltech.smarthome.ui.device.gqpro;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.BaseSelectDeviceGroupActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.databinding.ActSelect2Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.bean.SuperPanelInfo;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.response.super_panel.DetailSuperPanelResponse;
import com.ltech.smarthome.ui.device.gqpro.BleSyncHelper;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.ui.device.super_panel.ActSelectDeviceGroupForSuperPanel;
import com.ltech.smarthome.ui.device.super_panel.ActSelectSuperPanelAllScene;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActGqProRelation extends BaseSelectDeviceGroupActivity {
    private Device device;
    private long deviceId;
    private BleSyncHelper mBleSyncHelper;
    private List<Long> deviceIds = new ArrayList();
    private List<Long> groupIds = new ArrayList();
    private List<Long> sceneIds = new ArrayList();
    private List<Long> roleIds = new ArrayList();
    private List<SuperPanelInfo.SortInfo> sortRoleList = new ArrayList();
    private List<SuperPanelInfo.SortInfo> waitSortRoleList = new ArrayList();
    private List<Role> allRoleList = new ArrayList();
    public SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();

    @Override // com.ltech.smarthome.base.BaseList2Activity
    protected int itemLayout() {
        return R.layout.item_device_manage;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.ltech.smarthome.base.BaseSelectDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        char c2;
        Device device = this.device;
        int i = R.mipmap.pic_empty_device_mini;
        if (device != null) {
            String productId = device.getProductId();
            switch (productId.hashCode()) {
                case -1309274422:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO)) {
                        c2 = 7;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1308265372:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_12S)) {
                        c2 = 4;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1265646206:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                        c2 = 5;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1201890867:
                    if (productId.equals(ProductId.ID_SMART_PANEL_GQ_PRO)) {
                        c2 = 0;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -324427448:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_6S)) {
                        c2 = 3;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 294483828:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_4S)) {
                        c2 = 2;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 811752507:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI)) {
                        c2 = 1;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 956710656:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL)) {
                        c2 = 6;
                        break;
                    }
                    c2 = 65535;
                    break;
                default:
                    c2 = 65535;
                    break;
            }
            if (c2 == 0) {
                i = R.mipmap.empty_gq_bg_pic;
            } else if (c2 != 1 && c2 != 2) {
                i = c2 != 3 ? c2 != 4 ? c2 != 5 ? R.mipmap.pic_empty_device_superpanel : R.mipmap.pic_empty_device_g4max : R.mipmap.pic_empty_device_12s : R.mipmap.pic_empty_device_6s;
            }
        }
        return Gloading.from(new DefaultAdapter().emptyImageRes(i).emptyTryStringRes(R.string.add).emptyStringRes(R.string.app_str_gqpro_empty_device));
    }

    @Override // com.ltech.smarthome.base.BaseSelectDeviceGroupActivity, com.ltech.smarthome.base.BaseList2Activity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.app_str_sync_device) + "/" + getString(R.string.app_scene));
        setEditImage(R.mipmap.ic_edit_black);
        this.device = Injection.repo().device().getDeviceByDeviceId(getIntent().getLongExtra("device_id", -1L));
        ((ActSelect2Binding) this.mViewBinding).layoutSearch.setVisibility(8);
        ((ActSelect2Binding) this.mViewBinding).spinnerFloor.setVisibility(8);
        ((ActSelect2Binding) this.mViewBinding).spinnerRoom.setVisibility(8);
        this.canSelectRoom = false;
        ((ActSelect2Binding) this.mViewBinding).title.ivSearch.setVisibility(0);
        ((ActSelect2Binding) this.mViewBinding).title.ivSearch.setImageResource(R.mipmap.ic_sort);
        ((ActSelect2Binding) this.mViewBinding).title.ivSearch.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProRelation.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ActGqProRelation.this.getCurrentPlace().isOwner() || ActGqProRelation.this.getCurrentPlace().isManager()) {
                    NavUtils.destination(ActGqProSortRole.class).withLong("device_id", ActGqProRelation.this.deviceId).withString(Constants.ROLE_ID_ARRAY, GsonUtils.toJson(ActGqProRelation.this.waitSortRoleList)).withDefaultRequestCode().navigation(ActGqProRelation.this);
                } else {
                    ActGqProRelation.this.showNoPermissionDialogEvent.call();
                }
            }
        });
        initSyncBtn();
    }

    private void initSyncBtn() {
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.NEED_BLE_SYNC, false);
        ((ActSelect2Binding) this.mViewBinding).groupSync.setVisibility(booleanExtra ? 0 : 8);
        ((ActSelect2Binding) this.mViewBinding).viewSync.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProRelation.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ActGqProRelation.this.showSyncDialog();
            }
        });
        if (booleanExtra) {
            this.mBleSyncHelper = new BleSyncHelper(this.activity, this.device, getMainHandler());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSyncDialog() {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), this.activity.getString(R.string.app_str_gqpro_syning), this.activity.getString(R.string.app_str_need_gqpro_sync_immediately), this.activity.getString(R.string.app_str_gqpro_sync_now), this.activity.getString(R.string.cancel)).setOkButton(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProRelation.4
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                if (ActGqProRelation.this.dataList.isEmpty()) {
                    if (ActGqProRelation.this.mBleSyncHelper == null) {
                        return false;
                    }
                    ActGqProRelation.this.mBleSyncHelper.delete(5);
                    return false;
                }
                if (ActGqProRelation.this.mBleSyncHelper == null) {
                    return false;
                }
                ActGqProRelation.this.mBleSyncHelper.startSync();
                return false;
            }
        }).setCancelButton(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProRelation.3
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                if (!ActGqProRelation.this.isBlePermissionOk()) {
                    return false;
                }
                Injection.mesh().checkNearbyDevice(ActGqProRelation.this.activity);
                return false;
            }
        }).setCancelable(false).show();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showContent() {
        super.showContent();
        setEditImage(R.mipmap.ic_edit_black);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        super.showEmpty();
        setEditImage(R.mipmap.ic_edit_black);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onEmptyTry() {
        super.onEmptyTry();
        if (getCurrentPlace().isOwner() || getCurrentPlace().isManager()) {
            showBindDialog();
        } else {
            this.showNoPermissionDialogEvent.call();
        }
    }

    public void showBindDialog() {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.device));
        arrayList.add(getString(R.string.app_scene));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProRelation$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActGqProRelation.this.lambda$showBindDialog$0((Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBindDialog$0(Integer num) {
        int intValue = num.intValue();
        if (intValue != 0) {
            if (intValue != 1) {
                return;
            }
            long[] jArr = new long[this.sceneIds.size()];
            int size = this.sceneIds.size();
            for (int i = 0; i < size; i++) {
                jArr[i] = this.sceneIds.get(i).longValue();
            }
            NavUtils.destination(ActSelectSuperPanelAllScene.class).withInt(Constants.MAX, getIntent().getIntExtra(Constants.MAX, -1)).withLong(Constants.PLACE_ID, this.placeId).withLong("device_id", this.deviceId).withBoolean(Constants.IS_GQ, getIntent().getBooleanExtra(Constants.IS_GQ, false)).withLongArray(Constants.SCENE_ID_ARRAY, jArr).withDefaultRequestCode().navigation(this);
            return;
        }
        long[] jArr2 = new long[this.deviceIds.size()];
        int size2 = this.deviceIds.size();
        for (int i2 = 0; i2 < size2; i2++) {
            jArr2[i2] = this.deviceIds.get(i2).longValue();
        }
        long[] jArr3 = new long[this.groupIds.size()];
        int size3 = this.groupIds.size();
        for (int i3 = 0; i3 < size3; i3++) {
            jArr3[i3] = this.groupIds.get(i3).longValue();
        }
        NavUtils.destination(ActSelectDeviceGroupForSuperPanel.class).withLong(Constants.PLACE_ID, this.placeId).withLong("device_id", this.deviceId).withString(Constants.PRODUCT_ID, this.device.getProductId()).withLongArray(Constants.DEVICE_ID_ARRAY, jArr2).withLongArray(Constants.GROUP_ID_ARRAY, jArr3).withBoolean(Constants.IS_GQ, getIntent().getBooleanExtra(Constants.IS_GQ, false)).withInt(Constants.MAX, getIntent().getIntExtra(Constants.MAX, -1)).withDefaultRequestCode().navigation(this);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        onEmptyTry();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseList2Activity
    public void convertView(BaseViewHolder helper, Role item) {
        int productIcon;
        BaseViewHolder text = helper.setText(R.id.tv_device_name, item.getName());
        if (item instanceof Scene) {
            productIcon = SceneHelper.getSceneIcon(ActivityUtils.getTopActivity(), item.getIconPos());
        } else {
            productIcon = ProductRepository.getProductIcon(item);
        }
        text.setImageResource(R.id.iv_icon, productIcon).setVisible(R.id.iv_go, false).setText(R.id.tv_place_info, item.getFloorName() + " " + item.getRoomName());
        helper.setVisible(R.id.tv_dali, ProductRepository.isDaliLightGroup(item));
        ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseSelectDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
        this.device = Injection.repo().device().getDeviceByDeviceId(this.deviceId);
        bindData((SuperPanelInfo) GsonUtils.fromJson(getIntent().getStringExtra(Constants.SELECTED_LIST), new TypeToken<SuperPanelInfo>(this) { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProRelation.5
        }.getType()));
        refresh();
        this.showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProRelation$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActGqProRelation.this.lambda$startObserve$1((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r1) {
        showNoPermissionDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindData(SuperPanelInfo superPanelInfo) {
        Group relaySubGroup;
        Device relaySubDevice;
        this.allRoleList.clear();
        this.waitSortRoleList.clear();
        ArrayList<Role> arrayList = new ArrayList();
        if (superPanelInfo != null) {
            this.deviceIds.clear();
            this.groupIds.clear();
            this.sceneIds.clear();
            this.deviceIds.addAll(superPanelInfo.getDeviceIds());
            this.groupIds.addAll(superPanelInfo.getGroupIds());
            this.sceneIds.addAll(superPanelInfo.getSceneIds());
            this.sortRoleList.clear();
            this.roleIds.clear();
            this.sortRoleList.addAll(superPanelInfo.getSortRoleList());
            Collections.sort(this.sortRoleList, new Comparator() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProRelation$$ExternalSyntheticLambda1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ActGqProRelation.lambda$bindData$2((SuperPanelInfo.SortInfo) obj, (SuperPanelInfo.SortInfo) obj2);
                }
            });
            for (SuperPanelInfo.SortInfo sortInfo : this.sortRoleList) {
                Role roleByRoleId = getRoleByRoleId(sortInfo.getSortId(), sortInfo.getObjectType());
                if (roleByRoleId != null && filterRole(roleByRoleId)) {
                    this.waitSortRoleList.add(sortInfo);
                    this.roleIds.add(Long.valueOf(roleByRoleId.getObjectId()));
                    arrayList.add(roleByRoleId);
                }
            }
            if (this.mBleSyncHelper != null && !arrayList.isEmpty()) {
                ArrayList arrayList2 = new ArrayList();
                for (Role role : arrayList) {
                    int i = 0;
                    if (role instanceof Device) {
                        Device device = (Device) role;
                        if (superPanelInfo.getDevices() != null) {
                            Iterator<DetailSuperPanelResponse.DevicesBean.RowsBean> it = superPanelInfo.getDevices().iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    DetailSuperPanelResponse.DevicesBean.RowsBean next = it.next();
                                    if (next.getDeviceid() == device.getDeviceId()) {
                                        if (RelaySeparationHelper.isRelaySeparationPanelDevice(device)) {
                                            RelateInfoUtils.initRelateInfoList(device);
                                            RelateInfoUtils.relateInfoAssistant.getExtParam().setIsSeparated(1);
                                            while (i < RelateInfoUtils.relatedInfoList.size()) {
                                                RelateInfoItem relateInfoItem = RelateInfoUtils.relatedInfoList.get(i);
                                                if (relateInfoItem.relateInfo.switchIndex > 0 && (relaySubDevice = RelaySeparationHelper.getRelaySubDevice(device, relateInfoItem.relateInfo.switchIndex)) != null) {
                                                    relateInfoItem.relateInfo.switchName = relaySubDevice.getDeviceName();
                                                    RelateInfoUtils.relateInfoAssistant.setRelateInfo(i, relateInfoItem.relateInfo);
                                                }
                                                i++;
                                            }
                                            next.setParamext(RelateInfoUtils.relateInfoAssistant.getExtParamString());
                                        }
                                        arrayList2.add(new BleSyncHelper.BleSyncData(GsonUtils.toJson(next), 1, next.getDevicename()));
                                    }
                                }
                            }
                        }
                    }
                    if (role instanceof Group) {
                        Group group = (Group) role;
                        if (superPanelInfo.getGroups() != null) {
                            Iterator<DetailSuperPanelResponse.GroupsBean.RowsBean> it2 = superPanelInfo.getGroups().iterator();
                            while (true) {
                                if (it2.hasNext()) {
                                    DetailSuperPanelResponse.GroupsBean.RowsBean next2 = it2.next();
                                    if (next2.getGroupid() == group.getGroupId()) {
                                        if (RelaySeparationHelper.isRelaySeparationPanelDevice(group)) {
                                            RelateInfoUtils.initRelateInfoList(group);
                                            RelateInfoUtils.relateInfoAssistant.getExtParam().setIsSeparated(1);
                                            while (i < RelateInfoUtils.relatedInfoList.size()) {
                                                RelateInfoItem relateInfoItem2 = RelateInfoUtils.relatedInfoList.get(i);
                                                if (relateInfoItem2.relateInfo.switchIndex > 0 && (relaySubGroup = RelaySeparationHelper.getRelaySubGroup(group, relateInfoItem2.relateInfo.switchIndex)) != null) {
                                                    relateInfoItem2.relateInfo.switchName = relaySubGroup.getGroupName();
                                                    RelateInfoUtils.relateInfoAssistant.setRelateInfo(i, relateInfoItem2.relateInfo);
                                                }
                                                i++;
                                            }
                                            next2.setParamext(RelateInfoUtils.relateInfoAssistant.getExtParamString());
                                        }
                                        arrayList2.add(new BleSyncHelper.BleSyncData(GsonUtils.toJson(next2), 3, next2.getGroupname()));
                                    }
                                }
                            }
                        }
                    }
                    if (role instanceof Scene) {
                        Scene scene = (Scene) role;
                        if (superPanelInfo.getScenes() != null) {
                            Iterator<DetailSuperPanelResponse.ScenesBean.RowsBean> it3 = superPanelInfo.getScenes().iterator();
                            while (true) {
                                if (it3.hasNext()) {
                                    DetailSuperPanelResponse.ScenesBean.RowsBean next3 = it3.next();
                                    if (next3.getSceneid() == scene.getSceneId()) {
                                        arrayList2.add(new BleSyncHelper.BleSyncData(GsonUtils.toJson(next3), 2, next3.getScenename()));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                this.mBleSyncHelper.setDownloadData(arrayList2, 4);
            }
        }
        if (arrayList.isEmpty()) {
            showEmpty();
        } else {
            showContent();
        }
        setDataList(arrayList);
    }

    static /* synthetic */ int lambda$bindData$2(SuperPanelInfo.SortInfo sortInfo, SuperPanelInfo.SortInfo sortInfo2) {
        return sortInfo.getSort() > sortInfo2.getSort() ? 1 : -1;
    }

    private void refresh() {
        ((ObservableSubscribeProxy) Injection.repo().device().getGqProInfo(this.device.getDeviceId()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.activity, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<SuperPanelInfo>() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProRelation.6
            @Override // io.reactivex.functions.Consumer
            public void accept(SuperPanelInfo superPanelInfo) throws Exception {
                ActGqProRelation.this.bindData(superPanelInfo);
            }
        });
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

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    private boolean filterRole(Role role) {
        Device device;
        if (role instanceof Device) {
            device = (Device) role;
            String productId = device.getProductId();
            productId.hashCode();
            switch (productId) {
                case "122041818260301":
                case "122041818283501":
                case "122041818304701":
                case "121120911474901":
                case "3683369128495808":
                case "4249823578721536":
                case "3701704216101056":
                case "221042516351701":
                case "123072510445601":
                case "121061709483801":
                case "221030816330401":
                case "3701703750123712":
                case "121042516340801":
                case "121042516345401":
                    if (device.getParam() != null && device.getParam(BleParam.class) != null) {
                        return ((BleParam) device.getParam(BleParam.class)).getGroupId() == 0;
                    }
                    break;
            }
            if (RelaySeparationHelper.isRelaySeparationSub(device)) {
                if (device.getSubhide() == 1) {
                    return false;
                }
                Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getMacdeviceid());
                return deviceByDeviceId == null || deviceByDeviceId.getParam() == null || deviceByDeviceId.getParam(BleParam.class) == null || ((BleParam) deviceByDeviceId.getParam(BleParam.class)).getGroupId() == 0;
            }
            return true;
        }
        if (role instanceof Group) {
            Group group = (Group) role;
            return (RelaySeparationHelper.isRelaySeparationSub(group) && group.getSubhide() == 1) ? false : true;
        }
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            setResult(-1);
            refresh();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        BleSyncHelper bleSyncHelper = this.mBleSyncHelper;
        if (bleSyncHelper != null) {
            bleSyncHelper.disconnect();
        }
    }
}