package com.ltech.smarthome.base;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.databinding.ActSelectSceneAllBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.ViewHelpUtil;
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
public abstract class BaseSelectSceneAllActivity extends BaseNormalActivity<ActSelectSceneAllBinding> implements TextWatcher {
    protected int curSceneType;
    protected BaseItemDraggableAdapter<Scene, BaseViewHolder> mAdapter;
    protected long placeId;
    protected Listing<Scene> request;
    protected List<Scene> allData = new ArrayList();
    protected List<Scene> dataList = new ArrayList();
    protected MutableLiveData<Integer> selectCountLiveData = new MutableLiveData<>(0);
    protected boolean selectAll = false;

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    protected void changeTab() {
    }

    protected abstract void convertView(BaseViewHolder helper, Scene item);

    protected int getSceneType() {
        return 3;
    }

    protected abstract int itemLayout();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.rv_content;
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_scene_all;
    }

    protected List<Scene> getList() {
        return new ArrayList();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        this.request.refresh();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((ActSelectSceneAllBinding) this.mViewBinding).ivBack.setImageResource(R.mipmap.icon_back);
        setUpData();
        BaseItemDraggableAdapter<Scene, BaseViewHolder> baseItemDraggableAdapter = new BaseItemDraggableAdapter<Scene, BaseViewHolder>(itemLayout(), this.dataList) { // from class: com.ltech.smarthome.base.BaseSelectSceneAllActivity.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Scene item) {
                BaseSelectSceneAllActivity.this.convertView(helper, item);
            }
        };
        this.mAdapter = baseItemDraggableAdapter;
        baseItemDraggableAdapter.bindToRecyclerView(((ActSelectSceneAllBinding) this.mViewBinding).rvContent);
        ((ActSelectSceneAllBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectSceneAllBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActSelectSceneAllBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
        ((ActSelectSceneAllBinding) this.mViewBinding).searchBar.searchEdtTxt.setHint(getString(R.string.app_str_search_scene_name));
        ((ActSelectSceneAllBinding) this.mViewBinding).searchBar.searchEdtTxt.addTextChangedListener(this);
        ((ActSelectSceneAllBinding) this.mViewBinding).searchBar.ivClean.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.base.BaseSelectSceneAllActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ActSelectSceneAllBinding) BaseSelectSceneAllActivity.this.mViewBinding).searchBar.searchEdtTxt.setText("");
            }
        });
        TabLayout.Tab text = ((ActSelectSceneAllBinding) this.mViewBinding).tabs.newTab().setText(R.string.local_scene);
        ViewHelpUtil.createTabCustomView(this, text, getString(R.string.local_scene), true);
        TabLayout.Tab text2 = ((ActSelectSceneAllBinding) this.mViewBinding).tabs.newTab().setText(R.string.cloud_scene);
        ViewHelpUtil.createTabCustomView(this, text2, getString(R.string.cloud_scene), false);
        ((ActSelectSceneAllBinding) this.mViewBinding).tabs.addTab(text);
        ((ActSelectSceneAllBinding) this.mViewBinding).tabs.addTab(text2);
        ((ActSelectSceneAllBinding) this.mViewBinding).tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.base.BaseSelectSceneAllActivity.3
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                ViewHelpUtil.createTabCustomView(BaseSelectSceneAllActivity.this, tab, tab.getText().toString(), true);
                if (tab.getPosition() == 0) {
                    BaseSelectSceneAllActivity.this.curSceneType = 2;
                } else {
                    BaseSelectSceneAllActivity.this.curSceneType = 1;
                }
                BaseSelectSceneAllActivity.this.mAdapter.replaceData(BaseSelectSceneAllActivity.this.getSceneByType());
                BaseSelectSceneAllActivity.this.changeTab();
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
                ViewHelpUtil.createTabCustomView(BaseSelectSceneAllActivity.this, tab, tab.getText().toString(), false);
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyImageRes(R.mipmap.pic_empty_1).emptyStringRes(R.string.no_scence));
    }

    protected List<Scene> getSceneByType() {
        ArrayList arrayList = new ArrayList();
        for (Scene scene : this.allData) {
            if (scene.getSceneType() == this.curSceneType) {
                arrayList.add(scene);
            }
        }
        return arrayList;
    }

    protected void setUpData() {
        if (this.dataList == null) {
            this.dataList = new ArrayList();
        }
        this.dataList.addAll(getList());
    }

    protected void setDataList(List<Scene> list) {
        if (this.dataList == null) {
            this.dataList = new ArrayList();
        }
        this.dataList.clear();
        this.dataList.addAll(list);
        this.mAdapter.replaceData(list);
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
        search(editable.toString());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        this.placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        int intExtra = getIntent().getIntExtra(Constants.SCENE_TYPE, -1);
        if (intExtra != -1) {
            ((ActSelectSceneAllBinding) this.mViewBinding).tabs.setVisibility(8);
            ((ActSelectSceneAllBinding) this.mViewBinding).title.setVisibility(0);
            ((ActSelectSceneAllBinding) this.mViewBinding).title.setText(getString(intExtra == 2 ? R.string.select_local_scene : R.string.select_scene));
            this.request = Injection.repo().scene().getSceneList(this, this.placeId, intExtra);
            return;
        }
        this.request = Injection.repo().scene().getSceneList(this, this.placeId, getSceneType());
    }

    public void search(final String keyword) {
        ((ObservableSubscribeProxy) Observable.create(new ObservableOnSubscribe<List<Scene>>() { // from class: com.ltech.smarthome.base.BaseSelectSceneAllActivity.5
            boolean inSearchMode = false;

            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<List<Scene>> emitter) throws Exception {
                ArrayList arrayList = new ArrayList();
                boolean z = keyword.length() > 0;
                this.inSearchMode = z;
                if (z) {
                    for (Scene scene : BaseSelectSceneAllActivity.this.allData) {
                        if (scene.getSceneName().toLowerCase().contains(keyword.toLowerCase())) {
                            arrayList.add(scene);
                        }
                    }
                } else {
                    arrayList.addAll(BaseSelectSceneAllActivity.this.allData);
                }
                emitter.onNext(arrayList);
                emitter.onComplete();
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new DisposableObserver<List<Scene>>() { // from class: com.ltech.smarthome.base.BaseSelectSceneAllActivity.4
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onNext(List<Scene> items) {
                BaseSelectSceneAllActivity.this.mAdapter.replaceData(items);
            }
        });
    }
}