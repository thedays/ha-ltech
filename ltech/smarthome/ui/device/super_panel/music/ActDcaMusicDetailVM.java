package com.ltech.smarthome.ui.device.super_panel.music;

import android.view.View;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.ui.device.super_panel.music.base.ActDcaMusicBaseVM;
import com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager;
import com.ltech.smarthome.ui.device.super_panel.music.manager.SingleSongInterface;
import com.ltech.smarthome.ui.device.super_panel.music.manager.SongInterface;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.rich.czlylibary.bean.MusicInfo;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.StringUtils;

/* loaded from: classes4.dex */
public class ActDcaMusicDetailVM extends ActDcaMusicBaseVM {
    public int CurTime;
    public int curScheduledSetTime;
    public int curScheduledTimeLeft;
    public int curVolume;
    public boolean isCollect;
    public int songLength;
    protected MutableLiveData<Boolean> updateCollectData = new MutableLiveData<>();
    public MutableLiveData<Integer> playModeEvent = new MutableLiveData<>(0);
    public SingleLiveEvent<Void> showVolumeClickEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showTimeClickEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showMusicListClickEvent = new SingleLiveEvent<>();
    public MutableLiveData<Integer> refreshScheduledTimeEvent = new MutableLiveData<>(0);
    public MutableLiveData<MusicInfo> showMusicData = new MutableLiveData<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetailVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActDcaMusicDetailVM.this.lambda$new$1((View) obj);
        }
    });

    static /* synthetic */ void lambda$initSongData$0(ResponseMsg responseMsg) {
    }

    public void initSongData() {
        this.isInit = true;
        String str = this.songId;
        MiguManager.getInstance().findMusicInfoById(this.songId, new SingleSongInterface() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetailVM.1
            @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SingleSongInterface
            public void onFail() {
            }

            @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SingleSongInterface
            public void onSuccess(MusicInfo musicInfo) {
                ActDcaMusicDetailVM.this.showMusicData.setValue(musicInfo);
            }
        });
        this.songId = "";
        CmdAssistant.getMusicAssistant(this.controlDevice, new int[0]).orderMusic(ActivityUtils.getTopActivity(), str, 0, new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetailVM$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDcaMusicDetailVM.lambda$initSongData$0((ResponseMsg) obj);
            }
        });
    }

    public void refreshSong() {
        MiguManager.getInstance().findMusicInfoById(this.songId, new SingleSongInterface() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetailVM.2
            @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SingleSongInterface
            public void onFail() {
            }

            @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SingleSongInterface
            public void onSuccess(MusicInfo musicInfo) {
                ActDcaMusicDetailVM.this.musicData.setValue(musicInfo);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(View view) {
        switch (view.getId()) {
            case R.id.iv_mode /* 2131297137 */:
                setPlayMode();
                break;
            case R.id.iv_next /* 2131297154 */:
                playNextOrPrevious(4);
                break;
            case R.id.iv_play /* 2131297175 */:
                playOrStopMusic();
                break;
            case R.id.iv_playlist /* 2131297181 */:
                this.showMusicListClickEvent.call();
                break;
            case R.id.iv_previous /* 2131297190 */:
                playNextOrPrevious(3);
                break;
            case R.id.iv_time /* 2131297293 */:
                this.showTimeClickEvent.call();
                break;
            case R.id.iv_volume /* 2131297315 */:
                this.showVolumeClickEvent.call();
                break;
        }
    }

    private void setPlayMode() {
        this.curMode++;
        if (this.curMode > 3) {
            this.curMode = 0;
        }
        this.playModeEvent.setValue(Integer.valueOf(this.curMode));
        controlAction(5, this.curMode);
    }

    public void setVolume(int volume) {
        controlAction(6, volume);
    }

    private void playNextOrPrevious(int musicAction) {
        controlAction(musicAction, 0);
    }

    public void setCollect() {
        int i = !this.isCollect ? 1 : 0;
        MiguManager.getInstance().setCollect(this.songId, !this.isCollect, new SongInterface(this) { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetailVM.3
            @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SongInterface
            public void onFail() {
            }

            @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SongInterface
            public void onSuccess() {
            }
        });
        controlAction(8, i, this.songId, new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetailVM$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDcaMusicDetailVM.this.lambda$setCollect$2((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCollect$2(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getStateCode() != 0) {
            return;
        }
        boolean z = !this.isCollect;
        this.isCollect = z;
        this.updateCollectData.setValue(Boolean.valueOf(z));
    }

    public void setCloseTime(final int time) {
        controlAction(7, time, new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetailVM$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDcaMusicDetailVM.this.lambda$setCloseTime$3(time, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCloseTime$3(int i, ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getStateCode() != 0) {
            return;
        }
        this.curScheduledTimeLeft = i * 60;
        this.curScheduledSetTime = i;
        if (i == 0) {
            SmartToast.showCenterShort(ActivityUtils.getTopActivity().getString(R.string.music_timed_set_close));
        } else {
            SmartToast.showCenterShort(ActivityUtils.getTopActivity().getString(R.string.music_timed_set_success, new Object[]{String.valueOf(i)}));
        }
    }

    public void setSongTime(int time) {
        CmdAssistant.getMusicAssistant(this.controlDevice, new int[0]).orderMusic(ActivityUtils.getTopActivity(), this.songId, time, new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetailVM$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDcaMusicDetailVM.lambda$setSongTime$4((ResponseMsg) obj);
            }
        });
    }

    static /* synthetic */ void lambda$setSongTime$4(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            responseMsg.getStateCode();
        }
    }

    public void playOrStopMusic() {
        int i = this.isPlaying.getValue().booleanValue() ? 2 : 1;
        this.isPlaying.setValue(Boolean.valueOf(true ^ this.isPlaying.getValue().booleanValue()));
        controlAction(i, 0);
    }

    public void refreshPanelData(ResponseMsg msg, boolean isNeedRefreshSong) {
        String resData = msg.getResData();
        if (resData.length() > 61) {
            Integer.parseInt(resData.substring(16, 18), 16);
            String hexToString = StringUtils.hexToString(resData.substring(18, 40));
            int parseInt = Integer.parseInt(resData.substring(40, 42), 16);
            int parseInt2 = Integer.parseInt(resData.substring(42, 46), 16);
            int parseInt3 = Integer.parseInt(resData.substring(46, 48), 16);
            int parseInt4 = Integer.parseInt(resData.substring(48, 50), 16);
            int parseInt5 = Integer.parseInt(resData.substring(50, 52), 16);
            int parseInt6 = Integer.parseInt(resData.substring(52, 56), 16);
            int parseInt7 = Integer.parseInt(resData.substring(56, 60), 16);
            int parseInt8 = Integer.parseInt(resData.substring(60, 62), 16);
            this.CurTime = parseInt2;
            if (this.isPlaying.getValue().booleanValue() != (parseInt == 1) || this.isInit) {
                this.isInit = false;
                this.isPlaying.setValue(Boolean.valueOf(parseInt == 1));
            }
            this.isLocal = isLocal(hexToString);
            if (this.isLocal) {
                this.songId = hexToString;
                refreshLocalSong(parseInt7);
            } else if (!hexToString.equalsIgnoreCase(this.songId) || isNeedRefreshSong) {
                this.songId = hexToString;
                if (isLocal(this.songId)) {
                    refreshLocalSong(parseInt7);
                } else {
                    refreshSong();
                }
            }
            this.curMode = parseInt3;
            this.playModeEvent.setValue(Integer.valueOf(this.curMode));
            this.curScheduledSetTime = parseInt4;
            this.curScheduledTimeLeft = parseInt6;
            this.refreshScheduledTimeEvent.setValue(Integer.valueOf(parseInt6));
            boolean z = parseInt5 == 1;
            this.isCollect = z;
            this.updateCollectData.setValue(Boolean.valueOf(z));
            this.curVolume = parseInt8;
        }
    }

    public void getSongFromBle() {
        this.isInit = true;
        CmdAssistant.getMusicAssistant(this.controlDevice, new int[0]).getCurState(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetailVM$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDcaMusicDetailVM.this.lambda$getSongFromBle$5((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSongFromBle$5(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getStateCode() != 0) {
            return;
        }
        refreshPanelData(responseMsg, true);
    }
}