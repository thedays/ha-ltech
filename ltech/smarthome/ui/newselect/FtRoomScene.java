package com.ltech.smarthome.ui.newselect;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.google.common.primitives.Longs;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.databinding.FtDeviceAndGroupBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.ui.control.ActHome;
import com.ltech.smarthome.ui.control.FtSceneVM;
import com.ltech.smarthome.ui.device.dalipro.ActCgdProLight;
import com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSetting;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.device.dalipro.FtDaliScene;
import com.ltech.smarthome.ui.newselect.FtRoomScene;
import com.ltech.smarthome.ui.scene.ActAddCloudScene;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.ui.scene.local.ActAddLocalScene;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RoomPickHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class FtRoomScene extends BaseNormalFragment<FtDeviceAndGroupBinding> {
    public static final int TYPE_DALI_SCENE_LIST = 4;
    public static final int TYPE_DALI_SCENE_SELECT = 5;
    public static final int TYPE_SCENE_LIST = 1;
    public static final int TYPE_SELECT_SCENE = 2;
    public static final int TYPE_SORT_SCENE = 3;
    public BaseRoomSceneActivity activity;
    private long floorId;
    private FtRoomSceneVM mViewModel;
    private long macDeviceId;
    private long placeId;
    private long[] relateSceneIds;
    private long roomId;
    private MultipleItemRvAdapter<Scene, BaseViewHolder> sceneAdapter;
    private BaseItemDraggableAdapter<Scene, BaseViewHolder> sceneDraggableAdapter;
    private int selMax;
    public SingleLiveEvent<Integer> sortType = new SingleLiveEvent<>(0);
    private MutableLiveData<List<Scene>> sceneDataEvent = new MutableLiveData<>();
    public SingleLiveEvent<Void> refreshList = new SingleLiveEvent<>();
    public List<Scene> sceneList = new ArrayList();
    private RoomPickHelper roomPickHelper = new RoomPickHelper();
    public int listType = 1;

    static /* synthetic */ boolean lambda$showNoPermissionDialog$8(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_device_and_group;
    }

    public static FtRoomScene newInstance(long placeId, long roomId, long floorId) {
        FtRoomScene ftRoomScene = new FtRoomScene();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.PLACE_ID, placeId);
        bundle.putLong(Constants.ROOM_ID, roomId);
        bundle.putLong(Constants.FLOOR_ID, floorId);
        ftRoomScene.setArguments(bundle);
        return ftRoomScene;
    }

    public static FtRoomScene newInstance(long placeId, long roomId, long floorId, long[] relateSceneIds) {
        FtRoomScene ftRoomScene = new FtRoomScene();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.PLACE_ID, placeId);
        bundle.putLong(Constants.ROOM_ID, roomId);
        bundle.putLong(Constants.FLOOR_ID, floorId);
        bundle.putLongArray(Constants.SCENE_ID_ARRAY, relateSceneIds);
        ftRoomScene.setArguments(bundle);
        return ftRoomScene;
    }

    public static FtRoomScene newInstance(long placeId, long roomId, long floorId, long deviceId) {
        FtRoomScene ftRoomScene = new FtRoomScene();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.PLACE_ID, placeId);
        bundle.putLong(Constants.ROOM_ID, roomId);
        bundle.putLong(Constants.FLOOR_ID, floorId);
        bundle.putLong("device_id", deviceId);
        ftRoomScene.setArguments(bundle);
        return ftRoomScene;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyImageRes(R.mipmap.pic_empty_1).emptyStringRes(R.string.no_scence).emptyTryStringRes(R.string.add_group1));
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void onEmptyTry() {
        super.onEmptyTry();
        this.mViewModel.addSceneEvent.call();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initData() {
        super.initData();
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.placeId = arguments.getLong(Constants.PLACE_ID);
            this.floorId = arguments.getLong(Constants.FLOOR_ID);
            this.roomId = arguments.getLong(Constants.ROOM_ID);
            this.relateSceneIds = arguments.getLongArray(Constants.SCENE_ID_ARRAY);
            this.macDeviceId = arguments.getLong("device_id");
        }
        this.roomPickHelper.startObserve(this, this.placeId, -1L);
        if (ActivityUtils.getTopActivity() instanceof BaseRoomSceneActivity) {
            BaseRoomSceneActivity baseRoomSceneActivity = (BaseRoomSceneActivity) ActivityUtils.getTopActivity();
            this.activity = baseRoomSceneActivity;
            this.listType = baseRoomSceneActivity.listType;
            this.selMax = this.activity.getIntent().getIntExtra(Constants.MAX, -1);
        } else {
            this.activity = null;
        }
        if (this.macDeviceId > 0) {
            this.mViewModel = (FtRoomSceneVM) new ViewModelProvider(FtDaliScene.getObj()).get(FtRoomSceneVM.class);
            if (ActivityUtils.getTopActivity() instanceof ActCgdProLight) {
                this.listType = 4;
            } else {
                this.listType = 5;
            }
            getDaliData();
            return;
        }
        if (ActivityUtils.getTopActivity() instanceof ActHome) {
            if (getParentFragment() != null) {
                this.mViewModel = (FtRoomSceneVM) new ViewModelProvider(getParentFragment()).get(FtSceneVM.class);
            }
        } else if (getActivity() != null) {
            this.mViewModel = (FtRoomSceneVM) new ViewModelProvider(getActivity()).get(FtRoomSceneVM.class);
        }
        getData();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        if (this.listType == 3) {
            initSceneDraggableAdapter();
        } else {
            initSceneAdapter();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.macDeviceId == 0) {
            getData();
        }
    }

    private void initSceneDraggableAdapter() {
        BaseItemDraggableAdapter<Scene, BaseViewHolder> baseItemDraggableAdapter = new BaseItemDraggableAdapter<Scene, BaseViewHolder>(R.layout.item_scene_new, this.sceneList) { // from class: com.ltech.smarthome.ui.newselect.FtRoomScene.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Scene item) {
                helper.setText(R.id.tv_name, item.getSceneName()).setImageResource(R.id.appCompatImageView14, SceneHelper.getSceneIcon(ActivityUtils.getTopActivity(), item.getIconPos())).setImageResource(R.id.iv_edit, R.mipmap.ic_item_sort);
                FtRoomScene.this.showSceneItemByType(helper, item);
            }
        };
        this.sceneDraggableAdapter = baseItemDraggableAdapter;
        baseItemDraggableAdapter.bindToRecyclerView(((FtDeviceAndGroupBinding) this.mViewBinding).rvDevice);
        ((FtDeviceAndGroupBinding) this.mViewBinding).rvDevice.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.newselect.FtRoomScene.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
            }
        });
        ((FtDeviceAndGroupBinding) this.mViewBinding).rvDevice.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ((FtDeviceAndGroupBinding) this.mViewBinding).rvDevice.setHasFixedSize(true);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemDragAndSwipeCallback(this.sceneDraggableAdapter));
        itemTouchHelper.attachToRecyclerView(((FtDeviceAndGroupBinding) this.mViewBinding).rvDevice);
        this.sceneDraggableAdapter.enableDragItem(itemTouchHelper);
    }

    private void initSceneAdapter() {
        MultipleItemRvAdapter<Scene, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<Scene, BaseViewHolder>(this.sceneList) { // from class: com.ltech.smarthome.ui.newselect.FtRoomScene.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(Scene role) {
                return FtRoomScene.this.listType;
            }

            /* renamed from: com.ltech.smarthome.ui.newselect.FtRoomScene$3$1, reason: invalid class name */
            class AnonymousClass1 extends BaseItemProvider<Scene, BaseViewHolder> {
                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int layout() {
                    return R.layout.item_scene_new;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int viewType() {
                    return 1;
                }

                AnonymousClass1() {
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void convert(BaseViewHolder helper, final Scene item, int position) {
                    helper.setText(R.id.tv_name, item.getSceneName()).setImageResource(R.id.appCompatImageView14, SceneHelper.getSceneIcon(ActivityUtils.getTopActivity(), item.getIconPos()));
                    FtRoomScene.this.showSceneItemByType(helper, item);
                    helper.getView(R.id.iv_edit).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.newselect.FtRoomScene$3$1$$ExternalSyntheticLambda3
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            FtRoomScene.AnonymousClass3.AnonymousClass1.this.lambda$convert$0(item, view);
                        }
                    });
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$convert$0(Scene scene, View view) {
                    if (FtRoomScene.this.getCurrentPlace().isOwner() || FtRoomScene.this.getCurrentPlace().isManager()) {
                        Injection.limiter().reset(Injection.keyCreator().sceneContentKey(scene.getSceneId()));
                        if (scene.getSceneType() == 2) {
                            NavUtils.destination(ActAddLocalScene.class).withLong(Constants.PLACE_ID, FtRoomScene.this.placeId).withInt(Constants.ICON_POSITION, scene.getIconPos()).withString(Constants.SCENE_NAME, scene.getSceneName()).withInt(Constants.SCENE_NUM, scene.getSceneNum()).withLong(Constants.SCENE_ID, scene.getSceneId()).navigation(FtRoomScene.this.getActivity());
                            return;
                        } else if (scene.getSceneType() == 4) {
                            NavUtils.destination(ActDaliSceneSetting.class).withLong(Constants.PLACE_ID, FtRoomScene.this.placeId).withLong(Constants.SCENE_ID, scene.getSceneId()).navigation(FtRoomScene.this.getActivity());
                            return;
                        } else {
                            NavUtils.destination(ActAddCloudScene.class).withLong(Constants.PLACE_ID, FtRoomScene.this.placeId).withInt(Constants.ICON_POSITION, scene.getIconPos()).withString(Constants.SCENE_NAME, scene.getSceneName()).withLong(Constants.SCENE_ID, scene.getSceneId()).navigation(FtRoomScene.this.getActivity());
                            return;
                        }
                    }
                    FtRoomScene.this.showNoPermissionDialog();
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void onClick(BaseViewHolder helper, Scene scene, int position) {
                    if (scene.getSceneType() == 2) {
                        CmdAssistant.getSceneCmdAssistant(null, new int[0]).executeLocalScene(FtRoomScene.this.getActivity(), scene.getSceneNum());
                        FtRoomScene.this.showSuccessDialog(FtRoomScene.this.getString(R.string.execute_success));
                    } else {
                        if (scene.getSceneType() == 4) {
                            CmdAssistant.getSceneCmdAssistant(Injection.repo().device().getDeviceByDeviceId(scene.getMacdeviceid()), new int[0]).executeDaliScene(FtRoomScene.this.getActivity(), DaliProHelper.getSceneNum(scene));
                            FtRoomScene.this.showSuccessDialog(FtRoomScene.this.getString(R.string.execute_success));
                            return;
                        }
                        LHomeLog.i(getClass(), "场景执行 ID " + scene.getSceneId());
                        ((ObservableSubscribeProxy) Injection.net().executeScene(scene.getSceneId()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.newselect.FtRoomScene$3$1$$ExternalSyntheticLambda0
                            @Override // io.reactivex.functions.Consumer
                            public final void accept(Object obj) {
                                FtRoomScene.AnonymousClass3.AnonymousClass1.this.lambda$onClick$1((Disposable) obj);
                            }
                        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(FtRoomScene.this.getLifecycle(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.newselect.FtRoomScene$3$1$$ExternalSyntheticLambda1
                            @Override // io.reactivex.functions.Consumer
                            public final void accept(Object obj) {
                                FtRoomScene.AnonymousClass3.AnonymousClass1.this.lambda$onClick$2(obj);
                            }
                        }, new Consumer() { // from class: com.ltech.smarthome.ui.newselect.FtRoomScene$3$1$$ExternalSyntheticLambda2
                            @Override // io.reactivex.functions.Consumer
                            public final void accept(Object obj) {
                                FtRoomScene.AnonymousClass3.AnonymousClass1.this.lambda$onClick$3((Throwable) obj);
                            }
                        });
                    }
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$onClick$1(Disposable disposable) throws Exception {
                    FtRoomScene.this.showLoadingDialog(FtRoomScene.this.getString(R.string.executing));
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$onClick$2(Object obj) throws Exception {
                    FtRoomScene.this.showSuccessDialog(FtRoomScene.this.getString(R.string.execute_success));
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$onClick$3(Throwable th) throws Exception {
                    FtRoomScene.this.showErrorDialog(FtRoomScene.this.getString(R.string.execute_fail));
                }
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new AnonymousClass1());
                this.mProviderDelegate.registerProvider(new BaseItemProvider<Scene, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.newselect.FtRoomScene.3.2
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_scene_new;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 2;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, Scene item, int position) {
                        helper.setText(R.id.tv_name, item.getSceneName()).setImageResource(R.id.appCompatImageView14, SceneHelper.getSceneIcon(ActivityUtils.getTopActivity(), item.getIconPos())).setGone(R.id.iv_edit, false).setGone(R.id.iv_select, true).setImageResource(R.id.iv_select, FtRoomScene.this.activity.selectSceneList.contains(item) ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default).setGone(R.id.tv_type, true);
                        FtRoomScene.this.showSceneItemByType(helper, item);
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void onClick(BaseViewHolder helper, Scene data, int position) {
                        if (FtRoomScene.this.activity.allowMultiSelect) {
                            if (FtRoomScene.this.activity.selectSceneList.contains(data)) {
                                FtRoomScene.this.activity.selectSceneList.remove(data);
                            } else {
                                if (!FtRoomScene.this.activity.isMatter() && FtRoomScene.this.selMax > 0 && FtRoomScene.this.activity.selectSceneList.size() >= FtRoomScene.this.selMax) {
                                    SmartToast.showCenterShort(String.format(FtRoomScene.this.getString(R.string.app_str_gqpro_syning_num_tip), Integer.valueOf(FtRoomScene.this.selMax), FtRoomScene.this.getString(R.string.scenes)));
                                    return;
                                }
                                FtRoomScene.this.activity.selectSceneList.add(data);
                            }
                            FtRoomScene.this.activity.selectCountLiveData.setValue(Integer.valueOf(FtRoomScene.this.activity.selectSceneList.size()));
                        } else {
                            FtRoomScene.this.activity.selectSceneList.clear();
                            FtRoomScene.this.activity.selectSceneList.add(data);
                        }
                        FtRoomScene.this.refreshList.call();
                        FtRoomScene.this.activity.sceneClick(data);
                    }
                });
                this.mProviderDelegate.registerProvider(new C01643());
                this.mProviderDelegate.registerProvider(new BaseItemProvider<Scene, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.newselect.FtRoomScene.3.4
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_scene_new;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 5;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, Scene item, int position) {
                        helper.setText(R.id.tv_name, item.getSceneName()).setImageResource(R.id.appCompatImageView14, SceneHelper.getSceneIcon(ActivityUtils.getTopActivity(), item.getIconPos())).setGone(R.id.iv_edit, false).setGone(R.id.iv_select, true).setImageResource(R.id.iv_select, FtRoomScene.this.mViewModel.selectSceneList.contains(item) ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default).setBackgroundRes(R.id.tv_type, R.drawable.shape_scene_dali_bg).setText(R.id.tv_type, String.valueOf(item.getSceneNum() != 0 ? item.getSceneNum() - 1 : 0)).setTextColor(R.id.tv_type, FtRoomScene.this.getResources().getColor(R.color.color_cgd_add));
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void onClick(BaseViewHolder helper, Scene data, int position) {
                        if (FtRoomScene.this.mViewModel.allowMultiSelect) {
                            if (FtRoomScene.this.mViewModel.selectSceneList.contains(data)) {
                                FtRoomScene.this.mViewModel.selectSceneList.remove(data);
                            } else {
                                if (FtRoomScene.this.selMax > 0 && FtRoomScene.this.activity.selectSceneList.size() >= FtRoomScene.this.selMax) {
                                    SmartToast.showCenterShort(String.format(FtRoomScene.this.getString(R.string.app_str_gqpro_syning_num_tip), Integer.valueOf(FtRoomScene.this.selMax), FtRoomScene.this.getString(R.string.scenes)));
                                    return;
                                }
                                FtRoomScene.this.mViewModel.selectSceneList.add(data);
                            }
                            FtRoomScene.this.mViewModel.selectCountLiveData.setValue(Integer.valueOf(FtRoomScene.this.mViewModel.selectSceneList.size()));
                        } else {
                            FtRoomScene.this.mViewModel.selectSceneList.clear();
                            FtRoomScene.this.mViewModel.selectSceneList.add(data);
                        }
                        FtRoomScene.this.refreshList.call();
                    }
                });
            }

            /* renamed from: com.ltech.smarthome.ui.newselect.FtRoomScene$3$3, reason: invalid class name and collision with other inner class name */
            class C01643 extends BaseItemProvider<Scene, BaseViewHolder> {
                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int layout() {
                    return R.layout.item_scene_new;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int viewType() {
                    return 4;
                }

                C01643() {
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void convert(BaseViewHolder helper, final Scene item, int position) {
                    helper.setText(R.id.tv_name, item.getSceneName()).setImageResource(R.id.appCompatImageView14, SceneHelper.getSceneIcon(ActivityUtils.getTopActivity(), item.getIconPos())).setBackgroundRes(R.id.tv_type, R.drawable.shape_scene_dali_bg).setText(R.id.tv_type, String.valueOf(item.getSceneNum() == 0 ? 0 : item.getSceneNum() - 1)).setTextColor(R.id.tv_type, FtRoomScene.this.getResources().getColor(R.color.color_cgd_add));
                    helper.getView(R.id.iv_edit).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.newselect.FtRoomScene$3$3$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            FtRoomScene.AnonymousClass3.C01643.this.lambda$convert$0(item, view);
                        }
                    });
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$convert$0(Scene scene, View view) {
                    if (FtRoomScene.this.getCurrentPlace().isOwner() || FtRoomScene.this.getCurrentPlace().isManager()) {
                        Injection.limiter().reset(Injection.keyCreator().sceneContentKey(scene.getSceneId()));
                        NavUtils.destination(ActDaliSceneSetting.class).withLong(Constants.PLACE_ID, FtRoomScene.this.placeId).withLong(Constants.SCENE_ID, scene.getSceneId()).navigation(FtRoomScene.this.getActivity());
                    } else {
                        FtRoomScene.this.showNoPermissionDialog();
                    }
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void onClick(BaseViewHolder helper, Scene scene, int position) {
                    CmdAssistant.getSceneCmdAssistant(Injection.repo().device().getDeviceByDeviceId(scene.getMacdeviceid()), new int[0]).executeDaliScene(FtRoomScene.this.getActivity(), scene.getSceneNum());
                    FtRoomScene.this.showSuccessDialog(FtRoomScene.this.getString(R.string.execute_success));
                }
            }
        };
        this.sceneAdapter = multipleItemRvAdapter;
        multipleItemRvAdapter.finishInitialize();
        this.sceneAdapter.bindToRecyclerView(((FtDeviceAndGroupBinding) this.mViewBinding).rvDevice);
        ((FtDeviceAndGroupBinding) this.mViewBinding).rvDevice.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.newselect.FtRoomScene.4
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
            }
        });
        ((FtDeviceAndGroupBinding) this.mViewBinding).rvDevice.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ((DefaultItemAnimator) ((FtDeviceAndGroupBinding) this.mViewBinding).rvDevice.getItemAnimator()).setSupportsChangeAnimations(false);
        ((FtDeviceAndGroupBinding) this.mViewBinding).rvDevice.setHasFixedSize(true);
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
    protected void startObserve() {
        this.sceneDataEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.newselect.FtRoomScene$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtRoomScene.this.lambda$startObserve$0((List) obj);
            }
        });
        this.refreshList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.newselect.FtRoomScene$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtRoomScene.this.lambda$startObserve$1((Void) obj);
            }
        });
        this.sortType.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.newselect.FtRoomScene$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtRoomScene.this.lambda$startObserve$2((Integer) obj);
            }
        });
        this.mViewModel.addSceneEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.newselect.FtRoomScene$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtRoomScene.this.lambda$startObserve$3((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(List list) {
        this.sceneList.clear();
        this.sceneList.addAll(list);
        BaseRoomSceneActivity baseRoomSceneActivity = this.activity;
        if (baseRoomSceneActivity != null && baseRoomSceneActivity.selectFt == this) {
            this.activity.allSceneData.clear();
            this.activity.allSceneData.addAll(list);
            this.activity.selectCountLiveData.setValue(Integer.valueOf(this.activity.selectSceneList.size()));
        } else {
            FtRoomSceneVM ftRoomSceneVM = this.mViewModel;
            if (ftRoomSceneVM != null) {
                ftRoomSceneVM.allSceneData.clear();
                this.mViewModel.allSceneData.addAll(this.sceneList);
                this.mViewModel.selectCountLiveData.setValue(Integer.valueOf(this.mViewModel.selectSceneList.size()));
            }
        }
        if (list.size() > 0) {
            showContent();
            sortList(this.sortType.getValue().intValue());
            return;
        }
        showEmpty();
        if (this.listType == 3) {
            this.sceneDraggableAdapter.replaceData(this.sceneList);
        } else {
            this.sceneAdapter.replaceData(this.sceneList);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r2) {
        if (this.listType == 3) {
            this.sceneDraggableAdapter.notifyDataSetChanged();
        } else {
            this.sceneAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Integer num) {
        sortList(num.intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r2) {
        Place place = (Place) SharedPreferenceUtil.getBean(Constants.SELECT_PLACE, Place.class);
        if (place.isOwner() || place.isManager()) {
            this.mViewModel.showEditNameDialog(this.roomPickHelper);
        } else {
            showNoPermissionDialog();
        }
    }

    public void getData(String keyword) {
        loadData(this.roomId, this.floorId, keyword);
    }

    public void getData() {
        loadData(this.roomId, this.floorId, "");
    }

    private void loadData(long roomId, long floorId, String keyword) {
        ArrayList arrayList = new ArrayList();
        for (Scene scene : Injection.repo().scene().getSceneListByRoomId(this.placeId, floorId, roomId, new boolean[0])) {
            BaseRoomSceneActivity baseRoomSceneActivity = this.activity;
            if (baseRoomSceneActivity != null) {
                if (!baseRoomSceneActivity.filterScene(scene) || !checkSceneType(scene) || !this.activity.filterDaliScene(scene)) {
                    r14 = false;
                }
            } else {
                long[] jArr = this.relateSceneIds;
                if (jArr != null) {
                    r14 = Longs.asList(jArr).contains(Long.valueOf(scene.getSceneId()));
                } else if (scene.getSceneType() == 4) {
                    r14 = Injection.repo().device().getDeviceByDeviceId(scene.getMacdeviceid()) != null;
                    if (r14) {
                        r14 = DaliProHelper.isSceneVisible(scene);
                    }
                }
            }
            if (r14) {
                if (keyword.length() > 0) {
                    if (scene.getSceneName().toLowerCase().contains(keyword.toLowerCase())) {
                        arrayList.add(scene);
                    }
                } else {
                    arrayList.add(scene);
                }
            }
        }
        this.sceneDataEvent.setValue(arrayList);
    }

    public void getDaliData() {
        loadDaliData(this.roomId, this.floorId, "");
    }

    private void loadDaliData(long roomId, long floorId, String keyword) {
        ArrayList arrayList = new ArrayList();
        for (Scene scene : Injection.repo().scene().getSceneListByRoomId(this.placeId, floorId, roomId, this.macDeviceId)) {
            if (keyword.length() > 0) {
                if (scene.getSceneName().toLowerCase().contains(keyword.toLowerCase())) {
                    arrayList.add(scene);
                }
            } else {
                arrayList.add(scene);
            }
        }
        this.sceneDataEvent.setValue(arrayList);
    }

    private boolean checkSceneType(Scene scene) {
        if (this.activity.selectSceneType == scene.getSceneType()) {
            return true;
        }
        if (this.activity.selectSceneType == 3) {
            return (this.activity.isLocalAutomation && scene.getSceneType() == 1) ? false : true;
        }
        return false;
    }

    private void sortList(final int type) {
        if (this.sceneList.isEmpty()) {
            return;
        }
        int i = this.listType;
        if (i == 2) {
            if (type == 0 || type == 1) {
                Collections.sort(this.sceneList, new Comparator() { // from class: com.ltech.smarthome.ui.newselect.FtRoomScene$$ExternalSyntheticLambda4
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return FtRoomScene.lambda$sortList$4(type, (Scene) obj, (Scene) obj2);
                    }
                });
            } else {
                Collections.sort(this.sceneList, new Comparator() { // from class: com.ltech.smarthome.ui.newselect.FtRoomScene$$ExternalSyntheticLambda5
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return FtRoomScene.lambda$sortList$5(type, (Scene) obj, (Scene) obj2);
                    }
                });
            }
        } else if (i == 4 || i == 5) {
            Collections.sort(this.sceneList, new Comparator() { // from class: com.ltech.smarthome.ui.newselect.FtRoomScene$$ExternalSyntheticLambda6
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return FtRoomScene.lambda$sortList$6((Scene) obj, (Scene) obj2);
                }
            });
        } else {
            Collections.sort(this.sceneList, new Comparator() { // from class: com.ltech.smarthome.ui.newselect.FtRoomScene$$ExternalSyntheticLambda7
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return FtRoomScene.lambda$sortList$7((Scene) obj, (Scene) obj2);
                }
            });
        }
        if (this.listType == 3) {
            BaseItemDraggableAdapter<Scene, BaseViewHolder> baseItemDraggableAdapter = this.sceneDraggableAdapter;
            if (baseItemDraggableAdapter != null) {
                baseItemDraggableAdapter.replaceData(this.sceneList);
                return;
            }
            return;
        }
        MultipleItemRvAdapter<Scene, BaseViewHolder> multipleItemRvAdapter = this.sceneAdapter;
        if (multipleItemRvAdapter != null) {
            multipleItemRvAdapter.replaceData(this.sceneList);
        }
    }

    static /* synthetic */ int lambda$sortList$4(int i, Scene scene, Scene scene2) {
        if (scene.getCreateTime() == null || scene2.getCreateTime() == null) {
            return 0;
        }
        if (i == 0) {
            return scene.getCreateTime().compareTo(scene2.getCreateTime());
        }
        return scene2.getCreateTime().compareTo(scene.getCreateTime());
    }

    static /* synthetic */ int lambda$sortList$5(int i, Scene scene, Scene scene2) {
        Collator collator = Collator.getInstance(Locale.CHINA);
        String sceneName = scene.getSceneName();
        String sceneName2 = scene2.getSceneName();
        if (sceneName == null || sceneName2 == null) {
            return 0;
        }
        if (i == 2) {
            return collator.compare(sceneName, sceneName2);
        }
        return collator.compare(sceneName2, sceneName);
    }

    static /* synthetic */ int lambda$sortList$6(Scene scene, Scene scene2) {
        return scene.getSceneNum() - scene2.getSceneNum();
    }

    static /* synthetic */ int lambda$sortList$7(Scene scene, Scene scene2) {
        if ((scene.getSceneType() == 4 || scene2.getSceneType() == 4) && scene.getSceneType() != scene2.getSceneType()) {
            return scene.getSceneType() - scene2.getSceneType();
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showNoPermissionDialog() {
        MessageDialog.show((AppCompatActivity) getActivity(), getString(R.string.app_member_no_permission), getString(R.string.app_member_no_permission_tip)).setOkButton(getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.newselect.FtRoomScene$$ExternalSyntheticLambda8
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return FtRoomScene.lambda$showNoPermissionDialog$8(baseDialog, view);
            }
        });
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }
}