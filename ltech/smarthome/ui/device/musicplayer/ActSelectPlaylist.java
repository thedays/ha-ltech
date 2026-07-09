package com.ltech.smarthome.ui.device.musicplayer;

import android.content.Intent;
import androidx.appcompat.widget.AppCompatTextView;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseMultiSelectActivity;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.PlayListInfo;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectPlaylist extends BaseMultiSelectActivity<PlayListInfo> implements ISelectAction {
    private long deviceId = -1;
    private List<PlayListInfo> playList = new ArrayList();

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
        setTitle(getString(R.string.select_playlist));
        ((ActSelectBinding) this.mViewBinding).tvTip.setVisibility(8);
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
        getIntent().getStringExtra(Constants.SELECTED_LIST);
        super.initView();
        updateModeList();
        this.mAdapter.replaceData(this.playList);
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<PlayListInfo> getList() {
        return this.playList;
    }

    private void updateModeList() {
        List<PlayListInfo> playlist = Injection.repo().song().getPlaylist(this.deviceId);
        this.playList = playlist;
        setDataList(playlist);
    }

    @Override // com.ltech.smarthome.base.BaseMultiSelectActivity
    protected void save() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.selectArray.length; i++) {
            if (this.selectArray[i]) {
                arrayList.add(this.playList.get(i));
            }
        }
        Intent intent = new Intent();
        intent.putExtra(Constants.SELECT_SONG_PLAYLIST_ID, GsonUtils.toJson(arrayList));
        setResult(3013, intent);
        finishActivity();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, PlayListInfo item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setBackgroundRes(R.id.iv_select, this.selectArray[helper.getAdapterPosition()] ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
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
        if (i > 1) {
            this.selectArray[position] = false;
            this.mAdapter.notifyItemChanged(position);
            SmartToast.showShort(getResources().getString(R.string.you_have_reached_the_maximum_number_of_songs));
        }
    }
}