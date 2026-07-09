package com.ltech.smarthome.ui.device.super_panel.music;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSortLocalMusicBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.SongListItemInfo;
import com.ltech.smarthome.net.request.music.SongRequest;
import com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicListVM;
import com.ltech.smarthome.utils.Constants;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSortLocalMusic extends VMActivity<ActSortLocalMusicBinding, ActDcaMusicListVM> {
    private ItemTouchHelper itemTouchHelper;
    private BaseItemDraggableAdapter<ActDcaMusicListVM.MusicInfoItem, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_sort_local_music;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((ActDcaMusicListVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActDcaMusicListVM) this.mViewModel).controlDevice = Injection.repo().device().getDeviceById(((ActDcaMusicListVM) this.mViewModel).controlId);
        ((ActDcaMusicListVM) this.mViewModel).deviceMac = getIntent().getStringExtra(Constants.MAC_ADDRESS);
        String stringExtra = getIntent().getStringExtra(Constants.SELECTED_LIST);
        if (stringExtra != null) {
            ((ActDcaMusicListVM) this.mViewModel).itemInfoList = (List) GsonUtils.fromJson(stringExtra, new TypeToken<List<SongListItemInfo>>(this) { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActSortLocalMusic.1
            }.getType());
            if (((ActDcaMusicListVM) this.mViewModel).itemInfoList == null) {
                ((ActDcaMusicListVM) this.mViewModel).itemInfoList = new ArrayList();
            }
        }
        setBackString(getString(R.string.app_str_select_all));
        setEditString(getString(R.string.finish));
        iniRv();
        ((ActSortLocalMusicBinding) this.mViewBinding).tvDel.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActSortLocalMusic.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ActSortLocalMusic.this.showDelDialog();
            }
        });
        refreshUpgradeBtn();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDelDialog() {
        final ArrayList arrayList = new ArrayList();
        for (int size = this.mAdapter.getData().size() - 1; size >= 0; size--) {
            ActDcaMusicListVM.MusicInfoItem musicInfoItem = this.mAdapter.getData().get(size);
            if (musicInfoItem.isSel()) {
                arrayList.add(Long.valueOf(musicInfoItem.getItemInfo().getSongid()));
            }
        }
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), getString(R.string.app_str_del_music), String.format(getString(R.string.app_str_del_music_tip), Integer.valueOf(arrayList.size()))).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.cancel), new OnDialogButtonClickListener(this) { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActSortLocalMusic.3
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                return false;
            }
        }).setOkButton(ActivityUtils.getTopActivity().getString(R.string.delete), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActSortLocalMusic$$ExternalSyntheticLambda0
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showDelDialog$0;
                lambda$showDelDialog$0 = ActSortLocalMusic.this.lambda$showDelDialog$0(arrayList, baseDialog, view);
                return lambda$showDelDialog$0;
            }
        }).setCancelable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showDelDialog$0(List list, BaseDialog baseDialog, View view) {
        for (int size = this.mAdapter.getData().size() - 1; size >= 0; size--) {
            if (this.mAdapter.getData().get(size).isSel()) {
                this.mAdapter.remove(size);
            }
        }
        ((ActDcaMusicListVM) this.mViewModel).del(list);
        refreshUpgradeBtn();
        return false;
    }

    private void iniRv() {
        ArrayList arrayList = new ArrayList();
        Iterator<SongListItemInfo> it = ((ActDcaMusicListVM) this.mViewModel).itemInfoList.iterator();
        while (it.hasNext()) {
            arrayList.add(new ActDcaMusicListVM.MusicInfoItem(false, it.next()));
        }
        ((ActSortLocalMusicBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView = ((ActSortLocalMusicBinding) this.mViewBinding).rv;
        BaseItemDraggableAdapter<ActDcaMusicListVM.MusicInfoItem, BaseViewHolder> baseItemDraggableAdapter = new BaseItemDraggableAdapter<ActDcaMusicListVM.MusicInfoItem, BaseViewHolder>(this, R.layout.item_sort_and_del, arrayList) { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActSortLocalMusic.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, ActDcaMusicListVM.MusicInfoItem musicInfoItem) {
                baseViewHolder.setText(R.id.tv_name, musicInfoItem.getItemInfo().getName());
                baseViewHolder.setImageResource(R.id.cb, musicInfoItem.isSel() ? R.mipmap.ic_tick_select_white : R.mipmap.ic_tick_default);
            }
        };
        this.mAdapter = baseItemDraggableAdapter;
        recyclerView.setAdapter(baseItemDraggableAdapter);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActSortLocalMusic.5
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActDcaMusicListVM.MusicInfoItem musicInfoItem = (ActDcaMusicListVM.MusicInfoItem) ActSortLocalMusic.this.mAdapter.getData().get(i);
                musicInfoItem.setSel(!musicInfoItem.isSel());
                ActSortLocalMusic.this.mAdapter.setData(i, musicInfoItem);
                ActSortLocalMusic.this.refreshUpgradeBtn();
            }
        });
        this.mAdapter.bindToRecyclerView(((ActSortLocalMusicBinding) this.mViewBinding).rv);
        ((ActSortLocalMusicBinding) this.mViewBinding).rv.setHasFixedSize(true);
        enableDrag();
    }

    private void enableDrag() {
        if (this.itemTouchHelper == null) {
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemDragAndSwipeCallback(this.mAdapter));
            this.itemTouchHelper = itemTouchHelper;
            itemTouchHelper.attachToRecyclerView(((ActSortLocalMusicBinding) this.mViewBinding).rv);
        }
        this.mAdapter.enableDragItem(this.itemTouchHelper);
        this.mAdapter.setOnItemDragListener(new OnItemDragListener() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActSortLocalMusic.6
            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragMoving(RecyclerView.ViewHolder viewHolder, int i, RecyclerView.ViewHolder viewHolder1, int i1) {
            }

            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int i) {
            }

            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int p) {
                ArrayList arrayList = new ArrayList();
                int i = 0;
                while (i < ActSortLocalMusic.this.mAdapter.getData().size()) {
                    long songid = ((ActDcaMusicListVM.MusicInfoItem) ActSortLocalMusic.this.mAdapter.getData().get(i)).getItemInfo().getSongid();
                    StringBuilder sb = new StringBuilder();
                    i++;
                    sb.append(i);
                    sb.append("");
                    arrayList.add(new SongRequest.Sort(songid, sb.toString()));
                }
                ((ActDcaMusicListVM) ActSortLocalMusic.this.mViewModel).sort(arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshUpgradeBtn() {
        Iterator<ActDcaMusicListVM.MusicInfoItem> it = this.mAdapter.getData().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().isSel()) {
                i++;
            }
        }
        ((ActSortLocalMusicBinding) this.mViewBinding).tvDel.setText(getString(R.string.delete) + "(" + i + ")");
        if (i > 0) {
            ((ActSortLocalMusicBinding) this.mViewBinding).tvDel.setEnabled(true);
        } else {
            ((ActSortLocalMusicBinding) this.mViewBinding).tvDel.setEnabled(false);
        }
        if (!this.mAdapter.getData().isEmpty()) {
            if (i == this.mAdapter.getData().size()) {
                setBackString(getString(R.string.app_str_cancel_select_all));
            } else {
                setBackString(getString(R.string.app_str_select_all));
            }
        } else {
            setBackString("");
        }
        if (!this.mAdapter.getData().isEmpty()) {
            showContent();
        } else {
            showEmpty();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        boolean equals = getTitleBar().getBackString().equals(getString(R.string.app_str_select_all));
        for (int i = 0; i < this.mAdapter.getData().size(); i++) {
            ActDcaMusicListVM.MusicInfoItem musicInfoItem = this.mAdapter.getData().get(i);
            musicInfoItem.setSel(equals);
            this.mAdapter.setData(i, musicInfoItem);
        }
        refreshUpgradeBtn();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        finish();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        finish();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().bgRes(R.drawable.shape_white_round_music_bg).emptyStringRes(R.string.app_str_no_song).emptyImageRes(R.mipmap.pic_music_empty_2));
    }
}