package com.ltech.smarthome.ui.device.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.adapter.SongPlayListAdapter;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.databinding.FtSongPlaylistBinding;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.PlayListInfo;
import com.ltech.smarthome.model.bean.SongInfo;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.AddGroupDialog;
import java.util.List;

/* loaded from: classes4.dex */
public class FtSongPlayList extends VMFragment<FtSongPlaylistBinding, FtSongPlayListVM> {
    private AddGroupDialog addPlaylistDialog;
    private SongPlayListAdapter mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_song_playlist;
    }

    public static FtSongPlayList create(long controlId) {
        FtSongPlayList ftSongPlayList = new FtSongPlayList();
        Bundle bundle = new Bundle();
        bundle.putLong("controlId", controlId);
        ftSongPlayList.setArguments(bundle);
        return ftSongPlayList;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        showEmpty();
        initListView();
    }

    private void initListView() {
        ((FtSongPlaylistBinding) this.mViewBinding).recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView recyclerView = ((FtSongPlaylistBinding) this.mViewBinding).recyclerView;
        SongPlayListAdapter songPlayListAdapter = new SongPlayListAdapter(R.layout.item_song_playlist);
        this.mAdapter = songPlayListAdapter;
        recyclerView.setAdapter(songPlayListAdapter);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.musicplayer.FtSongPlayList.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PlayListInfo item = FtSongPlayList.this.mAdapter.getItem(position);
                if (item == null) {
                    return;
                }
                ((FtSongPlayListVM) FtSongPlayList.this.mViewModel).navSoundList(item);
            }
        });
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.item_song_playlist_foot, (ViewGroup) null);
        this.mAdapter.addFooterView(inflate);
        inflate.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.musicplayer.FtSongPlayList.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                FtSongPlayList.this.showCreatePlaylistDialog();
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        super.startObserve();
        if (getArguments() == null) {
            return;
        }
        ((FtSongPlayListVM) this.mViewModel).controlId = getArguments().getLong("controlId", -1L);
        ((FtSongPlayListVM) this.mViewModel).localPlayList.observe(this, new Observer<List<PlayListInfo>>() { // from class: com.ltech.smarthome.ui.device.musicplayer.FtSongPlayList.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<PlayListInfo> infos) {
                FtSongPlayList.this.showContent();
                FtSongPlayList.this.mAdapter.replaceData(infos);
            }
        });
        ((FtSongPlayListVM) this.mViewModel).controlDevice.observe(this, new Observer<Device>() { // from class: com.ltech.smarthome.ui.device.musicplayer.FtSongPlayList.4
            @Override // androidx.lifecycle.Observer
            public void onChanged(Device device) {
                ((FtSongPlayListVM) FtSongPlayList.this.mViewModel).loadPlayList(device);
            }
        });
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        ((FtSongPlayListVM) this.mViewModel).loadDevice();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void onEmptyTry() {
        super.onEmptyTry();
        showCreatePlaylistDialog();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.you_do_not_have_any_playlists_yet).emptyTryStringRes(R.string.add_group1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCreatePlaylistDialog() {
        if (((FtSongPlayListVM) this.mViewModel).canCreate()) {
            AddGroupDialog confirmAction = AddGroupDialog.asDefault().setTitle(getString(R.string.create_playlist)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.save)).setContentTip(getString(R.string.app_str_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.musicplayer.FtSongPlayList$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    FtSongPlayList.this.lambda$showCreatePlaylistDialog$0((String) obj);
                }
            });
            this.addPlaylistDialog = confirmAction;
            confirmAction.showDialog(getChildFragmentManager());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showCreatePlaylistDialog$0(String str) {
        if (TextUtils.isEmpty(str)) {
            SmartToast.showShort(getString(R.string.input_name));
        } else if (str.getBytes().length > 24) {
            SmartToast.showShort(getString(R.string.playlist_title_is_too_long));
        } else {
            ((FtSongPlayListVM) this.mViewModel).creatPlaylist(str);
            this.addPlaylistDialog.dismissDialog();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 3010 || data == null) {
            return;
        }
        String stringExtra = data.getStringExtra(Constants.SELECT_SONG_ID);
        int intExtra = data.getIntExtra(Constants.SELECT_SONG_PLAYLIST_ID, -1);
        ((FtSongPlayListVM) this.mViewModel).setSelectSongs((List) GsonUtils.fromJson(stringExtra, new TypeToken<List<SongInfo>>(this) { // from class: com.ltech.smarthome.ui.device.musicplayer.FtSongPlayList.5
        }.getType()));
        ((FtSongPlayListVM) this.mViewModel).addSongs2Playlist(intExtra);
        ((FtSongPlayListVM) this.mViewModel).loadPlayList(((FtSongPlayListVM) this.mViewModel).controlDevice.getValue());
    }
}