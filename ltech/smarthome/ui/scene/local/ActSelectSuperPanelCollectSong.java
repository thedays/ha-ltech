package com.ltech.smarthome.ui.scene.local;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.aispeech.dca.DcaSdk;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.bean.SongListItemInfo;
import com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager;
import com.ltech.smarthome.ui.device.super_panel.music.manager.SongListInterface;
import com.ltech.smarthome.utils.Constants;
import com.rich.czlylibary.sdk.MiguCzlySDK;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectSuperPanelCollectSong extends VMActivity<ActSelectBinding, BaseViewModel> {
    private String deviceSn;
    private BaseItemDraggableAdapter<SongListItemInfo, BaseViewHolder> mAdapter;
    private int selectPosition = -1;
    private List<SongListItemInfo> songListItemInfoList;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.collect_music));
        ((ActSelectBinding) this.mViewBinding).setBottomTip(getString(R.string.confirm));
        this.deviceSn = getIntent().getStringExtra("device_sn");
        ((ActSelectBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectSuperPanelCollectSong$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActSelectSuperPanelCollectSong.this.lambda$initView$0((View) obj);
            }
        }));
        initRv();
        getCollectList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (this.selectPosition != -1) {
            Intent intent = new Intent();
            if (this.songListItemInfoList.size() > 0) {
                intent.putExtra(Constants.SELECT_SONG_NAME, this.songListItemInfoList.get(this.selectPosition).name);
                intent.putExtra(Constants.SELECT_SONG_ID, this.songListItemInfoList.get(this.selectPosition).songId);
            }
            setResult(3022, intent);
            finish();
        }
    }

    private void initRv() {
        BaseItemDraggableAdapter<SongListItemInfo, BaseViewHolder> baseItemDraggableAdapter = new BaseItemDraggableAdapter<SongListItemInfo, BaseViewHolder>(R.layout.item_select, new ArrayList()) { // from class: com.ltech.smarthome.ui.scene.local.ActSelectSuperPanelCollectSong.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, SongListItemInfo item) {
                helper.setText(R.id.tv_name, item.getName()).setBackgroundRes(R.id.iv_select, ActSelectSuperPanelCollectSong.this.selectPosition == helper.getAdapterPosition() ? R.mipmap.ic_tick_sel : R.color.transparent);
                ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
            }
        };
        this.mAdapter = baseItemDraggableAdapter;
        baseItemDraggableAdapter.bindToRecyclerView(((ActSelectBinding) this.mViewBinding).rvContent);
        ((ActSelectBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActSelectBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectSuperPanelCollectSong$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSelectSuperPanelCollectSong.this.lambda$initRv$1(baseQuickAdapter, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$1(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (this.selectPosition != i) {
            this.selectPosition = i;
            baseQuickAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
    }

    private void getCollectList() {
        DcaSdk.setCurrentDeviceId(this.deviceSn);
        MiguCzlySDK.getInstance().setSmartDeviceId(this.deviceSn);
        MiguCzlySDK.getInstance().setUid(this.deviceSn);
        MiguManager.getInstance().getCollectAllList(1, new SongListInterface() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectSuperPanelCollectSong.2
            @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SongListInterface
            public void onFail() {
            }

            @Override // com.ltech.smarthome.ui.device.super_panel.music.manager.SongListInterface
            public void onSuccess(List<SongListItemInfo> listItemInfos) {
                ActSelectSuperPanelCollectSong.this.songListItemInfoList = listItemInfos;
                ActSelectSuperPanelCollectSong.this.mAdapter.setNewData(listItemInfos);
            }
        });
    }
}