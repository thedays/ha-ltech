package com.ltech.smarthome.ui.device.super_panel.sp485;

import android.graphics.Rect;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActPubListBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.device_param.Rs485ExtParam;
import com.ltech.smarthome.net.response.rs8.Rs8CategoryResponse;
import com.ltech.smarthome.ui.device.cg485.ActEditInstructCmd;
import com.ltech.smarthome.ui.device.cg485.Cg485Helper;
import com.ltech.smarthome.ui.device.rs8.ActRs8Brand;
import com.ltech.smarthome.ui.device.super_panel.sp485.ActSp485VM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.view.dialog.AddCg485DeviceDialog;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSp485Add extends VMActivity<ActPubListBinding, ActSp485VM> {
    private MultipleItemRvAdapter<ActSp485VM.item, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_pub_list;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.add_device));
        setBackImage(R.mipmap.icon_back);
        ((ActPubListBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(this));
        SwipeRecyclerView swipeRecyclerView = ((ActPubListBinding) this.mViewBinding).rv;
        MultipleItemRvAdapter<ActSp485VM.item, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<ActSp485VM.item, BaseViewHolder>(new ArrayList()) { // from class: com.ltech.smarthome.ui.device.super_panel.sp485.ActSp485Add.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(ActSp485VM.item item) {
                return item.getType();
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new BaseItemProvider<ActSp485VM.item, BaseViewHolder>(this) { // from class: com.ltech.smarthome.ui.device.super_panel.sp485.ActSp485Add.1.1
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
                this.mProviderDelegate.registerProvider(new BaseItemProvider<ActSp485VM.item, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.super_panel.sp485.ActSp485Add.1.2
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
                        Rs8CategoryResponse.Category category = item.getCategory();
                        if (category.getIcon().equals("1")) {
                            baseViewHolder.setImageResource(R.id.iv, R.mipmap.ic_device_curtain);
                        } else if (category.getIcon().equals("2")) {
                            baseViewHolder.setImageResource(R.id.iv, R.mipmap.trig_cur_ic_dream);
                        } else if (category.getIcon().equals("3")) {
                            baseViewHolder.setImageResource(R.id.iv, R.mipmap.trig_icon_cur);
                        } else {
                            baseViewHolder.setImageResource(R.id.iv, R.mipmap.device_icon_diy);
                        }
                        if (category.getCategoryid() == -999 || category.getCategoryid() == -998) {
                            baseViewHolder.setText(R.id.tv_title, item.getTitle());
                        } else if (LanguageUtils.isChinese(ActivityUtils.getTopActivity())) {
                            baseViewHolder.setText(R.id.tv_title, category.getCname());
                        } else {
                            baseViewHolder.setText(R.id.tv_title, category.getEname());
                        }
                        baseViewHolder.setGone(R.id.tv_sub, false);
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void onClick(BaseViewHolder helper, ActSp485VM.item data, int position) {
                        super.onClick((AnonymousClass2) helper, (BaseViewHolder) data, position);
                        Rs8CategoryResponse.Category category = data.getCategory();
                        if (category.getCategoryid() == -999) {
                            ActSp485Add.this.add485(1);
                        } else if (category.getCategoryid() != -998) {
                            ((ActSp485VM) ActSp485Add.this.mViewModel).categoryId = category.getCategoryid();
                            NavUtils.destination(ActRs8Brand.class).withLong(Constants.CONTROL_ID, ((ActSp485VM) ActSp485Add.this.mViewModel).controlId).withLong(Constants.PLACE_ID, ((ActSp485VM) ActSp485Add.this.mViewModel).placeId).withLong(Constants.CATEGORY_ID, ((ActSp485VM) ActSp485Add.this.mViewModel).categoryId).withString("image", category.getIcon()).withString("device_name", LanguageUtils.isChinese(ActivityUtils.getTopActivity()) ? category.getCname() : category.getEname()).withDefaultRequestCode().navigation(ActSp485Add.this.activity);
                        } else {
                            ActSp485Add.this.add485(2);
                        }
                    }
                });
            }
        };
        this.mAdapter = multipleItemRvAdapter;
        swipeRecyclerView.setAdapter(multipleItemRvAdapter);
        this.mAdapter.finishInitialize();
        this.mAdapter.bindToRecyclerView(((ActPubListBinding) this.mViewBinding).rv);
        ((ActPubListBinding) this.mViewBinding).rv.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.super_panel.sp485.ActSp485Add.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = ConvertUtils.dp2px(15.0f);
                outRect.right = ConvertUtils.dp2px(15.0f);
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActSp485VM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActSp485VM) this.mViewModel).placeId = Injection.repo().home().getSelPlace().getPlaceId();
        ((ActSp485VM) this.mViewModel).data.observe(this, new Observer<List<ActSp485VM.item>>() { // from class: com.ltech.smarthome.ui.device.super_panel.sp485.ActSp485Add.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<ActSp485VM.item> items) {
                ActSp485Add.this.mAdapter.setNewData(items);
            }
        });
        ((ActSp485VM) this.mViewModel).initCategoryData();
        ((ActSp485VM) this.mViewModel).device = Injection.repo().device().getDeviceById(((ActSp485VM) this.mViewModel).controlId);
        Cg485Helper.controlDevice = ((ActSp485VM) this.mViewModel).device;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void add485(int i) {
        AddCg485DeviceDialog.asDefault().setSelectPosition(0).setType(i).setOnSaveListener(new AddCg485DeviceDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.device.super_panel.sp485.ActSp485Add$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.dialog.AddCg485DeviceDialog.OnSaveListener
            public final void onSave(String str, int i2, int i3) {
                ActSp485Add.this.lambda$add485$0(str, i2, i3);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$add485$0(String str, int i, int i2) {
        if (i == 1) {
            Cg485Helper.setControlDevice(Cg485Helper.createBleTo485SubDevice(str, i2));
            NavUtils.destination(ActEditInstructCmd.class).withInt(Constants.COMMAND_TYPE, i).navigation(this.activity);
            return;
        }
        Rs485ExtParam.Category category = new Rs485ExtParam.Category();
        category.setCategoryName(str);
        category.setType(i);
        category.setColorIdx(i2);
        Cg485Helper.addCategory(i, category);
        NavUtils.destination(ActEditInstructCmd.class).withInt(Constants.COMMAND_TYPE, i).navigation(this);
        finishActivity();
    }
}