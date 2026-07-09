package com.ltech.smarthome.ui.scene;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.databinding.ActAddCloudSceneBinding;
import com.ltech.smarthome.databinding.ItemTextAddBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.ResourceEmptyLiveData;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.scene_param.AutomationParam;
import com.ltech.smarthome.model.scene_param.DeviceParam;
import com.ltech.smarthome.model.scene_param.GroupParam;
import com.ltech.smarthome.model.scene_param.SceneParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.device.cg485.Cg485Helper;
import com.ltech.smarthome.ui.scene.ActAddCloudScene;
import com.ltech.smarthome.ui.scene.ActAddCloudSceneVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.RoomSelectDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class ActAddCloudScene extends VMActivity<ActAddCloudSceneBinding, ActAddCloudSceneVM> {
    private BaseItemDraggableAdapter<Scene.SceneContent, BaseViewHolder> mAdapter;
    private ItemTextAddBinding textAddBinding;
    private boolean isFirstIn = true;
    private boolean editMode = false;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_add_cloud_scene;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditString("");
        ((ActAddCloudSceneVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActAddCloudSceneVM) this.mViewModel).floorId = getIntent().getLongExtra(Constants.FLOOR_ID, -1L);
        ((ActAddCloudSceneVM) this.mViewModel).roomId = getIntent().getLongExtra(Constants.ROOM_ID, -1L);
        ((ActAddCloudSceneVM) this.mViewModel).sceneId = getIntent().getLongExtra(Constants.SCENE_ID, 0L);
        ((ActAddCloudSceneVM) this.mViewModel).sceneName.setValue(getIntent().getStringExtra(Constants.SCENE_NAME));
        ((ActAddCloudSceneVM) this.mViewModel).isEdit = ((ActAddCloudSceneVM) this.mViewModel).sceneId > 0;
        if (((ActAddCloudSceneVM) this.mViewModel).sceneId > 0) {
            ((ActAddCloudSceneVM) this.mViewModel).scene = Injection.repo().scene().getSceneBySceneId(((ActAddCloudSceneVM) this.mViewModel).sceneId);
            ((ActAddCloudSceneVM) this.mViewModel).floorId = ((ActAddCloudSceneVM) this.mViewModel).scene.getFloorId();
            ((ActAddCloudSceneVM) this.mViewModel).roomId = ((ActAddCloudSceneVM) this.mViewModel).scene.getRoomId();
            ((ActAddCloudSceneBinding) this.mViewBinding).sbAddToCommon.setChecked(((ActAddCloudSceneVM) this.mViewModel).scene.isCommon());
        }
        ((ActAddCloudSceneBinding) this.mViewBinding).tvRoomName.setText(Injection.repo().home().getRoomName(((ActAddCloudSceneVM) this.mViewModel).floorId, ((ActAddCloudSceneVM) this.mViewModel).roomId));
        initActionRv();
        setTitle(getString(R.string.app_str_edit_cloud_scene));
        if (((ActAddCloudSceneVM) this.mViewModel).isEdit) {
            ((ActAddCloudSceneVM) this.mViewModel).sceneIconPos.setValue(Integer.valueOf(getIntent().getIntExtra(Constants.ICON_POSITION, 0)));
        }
        ((ActAddCloudSceneBinding) this.mViewBinding).sbAddToCommon.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda19
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                ActAddCloudScene.this.lambda$initView$2(switchButton, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(SwitchButton switchButton, final boolean z) {
        ((ObservableSubscribeProxy) Injection.net().setCommonScene(((ActAddCloudSceneVM) this.mViewModel).sceneId, z).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycle(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda21
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddCloudScene.this.lambda$initView$0(z, obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda22
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddCloudScene.this.lambda$initView$1(z, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(boolean z, Object obj) throws Exception {
        ((ActAddCloudSceneVM) this.mViewModel).scene.setCommon(z);
        Injection.repo().scene().saveScene(((ActAddCloudSceneVM) this.mViewModel).scene);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(boolean z, Throwable th) throws Exception {
        ((ActAddCloudSceneBinding) this.mViewBinding).sbAddToCommon.setCheckedNotByUser(!z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r1) {
        showEditNameDialog();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActAddCloudSceneVM) this.mViewModel).showEditDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda24
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddCloudScene.this.lambda$startObserve$3((Void) obj);
            }
        });
        ((ActAddCloudSceneVM) this.mViewModel).showEditRoomDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddCloudScene.this.lambda$startObserve$4((Void) obj);
            }
        });
        ((ActAddCloudSceneVM) this.mViewModel).showDelRoomDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddCloudScene.this.lambda$startObserve$5((Void) obj);
            }
        });
        ((ActAddCloudSceneVM) this.mViewModel).sceneIconPos.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddCloudScene.this.lambda$startObserve$6((Integer) obj);
            }
        });
        ((ActAddCloudSceneVM) this.mViewModel).roomPickHelper.startObserve(this, ((ActAddCloudSceneVM) this.mViewModel).placeId, ((ActAddCloudSceneVM) this.mViewModel).roomId);
        showLoadingDialog("");
        ((ActAddCloudSceneVM) this.mViewModel).request = Injection.repo().auto().getAutomationList(this, ((ActAddCloudSceneVM) this.mViewModel).placeId);
        handleData(((ActAddCloudSceneVM) this.mViewModel).request, new IAction() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActAddCloudScene.this.lambda$startObserve$7((List) obj);
            }
        });
        if (((ActAddCloudSceneVM) this.mViewModel).isEdit) {
            handleData(Transformations.switchMap(((ActAddCloudSceneVM) this.mViewModel).request.data(), new Function1() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda6
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LiveData lambda$startObserve$8;
                    lambda$startObserve$8 = ActAddCloudScene.this.lambda$startObserve$8((Resource) obj);
                    return lambda$startObserve$8;
                }
            }), new IAction() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda7
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActAddCloudScene.this.lambda$startObserve$9((List) obj);
                }
            });
        } else {
            initDataView(null, false);
            showContent();
        }
        ((ActAddCloudSceneVM) this.mViewModel).noSetActionDeviceEvent.observe(this, new Observer<List<ActAddCloudSceneVM.NoActionDevice>>() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<ActAddCloudSceneVM.NoActionDevice> devices) {
                MessageDialog.show(ActAddCloudScene.this.activity, ActAddCloudScene.this.getString(R.string.app_str_local_scene_device_no_action), "").setOkButton(ActAddCloudScene.this.getString(R.string.ok)).setCustomView(ActAddCloudScene.this.getCustomView(devices)).setCancelable(false);
            }
        });
        ((ActAddCloudSceneVM) this.mViewModel).showUnSupportDeviceDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddCloudScene.this.lambda$startObserve$10((List) obj);
            }
        });
        ((ActAddCloudSceneVM) this.mViewModel).totalSceneTime.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda9
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddCloudScene.this.lambda$startObserve$11((Integer) obj);
            }
        });
        ((ActAddCloudSceneVM) this.mViewModel).editActionEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda10
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddCloudScene.this.lambda$startObserve$12((Void) obj);
            }
        });
        ((ActAddCloudSceneVM) this.mViewModel).importEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda25
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddCloudScene.this.lambda$startObserve$13((Void) obj);
            }
        });
        ((ActAddCloudSceneVM) this.mViewModel).sortEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda26
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddCloudScene.this.lambda$startObserve$14((Void) obj);
            }
        });
        ((ActAddCloudSceneVM) this.mViewModel).editSuccessEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddCloudScene.this.lambda$startObserve$15((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r1) {
        showEditRoomDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Void r1) {
        showDelDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Integer num) {
        ((ActAddCloudSceneBinding) this.mViewBinding).ivModeIcon.setImageResource(SceneHelper.getSceneIcon(this, num.intValue()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(List list) {
        ((ActAddCloudSceneVM) this.mViewModel).automationList.setValue(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$startObserve$8(Resource resource) {
        if (resource == null) {
            return ResourceEmptyLiveData.create();
        }
        return Injection.repo().scene().getSceneContent(this, ((ActAddCloudSceneVM) this.mViewModel).sceneId).data();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$9(List list) {
        if (list == null || list.size() <= 0 || !this.isFirstIn) {
            return;
        }
        initDataView(((Scene) list.get(0)).getSceneContents(), true);
        if (((ActAddCloudSceneVM) this.mViewModel).actionList.size() > 0) {
            ((ActAddCloudSceneVM) this.mViewModel).isShowEditLayout.setValue(true);
        } else {
            ((ActAddCloudSceneVM) this.mViewModel).isShowEditLayout.setValue(false);
            this.editMode = true;
        }
        inOrOutEditMode(this.editMode);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$10(List list) {
        MessageDialog.show(this.activity, getString(R.string.save_fail), getString(R.string.not_support_cloud_tip)).setCustomView(getCustomView(list)).setOkButton(getString(R.string.ok)).setCancelable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$11(Integer num) {
        if (((ActAddCloudSceneVM) this.mViewModel).actionList == null || ((ActAddCloudSceneVM) this.mViewModel).actionList.size() == 0) {
            ((ActAddCloudSceneBinding) this.mViewBinding).tvTotalTime.setVisibility(8);
        } else if (LanguageUtils.isRussian(this.activity)) {
            ((ActAddCloudSceneBinding) this.mViewBinding).tvTotalTime.setVisibility(8);
        } else {
            ((ActAddCloudSceneBinding) this.mViewBinding).tvTotalTime.setVisibility(0);
            ((ActAddCloudSceneBinding) this.mViewBinding).tvTotalTime.setText(getString(R.string.total_scene_time, new Object[]{num}));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$12(Void r1) {
        if (this.editMode) {
            if (!((ActAddCloudSceneVM) this.mViewModel).hasAction()) {
                if (((ActAddCloudSceneVM) this.mViewModel).unSupportDevices.isEmpty()) {
                    SmartToast.showShort(getString(R.string.app_str_please_add_action));
                    return;
                }
                return;
            } else if (((ActAddCloudSceneVM) this.mViewModel).isEdit) {
                ((ActAddCloudSceneVM) this.mViewModel).editScene();
                return;
            } else {
                ((ActAddCloudSceneVM) this.mViewModel).addScene();
                return;
            }
        }
        this.editMode = true;
        inOrOutEditMode(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$13(Void r4) {
        SceneHelper.instance().maskType = MaskType.LOCAL;
        NavUtils.destination(ActImportSceneAllActivity.class).withLong(Constants.PLACE_ID, ((ActAddCloudSceneVM) this.mViewModel).placeId).withBoolean(Constants.IS_ACTION_EMPTY, ((ActAddCloudSceneVM) this.mViewModel).actionList.size() == 0).withLong(Constants.SCENE_ID, ((ActAddCloudSceneVM) this.mViewModel).sceneId).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$14(Void r4) {
        ((ActAddCloudSceneBinding) this.mViewBinding).tvModeName.setEnabled(true);
        ((ActAddCloudSceneBinding) this.mViewBinding).vChangeIcon.setEnabled(true);
        ((ActAddCloudSceneBinding) this.mViewBinding).rvAction.setEnabled(true);
        this.textAddBinding.rlAdd.setEnabled(true);
        ((ActAddCloudSceneBinding) this.mViewBinding).rvAction.setSwipeItemMenuEnabled(true);
        this.mAdapter.disableDragItem();
        ((ActAddCloudSceneVM) this.mViewModel).isShowSortLayout.setValue(false);
        ((ActAddCloudSceneVM) this.mViewModel).isShowEditLayout.setValue(true);
        ((ActAddCloudSceneBinding) this.mViewBinding).ivImport.setVisibility(0);
        this.textAddBinding.rlAdd.setVisibility(0);
        this.mAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$15(Void r1) {
        this.editMode = false;
        inOrOutEditMode(false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        initDataView(null, false);
        showContent();
    }

    private void inOrOutEditMode(boolean editMode) {
        ((ActAddCloudSceneBinding) this.mViewBinding).tvAction.setText(getString(editMode ? R.string.edit_save_action : R.string.edit_scene_action));
        ((ActAddCloudSceneBinding) this.mViewBinding).ivAction.setImageResource(editMode ? R.mipmap.ic_save_action : R.mipmap.ic_edit_action);
        this.textAddBinding.rlAdd.setVisibility(editMode ? 0 : 8);
        ((ActAddCloudSceneBinding) this.mViewBinding).rvAction.setSwipeItemMenuEnabled(editMode);
    }

    private void initDataView(List<Scene.SceneContent> sceneContents, boolean actionNotNull) {
        if (((ActAddCloudSceneVM) this.mViewModel).actionList == null || ((ActAddCloudSceneVM) this.mViewModel).actionList.size() == 0) {
            if (sceneContents != null) {
                ((ActAddCloudSceneVM) this.mViewModel).actionList = sceneContents;
                this.mAdapter.setNewData(((ActAddCloudSceneVM) this.mViewModel).actionList);
            } else {
                ((ActAddCloudSceneVM) this.mViewModel).actionList = new ArrayList();
                this.mAdapter.setNewData(((ActAddCloudSceneVM) this.mViewModel).actionList);
            }
            if (actionNotNull) {
                this.isFirstIn = false;
            }
            getTotalTime(((ActAddCloudSceneVM) this.mViewModel).actionList);
            dismissLoadingDialog();
        }
    }

    private void getTotalTime(List<Scene.SceneContent> actionList) {
        if (actionList == null || actionList.size() <= 0) {
            return;
        }
        int i = 0;
        for (int i2 = 0; i2 < actionList.size() - 1; i2++) {
            i += actionList.get(i2).getTimeSpace();
        }
        ((ActAddCloudSceneVM) this.mViewModel).totalSceneTime.setValue(Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showContent() {
        super.showContent();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        ((ActAddCloudSceneVM) this.mViewModel).request.retry();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (this.editMode && ((ActAddCloudSceneVM) this.mViewModel).actionList.size() > 0) {
            showNeedSaveDialog();
        } else {
            super.back();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View getCustomView(List<ActAddCloudSceneVM.NoActionDevice> devices) {
        View inflate = LayoutInflater.from(this.activity).inflate(R.layout.dialog_custom_list, (ViewGroup) null);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new BaseQuickAdapter<ActAddCloudSceneVM.NoActionDevice, BaseViewHolder>(this, R.layout.item_scene_no_action_tip, devices) { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, ActAddCloudSceneVM.NoActionDevice item) {
                helper.setText(R.id.tv_room, item.getRoom());
                helper.setText(R.id.tv_content, item.getName());
            }
        });
        return inflate;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (getTitleBar().getEditString().equals(getString(R.string.save))) {
            if (!((ActAddCloudSceneVM) this.mViewModel).hasAction()) {
                if (((ActAddCloudSceneVM) this.mViewModel).unSupportDevices.isEmpty()) {
                    SmartToast.showShort(getString(R.string.app_str_please_add_action));
                    return;
                }
                return;
            } else if (((ActAddCloudSceneVM) this.mViewModel).isEdit) {
                ((ActAddCloudSceneVM) this.mViewModel).editScene();
                return;
            } else {
                ((ActAddCloudSceneVM) this.mViewModel).addScene();
                return;
            }
        }
        setEditString("");
        ((ActAddCloudSceneBinding) this.mViewBinding).tvModeName.setEnabled(true);
        ((ActAddCloudSceneBinding) this.mViewBinding).vChangeIcon.setEnabled(true);
        ((ActAddCloudSceneBinding) this.mViewBinding).rvAction.setEnabled(true);
        this.textAddBinding.rlAdd.setEnabled(true);
        ((ActAddCloudSceneBinding) this.mViewBinding).rvAction.setSwipeItemMenuEnabled(true);
        this.mAdapter.disableDragItem();
        this.mAdapter.notifyDataSetChanged();
    }

    private void initActionRv() {
        ((ActAddCloudSceneBinding) this.mViewBinding).rvAction.setSwipeMenuCreator(new SwipeMenuCreator() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda12
            @Override // com.yanzhenjie.recyclerview.SwipeMenuCreator
            public final void onCreateMenu(SwipeMenu swipeMenu, SwipeMenu swipeMenu2, int i) {
                ActAddCloudScene.this.lambda$initActionRv$16(swipeMenu, swipeMenu2, i);
            }
        });
        ((ActAddCloudSceneBinding) this.mViewBinding).rvAction.setOnItemMenuClickListener(new OnItemMenuClickListener() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda13
            @Override // com.yanzhenjie.recyclerview.OnItemMenuClickListener
            public final void onItemClick(SwipeMenuBridge swipeMenuBridge, int i) {
                ActAddCloudScene.this.lambda$initActionRv$17(swipeMenuBridge, i);
            }
        });
        BaseItemDraggableAdapter<Scene.SceneContent, BaseViewHolder> baseItemDraggableAdapter = new BaseItemDraggableAdapter<Scene.SceneContent, BaseViewHolder>(R.layout.item_scene_action, new ArrayList()) { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Scene.SceneContent item) {
                ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
                ((AppCompatTextView) helper.getView(R.id.tv_delay_time)).getPaint().setFakeBoldText(true);
                if (helper.getBindingAdapterPosition() == ActAddCloudScene.this.mAdapter.getData().size() - 1) {
                    helper.getView(R.id.tv_delay_time).setVisibility(8);
                } else {
                    helper.getView(R.id.tv_delay_time).setVisibility(0);
                }
                helper.setText(R.id.tv_delay_time, ActAddCloudScene.this.getString(R.string.delay) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + item.getTimeSpace() + ActAddCloudScene.this.getString(R.string.sec)).addOnClickListener(R.id.v_action).addOnClickListener(R.id.tv_delay_time);
                ActAddCloudSceneVM.ContentState contentState = ((ActAddCloudSceneVM) ActAddCloudScene.this.mViewModel).getContentState(this.mContext, item);
                if (contentState != null) {
                    helper.setImageResource(R.id.iv_icon, contentState.iconRes).setText(R.id.tv_device_name, contentState.name).setText(R.id.tv_location, contentState.location).setText(R.id.tv_state, contentState.state).setGone(R.id.tv_virtual, contentState.isVirtual);
                    if (contentState.rgbColor != Integer.MIN_VALUE) {
                        helper.getView(R.id.civ_color).setVisibility(0);
                        ((CircleImageView) helper.getView(R.id.civ_color)).setImageDrawable(new ColorDrawable(contentState.rgbColor));
                    } else {
                        helper.getView(R.id.civ_color).setVisibility(8);
                    }
                    helper.setGone(R.id.tv_action_tip, !MaskType.isAutomationAction(item.getActionType()) && TextUtils.isEmpty(contentState.action));
                    if (!TextUtils.isEmpty(item.getErrorTip())) {
                        helper.setGone(R.id.tv_action_tip, true);
                        helper.setText(R.id.tv_action_tip, item.getErrorTip());
                        if (item.getErrorTip().equals(ActAddCloudScene.this.getString(R.string.not_support_cloud_tip))) {
                            helper.setEnabled(R.id.v_action, false);
                        }
                    }
                } else {
                    helper.setImageResource(R.id.iv_icon, R.color.transparent).setText(R.id.tv_device_name, "").setText(R.id.tv_state, "");
                }
                if (!((ActAddCloudSceneVM) ActAddCloudScene.this.mViewModel).isShowSortLayout.getValue().booleanValue()) {
                    helper.setGone(R.id.ivDrag, false);
                    helper.setGone(R.id.appCompatImageView13, true);
                    helper.setEnabled(R.id.v_action, true);
                    helper.setEnabled(R.id.tv_delay_time, true);
                    return;
                }
                helper.setGone(R.id.ivDrag, true);
                helper.setGone(R.id.appCompatImageView13, false);
                helper.setEnabled(R.id.v_action, false);
                helper.setEnabled(R.id.tv_delay_time, false);
            }
        };
        this.mAdapter = baseItemDraggableAdapter;
        baseItemDraggableAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda14
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActAddCloudScene.this.lambda$initActionRv$18(baseQuickAdapter, view, i);
            }
        });
        this.mAdapter.setOnItemDragListener(new OnItemDragListener() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene.4
            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int i) {
                viewHolder.itemView.findViewById(R.id.add_scene_colayout).setBackground(ActAddCloudScene.this.getResources().getDrawable(R.drawable.selector_scene_bg));
                viewHolder.itemView.findViewById(R.id.tv_delay_time).setBackground(null);
            }

            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragMoving(RecyclerView.ViewHolder viewHolder, int i, RecyclerView.ViewHolder viewHolder1, int i1) {
                viewHolder.itemView.findViewById(R.id.add_scene_colayout).setBackground(ActAddCloudScene.this.getResources().getDrawable(R.drawable.selector_scene_bg));
                viewHolder.itemView.findViewById(R.id.tv_delay_time).setBackground(null);
            }

            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int i) {
                viewHolder.itemView.findViewById(R.id.add_scene_colayout).setBackgroundColor(ActAddCloudScene.this.getResources().getColor(R.color.white));
                ActAddCloudScene.this.mAdapter.notifyDataSetChanged();
            }
        });
        this.mAdapter.bindToRecyclerView(((ActAddCloudSceneBinding) this.mViewBinding).rvAction);
        ((ActAddCloudSceneBinding) this.mViewBinding).rvAction.setLayoutManager(new LinearLayoutManager(this));
        ((ActAddCloudSceneBinding) this.mViewBinding).rvAction.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActAddCloudSceneBinding) this.mViewBinding).rvAction.getItemAnimator()).setSupportsChangeAnimations(false);
        ItemTextAddBinding itemTextAddBinding = (ItemTextAddBinding) DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_text_add, (ViewGroup) ((ActAddCloudSceneBinding) this.mViewBinding).rvAction.getParent(), false);
        this.textAddBinding = itemTextAddBinding;
        itemTextAddBinding.setImageRes(Integer.valueOf(R.mipmap.ic_add_red));
        this.textAddBinding.setItem(getString(R.string.add_action));
        this.textAddBinding.setTextColor(Integer.valueOf(ContextCompat.getColor(this, R.color.color_text_black)));
        this.textAddBinding.setClickCommand(new BindingCommand<>(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda15
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActAddCloudScene.this.showAddDialog();
            }
        }));
        ((ActAddCloudSceneBinding) this.mViewBinding).rvAction.addFooterView(this.textAddBinding.getRoot());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionRv$16(SwipeMenu swipeMenu, SwipeMenu swipeMenu2, int i) {
        swipeMenu2.addMenuItem(new SwipeMenuItem(this).setTextSize(14).setWidth(SizeUtils.dp2px(60.0f)).setHeight(-1).setText(R.string.more).setTextColor(-1).setBackgroundColorResource(R.color.color_light_gray));
        swipeMenu2.addMenuItem(new SwipeMenuItem(this).setTextSize(14).setWidth(SizeUtils.dp2px(60.0f)).setHeight(-1).setText(R.string.delete).setTextColor(-1).setBackgroundColorResource(R.color.color_text_red));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionRv$17(SwipeMenuBridge swipeMenuBridge, int i) {
        swipeMenuBridge.closeMenu();
        if (-1 == swipeMenuBridge.getDirection()) {
            int position = swipeMenuBridge.getPosition();
            if (position != 0) {
                if (position != 1) {
                    return;
                }
                this.mAdapter.remove(i);
                BaseItemDraggableAdapter<Scene.SceneContent, BaseViewHolder> baseItemDraggableAdapter = this.mAdapter;
                baseItemDraggableAdapter.notifyItemChanged(baseItemDraggableAdapter.getData().size() - 1);
                if (((ActAddCloudSceneVM) this.mViewModel).actionList.isEmpty()) {
                    ((ActAddCloudSceneVM) this.mViewModel).isShowEditLayout.setValue(false);
                }
                getTotalTime(((ActAddCloudSceneVM) this.mViewModel).actionList);
                return;
            }
            ((ActAddCloudSceneVM) this.mViewModel).isShowSortLayout.setValue(true);
            ((ActAddCloudSceneVM) this.mViewModel).isShowEditLayout.setValue(false);
            ((ActAddCloudSceneBinding) this.mViewBinding).ivImport.setVisibility(8);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemDragAndSwipeCallback(this.mAdapter));
            itemTouchHelper.attachToRecyclerView(((ActAddCloudSceneBinding) this.mViewBinding).rvAction);
            this.mAdapter.enableDragItem(itemTouchHelper, R.id.ivDrag, false);
            ((ActAddCloudSceneBinding) this.mViewBinding).tvModeName.setEnabled(false);
            ((ActAddCloudSceneBinding) this.mViewBinding).vChangeIcon.setEnabled(false);
            this.textAddBinding.rlAdd.setEnabled(false);
            ((ActAddCloudSceneBinding) this.mViewBinding).rvAction.setSwipeItemMenuEnabled(false);
            this.mAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionRv$18(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (this.editMode) {
            int id = view.getId();
            if (id == R.id.tv_delay_time) {
                editTimeSpace(i, this.mAdapter.getData().get(i).getTimeSpace());
            } else {
                if (id != R.id.v_action) {
                    return;
                }
                editAction(i);
            }
        }
    }

    private void showEditNameDialog() {
        EditDialog.asDefault().setContent(((ActAddCloudSceneVM) this.mViewModel).sceneName.getValue()).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActAddCloudScene.this.lambda$showEditNameDialog$21((String) obj);
            }
        }).showDialog(getSupportFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$21(final String str) {
        ((ActAddCloudSceneVM) this.mViewModel).sceneName.setValue(str);
        if (((ActAddCloudSceneVM) this.mViewModel).isEdit) {
            ((ObservableSubscribeProxy) Injection.net().updateSceneName(((ActAddCloudSceneVM) this.mViewModel).sceneId, str, ((ActAddCloudSceneVM) this.mViewModel).sceneIconPos.getValue().intValue()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda16
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActAddCloudScene.this.lambda$showEditNameDialog$19((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda17
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActAddCloudScene.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda18
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActAddCloudScene.this.lambda$showEditNameDialog$20(str, obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$19(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$20(String str, Object obj) throws Exception {
        Injection.repo().scene().updateSceneName(((ActAddCloudSceneVM) this.mViewModel).sceneId, str);
    }

    private void editAction(int position) {
        NavUtils.Builder goSelectAction;
        ((ActAddCloudSceneVM) this.mViewModel).editPosition = position;
        Scene.SceneContent sceneContent = this.mAdapter.getData().get(position);
        SceneHelper.instance().reset();
        SceneHelper.instance().automationAction = true;
        if (MaskType.isGroupAction(sceneContent.getActionType())) {
            GroupParam groupParam = (GroupParam) sceneContent.getExecuteCommand(GroupParam.class);
            SceneHelper.instance().selectInstruct = groupParam.instruct;
            NavUtils.Builder goSelectAction2 = SceneHelper.instance().goSelectAction(((ActAddCloudSceneVM) this.mViewModel).getGroup(groupParam.groupid));
            if (goSelectAction2 != null) {
                goSelectAction2.withLong(Constants.PLACE_ID, ((ActAddCloudSceneVM) this.mViewModel).placeId).withDefaultRequestCode().navigation(this);
                return;
            }
            return;
        }
        if (MaskType.isDeviceAction(sceneContent.getActionType())) {
            DeviceParam deviceParam = (DeviceParam) sceneContent.getExecuteCommand(DeviceParam.class);
            SceneHelper.instance().selectInstruct = deviceParam.instruct;
            Device device = ((ActAddCloudSceneVM) this.mViewModel).getDevice(deviceParam.deviceid);
            if (device != null) {
                if (device.getProductId().equals(ProductId.CG485_SUB_DEVICE)) {
                    SceneHelper.instance().maskType = MaskType.DEVICE;
                    SceneHelper.instance().controlObject = device;
                    Cg485Helper.showSceneActionDialog(this, device, false);
                    return;
                } else {
                    NavUtils.Builder goSelectAction3 = SceneHelper.instance().goSelectAction(((ActAddCloudSceneVM) this.mViewModel).getDevice(deviceParam.deviceid));
                    if (goSelectAction3 != null) {
                        goSelectAction3.withLong(Constants.PLACE_ID, ((ActAddCloudSceneVM) this.mViewModel).placeId).withString(SceneHelper.SCENE_PARAM_EXT, deviceParam.sceneParamExt).withDefaultRequestCode().navigation(this);
                        return;
                    }
                    return;
                }
            }
            return;
        }
        if (MaskType.isAutomationAction(sceneContent.getActionType())) {
            SceneHelper.instance().controlObject = ((ActAddCloudSceneVM) this.mViewModel).getCurActionAutomation();
            SceneHelper.goSelectAction(this, 3, ((ActAddCloudSceneVM) this.mViewModel).placeId);
            return;
        }
        if (MaskType.isSceneAction(sceneContent.getActionType())) {
            SceneParam sceneParam = (SceneParam) sceneContent.getExecuteCommand(SceneParam.class);
            SceneHelper.instance().selectInstruct = sceneParam.instruct;
            Scene scene = ((ActAddCloudSceneVM) this.mViewModel).getScene(sceneParam.sceneid);
            if (scene != null) {
                SceneHelper.instance().controlObject = Collections.singletonList(scene);
                NavUtils.destination(ActSelectDaliScene.class).withLong("device_id", scene.getMacdeviceid()).withLong(Constants.PLACE_ID, ((ActAddCloudSceneVM) this.mViewModel).placeId).withDefaultRequestCode().navigation(this);
                return;
            }
            return;
        }
        if (MaskType.isSonosAction(sceneContent.getActionType())) {
            DeviceParam deviceParam2 = (DeviceParam) sceneContent.getExecuteCommand(DeviceParam.class);
            if (((ActAddCloudSceneVM) this.mViewModel).getDevice(deviceParam2.deviceid) == null || (goSelectAction = SceneHelper.instance().goSelectAction(((ActAddCloudSceneVM) this.mViewModel).getDevice(deviceParam2.deviceid))) == null) {
                return;
            }
            goSelectAction.withLong(Constants.PLACE_ID, ((ActAddCloudSceneVM) this.mViewModel).placeId).withString(SceneHelper.SCENE_PARAM_EXT, deviceParam2.sceneParamExt).withDefaultRequestCode().navigation(this);
        }
    }

    private void editTimeSpace(final int position, int sec) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 60; i++) {
            arrayList.add(i < 10 ? "0" + i : String.valueOf(i));
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = 0; i2 < 60; i2++) {
            arrayList2.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
        }
        TimeSelectDialog.asDefault().setTitle(getString(R.string.delay_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(true).supportRightAway(false).setMinUnit(getString(R.string.min)).setSecUnit(getString(R.string.sec)).setSelectMinPosition(sec / 60).setSelectSecPosition(sec % 60).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda20
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i3, int i4) {
                ActAddCloudScene.this.lambda$editTimeSpace$22(position, i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$editTimeSpace$22(int i, int i2, int i3) {
        int intValue = ((ActAddCloudSceneVM) this.mViewModel).totalSceneTime.getValue().intValue() - ((ActAddCloudSceneVM) this.mViewModel).actionList.get(i).getTimeSpace();
        if (intValue < 0) {
            intValue = 0;
        }
        if (i2 == 0 && i3 == 0) {
            ((ActAddCloudSceneVM) this.mViewModel).actionList.get(i).setTimeSpace((i2 * 60) + 1);
        } else {
            ((ActAddCloudSceneVM) this.mViewModel).actionList.get(i).setTimeSpace((i2 * 60) + i3);
        }
        ((ActAddCloudSceneVM) this.mViewModel).totalSceneTime.setValue(Integer.valueOf(intValue + ((ActAddCloudSceneVM) this.mViewModel).actionList.get(i).getTimeSpace()));
        this.mAdapter.notifyItemChanged(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAddDialog() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.device));
        arrayList.add(getString(R.string.dali_scene));
        arrayList.add(getString(R.string.automation));
        SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActAddCloudScene.this.lambda$showAddDialog$23((Integer) obj);
            }
        }).setSelectList(arrayList).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAddDialog$23(Integer num) {
        ((ActAddCloudSceneVM) this.mViewModel).editPosition = -1;
        SceneHelper.instance().reset();
        SceneHelper.instance().initFloorRoom(((ActAddCloudSceneVM) this.mViewModel).floorId, ((ActAddCloudSceneVM) this.mViewModel).roomId);
        int intValue = num.intValue();
        if (intValue == 1) {
            NavUtils.destination(ActSelectCgdPro.class).withLong(Constants.PLACE_ID, ((ActAddCloudSceneVM) this.mViewModel).placeId).withDefaultRequestCode().navigation(this);
            return;
        }
        if (intValue == 2) {
            SceneHelper.instance().automationAction = true;
            SceneHelper.instance().controlObject = ((ActAddCloudSceneVM) this.mViewModel).getCurActionAutomation();
            SceneHelper.goSelectAction(this, 3, ((ActAddCloudSceneVM) this.mViewModel).placeId);
            return;
        }
        SceneHelper.goSelectAction(this, 6, ((ActAddCloudSceneVM) this.mViewModel).placeId);
    }

    private void showDelDialog() {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.tip_del_scene)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$$ExternalSyntheticLambda23
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showDelDialog$24;
                lambda$showDelDialog$24 = ActAddCloudScene.this.lambda$showDelDialog$24(baseDialog, view);
                return lambda$showDelDialog$24;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showDelDialog$24(BaseDialog baseDialog, View view) {
        ((ActAddCloudSceneVM) this.mViewModel).delScene(((ActAddCloudSceneVM) this.mViewModel).sceneId);
        return false;
    }

    private void saveAction() {
        List<Scene.SceneContent> convert2SceneContents = SceneHelper.instance().convert2SceneContents();
        if (convert2SceneContents.isEmpty()) {
            return;
        }
        SceneHelper.instance().closeLightDevice(convert2SceneContents, this);
        if (MaskType.isAutomationAction(convert2SceneContents.get(0).getActionType())) {
            int i = 0;
            while (i < ((ActAddCloudSceneVM) this.mViewModel).actionList.size()) {
                if (MaskType.isAutomationAction(((ActAddCloudSceneVM) this.mViewModel).actionList.get(i).getActionType())) {
                    Iterator<Scene.SceneContent> it = convert2SceneContents.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            Scene.SceneContent next = it.next();
                            if (((AutomationParam) next.getExecuteCommand(AutomationParam.class)).equals(((ActAddCloudSceneVM) this.mViewModel).actionList.get(i).getExecuteCommand(AutomationParam.class))) {
                                this.mAdapter.setData(i, next);
                                break;
                            }
                        } else {
                            this.mAdapter.remove(i);
                            i--;
                            break;
                        }
                    }
                }
                i++;
            }
            for (Scene.SceneContent sceneContent : convert2SceneContents) {
                int i2 = 0;
                while (true) {
                    if (i2 < ((ActAddCloudSceneVM) this.mViewModel).actionList.size()) {
                        if (((AutomationParam) sceneContent.getExecuteCommand(AutomationParam.class)).equals(((ActAddCloudSceneVM) this.mViewModel).actionList.get(i2).getExecuteCommand(AutomationParam.class))) {
                            break;
                        } else {
                            i2++;
                        }
                    } else {
                        this.mAdapter.addData((BaseItemDraggableAdapter<Scene.SceneContent, BaseViewHolder>) sceneContent);
                        break;
                    }
                }
            }
            this.mAdapter.notifyDataSetChanged();
        } else if (((ActAddCloudSceneVM) this.mViewModel).editPosition == -1) {
            this.mAdapter.addData(convert2SceneContents);
            this.mAdapter.notifyDataSetChanged();
        } else {
            convert2SceneContents.get(0).setTimeSpace(this.mAdapter.getData().get(((ActAddCloudSceneVM) this.mViewModel).editPosition).getTimeSpace());
            if (SceneHelper.instance().isCgdProAction) {
                this.mAdapter.remove(((ActAddCloudSceneVM) this.mViewModel).editPosition);
                this.mAdapter.addData(((ActAddCloudSceneVM) this.mViewModel).editPosition, convert2SceneContents);
            } else {
                this.mAdapter.setData(((ActAddCloudSceneVM) this.mViewModel).editPosition, convert2SceneContents.get(0));
            }
        }
        this.mAdapter.notifyDataSetChanged();
        getTotalTime(((ActAddCloudSceneVM) this.mViewModel).actionList);
        ((ActAddCloudSceneVM) this.mViewModel).isShowEditLayout.setValue(true);
        this.editMode = true;
        inOrOutEditMode(true);
    }

    private void showEditRoomDialog() {
        if (((ActAddCloudSceneVM) this.mViewModel).roomPickHelper.getCanSetRoom().getValue().booleanValue()) {
            RoomSelectDialog.asDefault().setFloorList(((ActAddCloudSceneVM) this.mViewModel).roomPickHelper.getCurrentFloorNames()).setRoomList(((ActAddCloudSceneVM) this.mViewModel).roomPickHelper.getCurrentRoomNames()).setSaveText(getString(R.string.save)).setSelectFloorPosition(((ActAddCloudSceneVM) this.mViewModel).roomPickHelper.getSelectFloorPosition()).setSelectRoomPosition(((ActAddCloudSceneVM) this.mViewModel).roomPickHelper.getSelectRoomPosition()).setSelectListener(new AnonymousClass5()).showDialog(this);
        }
    }

    /* renamed from: com.ltech.smarthome.ui.scene.ActAddCloudScene$5, reason: invalid class name */
    class AnonymousClass5 implements RoomSelectDialog.SelectListener {
        AnonymousClass5() {
        }

        @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
        public void confirm(int floorPosition, int roomPosition) {
            ((ActAddCloudSceneVM) ActAddCloudScene.this.mViewModel).roomPickHelper.setSelectPosition(floorPosition, roomPosition);
            ((ActAddCloudSceneVM) ActAddCloudScene.this.mViewModel).floorId = ((ActAddCloudSceneVM) ActAddCloudScene.this.mViewModel).roomPickHelper.getSelectFloorId();
            ((ActAddCloudSceneVM) ActAddCloudScene.this.mViewModel).roomId = ((ActAddCloudSceneVM) ActAddCloudScene.this.mViewModel).roomPickHelper.getSelectRoomId();
            ((ObservableSubscribeProxy) Injection.net().updateSceneRoom(((ActAddCloudSceneVM) ActAddCloudScene.this.mViewModel).sceneId, ((ActAddCloudSceneVM) ActAddCloudScene.this.mViewModel).floorId, ((ActAddCloudSceneVM) ActAddCloudScene.this.mViewModel).roomId).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$5$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActAddCloudScene.AnonymousClass5.this.lambda$confirm$0((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$5$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActAddCloudScene.AnonymousClass5.this.lambda$confirm$1();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(ActAddCloudScene.this.activity, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudScene$5$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActAddCloudScene.AnonymousClass5.this.lambda$confirm$2(obj);
                }
            }, new SmartErrorComsumer());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$confirm$0(Disposable disposable) throws Exception {
            ActAddCloudScene.this.showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$confirm$1() throws Exception {
            ActAddCloudScene.this.dismissLoadingDialog();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$confirm$2(Object obj) throws Exception {
            Injection.repo().scene().updateSceneRoom(((ActAddCloudSceneVM) ActAddCloudScene.this.mViewModel).sceneId, ((ActAddCloudSceneVM) ActAddCloudScene.this.mViewModel).floorId, ((ActAddCloudSceneVM) ActAddCloudScene.this.mViewModel).roomId);
            ((ActAddCloudSceneBinding) ActAddCloudScene.this.mViewBinding).tvRoomName.setText(Injection.repo().home().getRoomName(((ActAddCloudSceneVM) ActAddCloudScene.this.mViewModel).floorId, ((ActAddCloudSceneVM) ActAddCloudScene.this.mViewModel).roomId));
        }

        @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
        public void onFloorSelect(RoomSelectDialog dialog, int floorPosition) {
            dialog.setRoomList(((ActAddCloudSceneVM) ActAddCloudScene.this.mViewModel).roomPickHelper.getRoomNames(floorPosition));
            dialog.notifyDialog();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            saveAction();
            return;
        }
        if (3016 == resultCode) {
            importAction(data.getIntExtra("type", 0));
        } else {
            if (3002 != resultCode || data == null) {
                return;
            }
            ((ActAddCloudSceneVM) this.mViewModel).sceneIconPos.setValue(Integer.valueOf(data.getIntExtra(Constants.ICON_POSITION, 0)));
        }
    }

    private void importAction(int importType) {
        List<Scene.SceneContent> convert2ImportList;
        if (importType == 1) {
            convert2ImportList = SceneHelper.instance().convert2ImportListWithAction(false);
        } else {
            convert2ImportList = SceneHelper.instance().convert2ImportList(this, false);
        }
        if (convert2ImportList.size() > 0) {
            ((ActAddCloudSceneVM) this.mViewModel).isShowEditLayout.setValue(true);
        } else {
            ((ActAddCloudSceneVM) this.mViewModel).isShowEditLayout.setValue(false);
        }
        this.editMode = true;
        inOrOutEditMode(true);
        ((ActAddCloudSceneVM) this.mViewModel).actionList = convert2ImportList;
        getTotalTime(convert2ImportList);
        this.mAdapter.setNewData(((ActAddCloudSceneVM) this.mViewModel).actionList);
    }
}