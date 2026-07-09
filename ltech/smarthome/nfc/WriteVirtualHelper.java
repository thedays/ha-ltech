package com.ltech.smarthome.nfc;

import android.text.TextUtils;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.nfc.source.BleSource;
import com.ltech.nfc.source.NetInfo;
import com.ltech.nfc.source.SourceHelper;
import com.ltech.nfc.source.SourceInfo;
import com.ltech.nfc.source.SourceModel;
import com.ltech.nfc.utils.DataUtil;
import com.ltech.nfc.utils.NfcVUtil;
import com.ltech.smarthome.base.BaseExtParam;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.blemesh.IRefreshNetworkCallback;
import com.ltech.smarthome.blemesh.feasy.FeasyMeshNetHelper;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.E6ExtParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.model.scene_param.BaseLocalParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.net.response.device.GetReplaceDataResponse;
import com.ltech.smarthome.net.response.scene.QuerySceneActionResponse;
import com.ltech.smarthome.singleton.Singleton;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.base.BaseCmd;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class WriteVirtualHelper {
    public static final int END = 7;
    public static final int FAIL = 2;
    public static final int GET_DATA = 3;
    public static final int GET_NET_INFO = 1;
    public static final int MAC_NOT_MATCH = 5;
    public static final int MAC_NOT_VALID = 6;
    public static final int REFRESH_MESH = 6;
    public static final int STORAGE_NOT_ENOUGH = 3;
    public static final int SUCCESS = 1;
    public static final int TYPE_NOT_MATCH = 4;
    public static final int UPDATE_DEVICE = 5;
    public static final int WRITE_NET_INFO = 4;
    public static final int WRITE_PARAM_BLOCK = 2;
    private BaseNormalActivity activity;
    private onWriteCallBack callBack;
    private Device device;
    private boolean enterNet;
    private NetInfo netInfo;
    private NfcVUtil nfcVUtil;
    private SourceInfo sourceInfo;
    private List<Integer> processList = new ArrayList();
    private int statePos = 0;
    private GetReplaceDataResponse dataResponse = new GetReplaceDataResponse();
    private List<Integer> appDataList = new ArrayList();
    private int retryTimes = 3;

    public interface onWriteCallBack {
        void onFinish(int result);
    }

    public static WriteVirtualHelper instance() {
        return (WriteVirtualHelper) Singleton.getSingleton(WriteVirtualHelper.class);
    }

    public void init(BaseNormalActivity activity, Device device, boolean enterNet) {
        this.activity = activity;
        this.device = device;
        this.enterNet = enterNet;
    }

    public boolean isSupportNfc(String binName) {
        return SourceHelper.virtualProductList.contains(binName);
    }

    public boolean isEnterNet() {
        return this.enterNet;
    }

    public int isDeviceMatch(SourceInfo info) {
        Device device = this.device;
        if (device == null) {
            return 0;
        }
        if (this.enterNet && device.getExtParam() != null && ((BaseExtParam) this.device.getExtParam(BaseExtParam.class)).getBinNum() != info.getSourceId()) {
            return 4;
        }
        if (isMacValid(info.getMac())) {
            return (this.device.getWifiMac() == null || info.getMac().equals(this.device.getWifiMac())) ? 0 : 5;
        }
        return 6;
    }

    private boolean isMacValid(String mac) {
        if (mac.length() == 12) {
            return mac.startsWith("DC") || mac.startsWith("78");
        }
        return false;
    }

    public void updateDeviceWritable(final List<Long> deviceIds) {
        ((ObservableSubscribeProxy) Injection.net().updateDeviceBatch(deviceIds, 1).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.nfc.WriteVirtualHelper$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                WriteVirtualHelper.lambda$updateDeviceWritable$0(deviceIds, obj);
            }
        });
    }

    static /* synthetic */ void lambda$updateDeviceWritable$0(List list, Object obj) throws Exception {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(((Long) it.next()).longValue());
            if (!deviceByDeviceId.isSubDevice()) {
                deviceByDeviceId.setWritable(1);
                arrayList.add(deviceByDeviceId);
            }
        }
        Injection.repo().device().saveDevice(arrayList);
    }

    public int nextState() {
        int i = this.statePos + 1;
        this.statePos = i;
        if (i >= this.processList.size()) {
            this.statePos = this.processList.size() - 1;
        }
        return this.processList.get(this.statePos).intValue();
    }

    public void writeDevice(NfcVUtil nfcVUtil, SourceInfo sourceInfo, NetInfo info) {
        this.nfcVUtil = nfcVUtil;
        this.sourceInfo = sourceInfo;
        this.netInfo = info;
        ArrayList arrayList = new ArrayList();
        this.processList = arrayList;
        arrayList.add(1);
        if (this.enterNet) {
            this.processList.add(3);
            if (sourceInfo.getSourceType() != 17) {
                this.processList.add(2);
            }
        }
        this.processList.add(4);
        this.processList.add(5);
        if (this.enterNet) {
            this.processList.add(6);
        }
        this.processList.add(7);
        this.statePos = 0;
        writeDevice(this.processList.get(0).intValue());
    }

    public void writeDevice(int state) {
        switch (state) {
            case 1:
                getNetInfo();
                break;
            case 2:
                writeParamBlock();
                break;
            case 3:
                getData();
                break;
            case 4:
                writeNetInfo();
                break;
            case 5:
                updateDevice();
                break;
            case 6:
                refreshMeshNetwork();
                break;
            case 7:
                setSuccessView();
                break;
        }
    }

    private void getNetInfo() {
        this.activity.showLoadingDialog("");
        if (this.enterNet) {
            this.netInfo.setNetKey(Injection.repo().home().getSelPlace().getNetKey());
            BleParam bleParam = (BleParam) this.device.getParam(BleParam.class);
            if (!TextUtils.isEmpty(bleParam.getDeviceKey())) {
                this.netInfo.setDeviceKey(bleParam.getDeviceKey());
            }
            this.netInfo.setNodeId(this.device.getUnicastAddress());
            this.netInfo.setIvIndex(r0.getIvindex() - 1);
        }
        writeDevice(nextState());
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0058 A[Catch: Exception -> 0x0075, TryCatch #0 {Exception -> 0x0075, blocks: (B:3:0x0007, B:5:0x0015, B:8:0x001a, B:10:0x001e, B:11:0x004f, B:13:0x0058, B:15:0x0069, B:18:0x0071, B:21:0x0022, B:23:0x0026, B:24:0x002a, B:25:0x0032, B:27:0x0048, B:28:0x004c), top: B:2:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void writeParamBlock() {
        /*
            r6 = this;
            com.ltech.nfc.source.SourceInfo r0 = r6.sourceInfo
            int[] r0 = r0.getParamValue()
            r1 = 2
            com.ltech.nfc.source.SourceInfo r2 = r6.sourceInfo     // Catch: java.lang.Exception -> L75
            int r2 = r2.getSourceId()     // Catch: java.lang.Exception -> L75
            com.ltech.nfc.source.SourceModel r2 = com.ltech.nfc.source.SourceModel.getSourceModel(r2)     // Catch: java.lang.Exception -> L75
            boolean r3 = r2 instanceof com.ltech.nfc.source.BleSource     // Catch: java.lang.Exception -> L75
            if (r3 != 0) goto L32
            boolean r3 = r2 instanceof com.ltech.nfc.source.BleSourceFive     // Catch: java.lang.Exception -> L75
            if (r3 == 0) goto L1a
            goto L32
        L1a:
            boolean r3 = r2 instanceof com.ltech.nfc.source.E6Model     // Catch: java.lang.Exception -> L75
            if (r3 == 0) goto L22
            r6.writeE6ParamArray(r0)     // Catch: java.lang.Exception -> L75
            goto L4f
        L22:
            boolean r3 = r2 instanceof com.ltech.nfc.source.HB4Model     // Catch: java.lang.Exception -> L75
            if (r3 == 0) goto L2a
            r6.writeHB4ParamArray(r0)     // Catch: java.lang.Exception -> L75
            goto L4f
        L2a:
            int r3 = r6.nextState()     // Catch: java.lang.Exception -> L75
            r6.writeDevice(r3)     // Catch: java.lang.Exception -> L75
            goto L4f
        L32:
            com.ltech.smarthome.model.bean.Device r3 = r6.device     // Catch: java.lang.Exception -> L75
            java.lang.Class<com.ltech.smarthome.model.device_param.LightExtParam> r4 = com.ltech.smarthome.model.device_param.LightExtParam.class
            java.lang.Object r3 = r3.getExtParam(r4)     // Catch: java.lang.Exception -> L75
            com.ltech.smarthome.model.device_param.LightExtParam r3 = (com.ltech.smarthome.model.device_param.LightExtParam) r3     // Catch: java.lang.Exception -> L75
            int[] r4 = r3.getCurrentValue()     // Catch: java.lang.Exception -> L75
            int r3 = r3.getPwmFrequency()     // Catch: java.lang.Exception -> L75
            boolean r5 = r2 instanceof com.ltech.nfc.source.BleSource     // Catch: java.lang.Exception -> L75
            if (r5 == 0) goto L4c
            r6.writeBleParamArray(r0, r4, r3)     // Catch: java.lang.Exception -> L75
            goto L4f
        L4c:
            r6.writeBleFiveParamArray(r0, r4)     // Catch: java.lang.Exception -> L75
        L4f:
            com.ltech.nfc.utils.NfcVUtil r3 = r6.nfcVUtil     // Catch: java.lang.Exception -> L75
            r4 = 1
            boolean r3 = r3.checkPassword(r4)     // Catch: java.lang.Exception -> L75
            if (r3 == 0) goto L74
            com.ltech.nfc.utils.NfcVUtil r3 = r6.nfcVUtil     // Catch: java.lang.Exception -> L75
            int r2 = r2.getBlockNumber()     // Catch: java.lang.Exception -> L75
            byte[] r0 = com.ltech.nfc.utils.DataUtil.convertToBytes(r0)     // Catch: java.lang.Exception -> L75
            r4 = 0
            boolean r0 = r3.writeBlocks(r4, r2, r0)     // Catch: java.lang.Exception -> L75
            if (r0 == 0) goto L71
            int r0 = r6.nextState()     // Catch: java.lang.Exception -> L75
            r6.writeDevice(r0)     // Catch: java.lang.Exception -> L75
            return
        L71:
            r6.setErrorView(r1)     // Catch: java.lang.Exception -> L75
        L74:
            return
        L75:
            r6.setErrorView(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.nfc.WriteVirtualHelper.writeParamBlock():void");
    }

    private void writeBleParamArray(int[] array, int[] current, int pwmFrequency) {
        int i;
        SourceModel sourceModel = SourceModel.getSourceModel(this.sourceInfo.getSourceId());
        int i2 = current[0];
        array[4] = i2 % 256;
        array[5] = i2 / 256;
        int calculateGama = sourceModel.calculateGama(i2);
        array[6] = calculateGama % 256;
        array[7] = calculateGama / 256;
        JSONObject parseObject = JSON.parseObject(GsonUtils.toJson(this.dataResponse.getBackData()));
        int[] parseBackupData = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.FADE_TIME);
        int i3 = parseBackupData[0];
        array[8] = (i3 / 100) % 256;
        array[9] = (i3 / 100) / 256;
        int i4 = parseBackupData[1];
        array[10] = (i4 / 100) % 256;
        array[11] = (i4 / 100) / 256;
        int i5 = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.POWER_FADE_TIME)[0];
        array[12] = (i5 / 100) % 256;
        array[13] = (i5 / 100) / 256;
        int lightColorType = ProductRepository.getLightColorType((Object) this.device);
        if (lightColorType == 2) {
            int[] parseBackupData2 = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.CT_RANGE);
            int ctK2Y = LightUtils.ctK2Y(parseBackupData2[0], 10000, 1000);
            i = 0;
            array[16] = ctK2Y % 256;
            array[17] = ctK2Y / 256;
            int ctK2Y2 = LightUtils.ctK2Y(parseBackupData2[1], 10000, 1000);
            array[18] = ctK2Y2 % 256;
            array[19] = ctK2Y2 / 256;
            int i6 = ctK2Y + ctK2Y2;
            if (i6 % 2 != 0) {
                i6++;
            }
            int i7 = i6 / 2;
            array[22] = i7 % 256;
            array[23] = i7 / 256;
        } else {
            i = 0;
        }
        int[] parseBackupData3 = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.POWER_STATUS);
        array[20] = parseBackupData3[i];
        array[21] = parseBackupData3[1];
        if (parseBackupData3.length >= 10 && lightColorType == 2) {
            int i8 = parseBackupData3[7];
            array[22] = i8 % 256;
            array[23] = i8 / 256;
        }
        int[] parseBackupData4 = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.DIM_RANGE);
        array[24] = parseBackupData4[1];
        array[25] = parseBackupData4[i];
        int i9 = array[28];
        array[28] = i9 >= 255 ? 0 : i9 + 1;
        int checkSum = sourceModel.getCheckSum(array);
        array[29] = checkSum % 256;
        array[30] = checkSum / 256;
        array[47] = pwmFrequency;
        int i10 = array[46];
        if (i10 < 255) {
            i = i10 + 1;
        }
        array[46] = i;
        int checkSum2 = sourceModel.getCheckSum(BleSource.getExtendDataList(), array);
        array[44] = checkSum2 % 256;
        array[45] = checkSum2 / 256;
    }

    private void writeBleFiveParamArray(int[] array, int[] current) {
        int i;
        int i2;
        SourceModel sourceModel = SourceModel.getSourceModel(this.sourceInfo.getSourceId());
        for (int i3 = 0; i3 < 5; i3++) {
            int i4 = i3 * 4;
            array[i4 + 4] = current[i3] % 256;
            array[i4 + 5] = current[i3] / 256;
            int calculateGama = sourceModel.calculateGama(current[i3]);
            array[i4 + 6] = calculateGama % 256;
            array[i4 + 7] = calculateGama / 256;
        }
        array[24] = 0;
        array[25] = 18;
        array[26] = 52;
        array[27] = 80;
        JSONObject parseObject = JSON.parseObject(GsonUtils.toJson(this.dataResponse.getBackData()));
        int[] parseBackupData = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.FADE_TIME);
        int i5 = parseBackupData[0];
        array[28] = (i5 / 100) % 256;
        array[29] = (i5 / 100) / 256;
        int i6 = parseBackupData[1];
        array[30] = (i6 / 100) % 256;
        array[31] = (i6 / 100) / 256;
        int i7 = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.POWER_FADE_TIME)[0];
        array[32] = (i7 / 100) % 256;
        array[33] = (i7 / 100) / 256;
        int lightColorType = ProductRepository.getLightColorType((Object) this.device);
        if (lightColorType == 2) {
            int[] parseBackupData2 = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.CT_RANGE);
            i = parseBackupData2[0];
            array[40] = i % 256;
            array[41] = i / 256;
            i2 = parseBackupData2[1];
            array[42] = i2 % 256;
            array[43] = i2 / 256;
        } else {
            i = 1000;
            i2 = 10000;
        }
        array[45] = 1;
        int[] parseBackupData3 = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.POWER_STATUS);
        array[44] = parseBackupData3[0];
        array[34] = parseBackupData3[1];
        array[35] = parseBackupData3[2];
        if (parseBackupData3.length >= 10) {
            if (lightColorType == 2) {
                int ctY2K = LightUtils.ctY2K(parseBackupData3[7], i2, i);
                array[46] = ctY2K % 256;
                array[47] = ctY2K / 256;
            } else if (lightColorType >= 3) {
                array[48] = parseBackupData3[3];
                array[49] = parseBackupData3[4];
                array[50] = parseBackupData3[5];
            }
        }
        int[] parseBackupData4 = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.DIM_RANGE);
        array[53] = parseBackupData4[1];
        array[54] = parseBackupData4[0];
        int i8 = array[56];
        array[56] = i8 < 255 ? i8 + 1 : 0;
        int checkSum = sourceModel.getCheckSum(array);
        array[57] = checkSum % 256;
        array[58] = checkSum / 256;
    }

    private void writeE6ParamArray(int[] array) {
        SourceModel sourceModel = SourceModel.getSourceModel(this.sourceInfo.getSourceId());
        JSONObject parseObject = JSON.parseObject(GsonUtils.toJson(this.dataResponse.getBackData()));
        array[4] = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.KNOB_SENSITIVITY)[0];
        array[5] = ProductRepository.getLightColorType((Object) this.device);
        int[] parseBackupData = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.KNOB_SORT);
        array[6] = parseBackupData[2] | (parseBackupData[0] << 4) | (parseBackupData[1] << 2);
        array[7] = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.KNOB_DOUBLE_MEMORY)[0];
        array[8] = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.DALI_ON_OFF)[0];
        array[11] = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.INDICATOR_STATUS)[0];
        array[12] = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.LIGHT_TYPE)[0];
        array[13] = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.BUZZER_STATUS)[0];
        array[19] = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.RGB_TYPE)[0];
        E6ExtParam e6ExtParam = new E6ExtParam();
        e6ExtParam.fillMapWithString(this.device.getExtParam());
        e6ExtParam.initActionByType(this.device);
        if (!TextUtils.isEmpty(e6ExtParam.getAction(0).getObjectInstruct())) {
            int[] hexStringToInts = DataUtil.hexStringToInts(e6ExtParam.getAction(0).getObjectInstruct());
            if (hexStringToInts.length == 3) {
                array[16] = hexStringToInts[0];
                array[17] = hexStringToInts[2];
                array[18] = hexStringToInts[1];
            }
        }
        int i = 0;
        while (i < 4) {
            int i2 = i * 12;
            int i3 = i2 + 20;
            i++;
            if (!TextUtils.isEmpty(e6ExtParam.getAction(i).getObjectInstruct())) {
                int[] hexStringToInts2 = DataUtil.hexStringToInts(e6ExtParam.getAction(i).getObjectInstruct());
                if (hexStringToInts2.length == 3) {
                    array[i3] = hexStringToInts2[0];
                    array[i2 + 21] = hexStringToInts2[2];
                    array[i2 + 22] = hexStringToInts2[1];
                }
            }
            if (!TextUtils.isEmpty(e6ExtParam.getAction(i).getActionInstruct())) {
                int[] hexStringToInts3 = DataUtil.hexStringToInts(e6ExtParam.getAction(i).getActionInstruct());
                if (hexStringToInts3.length == 8) {
                    System.arraycopy(hexStringToInts3, 0, array, i2 + 23, 8);
                }
            }
        }
        int[] parseBackupData2 = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.POWER_STATUS, this.device);
        array[68] = parseBackupData2[0];
        int i4 = parseBackupData2[1];
        array[72] = i4;
        array[74] = i4;
        int i5 = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.POWER_FADE_TIME)[0];
        array[76] = (i5 / 100) % 256;
        array[77] = (i5 / 100) / 256;
        int[] parseBackupData3 = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.FADE_TIME);
        int i6 = parseBackupData3[0];
        array[78] = (i6 / 100) % 256;
        array[79] = (i6 / 100) / 256;
        int i7 = parseBackupData3[1];
        array[80] = (i7 / 100) % 256;
        array[81] = (i7 / 100) / 256;
        int i8 = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.SCENE_FADE_TIME)[0];
        array[82] = (i8 / 100) % 256;
        array[83] = (i8 / 100) / 256;
        int i9 = array[92];
        array[92] = i9 < 255 ? i9 + 1 : 0;
        int checkSum = sourceModel.getCheckSum(array);
        array[93] = checkSum % 256;
        array[94] = checkSum / 256;
    }

    private void writeHB4ParamArray(int[] array) {
        SourceModel sourceModel = SourceModel.getSourceModel(this.sourceInfo.getSourceId());
        JSONObject parseObject = JSON.parseObject(GsonUtils.toJson(this.dataResponse.getBackData()));
        int[] parseBackupData = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.FADE_TIME);
        int i = parseBackupData[0];
        array[8] = (i / 100) % 256;
        array[9] = (i / 100) / 256;
        int i2 = parseBackupData[1];
        array[10] = (i2 / 100) % 256;
        array[11] = (i2 / 100) / 256;
        int i3 = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.POWER_FADE_TIME)[0];
        array[12] = (i3 / 100) % 256;
        array[13] = (i3 / 100) / 256;
        int i4 = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.SCENE_FADE_TIME)[0];
        array[14] = (i4 / 100) % 256;
        array[15] = (i4 / 100) / 256;
        if (ProductRepository.getLightColorType((Object) this.device) == 2) {
            int[] parseBackupData2 = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.CT_RANGE);
            int i5 = parseBackupData2[0];
            array[28] = i5 % 256;
            array[29] = i5 / 256;
            int i6 = parseBackupData2[1];
            array[30] = i6 % 256;
            array[31] = i6 / 256;
        }
        int[] parseBackupData3 = ReplaceHelper.instance().parseBackupData(parseObject, UpdateBackDataRequest.POWER_STATUS, this.device);
        array[32] = parseBackupData3[0];
        int i7 = parseBackupData3[1];
        array[34] = i7;
        array[35] = i7;
        int i8 = array[60];
        array[60] = i8 < 255 ? i8 + 1 : 0;
        int checkSum = sourceModel.getCheckSum(array);
        array[61] = checkSum % 256;
        array[62] = checkSum / 256;
    }

    private void getData() {
        ((ObservableSubscribeProxy) Injection.net().queryReplaceData(this.device.getDeviceId()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.activity, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.nfc.WriteVirtualHelper$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                WriteVirtualHelper.this.lambda$getData$1((GetReplaceDataResponse) obj);
            }
        }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.nfc.WriteVirtualHelper.1
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                WriteVirtualHelper.this.setErrorView(2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getData$1(GetReplaceDataResponse getReplaceDataResponse) throws Exception {
        LHomeLog.i(getClass(), "queryDeviceReplaceData:" + getReplaceDataResponse.toString());
        this.dataResponse = getReplaceDataResponse;
        List<Integer> appData = getAppData();
        this.appDataList = appData;
        if (appData.size() > 3840) {
            setErrorView(3);
        } else {
            writeDevice(nextState());
        }
    }

    private void writeNetInfo() {
        try {
            if (this.nfcVUtil.checkPassword(1)) {
                List<Integer> netArray = this.netInfo.getNetArray(this.enterNet);
                if (this.enterNet) {
                    netArray.addAll(this.appDataList);
                }
                int size = netArray.size() % 4 == 0 ? netArray.size() / 4 : (netArray.size() / 4) + 1;
                int countFlag = this.netInfo.getCountFlag() + size;
                netArray.addAll(0, Arrays.asList(Integer.valueOf(countFlag & 255), Integer.valueOf(countFlag >> 8), Integer.valueOf(size & 255), 0));
                if (this.nfcVUtil.writeBlocks(52, size + 1, DataUtil.convertToBytes(netArray))) {
                    writeDevice(nextState());
                } else {
                    setErrorView(2);
                }
            }
        } catch (Exception unused) {
            setErrorView(2);
        }
    }

    private List<Integer> getAppData() {
        int i;
        ArrayList arrayList = new ArrayList();
        if (this.dataResponse.getGroupData() != null && !this.dataResponse.getGroupData().isEmpty()) {
            arrayList.add(160);
            int size = this.dataResponse.getGroupData().size();
            arrayList.add(Integer.valueOf(size));
            for (int i2 = 0; i2 < size; i2++) {
                arrayList.addAll(DataUtil.intToList(this.dataResponse.getGroupData().get(i2).getGroupAddress(), 2));
            }
            arrayList.addAll(1, DataUtil.intToList(DataUtil.getCheckSum(arrayList, 1, arrayList.size()), 2));
        }
        ArrayList arrayList2 = new ArrayList();
        String json = GsonUtils.toJson(this.dataResponse.getBackData());
        if (TextUtils.isEmpty(json)) {
            i = 0;
        } else {
            JSONObject parseObject = JSON.parseObject(json);
            i = 0;
            for (String str : parseObject.keySet()) {
                String str2 = (String) parseObject.get(str);
                if (!TextUtils.isEmpty(str2)) {
                    i++;
                    if (UpdateBackDataRequest.LIGHT_TYPE.equals(str) || UpdateBackDataRequest.RELAY_POSITION.equals(str)) {
                        arrayList2.add(0, Integer.valueOf(str2.length() / 2));
                        arrayList2.addAll(1, DataUtil.hexStringToIntList(str2));
                    } else {
                        arrayList2.add(Integer.valueOf(str2.length() / 2));
                        arrayList2.addAll(DataUtil.hexStringToIntList(str2));
                    }
                }
            }
        }
        if (this.dataResponse.getSceneData() != null && !this.dataResponse.getSceneData().isEmpty()) {
            Collections.sort(this.dataResponse.getSceneData(), new Comparator() { // from class: com.ltech.smarthome.nfc.WriteVirtualHelper$$ExternalSyntheticLambda4
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return WriteVirtualHelper.lambda$getAppData$2((QuerySceneActionResponse.RowsBean) obj, (QuerySceneActionResponse.RowsBean) obj2);
                }
            });
            HashMap hashMap = new HashMap();
            for (QuerySceneActionResponse.RowsBean rowsBean : this.dataResponse.getSceneData()) {
                if (rowsBean.getScenetype() == 2) {
                    BaseLocalParam executecommandObject = rowsBean.getExecutecommandObject();
                    int intValue = (hashMap.containsKey(Long.valueOf(rowsBean.getSceneid())) ? ((Integer) hashMap.get(Long.valueOf(rowsBean.getSceneid()))).intValue() : 0) + 1;
                    hashMap.put(Long.valueOf(rowsBean.getSceneid()), Integer.valueOf(intValue));
                    BaseCmd convert2cmd = Injection.strategy().getCmdConvertStrategy(2).convert2cmd(CmdAssistant.getSceneCmdAssistant(this.device, new int[0]).syncLocalSceneAction(rowsBean.getScenenum(), executecommandObject.instruct, intValue, executecommandObject.getTotalDelay(), executecommandObject.sceneZone, executecommandObject.sceneAddr, false, ProductRepository.getInfraredType(this.device.getProductId())));
                    String str3 = StringUtils.demToHexDouble(convert2cmd.getFunCode()) + StringUtils.byte2Str(convert2cmd.value(new Object[0]));
                    i++;
                    arrayList2.add(Integer.valueOf(str3.length() / 2));
                    arrayList2.addAll(DataUtil.hexStringToIntList(str3));
                }
            }
        }
        if (i > 0) {
            arrayList2.add(0, 161);
            arrayList2.addAll(1, DataUtil.intToList(i, 2));
            arrayList2.addAll(1, DataUtil.intToList(DataUtil.getCheckSum(arrayList2, 1, arrayList2.size()), 2));
            arrayList.addAll(arrayList2);
        }
        return arrayList;
    }

    static /* synthetic */ int lambda$getAppData$2(QuerySceneActionResponse.RowsBean rowsBean, QuerySceneActionResponse.RowsBean rowsBean2) {
        return rowsBean.getExecutecommandObject().getTotalDelay() - rowsBean2.getExecutecommandObject().getTotalDelay();
    }

    private void updateDevice() {
        if (this.enterNet) {
            final BleParam bleParam = (BleParam) this.device.getParam(BleParam.class);
            bleParam.setDeviceUUID(this.sourceInfo.getMac() + "4C54" + this.sourceInfo.getUuid() + "0000000000");
            ((ObservableSubscribeProxy) Injection.net().updateParamAndMac(this.device.getDeviceId(), GsonUtils.toJson(bleParam), this.sourceInfo.getMac(), 0).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.activity, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.nfc.WriteVirtualHelper$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    WriteVirtualHelper.this.lambda$updateDevice$3(bleParam, obj);
                }
            }, new Consumer() { // from class: com.ltech.smarthome.nfc.WriteVirtualHelper$$ExternalSyntheticLambda6
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    WriteVirtualHelper.this.lambda$updateDevice$4((Throwable) obj);
                }
            });
            return;
        }
        Device device = this.device;
        if (device == null) {
            Device deviceByMac = Injection.repo().device().getDeviceByMac(Injection.repo().home().getSelPlace().getPlaceId(), this.sourceInfo.getMac());
            if (deviceByMac == null) {
                writeDevice(nextState());
                return;
            } else {
                updateDeviceOutNet(deviceByMac);
                return;
            }
        }
        updateDeviceOutNet(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDevice$3(BleParam bleParam, Object obj) throws Exception {
        this.device.setWifiMac(this.sourceInfo.getMac());
        this.device.setParam(bleParam);
        this.device.setWritable(0);
        Injection.repo().device().saveDevice(this.device);
        writeDevice(nextState());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDevice$4(Throwable th) throws Exception {
        setErrorView(2);
    }

    private void updateDeviceOutNet(final Device device) {
        ((ObservableSubscribeProxy) Injection.net().updateDeviceVirtual(device.getDeviceId(), 2, 1).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.activity, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.nfc.WriteVirtualHelper$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                WriteVirtualHelper.this.lambda$updateDeviceOutNet$5(device, obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.nfc.WriteVirtualHelper$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                WriteVirtualHelper.this.lambda$updateDeviceOutNet$6((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDeviceOutNet$5(Device device, Object obj) throws Exception {
        device.setVirtual(2);
        device.setWritable(1);
        Injection.repo().device().saveDevice(device);
        writeDevice(nextState());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDeviceOutNet$6(Throwable th) throws Exception {
        setErrorView(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshMeshNetwork() {
        final Place selPlace = Injection.repo().home().getSelPlace();
        Injection.mesh().refreshNetwork(GsonUtils.getGson().toJson(FeasyMeshNetHelper.getMeshJsonBean(selPlace, selPlace.getNetKey(), selPlace.getAppKey(), selPlace.getProvisionerAddress())), new IRefreshNetworkCallback() { // from class: com.ltech.smarthome.nfc.WriteVirtualHelper.2
            @Override // com.ltech.smarthome.blemesh.IRefreshNetworkCallback
            public void onRefreshSuccess() {
                WriteVirtualHelper.this.retryTimes = 3;
                if (Injection.mesh().getConnectedDevice() == null) {
                    Injection.mesh().checkNearbyDevice(selPlace.getMeshUUID(), WriteVirtualHelper.this.activity);
                }
                WriteVirtualHelper writeVirtualHelper = WriteVirtualHelper.this;
                writeVirtualHelper.writeDevice(writeVirtualHelper.nextState());
            }

            @Override // com.ltech.smarthome.blemesh.IRefreshNetworkCallback
            public void onRefreshFail() {
                if (WriteVirtualHelper.this.retryTimes > 0) {
                    WriteVirtualHelper writeVirtualHelper = WriteVirtualHelper.this;
                    writeVirtualHelper.retryTimes--;
                    WriteVirtualHelper.this.refreshMeshNetwork();
                    return;
                }
                WriteVirtualHelper.this.setErrorView(2);
            }
        });
    }

    private void setSuccessView() {
        this.activity.dismissLoadingDialog();
        onWriteCallBack onwritecallback = this.callBack;
        if (onwritecallback != null) {
            onwritecallback.onFinish(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setErrorView(int resultCode) {
        this.activity.dismissLoadingDialog();
        onWriteCallBack onwritecallback = this.callBack;
        if (onwritecallback != null) {
            onwritecallback.onFinish(resultCode);
        }
    }

    public void setCallBack(onWriteCallBack callBack) {
        this.callBack = callBack;
    }
}