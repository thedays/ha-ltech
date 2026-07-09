package com.ltech.smarthome.utils.cmd_assistant;

import android.content.Context;
import com.ltech.smarthome.base.IAction;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.param.SyncDataParam;

/* loaded from: classes4.dex */
public class SyncDataAssistant extends BaseAssistant {
    public void syncDeviceData(Context context, int cmd, final IAction<Boolean> iAction) {
        SyncDataParam syncDataParam = new SyncDataParam();
        syncDataParam.setZoneNum(this.zoneNum);
        syncDataParam.setCmdType(1);
        syncDataParam.setCmd(cmd);
        sendWithResult(context, syncDataParam, new IAction<ResponseMsg>(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.SyncDataAssistant.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                IAction iAction2 = iAction;
                if (iAction2 != null) {
                    iAction2.act(Boolean.valueOf(responseMsg != null && responseMsg.getStateCode() == 0));
                }
            }
        });
    }
}