package com.ltech.smarthome.model.auto;

import android.content.Context;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.cg485.Cg485Helper;
import com.ltech.smarthome.utils.LanguageUtils;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes4.dex */
public class DeviceConditionParam {
    public String deviceName;
    public long deviceid;
    public String floorRoom;
    public int index;
    public String mac;
    public int operator;
    public String productid;
    public int subIndex;
    public int type;
    public int unicastAddress;
    public int value;
    public int value2;

    public int getValue2() {
        return this.value2;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }

    public String getProductid() {
        return this.productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getUnicastAddress() {
        return this.unicastAddress;
    }

    public void setUnicastAddress(int unicastAddress) {
        this.unicastAddress = unicastAddress;
    }

    public int getOperator() {
        return this.operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getSubIndex() {
        return this.subIndex;
    }

    public void setSubIndex(int subIndex) {
        this.subIndex = subIndex;
    }

    public String getFloorRoom() {
        return this.floorRoom;
    }

    public void setFloorRoom(String floorRoom) {
        this.floorRoom = floorRoom;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public long getDeviceid() {
        return this.deviceid;
    }

    public void setDeviceid(long deviceid) {
        this.deviceid = deviceid;
    }

    public String getState(Context context) {
        int i = this.type;
        String str = "";
        if (i == 1 || i == 6) {
            if (ProductId.ID_EUR_PANEL_EB6.equals(this.productid)) {
                int i2 = this.index;
                str = i2 == 1 ? context.getString(R.string.single_click_knob) : i2 == 2 ? context.getString(R.string.double_click_knob) : i2 == 9 ? context.getString(R.string.rotate_knob) : i2 == 10 ? context.getString(R.string.press_and_rotate) : context.getResources().getStringArray(R.array.click_scene_array)[this.index - 11];
            } else if (ProductId.ID_SMART_PANEL_GQX.equals(this.productid) || ProductId.ID_SMART_PANEL_GQ.equals(this.productid)) {
                int i3 = this.index;
                if (i3 == 1) {
                    str = context.getString(R.string.gqx_condition_1);
                } else if (i3 == 3) {
                    str = context.getString(R.string.gqx_condition_2);
                } else if (i3 == 5) {
                    str = context.getString(R.string.gqx_condition_3);
                } else if (i3 == 7) {
                    str = context.getString(R.string.gqx_condition_4);
                } else if (i3 == 11) {
                    str = context.getString(R.string.gqx_condition_5);
                } else if (i3 == 12) {
                    str = context.getString(R.string.gqx_condition_6);
                }
            } else if (ProductRepository.isEurPanel(this.productid)) {
                str = context.getResources().getStringArray(R.array.click_scene_array)[this.index - 11];
            } else if (ProductRepository.isAsPanel(this.productid)) {
                str = context.getResources().getStringArray(R.array.click_scene_array)[this.index - 11];
            } else if (ProductRepository.isRcB(this.productid)) {
                str = context.getResources().getStringArray(R.array.click_scene_array)[this.index - 1];
            } else if (ProductId.ID_DOOR_SENSOR.equals(this.productid)) {
                str = new String[]{context.getString(R.string.function_close), context.getString(R.string.function_open)}[this.index - 1];
            } else if (ProductId.ID_RC4S.equals(this.productid)) {
                switch (this.index) {
                    case 1:
                        str = String.format("%s%s%s", context.getString(R.string.zone), 1, context.getString(R.string.on));
                        break;
                    case 2:
                        str = String.format("%s%s%s", context.getString(R.string.zone), 1, context.getString(R.string.off));
                        break;
                    case 3:
                        str = String.format("%s%s%s", context.getString(R.string.zone), 2, context.getString(R.string.on));
                        break;
                    case 4:
                        str = String.format("%s%s%s", context.getString(R.string.zone), 2, context.getString(R.string.off));
                        break;
                    case 5:
                        str = String.format("%s%s%s", context.getString(R.string.zone), 3, context.getString(R.string.on));
                        break;
                    case 6:
                        str = String.format("%s%s%s", context.getString(R.string.zone), 3, context.getString(R.string.off));
                        break;
                    case 7:
                        str = String.format("%s%s%s", context.getString(R.string.zone), 4, context.getString(R.string.on));
                        break;
                    case 8:
                        str = String.format("%s%s%s", context.getString(R.string.zone), 4, context.getString(R.string.off));
                        break;
                }
            } else {
                switch (this.index) {
                    case 1:
                        str = context.getString(R.string.switch4_click_key1);
                        break;
                    case 2:
                        str = context.getString(R.string.switch4_double_click_key1);
                        break;
                    case 3:
                        str = context.getString(R.string.switch4_click_key2);
                        break;
                    case 4:
                        str = context.getString(R.string.switch4_double_click_key2);
                        break;
                    case 5:
                        str = context.getString(R.string.switch4_click_key3);
                        break;
                    case 6:
                        str = context.getString(R.string.switch4_double_click_key3);
                        break;
                    case 7:
                        str = context.getString(R.string.switch4_click_key4);
                        break;
                    case 8:
                        str = context.getString(R.string.switch4_double_click_key4);
                        break;
                    case 9:
                        str = context.getString(R.string.switch8_click_key5);
                        break;
                    case 10:
                        str = context.getString(R.string.switch4_double_click_key1);
                        break;
                    case 11:
                        str = context.getString(R.string.switch8_click_key6);
                        break;
                    case 12:
                        str = context.getString(R.string.switch4_double_click_key2);
                        break;
                    case 13:
                        str = context.getString(R.string.switch8_click_key7);
                        break;
                    case 14:
                        str = context.getString(R.string.switch4_double_click_key3);
                        break;
                    case 15:
                        str = context.getString(R.string.switch8_click_key8);
                        break;
                    case 16:
                        str = context.getString(R.string.switch4_double_click_key4);
                        break;
                    case 17:
                        str = context.getString(R.string.switch8_click_key9);
                        break;
                    case 19:
                        str = context.getString(R.string.switch8_click_key10);
                        break;
                    case 21:
                        str = context.getString(R.string.switch8_click_key11);
                        break;
                    case 23:
                        str = context.getString(R.string.switch8_click_key12);
                        break;
                }
            }
        } else {
            int i4 = R.string.app_str_blow;
            if (i == 2) {
                if (ProductId.ID_BODY_SENSOR.equals(this.productid)) {
                    switch (this.index) {
                        case 1:
                            str = context.getString(R.string.sensor_condition1);
                            break;
                        case 2:
                            str = context.getString(R.string.sensor_condition2);
                            break;
                        case 3:
                            str = context.getString(R.string.sensor_condition3);
                            break;
                        case 4:
                            str = context.getString(R.string.sensor_condition4);
                            break;
                        case 5:
                            str = context.getString(R.string.sensor_condition5);
                            break;
                        case 6:
                            str = context.getString(R.string.sensor_condition6);
                            break;
                        case 7:
                            str = context.getString(R.string.sensor_condition7);
                            break;
                    }
                } else {
                    int i5 = this.index;
                    if (i5 == 0) {
                        str = context.getString(R.string.mr_state_value_1);
                    } else if (i5 == 1) {
                        str = context.getString(R.string.mr_state_value_5);
                    } else if (i5 == 2) {
                        str = context.getString(R.string.mr_state_value_3);
                    } else if (i5 == 3) {
                        str = context.getString(R.string.mr_state_value_4);
                    } else if (i5 == 5) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(this.subIndex == 1 ? context.getString(R.string.lux_title) : context.getString(R.string.ct_title));
                        sb.append(LanguageUtils.isChinese(context) ? "" : " ");
                        sb.append(this.operator == 3 ? context.getString(R.string.app_str_blow) : context.getString(R.string.app_str_above));
                        sb.append(LanguageUtils.isChinese(context) ? "" : " ");
                        sb.append(this.value);
                        sb.append(this.subIndex != 1 ? "K" : "Lux");
                        str = sb.toString();
                    }
                }
            } else if (i == 3) {
                int i6 = this.index;
                if (i6 >= 1 && i6 <= 15) {
                    str = context.getResources().getStringArray(R.array.key_trig_to_ble_condition)[this.index - 1];
                }
            } else if (i == 4) {
                int i7 = this.index;
                if (i7 == 1) {
                    str = context.getString(R.string.single_click);
                } else if (i7 == 2) {
                    str = context.getString(R.string.double_click);
                } else if (i7 == 9) {
                    str = context.getString(R.string.rotate);
                } else if (i7 == 10) {
                    str = context.getString(R.string.press_and_rotate);
                }
            } else if (i == 5) {
                str = Cg485Helper.getInstructByIndex(this.deviceid, this.index);
            } else if (i == 7) {
                if (ProductId.ID_BLE_CURTAIN.equals(this.productid) || ProductId.ID_BLE_CURTAIN_CG_CURH3.equals(this.productid)) {
                    str = new String[]{context.getString(R.string.when_curtain_on), context.getString(R.string.when_curtain_close), context.getString(R.string.when_curtain_stop)}[this.index - 1];
                }
            } else if (i == 8) {
                int i8 = this.index;
                if (i8 == 0) {
                    int i9 = this.value;
                    str = i9 == 2 ? context.getString(R.string.no_motion_2_min) : i9 == 5 ? context.getString(R.string.no_motion_5_min) : context.getString(R.string.diy_no_motion_time);
                } else if (i8 != 1) {
                    if (i8 == 5) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(this.subIndex == 1 ? context.getString(R.string.lux_title) : context.getString(R.string.ct_title));
                        sb2.append(LanguageUtils.isChinese(context) ? "" : " ");
                        sb2.append(this.operator == 3 ? context.getString(R.string.app_str_blow) : context.getString(R.string.app_str_above));
                        sb2.append(LanguageUtils.isChinese(context) ? "" : " ");
                        sb2.append(this.value);
                        sb2.append(this.subIndex != 1 ? "K" : "Lux");
                        str = sb2.toString();
                    }
                } else if (this.value == 65535) {
                    str = context.getString(R.string.someone_move);
                } else {
                    String string = context.getString(R.string.someone_move);
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(context.getString(R.string.lux_title));
                    if (this.operator != 3) {
                        i4 = R.string.app_str_above;
                    }
                    sb3.append(context.getString(i4));
                    sb3.append(this.value);
                    sb3.append("Lux");
                    str = context.getString(R.string.someone_move_and_other, string, sb3.toString());
                }
            }
        }
        return this.floorRoom + " " + str;
    }

    public String getStatusConditionName(Context context) {
        String str;
        String string;
        int i = this.type;
        if (i == 1) {
            string = context.getString(R.string.app_str_key) + this.index + (getValue() == 0 ? context.getString(R.string.app_str_off_status) : context.getString(R.string.app_str_on_status));
        } else {
            str = "";
            if (i == 2) {
                int i2 = this.subIndex;
                if (i2 == 1) {
                    string = getValue() == 0 ? context.getString(R.string.mr_state_value_1) : context.getString(R.string.mr_state_value_5);
                } else if (i2 == 2) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(context.getString(R.string.lux_title));
                    sb.append(this.operator == 2 ? context.getString(R.string.app_str_above) : context.getString(R.string.app_str_blow));
                    sb.append(this.value);
                    sb.append("Lux");
                    string = sb.toString();
                } else {
                    if (i2 == 3) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(context.getString(R.string.ct_title));
                        sb2.append(this.operator == 2 ? context.getString(R.string.app_str_above) : context.getString(R.string.app_str_blow));
                        sb2.append(this.value);
                        sb2.append("K");
                        string = sb2.toString();
                    }
                    string = "";
                }
            } else if (i == 3) {
                string = getValue() == 0 ? context.getString(R.string.app_str_off_status) : context.getString(R.string.app_str_on_status);
            } else if (i == 5) {
                int i3 = this.operator;
                if (i3 == 2) {
                    str = context.getString(R.string.great_than);
                } else if (i3 == 3) {
                    str = context.getString(R.string.less_than);
                } else if (i3 == 4) {
                    str = context.getString(R.string.app_str_range);
                }
                String str2 = String.format(context.getString(R.string.app_str_specify_parameters), context.getString(R.string.app_str_location)) + str;
                if (this.operator == 4) {
                    string = str2 + this.value + Constants.ACCEPT_TIME_SEPARATOR_SERVER + this.value2 + "%";
                } else {
                    string = str2 + this.value + "%";
                }
            } else if (i == 6) {
                int i4 = this.subIndex;
                if (i4 == 1) {
                    string = String.format(context.getString(R.string.app_str_ac_power_status), this.value == 0 ? context.getString(R.string.function_close) : context.getString(R.string.function_open));
                } else if (i4 == 2) {
                    int operator = getOperator();
                    if (operator == 2) {
                        str = context.getString(R.string.app_str_above);
                    } else if (operator == 3) {
                        str = context.getString(R.string.app_str_blow);
                    }
                    string = context.getString(R.string.app_str_ac_temp_set) + str + this.value + "°C";
                } else if (i4 == 3) {
                    int i5 = this.value;
                    if (i5 == 0) {
                        string = context.getString(R.string.fan_speed_1);
                    } else if (i5 == 1) {
                        string = context.getString(R.string.fan_speed_4);
                    } else if (i5 == 2) {
                        string = context.getString(R.string.fan_speed_3);
                    } else if (i5 == 3) {
                        string = context.getString(R.string.fan_speed_5);
                    } else if (i5 != 4) {
                        if (i5 == 5) {
                            string = context.getString(R.string.fan_speed_6);
                        }
                        string = "";
                    } else {
                        string = context.getString(R.string.fan_speed_2);
                    }
                } else {
                    if (i4 == 4) {
                        switch (this.value) {
                            case 1:
                                string = context.getString(R.string.air_mode_1);
                                break;
                            case 2:
                                string = context.getString(R.string.air_mode_5);
                                break;
                            case 3:
                                string = context.getString(R.string.refreshing);
                                break;
                            case 4:
                                string = context.getString(R.string.air_mode_4);
                                break;
                            case 5:
                                string = context.getString(R.string.automatic_dehumidification);
                                break;
                            case 6:
                                string = context.getString(R.string.intimate_sleep);
                                break;
                            case 8:
                                string = context.getString(R.string.air_mode_2);
                                break;
                            case 9:
                                string = context.getString(R.string.floor_heat);
                                break;
                            case 10:
                                string = context.getString(R.string.strong_heating);
                                break;
                        }
                    }
                    string = "";
                }
            } else {
                if (i == 8) {
                    int i6 = this.subIndex;
                    if (i6 == 1) {
                        string = context.getString(R.string.someone_move);
                    } else if (i6 == 2) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(context.getString(R.string.lux_title));
                        sb3.append(LanguageUtils.isChinese(context) ? "" : " ");
                        sb3.append(this.operator == 3 ? context.getString(R.string.app_str_blow) : context.getString(R.string.app_str_above));
                        sb3.append(LanguageUtils.isChinese(context) ? "" : " ");
                        sb3.append(this.value);
                        sb3.append("Lux");
                        string = sb3.toString();
                    } else if (i6 == 3) {
                        int i7 = this.value;
                        if (i7 == 2) {
                            string = context.getString(R.string.no_motion_2_min);
                        } else if (i7 == 5) {
                            string = context.getString(R.string.no_motion_5_min);
                        } else {
                            string = context.getString(R.string.diy_no_motion_time);
                        }
                    }
                }
                string = "";
            }
        }
        return this.floorRoom + " " + string;
    }

    public String toString() {
        return "DeviceConditionParam{type=" + this.type + ", index=" + this.index + ", deviceid=" + this.deviceid + ", floorRoom='" + this.floorRoom + "', deviceName='" + this.deviceName + "'}";
    }
}