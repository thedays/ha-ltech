package com.ltech.smarthome.ui.control;

import android.graphics.Rect;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSmartSpeakerBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.net.response.user.QuerySpeakerResponse;
import com.ltech.smarthome.ui.control.ActSmartSpeakerVM;
import com.ltech.smarthome.ui.device.sonos.ActSonosAuth;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActThirdPart extends VMActivity<ActSmartSpeakerBinding, ActSmartSpeakerVM> {
    public static List<Place> placeList = new ArrayList();
    private BaseQuickAdapter<ActSmartSpeakerVM.SpeakerInfo, BaseViewHolder> mAdapter;
    private List<ActSmartSpeakerVM.SpeakerInfo> speakerInfoList = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_smart_speaker;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.smart_speaker));
        initRv();
    }

    private void initRv() {
        ((ActSmartSpeakerBinding) this.mViewBinding).rvSmartSpeaker.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView = ((ActSmartSpeakerBinding) this.mViewBinding).rvSmartSpeaker;
        BaseQuickAdapter<ActSmartSpeakerVM.SpeakerInfo, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ActSmartSpeakerVM.SpeakerInfo, BaseViewHolder>(R.layout.item_smart_speaker) { // from class: com.ltech.smarthome.ui.control.ActThirdPart.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, ActSmartSpeakerVM.SpeakerInfo item) {
                helper.setText(R.id.tv_main, item.name).setImageResource(R.id.ic_speaker, item.iconRes);
                if (item.place != null) {
                    helper.setText(R.id.tv_tip, item.place.getPlaceName()).setTextColor(R.id.tv_tip, ActThirdPart.this.getResources().getColor(R.color.color_text_black));
                } else {
                    helper.setText(R.id.tv_tip, ActThirdPart.this.getString(R.string.please_bind_family)).setTextColor(R.id.tv_tip, ActThirdPart.this.getResources().getColor(R.color.item_music_list_color));
                }
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        ((ActSmartSpeakerBinding) this.mViewBinding).rvSmartSpeaker.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.control.ActThirdPart.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, ConvertUtils.dp2px(8.0f), 0, ConvertUtils.dp2px(8.0f));
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.control.ActThirdPart$$ExternalSyntheticLambda2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActThirdPart.this.lambda$initRv$0(baseQuickAdapter2, view, i);
            }
        });
        this.speakerInfoList.add(new ActSmartSpeakerVM.SpeakerInfo(getString(R.string.app_str_matter), R.mipmap.logo_matter, 0));
        this.speakerInfoList.add(new ActSmartSpeakerVM.SpeakerInfo(getString(R.string.app_str_sonos), R.mipmap.sonos_logo, 0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        getSonosUrl();
    }

    private void getSonosUrl() {
        showLoadingDialog("");
        ((ObservableSubscribeProxy) Injection.net().getSonosUrl(Injection.repo().home().getSelPlace().getPlaceId()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.ActThirdPart$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActThirdPart.this.lambda$getSonosUrl$1((ResultBean) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.control.ActThirdPart$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActThirdPart.this.lambda$getSonosUrl$2((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSonosUrl$1(ResultBean resultBean) throws Exception {
        if (resultBean != null) {
            if (resultBean.getRet() == 0) {
                NavUtils.destination(ActSonosAuth.class).withString(Constants.WS_URL, (String) resultBean.getData()).navigation(ActivityUtils.getTopActivity());
            } else {
                if (resultBean.getRet() == 102018) {
                    ((ActSmartSpeakerVM) this.mViewModel).checkSonosSpeaker();
                    return;
                }
                SmartToast.showShort(StringUtils.getString(R.string.search_fail));
            }
        }
        dismissLoadingDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSonosUrl$2(Throwable th) throws Exception {
        dismissLoadingDialog();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActSmartSpeakerVM) this.mViewModel).mPlaceListing = Injection.repo().home().getPlaceList(this);
        handleData(((ActSmartSpeakerVM) this.mViewModel).mPlaceListing, new IAction() { // from class: com.ltech.smarthome.ui.control.ActThirdPart$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActThirdPart.this.lambda$startObserve$3((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(List list) {
        placeList = list;
        getSpeakerInfo();
    }

    private void getSpeakerInfo() {
        ((ObservableSubscribeProxy) Injection.net().querySpeakerPlace("0").delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.ActThirdPart$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActThirdPart.this.lambda$getSpeakerInfo$4((List) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.control.ActThirdPart$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActThirdPart.this.lambda$getSpeakerInfo$5((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSpeakerInfo$4(List list) throws Exception {
        if (list != null && list.size() > 0) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                QuerySpeakerResponse querySpeakerResponse = (QuerySpeakerResponse) it.next();
                Iterator<Place> it2 = placeList.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        Place next = it2.next();
                        if (next.getPlaceId() == querySpeakerResponse.getPlaceid()) {
                            if (Integer.parseInt(querySpeakerResponse.getPlatform()) == 6) {
                                this.speakerInfoList.get(1).place = next;
                            }
                        }
                    }
                }
            }
        }
        this.mAdapter.setNewData(this.speakerInfoList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSpeakerInfo$5(Throwable th) throws Exception {
        this.mAdapter.setNewData(this.speakerInfoList);
    }
}