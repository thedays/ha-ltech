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
import com.ltech.smarthome.net.response.rs8.Rs8CategoryResponse;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.NavUtils;
import java.util.List;

/* loaded from: classes4.dex */
public class ActRs8Category extends VMActivity<ActRs8AddSubDeviceBinding, ActRs8VM> {
    private BaseQuickAdapter<Rs8CategoryResponse.Category, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_rs8_add_sub_device;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.add_device));
        initListView();
    }

    private void initListView() {
        ((ActRs8AddSubDeviceBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView = ((ActRs8AddSubDeviceBinding) this.mViewBinding).rv;
        BaseQuickAdapter<Rs8CategoryResponse.Category, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Rs8CategoryResponse.Category, BaseViewHolder>(this, R.layout.item_rs8) { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8Category.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, Rs8CategoryResponse.Category category) {
                if (category.getIcon().equals("2")) {
                    baseViewHolder.setImageResource(R.id.iv, R.mipmap.trig_cur_ic_dream);
                } else if (category.getIcon().equals("3")) {
                    baseViewHolder.setImageResource(R.id.iv, R.mipmap.trig_icon_cur);
                } else {
                    baseViewHolder.setImageResource(R.id.iv, R.mipmap.ic_device_curtain);
                }
                if (LanguageUtils.isChinese(ActivityUtils.getTopActivity())) {
                    baseViewHolder.setText(R.id.tv_title, category.getCname());
                } else {
                    baseViewHolder.setText(R.id.tv_title, category.getEname());
                }
                baseViewHolder.setGone(R.id.tv_sub, false);
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8Category.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ((ActRs8VM) ActRs8Category.this.mViewModel).categoryId = ((Rs8CategoryResponse.Category) ActRs8Category.this.mAdapter.getData().get(i)).getCategoryid();
                NavUtils.destination(ActRs8Brand.class).withLong(Constants.CONTROL_ID, ((ActRs8VM) ActRs8Category.this.mViewModel).controlId).withLong(Constants.PLACE_ID, ((ActRs8VM) ActRs8Category.this.mViewModel).placeId).withLong(Constants.CATEGORY_ID, ((ActRs8VM) ActRs8Category.this.mViewModel).categoryId).withString("image", ((Rs8CategoryResponse.Category) ActRs8Category.this.mAdapter.getData().get(i)).getIcon()).withString("device_name", LanguageUtils.isChinese(ActivityUtils.getTopActivity()) ? ((Rs8CategoryResponse.Category) ActRs8Category.this.mAdapter.getData().get(i)).getCname() : ((Rs8CategoryResponse.Category) ActRs8Category.this.mAdapter.getData().get(i)).getEname()).withDefaultRequestCode().navigation(ActRs8Category.this.activity);
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActRs8VM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActRs8VM) this.mViewModel).placeId = Injection.repo().home().getSelPlace().getPlaceId();
        ((ActRs8VM) this.mViewModel).categoryData.observe(this, new Observer<List<Rs8CategoryResponse.Category>>() { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8Category.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<Rs8CategoryResponse.Category> categories) {
                ActRs8Category.this.mAdapter.replaceData(categories);
            }
        });
        ((ActRs8VM) this.mViewModel).initCategoryData();
    }
}