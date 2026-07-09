package com.ltech.smarthome.ui.control;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSmartSpeakerDetailBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.user.QuerySpeakerResponse;
import com.ltech.smarthome.ui.ifly.WebViewActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSmartSpeakerDetail extends VMActivity<ActSmartSpeakerDetailBinding, ActSmartSpeakerVM> {
    private BaseQuickAdapter<Place, BaseViewHolder> mAdapter;
    private List<Place> placeList = new ArrayList();
    private Place selectPlace;
    private int selectSpeaker;
    private String updatePlatform;
    private String url;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_smart_speaker_detail;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.bind_family));
        setEditString(getString(R.string.bind_tutorial));
        this.selectSpeaker = getIntent().getIntExtra(Constants.SELECT_SPEAKER, -1);
        initInfo();
        initRv();
    }

    private void initInfo() {
        int i = this.selectSpeaker;
        if (i == 1) {
            ((ActSmartSpeakerDetailBinding) this.mViewBinding).ivSmartSpeaker.setImageResource(R.mipmap.ic_xiaodu);
            ((ActSmartSpeakerDetailBinding) this.mViewBinding).tvSmartSpeaker.setText(getString(R.string.spealer_xiaodu));
            this.url = getString(R.string.smart_speak_xiaodu_url);
            this.updatePlatform = String.valueOf(2);
        } else if (i == 2) {
            ((ActSmartSpeakerDetailBinding) this.mViewBinding).ivSmartSpeaker.setImageResource(R.mipmap.ic_xiaoai);
            ((ActSmartSpeakerDetailBinding) this.mViewBinding).tvSmartSpeaker.setText(getString(R.string.spealer_xiaoai));
            this.url = getString(R.string.smart_speak_xiaoai_url);
            this.updatePlatform = String.valueOf(5);
        } else if (i == 3) {
            ((ActSmartSpeakerDetailBinding) this.mViewBinding).ivSmartSpeaker.setImageResource(R.mipmap.ic_alexa);
            ((ActSmartSpeakerDetailBinding) this.mViewBinding).tvSmartSpeaker.setText(getString(R.string.spealer_alexa));
            this.url = getString(R.string.smart_speak_alexa_url);
            this.updatePlatform = String.valueOf(3);
        } else if (i == 4) {
            ((ActSmartSpeakerDetailBinding) this.mViewBinding).ivSmartSpeaker.setImageResource(R.mipmap.ic_google);
            ((ActSmartSpeakerDetailBinding) this.mViewBinding).tvSmartSpeaker.setText(getString(R.string.spealer_google));
            this.url = getString(R.string.smart_speak_google_url);
            this.updatePlatform = String.valueOf(4);
        } else {
            this.url = getString(R.string.smart_speak_tmall_url);
            this.updatePlatform = String.valueOf(1);
        }
        ((ActSmartSpeakerDetailBinding) this.mViewBinding).tvSmartSpeakerTip.setText(getString(R.string.speaker_tip, new Object[]{((ActSmartSpeakerDetailBinding) this.mViewBinding).tvSmartSpeaker.getText().toString()}));
    }

    private void initRv() {
        ((ActSmartSpeakerDetailBinding) this.mViewBinding).rvSmartSpeaker.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView = ((ActSmartSpeakerDetailBinding) this.mViewBinding).rvSmartSpeaker;
        BaseQuickAdapter<Place, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Place, BaseViewHolder>(R.layout.item_select_place) { // from class: com.ltech.smarthome.ui.control.ActSmartSpeakerDetail.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Place item) {
                helper.setText(R.id.tv_name, item.getPlaceName());
                if (item == ActSmartSpeakerDetail.this.selectPlace) {
                    helper.setImageResource(R.id.iv_select, R.mipmap.ic_tick_sel);
                } else {
                    helper.setImageResource(R.id.iv_select, 0);
                }
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.control.ActSmartSpeakerDetail$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActSmartSpeakerDetail.this.lambda$initRv$0(baseQuickAdapter2, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.selectPlace = this.placeList.get(i);
        this.mAdapter.notifyDataSetChanged();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        List<Place> list = ActSmartSpeaker.placeList;
        this.placeList = list;
        this.mAdapter.setNewData(list);
        getCurrentSpeakerInfo();
        ((ActSmartSpeakerVM) this.mViewModel).saveEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.control.ActSmartSpeakerDetail$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSmartSpeakerDetail.this.lambda$startObserve$1((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r1) {
        if (this.selectPlace != null) {
            save();
        } else {
            SmartToast.showCenterShort(getString(R.string.please_bind_family));
        }
    }

    private void getCurrentSpeakerInfo() {
        ((ObservableSubscribeProxy) Injection.net().querySpeakerPlace(this.updatePlatform).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.ActSmartSpeakerDetail$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartSpeakerDetail.this.lambda$getCurrentSpeakerInfo$2((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getCurrentSpeakerInfo$2(List list) throws Exception {
        if (list == null || list.size() <= 0) {
            return;
        }
        long placeid = ((QuerySpeakerResponse) list.get(0)).getPlaceid();
        for (Place place : this.placeList) {
            if (place.getPlaceId() == placeid) {
                this.selectPlace = place;
                this.mAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    private void save() {
        ((ObservableSubscribeProxy) Injection.net().setSpeakerPlace(this.selectPlace.getPlaceId(), this.updatePlatform).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.ActSmartSpeakerDetail$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartSpeakerDetail.this.lambda$save$3((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.control.ActSmartSpeakerDetail$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSmartSpeakerDetail.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.ActSmartSpeakerDetail$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartSpeakerDetail.this.lambda$save$4(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$3(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$4(Object obj) throws Exception {
        SmartToast.showShort(getString(R.string.save_success));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, this.url).navigation(ActivityUtils.getTopActivity());
    }
}