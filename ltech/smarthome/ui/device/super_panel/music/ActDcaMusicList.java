package com.ltech.smarthome.ui.device.super_panel.music;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActDcaMusicListBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.SongListItemInfo;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicList;
import com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager;
import com.ltech.smarthome.ui.device.super_panel.music.manager.SongInterface;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.view.WaveView;
import com.ltech.smarthome.view.dialog.MusicListDialog;
import com.rich.czlylibary.bean.MusicInfo;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class ActDcaMusicList extends VMActivity<ActDcaMusicListBinding, ActDcaMusicListVM> {
    private BaseQuickAdapter<SongListItemInfo, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_dca_music_list;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().bgRes(R.drawable.shape_white_round_music_bg).emptyStringRes(R.string.app_str_no_song).emptyImageRes(R.mipmap.pic_music_empty_2));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back_white);
        ((ActDcaMusicListBinding) this.mViewBinding).title.grayLine.setVisibility(8);
        ((ActDcaMusicListBinding) this.mViewBinding).title.tvTitle.setTextColor(getResources().getColor(R.color.white));
        ((ActDcaMusicListBinding) this.mViewBinding).title.toolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
        ((ActDcaMusicListVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActDcaMusicListVM) this.mViewModel).controlDevice = Injection.repo().device().getDeviceById(((ActDcaMusicListVM) this.mViewModel).controlId);
        ((ActDcaMusicListVM) this.mViewModel).deviceMac = getIntent().getStringExtra(Constants.MAC_ADDRESS);
        ((ActDcaMusicListVM) this.mViewModel).songId = getIntent().getStringExtra(Constants.MUSIC_SONG_ID);
        ((ActDcaMusicListVM) this.mViewModel).title = getIntent().getStringExtra(Constants.MUSIC_AREA);
        setTitle(((ActDcaMusicListVM) this.mViewModel).title + getString(R.string.music_area));
        ((ActDcaMusicListBinding) this.mViewBinding).ivBgMusicList.setImageResource(((ActDcaMusicListVM) this.mViewModel).getImageResource(((ActDcaMusicListVM) this.mViewModel).title));
        initScroll();
        initRv();
        if (!getString(R.string.music_collect).equals(((ActDcaMusicListVM) this.mViewModel).title)) {
            ((ActDcaMusicListVM) this.mViewModel).getSongList(((ActDcaMusicListVM) this.mViewModel).title);
        }
        if (ActivityUtils.getTopActivity().getString(R.string.music_local_short).equals(((ActDcaMusicListVM) this.mViewModel).title)) {
            ((ActDcaMusicListBinding) this.mViewBinding).line.setVisibility(0);
            ((ActDcaMusicListBinding) this.mViewBinding).iv.setVisibility(0);
        }
    }

    private void initSong() {
        ((ActDcaMusicListVM) this.mViewModel).getSongFromBle();
    }

    /* renamed from: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicList$1, reason: invalid class name */
    class AnonymousClass1 extends BaseQuickAdapter<SongListItemInfo, BaseViewHolder> {
        AnonymousClass1(int layoutResId) {
            super(layoutResId);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(final BaseViewHolder helper, final SongListItemInfo item) {
            WaveView waveView = (WaveView) helper.getView(R.id.ic_playing);
            if (!item.isPlaying()) {
                helper.setText(R.id.tv_number, String.valueOf(((ActDcaMusicListVM) ActDcaMusicList.this.mViewModel).itemInfoList.indexOf(item) + 1));
                waveView.stop();
            } else {
                helper.setText(R.id.tv_number, "");
                waveView.start();
            }
            helper.setText(R.id.tv_main, item.getName());
            helper.setText(R.id.tv_sub, item.getSinger());
            helper.setVisible(R.id.iv_favorite, !((ActDcaMusicListVM) ActDcaMusicList.this.mViewModel).title.equals(ActDcaMusicList.this.getString(R.string.music_local_short)));
            helper.setImageResource(R.id.iv_favorite, item.isCollection() ? R.mipmap.ic_favorite_music : R.mipmap.ic_not_favorite);
            helper.getView(R.id.iv_favorite).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicList$1$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ActDcaMusicList.AnonymousClass1.this.lambda$convert$1(item, helper, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$1(final SongListItemInfo songListItemInfo, final BaseViewHolder baseViewHolder, View view) {
            ((ActDcaMusicListVM) ActDcaMusicList.this.mViewModel).setCollect(songListItemInfo.isCollection, songListItemInfo.songId, new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicList$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActDcaMusicList.AnonymousClass1.this.lambda$convert$0(songListItemInfo, baseViewHolder, (ResponseMsg) obj);
                }
            });
            MiguManager.getInstance().setCollect(songListItemInfo.songId, !songListItemInfo.isCollection(), new SongInterface() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicList.1.1
                @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SongInterface
                public void onFail() {
                }

                @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SongInterface
                public void onSuccess() {
                    if (ActDcaMusicList.this.getString(R.string.music_collect).equals(((ActDcaMusicListVM) ActDcaMusicList.this.mViewModel).title)) {
                        ((ActDcaMusicListVM) ActDcaMusicList.this.mViewModel).itemInfoList.remove(songListItemInfo);
                        ((ActDcaMusicListVM) ActDcaMusicList.this.mViewModel).page = 0;
                        ((ActDcaMusicListVM) ActDcaMusicList.this.mViewModel).getSongList(((ActDcaMusicListVM) ActDcaMusicList.this.mViewModel).title);
                    }
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(SongListItemInfo songListItemInfo, BaseViewHolder baseViewHolder, ResponseMsg responseMsg) {
            if (responseMsg == null || responseMsg.getStateCode() != 0 || ActDcaMusicList.this.getString(R.string.music_collect).equals(((ActDcaMusicListVM) ActDcaMusicList.this.mViewModel).title)) {
                return;
            }
            if (songListItemInfo.isCollection()) {
                ((ActDcaMusicListVM) ActDcaMusicList.this.mViewModel).itemInfoList.get(((ActDcaMusicListVM) ActDcaMusicList.this.mViewModel).itemInfoList.indexOf(songListItemInfo)).setIsCollection("0");
                songListItemInfo.setIsCollection("0");
            } else {
                ((ActDcaMusicListVM) ActDcaMusicList.this.mViewModel).itemInfoList.get(((ActDcaMusicListVM) ActDcaMusicList.this.mViewModel).itemInfoList.indexOf(songListItemInfo)).setIsCollection("1");
                songListItemInfo.setIsCollection("1");
            }
            baseViewHolder.setImageResource(R.id.iv_favorite, songListItemInfo.isCollection() ? R.mipmap.ic_favorite_music : R.mipmap.ic_not_favorite);
        }
    }

    private void initRv() {
        ((ActDcaMusicListBinding) this.mViewBinding).rv.setLayoutManager(new GridLayoutManager(this, 1));
        RecyclerView recyclerView = ((ActDcaMusicListBinding) this.mViewBinding).rv;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(R.layout.item_music_list);
        this.mAdapter = anonymousClass1;
        recyclerView.setAdapter(anonymousClass1);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicList$$ExternalSyntheticLambda1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActDcaMusicList.this.lambda$initRv$0(baseQuickAdapter, view, i);
            }
        });
        this.mAdapter.addFooterView(LayoutInflater.from(this).inflate(R.layout.item_empty_foot, (ViewGroup) null));
        ((ActDcaMusicListBinding) this.mViewBinding).rv.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicList.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView2, int dx, int dy) {
                super.onScrolled(recyclerView2, dx, dy);
                if (recyclerView2.canScrollVertically(1) || ((ActDcaMusicListVM) ActDcaMusicList.this.mViewModel).title.equals(ActDcaMusicList.this.getString(R.string.music_local_short))) {
                    return;
                }
                ((ActDcaMusicListVM) ActDcaMusicList.this.mViewModel).getSongList(((ActDcaMusicListVM) ActDcaMusicList.this.mViewModel).title);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        SongListItemInfo item = this.mAdapter.getItem(i);
        ((ActDcaMusicListVM) this.mViewModel).selectItemPosition = i;
        setSelect(i);
        NavUtils.destination(ActDcaMusicDetail.class).withLong(Constants.CONTROL_ID, ((ActDcaMusicListVM) this.mViewModel).controlId).withBoolean(Constants.MUSIC_NEED_ORDER_MUSIC, true).withString(Constants.MUSIC_SONG_ID, item.getSongId()).withBoolean(Constants.MUSIC_SONG_IS_LOCAL, ActivityUtils.getTopActivity().getString(R.string.music_local_short).equals(((ActDcaMusicListVM) this.mViewModel).title)).withString(Constants.MAC_ADDRESS, ((ActDcaMusicListVM) this.mViewModel).deviceMac).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
        ActivityUtils.getTopActivity().overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
    }

    private void setSelect(int pos) {
        Iterator<SongListItemInfo> it = ((ActDcaMusicListVM) this.mViewModel).itemInfoList.iterator();
        while (it.hasNext()) {
            SongListItemInfo next = it.next();
            next.setPlaying(((ActDcaMusicListVM) this.mViewModel).itemInfoList.get(pos) == next);
        }
        this.mAdapter.notifyDataSetChanged();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActDcaMusicListVM) this.mViewModel).updateDataList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicList$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDcaMusicList.this.lambda$startObserve$1((Boolean) obj);
            }
        });
        super.startObserve();
        ((ActDcaMusicListVM) this.mViewModel).showMusicListClickEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicList$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDcaMusicList.this.lambda$startObserve$3((Void) obj);
            }
        });
        ((ActDcaMusicListVM) this.mViewModel).musicData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicList$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDcaMusicList.this.lambda$startObserve$4((MusicInfo) obj);
            }
        });
        ((ActDcaMusicListVM) this.mViewModel).isPlaying.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicList$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDcaMusicList.this.lambda$startObserve$5((Boolean) obj);
            }
        });
        ((ActDcaMusicListVM) this.mViewModel).refreshIcon.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicList$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDcaMusicList.this.refreshIcon(((Integer) obj).intValue());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Boolean bool) {
        if (((ActDcaMusicListVM) this.mViewModel).itemInfoList.isEmpty()) {
            ((ActDcaMusicListBinding) this.mViewBinding).actAddDeviceScroll.setShowStickyView(false);
            showEmpty();
        } else {
            showContent();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r2) {
        MusicListDialog.asDefault().setDeviceMac(((ActDcaMusicListVM) this.mViewModel).deviceMac).setMusicId(((ActDcaMusicListVM) this.mViewModel).songId).setControlDevice(((ActDcaMusicListVM) this.mViewModel).controlDevice).setCurMode(((ActDcaMusicListVM) this.mViewModel).curMode).setOnDismissCallback(new MusicListDialog.OnDismissCallback() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicList$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.view.dialog.MusicListDialog.OnDismissCallback
            public final void onDismiss(String str) {
                ActDcaMusicList.this.lambda$startObserve$2(str);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(String str) {
        onResume();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(MusicInfo musicInfo) {
        ((ActDcaMusicListBinding) this.mViewBinding).tvSongName.setText(musicInfo.getMusicName());
        ((ActDcaMusicListBinding) this.mViewBinding).tvSinger.setText(musicInfo.getSingerName());
        for (SongListItemInfo songListItemInfo : ((ActDcaMusicListVM) this.mViewModel).itemInfoList) {
            if (songListItemInfo.getSongId().equals(musicInfo.getMusicId())) {
                ((ActDcaMusicListVM) this.mViewModel).selectItemPosition = ((ActDcaMusicListVM) this.mViewModel).itemInfoList.indexOf(songListItemInfo);
                setSelect(((ActDcaMusicListVM) this.mViewModel).itemInfoList.indexOf(songListItemInfo));
            }
        }
        ((ActDcaMusicListBinding) this.mViewBinding).songCirclePic.init();
        playOrPauseMusic();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Boolean bool) {
        ((ActDcaMusicListBinding) this.mViewBinding).songCirclePic.init();
        playOrPauseMusic();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshIcon(int area) {
        if (this.mAdapter.getData().size() > 0) {
            ((ActDcaMusicListBinding) this.mViewBinding).songCirclePic.setImageResource(((ActDcaMusicListVM) this.mViewModel).getIconResource(area));
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (getString(R.string.music_collect).equals(((ActDcaMusicListVM) this.mViewModel).title)) {
            ((ActDcaMusicListVM) this.mViewModel).page = 0;
            ((ActDcaMusicListVM) this.mViewModel).getSongList(((ActDcaMusicListVM) this.mViewModel).title);
        }
        if (getString(R.string.music_local_short).equals(((ActDcaMusicListVM) this.mViewModel).title)) {
            ((ActDcaMusicListVM) this.mViewModel).getSongList(((ActDcaMusicListVM) this.mViewModel).title);
        }
        initSong();
        MessageManager.getInstance().setSmartPanelMusicCallBack(new MessageManager.SmartPanelMusicCallBack() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicList$$ExternalSyntheticLambda0
            @Override // com.smart.message.MessageManager.SmartPanelMusicCallBack
            public final void onDataReceive(ResponseMsg responseMsg) {
                ActDcaMusicList.this.lambda$onResume$6(responseMsg);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResume$6(ResponseMsg responseMsg) {
        int parseInt = Integer.parseInt(responseMsg.getResData().substring(6, 10), 16);
        if ((ActivityUtils.getTopActivity() instanceof ActDcaMusicList) && parseInt == ((BleParam) ((ActDcaMusicListVM) this.mViewModel).controlDevice.getParam(BleParam.class)).getUnicastAddress()) {
            ((ActDcaMusicListVM) this.mViewModel).refreshPanelData(responseMsg);
        }
    }

    private void playOrPauseMusic() {
        if (((ActDcaMusicListVM) this.mViewModel).isPlaying.getValue().booleanValue()) {
            ((ActDcaMusicListBinding) this.mViewBinding).songCirclePic.playMusic();
        } else {
            ((ActDcaMusicListBinding) this.mViewBinding).songCirclePic.pauseMusic();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        super.showEmpty();
        ((ActDcaMusicListBinding) this.mViewBinding).songCirclePic.setImageResource(0);
        ((ActDcaMusicListBinding) this.mViewBinding).tvSongName.setText("");
        ((ActDcaMusicListBinding) this.mViewBinding).tvSinger.setText("");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showContent() {
        super.showContent();
        for (SongListItemInfo songListItemInfo : ((ActDcaMusicListVM) this.mViewModel).itemInfoList) {
            if (songListItemInfo.getSongId().equals(((ActDcaMusicListVM) this.mViewModel).songId)) {
                songListItemInfo.setPlaying(true);
            } else {
                songListItemInfo.setPlaying(false);
            }
        }
        this.mAdapter.setNewData(((ActDcaMusicListVM) this.mViewModel).itemInfoList);
        ((ActDcaMusicListBinding) this.mViewBinding).musicNumber.setText(String.valueOf(((ActDcaMusicListVM) this.mViewModel).itemInfoList.size()));
    }

    private void initScroll() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        int i = displayMetrics.heightPixels;
        TypedArray obtainStyledAttributes = getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int dimension = (int) obtainStyledAttributes.getDimension(0, 0.0f);
        obtainStyledAttributes.recycle();
        ((ActDcaMusicListBinding) this.mViewBinding).rv.setMinimumHeight((i - dimension) - getStatusBarHeight(this));
    }

    private int getStatusBarHeight(Activity activity) {
        int identifier = activity.getResources().getIdentifier("status_bar_height", "dimen", DispatchConstants.ANDROID);
        if (identifier > 0) {
            return activity.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 6100 || data == null) {
            return;
        }
        boolean booleanExtra = data.getBooleanExtra(Constants.MUSIC_FAVORITE_STATE, false);
        SongListItemInfo item = this.mAdapter.getItem(((ActDcaMusicListVM) this.mViewModel).selectItemPosition);
        if (item != null) {
            item.setIsCollection(booleanExtra ? "1" : "0");
            this.mAdapter.notifyItemChanged(((ActDcaMusicListVM) this.mViewModel).selectItemPosition);
        }
    }
}