package com.ltech.smarthome.ui.device.super_panel.music.base;

import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.SongListItemInfo;
import com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager;
import com.ltech.smarthome.utils.DateUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.rich.czlylibary.bean.MusicInfo;
import com.smart.message.ResponseMsg;
import java.util.List;

/* loaded from: classes4.dex */
public class ActDcaMusicBaseVM extends BaseViewModel {
    public int areaId;
    public Device controlDevice;
    public long controlId;
    public int curMode;
    public String deviceMac;
    public boolean isLocal;
    public String songId;
    public boolean isInit = false;
    public MutableLiveData<Boolean> isPlaying = new MutableLiveData<>(false);
    public MutableLiveData<Integer> refreshIcon = new MutableLiveData<>();
    public MutableLiveData<MusicInfo> musicData = new MutableLiveData<>();

    static /* synthetic */ void lambda$controlAction$1(ResponseMsg responseMsg) {
    }

    static /* synthetic */ void lambda$playMusicType$0(ResponseMsg responseMsg) {
    }

    public int getIconResource(int area) {
        switch (area) {
            case 2:
                return R.mipmap.ic_music_collect;
            case 3:
                return R.mipmap.ic_music_recommend;
            case 4:
                return R.mipmap.ic_music_morning;
            case 5:
                return R.mipmap.ic_music_sleep;
            case 6:
                return R.mipmap.ic_music_sport;
            case 7:
                return R.mipmap.ic_music_meditation;
            case 8:
                return R.mipmap.ic_music_relax;
            case 9:
                return R.mipmap.ic_music_focus;
            case 10:
                return R.mipmap.ic_music_classic;
            case 11:
                return R.mipmap.ic_music_new;
            case 12:
                return R.mipmap.ic_music_show;
            case 13:
                return R.mipmap.ic_music_blue;
            case 14:
                return R.mipmap.ic_music_inspirational;
            case 15:
                return R.mipmap.ic_music_yoga;
            case 16:
                return R.mipmap.ic_music_slow;
            case 17:
                return R.mipmap.ic_music_cofe;
            case 18:
                return R.mipmap.ic_music_date;
            default:
                return R.mipmap.ic_music_local;
        }
    }

    public void playMusicType(String type) {
        CmdAssistant.getMusicAssistant(this.controlDevice, new int[0]).playType(ActivityUtils.getTopActivity(), getAreaId(type), new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.music.base.ActDcaMusicBaseVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDcaMusicBaseVM.lambda$playMusicType$0((ResponseMsg) obj);
            }
        });
    }

    public void playMusicType(String type, IAction<ResponseMsg> action) {
        CmdAssistant.getMusicAssistant(this.controlDevice, new int[0]).playType(ActivityUtils.getTopActivity(), getAreaId(type), action);
    }

    public void refreshLocalSong(final int songTime) {
        if (MiguManager.getInstance().localSongs == null) {
            MiguManager.getInstance().getLocalMusicList(this.deviceMac, new IAction<List<SongListItemInfo>>() { // from class: com.ltech.smarthome.ui.device.super_panel.music.base.ActDcaMusicBaseVM.1
                @Override // com.ltech.smarthome.base.IAction
                public void act(List<SongListItemInfo> localSongList) {
                    for (SongListItemInfo songListItemInfo : localSongList) {
                        if (songListItemInfo.getSongId().equals(ActDcaMusicBaseVM.this.songId)) {
                            MusicInfo musicInfo = new MusicInfo();
                            musicInfo.setLength(DateUtils.getStrTime3(Integer.valueOf(songTime)));
                            musicInfo.setMusicId(songListItemInfo.getSongId());
                            musicInfo.setSingerName(songListItemInfo.getSinger());
                            musicInfo.setMusicName(songListItemInfo.getName());
                            ActDcaMusicBaseVM.this.musicData.setValue(musicInfo);
                            return;
                        }
                    }
                }
            });
            return;
        }
        for (SongListItemInfo songListItemInfo : MiguManager.getInstance().localSongs) {
            if (songListItemInfo.getSongId().equals(this.songId)) {
                MusicInfo musicInfo = new MusicInfo();
                musicInfo.setLength(DateUtils.getStrTime3(Integer.valueOf(songTime)));
                musicInfo.setMusicId(songListItemInfo.getSongId());
                musicInfo.setSingerName(songListItemInfo.getSinger());
                musicInfo.setMusicName(songListItemInfo.getName());
                this.musicData.setValue(musicInfo);
                return;
            }
        }
    }

    protected int getAreaId(String type) {
        if (ActivityUtils.getTopActivity().getString(R.string.music_local_short).equals(type)) {
            return 1;
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_collect).equals(type)) {
            return 2;
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_morning).equals(type)) {
            return 4;
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_recommend_short).equals(type)) {
            return 3;
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_sleep).equals(type)) {
            return 5;
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_sport).equals(type)) {
            return 6;
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_meditation).equals(type)) {
            return 7;
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_relax).equals(type)) {
            return 8;
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_focus).equals(type)) {
            return 9;
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_classic).equals(type)) {
            return 10;
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_new).equals(type)) {
            return 11;
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_show).equals(type)) {
            return 12;
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_blue).equals(type)) {
            return 13;
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_inspirational).equals(type)) {
            return 14;
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_yoga).equals(type)) {
            return 15;
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_slow).equals(type)) {
            return 16;
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_cofe).equals(type)) {
            return 17;
        }
        return ActivityUtils.getTopActivity().getString(R.string.music_date).equals(type) ? 18 : 1;
    }

    protected void controlAction(int order, int data, IAction<ResponseMsg> result) {
        CmdAssistant.getMusicAssistant(this.controlDevice, new int[0]).controlAction(ActivityUtils.getTopActivity(), order, data, result);
    }

    protected void controlAction(int order, int data, String songId, IAction<ResponseMsg> result) {
        CmdAssistant.getMusicAssistant(this.controlDevice, new int[0]).controlAction(ActivityUtils.getTopActivity(), order, data, songId, result);
    }

    protected void controlAction(int order, int data) {
        CmdAssistant.getMusicAssistant(this.controlDevice, new int[0]).controlAction(ActivityUtils.getTopActivity(), order, data, new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.music.base.ActDcaMusicBaseVM$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDcaMusicBaseVM.lambda$controlAction$1((ResponseMsg) obj);
            }
        });
    }

    public boolean isLocal(String songId) {
        return songId.contains("1000");
    }
}