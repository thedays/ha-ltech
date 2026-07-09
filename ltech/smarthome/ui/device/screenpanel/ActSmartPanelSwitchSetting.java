package com.ltech.smarthome.ui.device.screenpanel;

import android.graphics.Rect;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSmartPanelSwitchSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.view.SwitchButton;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSmartPanelSwitchSetting extends VMActivity<ActSmartPanelSwitchSettingBinding, ActSmartPanelSwitchSettingVM> {
    private BaseQuickAdapter<Device, BaseViewHolder> mAdapter;
    private BaseQuickAdapter<Group, BaseViewHolder> mGroupAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_smart_panel_switch_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.app_str_switch_setting));
        ((ActSmartPanelSwitchSettingVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActSmartPanelSwitchSettingVM) this.mViewModel).deviceId = getIntent().getLongExtra("device_id", -1L);
        ((ActSmartPanelSwitchSettingVM) this.mViewModel).roomPickHelper.startObserve(this, ((ActSmartPanelSwitchSettingVM) this.mViewModel).placeId, -1L);
        ((ActSmartPanelSwitchSettingBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(this));
        ((ActSmartPanelSwitchSettingBinding) this.mViewBinding).rv.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSetting.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ConvertUtils.dp2px(15.0f);
            }
        });
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        int i = R.layout.item_smart_panel_switch;
        if (booleanExtra) {
            RecyclerView recyclerView = ((ActSmartPanelSwitchSettingBinding) this.mViewBinding).rv;
            BaseQuickAdapter<Group, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Group, BaseViewHolder>(i) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSetting.2
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder baseViewHolder, final Group group) {
                    baseViewHolder.setImageResource(R.id.iv, ProductRepository.getProductIcon(group));
                    baseViewHolder.setText(R.id.tv_sub, group.getFloorName() + " " + group.getRoomName());
                    baseViewHolder.addOnClickListener(R.id.tv_setting);
                    baseViewHolder.setText(R.id.tv, group.getName());
                    SwitchButton switchButton = (SwitchButton) baseViewHolder.getView(R.id.sb);
                    switchButton.setCheckedNotByUser(group.getSubhide() == 0);
                    switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSetting.2.1
                        @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                        public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                            ((ActSmartPanelSwitchSettingVM) ActSmartPanelSwitchSetting.this.mViewModel).changeHide(group, isChecked);
                        }
                    });
                }
            };
            this.mGroupAdapter = baseQuickAdapter;
            recyclerView.setAdapter(baseQuickAdapter);
            this.mGroupAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSetting.3
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
                public void onItemChildClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                    Group group = (Group) ActSmartPanelSwitchSetting.this.mGroupAdapter.getItem(i2);
                    if (group != null) {
                        ((ActSmartPanelSwitchSettingVM) ActSmartPanelSwitchSetting.this.mViewModel).roomPickHelper.setCurrentFloorIdPos(group.getFloorId());
                        ((ActSmartPanelSwitchSettingVM) ActSmartPanelSwitchSetting.this.mViewModel).roomPickHelper.setCurrentRoomIdPos(group.getFloorId(), group.getRoomId());
                        ((ActSmartPanelSwitchSettingVM) ActSmartPanelSwitchSetting.this.mViewModel).showEditDialog(group);
                    }
                }
            });
            return;
        }
        RecyclerView recyclerView2 = ((ActSmartPanelSwitchSettingBinding) this.mViewBinding).rv;
        BaseQuickAdapter<Device, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<Device, BaseViewHolder>(i) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSetting.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, final Device device) {
                baseViewHolder.setImageResource(R.id.iv, ProductRepository.getProductIcon(device));
                baseViewHolder.setText(R.id.tv_sub, device.getFloorName() + " " + device.getRoomName());
                baseViewHolder.addOnClickListener(R.id.tv_setting);
                baseViewHolder.setText(R.id.tv, device.getName());
                SwitchButton switchButton = (SwitchButton) baseViewHolder.getView(R.id.sb);
                switchButton.setCheckedNotByUser(device.getSubhide() == 0);
                switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSetting.4.1
                    @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                        ((ActSmartPanelSwitchSettingVM) ActSmartPanelSwitchSetting.this.mViewModel).changeHide(device, isChecked);
                    }
                });
            }
        };
        this.mAdapter = baseQuickAdapter2;
        recyclerView2.setAdapter(baseQuickAdapter2);
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSetting.5
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter3, View view, int i2) {
                Device device = (Device) ActSmartPanelSwitchSetting.this.mAdapter.getItem(i2);
                if (device != null) {
                    ((ActSmartPanelSwitchSettingVM) ActSmartPanelSwitchSetting.this.mViewModel).roomPickHelper.setCurrentFloorIdPos(device.getFloorId());
                    ((ActSmartPanelSwitchSettingVM) ActSmartPanelSwitchSetting.this.mViewModel).roomPickHelper.setCurrentRoomIdPos(device.getFloorId(), device.getRoomId());
                    ((ActSmartPanelSwitchSettingVM) ActSmartPanelSwitchSetting.this.mViewModel).showEditDialog(device);
                }
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActSmartPanelSwitchSettingVM) this.mViewModel).refreshRoleItem.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSetting$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSmartPanelSwitchSetting.this.lambda$startObserve$0((Void) obj);
            }
        });
        ((ActSmartPanelSwitchSettingVM) this.mViewModel).refreshRoleItem.call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Void r5) {
        if (getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false)) {
            ((ActSmartPanelSwitchSettingVM) this.mViewModel).controlObject = Injection.repo().group().getGroupByGroupId(((ActSmartPanelSwitchSettingVM) this.mViewModel).deviceId);
            List<Group> subGroup = Injection.repo().group().getSubGroup(((ActSmartPanelSwitchSettingVM) this.mViewModel).placeId, ((ActSmartPanelSwitchSettingVM) this.mViewModel).deviceId);
            Collections.sort(subGroup, new Comparator<Group>(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSetting.6
                @Override // java.util.Comparator
                public int compare(Group o1, Group o2) {
                    return o1.getSubkey() - o2.getSubkey();
                }
            });
            this.mGroupAdapter.setNewData(subGroup);
            return;
        }
        ((ActSmartPanelSwitchSettingVM) this.mViewModel).controlObject = Injection.repo().device().getDeviceByDeviceId(((ActSmartPanelSwitchSettingVM) this.mViewModel).deviceId);
        List<Device> subDevice = Injection.repo().device().getSubDevice(((ActSmartPanelSwitchSettingVM) this.mViewModel).placeId, ((ActSmartPanelSwitchSettingVM) this.mViewModel).deviceId);
        Collections.sort(subDevice, new Comparator<Device>(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSetting.7
            @Override // java.util.Comparator
            public int compare(Device o1, Device o2) {
                return o1.getWifiMac().compareTo(o2.getWifiMac());
            }
        });
        this.mAdapter.setNewData(subDevice);
    }
}