package com.ltech.smarthome.ui.device.light;

import android.content.Context;
import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.product_agreement.param.LightCmdParam;

/* loaded from: classes4.dex */
public class FtMusicVM extends BaseViewModel {
    public long controlId;
    public Object controlObject;
    public boolean groupControl;
    private LightCmdParam mLightCmdParam;
    public boolean playListEmpty = true;
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.light.FtMusicVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            FtMusicVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.iv_play_list /* 2131297177 */:
                navigation(NavUtils.destination(ActMusicList.class));
                break;
            case R.id.iv_play_mode /* 2131297178 */:
                Injection.player().changeMode();
                break;
            default:
                if (this.playListEmpty) {
                    SmartToast.showShort(R.string.playlist_null);
                    break;
                } else {
                    int id = view.getId();
                    if (id == R.id.iv_next) {
                        Injection.player().playNext();
                        break;
                    } else if (id == R.id.iv_play) {
                        Injection.player().togglePlay();
                        break;
                    } else if (id == R.id.iv_previous) {
                        Injection.player().playPrevious();
                        break;
                    }
                }
                break;
        }
    }

    public void setVolume(Context context, float volumePercent) {
        getLightCmdParam().setCmdType(13);
        getLightCmdParam().setVolumePercent(volumePercent);
        if (Injection.player().isPlaying()) {
            Injection.message().create(context).cmdParam(getLightCmdParam()).intervalTime(0).control(this.controlObject).enqueue();
        }
    }

    public void setPlay(Context context, boolean play) {
        getLightCmdParam().setCmdType(13);
        getLightCmdParam().setPlayMusic(play);
        Injection.message().create(context).cmdParam(getLightCmdParam()).control(this.controlObject).sendTimes(3).enqueue();
    }

    private LightCmdParam getLightCmdParam() {
        if (this.mLightCmdParam == null) {
            this.mLightCmdParam = new LightCmdParam();
        }
        return this.mLightCmdParam;
    }

    public void startRecord() {
        Injection.player().startRecord();
    }

    public void stopRecord() {
        Injection.player().stopRecord();
    }
}