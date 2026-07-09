package com.ltech.smarthome.nfc;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.nfc.source.SourceHelper;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActAddVirtualDeviceBinding;
import com.ltech.smarthome.ui.config.ActAddDeviceVM;
import com.ltech.smarthome.utils.Constants;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActAddVirtualDevice extends VMActivity<ActAddVirtualDeviceBinding, ActAddDeviceVM> {
    private BaseQuickAdapter<Integer, BaseViewHolder> categoryAdapter;
    private MultipleItemRvAdapter<ActAddDeviceVM.ProductItem, BaseViewHolder> deviceAdapter;
    private int selectPosition = 0;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_add_virtual_device;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.add_virtual_device));
        setBackString(getString(R.string.cancel));
        ((ActAddDeviceVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActAddDeviceVM) this.mViewModel).roomPickHelper.startObserve(this, ((ActAddDeviceVM) this.mViewModel).placeId, -1L);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActAddDeviceVM) this.mViewModel).virtualData.observe(this, new Observer() { // from class: com.ltech.smarthome.nfc.ActAddVirtualDevice$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddVirtualDevice.this.lambda$startObserve$0((List) obj);
            }
        });
        ((ActAddDeviceVM) this.mViewModel).virtualData.setValue(SourceHelper.sourceModelList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(List list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        ((ActAddDeviceVM) this.mViewModel).initVirtualProductList(list);
        initCategoryRv();
        initDeviceRv(((ActAddDeviceVM) this.mViewModel).getProduct(this.categoryAdapter.getData().get(this.selectPosition).intValue()));
    }

    private void initCategoryRv() {
        if (this.categoryAdapter == null) {
            BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Integer, BaseViewHolder>(R.layout.item_category, new ArrayList()) { // from class: com.ltech.smarthome.nfc.ActAddVirtualDevice.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, Integer item) {
                    helper.setText(R.id.tv_content, item.intValue()).setTextColor(R.id.tv_content, ContextCompat.getColor(this.mContext, helper.getAdapterPosition() == ActAddVirtualDevice.this.selectPosition ? R.color.color_text_red : R.color.color_text_black)).setBackgroundColor(R.id.layout_bg, ContextCompat.getColor(this.mContext, helper.getAdapterPosition() == ActAddVirtualDevice.this.selectPosition ? android.R.color.white : 17170445));
                }
            };
            this.categoryAdapter = baseQuickAdapter;
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.nfc.ActAddVirtualDevice$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    ActAddVirtualDevice.this.lambda$initCategoryRv$1(baseQuickAdapter2, view, i);
                }
            });
            this.categoryAdapter.bindToRecyclerView(((ActAddVirtualDeviceBinding) this.mViewBinding).rvCategory);
            ((ActAddVirtualDeviceBinding) this.mViewBinding).rvCategory.setLayoutManager(new LinearLayoutManager(this));
            ((ActAddVirtualDeviceBinding) this.mViewBinding).rvCategory.setHasFixedSize(true);
        }
        this.categoryAdapter.replaceData(((ActAddDeviceVM) this.mViewModel).categoryList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initCategoryRv$1(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.selectPosition = i;
        this.categoryAdapter.notifyDataSetChanged();
        this.deviceAdapter.setNewData(((ActAddDeviceVM) this.mViewModel).getProduct(this.categoryAdapter.getData().get(i).intValue()));
    }

    private void initDeviceRv(List<ActAddDeviceVM.ProductItem> productItemList) {
        MultipleItemRvAdapter<ActAddDeviceVM.ProductItem, BaseViewHolder> multipleItemRvAdapter = this.deviceAdapter;
        if (multipleItemRvAdapter == null) {
            MultipleItemRvAdapter<ActAddDeviceVM.ProductItem, BaseViewHolder> multipleItemRvAdapter2 = new MultipleItemRvAdapter<ActAddDeviceVM.ProductItem, BaseViewHolder>(productItemList) { // from class: com.ltech.smarthome.nfc.ActAddVirtualDevice.2
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
                public int getViewType(ActAddDeviceVM.ProductItem productItem) {
                    return productItem.productInfo == null ? 1 : 2;
                }

                @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
                public void registerItemProvider() {
                    this.mProviderDelegate.registerProvider(new BaseItemProvider<ActAddDeviceVM.ProductItem, BaseViewHolder>() { // from class: com.ltech.smarthome.nfc.ActAddVirtualDevice.2.1
                        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                        public int layout() {
                            return R.layout.item_text_middle;
                        }

                        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                        public int viewType() {
                            return 1;
                        }

                        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                        public void convert(BaseViewHolder helper, ActAddDeviceVM.ProductItem item, int position) {
                            helper.setText(R.id.tv_name, item.title).setTextColor(R.id.tv_name, ContextCompat.getColor(ActAddVirtualDevice.this.activity, R.color.color_text_black));
                            ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                        }
                    });
                    this.mProviderDelegate.registerProvider(new BaseItemProvider<ActAddDeviceVM.ProductItem, BaseViewHolder>() { // from class: com.ltech.smarthome.nfc.ActAddVirtualDevice.2.2
                        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                        public int layout() {
                            return R.layout.item_select_product;
                        }

                        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                        public int viewType() {
                            return 2;
                        }

                        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                        public void convert(BaseViewHolder helper, ActAddDeviceVM.ProductItem item, int position) {
                            helper.getView(R.id.layout_item_bg).getLayoutParams().height = SizeUtils.dp2px(120.0f);
                            helper.setImageResource(R.id.iv_product_icon, item.productInfo.getDefaultIconRes());
                            if ("02".equalsIgnoreCase(item.productInfo.getProductKey())) {
                                helper.setText(R.id.tv_product_name, item.productInfo.getDefaultName(this.mContext) + "\n" + item.modelName);
                                return;
                            }
                            helper.setText(R.id.tv_product_name, item.productInfo.getDefaultName(this.mContext));
                        }

                        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                        public void onClick(BaseViewHolder helper, ActAddDeviceVM.ProductItem data, int position) {
                            if (data.isVirtual()) {
                                ((ActAddDeviceVM) ActAddVirtualDevice.this.mViewModel).showAddVirtualDialog(data);
                            }
                        }
                    });
                }
            };
            this.deviceAdapter = multipleItemRvAdapter2;
            multipleItemRvAdapter2.finishInitialize();
            this.deviceAdapter.bindToRecyclerView(((ActAddVirtualDeviceBinding) this.mViewBinding).rvDevice);
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.ltech.smarthome.nfc.ActAddVirtualDevice.3
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int position) {
                    if (((ActAddDeviceVM.ProductItem) ActAddVirtualDevice.this.deviceAdapter.getData().get(position)).productInfo == null) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 3;
                }
            });
            ((ActAddVirtualDeviceBinding) this.mViewBinding).rvDevice.setLayoutManager(gridLayoutManager);
            ((ActAddVirtualDeviceBinding) this.mViewBinding).rvDevice.setHasFixedSize(true);
            return;
        }
        multipleItemRvAdapter.replaceData(productItemList);
    }
}