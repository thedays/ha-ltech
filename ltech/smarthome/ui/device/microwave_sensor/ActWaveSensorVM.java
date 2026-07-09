package com.ltech.smarthome.ui.device.microwave_sensor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.device_param.WaveSensorExtParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.NameRepository;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.smart.product_agreement.bean.WaveSensorState;
import com.sun.jna.platform.win32.WinError;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xiaomi.mipush.sdk.Constants;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActWaveSensorVM extends BaseViewModel {
    public long controlId;
    public boolean groupControl;
    public WaveSensorExtParam mrExtParam;
    public long placeId;
    public Object relateObject;
    public MutableLiveData<Boolean> offline = new MutableLiveData<>(false);
    public MutableLiveData<Object> controlObject = new MutableLiveData<>();
    public RecyclerView.ItemDecoration decoration = new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorVM.1
        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(ConvertUtils.dp2px(10.0f), ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(10.0f), ConvertUtils.dp2px(5.0f));
        }
    };

    public void convertParams(String resData, int index) {
        String substring;
        String substring2;
        try {
            String substring3 = resData.substring(16);
            this.mrExtParam.getDelayParam(index).destAddress = Integer.parseInt(substring3.substring(0, 4), 16);
            if (this.mrExtParam.getDelayParam(index).destAddress != 0) {
                this.mrExtParam.getDelayParam(index).actionPart = 1;
                this.mrExtParam.getDelayParam(index).actionType = Integer.parseInt(substring3.substring(4, 6), 16);
                int parseInt = Integer.parseInt(substring3.substring(6, 8), 16);
                if (this.mrExtParam.getDelayParam(index).isV010orDali()) {
                    this.mrExtParam.getDelayParam(index).optionValue = Integer.parseInt(substring3.substring(8, 10), 16);
                } else if (this.mrExtParam.getDelayParam(index).actionType == 0) {
                    this.mrExtParam.getDelayParam(index).option = String.valueOf(Integer.parseInt(substring3.substring(8, 10), 16));
                }
                WaveSensorExtParam.SensorParam delayParam = this.mrExtParam.getDelayParam(index);
                StringBuilder sb = new StringBuilder();
                sb.append(substring3.substring(0, 6));
                sb.append("01");
                int i = parseInt * 2;
                int i2 = i + 8;
                sb.append(substring3.substring(6, i2));
                delayParam.instruct = sb.toString();
                int i3 = i + 12;
                this.mrExtParam.getDelayParam(index).delayTime = Integer.parseInt(substring3.substring(i2, i3), 16);
                substring = substring3.substring(i3);
            } else {
                substring = substring3.substring(4);
            }
            this.mrExtParam.getStayParam(index).destAddress = Integer.parseInt(substring.substring(0, 4), 16);
            if (this.mrExtParam.getStayParam(index).destAddress != 0) {
                this.mrExtParam.getStayParam(index).actionPart = 2;
                this.mrExtParam.getStayParam(index).actionType = Integer.parseInt(substring.substring(4, 6), 16);
                int parseInt2 = Integer.parseInt(substring.substring(6, 8), 16);
                if (this.mrExtParam.getStayParam(index).isV010orDali()) {
                    this.mrExtParam.getStayParam(index).optionValue = Integer.parseInt(substring.substring(8, 10), 16);
                }
                WaveSensorExtParam.SensorParam stayParam = this.mrExtParam.getStayParam(index);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(substring.substring(0, 6));
                sb2.append("02");
                int i4 = parseInt2 * 2;
                int i5 = i4 + 8;
                sb2.append(substring.substring(6, i5));
                stayParam.instruct = sb2.toString();
                int i6 = i4 + 12;
                this.mrExtParam.getStayParam(index).delayTime = Integer.parseInt(substring.substring(i5, i6), 16);
                substring2 = substring.substring(i6);
            } else {
                substring2 = substring.substring(4);
            }
            this.mrExtParam.getExitParam(index).destAddress = Integer.parseInt(substring2.substring(0, 4), 16);
            if (this.mrExtParam.getExitParam(index).destAddress != 0) {
                this.mrExtParam.getExitParam(index).actionPart = 3;
                this.mrExtParam.getExitParam(index).actionType = Integer.parseInt(substring2.substring(4, 6), 16);
                int parseInt3 = Integer.parseInt(substring2.substring(6, 8), 16);
                WaveSensorExtParam.SensorParam exitParam = this.mrExtParam.getExitParam(index);
                StringBuilder sb3 = new StringBuilder();
                sb3.append(substring2.substring(0, 6));
                sb3.append("03");
                int i7 = (parseInt3 * 2) + 8;
                sb3.append(substring2.substring(6, i7));
                exitParam.instruct = sb3.toString();
                substring2 = substring2.substring(i7);
            }
            this.mrExtParam.setEffectTimeStartH(index, Integer.parseInt(substring2.substring(0, 2), 16));
            this.mrExtParam.setEffectTimeStartM(index, Integer.parseInt(substring2.substring(2, 4), 16));
            this.mrExtParam.setEffectTimeEndH(index, Integer.parseInt(substring2.substring(4, 6), 16));
            this.mrExtParam.setEffectTimeEndM(index, Integer.parseInt(substring2.substring(6, 8), 16));
            this.mrExtParam.setEffectTimeRepeat(index, Integer.parseInt(substring2.substring(8, 10), 16));
            this.mrExtParam.setRelayAlwaysOn(index, Integer.parseInt(substring2.substring(10, 12), 16));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WaveSensorState getState() {
        if (!this.groupControl) {
            return ((Device) this.controlObject.getValue()).getDeviceState().getWaveSensorState();
        }
        return ((Group) this.controlObject.getValue()).getGroupState().getWaveSensorState();
    }

    public boolean convertState(String resData, WaveSensorState sensorState) {
        int sensitivity = sensorState.getSensitivity();
        int illuminance = sensorState.getIlluminance();
        if (resData.length() >= 22) {
            boolean z = Integer.parseInt(resData.substring(10, 12), 16) == 26;
            sensorState.setEnable(Integer.parseInt(resData.substring(12, 14), 16) != 0);
            sensorState.setCurState(Integer.parseInt(resData.substring(14, 16), 16));
            int parseInt = Integer.parseInt(resData.substring(16, 18), 16);
            sensorState.setIndicatorLight(parseInt & 1);
            sensorState.setRelayPowerOn((parseInt & 2) == 2);
            if (!this.groupControl) {
                sensorState.setSensitivity(Integer.parseInt(resData.substring(18, 20), 16));
                if (!z) {
                    sensorState.setIlluminance(Integer.parseInt(resData.substring(20, 22), 16));
                } else {
                    sensorState.setIlluminance(Integer.parseInt(resData.substring(20, 24), 16));
                }
            } else {
                sensorState.setSensitivity(this.mrExtParam.getMicroWaveSensitive());
                sensorState.setIlluminance(this.mrExtParam.getMicroWaveLux());
            }
            if (z && resData.length() >= 32) {
                sensorState.setCurLux(Integer.parseInt(resData.substring(24, 28), 16));
                sensorState.setCurCt(Integer.parseInt(resData.substring(28, 32), 16));
            }
        }
        return (sensitivity == sensorState.getIlluminance() && illuminance == sensorState.getSensitivity()) ? false : true;
    }

    public void getRelateObject(WaveSensorExtParam.SensorParam param) {
        Group groupByGroupId;
        if (param.smartType == 1) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(param.objectId);
            if (deviceByDeviceId != null) {
                this.relateObject = deviceByDeviceId;
                return;
            }
            return;
        }
        if (param.smartType != 2 || (groupByGroupId = Injection.repo().group().getGroupByGroupId(param.objectId)) == null) {
            return;
        }
        this.relateObject = groupByGroupId;
    }

    public String getRelateName(WaveSensorExtParam.SensorParam param) {
        getRelateObject(param);
        Object obj = this.relateObject;
        if (obj != null && (obj instanceof Device)) {
            return ((Device) obj).getName();
        }
        if (obj != null && (obj instanceof Group)) {
            return ((Group) obj).getName();
        }
        return ActivityUtils.getTopActivity().getString(R.string.please_choose);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x005d, code lost:
    
        if (r5.equals(com.ltech.smarthome.model.product.ProductId.ID_BLE_LIGHT_SPI) == false) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getRelateContent(com.ltech.smarthome.model.device_param.WaveSensorExtParam.SensorParam r11) {
        /*
            Method dump skipped, instructions count: 504
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorVM.getRelateContent(com.ltech.smarthome.model.device_param.WaveSensorExtParam$SensorParam):java.lang.String");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x003c, code lost:
    
        if (r10.equals(com.ltech.smarthome.model.product.ProductId.ID_BLE_SWITCH) == false) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isExitEnable(com.ltech.smarthome.model.device_param.WaveSensorExtParam.SensorParam r10) {
        /*
            r9 = this;
            int r0 = r10.smartType
            r1 = 5
            r2 = 4
            r3 = 3
            r4 = 0
            r5 = 2
            r6 = 1
            if (r0 != r6) goto L7a
            com.ltech.smarthome.model.Repository r0 = com.ltech.smarthome.model.Injection.repo()
            com.ltech.smarthome.model.repo.ifun.IDevice r0 = r0.device()
            long r7 = r10.objectId
            com.ltech.smarthome.model.bean.Device r10 = r0.getDeviceByDeviceId(r7)
            if (r10 == 0) goto La1
            java.lang.String r10 = r10.getProductId()
            r10.hashCode()
            int r0 = r10.hashCode()
            r7 = -1
            switch(r0) {
                case -1805322688: goto L6b;
                case -1805199680: goto L60;
                case -1804340546: goto L55;
                case -1804278081: goto L4a;
                case -1803448738: goto L3f;
                case 166485422: goto L36;
                case 662799966: goto L2b;
                default: goto L29;
            }
        L29:
            r1 = -1
            goto L75
        L2b:
            java.lang.String r0 = "122112209430401"
            boolean r10 = r10.equals(r0)
            if (r10 != 0) goto L34
            goto L29
        L34:
            r1 = 6
            goto L75
        L36:
            java.lang.String r0 = "120122111301201"
            boolean r10 = r10.equals(r0)
            if (r10 != 0) goto L75
            goto L29
        L3f:
            java.lang.String r0 = "120033108272201"
            boolean r10 = r10.equals(r0)
            if (r10 != 0) goto L48
            goto L29
        L48:
            r1 = 4
            goto L75
        L4a:
            java.lang.String r0 = "120033108265701"
            boolean r10 = r10.equals(r0)
            if (r10 != 0) goto L53
            goto L29
        L53:
            r1 = 3
            goto L75
        L55:
            java.lang.String r0 = "120033108263401"
            boolean r10 = r10.equals(r0)
            if (r10 != 0) goto L5e
            goto L29
        L5e:
            r1 = 2
            goto L75
        L60:
            java.lang.String r0 = "120033108255901"
            boolean r10 = r10.equals(r0)
            if (r10 != 0) goto L69
            goto L29
        L69:
            r1 = 1
            goto L75
        L6b:
            java.lang.String r0 = "120033108251501"
            boolean r10 = r10.equals(r0)
            if (r10 != 0) goto L74
            goto L29
        L74:
            r1 = 0
        L75:
            switch(r1) {
                case 0: goto L79;
                case 1: goto L79;
                case 2: goto L79;
                case 3: goto L79;
                case 4: goto L79;
                case 5: goto L79;
                case 6: goto L79;
                default: goto L78;
            }
        L78:
            goto La1
        L79:
            return r4
        L7a:
            int r0 = r10.smartType
            if (r0 != r5) goto La1
            com.ltech.smarthome.model.Repository r0 = com.ltech.smarthome.model.Injection.repo()
            com.ltech.smarthome.model.repo.ifun.IGroup r0 = r0.group()
            long r7 = r10.objectId
            com.ltech.smarthome.model.bean.Group r10 = r0.getGroupByGroupId(r7)
            if (r10 == 0) goto La1
            int r10 = com.ltech.smarthome.model.repo.ProductRepository.getLightColorType(r10)
            if (r10 == r6) goto La0
            if (r10 == r5) goto La0
            if (r10 == r3) goto La0
            if (r10 == r2) goto La0
            if (r10 == r1) goto La0
            r0 = 7
            if (r10 == r0) goto La0
            goto La1
        La0:
            return r4
        La1:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorVM.isExitEnable(com.ltech.smarthome.model.device_param.WaveSensorExtParam$SensorParam):boolean");
    }

    public String getLightAction(WaveSensorExtParam.SensorParam param, Object object) {
        Activity topActivity;
        topActivity = ActivityUtils.getTopActivity();
        String str = param.option;
        str.hashCode();
        switch (str) {
            case "0":
            case "11":
                if (ProductRepository.getLightColorType(object) == 31) {
                    return topActivity.getString(R.string.app_zdy_status);
                }
                return SceneHelper.getLightStaticAction(topActivity, ProductRepository.getLightColorType(object), String.valueOf(param.optionValue), SceneHelper.getLightCmdData(param.instruct.substring(10, 12) + param.instruct.substring(16)), object);
            case "4":
                return topActivity.getString(R.string.light_off);
            case "7":
                return topActivity.getString(R.string.light_on);
            default:
                return topActivity.getString(R.string.please_choose);
        }
    }

    public String getSmartPanelAction(WaveSensorExtParam.SensorParam param) {
        getRelateObject(param);
        Activity topActivity = ActivityUtils.getTopActivity();
        Object obj = this.relateObject;
        if (obj != null) {
            return getSmartPanelAction(topActivity, RelateInfoUtils.getSwitchNameArray(obj), param.option, param.optionValue);
        }
        if (param.option.equals("1")) {
            return topActivity.getString(R.string.close) + NameRepository.getSmartPanelS4KeyName(topActivity)[param.optionValue - 1];
        }
        if (param.option.equals("0")) {
            return topActivity.getString(R.string.on) + NameRepository.getSmartPanelS4KeyName(topActivity)[param.optionValue - 1];
        }
        return topActivity.getString(R.string.please_choose);
    }

    public static String getSmartPanelAction(Context context, String[] array, String option, int optionValue) {
        StringBuilder sb = new StringBuilder();
        option.hashCode();
        int i = 0;
        if (option.equals("0")) {
            while (i < array.length) {
                if (((optionValue >> i) & 1) != 0) {
                    if (sb.length() == 0) {
                        sb.append(array[i]);
                    } else {
                        sb.append("、" + array[i]);
                    }
                }
                i++;
            }
            if (sb.length() == 0) {
                return "";
            }
            return context.getString(R.string.switch_on) + sb.toString();
        }
        if (!option.equals("1")) {
            return "";
        }
        while (i < array.length) {
            if (((optionValue >> i) & 1) != 0) {
                if (sb.length() == 0) {
                    sb.append(array[i]);
                } else {
                    sb.append("、" + array[i]);
                }
            }
            i++;
        }
        if (sb.length() == 0) {
            return "";
        }
        return context.getString(R.string.switch_close) + sb.toString();
    }

    public String getCurtainAction(WaveSensorExtParam.SensorParam param) {
        Activity topActivity = ActivityUtils.getTopActivity();
        if (param.option.equals("1")) {
            return topActivity.getString(R.string.open_curtain);
        }
        if (param.option.equals("2")) {
            return topActivity.getString(R.string.close_curtain);
        }
        if (param.option.equals("3")) {
            return topActivity.getString(R.string.preview_stop);
        }
        if (param.option.equals("4")) {
            return topActivity.getString(R.string.app_str_curtain_mode_getup);
        }
        if (param.option.equals("5")) {
            return topActivity.getString(R.string.app_str_curtain_mode_sleep);
        }
        if (param.option.equals("6")) {
            return topActivity.getString(R.string.app_str_curtain_mode_shimmer);
        }
        if (param.option.equals("7")) {
            return topActivity.getString(R.string.app_str_curtain_mode_greeting);
        }
        if (param.option.equals("8")) {
            if (param.optionValue == 0) {
                return topActivity.getString(R.string.app_str_all_close);
            }
            if (param.optionValue == 100) {
                return topActivity.getString(R.string.app_str_all_open);
            }
            return topActivity.getString(R.string.app_str_curtain_open) + param.optionValue + "%";
        }
        return topActivity.getString(R.string.please_choose);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0031, code lost:
    
        if (r0.equals(com.ltech.smarthome.model.product.ProductId.ID_BLE_CURTAIN_CG_CURH3) == false) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void goSelectAction(java.lang.Object r11) {
        /*
            Method dump skipped, instructions count: 300
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorVM.goSelectAction(java.lang.Object):void");
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    public boolean isGroupEmpty() {
        if (!((Group) this.controlObject.getValue()).getDeviceIds().isEmpty()) {
            return false;
        }
        SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_group_empty));
        return true;
    }

    public void updateWaveSensorExt(final WaveSensorExtParam extParam) {
        if (!this.groupControl) {
            final Device device = (Device) this.controlObject.getValue();
            ((ObservableSubscribeProxy) Injection.net().updateParamExt(device.getDeviceId(), extParam.getSensorParamMapString()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorVM$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActWaveSensorVM.lambda$updateWaveSensorExt$0(Device.this, extParam, obj);
                }
            }, new SmartErrorComsumer());
        } else {
            final Group group = (Group) this.controlObject.getValue();
            ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(group.getGroupId(), extParam.getSensorParamMapString()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((AppCompatActivity) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorVM$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActWaveSensorVM.lambda$updateWaveSensorExt$1(Group.this, extParam, obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    static /* synthetic */ void lambda$updateWaveSensorExt$0(Device device, WaveSensorExtParam waveSensorExtParam, Object obj) throws Exception {
        Device deviceById = Injection.repo().device().getDeviceById(device.getId());
        deviceById.setExtParam(waveSensorExtParam.getSensorParamMapString());
        Injection.repo().device().saveDevice(deviceById);
    }

    static /* synthetic */ void lambda$updateWaveSensorExt$1(Group group, WaveSensorExtParam waveSensorExtParam, Object obj) throws Exception {
        Group groupByGroupId = Injection.repo().group().getGroupByGroupId(group.getGroupId());
        groupByGroupId.setExtParam(waveSensorExtParam.getSensorParamMapString());
        Injection.repo().group().saveGroup(groupByGroupId);
    }

    public String getRepeatString(int repeat) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            if (((1 << i) & repeat) != 0) {
                sb.append(i + 1);
                sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
            }
        }
        if (sb.length() != 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public int getStayTimeIndex(int delayTime) {
        int[] iArr = {0, 5, 10, 30, 60, 180, 300, 600, 1200, WinError.ERROR_INVALID_PRIORITY, 65535};
        for (int i = 0; i < 11; i++) {
            if (iArr[i] == delayTime) {
                return i;
            }
        }
        return 0;
    }

    public static class ParamItem {
        public GoItem firstGoItem;
        public int index;
        public boolean on;
        public GoItem secondGoItem;
        public String title;
        public int type;

        public static ParamItem paramOne(String title, GoItem goItem) {
            ParamItem paramItem = new ParamItem();
            paramItem.title = title;
            paramItem.firstGoItem = goItem;
            return paramItem;
        }

        public static ParamItem paramTwo(String title, GoItem firstGoItem, GoItem secondGoItem) {
            ParamItem paramItem = new ParamItem();
            paramItem.title = title;
            paramItem.firstGoItem = firstGoItem;
            paramItem.secondGoItem = secondGoItem;
            return paramItem;
        }

        public static ParamItem paramThree(int index, String title, boolean on) {
            ParamItem paramItem = new ParamItem();
            paramItem.title = title;
            paramItem.index = index;
            paramItem.type = 1;
            paramItem.on = on;
            return paramItem;
        }
    }
}