package com.ltech.smarthome.ui.mode;

import android.content.Context;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.ModeAssistant;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.bean.AdvancedModeInfo;
import com.smart.product_agreement.bean.AdvancedModeList;
import com.smart.product_agreement.parser.IModeParser;
import java.util.List;

/* loaded from: classes4.dex */
public class ActCmdEditAdvancedModeVM extends BaseEditAdvancedModeVM {
    @Override // com.ltech.smarthome.ui.mode.IEditAdvancedMode
    public void queryModeList(Context context, final IAction<AdvancedModeList> result) {
        getModeCmdHelper().queryAdvancedModeList(context, new IAction() { // from class: com.ltech.smarthome.ui.mode.ActCmdEditAdvancedModeVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActCmdEditAdvancedModeVM.lambda$queryModeList$0(IAction.this, (ResponseMsg) obj);
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

    @Override // com.ltech.smarthome.ui.mode.IEditAdvancedMode
    public void queryModeInfo(Context context, int modeNum, final IAction<String> result) {
        getModeCmdHelper().queryAdvancedModeInfo(context, modeNum, new IAction() { // from class: com.ltech.smarthome.ui.mode.ActCmdEditAdvancedModeVM$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActCmdEditAdvancedModeVM.lambda$queryModeInfo$1(IAction.this, (ResponseMsg) obj);
            }
        });
    }

    static /* synthetic */ void lambda$queryModeInfo$1(IAction iAction, ResponseMsg responseMsg) {
        if (responseMsg == null) {
            iAction.act(null);
        } else {
            iAction.act(responseMsg.getResData());
        }
    }

    @Override // com.ltech.smarthome.ui.mode.IEditAdvancedMode
    public void saveModeInfo(Context context, int modeNum, int playTime, List<AdvancedModeInfo.ContentItem> contentItemList, IAction<Boolean> result) {
        getModeCmdHelper().saveAdvancedModeInfo(context, modeNum, playTime, contentItemList, result);
    }

    @Override // com.ltech.smarthome.ui.mode.IEditAdvancedMode
    public void previewModeInfo(Context context, int modeNum, int playTime, List<AdvancedModeInfo.ContentItem> contentItemList) {
        getModePreviewCmdHelper().previewAdvancedModeInfo(context, modeNum, playTime, contentItemList);
    }

    @Override // com.ltech.smarthome.ui.mode.IEditAdvancedMode
    public void pausePreview(Context context, int modeNum, List<AdvancedModeInfo.ContentItem> contentItemList) {
        getModePreviewCmdHelper().pauseAdvancedModeInfo(context, modeNum, contentItemList);
    }

    @Override // com.ltech.smarthome.ui.mode.IEditAdvancedMode
    public void changeModeName(Context context, int modeNum, String modeName, IAction<Boolean> result) {
        getModeCmdHelper().changeAdvancedModeName(context, modeNum, modeName, result);
    }

    @Override // com.ltech.smarthome.ui.mode.IEditAdvancedMode
    public void resetMode(Context context, int modeNum) {
        getModeCmdHelper().resetAdvancedMode(context, modeNum);
    }

    @Override // com.ltech.smarthome.ui.mode.IEditAdvancedMode
    public void playMode(Context context, List<Integer> playList, int playTimes, boolean listPlay) {
        getModeCmdHelper().playAdvancedMode(context, playList, playTimes, listPlay);
    }

    @Override // com.ltech.smarthome.ui.mode.IEditAdvancedMode
    public void playAllList(Context context) {
        playMode(context, this.playList, this.listPlayTime.getValue().intValue(), true);
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