package com.ltech.smarthome.ui.device.sonos;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.net.exception.ApiException;
import com.ltech.smarthome.net.response.device.ListDeviceResponse;
import com.ltech.smarthome.ui.device.sonos.SonosResponse;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ActSonosPlayControlVM extends BaseViewModel {
    public int CurTime;
    public int curScheduledSetTime;
    public int curScheduledTimeLeft;
    public int curVolume;
    public long deviceId;
    public boolean isCollect;
    public long placeId;
    public int songLength;
    protected MutableLiveData<Boolean> updateCollectData = new MutableLiveData<>();
    public MutableLiveData<Boolean> isCrossFade = new MutableLiveData<>(false);
    public MutableLiveData<Integer> playModeEvent = new MutableLiveData<>(0);
    public MutableLiveData<Boolean> isPlaying = new MutableLiveData<>(false);
    public SingleLiveEvent<Void> showVolumeClickEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showTimeClickEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showMusicListClickEvent = new SingleLiveEvent<>();
    public MutableLiveData<Integer> refreshScheduledTimeEvent = new MutableLiveData<>(0);
    public MutableLiveData<Integer> volume = new MutableLiveData<>(0);
    public MutableLiveData<SonosResponse.Metadata> showMusicData = new MutableLiveData<>();
    public MutableLiveData<Integer> authEvent = new MutableLiveData<>();
    public MutableLiveData<List<ListDeviceResponse.RowsBean>> data = new MutableLiveData<>();
    public MutableLiveData<Boolean> canRepeat = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> canRepeatOne = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> canShuffle = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> canCrossfade = new MutableLiveData<>(false);
    public MutableLiveData<String> songName = new MutableLiveData<>();
    public MutableLiveData<String> singerName = new MutableLiveData<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda19
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActSonosPlayControlVM.this.lambda$new$6((View) obj);
        }
    });

    static /* synthetic */ void lambda$loadData$25(Throwable th) throws Exception {
    }

    static /* synthetic */ void lambda$play$26(List list) throws Exception {
    }

    static /* synthetic */ void lambda$play$27(Throwable th) throws Exception {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$6(View view) {
        switch (view.getId()) {
            case R.id.cross /* 2131296593 */:
            case R.id.iv_cross /* 2131296992 */:
            case R.id.tv_cross /* 2131298548 */:
                if (Boolean.FALSE.equals(this.canCrossfade.getValue())) {
                    SmartToast.showShort(R.string.app_str_sonos_action_no_support);
                    break;
                } else {
                    this.isCrossFade.setValue(Boolean.valueOf(Boolean.FALSE.equals(this.isCrossFade.getValue())));
                    setMode();
                    break;
                }
            case R.id.iv_next /* 2131297154 */:
                if (this.showMusicData.getValue() != null && this.showMusicData.getValue().getNextItem() != null && this.showMusicData.getValue().getNextItem() != null && this.showMusicData.getValue().getNextItem().getTrack() != null) {
                    this.songName.setValue(this.showMusicData.getValue().getNextItem().getTrack().getName());
                    if (this.showMusicData.getValue().getNextItem().getTrack().getArtist() != null) {
                        this.singerName.setValue(this.showMusicData.getValue().getNextItem().getTrack().getArtist().getName());
                    }
                }
                ((ObservableSubscribeProxy) Injection.net().sonosNext(this.deviceId, Injection.repo().home().getSelPlace().getPlaceId()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda22
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActSonosPlayControlVM.this.lambda$new$4((Objects) obj);
                    }
                }, new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda23
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActSonosPlayControlVM.this.lambda$new$5((Throwable) obj);
                    }
                });
                break;
            case R.id.iv_play /* 2131297175 */:
                this.isPlaying.setValue(Boolean.valueOf(Boolean.FALSE.equals(this.isPlaying.getValue())));
                ((ObservableSubscribeProxy) Injection.net().sonosPlayPause(this.deviceId, Injection.repo().home().getSelPlace().getPlaceId()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActSonosPlayControlVM.this.lambda$new$0((Objects) obj);
                    }
                }, new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda11
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActSonosPlayControlVM.this.lambda$new$1((Throwable) obj);
                    }
                });
                break;
            case R.id.iv_previous /* 2131297190 */:
                ((ObservableSubscribeProxy) Injection.net().sonosPre(this.deviceId, Injection.repo().home().getSelPlace().getPlaceId()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda20
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActSonosPlayControlVM.this.lambda$new$2((Objects) obj);
                    }
                }, new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda21
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActSonosPlayControlVM.this.lambda$new$3((Throwable) obj);
                    }
                });
                break;
            case R.id.iv_repeatOne_bg /* 2131297208 */:
                if (Boolean.FALSE.equals(this.canRepeatOne.getValue())) {
                    SmartToast.showShort(R.string.app_str_sonos_action_no_support);
                    break;
                } else {
                    if (this.playModeEvent.getValue() != null && this.playModeEvent.getValue().intValue() == 2) {
                        this.playModeEvent.setValue(0);
                    } else {
                        this.playModeEvent.setValue(2);
                    }
                    setMode();
                    break;
                }
                break;
            case R.id.iv_repeat_bg /* 2131297209 */:
                if (Boolean.FALSE.equals(this.canRepeat.getValue())) {
                    SmartToast.showShort(R.string.app_str_sonos_action_no_support);
                    break;
                } else {
                    if (this.playModeEvent.getValue() == null || this.playModeEvent.getValue().intValue() != 1) {
                        this.playModeEvent.setValue(1);
                    } else {
                        this.playModeEvent.setValue(0);
                    }
                    setMode();
                    break;
                }
                break;
            case R.id.iv_shuffle_bg /* 2131297255 */:
                if (Boolean.FALSE.equals(this.canShuffle.getValue())) {
                    SmartToast.showShort(R.string.app_str_sonos_action_no_support);
                    break;
                } else {
                    if (this.playModeEvent.getValue() != null && this.playModeEvent.getValue().intValue() == 3) {
                        this.playModeEvent.setValue(0);
                    } else {
                        this.playModeEvent.setValue(3);
                    }
                    setMode();
                    break;
                }
            case R.id.iv_time /* 2131297293 */:
                this.showTimeClickEvent.call();
                break;
            case R.id.layout_menu /* 2131297537 */:
                this.showMusicListClickEvent.call();
                break;
            case R.id.layout_volume /* 2131297692 */:
                this.showVolumeClickEvent.call();
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Objects objects) throws Exception {
        dismissLoadingDialog();
        if (objects != null) {
            LHomeLog.e(getClass(), objects.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(Throwable th) throws Exception {
        SmartToast.showShort(getContext().getString(R.string.app_str_sonos_action_no_support));
        dismissLoadingDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(Objects objects) throws Exception {
        dismissLoadingDialog();
        if (objects != null) {
            queryMetadataStatus();
            LHomeLog.e(getClass(), objects.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(Throwable th) throws Exception {
        SmartToast.showShort(getContext().getString(R.string.app_str_sonos_action_no_support));
        dismissLoadingDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$4(Objects objects) throws Exception {
        queryMetadataStatus();
        dismissLoadingDialog();
        if (objects != null) {
            LHomeLog.e(getClass(), objects.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$5(Throwable th) throws Exception {
        SmartToast.showShort(getContext().getString(R.string.app_str_sonos_action_no_support));
        dismissLoadingDialog();
    }

    public void login(String value) {
        LHomeLog.e(getClass(), "sonos授权" + value);
        try {
            if (new JSONObject(JSON.parse(value).toString()).optInt("ret") != 0) {
                this.authEvent.setValue(1);
            } else {
                this.authEvent.setValue(0);
                sync();
            }
        } catch (Exception unused) {
            this.authEvent.setValue(1);
        }
    }

    public void sync() {
        showLoadingDialog();
        ((ObservableSubscribeProxy) Injection.net().getSonosBindDevice(Injection.repo().home().getSelPlace().getPlaceId()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda25
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSonosPlayControlVM.this.lambda$sync$7((List) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda26
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSonosPlayControlVM.this.lambda$sync$8((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sync$7(List list) throws Exception {
        if (list != null) {
            this.authEvent.setValue(0);
            this.data.setValue(list);
            showSuccessTipDialog(StringUtils.getString(R.string.sync_success));
            loadData();
        } else {
            this.authEvent.setValue(2);
        }
        dismissLoadingDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sync$8(Throwable th) throws Exception {
        if ((th instanceof ApiException) && ((ApiException) th).getResultBean().getRet() == 102010) {
            this.authEvent.setValue(3);
        } else {
            this.authEvent.setValue(2);
        }
        dismissLoadingDialog();
    }

    private void showGroupDialog(final List<SonosResponse.Groups> groups) {
        if (groups == null) {
            SmartToast.showShort("没有设备可以选择");
            return;
        }
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle("选择Sonos设备").setCancelString("取消").setSelectPosition(-1);
        Iterator<SonosResponse.Groups> it = groups.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getName());
        }
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda24
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                NavUtils.destination(ActSonosPlayControl.class).withString(Constants.GROUP_ID, ((SonosResponse.Groups) groups.get(((Integer) obj).intValue())).getId()).navigation(ActivityUtils.getTopActivity());
            }
        }).setSelectList(arrayList);
        selectPosition.setOutCancel(false);
        selectPosition.showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    public void queryStatus() {
        ((ObservableSubscribeProxy) Injection.net().getSonosStatus(this.deviceId, Injection.repo().home().getSelPlace().getPlaceId()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda17
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSonosPlayControlVM.this.lambda$queryStatus$10((SonosResponse.PlayStatus) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda18
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSonosPlayControlVM.this.lambda$queryStatus$11((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryStatus$10(SonosResponse.PlayStatus playStatus) throws Exception {
        dismissLoadingDialog();
        if (playStatus != null) {
            boolean equals = playStatus.getPlaybackState().equals("PLAYBACK_STATE_PLAYING");
            if (equals != Boolean.TRUE.equals(this.isPlaying.getValue())) {
                this.isPlaying.setValue(Boolean.valueOf(equals));
            }
            this.playModeEvent.setValue(0);
            if (playStatus.getPlayModes() != null) {
                if ((playStatus.getPlayModes().isRepeat() && playStatus.getPlayModes().isRepeatOne()) || ((playStatus.getPlayModes().isRepeat() && playStatus.getPlayModes().isShuffle()) || (playStatus.getPlayModes().isRepeatOne() && playStatus.getPlayModes().isShuffle()))) {
                    this.playModeEvent.setValue(0);
                } else if (playStatus.getPlayModes().isRepeat()) {
                    this.playModeEvent.setValue(1);
                } else if (playStatus.getPlayModes().isRepeatOne()) {
                    this.playModeEvent.setValue(2);
                } else if (playStatus.getPlayModes().isShuffle()) {
                    this.playModeEvent.setValue(3);
                }
                this.isCrossFade.setValue(Boolean.valueOf(playStatus.getPlayModes().isCrossfade()));
            } else {
                this.playModeEvent.setValue(0);
            }
            if (playStatus.getAvailablePlaybackActions() != null) {
                this.canRepeat.setValue(Boolean.valueOf(playStatus.getAvailablePlaybackActions().isCanRepeat()));
                this.canRepeatOne.setValue(Boolean.valueOf(playStatus.getAvailablePlaybackActions().isCanRepeatOne()));
                this.canShuffle.setValue(Boolean.valueOf(playStatus.getAvailablePlaybackActions().isCanShuffle()));
                this.canCrossfade.setValue(Boolean.valueOf(playStatus.getAvailablePlaybackActions().isCanCrossfade()));
            }
            LHomeLog.e(getClass(), playStatus.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryStatus$11(Throwable th) throws Exception {
        SmartToast.showShort(StringUtils.getString(R.string.search_fail));
        dismissLoadingDialog();
    }

    public void queryVolume() {
        ((ObservableSubscribeProxy) Injection.net().sonosGetVolume(this.deviceId, Injection.repo().home().getSelPlace().getPlaceId()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSonosPlayControlVM.this.lambda$queryVolume$12((SonosResponse.Volume) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSonosPlayControlVM.this.lambda$queryVolume$13((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryVolume$12(SonosResponse.Volume volume) throws Exception {
        dismissLoadingDialog();
        if (volume != null) {
            if (volume.isMuted()) {
                this.volume.setValue(0);
            } else {
                this.volume.setValue(Integer.valueOf(volume.getVolume()));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryVolume$13(Throwable th) throws Exception {
        SmartToast.showShort(StringUtils.getString(R.string.search_fail));
        dismissLoadingDialog();
    }

    public void queryMetadataStatus() {
        ((ObservableSubscribeProxy) Injection.net().sonosGetMetadataStatus(this.deviceId, Injection.repo().home().getSelPlace().getPlaceId()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSonosPlayControlVM.this.lambda$queryMetadataStatus$14((SonosResponse.Metadata) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSonosPlayControlVM.this.lambda$queryMetadataStatus$15((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryMetadataStatus$14(SonosResponse.Metadata metadata) throws Exception {
        dismissLoadingDialog();
        if (metadata != null) {
            this.showMusicData.setValue(metadata);
            if (metadata.getCurrentItem() != null && metadata.getCurrentItem() != null && metadata.getCurrentItem().getTrack() != null) {
                this.songName.setValue(metadata.getCurrentItem().getTrack().getName());
                if (metadata.getCurrentItem().getTrack().getArtist() != null) {
                    this.singerName.setValue(metadata.getCurrentItem().getTrack().getArtist().getName());
                }
            }
            LHomeLog.e(getClass(), metadata.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryMetadataStatus$15(Throwable th) throws Exception {
        SmartToast.showShort(StringUtils.getString(R.string.search_fail));
        dismissLoadingDialog();
    }

    public void setMute(boolean b2) {
        ((ObservableSubscribeProxy) Injection.net().sonosSetMute(this.deviceId, Injection.repo().home().getSelPlace().getPlaceId(), b2).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSonosPlayControlVM.this.lambda$setMute$16((Objects) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda14
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSonosPlayControlVM.this.lambda$setMute$17((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setMute$16(Objects objects) throws Exception {
        dismissLoadingDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setMute$17(Throwable th) throws Exception {
        SmartToast.showShort(StringUtils.getString(R.string.app_str_setting_failed));
        dismissLoadingDialog();
    }

    public void setVolume(int progress) {
        this.volume.setValue(Integer.valueOf(progress));
        ((ObservableSubscribeProxy) Injection.net().sonosSetVolume(this.deviceId, Injection.repo().home().getSelPlace().getPlaceId(), progress).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda27
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSonosPlayControlVM.this.lambda$setVolume$18((Objects) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSonosPlayControlVM.this.lambda$setVolume$19((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setVolume$18(Objects objects) throws Exception {
        dismissLoadingDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setVolume$19(Throwable th) throws Exception {
        SmartToast.showShort(StringUtils.getString(R.string.app_str_setting_failed));
        dismissLoadingDialog();
    }

    public void setMode() {
        boolean z;
        boolean z2;
        boolean z3;
        showLoadingDialog();
        if (this.playModeEvent.getValue() != null) {
            boolean z4 = this.playModeEvent.getValue().intValue() == 1;
            boolean z5 = this.playModeEvent.getValue().intValue() == 2;
            z = z4;
            z3 = this.playModeEvent.getValue().intValue() == 3;
            z2 = z5;
        } else {
            z = false;
            z2 = false;
            z3 = false;
        }
        ((ObservableSubscribeProxy) Injection.net().sonosMode(this.deviceId, Injection.repo().home().getSelPlace().getPlaceId(), z, z2, z3, this.isCrossFade.getValue() != null ? this.isCrossFade.getValue().booleanValue() : false).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda15
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSonosPlayControlVM.this.lambda$setMode$20((Objects) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda16
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSonosPlayControlVM.this.lambda$setMode$21((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setMode$20(Objects objects) throws Exception {
        dismissLoadingDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setMode$21(Throwable th) throws Exception {
        SmartToast.showShort(StringUtils.getString(R.string.app_str_setting_failed));
        dismissLoadingDialog();
    }

    public void remove() {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), StringUtils.getString(R.string.app_str_third_part_remove_auth_verify), StringUtils.getString(R.string.app_str_third_part_remove_auth_verify_tip)).setOkButton(StringUtils.getString(R.string.app_str_sonos_remove_authorized), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda2
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$remove$24;
                lambda$remove$24 = ActSonosPlayControlVM.this.lambda$remove$24(baseDialog, view);
                return lambda$remove$24;
            }
        }).setCancelButton(StringUtils.getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$remove$24(BaseDialog baseDialog, View view) {
        ((ObservableSubscribeProxy) Injection.net().sonosUnbind(Injection.repo().home().getSelPlace().getPlaceId()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSonosPlayControlVM.this.lambda$remove$22((String) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda12
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSonosPlayControlVM.this.lambda$remove$23((Throwable) obj);
            }
        });
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$remove$22(String str) throws Exception {
        loadData();
        finishActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$remove$23(Throwable th) throws Exception {
        SmartToast.showShort(StringUtils.getString(R.string.app_str_operation_failure));
        dismissLoadingDialog();
    }

    private void loadData() {
        SharedPreferenceUtil.edit().keepShared(Constants.DEVICE_CHANGED, true);
        ((ObservableSubscribeProxy) Injection.repo().role().getRoleList(this.placeId, -1L, -1L).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<List<Role>>(this) { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM.1
            @Override // io.reactivex.functions.Consumer
            public void accept(List<Role> roles) throws Exception {
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSonosPlayControlVM.lambda$loadData$25((Throwable) obj);
            }
        });
    }

    public void refreshData(HashMap<String, Object> mapData) {
        boolean equals;
        if (mapData != null) {
            LHomeLog.e(getClass(), "推送的数据=" + mapData.toString());
            String valueString = getValueString(mapData.get("type"));
            valueString.hashCode();
            switch (valueString) {
                case "playbackMetadata":
                    String valueString2 = getValueString(mapData.get("name"));
                    String valueString3 = getValueString(mapData.get("artistName"));
                    this.songName.setValue(valueString2);
                    this.singerName.setValue(valueString3);
                    break;
                case "groupVolume":
                    this.volume.setValue(Integer.valueOf(Integer.parseInt(getValueString(mapData.get("volume")))));
                    if (Boolean.parseBoolean(getValueString(mapData.get("muted")))) {
                        this.volume.setValue(0);
                        break;
                    }
                    break;
                case "playback":
                    String valueString4 = getValueString(mapData.get("playbackState"));
                    if (!valueString4.equals("PLAYBACK_STATE_BUFFERING") && (equals = valueString4.equals("PLAYBACK_STATE_PLAYING")) != Boolean.TRUE.equals(this.isPlaying.getValue())) {
                        this.isPlaying.setValue(Boolean.valueOf(equals));
                    }
                    SonosResponse.PlayStatus.PlayModesBean playModesBean = (SonosResponse.PlayStatus.PlayModesBean) GsonUtils.fromJson(getValueString(mapData.get("playModes")), new TypeToken<SonosResponse.PlayStatus.PlayModesBean>(this) { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM.2
                    }.getType());
                    this.playModeEvent.setValue(0);
                    if (playModesBean != null) {
                        if ((playModesBean.isRepeat() && playModesBean.isRepeatOne()) || ((playModesBean.isRepeat() && playModesBean.isShuffle()) || (playModesBean.isRepeatOne() && playModesBean.isShuffle()))) {
                            this.playModeEvent.setValue(0);
                        } else if (playModesBean.isRepeat()) {
                            this.playModeEvent.setValue(1);
                        } else if (playModesBean.isRepeatOne()) {
                            this.playModeEvent.setValue(2);
                        } else if (playModesBean.isShuffle()) {
                            this.playModeEvent.setValue(3);
                        }
                        this.isCrossFade.setValue(Boolean.valueOf(playModesBean.isCrossfade()));
                    } else {
                        this.playModeEvent.setValue(0);
                    }
                    SonosResponse.PlayStatus.AvailablePlaybackActionsBean availablePlaybackActionsBean = (SonosResponse.PlayStatus.AvailablePlaybackActionsBean) GsonUtils.fromJson(getValueString(mapData.get("availablePlaybackActions")), new TypeToken<SonosResponse.PlayStatus.AvailablePlaybackActionsBean>(this) { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM.3
                    }.getType());
                    if (availablePlaybackActionsBean != null) {
                        this.canRepeat.setValue(Boolean.valueOf(availablePlaybackActionsBean.isCanRepeat()));
                        this.canRepeatOne.setValue(Boolean.valueOf(availablePlaybackActionsBean.isCanRepeatOne()));
                        this.canShuffle.setValue(Boolean.valueOf(availablePlaybackActionsBean.isCanShuffle()));
                        this.canCrossfade.setValue(Boolean.valueOf(availablePlaybackActionsBean.isCanCrossfade()));
                        break;
                    }
                    break;
            }
        }
    }

    private String getValueString(Object object) {
        String valueOf = String.valueOf(object);
        return (valueOf.startsWith("\"") && valueOf.endsWith("\"")) ? valueOf.substring(1, valueOf.length() - 1) : valueOf;
    }

    public void play(long id) {
        ((ObservableSubscribeProxy) Injection.net().sonosPlayFavorites(Injection.repo().home().getSelPlace().getPlaceId(), this.deviceId, id).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).doFinally(new Action(this) { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM.4
            @Override // io.reactivex.functions.Action
            public void run() throws Exception {
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSonosPlayControlVM.lambda$play$26((List) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSonosPlayControlVM.lambda$play$27((Throwable) obj);
            }
        });
    }
}