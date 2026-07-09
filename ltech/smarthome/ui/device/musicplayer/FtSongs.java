package com.ltech.smarthome.ui.device.musicplayer;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.adapter.SongsAdapter;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.databinding.FtSongsBinding;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.SongInfo;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.Utils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class FtSongs extends VMFragment<FtSongsBinding, FtSongsVM> {
    private SongsAdapter mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_songs;
    }

    public static FtSongs create(long controlId) {
        FtSongs ftSongs = new FtSongs();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.CONTROL_ID, controlId);
        ftSongs.setArguments(bundle);
        return ftSongs;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        showEmpty();
        initListView();
        initSeeKbar();
    }

    private void initSeeKbar() {
        if (this.mViewBinding == 0) {
            return;
        }
        ((FtSongsBinding) this.mViewBinding).sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.device.musicplayer.FtSongs.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ((FtSongsVM) FtSongs.this.mViewModel).soundValue.setValue(Integer.valueOf(progress));
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((FtSongsVM) FtSongs.this.mViewModel).setVolume(seekBar.getProgress());
            }
        });
    }

    private void initListView() {
        if (this.mViewBinding == 0) {
            return;
        }
        ((FtSongsBinding) this.mViewBinding).layoutControl.setOnClickListener(new View.OnClickListener(this) { // from class: com.ltech.smarthome.ui.device.musicplayer.FtSongs.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        ((FtSongsBinding) this.mViewBinding).recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView recyclerView = ((FtSongsBinding) this.mViewBinding).recyclerView;
        SongsAdapter songsAdapter = new SongsAdapter(R.layout.item_song_info);
        this.mAdapter = songsAdapter;
        recyclerView.setAdapter(songsAdapter);
        ((FtSongsBinding) this.mViewBinding).recyclerView.setHasFixedSize(true);
        this.mAdapter.bindToRecyclerView(((FtSongsBinding) this.mViewBinding).recyclerView);
        ((FtSongsBinding) this.mViewBinding).recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.ltech.smarthome.ui.device.musicplayer.FtSongs.3
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView2, int newState) {
                super.onScrollStateChanged(recyclerView2, newState);
                if (FtSongs.this.mViewBinding != null) {
                    if (newState == 0) {
                        ((FtSongsBinding) FtSongs.this.mViewBinding).layoutControl.setVisibility(0);
                    } else {
                        ((FtSongsBinding) FtSongs.this.mViewBinding).layoutControl.setVisibility(8);
                    }
                }
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.musicplayer.FtSongs.4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SongInfo item = FtSongs.this.mAdapter.getItem(position);
                if (item == null) {
                    return;
                }
                FtSongs.this.mAdapter.setSelectPosition(position);
                ((FtSongsVM) FtSongs.this.mViewModel).playById(item);
            }
        });
        View view = new View(getContext());
        view.setLayoutParams(new ViewGroup.LayoutParams(-1, Utils.dip2px(getContext(), 220.0f)));
        this.mAdapter.addFooterView(view);
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        super.startObserve();
        if (getArguments() == null) {
            return;
        }
        ((FtSongsVM) this.mViewModel).controlId = getArguments().getLong(Constants.CONTROL_ID, -1L);
        ((FtSongsVM) this.mViewModel).controlDevice.observe(this, new Observer<Device>() { // from class: com.ltech.smarthome.ui.device.musicplayer.FtSongs.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(Device device) {
                ((FtSongsVM) FtSongs.this.mViewModel).setCanSync(true);
            }
        });
        ((FtSongsVM) this.mViewModel).songData.observe(this, new Observer<SongInfo>() { // from class: com.ltech.smarthome.ui.device.musicplayer.FtSongs.6
            @Override // androidx.lifecycle.Observer
            public void onChanged(SongInfo songInfo) {
                FtSongs.this.showContent();
                FtSongs.this.mAdapter.addData((SongsAdapter) songInfo);
            }
        });
        ((FtSongsVM) this.mViewModel).cleanSongsItem.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.musicplayer.FtSongs.7
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                FtSongs.this.mAdapter.replaceData(new ArrayList());
            }
        });
        ((FtSongsVM) this.mViewModel).adapterNotifyItemChanged.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.device.musicplayer.FtSongs.8
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer integer) {
                FtSongs.this.mAdapter.setSelectPosition(integer.intValue());
            }
        });
        ((FtSongsVM) this.mViewModel).localSongs.observe(this, new Observer<List<SongInfo>>() { // from class: com.ltech.smarthome.ui.device.musicplayer.FtSongs.9
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<SongInfo> songInfos) {
                if (songInfos.size() > 0) {
                    ((FtSongsVM) FtSongs.this.mViewModel).checkDeviceState();
                    FtSongs.this.showContent();
                    FtSongs.this.mAdapter.replaceData(songInfos);
                }
            }
        });
        ((FtSongsVM) this.mViewModel).loadDevice(this);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        ((FtSongsVM) this.mViewModel).regDeviceStateLisener();
        if (this.mAdapter.getData().size() > 0) {
            ((FtSongsVM) this.mViewModel).checkDeviceState();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void onEmptyTry() {
        super.onEmptyTry();
        ((FtSongsVM) this.mViewModel).setFirst(true);
        ((FtSongsVM) this.mViewModel).loadSongs();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.you_do_not_have_any_songs_yet).emptyTryStringRes(R.string.sync_songs));
    }
}