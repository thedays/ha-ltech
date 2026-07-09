package com.ltech.smarthome.ui.device.musicplayer;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.extra.TabContentdefault;
import com.ltech.smarthome.utils.NavHelper;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActBleMusicPlayerVM extends BaseViewModel {
    public long controlId;
    private Device device;
    public List<TabContentdefault> tabs = new ArrayList();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.musicplayer.ActBleMusicPlayerVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActBleMusicPlayerVM.this.lambda$new$0((View) obj);
        }
    });

    public void initTabList() {
        this.tabs.add(new TabContentdefault(R.string.songs, FtSongs.create(this.controlId)));
        this.tabs.add(new TabContentdefault(R.string.music_playlist, FtSongPlayList.create(this.controlId)));
    }

    public void loadDevice() {
        this.device = Injection.repo().device().getDeviceById(this.controlId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        int id = view.getId();
        if (id == R.id.iv_back) {
            finishActivity();
        } else if (id == R.id.iv_setting) {
            NavHelper.goSetting(this.device);
        }
    }
}