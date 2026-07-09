package com.ltech.smarthome.model.repo.ifun;

import com.ltech.smarthome.model.bean.PlayListInfo;
import com.ltech.smarthome.model.bean.SongInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface ISong {
    void addPlaylist(PlayListInfo info);

    void addPlaylist(List<PlayListInfo> list);

    void addSongs(List<SongInfo> list);

    void delPlaylistById(long deviceId, long id);

    List<PlayListInfo> getPlaylist(long deviceId);

    PlayListInfo getPlaylistById(long deviceId, long id);

    SongInfo getSongById(long id);

    List<SongInfo> getSongs(long deviceId);

    List<SongInfo> getSongsByIds(List<Integer> list);

    boolean hasSongPlaylist(long deviceId);

    boolean hasSongs(long deviceId);

    void updataPlaylist(PlayListInfo info);
}