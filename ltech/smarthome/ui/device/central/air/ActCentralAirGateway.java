package com.ltech.smarthome.ui.device.central.air;

import android.os.Handler;
import android.os.Message;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.blemesh.ISendCallback;
import com.ltech.smarthome.databinding.ActCentralAirGatewayBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.CentralAirSubDeviceParam;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.smart.message.MessageManager;
import com.smart.message.utils.LHomeLog;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActCentralAirGateway extends BaseControlActivity<ActCentralAirGatewayBinding, ActCentralAirGatewayVM> {
    private static int HANDLE_SAVE_SUBDEVICE = 1;
    private static int HANDLE_SEARCH_SUBDEVICE = 2;
    private static int SAVE_SUB_INTERVAL_TIME = 200;
    private static int SUB_DEVICE_LENGTH = 20;
    BleParam deviceParam;
    private String framModData;
    ISendCallback iSendCallback;
    MultipleItemRvAdapter<Device, BaseViewHolder> mAdapter;
    private Handler subDeviceHandler;
    private int sum;
    int totalfram = 1;
    int framIndex = 0;
    List<Device> cacheSubDeviceList = new ArrayList();
    int totalSubDevice = 0;
    private List<CentralAirSubDeviceParam> subDeviceParamList = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_central_air_gateway;
    }

    @Override // com.ltech.smarthome.base.VMActivity
    protected boolean useVMStateEvent() {
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        this.subDeviceHandler = new Handler() { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAirGateway.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == ActCentralAirGateway.HANDLE_SAVE_SUBDEVICE) {
                    ((ActCentralAirGatewayVM) ActCentralAirGateway.this.mViewModel).saveSearchSubDevice((CentralAirSubDeviceParam) msg.obj);
                } else if (msg.what == ActCentralAirGateway.HANDLE_SEARCH_SUBDEVICE) {
                    ActCentralAirGateway.this.searchSubDevice();
                }
            }
        };
        this.iSendCallback = new ISendCallback() { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAirGateway.2
            @Override // com.ltech.smarthome.blemesh.ISendCallback
            public void onSendSuccess() {
                LHomeLog.i(ActCentralAirGateway.class, "onSendSuccess");
            }

            @Override // com.ltech.smarthome.blemesh.ISendCallback
            public void onSendFail() {
                ActCentralAirGateway.this.searchSubDevice();
            }
        };
        MessageManager.getInstance().setGetCentralAirSubData(new MessageManager.GetCentralAirSubData() { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAirGateway.3
            @Override // com.smart.message.MessageManager.GetCentralAirSubData
            public void get(String data) {
                LHomeLog.i(ActCentralAirGateway.class, "framIndex=" + ActCentralAirGateway.this.framIndex + Constants.COLON_SEPARATOR + data);
                if (data.length() < 16 || ActCentralAirGateway.this.framIndex >= ActCentralAirGateway.this.totalfram || !data.substring(10, 12).equals("C4")) {
                    return;
                }
                String substring = data.substring(16);
                if (substring.length() < 12) {
                    return;
                }
                if (Utils.checkData(substring)) {
                    if (ActCentralAirGateway.this.framIndex == 0) {
                        ActCentralAirGateway.this.totalfram = Integer.valueOf(substring.substring(0, 2), 16).intValue();
                        ActCentralAirGateway.this.totalSubDevice = Integer.valueOf(substring.substring(10, 12), 16).intValue();
                        ActCentralAirGateway.this.parseSubData(substring.substring(12, substring.length() - 2));
                    } else {
                        String substring2 = substring.substring(4, substring.length() - 2);
                        if (ActCentralAirGateway.this.framModData.length() > 0) {
                            substring2 = ActCentralAirGateway.this.framModData + substring2;
                        }
                        ActCentralAirGateway.this.parseSubData(substring2);
                    }
                    ActCentralAirGateway.this.framIndex++;
                    LHomeLog.i(ActCentralAirGateway.class, "framIndex=" + ActCentralAirGateway.this.framIndex + ":totalfram=" + ActCentralAirGateway.this.totalfram);
                    if (ActCentralAirGateway.this.framIndex < ActCentralAirGateway.this.totalfram) {
                        ActCentralAirGateway.this.subDeviceHandler.sendEmptyMessageDelayed(ActCentralAirGateway.HANDLE_SEARCH_SUBDEVICE, 500L);
                        return;
                    }
                    return;
                }
                ActCentralAirGateway.this.subDeviceHandler.sendEmptyMessageDelayed(ActCentralAirGateway.HANDLE_SEARCH_SUBDEVICE, 500L);
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        NavHelper.goSetting(((ActCentralAirGatewayVM) this.mViewModel).controlObject.getValue());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        String extParam;
        ((ActCentralAirGatewayVM) this.mViewModel).controlId = getIntent().getLongExtra(com.ltech.smarthome.utils.Constants.CONTROL_ID, -1L);
        ((ActCentralAirGatewayVM) this.mViewModel).placeId = getIntent().getLongExtra(com.ltech.smarthome.utils.Constants.PLACE_ID, -1L);
        ((ActCentralAirGatewayVM) this.mViewModel).subDeviceList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAirGateway$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCentralAirGateway.this.lambda$startObserve$0((List) obj);
            }
        });
        Injection.repo().device().getDeviceList(this, ((ActCentralAirGatewayVM) this.mViewModel).placeId, -1L, -1L).data();
        for (Device device : Injection.repo().device().getDeviceListCache().getValue()) {
            if (device.getId() == ((ActCentralAirGatewayVM) this.mViewModel).controlId) {
                setTitle(device.getDeviceName());
                ((ActCentralAirGatewayVM) this.mViewModel).controlObject.setValue(device);
                Floor floor = Injection.repo().home().getFloor(device.getFloorId());
                if (floor != null) {
                    ((ActCentralAirGatewayVM) this.mViewModel).floorName = floor.getFloorName();
                }
                Room room = Injection.repo().home().getRoom(device.getRoomId());
                if (room != null) {
                    ((ActCentralAirGatewayVM) this.mViewModel).roomName = room.getRoomName();
                }
                this.deviceParam = (BleParam) device.getParam(BleParam.class);
            }
            if (device.isSubDevice() && (extParam = device.getExtParam()) != null && extParam.contains("\"ACType\":1")) {
                this.cacheSubDeviceList.add(device);
            }
        }
        if (((ActCentralAirGatewayVM) this.mViewModel).controlObject.getValue() != null) {
            int i = 0;
            while (i < this.cacheSubDeviceList.size()) {
                if (this.cacheSubDeviceList.get(i).getMacdeviceid() != ((ActCentralAirGatewayVM) this.mViewModel).controlObject.getValue().getDeviceId()) {
                    this.cacheSubDeviceList.remove(i);
                    i--;
                }
                i++;
            }
        }
        LHomeLog.i(ActCentralAirGateway.class, "size=" + this.cacheSubDeviceList.size());
        ((ActCentralAirGatewayVM) this.mViewModel).subDeviceList.setValue(this.cacheSubDeviceList);
        initRvData();
        if (((ActCentralAirGatewayVM) this.mViewModel).subDeviceList.getValue().size() == 0) {
            this.subDeviceHandler.sendEmptyMessageDelayed(HANDLE_SEARCH_SUBDEVICE, 500L);
        }
        ((ActCentralAirGatewayVM) this.mViewModel).searchDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAirGateway$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCentralAirGateway.this.lambda$startObserve$1((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(List list) {
        int size = ((ActCentralAirGatewayVM) this.mViewModel).subDeviceList.getValue().size();
        if (size > 0 && size >= this.sum) {
            dismissLoadingDialog();
        }
        updateUi(list.size());
        LHomeLog.i(ActCentralAirGateway.class, "update data list");
        this.mAdapter.setNewData(((ActCentralAirGatewayVM) this.mViewModel).subDeviceList.getValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r9) {
        String extParam;
        Injection.repo().device().getDeviceList(this, ((ActCentralAirGatewayVM) this.mViewModel).placeId, -1L, -1L).data();
        List<Device> value = Injection.repo().device().getDeviceListCache().getValue();
        this.cacheSubDeviceList.clear();
        ((ActCentralAirGatewayVM) this.mViewModel).saveDevice.clear();
        for (Device device : value) {
            if (device.getId() == ((ActCentralAirGatewayVM) this.mViewModel).controlId) {
                setTitle(device.getDeviceName());
                ((ActCentralAirGatewayVM) this.mViewModel).controlObject.setValue(device);
                Floor floor = Injection.repo().home().getFloor(device.getFloorId());
                if (floor != null) {
                    ((ActCentralAirGatewayVM) this.mViewModel).floorName = floor.getFloorName();
                }
                Room room = Injection.repo().home().getRoom(device.getRoomId());
                if (room != null) {
                    ((ActCentralAirGatewayVM) this.mViewModel).roomName = room.getRoomName();
                }
                this.deviceParam = (BleParam) device.getParam(BleParam.class);
            }
            if (device.isSubDevice() && device.getMacdeviceid() == ((ActCentralAirGatewayVM) this.mViewModel).controlObject.getValue().getDeviceId() && (extParam = device.getExtParam()) != null && extParam.contains("\"ACType\":1")) {
                this.cacheSubDeviceList.add(device);
            }
        }
        ((ActCentralAirGatewayVM) this.mViewModel).saveDevice.addAll(this.cacheSubDeviceList);
        this.framIndex = 0;
        this.sum = 0;
        this.subDeviceParamList.clear();
        searchSubDevice();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void searchSubDevice() {
        LHomeLog.i(getClass(), "start search subdevice...");
        showLoadingDialog(getString(R.string.searching), 30000);
        ((ActCentralAirGatewayVM) this.mViewModel).getSubDevice(this.framIndex, this.iSendCallback);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        super.showContent();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
    }

    private void initRvData() {
        MultipleItemRvAdapter<Device, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<Device, BaseViewHolder>(((ActCentralAirGatewayVM) this.mViewModel).subDeviceList.getValue()) { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAirGateway.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(Device centralAirSubDeviceItem) {
                return 0;
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new BaseItemProvider<Device, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAirGateway.4.1
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.act_central_air_sub_item;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 0;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, Device content, int position) {
                        helper.setText(R.id.tv_name, content.getDeviceName()).setText(R.id.tv_position, content.getFloorName() + content.getRoomName());
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void onClick(BaseViewHolder helper, Device data, int position) {
                        NavUtils.destination(ActCentralAc.class).withLong(com.ltech.smarthome.utils.Constants.CONTROL_ID, data.getId()).withBoolean(com.ltech.smarthome.utils.Constants.CENTRAL_GATEWAY, true).withDefaultRequestCode().navigation(ActCentralAirGateway.this);
                    }
                });
            }
        };
        this.mAdapter = multipleItemRvAdapter;
        multipleItemRvAdapter.finishInitialize();
        this.mAdapter.bindToRecyclerView(((ActCentralAirGatewayBinding) this.mViewBinding).rvContent);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(1);
        ((ActCentralAirGatewayBinding) this.mViewBinding).rvContent.setLayoutManager(linearLayoutManager);
        ((ActCentralAirGatewayBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parseSubData(String data) {
        int i;
        int length = data.length() / SUB_DEVICE_LENGTH;
        LHomeLog.i(getClass(), "size=" + length);
        int length2 = data.length() % SUB_DEVICE_LENGTH;
        LHomeLog.i(getClass(), "modData=" + length2);
        if (length2 > 0) {
            this.framModData = data.substring(data.length() - length2);
        }
        for (int i2 = 0; i2 < length; i2++) {
            this.sum++;
            int i3 = SUB_DEVICE_LENGTH;
            String substring = data.substring(i2 * i3, (i2 * i3) + i3);
            CentralAirSubDeviceParam centralAirSubDeviceParam = new CentralAirSubDeviceParam(this.deviceParam.getUnicastAddress(), substring, getString(R.string.central_air) + this.sum, ((ActCentralAirGatewayVM) this.mViewModel).controlObject.getValue().getFloorName() + ((ActCentralAirGatewayVM) this.mViewModel).controlObject.getValue().getRoomName());
            while (true) {
                if (i < ((ActCentralAirGatewayVM) this.mViewModel).saveDevice.size()) {
                    CentralAirSubDeviceParam centralAirSubDeviceParam2 = (CentralAirSubDeviceParam) ((ActCentralAirGatewayVM) this.mViewModel).saveDevice.get(i).getParam(CentralAirSubDeviceParam.class);
                    i = (centralAirSubDeviceParam2.inAddr == centralAirSubDeviceParam.inAddr && centralAirSubDeviceParam2.outAddr == centralAirSubDeviceParam.outAddr) ? 0 : i + 1;
                } else {
                    LHomeLog.i(getClass(), "save device param...." + this.sum);
                    this.subDeviceParamList.add(centralAirSubDeviceParam);
                    break;
                }
            }
        }
        if (this.framIndex + 1 == this.totalfram) {
            for (int i4 = 0; i4 < this.subDeviceParamList.size(); i4++) {
                saveDevice(this.subDeviceParamList.get(i4), SAVE_SUB_INTERVAL_TIME * i4);
            }
        }
    }

    private void saveDevice(CentralAirSubDeviceParam centralAirSubDeviceParam, int intervalTime) {
        Message message = new Message();
        message.what = HANDLE_SAVE_SUBDEVICE;
        message.obj = centralAirSubDeviceParam;
        this.subDeviceHandler.sendMessageDelayed(message, intervalTime);
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.subDeviceHandler.removeCallbacksAndMessages(null);
        MessageManager.getInstance().setGetCentralAirSubData(null);
    }

    private void updateUi(int size) {
        if (size <= 0 || this.mViewBinding == 0) {
            return;
        }
        if (((ActCentralAirGatewayBinding) this.mViewBinding).ivEmpty.getVisibility() != 8) {
            ((ActCentralAirGatewayBinding) this.mViewBinding).ivEmpty.setVisibility(8);
            ((ActCentralAirGatewayBinding) this.mViewBinding).tvEmptyTip.setVisibility(8);
            ((ActCentralAirGatewayBinding) this.mViewBinding).btSearch.setVisibility(8);
            ((ActCentralAirGatewayBinding) this.mViewBinding).tvSearchSum.setVisibility(0);
            ((ActCentralAirGatewayBinding) this.mViewBinding).ivSearchAdd.setVisibility(0);
            ((ActCentralAirGatewayBinding) this.mViewBinding).rvContent.setVisibility(0);
            ((ActCentralAirGatewayBinding) this.mViewBinding).rvContent.setVisibility(0);
        }
        ((ActCentralAirGatewayBinding) this.mViewBinding).tvSearchSum.setText(getString(R.string.sub_device) + size);
    }
}