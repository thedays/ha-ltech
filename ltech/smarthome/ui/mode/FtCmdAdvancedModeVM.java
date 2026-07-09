package com.ltech.smarthome.ui.mode;

import android.content.Context;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.ModeAssistant;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.bean.AdvancedModeList;
import com.smart.product_agreement.parser.IModeParser;
import java.util.List;

/* loaded from: classes4.dex */
public class FtCmdAdvancedModeVM extends BaseFtAdvancedModeVM {
    @Override // com.ltech.smarthome.ui.mode.IFtAdvancedMode
    public void queryModeList(Context context, final IAction<AdvancedModeList> result) {
        getModeCmdHelper().queryAdvancedModeList(context, new IAction() { // from class: com.ltech.smarthome.ui.mode.FtCmdAdvancedModeVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtCmdAdvancedModeVM.lambda$queryModeList$0(IAction.this, (ResponseMsg) obj);
            }
        });
    }

    static /* synthetic */ void lambda$queryModeList$0(IAction iAction, ResponseMsg responseMsg) {
        if (responseMsg == null) {
            iAction.act(null);
        } else {
            iAction.act(((IModeParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parseAdvancedModeList(responseMsg.getResData()));
        }
    }

    @Override // com.ltech.smarthome.ui.mode.IFtAdvancedMode
    public void changeModeName(Context context, int modeNum, String modeName, IAction<Boolean> result) {
        getModeCmdHelper().changeAdvancedModeName(context, modeNum, modeName, result);
    }

    @Override // com.ltech.smarthome.ui.mode.IFtAdvancedMode
    public void resetMode(Context context, int modeNum) {
        getModeCmdHelper().resetAdvancedMode(context, modeNum);
    }

    @Override // com.ltech.smarthome.ui.mode.IFtAdvancedMode
    public void playMode(Context context, List<Integer> playList, int playTimes, boolean listPlay) {
        getModeCmdHelper().playAdvancedMode(context, playList, playTimes, listPlay);
    }

    @Override // com.ltech.smarthome.ui.mode.IFtAdvancedMode
    public void playAllList(Context context) {
        playMode(context, this.playList, this.listPlayTime.getValue().intValue(), true);
    }

    private ModeAssistant getModeCmdHelper() {
        return CmdAssistant.getModeCmdAssistant(this.controlObject, this.zoneNum);
    }
}