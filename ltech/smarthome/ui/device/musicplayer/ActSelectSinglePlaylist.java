package com.ltech.smarthome.ui.device.musicplayer;

import android.content.Intent;
import androidx.appcompat.widget.AppCompatTextView;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.BaseSingleSelectActivity;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.PlayListInfo;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectSinglePlaylist extends BaseSingleSelectActivity<PlayListInfo> implements ISelectAction {
    private long deviceId = -1;
    private List<PlayListInfo> playList = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select;
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity
    protected void save() {
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

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        setBackImage(R.mipmap.icon_back);
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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, PlayListInfo item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == this.selectPosition ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity
    protected void onItemClick(int position) {
        super.onItemClick(position);
        NavUtils.destination(ActMusicPlayerModeSelect.class).withBoolean(Constants.OPEN_SHUFFLE_MENU, true).withLong("device_id", this.deviceId).withDefaultRequestCode().navigation(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && 3015 == resultCode) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(this.playList.get(this.selectPosition));
            data.putExtra(Constants.SELECT_SONG_PLAYLIST_ID, GsonUtils.toJson(arrayList));
            setResult(3013, data);
            finishActivity();
        }
    }
}