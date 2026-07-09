package com.ltech.smarthome.ui.matter;

import android.graphics.Rect;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActMatterListBinding;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.homekit.ActHomeKit;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import java.util.List;

/* loaded from: classes4.dex */
public class ActMatter extends VMActivity<ActMatterListBinding, ActMatterVM> {
    private BaseQuickAdapter<Device, BaseViewHolder> adapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_matter_list;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.app_str_matter));
        setBackImage(R.mipmap.icon_back);
        initRv();
    }

    private void initRv() {
        ((ActMatterListBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(this));
        SwipeRecyclerView swipeRecyclerView = ((ActMatterListBinding) this.mViewBinding).rv;
        BaseQuickAdapter<Device, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Device, BaseViewHolder>(this, R.layout.item_smart_speaker) { // from class: com.ltech.smarthome.ui.matter.ActMatter.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, Device device) {
                baseViewHolder.setImageResource(R.id.ic_speaker, ProductRepository.getProductIcon(device));
                baseViewHolder.setText(R.id.tv_main, device.getName());
            }
        };
        this.adapter = baseQuickAdapter;
        swipeRecyclerView.setAdapter(baseQuickAdapter);
        this.adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.matter.ActMatter.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                NavUtils.destination(ActHomeKit.class).withLong(Constants.CONTROL_ID, ((Device) ActMatter.this.adapter.getData().get(i)).getId()).navigation(ActMatter.this.activity);
            }
        });
        ((ActMatterListBinding) this.mViewBinding).rv.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.matter.ActMatter.3
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = ConvertUtils.dp2px(15.0f);
            }
        });
        View view = new View(this);
        view.setLayoutParams(new RecyclerView.LayoutParams(-1, ConvertUtils.dp2px(140.0f)));
        this.adapter.addFooterView(view);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActMatterVM) this.mViewModel).loadMatterDevice();
        ((ActMatterVM) this.mViewModel).data.observe(this, new Observer<List<Device>>() { // from class: com.ltech.smarthome.ui.matter.ActMatter.4
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<Device> devices) {
                if (devices.isEmpty()) {
                    ActMatter.this.showEmpty();
                } else {
                    ActMatter.this.showContent();
                    ActMatter.this.adapter.replaceData(devices);
                }
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.app_str_empty_matter));
    }
}