package com.ltech.smarthome.ui.device.mesh_gateway;

import android.view.View;
import androidx.lifecycle.MediatorLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.ui.config.ConfigHelper;
import com.ltech.smarthome.ui.device.ir.ActAddIrDevice;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActMeshGatewayVM extends BaseViewModel {
    public Device controlDevice;
    public long controlId;
    public Listing<Floor> mRequest;
    public long placeId;
    public MediatorLiveData<List<Room>> roomList = new MediatorLiveData<>();
    public MediatorLiveData<List<Floor>> floorList = new MediatorLiveData<>();
    public MediatorLiveData<List<Device>> deviceList = new MediatorLiveData<>();
    public SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();
    public BindingCommand<View> clickCommand = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActMeshGatewayVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (view.getId() != R.id.iv_add) {
            return;
        }
        addIrDevice();
    }

    public void addIrDevice() {
        if (getCurrentPlace().isOwner() || getCurrentPlace().isManager()) {
            ConfigHelper.instance().reset();
            if (this.controlDevice.getParam() != null && this.controlDevice.getParam(BleParam.class) != null) {
                BleParam bleParam = (BleParam) this.controlDevice.getParam(BleParam.class);
                ConfigHelper.instance().unicastAddress = bleParam.getUnicastAddress();
            }
            ConfigHelper.instance().placeId = this.controlDevice.getPlaceId();
            ConfigHelper.instance().floorId = this.controlDevice.getFloorId();
            ConfigHelper.instance().roomId = this.controlDevice.getRoomId();
            ConfigHelper.instance().macdeviceid = this.controlDevice.getDeviceId();
            ConfigHelper.instance().mac = this.controlDevice.getWifiMac();
            navigation(NavUtils.destination(ActAddIrDevice.class).withString(Constants.PRODUCT_ID, this.controlDevice.getProductId()));
            return;
        }
        this.showNoPermissionDialogEvent.call();
    }

    public String getPlaceInfo(long floorId, long roomId) {
        StringBuilder sb = new StringBuilder();
        if (this.floorList.getValue() != null) {
            Iterator<Floor> it = this.floorList.getValue().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Floor next = it.next();
                if (floorId == next.getFloorId()) {
                    sb.append(next.getFloorName());
                    break;
                }
            }
        }
        if (this.roomList.getValue() != null) {
            Iterator<Room> it2 = this.roomList.getValue().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Room next2 = it2.next();
                if (roomId == next2.getRoomId()) {
                    sb.append(next2.getRoomName());
                    break;
                }
            }
        }
        return sb.toString();
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }
}