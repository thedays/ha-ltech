package com.ltech.smarthome.ui.device.rs8;

import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActRs8AddSubDeviceBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.response.rs8.Rs8BrandResponse;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.NavUtils;
import java.util.List;

/* loaded from: classes4.dex */
public class ActRs8Brand extends VMActivity<ActRs8AddSubDeviceBinding, ActRs8VM> {
    private BaseQuickAdapter<Rs8BrandResponse.Brand, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_rs8_add_sub_device;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.choose_brand));
        initListView();
    }

    private void initListView() {
        ((ActRs8AddSubDeviceBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView = ((ActRs8AddSubDeviceBinding) this.mViewBinding).rv;
        BaseQuickAdapter<Rs8BrandResponse.Brand, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Rs8BrandResponse.Brand, BaseViewHolder>(this, R.layout.item_rs8) { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8Brand.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, Rs8BrandResponse.Brand brand) {
                baseViewHolder.setGone(R.id.iv, false);
                if (LanguageUtils.isChinese(ActivityUtils.getTopActivity())) {
                    baseViewHolder.setText(R.id.tv_title, brand.getCname());
                } else {
                    baseViewHolder.setText(R.id.tv_title, brand.getEname());
                }
                baseViewHolder.setGone(R.id.tv_sub, false);
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8Brand.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                Rs8BrandResponse.Brand brand = (Rs8BrandResponse.Brand) ActRs8Brand.this.mAdapter.getData().get(i);
                ((ActRs8VM) ActRs8Brand.this.mViewModel).brandId = brand.getBrandid();
                NavUtils.Builder withString = NavUtils.destination(ActRs8AddressWrite.class).withLong(Constants.CONTROL_ID, ((ActRs8VM) ActRs8Brand.this.mViewModel).controlId).withLong(Constants.PLACE_ID, ((ActRs8VM) ActRs8Brand.this.mViewModel).placeId).withLong(Constants.CATEGORY_ID, ((ActRs8VM) ActRs8Brand.this.mViewModel).categoryId).withLong(Constants.BRAND_ID, ((ActRs8VM) ActRs8Brand.this.mViewModel).brandId).withString("image", ActRs8Brand.this.getIntent().getStringExtra("image")).withString(Constants.TIP, LanguageUtils.isChinese(ActivityUtils.getTopActivity()) ? brand.getCndesc() : brand.getEndesc());
                StringBuilder sb = new StringBuilder();
                sb.append(LanguageUtils.isChinese(ActivityUtils.getTopActivity()) ? brand.getCname() : brand.getEname());
                sb.append(ActRs8Brand.this.getIntent().getStringExtra("device_name"));
                withString.withString("device_name", sb.toString()).withDefaultRequestCode().navigation(ActRs8Brand.this.activity);
                ActRs8Brand.this.finishActivity();
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActRs8VM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActRs8VM) this.mViewModel).placeId = Injection.repo().home().getSelPlace().getPlaceId();
        ((ActRs8VM) this.mViewModel).categoryId = getIntent().getLongExtra(Constants.CATEGORY_ID, -1L);
        ((ActRs8VM) this.mViewModel).brandData.observe(this, new Observer<List<Rs8BrandResponse.Brand>>() { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8Brand.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<Rs8BrandResponse.Brand> brands) {
                ActRs8Brand.this.mAdapter.replaceData(brands);
            }
        });
        ((ActRs8VM) this.mViewModel).initBrandData();
    }
}