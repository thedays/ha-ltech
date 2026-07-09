package com.ltech.smarthome.utils.cmd_assistant;

import com.ltech.smarthome.R;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class CmdResCode {
    static HashMap<Integer, Integer> RES_CODE;

    static {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        RES_CODE = hashMap;
        hashMap.put(0, Integer.valueOf(R.string.cmd_ok));
        RES_CODE.put(1, Integer.valueOf(R.string.cmd_fail));
        RES_CODE.put(2, Integer.valueOf(R.string.cmd_param_out_range));
        RES_CODE.put(3, Integer.valueOf(R.string.cmd_param_no_set));
        RES_CODE.put(4, Integer.valueOf(R.string.cmd_password_error));
        RES_CODE.put(5, Integer.valueOf(R.string.cmd_frame_ok));
        RES_CODE.put(6, Integer.valueOf(R.string.cmd_frame_error));
        RES_CODE.put(7, Integer.valueOf(R.string.cmd_update_cancel));
        RES_CODE.put(8, Integer.valueOf(R.string.cmd_update_ok));
        RES_CODE.put(9, Integer.valueOf(R.string.cmd_firmware_version_not_match));
        RES_CODE.put(153, Integer.valueOf(R.string.cmd_no_function));
        RES_CODE.put(10, Integer.valueOf(R.string.cmd_firmware_need_upgrade));
    }
}