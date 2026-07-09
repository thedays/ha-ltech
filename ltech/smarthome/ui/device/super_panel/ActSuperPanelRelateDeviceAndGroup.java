package com.ltech.smarthome.ui.device.super_panel;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.BaseSelectDeviceGroupActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.databinding.ActSelect2Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.ResourceEmptyLiveData;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.SuperPanelInfo;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.response.super_panel.DetailSuperPanelResponse;
import com.ltech.smarthome.ui.device.gqpro.BleSyncHelper;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class ActSuperPanelRelateDeviceAndGroup extends BaseSelectDeviceGroupActivity {
    private Device device;
    private long deviceId;
    private BleSyncHelper mBleSyncHelper;
    private List<Long> deviceIds = new ArrayList();
    private List<Long> groupIds = new ArrayList();
    private List<Long> roleIds = new ArrayList();
    private List<SuperPanelInfo.SortInfo> sortRoleList = new ArrayList();
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
        return Gloading.from(new DefaultAdapter().emptyImageRes(i).emptyTryStringRes(R.string.add).emptyStringRes(R.string.super_panel_empty_device));
    }

    @Override // com.ltech.smarthome.base.BaseSelectDeviceGroupActivity, com.ltech.smarthome.base.BaseList2Activity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.display_device));
        this.device = Injection.repo().device().getDeviceByDeviceId(getIntent().getLongExtra("device_id", -1L));
        ((ActSelect2Binding) this.mViewBinding).layoutSearch.setVisibility(8);
        ((ActSelect2Binding) this.mViewBinding).spinnerFloor.setVisibility(8);
        ((ActSelect2Binding) this.mViewBinding).spinnerRoom.setVisibility(8);
        this.canSelectRoom = false;
        ((ActSelect2Binding) this.mViewBinding).title.ivSearch.setVisibility(0);
        ((ActSelect2Binding) this.mViewBinding).title.ivSearch.setImageResource(R.mipmap.ic_sort);
        ((ActSelect2Binding) this.mViewBinding).title.ivSearch.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelRelateDeviceAndGroup.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ActSuperPanelRelateDeviceAndGroup.this.getCurrentPlace().isOwner() || ActSuperPanelRelateDeviceAndGroup.this.getCurrentPlace().isManager()) {
                    long[] jArr = new long[ActSuperPanelRelateDeviceAndGroup.this.roleIds.size()];
                    int size = ActSuperPanelRelateDeviceAndGroup.this.roleIds.size();
                    for (int i = 0; i < size; i++) {
                        jArr[i] = ((Long) ActSuperPanelRelateDeviceAndGroup.this.roleIds.get(i)).longValue();
                    }
                    NavUtils.destination(ActSortRole.class).withLong("device_id", ActSuperPanelRelateDeviceAndGroup.this.deviceId).withLongArray(Constants.ROLE_ID_ARRAY, jArr).navigation(ActSuperPanelRelateDeviceAndGroup.this);
                    return;
                }
                ActSuperPanelRelateDeviceAndGroup.this.showNoPermissionDialogEvent.call();
            }
        });
        initSyncBtn();
    }

    private void initSyncBtn() {
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.NEED_BLE_SYNC, false);
        ((ActSelect2Binding) this.mViewBinding).groupSync.setVisibility(booleanExtra ? 0 : 8);
        ((ActSelect2Binding) this.mViewBinding).viewSync.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelRelateDeviceAndGroup.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ActSuperPanelRelateDeviceAndGroup.this.showSyncDialog();
            }
        });
        if (booleanExtra) {
            this.mBleSyncHelper = new BleSyncHelper(this.activity, this.device, getMainHandler());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSyncDialog() {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), this.activity.getString(R.string.app_str_gqpro_syning), this.activity.getString(R.string.app_str_need_gqpro_sync_immediately), this.activity.getString(R.string.app_str_gqpro_sync_now), this.activity.getString(R.string.cancel)).setOkButton(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelRelateDeviceAndGroup.4
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                if (ActSuperPanelRelateDeviceAndGroup.this.dataList.isEmpty()) {
                    if (ActSuperPanelRelateDeviceAndGroup.this.mBleSyncHelper == null) {
                        return false;
                    }
                    ActSuperPanelRelateDeviceAndGroup.this.mBleSyncHelper.delete(5);
                    return false;
                }
                if (ActSuperPanelRelateDeviceAndGroup.this.mBleSyncHelper == null) {
                    return false;
                }
                ActSuperPanelRelateDeviceAndGroup.this.mBleSyncHelper.startSync();
                return false;
            }
        }).setCancelButton(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelRelateDeviceAndGroup.3
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                if (!ActSuperPanelRelateDeviceAndGroup.this.isBlePermissionOk()) {
                    return false;
                }
                Injection.mesh().checkNearbyDevice(ActSuperPanelRelateDeviceAndGroup.this.activity);
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
            long[] jArr = new long[this.deviceIds.size()];
            int size = this.deviceIds.size();
            for (int i = 0; i < size; i++) {
                jArr[i] = this.deviceIds.get(i).longValue();
            }
            long[] jArr2 = new long[this.groupIds.size()];
            int size2 = this.groupIds.size();
            for (int i2 = 0; i2 < size2; i2++) {
                jArr2[i2] = this.groupIds.get(i2).longValue();
            }
            NavUtils.destination(ActSelectDeviceGroupForSuperPanel.class).withLong(Constants.PLACE_ID, this.placeId).withLong("device_id", this.deviceId).withString(Constants.PRODUCT_ID, this.device.getProductId()).withLongArray(Constants.DEVICE_ID_ARRAY, jArr).withLongArray(Constants.GROUP_ID_ARRAY, jArr2).withInt(Constants.MAX, getIntent().getIntExtra(Constants.MAX, -1)).withDefaultRequestCode().navigation(this);
            return;
        }
        this.showNoPermissionDialogEvent.call();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        onEmptyTry();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseList2Activity
    public void convertView(BaseViewHolder helper, Role item) {
        helper.setText(R.id.tv_device_name, item.getName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item)).setVisible(R.id.iv_go, false).setText(R.id.tv_place_info, getPlaceInfo(item.getFloorId(), item.getRoomId())).setGone(R.id.tv_virtual, item.isVirtual()).setVisible(R.id.tv_dali, ProductRepository.isDaliLightGroup(item));
        ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseSelectDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(this.deviceId);
        this.device = deviceByDeviceId;
        if (deviceByDeviceId != null && (deviceByDeviceId.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_6S) || this.device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_12S))) {
            ((ActSelect2Binding) this.mViewBinding).title.ivSearch.setVisibility(8);
        }
        handleData(Transformations.switchMap(this.roleList, new Function1() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelRelateDeviceAndGroup$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$0;
                lambda$startObserve$0 = ActSuperPanelRelateDeviceAndGroup.this.lambda$startObserve$0((Resource) obj);
                return lambda$startObserve$0;
            }
        }), new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelRelateDeviceAndGroup$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSuperPanelRelateDeviceAndGroup.this.lambda$startObserve$2((List) obj);
            }
        });
        this.showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelRelateDeviceAndGroup$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanelRelateDeviceAndGroup.this.lambda$startObserve$3((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$startObserve$0(Resource resource) {
        if (resource == null) {
            return ResourceEmptyLiveData.create();
        }
        this.allRoleList.clear();
        if (resource.getData() != null) {
            this.allRoleList.addAll((Collection) resource.getData());
        }
        Injection.limiter().reset(Injection.keyCreator().superPanelInfoKey(this.deviceId));
        return Injection.repo().device().getSuperPanelInfo(this, this.deviceId).data();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(List list) {
        ArrayList<Role> arrayList = new ArrayList();
        if (list != null && !list.isEmpty()) {
            SuperPanelInfo superPanelInfo = (SuperPanelInfo) list.get(0);
            this.deviceIds.clear();
            this.groupIds.clear();
            this.deviceIds.addAll(superPanelInfo.getDeviceIds());
            this.groupIds.addAll(superPanelInfo.getGroupIds());
            this.sortRoleList.clear();
            this.roleIds.clear();
            this.sortRoleList.addAll(superPanelInfo.getSortRoleList());
            Collections.sort(this.sortRoleList, new Comparator() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelRelateDeviceAndGroup$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ActSuperPanelRelateDeviceAndGroup.lambda$startObserve$1((SuperPanelInfo.SortInfo) obj, (SuperPanelInfo.SortInfo) obj2);
                }
            });
            Iterator<SuperPanelInfo.SortInfo> it = this.sortRoleList.iterator();
            while (it.hasNext()) {
                Role roleByRoleId = getRoleByRoleId(it.next().getSortId());
                if (filterRole(roleByRoleId)) {
                    this.roleIds.add(Long.valueOf(roleByRoleId.getObjectId()));
                    arrayList.add(roleByRoleId);
                }
            }
            if (this.mBleSyncHelper != null && !arrayList.isEmpty()) {
                ArrayList arrayList2 = new ArrayList();
                for (Role role : arrayList) {
                    if (role instanceof Device) {
                        Device device = (Device) role;
                        if (superPanelInfo.getDevices() != null) {
                            Iterator<DetailSuperPanelResponse.DevicesBean.RowsBean> it2 = superPanelInfo.getDevices().iterator();
                            while (true) {
                                if (it2.hasNext()) {
                                    DetailSuperPanelResponse.DevicesBean.RowsBean next = it2.next();
                                    if (next.getDeviceid() == device.getDeviceId()) {
                                        arrayList2.add(new BleSyncHelper.BleSyncData(GsonUtils.toJson(next), 1, next.getDevicename()));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (role instanceof Group) {
                        Group group = (Group) role;
                        if (superPanelInfo.getGroups() != null) {
                            Iterator<DetailSuperPanelResponse.GroupsBean.RowsBean> it3 = superPanelInfo.getGroups().iterator();
                            while (true) {
                                if (it3.hasNext()) {
                                    DetailSuperPanelResponse.GroupsBean.RowsBean next2 = it3.next();
                                    if (next2.getGroupid() == group.getGroupId()) {
                                        arrayList2.add(new BleSyncHelper.BleSyncData(GsonUtils.toJson(next2), 3, next2.getGroupname()));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                this.mBleSyncHelper.setDownloadData(arrayList2, 1);
            }
        }
        if (arrayList.isEmpty()) {
            showEmpty();
        }
        setDataList(arrayList);
    }

    static /* synthetic */ int lambda$startObserve$1(SuperPanelInfo.SortInfo sortInfo, SuperPanelInfo.SortInfo sortInfo2) {
        return sortInfo.getSort() > sortInfo2.getSort() ? 1 : -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r1) {
        showNoPermissionDialog();
    }

    private Role getRoleByRoleId(long roleId) {
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(roleId);
        return deviceByDeviceId != null ? deviceByDeviceId : Injection.repo().group().getGroupByGroupId(roleId);
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
        }
    }
}