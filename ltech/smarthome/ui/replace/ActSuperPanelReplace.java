package com.ltech.smarthome.ui.replace;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActDeviceReplaceBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.super_panel.SuperPanelVersionHelper;
import com.ltech.smarthome.ui.replace.ActSuperPanelReplace;
import com.ltech.smarthome.utils.NavUtils;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActSuperPanelReplace extends VMActivity<ActDeviceReplaceBinding, ActDeviceReplaceVM> {
    private BaseQuickAdapter<Device, BaseViewHolder> deviceAdapter;
    private Device oldDevice;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_device_replace;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        this.oldDevice = Injection.repo().device().getDeviceByDeviceId(getIntent().getLongExtra("device_id", -1L));
        ((ActDeviceReplaceVM) this.mViewModel).placeId = this.oldDevice.getPlaceId();
        setTitle(getString(R.string.device_replace));
        setBackImage(R.mipmap.icon_back);
        initAdapter();
        ((ActDeviceReplaceVM) this.mViewModel).initFloorList(this.oldDevice.getFloorId());
        ((ActDeviceReplaceVM) this.mViewModel).initFloorSpinner(((ActDeviceReplaceBinding) this.mViewBinding).spinnerFloor);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActDeviceReplaceVM) this.mViewModel).selectFloor.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.replace.ActSuperPanelReplace$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanelReplace.this.lambda$startObserve$0((Floor) obj);
            }
        });
        ((ActDeviceReplaceVM) this.mViewModel).selectRoom.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.replace.ActSuperPanelReplace$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanelReplace.this.lambda$startObserve$1((Room) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Floor floor) {
        ((ActDeviceReplaceVM) this.mViewModel).initRoomList(floor, this.oldDevice.getRoomId());
        ((ActDeviceReplaceVM) this.mViewModel).initRoomSpinner(((ActDeviceReplaceBinding) this.mViewBinding).spinnerRoom);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Room room) {
        showDeviceList();
    }

    private void initAdapter() {
        BaseQuickAdapter<Device, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Device, BaseViewHolder>(R.layout.item_device_manage_new, new ArrayList()) { // from class: com.ltech.smarthome.ui.replace.ActSuperPanelReplace.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Device item) {
                helper.setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item)).setText(R.id.tv_device_name, item.getDeviceName()).setGone(R.id.iv_edit, false).setGone(R.id.iv_location, false);
                helper.setText(R.id.tv_place_info, ((ActDeviceReplaceVM) ActSuperPanelReplace.this.mViewModel).getLocationName(item.getFloorId(), item.getRoomId()));
            }
        };
        this.deviceAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new AnonymousClass2());
        this.deviceAdapter.bindToRecyclerView(((ActDeviceReplaceBinding) this.mViewBinding).rvDevice);
        ((ActDeviceReplaceBinding) this.mViewBinding).rvDevice.setLayoutManager(new LinearLayoutManager(this));
        ((ActDeviceReplaceBinding) this.mViewBinding).rvDevice.setHasFixedSize(true);
    }

    /* renamed from: com.ltech.smarthome.ui.replace.ActSuperPanelReplace$2, reason: invalid class name */
    class AnonymousClass2 implements BaseQuickAdapter.OnItemClickListener {
        AnonymousClass2() {
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
        public void onItemClick(BaseQuickAdapter adapter, View view, final int i) {
            MessageDialog.show(ActSuperPanelReplace.this.activity, ActSuperPanelReplace.this.getString(R.string.replace_old_device), ActSuperPanelReplace.this.getString(R.string.replace_device_tip)).setOkButton(R.string.confirm, new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.replace.ActSuperPanelReplace$2$$ExternalSyntheticLambda0
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view2) {
                    boolean lambda$onItemClick$0;
                    lambda$onItemClick$0 = ActSuperPanelReplace.AnonymousClass2.this.lambda$onItemClick$0(i, baseDialog, view2);
                    return lambda$onItemClick$0;
                }
            }).setCancelButton(R.string.cancel);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$onItemClick$0(int i, BaseDialog baseDialog, View view) {
            ActSuperPanelReplace actSuperPanelReplace = ActSuperPanelReplace.this;
            actSuperPanelReplace.checkSuppport((Device) actSuperPanelReplace.deviceAdapter.getData().get(i));
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkSuppport(Device newDevice) {
        ReplaceHelper.instance().newDevice = newDevice;
        if (!SuperPanelVersionHelper.isSupportReplace(newDevice)) {
            MessageDialog.show(this.activity, getString(R.string.device_not_support_replace), "").setOkButton(R.string.i_know).show();
        } else {
            checkDeviceVersion(newDevice);
        }
    }

    private void checkDeviceVersion(Device newDevice) {
        if (TextUtils.isEmpty(this.oldDevice.getMcuversion())) {
            MessageDialog.show(this.activity, R.string.old_device_app_not_query, R.string.old_device_not_query_tip).setOkButton(R.string.cancel).setCancelButton(R.string.go_continue, new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.replace.ActSuperPanelReplace$$ExternalSyntheticLambda2
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$checkDeviceVersion$2;
                    lambda$checkDeviceVersion$2 = ActSuperPanelReplace.this.lambda$checkDeviceVersion$2(baseDialog, view);
                    return lambda$checkDeviceVersion$2;
                }
            }).show();
        } else if (newDevice.getMcuversion().compareTo(this.oldDevice.getMcuversion()) >= 0) {
            goReplace();
        } else {
            MessageDialog.show(this.activity, R.string.device_version_low, R.string.device_app_version_low_tip).setOkButton(getString(R.string.ok)).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$checkDeviceVersion$2(BaseDialog baseDialog, View view) {
        goReplace();
        return false;
    }

    private void goReplace() {
        if (ReplaceHelper.instance().newDevice.isOnline()) {
            NavUtils.destination(ActReplace.class).withLong("device_id", this.oldDevice.getDeviceId()).withDefaultRequestCode().navigation(this);
        } else {
            MessageDialog.show(this.activity, R.string.device_offline, R.string.str_replacing_tip).setOkButton(R.string.ok).show();
        }
    }

    private void showDeviceList() {
        ArrayList arrayList = new ArrayList();
        for (Device device : Injection.repo().device().getDeviceListByRoomIdFromDb(this.oldDevice.getPlaceId(), ((ActDeviceReplaceVM) this.mViewModel).getCurFloor().getFloorId(), ((ActDeviceReplaceVM) this.mViewModel).getCurRoom().getRoomId())) {
            if (device.getDeviceId() != this.oldDevice.getDeviceId() && isSameModel(device)) {
                arrayList.add(device);
            }
        }
        this.deviceAdapter.replaceData(arrayList);
    }

    private boolean isSameModel(Device newDevice) {
        return newDevice.getProductId().equals(this.oldDevice.getProductId());
    }

    private boolean isProvisioned(Device newDevice) {
        return (newDevice.getParam() == null || newDevice.getParam(BleParam.class) == null || ((BleParam) newDevice.getParam(BleParam.class)).getUnicastAddress() == 0) ? false : true;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3023 == resultCode) {
            setResult(3023);
            finishActivity();
        }
    }
}