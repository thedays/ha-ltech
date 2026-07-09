package com.ltech.smarthome.ui.device.super_panel.sp485;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.Rs485ExtParam;
import com.ltech.smarthome.model.device_param.SuperPanelExtParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.response.rs8.Rs8CategoryResponse;
import com.ltech.smarthome.ui.device.cg485.ActCg485SettingVM;
import com.ltech.smarthome.ui.device.cg485.Cg485Helper;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSp485VM extends ActCg485SettingVM {
    public long categoryId;
    public long controlId;
    public MutableLiveData<List<item>> data = new MutableLiveData<>();
    public Device device;
    public long placeId;

    public void initCategoryData() {
        final ArrayList arrayList = new ArrayList();
        arrayList.add(new item(0, getContext().getString(R.string.str_ble_to_485)));
        arrayList.add(new item(1, getContext().getString(R.string.mode_diy), new Rs8CategoryResponse.Category(-999L), 3));
        arrayList.add(new item(0, getContext().getString(R.string.str_485_to_ble)));
        arrayList.add(new item(1, getContext().getString(R.string.mode_diy), new Rs8CategoryResponse.Category(-998L), 3));
        this.data.setValue(arrayList);
        ((ObservableSubscribeProxy) Injection.net().queryRs8Category().delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.sp485.ActSp485VM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSp485VM.this.lambda$initCategoryData$0((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.super_panel.sp485.ActSp485VM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSp485VM.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<Rs8CategoryResponse>() { // from class: com.ltech.smarthome.ui.device.super_panel.sp485.ActSp485VM.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Rs8CategoryResponse response) throws Exception {
                if (response == null || response.getRows() == null || response.getRows().isEmpty()) {
                    return;
                }
                ArrayList arrayList2 = new ArrayList();
                for (Rs8CategoryResponse.Category category : response.getRows()) {
                    if (arrayList2.isEmpty()) {
                        arrayList2.add(new item(1, "", category, 1));
                    } else {
                        arrayList2.add(new item(1, "", category, 0));
                    }
                }
                arrayList.addAll(1, arrayList2);
                ActSp485VM.this.data.setValue(arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initCategoryData$0(Disposable disposable) throws Exception {
        showLoadingDialog(getContext().getString(R.string.loading));
    }

    public void loadSubDevice() {
        ArrayList arrayList = new ArrayList();
        List<Device> subDevice = Injection.repo().device().getSubDevice(this.placeId, this.device.getDeviceId());
        if (!subDevice.isEmpty()) {
            arrayList.add(new item(0, getContext().getString(R.string.str_ble_to_485)));
            ArrayList arrayList2 = new ArrayList();
            for (Device device : subDevice) {
                if (arrayList2.isEmpty()) {
                    arrayList2.add(new item(1, device.getDeviceName(), ProductRepository.getProductIcon(device), device, 1));
                } else if (arrayList2.size() == subDevice.size() - 1) {
                    arrayList2.add(new item(1, device.getDeviceName(), ProductRepository.getProductIcon(device), device, 3));
                } else if (subDevice.size() == 1) {
                    arrayList2.add(new item(1, device.getDeviceName(), ProductRepository.getProductIcon(device), device, 2));
                } else {
                    arrayList2.add(new item(1, device.getDeviceName(), ProductRepository.getProductIcon(device), device, 0));
                }
            }
            arrayList.addAll(arrayList2);
        }
        SuperPanelExtParam superPanelExtParam = (SuperPanelExtParam) this.device.getExtParam(SuperPanelExtParam.class);
        if (superPanelExtParam != null && !superPanelExtParam.getToBleList().isEmpty()) {
            arrayList.add(new item(0, getContext().getString(R.string.str_485_to_ble)));
            ArrayList arrayList3 = new ArrayList();
            int i = 0;
            for (Rs485ExtParam.Category category : superPanelExtParam.getToBleList()) {
                if (arrayList3.isEmpty()) {
                    arrayList3.add(new item(1, category.getCategoryName(), Cg485Helper.getDeviceImage(getContext(), category.getColorIdx()), 1, i));
                } else if (arrayList3.size() == subDevice.size() - 1) {
                    arrayList3.add(new item(1, category.getCategoryName(), Cg485Helper.getDeviceImage(getContext(), category.getColorIdx()), 3, i));
                } else if (subDevice.size() == 1) {
                    arrayList3.add(new item(1, category.getCategoryName(), Cg485Helper.getDeviceImage(getContext(), category.getColorIdx()), 2, i));
                } else {
                    arrayList3.add(new item(1, category.getCategoryName(), Cg485Helper.getDeviceImage(getContext(), category.getColorIdx()), 0, i));
                }
                i++;
            }
            arrayList.addAll(arrayList3);
        }
        this.data.setValue(arrayList);
    }

    public static class item {
        public static final int CONTENT = 1;
        public static final int HEAD = 0;
        private int background;
        private Rs8CategoryResponse.Category category;
        private Device device;
        private int img;
        private int pos;
        private String title;
        private int type;

        public item(int type, String title) {
            this.type = type;
            this.title = title;
        }

        public item(int type, String title, int img, Device device, int background) {
            this.type = type;
            this.title = title;
            this.img = img;
            this.device = device;
            this.background = background;
        }

        public item(int type, String title, Rs8CategoryResponse.Category category, int background) {
            this.type = type;
            this.title = title;
            this.category = category;
            this.background = background;
        }

        public item(int type, String title, int img, int background, int pos) {
            this.type = type;
            this.title = title;
            this.img = img;
            this.background = background;
            this.pos = pos;
        }

        public int getPos() {
            return this.pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }

        public int getType() {
            return this.type;
        }

        public String getTitle() {
            return this.title;
        }

        public int getImg() {
            return this.img;
        }

        public Device getDevice() {
            return this.device;
        }

        public Rs8CategoryResponse.Category getCategory() {
            return this.category;
        }

        public int getBackground() {
            return this.background;
        }
    }
}