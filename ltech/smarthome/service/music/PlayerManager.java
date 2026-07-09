package com.ltech.smarthome.service.music;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.preference_bean.MusicBean;
import com.ltech.smarthome.service.music.RecordThread;
import com.ltech.smarthome.utils.VersionUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class PlayerManager implements IPlayerManager<MusicBean> {
    private IPlayerController<MusicBean> mController;
    private IAction<Float> mRecordAction;
    private RecordThread mRecordThread;

    private PlayerManager() {
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void init(Context context) {
        if (this.mController == null) {
            this.mController = new PlayerController();
        }
        this.mController.init(context);
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void setPlayList(List<MusicBean> list) {
        this.mController.setPlayList(list);
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public List<MusicBean> getSavePlayList() {
        return this.mController.getSavePlayList();
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void savePlayList(List<MusicBean> list) {
        this.mController.savePlayList(list);
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void playAudio() {
        this.mController.playAudio();
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void playAudio(int index) {
        this.mController.playAudio(index);
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void togglePlay() {
        this.mController.togglePlay();
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void playNext() {
        this.mController.playNext();
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void playPrevious() {
        this.mController.playPrevious();
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void playRandom() {
        this.mController.playRandom();
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void pauseAudio() {
        this.mController.pauseAudio();
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void resumeAudio() {
        this.mController.resumeAudio();
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void clear() {
        this.mController.clear();
        this.mController = null;
        this.mRecordAction = null;
        stopRecord();
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void changeMode() {
        this.mController.changeMode();
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void changeMode(int mode) {
        this.mController.changeMode(mode);
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void setSeek(int progress) {
        this.mController.setSeek(progress);
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public boolean isPlaying() {
        return this.mController.isPlaying();
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void reset() {
        this.mController.reset();
    }

    @Override // com.ltech.smarthome.service.music.ILiveDataNotifier
    public MutableLiveData<Boolean> getPauseLiveData() {
        return this.mController.getPauseLiveData();
    }

    @Override // com.ltech.smarthome.service.music.ILiveDataNotifier
    public MutableLiveData<Integer> getPlayModeLiveData() {
        return this.mController.getPlayModeLiveData();
    }

    @Override // com.ltech.smarthome.service.music.ILiveDataNotifier
    public MutableLiveData<PlayingMusic> getCurrentPlayMusic() {
        return this.mController.getCurrentPlayMusic();
    }

    @Override // com.ltech.smarthome.service.music.ILiveDataNotifier
    public MutableLiveData<MusicBean> getChangePlayMusic() {
        return this.mController.getChangePlayMusic();
    }

    @Override // com.ltech.smarthome.service.music.ILiveDataNotifier
    public MutableLiveData<Float> getVisualizerValue() {
        return this.mController.getVisualizerValue();
    }

    @Override // com.ltech.smarthome.service.music.ILiveDataNotifier
    public MutableLiveData<List<MusicBean>> getPlayList() {
        return this.mController.getPlayList();
    }

    @Override // com.ltech.smarthome.service.music.IPlayerManager
    public List<MusicBean> queryMusic(Context context) {
        Cursor query = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[]{"title", "duration", "artist", "_id", "_display_name", "_data", "album_id", "date_added"}, null, null, "title_key");
        ArrayList arrayList = new ArrayList();
        if (query != null && query.getCount() > 0) {
            query.moveToFirst();
            for (int i = 0; i < query.getCount(); i++) {
                try {
                    MusicBean musicBean = new MusicBean();
                    musicBean.setTitle(query.getString(query.getColumnIndex("title")));
                    musicBean.setArtist(query.getString(query.getColumnIndex("artist")));
                    musicBean.setDuration(query.getLong(query.getColumnIndex("duration")));
                    musicBean.setId(query.getLong(query.getColumnIndex("_id")));
                    if (VersionUtils.isAndroidQ()) {
                        musicBean.setPath(ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, musicBean.getId()).toString());
                    } else {
                        musicBean.setPath(query.getString(query.getColumnIndex("_data")));
                    }
                    arrayList.add(musicBean);
                    query.moveToNext();
                } catch (Exception unused) {
                } finally {
                    query.close();
                }
            }
            return arrayList;
        }
        return arrayList;
    }

    @Override // com.ltech.smarthome.service.music.IPlayerManager
    public List<MusicBean> refreshSaveList(Context context) {
        ArrayList arrayList = new ArrayList();
        List<MusicBean> savePlayList = Injection.player().getSavePlayList();
        for (MusicBean musicBean : Injection.player().queryMusic(context)) {
            Iterator<MusicBean> it = savePlayList.iterator();
            while (it.hasNext()) {
                if (musicBean.equals(it.next())) {
                    arrayList.add(musicBean);
                }
            }
        }
        savePlayList(arrayList);
        return arrayList;
    }

    @Override // com.ltech.smarthome.service.music.IPlayerManager
    public void startRecord() {
        if (this.mRecordThread == null) {
            this.mRecordThread = new RecordThread(new RecordThread.RecordCallback() { // from class: com.ltech.smarthome.service.music.PlayerManager$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.service.music.RecordThread.RecordCallback
                public final void onVolumes(float f) {
                    PlayerManager.this.lambda$startRecord$0(f);
                }
            });
        }
        if (this.mRecordThread.isRecording()) {
            return;
        }
        this.mRecordThread.startRecord();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startRecord$0(float f) {
        IAction<Float> iAction = this.mRecordAction;
        if (iAction != null) {
            iAction.act(Float.valueOf(f));
        }
    }

    @Override // com.ltech.smarthome.service.music.IPlayerManager
    public void stopRecord() {
        RecordThread recordThread = this.mRecordThread;
        if (recordThread == null || !recordThread.isRecording()) {
            return;
        }
        this.mRecordThread.stopRecord();
        this.mRecordThread = null;
    }

    @Override // com.ltech.smarthome.service.music.IPlayerManager
    public void setRecordAction(IAction<Float> action) {
        this.mRecordAction = action;
    }
}