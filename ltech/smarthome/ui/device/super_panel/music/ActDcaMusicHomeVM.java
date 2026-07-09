package com.ltech.smarthome.ui.device.super_panel.music;

import android.view.View;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.ui.device.super_panel.music.base.ActDcaMusicBaseVM;
import com.ltech.smarthome.ui.device.super_panel.music.manager.CategoryInterface;
import com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager;
import com.ltech.smarthome.ui.device.super_panel.music.manager.SingleSongInterface;
import com.ltech.smarthome.ui.device.super_panel.music.manager.SongInterface;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.rich.czlylibary.bean.MusicInfo;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.StringUtils;
import java.util.List;

/* loaded from: classes4.dex */
public class ActDcaMusicHomeVM extends ActDcaMusicBaseVM {
    public boolean isOnline;
    public SingleLiveEvent<Void> showMusicListClickEvent = new SingleLiveEvent<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHomeVM$$ExternalSyntheticLambda1
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActDcaMusicHomeVM.this.lambda$new$0((View) obj);
        }
    });

    public int getListIdByAreaId(int areaId) {
        if (areaId == 1) {
            return 0;
        }
        switch (areaId) {
            case 4:
                return 0;
            case 5:
                return 2;
            case 6:
                return 3;
            case 7:
                return 4;
            case 8:
                return 5;
            case 9:
                return 6;
            case 10:
                return 7;
            case 11:
                return 9;
            case 12:
                return 10;
            case 13:
                return 11;
            case 14:
                return 12;
            case 15:
                return 13;
            case 16:
                return 14;
            case 17:
                return 15;
            case 18:
                return 16;
            default:
                return 1;
        }
    }

    public void initSong() {
        MiguManager.getInstance().getCategory(new CategoryInterface(this) { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHomeVM.1
            @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.CategoryInterface
            public void onFail(int error) {
            }

            @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.CategoryInterface
            public void onSuccess(List<String> songCategoryInfoList) {
            }
        });
        MiguManager.getInstance().getNewSongs(new SongInterface(this) { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHomeVM.2
            @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SongInterface
            public void onFail() {
            }

            @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SongInterface
            public void onSuccess() {
                MiguManager.getInstance().getRankSongs();
            }
        });
    }

    public void playOrStopMusic() {
        controlAction(this.isPlaying.getValue().booleanValue() ? 2 : 1, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.iv_play /* 2131297175 */:
                if (this.isOnline) {
                    playOrStopMusic();
                    break;
                } else {
                    SmartToast.showCenterShort(ActivityUtils.getTopActivity().getString(R.string.device_offline));
                    break;
                }
            case R.id.iv_playlist /* 2131297181 */:
                if (this.isOnline) {
                    this.showMusicListClickEvent.call();
                    break;
                } else {
                    SmartToast.showCenterShort(ActivityUtils.getTopActivity().getString(R.string.device_offline));
                    break;
                }
            case R.id.layout_bottom /* 2131297374 */:
                if (this.isOnline) {
                    NavUtils.destination(ActDcaMusicDetail.class).withLong(Constants.CONTROL_ID, this.controlId).withBoolean(Constants.MUSIC_NEED_ORDER_MUSIC, false).withString(Constants.MUSIC_SONG_ID, this.songId).withBoolean(Constants.MUSIC_SONG_IS_LOCAL, this.areaId == 1).withString(Constants.MAC_ADDRESS, this.deviceMac).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
                    ActivityUtils.getTopActivity().overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
                    break;
                } else {
                    SmartToast.showCenterShort(ActivityUtils.getTopActivity().getString(R.string.device_offline));
                    break;
                }
            case R.id.layout_search /* 2131297618 */:
                if (this.isOnline) {
                    navigation(NavUtils.destination(ActDcaMusicSearch.class).withLong(Constants.CONTROL_ID, this.controlId).withString(Constants.MAC_ADDRESS, this.deviceMac));
                    break;
                } else {
                    SmartToast.showCenterShort(ActivityUtils.getTopActivity().getString(R.string.device_offline));
                    break;
                }
        }
    }

    private void refreshSong() {
        MiguManager.getInstance().findMusicInfoById(this.songId, new SingleSongInterface() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHomeVM.3
            @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SingleSongInterface
            public void onFail() {
            }

            @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SingleSongInterface
            public void onSuccess(MusicInfo musicInfo) {
                ActDcaMusicHomeVM.this.musicData.setValue(musicInfo);
            }
        });
    }

    public void getSongFromBle() {
        CmdAssistant.getMusicAssistant(this.controlDevice, new int[0]).getCurState(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHomeVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDcaMusicHomeVM.this.lambda$getSongFromBle$1((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSongFromBle$1(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getStateCode() != 0) {
            return;
        }
        refreshPanelData(responseMsg);
    }

    public void refreshPanelData(ResponseMsg msg) {
        String resData = msg.getResData();
        if (resData.length() > 61) {
            this.isOnline = true;
            int parseInt = Integer.parseInt(resData.substring(16, 18), 16);
            int parseInt2 = Integer.parseInt(resData.substring(56, 60), 16);
            this.isLocal = parseInt == 1;
            String hexToString = StringUtils.hexToString(resData.substring(18, 40));
            if (!hexToString.equalsIgnoreCase(this.songId)) {
                this.songId = hexToString;
                if (isLocal(this.songId)) {
                    refreshLocalSong(parseInt2);
                } else {
                    refreshSong();
                }
            }
            if (this.areaId != parseInt) {
                this.refreshIcon.setValue(Integer.valueOf(parseInt));
            }
            this.areaId = parseInt;
            int parseInt3 = Integer.parseInt(resData.substring(40, 42), 16);
            if (this.isPlaying.getValue().booleanValue() != (parseInt3 == 1)) {
                this.isPlaying.setValue(Boolean.valueOf(parseInt3 == 1));
            }
            this.curMode = Integer.parseInt(resData.substring(46, 48), 16);
        }
    }
}