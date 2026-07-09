package com.ltech.smarthome.ui.matter;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActMatterSubListBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.response.MatterDeviceResponse;
import com.ltech.smarthome.ui.matter.ActMatterSubVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActMatterSub extends VMActivity<ActMatterSubListBinding, ActMatterSubVM> {
    private BaseQuickAdapter<MatterDeviceResponse.MatterDevice, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_matter_sub_list;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.app_str_connect_matter));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.disable));
        ((ActMatterSubVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActMatterSubVM) this.mViewModel).device = Injection.repo().device().getDeviceById(((ActMatterSubVM) this.mViewModel).controlId);
        ((ActMatterSubListBinding) this.mViewBinding).layoutQrcode.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.matter.ActMatterSub.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ((ActMatterSubVM) ActMatterSub.this.mViewModel).showDialog(ActMatterSub.this.activity);
            }
        });
        ((ActMatterSubListBinding) this.mViewBinding).layoutPlatform.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.matter.ActMatterSub.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                NavUtils.destination(ActMatterPlatform.class).withLong(Constants.CONTROL_ID, ((ActMatterSubVM) ActMatterSub.this.mViewModel).controlId).withString(Constants.MODE_DATA, GsonUtils.toJson(((ActMatterSubVM) ActMatterSub.this.mViewModel).fabricData.getValue())).navigation(ActMatterSub.this.activity);
            }
        });
        ((ActMatterSubListBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.matter.ActMatterSub.3
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public void onRefresh(RefreshLayout refreshLayout) {
                ((ActMatterSubVM) ActMatterSub.this.mViewModel).checkFabric();
                ((ActMatterSubVM) ActMatterSub.this.mViewModel).loadDevice();
            }
        });
        ((ActMatterSubListBinding) this.mViewBinding).tvSync.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.matter.ActMatterSub.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ActMatterSub.this.sync();
            }
        });
        ((ActMatterSubListBinding) this.mViewBinding).ivSync.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.matter.ActMatterSub.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                MessageDialog.show(ActMatterSub.this.activity, ActMatterSub.this.getString(R.string.tips), ActMatterSub.this.getString(R.string.app_matter_sync_scene_empty_tip), ActMatterSub.this.getString(R.string.ok));
            }
        });
        if (isNew()) {
            initTab();
        } else {
            initRv();
        }
        ((ActMatterSubVM) this.mViewModel).loadFabric(getIntent());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActMatterSubVM) this.mViewModel).fabricData.observe(this, new Observer<List<ActMatterSubVM.Fabric>>() { // from class: com.ltech.smarthome.ui.matter.ActMatterSub.6
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<ActMatterSubVM.Fabric> fabrics) {
                ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).tvPlatformNum.setText(fabrics.size() + "");
            }
        });
        ((ActMatterSubVM) this.mViewModel).matterDevices.observe(this, new Observer<List<MatterDeviceResponse.MatterDevice>>() { // from class: com.ltech.smarthome.ui.matter.ActMatterSub.7
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<MatterDeviceResponse.MatterDevice> rowsBeans) {
                ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).refreshLayout.finishRefresh();
                if (ActMatterSub.this.isNew()) {
                    if (((ActMatterSubVM) ActMatterSub.this.mViewModel).chooseTabEvent.getValue().intValue() == 0) {
                        if (rowsBeans.isEmpty()) {
                            ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).layoutNum.setVisibility(8);
                            ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).ivSync.setVisibility(8);
                            return;
                        }
                        int i = 0;
                        for (MatterDeviceResponse.MatterDevice matterDevice : rowsBeans) {
                            if (matterDevice != null) {
                                i += matterDevice.getNum();
                            }
                        }
                        ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).layoutNum.setVisibility(0);
                        ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).ivSync.setVisibility(8);
                        ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).tvSyncNum.setText(ActMatterSub.this.getString(R.string.app_str_has_sync) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + i);
                        return;
                    }
                    return;
                }
                if (rowsBeans.isEmpty()) {
                    ActMatterSub.this.showEmpty();
                    ActMatterSub.this.mAdapter.setNewData(new ArrayList());
                } else {
                    ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).tvDeviceNum.setText(String.format(ActMatterSub.this.getString(R.string.app_str_sonos_has_sync_devices_num), Integer.valueOf(rowsBeans.size())));
                    ActMatterSub.this.showContent();
                    ActMatterSub.this.mAdapter.setNewData(rowsBeans);
                }
            }
        });
        ((ActMatterSubVM) this.mViewModel).matterScenes.observe(this, new Observer<List<MatterDeviceResponse.MatterDevice>>() { // from class: com.ltech.smarthome.ui.matter.ActMatterSub.8
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<MatterDeviceResponse.MatterDevice> rowsBeans) {
                ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).refreshLayout.finishRefresh();
                if (ActMatterSub.this.isNew() && ((ActMatterSubVM) ActMatterSub.this.mViewModel).chooseTabEvent.getValue().intValue() == 1) {
                    if (!rowsBeans.isEmpty()) {
                        ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).layoutNum.setVisibility(0);
                        ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).ivSync.setVisibility(0);
                        ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).tvSyncNum.setText(ActMatterSub.this.getString(R.string.app_str_has_sync) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + rowsBeans.size());
                        return;
                    }
                    ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).layoutNum.setVisibility(8);
                    ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).ivSync.setVisibility(8);
                }
            }
        });
        ((ActMatterSubVM) this.mViewModel).chooseTabEvent.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.matter.ActMatterSub.9
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer tab) {
                ActMatterSub actMatterSub;
                int i;
                AppCompatTextView appCompatTextView = ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).tvDevice;
                int intValue = tab.intValue();
                int i2 = R.drawable.shape_white_round_bg_10;
                int i3 = 0;
                appCompatTextView.setBackgroundResource(intValue == 0 ? R.drawable.shape_white_round_bg_10 : 0);
                ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).tvDevice.setTypeface(tab.intValue() == 0 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
                AppCompatTextView appCompatTextView2 = ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).tvScene;
                if (tab.intValue() != 1) {
                    i2 = 0;
                }
                appCompatTextView2.setBackgroundResource(i2);
                ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).tvScene.setTypeface(tab.intValue() == 1 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
                FragmentUtils.showHide(tab.intValue(), ((ActMatterSubVM) ActMatterSub.this.mViewModel).fragmentList);
                AppCompatTextView appCompatTextView3 = ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).tvSync;
                if (tab.intValue() == 0) {
                    actMatterSub = ActMatterSub.this;
                    i = R.string.app_str_sync_device;
                } else {
                    actMatterSub = ActMatterSub.this;
                    i = R.string.app_str_sync_scene;
                }
                appCompatTextView3.setText(actMatterSub.getString(i));
                if (((ActMatterSubVM) ActMatterSub.this.mViewModel).matterScenes.getValue() == null || tab.intValue() != 1 || ((ActMatterSubVM) ActMatterSub.this.mViewModel).matterScenes.getValue().isEmpty()) {
                    if (((ActMatterSubVM) ActMatterSub.this.mViewModel).matterDevices.getValue() == null || tab.intValue() != 0 || ((ActMatterSubVM) ActMatterSub.this.mViewModel).matterDevices.getValue().isEmpty()) {
                        ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).layoutNum.setVisibility(8);
                        ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).ivSync.setVisibility(8);
                        return;
                    }
                    ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).layoutNum.setVisibility(0);
                    ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).ivSync.setVisibility(8);
                    for (MatterDeviceResponse.MatterDevice matterDevice : ((ActMatterSubVM) ActMatterSub.this.mViewModel).matterDevices.getValue()) {
                        if (matterDevice != null) {
                            i3 += matterDevice.getNum();
                        }
                    }
                    ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).tvSyncNum.setText(ActMatterSub.this.getString(R.string.app_str_has_sync) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + i3);
                    return;
                }
                ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).layoutNum.setVisibility(0);
                ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).ivSync.setVisibility(0);
                ((ActMatterSubListBinding) ActMatterSub.this.mViewBinding).tvSyncNum.setText(ActMatterSub.this.getString(R.string.app_str_has_sync) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + ((ActMatterSubVM) ActMatterSub.this.mViewModel).matterScenes.getValue().size());
            }
        });
        ((ActMatterSubVM) this.mViewModel).loadDevice();
    }

    private void initTab() {
        ((ActMatterSubListBinding) this.mViewBinding).rv.setVisibility(8);
        ((ActMatterSubListBinding) this.mViewBinding).layoutTab.setVisibility(0);
        ((ActMatterSubListBinding) this.mViewBinding).fragmentContainer.setVisibility(0);
        ((ActMatterSubListBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.matter.ActMatterSub$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActMatterSub.this.lambda$initTab$0((View) obj);
            }
        }));
        initFragment();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTab$0(View view) {
        int id = view.getId();
        if (id == R.id.tv_device) {
            ((ActMatterSubVM) this.mViewModel).chooseTabEvent.setValue(0);
        } else {
            if (id != R.id.tv_scene) {
                return;
            }
            ((ActMatterSubVM) this.mViewModel).chooseTabEvent.setValue(1);
        }
    }

    private void initFragment() {
        ((ActMatterSubVM) this.mViewModel).fragmentList.clear();
        ((ActMatterSubVM) this.mViewModel).fragmentList.add(new FtMatterDevice());
        ((ActMatterSubVM) this.mViewModel).fragmentList.add(new FtMatterScene());
        FragmentUtils.add(getSupportFragmentManager(), ((ActMatterSubVM) this.mViewModel).fragmentList, R.id.fragment_container, 0);
    }

    private void initRv() {
        ((ActMatterSubListBinding) this.mViewBinding).rv.setVisibility(0);
        ((ActMatterSubListBinding) this.mViewBinding).layoutTab.setVisibility(8);
        ((ActMatterSubListBinding) this.mViewBinding).fragmentContainer.setVisibility(8);
        ((ActMatterSubListBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(this.activity));
        RecyclerView recyclerView = ((ActMatterSubListBinding) this.mViewBinding).rv;
        BaseQuickAdapter<MatterDeviceResponse.MatterDevice, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<MatterDeviceResponse.MatterDevice, BaseViewHolder>(this, R.layout.item_device_manage) { // from class: com.ltech.smarthome.ui.matter.ActMatterSub.10
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
        ((ActMatterSubListBinding) this.mViewBinding).rv.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.matter.ActMatterSub.11
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = ConvertUtils.dp2px(15.0f);
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        showDisableDialog();
    }

    private void showDisableDialog() {
        MessageDialog.show(this.activity, getString(R.string.app_str_matter_disable_title), getString(R.string.app_str_matter_disable_tip)).setOkButton(getString(R.string.confirm)).setOkButton(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.matter.ActMatterSub.12
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                ((ActMatterSubVM) ActMatterSub.this.mViewModel).quitMatter();
                return false;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sync() {
        int i = 0;
        if (((ActMatterSubVM) this.mViewModel).chooseTabEvent.getValue() == null || ((ActMatterSubVM) this.mViewModel).chooseTabEvent.getValue().intValue() == 0) {
            long[] jArr = new long[((ActMatterSubVM) this.mViewModel).deviceIds.size()];
            int size = ((ActMatterSubVM) this.mViewModel).deviceIds.size();
            for (int i2 = 0; i2 < size; i2++) {
                jArr[i2] = ((ActMatterSubVM) this.mViewModel).deviceIds.get(i2).longValue();
            }
            long[] jArr2 = new long[((ActMatterSubVM) this.mViewModel).groupIds.size()];
            int size2 = ((ActMatterSubVM) this.mViewModel).groupIds.size();
            while (i < size2) {
                jArr2[i] = ((ActMatterSubVM) this.mViewModel).groupIds.get(i).longValue();
                i++;
            }
            NavUtils.destination(ActSelectDeviceGroupForMatter.class).withLong(Constants.PLACE_ID, ((ActMatterSubVM) this.mViewModel).device.getPlaceId()).withLong("device_id", ((ActMatterSubVM) this.mViewModel).device.getDeviceId()).withString(Constants.MAC_ADDRESS, ((ActMatterSubVM) this.mViewModel).device.getWifiMac()).withString(Constants.PRODUCT_ID, ((ActMatterSubVM) this.mViewModel).device.getProductId()).withLongArray(Constants.DEVICE_ID_ARRAY, jArr).withLongArray(Constants.GROUP_ID_ARRAY, jArr2).withInt(Constants.MAX, getIntent().getIntExtra(Constants.MAX, -1) - ((ActMatterSubVM) this.mViewModel).scenesIds.size()).withDefaultRequestCode().navigation(this);
            return;
        }
        long[] jArr3 = new long[((ActMatterSubVM) this.mViewModel).scenesIds.size()];
        int size3 = ((ActMatterSubVM) this.mViewModel).scenesIds.size();
        for (int i3 = 0; i3 < size3; i3++) {
            jArr3[i3] = ((ActMatterSubVM) this.mViewModel).scenesIds.get(i3).longValue();
        }
        Iterator<Long> it = ((ActMatterSubVM) this.mViewModel).deviceIds.iterator();
        while (it.hasNext()) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(it.next().longValue());
            if (deviceByDeviceId != null) {
                i += ProductRepository.getRelayCount(ProductRepository.getLightColorType((Object) deviceByDeviceId));
            }
        }
        Iterator<Long> it2 = ((ActMatterSubVM) this.mViewModel).groupIds.iterator();
        while (it2.hasNext()) {
            Group groupByGroupId = Injection.repo().group().getGroupByGroupId(it2.next().longValue());
            if (groupByGroupId != null) {
                i += ProductRepository.getRelayCount(ProductRepository.getLightColorType((Object) groupByGroupId));
            }
        }
        NavUtils.destination(ActSelectSceneForMatter.class).withLong(Constants.PLACE_ID, ((ActMatterSubVM) this.mViewModel).device.getPlaceId()).withLong("device_id", ((ActMatterSubVM) this.mViewModel).device.getDeviceId()).withString(Constants.MAC_ADDRESS, ((ActMatterSubVM) this.mViewModel).device.getWifiMac()).withString(Constants.PRODUCT_ID, ((ActMatterSubVM) this.mViewModel).device.getProductId()).withLongArray(Constants.SCENE_ID_ARRAY, jArr3).withInt(Constants.MAX, getIntent().getIntExtra(Constants.MAX, -1) - i).withDefaultRequestCode().navigation(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            ((ActMatterSubVM) this.mViewModel).loadDevice();
        }
    }

    public boolean isNew() {
        if (((ActMatterSubVM) this.mViewModel).device == null || "SVER000.003.000".compareTo(((ActMatterSubVM) this.mViewModel).device.getFirmwareversion().toUpperCase()) < 0) {
            return AppUtils.isAppDebug();
        }
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.no_device));
    }
}