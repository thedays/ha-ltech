package com.ltech.smarthome.ui.mode;

import android.content.Context;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.ModeAssistant;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.bean.GeneralModeList;
import com.smart.product_agreement.parser.IModeParser;
import java.util.List;

/* loaded from: classes4.dex */
public class FtCmdGeneralModeVM extends BaseFtGeneralModeVM {
    @Override // com.ltech.smarthome.ui.mode.IFtGeneralMode
    public void queryModeList(Context context, final IAction<GeneralModeList> result) {
        getModeCmdHelper().queryGeneralModeList(context, new IAction() { // from class: com.ltech.smarthome.ui.mode.FtCmdGeneralModeVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtCmdGeneralModeVM.lambda$queryModeList$0(IAction.this, (ResponseMsg) obj);
            }
        });
    }

    static /* synthetic */ void lambda$queryModeList$0(IAction iAction, ResponseMsg responseMsg) {
        if (responseMsg == null) {
            iAction.act(null);
        } else {
            iAction.act(((IModeParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parseGeneralModeList(responseMsg.getResData()));
        }
    }

    @Override // com.ltech.smarthome.ui.mode.IFtGeneralMode
    public void changeModeName(Context context, int modeNum, String modeName, IAction<Boolean> result) {
        getModeCmdHelper().changeGeneralModeName(context, modeNum, modeName, result);
    }

    @Override // com.ltech.smarthome.ui.mode.IFtGeneralMode
    public void resetMode(Context context, int modeNum) {
        getModeCmdHelper().resetGeneralMode(context, modeNum);
    }

    @Override // com.ltech.smarthome.ui.mode.IFtGeneralMode
    public void playMode(Context context, List<Integer> playList, int playTimes, boolean listPlay) {
        getModeCmdHelper().playGeneralMode(context, playList, playTimes, listPlay);
    }

    @Override // com.ltech.smarthome.ui.mode.IFtGeneralMode
    public void playAllList(Context context) {
        playMode(context, this.playList, this.listPlayTime.getValue().intValue(), true);
    }

    private ModeAssistant getModeCmdHelper() {
        return CmdAssistant.getModeCmdAssistant(this.controlObject, this.zoneNum);
    }
}