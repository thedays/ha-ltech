package com.ltech.smarthome.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.PlayListInfo;

/* loaded from: classes3.dex */
public class SongPlayListAdapter extends BaseQuickAdapter<PlayListInfo, BaseViewHolder> {
    private int selectPos;

    public SongPlayListAdapter(int layoutResId) {
        super(layoutResId);
        this.selectPos = -1;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, PlayListInfo item) {
        helper.setText(R.id.tv_song_name, item.getName());
        helper.setText(R.id.tv_song_author, String.format(this.mContext.getString(R.string.num_of_songs), Integer.valueOf(item.getSongCount())));
    }

    public void setSelectPosition(int pos) {
        int i = this.selectPos;
        this.selectPos = pos;
        if (i != -1) {
            notifyItemChanged(i);
        }
        notifyItemChanged(this.selectPos);
    }
}