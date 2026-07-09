package com.ltech.smarthome.model.repo;

import com.ltech.smarthome.model.bean.PlayListInfo;
import com.ltech.smarthome.model.bean.SongInfo;
import com.ltech.smarthome.model.repo.ifun.ISong;
import com.ltech.smarthome.model.repo.ifun.IUser;
import com.ltech.smarthome.singleton.KeyCreator;
import com.ltech.smarthome.singleton.RateLimiter;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class SongRepository extends BaseRepository implements ISong {
    public SongRepository(BoxStore boxStore, RateLimiter limiter, KeyCreator keyCreator, IUser user) {
        super(boxStore, limiter, keyCreator, user);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.ISong
    public void addSongs(final List<SongInfo> list) {
        this.mBoxStore.runInTxAsync(new Runnable() { // from class: com.ltech.smarthome.model.repo.SongRepository.1
            @Override // java.lang.Runnable
            public void run() {
                if (list.size() > 0) {
                    for (SongInfo songInfo : SongRepository.this.mBoxBuilderFactory.querySongList(((SongInfo) list.get(0)).getDeviceId()).build().find()) {
                        Iterator it = list.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                if (((SongInfo) it.next()).getId() == songInfo.getId()) {
                                    songInfo.setState(0);
                                    break;
                                }
                            } else {
                                songInfo.setState(1);
                                break;
                            }
                        }
                    }
                    SongRepository.this.mBoxStore.boxFor(SongInfo.class).put((Collection) list);
                    return;
                }
                SongRepository.this.mBoxStore.boxFor(SongInfo.class).removeAll();
            }
        }, null);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.ISong
    public List<SongInfo> getSongs(long deviceId) {
        return this.mBoxBuilderFactory.queryActivSongList(deviceId).build().find();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.ISong
    public boolean hasSongs(long deviceId) {
        return this.mBoxBuilderFactory.queryActivSongList(deviceId).build().count() != 0;
    }

    @Override // com.ltech.smarthome.model.repo.ifun.ISong
    public boolean hasSongPlaylist(long deviceId) {
        return this.mBoxBuilderFactory.queryPlaylist(deviceId).build().count() != 0;
    }

    @Override // com.ltech.smarthome.model.repo.ifun.ISong
    public SongInfo getSongById(long id) {
        return this.mBoxBuilderFactory.querySongInfo(id).build().findUnique();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.ISong
    public List<SongInfo> getSongsByIds(List<Integer> list) {
        return this.mBoxBuilderFactory.querySongsByIds(list).build().find();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.ISong
    public void addPlaylist(final List<PlayListInfo> list) {
        this.mBoxStore.runInTxAsync(new Runnable() { // from class: com.ltech.smarthome.model.repo.SongRepository.2
            @Override // java.lang.Runnable
            public void run() {
                SongRepository.this.mBoxStore.boxFor(PlayListInfo.class).removeAll();
                SongRepository.this.mBoxStore.boxFor(PlayListInfo.class).put((Collection) list);
            }
        }, null);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.ISong
    public void addPlaylist(final PlayListInfo info) {
        this.mBoxStore.runInTxAsync(new Runnable() { // from class: com.ltech.smarthome.model.repo.SongRepository.3
            @Override // java.lang.Runnable
            public void run() {
                SongRepository.this.mBoxStore.boxFor(PlayListInfo.class).put((Box) info);
            }
        }, null);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.ISong
    public List<PlayListInfo> getPlaylist(long deviceId) {
        return this.mBoxBuilderFactory.queryPlaylist(deviceId).build().find();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.ISong
    public PlayListInfo getPlaylistById(long deviceId, long id) {
        return this.mBoxBuilderFactory.queryPlaylistById(deviceId, id).build().findFirst();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.ISong
    public void updataPlaylist(final PlayListInfo info) {
        this.mBoxStore.runInTxAsync(new Runnable() { // from class: com.ltech.smarthome.model.repo.SongRepository.4
            @Override // java.lang.Runnable
            public void run() {
                SongRepository.this.mBoxStore.boxFor(PlayListInfo.class).put((Box) info);
            }
        }, null);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.ISong
    public void delPlaylistById(final long deviceId, final long id) {
        this.mBoxStore.runInTxAsync(new Runnable() { // from class: com.ltech.smarthome.model.repo.SongRepository.5
            @Override // java.lang.Runnable
            public void run() {
                SongRepository.this.mBoxBuilderFactory.queryPlaylistById(deviceId, id).build().remove();
            }
        }, null);
    }
}