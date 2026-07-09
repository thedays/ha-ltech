package com.ltech.smarthome.ui.device.base;

import android.content.Intent;
import android.text.TextUtils;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.log.DeviceLog;
import com.ltech.smarthome.net.response.log.ListLogResponse;
import com.ltech.smarthome.ui.config.ActMeshScanProxy;
import com.ltech.smarthome.upgrade.UpgradeFactory;
import com.ltech.smarthome.upgrade.UpgradeInfo;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.bean.ProductVersionInfo;
import com.smart.product_agreement.parser.IUpgradeParser;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public abstract class BaseControlActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends VMActivity<V, VM> {
    private BaseQuickAdapter<DeviceLog, BaseViewHolder> logAdapter;
    private ProductVersionInfo versionInfo;
    protected boolean isFirst = true;
    protected boolean needUpgrade = false;
    public SingleLiveEvent<Void> checkVersionFinish = new SingleLiveEvent<>();
    protected DefaultAdapter defaultAdapter = new DefaultAdapter();
    protected List<DeviceLog> logList = new ArrayList();
    public SingleLiveEvent<Boolean> queryLogResult = new SingleLiveEvent<>();

    protected int getGroupUpdateTipRes() {
        return R.string.app_str_force_upgrade_tips;
    }

    protected int getUpdateTipRes() {
        return R.string.app_str_force_upgrade_tips;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected boolean isRootViewClickEnable() {
        return false;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (5002 == resultCode || 5001 == resultCode) {
            finishActivity();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getPlaceInfo(Device device) {
        if (device != null) {
            Floor floor = Injection.repo().home().getFloor(device.getFloorId());
            Room room = Injection.repo().home().getRoom(device.getRoomId());
            if (floor == null || room == null) {
                if (floor != null) {
                    return floor.getFloorName();
                }
                return room != null ? room.getRoomName() : getString(R.string.app_str_not_distribution);
            }
            return floor.getFloorName() + room.getRoomName();
        }
        return "";
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getPlaceInfo(Group group) {
        if (group != null) {
            Floor floor = Injection.repo().home().getFloor(group.getFloorId());
            Room room = Injection.repo().home().getRoom(group.getRoomId());
            if (floor == null || room == null) {
                if (floor != null) {
                    return floor.getFloorName();
                }
                return room != null ? room.getRoomName() : getString(R.string.app_str_not_distribution);
            }
            return floor.getFloorName() + room.getRoomName();
        }
        return "";
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Role getRoleByRoleId(long roleId) {
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(roleId);
        return deviceByDeviceId != null ? deviceByDeviceId : Injection.repo().group().getGroupByGroupId(roleId);
    }

    protected boolean isE6() {
        return getIntent().getBooleanExtra(Constants.IS_E6, false);
    }

    protected void checkVersionForUpdate(final Device device, final String baseVersion) {
        if (this.isFirst && device != null) {
            if (device.getMcuversion() != null && UpgradeInfo.getSoftwareVersion(baseVersion).compareTo(UpgradeInfo.getSoftwareVersion(device.getMcuversion())) <= 0) {
                setEditImage(R.mipmap.ic_setting);
                showContent();
                this.checkVersionFinish.call();
            } else {
                showLoading();
                CmdAssistant.getQueryCmdAssistant(device, new int[0]).queryProductVersion(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.base.BaseControlActivity$$ExternalSyntheticLambda1
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        BaseControlActivity.this.lambda$checkVersionForUpdate$0(device, baseVersion, (ResponseMsg) obj);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkVersionForUpdate$0(Device device, String str, ResponseMsg responseMsg) {
        if (responseMsg == null) {
            this.defaultAdapter.errorTryStringRes(R.string.retry);
            showError();
            return;
        }
        if (Integer.parseInt(responseMsg.getDeviceFlag(), 16) != ((BleParam) device.getParam(BleParam.class)).getUnicastAddress()) {
            checkVersionForUpdate(device, str);
            return;
        }
        this.isFirst = false;
        if (responseMsg.getResData().length() > 16) {
            this.versionInfo = ((IUpgradeParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserUpgradeInfo(new String(StringUtils.hexStringToByte(responseMsg.getResData().substring(16))));
        }
        ProductVersionInfo productVersionInfo = this.versionInfo;
        if (productVersionInfo == null) {
            this.defaultAdapter.errorTryStringRes(R.string.retry);
            showError();
            return;
        }
        changeDeviceVersion(device, productVersionInfo.getsVer(), this.versionInfo.gethVer());
        if (UpgradeFactory.getUpgradeInfo(this.versionInfo) == null || str.compareTo(this.versionInfo.getsVer()) <= 0) {
            setEditImage(R.mipmap.ic_setting);
            showContent();
            this.checkVersionFinish.call();
            return;
        }
        showNeedUpdate(getString(getUpdateTipRes()));
    }

    protected void checkVersionForUpdate(Group group, String baseVersion) {
        if (!this.isFirst || group == null) {
            this.checkVersionFinish.call();
            return;
        }
        StringBuilder sb = new StringBuilder();
        Iterator<Long> it = group.getDeviceIds().iterator();
        while (it.hasNext()) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(it.next().longValue());
            if (deviceByDeviceId != null && deviceByDeviceId.getMcuversion() != null && ProductRepository.isScreenPanelDevice(deviceByDeviceId.getProductId()) && UpgradeInfo.getSoftwareVersion(deviceByDeviceId.getMcuversion()).compareTo(UpgradeInfo.getSoftwareVersion(baseVersion)) < 0) {
                if (!sb.toString().isEmpty()) {
                    sb.append(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
                }
                sb.append(deviceByDeviceId.getDeviceName());
            }
        }
        if (!sb.toString().isEmpty()) {
            showNeedUpdate(((Object) sb) + ": " + getString(getGroupUpdateTipRes()));
        } else {
            setEditImage(R.mipmap.ic_setting);
            showContent();
            this.checkVersionFinish.call();
        }
        this.isFirst = false;
    }

    public void changeDeviceVersion(final Device device, final String sVersion, final String hVersion) {
        if (device != null) {
            if (TextUtils.isEmpty(device.getMcuversion()) || !device.getMcuversion().equals(sVersion) || TextUtils.isEmpty(device.getFirmwareversion()) || !device.getFirmwareversion().equals(hVersion)) {
                ((ObservableSubscribeProxy) Injection.net().updateDeviceVersion(device.getDeviceId(), sVersion, hVersion).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.base.BaseControlActivity$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        BaseControlActivity.lambda$changeDeviceVersion$1(Device.this, sVersion, hVersion, obj);
                    }
                }, new SmartErrorComsumer());
            }
        }
    }

    static /* synthetic */ void lambda$changeDeviceVersion$1(Device device, String str, String str2, Object obj) throws Exception {
        device.setMcuversion(str);
        device.setFirmwareversion(str2);
        Injection.repo().device().saveDevice(device);
    }

    public void queryLog(long deviceId, int pageSize, int pageNum) {
        ((ObservableSubscribeProxy) Injection.net().queryLog(deviceId, pageSize, pageNum).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.base.BaseControlActivity$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseControlActivity.this.lambda$queryLog$2((ListLogResponse) obj);
            }
        }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.device.base.BaseControlActivity.1
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                BaseControlActivity.this.queryLogResult.setValue(false);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryLog$2(ListLogResponse listLogResponse) throws Exception {
        this.logList.clear();
        this.logList.addAll(listLogResponse.getRows());
        this.queryLogResult.setValue(true);
    }

    protected BaseQuickAdapter<DeviceLog, BaseViewHolder> getLogAdapter() {
        if (this.logAdapter == null) {
            this.logAdapter = new BaseQuickAdapter<DeviceLog, BaseViewHolder>(R.layout.item_device_log, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.base.BaseControlActivity.2
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, DeviceLog deviceLog) {
                    helper.setGone(R.id.time_line, helper.getBindingAdapterPosition() != getItemCount() - 1).setText(R.id.tv_time, deviceLog.getShortCreatetime()).setText(R.id.tv_log, LanguageUtils.isChinese(BaseControlActivity.this.activity) ? deviceLog.getCnmessage() : deviceLog.getEnmessage());
                }
            };
        }
        return this.logAdapter;
    }

    protected void goUpgrade(long controlId) {
        finishActivity();
        NavUtils.destination(ActMeshScanProxy.class).withLong(Constants.CONTROL_ID, controlId).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).navigation(this);
    }

    private void showNeedUpdate(String tipContent) {
        this.defaultAdapter.loadFailString(tipContent).errorImageRes(R.mipmap.pic_empty_1).errorTryStringRes(R.string.firmware_upgrade);
        this.needUpgrade = true;
        showError();
        setEditImage(0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isEmptyGroup(Object object) {
        if (!(object instanceof Group) || !((Group) object).getDeviceIds().isEmpty()) {
            return false;
        }
        SmartToast.showShort(getString(R.string.app_str_group_empty));
        return true;
    }

    protected boolean isAddressInGroup(Group group, int address) {
        Iterator<Long> it = group.getDeviceIds().iterator();
        while (it.hasNext()) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(it.next().longValue());
            if (deviceByDeviceId != null && address == ((BleParam) deviceByDeviceId.getParam(BleParam.class)).getUnicastAddress()) {
                return true;
            }
        }
        return false;
    }
}