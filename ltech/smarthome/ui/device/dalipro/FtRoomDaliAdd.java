package com.ltech.smarthome.ui.device.dalipro;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.FtRoomDaliAddBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.model.scene_param.LocalDeviceParam;
import com.ltech.smarthome.ui.device.dalipro.CgdActionSelectView;
import com.ltech.smarthome.ui.device.dalipro.FtDaliAddVM;
import com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.ltech.smarthome.view.dialog.CenterSelectListDialog;
import com.ltech.smarthome.view.dialog.CenterTipDialog;
import com.ltech.smarthome.view.dialog.DaliDetectDialog;
import com.ltech.smarthome.view.dialog.DaliDetectTipDialog;
import com.ltech.smarthome.view.dialog.LightQuickDialog;
import com.ltech.smarthome.view.dialog.SelectCgdActionDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.MessageManager;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.param.LightCmdParam;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes4.dex */
public class FtRoomDaliAdd extends BaseNormalFragment<FtRoomDaliAddBinding> {
    public static final int TYPE_ADD_LIST = 1;
    public static final int TYPE_EDIT_DALI_SCENE = 2;
    public static final int TYPE_SELECT_DALI_ADD = 3;
    private int allDeviceCount;
    private DaliDetectDialog daliDetectDialog;
    private MultipleItemRvAdapter<Role, BaseViewHolder> deviceAdapter;
    private long floorId;
    private MultipleItemRvAdapter<Role, BaseViewHolder> groupAdapter;
    private FtDaliAddVM mViewModel;
    private long macDeviceId;
    private boolean needScroll;
    private long placeId;
    private long roomId;
    private MultipleItemRvAdapter<Role, BaseViewHolder> sceneAdapter;
    public List<Group> groupList = new ArrayList();
    public List<Device> deviceList = new ArrayList();
    public SingleLiveEvent<Void> refreshList = new SingleLiveEvent<>();

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_room_dali_add;
    }

    public static FtRoomDaliAdd newInstance(long placeId, long roomId, long floorId, long deviceId) {
        FtRoomDaliAdd ftRoomDaliAdd = new FtRoomDaliAdd();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.PLACE_ID, placeId);
        bundle.putLong(Constants.ROOM_ID, roomId);
        bundle.putLong(Constants.FLOOR_ID, floorId);
        bundle.putLong("device_id", deviceId);
        ftRoomDaliAdd.setArguments(bundle);
        return ftRoomDaliAdd;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initData() {
        super.initData();
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.placeId = arguments.getLong(Constants.PLACE_ID);
            this.floorId = arguments.getLong(Constants.FLOOR_ID);
            this.roomId = arguments.getLong(Constants.ROOM_ID);
            this.macDeviceId = arguments.getLong("device_id");
        }
        this.mViewModel = (FtDaliAddVM) new ViewModelProvider(FtDaliAdd.getObj()).get(FtDaliAddVM.class);
        getData();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        if (this.mViewModel.listType == 2) {
            ((FtRoomDaliAddBinding) this.mViewBinding).tvGroupTip.setText(R.string.dali_edit_group_tip);
            ((FtRoomDaliAddBinding) this.mViewBinding).tvLightTip.setText(R.string.dali_edit_light_tip);
        } else if (this.mViewModel.listType == 3) {
            ((FtRoomDaliAddBinding) this.mViewBinding).tvGroupTip.setVisibility(8);
            ((FtRoomDaliAddBinding) this.mViewBinding).layoutLight.setVisibility(8);
            if (this.mViewModel.showType == 1) {
                ((FtRoomDaliAddBinding) this.mViewBinding).rvLight.setVisibility(8);
                showGroup();
                return;
            } else if (this.mViewModel.showType == 2) {
                ((FtRoomDaliAddBinding) this.mViewBinding).rvGroup.setVisibility(8);
                showLight();
                return;
            }
        }
        showScene();
        showGroup();
        showLight();
    }

    private void showScene() {
        MultipleItemRvAdapter<Role, BaseViewHolder> adapter = getAdapter();
        this.sceneAdapter = adapter;
        adapter.finishInitialize();
        this.sceneAdapter.bindToRecyclerView(((FtRoomDaliAddBinding) this.mViewBinding).rvScene);
        ((FtRoomDaliAddBinding) this.mViewBinding).rvScene.addItemDecoration(this.mViewModel.decoration);
        ((FtRoomDaliAddBinding) this.mViewBinding).rvScene.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        ((DefaultItemAnimator) ((FtRoomDaliAddBinding) this.mViewBinding).rvScene.getItemAnimator()).setSupportsChangeAnimations(false);
        ((FtRoomDaliAddBinding) this.mViewBinding).rvScene.setHasFixedSize(true);
        ArrayList arrayList = new ArrayList();
        Iterator<Long> it = this.mViewModel.actionMap.keySet().iterator();
        while (it.hasNext()) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(it.next().longValue());
            if (deviceByDeviceId != null && deviceByDeviceId.getFloorId() == this.floorId && deviceByDeviceId.getRoomId() == this.roomId) {
                arrayList.add(deviceByDeviceId);
            }
        }
        this.sceneAdapter.replaceData(arrayList);
        ((FtRoomDaliAddBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                FtRoomDaliAdd.this.lambda$showScene$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showScene$0(View view) {
        if (((FtRoomDaliAddBinding) this.mViewBinding).rvScene.getVisibility() == 0) {
            ((FtRoomDaliAddBinding) this.mViewBinding).rvScene.setVisibility(8);
            ((FtRoomDaliAddBinding) this.mViewBinding).tvShow.setText(R.string.str_show);
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_up_gray);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            ((FtRoomDaliAddBinding) this.mViewBinding).tvShow.setCompoundDrawables(null, null, drawable, null);
            return;
        }
        ((FtRoomDaliAddBinding) this.mViewBinding).rvScene.setVisibility(0);
        ((FtRoomDaliAddBinding) this.mViewBinding).tvShow.setText(R.string.str_hide);
        Drawable drawable2 = getResources().getDrawable(R.mipmap.ic_down_gray);
        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
        ((FtRoomDaliAddBinding) this.mViewBinding).tvShow.setCompoundDrawables(null, null, drawable2, null);
    }

    private void showGroup() {
        MultipleItemRvAdapter<Role, BaseViewHolder> adapter = getAdapter();
        this.groupAdapter = adapter;
        adapter.finishInitialize();
        this.groupAdapter.bindToRecyclerView(((FtRoomDaliAddBinding) this.mViewBinding).rvGroup);
        ((FtRoomDaliAddBinding) this.mViewBinding).rvGroup.addItemDecoration(this.mViewModel.decoration);
        ((FtRoomDaliAddBinding) this.mViewBinding).rvGroup.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        ((DefaultItemAnimator) ((FtRoomDaliAddBinding) this.mViewBinding).rvGroup.getItemAnimator()).setSupportsChangeAnimations(false);
        ((FtRoomDaliAddBinding) this.mViewBinding).rvGroup.setHasFixedSize(true);
    }

    private void showLight() {
        MultipleItemRvAdapter<Role, BaseViewHolder> adapter = getAdapter();
        this.deviceAdapter = adapter;
        adapter.finishInitialize();
        this.deviceAdapter.bindToRecyclerView(((FtRoomDaliAddBinding) this.mViewBinding).rvLight);
        ((FtRoomDaliAddBinding) this.mViewBinding).rvLight.addItemDecoration(this.mViewModel.decoration);
        ((FtRoomDaliAddBinding) this.mViewBinding).rvLight.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        ((DefaultItemAnimator) ((FtRoomDaliAddBinding) this.mViewBinding).rvLight.getItemAnimator()).setSupportsChangeAnimations(false);
        ((FtRoomDaliAddBinding) this.mViewBinding).rvLight.setHasFixedSize(true);
        ((FtRoomDaliAddBinding) this.mViewBinding).rvLight.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (FtRoomDaliAdd.this.needScroll && newState == 0) {
                    FtRoomDaliAdd.this.needScroll = false;
                    FtRoomDaliAdd ftRoomDaliAdd = FtRoomDaliAdd.this;
                    ftRoomDaliAdd.smoothMoveToPosition(((FtRoomDaliAddBinding) ftRoomDaliAdd.mViewBinding).rvLight, 40);
                }
            }
        });
        ((FtRoomDaliAddBinding) this.mViewBinding).radioPosition.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd$$ExternalSyntheticLambda1
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public final void onCheckedChanged(RadioGroup radioGroup, int i) {
                FtRoomDaliAdd.this.lambda$showLight$1(radioGroup, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showLight$1(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.radio_0 /* 2131297917 */:
                smoothMoveToPosition(((FtRoomDaliAddBinding) this.mViewBinding).rvLight, 0);
                break;
            case R.id.radio_20 /* 2131297919 */:
                smoothMoveToPosition(((FtRoomDaliAddBinding) this.mViewBinding).rvLight, 20);
                break;
            case R.id.radio_40 /* 2131297920 */:
                smoothMoveToPosition(((FtRoomDaliAddBinding) this.mViewBinding).rvLight, 40);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        int childLayoutPosition = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        int childLayoutPosition2 = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < childLayoutPosition) {
            mRecyclerView.smoothScrollToPosition(position);
            return;
        }
        if (position <= childLayoutPosition2) {
            int i = position - childLayoutPosition;
            if (i < 0 || i >= mRecyclerView.getChildCount()) {
                return;
            }
            mRecyclerView.smoothScrollBy(0, mRecyclerView.getChildAt(i).getTop());
            return;
        }
        mRecyclerView.smoothScrollToPosition(position);
        this.needScroll = true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        this.refreshList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd$$ExternalSyntheticLambda9
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtRoomDaliAdd.this.lambda$startObserve$2((Void) obj);
            }
        });
        this.mViewModel.freshDeviceData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd$$ExternalSyntheticLambda10
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtRoomDaliAdd.this.lambda$startObserve$3((Role) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r7) {
        if (this.deviceList.size() == 0 && this.groupList.size() == 0) {
            showEmpty();
            return;
        }
        showContent();
        this.mViewModel.allRoleData.clear();
        sortList();
        if (this.deviceAdapter != null) {
            this.mViewModel.allRoleData.addAll(this.deviceList);
            LinkedHashMap linkedHashMap = new LinkedHashMap(this.mViewModel.actionMap);
            this.mViewModel.actionMap.clear();
            for (Device device : this.deviceList) {
                if (!linkedHashMap.containsKey(Long.valueOf(device.getDeviceId()))) {
                    createLightCloseParam(device);
                } else {
                    this.mViewModel.actionMap.put(Long.valueOf(device.getDeviceId()), (LocalDeviceParam) linkedHashMap.get(Long.valueOf(device.getDeviceId())));
                }
            }
            this.deviceAdapter.replaceData(this.deviceList);
            if (this.groupAdapter == null) {
                check();
            }
        }
        if (this.groupAdapter != null) {
            this.mViewModel.allRoleData.addAll(this.groupList);
            this.groupAdapter.replaceData(this.groupList);
            check();
        }
        boolean z = false;
        if (this.mViewModel.listType != 3) {
            ((FtRoomDaliAddBinding) this.mViewBinding).layoutLight.setVisibility(this.deviceList.size() == 0 ? 8 : 0);
            ((FtRoomDaliAddBinding) this.mViewBinding).tvGroupTip.setVisibility(this.groupList.size() != 0 ? 0 : 8);
        }
        if (this.mViewModel.listType == 2) {
            Iterator<Device> it = this.deviceList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (this.mViewModel.selectDeviceIds.contains(Long.valueOf(it.next().getDeviceId()))) {
                    z = true;
                    break;
                }
            }
        }
        ((FtRoomDaliAddBinding) this.mViewBinding).setSceneEdit(Boolean.valueOf(z));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Role role) {
        int i = 0;
        if (role instanceof Device) {
            while (i < this.deviceAdapter.getItemCount()) {
                Role item = this.deviceAdapter.getItem(i);
                if (item != null && item.getObjectId() == role.getObjectId()) {
                    item.setDeviceState(role.getDeviceState());
                    this.deviceAdapter.notifyItemChanged(i);
                    return;
                }
                i++;
            }
            return;
        }
        while (i < this.groupAdapter.getItemCount()) {
            Role item2 = this.groupAdapter.getItem(i);
            if (item2 != null && item2.getObjectId() == role.getObjectId()) {
                item2.setDeviceState(role.getDeviceState());
                this.groupAdapter.notifyItemChanged(i);
                return;
            }
            i++;
        }
    }

    private void check() {
        if (this.mViewModel.listType == 1) {
            FtDaliAddVM ftDaliAddVM = this.mViewModel;
            ftDaliAddVM.checkDeviceStatus(ftDaliAddVM.allRoleData);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected Gloading createGLoading() {
        if (Injection.repo().device().getSubDeviceListFromDb(this.placeId, -1L, -1L, FtDaliAdd.getObj().deviceId).size() > 0) {
            return Gloading.from(new DefaultAdapter().emptyImageRes(R.mipmap.pic_empty_1).emptyStringRes(R.string.no_device));
        }
        return Gloading.from(new DefaultAdapter().emptyImageRes(R.mipmap.pic_empty_1).emptyTryStringRes(R.string.app_str_search_address).emptyStringRes(R.string.dali_no_device));
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void onEmptyTry() {
        showSearchAddressDialog();
    }

    private void showSearchAddressDialog() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.reserve_device_data));
        arrayList.add(getString(R.string.reset_device_data));
        CenterSelectListDialog.asDefault(true).setConfirmString(getString(R.string.app_str_start_search)).setTitle(getString(R.string.app_str_search_address)).setCancelString(getString(R.string.cancel)).setSelectList(arrayList).setSelectPosition(0).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtRoomDaliAdd.this.lambda$showSearchAddressDialog$5((Integer) obj);
            }
        }).showDialog(getActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSearchAddressDialog$5(Integer num) {
        if (num.intValue() == 0) {
            getDaliData(false);
        } else {
            CenterTipDialog.asDefault().setTitle(getString(R.string.sure_reset_device_data)).setMessageString(getString(R.string.reset_device_data_message)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.confirm)).setOnConfirmListener(new CenterTipDialog.OnConfirmListener() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd$$ExternalSyntheticLambda7
                @Override // com.ltech.smarthome.view.dialog.CenterTipDialog.OnConfirmListener
                public final void onConfirm() {
                    FtRoomDaliAdd.this.lambda$showSearchAddressDialog$4();
                }
            }).showDialog(getActivity());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSearchAddressDialog$4() {
        getDaliData(true);
    }

    private void getDaliData(boolean isClean) {
        final DaliDetectTipDialog onCreateDetectDialogListener = DaliDetectTipDialog.asDefault().setOnCreateDetectDialogListener(new DaliDetectTipDialog.OnCreateDetectDialogListener() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.dialog.DaliDetectTipDialog.OnCreateDetectDialogListener
            public final void onCreateDetectDialog(DaliDetectDialog daliDetectDialog) {
                FtRoomDaliAdd.this.lambda$getDaliData$6(daliDetectDialog);
            }
        });
        onCreateDetectDialogListener.showDialog(getActivity());
        this.mViewModel.initData(Boolean.valueOf(isClean), new FtDaliAddVM.OnCgpProGetDataListener() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd.2
            @Override // com.ltech.smarthome.ui.device.dalipro.FtDaliAddVM.OnCgpProGetDataListener
            public void onGetFirstFrameData() {
                onCreateDetectDialogListener.toDetectDialog();
            }

            @Override // com.ltech.smarthome.ui.device.dalipro.FtDaliAddVM.OnCgpProGetDataListener
            public void onDismissDialog() {
                onCreateDetectDialogListener.dismissDialog();
                if (FtRoomDaliAdd.this.daliDetectDialog != null) {
                    FtRoomDaliAdd.this.daliDetectDialog.dismissDialog();
                }
            }

            @Override // com.ltech.smarthome.ui.device.dalipro.FtDaliAddVM.OnCgpProGetDataListener
            public void onGetAllData() {
                if (FtRoomDaliAdd.this.daliDetectDialog != null) {
                    FtRoomDaliAdd.this.daliDetectDialog.dismissDialog();
                }
                CenterTipDialog asDefault = CenterTipDialog.asDefault();
                FtRoomDaliAdd ftRoomDaliAdd = FtRoomDaliAdd.this;
                asDefault.setTitle(ftRoomDaliAdd.getString(R.string.success_detect_count, String.valueOf(ftRoomDaliAdd.allDeviceCount))).setCancelString(FtRoomDaliAdd.this.getString(R.string.app_str_guide_know)).showDialog(FtRoomDaliAdd.this.getActivity());
            }

            @Override // com.ltech.smarthome.ui.device.dalipro.FtDaliAddVM.OnCgpProGetDataListener
            public void updateProgress(int progress) {
                FtRoomDaliAdd.this.allDeviceCount = progress;
                onCreateDetectDialogListener.setProgress(progress);
                if (FtRoomDaliAdd.this.daliDetectDialog != null) {
                    FtRoomDaliAdd.this.daliDetectDialog.setProgress(progress);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDaliData$6(DaliDetectDialog daliDetectDialog) {
        this.daliDetectDialog = daliDetectDialog;
    }

    private MultipleItemRvAdapter<Role, BaseViewHolder> getAdapter() {
        return new MultipleItemRvAdapter<Role, BaseViewHolder>(new ArrayList()) { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(Role role) {
                return FtRoomDaliAdd.this.mViewModel.listType;
            }

            /* renamed from: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd$3$1, reason: invalid class name */
            class AnonymousClass1 extends BaseItemProvider<Role, BaseViewHolder> {
                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int layout() {
                    return R.layout.item_cgd;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int viewType() {
                    return 1;
                }

                AnonymousClass1() {
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void convert(BaseViewHolder helper, final Role item, int position) {
                    final boolean z = item instanceof Group;
                    helper.setText(R.id.tv_name, item.getName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item)).setText(R.id.tv_add, String.valueOf(DaliProHelper.getDaliAddress(item)));
                    helper.setVisible(R.id.tv_offline, item.getDeviceState().getOnlineState() == 2);
                    helper.getView(R.id.iv_edit).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd$3$1$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            FtRoomDaliAdd.AnonymousClass3.AnonymousClass1.this.lambda$convert$0(z, item, view);
                        }
                    });
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$convert$0(boolean z, Role role, View view) {
                    if (z) {
                        FtRoomDaliAdd.this.nav(role, DaliProHelper.getDaliGroupType((Group) role), true);
                    } else {
                        FtRoomDaliAdd.this.nav(role, DaliProHelper.getDaliDeviceType((Device) role), false);
                    }
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void onClick(BaseViewHolder helper, Role item, int position) {
                    if (item instanceof Group) {
                        Group group = (Group) item;
                        if (group.getDeviceIds().size() == 0) {
                            FtRoomDaliAdd.this.showEmptyDialog(group);
                            return;
                        } else {
                            FtRoomDaliAdd.this.showQuickDialog(group);
                            return;
                        }
                    }
                    FtRoomDaliAdd.this.showQuickDialog((Device) item);
                }
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new AnonymousClass1());
                this.mProviderDelegate.registerProvider(new BaseItemProvider<Role, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd.3.2
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_cgd_action;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 2;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, Role item, int position) {
                        helper.setText(R.id.tv_name, item.getName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item)).setGone(R.id.tv_action, item instanceof Device).setText(R.id.tv_add, String.valueOf(DaliProHelper.getDaliAddress(item)));
                        if (FtRoomDaliAdd.this.mViewModel.selectDeviceIds.contains(Long.valueOf(item.getObjectId())) && FtRoomDaliAdd.this.mViewModel.actionMap.containsKey(Long.valueOf(item.getObjectId()))) {
                            LocalDeviceParam localDeviceParam = FtRoomDaliAdd.this.mViewModel.actionMap.get(Long.valueOf(item.getObjectId()));
                            String lightAction = DaliProHelper.getLightAction(FtRoomDaliAdd.this.getContext(), localDeviceParam.option, localDeviceParam.instruct, item);
                            helper.setGone(R.id.group_color, lightAction.startsWith(MqttTopic.MULTI_LEVEL_WILDCARD));
                            if (lightAction.startsWith(MqttTopic.MULTI_LEVEL_WILDCARD)) {
                                ((CircleImageView) helper.getView(R.id.civ_color)).setImageDrawable(new ColorDrawable(Color.rgb(Integer.parseInt(lightAction.substring(1, 3), 16), Integer.parseInt(lightAction.substring(3, 5), 16), Integer.parseInt(lightAction.substring(5, 7), 16))));
                                lightAction = lightAction.substring(7);
                            }
                            helper.setText(R.id.tv_action, lightAction);
                        } else {
                            helper.setGone(R.id.group_color, false);
                            helper.setText(R.id.tv_action, R.string.app_str_valid);
                        }
                        if (FtRoomDaliAdd.this.mViewModel.selectDeviceIds.contains(Long.valueOf(item.getObjectId())) || FtRoomDaliAdd.this.mViewModel.selectGroupIds.contains(Long.valueOf(item.getObjectId()))) {
                            helper.itemView.setBackgroundResource(R.drawable.selector_cardview_select_bg);
                            helper.setImageResource(R.id.iv_select, R.mipmap.ic_tick_sel);
                        } else {
                            helper.itemView.setBackgroundResource(R.drawable.selector_cardview_unselect_bg);
                            helper.setImageResource(R.id.iv_select, R.mipmap.ic_tick_default);
                        }
                    }

                    /* JADX WARN: Removed duplicated region for block: B:42:0x014f A[SYNTHETIC] */
                    /* JADX WARN: Removed duplicated region for block: B:46:0x0139 A[SYNTHETIC] */
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public void onClick(com.chad.library.adapter.base.BaseViewHolder r6, com.ltech.smarthome.model.bean.Role r7, int r8) {
                        /*
                            Method dump skipped, instructions count: 458
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd.AnonymousClass3.AnonymousClass2.onClick(com.chad.library.adapter.base.BaseViewHolder, com.ltech.smarthome.model.bean.Role, int):void");
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public boolean onLongClick(BaseViewHolder helper, Role item, int position) {
                        if (FtRoomDaliAdd.this.mViewModel.selectGroupIds.contains(Long.valueOf(item.getObjectId()))) {
                            FtRoomDaliAdd.this.showEditDialog(item);
                            return true;
                        }
                        if (!FtRoomDaliAdd.this.mViewModel.selectDeviceIds.contains(Long.valueOf(item.getObjectId()))) {
                            return true;
                        }
                        FtRoomDaliAdd.this.showEditDialog(item);
                        return true;
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<Role, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd.3.3
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_cgd;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 3;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, Role item, int position) {
                        helper.setText(R.id.tv_name, item.getName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item)).setText(R.id.tv_add, String.valueOf(DaliProHelper.getDaliAddress(item)));
                        if (FtRoomDaliAdd.this.mViewModel.selectDeviceIds.contains(Long.valueOf(item.getObjectId())) || FtRoomDaliAdd.this.mViewModel.selectGroupIds.contains(Long.valueOf(item.getObjectId()))) {
                            helper.setImageResource(R.id.iv_edit, R.mipmap.ic_tick_sel);
                        } else {
                            helper.setImageResource(R.id.iv_edit, R.mipmap.ic_tick_default);
                        }
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void onClick(BaseViewHolder helper, Role item, int position) {
                        if (item instanceof Group) {
                            if (FtRoomDaliAdd.this.mViewModel.selectGroupIds.contains(Long.valueOf(item.getObjectId()))) {
                                FtRoomDaliAdd.this.mViewModel.selectGroupIds.remove(Long.valueOf(item.getObjectId()));
                            } else {
                                FtRoomDaliAdd.this.mViewModel.selectGroupIds.add(Long.valueOf(item.getObjectId()));
                            }
                            FtRoomDaliAdd.this.groupAdapter.notifyDataSetChanged();
                        } else {
                            if (FtRoomDaliAdd.this.mViewModel.selectDeviceIds.contains(Long.valueOf(item.getObjectId()))) {
                                FtRoomDaliAdd.this.mViewModel.selectDeviceIds.remove(Long.valueOf(item.getObjectId()));
                            } else {
                                FtRoomDaliAdd.this.mViewModel.selectDeviceIds.add(Long.valueOf(item.getObjectId()));
                            }
                            FtRoomDaliAdd.this.deviceAdapter.notifyDataSetChanged();
                        }
                        FtRoomDaliAdd.this.mViewModel.selectCountLiveData.setValue(Integer.valueOf(FtRoomDaliAdd.this.mViewModel.selectDeviceIds.size() + FtRoomDaliAdd.this.mViewModel.selectGroupIds.size()));
                    }
                });
            }
        };
    }

    public void getData() {
        loadData(this.roomId, this.floorId, this.macDeviceId);
    }

    private void loadData(long roomId, long floorId, long deviceId) {
        this.deviceList = Injection.repo().device().getSubDeviceListFromDb(this.placeId, floorId, roomId, deviceId);
        this.groupList = Injection.repo().group().getSubGroupListFromDb(this.placeId, floorId, roomId, deviceId);
        if (ActivityUtils.getTopActivity() instanceof ActEditDaliScene) {
            Iterator<Group> it = this.groupList.iterator();
            while (it.hasNext()) {
                if (it.next().getDeviceIds().size() == 0) {
                    it.remove();
                }
            }
        }
        this.refreshList.call();
    }

    private void sortList() {
        if (!this.deviceList.isEmpty()) {
            Collections.sort(this.deviceList, new Comparator() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd$$ExternalSyntheticLambda2
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return FtRoomDaliAdd.lambda$sortList$7((Device) obj, (Device) obj2);
                }
            });
        }
        if (this.groupList.isEmpty()) {
            return;
        }
        Collections.sort(this.groupList, new Comparator() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd$$ExternalSyntheticLambda3
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return FtRoomDaliAdd.lambda$sortList$8((Group) obj, (Group) obj2);
            }
        });
    }

    static /* synthetic */ int lambda$sortList$7(Device device, Device device2) {
        return DaliProHelper.getDaliAddress(device) - DaliProHelper.getDaliAddress(device2);
    }

    static /* synthetic */ int lambda$sortList$8(Group group, Group group2) {
        return DaliProHelper.getDaliAddress(group) - DaliProHelper.getDaliAddress(group2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEmptyDialog(final Group group) {
        MessageDialog.show((AppCompatActivity) requireActivity(), "", getString(R.string.dali_group_no_light_tip)).setOkButton(getString(R.string.add_dali_light), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd$$ExternalSyntheticLambda6
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showEmptyDialog$9;
                lambda$showEmptyDialog$9 = FtRoomDaliAdd.this.lambda$showEmptyDialog$9(group, baseDialog, view);
                return lambda$showEmptyDialog$9;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showEmptyDialog$9(Group group, BaseDialog baseDialog, View view) {
        NavUtils.destination(ActSelectDaliLight.class).withLong(Constants.PLACE_ID, this.placeId).withLong("device_id", group.getMacdeviceid()).withLong(Constants.GROUP_ID, group.getGroupId()).navigation(ActivityUtils.getTopActivity());
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showQuickDialog(Role role) {
        boolean z = role instanceof Group;
        int convertDaliType = DaliProHelper.convertDaliType(role);
        if (convertDaliType == 1) {
            LightQuickDialog dim = LightQuickDialog.dim();
            dim.setLightName(role.getName()).setOnline(role.getDeviceState().isOnline()).setSwitchOn(role.getDeviceState().isOn()).setRgbBrt(role.getDeviceState().getWyBrt()).setCallback(new AnonymousClass6(role, dim, convertDaliType, z)).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
            selectStatus(role, dim);
            return;
        }
        if (convertDaliType == 2) {
            LHomeLog.e(getClass(), "LightItem.wyBrt=" + role.getDeviceState().getWyBrt());
            LightQuickDialog ct = LightQuickDialog.ct();
            ct.setLightName(role.getName()).setOnline(role.getDeviceState().isOnline()).setWy(role.getDeviceState().getWy()).setControlObject(role).setRgbBrt(role.getDeviceState().getWyBrt()).setSwitchOn(role.getDeviceState().isOn()).setCallback(new AnonymousClass4(role, ct, convertDaliType, z)).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
            selectStatus(role, ct);
            return;
        }
        if (convertDaliType == 3) {
            LightQuickDialog rgb = LightQuickDialog.rgb();
            rgb.setLightName(role.getName()).setOnline(role.getDeviceState().isOnline()).setSwitchOn(role.getDeviceState().isOn()).setRgbBrt(role.getDeviceState().getRgbBrt()).setCallback(new AnonymousClass5(role, rgb, convertDaliType, z)).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
            selectStatus(role, rgb);
        } else {
            if (convertDaliType != 5) {
                return;
            }
            LightQuickDialog ctRgb = LightQuickDialog.ctRgb();
            ctRgb.setLightName(role.getName()).setOnline(role.getDeviceState().isOnline()).setSwitchOn(role.getDeviceState().isOn()).setWy(role.getDeviceState().getWy()).setControlObject(role).setRgbBrt(role.getDeviceState().getRgbBrt()).setCallback(new AnonymousClass7(role, ctRgb, convertDaliType, z)).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
            selectStatus(role, ctRgb);
        }
    }

    /* renamed from: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd$4, reason: invalid class name */
    class AnonymousClass4 implements LightQuickDialog.OnDialogCallback {
        final /* synthetic */ LightQuickDialog val$ctDialog;
        final /* synthetic */ boolean val$isGroup;
        final /* synthetic */ int val$lightType;
        final /* synthetic */ Role val$role;

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onColorChanged(float xProgress, int color, boolean finish) {
        }

        AnonymousClass4(final Role val$role, final LightQuickDialog val$ctDialog, final int val$lightType, final boolean val$isGroup) {
            this.val$role = val$role;
            this.val$ctDialog = val$ctDialog;
            this.val$lightType = val$lightType;
            this.val$isGroup = val$isGroup;
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onBrtChanged(int progress, boolean finish) {
            LHomeLog.e(getClass(), "onBrtChanged.progress=" + progress);
            FtRoomDaliAdd.this.getLightCmdHelper(this.val$role).sendCtBrtHas1to9(ActivityUtils.getTopActivity(), progress, finish);
            FtRoomDaliAdd.this.getLightCmdHelper(this.val$role).setState(true);
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onSwitch(final boolean on) {
            LightAssistant lightCmdHelper = FtRoomDaliAdd.this.getLightCmdHelper(this.val$role);
            Activity topActivity = ActivityUtils.getTopActivity();
            final LightQuickDialog lightQuickDialog = this.val$ctDialog;
            lightCmdHelper.sendOnOff(topActivity, on, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd$4$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    FtRoomDaliAdd.AnonymousClass4.lambda$onSwitch$0(LightQuickDialog.this, on, (Boolean) obj);
                }
            });
        }

        static /* synthetic */ void lambda$onSwitch$0(LightQuickDialog lightQuickDialog, boolean z, Boolean bool) {
            if (bool.booleanValue()) {
                return;
            }
            lightQuickDialog.setSwitchOn(!z);
            lightQuickDialog.notifyDialog();
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onMoreAction() {
            FtRoomDaliAdd.this.nav(this.val$role, this.val$lightType, Boolean.valueOf(this.val$isGroup));
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onCtChanged(float xProgress, int color, boolean finish) {
            FtRoomDaliAdd.this.getLightCmdHelper(this.val$role).sendCW(ActivityUtils.getTopActivity(), LightUtils.percent2C(xProgress), finish);
            FtRoomDaliAdd.this.getLightCmdHelper(this.val$role).setState(true);
        }
    }

    /* renamed from: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd$5, reason: invalid class name */
    class AnonymousClass5 implements LightQuickDialog.OnDialogCallback {
        final /* synthetic */ boolean val$isGroup;
        final /* synthetic */ int val$lightType;
        final /* synthetic */ LightQuickDialog val$rgbDialog;
        final /* synthetic */ Role val$role;

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onCtChanged(float xProgress, int color, boolean finish) {
        }

        AnonymousClass5(final Role val$role, final LightQuickDialog val$rgbDialog, final int val$lightType, final boolean val$isGroup) {
            this.val$role = val$role;
            this.val$rgbDialog = val$rgbDialog;
            this.val$lightType = val$lightType;
            this.val$isGroup = val$isGroup;
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onColorChanged(float xProgress, int color, boolean finish) {
            FtRoomDaliAdd.this.getLightCmdHelper(this.val$role).sendRgb(ActivityUtils.getTopActivity(), color, finish);
            FtRoomDaliAdd.this.getLightCmdHelper(this.val$role).setState(true);
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onBrtChanged(int progress, boolean finish) {
            FtRoomDaliAdd.this.getLightCmdHelper(this.val$role).sendRgbBrtHas1to9(ActivityUtils.getTopActivity(), progress, finish);
            FtRoomDaliAdd.this.getLightCmdHelper(this.val$role).setState(true);
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onSwitch(final boolean on) {
            LightAssistant lightCmdHelper = FtRoomDaliAdd.this.getLightCmdHelper(this.val$role);
            Activity topActivity = ActivityUtils.getTopActivity();
            final LightQuickDialog lightQuickDialog = this.val$rgbDialog;
            lightCmdHelper.sendOnOff(topActivity, on, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd$5$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    FtRoomDaliAdd.AnonymousClass5.lambda$onSwitch$0(LightQuickDialog.this, on, (Boolean) obj);
                }
            });
        }

        static /* synthetic */ void lambda$onSwitch$0(LightQuickDialog lightQuickDialog, boolean z, Boolean bool) {
            if (bool.booleanValue()) {
                return;
            }
            lightQuickDialog.setSwitchOn(!z);
            lightQuickDialog.notifyDialog();
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onMoreAction() {
            FtRoomDaliAdd.this.nav(this.val$role, this.val$lightType, Boolean.valueOf(this.val$isGroup));
        }
    }

    /* renamed from: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd$6, reason: invalid class name */
    class AnonymousClass6 implements LightQuickDialog.OnDialogCallback {
        final /* synthetic */ LightQuickDialog val$dimDialog;
        final /* synthetic */ boolean val$isGroup;
        final /* synthetic */ int val$lightType;
        final /* synthetic */ Role val$role;

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onColorChanged(float xProgress, int color, boolean finish) {
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onCtChanged(float xProgress, int color, boolean finish) {
        }

        AnonymousClass6(final Role val$role, final LightQuickDialog val$dimDialog, final int val$lightType, final boolean val$isGroup) {
            this.val$role = val$role;
            this.val$dimDialog = val$dimDialog;
            this.val$lightType = val$lightType;
            this.val$isGroup = val$isGroup;
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onBrtChanged(int progress, boolean finish) {
            FtRoomDaliAdd.this.getLightCmdHelper(this.val$role).sendDimBrtHas1to9(ActivityUtils.getTopActivity(), progress, finish);
            FtRoomDaliAdd.this.getLightCmdHelper(this.val$role).setState(true);
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onSwitch(final boolean on) {
            LightAssistant lightCmdHelper = FtRoomDaliAdd.this.getLightCmdHelper(this.val$role);
            Activity topActivity = ActivityUtils.getTopActivity();
            final LightQuickDialog lightQuickDialog = this.val$dimDialog;
            lightCmdHelper.sendOnOff(topActivity, on, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd$6$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    FtRoomDaliAdd.AnonymousClass6.lambda$onSwitch$0(LightQuickDialog.this, on, (Boolean) obj);
                }
            });
        }

        static /* synthetic */ void lambda$onSwitch$0(LightQuickDialog lightQuickDialog, boolean z, Boolean bool) {
            if (bool.booleanValue()) {
                return;
            }
            lightQuickDialog.setSwitchOn(!z);
            lightQuickDialog.notifyDialog();
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onMoreAction() {
            FtRoomDaliAdd.this.nav(this.val$role, this.val$lightType, Boolean.valueOf(this.val$isGroup));
        }
    }

    /* renamed from: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd$7, reason: invalid class name */
    class AnonymousClass7 implements LightQuickDialog.OnDialogCallback {
        final /* synthetic */ LightQuickDialog val$dialog;
        final /* synthetic */ boolean val$isGroup;
        final /* synthetic */ int val$lightType;
        final /* synthetic */ Role val$role;

        AnonymousClass7(final Role val$role, final LightQuickDialog val$dialog, final int val$lightType, final boolean val$isGroup) {
            this.val$role = val$role;
            this.val$dialog = val$dialog;
            this.val$lightType = val$lightType;
            this.val$isGroup = val$isGroup;
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onColorChanged(float xProgress, int color, boolean finish) {
            FtRoomDaliAdd.this.getLightCmdHelper(this.val$role).sendRgb(ActivityUtils.getTopActivity(), color, finish);
            FtRoomDaliAdd.this.getLightCmdHelper(this.val$role).setState(true);
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onBrtChanged(int progress, boolean finish) {
            FtRoomDaliAdd.this.getLightCmdHelper(this.val$role).sendDimBrtD1Has1to9(ActivityUtils.getTopActivity(), progress, finish);
            FtRoomDaliAdd.this.getLightCmdHelper(this.val$role).setState(true);
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onSwitch(final boolean on) {
            LightAssistant lightCmdHelper = FtRoomDaliAdd.this.getLightCmdHelper(this.val$role);
            Activity topActivity = ActivityUtils.getTopActivity();
            final LightQuickDialog lightQuickDialog = this.val$dialog;
            lightCmdHelper.sendOnOff(topActivity, on, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd$7$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    FtRoomDaliAdd.AnonymousClass7.lambda$onSwitch$0(LightQuickDialog.this, on, (Boolean) obj);
                }
            });
        }

        static /* synthetic */ void lambda$onSwitch$0(LightQuickDialog lightQuickDialog, boolean z, Boolean bool) {
            if (bool.booleanValue()) {
                return;
            }
            lightQuickDialog.setSwitchOn(!z);
            lightQuickDialog.notifyDialog();
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onMoreAction() {
            FtRoomDaliAdd.this.nav(this.val$role, this.val$lightType, Boolean.valueOf(this.val$isGroup));
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onCtChanged(float xProgress, int color, boolean finish) {
            FtRoomDaliAdd.this.getLightCmdHelper(this.val$role).sendCW(ActivityUtils.getTopActivity(), LightUtils.percent2C(xProgress), finish);
            FtRoomDaliAdd.this.getLightCmdHelper(this.val$role).setState(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEditDialog(final Role role) {
        SelectCgdActionDialog.asDefault().setTitle(role.getName()).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.confirm)).setRole(role).setCallback(new CgdActionSelectView.OnDialogCallback() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd.8
            @Override // com.ltech.smarthome.ui.device.dalipro.CgdActionSelectView.OnDialogCallback
            public void onColorChanged(float xProgress, int color, boolean finish) {
                FtRoomDaliAdd.this.getLightCmdHelper(role).sendRgb(FtRoomDaliAdd.this.getActivity(), color, finish);
            }

            @Override // com.ltech.smarthome.ui.device.dalipro.CgdActionSelectView.OnDialogCallback
            public void onBrtChanged(int progress, boolean finish) {
                FtRoomDaliAdd.this.getLightCmdHelper(role).sendDimBrtD1Has1to9(FtRoomDaliAdd.this.getActivity(), progress, finish);
            }

            @Override // com.ltech.smarthome.ui.device.dalipro.CgdActionSelectView.OnDialogCallback
            public void onCtChanged(int v, boolean finish) {
                FtRoomDaliAdd.this.getLightCmdHelper(role).sendCW(FtRoomDaliAdd.this.getContext(), v, finish);
            }
        }).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtRoomDaliAdd.this.lambda$showEditDialog$10(role, (Boolean) obj);
            }
        }).showDialog(getActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditDialog$10(Role role, Boolean bool) {
        if (role instanceof Group) {
            for (Long l : ((Group) role).getDeviceIds()) {
                this.mViewModel.actionMap.put(l, DaliProHelper.getDeviceParam(l.longValue()));
            }
        } else {
            Device device = (Device) role;
            this.mViewModel.actionMap.put(Long.valueOf(device.getDeviceId()), DaliProHelper.getDeviceParam(device.getDeviceId()));
        }
        this.deviceAdapter.notifyDataSetChanged();
        if (((FtRoomDaliAddBinding) this.mViewBinding).getSceneEdit().booleanValue()) {
            this.sceneAdapter.notifyDataSetChanged();
        }
    }

    private void createLightCloseParam(Role role) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setCmdType(1);
        lightCmdParam.setOn(false);
        lightCmdParam.addExtParam(SceneHelper.OPTION, "4");
        DaliProHelper.cmdParam = lightCmdParam;
        if (role instanceof Group) {
            for (Long l : ((Group) role).getDeviceIds()) {
                this.mViewModel.actionMap.put(l, DaliProHelper.getDeviceParam(l.longValue()));
            }
            return;
        }
        Device device = (Device) role;
        this.mViewModel.actionMap.put(Long.valueOf(device.getDeviceId()), DaliProHelper.getDeviceParam(device.getDeviceId()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nav(Role role, int lightType, Boolean isGroupControl) {
        NavUtils.destination(ActDaliLightOrGroup.class).withLong(Constants.PLACE_ID, this.placeId).withLong("device_id", this.macDeviceId).withBoolean(Constants.GROUP_CONTROL, isGroupControl.booleanValue()).withLong(Constants.CONTROL_ID, role.getId()).withInt(Constants.LIGHT_TYPE, lightType).navigation(ActivityUtils.getTopActivity());
    }

    public boolean inSelectGroups(long deviceId) {
        Iterator<Long> it = this.mViewModel.selectGroupIds.iterator();
        while (it.hasNext()) {
            if (Injection.repo().group().getGroupByGroupId(it.next().longValue()).getDeviceIds().contains(Long.valueOf(deviceId))) {
                return true;
            }
        }
        return false;
    }

    public LightAssistant getLightCmdHelper(Role role) {
        return CmdAssistant.getLightCmdAssistant(role, new int[0]);
    }

    private void selectStatus(Role role, final LightQuickDialog lightQuickDialog) {
        if (role instanceof Group) {
            final Group group = (Group) role;
            MessageManager.getInstance().setDaliLightStatusCallBack(new MessageManager.DaliLightStatusCallBack(this) { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd.9
                @Override // com.smart.message.MessageManager.DaliLightStatusCallBack
                public void onDataReceive(int zoneNum, int deviceAddress, boolean isOn, int wyBrt, int wy, int rgbBrt, boolean supportK, int rgbColor) {
                    Group groupByGroupId = Injection.repo().group().getGroupByGroupId(group.getGroupId());
                    if (groupByGroupId != null && zoneNum == DaliProHelper.getZoneNum(group) && deviceAddress == group.getFirstDevUniAddr()) {
                        groupByGroupId.getGroupState().setOn(isOn);
                        groupByGroupId.getGroupState().setWyBrt(LightUtils.brt2ProgressHasBelowZero(wyBrt));
                        groupByGroupId.getGroupState().setWy(wy);
                        groupByGroupId.getGroupState().setRgbBrt(LightUtils.brt2ProgressHasBelowZero(rgbBrt));
                        groupByGroupId.getGroupState().setOnlineState(1);
                        Injection.repo().group().saveGroup(groupByGroupId);
                        lightQuickDialog.setOnline(true).setSwitchOn(isOn);
                        lightQuickDialog.setWy(wy);
                        int lightColorType = ProductRepository.getLightColorType((Object) groupByGroupId);
                        if (lightColorType == 1 || lightColorType == 2) {
                            lightQuickDialog.setRgbBrt(groupByGroupId.getGroupState().getWyBrt());
                        } else {
                            lightQuickDialog.setRgbBrt(groupByGroupId.getGroupState().getRgbBrt());
                        }
                        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd.9.1
                            @Override // java.lang.Runnable
                            public void run() {
                                lightQuickDialog.notifyDialog();
                            }
                        });
                    }
                }
            });
            CmdAssistant.getQueryCmdAssistant(group, new int[0]).queryLightState(getContext(), DaliProHelper.getZoneNum(group));
        } else {
            final Device device = (Device) role;
            MessageManager.getInstance().setDaliLightStatusCallBack(new MessageManager.DaliLightStatusCallBack(this) { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd.10
                @Override // com.smart.message.MessageManager.DaliLightStatusCallBack
                public void onDataReceive(int zoneNum, int deviceAddress, boolean isOn, int wyBrt, int wy, int rgbBrt, boolean supportK, int rgbColor) {
                    Device deviceById = Injection.repo().device().getDeviceById(device.getId());
                    if (deviceById != null && zoneNum == DaliProHelper.getZoneNum(deviceById) && deviceAddress == device.getUnicastAddress()) {
                        deviceById.getDeviceState().setOn(isOn);
                        deviceById.getDeviceState().setWyBrt(LightUtils.brt2ProgressHasBelowZero(wyBrt));
                        deviceById.getDeviceState().setWy(wy);
                        deviceById.getDeviceState().setRgbBrt(LightUtils.brt2ProgressHasBelowZero(rgbBrt));
                        deviceById.setOnlineFlag(1);
                        deviceById.getDeviceState().setOnlineState(1);
                        Injection.repo().device().saveDevice(deviceById);
                        lightQuickDialog.setOnline(true).setSwitchOn(isOn);
                        lightQuickDialog.setWy(wy);
                        int lightColorType = ProductRepository.getLightColorType((Object) deviceById);
                        if (lightColorType == 101 || lightColorType == 102) {
                            lightQuickDialog.setRgbBrt(deviceById.getDeviceState().getWyBrt());
                        } else {
                            lightQuickDialog.setRgbBrt(deviceById.getDeviceState().getRgbBrt());
                        }
                        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRoomDaliAdd.10.1
                            @Override // java.lang.Runnable
                            public void run() {
                                lightQuickDialog.notifyDialog();
                            }
                        });
                    }
                }
            });
            CmdAssistant.getQueryCmdAssistant(device, new int[0]).queryLightState(getContext(), DaliProHelper.getZoneNum(device));
        }
    }
}