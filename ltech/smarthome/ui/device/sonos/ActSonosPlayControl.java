package com.ltech.smarthome.ui.device.sonos;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSonosMusicDetailBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.setting.ActSonosSettingDefault;
import com.ltech.smarthome.ui.device.sonos.SonosResponse;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.FastBlurUtils;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.SelectListLeftTitleDialog;
import com.ltech.smarthome.view.dialog.SelectVolumeDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSonosPlayControl extends BaseControlActivity<ActSonosMusicDetailBinding, ActSonosPlayControlVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_sonos_music_detail;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        ((ActSonosPlayControlVM) this.mViewModel).deviceId = getIntent().getLongExtra("device_id", 0L);
        setTitle(getIntent().getStringExtra("device_name"));
        ((ActSonosPlayControlVM) this.mViewModel).queryStatus();
        ((ActSonosPlayControlVM) this.mViewModel).queryVolume();
        ((ActSonosPlayControlVM) this.mViewModel).queryMetadataStatus();
        ((ActSonosMusicDetailBinding) this.mViewBinding).sbCrossFade.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControl.1
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (Boolean.FALSE.equals(((ActSonosPlayControlVM) ActSonosPlayControl.this.mViewModel).canCrossfade.getValue())) {
                    ((ActSonosMusicDetailBinding) ActSonosPlayControl.this.mViewBinding).sbCrossFade.setCheckedNotByUser(!isChecked);
                    SmartToast.showShort(R.string.app_str_sonos_action_no_support);
                } else {
                    ((ActSonosPlayControlVM) ActSonosPlayControl.this.mViewModel).isCrossFade.setValue(Boolean.valueOf(isChecked));
                    ((ActSonosPlayControlVM) ActSonosPlayControl.this.mViewModel).setMode();
                }
            }
        });
        ((ActSonosMusicDetailBinding) this.mViewBinding).line.setVisibility(LanguageUtils.isChinese(this) ? 4 : 0);
        ((ActSonosMusicDetailBinding) this.mViewBinding).line.setVisibility(4);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActSonosPlayControlVM) this.mViewModel).isPlaying.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControl$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSonosPlayControl.this.lambda$startObserve$0((Boolean) obj);
            }
        });
        ((ActSonosPlayControlVM) this.mViewModel).showVolumeClickEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControl.2
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                SelectVolumeDialog.asDefault(false).setVolume(((ActSonosPlayControlVM) ActSonosPlayControl.this.mViewModel).volume.getValue() != null ? ((ActSonosPlayControlVM) ActSonosPlayControl.this.mViewModel).volume.getValue().intValue() : 20).setTitle(ActSonosPlayControl.this.getString(R.string.volume)).setConfirmAction(new IAction<Integer>() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControl.2.1
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(Integer integer) {
                        if (integer.intValue() == 0) {
                            ((ActSonosPlayControlVM) ActSonosPlayControl.this.mViewModel).setMute(true);
                            ((ActSonosPlayControlVM) ActSonosPlayControl.this.mViewModel).setVolume(0);
                        } else {
                            ((ActSonosPlayControlVM) ActSonosPlayControl.this.mViewModel).setMute(false);
                            ((ActSonosPlayControlVM) ActSonosPlayControl.this.mViewModel).setVolume(integer.intValue());
                        }
                    }
                }).showDialog(ActSonosPlayControl.this.activity);
            }
        });
        ((ActSonosPlayControlVM) this.mViewModel).showMusicListClickEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControl.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActSonosPlayControl.this.showPlayList();
            }
        });
        ((ActSonosPlayControlVM) this.mViewModel).playModeEvent.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControl.4
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer integer) {
                int intValue = integer.intValue();
                if (intValue == 0) {
                    ((ActSonosMusicDetailBinding) ActSonosPlayControl.this.mViewBinding).tvModeFull.setText("");
                    return;
                }
                if (intValue == 1) {
                    ((ActSonosMusicDetailBinding) ActSonosPlayControl.this.mViewBinding).tvModeFull.setText(ActSonosPlayControl.this.getString(R.string.music_playback));
                } else if (intValue == 2) {
                    ((ActSonosMusicDetailBinding) ActSonosPlayControl.this.mViewBinding).tvModeFull.setText(ActSonosPlayControl.this.getString(R.string.music_play_cycle_one));
                } else {
                    if (intValue != 3) {
                        return;
                    }
                    ((ActSonosMusicDetailBinding) ActSonosPlayControl.this.mViewBinding).tvModeFull.setText(ActSonosPlayControl.this.getString(R.string.music_play_random));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Boolean bool) {
        ((ActSonosMusicDetailBinding) this.mViewBinding).ivMusicCd.init();
        playOrPauseMusic();
    }

    private void playOrPauseMusic() {
        if (Boolean.TRUE.equals(((ActSonosPlayControlVM) this.mViewModel).isPlaying.getValue())) {
            ((ActSonosMusicDetailBinding) this.mViewBinding).ivMusicCd.playMusic();
        } else {
            ((ActSonosMusicDetailBinding) this.mViewBinding).ivMusicCd.pauseMusic();
        }
    }

    private void setBlurBackground(Bitmap bitmap) {
        Glide.with((FragmentActivity) this).asBitmap().load(bitmap).into((RequestBuilder<Bitmap>) new CustomTarget<Bitmap>() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControl.5
            @Override // com.bumptech.glide.request.target.Target
            public void onLoadCleared(Drawable placeholder) {
            }

            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(Object resource, Transition transition) {
                onResourceReady((Bitmap) resource, (Transition<? super Bitmap>) transition);
            }

            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                ((ActSonosMusicDetailBinding) ActSonosPlayControl.this.mViewBinding).layoutMusicDetail.setBackground(new BitmapDrawable(ActSonosPlayControl.this.getResources(), FastBlurUtils.startBlurBackground(resource)));
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavUtils.destination(ActSonosSettingDefault.class).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withLong("device_id", ((ActSonosPlayControlVM) this.mViewModel).deviceId).withBoolean("canCrossfade", Boolean.TRUE.equals(((ActSonosPlayControlVM) this.mViewModel).canCrossfade.getValue())).withBoolean("isCrossFade", Boolean.TRUE.equals(((ActSonosPlayControlVM) this.mViewModel).isCrossFade.getValue())).withInt("playMode", ((ActSonosPlayControlVM) this.mViewModel).playModeEvent.getValue() != null ? ((ActSonosPlayControlVM) this.mViewModel).playModeEvent.getValue().intValue() : 0).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
    }

    public void refreshData(HashMap<String, Object> mapData) {
        try {
            ((ActSonosPlayControlVM) this.mViewModel).refreshData(mapData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showPlayList() {
        showLoadingDialog("");
        ((ObservableSubscribeProxy) Injection.net().sonosFavorites(Injection.repo().home().getSelPlace().getPlaceId()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControl.6
            @Override // io.reactivex.functions.Action
            public void run() throws Exception {
                ActSonosPlayControl.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControl$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSonosPlayControl.this.lambda$showPlayList$1((List) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControl$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSonosPlayControl.this.lambda$showPlayList$2((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPlayList$1(final List list) throws Exception {
        dismissLoadingDialog();
        if (list != null && !list.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                SonosResponse.Favorites favorites = (SonosResponse.Favorites) it.next();
                arrayList.add(favorites.getName());
                arrayList2.add(favorites.getDescription());
            }
            SelectListLeftTitleDialog.asDefault(false).setTitle(getString(R.string.play_list2)).setSelectList(arrayList, arrayList2).setSelectPosition(-1).setConfirmAction(new IAction<Integer>() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosPlayControl.7
                @Override // com.ltech.smarthome.base.IAction
                public void act(Integer integer) {
                    if (integer.intValue() < list.size()) {
                        ((ActSonosPlayControlVM) ActSonosPlayControl.this.mViewModel).play(((SonosResponse.Favorites) list.get(integer.intValue())).getId());
                    }
                }
            }).showDialog(this.activity);
            return;
        }
        SmartToast.showShort(getString(R.string.no_collect_song));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPlayList$2(Throwable th) throws Exception {
        SmartToast.showShort(getString(R.string.no_collect_song));
        dismissLoadingDialog();
    }
}