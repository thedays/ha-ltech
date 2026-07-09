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
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActMatterPlatformListBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.ui.matter.ActMatterSubVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.MatterQrDialog;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes4.dex */
public class ActMatterPlatform extends VMActivity<ActMatterPlatformListBinding, ActMatterSubVM> {
    private MatterQrDialog dialog;
    private BaseQuickAdapter<ActMatterSubVM.Fabric, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_matter_platform_list;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.app_str_connect_matter));
        setBackImage(R.mipmap.icon_back);
        ((ActMatterSubVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActMatterSubVM) this.mViewModel).device = Injection.repo().device().getDeviceById(((ActMatterSubVM) this.mViewModel).controlId);
        ((ActMatterPlatformListBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.matter.ActMatterPlatform.1
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public void onRefresh(RefreshLayout refreshLayout) {
                ((ActMatterSubVM) ActMatterPlatform.this.mViewModel).checkFabric();
            }
        });
        initRv();
        ((ActMatterPlatformListBinding) this.mViewBinding).tvSync.setOnClickListener(new AnonymousClass2());
    }

    /* renamed from: com.ltech.smarthome.ui.matter.ActMatterPlatform$2, reason: invalid class name */
    class AnonymousClass2 implements View.OnClickListener {
        AnonymousClass2() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            ActMatterPlatform.this.dialog = MatterQrDialog.asDefault().setOnMatterCallBack(new MatterQrDialog.OnMatterCallBack() { // from class: com.ltech.smarthome.ui.matter.ActMatterPlatform.2.1
                @Override // com.ltech.smarthome.view.dialog.MatterQrDialog.OnMatterCallBack
                public void onRefresh() {
                    ((ActMatterSubVM) ActMatterPlatform.this.mViewModel).checkQrCode(new IAction<String>() { // from class: com.ltech.smarthome.ui.matter.ActMatterPlatform.2.1.1
                        @Override // com.ltech.smarthome.base.IAction
                        public void act(String s) {
                            if (s == null) {
                                ActMatterPlatform.this.dialog.showFail();
                            } else {
                                String[] split = s.split(MqttTopic.MULTI_LEVEL_WILDCARD);
                                ActMatterPlatform.this.dialog.showSuccess(split[0], split[1]);
                            }
                        }
                    });
                }
            });
            ActMatterPlatform.this.dialog.showDialog(ActMatterPlatform.this.activity);
            ((ActMatterSubVM) ActMatterPlatform.this.mViewModel).checkQrCode(new IAction<String>() { // from class: com.ltech.smarthome.ui.matter.ActMatterPlatform.2.2
                @Override // com.ltech.smarthome.base.IAction
                public void act(String s) {
                    if (s == null) {
                        ActMatterPlatform.this.dialog.showFail();
                    } else {
                        String[] split = s.split(MqttTopic.MULTI_LEVEL_WILDCARD);
                        ActMatterPlatform.this.dialog.showSuccess(split[0], split[1]);
                    }
                }
            });
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActMatterSubVM) this.mViewModel).fabricData.observe(this, new Observer<List<ActMatterSubVM.Fabric>>() { // from class: com.ltech.smarthome.ui.matter.ActMatterPlatform.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<ActMatterSubVM.Fabric> fabrics) {
                if (!fabrics.isEmpty()) {
                    ActMatterPlatform.this.showContent();
                    ActMatterPlatform.this.mAdapter.replaceData(fabrics);
                } else {
                    ActMatterPlatform.this.showEmpty();
                }
            }
        });
        ((ActMatterSubVM) this.mViewModel).loadFabric(getIntent());
    }

    private void initRv() {
        ((ActMatterPlatformListBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(this.activity));
        RecyclerView recyclerView = ((ActMatterPlatformListBinding) this.mViewBinding).rv;
        BaseQuickAdapter<ActMatterSubVM.Fabric, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ActMatterSubVM.Fabric, BaseViewHolder>(this, R.layout.item_fabric) { // from class: com.ltech.smarthome.ui.matter.ActMatterPlatform.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, ActMatterSubVM.Fabric fabric) {
                baseViewHolder.setImageResource(R.id.iv, fabric.getImg());
                baseViewHolder.setText(R.id.tv, fabric.getName());
                baseViewHolder.addOnClickListener(R.id.tv_del);
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        ((ActMatterPlatformListBinding) this.mViewBinding).rv.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.matter.ActMatterPlatform.5
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = ConvertUtils.dp2px(15.0f);
            }
        });
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.matter.ActMatterPlatform.6
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter2, View view, final int i) {
                ActMatterPlatform.this.showLoadingDialog("");
                ((ActMatterSubVM) ActMatterPlatform.this.mViewModel).remove(((ActMatterSubVM.Fabric) ActMatterPlatform.this.mAdapter.getData().get(i)).getIndex(), new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.matter.ActMatterPlatform.6.1
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(Boolean aBoolean) {
                        ActMatterPlatform.this.dismissLoadingDialog();
                        if (aBoolean.booleanValue()) {
                            ActMatterPlatform.this.mAdapter.remove(i);
                            if (ActMatterPlatform.this.mAdapter.getData().isEmpty()) {
                                ActMatterPlatform.this.showEmpty();
                                return;
                            } else {
                                ActMatterPlatform.this.showContent();
                                return;
                            }
                        }
                        SmartToast.showCenterShort(ActMatterPlatform.this.getString(R.string.delete_success));
                    }
                });
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return super.createGLoading();
    }
}