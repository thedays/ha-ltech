package com.ltech.smarthome.adapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.SongInfo;
import com.ltech.smarthome.view.VoisePlayingIcon;

/* loaded from: classes3.dex */
public class SongsAdapter extends BaseQuickAdapter<SongInfo, BaseViewHolder> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private int selectPos;

    public SongsAdapter(int layoutResId) {
        super(layoutResId);
        this.selectPos = -1;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, SongInfo item) {
        helper.setText(R.id.tv_song_name, item.getName());
        helper.setText(R.id.tv_song_author, item.getName());
        VoisePlayingIcon voisePlayingIcon = (VoisePlayingIcon) helper.getView(R.id.iv_play_anim);
        if (helper.getBindingAdapterPosition() == this.selectPos) {
            voisePlayingIcon.setVisibility(0);
            voisePlayingIcon.start();
        } else {
            voisePlayingIcon.setVisibility(4);
            voisePlayingIcon.stop();
        }
    }

    public void setSelectPosition(int pos) {
        int i = this.selectPos;
        this.selectPos = pos;
        if (getRecyclerView() == null || getRecyclerView().getLayoutManager() == null) {
            return;
        }
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getRecyclerView().getLayoutManager();
        int findLastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
        int findFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        if (i >= findFirstVisibleItemPosition && i <= findLastVisibleItemPosition && i != -1) {
            notifyItemChanged(i);
        }
        int i2 = this.selectPos;
        if (i2 < findFirstVisibleItemPosition || i2 > findLastVisibleItemPosition) {
            return;
        }
        notifyItemChanged(i2);
    }
}