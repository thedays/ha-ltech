package com.ltech.smarthome.ui.device.super_panel.music;

import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActDcaMusicHomeBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.SuperPanelBleParam;
import com.ltech.smarthome.ui.item.MusicItem;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.WaveView;
import com.ltech.smarthome.view.dialog.MusicListDialog;
import com.ltech.smarthome.view.dialog.MusicVipTipDialog;
import com.rich.czlylibary.bean.MusicInfo;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActDcaMusicHome extends VMActivity<ActDcaMusicHomeBinding, ActDcaMusicHomeVM> {
    private MultipleItemRvAdapter<RvItem, BaseViewHolder> mAdapter;
    private List<RvItem> list = new ArrayList();
    private boolean isVip = false;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_dca_music_home;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.music));
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_music_vip);
        ((ActDcaMusicHomeBinding) this.mViewBinding).title.grayLine.setVisibility(8);
        ((ActDcaMusicHomeVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActDcaMusicHomeVM) this.mViewModel).isOnline = getIntent().getBooleanExtra(Constants.DEVICE_IS_ONLINE, false);
        ((ActDcaMusicHomeVM) this.mViewModel).controlDevice = Injection.repo().device().getDeviceById(((ActDcaMusicHomeVM) this.mViewModel).controlId);
        ((ActDcaMusicHomeVM) this.mViewModel).deviceMac = getIntent().getStringExtra(Constants.MAC_ADDRESS);
        ((ActDcaMusicHomeBinding) this.mViewBinding).songCirclePic.setImageResource(R.mipmap.ic_music_local);
        if (((ActDcaMusicHomeVM) this.mViewModel).controlDevice != null) {
            SuperPanelBleParam superPanelBleParam = (SuperPanelBleParam) ((ActDcaMusicHomeVM) this.mViewModel).controlDevice.getParam(SuperPanelBleParam.class);
            if (superPanelBleParam.getMusicDeadline() != null && !superPanelBleParam.getMusicDeadline().isEmpty()) {
                this.isVip = isDuringPayment(superPanelBleParam.getMusicDeadline());
            }
        }
        initList();
        initRv();
        initSong();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActDcaMusicHomeVM) this.mViewModel).showMusicListClickEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHome$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDcaMusicHome.this.lambda$startObserve$1((Void) obj);
            }
        });
        ((ActDcaMusicHomeVM) this.mViewModel).musicData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHome$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDcaMusicHome.this.lambda$startObserve$2((MusicInfo) obj);
            }
        });
        ((ActDcaMusicHomeVM) this.mViewModel).isPlaying.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHome$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDcaMusicHome.this.lambda$startObserve$3((Boolean) obj);
            }
        });
        ((ActDcaMusicHomeVM) this.mViewModel).refreshIcon.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHome$$ExternalSyntheticLambda9
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDcaMusicHome.this.lambda$startObserve$4((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r2) {
        MusicListDialog.asDefault().setDeviceMac(((ActDcaMusicHomeVM) this.mViewModel).deviceMac).setMusicId(((ActDcaMusicHomeVM) this.mViewModel).songId).setCurMode(((ActDcaMusicHomeVM) this.mViewModel).curMode).setControlDevice(((ActDcaMusicHomeVM) this.mViewModel).controlDevice).setOnDismissCallback(new MusicListDialog.OnDismissCallback() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHome$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.view.dialog.MusicListDialog.OnDismissCallback
            public final void onDismiss(String str) {
                ActDcaMusicHome.this.lambda$startObserve$0(str);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(String str) {
        onResume();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(MusicInfo musicInfo) {
        ((ActDcaMusicHomeBinding) this.mViewBinding).tvSongName.setText(musicInfo.getMusicName());
        ((ActDcaMusicHomeBinding) this.mViewBinding).tvSinger.setText(musicInfo.getSingerName());
        ((ActDcaMusicHomeBinding) this.mViewBinding).songCirclePic.init();
        playOrPauseMusic();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Boolean bool) {
        ((ActDcaMusicHomeBinding) this.mViewBinding).songCirclePic.init();
        playOrPauseMusic();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Integer num) {
        refreshIcon(num.intValue());
        setPlayState(num.intValue());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        showMusicVipTipDialog();
    }

    private void showMusicVipTipDialog() {
        MusicVipTipDialog.asDefault().showDialog(this);
    }

    private void setPlayState(int area) {
        int listIdByAreaId = ((ActDcaMusicHomeVM) this.mViewModel).getListIdByAreaId(area);
        if (area == 1) {
            setSelect(listIdByAreaId, true);
        } else if (area == 3) {
            setSelect(listIdByAreaId, true);
        } else {
            setSelect(listIdByAreaId);
        }
    }

    private void refreshIcon(int area) {
        ((ActDcaMusicHomeBinding) this.mViewBinding).songCirclePic.setImageResource(((ActDcaMusicHomeVM) this.mViewModel).getIconResource(area));
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((ActDcaMusicHomeVM) this.mViewModel).getSongFromBle();
        MessageManager.getInstance().setSmartPanelMusicCallBack(new MessageManager.SmartPanelMusicCallBack() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHome$$ExternalSyntheticLambda0
            @Override // com.smart.message.MessageManager.SmartPanelMusicCallBack
            public final void onDataReceive(ResponseMsg responseMsg) {
                ActDcaMusicHome.this.lambda$onResume$5(responseMsg);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResume$5(ResponseMsg responseMsg) {
        int parseInt = Integer.parseInt(responseMsg.getResData().substring(6, 10), 16);
        if ((ActivityUtils.getTopActivity() instanceof ActDcaMusicHome) && parseInt == ((BleParam) ((ActDcaMusicHomeVM) this.mViewModel).controlDevice.getParam(BleParam.class)).getUnicastAddress()) {
            ((ActDcaMusicHomeVM) this.mViewModel).refreshPanelData(responseMsg);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
    }

    private void playOrPauseMusic() {
        if (((ActDcaMusicHomeVM) this.mViewModel).isPlaying.getValue().booleanValue()) {
            ((ActDcaMusicHomeBinding) this.mViewBinding).songCirclePic.playMusic();
        } else {
            ((ActDcaMusicHomeBinding) this.mViewBinding).songCirclePic.pauseMusic();
        }
    }

    private void initSong() {
        ((ActDcaMusicHomeVM) this.mViewModel).initSong();
    }

    private void initList() {
        RvItem rvItem = new RvItem(2);
        rvItem.musicItem.setMainText(getString(R.string.music_local_short));
        rvItem.musicItem.setSubText(getString(R.string.music_morning));
        this.list.add(rvItem);
        RvItem rvItem2 = new RvItem(3);
        rvItem2.musicItem.setMainText(getString(R.string.music_recommend_short));
        rvItem2.musicItem.setSubText(getString(R.string.music_collect));
        this.list.add(rvItem2);
        this.list.add(RvItem.contentNormal(new MusicItem().setMainText(getString(R.string.music_sleep)).setImageRes(R.mipmap.ic_music_sleep)));
        this.list.add(RvItem.contentNormal(new MusicItem().setMainText(getString(R.string.music_sport)).setImageRes(R.mipmap.ic_music_sport)));
        this.list.add(RvItem.contentNormal(new MusicItem().setMainText(getString(R.string.music_meditation)).setImageRes(R.mipmap.ic_music_meditation)));
        this.list.add(RvItem.contentNormal(new MusicItem().setMainText(getString(R.string.music_relax)).setImageRes(R.mipmap.ic_music_relax)));
        this.list.add(RvItem.contentNormal(new MusicItem().setMainText(getString(R.string.music_focus)).setImageRes(R.mipmap.ic_music_focus)));
        this.list.add(RvItem.contentNormal(new MusicItem().setMainText(getString(R.string.music_classic)).setImageRes(R.mipmap.ic_music_classic)));
        this.list.add(RvItem.title(new MusicItem().setMainText(getString(R.string.music_more))));
        this.list.add(RvItem.contentSmall(new MusicItem().setMainText(getString(R.string.music_new)).setImageRes(R.mipmap.ic_music_new)));
        this.list.add(RvItem.contentSmall(new MusicItem().setMainText(getString(R.string.music_show)).setImageRes(R.mipmap.ic_music_show)));
        this.list.add(RvItem.contentSmall(new MusicItem().setMainText(getString(R.string.music_blue)).setImageRes(R.mipmap.ic_music_blue)));
        this.list.add(RvItem.contentNormal(new MusicItem().setMainText(getString(R.string.music_inspirational)).setImageRes(R.mipmap.ic_music_inspirational)));
        this.list.add(RvItem.contentNormal(new MusicItem().setMainText(getString(R.string.music_yoga)).setImageRes(R.mipmap.ic_music_yoga)));
        this.list.add(RvItem.contentSmall(new MusicItem().setMainText(getString(R.string.music_slow)).setImageRes(R.mipmap.ic_music_slow)));
        this.list.add(RvItem.contentSmall(new MusicItem().setMainText(getString(R.string.music_cofe)).setImageRes(R.mipmap.ic_music_cofe)));
        this.list.add(RvItem.contentSmall(new MusicItem().setMainText(getString(R.string.music_date)).setImageRes(R.mipmap.ic_music_date)));
        this.list.add(RvItem.tail(new MusicItem().setMainText(getString(R.string.music_end))));
    }

    private void initRv() {
        MultipleItemRvAdapter<RvItem, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<RvItem, BaseViewHolder>(this.list) { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHome.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(RvItem content) {
                return content.type;
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new BaseItemProvider<RvItem, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHome.1.1
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_big_content_left;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 2;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, RvItem item, int position) {
                        ActDcaMusicHome.this.setLeftRightCommon(helper, item, position);
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<RvItem, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHome.1.2
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_big_content_right;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 3;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, RvItem item, int position) {
                        ActDcaMusicHome.this.setLeftRightCommon(helper, item, position);
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<RvItem, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHome.1.3
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_content_normal;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 4;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, RvItem item, int position) {
                        ActDcaMusicHome.this.setNormalSmallCommon(helper, item, position);
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<RvItem, BaseViewHolder>(this) { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHome.1.4
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_title;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 1;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, RvItem item, int position) {
                        helper.setText(R.id.tv_main, item.musicItem.getMainText());
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<RvItem, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHome.1.5
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_content_small;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 5;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, RvItem item, int position) {
                        ActDcaMusicHome.this.setNormalSmallCommon(helper, item, position);
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<RvItem, BaseViewHolder>(this) { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHome.1.6
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_tail;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 6;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, RvItem item, int position) {
                        helper.setText(R.id.tv_main, item.musicItem.getMainText());
                    }
                });
            }
        };
        this.mAdapter = multipleItemRvAdapter;
        multipleItemRvAdapter.finishInitialize();
        this.mAdapter.bindToRecyclerView(((ActDcaMusicHomeBinding) this.mViewBinding).rv);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHome.2
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int position) {
                int i = ((RvItem) ActDcaMusicHome.this.mAdapter.getData().get(position)).type;
                if (i == 2 || i == 3 || i == 4) {
                    return gridLayoutManager.getSpanCount() / 2;
                }
                if (i == 5) {
                    return gridLayoutManager.getSpanCount() / 3;
                }
                return gridLayoutManager.getSpanCount();
            }
        });
        ((ActDcaMusicHomeBinding) this.mViewBinding).rv.setLayoutManager(gridLayoutManager);
        ((ActDcaMusicHomeBinding) this.mViewBinding).rv.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setNormalSmallCommon(BaseViewHolder helper, final RvItem item, final int position) {
        helper.setText(R.id.tv_main, item.musicItem.getMainText());
        helper.setImageResource(R.id.iv_logo, item.musicItem.getImageRes());
        WaveView waveView = (WaveView) helper.getView(R.id.ic_playing);
        helper.getView(R.id.iv_logo).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHome$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActDcaMusicHome.this.lambda$setNormalSmallCommon$6(position, item, view);
            }
        });
        if (!item.musicItem.isSelect()) {
            waveView.stop();
            helper.setVisible(R.id.iv_play, true);
        } else {
            helper.setGone(R.id.iv_play, false);
            waveView.start();
        }
        helper.getView(R.id.layout_normal_or_small).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHome$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActDcaMusicHome.this.lambda$setNormalSmallCommon$7(item, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setNormalSmallCommon$6(int i, RvItem rvItem, View view) {
        if (((ActDcaMusicHomeVM) this.mViewModel).isOnline) {
            if (this.isVip) {
                setSelect(i);
                ((ActDcaMusicHomeVM) this.mViewModel).playMusicType(rvItem.musicItem.getMainText());
                return;
            } else {
                showMusicVipTipDialog();
                return;
            }
        }
        SmartToast.showCenterShort(getString(R.string.device_offline));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setNormalSmallCommon$7(RvItem rvItem, View view) {
        navMusicList(rvItem.musicItem.getMainText());
    }

    private void navMusicList(String musicArea) {
        if (((ActDcaMusicHomeVM) this.mViewModel).isOnline) {
            if (this.isVip || musicArea.equals(getString(R.string.music_local_short))) {
                NavUtils.destination(ActDcaMusicList.class).withString(Constants.MUSIC_AREA, musicArea).withLong(Constants.CONTROL_ID, ((ActDcaMusicHomeVM) this.mViewModel).controlId).withString(Constants.MUSIC_SONG_ID, ((ActDcaMusicHomeVM) this.mViewModel).songId).withString(Constants.MAC_ADDRESS, ((ActDcaMusicHomeVM) this.mViewModel).deviceMac).navigation(this);
                return;
            } else {
                showMusicVipTipDialog();
                return;
            }
        }
        SmartToast.showCenterShort(getString(R.string.device_offline));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLeftRightCommon(final BaseViewHolder helper, final RvItem item, final int position) {
        helper.getView(R.id.layout_main).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHome$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActDcaMusicHome.this.lambda$setLeftRightCommon$8(item, view);
            }
        });
        helper.getView(R.id.layout_sub).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHome$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActDcaMusicHome.this.lambda$setLeftRightCommon$9(item, view);
            }
        });
        final WaveView waveView = (WaveView) helper.getView(R.id.ic_playing_main);
        final WaveView waveView2 = (WaveView) helper.getView(R.id.ic_playing_sub);
        helper.getView(R.id.iv_main).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHome$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActDcaMusicHome.this.lambda$setLeftRightCommon$10(item, position, helper, waveView, waveView2, view);
            }
        });
        helper.getView(R.id.iv_sub).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHome$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActDcaMusicHome.this.lambda$setLeftRightCommon$11(position, helper, waveView, waveView2, item, view);
            }
        });
        if (!item.musicItem.isSelect()) {
            helper.setVisible(R.id.iv_sub_play, true);
            waveView.stop();
            waveView2.stop();
            helper.setVisible(R.id.iv_play, true);
            return;
        }
        if (item.musicItem.isSelect() && item.musicItem.isMainArea()) {
            helper.setGone(R.id.iv_play, false);
            waveView.start();
            waveView2.stop();
            helper.setVisible(R.id.iv_sub_play, true);
            return;
        }
        if (!item.musicItem.isSelect() || item.musicItem.isMainArea()) {
            return;
        }
        helper.setGone(R.id.iv_sub_play, false);
        waveView.stop();
        waveView2.start();
        helper.setVisible(R.id.iv_play, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setLeftRightCommon$8(RvItem rvItem, View view) {
        navMusicList(rvItem.musicItem.getMainText());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setLeftRightCommon$9(RvItem rvItem, View view) {
        navMusicList(rvItem.musicItem.getSubText());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setLeftRightCommon$10(RvItem rvItem, int i, BaseViewHolder baseViewHolder, WaveView waveView, WaveView waveView2, View view) {
        if (((ActDcaMusicHomeVM) this.mViewModel).isOnline) {
            if (this.isVip || rvItem.musicItem.getMainText().equals(getString(R.string.music_local_short))) {
                setSelect(i);
                baseViewHolder.setGone(R.id.iv_play, false);
                waveView.start();
                waveView2.stop();
                baseViewHolder.setVisible(R.id.iv_sub_play, true);
                rvItem.musicItem.setMainArea(true);
                ((ActDcaMusicHomeVM) this.mViewModel).playMusicType(rvItem.musicItem.getMainText());
                return;
            }
            showMusicVipTipDialog();
            return;
        }
        SmartToast.showCenterShort(getString(R.string.device_offline));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setLeftRightCommon$11(int i, BaseViewHolder baseViewHolder, WaveView waveView, WaveView waveView2, RvItem rvItem, View view) {
        if (((ActDcaMusicHomeVM) this.mViewModel).isOnline) {
            if (this.isVip) {
                setSelect(i);
                baseViewHolder.setGone(R.id.iv_sub_play, false);
                waveView.stop();
                waveView2.start();
                baseViewHolder.setVisible(R.id.iv_play, true);
                ((ActDcaMusicHomeVM) this.mViewModel).playMusicType(rvItem.musicItem.getSubText());
                return;
            }
            showMusicVipTipDialog();
            return;
        }
        SmartToast.showCenterShort(getString(R.string.device_offline));
    }

    private void setSelect(int pos) {
        for (RvItem rvItem : this.list) {
            boolean z = false;
            rvItem.musicItem.setMainArea(false);
            MusicItem musicItem = rvItem.musicItem;
            if (this.list.get(pos) == rvItem) {
                z = true;
            }
            musicItem.setSelect(z);
        }
        this.mAdapter.notifyDataSetChanged();
    }

    private void setSelect(int pos, boolean isMainArea) {
        Iterator<RvItem> it = this.list.iterator();
        while (it.hasNext()) {
            RvItem next = it.next();
            if (this.list.get(pos) == next) {
                next.musicItem.setMainArea(isMainArea);
            }
            next.musicItem.setSelect(this.list.get(pos) == next);
        }
        this.mAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class RvItem {
        public static final int TYPE_BIG_CONTENT_LEFT = 2;
        public static final int TYPE_BIG_CONTENT_RIGHT = 3;
        public static final int TYPE_CONTENT_NORMAL = 4;
        public static final int TYPE_CONTENT_SMALL = 5;
        public static final int TYPE_TAIL = 6;
        public static final int TYPE_TITLE = 1;
        public MusicItem musicItem;
        public int type;

        private RvItem(int type) {
            this.musicItem = new MusicItem();
            this.type = type;
        }

        public static RvItem title(MusicItem musicItem) {
            RvItem rvItem = new RvItem(1);
            rvItem.musicItem = musicItem;
            return rvItem;
        }

        public static RvItem tail(MusicItem musicItem) {
            RvItem rvItem = new RvItem(6);
            rvItem.musicItem = musicItem;
            return rvItem;
        }

        public static RvItem contentNormal(MusicItem musicItem) {
            RvItem rvItem = new RvItem(4);
            rvItem.musicItem = musicItem;
            return rvItem;
        }

        public static RvItem contentSmall(MusicItem musicItem) {
            RvItem rvItem = new RvItem(5);
            rvItem.musicItem = musicItem;
            return rvItem;
        }
    }

    private boolean isDuringPayment(String time) {
        try {
            return new SimpleDateFormat("yyyyMMddhhmmss").parse(time).getTime() - System.currentTimeMillis() > 0;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}