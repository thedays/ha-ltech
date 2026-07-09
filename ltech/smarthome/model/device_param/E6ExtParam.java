package com.ltech.smarthome.model.device_param;

import android.content.Context;
import android.text.TextUtils;
import android.util.ArrayMap;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.nfc.utils.DataUtil;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseExtParam;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.utils.BitUtils;
import com.ltech.smarthome.utils.LightUtils;
import java.util.Locale;

/* loaded from: classes4.dex */
public class E6ExtParam extends BaseExtParam {
    private int buzzerStatus = 1;
    private int indicatorStatus = 1;
    private ArrayMap<String, E6Action> actionMap = new ArrayMap<>();

    public String getParamString() {
        JSONObject parseObject = JSONObject.parseObject(GsonUtils.getGson().toJson(this.actionMap));
        parseObject.put(UpdateBackDataRequest.BUZZER_STATUS, (Object) Integer.valueOf(this.buzzerStatus));
        parseObject.put(UpdateBackDataRequest.INDICATOR_STATUS, (Object) Integer.valueOf(this.indicatorStatus));
        putBaseFields(parseObject);
        return parseObject.toJSONString();
    }

    public void fillMapWithString(String infoString) {
        if (this.actionMap == null) {
            this.actionMap = new ArrayMap<>();
        }
        if (infoString != null) {
            JSONObject parseObject = JSONObject.parseObject(infoString);
            for (String str : parseObject.keySet()) {
                if (str.equals("zone0") || str.equals("zone1") || str.equals("zone2") || str.equals("zone3") || str.equals("zone4")) {
                    this.actionMap.put(str, (E6Action) GsonUtils.getGson().fromJson(parseObject.getString(str), E6Action.class));
                } else if (str.equals(UpdateBackDataRequest.BUZZER_STATUS)) {
                    this.buzzerStatus = parseObject.getIntValue(str);
                } else if (str.equals(UpdateBackDataRequest.INDICATOR_STATUS)) {
                    this.indicatorStatus = parseObject.getIntValue(str);
                } else {
                    getBaseFields(parseObject, str);
                }
            }
        }
    }

    public void initActionByType(Device device) {
        int[] iArr;
        int[] iArr2;
        int[] iArr3;
        String productId = device.getProductId();
        int lightColorType = ProductRepository.getLightColorType((Object) device);
        char c2 = 3;
        if (ProductId.ID_KNOB_PANEL_E6D.equals(productId) || ProductId.ID_KNOB_PANEL_E6M.equals(productId)) {
            for (int i = 0; i <= 4; i++) {
                E6Action action = getAction(i);
                if (TextUtils.isEmpty(action.getObjectInstruct())) {
                    if (ProductId.ID_KNOB_PANEL_E6M.equals(productId)) {
                        iArr = new int[]{0, 0, 1};
                    } else {
                        iArr = new int[]{2, 0, 254};
                    }
                    action.setObjectInstruct(DataUtil.formatHexString(iArr));
                    setAction(i, action);
                }
            }
        }
        int i2 = 1;
        for (int i3 = 4; i2 <= i3; i3 = 4) {
            E6Action action2 = getAction(i2);
            if (TextUtils.isEmpty(action2.getActionInstruct())) {
                if (lightColorType == 1) {
                    iArr3 = new int[8];
                    iArr3[0] = 28;
                    iArr3[1] = 0;
                    iArr3[2] = 0;
                    iArr3[c2] = 0;
                    iArr3[i3] = LightUtils.progress2BrtHasBelowOne(i2 * 25);
                    iArr3[5] = 0;
                    iArr3[6] = 0;
                    iArr3[7] = 0;
                } else {
                    if (lightColorType == 2) {
                        int[] iArr4 = {2700, 3500, 5000, 6500};
                        if (device.getMaxkelvin() + device.getMinkelvin() == 0) {
                            iArr2 = new int[]{28, 0, 0, 0, 255, LightUtils.ctK2Y(iArr4[i2 - 1], 6500, 2700), 0};
                        } else {
                            iArr2 = new int[]{28, 0, 0, 0, 255, LightUtils.ctK2Y(iArr4[i2 - 1], 10000, 1000), 0};
                        }
                    } else {
                        int i4 = i2 - 1;
                        iArr2 = new int[]{28, new int[]{255, 255, 0, 255}[i4], new int[]{182, 20, 255, 255}[i4], new int[]{193, 147, 255, 255}[i4], 255, 255, 255};
                    }
                    iArr3 = iArr2;
                }
                action2.setActionInstruct(DataUtil.formatHexString(iArr3));
                setAction(i2, action2);
            }
            i2++;
            c2 = 3;
        }
    }

    public E6Action getAction(int position) {
        if (this.actionMap.get("zone" + position) == null) {
            return new E6Action();
        }
        return this.actionMap.get("zone" + position);
    }

    public void setAction(int position, E6Action action) {
        this.actionMap.put("zone" + position, action);
    }

    public int getBuzzerStatus() {
        return this.buzzerStatus;
    }

    public void setBuzzerStatus(int buzzerStatus) {
        this.buzzerStatus = buzzerStatus;
    }

    public int getIndicatorStatus() {
        return this.indicatorStatus;
    }

    public void setIndicatorStatus(int indicatorStatus) {
        this.indicatorStatus = indicatorStatus;
    }

    public static class E6Action {
        public static final int ADVANCE_MODE = 30;
        public static final int CLOSE = 1;
        public static final int DMX_CHANNEL = 34;
        public static final int GENERAL_MODE = 31;
        public static final int RGBWAF_BRT = 33;
        public static final int SCENE = 12;
        public static final int STATIC_COLOR = 28;
        public static final int THEME = 29;
        public static final int XY_BRT = 32;
        private String actionInstruct;
        private String objectInstruct;

        public String getObjectInstruct() {
            return this.objectInstruct;
        }

        public void setObjectInstruct(String objectInstruct) {
            this.objectInstruct = objectInstruct;
        }

        public String getActionInstruct() {
            return this.actionInstruct;
        }

        public void setActionInstruct(String actionInstruct) {
            this.actionInstruct = actionInstruct;
        }

        public String getObjectName(Context context, String productId) {
            if (!TextUtils.isEmpty(this.objectInstruct)) {
                int parseInt = Integer.parseInt(this.objectInstruct.substring(0, 2), 16);
                int parseInt2 = Integer.parseInt(this.objectInstruct.substring(2, 6), 16);
                if (!ProductId.ID_KNOB_PANEL_E6D.equals(productId)) {
                    return ProductId.ID_KNOB_PANEL_E6M.equals(productId) ? String.format(Locale.US, "%03d", Integer.valueOf(Math.min(parseInt2, 512))) : "";
                }
                if (parseInt == 0) {
                    return context.getString(R.string.single_address) + parseInt2;
                }
                if (parseInt == 1) {
                    return context.getString(R.string.group_address) + parseInt2;
                }
                return context.getString(R.string.dali_broadcast);
            }
            return "";
        }

        public String getActionContent(Context context, Device device) {
            if (TextUtils.isEmpty(this.actionInstruct)) {
                return "";
            }
            int lightColorType = ProductRepository.getLightColorType((Object) device);
            int[] convertStringToArray = BitUtils.convertStringToArray(this.actionInstruct);
            int i = convertStringToArray[0];
            if (i == 1) {
                return context.getString(R.string.light_off_1);
            }
            if (i == 12) {
                return context.getString(R.string.app_scene) + Integer.parseInt(this.actionInstruct.substring(2, 4), 16);
            }
            switch (i) {
                case 28:
                    if (lightColorType != 1) {
                        if (lightColorType != 2) {
                            if (lightColorType != 3) {
                                if (lightColorType == 4) {
                                    break;
                                } else {
                                    break;
                                }
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                case 29:
                    break;
                case 30:
                    break;
                case 31:
                    break;
                case 32:
                    break;
                case 33:
                    break;
            }
            return "";
        }

        public String getChannelAction(int lightType, int[] channel) {
            if (lightType == 1) {
                return "CH1:" + channel[1];
            }
            if (lightType == 2) {
                return "CH1:" + channel[1] + " CH2:" + channel[2];
            }
            if (lightType == 3) {
                return "CH1:" + channel[1] + " CH2:" + channel[2] + " CH3:" + channel[3];
            }
            if (lightType == 4) {
                return "CH1:" + channel[1] + " CH2:" + channel[2] + " CH3:" + channel[3] + " CH4:" + channel[4];
            }
            return "CH1:" + channel[1] + " CH2:" + channel[2] + " CH3:" + channel[3] + " CH4:" + channel[4] + " CH5:" + channel[5];
        }
    }
}