package com.ltech.smarthome.view.dialog;

import android.content.DialogInterface;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.databinding.DialogMusicListBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.MusicInfo;
import com.ltech.smarthome.model.bean.SongListItemInfo;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.music.MusicListResponse;
import com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.WaveView;
import com.ltech.smarthome.view.dialog.MusicListDialog;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.StringUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class MusicListDialog extends SmartDialog<DialogMusicListBinding> {
    private Device controlDevice;
    private String deviceMac;
    private ItemTouchHelper itemTouchHelper;
    private BaseItemDraggableAdapter<MusicInfo, BaseViewHolder> mAdapter;
    private int mode;
    private String musicId;
    private OnDismissCallback onDismissCallback;
    private OnMusicChangeCallback onMusicChangeCallback;
    private List<MusicInfo> musicInfoList = new ArrayList();
    private boolean canSort = true;

    public interface OnDismissCallback {
        void onDismiss(String musicId);
    }

    public interface OnMusicChangeCallback {
        void onChange(ResponseMsg msg, String musicId);
    }

    private int getModeResource(int mode) {
        return mode != 1 ? mode != 2 ? mode != 3 ? R.mipmap.ic_music_detail_playback_gray : R.mipmap.ic_music_detail_play_once_gray : R.mipmap.ic_music_detail_random_gray : R.mipmap.ic_music_detail_cycle_gray;
    }

    static /* synthetic */ void lambda$controlMode$5(ResponseMsg responseMsg) {
    }

    static /* synthetic */ void lambda$orderMusic$3(ResponseMsg responseMsg) {
    }

    static /* synthetic */ void lambda$updateList$7(ResponseMsg responseMsg) {
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_music_list;
    }

    public static MusicListDialog asDefault() {
        return (MusicListDialog) new MusicListDialog().setViewConverter(new SmartDialog.ViewConverter<DialogMusicListBinding, MusicListDialog>() { // from class: com.ltech.smarthome.view.dialog.MusicListDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(DialogMusicListBinding viewBinding, MusicListDialog dialog) {
                dialog.initRv(viewBinding);
                dialog.initView(viewBinding);
            }
        }).setMargin(16).setY(16).setGravity(80);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initView(final DialogMusicListBinding viewBinding) {
        setIconAndTestByMode(viewBinding, this.mode);
        viewBinding.ivMode.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.view.dialog.MusicListDialog$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MusicListDialog.this.lambda$initView$0(viewBinding, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(DialogMusicListBinding dialogMusicListBinding, View view) {
        int i = this.mode + 1;
        this.mode = i;
        if (i > 3) {
            this.mode = 0;
        }
        setIconAndTestByMode(dialogMusicListBinding, this.mode);
        controlMode(5, this.mode);
    }

    private void setIconAndTestByMode(DialogMusicListBinding viewBinding, int mode) {
        viewBinding.ivMode.setImageDrawable(getResources().getDrawable(getModeResource(mode)));
        viewBinding.tvMode.setText(getTextResource(mode));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRv(final DialogMusicListBinding viewBinding) {
        viewBinding.rvMusic.setLayoutManager(new GridLayoutManager(getContext(), 1));
        RecyclerView recyclerView = viewBinding.rvMusic;
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(R.layout.item_music_dialog_list, new ArrayList(), viewBinding);
        this.mAdapter = anonymousClass2;
        recyclerView.setAdapter(anonymousClass2);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.MusicListDialog$$ExternalSyntheticLambda1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                MusicListDialog.this.lambda$initRv$1(baseQuickAdapter, view, i);
            }
        });
        MessageManager.getInstance().setSmartPanelMusicCallBack(new MessageManager.SmartPanelMusicCallBack() { // from class: com.ltech.smarthome.view.dialog.MusicListDialog$$ExternalSyntheticLambda2
            @Override // com.smart.message.MessageManager.SmartPanelMusicCallBack
            public final void onDataReceive(ResponseMsg responseMsg) {
                MusicListDialog.this.lambda$initRv$2(viewBinding, responseMsg);
            }
        });
        getSong(viewBinding);
    }

    /* renamed from: com.ltech.smarthome.view.dialog.MusicListDialog$2, reason: invalid class name */
    class AnonymousClass2 extends BaseItemDraggableAdapter<MusicInfo, BaseViewHolder> {
        final /* synthetic */ DialogMusicListBinding val$viewBinding;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(int layoutResId, List data, final DialogMusicListBinding val$viewBinding) {
            super(layoutResId, data);
            this.val$viewBinding = val$viewBinding;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(BaseViewHolder helper, final MusicInfo item) {
            WaveView waveView = (WaveView) helper.getView(R.id.ic_playing);
            if (item.getMusicid().equals(MusicListDialog.this.musicId)) {
                helper.setText(R.id.tv_number, "");
                helper.setVisible(R.id.ic_playing, true);
                helper.setVisible(R.id.iv_favorite, false);
                waveView.start();
            } else {
                helper.setVisible(R.id.ic_playing, false);
                helper.setText(R.id.tv_number, String.valueOf(MusicListDialog.this.musicInfoList.indexOf(item) + 1));
                helper.setVisible(R.id.iv_favorite, true);
                waveView.stop();
            }
            helper.setGone(R.id.layout_sort_icon, MusicListDialog.this.canSort);
            helper.setText(R.id.tv_main, item.getMusicname());
            if (item.getSingername().isEmpty()) {
                helper.setText(R.id.tv_sub, R.string.unknown_singer);
            } else {
                helper.setText(R.id.tv_sub, item.getSingername());
            }
            View view = helper.getView(R.id.layout_icon);
            final DialogMusicListBinding dialogMusicListBinding = this.val$viewBinding;
            view.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.view.dialog.MusicListDialog$2$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MusicListDialog.AnonymousClass2.this.lambda$convert$0(item, dialogMusicListBinding, view2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(MusicInfo musicInfo, DialogMusicListBinding dialogMusicListBinding, View view) {
            MusicListDialog musicListDialog = MusicListDialog.this;
            musicListDialog.controlMode(9, musicListDialog.musicInfoList.indexOf(musicInfo));
            MusicListDialog.this.musicInfoList.remove(musicInfo);
            MusicListDialog.this.updateList(dialogMusicListBinding);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$1(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        MusicInfo musicInfo = this.musicInfoList.get(i);
        if (musicInfo.getMusicid().equals(this.musicId)) {
            return;
        }
        orderMusic(musicInfo.getMusicid());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$2(DialogMusicListBinding dialogMusicListBinding, ResponseMsg responseMsg) {
        int parseInt = Integer.parseInt(responseMsg.getResData().substring(6, 10), 16);
        if (dialogMusicListBinding == null || parseInt != ((BleParam) this.controlDevice.getParam(BleParam.class)).getUnicastAddress()) {
            return;
        }
        refreshPanelData(responseMsg);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableDrag() {
        if (this.itemTouchHelper == null) {
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemDragAndSwipeCallback(this.mAdapter));
            this.itemTouchHelper = itemTouchHelper;
            itemTouchHelper.attachToRecyclerView(((DialogMusicListBinding) this.mViewBinding).rvMusic);
        }
        this.mAdapter.enableDragItem(this.itemTouchHelper);
        this.mAdapter.setOnItemDragListener(new OnItemDragListener() { // from class: com.ltech.smarthome.view.dialog.MusicListDialog.3
            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragMoving(RecyclerView.ViewHolder viewHolder, int i, RecyclerView.ViewHolder viewHolder1, int i1) {
            }

            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int i) {
            }

            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int p) {
                MusicListDialog musicListDialog = MusicListDialog.this;
                musicListDialog.musicInfoList = musicListDialog.mAdapter.getData();
                MusicListDialog.this.updateList();
            }
        });
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        OnDismissCallback onDismissCallback = this.onDismissCallback;
        if (onDismissCallback != null) {
            onDismissCallback.onDismiss(this.musicId);
        }
    }

    private void orderMusic(String musicId) {
        CmdAssistant.getMusicAssistant(this.controlDevice, new int[0]).orderMusic(ActivityUtils.getTopActivity(), musicId, 0, new IAction() { // from class: com.ltech.smarthome.view.dialog.MusicListDialog$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                MusicListDialog.lambda$orderMusic$3((ResponseMsg) obj);
            }
        });
    }

    private String getTextResource(int mode) {
        if (mode == 1) {
            return getString(R.string.music_play_cycle_one);
        }
        if (mode == 2) {
            return getString(R.string.music_play_random);
        }
        if (mode == 3) {
            return getString(R.string.music_play_once);
        }
        return getString(R.string.music_playback);
    }

    public MusicListDialog setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
        return this;
    }

    public MusicListDialog setCurMode(int mode) {
        this.mode = mode;
        return this;
    }

    public MusicListDialog setControlDevice(Device device) {
        this.controlDevice = device;
        return this;
    }

    public MusicListDialog setMusicId(String musicId) {
        this.musicId = musicId;
        return this;
    }

    public MusicListDialog setOnDismissCallback(OnDismissCallback onDismissCallback) {
        this.onDismissCallback = onDismissCallback;
        return this;
    }

    public MusicListDialog setOnMusicChangeCallback(OnMusicChangeCallback onMusicChangeCallback) {
        this.onMusicChangeCallback = onMusicChangeCallback;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "music_time_dialog";
    }

    private void getSong(final DialogMusicListBinding viewBinding) {
        ((ObservableSubscribeProxy) Injection.net().getMusicInfo(this.deviceMac).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.view.dialog.MusicListDialog$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicListDialog.this.lambda$getSong$4(viewBinding, (MusicListResponse) obj);
            }
        }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.view.dialog.MusicListDialog.4
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                MiguManager.getInstance().getLocalMusicList(MusicListDialog.this.deviceMac, new IAction<List<SongListItemInfo>>() { // from class: com.ltech.smarthome.view.dialog.MusicListDialog.4.1
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(List<SongListItemInfo> songListItemInfos) {
                        MusicListDialog.this.musicInfoList = MusicListDialog.this.getLocalSongList(songListItemInfos);
                        viewBinding.tvNumber.setText("(" + MusicListDialog.this.musicInfoList.size() + ")");
                        MusicListDialog.this.canSort = true;
                        MusicListDialog.this.enableDrag();
                        MusicListDialog.this.mAdapter.setNewData(MusicListDialog.this.musicInfoList);
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSong$4(final DialogMusicListBinding dialogMusicListBinding, MusicListResponse musicListResponse) throws Exception {
        if (!musicListResponse.getRows().isEmpty()) {
            this.musicInfoList = musicListResponse.getRows();
            dialogMusicListBinding.tvNumber.setText("(" + musicListResponse.getTotal() + ")");
            Iterator<MusicInfo> it = this.musicInfoList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (it.next().getIslocal().equals("0")) {
                    this.canSort = false;
                    break;
                }
            }
            if (this.canSort) {
                enableDrag();
            }
            this.mAdapter.setNewData(this.musicInfoList);
            return;
        }
        MiguManager.getInstance().getLocalMusicList(this.deviceMac, new IAction<List<SongListItemInfo>>() { // from class: com.ltech.smarthome.view.dialog.MusicListDialog.5
            @Override // com.ltech.smarthome.base.IAction
            public void act(List<SongListItemInfo> songListItemInfos) {
                MusicListDialog musicListDialog = MusicListDialog.this;
                musicListDialog.musicInfoList = musicListDialog.getLocalSongList(songListItemInfos);
                dialogMusicListBinding.tvNumber.setText("(" + MusicListDialog.this.musicInfoList.size() + ")");
                MusicListDialog.this.canSort = true;
                MusicListDialog.this.enableDrag();
                MusicListDialog.this.mAdapter.setNewData(MusicListDialog.this.musicInfoList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<MusicInfo> getLocalSongList(List<SongListItemInfo> songListItemInfos) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < songListItemInfos.size(); i++) {
            SongListItemInfo songListItemInfo = songListItemInfos.get(i);
            MusicInfo musicInfo = new MusicInfo();
            musicInfo.setMusicname(songListItemInfo.name);
            musicInfo.setSingername(songListItemInfo.getSinger());
            musicInfo.setMusicid(songListItemInfo.getSongId());
            musicInfo.setIslocal(songListItemInfo.isLocal ? "1" : "0");
            musicInfo.setIscollection(songListItemInfo.isCollection);
            arrayList.add(musicInfo);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void controlMode(int order, int data) {
        CmdAssistant.getMusicAssistant(this.controlDevice, new int[0]).controlAction(ActivityUtils.getTopActivity(), order, data, new IAction() { // from class: com.ltech.smarthome.view.dialog.MusicListDialog$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                MusicListDialog.lambda$controlMode$5((ResponseMsg) obj);
            }
        });
    }

    public void refreshPanelData(ResponseMsg msg) {
        String resData = msg.getResData();
        if (resData.length() > 61) {
            String hexToString = StringUtils.hexToString(resData.substring(18, 40));
            if (!hexToString.equalsIgnoreCase(this.musicId)) {
                this.musicId = hexToString;
                this.mAdapter.notifyDataSetChanged();
            }
            OnMusicChangeCallback onMusicChangeCallback = this.onMusicChangeCallback;
            if (onMusicChangeCallback != null) {
                onMusicChangeCallback.onChange(msg, this.musicId);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateList(final DialogMusicListBinding viewBinding) {
        ((ObservableSubscribeProxy) Injection.net().updateMusicInfo(this.deviceMac, this.musicInfoList).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.view.dialog.MusicListDialog$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicListDialog.this.lambda$updateList$6(viewBinding, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateList$6(DialogMusicListBinding dialogMusicListBinding, Object obj) throws Exception {
        getSong(dialogMusicListBinding);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateList() {
        ((ObservableSubscribeProxy) Injection.net().updateMusicInfo(this.deviceMac, this.musicInfoList).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.view.dialog.MusicListDialog$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicListDialog.this.lambda$updateList$8(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateList$8(Object obj) throws Exception {
        CmdAssistant.getMusicAssistant(this.controlDevice, new int[0]).playListSort(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.view.dialog.MusicListDialog$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj2) {
                MusicListDialog.lambda$updateList$7((ResponseMsg) obj2);
            }
        });
    }
}