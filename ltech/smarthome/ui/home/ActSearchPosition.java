package com.ltech.smarthome.ui.home;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSearchPositionBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.response.map.PlaceResponse;
import com.ltech.smarthome.utils.Constants;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes4.dex */
public class ActSearchPosition extends BaseNormalActivity<ActSearchPositionBinding> {
    private String city;
    private BaseQuickAdapter<PlaceResponse.PoiItem, BaseViewHolder> mAdapter;
    private MutableLiveData<List<PlaceResponse.PoiItem>> resultList = new MutableLiveData<>(new ArrayList());
    private TextWatcher mTextWatcher = new TextWatcher() { // from class: com.ltech.smarthome.ui.home.ActSearchPosition.3
        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(s)) {
                ActSearchPosition.this.resultList.setValue(new ArrayList());
            } else {
                ActSearchPosition.this.doSearchQuery(s.toString());
            }
        }
    };

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_search_position;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.search_position));
        String stringExtra = getIntent().getStringExtra(Constants.CITY);
        this.city = stringExtra;
        if (TextUtils.isEmpty(stringExtra)) {
            this.city = "北京";
        }
        doSearchQuery(getIntent().getStringExtra(Constants.ADDRESS));
        ((ActSearchPositionBinding) this.mViewBinding).etSearch.addTextChangedListener(this.mTextWatcher);
        BaseQuickAdapter<PlaceResponse.PoiItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<PlaceResponse.PoiItem, BaseViewHolder>(this, R.layout.item_double_text, this.resultList.getValue()) { // from class: com.ltech.smarthome.ui.home.ActSearchPosition.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, PlaceResponse.PoiItem item) {
                helper.setText(R.id.tv_main, item.getName()).setText(R.id.tv_sub, item.getAddress());
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.home.ActSearchPosition$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActSearchPosition.this.lambda$initView$0(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActSearchPositionBinding) this.mViewBinding).rvResult);
        ((ActSearchPositionBinding) this.mViewBinding).rvResult.setLayoutManager(new LinearLayoutManager(this));
        ((ActSearchPositionBinding) this.mViewBinding).rvResult.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActSearchPositionBinding) this.mViewBinding).rvResult.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        PlaceResponse.PoiItem item = this.mAdapter.getItem(i);
        if (item == null || item.getLocation() == null) {
            return;
        }
        String[] split = item.getLocation().split(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
        if (split.length == 2) {
            try {
                Intent intent = new Intent();
                intent.putExtra(Constants.LATITUDE, Double.parseDouble(split[1]));
                intent.putExtra(Constants.LONGITUDE, Double.parseDouble(split[0]));
                intent.putExtra(Constants.ADDRESS, item.getName());
                intent.putExtra(Constants.CITY, item.getCityname());
                setResult(5000, intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        back();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(List list) {
        this.mAdapter.setNewData(list);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        this.resultList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.home.ActSearchPosition$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSearchPosition.this.lambda$startObserve$1((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doSearchQuery(String s) {
        Injection.net().getPlaceByMapApi(s, this.city).enqueue(new Callback<PlaceResponse>() { // from class: com.ltech.smarthome.ui.home.ActSearchPosition.2
            @Override // retrofit2.Callback
            public void onFailure(Call<PlaceResponse> call, Throwable t) {
            }

            @Override // retrofit2.Callback
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                List<PlaceResponse.PoiItem> pois;
                if (response.body() == null || (pois = response.body().getPois()) == null || pois.size() <= 0) {
                    return;
                }
                ActSearchPosition.this.resultList.setValue(pois);
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        ((ActSearchPositionBinding) this.mViewBinding).etSearch.removeTextChangedListener(this.mTextWatcher);
        super.onDestroy();
    }
}