package com.ltech.smarthome.ui.device.musicplayer;

import android.content.Intent;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseMultiSelectActivity;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.SongInfo;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectSongs extends BaseMultiSelectActivity<SongInfo> implements ISelectAction {
    private boolean isEdit;
    private boolean isOrder;
    private int playlistId;
    private List<SongInfo> selectSongs;
    private long deviceId = -1;
    private List<SongInfo> songsList = new ArrayList();
    OnItemDragListener onItemDragListener = new OnItemDragListener(this) { // from class: com.ltech.smarthome.ui.device.musicplayer.ActSelectSongs.2
        @Override // com.chad.library.adapter.base.listener.OnItemDragListener
        public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
        }

        @Override // com.chad.library.adapter.base.listener.OnItemDragListener
        public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
        }

        @Override // com.chad.library.adapter.base.listener.OnItemDragListener
        public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
        }
    };

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$edit$1(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseMultiSelectActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.confirm));
        setTitle(getString(R.string.select_song));
        ((ActSelectBinding) this.mViewBinding).tvTip.setVisibility(8);
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
        this.playlistId = getIntent().getIntExtra(Constants.PLAY_LIST_ID, -1);
        this.isEdit = getIntent().getBooleanExtra(Constants.SONG_EDIT, false);
        this.isOrder = getIntent().getBooleanExtra(Constants.SONG_ORDER, false);
        String stringExtra = getIntent().getStringExtra(Constants.SELECTED_LIST);
        if (stringExtra != null) {
            this.selectSongs = (List) GsonUtils.fromJson(stringExtra, new TypeToken<List<SongInfo>>(this) { // from class: com.ltech.smarthome.ui.device.musicplayer.ActSelectSongs.1
            }.getType());
        }
        super.initView();
        updateModeList();
        if (this.isEdit) {
            setTitle(getString(R.string.manage_your_songs));
        }
        if (this.isOrder) {
            this.mAdapter.setOnItemClickListener(null);
            setTitle(getString(R.string.song_order));
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemDragAndSwipeCallback(this.mAdapter));
            itemTouchHelper.attachToRecyclerView(((ActSelectBinding) this.mViewBinding).rvContent);
            this.mAdapter.enableDragItem(itemTouchHelper, R.id.iv_select, true);
            this.mAdapter.setOnItemDragListener(this.onItemDragListener);
        }
        this.mAdapter.replaceData(this.songsList);
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<SongInfo> getList() {
        return this.songsList;
    }

    private void updateModeList() {
        List<SongInfo> list;
        if (this.isOrder) {
            this.songsList = this.selectSongs;
            return;
        }
        List<SongInfo> songs = Injection.repo().song().getSongs(this.deviceId);
        this.songsList = songs;
        setDataList(songs);
        if (!this.isEdit || (list = this.selectSongs) == null || list.size() <= 0) {
            return;
        }
        HashMap hashMap = new HashMap();
        Iterator<SongInfo> it = this.songsList.iterator();
        int i = 0;
        while (it.hasNext()) {
            hashMap.put(Integer.valueOf(it.next().getNum()), Integer.valueOf(i));
            i++;
        }
        for (SongInfo songInfo : this.selectSongs) {
            if (songInfo != null && hashMap.containsKey(Integer.valueOf(songInfo.getNum())) && hashMap.get(Integer.valueOf(songInfo.getNum())) != null) {
                this.selectArray[((Integer) hashMap.get(Integer.valueOf(songInfo.getNum()))).intValue()] = true;
            }
        }
    }

    @Override // com.ltech.smarthome.base.BaseMultiSelectActivity
    protected void save() {
        List arrayList = new ArrayList();
        if (this.isOrder) {
            arrayList = this.mAdapter.getData();
        } else {
            for (int i = 0; i < this.selectArray.length; i++) {
                if (this.selectArray[i]) {
                    arrayList.add(this.songsList.get(i));
                }
            }
        }
        Intent intent = new Intent();
        intent.putExtra(Constants.SELECT_SONG_ID, GsonUtils.toJson(arrayList));
        intent.putExtra(Constants.SELECT_SONG_PLAYLIST_ID, this.playlistId);
        if (this.isEdit) {
            setResult(3011, intent);
        } else if (this.isOrder) {
            setResult(3012, intent);
        } else {
            setResult(3010, intent);
        }
        finishActivity();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, SongInfo item) {
        helper.setText(R.id.tv_name, item.getName());
        if (this.isOrder) {
            helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_item_sort);
        } else {
            helper.setBackgroundRes(R.id.iv_select, this.selectArray[helper.getAdapterPosition()] ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
        }
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseMultiSelectActivity
    protected void onItemClick(int position) {
        super.onItemClick(position);
        int i = 0;
        for (boolean z : this.selectArray) {
            if (z) {
                i++;
            }
        }
        if (i > 32) {
            this.selectArray[position] = false;
            this.mAdapter.notifyItemChanged(position);
            SmartToast.showShort(getResources().getString(R.string.you_have_reached_the_maximum_number_of_songs));
        }
    }
}