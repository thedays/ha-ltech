package com.ltech.smarthome.ui.scene;

import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.smart.product_agreement.param.BleMusicPlayerParam;

/* loaded from: classes4.dex */
public class ActSelectMusicPlayerActionVM extends BaseViewModel {
    public void pause() {
        BleMusicPlayerParam bleMusicPlayerParam = new BleMusicPlayerParam();
        bleMusicPlayerParam.setData(0);
        bleMusicPlayerParam.setCmdType(2);
        bleMusicPlayerParam.addExtParam(SceneHelper.OPTION_VALUE, ActivityUtils.getTopActivity().getString(R.string.music_player_pause_the_song));
        SceneHelper.instance().cmdParam = bleMusicPlayerParam;
    }

    public void playAll(int mode, int volume) {
        BleMusicPlayerParam bleMusicPlayerParam = new BleMusicPlayerParam();
        bleMusicPlayerParam.setScope(1);
        bleMusicPlayerParam.setVolume(volume);
        bleMusicPlayerParam.setDir(0);
        bleMusicPlayerParam.setMode(mode);
        bleMusicPlayerParam.setSound(0);
        bleMusicPlayerParam.setCmdType(15);
        bleMusicPlayerParam.addExtParam(SceneHelper.OPTION_VALUE, String.format("%s%s", getModeString(mode), ActivityUtils.getTopActivity().getString(R.string.music_player_all_songs)));
        SceneHelper.instance().cmdParam = bleMusicPlayerParam;
    }

    public void singleSong(long song, int mode, int volume, String name) {
        BleMusicPlayerParam bleMusicPlayerParam = new BleMusicPlayerParam();
        bleMusicPlayerParam.setScope(3);
        bleMusicPlayerParam.setVolume(volume);
        bleMusicPlayerParam.setDir(0);
        bleMusicPlayerParam.setMode(mode);
        bleMusicPlayerParam.setSound((int) song);
        bleMusicPlayerParam.setCmdType(15);
        bleMusicPlayerParam.addExtParam(SceneHelper.OPTION_VALUE, String.format("%s%s:%s", getModeString(mode), ActivityUtils.getTopActivity().getString(R.string.songs), name));
        SceneHelper.instance().cmdParam = bleMusicPlayerParam;
    }

    public void singlePlaylist(long playList, int mode, int volume, String name) {
        BleMusicPlayerParam bleMusicPlayerParam = new BleMusicPlayerParam();
        bleMusicPlayerParam.setScope(2);
        bleMusicPlayerParam.setVolume(volume);
        bleMusicPlayerParam.setDir((int) playList);
        bleMusicPlayerParam.setMode(mode);
        bleMusicPlayerParam.setSound(0);
        bleMusicPlayerParam.setCmdType(15);
        bleMusicPlayerParam.addExtParam(SceneHelper.OPTION_VALUE, String.format("%s%s:%s", getModeString(mode), ActivityUtils.getTopActivity().getString(R.string.music_playlist), name));
        SceneHelper.instance().cmdParam = bleMusicPlayerParam;
    }

    private String getModeString(int mode) {
        if (mode == 1 || mode == 2) {
            return ActivityUtils.getTopActivity().getString(R.string.music_player_loop_playback);
        }
        if (mode == 3) {
            return ActivityUtils.getTopActivity().getString(R.string.music_player_shuffle);
        }
        return ActivityUtils.getTopActivity().getString(R.string.music_player_play_once);
    }
}