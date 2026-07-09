package com.ltech.smarthome.ui.device.musicplayer;

import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActTrigCurtainOpenDirSetBinding;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.view.HorizontalSeekBar;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActMusicPlayerModeSelect extends BaseNormalActivity<ActTrigCurtainOpenDirSetBinding> {
    MultipleItemRvAdapter<State, BaseViewHolder> keyAdapter;
    List<State> keyItemList;
    protected int selectPosition = 1;
    private int lastSelectPosition = 1;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_trig_curtain_open_dir_set;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_action));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        this.keyItemList = getContentList();
        MultipleItemRvAdapter<State, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<State, BaseViewHolder>(this.keyItemList) { // from class: com.ltech.smarthome.ui.device.musicplayer.ActMusicPlayerModeSelect.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(State onOffState) {
                return onOffState.getType();
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new BaseItemProvider<State, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.musicplayer.ActMusicPlayerModeSelect.1.1
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_select;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 1;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, State data, int position) {
                        helper.setText(R.id.tv_name, data.getName());
                        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                        if (helper.getAdapterPosition() == ActMusicPlayerModeSelect.this.selectPosition) {
                            helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_sel).setGone(R.id.iv_select, true);
                        } else {
                            helper.setGone(R.id.iv_select, false);
                        }
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<State, BaseViewHolder>(this) { // from class: com.ltech.smarthome.ui.device.musicplayer.ActMusicPlayerModeSelect.1.2
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_select_music_player_volume;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 2;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(final BaseViewHolder helper, final State data, int position) {
                        helper.setText(R.id.tv_name, data.getName());
                        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                        helper.setText(R.id.tv_brt, data.getMainValue() + "").setEnabled(R.id.sb_brt, true).setGone(R.id.layout_brt, true);
                        ((HorizontalSeekBar) helper.getView(R.id.sb_brt)).setProgress(data.getMainValue());
                        Rect bounds = ((HorizontalSeekBar) helper.getView(R.id.sb_brt)).getProgressDrawable().getBounds();
                        ((HorizontalSeekBar) helper.getView(R.id.sb_brt)).setProgressDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.style_seekbar_red));
                        ((HorizontalSeekBar) helper.getView(R.id.sb_brt)).getProgressDrawable().setBounds(bounds);
                        ((HorizontalSeekBar) helper.getView(R.id.sb_brt)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(this) { // from class: com.ltech.smarthome.ui.device.musicplayer.ActMusicPlayerModeSelect.1.2.1
                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                data.setMainValue(progress);
                                helper.setText(R.id.tv_brt, data.getMainValue() + "");
                            }

                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public void onStopTrackingTouch(SeekBar seekBar) {
                                data.setMainValue(seekBar.getProgress());
                                helper.setText(R.id.tv_brt, data.getMainValue() + "");
                            }
                        });
                    }
                });
            }
        };
        this.keyAdapter = multipleItemRvAdapter;
        multipleItemRvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.musicplayer.ActMusicPlayerModeSelect$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActMusicPlayerModeSelect.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
        this.keyAdapter.finishInitialize();
        this.keyAdapter.bindToRecyclerView(((ActTrigCurtainOpenDirSetBinding) this.mViewBinding).rvContent);
        ((ActTrigCurtainOpenDirSetBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this, 1, false));
        ((ActTrigCurtainOpenDirSetBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int i2;
        if (i == baseQuickAdapter.getItemCount() - 1 || (i2 = this.selectPosition) == i) {
            return;
        }
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

    private List<State> getContentList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new State(1, getString(R.string.music_player_play_once), 4));
        arrayList.add(new State(1, getString(R.string.music_player_loop_playback), 2));
        if (getIntent().getBooleanExtra(Constants.OPEN_SHUFFLE_MENU, true)) {
            arrayList.add(new State(1, getString(R.string.music_player_shuffle), 3));
        }
        arrayList.add(new State(2, getString(R.string.volume), SharedPreferenceUtil.queryIntValue(Constants.MUSIC_PLAYER_VOLUME)));
        return arrayList;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        Intent intent = new Intent();
        int mainValue = this.keyAdapter.getData().get(this.selectPosition).getMainValue();
        int mainValue2 = this.keyAdapter.getData().get(this.keyAdapter.getItemCount() - 1).getMainValue();
        intent.putExtra(Constants.MUSIC_PLAYER_MODE, mainValue);
        intent.putExtra(Constants.MUSIC_PLAYER_VOLUME, mainValue2);
        setResult(3015, intent);
        finishActivity();
    }

    private static final class State {
        private static final int TYPE_DEFAULT = 1;
        private static final int TYPE_VOLUME = 2;
        private int mainValue;
        private String name;
        private int type;

        State(int type, String name, int mainValue) {
            this.type = type;
            this.name = name;
            this.mainValue = mainValue;
        }

        int getType() {
            return this.type;
        }

        String getName() {
            return this.name;
        }

        int getMainValue() {
            return this.mainValue;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setMainValue(int mainValue) {
            this.mainValue = mainValue;
        }
    }
}