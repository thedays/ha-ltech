package com.ltech.smarthome.ui.device.super_panel;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.databinding.FtSubDeviceBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class FtDeviceAndScene extends BaseNormalFragment<FtSubDeviceBinding> {
    public static final int TYPE_DEVICE_AND_GROUP = 1;
    public static final int TYPE_SCENE = 2;
    private ActSuperPanelVoiceControlRangeRole activity;
    private MultipleItemRvAdapter<Role, BaseViewHolder> deviceAdapter;
    private int listType;
    private long placeId;
    public List<Role> roleList = new ArrayList();
    public List<Room> roomList = new ArrayList();
    public List<Floor> floorList = new ArrayList();
    private MutableLiveData<List<Role>> roleDataEvent = new MutableLiveData<>();
    private int emptyType = 1;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_sub_device;
    }

    public static FtDeviceAndScene newInstance(int listType, long placeId) {
        FtDeviceAndScene ftDeviceAndScene = new FtDeviceAndScene();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.LIST_TYPE, listType);
        bundle.putLong(Constants.PLACE_ID, placeId);
        ftDeviceAndScene.setArguments(bundle);
        return ftDeviceAndScene;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.listType = arguments.getInt(Constants.LIST_TYPE);
            this.placeId = arguments.getLong(Constants.PLACE_ID);
        }
        this.floorList = Injection.repo().home().getFloorListByPlaceId(this.placeId);
        this.roomList = Injection.repo().home().getRoomList(this.placeId);
        this.activity = (ActSuperPanelVoiceControlRangeRole) ActivityUtils.getTopActivity();
        initRv();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void showEmpty() {
        if (this.emptyType == 2) {
            this.mHolder.setAdapter(new DefaultAdapter().emptyStringRes(R.string.please_add_voice_scene).emptyTryStringRes(R.string.add));
        } else {
            this.mHolder.setAdapter(new DefaultAdapter().emptyStringRes(R.string.please_add_voice_device).emptyTryStringRes(R.string.add));
        }
        super.showEmpty();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.please_add_voice_device).emptyTryStringRes(R.string.add));
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void onEmptyTry() {
        super.onEmptyTry();
        this.activity.nav();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        super.startObserve();
        ((FtSubDeviceBinding) this.mViewBinding).tvCount.setVisibility(8);
        this.roleDataEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.FtDeviceAndScene$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDeviceAndScene.this.lambda$startObserve$0((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(List list) {
        this.roleList.clear();
        this.roleList.addAll(list);
        if (list.size() > 0) {
            showContent();
        } else {
            if (this.listType == 2) {
                this.emptyType = 2;
            } else {
                this.emptyType = 1;
            }
            showEmpty();
        }
        this.deviceAdapter.replaceData(this.roleList);
    }

    private void initRv() {
        MultipleItemRvAdapter<Role, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<Role, BaseViewHolder>(this.roleList) { // from class: com.ltech.smarthome.ui.device.super_panel.FtDeviceAndScene.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(Role role) {
                return FtDeviceAndScene.this.listType;
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new BaseItemProvider<Role, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.super_panel.FtDeviceAndScene.1.1
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_device_manage;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void onClick(BaseViewHolder helper, Role data, int position) {
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 1;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, Role item, int position) {
                        helper.setText(R.id.tv_device_name, item.getName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item)).setVisible(R.id.iv_go, false).setText(R.id.tv_place_info, FtDeviceAndScene.this.getPlaceInfo(item.getFloorId(), item.getRoomId()));
                        helper.setVisible(R.id.tv_dali, ProductRepository.isDaliLightGroup(item));
                        ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<Role, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.super_panel.FtDeviceAndScene.1.2
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_scene_new;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void onClick(BaseViewHolder helper, Role data, int position) {
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 2;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, Role item, int position) {
                        helper.setText(R.id.tv_name, item.getName()).setImageResource(R.id.appCompatImageView14, SceneHelper.getSceneIcon(ActivityUtils.getTopActivity(), item.getIconPos())).setGone(R.id.iv_edit, false).setGone(R.id.iv_select, false);
                        FtDeviceAndScene.this.showSceneItemByType(helper, item);
                    }
                });
            }
        };
        this.deviceAdapter = multipleItemRvAdapter;
        multipleItemRvAdapter.finishInitialize();
        this.deviceAdapter.bindToRecyclerView(((FtSubDeviceBinding) this.mViewBinding).rvDevice);
        ((FtSubDeviceBinding) this.mViewBinding).rvDevice.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((FtSubDeviceBinding) this.mViewBinding).rvDevice.setHasFixedSize(true);
        if (this.listType == 2) {
            ((FtSubDeviceBinding) this.mViewBinding).rvDevice.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.super_panel.FtDeviceAndScene.2
                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(ConvertUtils.dp2px(6.0f), ConvertUtils.dp2px(6.0f), ConvertUtils.dp2px(6.0f), ConvertUtils.dp2px(6.0f));
                }
            });
            ((FtSubDeviceBinding) this.mViewBinding).rvDevice.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            ((DefaultItemAnimator) ((FtSubDeviceBinding) this.mViewBinding).rvDevice.getItemAnimator()).setSupportsChangeAnimations(false);
        }
    }

    public void replaceSceneData(List<Long> roleIdList) {
        ArrayList arrayList = new ArrayList();
        Iterator<Long> it = roleIdList.iterator();
        while (it.hasNext()) {
            arrayList.add(Injection.repo().scene().getSceneBySceneId(it.next().longValue()));
        }
        this.roleDataEvent.setValue(arrayList);
    }

    public void replaceDeviceAndGroupData(List<Long> roleIdList) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        Iterator<Long> it = roleIdList.iterator();
        while (it.hasNext()) {
            Role roleByRoleId = getRoleByRoleId(it.next().longValue());
            if (roleByRoleId != null) {
                if (roleByRoleId instanceof Device) {
                    arrayList3.add((Device) roleByRoleId);
                } else if (roleByRoleId instanceof Group) {
                    arrayList2.add((Group) roleByRoleId);
                }
            }
        }
        arrayList.addAll(arrayList2);
        arrayList.addAll(arrayList3);
        this.roleDataEvent.setValue(arrayList);
    }

    private Role getRoleByRoleId(long roleId) {
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(roleId);
        return deviceByDeviceId != null ? deviceByDeviceId : Injection.repo().group().getGroupByGroupId(roleId);
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

    public String getPlaceInfo(long floorId, long roomId) {
        StringBuilder sb = new StringBuilder();
        List<Floor> list = this.floorList;
        if (list != null) {
            Iterator<Floor> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Floor next = it.next();
                if (floorId == next.getFloorId()) {
                    sb.append(next.getFloorName());
                    break;
                }
            }
        }
        List<Room> list2 = this.roomList;
        if (list2 != null) {
            Iterator<Room> it2 = list2.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Room next2 = it2.next();
                if (roomId == next2.getRoomId()) {
                    sb.append(next2.getRoomName());
                    break;
                }
            }
        }
        return sb.toString();
    }
}