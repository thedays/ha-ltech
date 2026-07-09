package com.ltech.smarthome.ui.device.musicplayer;

import android.content.Intent;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActTrigCurtainOpenDirSetBinding;
import com.ltech.smarthome.utils.Constants;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class ActMusicPlayerPowerOnStateSetting extends BaseNormalActivity<ActTrigCurtainOpenDirSetBinding> {
    BaseQuickAdapter<String, BaseViewHolder> keyAdapter;
    List<String> keyItemList;
    private List<String> keyItemTipList;
    protected int selectPosition = -1;
    private int lastSelectPosition = -1;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_trig_curtain_open_dir_set;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.light_on_state));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        this.keyItemList = Arrays.asList(getResources().getStringArray(R.array.music_player_power_state));
        this.keyItemTipList = Arrays.asList(getResources().getStringArray(R.array.music_player_power_state_tip));
        this.selectPosition = getIntent().getIntExtra(Constants.SELECT_POSITION, -1);
        BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_select_subtext_ver, this.keyItemList) { // from class: com.ltech.smarthome.ui.device.musicplayer.ActMusicPlayerPowerOnStateSetting.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_name, item).setText(R.id.tv_sub_name, (CharSequence) ActMusicPlayerPowerOnStateSetting.this.keyItemTipList.get(helper.getLayoutPosition())).setImageResource(R.id.iv_select, ActMusicPlayerPowerOnStateSetting.this.selectPosition == helper.getAdapterPosition() ? R.mipmap.ic_tick_sel : 0);
            }
        };
        this.keyAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.musicplayer.ActMusicPlayerPowerOnStateSetting$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActMusicPlayerPowerOnStateSetting.this.lambda$initView$0(baseQuickAdapter2, view, i);
            }
        });
        this.keyAdapter.bindToRecyclerView(((ActTrigCurtainOpenDirSetBinding) this.mViewBinding).rvContent);
        ((ActTrigCurtainOpenDirSetBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this, 1, false));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int i2 = this.selectPosition;
        if (i2 != i) {
            this.lastSelectPosition = i2;
            this.selectPosition = i;
            if (i != -1) {
                baseQuickAdapter.notifyItemChanged(i);
            }
            int i3 = this.lastSelectPosition;
            if (i3 != -1) {
                baseQuickAdapter.notifyItemChanged(i3);
            }
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        Intent intent = new Intent();
        int i = this.selectPosition;
        if (i == 0) {
            intent.putExtra(Constants.MUSIC_PLAYER_POWER_STATE, 3);
        } else if (i == 1) {
            intent.putExtra(Constants.MUSIC_PLAYER_POWER_STATE, 1);
        } else if (i == 2) {
            intent.putExtra(Constants.MUSIC_PLAYER_POWER_STATE, 2);
        } else if (i == 3) {
            intent.putExtra(Constants.MUSIC_PLAYER_POWER_STATE, 5);
        }
        setResult(3014, intent);
        finishActivity();
    }
}