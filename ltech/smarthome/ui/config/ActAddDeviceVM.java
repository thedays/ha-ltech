package com.ltech.smarthome.ui.config;

import android.text.TextUtils;
import android.util.ArrayMap;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.ltech.nfc.source.SourceModel;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.blemesh.bean.ExtendedBluetoothDevice;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.product.ProductInfo;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.nfc.AddVirtualHelper;
import com.ltech.smarthome.ui.config.ActMeshScanVM;
import com.ltech.smarthome.ui.device.ir.ActIrDiy;
import com.ltech.smarthome.ui.device.ir.ActListSelectBrand;
import com.ltech.smarthome.ui.device.ir.ActSelectBrand;
import com.ltech.smarthome.ui.device.ir.ActSelectOperators;
import com.ltech.smarthome.ui.group.ActSelectDeviceNew;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.RoomPickHelper;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.view.dialog.SetBleTypeDialog;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActAddDeviceVM extends BaseViewModel {
    public ObservableList<ActMeshScanVM.ScanItem> deviceList;
    public Device filerDevice;
    public long floorId;
    public long placeId;
    public Listing<Floor> request;
    public long roomId;
    public MediatorLiveData<List<Floor>> floorList = new MediatorLiveData<>();
    public MediatorLiveData<List<Room>> roomList = new MediatorLiveData<>();
    public MediatorLiveData<List<Device>> gatewayList = new MediatorLiveData<>();
    public RoomPickHelper roomPickHelper = new RoomPickHelper();
    public MutableLiveData<List<SourceModel>> virtualData = new MutableLiveData<>();
    public MutableLiveData<List<Device>> addList = new MutableLiveData<>(new ArrayList());
    public MutableLiveData<Boolean> startScanLiveData = new MutableLiveData<>(false);
    public SingleLiveEvent<Void> refreshTypeItemEvent = new SingleLiveEvent<>();
    public List<ScanTypeItem> scanTypeList = new ArrayList();
    public List<Device> moduleDeviceList = new ArrayList();
    public List<Integer> categoryList = new ArrayList();
    public ArrayMap<Integer, List<ProductItem>> productItemMap = new ArrayMap<>();

    public ActAddDeviceVM() {
        List<Integer> list = this.categoryList;
        Integer valueOf = Integer.valueOf(R.string.category_gateway);
        list.add(valueOf);
        this.productItemMap.put(valueOf, getGatewayProduct());
        List<Integer> list2 = this.categoryList;
        Integer valueOf2 = Integer.valueOf(R.string.category_panel);
        list2.add(valueOf2);
        this.productItemMap.put(valueOf2, getRemoteControllerProduct());
        List<Integer> list3 = this.categoryList;
        Integer valueOf3 = Integer.valueOf(R.string.category_light);
        list3.add(valueOf3);
        this.productItemMap.put(valueOf3, getLightProduct());
        List<Integer> list4 = this.categoryList;
        Integer valueOf4 = Integer.valueOf(R.string.category_module);
        list4.add(valueOf4);
        this.productItemMap.put(valueOf4, getModuleProduct());
        List<Integer> list5 = this.categoryList;
        Integer valueOf5 = Integer.valueOf(R.string.category_ir_cotrol);
        list5.add(valueOf5);
        this.productItemMap.put(valueOf5, getHomeProduct());
        List<Integer> list6 = this.categoryList;
        Integer valueOf6 = Integer.valueOf(R.string.category_sensor);
        list6.add(valueOf6);
        this.productItemMap.put(valueOf6, getSensorProduct());
        List<Integer> list7 = this.categoryList;
        Integer valueOf7 = Integer.valueOf(R.string.category_led_control);
        list7.add(valueOf7);
        this.productItemMap.put(valueOf7, getPanelProduct());
        if (Utils.isYYBFlavor(ActivityUtils.getTopActivity())) {
            this.categoryList.add(Integer.valueOf(R.string.category_camera));
            this.productItemMap.put(Integer.valueOf(R.string.category_camera), getCameraProduct());
        }
        this.categoryList.add(Integer.valueOf(R.string.category_group));
        this.productItemMap.put(Integer.valueOf(R.string.category_group), getGroupProduct());
        this.deviceList = new ObservableArrayList();
        initScanTypeList();
    }

    public static List<ProductItem> getIrProduct() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ProductItem(Injection.productFactory().createTV(), ActSelectBrand.class));
        arrayList.add(new ProductItem(Injection.productFactory().createStb(), ActSelectOperators.class));
        arrayList.add(new ProductItem(Injection.productFactory().createTvBox(), ActSelectBrand.class));
        arrayList.add(new ProductItem(Injection.productFactory().createAc(), ActSelectBrand.class));
        arrayList.add(new ProductItem(Injection.productFactory().createFan(), ActSelectBrand.class));
        arrayList.add(new ProductItem(Injection.productFactory().createProjector(), ActSelectBrand.class));
        arrayList.add(new ProductItem(Injection.productFactory().createAirCleaner(), ActSelectBrand.class));
        arrayList.add(new ProductItem(Injection.productFactory().createWaterHeater(), ActSelectBrand.class));
        arrayList.add(new ProductItem(Injection.productFactory().createIrDiy(), ActIrDiy.class));
        arrayList.add(new ProductItem(Injection.productFactory().createCurtain(), ActListSelectBrand.class));
        arrayList.add(new ProductItem(Injection.productFactory().createHanger(), ActListSelectBrand.class));
        return arrayList;
    }

    public static List<ProductItem> getHomeProduct() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ProductItem(ActivityUtils.getTopActivity().getString(R.string.device_bt)));
        arrayList.add(new ProductItem(Injection.productFactory().createBleCurtainCGCUR15(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleCurtainCGCURH3(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(ActivityUtils.getTopActivity().getString(R.string.device_ir)));
        arrayList.add(new ProductItem(Injection.productFactory().createTV(), ActSelectBrand.class));
        arrayList.add(new ProductItem(Injection.productFactory().createStb(), ActSelectOperators.class));
        arrayList.add(new ProductItem(Injection.productFactory().createTvBox(), ActSelectBrand.class));
        arrayList.add(new ProductItem(Injection.productFactory().createAc(), ActSelectBrand.class));
        arrayList.add(new ProductItem(Injection.productFactory().createFan(), ActSelectBrand.class));
        arrayList.add(new ProductItem(Injection.productFactory().createProjector(), ActSelectBrand.class));
        arrayList.add(new ProductItem(Injection.productFactory().createAirCleaner(), ActSelectBrand.class));
        arrayList.add(new ProductItem(Injection.productFactory().createWaterHeater(), ActSelectBrand.class));
        arrayList.add(new ProductItem(Injection.productFactory().createIrDiy(), ActIrDiy.class));
        arrayList.add(new ProductItem(ActivityUtils.getTopActivity().getString(R.string.device_rf)));
        arrayList.add(new ProductItem(Injection.productFactory().createCurtain(), ActListSelectBrand.class));
        arrayList.add(new ProductItem(Injection.productFactory().createHanger(), ActListSelectBrand.class));
        return arrayList;
    }

    private List<ProductItem> getLightProduct() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ProductItem(Injection.productFactory().createBleLightDim(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleLightCt(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleLightRgb(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleLightRgbw(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleLightRgbwy(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createKbs(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createKbs1(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleSwitch(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createRcB1(), ActStepsIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createRcB2(), ActStepsIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createRcB5(), ActStepsIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createRcB8(), ActStepsIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createRc4S(), ActStepsIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createRc4(), ActStepsIntroduction.class));
        return arrayList;
    }

    private List<ProductItem> getGatewayProduct() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ProductItem(Injection.productFactory().createHomeKit(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createAndroidSuperPanelG4Max(), ActProductIntroduction1.class));
        arrayList.add(new ProductItem(Injection.productFactory().createAndroidSuperPanel12S(), ActProductIntroduction1.class));
        arrayList.add(new ProductItem(Injection.productFactory().createAndroidSuperPanel6S(), ActProductIntroduction1.class));
        arrayList.add(new ProductItem(Injection.productFactory().createAndroidSuperPanelPro(), ActProductIntroduction1.class));
        arrayList.add(new ProductItem(Injection.productFactory().createAndroidSuperPanel4S(), ActProductIntroduction1.class));
        arrayList.add(new ProductItem(Injection.productFactory().createAndroidSuperPanelMini(), ActProductIntroduction1.class));
        arrayList.add(new ProductItem(Injection.productFactory().createAndroidSuperPanel(), ActProductIntroduction1.class));
        return arrayList;
    }

    private List<ProductItem> getModuleProduct() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ProductItem(Injection.productFactory().createRs8Module(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createRs485Module(), ActProductIntroduction.class));
        arrayList.add(centreAirMaxGateway());
        arrayList.add(centreAirProGateway());
        arrayList.add(new ProductItem(Injection.productFactory().createCgdPro(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleLightDam(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleLightGam(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleLightCgt(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleLightCgd(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleDryContact(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createDryContactToBle(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleMusicPlayer(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleHAM(), ActProductIntroduction.class));
        arrayList.add(irGateway());
        arrayList.add(centreAirGateway());
        return arrayList;
    }

    private List<ProductItem> getCameraProduct() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ProductItem(Injection.productFactory().createCamera(), ActProductIntroduction.class));
        return arrayList;
    }

    public ProductItem irGateway() {
        return new ProductItem(Injection.productFactory().createMeshGateway(), ActProductIntroduction.class);
    }

    public ProductItem centreAirGateway() {
        return new ProductItem(Injection.productFactory().createCentreAirGateway(), ActProductIntroduction.class);
    }

    public ProductItem centreAirProGateway() {
        return new ProductItem(Injection.productFactory().createCentreAirProGateway(), ActStepsIntroduction.class);
    }

    public ProductItem centreAirMaxGateway() {
        return new ProductItem(Injection.productFactory().createCentreAirMaxGateway(), ActStepsIntroduction.class);
    }

    private List<ProductItem> getPanelProduct() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ProductItem(Injection.productFactory().createKnobPanelE6M(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createKnobPanelE6A(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createKnobPanelE6T(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createEurPanelEB1(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createEurPanelEB2(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createEurPanelEB5(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createEurPanelEB6(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createEurPanelEB8(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleLightSpi(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createAsPanelU1S(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createAsPanelU2S(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createAsPanelU4S(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createAsPanelU5S(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createAsPanelUB8(), ActProductIntroduction.class));
        return arrayList;
    }

    private List<ProductItem> getRemoteControllerProduct() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ProductItem(Injection.productFactory().createKnobPanelGQPro(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createKnobPanelGQMax(), ActStepsIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createKnobRemoteGQ(), ActStepsIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createSwitchPanelG4(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createSwitchPanelG4Pro(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createSmartPanelG4TE(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createSwitchPanelS6(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createScenePanelS6B(), ActStepsIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createSwitchPanelS1Pro(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createSwitchPanelS2Pro(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createSwitchPanelS3Pro(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createSwitchPanelS6Pro(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createSwitchPanelSQPro(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createSwitchPanelSQ(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createSwitchPanelSQB(), ActStepsIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createSwitchPanelS1(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createSwitchPanelS2(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createSwitchPanelS3(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createSwitchPanelS4(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createSwitchPanelS4M(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createScenePanelS8M(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createSwitchPanelS1C(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createSwitchPanelS2C(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createSwitchPanelS3C(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createKeySwitch1(), ActStepsIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createKeySwitch2(), ActStepsIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createKeySwitch4(), ActStepsIntroduction.class));
        return arrayList;
    }

    private List<ProductItem> getGroupProduct() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupDim(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupCt(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupRgb(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupRgbw(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupRgbwy(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupRgbwyCC(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupSwitch(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupSwitchPanelS1(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupSwitchPanelS2(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupSwitchPanelS3(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupSwitchPanelS4(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupSwitchPanelS4M(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupSwitchPanelS6Pro(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupSwitchPanelG4(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupEb1(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupEb2(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupEb5(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupUb1(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupUb2(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupUb4(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupUb5(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupCurtain(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupDryCurtain(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupDryDreamCurtain(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupWaveSensorMR03(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupWaveSensorMR04(), ActSelectDeviceNew.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBleGroupWaveSensorMS03(), ActSelectDeviceNew.class));
        return arrayList;
    }

    private List<ProductItem> getSensorProduct() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ProductItem(Injection.productFactory().createDoorSensor(), ActStepsIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createWaveSensorMS3(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createWaveSensorMR3(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createWaveSensorMR4(), ActProductIntroduction.class));
        arrayList.add(new ProductItem(Injection.productFactory().createBodySensor(), ActStepsIntroduction.class));
        return arrayList;
    }

    public void initVirtualProductList(List<SourceModel> modelList) {
        Collections.sort(modelList, new Comparator() { // from class: com.ltech.smarthome.ui.config.ActAddDeviceVM$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ActAddDeviceVM.lambda$initVirtualProductList$0((SourceModel) obj, (SourceModel) obj2);
            }
        });
        this.categoryList.clear();
        List<Integer> list = this.categoryList;
        Integer valueOf = Integer.valueOf(R.string.category_light);
        list.add(valueOf);
        this.productItemMap.clear();
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ProductItem(getContext().getString(R.string.cc_source)));
        for (SourceModel sourceModel : modelList) {
            if (sourceModel.isBleSource() && !sourceModel.isConstantVoltage()) {
                arrayList.add(getProductItem(sourceModel));
            }
        }
        arrayList.add(new ProductItem(getContext().getString(R.string.cv_source)));
        for (SourceModel sourceModel2 : modelList) {
            if (sourceModel2.isBleSource() && sourceModel2.isConstantVoltage()) {
                arrayList.add(getProductItem(sourceModel2));
            }
        }
        arrayList.add(new ProductItem(getContext().getString(R.string.ble_switch_short)));
        for (SourceModel sourceModel3 : modelList) {
            if (sourceModel3.isBleSwitch()) {
                arrayList.add(getProductItem(sourceModel3));
            }
        }
        this.productItemMap.put(valueOf, new ArrayList(arrayList));
        arrayList.clear();
        for (SourceModel sourceModel4 : modelList) {
            if (sourceModel4.sourceType == 17 && !sourceModel4.isBleSwitch()) {
                arrayList.add(getProductItem(sourceModel4));
            }
        }
        if (!arrayList.isEmpty()) {
            this.categoryList.add(Integer.valueOf(R.string.category_panel));
            this.productItemMap.put(Integer.valueOf(R.string.category_panel), new ArrayList(arrayList));
        }
        arrayList.clear();
        for (SourceModel sourceModel5 : modelList) {
            if (sourceModel5.sourceType == 12) {
                arrayList.add(getProductItem(sourceModel5));
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        this.categoryList.add(Integer.valueOf(R.string.category_led_control));
        this.productItemMap.put(Integer.valueOf(R.string.category_led_control), new ArrayList(arrayList));
    }

    static /* synthetic */ int lambda$initVirtualProductList$0(SourceModel sourceModel, SourceModel sourceModel2) {
        int parseInt = Integer.parseInt(sourceModel.uuid.substring(4, 6), 16);
        int parseInt2 = Integer.parseInt(sourceModel2.uuid.substring(4, 6), 16);
        return parseInt == parseInt2 ? sourceModel.sourceName.compareTo(sourceModel2.sourceName) : parseInt - parseInt2;
    }

    private ProductItem getProductItem(SourceModel item) {
        return new ProductItem(ProductRepository.getBleProductInfoByType(item.uuid.substring(2, 4), item.uuid.substring(4, 6)), item.sourceName, item.sourceId);
    }

    public List<ProductItem> getProduct(int category) {
        return this.productItemMap.get(Integer.valueOf(category));
    }

    public static final class ProductItem {
        public int binNum;
        public String modelName;
        public Class navClz;
        public ProductInfo productInfo;
        public String title;

        public ProductItem(ProductInfo productInfo, Class navClz) {
            this.productInfo = productInfo;
            this.navClz = navClz;
        }

        public ProductItem(String title) {
            this.title = title;
        }

        public ProductItem(ProductInfo productInfo, String modelName, int binNum) {
            this.productInfo = productInfo;
            this.modelName = modelName;
            this.binNum = binNum;
        }

        public boolean isVirtual() {
            return !TextUtils.isEmpty(this.modelName) && this.binNum > 0;
        }
    }

    public String getLocationName(long deviceId) {
        StringBuilder sb = new StringBuilder();
        if (this.gatewayList.getValue() != null && this.floorList.getValue() != null && this.roomList.getValue() != null) {
            Iterator<Device> it = this.gatewayList.getValue().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Device next = it.next();
                if (deviceId == next.getDeviceId()) {
                    Iterator<Floor> it2 = this.floorList.getValue().iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        Floor next2 = it2.next();
                        if (next2.getFloorId() == next.getFloorId()) {
                            sb.append(next2.getFloorName());
                            break;
                        }
                    }
                    Iterator<Room> it3 = this.roomList.getValue().iterator();
                    while (true) {
                        if (!it3.hasNext()) {
                            break;
                        }
                        Room next3 = it3.next();
                        if (next3.getRoomId() == next.getRoomId()) {
                            sb.append(next3.getRoomName());
                            break;
                        }
                    }
                }
            }
        }
        return sb.toString();
    }

    public boolean refreshRssi(ExtendedBluetoothDevice bluetoothDevice) {
        for (int i = 0; i < this.deviceList.size(); i++) {
            ActMeshScanVM.ScanItem scanItem = this.deviceList.get(i);
            if (scanItem.matches(bluetoothDevice)) {
                scanItem.bluetoothDevice.setRssi(bluetoothDevice.getRssi());
                this.deviceList.set(i, scanItem);
                return true;
            }
        }
        return false;
    }

    public boolean addDevice(ExtendedBluetoothDevice bluetoothDevice) {
        ProductInfo bleProductInfoByType;
        if ((this.filerDevice != null && !bluetoothDevice.getAddress().replaceAll(Constants.COLON_SEPARATOR, "").equalsIgnoreCase(this.filerDevice.getWifiMac().replaceAll(Constants.COLON_SEPARATOR, ""))) || (bleProductInfoByType = ProductRepository.getBleProductInfoByType(bluetoothDevice.getProductType(), bluetoothDevice.getSubProductType())) == null) {
            return false;
        }
        if (this.filerDevice == null && (bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL) || bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI) || bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_4S) || bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_6S) || bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_12S) || bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO) || bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX))) {
            return false;
        }
        bluetoothDevice.setControlType(bleProductInfoByType.getControlType());
        ActMeshScanVM.ScanItem scanItem = new ActMeshScanVM.ScanItem(bluetoothDevice, false);
        scanItem.productInfo = bleProductInfoByType;
        scanItem.name = getDeviceName(bleProductInfoByType);
        scanItem.autoAdd = ProductRepository.canAutoAdd(bleProductInfoByType);
        this.deviceList.add(scanItem);
        convertScanItem(scanItem);
        this.refreshTypeItemEvent.call();
        return true;
    }

    public String getDeviceName(ProductInfo productInfo) {
        String str = "";
        String replace = ActivityUtils.getTopActivity().getString(productInfo.getAddNameRes()).replace("\n", "");
        if (productInfo.getProductId().equalsIgnoreCase(ProductId.ID_BLE_CURTAIN)) {
            replace = ActivityUtils.getTopActivity().getString(R.string.app_str_ble_curtain_name);
        }
        if (ProductRepository.isSuperPanel(productInfo.getProductId())) {
            return replace;
        }
        boolean z = true;
        int i = 0;
        while (z) {
            i++;
            if (LanguageUtils.isChinese(ActivityUtils.getTopActivity())) {
                str = replace + i;
            } else {
                str = replace + " " + i;
            }
            Iterator<ActMeshScanVM.ScanItem> it = this.deviceList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                if (it.next().name.equals(str)) {
                    z = true;
                    break;
                }
            }
            if (Injection.repo().device().isDeviceNameExist(this.placeId, str)) {
                z = true;
            }
        }
        return str;
    }

    public void showAddVirtualDialog(final ProductItem item) {
        this.roomPickHelper.resetObserve();
        this.roomPickHelper.startObserve(getLifecycleOwner(), this.placeId, SPUtils.getInstance().getLong(com.ltech.smarthome.utils.Constants.USER_CUR_ROOM_FOR_GROUP, -1L));
        final SetBleTypeDialog onSaveListener = SetBleTypeDialog.asDefault().setTitle(getContext().getString(R.string.add_device)).setContent(AddVirtualHelper.instance().getDeviceName(this.placeId, item)).setOnSaveListener(new SetBleTypeDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.config.ActAddDeviceVM.1
            @Override // com.ltech.smarthome.view.dialog.SetBleTypeDialog.OnSaveListener
            public void cancel() {
            }

            @Override // com.ltech.smarthome.view.dialog.SetBleTypeDialog.OnSaveListener
            public boolean onSave(String name, boolean changeType, int type, int outputType, int floorPos, int roomPos, int zoneControl, int controlType) {
                if (TextUtils.isEmpty(name.trim())) {
                    SmartToast.showShort(R.string.input_name);
                    return false;
                }
                ActAddDeviceVM.this.roomPickHelper.setSelectPosition(floorPos, roomPos);
                long selectFloorId = ActAddDeviceVM.this.roomPickHelper.getSelectFloorId();
                long selectRoomId = ActAddDeviceVM.this.roomPickHelper.getSelectRoomId();
                ConfigHelper.instance().placeId = ActAddDeviceVM.this.placeId;
                ConfigHelper.instance().floorId = selectFloorId;
                ConfigHelper.instance().roomId = selectRoomId;
                ConfigHelper.instance().productInfo = item.productInfo;
                ConfigHelper.instance().deviceName = name;
                if (!changeType) {
                    type = 0;
                }
                AddVirtualHelper.instance().init((BaseNormalActivity) ActivityUtils.getTopActivity(), item, type);
                AddVirtualHelper.instance().addVirtualDevice();
                return true;
            }
        });
        this.roomPickHelper.setCurrentFloorIdPos(this.floorId);
        this.roomPickHelper.setCurrentRoomIdPos(this.floorId, this.roomId);
        onSaveListener.controlType(item.productInfo.getControlType(), Integer.parseInt(SourceModel.getSourceModel(item.binNum).uuid.substring(0, 2), 16), 0);
        onSaveListener.setProductId(item.productInfo.getProductId());
        onSaveListener.setSelectRoom(this.roomPickHelper.canSetRoom()).setFloorList(this.roomPickHelper.getCurrentFloorNames()).setRoomList(this.roomPickHelper.getCurrentRoomNames()).setSelectFloorPosition(this.roomPickHelper.getSelectFloorPosition()).setSelectRoomPosition(this.roomPickHelper.getSelectRoomPosition()).setOnSelectFloorListener(new SetBleTypeDialog.OnSelectFloorListener() { // from class: com.ltech.smarthome.ui.config.ActAddDeviceVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.dialog.SetBleTypeDialog.OnSelectFloorListener
            public final void selectFloor(SetBleTypeDialog setBleTypeDialog, int i, String str) {
                ActAddDeviceVM.this.lambda$showAddVirtualDialog$1(onSaveListener, setBleTypeDialog, i, str);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAddVirtualDialog$1(SetBleTypeDialog setBleTypeDialog, SetBleTypeDialog setBleTypeDialog2, int i, String str) {
        setBleTypeDialog.setRoomList(this.roomPickHelper.getRoomNames(i));
        setBleTypeDialog.initRoomData();
    }

    public void convertScanItem(ActMeshScanVM.ScanItem item) {
        ProductInfo productInfo = item.productInfo;
        if (productInfo.getProductKey().equals("01")) {
            if (productInfo.getProductId().equals(ProductId.ID_BLE_HAM)) {
                this.scanTypeList.get(2).number++;
                return;
            } else if (productInfo.getProductId().equals(ProductId.ID_CENTRE_AIR_GATEWAY) || productInfo.getProductId().equals(ProductId.ID_CENTRE_AIR_PRO_GATEWAY)) {
                this.scanTypeList.get(4).number++;
                return;
            } else {
                if (productInfo.getProductId().equals(ProductId.ID_HOME_KIT)) {
                    this.scanTypeList.get(15).number++;
                    return;
                }
                return;
            }
        }
        if (productInfo.getProductKey().equals("02")) {
            if (productInfo.getProductId().equals(ProductId.ID_BLE_LIGHT_CGD_PRO)) {
                this.scanTypeList.get(10).number++;
                return;
            }
            if (productInfo.getProductId().equals(ProductId.ID_BLE_LIGHT_SPI)) {
                this.scanTypeList.get(11).number++;
                return;
            } else if (productInfo.getProductId().equals(ProductId.ID_RS485_BLE) || productInfo.getProductId().equals(ProductId.ID_BLE_RS8)) {
                this.scanTypeList.get(13).number++;
                return;
            } else {
                this.scanTypeList.get(0).number++;
                return;
            }
        }
        if (productInfo.getProductKey().equals("03")) {
            if (productInfo.getProductId().equals(ProductId.ID_RC4) || productInfo.getProductId().equals(ProductId.ID_RC_B1) || productInfo.getProductId().equals(ProductId.ID_RC_B2) || productInfo.getProductId().equals(ProductId.ID_RC_B5) || productInfo.getProductId().equals(ProductId.ID_RC_B8) || productInfo.getProductId().equals(ProductId.ID_RC4S)) {
                this.scanTypeList.get(9).number++;
                return;
            } else if (productInfo.getSubProductKey().equals("0A")) {
                this.scanTypeList.get(14).number++;
                return;
            } else if (productInfo.getSubProductKey().equals("0B")) {
                this.scanTypeList.get(16).number++;
                return;
            } else {
                this.scanTypeList.get(1).number++;
                return;
            }
        }
        if (productInfo.getProductKey().equals("04")) {
            if (productInfo.getSubProductKey().equals("01") || productInfo.getSubProductKey().equals("02") || productInfo.getSubProductKey().equals("03") || productInfo.getSubProductKey().equals("04")) {
                this.scanTypeList.get(8).number++;
                return;
            }
            if (productInfo.getSubProductKey().equals("0C") || productInfo.getSubProductKey().equals("0D")) {
                this.scanTypeList.get(6).number++;
                return;
            }
            if (productInfo.getSubProductKey().equals("06") || productInfo.getSubProductKey().equals("23")) {
                this.scanTypeList.get(0).number++;
                return;
            }
            if (productInfo.getSubProductKey().equals(ProductId.BLE_EUR_PANEL_EB1) || productInfo.getSubProductKey().equals(ProductId.BLE_EUR_PANEL_EB2) || productInfo.getSubProductKey().equals(ProductId.BLE_EUR_PANEL_EB5) || productInfo.getSubProductKey().equals(ProductId.BLE_EUR_PANEL_EB6)) {
                this.scanTypeList.get(12).number++;
                return;
            }
            if (productInfo.getSubProductKey().equals(ProductId.BLE_EUR_PANEL_EB8)) {
                this.scanTypeList.get(19).number++;
                return;
            }
            if (ProductRepository.isE6Panel(productInfo.getProductId())) {
                this.scanTypeList.get(17).number++;
                return;
            } else if (productInfo.getSubProductKey().equals("21") || productInfo.getSubProductKey().equals(ProductId.BLE_SMART_PANEL_SUB_TYPE_GQ_MAX)) {
                this.scanTypeList.get(18).number++;
                return;
            } else {
                this.scanTypeList.get(1).number++;
                return;
            }
        }
        if (productInfo.getProductKey().equals("05")) {
            this.scanTypeList.get(5).number++;
        } else if (productInfo.getProductKey().equals("07")) {
            this.scanTypeList.get(3).number++;
        } else if (productInfo.getProductId().equals(ProductId.ID_BLE_MUSIC_PLAYER)) {
            this.scanTypeList.get(7).number++;
        }
    }

    public void convertScanItemList(List<ActMeshScanVM.ScanItem> itemList) {
        initScanTypeList();
        Iterator<ActMeshScanVM.ScanItem> it = itemList.iterator();
        while (it.hasNext()) {
            convertScanItem(it.next());
        }
    }

    public void initScanTypeList() {
        this.scanTypeList.clear();
        this.scanTypeList.add(new ScanTypeItem(0, R.mipmap.ic_device_light));
        this.scanTypeList.add(new ScanTypeItem(1, R.mipmap.device_icon_s3_pro));
        this.scanTypeList.add(new ScanTypeItem(2, R.mipmap.ic_device_gateway));
        this.scanTypeList.add(new ScanTypeItem(3, R.mipmap.ic_device_curtain));
        this.scanTypeList.add(new ScanTypeItem(4, R.mipmap.device_icon_centralac));
        this.scanTypeList.add(new ScanTypeItem(5, R.mipmap.icon_device_mr03));
        this.scanTypeList.add(new ScanTypeItem(6, R.mipmap.trig_icon_normal));
        this.scanTypeList.add(new ScanTypeItem(7, R.mipmap.ic_device_cgmusic_icon));
        this.scanTypeList.add(new ScanTypeItem(8, R.mipmap.ic_deivce_as_panel));
        this.scanTypeList.add(new ScanTypeItem(9, R.mipmap.ic_device_rc));
        this.scanTypeList.add(new ScanTypeItem(10, R.mipmap.ic_device_dali_control));
        this.scanTypeList.add(new ScanTypeItem(11, R.mipmap.ic_device_spi));
        this.scanTypeList.add(new ScanTypeItem(12, R.mipmap.ic_device_eur_device));
        this.scanTypeList.add(new ScanTypeItem(13, R.mipmap.icon_device_cg485));
        this.scanTypeList.add(new ScanTypeItem(14, R.mipmap.device_icon_gq));
        this.scanTypeList.add(new ScanTypeItem(15, R.mipmap.ic_device_cgkit));
        this.scanTypeList.add(new ScanTypeItem(16, R.mipmap.device_icon_gqx));
        this.scanTypeList.add(new ScanTypeItem(17, R.mipmap.ic_device_e6d));
        this.scanTypeList.add(new ScanTypeItem(18, R.mipmap.icon_cqpro));
        this.scanTypeList.add(new ScanTypeItem(19, R.mipmap.ic_device_eur_eb8));
    }

    public static class ScanTypeItem {
        public int iconRes;
        public int number;
        public int type;

        public ScanTypeItem(int type, int iconRes) {
            this.type = type;
            this.iconRes = iconRes;
        }

        public int getType() {
            return this.type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getIconRes() {
            return this.iconRes;
        }

        public void setIconRes(int iconRes) {
            this.iconRes = iconRes;
        }

        public int getNumber() {
            return this.number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
}