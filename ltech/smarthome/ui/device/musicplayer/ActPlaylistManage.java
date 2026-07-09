package com.ltech.smarthome.ui.device.musicplayer;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActPlaylistManageBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.PlayListInfo;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.cmd_assistant.BleMusicPlayerAssistant;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.ResponseMsg;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActPlaylistManage extends BaseNormalActivity<ActPlaylistManageBinding> implements ISelectAction {
    private BaseItemDraggableAdapter<PlayListInfo, BaseViewHolder> mAdapter;
    private BleMusicPlayerAssistant mAssistant;
    private long deviceId = -1;
    private List<PlayListInfo> playLists = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_playlist_manage;
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

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.manage_your_playlists));
        ((ActPlaylistManageBinding) this.mViewBinding).tvTip.setVisibility(8);
        initListView();
        updateModeList();
    }

    private void initListView() {
        ((ActPlaylistManageBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActPlaylistManageBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActPlaylistManageBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
        BaseItemDraggableAdapter<PlayListInfo, BaseViewHolder> baseItemDraggableAdapter = new BaseItemDraggableAdapter<PlayListInfo, BaseViewHolder>(R.layout.item_playlist_manager, this.playLists) { // from class: com.ltech.smarthome.ui.device.musicplayer.ActPlaylistManage.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, PlayListInfo item) {
                helper.setText(R.id.tv_song_name, item.getName());
                helper.setText(R.id.tv_song_author, String.format(ActPlaylistManage.this.getString(R.string.num_of_songs), Integer.valueOf(item.getSongCount())));
                helper.addOnClickListener(R.id.iv_del);
                ((AppCompatTextView) helper.getView(R.id.tv_song_name)).getPaint().setFakeBoldText(true);
            }
        };
        this.mAdapter = baseItemDraggableAdapter;
        baseItemDraggableAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.device.musicplayer.ActPlaylistManage$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActPlaylistManage.this.lambda$initListView$0(baseQuickAdapter, view, i);
            }
        });
        ((ActPlaylistManageBinding) this.mViewBinding).rvContent.setSwipeMenuCreator(new SwipeMenuCreator() { // from class: com.ltech.smarthome.ui.device.musicplayer.ActPlaylistManage$$ExternalSyntheticLambda1
            @Override // com.yanzhenjie.recyclerview.SwipeMenuCreator
            public final void onCreateMenu(SwipeMenu swipeMenu, SwipeMenu swipeMenu2, int i) {
                ActPlaylistManage.this.lambda$initListView$1(swipeMenu, swipeMenu2, i);
            }
        });
        ((ActPlaylistManageBinding) this.mViewBinding).rvContent.setOnItemMenuClickListener(new OnItemMenuClickListener() { // from class: com.ltech.smarthome.ui.device.musicplayer.ActPlaylistManage$$ExternalSyntheticLambda2
            @Override // com.yanzhenjie.recyclerview.OnItemMenuClickListener
            public final void onItemClick(SwipeMenuBridge swipeMenuBridge, int i) {
                ActPlaylistManage.this.lambda$initListView$2(swipeMenuBridge, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActPlaylistManageBinding) this.mViewBinding).rvContent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (view.getId() == R.id.iv_del) {
            ((ActPlaylistManageBinding) this.mViewBinding).rvContent.smoothOpenRightMenu(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListView$1(SwipeMenu swipeMenu, SwipeMenu swipeMenu2, int i) {
        swipeMenu2.addMenuItem(new SwipeMenuItem(this).setTextSize(14).setWidth(SizeUtils.dp2px(60.0f)).setHeight(SizeUtils.dp2px(60.0f)).setText(R.string.delete).setTextColor(-1).setBackgroundColorResource(R.color.color_text_red));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListView$2(SwipeMenuBridge swipeMenuBridge, int i) {
        swipeMenuBridge.closeMenu();
        if (-1 == swipeMenuBridge.getDirection() && swipeMenuBridge.getPosition() == 0) {
            del(i);
        }
    }

    private void updateModeList() {
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
        this.playLists = Injection.repo().song().getPlaylist(this.deviceId);
        this.mAssistant = CmdAssistant.getBleMusicPlayerAssistant(Injection.repo().device().getDeviceByDeviceId(this.deviceId), new int[0]);
        this.mAdapter.replaceData(this.playLists);
    }

    private void del(final int pos) {
        final PlayListInfo item = this.mAdapter.getItem(pos);
        if (item == null) {
            return;
        }
        showLoadingDialog(getString(R.string.executing));
        this.mAssistant.deletePlaylist(ActivityUtils.getTopActivity(), item.getNum(), item.getName(), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.musicplayer.ActPlaylistManage.2
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                if (responseMsg != null) {
                    ActPlaylistManage.this.mAdapter.remove(pos);
                    Injection.repo().song().delPlaylistById(ActPlaylistManage.this.deviceId, item.getNum());
                }
                ActPlaylistManage.this.dismissLoadingDialog();
            }
        });
    }
}