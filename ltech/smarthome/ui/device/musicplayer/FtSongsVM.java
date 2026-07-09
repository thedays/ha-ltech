package com.ltech.smarthome.ui.device.musicplayer;

import android.app.Activity;
import android.view.View;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.BleMusicPlayerState;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.PlayListInfo;
import com.ltech.smarthome.model.bean.SongInfo;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.BleMusicPlayerAssistant;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.StringUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class FtSongsVM extends BaseViewModel {
    public long controlId;
    public long deviceId;
    private boolean isFirst;
    public int nextplayMode;
    public int selectPosition;
    private BleMusicPlayerState state;
    public MutableLiveData<Device> controlDevice = new MutableLiveData<>();
    public MutableLiveData<SongInfo> songData = new MutableLiveData<>();
    public SingleLiveEvent<Void> cleanSongsItem = new SingleLiveEvent<>();
    public MutableLiveData<Integer> soundValue = new MutableLiveData<>();
    public MutableLiveData<SongInfo> curPlaySound = new MutableLiveData<>();
    public MutableLiveData<Integer> adapterNotifyItemChanged = new MutableLiveData<>();
    public MutableLiveData<Integer> playActionRes = new MutableLiveData<>();
    public MutableLiveData<Integer> playModeRes = new MutableLiveData<>();
    public MediatorLiveData<List<SongInfo>> localSongs = new MediatorLiveData<>();
    public MutableLiveData<Integer> totolCount = new MutableLiveData<>();
    public MutableLiveData<String> title = new MutableLiveData<>();
    private List<SongInfo> list = new ArrayList();
    public long playListId = -1;
    private int starNum = 1;
    private boolean canSync = false;
    private int reTryTime = 1;
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.musicplayer.FtSongsVM$$ExternalSyntheticLambda1
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            FtSongsVM.this.lambda$new$1((View) obj);
        }
    });

    public void loadDevice(LifecycleOwner owner) {
        this.playActionRes.setValue(Integer.valueOf(R.mipmap.icon_cgmusic_icon_play_stop));
        this.playModeRes.setValue(Integer.valueOf(R.mipmap.icon_cgmusic_icon_playmode_list));
        Device deviceById = Injection.repo().device().getDeviceById(this.controlId);
        if (deviceById != null) {
            this.deviceId = deviceById.getDeviceId();
            this.controlDevice.setValue(deviceById);
            this.localSongs.addSource(this.controlDevice, new Observer<Device>() { // from class: com.ltech.smarthome.ui.device.musicplayer.FtSongsVM.1
                @Override // androidx.lifecycle.Observer
                public void onChanged(Device device) {
                    if (FtSongsVM.this.playListId == -1) {
                        List<SongInfo> songs = Injection.repo().song().getSongs(device.getDeviceId());
                        FtSongsVM.this.list = songs;
                        FtSongsVM.this.totolCount.setValue(Integer.valueOf(songs.size()));
                        FtSongsVM.this.localSongs.setValue(songs);
                        return;
                    }
                    FtSongsVM.this.loadPlaylistSongs();
                }
            });
        }
    }

    public void regDeviceStateLisener() {
        MessageManager.getInstance().setMusicPlayerBleStatusCallBack(new MessageManager.MusicPlayerBleStatusCallBack() { // from class: com.ltech.smarthome.ui.device.musicplayer.FtSongsVM$$ExternalSyntheticLambda0
            @Override // com.smart.message.MessageManager.MusicPlayerBleStatusCallBack
            public final void onDataReceive(ResponseMsg responseMsg) {
                FtSongsVM.this.lambda$regDeviceStateLisener$0(responseMsg);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$regDeviceStateLisener$0(ResponseMsg responseMsg) {
        int i;
        if (responseMsg != null) {
            this.state = coverToPlayerState(responseMsg.getResData());
            SharedPreferenceUtil.edit().keepShared(Constants.MUSIC_PLAYER_VOLUME, this.state.getVolume());
            this.soundValue.setValue(Integer.valueOf(this.state.getVolume()));
            this.playActionRes.setValue(Integer.valueOf(this.state.getPlayAction() == 1 ? R.mipmap.icon_cgmusic_icon_play_playing : R.mipmap.icon_cgmusic_icon_play_stop));
            int i2 = 0;
            while (true) {
                if (i2 >= this.list.size()) {
                    break;
                }
                SongInfo songInfo = this.list.get(i2);
                if (songInfo.getNum() == this.state.getSongsId()) {
                    this.curPlaySound.setValue(songInfo);
                    this.selectPosition = i2;
                    this.adapterNotifyItemChanged.setValue(Integer.valueOf(i2));
                    break;
                }
                i2++;
            }
            this.nextplayMode = 1;
            int playMode = this.state.getPlayMode();
            if (playMode == 1) {
                this.nextplayMode = 2;
                i = R.mipmap.icon_cgmusic_icon_playmode_single;
            } else if (playMode == 2) {
                this.nextplayMode = 3;
                i = R.mipmap.icon_cgmusic_icon_playmode_listcycle;
            } else if (playMode != 3) {
                i = R.mipmap.icon_cgmusic_icon_playmode_list;
                if (playMode == 4) {
                    this.nextplayMode = 1;
                }
            } else {
                this.nextplayMode = 4;
                i = R.mipmap.icon_cgmusic_icon_playmode_random;
            }
            this.playModeRes.setValue(Integer.valueOf(i));
        }
    }

    public void loadSongs() {
        if (!this.canSync) {
            dismissLoadingDialog();
            return;
        }
        if (!this.isFirst && this.totolCount.getValue() != null && this.starNum > this.totolCount.getValue().intValue()) {
            this.starNum = 1;
            addSongs2DB();
            dismissLoadingDialog();
        } else {
            showLoadingDialog(String.format("%s(%s/%s)", ActivityUtils.getTopActivity().getString(R.string.syncing_songs), Integer.valueOf(this.starNum), this.totolCount.getValue()));
            CmdAssistant.getBleMusicPlayerAssistant(this.controlDevice.getValue(), new int[0]).queryBleMusicSongsName(ActivityUtils.getTopActivity(), this.starNum, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.musicplayer.FtSongsVM.2
                @Override // com.ltech.smarthome.base.IAction
                public void act(ResponseMsg responseMsg) {
                    if (responseMsg != null && responseMsg.getResData().length() > 28) {
                        FtSongsVM.this.reTryTime = 1;
                        SongInfo converToSong = FtSongsVM.this.converToSong(responseMsg.getResData());
                        if (FtSongsVM.this.isFirst) {
                            FtSongsVM.this.cleanSongsItem.call();
                            FtSongsVM.this.curPlaySound.setValue(converToSong);
                            FtSongsVM.this.totolCount.setValue(Integer.valueOf(converToSong.getTotalCount()));
                        }
                        FtSongsVM.this.songData.setValue(converToSong);
                        FtSongsVM.this.list.add(converToSong);
                        FtSongsVM.this.starNum++;
                        FtSongsVM.this.loadSongs();
                        FtSongsVM.this.isFirst = false;
                        return;
                    }
                    if (FtSongsVM.this.reTryTime > 0) {
                        FtSongsVM.this.reTryTime = 0;
                    } else if (FtSongsVM.this.starNum == 1) {
                        FtSongsVM.this.dismissLoadingDialog();
                        return;
                    } else {
                        FtSongsVM.this.starNum++;
                    }
                    FtSongsVM.this.loadSongs();
                }
            });
        }
    }

    private void addSongs2DB() {
        if (this.list.size() > 0) {
            checkDeviceState();
            Injection.repo().song().addSongs(this.list);
        }
    }

    public void checkDeviceState() {
        CmdAssistant.getQueryCmdAssistant(this.controlDevice.getValue(), new int[0]).queryPanelDeviceMotorState(ActivityUtils.getTopActivity());
    }

    private BleMusicPlayerState coverToPlayerState(String resData) {
        BleMusicPlayerState bleMusicPlayerState = new BleMusicPlayerState();
        bleMusicPlayerState.setPlayScope(Integer.parseInt(resData.substring(12, 14), 16));
        bleMusicPlayerState.setPlayDir(Integer.parseInt(resData.substring(14, 16), 16));
        bleMusicPlayerState.setPlayMode(Integer.parseInt(resData.substring(16, 18), 16));
        bleMusicPlayerState.setPlayAction(Integer.parseInt(resData.substring(18, 20), 16));
        bleMusicPlayerState.setSongsId(Integer.parseInt(resData.substring(20, 24), 16));
        bleMusicPlayerState.setVolume(Integer.parseInt(resData.substring(24, 26), 16));
        bleMusicPlayerState.setBleState(Integer.parseInt(resData.substring(26, 28), 16));
        return bleMusicPlayerState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SongInfo converToSong(String resData) {
        SongInfo songInfo = new SongInfo();
        songInfo.setTotalCount(Integer.parseInt(resData.substring(16, 20), 16));
        songInfo.setId(Integer.parseInt(resData.substring(20, 24), 16));
        songInfo.setNum(Integer.parseInt(resData.substring(24, 28), 16));
        songInfo.setName(StringUtils.bytesStr2WordStr(resData.substring(28), "utf-16le"));
        if (this.controlDevice.getValue() != null) {
            songInfo.setDeviceId(this.controlDevice.getValue().getDeviceId());
        }
        return songInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<SongInfo> converToSongList(String resData) {
        this.totolCount.setValue(Integer.valueOf(Integer.parseInt(resData.substring(18, 20), 16)));
        String substring = resData.substring(20);
        ArrayList arrayList = new ArrayList();
        if (substring.length() % 4 != 0) {
            return new ArrayList();
        }
        int i = 0;
        while (i < substring.length() / 4) {
            int i2 = i * 4;
            i++;
            arrayList.add(Integer.valueOf(Integer.parseInt(substring.substring(i2, i * 4), 16)));
        }
        return converToSongListByIds(arrayList);
    }

    private List<SongInfo> converToSongListByIds(List<Integer> ids) {
        if (ids == null || ids.size() == 0) {
            return new ArrayList();
        }
        return Injection.repo().song().getSongsByIds(ids);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(View view) {
        int id = view.getId();
        if (id == R.id.tv_play_all) {
            if (this.list.size() > 0) {
                playById(this.list.get(0));
                return;
            }
            return;
        }
        if (id == R.id.tv_sync) {
            this.starNum = 1;
            setFirst(true);
            loadSongs();
            return;
        }
        if (id == R.id.iv_play) {
            play();
            return;
        }
        if (id == R.id.iv_mute) {
            mute();
            return;
        }
        if (id == R.id.iv_last) {
            last();
        } else if (id == R.id.iv_next) {
            next();
        } else if (id == R.id.iv_mode) {
            changemode();
        }
    }

    private void changemode() {
        CmdAssistant.getBleMusicPlayerAssistant(this.controlDevice.getValue(), new int[0]).setPlayMode(ActivityUtils.getTopActivity(), this.nextplayMode);
    }

    private void next() {
        if (this.curPlaySound.getValue() == null) {
            return;
        }
        CmdAssistant.getBleMusicPlayerAssistant(this.controlDevice.getValue(), new int[0]).next(ActivityUtils.getTopActivity());
    }

    private void last() {
        if (this.curPlaySound.getValue() == null) {
            return;
        }
        CmdAssistant.getBleMusicPlayerAssistant(this.controlDevice.getValue(), new int[0]).last(ActivityUtils.getTopActivity());
    }

    private void mute() {
        setVolume(0);
    }

    public void setVolume(int num) {
        CmdAssistant.getBleMusicPlayerAssistant(this.controlDevice.getValue(), new int[0]).setVolume(ActivityUtils.getTopActivity(), num);
    }

    public void playById(SongInfo songInfo) {
        this.curPlaySound.setValue(songInfo);
        int intValue = this.soundValue.getValue() == null ? 6 : this.soundValue.getValue().intValue();
        if (this.playListId != -1) {
            BleMusicPlayerAssistant bleMusicPlayerAssistant = CmdAssistant.getBleMusicPlayerAssistant(this.controlDevice.getValue(), new int[0]);
            Activity topActivity = ActivityUtils.getTopActivity();
            int i = (int) this.playListId;
            BleMusicPlayerState bleMusicPlayerState = this.state;
            bleMusicPlayerAssistant.control(topActivity, 2, i, bleMusicPlayerState != null ? bleMusicPlayerState.getPlayMode() : 4, songInfo.getNum(), intValue);
            return;
        }
        BleMusicPlayerAssistant bleMusicPlayerAssistant2 = CmdAssistant.getBleMusicPlayerAssistant(this.controlDevice.getValue(), new int[0]);
        Activity topActivity2 = ActivityUtils.getTopActivity();
        BleMusicPlayerState bleMusicPlayerState2 = this.state;
        bleMusicPlayerAssistant2.control(topActivity2, 1, 0, bleMusicPlayerState2 != null ? bleMusicPlayerState2.getPlayMode() : 4, songInfo.getNum(), intValue);
    }

    public void play() {
        BleMusicPlayerState bleMusicPlayerState = this.state;
        if (bleMusicPlayerState != null && bleMusicPlayerState.getPlayAction() == 1) {
            CmdAssistant.getBleMusicPlayerAssistant(this.controlDevice.getValue(), new int[0]).stop(ActivityUtils.getTopActivity());
        } else {
            CmdAssistant.getBleMusicPlayerAssistant(this.controlDevice.getValue(), new int[0]).play(ActivityUtils.getTopActivity());
        }
    }

    public void setCanSync(boolean canSync) {
        this.canSync = canSync;
    }

    public void setFirst(boolean first) {
        this.isFirst = first;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadPlaylistSongs() {
        List<Integer> list;
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.syncing_songs));
        final PlayListInfo playlistById = Injection.repo().song().getPlaylistById(this.deviceId, this.playListId);
        if (playlistById != null && (list = playlistById.getList()) != null && list.size() > 0) {
            List<SongInfo> converToSongListByIds = converToSongListByIds(list);
            this.list = converToSongListByIds;
            this.localSongs.setValue(converToSongListByIds);
            if (converToSongListByIds != null && converToSongListByIds.size() > 0) {
                this.totolCount.setValue(Integer.valueOf(converToSongListByIds.size()));
                dismissLoadingDialog();
            }
        }
        CmdAssistant.getBleMusicPlayerAssistant(this.controlDevice.getValue(), new int[0]).queryPlaylistSongs(ActivityUtils.getTopActivity(), (int) this.playListId, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.musicplayer.FtSongsVM.3
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                if (responseMsg != null) {
                    List<SongInfo> converToSongList = FtSongsVM.this.converToSongList(responseMsg.getResData());
                    if (playlistById != null) {
                        ArrayList arrayList = new ArrayList();
                        Iterator<SongInfo> it = converToSongList.iterator();
                        while (it.hasNext()) {
                            arrayList.add(Integer.valueOf(it.next().getNum()));
                        }
                        playlistById.setList(arrayList);
                        Injection.repo().song().updataPlaylist(playlistById);
                    }
                    FtSongsVM.this.list = converToSongList;
                    FtSongsVM.this.localSongs.setValue(converToSongList);
                }
                FtSongsVM.this.dismissLoadingDialog();
            }
        });
    }

    public void rename(final String name) {
        showLoadingDialog();
        CmdAssistant.getBleMusicPlayerAssistant(this.controlDevice.getValue(), new int[0]).editPlaylistName(ActivityUtils.getTopActivity(), (int) this.playListId, name, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.musicplayer.FtSongsVM.4
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                PlayListInfo playlistById;
                FtSongsVM.this.dismissLoadingDialog();
                if (responseMsg == null || (playlistById = Injection.repo().song().getPlaylistById(FtSongsVM.this.deviceId, FtSongsVM.this.playListId)) == null) {
                    return;
                }
                playlistById.setName(name);
                Injection.repo().song().updataPlaylist(playlistById);
                FtSongsVM.this.title.setValue(name);
                SmartToast.showShort(ActivityUtils.getTopActivity().getResources().getString(R.string.change_success));
            }
        });
    }

    public List<SongInfo> getData() {
        return this.list;
    }

    public void addSongs2Playlist(List<SongInfo> songInfos) {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.create_playlist));
        final ArrayList arrayList = new ArrayList();
        Iterator<SongInfo> it = songInfos.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(it.next().getNum()));
        }
        CmdAssistant.getBleMusicPlayerAssistant(this.controlDevice.getValue(), new int[0]).addSongs2Playlist(ActivityUtils.getTopActivity(), (int) this.playListId, arrayList, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.musicplayer.FtSongsVM.5
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                PlayListInfo playlistById;
                if (responseMsg != null && (playlistById = Injection.repo().song().getPlaylistById(FtSongsVM.this.deviceId, FtSongsVM.this.playListId)) != null) {
                    playlistById.setList(arrayList);
                    playlistById.setSongCount(arrayList.size());
                    Injection.repo().song().updataPlaylist(playlistById);
                    SmartToast.showShort(ActivityUtils.getTopActivity().getResources().getString(R.string.change_success));
                }
                FtSongsVM.this.dismissLoadingDialog();
            }
        });
    }
}