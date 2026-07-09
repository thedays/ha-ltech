package com.ltech.smarthome.ui.matter;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
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
public class FtMatterScene extends VMFragment<FtListBinding, ActMatterSubVM> {
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
        ((FtListBinding) this.mViewBinding).rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        RecyclerView recyclerView = ((FtListBinding) this.mViewBinding).rv;
        BaseQuickAdapter<MatterDeviceResponse.MatterDevice, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<MatterDeviceResponse.MatterDevice, BaseViewHolder>(this, R.layout.item_scene_new) { // from class: com.ltech.smarthome.ui.matter.FtMatterScene.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, MatterDeviceResponse.MatterDevice rowsBean) {
                baseViewHolder.setImageResource(R.id.appCompatImageView14, rowsBean.getIcon());
                baseViewHolder.setText(R.id.tv_name, rowsBean.getName());
                baseViewHolder.setBackgroundRes(R.id.tv_type, R.drawable.shape_scene_local_bg);
                baseViewHolder.setGone(R.id.iv_edit, false);
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        super.startObserve();
        ((ActMatterSubVM) this.mViewModel).matterScenes.observe(this, new Observer<List<MatterDeviceResponse.MatterDevice>>() { // from class: com.ltech.smarthome.ui.matter.FtMatterScene.2
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<MatterDeviceResponse.MatterDevice> data) {
                if (data == null || data.isEmpty()) {
                    FtMatterScene.this.showEmpty();
                } else {
                    FtMatterScene.this.showContent();
                    FtMatterScene.this.mAdapter.replaceData(data);
                }
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.app_matter_sync_scene_empty_tip));
    }
}