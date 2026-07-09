package com.ltech.smarthome.ui.scene;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.PlayListInfo;
import com.ltech.smarthome.model.bean.SongInfo;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.ui.device.musicplayer.ActMusicPlayerModeSelect;
import com.ltech.smarthome.ui.device.musicplayer.ActSelectSinglePlaylist;
import com.ltech.smarthome.ui.device.musicplayer.ActSelectSingleSong;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectMusicPlayerSceneAction extends VMActivity<ActSelectBinding, ActSelectMusicPlayerActionVM> implements ISelectAction {
    private long deviceId;
    private boolean isLocal;
    private BaseQuickAdapter<GoItem, BaseViewHolder> mAdapter;
    private String productId;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public /* synthetic */ void lambda$onActivityResult$8(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((ActSelectBinding) this.mViewBinding).tvTip.setVisibility(8);
        setTitle(getString(R.string.choose_action));
        setBackImage(R.mipmap.icon_back);
        ArrayList arrayList = new ArrayList();
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
        arrayList.add(new GoItem().setMainText(getString(R.string.music_player_single)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectMusicPlayerSceneAction$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectMusicPlayerSceneAction.this.lambda$initView$0();
            }
        })));
        arrayList.add(new GoItem().setMainText(getString(R.string.music_playlist)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectMusicPlayerSceneAction$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectMusicPlayerSceneAction.this.lambda$initView$1();
            }
        })));
        arrayList.add(new GoItem().setMainText(getString(R.string.music_player_all_songs)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectMusicPlayerSceneAction$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectMusicPlayerSceneAction.this.lambda$initView$2();
            }
        })));
        arrayList.add(new GoItem().setMainText(getString(R.string.music_player_pause_the_song)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectMusicPlayerSceneAction$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectMusicPlayerSceneAction.this.lambda$initView$4();
            }
        })));
        BaseQuickAdapter<GoItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<GoItem, BaseViewHolder>(this, R.layout.item_go_1, arrayList) { // from class: com.ltech.smarthome.ui.scene.ActSelectMusicPlayerSceneAction.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, GoItem item) {
                helper.setText(R.id.tv_main, item.getMainText());
                ((AppCompatTextView) helper.getView(R.id.tv_main)).getPaint().setFakeBoldText(true);
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectMusicPlayerSceneAction$$ExternalSyntheticLambda4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActSelectMusicPlayerSceneAction.this.lambda$initView$5(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActSelectBinding) this.mViewBinding).rvContent);
        ((ActSelectBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0() {
        if (!Injection.repo().song().hasSongs(this.deviceId)) {
            SmartToast.showShort(getResources().getString(R.string.you_do_not_have_any_songs_yet));
        } else {
            NavUtils.destination(ActSelectSingleSong.class).withLong("device_id", this.deviceId).withDefaultRequestCode().navigation(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1() {
        if (!Injection.repo().song().hasSongPlaylist(this.deviceId)) {
            SmartToast.showShort(getResources().getString(R.string.you_do_not_have_any_playlists_yet));
        } else {
            NavUtils.destination(ActSelectSinglePlaylist.class).withLong("device_id", this.deviceId).withDefaultRequestCode().navigation(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2() {
        if (!Injection.repo().song().hasSongs(this.deviceId)) {
            SmartToast.showShort(getResources().getString(R.string.you_do_not_have_any_songs_yet));
        } else {
            NavUtils.destination(ActMusicPlayerModeSelect.class).withLong("device_id", this.deviceId).withDefaultRequestCode().navigation(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$4() {
        ((ActSelectMusicPlayerActionVM) this.mViewModel).pause();
        boolean z = this.isLocal;
        if (z) {
            SceneHelper.instance().maskType = MaskType.LOCAL;
            setResult(3001);
            finishActivity();
            return;
        }
        if (z) {
            SceneHelper.instance().maskType = MaskType.LOCAL;
            setResult(3001);
            finishActivity();
            return;
        }
        SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectMusicPlayerSceneAction$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectMusicPlayerSceneAction.this.lambda$initView$3((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$5(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.mAdapter.getData().get(i).getAction().execute();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        this.isLocal = getIntent().getBooleanExtra(Constants.IS_LOCAL_SCENE, false);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        int intExtra = data.getIntExtra(Constants.MUSIC_PLAYER_MODE, 4);
        int intExtra2 = data.getIntExtra(Constants.MUSIC_PLAYER_VOLUME, 3);
        if (3010 == resultCode) {
            List list = (List) GsonUtils.fromJson(data.getStringExtra(Constants.SELECT_SONG_ID), new TypeToken<List<SongInfo>>(this) { // from class: com.ltech.smarthome.ui.scene.ActSelectMusicPlayerSceneAction.2
            }.getType());
            if (list == null || list.size() <= 0) {
                return;
            }
            ((ActSelectMusicPlayerActionVM) this.mViewModel).singleSong(((SongInfo) list.get(0)).getNum(), intExtra == 2 ? 1 : intExtra, intExtra2, ((SongInfo) list.get(0)).getName());
            if (this.isLocal) {
                SceneHelper.instance().maskType = MaskType.LOCAL;
                setResult(3001);
                finishActivity();
                return;
            }
            SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectMusicPlayerSceneAction$$ExternalSyntheticLambda5
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectMusicPlayerSceneAction.this.lambda$onActivityResult$6((Boolean) obj);
                }
            });
            return;
        }
        if (3013 != resultCode) {
            if (3015 == resultCode) {
                ((ActSelectMusicPlayerActionVM) this.mViewModel).playAll(intExtra, intExtra2);
                if (this.isLocal) {
                    SceneHelper.instance().maskType = MaskType.LOCAL;
                    setResult(3001);
                    finishActivity();
                    return;
                }
                SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectMusicPlayerSceneAction$$ExternalSyntheticLambda7
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActSelectMusicPlayerSceneAction.this.lambda$onActivityResult$8((Boolean) obj);
                    }
                });
                return;
            }
            return;
        }
        List list2 = (List) GsonUtils.fromJson(data.getStringExtra(Constants.SELECT_SONG_PLAYLIST_ID), new TypeToken<List<PlayListInfo>>(this) { // from class: com.ltech.smarthome.ui.scene.ActSelectMusicPlayerSceneAction.3
        }.getType());
        if (list2 == null || list2.size() <= 0) {
            return;
        }
        ((ActSelectMusicPlayerActionVM) this.mViewModel).singlePlaylist(((PlayListInfo) list2.get(0)).getNum(), intExtra, intExtra2, ((PlayListInfo) list2.get(0)).getName());
        if (this.isLocal) {
            SceneHelper.instance().maskType = MaskType.LOCAL;
            setResult(3001);
            finishActivity();
            return;
        }
        SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectMusicPlayerSceneAction$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectMusicPlayerSceneAction.this.lambda$onActivityResult$7((Boolean) obj);
            }
        });
    }
}