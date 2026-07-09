package com.ltech.smarthome.ui.scene.local;

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
import androidx.lifecycle.Observer;
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
import com.ltech.smarthome.databinding.ActAddLocalSceneBinding;
import com.ltech.smarthome.databinding.ItemTextAddBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.model.scene_param.DeviceParam;
import com.ltech.smarthome.model.scene_param.GroupParam;
import com.ltech.smarthome.model.scene_param.SceneParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.api.ApiConstants;
import com.ltech.smarthome.ui.device.cg485.Cg485Helper;
import com.ltech.smarthome.ui.scene.ActImportSceneAllActivity;
import com.ltech.smarthome.ui.scene.ActSelectCgdPro;
import com.ltech.smarthome.ui.scene.ActSelectDaliScene;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.ui.scene.local.ActAddLocalScene;
import com.ltech.smarthome.ui.scene.local.ActAddLocalSceneVM;
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
import com.ltech.smarthome.view.dialog.TimeSelectWithMsDialog;
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
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActAddLocalScene extends VMActivity<ActAddLocalSceneBinding, ActAddLocalSceneVM> {
    private boolean editMode = false;
    private boolean isFirstIn = true;
    private BaseItemDraggableAdapter<Scene.SceneContent, BaseViewHolder> mAdapter;
    private ItemTextAddBinding textAddBinding;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_add_local_scene;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditString("");
        ((ActAddLocalSceneVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActAddLocalSceneVM) this.mViewModel).floorId = getIntent().getLongExtra(Constants.FLOOR_ID, -1L);
        ((ActAddLocalSceneVM) this.mViewModel).roomId = getIntent().getLongExtra(Constants.ROOM_ID, -1L);
        ((ActAddLocalSceneVM) this.mViewModel).sceneId = getIntent().getLongExtra(Constants.SCENE_ID, 0L);
        ((ActAddLocalSceneVM) this.mViewModel).sceneNum = getIntent().getIntExtra(Constants.SCENE_NUM, 0);
        ((ActAddLocalSceneVM) this.mViewModel).sceneName.setValue(getIntent().getStringExtra(Constants.SCENE_NAME));
        ((ActAddLocalSceneVM) this.mViewModel).isEdit = ((ActAddLocalSceneVM) this.mViewModel).sceneId > 0;
        if (((ActAddLocalSceneVM) this.mViewModel).sceneId > 0) {
            ((ActAddLocalSceneVM) this.mViewModel).scene = Injection.repo().scene().getSceneBySceneId(((ActAddLocalSceneVM) this.mViewModel).sceneId);
            ((ActAddLocalSceneVM) this.mViewModel).floorId = ((ActAddLocalSceneVM) this.mViewModel).scene.getFloorId();
            ((ActAddLocalSceneVM) this.mViewModel).roomId = ((ActAddLocalSceneVM) this.mViewModel).scene.getRoomId();
            ((ActAddLocalSceneBinding) this.mViewBinding).sbAddToCommon.setChecked(((ActAddLocalSceneVM) this.mViewModel).scene.isCommon());
            ((ActAddLocalSceneBinding) this.mViewBinding).sbSetDynamic.setChecked(((ActAddLocalSceneVM) this.mViewModel).scene.isDynamic());
        }
        ((ActAddLocalSceneBinding) this.mViewBinding).tvRoomName.setText(Injection.repo().home().getRoomName(((ActAddLocalSceneVM) this.mViewModel).floorId, ((ActAddLocalSceneVM) this.mViewModel).roomId));
        initRvMenu();
        setTitle(getString(R.string.app_str_edit_local_scene));
        if (((ActAddLocalSceneVM) this.mViewModel).isEdit) {
            ((ActAddLocalSceneVM) this.mViewModel).sceneIconPos.setValue(Integer.valueOf(getIntent().getIntExtra(Constants.ICON_POSITION, 0)));
        }
        ((ActAddLocalSceneBinding) this.mViewBinding).sbAddToCommon.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                ActAddLocalScene.this.lambda$initView$2(switchButton, z);
            }
        });
        ((ActAddLocalSceneBinding) this.mViewBinding).sbSetDynamic.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                ActAddLocalScene.this.lambda$initView$5(switchButton, z);
            }
        });
        ((ActAddLocalSceneBinding) this.mViewBinding).ivDynamic.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda22
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActAddLocalScene.this.lambda$initView$6(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(SwitchButton switchButton, final boolean z) {
        ((ObservableSubscribeProxy) Injection.net().setCommonScene(((ActAddLocalSceneVM) this.mViewModel).sceneId, z).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycle(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddLocalScene.this.lambda$initView$0(z, obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddLocalScene.this.lambda$initView$1(z, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(boolean z, Object obj) throws Exception {
        ((ActAddLocalSceneVM) this.mViewModel).scene.setCommon(z);
        Injection.repo().scene().saveScene(((ActAddLocalSceneVM) this.mViewModel).scene);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(boolean z, Throwable th) throws Exception {
        ((ActAddLocalSceneBinding) this.mViewBinding).sbAddToCommon.setCheckedNotByUser(!z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$5(SwitchButton switchButton, boolean z) {
        if (z && !((ActAddLocalSceneVM) this.mViewModel).checkSupportDynamic()) {
            ((ActAddLocalSceneBinding) this.mViewBinding).sbSetDynamic.setCheckedNotByUser(false);
            MessageDialog.show(this, R.string.tips, R.string.open_dynamic_fail_tip).setOkButton(R.string.confirm).show();
        } else if (z) {
            setDynamicScene(true);
        } else {
            MessageDialog.show(this, R.string.tips, R.string.close_dynamic_tip).setOkButton(R.string.confirm, new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda26
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$initView$3;
                    lambda$initView$3 = ActAddLocalScene.this.lambda$initView$3(baseDialog, view);
                    return lambda$initView$3;
                }
            }).setCancelButton(R.string.cancel, new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda27
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$initView$4;
                    lambda$initView$4 = ActAddLocalScene.this.lambda$initView$4(baseDialog, view);
                    return lambda$initView$4;
                }
            }).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initView$3(BaseDialog baseDialog, View view) {
        setDynamicScene(false);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initView$4(BaseDialog baseDialog, View view) {
        ((ActAddLocalSceneBinding) this.mViewBinding).sbSetDynamic.setCheckedNotByUser(true);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$6(View view) {
        MessageDialog.show(this, R.string.tips, R.string.dynamic_scene_tip).setOkButton(R.string.confirm).show();
    }

    private void setDynamicScene(final boolean isChecked) {
        showLoadingDialog("");
        ((ObservableSubscribeProxy) Injection.net().setDynamicScene(((ActAddLocalSceneVM) this.mViewModel).sceneId, isChecked).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene.1
            @Override // io.reactivex.functions.Action
            public void run() throws Exception {
                ActAddLocalScene.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycle(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda24
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddLocalScene.this.lambda$setDynamicScene$7(isChecked, obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda25
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddLocalScene.this.lambda$setDynamicScene$8(isChecked, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDynamicScene$7(boolean z, Object obj) throws Exception {
        ((ActAddLocalSceneVM) this.mViewModel).scene.setDynamic(z);
        Injection.repo().scene().saveScene(((ActAddLocalSceneVM) this.mViewModel).scene);
        if (((ActAddLocalSceneVM) this.mViewModel).actionList == null || ((ActAddLocalSceneVM) this.mViewModel).actionList.size() <= 0) {
            return;
        }
        getTotalTime(((ActAddLocalSceneVM) this.mViewModel).actionList);
        ((ActAddLocalSceneVM) this.mViewModel).check(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDynamicScene$8(boolean z, Throwable th) throws Exception {
        ((ActAddLocalSceneBinding) this.mViewBinding).sbSetDynamic.setCheckedNotByUser(!z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$9(Void r1) {
        showEditNameDialog();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActAddLocalSceneVM) this.mViewModel).showEditDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda30
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddLocalScene.this.lambda$startObserve$9((Void) obj);
            }
        });
        ((ActAddLocalSceneVM) this.mViewModel).showEditRoomDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda35
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddLocalScene.this.lambda$startObserve$10((Void) obj);
            }
        });
        ((ActAddLocalSceneVM) this.mViewModel).showDelRoomDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda36
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddLocalScene.this.lambda$startObserve$11((Void) obj);
            }
        });
        ((ActAddLocalSceneVM) this.mViewModel).sceneIconPos.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddLocalScene.this.lambda$startObserve$12((Integer) obj);
            }
        });
        ((ActAddLocalSceneVM) this.mViewModel).roomPickHelper.startObserve(this, ((ActAddLocalSceneVM) this.mViewModel).placeId, ((ActAddLocalSceneVM) this.mViewModel).roomId);
        showLoadingDialog("");
        ((ActAddLocalSceneVM) this.mViewModel).request = Injection.repo().scene().getSceneContent(this, ((ActAddLocalSceneVM) this.mViewModel).sceneId);
        handleData(((ActAddLocalSceneVM) this.mViewModel).request, new IAction<List<Scene>>() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene.2
            @Override // com.ltech.smarthome.base.IAction
            public void act(List<Scene> scenes) {
                if (scenes == null || scenes.isEmpty()) {
                    return;
                }
                ActAddLocalScene.this.initDataView(scenes.get(0).getSceneContents());
                if (scenes.get(0).getSceneContents() == null || !ActAddLocalScene.this.isFirstIn) {
                    return;
                }
                if (!scenes.get(0).getSceneContents().isEmpty()) {
                    ((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).isShowEditLayout.setValue(true);
                } else {
                    ((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).isShowEditLayout.setValue(true);
                    ActAddLocalScene.this.editMode = true;
                }
                ActAddLocalScene.this.isFirstIn = false;
                ActAddLocalScene actAddLocalScene = ActAddLocalScene.this;
                actAddLocalScene.inOrOutEditMode(actAddLocalScene.editMode);
                if (ActAddLocalScene.this.editMode) {
                    ActAddLocalScene.this.initEditRvMenu();
                }
            }
        });
        ((ActAddLocalSceneVM) this.mViewModel).showErrorDialogEvent.observe(this, new Observer<String>() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(String s) {
                MessageDialog.show(ActAddLocalScene.this, ActivityUtils.getTopActivity().getString(R.string.tips), s).setOkButton(ActAddLocalScene.this.getString(R.string.ok)).setCancelable(false);
            }
        });
        ((ActAddLocalSceneVM) this.mViewModel).showSameActionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddLocalScene.this.lambda$startObserve$13((List) obj);
            }
        });
        ((ActAddLocalSceneVM) this.mViewModel).showDeviceFailDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddLocalScene.this.lambda$startObserve$14((List) obj);
            }
        });
        ((ActAddLocalSceneVM) this.mViewModel).showDeviceDeleteFailDialogEvent.observe(this, new Observer<List<ActAddLocalSceneVM.NoActionDevice>>() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene.6
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<ActAddLocalSceneVM.NoActionDevice> devices) {
                MessageDialog.show(ActAddLocalScene.this, ActivityUtils.getTopActivity().getString(R.string.save_fail), ActivityUtils.getTopActivity().getString(R.string.app_str_local_scene_device_offline)).setCustomView(ActAddLocalScene.this.getCustomView(devices)).setCancelButton(ActAddLocalScene.this.getString(R.string.cancel)).setOkButton(ActAddLocalScene.this.getString(R.string.app_str_retry)).setCancelButton(new OnDialogButtonClickListener(this) { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene.6.2
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        return false;
                    }
                }).setOkButton(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene.6.1
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        ((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).saveData();
                        return false;
                    }
                }).setCancelable(false);
            }
        });
        ((ActAddLocalSceneVM) this.mViewModel).showAutoDeviceFailDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddLocalScene.this.lambda$startObserve$15((List) obj);
            }
        });
        ((ActAddLocalSceneVM) this.mViewModel).noSetActionDeviceEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddLocalScene.this.lambda$startObserve$16((List) obj);
            }
        });
        ((ActAddLocalSceneVM) this.mViewModel).refreshErrorEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddLocalScene.this.lambda$startObserve$17((Void) obj);
            }
        });
        ((ActAddLocalSceneVM) this.mViewModel).totalSceneTime.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddLocalScene.this.lambda$startObserve$18((Integer) obj);
            }
        });
        ((ActAddLocalSceneVM) this.mViewModel).editActionEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda31
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddLocalScene.this.lambda$startObserve$19((Void) obj);
            }
        });
        ((ActAddLocalSceneVM) this.mViewModel).importEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda32
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddLocalScene.this.lambda$startObserve$20((Void) obj);
            }
        });
        ((ActAddLocalSceneVM) this.mViewModel).sortEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda33
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddLocalScene.this.lambda$startObserve$21((Void) obj);
            }
        });
        ((ActAddLocalSceneVM) this.mViewModel).editSuccessEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda34
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddLocalScene.this.lambda$startObserve$22((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$10(Void r1) {
        showEditRoomDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$11(Void r1) {
        showDelDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$12(Integer num) {
        ((ActAddLocalSceneBinding) this.mViewBinding).ivModeIcon.setImageResource(SceneHelper.getSceneIcon(this, num.intValue()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$13(List list) {
        MessageDialog.show(this, ActivityUtils.getTopActivity().getString(R.string.save_fail), ActivityUtils.getTopActivity().getString(R.string.app_str_local_scene_device_step_same_time)).setCustomView(getCustomView(list)).setOkButton(getString(R.string.ok)).setCancelable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$14(List list) {
        MessageDialog.show(this, ActivityUtils.getTopActivity().getString(R.string.save_fail), ActivityUtils.getTopActivity().getString(R.string.app_str_local_scene_device_offline)).setCustomView(getCustomView(list)).setCancelButton(getString(R.string.cancel)).setOkButton(getString(R.string.app_str_retry)).setCancelButton(new OnDialogButtonClickListener(this) { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene.5
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                return false;
            }
        }).setOkButton(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene.4
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                ((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).retryFailDevice();
                return false;
            }
        }).setCancelable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$15(List list) {
        MessageDialog.show(this, ActivityUtils.getTopActivity().getString(R.string.save_fail), ActivityUtils.getTopActivity().getString(R.string.not_support_local_tip)).setCustomView(getCustomView(list)).setOkButton(getString(R.string.ok)).setCancelable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$16(List list) {
        MessageDialog.show(this.activity, ActivityUtils.getTopActivity().getString(R.string.save_fail), ActivityUtils.getTopActivity().getString(R.string.app_str_local_scene_device_no_action), "").setOkButton(getString(R.string.ok)).setCustomView(getCustomView(list)).setCancelable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$17(Void r2) {
        ((ActAddLocalSceneVM) this.mViewModel).refreshErrorTip = true;
        this.mAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$18(Integer num) {
        if (((ActAddLocalSceneVM) this.mViewModel).scene.isDynamic()) {
            ((ActAddLocalSceneBinding) this.mViewBinding).tvTotalTime.setText(getString(R.string.total_local_scene_time, new Object[]{Float.valueOf(num.intValue() / 10.0f)}));
        } else {
            ((ActAddLocalSceneBinding) this.mViewBinding).tvTotalTime.setText(getString(R.string.total_scene_time, new Object[]{Integer.valueOf(num.intValue() / 10)}));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$19(Void r2) {
        if (this.editMode) {
            if (!((ActAddLocalSceneVM) this.mViewModel).hasAction()) {
                SmartToast.showShort(getString(R.string.app_str_please_add_action));
                return;
            } else {
                if (((ActAddLocalSceneVM) this.mViewModel).check(true)) {
                    ((ActAddLocalSceneVM) this.mViewModel).saveData();
                    return;
                }
                return;
            }
        }
        this.editMode = true;
        inOrOutEditMode(true);
        initEditRvMenu();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$20(Void r4) {
        SceneHelper.instance().maskType = MaskType.LOCAL;
        NavUtils.destination(ActImportSceneAllActivity.class).withLong(Constants.PLACE_ID, ((ActAddLocalSceneVM) this.mViewModel).placeId).withBoolean(Constants.IS_ACTION_EMPTY, ((ActAddLocalSceneVM) this.mViewModel).actionList.size() == 0).withLong(Constants.SCENE_ID, ((ActAddLocalSceneVM) this.mViewModel).sceneId).withBoolean(Constants.IS_DYNAMIC_SCENE, ((ActAddLocalSceneVM) this.mViewModel).scene.isDynamic()).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$21(Void r4) {
        ((ActAddLocalSceneBinding) this.mViewBinding).tvModeName.setEnabled(true);
        ((ActAddLocalSceneBinding) this.mViewBinding).vChangeIcon.setEnabled(true);
        ((ActAddLocalSceneBinding) this.mViewBinding).rvAction.setEnabled(true);
        this.textAddBinding.rlAdd.setEnabled(true);
        ((ActAddLocalSceneBinding) this.mViewBinding).rvAction.setSwipeItemMenuEnabled(true);
        this.mAdapter.disableDragItem();
        ((ActAddLocalSceneVM) this.mViewModel).isShowSortLayout.setValue(false);
        ((ActAddLocalSceneVM) this.mViewModel).isShowEditLayout.setValue(true);
        ((ActAddLocalSceneBinding) this.mViewBinding).ivImport.setVisibility(0);
        this.mAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$22(Void r1) {
        this.editMode = false;
        inOrOutEditMode(false);
        initRvMenu();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void inOrOutEditMode(boolean editMode) {
        ((ActAddLocalSceneBinding) this.mViewBinding).tvAction.setText(getString(editMode ? R.string.edit_save_action : R.string.edit_scene_action));
        ((ActAddLocalSceneBinding) this.mViewBinding).ivAction.setImageResource(editMode ? R.mipmap.ic_save_action : R.mipmap.ic_edit_action);
        this.textAddBinding.rlAdd.setVisibility(editMode ? 0 : 8);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (this.editMode && ((ActAddLocalSceneVM) this.mViewModel).actionList.size() > 0) {
            showNeedSaveDialog();
        } else {
            super.back();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View getCustomView(List<ActAddLocalSceneVM.NoActionDevice> devices) {
        View inflate = LayoutInflater.from(this.activity).inflate(R.layout.dialog_custom_list, (ViewGroup) null);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new BaseQuickAdapter<ActAddLocalSceneVM.NoActionDevice, BaseViewHolder>(this, R.layout.item_scene_no_action_tip, devices) { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene.7
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, ActAddLocalSceneVM.NoActionDevice item) {
                helper.setText(R.id.tv_room, item.getRoom());
                helper.setText(R.id.tv_content, item.getName());
            }
        });
        return inflate;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        initDataView(null);
        showContent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initDataView(List<Scene.SceneContent> sceneContents) {
        if (((ActAddLocalSceneVM) this.mViewModel).actionList == null) {
            if (sceneContents != null) {
                ((ActAddLocalSceneVM) this.mViewModel).actionList = sceneContents;
                ((ActAddLocalSceneVM) this.mViewModel).setLastDevice();
                this.mAdapter.setNewData(((ActAddLocalSceneVM) this.mViewModel).actionList);
            } else {
                ((ActAddLocalSceneVM) this.mViewModel).actionList = new ArrayList();
                this.mAdapter.setNewData(((ActAddLocalSceneVM) this.mViewModel).actionList);
            }
            getTotalTime(((ActAddLocalSceneVM) this.mViewModel).actionList);
            dismissLoadingDialog();
            ((ActAddLocalSceneVM) this.mViewModel).check(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getTotalTime(List<Scene.SceneContent> actionList) {
        if (actionList.size() == 0) {
            ((ActAddLocalSceneBinding) this.mViewBinding).tvTotalTime.setVisibility(8);
            return;
        }
        if (LanguageUtils.isRussian(this.activity)) {
            ((ActAddLocalSceneBinding) this.mViewBinding).tvTotalTime.setVisibility(8);
            return;
        }
        ((ActAddLocalSceneBinding) this.mViewBinding).tvTotalTime.setVisibility(0);
        int i = 0;
        for (int i2 = 0; i2 < actionList.size() - 1; i2++) {
            i += actionList.get(i2).getDelayTime(((ActAddLocalSceneVM) this.mViewModel).scene.isDynamic());
        }
        ((ActAddLocalSceneVM) this.mViewModel).totalSceneTime.setValue(Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showContent() {
        super.showContent();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        ((ActAddLocalSceneVM) this.mViewModel).request.retry();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (getTitleBar().getEditString().equals(getString(R.string.save))) {
            if (!((ActAddLocalSceneVM) this.mViewModel).hasAction()) {
                SmartToast.showShort(getString(R.string.app_str_please_add_action));
                return;
            } else {
                if (((ActAddLocalSceneVM) this.mViewModel).check(true)) {
                    ((ActAddLocalSceneVM) this.mViewModel).saveData();
                    return;
                }
                return;
            }
        }
        setEditString("");
        ((ActAddLocalSceneBinding) this.mViewBinding).tvModeName.setEnabled(true);
        ((ActAddLocalSceneBinding) this.mViewBinding).vChangeIcon.setEnabled(true);
        ((ActAddLocalSceneBinding) this.mViewBinding).rvAction.setEnabled(true);
        this.textAddBinding.rlAdd.setEnabled(true);
        ((ActAddLocalSceneBinding) this.mViewBinding).rvAction.setSwipeItemMenuEnabled(true);
        this.mAdapter.disableDragItem();
        this.mAdapter.notifyDataSetChanged();
    }

    private void initRvMenu() {
        if (((ActAddLocalSceneBinding) this.mViewBinding).rvAction.getAdapter() != null) {
            ((ActAddLocalSceneBinding) this.mViewBinding).rvAction.setAdapter(null);
        }
        ((ActAddLocalSceneBinding) this.mViewBinding).rvAction.setSwipeMenuCreator(new SwipeMenuCreator() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda13
            @Override // com.yanzhenjie.recyclerview.SwipeMenuCreator
            public final void onCreateMenu(SwipeMenu swipeMenu, SwipeMenu swipeMenu2, int i) {
                ActAddLocalScene.this.lambda$initRvMenu$23(swipeMenu, swipeMenu2, i);
            }
        });
        ((ActAddLocalSceneBinding) this.mViewBinding).rvAction.setOnItemMenuClickListener(new OnItemMenuClickListener() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda14
            @Override // com.yanzhenjie.recyclerview.OnItemMenuClickListener
            public final void onItemClick(SwipeMenuBridge swipeMenuBridge, int i) {
                ActAddLocalScene.this.lambda$initRvMenu$24(swipeMenuBridge, i);
            }
        });
        initActionRv();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRvMenu$23(SwipeMenu swipeMenu, SwipeMenu swipeMenu2, int i) {
        swipeMenu2.addMenuItem(new SwipeMenuItem(this).setTextSize(14).setWidth(SizeUtils.dp2px(60.0f)).setHeight(-1).setText(R.string.app_str_retry).setTextColor(-1).setBackgroundColorResource(R.color.color_text_red));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRvMenu$24(SwipeMenuBridge swipeMenuBridge, int i) {
        swipeMenuBridge.closeMenu();
        if (-1 == swipeMenuBridge.getDirection() && swipeMenuBridge.getPosition() == 0) {
            ((ActAddLocalSceneVM) this.mViewModel).retrySingleStep(this.mAdapter.getData().get(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initEditRvMenu() {
        if (((ActAddLocalSceneBinding) this.mViewBinding).rvAction.getAdapter() != null) {
            ((ActAddLocalSceneBinding) this.mViewBinding).rvAction.setAdapter(null);
        }
        ((ActAddLocalSceneBinding) this.mViewBinding).rvAction.setSwipeMenuCreator(new SwipeMenuCreator() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda28
            @Override // com.yanzhenjie.recyclerview.SwipeMenuCreator
            public final void onCreateMenu(SwipeMenu swipeMenu, SwipeMenu swipeMenu2, int i) {
                ActAddLocalScene.this.lambda$initEditRvMenu$25(swipeMenu, swipeMenu2, i);
            }
        });
        ((ActAddLocalSceneBinding) this.mViewBinding).rvAction.setOnItemMenuClickListener(new OnItemMenuClickListener() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda29
            @Override // com.yanzhenjie.recyclerview.OnItemMenuClickListener
            public final void onItemClick(SwipeMenuBridge swipeMenuBridge, int i) {
                ActAddLocalScene.this.lambda$initEditRvMenu$26(swipeMenuBridge, i);
            }
        });
        initActionRv();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initEditRvMenu$25(SwipeMenu swipeMenu, SwipeMenu swipeMenu2, int i) {
        swipeMenu2.addMenuItem(new SwipeMenuItem(this).setTextSize(14).setWidth(SizeUtils.dp2px(60.0f)).setHeight(-1).setText(R.string.more).setTextColor(-1).setBackgroundColorResource(R.color.color_light_gray));
        swipeMenu2.addMenuItem(new SwipeMenuItem(this).setTextSize(14).setWidth(SizeUtils.dp2px(60.0f)).setHeight(-1).setText(R.string.delete).setTextColor(-1).setBackgroundColorResource(R.color.color_text_red));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initEditRvMenu$26(SwipeMenuBridge swipeMenuBridge, int i) {
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
                getTotalTime(((ActAddLocalSceneVM) this.mViewModel).actionList);
                return;
            }
            ((ActAddLocalSceneVM) this.mViewModel).isShowSortLayout.setValue(true);
            ((ActAddLocalSceneVM) this.mViewModel).isShowEditLayout.setValue(false);
            ((ActAddLocalSceneBinding) this.mViewBinding).ivImport.setVisibility(8);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemDragAndSwipeCallback(this.mAdapter));
            itemTouchHelper.attachToRecyclerView(((ActAddLocalSceneBinding) this.mViewBinding).rvAction);
            this.mAdapter.enableDragItem(itemTouchHelper, R.id.ivDrag, false);
            ((ActAddLocalSceneBinding) this.mViewBinding).tvModeName.setEnabled(false);
            ((ActAddLocalSceneBinding) this.mViewBinding).vChangeIcon.setEnabled(false);
            this.textAddBinding.rlAdd.setEnabled(false);
            ((ActAddLocalSceneBinding) this.mViewBinding).rvAction.setSwipeItemMenuEnabled(false);
            this.mAdapter.notifyDataSetChanged();
        }
    }

    private void initActionRv() {
        ((ActAddLocalSceneBinding) this.mViewBinding).rvAction.setSwipeItemMenuEnabled(true);
        BaseItemDraggableAdapter<Scene.SceneContent, BaseViewHolder> baseItemDraggableAdapter = new BaseItemDraggableAdapter<Scene.SceneContent, BaseViewHolder>(R.layout.item_scene_action, ((ActAddLocalSceneVM) this.mViewModel).actionList == null ? new ArrayList() : ((ActAddLocalSceneVM) this.mViewModel).actionList) { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene.8
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Scene.SceneContent item) {
                ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
                ((AppCompatTextView) helper.getView(R.id.tv_delay_time)).getPaint().setFakeBoldText(true);
                if (helper.getBindingAdapterPosition() == ActAddLocalScene.this.mAdapter.getData().size() - 1) {
                    helper.getView(R.id.tv_delay_time).setVisibility(8);
                } else {
                    helper.getView(R.id.tv_delay_time).setVisibility(0);
                }
                if (((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).scene.isDynamic()) {
                    helper.setText(R.id.tv_delay_time, ActAddLocalScene.this.getString(R.string.delay) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + String.format(Locale.US, "%.1f", Float.valueOf(item.getDelayTime(true) / 10.0f)) + ActAddLocalScene.this.getString(R.string.sec));
                } else {
                    helper.setText(R.id.tv_delay_time, ActAddLocalScene.this.getString(R.string.delay) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + String.format(Locale.US, "%d", Integer.valueOf(item.getDelayTime(false) / 10)) + ActAddLocalScene.this.getString(R.string.sec));
                }
                helper.addOnClickListener(R.id.v_action).addOnClickListener(R.id.tv_delay_time);
                if (!((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).isShowSortLayout.getValue().booleanValue()) {
                    helper.setGone(R.id.ivDrag, false);
                    helper.setGone(R.id.appCompatImageView13, true);
                    helper.setEnabled(R.id.v_action, true);
                    helper.setEnabled(R.id.tv_delay_time, true);
                } else {
                    helper.setGone(R.id.ivDrag, true);
                    helper.setGone(R.id.appCompatImageView13, false);
                    helper.setEnabled(R.id.v_action, false);
                    helper.setEnabled(R.id.tv_delay_time, false);
                }
                ActAddLocalSceneVM.ContentState contentState = ((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).getContentState(this.mContext, item, helper.getBindingAdapterPosition());
                if (contentState != null) {
                    helper.setImageResource(R.id.iv_icon, contentState.iconRes).setText(R.id.tv_device_name, contentState.name).setText(R.id.tv_location, contentState.location).setText(R.id.tv_state, contentState.state).setGone(R.id.tv_virtual, contentState.isVirtual);
                    if (contentState.rgbColor != Integer.MIN_VALUE) {
                        helper.getView(R.id.civ_color).setVisibility(0);
                        ((CircleImageView) helper.getView(R.id.civ_color)).setImageDrawable(new ColorDrawable(contentState.rgbColor));
                    } else {
                        helper.getView(R.id.civ_color).setVisibility(8);
                    }
                    if (!TextUtils.isEmpty(item.getErrorTip())) {
                        helper.setGone(R.id.tv_action_tip, true);
                        helper.setText(R.id.tv_action_tip, item.getErrorTip());
                        if (item.getErrorTip().equals(ActAddLocalScene.this.getString(R.string.not_support_local_tip))) {
                            helper.setEnabled(R.id.v_action, false);
                        }
                    } else {
                        helper.setGone(R.id.tv_action_tip, false);
                    }
                } else {
                    helper.setImageResource(R.id.iv_icon, R.color.transparent).setText(R.id.tv_device_name, "").setText(R.id.tv_state, "");
                }
                if (helper.getBindingAdapterPosition() == ActAddLocalScene.this.mAdapter.getData().size() - 1) {
                    ((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).refreshErrorTip = false;
                }
            }
        };
        this.mAdapter = baseItemDraggableAdapter;
        baseItemDraggableAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda19
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActAddLocalScene.this.lambda$initActionRv$27(baseQuickAdapter, view, i);
            }
        });
        this.mAdapter.setOnItemDragListener(new OnItemDragListener() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene.9
            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int i) {
                viewHolder.itemView.findViewById(R.id.add_scene_colayout).setBackground(ActAddLocalScene.this.getResources().getDrawable(R.drawable.selector_scene_bg));
                viewHolder.itemView.findViewById(R.id.tv_delay_time).setBackground(null);
            }

            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragMoving(RecyclerView.ViewHolder viewHolder, int i, RecyclerView.ViewHolder viewHolder1, int i1) {
                viewHolder.itemView.findViewById(R.id.add_scene_colayout).setBackground(ActAddLocalScene.this.getResources().getDrawable(R.drawable.selector_scene_bg));
                viewHolder.itemView.findViewById(R.id.tv_delay_time).setBackground(null);
            }

            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int i) {
                viewHolder.itemView.findViewById(R.id.add_scene_colayout).setBackgroundColor(ActAddLocalScene.this.getResources().getColor(R.color.white));
                ActAddLocalScene.this.mAdapter.notifyDataSetChanged();
            }
        });
        this.mAdapter.bindToRecyclerView(((ActAddLocalSceneBinding) this.mViewBinding).rvAction);
        ((ActAddLocalSceneBinding) this.mViewBinding).rvAction.setLayoutManager(new LinearLayoutManager(this));
        ((ActAddLocalSceneBinding) this.mViewBinding).rvAction.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActAddLocalSceneBinding) this.mViewBinding).rvAction.getItemAnimator()).setSupportsChangeAnimations(false);
        if (((ActAddLocalSceneBinding) this.mViewBinding).rvAction.getFooterCount() == 0) {
            ItemTextAddBinding itemTextAddBinding = (ItemTextAddBinding) DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_text_add, (ViewGroup) ((ActAddLocalSceneBinding) this.mViewBinding).rvAction.getParent(), false);
            this.textAddBinding = itemTextAddBinding;
            itemTextAddBinding.setImageRes(Integer.valueOf(R.mipmap.ic_add_red));
            this.textAddBinding.setItem(getString(R.string.add_action));
            this.textAddBinding.setTextColor(Integer.valueOf(ContextCompat.getColor(this, R.color.color_text_black)));
            this.textAddBinding.setClickCommand(new BindingCommand<>(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda20
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActAddLocalScene.this.showAddDialog();
                }
            }));
            ((ActAddLocalSceneBinding) this.mViewBinding).rvAction.addFooterView(this.textAddBinding.getRoot());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionRv$27(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (this.editMode) {
            int id = view.getId();
            if (id == R.id.tv_delay_time) {
                editTimeSpace(i, this.mAdapter.getData().get(i).getDelayTime(((ActAddLocalSceneVM) this.mViewModel).scene.isDynamic()));
            } else {
                if (id != R.id.v_action) {
                    return;
                }
                editAction(i);
            }
        }
    }

    private void showEditNameDialog() {
        EditDialog.asDefault().setContent(((ActAddLocalSceneVM) this.mViewModel).sceneName.getValue()).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda21
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActAddLocalScene.this.lambda$showEditNameDialog$30((String) obj);
            }
        }).showDialog(getSupportFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$30(final String str) {
        ((ActAddLocalSceneVM) this.mViewModel).sceneName.setValue(str);
        ((ObservableSubscribeProxy) Injection.net().updateSceneName(((ActAddLocalSceneVM) this.mViewModel).sceneId, str, ((ActAddLocalSceneVM) this.mViewModel).sceneIconPos.getValue().intValue()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda15
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddLocalScene.this.lambda$showEditNameDialog$28((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda16
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActAddLocalScene.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda17
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddLocalScene.this.lambda$showEditNameDialog$29(str, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$28(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$29(String str, Object obj) throws Exception {
        Injection.repo().scene().updateSceneName(((ActAddLocalSceneVM) this.mViewModel).sceneId, str);
    }

    private void editAction(int position) {
        ((ActAddLocalSceneVM) this.mViewModel).editPosition = position;
        Scene.SceneContent sceneContent = this.mAdapter.getData().get(position);
        SceneHelper.instance().reset();
        SceneHelper.instance().automationAction = true;
        if (MaskType.isGroupAction(sceneContent.getActionType())) {
            GroupParam groupParam = (GroupParam) sceneContent.getExecuteCommand(GroupParam.class);
            SceneHelper.instance().selectInstruct = groupParam.instruct;
            NavUtils.Builder goSelectLocalAction = SceneHelper.instance().goSelectLocalAction(((ActAddLocalSceneVM) this.mViewModel).getGroup(groupParam.groupid));
            SceneHelper.instance().maskType = MaskType.LOCAL;
            if (goSelectLocalAction != null) {
                goSelectLocalAction.withLong(Constants.PLACE_ID, ((ActAddLocalSceneVM) this.mViewModel).placeId).withDefaultRequestCode().navigation(this);
                return;
            }
            return;
        }
        if (MaskType.isDeviceAction(sceneContent.getActionType())) {
            DeviceParam deviceParam = (DeviceParam) sceneContent.getExecuteCommand(DeviceParam.class);
            SceneHelper.instance().selectDeviceParam = deviceParam;
            SceneHelper.instance().selectInstruct = deviceParam.instruct;
            Device device = ((ActAddLocalSceneVM) this.mViewModel).getDevice(deviceParam.deviceid);
            if (device != null) {
                if (ProductRepository.isSuperPanel(device.getProductId())) {
                    if (deviceParam.option != null && deviceParam.option.equals("3")) {
                        SceneHelper.instance().maskType = MaskType.LOCAL;
                        SceneHelper.instance().controlObject = device;
                        NavUtils.destination(ActSelectSuperPanelMusic.class).withLong(Constants.CONTROL_ID, device.getId()).withBoolean(Constants.IS_LOCAL_SCENE, true).withString(Constants.SCENE_INSTRUCT, deviceParam.instruct).withString(Constants.SCENE_OPTION_VALUE, deviceParam.optionvalue).withString(SceneHelper.SONG_NAMES, deviceParam.songNames).withDefaultRequestCode().navigation(this);
                        return;
                    }
                    if (deviceParam.option != null && deviceParam.option.equals("4")) {
                        SceneHelper.instance().maskType = MaskType.LOCAL;
                        SceneHelper.instance().controlObject = device;
                        NavUtils.destination(ActSelectSuperPanelSecurity.class).withLong(Constants.CONTROL_ID, device.getId()).withBoolean(Constants.IS_LOCAL_SCENE, true).withString(Constants.SCENE_INSTRUCT, deviceParam.instruct).withString(Constants.SCENE_OPTION_VALUE, deviceParam.optionvalue).withDefaultRequestCode().navigation(this);
                        return;
                    }
                } else if (device.getProductId().equals(ProductId.CG485_SUB_DEVICE)) {
                    SceneHelper.instance().maskType = MaskType.LOCAL;
                    SceneHelper.instance().controlObject = device;
                    Cg485Helper.showSceneActionDialog(this, device, true);
                    return;
                }
                NavUtils.Builder goSelectLocalAction2 = SceneHelper.instance().goSelectLocalAction(device);
                SceneHelper.instance().maskType = MaskType.LOCAL;
                if (goSelectLocalAction2 != null) {
                    goSelectLocalAction2.withLong(Constants.PLACE_ID, ((ActAddLocalSceneVM) this.mViewModel).placeId).withString(SceneHelper.SCENE_PARAM_EXT, deviceParam.sceneParamExt).withDefaultRequestCode().navigation(this);
                    return;
                }
                return;
            }
            return;
        }
        if (MaskType.isAutomationAction(sceneContent.getActionType())) {
            SceneHelper.instance().controlObject = ((ActAddLocalSceneVM) this.mViewModel).getCurActionAutomation();
            SceneHelper.goSelectAction(this, 3, ((ActAddLocalSceneVM) this.mViewModel).placeId);
        } else if (MaskType.isSceneAction(sceneContent.getActionType())) {
            SceneParam sceneParam = (SceneParam) sceneContent.getExecuteCommand(SceneParam.class);
            SceneHelper.instance().selectInstruct = sceneParam.instruct;
            Scene scene = ((ActAddLocalSceneVM) this.mViewModel).getScene(sceneParam.sceneid);
            if (scene != null) {
                SceneHelper.instance().controlObject = Collections.singletonList(scene);
                NavUtils.destination(ActSelectDaliScene.class).withLong("device_id", scene.getMacdeviceid()).withLong(Constants.PLACE_ID, ((ActAddLocalSceneVM) this.mViewModel).placeId).withDefaultRequestCode().navigation(this);
            }
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
        if (((ActAddLocalSceneVM) this.mViewModel).scene.isDynamic()) {
            ArrayList arrayList3 = new ArrayList();
            arrayList3.add("000");
            for (int i3 = 1; i3 < 10; i3++) {
                arrayList3.add(String.valueOf(i3 * 100));
            }
            TimeSelectWithMsDialog withUnit = TimeSelectWithMsDialog.asDefault().setTitle(getString(R.string.delay_time)).setMinList(arrayList).setSecList(arrayList2).setMsList(arrayList3).withUnit(true);
            int i4 = sec / 10;
            withUnit.setSelectMinPosition(i4 / 60).setSelectSecPosition(i4 % 60).setSelectMsPosition(sec % 10).setSelectListener(new TimeSelectWithMsDialog.SelectListener() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda10
                @Override // com.ltech.smarthome.view.dialog.TimeSelectWithMsDialog.SelectListener
                public final void confirm(int i5, int i6, int i7) {
                    ActAddLocalScene.this.lambda$editTimeSpace$31(position, i5, i6, i7);
                }
            }).showDialog(this);
            return;
        }
        int i5 = sec / 10;
        TimeSelectDialog.asDefault().setTitle(getString(R.string.delay_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(true).setMinUnit(getString(R.string.min)).setSecUnit(getString(R.string.sec)).setSelectMinPosition(i5 / 60).setSelectSecPosition(i5 % 60).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda12
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i6, int i7) {
                ActAddLocalScene.this.lambda$editTimeSpace$32(position, i6, i7);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$editTimeSpace$31(int i, int i2, int i3, int i4) {
        int intValue = ((ActAddLocalSceneVM) this.mViewModel).totalSceneTime.getValue().intValue() - ((ActAddLocalSceneVM) this.mViewModel).actionList.get(i).getDelayTime(true);
        int i5 = (((i2 * 60) + i3) * 10) + i4;
        ((ActAddLocalSceneVM) this.mViewModel).actionList.get(i).setDelayTime(i5);
        ((ActAddLocalSceneVM) this.mViewModel).totalSceneTime.setValue(Integer.valueOf(intValue + i5));
        ((ActAddLocalSceneVM) this.mViewModel).check(false);
        this.mAdapter.notifyItemChanged(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$editTimeSpace$32(int i, int i2, int i3) {
        int intValue = ((ActAddLocalSceneVM) this.mViewModel).totalSceneTime.getValue().intValue() - ((ActAddLocalSceneVM) this.mViewModel).actionList.get(i).getDelayTime(false);
        int i4 = ((i2 * 60) + i3) * 10;
        ((ActAddLocalSceneVM) this.mViewModel).actionList.get(i).setDelayTime(i4);
        ((ActAddLocalSceneVM) this.mViewModel).totalSceneTime.setValue(Integer.valueOf(intValue + i4));
        ((ActAddLocalSceneVM) this.mViewModel).check(false);
        this.mAdapter.notifyItemChanged(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAddDialog() {
        final List asList;
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.device));
        if (((ActAddLocalSceneVM) this.mViewModel).scene.isDynamic()) {
            asList = null;
        } else {
            arrayList.add(getString(R.string.dali_scene));
            if (ApiConstants.isChinaNode()) {
                arrayList.add(getString(R.string.voice_speak));
            }
            arrayList.add(getString(R.string.play_music));
            arrayList.add(getString(R.string.security_scene));
            asList = ApiConstants.isChinaNode() ? Arrays.asList(0, 1, 2, 3, 4) : Arrays.asList(0, 1, 3, 4);
        }
        SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda23
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActAddLocalScene.this.lambda$showAddDialog$33(asList, (Integer) obj);
            }
        }).setSelectList(arrayList).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAddDialog$33(List list, Integer num) {
        ((ActAddLocalSceneVM) this.mViewModel).editPosition = -1;
        SceneHelper.instance().reset();
        SceneHelper.instance().initFloorRoom(((ActAddLocalSceneVM) this.mViewModel).floorId, ((ActAddLocalSceneVM) this.mViewModel).roomId);
        if (list != null) {
            num = (Integer) list.get(num.intValue());
        }
        int intValue = num.intValue();
        if (intValue == 1) {
            NavUtils.destination(ActSelectCgdPro.class).withLong(Constants.PLACE_ID, ((ActAddLocalSceneVM) this.mViewModel).placeId).withDefaultRequestCode().navigation(this);
            return;
        }
        if (intValue == 2) {
            SceneHelper.goSelectVoiceAndMusic(this, 10, ((ActAddLocalSceneVM) this.mViewModel).placeId, ((ActAddLocalSceneVM) this.mViewModel).sceneNum);
            return;
        }
        if (intValue == 3) {
            SceneHelper.goSelectVoiceAndMusic(this, 11, ((ActAddLocalSceneVM) this.mViewModel).placeId, ((ActAddLocalSceneVM) this.mViewModel).sceneNum);
        } else if (intValue == 4) {
            SceneHelper.goSelectVoiceAndMusic(this, 12, ((ActAddLocalSceneVM) this.mViewModel).placeId, ((ActAddLocalSceneVM) this.mViewModel).sceneNum);
        } else {
            SceneHelper.goSelectAction(this, 9, ((ActAddLocalSceneVM) this.mViewModel).placeId, ((ActAddLocalSceneVM) this.mViewModel).sceneNum, ((ActAddLocalSceneVM) this.mViewModel).scene.isDynamic());
        }
    }

    private void showDelDialog() {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.tip_del_scene)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$$ExternalSyntheticLambda18
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showDelDialog$34;
                lambda$showDelDialog$34 = ActAddLocalScene.this.lambda$showDelDialog$34(baseDialog, view);
                return lambda$showDelDialog$34;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showDelDialog$34(BaseDialog baseDialog, View view) {
        ((ActAddLocalSceneVM) this.mViewModel).delScene();
        return false;
    }

    private void saveAction(final boolean onActivityResult) {
        ((ObservableSubscribeProxy) Observable.create(new ObservableOnSubscribe<Boolean>() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene.11
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                List<Scene.SceneContent> convert2LocalSceneContents = SceneHelper.instance().convert2LocalSceneContents();
                if (convert2LocalSceneContents.isEmpty()) {
                    return;
                }
                if (onActivityResult) {
                    SceneHelper.instance().closeLightDevice(convert2LocalSceneContents, ActAddLocalScene.this);
                }
                if (((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).editPosition != -1) {
                    convert2LocalSceneContents.get(0).setDelayTime(((Scene.SceneContent) ActAddLocalScene.this.mAdapter.getData().get(((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).editPosition)).getDelayTime(((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).scene.isDynamic()));
                    if (SceneHelper.instance().isCgdProAction) {
                        ActAddLocalScene.this.mAdapter.remove(((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).editPosition);
                        ActAddLocalScene.this.mAdapter.addData(((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).editPosition, (Collection) convert2LocalSceneContents);
                    } else {
                        ActAddLocalScene.this.mAdapter.setData(((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).editPosition, convert2LocalSceneContents.get(0));
                    }
                } else {
                    ActAddLocalScene.this.mAdapter.addData((Collection) convert2LocalSceneContents);
                }
                emitter.onNext(true);
                emitter.onComplete();
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new DisposableObserver<Boolean>() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene.10
            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onNext(Boolean aBoolean) {
                ((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).check(false);
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                ((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).refreshErrorTip = true;
                ActAddLocalScene.this.mAdapter.notifyDataSetChanged();
                ActAddLocalScene actAddLocalScene = ActAddLocalScene.this;
                actAddLocalScene.getTotalTime(((ActAddLocalSceneVM) actAddLocalScene.mViewModel).actionList);
                ((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).isShowEditLayout.setValue(true);
                ActAddLocalScene.this.editMode = true;
                ActAddLocalScene actAddLocalScene2 = ActAddLocalScene.this;
                actAddLocalScene2.inOrOutEditMode(actAddLocalScene2.editMode);
            }
        });
    }

    private void showEditRoomDialog() {
        if (((ActAddLocalSceneVM) this.mViewModel).roomPickHelper.getCanSetRoom().getValue().booleanValue()) {
            RoomSelectDialog.asDefault().setFloorList(((ActAddLocalSceneVM) this.mViewModel).roomPickHelper.getCurrentFloorNames()).setRoomList(((ActAddLocalSceneVM) this.mViewModel).roomPickHelper.getCurrentRoomNames()).setSaveText(getString(R.string.save)).setSelectFloorPosition(((ActAddLocalSceneVM) this.mViewModel).roomPickHelper.getSelectFloorPosition()).setSelectRoomPosition(((ActAddLocalSceneVM) this.mViewModel).roomPickHelper.getSelectRoomPosition()).setSelectListener(new AnonymousClass12()).showDialog(this);
        }
    }

    /* renamed from: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$12, reason: invalid class name */
    class AnonymousClass12 implements RoomSelectDialog.SelectListener {
        AnonymousClass12() {
        }

        @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
        public void confirm(int floorPosition, int roomPosition) {
            ((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).roomPickHelper.setSelectPosition(floorPosition, roomPosition);
            ((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).floorId = ((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).roomPickHelper.getSelectFloorId();
            ((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).roomId = ((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).roomPickHelper.getSelectRoomId();
            ((ObservableSubscribeProxy) Injection.net().updateSceneRoom(((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).sceneId, ((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).floorId, ((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).roomId).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$12$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActAddLocalScene.AnonymousClass12.this.lambda$confirm$0((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$12$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActAddLocalScene.AnonymousClass12.this.lambda$confirm$1();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(ActAddLocalScene.this.activity, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalScene$12$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActAddLocalScene.AnonymousClass12.this.lambda$confirm$2(obj);
                }
            }, new SmartErrorComsumer());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$confirm$0(Disposable disposable) throws Exception {
            ActAddLocalScene.this.showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$confirm$1() throws Exception {
            ActAddLocalScene.this.dismissLoadingDialog();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$confirm$2(Object obj) throws Exception {
            Injection.repo().scene().updateSceneRoom(((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).sceneId, ((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).floorId, ((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).roomId);
            ((ActAddLocalSceneBinding) ActAddLocalScene.this.mViewBinding).tvRoomName.setText(Injection.repo().home().getRoomName(((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).floorId, ((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).roomId));
        }

        @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
        public void onFloorSelect(RoomSelectDialog dialog, int floorPosition) {
            dialog.setRoomList(((ActAddLocalSceneVM) ActAddLocalScene.this.mViewModel).roomPickHelper.getRoomNames(floorPosition));
            dialog.notifyDialog();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            saveAction(true);
            return;
        }
        if (3016 == resultCode) {
            importAction(data.getIntExtra("type", 0));
        } else {
            if (3002 != resultCode || data == null) {
                return;
            }
            ((ActAddLocalSceneVM) this.mViewModel).sceneIconPos.setValue(Integer.valueOf(data.getIntExtra(Constants.ICON_POSITION, 0)));
        }
    }

    private void importAction(int importType) {
        List<Scene.SceneContent> convert2ImportList;
        if (importType == 1) {
            convert2ImportList = SceneHelper.instance().convert2ImportListWithAction(true);
        } else {
            convert2ImportList = SceneHelper.instance().convert2ImportList(this, true);
        }
        if (convert2ImportList.size() > 0) {
            ((ActAddLocalSceneVM) this.mViewModel).isShowEditLayout.setValue(true);
        } else {
            ((ActAddLocalSceneVM) this.mViewModel).isShowEditLayout.setValue(false);
        }
        this.editMode = true;
        inOrOutEditMode(true);
        ((ActAddLocalSceneVM) this.mViewModel).actionList = convert2ImportList;
        getTotalTime(convert2ImportList);
        ((ActAddLocalSceneVM) this.mViewModel).refreshErrorTip = true;
        this.mAdapter.setNewData(((ActAddLocalSceneVM) this.mViewModel).actionList);
        initEditRvMenu();
    }
}