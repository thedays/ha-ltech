package com.ltech.smarthome.ui.matter;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.databinding.FtListBinding;
import com.ltech.smarthome.net.response.MatterDeviceResponse;
import java.util.List;

/* loaded from: classes4.dex */
public class FtMatterDevice extends VMFragment<FtListBinding, ActMatterSubVM> {
    private BaseQuickAdapter<MatterDeviceResponse.MatterDevice, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_list;
    }

    @Override // com.ltech.smarthome.base.VMFragment
    protected boolean shareVM() {
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        initRv();
    }

    private void initRv() {
        ((FtListBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView recyclerView = ((FtListBinding) this.mViewBinding).rv;
        BaseQuickAdapter<MatterDeviceResponse.MatterDevice, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<MatterDeviceResponse.MatterDevice, BaseViewHolder>(this, R.layout.item_device_manage) { // from class: com.ltech.smarthome.ui.matter.FtMatterDevice.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, MatterDeviceResponse.MatterDevice rowsBean) {
                baseViewHolder.setImageResource(R.id.iv_icon, rowsBean.getIcon());
                baseViewHolder.setText(R.id.tv_device_name, rowsBean.getName());
                baseViewHolder.setText(R.id.tv_place_info, rowsBean.getSub());
                baseViewHolder.setVisible(R.id.iv_go, false);
                baseViewHolder.addOnClickListener(R.id.tv_del);
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        super.startObserve();
        ((ActMatterSubVM) this.mViewModel).matterDevices.observe(this, new Observer<List<MatterDeviceResponse.MatterDevice>>() { // from class: com.ltech.smarthome.ui.matter.FtMatterDevice.2
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<MatterDeviceResponse.MatterDevice> data) {
                if (data == null || data.isEmpty()) {
                    FtMatterDevice.this.showEmpty();
                } else {
                    FtMatterDevice.this.showContent();
                    FtMatterDevice.this.mAdapter.replaceData(data);
                }
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.no_device));
    }
}