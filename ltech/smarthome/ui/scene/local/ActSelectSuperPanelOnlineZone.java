package com.ltech.smarthome.ui.scene.local;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseListActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NameRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectSuperPanelOnlineZone extends BaseListActivity<String> {
    private int selectPosition;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select;
    }

    @Override // com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.online_music));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.confirm));
        this.selectPosition = getIntent().getIntExtra(Constants.SCENE_ONLINE_MUSIC_POSITION, 0);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectSuperPanelOnlineZone$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSelectSuperPanelOnlineZone.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (this.selectPosition != i) {
            this.selectPosition = i;
            baseQuickAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<String> getList() {
        return Arrays.asList(NameRepository.getOnlineZoneName(this));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, item).setBackgroundRes(R.id.iv_select, this.selectPosition == helper.getAdapterPosition() ? R.mipmap.ic_tick_sel : R.color.transparent);
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        Intent intent = new Intent();
        intent.putIntegerArrayListExtra(Constants.SELECT_SONG_ID, new ArrayList<>(Collections.singletonList(Integer.valueOf(this.selectPosition))));
        setResult(3020, intent);
        finish();
    }
}