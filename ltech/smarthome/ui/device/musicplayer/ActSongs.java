package com.ltech.smarthome.ui.device.musicplayer;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.adapter.SongsAdapter;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSongsBinding;
import com.ltech.smarthome.model.bean.SongInfo;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSongs extends VMActivity<ActSongsBinding, FtSongsVM> {
    private SongsAdapter mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_songs;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((FtSongsVM) this.mViewModel).title.setValue(getIntent().getStringExtra(Constants.PLAY_LIST_TITLE));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.edit));
        showEmpty();
        initListView();
        initSeeKbar();
    }

    private void initListView() {
        if (this.mViewBinding == 0) {
            return;
        }
        ((ActSongsBinding) this.mViewBinding).layoutControl.setOnClickListener(new View.OnClickListener(this) { // from class: com.ltech.smarthome.ui.device.musicplayer.ActSongs.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        ((ActSongsBinding) this.mViewBinding).recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView = ((ActSongsBinding) this.mViewBinding).recyclerView;
        SongsAdapter songsAdapter = new SongsAdapter(R.layout.item_song_info);
        this.mAdapter = songsAdapter;
        recyclerView.setAdapter(songsAdapter);
        ((ActSongsBinding) this.mViewBinding).recyclerView.setHasFixedSize(true);
        this.mAdapter.bindToRecyclerView(((ActSongsBinding) this.mViewBinding).recyclerView);
        ((ActSongsBinding) this.mViewBinding).recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.ltech.smarthome.ui.device.musicplayer.ActSongs.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView2, int newState) {
                super.onScrollStateChanged(recyclerView2, newState);
                if (newState == 0) {
                    ((ActSongsBinding) ActSongs.this.mViewBinding).layoutControl.setVisibility(0);
                } else {
                    ((ActSongsBinding) ActSongs.this.mViewBinding).layoutControl.setVisibility(8);
                }
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.musicplayer.ActSongs.3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SongInfo item = ActSongs.this.mAdapter.getItem(position);
                if (item == null) {
                    return;
                }
                ActSongs.this.mAdapter.setSelectPosition(position);
                ((FtSongsVM) ActSongs.this.mViewModel).playById(item);
            }
        });
        View view = new View(this);
        view.setLayoutParams(new ViewGroup.LayoutParams(-1, Utils.dip2px(this, 220.0f)));
        this.mAdapter.addFooterView(view);
    }

    private void initSeeKbar() {
        if (this.mViewBinding == 0) {
            return;
        }
        ((ActSongsBinding) this.mViewBinding).sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.device.musicplayer.ActSongs.4
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ((FtSongsVM) ActSongs.this.mViewModel).soundValue.setValue(Integer.valueOf(progress));
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((FtSongsVM) ActSongs.this.mViewModel).setVolume(seekBar.getProgress());
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((FtSongsVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((FtSongsVM) this.mViewModel).playListId = getIntent().getLongExtra(Constants.PLAY_LIST_ID, -1L);
        ((FtSongsVM) this.mViewModel).localSongs.observe(this, new Observer<List<SongInfo>>() { // from class: com.ltech.smarthome.ui.device.musicplayer.ActSongs.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<SongInfo> songInfos) {
                if (songInfos.size() > 0) {
                    ((FtSongsVM) ActSongs.this.mViewModel).checkDeviceState();
                    ActSongs.this.showContent();
                    ActSongs.this.mAdapter.replaceData(songInfos);
                }
            }
        });
        ((FtSongsVM) this.mViewModel).title.observe(this, new Observer<String>() { // from class: com.ltech.smarthome.ui.device.musicplayer.ActSongs.6
            @Override // androidx.lifecycle.Observer
            public void onChanged(String s) {
                ActSongs.this.setTitle(s);
            }
        });
        ((FtSongsVM) this.mViewModel).adapterNotifyItemChanged.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.device.musicplayer.ActSongs.7
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer integer) {
                ActSongs.this.mAdapter.setSelectPosition(integer.intValue());
                if (ActSongs.this.mViewBinding != null) {
                    ((ActSongsBinding) ActSongs.this.mViewBinding).layoutControl.setVisibility(0);
                }
            }
        });
        ((FtSongsVM) this.mViewModel).loadDevice(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((FtSongsVM) this.mViewModel).regDeviceStateLisener();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.you_do_not_have_any_songs_yet));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        showEditDialog();
    }

    private void showEditDialog() {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.rename_the_playlist));
        arrayList.add(getString(R.string.manage_your_songs));
        arrayList.add(getString(R.string.song_order));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.musicplayer.ActSongs$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSongs.this.lambda$showEditDialog$0((Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditDialog$0(Integer num) {
        int intValue = num.intValue();
        if (intValue == 0) {
            showEditNameDialog();
        } else if (intValue == 1) {
            NavUtils.destination(ActSelectSongs.class).withString(Constants.SELECTED_LIST, GsonUtils.toJson(((FtSongsVM) this.mViewModel).getData())).withBoolean(Constants.SONG_EDIT, true).withInt(Constants.PLAY_LIST_ID, (int) ((FtSongsVM) this.mViewModel).playListId).withLong("device_id", ((FtSongsVM) this.mViewModel).deviceId).withDefaultRequestCode().navigation(this);
        } else {
            if (intValue != 2) {
                return;
            }
            NavUtils.destination(ActSelectSongs.class).withString(Constants.SELECTED_LIST, GsonUtils.toJson(((FtSongsVM) this.mViewModel).getData())).withBoolean(Constants.SONG_ORDER, true).withInt(Constants.PLAY_LIST_ID, (int) ((FtSongsVM) this.mViewModel).playListId).withLong("device_id", ((FtSongsVM) this.mViewModel).deviceId).withDefaultRequestCode().navigation(this);
        }
    }

    private void showEditNameDialog() {
        EditDialog.asDefault().setContent(((FtSongsVM) this.mViewModel).title.getValue()).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.musicplayer.ActSongs$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSongs.this.lambda$showEditNameDialog$1((String) obj);
            }
        }).showDialog(getSupportFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$1(String str) {
        ((FtSongsVM) this.mViewModel).rename(str);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        List<SongInfo> list;
        super.onActivityResult(requestCode, resultCode, data);
        if ((resultCode != 3011 && resultCode != 3012) || data == null || (list = (List) GsonUtils.fromJson(data.getStringExtra(Constants.SELECT_SONG_ID), new TypeToken<List<SongInfo>>(this) { // from class: com.ltech.smarthome.ui.device.musicplayer.ActSongs.8
        }.getType())) == null) {
            return;
        }
        ((FtSongsVM) this.mViewModel).totolCount.postValue(Integer.valueOf(list.size()));
        if (list.size() > 0) {
            showContent();
        } else {
            showEmpty();
        }
        this.mAdapter.replaceData(list);
        ((FtSongsVM) this.mViewModel).addSongs2Playlist(list);
    }
}