package com.ltech.smarthome.ui.device.rs8;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActRs8Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import java.util.List;

/* loaded from: classes4.dex */
public class ActRs8 extends BaseControlActivity<ActRs8Binding, ActRs8VM> {
    private boolean isFirst = true;
    private BaseQuickAdapter<Device, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_rs8;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        setTitle(getString(R.string.add_cg_rs8_name));
        ((ActRs8Binding) this.mViewBinding).tvSearchSum.setText(String.format("%s%s", getString(R.string.sub_device), 0));
        initListView();
    }

    private void initListView() {
        ((ActRs8Binding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView = ((ActRs8Binding) this.mViewBinding).rvContent;
        BaseQuickAdapter<Device, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Device, BaseViewHolder>(this, R.layout.item_rs8) { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, Device device) {
                baseViewHolder.setImageResource(R.id.iv, ProductRepository.getProductIcon(device));
                baseViewHolder.setText(R.id.tv_title, device.getDeviceName());
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                NavUtils.destination(ActRs8Curtain.class).withLong(Constants.PLACE_ID, ((ActRs8VM) ActRs8.this.mViewModel).placeId).withLong(Constants.CONTROL_ID, ((Device) ActRs8.this.mAdapter.getData().get(i)).getId()).navigation(ActRs8.this.activity);
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActRs8VM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActRs8VM) this.mViewModel).placeId = Injection.repo().home().getSelPlace().getPlaceId();
        ((ActRs8VM) this.mViewModel).device = Injection.repo().device().getDeviceById(((ActRs8VM) this.mViewModel).controlId);
        if (((ActRs8VM) this.mViewModel).device != null) {
            setTitle(((ActRs8VM) this.mViewModel).device.getDeviceName());
        }
        loadSubDevice();
        if (Injection.repo().home().getSelPlace().isManager() || Injection.repo().home().getSelPlace().isOwner()) {
            ((ActRs8Binding) this.mViewBinding).ivSearchAdd.setVisibility(0);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (!this.isFirst) {
            loadSubDevice();
        } else {
            this.isFirst = false;
        }
    }

    private void loadSubDevice() {
        List<Device> subDevice = Injection.repo().device().getSubDevice(((ActRs8VM) this.mViewModel).placeId, ((ActRs8VM) this.mViewModel).device.getDeviceId());
        if (this.mAdapter != null) {
            if (!subDevice.isEmpty()) {
                ((ActRs8Binding) this.mViewBinding).rvContent.setVisibility(0);
                ((ActRs8Binding) this.mViewBinding).btSearch.setVisibility(8);
                ((ActRs8Binding) this.mViewBinding).ivEmpty.setVisibility(8);
                ((ActRs8Binding) this.mViewBinding).tvEmptyTip.setVisibility(8);
            }
            ((ActRs8Binding) this.mViewBinding).tvSearchSum.setText(String.format("%s%s", getString(R.string.sub_device), Integer.valueOf(subDevice.size())));
            this.mAdapter.replaceData(subDevice);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavHelper.goSetting(((ActRs8VM) this.mViewModel).device);
    }
}