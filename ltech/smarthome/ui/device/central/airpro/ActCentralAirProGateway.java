package com.ltech.smarthome.ui.device.central.airpro;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActCentralAirProGatewayBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.CentralAirSubDeviceParam;
import com.ltech.smarthome.model.device_param.FloorHeatSubDeviceParam;
import com.ltech.smarthome.model.device_param.FreshAirSubDeviceParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.product.ProductInfo;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.central.air.FtSubDevice;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.productBle.CmdBleFactory;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActCentralAirProGateway extends BaseControlActivity<ActCentralAirProGatewayBinding, ActCentralAirProGatewayVM> {
    private static int HANDLE_SAVE_FLOOR_HEAT_DEVICE = 4;
    private static int HANDLE_SAVE_NEW_AIR_DEVICE = 3;
    private static int HANDLE_SAVE_SUBDEVICE = 1;
    private static int HANDLE_SEARCH_SUBDEVICE = 2;
    private static int SAVE_SUB_INTERVAL_TIME = 200;
    BleParam deviceParam;
    public FtSubDevice selectFt;
    private Handler subDeviceHandler;
    int framIndex = 0;
    List<Device> cacheSubDeviceList = new ArrayList();
    int totalSubDevice = 0;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_central_air_pro_gateway;
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
        ((ActCentralAirProGatewayBinding) this.mViewBinding).title.ivSearch.setImageResource(R.mipmap.ic_air_search);
        ((ActCentralAirProGatewayBinding) this.mViewBinding).title.ivSearch.setVisibility(0);
        ((ActCentralAirProGatewayBinding) this.mViewBinding).title.ivSearch.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralAirProGateway$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActCentralAirProGateway.this.lambda$initView$0(view);
            }
        });
        this.subDeviceHandler = new Handler() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralAirProGateway.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == ActCentralAirProGateway.HANDLE_SAVE_SUBDEVICE) {
                    ((ActCentralAirProGatewayVM) ActCentralAirProGateway.this.mViewModel).saveSearchSubDevice((CentralAirSubDeviceParam) msg.obj);
                    return;
                }
                if (msg.what == ActCentralAirProGateway.HANDLE_SAVE_NEW_AIR_DEVICE) {
                    ((ActCentralAirProGatewayVM) ActCentralAirProGateway.this.mViewModel).saveNewAirDevice((FreshAirSubDeviceParam) msg.obj);
                } else if (msg.what == ActCentralAirProGateway.HANDLE_SAVE_FLOOR_HEAT_DEVICE) {
                    ((ActCentralAirProGatewayVM) ActCentralAirProGateway.this.mViewModel).saveFloorHeatDevice((FloorHeatSubDeviceParam) msg.obj);
                } else if (msg.what == ActCentralAirProGateway.HANDLE_SEARCH_SUBDEVICE) {
                    ActCentralAirProGateway.this.searchSubDevice();
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        ((ActCentralAirProGatewayVM) this.mViewModel).searchDevice.call();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        NavHelper.goSetting(((ActCentralAirProGatewayVM) this.mViewModel).controlObject.getValue());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActCentralAirProGatewayVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActCentralAirProGatewayVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActCentralAirProGatewayVM) this.mViewModel).subDeviceList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralAirProGateway$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCentralAirProGateway.this.lambda$startObserve$1((List) obj);
            }
        });
        Device deviceById = Injection.repo().device().getDeviceById(((ActCentralAirProGatewayVM) this.mViewModel).controlId);
        ((ActCentralAirProGatewayVM) this.mViewModel).controlObject.setValue(deviceById);
        setTitle(deviceById.getDeviceName());
        ProductInfo bleProductInfoByType = ProductRepository.getBleProductInfoByType(deviceById);
        if (bleProductInfoByType != null && bleProductInfoByType.getProductId().equals(ProductId.ID_CENTRE_AIR_PRO_GATEWAY) && bleProductInfoByType.getSubProductKey().equals("0D")) {
            ((ActCentralAirProGatewayBinding) this.mViewBinding).appCompatImageView11.setBackgroundResource(R.mipmap.airmax_pic_bg);
        }
        ((ActCentralAirProGatewayVM) this.mViewModel).initSubDeviceList();
        initViewpager();
        ((ActCentralAirProGatewayVM) this.mViewModel).searchDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralAirProGateway$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCentralAirProGateway.this.lambda$startObserve$2((Void) obj);
            }
        });
        ((ActCentralAirProGatewayVM) this.mViewModel).startUpdateCentralAir.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralAirProGateway$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCentralAirProGateway.this.lambda$startObserve$3((Void) obj);
            }
        });
        ((ActCentralAirProGatewayVM) this.mViewModel).startUpdateNewAir.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralAirProGateway$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCentralAirProGateway.this.lambda$startObserve$4((Void) obj);
            }
        });
        ((ActCentralAirProGatewayVM) this.mViewModel).startUpdateFloorHeat.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralAirProGateway$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCentralAirProGateway.this.lambda$startObserve$5((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(List list) {
        int size = ((ActCentralAirProGatewayVM) this.mViewModel).subDeviceList.getValue().size();
        if (size > 0 && size == ((ActCentralAirProGatewayVM) this.mViewModel).centralAirByteDataList.size()) {
            ((ActCentralAirProGatewayVM) this.mViewModel).getNewAirDevice();
        } else if (size > 0 && size == ((ActCentralAirProGatewayVM) this.mViewModel).newAirByteDataList.size() + ((ActCentralAirProGatewayVM) this.mViewModel).centralAirByteDataList.size()) {
            ((ActCentralAirProGatewayVM) this.mViewModel).getFloorHeatDevice();
        } else if (size > 0 && size == ((ActCentralAirProGatewayVM) this.mViewModel).newAirByteDataList.size() + ((ActCentralAirProGatewayVM) this.mViewModel).centralAirByteDataList.size() + ((ActCentralAirProGatewayVM) this.mViewModel).floorHeatByteDataList.size()) {
            showSuccessDialog(String.format(getString(R.string.total_search_device), Integer.valueOf(size)));
        }
        this.selectFt.getData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r9) {
        Injection.repo().device().getDeviceList(this, ((ActCentralAirProGatewayVM) this.mViewModel).placeId, -1L, -1L).data();
        List<Device> value = Injection.repo().device().getDeviceListCache().getValue();
        this.cacheSubDeviceList.clear();
        ((ActCentralAirProGatewayVM) this.mViewModel).saveDevice.clear();
        for (Device device : value) {
            if (device.getId() == ((ActCentralAirProGatewayVM) this.mViewModel).controlId) {
                setTitle(device.getDeviceName());
                ((ActCentralAirProGatewayVM) this.mViewModel).controlObject.setValue(device);
                Floor floor = Injection.repo().home().getFloor(device.getFloorId());
                if (floor != null) {
                    ((ActCentralAirProGatewayVM) this.mViewModel).floorName = floor.getFloorName();
                }
                Room room = Injection.repo().home().getRoom(device.getRoomId());
                if (room != null) {
                    ((ActCentralAirProGatewayVM) this.mViewModel).roomName = room.getRoomName();
                }
                this.deviceParam = (BleParam) device.getParam(BleParam.class);
            }
            if (device.isSubDevice() && device.getMacdeviceid() == ((ActCentralAirProGatewayVM) this.mViewModel).controlObject.getValue().getDeviceId()) {
                String extParam = device.getExtParam();
                if (extParam != null && extParam.contains("\"ACType\":1")) {
                    this.cacheSubDeviceList.add(device);
                }
                if (extParam != null && extParam.contains("\"ACType\":2")) {
                    this.cacheSubDeviceList.add(device);
                }
                if (extParam != null && extParam.contains("\"ACType\":3")) {
                    this.cacheSubDeviceList.add(device);
                }
            }
        }
        ((ActCentralAirProGatewayVM) this.mViewModel).saveDevice.addAll(this.cacheSubDeviceList);
        ((ActCentralAirProGatewayVM) this.mViewModel).centralAirParamList.clear();
        ((ActCentralAirProGatewayVM) this.mViewModel).newAirParamList.clear();
        ((ActCentralAirProGatewayVM) this.mViewModel).floorHeatParamList.clear();
        searchSubDevice();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r3) {
        for (int i = 0; i < ((ActCentralAirProGatewayVM) this.mViewModel).centralAirParamList.size(); i++) {
            this.totalSubDevice = i;
            saveDevice(((ActCentralAirProGatewayVM) this.mViewModel).centralAirParamList.get(i), SAVE_SUB_INTERVAL_TIME * i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r3) {
        for (int i = 0; i < ((ActCentralAirProGatewayVM) this.mViewModel).newAirParamList.size(); i++) {
            saveNewAirDevice(((ActCentralAirProGatewayVM) this.mViewModel).newAirParamList.get(i), SAVE_SUB_INTERVAL_TIME * i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Void r3) {
        for (int i = 0; i < ((ActCentralAirProGatewayVM) this.mViewModel).floorHeatParamList.size(); i++) {
            saveFloorHeatDevice(((ActCentralAirProGatewayVM) this.mViewModel).floorHeatParamList.get(i), SAVE_SUB_INTERVAL_TIME * i);
        }
    }

    private void initViewpager() {
        ((ActCentralAirProGatewayBinding) this.mViewBinding).viewpager.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralAirProGateway.2
            @Override // androidx.viewpager2.adapter.FragmentStateAdapter
            public Fragment createFragment(int position) {
                return ((ActCentralAirProGatewayVM) ActCentralAirProGateway.this.mViewModel).subDeviceFtList.get(position);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return ((ActCentralAirProGatewayVM) ActCentralAirProGateway.this.mViewModel).subDeviceFtList.size();
            }
        });
        ((ActCentralAirProGatewayBinding) this.mViewBinding).viewpager.setOffscreenPageLimit(-1);
        new TabLayoutMediator(((ActCentralAirProGatewayBinding) this.mViewBinding).tabs, ((ActCentralAirProGatewayBinding) this.mViewBinding).viewpager, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralAirProGateway$$ExternalSyntheticLambda5
            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
            public final void onConfigureTab(TabLayout.Tab tab, int i) {
                ActCentralAirProGateway.this.lambda$initViewpager$6(tab, i);
            }
        }).attach();
        ((ActCentralAirProGatewayBinding) this.mViewBinding).viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralAirProGateway.3
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int position) {
                if (position == 1) {
                    ((ActCentralAirProGatewayBinding) ActCentralAirProGateway.this.mViewBinding).tabs.setBackground(ActCentralAirProGateway.this.getDrawable(R.mipmap.bg_central_air_pro_tab_2));
                } else if (position == 2) {
                    ((ActCentralAirProGatewayBinding) ActCentralAirProGateway.this.mViewBinding).tabs.setBackground(ActCentralAirProGateway.this.getDrawable(R.mipmap.bg_central_air_pro_tab_3));
                } else {
                    ((ActCentralAirProGatewayBinding) ActCentralAirProGateway.this.mViewBinding).tabs.setBackground(ActCentralAirProGateway.this.getDrawable(R.mipmap.bg_central_air_pro_tab_1));
                }
                ActCentralAirProGateway actCentralAirProGateway = ActCentralAirProGateway.this;
                actCentralAirProGateway.selectFt = ((ActCentralAirProGatewayVM) actCentralAirProGateway.mViewModel).subDeviceFtList.get(position);
                ActCentralAirProGateway.this.selectFt.getData();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViewpager$6(TabLayout.Tab tab, int i) {
        tab.setText(getResources().getStringArray(R.array.central_air_pro_tab)[i]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void searchSubDevice() {
        LHomeLog.i(getClass(), "start search subdevice...");
        showLoadingDialog(getString(R.string.searching), 35000);
        CmdAssistant.getDeviceAssistant(((ActCentralAirProGatewayVM) this.mViewModel).controlObject.getValue(), new int[0]).sendCmdWithResult(this, CmdBleFactory.queryCentralAcFeature(), new IAction() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralAirProGateway$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActCentralAirProGateway.this.lambda$searchSubDevice$7((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$searchSubDevice$7(ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            ((ActCentralAirProGatewayVM) this.mViewModel).deviceFeature = responseMsg.getResData().substring(16);
        }
        ((ActCentralAirProGatewayVM) this.mViewModel).centralAirByteDataList.clear();
        ((ActCentralAirProGatewayVM) this.mViewModel).getSubDevice(this.framIndex);
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

    private void saveDevice(CentralAirSubDeviceParam centralAirSubDeviceParam, int intervalTime) {
        Message message = new Message();
        message.what = HANDLE_SAVE_SUBDEVICE;
        message.obj = centralAirSubDeviceParam;
        this.subDeviceHandler.sendMessageDelayed(message, intervalTime);
    }

    private void saveNewAirDevice(FreshAirSubDeviceParam freshAirSubDeviceParam, int intervalTime) {
        Message message = new Message();
        message.what = HANDLE_SAVE_NEW_AIR_DEVICE;
        message.obj = freshAirSubDeviceParam;
        this.subDeviceHandler.sendMessageDelayed(message, intervalTime);
    }

    private void saveFloorHeatDevice(FloorHeatSubDeviceParam floorHeatSubDeviceParam, int intervalTime) {
        Message message = new Message();
        message.what = HANDLE_SAVE_FLOOR_HEAT_DEVICE;
        message.obj = floorHeatSubDeviceParam;
        this.subDeviceHandler.sendMessageDelayed(message, intervalTime);
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.subDeviceHandler.removeCallbacksAndMessages(null);
        MessageManager.getInstance().setGetCentralAirSubData(null);
    }
}