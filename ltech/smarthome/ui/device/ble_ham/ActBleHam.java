package com.ltech.smarthome.ui.device.ble_ham;

import android.graphics.Rect;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.databinding.ActMeshGatewayBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActBleHam extends BaseControlActivity<ActMeshGatewayBinding, ActMeshGatewayVM> {
    private BaseQuickAdapter<Device, BaseViewHolder> mDeviceAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_mesh_gateway;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().bgRes(R.drawable.shape_white_round_bg_1).emptyStringRes(R.string.no_device).emptyTryStringRes(R.string.add_device));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        ((ActMeshGatewayBinding) this.mViewBinding).appCompatTextView38.setText(getString(R.string.ble_home_appliance_module));
        BaseQuickAdapter<Device, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Device, BaseViewHolder>(R.layout.item_device_manage, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.ble_ham.ActBleHam.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Device item) {
                helper.setText(R.id.tv_device_name, item.getDeviceName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item)).setText(R.id.tv_place_info, ((ActMeshGatewayVM) ActBleHam.this.mViewModel).getPlaceInfo(item.getFloorId(), item.getRoomId()));
                ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
            }
        };
        this.mDeviceAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.ble_ham.ActBleHam$$ExternalSyntheticLambda2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActBleHam.this.lambda$initView$0(baseQuickAdapter2, view, i);
            }
        });
        this.mDeviceAdapter.bindToRecyclerView(((ActMeshGatewayBinding) this.mViewBinding).rvContent);
        ((ActMeshGatewayBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActMeshGatewayBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((ActMeshGatewayBinding) this.mViewBinding).rvContent.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.ble_ham.ActBleHam.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, ConvertUtils.dp2px(5.0f), 0, ConvertUtils.dp2px(5.0f));
            }
        });
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
    protected void edit() {
        super.edit();
        NavHelper.goSetting(((ActMeshGatewayVM) this.mViewModel).controlDevice);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onEmptyTry() {
        super.onEmptyTry();
        ((ActMeshGatewayVM) this.mViewModel).addIrDevice();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActMeshGatewayVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActMeshGatewayVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActMeshGatewayVM) this.mViewModel).floorList.setValue(Injection.repo().home().getFloorListByPlaceId(((ActMeshGatewayVM) this.mViewModel).placeId));
        ((ActMeshGatewayVM) this.mViewModel).roomList.setValue(Injection.repo().home().getRoomListByFloorId(((ActMeshGatewayVM) this.mViewModel).placeId, -1L));
        ((ActMeshGatewayVM) this.mViewModel).deviceList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ble_ham.ActBleHam$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActBleHam.this.lambda$startObserve$1((List) obj);
            }
        });
        ((ActMeshGatewayVM) this.mViewModel).showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ble_ham.ActBleHam$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActBleHam.this.lambda$startObserve$2((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(List list) {
        if (list.isEmpty()) {
            showEmpty();
        }
        this.mDeviceAdapter.setNewData(list);
        ((ActMeshGatewayBinding) this.mViewBinding).tvNum.setText(getString(R.string.ir_device_num, new Object[]{Integer.valueOf(list.size())}));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        showNoPermissionDialog();
    }

    private void initData() {
        List<Device> deviceListByPlaceId = Injection.repo().device().getDeviceListByPlaceId(((ActMeshGatewayVM) this.mViewModel).placeId);
        ArrayList arrayList = new ArrayList();
        for (Device device : deviceListByPlaceId) {
            if (device.getId() == ((ActMeshGatewayVM) this.mViewModel).controlId) {
                ((ActMeshGatewayVM) this.mViewModel).controlDevice = device;
                setTitle(device.getDeviceName());
            }
            if (device.isSubDevice()) {
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
}