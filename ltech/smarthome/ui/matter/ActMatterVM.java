package com.ltech.smarthome.ui.matter;

import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.product.ProductId;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActMatterVM extends BaseViewModel {
    public MutableLiveData<List<Device>> data = new MutableLiveData<>();

    public void loadMatterDevice() {
        ArrayList arrayList = new ArrayList();
        Place selPlace = Injection.repo().home().getSelPlace();
        if (selPlace != null) {
            for (Device device : Injection.repo().device().getDeviceListByPlaceId(selPlace.getPlaceId())) {
                if (ProductId.ID_HOME_KIT.equals(device.getProductId()) && ((BleParam) device.getParam(BleParam.class)).getBleType() == 270) {
                    arrayList.add(device);
                }
            }
        }
        this.data.setValue(arrayList);
    }
}