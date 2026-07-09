package com.ltech.smarthome.net.request.device;

import com.alibaba.fastjson.JSONObject;
import com.ltech.smarthome.push.PushContentParamKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class UpdateBackDataRequest {
    public static final String ADVANCE_MODE = "advanced_";
    public static final String BIND = "switch_bind_";
    public static final String BRIGHTNESS_BUTTON = "brightness_button";
    public static final String BUZZER_STATUS = "buzzerStatus";
    public static final String CIRCADIAN_PLAN = "circadianLighting";
    public static final String CONTROL_ACTION = "controlAction_";
    public static final String CONTROL_INPUT_TYPE = "control_input_type";
    public static final String CONTROL_OBJECT = "controlObject_";
    public static final String CT_DIM_TYPE = "ctDimmerType";
    public static final String CT_RANGE = "kelvin";
    public static final String CURTAIN_MODES = "curtainModes_";
    public static final String CURTAIN_SETTING = "curtainSetting";
    public static final String DALI_ON_OFF = "daliOnOff";
    public static final String DIM_DEPTH = "dimmerDepth";
    public static final String DIM_RANGE = "dimmerRange";
    public static final String DRY_CONTACT_BLE_BIND = "dryContactBleSubscribe_";
    public static final String DRY_CONTACT_CHANNEL = "dryContactChannel_";
    public static final String DRY_CONTACT_SIGNAL_CONTROL = "dryContactSignalControl";
    public static final String FADE_TIME = "fadeTime";
    public static final String GATEWAY_NIGHT_MODE = "gateway_night_mode";
    public static final String GENERAL_MODE = "normal_";
    public static final String ICON_MODE = "switch_icon_mode";
    public static final String INDICATOR_STATUS = "indicatorStatus";
    public static final String KEY_SAVE = "sceneLongPress";
    public static final String KNOB_DOUBLE_MEMORY = "knobDoubleMemory";
    public static final String KNOB_SENSITIVITY = "knobSensitivity";
    public static final String KNOB_SORT = "knobSort";
    public static final String K_VALUE = "switch_kvalue_";
    public static final String LANGUAGE = "switch_language";
    public static final String LIGHT_SEQUENCE = "lightSequence";
    public static final String LIGHT_TYPE = "lightType";
    public static final String LONG_BIND = "switch_long_bind_";
    public static final String LONG_TEXT = "switch_long_text_";
    public static final String NIGHT_MODE = "switch_night_mode";
    public static final String NIGHT_UP_MODE = "night_action_enabled";
    public static final String NIGHT_UP_MODE_EXC_SCENE = "night_action_trigger";
    public static final String NIGHT_UP_MODE_RESET_SCENE = "night_action_anew";
    public static final String NIGHT_UP_MODE_TIME = "night_action_time";
    public static final String OUTPUT_TYPE = "outputSignal";
    public static final String POWER_FADE_TIME = "powerFadeTime";
    public static final String POWER_STATUS = "powerStatus";
    public static final String RELAY_POSITION = "switch_positions";
    public static final String RGB_TYPE = "daliRgbType";
    public static final String RS485_TO_BLE_ACTION = "rs485ToBleAction_";
    public static final String SCENE_BIND = "scene_bind_";
    public static final String SCENE_FADE_TIME = "sceneFadeTime";
    public static final String SCREEN_REST = "screen_rest";
    public static final String SCREEN_SAVER = "screen_saver";
    public static final String SCREEN_VISIBLE = "screen_visible_";
    public static final String SCREEN_VISIBLE_TIME = "screen_visible_time_";
    public static final String SCREEN_VISIBLE_TIME_ENABLED = "screen_visible_time_enabled_";
    public static final String SENSING_DISTANCE = "sensor_sensitivity";
    public static final String SENSING_DISTANCE_ENABLED = "sensor_enabled";
    public static final String SERIAL_PORT_BPS = "serialPortBps";
    public static final String SERIAL_PORT_CHECK = "serialPortCheck";
    public static final String SERIAL_PORT_DATA_BIT = "serialPortDataBit";
    public static final String SERIAL_PORT_END_BIT = "serialPortEndBit";
    public static final String SPI_PARAM = "spiParam";
    public static final String SPI_PLAY_LIST = "spiPlaylist_";
    public static final String SWITCH_ACTION_DELAY = "switch_action_delay_";
    public static final String TEXT = "switch_text_";
    public static final String TRIGGER_DELAY = "trigger_delay_";
    public static final String TRIGGER_SCENE = "trigger_scene_";
    private List<JSONObject> devices = new ArrayList();

    public UpdateBackDataRequest(List<RowsBean> list) {
        Iterator<RowsBean> it = list.iterator();
        while (it.hasNext()) {
            this.devices.add(it.next().getRequestParam());
        }
    }

    public static final class RowsBean {
        private String cmdData;
        private long deviceId;
        private String flag;
        private int index;
        private JSONObject jsonObject;

        public long getDeviceId() {
            return this.deviceId;
        }

        public RowsBean(long deviceId, String flag, String cmdData) {
            this.index = -1;
            this.jsonObject = new JSONObject();
            this.deviceId = deviceId;
            this.flag = flag;
            this.cmdData = cmdData;
        }

        public RowsBean(long deviceId, String flag, String cmdData, int index) {
            this.index = -1;
            this.jsonObject = new JSONObject();
            this.deviceId = deviceId;
            this.flag = flag;
            this.cmdData = cmdData;
            this.index = index;
        }

        public JSONObject getRequestParam() {
            this.jsonObject.put(PushContentParamKey.DEVICE_ID, (Object) Long.valueOf(this.deviceId));
            int i = this.index;
            if (i == -1) {
                setData(this.flag, this.cmdData);
            } else {
                setIndexData(this.flag, this.cmdData, i);
            }
            return this.jsonObject;
        }

        public void setData(String flag, String data) {
            this.jsonObject.put(flag, (Object) data);
        }

        public void setIndexData(String flag, String data, int index) {
            this.jsonObject.put(flag + index, (Object) data);
        }
    }
}