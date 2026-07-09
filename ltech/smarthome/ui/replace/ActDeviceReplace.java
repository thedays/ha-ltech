package com.ltech.smarthome.ui.replace;

import android.content.Intent;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseExtParam;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActDeviceReplaceBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.config.ActMeshScanProxy;
import com.ltech.smarthome.ui.replace.ActDeviceReplace;
import com.ltech.smarthome.upgrade.UpgradeInfo;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.bean.ProductVersionInfo;
import com.smart.product_agreement.parser.IUpgradeParser;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActDeviceReplace extends VMActivity<ActDeviceReplaceBinding, ActDeviceReplaceVM> {
    private BaseQuickAdapter<Device, BaseViewHolder> deviceAdapter;
    private String oldBinName;
    private Device oldDevice;
    private BaseExtParam oldParam;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_device_replace;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        this.oldDevice = Injection.repo().device().getDeviceByDeviceId(getIntent().getLongExtra("device_id", -1L));
        ((ActDeviceReplaceVM) this.mViewModel).placeId = this.oldDevice.getPlaceId();
        Device device = this.oldDevice;
        if (device != null) {
            BaseExtParam baseExtParam = (BaseExtParam) device.getExtParam(BaseExtParam.class);
            this.oldParam = baseExtParam;
            if (baseExtParam != null && baseExtParam.getBinName() != null) {
                this.oldBinName = this.oldParam.getBinName();
            }
        }
        setTitle(getString(R.string.device_replace));
        setBackImage(R.mipmap.icon_back);
        initAdapter();
        ((ActDeviceReplaceVM) this.mViewModel).initFloorList(this.oldDevice.getFloorId());
        ((ActDeviceReplaceVM) this.mViewModel).initFloorSpinner(((ActDeviceReplaceBinding) this.mViewBinding).spinnerFloor);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActDeviceReplaceVM) this.mViewModel).selectFloor.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.replace.ActDeviceReplace$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDeviceReplace.this.lambda$startObserve$0((Floor) obj);
            }
        });
        ((ActDeviceReplaceVM) this.mViewModel).selectRoom.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.replace.ActDeviceReplace$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDeviceReplace.this.lambda$startObserve$1((Room) obj);
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

    /* renamed from: com.ltech.smarthome.ui.replace.ActDeviceReplace$1, reason: invalid class name */
    class AnonymousClass1 extends BaseQuickAdapter<Device, BaseViewHolder> {
        AnonymousClass1(int layoutResId, List data) {
            super(layoutResId, data);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(BaseViewHolder helper, final Device item) {
            helper.setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item)).setText(R.id.tv_device_name, item.getDeviceName()).setGone(R.id.iv_edit, false);
            helper.setText(R.id.tv_place_info, ((ActDeviceReplaceVM) ActDeviceReplace.this.mViewModel).getLocationName(item.getFloorId(), item.getRoomId()));
            helper.getView(R.id.iv_location).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.replace.ActDeviceReplace$1$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ActDeviceReplace.AnonymousClass1.this.lambda$convert$0(item, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(Device device, View view) {
            CmdAssistant.getSettingCmdAssistant(device, new int[0]).testDeviceLocation(ActDeviceReplace.this.activity);
        }
    }

    private void initAdapter() {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(R.layout.item_device_manage_new, new ArrayList());
        this.deviceAdapter = anonymousClass1;
        anonymousClass1.setOnItemClickListener(new AnonymousClass2());
        this.deviceAdapter.bindToRecyclerView(((ActDeviceReplaceBinding) this.mViewBinding).rvDevice);
        ((ActDeviceReplaceBinding) this.mViewBinding).rvDevice.setLayoutManager(new LinearLayoutManager(this));
        ((ActDeviceReplaceBinding) this.mViewBinding).rvDevice.setHasFixedSize(true);
    }

    /* renamed from: com.ltech.smarthome.ui.replace.ActDeviceReplace$2, reason: invalid class name */
    class AnonymousClass2 implements BaseQuickAdapter.OnItemClickListener {
        AnonymousClass2() {
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
        public void onItemClick(BaseQuickAdapter adapter, View view, final int i) {
            MessageDialog.show(ActDeviceReplace.this.activity, ActDeviceReplace.this.getString(R.string.replace_old_device), ActDeviceReplace.this.getString(R.string.replace_device_tip)).setOkButton(R.string.confirm, new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.replace.ActDeviceReplace$2$$ExternalSyntheticLambda0
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view2) {
                    boolean lambda$onItemClick$0;
                    lambda$onItemClick$0 = ActDeviceReplace.AnonymousClass2.this.lambda$onItemClick$0(i, baseDialog, view2);
                    return lambda$onItemClick$0;
                }
            }).setCancelButton(R.string.cancel);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$onItemClick$0(int i, BaseDialog baseDialog, View view) {
            ActDeviceReplace actDeviceReplace = ActDeviceReplace.this;
            actDeviceReplace.checkVersion((Device) actDeviceReplace.deviceAdapter.getData().get(i));
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkVersion(Device newDevice) {
        ReplaceHelper.instance().newDevice = newDevice;
        BaseExtParam baseExtParam = this.oldParam;
        if (baseExtParam == null || baseExtParam.getBinName() == null) {
            checkDeviceVersion(this.oldDevice, false);
        } else {
            checkDeviceVersion(newDevice, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkSuppport(Device newDevice) {
        showLoadingDialog("");
        CmdAssistant.getQueryCmdAssistant(newDevice, new int[0]).querySupportReplace(this.activity, new IAction() { // from class: com.ltech.smarthome.ui.replace.ActDeviceReplace$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDeviceReplace.this.lambda$checkSuppport$2((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkSuppport$2(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getStateCode() == 0 && responseMsg.getResData().length() > 16 && Integer.parseInt(responseMsg.getResData().substring(16, 18), 16) == 1) {
                dismissLoadingDialog();
                goReplace();
                return;
            } else {
                dismissLoadingDialog();
                MessageDialog.show(this.activity, getString(R.string.device_not_support_replace), "").setOkButton(R.string.i_know).show();
                return;
            }
        }
        dismissLoadingDialog();
        MessageDialog.show(this.activity, R.string.device_offline, R.string.str_replacing_tip).setOkButton(R.string.ok).show();
    }

    private void checkDeviceVersion(final Device device, final boolean isNew) {
        showLoadingDialog("");
        CmdAssistant.getQueryCmdAssistant(device, new int[0]).queryProductVersion(this, new IAction() { // from class: com.ltech.smarthome.ui.replace.ActDeviceReplace$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDeviceReplace.this.lambda$checkDeviceVersion$5(isNew, device, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkDeviceVersion$5(boolean z, final Device device, ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getResData().length() < 16) {
            if (z) {
                dismissLoadingDialog();
                MessageDialog.show(this.activity, R.string.device_offline, R.string.str_replacing_tip).setOkButton(R.string.ok).show();
                return;
            } else {
                dismissLoadingDialog();
                MessageDialog.show(this.activity, R.string.old_device_not_query, R.string.old_device_not_query_tip).setOkButton(R.string.cancel).setCancelButton(R.string.go_continue, new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.replace.ActDeviceReplace$$ExternalSyntheticLambda0
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        boolean lambda$checkDeviceVersion$3;
                        lambda$checkDeviceVersion$3 = ActDeviceReplace.this.lambda$checkDeviceVersion$3(baseDialog, view);
                        return lambda$checkDeviceVersion$3;
                    }
                }).show();
                return;
            }
        }
        ProductVersionInfo parserUpgradeInfo = ((IUpgradeParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserUpgradeInfo(new String(StringUtils.hexStringToByte(responseMsg.getResData().substring(16))));
        if (parserUpgradeInfo == null) {
            return;
        }
        ((ActDeviceReplaceVM) this.mViewModel).changeDeviceVersion(device, parserUpgradeInfo.getsVer(), parserUpgradeInfo.gethVer());
        if (!z) {
            this.oldDevice.setMcuversion(parserUpgradeInfo.getsVer());
            this.oldBinName = parserUpgradeInfo.getDeviceModel();
        }
        if (device.getExtParam() != null && JSONObject.parseObject(device.getExtParam()).getString("binName") == null) {
            ((ActDeviceReplaceVM) this.mViewModel).changeDeviceProductName(device, parserUpgradeInfo.getDeviceModel());
        }
        if (z) {
            dismissLoadingDialog();
            if (this.oldBinName != null && !parserUpgradeInfo.getDeviceModel().equals(this.oldBinName)) {
                MessageDialog.show(this.activity, getString(R.string.model_not_match), "").setOkButton(R.string.i_know).show();
                return;
            } else if (UpgradeInfo.getSoftwareVersion(parserUpgradeInfo.getsVer()).compareTo(UpgradeInfo.getSoftwareVersion(this.oldDevice.getMcuversion())) < 0) {
                MessageDialog.show(this.activity, R.string.device_version_low, R.string.device_version_low_tip).setOkButton(R.string.go_upgrade, new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.replace.ActDeviceReplace$$ExternalSyntheticLambda1
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        boolean lambda$checkDeviceVersion$4;
                        lambda$checkDeviceVersion$4 = ActDeviceReplace.this.lambda$checkDeviceVersion$4(device, baseDialog, view);
                        return lambda$checkDeviceVersion$4;
                    }
                }).setCancelButton(R.string.cancel).show();
                return;
            } else {
                getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.replace.ActDeviceReplace.3
                    @Override // java.lang.Runnable
                    public void run() {
                        ActDeviceReplace.this.checkSuppport(ReplaceHelper.instance().newDevice);
                    }
                }, 1000L);
                return;
            }
        }
        checkDeviceVersion(ReplaceHelper.instance().newDevice, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$checkDeviceVersion$3(BaseDialog baseDialog, View view) {
        checkSuppport(ReplaceHelper.instance().newDevice);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$checkDeviceVersion$4(Device device, BaseDialog baseDialog, View view) {
        NavUtils.destination(ActMeshScanProxy.class).withLong(Constants.CONTROL_ID, device.getId()).withLong(Constants.PLACE_ID, device.getPlaceId()).navigation(this);
        return false;
    }

    private void goReplace() {
        NavUtils.destination(ActReplace.class).withLong("device_id", this.oldDevice.getDeviceId()).withDefaultRequestCode().navigation(this);
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
        boolean z;
        if (newDevice.isVirtual() || newDevice.isSubDevice()) {
            return false;
        }
        BaseExtParam baseExtParam = (BaseExtParam) newDevice.getExtParam(BaseExtParam.class);
        BaseExtParam baseExtParam2 = this.oldParam;
        boolean z2 = (baseExtParam2 == null || baseExtParam2.getBinName() == null || baseExtParam == null || baseExtParam.getBinName() == null || !baseExtParam.getBinName().equals(this.oldParam.getBinName())) ? false : true;
        BleParam bleParam = (BleParam) newDevice.getParam(BleParam.class);
        BleParam bleParam2 = (BleParam) this.oldDevice.getParam(BleParam.class);
        if (bleParam != null && bleParam2 != null) {
            int bleType = bleParam.getBleType();
            int bleType2 = bleParam2.getBleType();
            if (bleType2 != 0 && bleType == bleType2) {
                z = true;
                return !z2 && z && ProductRepository.getLightColorType((Object) newDevice) == ProductRepository.getLightColorType((Object) this.oldDevice);
            }
        }
        z = false;
        if (z2) {
        }
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