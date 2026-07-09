package com.ltech.smarthome.ui.device.super_panel;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.databinding.ActSuperPanelIrRemoteControlBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSuperPanelIrRemoteControl extends BaseControlActivity<ActSuperPanelIrRemoteControlBinding, ActMeshGatewayVM> {
    private BaseQuickAdapter<Device, BaseViewHolder> mDeviceAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_super_panel_ir_remote_control;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_add);
        setTitle(getString(R.string.infrared_appliance));
        BaseQuickAdapter<Device, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Device, BaseViewHolder>(R.layout.item_device_manage, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelIrRemoteControl.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Device item) {
                helper.setText(R.id.tv_device_name, item.getDeviceName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item)).setText(R.id.tv_place_info, ((ActMeshGatewayVM) ActSuperPanelIrRemoteControl.this.mViewModel).getPlaceInfo(item.getFloorId(), item.getRoomId()));
                ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
            }
        };
        this.mDeviceAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelIrRemoteControl$$ExternalSyntheticLambda2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActSuperPanelIrRemoteControl.this.lambda$initView$0(baseQuickAdapter2, view, i);
            }
        });
        this.mDeviceAdapter.bindToRecyclerView(((ActSuperPanelIrRemoteControlBinding) this.mViewBinding).rvContent);
        ((ActSuperPanelIrRemoteControlBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSuperPanelIrRemoteControlBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        Device device = this.mDeviceAdapter.getData().get(i);
        NavUtils.Builder irNavBuilder = NavHelper.getIrNavBuilder(device.getProductId());
        if (irNavBuilder != null) {
            irNavBuilder.withLong(Constants.CONTROL_ID, device.getId()).navigation(this);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyImageRes(R.mipmap.pic_empty_1).emptyStringRes(R.string.no_device));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        ((ActMeshGatewayVM) this.mViewModel).addIrDevice();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        super.showEmpty();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        List<Device> deviceListByPlaceId = Injection.repo().device().getDeviceListByPlaceId(((ActMeshGatewayVM) this.mViewModel).placeId);
        ArrayList arrayList = new ArrayList();
        for (Device device : deviceListByPlaceId) {
            if (device.getId() == ((ActMeshGatewayVM) this.mViewModel).controlId) {
                ((ActMeshGatewayVM) this.mViewModel).controlDevice = device;
            }
            if (device.isSubDevice() && !ProductId.CG485_SUB_DEVICE.equals(device.getProductId()) && !ProductId.CGRS8_SUB_DEVICE.equals(device.getProductId())) {
                arrayList.add(device);
            }
        }
        if (((ActMeshGatewayVM) this.mViewModel).controlDevice != null) {
            int i = 0;
            while (i < arrayList.size()) {
                if (((Device) arrayList.get(i)).getMacdeviceid() != ((ActMeshGatewayVM) this.mViewModel).controlDevice.getDeviceId()) {
                    arrayList.remove(i);
                    i--;
                }
                i++;
            }
        }
        ((ActMeshGatewayVM) this.mViewModel).deviceList.setValue(arrayList);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActMeshGatewayVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActMeshGatewayVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActMeshGatewayVM) this.mViewModel).floorList.setValue(Injection.repo().home().getFloorListByPlaceId(((ActMeshGatewayVM) this.mViewModel).placeId));
        ((ActMeshGatewayVM) this.mViewModel).roomList.setValue(Injection.repo().home().getRoomListByFloorId(((ActMeshGatewayVM) this.mViewModel).placeId, -1L));
        ((ActMeshGatewayVM) this.mViewModel).deviceList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelIrRemoteControl$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanelIrRemoteControl.this.lambda$startObserve$1((List) obj);
            }
        });
        ((ActMeshGatewayVM) this.mViewModel).showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelIrRemoteControl$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanelIrRemoteControl.this.lambda$startObserve$2((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(List list) {
        if (list.isEmpty()) {
            showEmpty();
        }
        this.mDeviceAdapter.setNewData(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        showNoPermissionDialog();
    }
}