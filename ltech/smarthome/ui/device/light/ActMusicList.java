package com.ltech.smarthome.ui.device.light;

import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActMusicListBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.preference_bean.MusicBean;
import com.ltech.smarthome.utils.NavUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class ActMusicList extends BaseNormalActivity<ActMusicListBinding> {
    private String currentMusicPath = "";
    private BaseQuickAdapter<MusicBean, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_music_list;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.no_song).emptyImageRes(R.mipmap.pic_empty_6));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.music_list));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.input_music));
        BaseQuickAdapter<MusicBean, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<MusicBean, BaseViewHolder>(R.layout.item_music, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.light.ActMusicList.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, MusicBean item) {
                helper.setText(R.id.tv_music_name, item.getTitle()).setText(R.id.tv_artist, item.getArtist());
                helper.setImageResource(R.id.iv_select, R.mipmap.ic_music_playing);
                helper.setVisible(R.id.iv_select, item.getPath().equals(ActMusicList.this.currentMusicPath));
                helper.setGone(R.id.iv_delete, true).setImageResource(R.id.iv_delete, R.mipmap.ic_list_del).setGone(R.id.v_delete, true).addOnClickListener(R.id.v_delete);
                ((AppCompatTextView) helper.getView(R.id.tv_music_name)).getPaint().setFakeBoldText(true);
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActMusicList$$ExternalSyntheticLambda2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActMusicList.lambda$initView$0(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActMusicList$$ExternalSyntheticLambda3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActMusicList.lambda$initView$1(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActMusicListBinding) this.mViewBinding).rvMusic);
        ((ActMusicListBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.light.ActMusicList$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActMusicList.lambda$initView$2((View) obj);
            }
        }));
    }

    static /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        Injection.player().playAudio(i);
        Injection.player().startRecord();
    }

    static /* synthetic */ void lambda$initView$1(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        List<MusicBean> value = Injection.player().getPlayList().getValue();
        if (value == null || value.isEmpty()) {
            return;
        }
        value.remove(i);
        Injection.player().savePlayList(value);
    }

    static /* synthetic */ void lambda$initView$2(View view) {
        if (view.getId() == R.id.v_play_all) {
            if (Injection.player().getPlayModeLiveData().getValue().intValue() == 2) {
                Injection.player().playRandom();
                Injection.player().startRecord();
            } else {
                Injection.player().changeMode(0);
                Injection.player().playAudio(0);
                Injection.player().startRecord();
            }
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        Injection.player().getChangePlayMusic().observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActMusicList$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMusicList.this.lambda$startObserve$3((MusicBean) obj);
            }
        });
        Injection.player().getPlayList().observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActMusicList$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMusicList.this.lambda$startObserve$4((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(MusicBean musicBean) {
        if (TextUtils.isEmpty(musicBean.getPath()) || musicBean.getPath().equals(this.currentMusicPath)) {
            return;
        }
        this.currentMusicPath = musicBean.getPath();
        this.mAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(List list) {
        if (list.isEmpty()) {
            showEmpty();
            Injection.player().stopRecord();
            Injection.player().reset();
        } else {
            showContent();
            this.mAdapter.setNewData(list);
            ((ActMusicListBinding) this.mViewBinding).tvTotal.setText(String.format(Locale.US, "%s(%d)", getString(R.string.play_all), Integer.valueOf(list.size())));
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavUtils.destination(ActAddMusic.class).navigation(this);
    }
}