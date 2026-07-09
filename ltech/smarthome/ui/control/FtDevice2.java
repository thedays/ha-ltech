package com.ltech.smarthome.ui.control;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.adapter.MultipleItemRvAdapter;
import com.ltech.smarthome.animation.MyItemAnimator;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.databinding.FtDevice2Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.ItemHomePageLabel;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.control.FtDevice2;
import com.ltech.smarthome.ui.control.FtRoomVM;
import com.ltech.smarthome.ui.control.provider.AsPanelItemProvider;
import com.ltech.smarthome.ui.control.provider.BaseDeviceProvider;
import com.ltech.smarthome.ui.control.provider.BleCurtainGroupItemProvider;
import com.ltech.smarthome.ui.control.provider.BleDeviceItemProvider;
import com.ltech.smarthome.ui.control.provider.BleDryCurtainGroupItemProvider;
import com.ltech.smarthome.ui.control.provider.BleHamItemProvider;
import com.ltech.smarthome.ui.control.provider.CameraItemProvider;
import com.ltech.smarthome.ui.control.provider.CentralAirItemProvider;
import com.ltech.smarthome.ui.control.provider.CentralAirSubDeviceItemProvider;
import com.ltech.smarthome.ui.control.provider.DefaultGroupItemProvider;
import com.ltech.smarthome.ui.control.provider.DefaultItemProvider;
import com.ltech.smarthome.ui.control.provider.DryToBleItemProvider;
import com.ltech.smarthome.ui.control.provider.EurPanelProItemProvider;
import com.ltech.smarthome.ui.control.provider.IrDeviceItemProvider;
import com.ltech.smarthome.ui.control.provider.KbsGroupItemProvider;
import com.ltech.smarthome.ui.control.provider.KbsItemProvider;
import com.ltech.smarthome.ui.control.provider.KeySwitchItemProvider;
import com.ltech.smarthome.ui.control.provider.KnobPanelBatteryItemProvider;
import com.ltech.smarthome.ui.control.provider.KnobPanelItemProvider;
import com.ltech.smarthome.ui.control.provider.KnobPanelProItemProvider;
import com.ltech.smarthome.ui.control.provider.LightGroupItemProvider;
import com.ltech.smarthome.ui.control.provider.LightItemProvider;
import com.ltech.smarthome.ui.control.provider.MeshGatewayItemProvider;
import com.ltech.smarthome.ui.control.provider.MusicPlayerItemProvider;
import com.ltech.smarthome.ui.control.provider.Rs8ItemProvider;
import com.ltech.smarthome.ui.control.provider.Scene8ItemProvider;
import com.ltech.smarthome.ui.control.provider.SceneItemProvider;
import com.ltech.smarthome.ui.control.provider.SensorGroupItemProvider;
import com.ltech.smarthome.ui.control.provider.SensorItemProvider;
import com.ltech.smarthome.ui.control.provider.SmartPanelGroupItemProvider;
import com.ltech.smarthome.ui.control.provider.SmartPanelItemProvider;
import com.ltech.smarthome.ui.control.provider.SuperPanelItemProvider;
import com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSetting;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.scene.ActAddCloudScene;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.ui.scene.local.ActAddLocalScene;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class FtDevice2 extends VMFragment<FtDevice2Binding, FtRoomVM> implements BaseDeviceProvider.OnDataChangeListener {
    private MultipleItemRvAdapter<Role, BaseViewHolder> deviceAdapter;
    private long roomId;
    private boolean isChange = false;
    private boolean sceneListExtend = false;
    private boolean sceneListHide = false;
    private List<Role> curAllRoleList = new ArrayList();
    private List<Scene> curAllSceneList = new ArrayList();
    private final IAction<FtRoomVM.SceneData> iSceneAction = new IAction<FtRoomVM.SceneData>() { // from class: com.ltech.smarthome.ui.control.FtDevice2.15
        @Override // com.ltech.smarthome.base.IAction
        public void act(FtRoomVM.SceneData data) {
            if (FtDevice2.this.mViewBinding != null) {
                if (FtDevice2.this.roomId == data.getRoomId()) {
                    for (int size = FtDevice2.this.deviceAdapter.getData().size() - 1; size >= 0; size--) {
                        Role role = (Role) FtDevice2.this.deviceAdapter.getData().get(size);
                        if ((role instanceof Scene) || ((role instanceof ItemHomePageLabel) && ((ItemHomePageLabel) role).getType() == 1)) {
                            FtDevice2.this.deviceAdapter.remove(size);
                        }
                    }
                    if (data.getScenes() != null && !data.getScenes().isEmpty()) {
                        ArrayList arrayList = new ArrayList();
                        if (data.getScenes().size() > 4) {
                            if (FtDevice2.this.sceneListExtend) {
                                arrayList.add(new ItemHomePageLabel(1, String.format(FtDevice2.this.getString(R.string.common_scene_count), Integer.valueOf(data.getScenes().size())), true, 1));
                                arrayList.addAll(data.getScenes());
                                FtDevice2.this.deviceAdapter.addData(0, (Collection) arrayList);
                            } else {
                                arrayList.add(new ItemHomePageLabel(1, String.format(FtDevice2.this.getString(R.string.common_scene_count), Integer.valueOf(data.getScenes().size())), true, 0));
                                arrayList.addAll(data.getScenes().subList(0, 4));
                                FtDevice2.this.deviceAdapter.addData(0, (Collection) arrayList);
                            }
                        } else {
                            arrayList.add(new ItemHomePageLabel(1, String.format(FtDevice2.this.getString(R.string.common_scene_count), Integer.valueOf(data.getScenes().size())), false));
                            arrayList.addAll(data.getScenes());
                            FtDevice2.this.deviceAdapter.addData(0, (Collection) arrayList);
                        }
                        FtDevice2.this.curAllSceneList = data.getScenes();
                        ((FtDevice2Binding) FtDevice2.this.mViewBinding).setDeviceVisible(true);
                        FtDevice2.this.showContent();
                    } else {
                        FtDevice2.this.curAllSceneList = new ArrayList();
                    }
                    if (((FtRoomVM) FtDevice2.this.mViewModel).isRoomSelect(FtDevice2.this.roomId) && FtDevice2.this.isChange) {
                        FtDevice2.this.isChange = false;
                    }
                    if (FtDevice2.this.curAllRoleList.isEmpty() && FtDevice2.this.curAllSceneList.isEmpty()) {
                        FtDevice2.this.showEmpty();
                    }
                }
                FtDevice2.this.dismissLoadingDialog();
            }
        }
    };
    private final IAction<FtRoomVM.RoleData> iDeviceAction = new IAction<FtRoomVM.RoleData>() { // from class: com.ltech.smarthome.ui.control.FtDevice2.16
        @Override // com.ltech.smarthome.base.IAction
        public void act(FtRoomVM.RoleData data) {
            if (FtDevice2.this.mViewBinding != null) {
                if (FtDevice2.this.roomId == data.getRoomId()) {
                    ArrayList arrayList = new ArrayList();
                    if (data.getRoles() == null || data.getRoles().isEmpty()) {
                        ((FtDevice2Binding) FtDevice2.this.mViewBinding).setDeviceVisible(false);
                        FtDevice2.this.deviceAdapter.replaceData(new ArrayList());
                        FtDevice2.this.curAllRoleList = new ArrayList();
                    } else {
                        arrayList.add(new ItemHomePageLabel(0, String.format(FtDevice2.this.getString(R.string.device_count), Integer.valueOf(data.getRoles().size())), false));
                        arrayList.addAll(data.getRoles());
                        ((FtDevice2Binding) FtDevice2.this.mViewBinding).setDeviceVisible(true);
                        FtDevice2.this.deviceAdapter.replaceData(arrayList);
                        FtDevice2.this.curAllRoleList = data.getRoles();
                        FtDevice2.this.showContent();
                    }
                    if (((FtRoomVM) FtDevice2.this.mViewModel).isRoomSelect(FtDevice2.this.roomId) && FtDevice2.this.isChange) {
                        FtDevice2.this.isChange = false;
                        FtDevice2.this.check();
                    }
                }
                ((FtRoomVM) FtDevice2.this.mViewModel).filterSceneByRoom(FtDevice2.this.roomId, FtDevice2.this.iSceneAction);
            }
        }
    };

    static /* synthetic */ boolean lambda$showNoPermissionDialog$6(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_device2;
    }

    @Override // com.ltech.smarthome.base.VMFragment
    protected boolean shareVM() {
        return true;
    }

    public static FtDevice2 newInstance(long placeId, long roomId) {
        FtDevice2 ftDevice2 = new FtDevice2();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.PLACE_ID, placeId);
        bundle.putLong(Constants.ROOM_ID, roomId);
        ftDevice2.setArguments(bundle);
        return ftDevice2;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.no_device).emptyTryStringRes(R.string.add_device));
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initData() {
        super.initData();
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.roomId = arguments.getLong(Constants.ROOM_ID);
            ((FtRoomVM) this.mViewModel).placeId = arguments.getLong(Constants.PLACE_ID);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        ((FtRoomVM) this.mViewModel).isFirst = true;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(new ArrayList());
        this.deviceAdapter = anonymousClass1;
        anonymousClass1.finishInitialize();
        this.deviceAdapter.bindToRecyclerView(((FtDevice2Binding) this.mViewBinding).rvDevice);
        ((FtDevice2Binding) this.mViewBinding).rvDevice.setLayoutManager(getGridLayoutManager());
        MyItemAnimator myItemAnimator = new MyItemAnimator();
        myItemAnimator.setSupportsChangeAnimations(false);
        ((FtDevice2Binding) this.mViewBinding).rvDevice.setItemAnimator(myItemAnimator);
        ((FtDevice2Binding) this.mViewBinding).rvDevice.setHasFixedSize(true);
        ((FtDevice2Binding) this.mViewBinding).rvDevice.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.ltech.smarthome.ui.control.FtDevice2.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 0) {
                    if (((FtRoomVM) FtDevice2.this.mViewModel).isRefresh) {
                        return;
                    }
                    FtDevice2.this.check();
                    return;
                }
                ((FtRoomVM) FtDevice2.this.mViewModel).cleanCheck();
            }
        });
        ItemTouchHelper itemTouchHelper = getItemTouchHelper();
        itemTouchHelper.attachToRecyclerView(((FtDevice2Binding) this.mViewBinding).rvDevice);
        this.deviceAdapter.enableDragItem(itemTouchHelper);
    }

    /* renamed from: com.ltech.smarthome.ui.control.FtDevice2$1, reason: invalid class name */
    class AnonymousClass1 extends MultipleItemRvAdapter<Role, BaseViewHolder> {
        AnonymousClass1(List data) {
            super(data);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.adapter.MultipleItemRvAdapter
        public int getViewType(Role role) {
            return role instanceof Scene ? Boolean.TRUE.equals(((FtRoomVM) FtDevice2.this.mViewModel).editSceneMode.getValue()) ? Constants.VIEW_TYPE_SCENE_9991 : Boolean.TRUE.equals(((FtRoomVM) FtDevice2.this.mViewModel).editRoleMode.getValue()) ? Constants.VIEW_TYPE_SCENE_9992 : Constants.VIEW_TYPE_SCENE_9990 : role instanceof ItemHomePageLabel ? Constants.VIEW_TYPE_LABEL_9993 : ((FtRoomVM) FtDevice2.this.mViewModel).getViewType(role);
        }

        @Override // com.ltech.smarthome.adapter.MultipleItemRvAdapter
        public void registerItemProvider() {
            this.mProviderDelegate.registerProvider(new AsPanelItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new MeshGatewayItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new LightItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new IrDeviceItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new BleDeviceItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new SceneItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new Scene8ItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new SuperPanelItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new KeySwitchItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new CentralAirItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new MusicPlayerItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new SmartPanelItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new SensorItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new CentralAirSubDeviceItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new SmartPanelGroupItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new LightGroupItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new BleCurtainGroupItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new BleDryCurtainGroupItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new DryToBleItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new BleHamItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new KnobPanelItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new SensorGroupItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new CameraItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new KnobPanelBatteryItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new KnobPanelProItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new DefaultItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new EurPanelProItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new DefaultGroupItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new KbsItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new KbsGroupItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new Rs8ItemProvider(FtDevice2.this.getActivity(), ((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice, FtDevice2.this.mViewModel, FtDevice2.this));
            this.mProviderDelegate.registerProvider(new C01111());
            this.mProviderDelegate.registerProvider(new BaseItemProvider<Scene, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.control.FtDevice2.1.2
                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int layout() {
                    return R.layout.item_scene_new;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int viewType() {
                    return Constants.VIEW_TYPE_SCENE_9991;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void convert(BaseViewHolder helper, Scene item, int position) {
                    helper.setText(R.id.tv_name, item.getSceneName()).setImageResource(R.id.appCompatImageView14, SceneHelper.getSceneIcon(ActivityUtils.getTopActivity(), item.getIconPos())).setGone(R.id.iv_edit, false).setGone(R.id.iv_select, true).setImageResource(R.id.iv_select, ((FtRoomVM) FtDevice2.this.mViewModel).selectSceneList.getValue().contains(item) ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
                    FtDevice2.this.showSceneItemByType(helper, item);
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void onClick(BaseViewHolder helper, Scene scene, int position) {
                    List<Scene> value = ((FtRoomVM) FtDevice2.this.mViewModel).selectSceneList.getValue();
                    if (value != null) {
                        if (value.contains(scene)) {
                            value.remove(scene);
                        } else {
                            value.add(scene);
                        }
                        ((FtRoomVM) FtDevice2.this.mViewModel).selectSceneList.setValue(value);
                        ((FtRoomVM) FtDevice2.this.mViewModel).selectScenePos.setValue(Integer.valueOf(position));
                    }
                }
            });
            this.mProviderDelegate.registerProvider(new BaseItemProvider<Scene, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.control.FtDevice2.1.3
                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int layout() {
                    return R.layout.item_scene_new;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int viewType() {
                    return Constants.VIEW_TYPE_SCENE_9992;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void convert(BaseViewHolder helper, Scene item, int position) {
                    helper.setText(R.id.tv_name, item.getSceneName()).setImageResource(R.id.appCompatImageView14, SceneHelper.getSceneIcon(ActivityUtils.getTopActivity(), item.getIconPos())).setGone(R.id.iv_edit, false).setGone(R.id.iv_select, false);
                    FtDevice2.this.showSceneItemByType(helper, item);
                }
            });
            this.mProviderDelegate.registerProvider(new BaseItemProvider<ItemHomePageLabel, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.control.FtDevice2.1.4
                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int layout() {
                    return R.layout.item_homepage_header;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int viewType() {
                    return Constants.VIEW_TYPE_LABEL_9993;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void convert(BaseViewHolder helper, final ItemHomePageLabel item, final int position) {
                    helper.setText(R.id.tv_count, item.getName()).setImageResource(R.id.iv_show, item.getIndex() == 0 ? R.mipmap.ic_down_gray : R.mipmap.ic_up_gray).setGone(R.id.iv_show, item.getHideDevice() == 1);
                    helper.getView(R.id.iv_show).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.FtDevice2.1.4.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            for (int size = FtDevice2.this.deviceAdapter.getData().size() - 1; size >= 0; size--) {
                                if (((Role) FtDevice2.this.deviceAdapter.getData().get(size)) instanceof Scene) {
                                    FtDevice2.this.deviceAdapter.remove(size);
                                }
                            }
                            if (FtDevice2.this.sceneListExtend) {
                                FtDevice2.this.sceneListExtend = false;
                                item.setIndex(0);
                                FtDevice2.this.deviceAdapter.notifyItemChanged(position);
                                if (FtDevice2.this.curAllSceneList.size() > 4) {
                                    FtDevice2.this.deviceAdapter.addData(1, (Collection) FtDevice2.this.curAllSceneList.subList(0, 4));
                                    return;
                                }
                                return;
                            }
                            FtDevice2.this.sceneListExtend = true;
                            item.setIndex(1);
                            FtDevice2.this.deviceAdapter.notifyItemChanged(position);
                            FtDevice2.this.deviceAdapter.addData(1, (Collection) FtDevice2.this.curAllSceneList);
                        }
                    });
                }
            });
        }

        /* renamed from: com.ltech.smarthome.ui.control.FtDevice2$1$1, reason: invalid class name and collision with other inner class name */
        class C01111 extends BaseItemProvider<Scene, BaseViewHolder> {
            @Override // com.chad.library.adapter.base.provider.BaseItemProvider
            public int layout() {
                return R.layout.item_scene_new;
            }

            @Override // com.chad.library.adapter.base.provider.BaseItemProvider
            public int viewType() {
                return Constants.VIEW_TYPE_SCENE_9990;
            }

            C01111() {
            }

            @Override // com.chad.library.adapter.base.provider.BaseItemProvider
            public void convert(BaseViewHolder helper, final Scene item, int position) {
                helper.setText(R.id.tv_name, item.getSceneName()).setImageResource(R.id.appCompatImageView14, SceneHelper.getSceneIcon(ActivityUtils.getTopActivity(), item.getIconPos()));
                FtDevice2.this.showSceneItemByType(helper, item);
                helper.getView(R.id.iv_edit).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.FtDevice2$1$1$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        FtDevice2.AnonymousClass1.C01111.this.lambda$convert$0(item, view);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$convert$0(Scene scene, View view) {
                Injection.limiter().reset(Injection.keyCreator().sceneContentKey(scene.getSceneId()));
                if (scene.getSceneType() == 2) {
                    NavUtils.destination(ActAddLocalScene.class).withLong(Constants.PLACE_ID, scene.getPlaceId()).withInt(Constants.ICON_POSITION, scene.getIconPos()).withString(Constants.SCENE_NAME, scene.getSceneName()).withInt(Constants.SCENE_NUM, scene.getSceneNum()).withLong(Constants.SCENE_ID, scene.getSceneId()).navigation(FtDevice2.this.getActivity());
                } else if (scene.getSceneType() == 4) {
                    NavUtils.destination(ActDaliSceneSetting.class).withLong(Constants.PLACE_ID, scene.getPlaceId()).withLong(Constants.SCENE_ID, scene.getSceneId()).navigation(FtDevice2.this.getActivity());
                } else {
                    NavUtils.destination(ActAddCloudScene.class).withLong(Constants.PLACE_ID, scene.getPlaceId()).withInt(Constants.ICON_POSITION, scene.getIconPos()).withString(Constants.SCENE_NAME, scene.getSceneName()).withLong(Constants.SCENE_ID, scene.getSceneId()).navigation(FtDevice2.this.getActivity());
                }
            }

            @Override // com.chad.library.adapter.base.provider.BaseItemProvider
            public void onClick(BaseViewHolder helper, Scene scene, int position) {
                if (scene.getSceneType() == 2) {
                    CmdAssistant.getSceneCmdAssistant(null, new int[0]).executeLocalScene(FtDevice2.this.getActivity(), scene.getSceneNum());
                    FtDevice2.this.showSuccessDialog(FtDevice2.this.getString(R.string.execute_success));
                } else if (scene.getSceneType() == 4) {
                    CmdAssistant.getSceneCmdAssistant(Injection.repo().device().getDeviceByDeviceId(scene.getMacdeviceid()), new int[0]).executeDaliScene(FtDevice2.this.getActivity(), DaliProHelper.getSceneNum(scene));
                    FtDevice2.this.showSuccessDialog(FtDevice2.this.getString(R.string.execute_success));
                } else {
                    ((ObservableSubscribeProxy) Injection.net().executeScene(scene.getSceneId()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtDevice2$1$1$$ExternalSyntheticLambda1
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) {
                            FtDevice2.AnonymousClass1.C01111.this.lambda$onClick$1((Disposable) obj);
                        }
                    }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(FtDevice2.this.getLifecycle(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtDevice2$1$1$$ExternalSyntheticLambda2
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) {
                            FtDevice2.AnonymousClass1.C01111.this.lambda$onClick$2(obj);
                        }
                    }, new Consumer() { // from class: com.ltech.smarthome.ui.control.FtDevice2$1$1$$ExternalSyntheticLambda3
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) {
                            FtDevice2.AnonymousClass1.C01111.this.lambda$onClick$3((Throwable) obj);
                        }
                    });
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onClick$1(Disposable disposable) throws Exception {
                FtDevice2.this.showLoadingDialog(FtDevice2.this.getString(R.string.executing));
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onClick$2(Object obj) throws Exception {
                FtDevice2.this.showSuccessDialog(FtDevice2.this.getString(R.string.execute_success));
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onClick$3(Throwable th) throws Exception {
                FtDevice2.this.showErrorDialog(FtDevice2.this.getString(R.string.execute_fail));
            }

            @Override // com.chad.library.adapter.base.provider.BaseItemProvider
            public boolean onLongClick(BaseViewHolder helper, Scene data, int position) {
                if ((((FtRoomVM) FtDevice2.this.mViewModel).getCurPlace().isManager() || ((FtRoomVM) FtDevice2.this.mViewModel).getCurPlace().isOwner()) && Boolean.FALSE.equals(((FtRoomVM) FtDevice2.this.mViewModel).editSceneMode.getValue())) {
                    ((FtRoomVM) FtDevice2.this.mViewModel).selectSceneList.setValue(new ArrayList());
                    ((FtRoomVM) FtDevice2.this.mViewModel).editSceneMode.setValue(true);
                }
                return true;
            }
        }
    }

    private ItemTouchHelper getItemTouchHelper() {
        return new ItemTouchHelper(new ItemDragAndSwipeCallback(this.deviceAdapter) { // from class: com.ltech.smarthome.ui.control.FtDevice2.3
            @Override // com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback, androidx.recyclerview.widget.ItemTouchHelper.Callback
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            }

            @Override // com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback, androidx.recyclerview.widget.ItemTouchHelper.Callback
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int adapterPosition = viewHolder.getAdapterPosition();
                if (!Boolean.TRUE.equals(((FtRoomVM) FtDevice2.this.mViewModel).editSceneMode.getValue()) || !inRange(adapterPosition) || !(FtDevice2.this.deviceAdapter.getData().get(adapterPosition) instanceof Scene)) {
                    if (Boolean.TRUE.equals(((FtRoomVM) FtDevice2.this.mViewModel).editRoleMode.getValue()) && inRange(adapterPosition) && (((FtDevice2.this.deviceAdapter.getData().get(adapterPosition) instanceof Group) || (FtDevice2.this.deviceAdapter.getData().get(adapterPosition) instanceof Device)) && viewHolder.getItemViewType() != 21)) {
                        return makeMovementFlags(15, 0);
                    }
                    return 0;
                }
                return makeMovementFlags(15, 0);
            }

            @Override // com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback, androidx.recyclerview.widget.ItemTouchHelper.Callback
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Role role;
                Role role2;
                int viewHolderPosition = getViewHolderPosition(viewHolder);
                int viewHolderPosition2 = getViewHolderPosition(target);
                if (inRange(viewHolderPosition) && inRange(viewHolderPosition2)) {
                    role = (Role) FtDevice2.this.deviceAdapter.getData().get(viewHolderPosition);
                    role2 = (Role) FtDevice2.this.deviceAdapter.getData().get(viewHolderPosition2);
                } else {
                    role = null;
                    role2 = null;
                }
                if (role == null || role2 == null || target.getItemViewType() == 21 || viewHolder.getItemViewType() == 21 || (role instanceof ItemHomePageLabel) || (role2 instanceof ItemHomePageLabel)) {
                    return false;
                }
                if ((role instanceof Scene) && ((role2 instanceof Group) || (role2 instanceof Device))) {
                    return false;
                }
                return (((role instanceof Group) || (role instanceof Device)) && (role2 instanceof Scene)) ? false : true;
            }

            private boolean inRange(int position) {
                return position >= 0 && position < FtDevice2.this.deviceAdapter.getData().size();
            }

            private int getViewHolderPosition(RecyclerView.ViewHolder viewHolder) {
                return viewHolder.getAdapterPosition() - FtDevice2.this.deviceAdapter.getHeaderLayoutCount();
            }
        });
    }

    private GridLayoutManager getGridLayoutManager() {
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2) { // from class: com.ltech.smarthome.ui.control.FtDevice2.4
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean canScrollVertically() {
                return Boolean.TRUE.equals(((FtRoomVM) FtDevice2.this.mViewModel).canScroll.getValue());
            }
        };
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.ltech.smarthome.ui.control.FtDevice2.5
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int position) {
                if (FtDevice2.this.deviceAdapter.getItemViewType(position) == 21 || FtDevice2.this.deviceAdapter.getItemViewType(position) == 9993) {
                    return gridLayoutManager.getSpanCount();
                }
                return 1;
            }
        });
        return gridLayoutManager;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        ((FtRoomVM) this.mViewModel).init();
        ((FtRoomVM) this.mViewModel).showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.control.FtDevice2$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDevice2.this.lambda$startObserve$0((Void) obj);
            }
        });
        ((FtRoomVM) this.mViewModel).roleResult.observe(this, new Observer<Resource<List<Role>>>() { // from class: com.ltech.smarthome.ui.control.FtDevice2.6
            @Override // androidx.lifecycle.Observer
            public void onChanged(Resource<List<Role>> listResource) {
                if (!listResource.isSuccess() || listResource.getData() == null) {
                    return;
                }
                if (!listResource.getData().isEmpty()) {
                    FtDevice2.this.isChange = true;
                    ((FtRoomVM) FtDevice2.this.mViewModel).filterDeviceByRoom(FtDevice2.this.roomId, FtDevice2.this.iDeviceAction);
                } else {
                    FtDevice2.this.showEmpty();
                }
            }
        });
        ((FtRoomVM) this.mViewModel).commonSceneResult.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.control.FtDevice2$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDevice2.this.lambda$startObserve$1((Resource) obj);
            }
        });
        ((FtRoomVM) this.mViewModel).freshDeviceData.observe(this, new Observer<Role>() { // from class: com.ltech.smarthome.ui.control.FtDevice2.7
            @Override // androidx.lifecycle.Observer
            public void onChanged(Role role) {
                if (((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice.getScrollState() == 0 && !((FtDevice2Binding) FtDevice2.this.mViewBinding).rvDevice.isComputingLayout() && ((FtRoomVM) FtDevice2.this.mViewModel).isRoomSelect(FtDevice2.this.roomId)) {
                    for (int i = 0; i < FtDevice2.this.deviceAdapter.getItemCount(); i++) {
                        Role role2 = (Role) FtDevice2.this.deviceAdapter.getItem(i);
                        if (role2 != null && role2.getObjectId() == role.getObjectId()) {
                            if ((role2 instanceof Device) && (role instanceof Device)) {
                                role2.setDeviceState(role.getDeviceState());
                            } else if ((role2 instanceof Group) && (role instanceof Group)) {
                                ((Group) role2).setGroupState(((Group) role).getGroupState());
                            }
                            if (role instanceof Device) {
                                Device device = (Device) role;
                                if (ProductId.CENTRAL_AIR_SUB_DEVICE.equals(device.getProductId())) {
                                    ((Device) role2).setParam(device.getParam());
                                }
                            }
                            FtDevice2.this.deviceAdapter.setData(i, role2);
                            return;
                        }
                    }
                }
            }
        });
        ((FtRoomVM) this.mViewModel).checkDevStatus.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.control.FtDevice2.8
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                if (((FtRoomVM) FtDevice2.this.mViewModel).isRoomSelect(FtDevice2.this.roomId)) {
                    FtDevice2.this.check();
                }
            }
        });
        ((FtRoomVM) this.mViewModel).editRoleMode.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.control.FtDevice2$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDevice2.this.lambda$startObserve$2((Boolean) obj);
            }
        });
        ((FtRoomVM) this.mViewModel).selectRoleList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.control.FtDevice2$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDevice2.this.lambda$startObserve$3((List) obj);
            }
        });
        ((FtRoomVM) this.mViewModel).selectRolePos.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.control.FtDevice2.9
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer integer) {
                if (!((FtRoomVM) FtDevice2.this.mViewModel).isRoomSelect(FtDevice2.this.roomId) || FtDevice2.this.deviceAdapter.getData().size() <= integer.intValue()) {
                    return;
                }
                FtDevice2.this.deviceAdapter.notifyItemChanged(integer.intValue());
            }
        });
        ((FtRoomVM) this.mViewModel).selectScenePos.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.control.FtDevice2.10
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer integer) {
                if (!((FtRoomVM) FtDevice2.this.mViewModel).isRoomSelect(FtDevice2.this.roomId) || FtDevice2.this.deviceAdapter.getData().size() <= integer.intValue()) {
                    return;
                }
                FtDevice2.this.deviceAdapter.notifyItemChanged(integer.intValue());
            }
        });
        ((FtRoomVM) this.mViewModel).editSceneMode.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.control.FtDevice2$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDevice2.this.lambda$startObserve$4((Boolean) obj);
            }
        });
        ((FtRoomVM) this.mViewModel).selectSceneList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.control.FtDevice2$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDevice2.this.lambda$startObserve$5((List) obj);
            }
        });
        ((FtRoomVM) this.mViewModel).editFinishEvent.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.control.FtDevice2.11
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean unused) {
                if (((FtRoomVM) FtDevice2.this.mViewModel).isRoomSelect(FtDevice2.this.roomId)) {
                    FtDevice2.this.curAllSceneList.clear();
                    FtDevice2.this.curAllRoleList.clear();
                    for (T t : FtDevice2.this.deviceAdapter.getData()) {
                        if (t instanceof Scene) {
                            FtDevice2.this.curAllSceneList.add((Scene) t);
                        } else if ((t instanceof Device) || (t instanceof Group)) {
                            FtDevice2.this.curAllRoleList.add(t);
                        }
                    }
                    ((FtRoomVM) FtDevice2.this.mViewModel).editFinish(FtDevice2.this.curAllRoleList, FtDevice2.this.curAllSceneList);
                }
            }
        });
        ((FtRoomVM) this.mViewModel).selectAllEvent.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.control.FtDevice2.12
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean unused) {
                if (((FtRoomVM) FtDevice2.this.mViewModel).isRoomSelect(FtDevice2.this.roomId)) {
                    ((FtRoomVM) FtDevice2.this.mViewModel).selectAll(FtDevice2.this.curAllRoleList, FtDevice2.this.curAllSceneList);
                }
            }
        });
        ((FtRoomVM) this.mViewModel).refreshAllDeviceEvent.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.control.FtDevice2.13
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean unused) {
                if (((FtRoomVM) FtDevice2.this.mViewModel).isRoomSelect(FtDevice2.this.roomId)) {
                    for (int i = 0; i < FtDevice2.this.deviceAdapter.getData().size(); i++) {
                        FtDevice2.this.deviceAdapter.notifyItemChanged(i);
                    }
                }
            }
        });
        ((FtRoomVM) this.mViewModel).refreshAllSceneEvent.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.control.FtDevice2.14
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean unused) {
                if (((FtRoomVM) FtDevice2.this.mViewModel).isRoomSelect(FtDevice2.this.roomId)) {
                    for (int i = 0; i < FtDevice2.this.deviceAdapter.getData().size(); i++) {
                        FtDevice2.this.deviceAdapter.notifyItemChanged(i);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Void r1) {
        showNoPermissionDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Resource resource) {
        ((FtRoomVM) this.mViewModel).filterSceneByRoom(this.roomId, this.iSceneAction);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Boolean bool) {
        if (((FtRoomVM) this.mViewModel).isRoomSelect(this.roomId)) {
            for (int i = 0; i < this.deviceAdapter.getData().size(); i++) {
                this.deviceAdapter.notifyItemChanged(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(List list) {
        if (((FtRoomVM) this.mViewModel).isRoomSelect(this.roomId)) {
            ((FtRoomVM) this.mViewModel).selectNumber.setValue(getString(R.string.already_select, Integer.valueOf(list.size())));
            ((FtRoomVM) this.mViewModel).isShowSelectAll.setValue(Boolean.valueOf(!list.isEmpty()));
            if (!list.isEmpty() && list.size() != this.curAllRoleList.size()) {
                ((FtRoomVM) this.mViewModel).selectAllText.setValue(getString(R.string.select_all_device));
            } else if (list.size() == this.curAllRoleList.size()) {
                ((FtRoomVM) this.mViewModel).selectAllText.setValue(getString(R.string.select_all_cancel));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Boolean bool) {
        if (((FtRoomVM) this.mViewModel).isRoomSelect(this.roomId)) {
            for (int i = 0; i < this.deviceAdapter.getData().size(); i++) {
                this.deviceAdapter.notifyItemChanged(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(List list) {
        if (((FtRoomVM) this.mViewModel).isRoomSelect(this.roomId)) {
            ((FtRoomVM) this.mViewModel).selectNumber.setValue(getString(R.string.already_select, Integer.valueOf(list.size())));
            ((FtRoomVM) this.mViewModel).isShowSelectAll.setValue(Boolean.valueOf(!list.isEmpty()));
            if (!list.isEmpty() && list.size() != this.curAllSceneList.size()) {
                ((FtRoomVM) this.mViewModel).selectAllText.setValue(getString(R.string.select_all_scene));
            } else if (list.size() == this.curAllSceneList.size()) {
                ((FtRoomVM) this.mViewModel).selectAllText.setValue(getString(R.string.select_all_cancel));
            }
        }
    }

    public void refreshGatewayData(Device device) {
        if (this.deviceAdapter != null) {
            for (int i = 0; i < this.deviceAdapter.getItemCount(); i++) {
                Role item = this.deviceAdapter.getItem(i);
                if ((item instanceof Device) && item.getObjectId() == device.getObjectId()) {
                    ((Device) item).setOnlineFlag(device.getOnlineFlag());
                    this.deviceAdapter.notifyItemChanged(i);
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void check() {
        RecyclerView.LayoutManager layoutManager = ((FtDevice2Binding) this.mViewBinding).rvDevice.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int findFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            int findLastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            if (findFirstVisibleItemPosition == -1 || findLastVisibleItemPosition == -1) {
                return;
            }
            ArrayList arrayList = new ArrayList();
            while (findFirstVisibleItemPosition <= findLastVisibleItemPosition) {
                Role item = this.deviceAdapter.getItem(findFirstVisibleItemPosition);
                if (item instanceof Device) {
                    arrayList.add(item);
                } else if (item instanceof Group) {
                    Group group = (Group) item;
                    if (!group.getDeviceIds().isEmpty()) {
                        if (ProductRepository.needCheckOnlineState(group)) {
                            arrayList.add(group);
                        } else {
                            List<Device> deviceByUnicastAddress = Injection.repo().device().getDeviceByUnicastAddress(group.getPlaceId(), group.getFirstDevUniAddr());
                            if (!deviceByUnicastAddress.isEmpty()) {
                                arrayList.add(deviceByUnicastAddress.get(0));
                            }
                        }
                    }
                }
                findFirstVisibleItemPosition++;
            }
            ((FtRoomVM) this.mViewModel).checkDeviceStatus(arrayList);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSceneItemByType(BaseViewHolder helper, Scene item) {
        if (item.getSceneType() == 1) {
            helper.setBackgroundRes(R.id.tv_type, R.drawable.shape_scene_cloud_bg).setText(R.id.tv_type, R.string.type_cloud).setTextColor(R.id.tv_type, getResources().getColor(R.color.color_text_blue));
        } else if (item.getSceneType() == 2) {
            helper.setBackgroundRes(R.id.tv_type, R.drawable.shape_scene_local_bg).setText(R.id.tv_type, R.string.type_local).setTextColor(R.id.tv_type, getResources().getColor(R.color.color_text_red));
        } else {
            helper.setBackgroundRes(R.id.tv_type, R.drawable.shape_scene_dali_bg).setText(R.id.tv_type, R.string.type_dali).setTextColor(R.id.tv_type, getResources().getColor(R.color.color_cgd_add));
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void showEmpty() {
        super.showEmpty();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void onEmptyTry() {
        super.onEmptyTry();
        ((FtRoomVM) this.mViewModel).addDevice(this.roomId);
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void onRetry() {
        super.onRetry();
        ((FtRoomVM) this.mViewModel).loadData();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.control.FtDevice2.17
            @Override // java.lang.Runnable
            public void run() {
                ((FtRoomVM) FtDevice2.this.mViewModel).filterDeviceByRoom(FtDevice2.this.roomId, FtDevice2.this.iDeviceAction);
            }
        }, 500L);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    private void showNoPermissionDialog() {
        MessageDialog.show((AppCompatActivity) getActivity(), getString(R.string.app_member_no_permission), getString(R.string.app_member_no_permission_tip)).setOkButton(getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.control.FtDevice2$$ExternalSyntheticLambda6
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return FtDevice2.lambda$showNoPermissionDialog$6(baseDialog, view);
            }
        });
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.OnDataChangeListener
    public void onDataOfflineChange(int deviceAddress) {
        List<Device> deviceByUnicastAddress = Injection.repo().device().getDeviceByUnicastAddress(((FtRoomVM) this.mViewModel).placeId, deviceAddress);
        List<Group> groupByDeviceUnicastAddress = Injection.repo().group().getGroupByDeviceUnicastAddress(((FtRoomVM) this.mViewModel).placeId, deviceAddress);
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(deviceByUnicastAddress);
        arrayList.addAll(groupByDeviceUnicastAddress);
        for (int i = 0; i < arrayList.size(); i++) {
            ((FtRoomVM) this.mViewModel).freshDeviceData.setValue((Role) arrayList.get(i));
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            Role role = (Role) arrayList.get(i2);
            if (role instanceof Device) {
                Device device = (Device) role;
                if (device.isSubDevice()) {
                    Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getMacdeviceid());
                    device.setOnlineFlag(deviceByDeviceId == null ? 1 : deviceByDeviceId.getOnlineFlag());
                    Injection.repo().device().saveDevice(device);
                    ((FtRoomVM) this.mViewModel).freshDeviceData.setValue(device);
                }
            } else if (role instanceof Group) {
                Iterator<Device> it = Injection.repo().device().getDevicesByIds(((Group) role).getDeviceIds()).iterator();
                while (it.hasNext()) {
                    ((FtRoomVM) this.mViewModel).freshDeviceData.setValue(it.next());
                }
            }
        }
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.OnDataChangeListener
    public void onGroupOfflineChange(int groupAddress) {
        List<Group> queryGroupByUnicastAddress = Injection.repo().group().queryGroupByUnicastAddress(((FtRoomVM) this.mViewModel).placeId, groupAddress);
        if (queryGroupByUnicastAddress != null) {
            for (Group group : queryGroupByUnicastAddress) {
                ((FtRoomVM) this.mViewModel).freshDeviceData.setValue(group);
                if (!group.getDeviceIds().isEmpty()) {
                    Iterator<Device> it = Injection.repo().device().getDevicesByIds(group.getDeviceIds()).iterator();
                    while (it.hasNext()) {
                        ((FtRoomVM) this.mViewModel).freshDeviceData.setValue(it.next());
                    }
                }
            }
        }
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.OnDataChangeListener
    public void onDeviceHide(Role role) {
        for (int itemCount = this.deviceAdapter.getItemCount() - 1; itemCount >= 0; itemCount--) {
            Role item = this.deviceAdapter.getItem(itemCount);
            if ((role instanceof Device) && (item instanceof Device)) {
                if (((Device) item).getDeviceId() == ((Device) role).getDeviceId()) {
                    this.deviceAdapter.remove(itemCount);
                    return;
                }
            } else if ((role instanceof Group) && (item instanceof Group) && ((Group) item).getGroupId() == ((Group) role).getGroupId()) {
                this.deviceAdapter.remove(itemCount);
                return;
            }
        }
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.OnDataChangeListener
    public void onDataDelete(Role role) {
        for (int itemCount = this.deviceAdapter.getItemCount() - 1; itemCount >= 0; itemCount--) {
            Role item = this.deviceAdapter.getItem(itemCount);
            if ((role instanceof Device) && (item instanceof Device)) {
                if (((Device) item).getDeviceId() == ((Device) role).getDeviceId()) {
                    this.deviceAdapter.remove(itemCount);
                    return;
                }
            } else if ((role instanceof Group) && (item instanceof Group) && ((Group) item).getGroupId() == ((Group) role).getGroupId()) {
                this.deviceAdapter.remove(itemCount);
                return;
            }
        }
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.OnDataChangeListener
    public void onDataChange(Group sub) {
        for (final int itemCount = this.deviceAdapter.getItemCount() - 1; itemCount >= 0; itemCount--) {
            Role item = this.deviceAdapter.getItem(itemCount);
            if (item instanceof Group) {
                Group group = (Group) item;
                if (group.getGroupId() == sub.getGroupId()) {
                    group.getGroupState().setOn(sub.getGroupState().isOn());
                    if (!((FtDevice2Binding) this.mViewBinding).rvDevice.isComputingLayout()) {
                        this.deviceAdapter.notifyItemChanged(itemCount);
                        return;
                    } else {
                        ((FtDevice2Binding) this.mViewBinding).rvDevice.post(new Runnable() { // from class: com.ltech.smarthome.ui.control.FtDevice2.18
                            @Override // java.lang.Runnable
                            public void run() {
                                FtDevice2.this.deviceAdapter.notifyItemChanged(itemCount);
                            }
                        });
                        return;
                    }
                }
            }
        }
    }
}