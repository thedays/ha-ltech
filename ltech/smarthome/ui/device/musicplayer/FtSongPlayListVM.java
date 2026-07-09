package com.ltech.smarthome.ui.device.musicplayer;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.PlayListInfo;
import com.ltech.smarthome.model.bean.SongInfo;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.StringUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class FtSongPlayListVM extends BaseViewModel {
    public long controlId;
    private long deviceId;
    private boolean isFirst;
    private List<PlayListInfo> list;
    public int selectPosition;
    private List<SongInfo> selectSongs;
    public MutableLiveData<Device> controlDevice = new MutableLiveData<>();
    public MediatorLiveData<List<PlayListInfo>> localPlayList = new MediatorLiveData<>();
    private int starNum = 1;
    private int totalCount = 1;
    private boolean isLoading = false;
    private int reTryTime = 1;
    private boolean needSync = true;

    public void loadDevice() {
        Device deviceById = Injection.repo().device().getDeviceById(this.controlId);
        this.deviceId = deviceById.getDeviceId();
        this.controlDevice.setValue(deviceById);
    }

    public void loadPlayList(Device device) {
        if (this.isLoading) {
            return;
        }
        this.localPlayList.setValue(Injection.repo().song().getPlaylist(device.getDeviceId()));
        if (!Injection.repo().song().hasSongs(device.getDeviceId())) {
            this.isLoading = false;
            return;
        }
        this.starNum = 1;
        this.list = new ArrayList();
        this.isFirst = true;
        if (this.needSync) {
            loadPlayerListFromDevice();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadPlayerListFromDevice() {
        if (!this.isFirst && this.starNum > this.totalCount) {
            this.isLoading = false;
            this.localPlayList.setValue(this.list);
            addPlaylist2DB();
            this.needSync = false;
            dismissLoadingDialog();
            return;
        }
        this.isLoading = true;
        showLoadingDialog(String.format("%s(%s/%s)", ActivityUtils.getTopActivity().getString(R.string.syncing_playlists), Integer.valueOf(this.starNum), Integer.valueOf(this.totalCount)));
        CmdAssistant.getBleMusicPlayerAssistant(this.controlDevice.getValue(), new int[0]).queryBleMusicPlayerPlayList(ActivityUtils.getTopActivity(), this.starNum, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.musicplayer.FtSongPlayListVM.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                if (responseMsg != null) {
                    FtSongPlayListVM.this.reTryTime = 1;
                    PlayListInfo converToPlayList = FtSongPlayListVM.this.converToPlayList(responseMsg.getResData());
                    if (FtSongPlayListVM.this.isFirst) {
                        FtSongPlayListVM.this.totalCount = converToPlayList.getTotalCount();
                    }
                    FtSongPlayListVM.this.list.add(converToPlayList);
                    FtSongPlayListVM.this.starNum++;
                    FtSongPlayListVM.this.isFirst = false;
                    FtSongPlayListVM.this.isLoading = false;
                    FtSongPlayListVM.this.loadPlayerListFromDevice();
                    return;
                }
                if (FtSongPlayListVM.this.reTryTime > 0) {
                    FtSongPlayListVM.this.reTryTime = 0;
                } else if (FtSongPlayListVM.this.starNum == 1) {
                    FtSongPlayListVM.this.isLoading = false;
                    FtSongPlayListVM.this.dismissLoadingDialog();
                    return;
                } else {
                    FtSongPlayListVM.this.starNum++;
                }
                FtSongPlayListVM.this.isLoading = false;
                FtSongPlayListVM.this.loadPlayerListFromDevice();
            }
        });
    }

    private void addPlaylist2DB() {
        if (this.list.size() > 0) {
            Injection.repo().song().addPlaylist(this.list);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PlayListInfo converToPlayList(String resData) {
        PlayListInfo playListInfo = new PlayListInfo();
        playListInfo.setTotalCount(Integer.parseInt(resData.substring(16, 18), 16));
        playListInfo.setId(Integer.parseInt(resData.substring(18, 20), 16));
        playListInfo.setNum(Integer.parseInt(resData.substring(20, 22), 16));
        playListInfo.setSongCount(Integer.parseInt(resData.substring(22, 24), 16));
        playListInfo.setName(StringUtils.bytesStr2WordStr(resData.substring(24), "UTF-8"));
        if (this.controlDevice.getValue() != null) {
            playListInfo.setDeviceId(this.controlDevice.getValue().getDeviceId());
        }
        return playListInfo;
    }

    public void navSoundList(PlayListInfo info) {
        if (!Injection.repo().song().hasSongs(this.deviceId)) {
            SmartToast.showShort(ActivityUtils.getTopActivity().getResources().getString(R.string.you_do_not_have_any_songs_yet));
        } else {
            navSongsView(info);
        }
    }

    public void creatPlaylist(final String s) {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.create_playlist));
        CmdAssistant.getBleMusicPlayerAssistant(this.controlDevice.getValue(), new int[0]).createplayList(ActivityUtils.getTopActivity(), s, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.musicplayer.FtSongPlayListVM.2
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                if (responseMsg != null) {
                    PlayListInfo playListInfo = new PlayListInfo();
                    if (FtSongPlayListVM.this.list == null) {
                        FtSongPlayListVM.this.list = new ArrayList();
                    }
                    playListInfo.setTotalCount(FtSongPlayListVM.this.list.size() + 1);
                    playListInfo.setNum(Integer.parseInt(responseMsg.getResData().substring(16, 18), 16));
                    playListInfo.setSongCount(0);
                    playListInfo.setName(s);
                    if (FtSongPlayListVM.this.controlDevice.getValue() != null) {
                        playListInfo.setDeviceId(FtSongPlayListVM.this.controlDevice.getValue().getDeviceId());
                    }
                    FtSongPlayListVM.this.list.add(playListInfo);
                    Injection.repo().song().addPlaylist(playListInfo);
                    FtSongPlayListVM.this.navSelectView(Integer.parseInt(responseMsg.getResData().substring(16, 18), 16));
                }
                FtSongPlayListVM.this.dismissLoadingDialog();
            }
        });
    }

    public boolean canCreate() {
        List<PlayListInfo> list = this.list;
        if (list == null) {
            return true;
        }
        if (list.size() >= 8) {
            SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.you_have_reached_the_maximum_number_of_lists));
            return false;
        }
        if (Injection.repo().song().hasSongs(this.deviceId)) {
            return true;
        }
        SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.you_do_not_have_any_playlists_yet));
        return false;
    }

    public void navSelectView(int id) {
        navigation(NavUtils.destination(ActSelectSongs.class).withInt(Constants.PLAY_LIST_ID, id).withLong("device_id", this.controlDevice.getValue().getDeviceId()).withDefaultRequestCode());
    }

    public void navSongsView(PlayListInfo info) {
        navigation(NavUtils.destination(ActSongs.class).withLong(Constants.PLAY_LIST_ID, info.getNum()).withString(Constants.PLAY_LIST_TITLE, info.getName()).withLong(Constants.CONTROL_ID, this.controlId).withDefaultRequestCode());
    }

    public void setSelectSongs(List<SongInfo> selectSongs) {
        this.selectSongs = selectSongs;
    }

    public List<SongInfo> getSelectSongs() {
        return this.selectSongs;
    }

    public void addSongs2Playlist(int id) {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.create_playlist));
        ArrayList arrayList = new ArrayList();
        Iterator<SongInfo> it = getSelectSongs().iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(it.next().getNum()));
        }
        CmdAssistant.getBleMusicPlayerAssistant(this.controlDevice.getValue(), new int[0]).addSongs2Playlist(ActivityUtils.getTopActivity(), id, arrayList, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.musicplayer.FtSongPlayListVM.3
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                FtSongPlayListVM.this.dismissLoadingDialog();
            }
        });
    }
}