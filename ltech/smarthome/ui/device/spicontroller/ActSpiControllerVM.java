package com.ltech.smarthome.ui.device.spicontroller;

import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.extra.TabContentdefault;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.ltech.smarthome.utils.cmd_assistant.ModeAssistant;
import com.smart.message.ResponseMsg;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSpiControllerVM extends BaseViewModel {
    public long controlId;
    public boolean groupControl;
    public boolean isPlay;
    public boolean isPlayList;
    public boolean selectAction;
    public Fragment selectFragment;
    public List<TabContentdefault> tabContentList;
    public int selectModeNum = 0;
    public int selectPlayList = 0;
    public MutableLiveData<Device> controlObject = new MutableLiveData<>();
    public SingleLiveEvent<Void> refreshConsole = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> refreshModeList = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> stateOn = new MutableLiveData<>();
    public boolean forward = true;
    public int speed = 1;
    public int rgbBrt = 255;
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiControllerVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActSpiControllerVM.this.lambda$new$0((View) obj);
        }
    });
    private final IAction<ResponseMsg> iAction = new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiControllerVM.1
        @Override // com.ltech.smarthome.base.IAction
        public void act(ResponseMsg responseMsg) {
            if (responseMsg == null || responseMsg.getStateCode() != 0) {
                return;
            }
            ActSpiControllerVM.this.showSuccessTipDialog(ActivityUtils.getTopActivity().getString(R.string.send_to_device_success));
        }
    };
    private final IAction<ResponseMsg> iModeQuery = new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiControllerVM.2
        @Override // com.ltech.smarthome.base.IAction
        public void act(ResponseMsg responseMsg) {
            ActSpiControllerVM.this.dismissLoadingDialog();
            if (responseMsg == null || responseMsg.getStateCode() != 0) {
                return;
            }
            ActSpiControllerVM.this.isPlay = Integer.parseInt(responseMsg.getResData().substring(20, 22), 16) == 1;
            ActSpiControllerVM.this.forward = Integer.parseInt(responseMsg.getResData().substring(22, 24), 16) == 1;
            ActSpiControllerVM.this.rgbBrt = Integer.parseInt(responseMsg.getResData().substring(24, 26), 16);
            ActSpiControllerVM.this.speed = Integer.parseInt(responseMsg.getResData().substring(26, 28), 16);
            ActSpiControllerVM.this.refreshConsole.call();
        }
    };

    public LightAssistant getLightAssistant() {
        return CmdAssistant.getLightCmdAssistant(this.controlObject.getValue(), new int[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (view.getId() != R.id.iv_open) {
            return;
        }
        if (Boolean.TRUE.equals(this.stateOn.getValue())) {
            getLightAssistant().sendOnOff(ActivityUtils.getTopActivity(), false);
            this.stateOn.setValue(false);
        } else {
            getLightAssistant().sendOnOff(ActivityUtils.getTopActivity(), true);
            this.stateOn.setValue(true);
        }
    }

    public void playMode() {
        if (!this.isPlayList) {
            getModeCmdHelper().playSpiMode(ActivityUtils.getTopActivity(), this.selectModeNum, 0, this.forward ? 1 : 2, this.speed, this.rgbBrt, false, this.iAction);
        } else {
            getModeCmdHelper().playSpiMode(ActivityUtils.getTopActivity(), this.selectPlayList, 0, this.forward ? 1 : 2, this.speed, this.rgbBrt, true, this.iAction);
        }
    }

    public void pauseMode() {
        getModeCmdHelper().pauseSpiMode(ActivityUtils.getTopActivity(), this.iAction);
    }

    public void queryModeInfo() {
        if (this.selectModeNum > 0) {
            getModeCmdHelper().querySpiModeState(ActivityUtils.getTopActivity(), this.selectModeNum, this.iModeQuery);
        }
    }

    public void queryPlayListInfo() {
        if (this.selectPlayList > 0) {
            getModeCmdHelper().querySpiPlayListState(ActivityUtils.getTopActivity(), this.selectPlayList, this.iModeQuery);
        }
    }

    public void initTabList() {
        List<TabContentdefault> list = this.tabContentList;
        if (list == null) {
            this.tabContentList = new ArrayList();
        } else {
            list.clear();
        }
        this.tabContentList.add(new TabContentdefault(R.string.spi_mode, new FtSpiMode()));
        this.tabContentList.add(new TabContentdefault(R.string.play_list, FtSpiPlayList.newInstance(this.controlId)));
        this.tabContentList.add(new TabContentdefault(R.string.spi_color, new FtSpiColor()));
    }

    public void initTabListForAction() {
        List<TabContentdefault> list = this.tabContentList;
        if (list == null) {
            this.tabContentList = new ArrayList();
        } else {
            list.clear();
        }
        this.tabContentList.add(new TabContentdefault(R.string.spi_mode, new FtSpiMode()));
        this.tabContentList.add(new TabContentdefault(R.string.play_list, FtSpiPlayList.newInstance(this.controlId)));
    }

    public LightAssistant getLightCmdHelper() {
        return CmdAssistant.getLightCmdAssistant(this.controlObject.getValue(), new int[0]);
    }

    public ModeAssistant getModeCmdHelper() {
        return CmdAssistant.getModeCmdAssistant(this.controlObject.getValue(), new int[0]);
    }
}