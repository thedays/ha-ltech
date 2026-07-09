package com.ltech.smarthome.ui.device.super_panel.music;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.MusicInfo;
import com.ltech.smarthome.model.bean.SongListItemInfo;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.device.super_panel.music.base.ActDcaMusicBaseVM;
import com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager;
import com.ltech.smarthome.ui.device.super_panel.music.manager.SearchSongInfoInterface;
import com.ltech.smarthome.ui.device.super_panel.music.manager.SongListInterface;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.rich.czlylibary.bean.SongNewVoiceBox;
import com.smart.message.ResponseMsg;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActDcaMusicSearchVM extends ActDcaMusicBaseVM {
    public SingleLiveEvent<Void> getSearchList = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> historyDeleteEvent = new SingleLiveEvent<>();
    public List<SongNewVoiceBox> songNewVoiceBoxList = new ArrayList();
    public List<SongListItemInfo> itemInfoList = new ArrayList();
    public MutableLiveData<Boolean> showHistoryLayout = new MutableLiveData<>(true);
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicSearchVM$$ExternalSyntheticLambda1
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActDcaMusicSearchVM.this.lambda$new$0((View) obj);
        }
    });

    public void search(String name) {
        showLoadingDialog();
        MiguManager.getInstance().searchSongInfo(name, new SearchSongInfoInterface() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicSearchVM.1
            @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SearchSongInfoInterface
            public void onSuccess(List<SongNewVoiceBox> songNewVoiceBoxes) {
                ActDcaMusicSearchVM.this.songNewVoiceBoxList.clear();
                ActDcaMusicSearchVM.this.songNewVoiceBoxList = songNewVoiceBoxes;
                if (ActDcaMusicSearchVM.this.songNewVoiceBoxList.isEmpty()) {
                    return;
                }
                ActDcaMusicSearchVM.this.itemInfoList.clear();
                if (ActDcaMusicSearchVM.this.songNewVoiceBoxList.size() > 20) {
                    ActDcaMusicSearchVM.this.getSearchFinalResult(2);
                } else {
                    ActDcaMusicSearchVM.this.getSearchFinalResult(1);
                }
            }

            @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SearchSongInfoInterface
            public void onFail() {
                ActDcaMusicSearchVM.this.dismissLoadingDialog();
                ActDcaMusicSearchVM.this.itemInfoList.clear();
                ActDcaMusicSearchVM.this.songNewVoiceBoxList.clear();
                ActDcaMusicSearchVM.this.getSearchList.call();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getSearchFinalResult(final int page) {
        final ArrayList<String> arrayList = new ArrayList<>();
        for (SongNewVoiceBox songNewVoiceBox : this.songNewVoiceBoxList) {
            if (songNewVoiceBox.getFullSongs() != null) {
                arrayList.add(songNewVoiceBox.getFullSongs()[0].getCopyrightId());
            }
        }
        for (final int i = 1; i <= page; i++) {
            MiguManager.getInstance().getSongList(arrayList, i, new SongListInterface() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicSearchVM.2
                @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SongListInterface
                public void onSuccess(List<SongListItemInfo> listItemInfos) {
                    ActDcaMusicSearchVM.this.itemInfoList.addAll(listItemInfos);
                    if (i == page || arrayList.size() == ActDcaMusicSearchVM.this.itemInfoList.size()) {
                        for (SongListItemInfo songListItemInfo : ActDcaMusicSearchVM.this.itemInfoList) {
                            for (SongNewVoiceBox songNewVoiceBox2 : ActDcaMusicSearchVM.this.songNewVoiceBoxList) {
                                if (songNewVoiceBox2.getFullSongs()[0].getCopyrightId().equals(songListItemInfo.getSongId())) {
                                    songListItemInfo.setHighlightStr(songNewVoiceBox2.getHighlightStr());
                                }
                            }
                        }
                        ActDcaMusicSearchVM.this.dismissLoadingDialog();
                        ActDcaMusicSearchVM.this.getSearchList.call();
                    }
                }

                @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SongListInterface
                public void onFail() {
                    if (i == page) {
                        ActDcaMusicSearchVM.this.dismissLoadingDialog();
                        ActDcaMusicSearchVM.this.getSearchList.call();
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        int id = view.getId();
        if (id == R.id.layout_play_all) {
            playAll();
        } else {
            if (id != R.id.search_del) {
                return;
            }
            this.historyDeleteEvent.call();
        }
    }

    private void playAll() {
        if (this.songNewVoiceBoxList.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (SongListItemInfo songListItemInfo : this.itemInfoList) {
            MusicInfo musicInfo = new MusicInfo();
            musicInfo.setMusicid(songListItemInfo.getSongId());
            musicInfo.setSingername(songListItemInfo.getSinger());
            musicInfo.setMusicname(songListItemInfo.getName());
            arrayList.add(musicInfo);
        }
        ((ObservableSubscribeProxy) Injection.net().updateMusicInfo(this.deviceMac, arrayList).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicSearchVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDcaMusicSearchVM.this.lambda$playAll$1(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$playAll$1(Object obj) throws Exception {
        playMusic();
    }

    private void playMusic() {
        CmdAssistant.getMusicAssistant(this.controlDevice, new int[0]).playList(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicSearchVM$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDcaMusicSearchVM.this.lambda$playMusic$2((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$playMusic$2(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getStateCode() != 0) {
            return;
        }
        NavUtils.destination(ActDcaMusicDetail.class).withLong(Constants.CONTROL_ID, this.controlId).withBoolean(Constants.MUSIC_NEED_ORDER_MUSIC, false).withBoolean(Constants.MUSIC_SONG_IS_LOCAL, false).withString(Constants.MAC_ADDRESS, this.deviceMac).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
        ActivityUtils.getTopActivity().overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
    }
}