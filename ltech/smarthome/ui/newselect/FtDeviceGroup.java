package com.ltech.smarthome.ui.newselect;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.databinding.FtDeviceAndGroupBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.device.setting.ActDeviceSettingDefault;
import com.ltech.smarthome.ui.newselect.FtDeviceGroup;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.ui.scene.ActSelectDeviceForCondition;
import com.ltech.smarthome.ui.scene.ActSelectDeviceGroupForAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.CircularProgressView;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class FtDeviceGroup extends VMFragment<FtDeviceAndGroupBinding, FtDeviceGroupVM> {
    public static final int SORT_DALI_LIGHT_ADD = 11;
    public static final int SORT_DALI_LIGHT_TYPE = 12;
    public static final int TYPE_BIND_GROUP_OR_DEVICE = 4;
    public static final int TYPE_DEVICE_MANAGE = 2;
    public static final int TYPE_OTHER = 5;
    public static final int TYPE_SELECT_DEVICE = 1;
    public static final int TYPE_SELECT_DEVICE_OR_GROUP_ONE = 8;
    public static final int TYPE_SELECT_SCENE = 7;
    public static final int TYPE_SELECT_SYNC_DEVICE = 3;
    public static final int TYPE_SORT_DEVICE = 6;
    public BaseRoomDeviceGroupActivity activity;
    private MultipleItemRvAdapter<Role, BaseViewHolder> deviceAdapter;
    private BaseItemDraggableAdapter<Role, BaseViewHolder> deviceDraggableAdapter;
    private long floorId;
    private long placeId;
    private long roomId;
    private int selMax;
    public HashMap<Long, FtDeviceGroupVM.InOrOutGroupItem> inOrOutGroupItemMap = new HashMap<>();
    public MutableLiveData<List<Role>> deviceDataEvent = new MutableLiveData<>();
    public SingleLiveEvent<Void> refreshList = new SingleLiveEvent<>();
    public SingleLiveEvent<Integer> sortType = new SingleLiveEvent<>(0);
    public SingleLiveEvent<Integer> sortSceneType = new SingleLiveEvent<>(0);
    public List<Role> roleList = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_device_and_group;
    }

    public static FtDeviceGroup newInstance(long placeId, long roomId, long floorId) {
        FtDeviceGroup ftDeviceGroup = new FtDeviceGroup();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.PLACE_ID, placeId);
        bundle.putLong(Constants.ROOM_ID, roomId);
        bundle.putLong(Constants.FLOOR_ID, floorId);
        ftDeviceGroup.setArguments(bundle);
        return ftDeviceGroup;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void showEmpty() {
        if ((ActivityUtils.getTopActivity() instanceof ActSelectDeviceGroupForAction) || (ActivityUtils.getTopActivity() instanceof ActSelectDeviceForCondition)) {
            if (Injection.repo().device().getSuperPanel() == null) {
                this.mHolder.setAdapter(new DefaultAdapter().emptyStringRes(R.string.app_no_intelligent_gateway));
            }
        } else if (this.activity.listType == 7) {
            this.mHolder.setAdapter(new DefaultAdapter().emptyStringRes(R.string.no_scence));
        } else {
            this.mHolder.setAdapter(new DefaultAdapter().emptyStringRes(R.string.device_choice_list_empty));
        }
        super.showEmpty();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected Gloading createGLoading() {
        if (((ActivityUtils.getTopActivity() instanceof ActSelectDeviceGroupForAction) || (ActivityUtils.getTopActivity() instanceof ActSelectDeviceForCondition)) && Injection.repo().device().getSuperPanel() == null) {
            return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.app_no_intelligent_gateway));
        }
        return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.device_choice_list_empty));
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initData() {
        super.initData();
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.roomId = arguments.getLong(Constants.ROOM_ID);
            ((FtDeviceGroupVM) this.mViewModel).placeId.setValue(Long.valueOf(arguments.getLong(Constants.PLACE_ID)));
            this.floorId = arguments.getLong(Constants.FLOOR_ID);
            this.placeId = ((FtDeviceGroupVM) this.mViewModel).placeId.getValue().longValue();
        }
        ((FtDeviceGroupVM) this.mViewModel).floorList = Injection.repo().home().getFloorListByPlaceId(this.placeId);
        ((FtDeviceGroupVM) this.mViewModel).roomList = Injection.repo().home().getRoomList(this.placeId);
        getData();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        BaseRoomDeviceGroupActivity baseRoomDeviceGroupActivity = this.activity;
        if (baseRoomDeviceGroupActivity != null) {
            if (baseRoomDeviceGroupActivity.listType == 6) {
                initRoleDraggableAdapter();
            } else {
                initRoleAdapter();
            }
            this.selMax = this.activity.getIntent().getIntExtra(Constants.MAX, -1);
        }
    }

    private void initRoleAdapter() {
        MultipleItemRvAdapter<Role, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<Role, BaseViewHolder>(this.roleList) { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroup.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(Role role) {
                return FtDeviceGroup.this.activity.listType;
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new BaseItemProvider<Role, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroup.1.1
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_select_device_with_place;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 1;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, Role item, int position) {
                        helper.setText(R.id.tv_device_name, item.getName());
                        helper.setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item));
                        String placeInfo = ((FtDeviceGroupVM) FtDeviceGroup.this.mViewModel).getPlaceInfo(item.getFloorId(), item.getRoomId());
                        helper.setGone(R.id.tv_place_info, !TextUtils.isEmpty(placeInfo));
                        helper.setText(R.id.tv_place_info, placeInfo);
                        helper.setGone(R.id.tv_virtual, item.isVirtual());
                        if (FtDeviceGroup.this.activity.selectRoleIds.contains(Long.valueOf(item.getObjectId()))) {
                            helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_sel);
                        } else {
                            helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_default);
                        }
                        helper.setVisible(R.id.tv_dali, ProductRepository.isDaliLightGroup(item));
                        if (item instanceof Device) {
                            FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem = FtDeviceGroup.this.inOrOutGroupItemMap.get(Long.valueOf(item.getId()));
                            if (inOrOutGroupItem != null) {
                                if (inOrOutGroupItem.state == FtDeviceGroupVM.StateParam.STATE_WORKING || inOrOutGroupItem.state == FtDeviceGroupVM.StateParam.STATE_COMPLETED) {
                                    helper.setVisible(R.id.iv_upgrade_waiting, true);
                                    helper.setVisible(R.id.iv_select, false);
                                    ((CircularProgressView) helper.getView(R.id.iv_upgrade_waiting)).setProgress(inOrOutGroupItem.progress);
                                    return;
                                } else {
                                    helper.setVisible(R.id.iv_select, true);
                                    helper.setVisible(R.id.iv_upgrade_waiting, false);
                                    return;
                                }
                            }
                            helper.setVisible(R.id.iv_select, true);
                            helper.setVisible(R.id.iv_upgrade_waiting, false);
                        }
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void onClick(BaseViewHolder helper, Role data, int position) {
                        Role role = getData().get(position);
                        if (FtDeviceGroup.this.activity.canSelected(role)) {
                            if (FtDeviceGroup.this.activity.selectRoleIds.contains(Long.valueOf(role.getObjectId()))) {
                                FtDeviceGroup.this.activity.selectRoleIds.remove(Long.valueOf(role.getObjectId()));
                                FtDeviceGroup.this.activity.matterRealSelectedNum -= ProductRepository.getRelayCount(ProductRepository.getLightColorType(role));
                                helper.getView(R.id.iv_select).setBackgroundResource(R.mipmap.ic_tick_default);
                            } else {
                                int relayCount = ProductRepository.getRelayCount(ProductRepository.getLightColorType(role));
                                if (!FtDeviceGroup.this.activity.isMatter() && FtDeviceGroup.this.selMax > 0 && FtDeviceGroup.this.activity.selectRoleIds.size() >= FtDeviceGroup.this.selMax) {
                                    SmartToast.showCenterShort(String.format(FtDeviceGroup.this.getString(R.string.app_str_gqpro_syning_num_tip), Integer.valueOf(FtDeviceGroup.this.selMax), FtDeviceGroup.this.getString(R.string.device)));
                                    return;
                                }
                                FtDeviceGroup.this.activity.matterRealSelectedNum += relayCount;
                                FtDeviceGroup.this.activity.selectRoleIds.add(Long.valueOf(role.getObjectId()));
                                helper.getView(R.id.iv_select).setBackgroundResource(R.mipmap.ic_tick_sel);
                            }
                            FtDeviceGroup.this.activity.selectCountLiveData.setValue(Integer.valueOf(FtDeviceGroup.this.activity.isMatter() ? FtDeviceGroup.this.activity.matterRealSelectedNum : FtDeviceGroup.this.activity.selectRoleIds.size()));
                        }
                    }
                });
                this.mProviderDelegate.registerProvider(new AnonymousClass2());
                this.mProviderDelegate.registerProvider(new AnonymousClass3());
                this.mProviderDelegate.registerProvider(new BaseItemProvider<Role, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroup.1.4
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_device_manage;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 4;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, Role item, int position) {
                        helper.setText(R.id.tv_device_name, item.getName());
                        helper.setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item));
                        String placeInfo = ((FtDeviceGroupVM) FtDeviceGroup.this.mViewModel).getPlaceInfo(item.getFloorId(), item.getRoomId());
                        helper.setGone(R.id.tv_place_info, !TextUtils.isEmpty(placeInfo));
                        helper.setText(R.id.tv_place_info, placeInfo);
                        helper.setGone(R.id.tv_dali, ProductRepository.isDaliLightGroup(item));
                        helper.setGone(R.id.tv_virtual, item.isVirtual());
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void onClick(BaseViewHolder helper, Role data, int position) {
                        FtDeviceGroup.this.activity.roleClick(data);
                        if (data instanceof Device) {
                            FtDeviceGroup.this.activity.deviceClick((Device) data);
                        } else if (data instanceof Group) {
                            FtDeviceGroup.this.activity.groupClick((Group) data);
                        }
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<Role, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroup.1.5
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 5;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return FtDeviceGroup.this.activity.itemLayout();
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, Role item, int position) {
                        if (helper.getView(R.id.tv_virtual) != null) {
                            helper.setGone(R.id.tv_virtual, item.isVirtual());
                        }
                        FtDeviceGroup.this.activity.convertView(helper, item);
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<Role, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroup.1.6
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_scene_new;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 7;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, Role item, int position) {
                        helper.setText(R.id.tv_name, item.getName()).setImageResource(R.id.appCompatImageView14, SceneHelper.getSceneIcon(ActivityUtils.getTopActivity(), item.getIconPos())).setGone(R.id.iv_edit, false).setGone(R.id.iv_select, true).setGone(R.id.tv_type, false);
                        if (FtDeviceGroup.this.activity.selectRoleIds.contains(Long.valueOf(item.getObjectId()))) {
                            helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_sel);
                        } else {
                            helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_default);
                        }
                        helper.setGone(R.id.tv_type, true);
                        FtDeviceGroup.this.showSceneItemByType(helper, item);
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void onClick(BaseViewHolder helper, Role data, int position) {
                        Role role = getData().get(position);
                        if (FtDeviceGroup.this.activity.selectRoleIds.contains(Long.valueOf(role.getObjectId()))) {
                            FtDeviceGroup.this.activity.selectRoleIds.remove(Long.valueOf(role.getObjectId()));
                            helper.getView(R.id.iv_select).setBackgroundResource(R.mipmap.ic_tick_default);
                        } else if (FtDeviceGroup.this.selMax > 0 && FtDeviceGroup.this.activity.selectRoleIds.size() >= FtDeviceGroup.this.selMax) {
                            SmartToast.showCenterShort(String.format(FtDeviceGroup.this.getString(R.string.app_str_gqpro_syning_num_tip), Integer.valueOf(FtDeviceGroup.this.selMax), FtDeviceGroup.this.getString(R.string.scenes)));
                            return;
                        } else {
                            FtDeviceGroup.this.activity.selectRoleIds.add(Long.valueOf(role.getObjectId()));
                            helper.getView(R.id.iv_select).setBackgroundResource(R.mipmap.ic_tick_sel);
                        }
                        FtDeviceGroup.this.activity.selectCountLiveData.setValue(Integer.valueOf(FtDeviceGroup.this.activity.selectRoleIds.size()));
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<Role, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroup.1.7
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_select_device_with_place;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 8;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, Role item, int position) {
                        helper.setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item));
                        String placeInfo = ((FtDeviceGroupVM) FtDeviceGroup.this.mViewModel).getPlaceInfo(item.getFloorId(), item.getRoomId());
                        helper.setGone(R.id.tv_place_info, !TextUtils.isEmpty(placeInfo));
                        helper.setText(R.id.tv_place_info, placeInfo);
                        helper.setText(R.id.tv_device_name, item.getName());
                        helper.setGone(R.id.tv_virtual, item.isVirtual());
                        if (FtDeviceGroup.this.activity.selectRoleIds.contains(Long.valueOf(item.getObjectId()))) {
                            helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_sel);
                        } else {
                            helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_default);
                        }
                        helper.setVisible(R.id.tv_dali, ProductRepository.isDaliLightGroup(item));
                        if (item instanceof Device) {
                            FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem = FtDeviceGroup.this.inOrOutGroupItemMap.get(Long.valueOf(item.getId()));
                            if (inOrOutGroupItem != null) {
                                if (inOrOutGroupItem.state == FtDeviceGroupVM.StateParam.STATE_WORKING || inOrOutGroupItem.state == FtDeviceGroupVM.StateParam.STATE_COMPLETED) {
                                    helper.setVisible(R.id.iv_upgrade_waiting, true);
                                    helper.setVisible(R.id.iv_select, false);
                                    ((CircularProgressView) helper.getView(R.id.iv_upgrade_waiting)).setProgress(inOrOutGroupItem.progress);
                                    return;
                                } else {
                                    helper.setVisible(R.id.iv_select, true);
                                    helper.setVisible(R.id.iv_upgrade_waiting, false);
                                    return;
                                }
                            }
                            helper.setVisible(R.id.iv_select, true);
                            helper.setVisible(R.id.iv_upgrade_waiting, false);
                        }
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void onClick(BaseViewHolder helper, Role data, int position) {
                        Role role = getData().get(position);
                        if (FtDeviceGroup.this.activity.selectRoleIds.size() > 0) {
                            for (int i = 0; i < FtDeviceGroup.this.activity.selectRoleIds.size(); i++) {
                                FtDeviceGroup.this.activity.selectRoleIds.remove(FtDeviceGroup.this.activity.selectRoleIds.get(i));
                            }
                        }
                        FtDeviceGroup.this.activity.selectRoleIds.add(Long.valueOf(role.getObjectId()));
                        notifyDataSetChanged();
                        FtDeviceGroup.this.activity.selectCountLiveData.setValue(Integer.valueOf(FtDeviceGroup.this.activity.selectRoleIds.size()));
                    }
                });
            }

            /* renamed from: com.ltech.smarthome.ui.newselect.FtDeviceGroup$1$2, reason: invalid class name */
            class AnonymousClass2 extends BaseItemProvider<Role, BaseViewHolder> {
                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int layout() {
                    return R.layout.item_device_manage_new;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int viewType() {
                    return 2;
                }

                AnonymousClass2() {
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void convert(BaseViewHolder helper, final Role item, int position) {
                    helper.setText(R.id.tv_device_name, item.getName());
                    helper.setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item));
                    String placeInfo = ((FtDeviceGroupVM) FtDeviceGroup.this.mViewModel).getPlaceInfo(item.getFloorId(), item.getRoomId());
                    helper.setGone(R.id.tv_place_info, !TextUtils.isEmpty(placeInfo));
                    helper.setText(R.id.tv_place_info, placeInfo);
                    helper.setGone(R.id.tv_virtual, item.isVirtual());
                    helper.setGone(R.id.iv_location, ((FtDeviceGroupVM) FtDeviceGroup.this.mViewModel).needLocation(item));
                    helper.getView(R.id.iv_location).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroup$1$2$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            FtDeviceGroup.AnonymousClass1.AnonymousClass2.this.lambda$convert$0(item, view);
                        }
                    });
                    helper.getView(R.id.iv_edit).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroup$1$2$$ExternalSyntheticLambda1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            FtDeviceGroup.AnonymousClass1.AnonymousClass2.this.lambda$convert$1(item, view);
                        }
                    });
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$convert$0(Role role, View view) {
                    if ((role instanceof Group) && ((Group) role).getDeviceIds().isEmpty()) {
                        SmartToast.showShort(R.string.app_str_empty_group_tip);
                    } else {
                        CmdAssistant.getSettingCmdAssistant(role, new int[0]).testDeviceLocation(FtDeviceGroup.this.getActivity());
                    }
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$convert$1(Role role, View view) {
                    ((FtDeviceGroupVM) FtDeviceGroup.this.mViewModel).showEditDialog(role);
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void onClick(BaseViewHolder helper, Role data, int position) {
                    Place value = Injection.repo().home().getSelectPlace().getValue();
                    if (value != null && FtDeviceGroup.this.placeId != value.getPlaceId()) {
                        if (data instanceof Device) {
                            Device device = (Device) data;
                            NavUtils.destination(ActDeviceSettingDefault.class).withLong(Constants.PLACE_ID, FtDeviceGroup.this.placeId).withLong(Constants.CONTROL_ID, device.getId()).withLong("device_id", device.getDeviceId()).withBoolean(Constants.SAME_PLACE, true).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
                            return;
                        }
                        return;
                    }
                    NavHelper.goSetting(data);
                }
            }

            /* renamed from: com.ltech.smarthome.ui.newselect.FtDeviceGroup$1$3, reason: invalid class name */
            class AnonymousClass3 extends BaseItemProvider<Role, BaseViewHolder> {
                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int layout() {
                    return R.layout.item_select_device_with_sync;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int viewType() {
                    return 3;
                }

                AnonymousClass3() {
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void convert(BaseViewHolder helper, final Role item, int position) {
                    helper.setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item));
                    helper.setText(R.id.tv_place_info, ((FtDeviceGroupVM) FtDeviceGroup.this.mViewModel).getPlaceInfo(item.getFloorId(), item.getRoomId()));
                    helper.setGone(R.id.sync_tv, FtDeviceGroup.this.activity.syncTotalNum > 0 && !item.isVirtual());
                    helper.setText(R.id.tv_device_name, item.getName());
                    helper.setGone(R.id.tv_virtual, item.isVirtual());
                    if (FtDeviceGroup.this.activity.selectRoleIds.contains(Long.valueOf(item.getObjectId()))) {
                        helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_sel);
                    } else {
                        helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_default);
                    }
                    FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem = FtDeviceGroup.this.inOrOutGroupItemMap.get(Long.valueOf(item.getId()));
                    if (inOrOutGroupItem != null) {
                        if (inOrOutGroupItem.state == FtDeviceGroupVM.StateParam.STATE_WORKING || inOrOutGroupItem.state == FtDeviceGroupVM.StateParam.STATE_COMPLETED) {
                            helper.setVisible(R.id.iv_upgrade_waiting, true);
                            helper.setVisible(R.id.iv_select, false);
                            ((CircularProgressView) helper.getView(R.id.iv_upgrade_waiting)).setProgress(inOrOutGroupItem.progress);
                        } else {
                            helper.setVisible(R.id.iv_select, true);
                            helper.setVisible(R.id.iv_upgrade_waiting, false);
                        }
                    } else {
                        helper.setVisible(R.id.iv_select, true);
                        helper.setVisible(R.id.iv_upgrade_waiting, false);
                    }
                    helper.getView(R.id.sync_tv).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroup$1$3$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            FtDeviceGroup.AnonymousClass1.AnonymousClass3.this.lambda$convert$0(item, view);
                        }
                    });
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$convert$0(Role role, View view) {
                    FtDeviceGroup.this.activity.syncLocalScene((Device) role);
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void onClick(BaseViewHolder helper, Role data, int position) {
                    Role role = getData().get(position);
                    if (FtDeviceGroup.this.activity.selectRoleIds.contains(Long.valueOf(role.getObjectId()))) {
                        FtDeviceGroup.this.activity.selectRoleIds.remove(Long.valueOf(role.getObjectId()));
                        helper.getView(R.id.iv_select).setBackgroundResource(R.mipmap.ic_tick_default);
                    } else if (FtDeviceGroup.this.selMax > 0 && FtDeviceGroup.this.activity.selectRoleIds.size() >= FtDeviceGroup.this.selMax) {
                        SmartToast.showCenterShort(String.format(FtDeviceGroup.this.getString(R.string.app_str_gqpro_syning_num_tip), Integer.valueOf(FtDeviceGroup.this.selMax), FtDeviceGroup.this.getString(R.string.device)));
                        return;
                    } else {
                        FtDeviceGroup.this.activity.selectRoleIds.add(Long.valueOf(role.getObjectId()));
                        helper.getView(R.id.iv_select).findViewById(R.id.iv_select).setBackgroundResource(R.mipmap.ic_tick_sel);
                    }
                    FtDeviceGroup.this.activity.selectCountLiveData.setValue(Integer.valueOf(FtDeviceGroup.this.activity.selectRoleIds.size()));
                }
            }
        };
        this.deviceAdapter = multipleItemRvAdapter;
        multipleItemRvAdapter.finishInitialize();
        this.deviceAdapter.bindToRecyclerView(((FtDeviceAndGroupBinding) this.mViewBinding).rvDevice);
        ((FtDeviceAndGroupBinding) this.mViewBinding).rvDevice.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((FtDeviceAndGroupBinding) this.mViewBinding).rvDevice.setHasFixedSize(true);
        if (this.activity.listType == 7) {
            ((FtDeviceAndGroupBinding) this.mViewBinding).rvDevice.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroup.2
                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(ConvertUtils.dp2px(6.0f), ConvertUtils.dp2px(6.0f), ConvertUtils.dp2px(6.0f), ConvertUtils.dp2px(6.0f));
                }
            });
            ((FtDeviceAndGroupBinding) this.mViewBinding).rvDevice.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            ((DefaultItemAnimator) ((FtDeviceAndGroupBinding) this.mViewBinding).rvDevice.getItemAnimator()).setSupportsChangeAnimations(false);
        }
    }

    private void initRoleDraggableAdapter() {
        BaseItemDraggableAdapter<Role, BaseViewHolder> baseItemDraggableAdapter = new BaseItemDraggableAdapter<Role, BaseViewHolder>(this, R.layout.item_device_light, this.roleList) { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroup.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Role item) {
                helper.setText(R.id.appCompatTextView15, item.getName()).setImageResource(R.id.appCompatImageView9, ProductRepository.getProductIcon(item)).setGone(R.id.tv_virtual, item.isVirtual()).setBackgroundRes(R.id.iv_device_more, R.mipmap.ic_item_sort).setGone(R.id.sb, false).setGone(R.id.appCompatTextView16, false);
            }
        };
        this.deviceDraggableAdapter = baseItemDraggableAdapter;
        baseItemDraggableAdapter.bindToRecyclerView(((FtDeviceAndGroupBinding) this.mViewBinding).rvDevice);
        ((FtDeviceAndGroupBinding) this.mViewBinding).rvDevice.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroup.4
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(5.0f), 0, ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(10.0f));
            }
        });
        ((FtDeviceAndGroupBinding) this.mViewBinding).rvDevice.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ((FtDeviceAndGroupBinding) this.mViewBinding).rvDevice.setHasFixedSize(true);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemDragAndSwipeCallback(this.deviceDraggableAdapter));
        itemTouchHelper.attachToRecyclerView(((FtDeviceAndGroupBinding) this.mViewBinding).rvDevice);
        this.deviceDraggableAdapter.enableDragItem(itemTouchHelper);
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        this.deviceDataEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroup$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDeviceGroup.this.lambda$startObserve$0((List) obj);
            }
        });
        this.refreshList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroup$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDeviceGroup.this.lambda$startObserve$1((Void) obj);
            }
        });
        this.sortType.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroup$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDeviceGroup.this.lambda$startObserve$2((Integer) obj);
            }
        });
        this.sortSceneType.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroup$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDeviceGroup.this.lambda$startObserve$3((Integer) obj);
            }
        });
        ((FtDeviceGroupVM) this.mViewModel).refreshRoleItem.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroup$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDeviceGroup.this.lambda$startObserve$4((Void) obj);
            }
        });
        ((FtDeviceGroupVM) this.mViewModel).roomPickHelper.startObserve(this, this.placeId, -1L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(List list) {
        this.roleList.clear();
        this.roleList.addAll(list);
        if (list.size() > 0) {
            showContent();
            sortList(this.activity.selectSortType);
        } else {
            showEmpty();
            replaceData();
        }
        if (this.activity.selectFt == this) {
            this.activity.allRoleData.clear();
            this.activity.allRoleData.addAll(this.roleList);
            this.activity.initData();
            this.activity.selectCountLiveData.setValue(Integer.valueOf(this.activity.isMatter() ? this.activity.matterRealSelectedNum : this.activity.selectRoleIds.size()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r1) {
        this.deviceAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Integer num) {
        this.activity.selectSortType = num.intValue();
        sortList(num.intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Integer num) {
        this.activity.selectSortSceneType = num.intValue();
        sortList(num.intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r1) {
        getData();
        this.deviceAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSceneItemByType(BaseViewHolder helper, Role item) {
        if (item.getSceneType() == 1) {
            helper.setBackgroundRes(R.id.tv_type, R.drawable.shape_scene_cloud_bg).setText(R.id.tv_type, R.string.type_cloud).setTextColor(R.id.tv_type, getResources().getColor(R.color.color_text_blue));
        } else if (item.getSceneType() == 2) {
            helper.setBackgroundRes(R.id.tv_type, R.drawable.shape_scene_local_bg).setText(R.id.tv_type, R.string.type_local).setTextColor(R.id.tv_type, getResources().getColor(R.color.color_text_red));
        } else {
            helper.setBackgroundRes(R.id.tv_type, R.drawable.shape_scene_dali_bg).setText(R.id.tv_type, R.string.type_dali).setTextColor(R.id.tv_type, getResources().getColor(R.color.color_cgd_add));
        }
    }

    private void replaceData() {
        if (this.activity.listType == 6) {
            this.deviceDraggableAdapter.replaceData(this.roleList);
        } else {
            this.deviceAdapter.replaceData(this.roleList);
        }
    }

    public void changeProgress(FtDeviceGroupVM.StateParam stateParam) {
        FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem = this.inOrOutGroupItemMap.get(Long.valueOf(stateParam.id));
        if (inOrOutGroupItem != null) {
            inOrOutGroupItem.progress = stateParam.progress;
            inOrOutGroupItem.state = stateParam.state;
            if (stateParam.state == FtDeviceGroupVM.StateParam.STATE_ALL_COMPLETED) {
                Iterator<FtDeviceGroupVM.InOrOutGroupItem> it = this.inOrOutGroupItemMap.values().iterator();
                while (it.hasNext()) {
                    it.next().state = FtDeviceGroupVM.StateParam.STATE_PENDING;
                }
            }
            this.deviceAdapter.notifyDataSetChanged();
        }
    }

    public void getData(String keyword) {
        loadData(this.roomId, this.floorId, keyword);
    }

    public void getData() {
        loadData(this.roomId, this.floorId, "");
    }

    public MultipleItemRvAdapter<Role, BaseViewHolder> getAdapter() {
        return this.deviceAdapter;
    }

    private void loadData(long roomId, long floorId, String keyword) {
        if (ActivityUtils.getTopActivity() instanceof BaseRoomDeviceGroupActivity) {
            this.activity = (BaseRoomDeviceGroupActivity) ActivityUtils.getTopActivity();
            ArrayList arrayList = new ArrayList();
            List<Device> deviceListByRoomIdFromDb = Injection.repo().device().getDeviceListByRoomIdFromDb(this.placeId, floorId, roomId);
            List<Group> groupListByRoomIdFromDb = Injection.repo().group().getGroupListByRoomIdFromDb(this.placeId, floorId, roomId);
            List<Scene> arrayList2 = new ArrayList<>();
            if (this.activity.includeScene) {
                arrayList2 = Injection.repo().scene().getSceneListByRoomId(this.placeId, floorId, roomId, new boolean[0]);
            }
            for (Device device : deviceListByRoomIdFromDb) {
                if (this.activity.filterDevice(device) && this.activity.checkDeviceType(device) && this.activity.filterDaliSubDeviceGroup(device)) {
                    if (keyword.length() > 0) {
                        if (device.getName().toLowerCase().contains(keyword.toLowerCase())) {
                            arrayList.add(device);
                        }
                    } else {
                        arrayList.add(device);
                    }
                }
            }
            for (Group group : groupListByRoomIdFromDb) {
                if (this.activity.filterGroup(group) && this.activity.checkGroupType(group) && this.activity.filterDaliSubDeviceGroup(group)) {
                    if (keyword.length() > 0) {
                        if (group.getName().toLowerCase().contains(keyword.toLowerCase())) {
                            arrayList.add(group);
                        }
                    } else {
                        arrayList.add(group);
                    }
                }
            }
            for (Scene scene : arrayList2) {
                if (this.activity.filterScene(scene) && this.activity.checkSceneType(scene)) {
                    if (keyword.length() > 0) {
                        if (scene.getName().toLowerCase().contains(keyword.toLowerCase())) {
                            arrayList.add(scene);
                        }
                    } else {
                        arrayList.add(scene);
                    }
                }
            }
            this.deviceDataEvent.setValue(arrayList);
        }
    }

    private void sortList(final int type) {
        if (this.roleList.isEmpty()) {
            return;
        }
        if (this.activity.listType == 6) {
            Collections.sort(this.roleList, new Comparator() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroup$$ExternalSyntheticLambda9
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return FtDeviceGroup.lambda$sortList$5((Role) obj, (Role) obj2);
                }
            });
        } else if (this.activity.listType == 7) {
            if (type == 0 || type == 1) {
                Collections.sort(this.roleList, new Comparator() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroup$$ExternalSyntheticLambda10
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return FtDeviceGroup.lambda$sortList$6(type, (Role) obj, (Role) obj2);
                    }
                });
            } else {
                Collections.sort(this.roleList, new Comparator() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroup$$ExternalSyntheticLambda11
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return FtDeviceGroup.lambda$sortList$7(type, (Role) obj, (Role) obj2);
                    }
                });
            }
        } else if (type == 0) {
            Collections.sort(this.roleList, new Comparator() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroup$$ExternalSyntheticLambda12
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return FtDeviceGroup.lambda$sortList$8((Role) obj, (Role) obj2);
                }
            });
        } else if (type == 1 || type == 2) {
            Collections.sort(this.roleList, new Comparator() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroup$$ExternalSyntheticLambda1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return FtDeviceGroup.lambda$sortList$9(type, (Role) obj, (Role) obj2);
                }
            });
        } else if (type == 3 || type == 4) {
            Collections.sort(this.roleList, new Comparator() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroup$$ExternalSyntheticLambda2
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return FtDeviceGroup.lambda$sortList$10(type, (Role) obj, (Role) obj2);
                }
            });
        } else if (type == 11 || type == 12) {
            Collections.sort(this.roleList, new Comparator() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroup$$ExternalSyntheticLambda3
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return FtDeviceGroup.lambda$sortList$11(type, (Role) obj, (Role) obj2);
                }
            });
        } else if (type == 5 && !this.activity.multiSortType.isEmpty()) {
            multiSortList();
        }
        replaceData();
    }

    static /* synthetic */ int lambda$sortList$5(Role role, Role role2) {
        if (role.getIndex() > role2.getIndex()) {
            return 1;
        }
        if (role.getIndex() < role2.getIndex()) {
            return -1;
        }
        if (role.getCreatetime() == null || role2.getCreatetime() == null) {
            return 0;
        }
        int compareTo = role.getCreatetime().compareTo(role2.getCreatetime());
        if (compareTo != 0) {
            return compareTo;
        }
        if (role instanceof Device) {
            Device device = (Device) role;
            if (role2 instanceof Device) {
                Device device2 = (Device) role2;
                if (device.getWifiMac() == null || device2.getWifiMac() == null) {
                    return 0;
                }
                return device.getWifiMac().compareTo(device2.getWifiMac());
            }
        }
        if (!(role instanceof Group)) {
            return 0;
        }
        Group group = (Group) role;
        if (role2 instanceof Group) {
            return group.getSubkey() - ((Group) role2).getSubkey();
        }
        return 0;
    }

    static /* synthetic */ int lambda$sortList$6(int i, Role role, Role role2) {
        if (role.getCreatetime() == null || role2.getCreatetime() == null) {
            return 0;
        }
        if (i == 0) {
            return role.getCreatetime().compareTo(role2.getCreatetime());
        }
        return role2.getCreatetime().compareTo(role.getCreatetime());
    }

    static /* synthetic */ int lambda$sortList$7(int i, Role role, Role role2) {
        Collator collator = Collator.getInstance(Locale.CHINA);
        String name = role.getName();
        String name2 = role2.getName();
        if (name == null || name2 == null) {
            return 0;
        }
        if (i == 2) {
            return collator.compare(name, name2);
        }
        return collator.compare(name2, name);
    }

    static /* synthetic */ int lambda$sortList$8(Role role, Role role2) {
        int lightColorType = role instanceof Group ? ProductRepository.getLightColorType(role) : 1000;
        int lightColorType2 = role2 instanceof Group ? ProductRepository.getLightColorType(role2) : 1000;
        return (lightColorType != lightColorType2 || role.getCreatetime() == null || role2.getCreatetime() == null) ? lightColorType - lightColorType2 : role.getCreatetime().compareTo(role2.getCreatetime());
    }

    static /* synthetic */ int lambda$sortList$9(int i, Role role, Role role2) {
        if (role.getCreatetime() == null || role2.getCreatetime() == null) {
            return 0;
        }
        if (i == 1) {
            return role.getCreatetime().compareTo(role2.getCreatetime());
        }
        return role2.getCreatetime().compareTo(role.getCreatetime());
    }

    static /* synthetic */ int lambda$sortList$10(int i, Role role, Role role2) {
        Collator collator = Collator.getInstance(Locale.CHINA);
        String name = role.getName();
        String name2 = role2.getName();
        if (name == null || name2 == null) {
            return 0;
        }
        if (i == 3) {
            return collator.compare(name, name2);
        }
        return collator.compare(name2, name);
    }

    static /* synthetic */ int lambda$sortList$11(int i, Role role, Role role2) {
        int daliDeviceType;
        int daliDeviceType2;
        if (i == 11) {
            daliDeviceType = DaliProHelper.getDaliAddress(role);
            daliDeviceType2 = DaliProHelper.getDaliAddress(role2);
        } else {
            daliDeviceType = DaliProHelper.getDaliDeviceType((Device) role);
            daliDeviceType2 = DaliProHelper.getDaliDeviceType((Device) role2);
        }
        return daliDeviceType - daliDeviceType2;
    }

    private void multiSortList() {
        if (this.roleList.isEmpty()) {
            return;
        }
        final ArrayList arrayList = new ArrayList(this.activity.multiSortType);
        final boolean contains = arrayList.contains(0);
        if (contains) {
            arrayList.remove((Object) 0);
        }
        Collections.sort(this.roleList, new Comparator() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroup$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return FtDeviceGroup.lambda$multiSortList$12(contains, arrayList, (Role) obj, (Role) obj2);
            }
        });
        replaceData();
    }

    static /* synthetic */ int lambda$multiSortList$12(boolean z, List list, Role role, Role role2) {
        int i;
        int i2;
        int compareTo;
        int compareTo2;
        if (z) {
            i2 = 1000;
            i = role instanceof Group ? ProductRepository.getLightColorType(role) : 1000;
            if (role2 instanceof Group) {
                i2 = ProductRepository.getLightColorType(role2);
            }
        } else {
            i = 0;
            i2 = 0;
        }
        if (i == i2) {
            if (((Integer) list.get(0)).intValue() == 1 || ((Integer) list.get(0)).intValue() == 2) {
                if (((Integer) list.get(0)).intValue() == 1) {
                    compareTo = role.getCreatetime().compareTo(role2.getCreatetime());
                } else {
                    compareTo = role2.getCreatetime().compareTo(role.getCreatetime());
                }
                if (compareTo != 0 || list.size() <= 1) {
                    return compareTo;
                }
                if (((Integer) list.get(1)).intValue() == 3) {
                    return role.getName().compareTo(role2.getName());
                }
                return role2.getName().compareTo(role.getName());
            }
            if (((Integer) list.get(0)).intValue() == 3 || ((Integer) list.get(0)).intValue() == 4) {
                if (((Integer) list.get(0)).intValue() == 3) {
                    compareTo2 = role.getName().compareTo(role2.getName());
                } else {
                    compareTo2 = role2.getName().compareTo(role.getName());
                }
                if (compareTo2 != 0 || list.size() <= 1) {
                    return compareTo2;
                }
                if (((Integer) list.get(1)).intValue() == 1) {
                    return role.getCreatetime().compareTo(role2.getCreatetime());
                }
                return role2.getCreatetime().compareTo(role.getCreatetime());
            }
        }
        return i - i2;
    }
}