package com.ltech.smarthome.ui.device.light;

import android.graphics.Rect;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActLightGroupSubItemControlBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.expand.LightGroupExpandableItem;
import com.ltech.smarthome.model.expand.LightGroupSubExpandableItem;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.control.provider.LightCtGroupSubExpandableItemProvider;
import com.ltech.smarthome.ui.control.provider.LightGroupExpandableItemProvider;
import com.ltech.smarthome.ui.control.provider.LightGroupSubCountItemProvider;
import com.ltech.smarthome.ui.control.provider.LightGroupSubExpandableItemProvider;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.smart.message.MessageManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActLightGroupSubItemControl extends BaseControlActivity<ActLightGroupSubItemControlBinding, ActLightGroupChildItemControlVM> {
    private Group group;
    private boolean isCtGroup = false;
    private MultipleItemRvAdapter<MultiItemEntity, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_light_group_sub_item_control;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.group_device));
        ((ActLightGroupChildItemControlVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        Group groupById = Injection.repo().group().getGroupById(((ActLightGroupChildItemControlVM) this.mViewModel).controlId);
        this.group = groupById;
        if (groupById.getGroupKey().equals(ProductId.BLE_GROUP_CT_LIGHT)) {
            this.isCtGroup = true;
        }
        this.mAdapter = new MultipleItemRvAdapter<MultiItemEntity, BaseViewHolder>(new ArrayList()) { // from class: com.ltech.smarthome.ui.device.light.ActLightGroupSubItemControl.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(MultiItemEntity entity) {
                return entity.getItemType();
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new LightGroupExpandableItemProvider((ActLightGroupChildItemControlVM) ActLightGroupSubItemControl.this.mViewModel));
                if (ActLightGroupSubItemControl.this.isCtGroup) {
                    this.mProviderDelegate.registerProvider(new LightCtGroupSubExpandableItemProvider((ActLightGroupChildItemControlVM) ActLightGroupSubItemControl.this.mViewModel));
                } else {
                    this.mProviderDelegate.registerProvider(new LightGroupSubExpandableItemProvider((ActLightGroupChildItemControlVM) ActLightGroupSubItemControl.this.mViewModel));
                }
                this.mProviderDelegate.registerProvider(new LightGroupSubCountItemProvider());
            }
        };
        ((ActLightGroupSubItemControlBinding) this.mViewBinding).rv.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.light.ActLightGroupSubItemControl.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 0, 0, ConvertUtils.dp2px(5.0f));
            }
        });
        this.mAdapter.finishInitialize();
        this.mAdapter.bindToRecyclerView(((ActLightGroupSubItemControlBinding) this.mViewBinding).rv);
        ((ActLightGroupSubItemControlBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(this));
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActLightGroupSubItemControl.3
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                LightGroupExpandableItem lightGroupExpandableItem;
                if (!(ActLightGroupSubItemControl.this.mAdapter.getItem(position) instanceof LightGroupExpandableItem) || (lightGroupExpandableItem = (LightGroupExpandableItem) ActLightGroupSubItemControl.this.mAdapter.getItem(position)) == null) {
                    return;
                }
                if (lightGroupExpandableItem.isExpanded()) {
                    ActLightGroupSubItemControl.this.mAdapter.collapse(position);
                } else {
                    ActLightGroupSubItemControl.this.mAdapter.expand(position);
                }
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActLightGroupSubItemControl.4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MultiItemEntity multiItemEntity = (MultiItemEntity) ActLightGroupSubItemControl.this.mAdapter.getItem(position);
                if (multiItemEntity instanceof LightGroupExpandableItem) {
                    LightGroupExpandableItem lightGroupExpandableItem = (LightGroupExpandableItem) multiItemEntity;
                    if (lightGroupExpandableItem.getItem() instanceof Device) {
                        ActLightGroupSubItemControl.this.nav((Device) lightGroupExpandableItem.getItem());
                    }
                }
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActLightGroupChildItemControlVM) this.mViewModel).loadData();
        ((ActLightGroupChildItemControlVM) this.mViewModel).refreshData.observe(this, new Observer<List<MultiItemEntity>>() { // from class: com.ltech.smarthome.ui.device.light.ActLightGroupSubItemControl.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<MultiItemEntity> multiItemEntities) {
                ActLightGroupSubItemControl.this.mAdapter.replaceData(multiItemEntities);
                ActLightGroupSubItemControl.this.mAdapter.expand(0);
                ((ActLightGroupChildItemControlVM) ActLightGroupSubItemControl.this.mViewModel).checkSingleDeviceStatus();
            }
        });
        ((ActLightGroupChildItemControlVM) this.mViewModel).groupSwitchChange.observe(this, new Observer<Group>() { // from class: com.ltech.smarthome.ui.device.light.ActLightGroupSubItemControl.6
            @Override // androidx.lifecycle.Observer
            public void onChanged(Group group) {
                if (group != null) {
                    ((ActLightGroupChildItemControlVM) ActLightGroupSubItemControl.this.mViewModel).updateStateLight(group);
                    List<T> data = ActLightGroupSubItemControl.this.mAdapter.getData();
                    for (int i = 0; i < data.size(); i++) {
                        MultiItemEntity multiItemEntity = (MultiItemEntity) data.get(i);
                        if (multiItemEntity instanceof LightGroupExpandableItem) {
                            LightGroupExpandableItem lightGroupExpandableItem = (LightGroupExpandableItem) multiItemEntity;
                            if (lightGroupExpandableItem.getItem() instanceof Device) {
                                lightGroupExpandableItem.getItem().setDeviceState(group.getDeviceState());
                                ActLightGroupSubItemControl.this.mAdapter.refreshNotifyItemChanged(i);
                            }
                        } else if (multiItemEntity instanceof LightGroupSubExpandableItem) {
                            LightGroupSubExpandableItem lightGroupSubExpandableItem = (LightGroupSubExpandableItem) multiItemEntity;
                            if (lightGroupSubExpandableItem.getItem() instanceof Device) {
                                lightGroupSubExpandableItem.getItem().setDeviceState(group.getDeviceState());
                                ActLightGroupSubItemControl.this.mAdapter.refreshNotifyItemChanged(i);
                            }
                        }
                    }
                }
            }
        });
        ((ActLightGroupChildItemControlVM) this.mViewModel).groupChangeOnly.observe(this, new Observer<Group>() { // from class: com.ltech.smarthome.ui.device.light.ActLightGroupSubItemControl.7
            @Override // androidx.lifecycle.Observer
            public void onChanged(Group group) {
                if (group != null) {
                    List<T> data = ActLightGroupSubItemControl.this.mAdapter.getData();
                    for (int i = 0; i < data.size(); i++) {
                        MultiItemEntity multiItemEntity = (MultiItemEntity) data.get(i);
                        if (multiItemEntity instanceof LightGroupExpandableItem) {
                            LightGroupExpandableItem lightGroupExpandableItem = (LightGroupExpandableItem) multiItemEntity;
                            if (lightGroupExpandableItem.getItem() instanceof Group) {
                                lightGroupExpandableItem.getItem().setDeviceState(group.getGroupState());
                                ActLightGroupSubItemControl.this.mAdapter.refreshNotifyItemChanged(i);
                            }
                        } else if (multiItemEntity instanceof LightGroupSubExpandableItem) {
                            LightGroupSubExpandableItem lightGroupSubExpandableItem = (LightGroupSubExpandableItem) multiItemEntity;
                            if (lightGroupSubExpandableItem.getItem() instanceof Group) {
                                lightGroupSubExpandableItem.getItem().setDeviceState(group.getGroupState());
                                ActLightGroupSubItemControl.this.mAdapter.refreshNotifyItemChanged(i);
                            }
                        }
                    }
                }
            }
        });
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        MultipleItemRvAdapter<MultiItemEntity, BaseViewHolder> multipleItemRvAdapter = this.mAdapter;
        if (multipleItemRvAdapter != null && multipleItemRvAdapter.getData().size() > 0) {
            List<MultiItemEntity> data = this.mAdapter.getData();
            for (int i = 0; i < data.size(); i++) {
                MultiItemEntity multiItemEntity = data.get(i);
                if (multiItemEntity instanceof LightGroupSubExpandableItem) {
                    LightGroupSubExpandableItem lightGroupSubExpandableItem = (LightGroupSubExpandableItem) multiItemEntity;
                    if (lightGroupSubExpandableItem.getItem() instanceof Device) {
                        lightGroupSubExpandableItem.getItem().setDeviceState(Injection.repo().device().getDeviceByDeviceId(lightGroupSubExpandableItem.getItem().getObjectId()).getDeviceState());
                        this.mAdapter.refreshNotifyItemChanged(i);
                    }
                }
            }
        }
        regStatus();
    }

    public void regStatus() {
        MessageManager.getInstance().setLightStatusCallBack(new MessageManager.LightStatusCallBack() { // from class: com.ltech.smarthome.ui.device.light.ActLightGroupSubItemControl.8
            @Override // com.smart.message.MessageManager.LightStatusCallBack
            public void onDataReceive(int deviceAddress, boolean isOn, int wyBrt, int wy, int rgbBrt, boolean rgbOn, boolean supportK, boolean rhythmPlay, int rgbColor) {
                Device device;
                List<T> data = ActLightGroupSubItemControl.this.mAdapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    MultiItemEntity multiItemEntity = (MultiItemEntity) data.get(i);
                    if (multiItemEntity instanceof LightGroupExpandableItem) {
                        LightGroupExpandableItem lightGroupExpandableItem = (LightGroupExpandableItem) multiItemEntity;
                        if ((lightGroupExpandableItem.getItem() instanceof Device) && (device = (Device) lightGroupExpandableItem.getItem()) != null && ((BleParam) device.getParam(BleParam.class)).getUnicastAddress() == deviceAddress) {
                            device.getDeviceState().setOn(isOn);
                            device.getDeviceState().setWyBrt(LightUtils.brt2ProgressHasBelowZero(wyBrt));
                            device.getDeviceState().setWy(wy);
                            device.getDeviceState().setRgbBrt(LightUtils.brt2ProgressHasBelowZero(rgbBrt));
                            ActLightGroupSubItemControl.this.mAdapter.refreshNotifyItemChanged(i);
                            ActLightGroupSubItemControl.this.mAdapter.refreshNotifyItemChanged(i + 1);
                            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getDeviceId());
                            deviceByDeviceId.getDeviceState().setOn(isOn);
                            deviceByDeviceId.getDeviceState().setWyBrt(LightUtils.brt2ProgressHasBelowZero(wyBrt));
                            deviceByDeviceId.getDeviceState().setWy(wy);
                            deviceByDeviceId.getDeviceState().setRgbBrt(LightUtils.brt2ProgressHasBelowZero(rgbBrt));
                            deviceByDeviceId.setOnlineFlag(1);
                            deviceByDeviceId.getDeviceState().setOnlineState(1);
                            Injection.repo().device().saveDevice(deviceByDeviceId);
                            if (device.getDeviceId() == ActLightGroupSubItemControl.this.group.getDeviceIds().get(0).longValue()) {
                                ((ActLightGroupChildItemControlVM) ActLightGroupSubItemControl.this.mViewModel).updateStateGroup(device);
                            }
                        }
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nav(Device device) {
        NavUtils.Builder destination;
        int lightColorType = ProductRepository.getLightColorType((Object) device);
        if (lightColorType == 1) {
            destination = NavUtils.destination(ActDimLight.class);
        } else if (lightColorType == 2) {
            destination = NavUtils.destination(ActCtLight.class);
        } else if (lightColorType == 3 || lightColorType == 4 || lightColorType == 5) {
            destination = NavUtils.destination(ActColorLight.class);
        } else if (lightColorType != 7) {
            destination = lightColorType != 20 ? null : NavUtils.destination(ActColorCCLight.class);
        } else {
            destination = NavUtils.destination(ActModuleSwitch.class);
        }
        if (destination != null) {
            destination.withLong(Constants.CONTROL_ID, device.getId()).withInt(Constants.LIGHT_TYPE, lightColorType).withBoolean(Constants.GROUP_CONTROL, false).navigation(this);
        }
    }
}