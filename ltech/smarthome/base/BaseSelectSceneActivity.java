package com.ltech.smarthome.base;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Scene;
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
public abstract class BaseSelectSceneActivity extends BaseListActivity<Scene> {
    protected List<Scene> allData = new ArrayList();
    protected long placeId;
    protected Listing<Scene> request;

    protected int getSceneType() {
        return 3;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.rv_content;
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<Scene> getList() {
        return new ArrayList();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        this.request.refresh();
    }

    @Override // com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.select_scene));
        ((ActSelectBinding) this.mViewBinding).searchBar.searchEdtTxt.setHint(getString(R.string.app_str_search_scene_name));
        setBackImage(R.mipmap.icon_back);
        ((ActSelectBinding) this.mViewBinding).searchBar.ivClean.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.base.BaseSelectSceneActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ActSelectBinding) BaseSelectSceneActivity.this.mViewBinding).searchBar.searchEdtTxt.setText("");
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        this.placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        int intExtra = getIntent().getIntExtra(Constants.SCENE_TYPE, -1);
        if (intExtra != -1) {
            this.request = Injection.repo().scene().getSceneList(this, this.placeId, intExtra);
        } else {
            this.request = Injection.repo().scene().getSceneList(this, this.placeId, getSceneType());
        }
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    public void search(final String keyword) {
        ((ObservableSubscribeProxy) Observable.create(new ObservableOnSubscribe<List<Scene>>() { // from class: com.ltech.smarthome.base.BaseSelectSceneActivity.3
            boolean inSearchMode = false;

            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<List<Scene>> emitter) throws Exception {
                ArrayList arrayList = new ArrayList();
                boolean z = keyword.length() > 0;
                this.inSearchMode = z;
                if (z) {
                    for (Scene scene : BaseSelectSceneActivity.this.allData) {
                        if (scene.getSceneName().toLowerCase().contains(keyword.toLowerCase())) {
                            arrayList.add(scene);
                        }
                    }
                } else {
                    arrayList.addAll(BaseSelectSceneActivity.this.allData);
                }
                emitter.onNext(arrayList);
                emitter.onComplete();
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new DisposableObserver<List<Scene>>() { // from class: com.ltech.smarthome.base.BaseSelectSceneActivity.2
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onNext(List<Scene> items) {
                BaseSelectSceneActivity.this.mAdapter.replaceData(items);
            }
        });
    }
}