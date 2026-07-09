package com.ltech.smarthome.ui.device.screenpanel;

import android.graphics.Rect;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.databinding.FtPageScreenPanelSwitchBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.smart.product_agreement.bean.SwitchPanelState;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes4.dex */
public class FtPageScreenPanelSwitch extends VMFragment<FtPageScreenPanelSwitchBinding, ActSmartPanelVM> {
    private BaseQuickAdapter<Device, BaseViewHolder> deviceAdapter;
    private BaseQuickAdapter<Group, BaseViewHolder> groupAdapter;
    private boolean[] selectArray;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_page_screen_panel_switch;
    }

    @Override // com.ltech.smarthome.base.VMFragment
    protected boolean shareVM() {
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        Object value = ((ActSmartPanelVM) this.mViewModel).controlObject.getValue();
        if (value instanceof Device) {
            List<Device> subDevice = Injection.repo().device().getSubDevice(((ActSmartPanelVM) this.mViewModel).placeId, ((Device) value).getDeviceId());
            Collections.sort(subDevice, new Comparator<Device>(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelSwitch.1
                @Override // java.util.Comparator
                public int compare(Device o1, Device o2) {
                    return o1.getWifiMac().compareTo(o2.getWifiMac());
                }
            });
            boolean[] zArr = new boolean[subDevice.size()];
            this.selectArray = zArr;
            if (zArr.length > 0) {
                initDeviceSwitch();
                this.deviceAdapter.replaceData(subDevice);
            }
        } else {
            Object value2 = ((ActSmartPanelVM) this.mViewModel).controlObject.getValue();
            if (value2 instanceof Group) {
                Group group = (Group) value2;
                List<Group> subGroup = Injection.repo().group().getSubGroup(group.getPlaceId(), group.getGroupId());
                Collections.sort(subGroup, new Comparator<Group>(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelSwitch.2
                    @Override // java.util.Comparator
                    public int compare(Group o1, Group o2) {
                        return o1.getSubkey() - o2.getSubkey();
                    }
                });
                boolean[] zArr2 = new boolean[subGroup.size()];
                this.selectArray = zArr2;
                if (zArr2.length > 0) {
                    initGroupSwitch();
                    this.groupAdapter.replaceData(subGroup);
                }
            }
        }
        ((FtPageScreenPanelSwitchBinding) this.mViewBinding).rv.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelSwitch.3
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.right = ConvertUtils.dp2px(4.0f);
                outRect.left = ConvertUtils.dp2px(4.0f);
            }
        });
        int lightColorType = ProductRepository.getLightColorType(((ActSmartPanelVM) this.mViewModel).controlObject.getValue());
        if (lightColorType == 8) {
            if (((ActSmartPanelVM) this.mViewModel).isPro) {
                ((FtPageScreenPanelSwitchBinding) this.mViewBinding).tvTipMessage.setText(R.string.app_str_smart_panel_tip_msg_new_1_pro);
                return;
            } else {
                ((FtPageScreenPanelSwitchBinding) this.mViewBinding).tvTipMessage.setText(R.string.app_str_smart_panel_tip_msg_new_1);
                return;
            }
        }
        if (lightColorType == 19) {
            if (((ActSmartPanelVM) this.mViewModel).isPro) {
                ((FtPageScreenPanelSwitchBinding) this.mViewBinding).tvTipMessage.setText(R.string.app_str_smart_panel_tip_msg_s6pro);
                return;
            } else {
                ((FtPageScreenPanelSwitchBinding) this.mViewBinding).tvTipMessage.setText(R.string.app_str_smart_panel_tip_msg_s6);
                return;
            }
        }
        if (lightColorType == 21) {
            ((FtPageScreenPanelSwitchBinding) this.mViewBinding).tvTipMessage.setText(R.string.app_str_smart_panel_tip_msg_g4);
        } else if (((ActSmartPanelVM) this.mViewModel).isPro) {
            ((FtPageScreenPanelSwitchBinding) this.mViewBinding).tvTipMessage.setText(R.string.app_str_smart_panel_tip_msg_new_234_pro);
        } else {
            ((FtPageScreenPanelSwitchBinding) this.mViewBinding).tvTipMessage.setText(R.string.app_str_smart_panel_tip_msg_new_234);
        }
    }

    private void initGroupSwitch() {
        BaseQuickAdapter<Group, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Group, BaseViewHolder>(R.layout.item_screen_sp_btn_key) { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelSwitch.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Group group) {
                helper.setText(R.id.tv_device_name, group.getGroupName());
                helper.setImageResource(R.id.iv_switch, FtPageScreenPanelSwitch.this.selectArray[helper.getAdapterPosition()] ? R.mipmap.icon_ir_power_blue : R.mipmap.icon_ir_power);
            }
        };
        this.groupAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelSwitch$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                FtPageScreenPanelSwitch.this.lambda$initGroupSwitch$0(baseQuickAdapter2, view, i);
            }
        });
        this.groupAdapter.bindToRecyclerView(((FtPageScreenPanelSwitchBinding) this.mViewBinding).rv);
        ((FtPageScreenPanelSwitchBinding) this.mViewBinding).rv.setLayoutManager(new GridLayoutManager(getContext(), this.selectArray.length));
        ((FtPageScreenPanelSwitchBinding) this.mViewBinding).rv.setHasFixedSize(true);
        ((DefaultItemAnimator) ((FtPageScreenPanelSwitchBinding) this.mViewBinding).rv.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initGroupSwitch$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.selectArray[i] = !r2[i];
        this.groupAdapter.notifyItemChanged(i);
        ((ActSmartPanelVM) this.mViewModel).sendSingleOnOff(i, this.selectArray[i]);
        Object value = ((ActSmartPanelVM) this.mViewModel).controlObject.getValue();
        if (value instanceof Group) {
            Group group = (Group) value;
            if (group.getDeviceIds().isEmpty()) {
                RelaySeparationHelper.setSubGroupOnOff(group, i + 1, this.selectArray[i]);
            }
        }
    }

    private void initDeviceSwitch() {
        BaseQuickAdapter<Device, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Device, BaseViewHolder>(R.layout.item_screen_sp_btn_key) { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelSwitch.5
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Device device) {
                helper.setText(R.id.tv_device_name, device.getDeviceName());
                helper.setImageResource(R.id.iv_switch, FtPageScreenPanelSwitch.this.selectArray[helper.getAdapterPosition()] ? R.mipmap.icon_ir_power_blue : R.mipmap.icon_ir_power);
            }
        };
        this.deviceAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelSwitch$$ExternalSyntheticLambda2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                FtPageScreenPanelSwitch.this.lambda$initDeviceSwitch$1(baseQuickAdapter2, view, i);
            }
        });
        this.deviceAdapter.bindToRecyclerView(((FtPageScreenPanelSwitchBinding) this.mViewBinding).rv);
        ((FtPageScreenPanelSwitchBinding) this.mViewBinding).rv.setLayoutManager(new GridLayoutManager(getContext(), this.selectArray.length));
        ((FtPageScreenPanelSwitchBinding) this.mViewBinding).rv.setHasFixedSize(true);
        ((DefaultItemAnimator) ((FtPageScreenPanelSwitchBinding) this.mViewBinding).rv.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDeviceSwitch$1(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.selectArray[i] = !r1[i];
        this.deviceAdapter.notifyItemChanged(i);
        ((ActSmartPanelVM) this.mViewModel).sendSingleOnOff(i, this.selectArray[i]);
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        super.startObserve();
        ((ActSmartPanelVM) this.mViewModel).panelZoneStateLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelSwitch$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtPageScreenPanelSwitch.this.lambda$startObserve$2((List) obj);
            }
        });
        ((ActSmartPanelVM) this.mViewModel).controlObject.observe(this, new Observer<Object>() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelSwitch.6
            @Override // androidx.lifecycle.Observer
            public void onChanged(Object o) {
                FtPageScreenPanelSwitch.this.refresh();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(List list) {
        if (this.selectArray == null) {
            return;
        }
        int i = 0;
        while (true) {
            boolean[] zArr = this.selectArray;
            if (i >= zArr.length) {
                return;
            }
            zArr[i] = ((SwitchPanelState.SwitchPanelZoneState) list.get(i)).isSwitchOnOff();
            BaseQuickAdapter<Device, BaseViewHolder> baseQuickAdapter = this.deviceAdapter;
            if (baseQuickAdapter != null) {
                baseQuickAdapter.notifyItemChanged(i);
            }
            BaseQuickAdapter<Group, BaseViewHolder> baseQuickAdapter2 = this.groupAdapter;
            if (baseQuickAdapter2 != null) {
                baseQuickAdapter2.notifyItemChanged(i);
            }
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refresh() {
        Object value = ((ActSmartPanelVM) this.mViewModel).controlObject.getValue();
        if (value instanceof Device) {
            List<Device> subDevice = Injection.repo().device().getSubDevice(((ActSmartPanelVM) this.mViewModel).placeId, ((Device) value).getDeviceId());
            Collections.sort(subDevice, new Comparator<Device>(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelSwitch.7
                @Override // java.util.Comparator
                public int compare(Device o1, Device o2) {
                    return o1.getWifiMac().compareTo(o2.getWifiMac());
                }
            });
            BaseQuickAdapter<Device, BaseViewHolder> baseQuickAdapter = this.deviceAdapter;
            if (baseQuickAdapter != null) {
                baseQuickAdapter.replaceData(subDevice);
                return;
            }
            return;
        }
        Object value2 = ((ActSmartPanelVM) this.mViewModel).controlObject.getValue();
        if (value2 instanceof Group) {
            Group group = (Group) value2;
            List<Group> subGroup = Injection.repo().group().getSubGroup(group.getPlaceId(), group.getGroupId());
            Collections.sort(subGroup, new Comparator<Group>(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelSwitch.8
                @Override // java.util.Comparator
                public int compare(Group o1, Group o2) {
                    return o1.getSubkey() - o2.getSubkey();
                }
            });
            BaseQuickAdapter<Group, BaseViewHolder> baseQuickAdapter2 = this.groupAdapter;
            if (baseQuickAdapter2 != null) {
                baseQuickAdapter2.replaceData(subGroup);
            }
        }
    }
}