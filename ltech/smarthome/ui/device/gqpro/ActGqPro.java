package com.ltech.smarthome.ui.device.gqpro;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActGqProBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.SuperPanelInfo;
import com.ltech.smarthome.ui.device.gqpro.ActGqProVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnPageChangeListener;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActGqPro extends VMActivity<ActGqProBinding, ActGqProVM> {
    private GqProBannerAdapter bannerAdapter;
    private MultipleItemRvAdapter<ActGqProVM.ActionItem, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_gq_pro;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActGqProBinding) this.mViewBinding).banner.addBannerLifecycleObserver(this);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        ((ActGqProVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        initListView();
        initBanner();
    }

    private void initBanner() {
        Banner banner = ((ActGqProBinding) this.mViewBinding).banner;
        GqProBannerAdapter gqProBannerAdapter = new GqProBannerAdapter(new ArrayList());
        this.bannerAdapter = gqProBannerAdapter;
        banner.setAdapter(gqProBannerAdapter, false);
        ((ActGqProBinding) this.mViewBinding).banner.isAutoLoop(false);
        ((ActGqProBinding) this.mViewBinding).banner.addOnPageChangeListener(new OnPageChangeListener() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqPro.1
            @Override // com.youth.banner.listener.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
            }

            @Override // com.youth.banner.listener.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override // com.youth.banner.listener.OnPageChangeListener
            public void onPageSelected(int position) {
                ((ActGqProVM) ActGqPro.this.mViewModel).selPos = position;
                ((ActGqProBinding) ActGqPro.this.mViewBinding).tvNum.setText(String.format("%s/%s", Integer.valueOf(position + 1), Integer.valueOf(((ActGqProBinding) ActGqPro.this.mViewBinding).banner.getRealCount())));
            }
        });
    }

    private void initListView() {
        ((ActGqProBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(this));
        MultipleItemRvAdapter<ActGqProVM.ActionItem, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<ActGqProVM.ActionItem, BaseViewHolder>(this, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqPro.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(ActGqProVM.ActionItem actionItem) {
                return actionItem.getType();
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new BaseItemProvider<ActGqProVM.ActionItem, BaseViewHolder>(this) { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqPro.2.1
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_gq_pro;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 0;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder baseViewHolder, ActGqProVM.ActionItem actionItem, int i) {
                        baseViewHolder.setText(R.id.tv_title, actionItem.getTitle());
                        baseViewHolder.setText(R.id.tv_sub, actionItem.getSub());
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<ActGqProVM.ActionItem, BaseViewHolder>(this) { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqPro.2.2
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_gq_pro;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 0;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder baseViewHolder, ActGqProVM.ActionItem actionItem, int i) {
                        baseViewHolder.setText(R.id.tv_title, actionItem.getTitle());
                        baseViewHolder.setText(R.id.tv_sub, actionItem.getSub());
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<ActGqProVM.ActionItem, BaseViewHolder>(this) { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqPro.2.3
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_gq_pro_second;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 1;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder baseViewHolder, ActGqProVM.ActionItem actionItem, int i) {
                        baseViewHolder.setText(R.id.tv_title, actionItem.getTitle());
                        if (actionItem.getList() == null || actionItem.getList().size() <= 1) {
                            return;
                        }
                        baseViewHolder.setText(R.id.tv_title2, actionItem.getList().get(0).getTitle());
                        baseViewHolder.setText(R.id.tv_sub2, actionItem.getList().get(0).getSub());
                        baseViewHolder.setText(R.id.tv_title3, actionItem.getList().get(1).getTitle());
                        baseViewHolder.setText(R.id.tv_sub3, actionItem.getList().get(1).getSub());
                    }
                });
            }
        };
        this.mAdapter = multipleItemRvAdapter;
        multipleItemRvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqPro.3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (!Injection.repo().home().getSelPlace().isOwner() && !Injection.repo().home().getSelPlace().isManager()) {
                    ActGqPro.this.showNoPermissionDialog();
                } else if (i == 0) {
                    NavUtils.destination(ActGqProTheme.class).withLong(Constants.CONTROL_ID, ((ActGqProVM) ActGqPro.this.mViewModel).controlId).withString("image", GsonUtils.toJson(((ActGqProVM) ActGqPro.this.mViewModel).photoList)).withDefaultRequestCode().navigation(ActGqPro.this.activity);
                } else {
                    ActGqPro.this.addPlaceIdDeviceId(ActGqProRelation.class).navigation(ActGqPro.this.activity);
                }
            }
        });
        ((ActGqProBinding) this.mViewBinding).rv.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqPro.4
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ConvertUtils.dp2px(19.0f);
            }
        });
        this.mAdapter.finishInitialize();
        this.mAdapter.bindToRecyclerView(((ActGqProBinding) this.mViewBinding).rv);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActGqProVM) this.mViewModel).initData();
        ((ActGqProVM) this.mViewModel).controlObject.observe(this, new Observer<Object>() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqPro.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(Object o) {
                if (o instanceof Device) {
                    ActGqPro.this.setTitle(((Device) o).getDeviceName());
                    ((ObservableSubscribeProxy) Injection.repo().device().getGqProInfo(((ActGqProVM) ActGqPro.this.mViewModel).device.getDeviceId()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(ActGqPro.this.activity, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<SuperPanelInfo>() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqPro.5.1
                        @Override // io.reactivex.functions.Consumer
                        public void accept(SuperPanelInfo superPanelInfo) throws Exception {
                            ((ActGqProVM) ActGqPro.this.mViewModel).superPanelInfo = superPanelInfo;
                            ActGqPro.this.paras(superPanelInfo);
                        }
                    });
                }
            }
        });
        ((ActGqProVM) this.mViewModel).actions.observe(this, new Observer<List<ActGqProVM.ActionItem>>() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqPro.6
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<ActGqProVM.ActionItem> actionItems) {
                ActGqPro.this.mAdapter.replaceData(actionItems);
            }
        });
        ((ActGqProVM) this.mViewModel).roleList.observe(this, new Observer<List<Role>>() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqPro.7
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<Role> roles) {
                if (roles.isEmpty()) {
                    ((ActGqProBinding) ActGqPro.this.mViewBinding).groupNoData.setVisibility(0);
                    ((ActGqProBinding) ActGqPro.this.mViewBinding).tvNum.setVisibility(8);
                    ((ActGqProBinding) ActGqPro.this.mViewBinding).cardview.setVisibility(8);
                    return;
                }
                ((ActGqProBinding) ActGqPro.this.mViewBinding).cardview.setVisibility(0);
                ((ActGqProBinding) ActGqPro.this.mViewBinding).tvNum.setVisibility(0);
                ((ActGqProBinding) ActGqPro.this.mViewBinding).groupNoData.setVisibility(8);
                ((ActGqProBinding) ActGqPro.this.mViewBinding).ivCover.setImageResource(0);
                ActGqPro.this.bannerAdapter.updateData(((ActGqProVM) ActGqPro.this.mViewModel).covertBannerData(roles));
                ((ActGqProBinding) ActGqPro.this.mViewBinding).banner.setCurrentItem(0);
                ((ActGqProBinding) ActGqPro.this.mViewBinding).tvNum.setText(String.format("%s/%s", 1, Integer.valueOf(roles.size())));
            }
        });
        ((ActGqProVM) this.mViewModel).deviceNum.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqPro.8
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer integer) {
                if (ActGqPro.this.mAdapter.getData().size() > 1) {
                    ActGqProVM.ActionItem actionItem = (ActGqProVM.ActionItem) ActGqPro.this.mAdapter.getData().get(1);
                    if (actionItem.getList() != null && !actionItem.getList().isEmpty()) {
                        actionItem.getList().get(0).setSub(integer.intValue() == 0 ? ActGqPro.this.getString(R.string.str_not_add) : String.format(ActGqPro.this.getString(R.string.app_str_gqpro_has_sync_num), integer));
                    }
                    ActGqPro.this.mAdapter.setData(1, actionItem);
                }
            }
        });
        ((ActGqProVM) this.mViewModel).sceneNum.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqPro.9
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer integer) {
                if (ActGqPro.this.mAdapter.getData().size() > 1) {
                    ActGqProVM.ActionItem actionItem = (ActGqProVM.ActionItem) ActGqPro.this.mAdapter.getData().get(1);
                    if (actionItem.getList() != null && actionItem.getList().size() > 1) {
                        actionItem.getList().get(1).setSub(integer.intValue() == 0 ? ActGqPro.this.getString(R.string.str_not_add) : String.format(ActGqPro.this.getString(R.string.app_str_gqpro_has_sync_num), integer));
                    }
                    ActGqPro.this.mAdapter.setData(1, actionItem);
                }
            }
        });
        ((ActGqProVM) this.mViewModel).picNum.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqPro.10
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer integer) {
                if (ActGqPro.this.mAdapter.getData().isEmpty()) {
                    return;
                }
                ((ActGqProVM.ActionItem) ActGqPro.this.mAdapter.getData().get(0)).setSub(integer.intValue() == 0 ? ActGqPro.this.getString(R.string.app_str_has_no_upload) : String.format(ActGqPro.this.getString(R.string.app_str_gqpro_has_pic_num), integer));
                ActGqPro.this.mAdapter.notifyItemChanged(0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void paras(SuperPanelInfo superPanelInfo) {
        ArrayList arrayList = new ArrayList();
        ArrayList<SuperPanelInfo.SortInfo> arrayList2 = new ArrayList(superPanelInfo.getSortRoleList());
        Collections.sort(arrayList2, new Comparator() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqPro$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ActGqPro.lambda$paras$0((SuperPanelInfo.SortInfo) obj, (SuperPanelInfo.SortInfo) obj2);
            }
        });
        ((ActGqProVM) this.mViewModel).deviceNum.setValue(Integer.valueOf(superPanelInfo.getDeviceIds().size() + superPanelInfo.getGroupIds().size()));
        ((ActGqProVM) this.mViewModel).sceneNum.setValue(Integer.valueOf(superPanelInfo.getSceneIds().size()));
        for (SuperPanelInfo.SortInfo sortInfo : arrayList2) {
            Role roleByRoleId = ((ActGqProVM) this.mViewModel).getRoleByRoleId(sortInfo.getSortId(), sortInfo.getObjectType());
            if (roleByRoleId != null && ((ActGqProVM) this.mViewModel).filterRole(roleByRoleId)) {
                arrayList.add(roleByRoleId);
            }
        }
        ((ActGqProVM) this.mViewModel).roleList.postValue(arrayList);
    }

    static /* synthetic */ int lambda$paras$0(SuperPanelInfo.SortInfo sortInfo, SuperPanelInfo.SortInfo sortInfo2) {
        return sortInfo.getSort() > sortInfo2.getSort() ? 1 : -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public NavUtils.Builder addPlaceIdDeviceId(Class clazz) {
        NavUtils.Builder destination = NavUtils.destination(clazz);
        if (((ActGqProVM) this.mViewModel).device != null) {
            Injection.limiter().reset(Injection.keyCreator().superPanelInfoKey(((ActGqProVM) this.mViewModel).device.getDeviceId()));
            destination.withLong(Constants.PLACE_ID, ((ActGqProVM) this.mViewModel).device.getPlaceId()).withLong("device_id", ((ActGqProVM) this.mViewModel).device.getDeviceId()).withLong(Constants.CONTROL_ID, ((ActGqProVM) this.mViewModel).device.getId()).withInt(Constants.MAX, 32).withBoolean(Constants.IS_GQ, true).withBoolean(Constants.NEED_BLE_SYNC, true).withString(Constants.SELECTED_LIST, GsonUtils.toJson(((ActGqProVM) this.mViewModel).superPanelInfo)).withDefaultRequestCode();
        }
        return destination;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavHelper.goSetting(((ActGqProVM) this.mViewModel).controlObject.getValue());
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            setResult(-1);
            paras(Injection.repo().device().getSuperPanelInfoByDb(((ActGqProVM) this.mViewModel).device.getDeviceId()));
        }
    }
}