package com.ltech.smarthome.ui.device.super_panel.music;

import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.SongListItemInfo;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.music.SongRequest;
import com.ltech.smarthome.ui.device.super_panel.music.base.ActDcaMusicBaseVM;
import com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager;
import com.ltech.smarthome.ui.device.super_panel.music.manager.SingleSongInterface;
import com.ltech.smarthome.ui.device.super_panel.music.manager.SongListInterface;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.rich.czlylibary.bean.MusicInfo;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.StringUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActDcaMusicListVM extends ActDcaMusicBaseVM {
    public int selectItemPosition;
    public String title;
    public SingleLiveEvent<Void> showMusicListClickEvent = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> updateDataList = new MutableLiveData<>();
    public MutableLiveData<String> ipStr = new MutableLiveData<>();
    public MutableLiveData<String> sizeStr = new MutableLiveData<>();
    public List<SongListItemInfo> itemInfoList = new ArrayList();
    public int page = 0;
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicListVM$$ExternalSyntheticLambda4
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActDcaMusicListVM.this.lambda$new$0((View) obj);
        }
    });

    static /* synthetic */ void lambda$sort$5(Object obj) throws Exception {
    }

    public int getImageResource(String title) {
        return ActivityUtils.getTopActivity().getString(R.string.music_local_short).equals(title) ? R.mipmap.bg_music_list_local : ActivityUtils.getTopActivity().getString(R.string.music_collect).equals(title) ? R.mipmap.bg_music_list_collect : ActivityUtils.getTopActivity().getString(R.string.music_morning).equals(title) ? R.mipmap.bg_music_list_morning : ActivityUtils.getTopActivity().getString(R.string.music_recommend_short).equals(title) ? R.mipmap.bg_music_list_recommend : ActivityUtils.getTopActivity().getString(R.string.music_sleep).equals(title) ? R.mipmap.bg_music_list_sleep : ActivityUtils.getTopActivity().getString(R.string.music_sport).equals(title) ? R.mipmap.bg_music_list_sport : ActivityUtils.getTopActivity().getString(R.string.music_meditation).equals(title) ? R.mipmap.bg_music_list_meditation : ActivityUtils.getTopActivity().getString(R.string.music_relax).equals(title) ? R.mipmap.bg_music_list_relax : ActivityUtils.getTopActivity().getString(R.string.music_focus).equals(title) ? R.mipmap.bg_music_list_focus : ActivityUtils.getTopActivity().getString(R.string.music_classic).equals(title) ? R.mipmap.bg_music_list_classic : ActivityUtils.getTopActivity().getString(R.string.music_new).equals(title) ? R.mipmap.bg_music_list_new : ActivityUtils.getTopActivity().getString(R.string.music_show).equals(title) ? R.mipmap.bg_music_list_show : ActivityUtils.getTopActivity().getString(R.string.music_blue).equals(title) ? R.mipmap.bg_music_list_blue : ActivityUtils.getTopActivity().getString(R.string.music_inspirational).equals(title) ? R.mipmap.bg_music_list_inspirational : ActivityUtils.getTopActivity().getString(R.string.music_yoga).equals(title) ? R.mipmap.bg_music_list_yoga : ActivityUtils.getTopActivity().getString(R.string.music_slow).equals(title) ? R.mipmap.bg_music_list_slow : ActivityUtils.getTopActivity().getString(R.string.music_cofe).equals(title) ? R.mipmap.bg_music_list_cofe : ActivityUtils.getTopActivity().getString(R.string.music_date).equals(title) ? R.mipmap.bg_music_list_date : R.mipmap.bg_music_list_local;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.iv_copy /* 2131296989 */:
            case R.id.tv_address /* 2131298443 */:
                if (!TextUtils.isEmpty(this.ipStr.getValue())) {
                    Utils.copyText(getContext(), this.ipStr.getValue());
                    SmartToast.showCenterShort(getContext().getString(R.string.copy_success));
                    break;
                }
                break;
            case R.id.iv_play /* 2131297175 */:
                playOrStopMusic();
                break;
            case R.id.iv_playlist /* 2131297181 */:
                this.showMusicListClickEvent.call();
                break;
            case R.id.layout_bottom /* 2131297374 */:
                NavUtils.destination(ActDcaMusicDetail.class).withLong(Constants.CONTROL_ID, this.controlId).withBoolean(Constants.MUSIC_NEED_ORDER_MUSIC, false).withString(Constants.MUSIC_SONG_ID, this.songId).withBoolean(Constants.MUSIC_SONG_IS_LOCAL, this.areaId == 1).withString(Constants.MAC_ADDRESS, this.deviceMac).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
                ActivityUtils.getTopActivity().overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
                break;
            case R.id.layout_play_all /* 2131297575 */:
                playAll();
                break;
            case R.id.layout_sort /* 2131297654 */:
                if (Injection.repo().home().getSelPlace().isManager() || Injection.repo().home().getSelPlace().isOwner()) {
                    NavUtils.destination(ActSortLocalMusic.class).withLong(Constants.CONTROL_ID, this.controlId).withString(Constants.MAC_ADDRESS, this.deviceMac).withString(Constants.SELECTED_LIST, GsonUtils.toJson(this.itemInfoList)).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
                    break;
                } else {
                    SmartToast.showCenterShort(getContext().getString(R.string.app_str_do_not_have_enough_permissions));
                    break;
                }
            case R.id.tv_wifi /* 2131299105 */:
                if (Injection.repo().home().getSelPlace().isManager() || Injection.repo().home().getSelPlace().isOwner()) {
                    NavUtils.destination(ActTransferMusic.class).withLong(Constants.CONTROL_ID, this.controlId).withString(Constants.MAC_ADDRESS, this.deviceMac).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
                    break;
                } else {
                    SmartToast.showCenterShort(getContext().getString(R.string.app_str_do_not_have_enough_permissions));
                    break;
                }
                break;
        }
    }

    private void playAll() {
        playMusicType(this.title, new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicListVM$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDcaMusicListVM.this.lambda$playAll$1((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$playAll$1(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getStateCode() != 0) {
            return;
        }
        NavUtils.destination(ActDcaMusicDetail.class).withLong(Constants.CONTROL_ID, this.controlId).withBoolean(Constants.MUSIC_NEED_ORDER_MUSIC, false).withString(Constants.MUSIC_SONG_ID, this.songId).withBoolean(Constants.MUSIC_SONG_IS_LOCAL, this.areaId == 1).withString(Constants.MAC_ADDRESS, this.deviceMac).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
        ActivityUtils.getTopActivity().overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
    }

    public void setCollect(String isCollect, String musicId, IAction<ResponseMsg> result) {
        controlAction(8, Integer.parseInt(isCollect) == 0 ? 1 : 0, musicId, result);
    }

    public void getSongList(String title) {
        String categoryName = getCategoryName(title);
        String menuName = getMenuName(title);
        this.page++;
        if (categoryName.equals("全部") || categoryName.equals("尖叫热歌榜")) {
            MiguManager.getInstance().getSongList(categoryName, this.page, new SongListInterface() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicListVM.1
                @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SongListInterface
                public void onSuccess(List<SongListItemInfo> listItemInfos) {
                    ActDcaMusicListVM.this.itemInfoList.addAll(listItemInfos);
                    ActDcaMusicListVM.this.updateDataList.setValue(true);
                }

                @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SongListInterface
                public void onFail() {
                    ActDcaMusicListVM.this.updateDataList.setValue(false);
                }
            });
            return;
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_collect).equals(title)) {
            MiguManager.getInstance().getCollectList(this.page, new SongListInterface() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicListVM.2
                @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SongListInterface
                public void onSuccess(List<SongListItemInfo> listItemInfos) {
                    ActDcaMusicListVM.this.itemInfoList.clear();
                    ActDcaMusicListVM.this.itemInfoList.addAll(listItemInfos);
                    ActDcaMusicListVM.this.updateDataList.setValue(true);
                }

                @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SongListInterface
                public void onFail() {
                    ActDcaMusicListVM.this.updateDataList.setValue(false);
                }
            });
        } else if (ActivityUtils.getTopActivity().getString(R.string.music_local_short).equals(title)) {
            MiguManager.getInstance().getLocalMusicList(this.deviceMac, new IAction<List<SongListItemInfo>>() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicListVM.3
                @Override // com.ltech.smarthome.base.IAction
                public void act(List<SongListItemInfo> songListItemInfos) {
                    ActDcaMusicListVM.this.itemInfoList.clear();
                    ActDcaMusicListVM.this.itemInfoList.addAll(songListItemInfos);
                    ActDcaMusicListVM.this.updateDataList.setValue(true);
                }
            });
        } else {
            MiguManager.getInstance().getSongList(categoryName, menuName, this.page, new SongListInterface() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicListVM.4
                @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SongListInterface
                public void onSuccess(List<SongListItemInfo> listItemInfos) {
                    ActDcaMusicListVM.this.itemInfoList.addAll(listItemInfos);
                    ActDcaMusicListVM.this.updateDataList.setValue(true);
                }

                @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SongListInterface
                public void onFail() {
                    ActDcaMusicListVM.this.updateDataList.setValue(false);
                }
            });
        }
    }

    public void playOrStopMusic() {
        controlAction(this.isPlaying.getValue().booleanValue() ? 2 : 1, 0);
    }

    public int getTotal(String title) {
        String categoryName = getCategoryName(title);
        String menuName = getMenuName(title);
        if (ActivityUtils.getTopActivity().getString(R.string.music_local_short).equals(title)) {
            return 10;
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_new).equals(title) || ActivityUtils.getTopActivity().getString(R.string.music_recommend_short).equals(title)) {
            return MiguManager.getInstance().getSongListSize(categoryName);
        }
        return MiguManager.getInstance().getSongListSize(categoryName, menuName);
    }

    private String getCategoryName(String title) {
        if (ActivityUtils.getTopActivity().getString(R.string.music_morning).equals(title)) {
            return "情感";
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_sleep).equals(title)) {
            return "场景";
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_sport).equals(title) || ActivityUtils.getTopActivity().getString(R.string.music_meditation).equals(title)) {
            return "运动";
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_relax).equals(title) || ActivityUtils.getTopActivity().getString(R.string.music_focus).equals(title) || ActivityUtils.getTopActivity().getString(R.string.music_classic).equals(title)) {
            return "场景";
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_show).equals(title)) {
            return "综艺";
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_blue).equals(title) || ActivityUtils.getTopActivity().getString(R.string.music_inspirational).equals(title)) {
            return "情感";
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_yoga).equals(title)) {
            return "运动";
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_slow).equals(title) || ActivityUtils.getTopActivity().getString(R.string.music_cofe).equals(title) || ActivityUtils.getTopActivity().getString(R.string.music_date).equals(title)) {
            return "场景";
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_recommend_short).equals(title)) {
            return "尖叫热歌榜";
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_new).equals(title)) {
            return "全部";
        }
        return "";
    }

    private String getMenuName(String title) {
        if (ActivityUtils.getTopActivity().getString(R.string.music_morning).equals(title)) {
            return "快乐";
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_sleep).equals(title)) {
            return "胎教";
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_sport).equals(title)) {
            return "骑行";
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_meditation).equals(title)) {
            return "瑜伽：冥想";
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_relax).equals(title)) {
            return "放松";
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_focus).equals(title)) {
            return "午休";
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_classic).equals(title)) {
            return "在路上";
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_show).equals(title)) {
            return "蒙面唱将";
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_blue).equals(title)) {
            return "治愈";
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_inspirational).equals(title)) {
            return "励志";
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_yoga).equals(title)) {
            return "瑜伽：空中";
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_slow).equals(title)) {
            return "开车";
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_cofe).equals(title)) {
            return "睡前故事";
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_date).equals(title)) {
            return "情调";
        }
        return "";
    }

    public void getSongFromBle() {
        this.songId = "";
        CmdAssistant.getMusicAssistant(this.controlDevice, new int[0]).getCurState(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicListVM$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDcaMusicListVM.this.lambda$getSongFromBle$2((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSongFromBle$2(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getStateCode() != 0) {
            return;
        }
        refreshPanelData(responseMsg);
    }

    public void refreshPanelData(ResponseMsg msg) {
        String resData = msg.getResData();
        if (resData.length() > 61) {
            int parseInt = Integer.parseInt(resData.substring(16, 18), 16);
            int parseInt2 = Integer.parseInt(resData.substring(56, 60), 16);
            this.isLocal = parseInt == 1 || isLocal(this.songId);
            String hexToString = StringUtils.hexToString(resData.substring(18, 40));
            if (!hexToString.equalsIgnoreCase(this.songId)) {
                this.songId = hexToString;
                if (isLocal(this.songId)) {
                    refreshLocalSong(parseInt2);
                } else {
                    refreshSong();
                }
            }
            if (this.areaId != parseInt) {
                this.refreshIcon.setValue(Integer.valueOf(parseInt));
            }
            this.areaId = parseInt;
            int parseInt3 = Integer.parseInt(resData.substring(40, 42), 16);
            if (this.isPlaying.getValue().booleanValue() != (parseInt3 == 1) || this.isInit) {
                this.isPlaying.setValue(Boolean.valueOf(parseInt3 == 1));
            }
            this.curMode = Integer.parseInt(resData.substring(46, 48), 16);
        }
    }

    private void refreshSong() {
        MiguManager.getInstance().findMusicInfoById(this.songId, new SingleSongInterface() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicListVM.5
            @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SingleSongInterface
            public void onFail() {
            }

            @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SingleSongInterface
            public void onSuccess(MusicInfo musicInfo) {
                ActDcaMusicListVM.this.musicData.setValue(musicInfo);
            }
        });
    }

    public void getWifiTransferInfo() {
        CmdAssistant.getMusicAssistant(this.controlDevice, new int[0]).queryWifiTransferInfo(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicListVM$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDcaMusicListVM.this.lambda$getWifiTransferInfo$3((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getWifiTransferInfo$3(ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0 && responseMsg.getResData() != null) {
            this.sizeStr.setValue(String.format(getContext().getString(R.string.app_str_wifi_transfer_music_tip4), Integer.valueOf(Integer.parseInt(responseMsg.getResData().substring(28, 30), 16))));
            this.ipStr.setValue("http://" + Integer.parseInt(responseMsg.getResData().substring(16, 18), 16) + "." + Integer.parseInt(responseMsg.getResData().substring(18, 20), 16) + "." + Integer.parseInt(responseMsg.getResData().substring(20, 22), 16) + "." + Integer.parseInt(responseMsg.getResData().substring(22, 24), 16) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + Integer.parseInt(responseMsg.getResData().substring(24, 28), 16));
            return;
        }
        SmartToast.showCenterShort(getContext().getString(R.string.search_fail));
    }

    public void controlWifiTransfer(final boolean z) {
        CmdAssistant.getMusicAssistant(this.controlDevice, new int[0]).controlWifiTransfer(ActivityUtils.getTopActivity(), z ? 1 : 0, new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicListVM$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDcaMusicListVM.this.lambda$controlWifiTransfer$4(z, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$controlWifiTransfer$4(boolean z, ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getStateCode() != 0) {
            SmartToast.showCenterShort(getContext().getString(R.string.app_str_operation_failure));
        } else if (z) {
            getWifiTransferInfo();
        }
    }

    public void sort(List<SongRequest.Sort> requests) {
        SongRequest songRequest = new SongRequest();
        songRequest.setSongs(requests);
        ((ObservableSubscribeProxy) Injection.net().sortLocalMusic(songRequest).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicListVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDcaMusicListVM.lambda$sort$5(obj);
            }
        }, new SmartErrorComsumer());
    }

    public void del(final List<Long> ids) {
        SongRequest songRequest = new SongRequest();
        songRequest.setSongids(ids);
        ((ObservableSubscribeProxy) Injection.net().delLocalMusic(songRequest).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicListVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDcaMusicListVM.this.lambda$del$6(ids, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$del$6(List list, Object obj) throws Exception {
        SmartToast.showCenterShort(String.format(getContext().getString(R.string.app_str_del_music_success_tip), Integer.valueOf(list.size())));
    }

    public static class MusicInfoItem {
        private SongListItemInfo itemInfo;
        private boolean sel;

        public MusicInfoItem(boolean sel, SongListItemInfo itemInfo) {
            this.sel = sel;
            this.itemInfo = itemInfo;
        }

        public void setSel(boolean sel) {
            this.sel = sel;
        }

        public void setItemInfo(SongListItemInfo itemInfo) {
            this.itemInfo = itemInfo;
        }

        public boolean isSel() {
            return this.sel;
        }

        public SongListItemInfo getItemInfo() {
            return this.itemInfo;
        }
    }
}