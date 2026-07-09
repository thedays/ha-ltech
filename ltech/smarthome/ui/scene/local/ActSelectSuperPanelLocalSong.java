package com.ltech.smarthome.ui.scene.local;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseListActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.bean.SongListItemInfo;
import com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager;
import com.ltech.smarthome.utils.Constants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectSuperPanelLocalSong extends BaseListActivity<SongListItemInfo> {
    private ArrayList<Integer> selectSongList = new ArrayList<>();
    private boolean supportNewMusic;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select;
    }

    @Override // com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.local_music));
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.SUPER_PANEL_NEW_MUSIC, false);
        this.supportNewMusic = booleanExtra;
        if (booleanExtra) {
            setEditString(getString(R.string.app_str_select_all));
            ((ActSelectBinding) this.mViewBinding).setBottomTip(getString(R.string.finish_with_num, new Object[]{0}));
        } else {
            ((ActSelectBinding) this.mViewBinding).setBottomTip(getString(R.string.finish));
        }
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectSuperPanelLocalSong$$ExternalSyntheticLambda2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSelectSuperPanelLocalSong.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
        ((ActSelectBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectSuperPanelLocalSong$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActSelectSuperPanelLocalSong.this.lambda$initView$1((View) obj);
            }
        }));
        int[] intArrayExtra = getIntent().getIntArrayExtra(Constants.SCENE_LOCAL_SONG_LIST);
        if (intArrayExtra != null && intArrayExtra.length > 0) {
            for (int i : intArrayExtra) {
                this.selectSongList.add(Integer.valueOf(i));
            }
            this.selectCountLiveData.setValue(Integer.valueOf(this.selectSongList.size()));
        }
        getLocalMusicList(getIntent().getStringExtra(Constants.MAC_ADDRESS));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (this.supportNewMusic) {
            if (this.selectSongList.contains(Integer.valueOf(i))) {
                this.selectSongList.remove(Integer.valueOf(i));
            } else {
                this.selectSongList.add(Integer.valueOf(i));
            }
            this.mAdapter.notifyItemChanged(i);
        } else {
            this.selectSongList.clear();
            this.selectSongList.add(Integer.valueOf(i));
            this.mAdapter.notifyDataSetChanged();
        }
        this.selectCountLiveData.setValue(Integer.valueOf(this.selectSongList.size()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view) {
        Intent intent = new Intent();
        intent.putIntegerArrayListExtra(Constants.SELECT_SONG_ID, this.selectSongList);
        StringBuilder sb = new StringBuilder();
        Iterator<Integer> it = this.selectSongList.iterator();
        while (it.hasNext()) {
            int intValue = it.next().intValue();
            if (sb.length() == 0) {
                sb.append(((SongListItemInfo) this.mAdapter.getData().get(intValue)).getName());
            } else {
                sb.append(";");
                sb.append(((SongListItemInfo) this.mAdapter.getData().get(intValue)).getName());
            }
        }
        intent.putExtra(Constants.SELECT_SONG_NAME, sb.toString());
        setResult(3019, intent);
        finish();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        this.selectCountLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectSuperPanelLocalSong$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectSuperPanelLocalSong.this.changeSelectCount(((Integer) obj).intValue());
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected void changeSelectCount(int selectCount) {
        if (this.supportNewMusic) {
            super.changeSelectCount(selectCount);
            ((ActSelectBinding) this.mViewBinding).setBottomTip(getString(R.string.finish_with_num, new Object[]{Integer.valueOf(selectCount)}));
        }
        ((ActSelectBinding) this.mViewBinding).tvBottom.setEnabled(selectCount != 0);
        ((ActSelectBinding) this.mViewBinding).tvBottom.setTextColor(getResources().getColor(selectCount != 0 ? R.color.color_text_red : R.color.color_text_red_disable));
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<SongListItemInfo> getList() {
        return new ArrayList();
    }

    public void getLocalMusicList(String mac) {
        MiguManager.getInstance().getLocalMusicList(mac, new IAction() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectSuperPanelLocalSong$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectSuperPanelLocalSong.this.setDataList((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, SongListItemInfo item) {
        helper.setText(R.id.tv_name, item.getName()).setBackgroundRes(R.id.iv_select, this.selectSongList.contains(Integer.valueOf(helper.getAdapterPosition())) ? R.mipmap.ic_tick_sel : R.color.transparent);
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        selectAll();
    }

    private void selectAll() {
        if (this.dataList.isEmpty()) {
            return;
        }
        if (!this.selectAll) {
            for (int i = 0; i < this.dataList.size(); i++) {
                if (!this.selectSongList.contains(Integer.valueOf(i))) {
                    this.selectSongList.add(Integer.valueOf(i));
                }
            }
        } else {
            this.selectSongList.clear();
        }
        this.mAdapter.notifyDataSetChanged();
        this.selectCountLiveData.setValue(Integer.valueOf(this.selectSongList.size()));
    }
}