package com.ltech.smarthome.ui.device.super_panel.sp485;

import android.app.ComponentCaller;
import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSp485ListBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.cg485.ActCg485Device;
import com.ltech.smarthome.ui.device.cg485.Cg485Helper;
import com.ltech.smarthome.ui.device.rs8.ActRs8Curtain;
import com.ltech.smarthome.ui.device.super_panel.sp485.ActSp485VM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSp485 extends VMActivity<ActSp485ListBinding, ActSp485VM> {
    private MultipleItemRvAdapter<ActSp485VM.item, BaseViewHolder> adapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_sp485_list;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.app_str_sp_485));
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        ((ActSp485ListBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.device.super_panel.sp485.ActSp485.1
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
                ((ActSp485VM) ActSp485.this.mViewModel).device = Injection.repo().device().getDeviceById(((ActSp485VM) ActSp485.this.mViewModel).controlId);
                ((ActSp485VM) ActSp485.this.mViewModel).loadSubDevice();
            }
        });
        ((ActSp485ListBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(this));
        SwipeRecyclerView swipeRecyclerView = ((ActSp485ListBinding) this.mViewBinding).rv;
        MultipleItemRvAdapter<ActSp485VM.item, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<ActSp485VM.item, BaseViewHolder>(new ArrayList()) { // from class: com.ltech.smarthome.ui.device.super_panel.sp485.ActSp485.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(ActSp485VM.item item) {
                return item.getType();
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new BaseItemProvider<ActSp485VM.item, BaseViewHolder>(this) { // from class: com.ltech.smarthome.ui.device.super_panel.sp485.ActSp485.2.1
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_category_header;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 0;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder baseViewHolder, ActSp485VM.item item, int i) {
                        baseViewHolder.setText(R.id.tv_name, item.getTitle());
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<ActSp485VM.item, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.super_panel.sp485.ActSp485.2.2
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_rs8;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 1;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder baseViewHolder, ActSp485VM.item item, int i) {
                        if (item.getBackground() == 1) {
                            baseViewHolder.itemView.setBackgroundResource(R.drawable.shape_white_top_bg);
                        } else if (item.getBackground() == 3) {
                            baseViewHolder.itemView.setBackgroundResource(R.drawable.shape_white_bottom_bg);
                        } else if (item.getBackground() == 2) {
                            baseViewHolder.itemView.setBackgroundResource(R.drawable.shape_white_bg);
                        } else {
                            baseViewHolder.itemView.setBackgroundColor(-1);
                        }
                        baseViewHolder.setText(R.id.tv_title, item.getTitle());
                        baseViewHolder.setImageResource(R.id.iv, item.getImg());
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void onClick(BaseViewHolder helper, ActSp485VM.item data, int position) {
                        super.onClick((C01552) helper, (BaseViewHolder) data, position);
                        if (data.getDevice() != null) {
                            Device device = data.getDevice();
                            String productId = device.getProductId();
                            if (ProductId.CG485_SUB_DEVICE.equals(productId)) {
                                NavUtils.destination(ActCg485Device.class).withLong(Constants.CONTROL_ID, device.getId()).withInt(Constants.COMMAND_TYPE, 1).withDefaultRequestCode().navigation(ActSp485.this.activity);
                                return;
                            } else {
                                if (ProductId.CGRS8_SUB_DEVICE.equals(productId)) {
                                    NavUtils.destination(ActRs8Curtain.class).withLong(Constants.PLACE_ID, ((ActSp485VM) ActSp485.this.mViewModel).placeId).withLong(Constants.CONTROL_ID, device.getId()).navigation(ActSp485.this.activity);
                                    return;
                                }
                                return;
                            }
                        }
                        Cg485Helper.categoryPosition = data.getPos();
                        NavUtils.destination(ActCg485Device.class).withLong(Constants.CONTROL_ID, ((ActSp485VM) ActSp485.this.mViewModel).controlId).withInt(Constants.COMMAND_TYPE, 2).withDefaultRequestCode().navigation(ActSp485.this.activity);
                    }
                });
            }
        };
        this.adapter = multipleItemRvAdapter;
        swipeRecyclerView.setAdapter(multipleItemRvAdapter);
        this.adapter.finishInitialize();
        this.adapter.bindToRecyclerView(((ActSp485ListBinding) this.mViewBinding).rv);
        ((ActSp485ListBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(this));
        ((ActSp485ListBinding) this.mViewBinding).rv.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActSp485ListBinding) this.mViewBinding).rv.getItemAnimator()).setSupportsChangeAnimations(true);
        ((ActSp485ListBinding) this.mViewBinding).rv.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.super_panel.sp485.ActSp485.3
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = ConvertUtils.dp2px(15.0f);
                outRect.right = ConvertUtils.dp2px(15.0f);
            }
        });
        ((ActSp485ListBinding) this.mViewBinding).tvBottom.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.sp485.ActSp485.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                NavUtils.destination(ActSp485Add.class).withLong(Constants.PLACE_ID, ((ActSp485VM) ActSp485.this.mViewModel).placeId).withLong(Constants.CONTROL_ID, ((ActSp485VM) ActSp485.this.mViewModel).controlId).withRequestCode(100).navigation(ActSp485.this.activity);
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.no_device));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data, ComponentCaller caller) {
        super.onActivityResult(requestCode, resultCode, data, caller);
        if (requestCode == 100) {
            ((ActSp485VM) this.mViewModel).device = Injection.repo().device().getDeviceById(((ActSp485VM) this.mViewModel).controlId);
            ((ActSp485VM) this.mViewModel).loadSubDevice();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActSp485VM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActSp485VM) this.mViewModel).placeId = Injection.repo().home().getSelPlace().getPlaceId();
        ((ActSp485VM) this.mViewModel).device = Injection.repo().device().getDeviceById(((ActSp485VM) this.mViewModel).controlId);
        ((ActSp485VM) this.mViewModel).data.observe(this, new Observer<List<ActSp485VM.item>>() { // from class: com.ltech.smarthome.ui.device.super_panel.sp485.ActSp485.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<ActSp485VM.item> items) {
                if (items.isEmpty()) {
                    ActSp485.this.showEmpty();
                } else {
                    ActSp485.this.showContent();
                    ActSp485.this.adapter.replaceData(items);
                }
            }
        });
        ((ActSp485VM) this.mViewModel).queryParamResult.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.sp485.ActSp485$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSp485.this.lambda$startObserve$0((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Boolean bool) {
        if (bool.booleanValue()) {
            dismissLoadingDialog();
            ((ActSp485VM) this.mViewModel).goSerialSettings();
        } else {
            showErrorDialog(getString(R.string.search_fail));
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((ActSp485VM) this.mViewModel).loadSubDevice();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        showLoadingDialog("");
        CmdAssistant.getQueryCmdAssistant(((ActSp485VM) this.mViewModel).device, new int[0]).queryCg485Setting(this, ((ActSp485VM) this.mViewModel).iQuery);
    }
}