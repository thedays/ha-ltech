package com.ltech.smarthome.ui.device.cg485;

import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActCg485Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.Rs485ExtParam;
import com.ltech.smarthome.model.device_param.SuperPanelExtParam;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.view.dialog.AddCg485DeviceDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActCg485 extends BaseControlActivity<ActCg485Binding, ActCg485VM> {
    private static final int TYPE_485_TO_BT = 2;
    private static final int TYPE_BT_TO_485 = 1;
    private BaseQuickAdapter<Rs485ExtParam.Category, BaseViewHolder> m485Adapter;
    private BaseQuickAdapter<Device, BaseViewHolder> mBleAdapter;
    private List<Rs485ExtParam.Category> commandList = new ArrayList();
    private List<Device> subDeviceList = new ArrayList();
    private SingleLiveEvent<Void> refreshList = new SingleLiveEvent<>();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.layout_content;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_cg_485;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        ((ActCg485VM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActCg485VM) this.mViewModel).placeId = Injection.repo().home().getSelPlace().getPlaceId();
        ((ActCg485Binding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActCg485.this.lambda$initView$2((View) obj);
            }
        }));
        ((ActCg485Binding) this.mViewBinding).layoutBottom.setVisibility(isOwnerOrManager() ? 0 : 8);
        initBleAdapter();
        init485Adapter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(View view) {
        if (view.getId() != R.id.tv_bottom) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.ble_to_rs485_device));
        arrayList.add(getString(R.string.rs485_to_ble_device));
        SelectListDialog.asDefault(true).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.confirm)).setSelectPosition(0).setSelectList(arrayList).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActCg485.this.lambda$initView$1((Integer) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(Integer num) {
        AddCg485DeviceDialog.asDefault().setSelectPosition(0).setType(num.intValue() + 1).setOnSaveListener(new AddCg485DeviceDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.view.dialog.AddCg485DeviceDialog.OnSaveListener
            public final void onSave(String str, int i, int i2) {
                ActCg485.this.lambda$initView$0(str, i, i2);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(String str, int i, int i2) {
        if (i == 1) {
            Cg485Helper.setControlDevice(Cg485Helper.createBleTo485SubDevice(str, i2));
            NavUtils.destination(ActEditInstructCmd.class).withInt(Constants.COMMAND_TYPE, i).navigation(this.activity);
            return;
        }
        Rs485ExtParam.Category category = new Rs485ExtParam.Category();
        category.setCategoryName(str);
        category.setType(i);
        category.setColorIdx(i2);
        Cg485Helper.addCategory(i, category);
        NavUtils.destination(ActEditInstructCmd.class).withInt(Constants.COMMAND_TYPE, i).navigation(this);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.no_device));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActCg485VM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCg485.this.lambda$startObserve$3((Device) obj);
            }
        });
        this.refreshList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCg485.this.lambda$startObserve$4((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Device device) {
        setTitle(device.getDeviceName());
        ((ActCg485VM) this.mViewModel).extParam = (SuperPanelExtParam) device.getExtParam(SuperPanelExtParam.class);
        if (((ActCg485VM) this.mViewModel).extParam == null) {
            ((ActCg485VM) this.mViewModel).extParam = new SuperPanelExtParam();
        }
        Cg485Helper.extParam = ((ActCg485VM) this.mViewModel).extParam;
        this.commandList = ((ActCg485VM) this.mViewModel).extParam.getToBleList();
        this.subDeviceList = Injection.repo().device().getSubDevice(((ActCg485VM) this.mViewModel).placeId, device.getDeviceId());
        this.refreshList.call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r4) {
        if (this.subDeviceList.isEmpty() && this.commandList.isEmpty()) {
            showEmpty();
            return;
        }
        showContent();
        ((ActCg485Binding) this.mViewBinding).tvBle.setVisibility(this.subDeviceList.isEmpty() ? 8 : 0);
        ((ActCg485Binding) this.mViewBinding).layoutBle.setVisibility(this.subDeviceList.isEmpty() ? 8 : 0);
        this.mBleAdapter.replaceData(this.subDeviceList);
        ((ActCg485Binding) this.mViewBinding).tv485.setVisibility(this.commandList.isEmpty() ? 8 : 0);
        ((ActCg485Binding) this.mViewBinding).layout485.setVisibility(this.commandList.isEmpty() ? 8 : 0);
        this.m485Adapter.replaceData(this.commandList);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        NavHelper.goSetting(((ActCg485VM) this.mViewModel).controlObject.getValue());
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        startObjectObserve();
    }

    private void startObjectObserve() {
        Device deviceById = Injection.repo().device().getDeviceById(((ActCg485VM) this.mViewModel).controlId);
        if (deviceById != null) {
            ((ActCg485VM) this.mViewModel).controlObject.setValue(deviceById);
            Cg485Helper.controlDevice = ((ActCg485VM) this.mViewModel).controlObject.getValue();
        }
    }

    private void initBleAdapter() {
        BaseQuickAdapter<Device, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Device, BaseViewHolder>(R.layout.item_select_with_place_cg485, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Device item) {
                helper.setText(R.id.tv_device_name, item.getName()).setImageResource(R.id.iv_icon, Cg485Helper.getDeviceImage(ActCg485.this.activity, Cg485Helper.getSubCategory(item).getColorIdx())).setText(R.id.tv_place_info, ActCg485.this.getPlaceInfo(item)).setImageResource(R.id.iv_select, R.mipmap.icon_more).setGone(R.id.view_divider, helper.getAdapterPosition() != ActCg485.this.mBleAdapter.getData().size() - 1);
            }
        };
        this.mBleAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActCg485.this.lambda$initBleAdapter$5(baseQuickAdapter2, view, i);
            }
        });
        this.mBleAdapter.bindToRecyclerView(((ActCg485Binding) this.mViewBinding).rvBleTo485);
        ((ActCg485Binding) this.mViewBinding).rvBleTo485.setLayoutManager(new LinearLayoutManager(this));
        ((ActCg485Binding) this.mViewBinding).rvBleTo485.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initBleAdapter$5(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        NavUtils.destination(ActCg485Device.class).withLong(Constants.CONTROL_ID, this.mBleAdapter.getData().get(i).getId()).withInt(Constants.COMMAND_TYPE, 1).withDefaultRequestCode().navigation(this.activity);
    }

    private void init485Adapter() {
        BaseQuickAdapter<Rs485ExtParam.Category, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Rs485ExtParam.Category, BaseViewHolder>(R.layout.item_select_with_place_cg485, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Rs485ExtParam.Category item) {
                BaseViewHolder imageResource = helper.setText(R.id.tv_device_name, item.getCategoryName()).setImageResource(R.id.iv_icon, Cg485Helper.getDeviceImage(ActCg485.this.activity, item.getColorIdx()));
                ActCg485 actCg485 = ActCg485.this;
                imageResource.setText(R.id.tv_place_info, actCg485.getPlaceInfo(((ActCg485VM) actCg485.mViewModel).controlObject.getValue())).setImageResource(R.id.iv_select, R.mipmap.icon_more).setGone(R.id.view_divider, helper.getAdapterPosition() != ActCg485.this.m485Adapter.getData().size() - 1);
            }
        };
        this.m485Adapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485$$ExternalSyntheticLambda5
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActCg485.this.lambda$init485Adapter$6(baseQuickAdapter2, view, i);
            }
        });
        this.m485Adapter.bindToRecyclerView(((ActCg485Binding) this.mViewBinding).rv485ToBle);
        ((ActCg485Binding) this.mViewBinding).rv485ToBle.setLayoutManager(new LinearLayoutManager(this));
        ((ActCg485Binding) this.mViewBinding).rv485ToBle.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init485Adapter$6(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        Cg485Helper.categoryPosition = i;
        NavUtils.destination(ActCg485Device.class).withLong(Constants.CONTROL_ID, ((ActCg485VM) this.mViewModel).controlId).withInt(Constants.COMMAND_TYPE, 2).withDefaultRequestCode().navigation(this.activity);
    }
}