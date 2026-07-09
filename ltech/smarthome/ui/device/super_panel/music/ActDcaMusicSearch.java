package com.ltech.smarthome.ui.device.super_panel.music;

import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSearchMusicBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.SongListItemInfo;
import com.ltech.smarthome.ui.device.e6knob.E6Helper$$ExternalSyntheticBackport0;
import com.ltech.smarthome.ui.device.super_panel.music.manager.FlowLayoutManager;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.smart.message.utils.StringUtils;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class ActDcaMusicSearch extends VMActivity<ActSearchMusicBinding, ActDcaMusicSearchVM> implements View.OnKeyListener, TextWatcher {
    private BaseQuickAdapter<String, BaseViewHolder> historyAdapter;
    private List<String> historyList;
    private BaseQuickAdapter<SongListItemInfo, BaseViewHolder> resultAdapter;

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_search_music;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((ActDcaMusicSearchVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActDcaMusicSearchVM) this.mViewModel).controlDevice = Injection.repo().device().getDeviceById(((ActDcaMusicSearchVM) this.mViewModel).controlId);
        ((ActDcaMusicSearchVM) this.mViewModel).deviceMac = getIntent().getStringExtra(Constants.MAC_ADDRESS);
        ((ActSearchMusicBinding) this.mViewBinding).searchBar.searchEdtTxt.setHint(R.string.search);
        ((ActSearchMusicBinding) this.mViewBinding).searchBar.searchEdtTxt.setOnKeyListener(this);
        ((ActSearchMusicBinding) this.mViewBinding).searchBar.searchEdtTxt.addTextChangedListener(this);
        ((ActSearchMusicBinding) this.mViewBinding).searchBar.layoutSearch.setBackgroundResource(R.drawable.shape_search_editxt_music_bg);
        ((ActSearchMusicBinding) this.mViewBinding).searchBar.cancelBtn.setVisibility(0);
        ((ActSearchMusicBinding) this.mViewBinding).searchBar.cancelBtn.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicSearch.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ActDcaMusicSearch.this.finishActivity();
            }
        });
        ((ActSearchMusicBinding) this.mViewBinding).searchBar.ivClean.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicSearch.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ActSearchMusicBinding) ActDcaMusicSearch.this.mViewBinding).searchBar.searchEdtTxt.setText("");
            }
        });
        initHistoryRv();
        initResultRv();
    }

    private void initHistoryRv() {
        ((ActSearchMusicBinding) this.mViewBinding).rvHistory.setLayoutManager(new FlowLayoutManager());
        RecyclerView recyclerView = ((ActSearchMusicBinding) this.mViewBinding).rvHistory;
        BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(this, R.layout.item_music_history) { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicSearch.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, String s) {
                helper.setText(R.id.tv_history, s);
            }
        };
        this.historyAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        List<String> stringList = StringUtils.getStringList(SharedPreferenceUtil.queryValue(Constants.MUSIC_HISTORY));
        this.historyList = stringList;
        this.historyAdapter.setNewData(stringList);
        this.historyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicSearch$$ExternalSyntheticLambda3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActDcaMusicSearch.this.lambda$initHistoryRv$0(baseQuickAdapter2, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initHistoryRv$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        ((ActSearchMusicBinding) this.mViewBinding).searchBar.searchEdtTxt.setText(this.historyList.get(i));
        searchAndSaveText(this.historyList.get(i), false);
    }

    private void initResultRv() {
        ((ActSearchMusicBinding) this.mViewBinding).rvResult.setLayoutManager(new GridLayoutManager(this, 1));
        RecyclerView recyclerView = ((ActSearchMusicBinding) this.mViewBinding).rvResult;
        BaseQuickAdapter<SongListItemInfo, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<SongListItemInfo, BaseViewHolder>(R.layout.item_music_search) { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicSearch.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, SongListItemInfo item) {
                helper.setText(R.id.tv_main, ActDcaMusicSearch.this.highlight(item.getName(), item.getHighlightStr()));
                if (!item.getSinger().isEmpty()) {
                    helper.setText(R.id.tv_sub, ActDcaMusicSearch.this.highlight(item.getSinger(), item.getHighlightStr()));
                } else {
                    helper.setText(R.id.tv_sub, R.string.unknown_singer);
                }
            }
        };
        this.resultAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        this.resultAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicSearch$$ExternalSyntheticLambda4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActDcaMusicSearch.this.lambda$initResultRv$1(baseQuickAdapter2, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initResultRv$1(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        NavUtils.destination(ActDcaMusicDetail.class).withLong(Constants.CONTROL_ID, ((ActDcaMusicSearchVM) this.mViewModel).controlId).withBoolean(Constants.MUSIC_NEED_ORDER_MUSIC, true).withString(Constants.MUSIC_SONG_ID, ((ActDcaMusicSearchVM) this.mViewModel).itemInfoList.get(i).getSongId()).withBoolean(Constants.MUSIC_SONG_IS_LOCAL, false).withString(Constants.MAC_ADDRESS, ((ActDcaMusicSearchVM) this.mViewModel).deviceMac).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
        ActivityUtils.getTopActivity().overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActDcaMusicSearchVM) this.mViewModel).getSearchList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicSearch$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDcaMusicSearch.this.lambda$startObserve$2((Void) obj);
            }
        });
        ((ActDcaMusicSearchVM) this.mViewModel).historyDeleteEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicSearch$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDcaMusicSearch.this.lambda$startObserve$3((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        if (((ActDcaMusicSearchVM) this.mViewModel).songNewVoiceBoxList.size() == 0) {
            showEmpty();
        } else {
            showContent();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r3) {
        SharedPreferenceUtil.edit().keepShared(Constants.MUSIC_HISTORY, "");
        this.historyList.clear();
        this.historyAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SpannableString highlight(String text, String target) {
        SpannableString spannableString = new SpannableString(text);
        try {
            Matcher matcher = Pattern.compile(target).matcher(text);
            while (matcher.find()) {
                spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.wave_color_4)), matcher.start(), matcher.end(), 33);
            }
            return spannableString;
        } catch (Exception e) {
            e.printStackTrace();
            return spannableString;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showContent() {
        super.showContent();
        if (this.resultAdapter != null) {
            ((ActSearchMusicBinding) this.mViewBinding).tvMusicNumber.setText("(" + ((ActDcaMusicSearchVM) this.mViewModel).itemInfoList.size() + ")");
            this.resultAdapter.setNewData(((ActDcaMusicSearchVM) this.mViewModel).itemInfoList);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyImageRes(R.mipmap.pic_empty_1).emptyStringRes(R.string.music_search_empty));
    }

    private void searchAndSaveText(String s, boolean isSave) {
        ((ActDcaMusicSearchVM) this.mViewModel).showHistoryLayout.setValue(false);
        ((ActDcaMusicSearchVM) this.mViewModel).search(s);
        if (isSave) {
            this.historyList.add(0, s);
            SharedPreferenceUtil.edit().keepShared(Constants.MUSIC_HISTORY, E6Helper$$ExternalSyntheticBackport0.m(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP, this.historyList));
        }
    }

    @Override // android.view.View.OnKeyListener
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i != 66 || keyEvent.getAction() != 0) {
            return false;
        }
        EditText editText = (EditText) view;
        if (!editText.getText().toString().isEmpty()) {
            searchAndSaveText(editText.getText().toString(), true);
        }
        return true;
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
        if (editable.toString().isEmpty()) {
            showContent();
            ((ActDcaMusicSearchVM) this.mViewModel).showHistoryLayout.setValue(true);
            this.historyAdapter.notifyDataSetChanged();
        }
    }
}