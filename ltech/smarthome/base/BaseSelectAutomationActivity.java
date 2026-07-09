package com.ltech.smarthome.base;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Automation;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.observers.DisposableObserver;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class BaseSelectAutomationActivity extends BaseListActivity<Automation> {
    protected List<Automation> allData = new ArrayList();
    protected boolean isLocalAutomation;
    protected long placeId;
    protected Listing<Automation> request;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.rv_content;
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<Automation> getList() {
        return new ArrayList();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        Listing<Automation> listing = this.request;
        if (listing != null) {
            listing.retry();
        }
    }

    @Override // com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.IS_LOCAL_AUTOMATION, false);
        this.isLocalAutomation = booleanExtra;
        setTitle(getString(booleanExtra ? R.string.select_local_automation : R.string.action_automation_select));
        ((ActSelectBinding) this.mViewBinding).layoutSearch.setVisibility(0);
        ((ActSelectBinding) this.mViewBinding).searchBar.searchEdtTxt.setHint(getString(R.string.app_str_search_automation_name));
        ((ActSelectBinding) this.mViewBinding).searchBar.ivClean.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.base.BaseSelectAutomationActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ActSelectBinding) BaseSelectAutomationActivity.this.mViewBinding).searchBar.searchEdtTxt.setText("");
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        this.placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        if (!this.isLocalAutomation) {
            this.request = Injection.repo().auto().getAutomationList(this, this.placeId);
        } else {
            this.request = Injection.repo().auto().getAutomationList(this, this.placeId, 2);
        }
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    public void search(final String keyword) {
        ((ObservableSubscribeProxy) Observable.create(new ObservableOnSubscribe<List<Automation>>() { // from class: com.ltech.smarthome.base.BaseSelectAutomationActivity.3
            boolean inSearchMode = false;

            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<List<Automation>> emitter) throws Exception {
                ArrayList arrayList = new ArrayList();
                boolean z = keyword.length() > 0;
                this.inSearchMode = z;
                if (z) {
                    for (Automation automation : BaseSelectAutomationActivity.this.allData) {
                        if (automation.getName().toLowerCase().contains(keyword.toLowerCase())) {
                            arrayList.add(automation);
                        }
                    }
                } else {
                    arrayList.addAll(BaseSelectAutomationActivity.this.allData);
                }
                emitter.onNext(arrayList);
                emitter.onComplete();
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new DisposableObserver<List<Automation>>() { // from class: com.ltech.smarthome.base.BaseSelectAutomationActivity.2
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onNext(List<Automation> items) {
                BaseSelectAutomationActivity.this.mAdapter.replaceData(items);
            }
        });
    }
}