package com.ltech.smarthome.ui.mode;

import android.content.Context;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.ModeAssistant;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.bean.GeneralModeInfo;
import com.smart.product_agreement.bean.GeneralModeList;
import com.smart.product_agreement.parser.IModeParser;
import java.util.List;

/* loaded from: classes4.dex */
public class ActCmdEditGeneralModeVM extends BaseEditGeneralModeVM {
    @Override // com.ltech.smarthome.ui.mode.IEditGeneralMode
    public void queryModeList(Context context, final IAction<GeneralModeList> result) {
        getModeCmdHelper().queryGeneralModeList(context, new IAction() { // from class: com.ltech.smarthome.ui.mode.ActCmdEditGeneralModeVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActCmdEditGeneralModeVM.lambda$queryModeList$0(IAction.this, (ResponseMsg) obj);
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

    @Override // com.ltech.smarthome.ui.mode.IEditGeneralMode
    public void queryModeInfo(Context context, int modeNum, final IAction<GeneralModeInfo> result) {
        getModeCmdHelper().queryGeneralModeInfo(context, modeNum, new IAction() { // from class: com.ltech.smarthome.ui.mode.ActCmdEditGeneralModeVM$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActCmdEditGeneralModeVM.lambda$queryModeInfo$1(IAction.this, (ResponseMsg) obj);
            }
        });
    }

    static /* synthetic */ void lambda$queryModeInfo$1(IAction iAction, ResponseMsg responseMsg) {
        if (responseMsg == null) {
            iAction.act(null);
        } else {
            iAction.act(((IModeParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parseGeneralModeInfo(responseMsg.getResData()));
        }
    }

    @Override // com.ltech.smarthome.ui.mode.IEditGeneralMode
    public void previewModeInfo(Context context, int modeNum, GeneralModeInfo modeInfo) {
        getModePreviewCmdHelper().previewGeneralModeInfo(context, modeNum, modeInfo);
    }

    @Override // com.ltech.smarthome.ui.mode.IEditGeneralMode
    public void pausePreview(Context context, int modeNum) {
        getModePreviewCmdHelper().pauseGeneralModeInfo(context, modeNum, new GeneralModeInfo[0]);
    }

    @Override // com.ltech.smarthome.ui.mode.IEditGeneralMode
    public void changeModeName(Context context, int modeNum, String modeName, IAction<Boolean> result) {
        getModeCmdHelper().changeGeneralModeName(context, modeNum, modeName, result);
    }

    @Override // com.ltech.smarthome.ui.mode.IEditGeneralMode
    public void resetMode(Context context, int modeNum) {
        getModeCmdHelper().resetGeneralMode(context, modeNum);
    }

    @Override // com.ltech.smarthome.ui.mode.IEditGeneralMode
    public void playMode(Context context, List<Integer> playList, int playTimes, boolean listPlay) {
        getModeCmdHelper().playGeneralMode(context, playList, playTimes, listPlay);
    }

    @Override // com.ltech.smarthome.ui.mode.IEditGeneralMode
    public void playAllList(Context context) {
        playMode(context, this.playList, this.listPlayTime.getValue().intValue(), true);
    }

    @Override // com.ltech.smarthome.ui.mode.IEditGeneralMode
    public void saveModeInfo(Context context, int modeNum, GeneralModeInfo modeInfo, IAction<Boolean> result) {
        getModeCmdHelper().saveGeneralModeInfo(context, modeNum, modeInfo, result);
    }

    private ModeAssistant getModeCmdHelper() {
        return CmdAssistant.getModeCmdAssistant(this.controlObject, this.zoneNum);
    }

    private ModeAssistant getModePreviewCmdHelper() {
        return CmdAssistant.getModeCmdAssistant(this.controlObject, getModeZoneNum((Device) this.controlObject));
    }

    private int getModeZoneNum(Device device) {
        if (ProductRepository.isAsPanel(device) || ProductRepository.isEurPanel(device)) {
            return 15;
        }
        return this.zoneNum;
    }
}