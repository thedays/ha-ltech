package com.ltech.smarthome.ui.device.dalipro;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActDaliSceneSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.CgdProLightExtParam;
import com.ltech.smarthome.model.device_param.CgdProSceneExtParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.model.scene_param.LocalDeviceParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSetting;
import com.ltech.smarthome.ui.scene.ActSelectIcon;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.RoomSelectDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes4.dex */
public class ActDaliSceneSetting extends VMActivity<ActDaliSceneSettingBinding, ActDaliSceneSettingVM> {
    private BaseQuickAdapter<Scene.SceneContent, BaseViewHolder> mAdapter;
    private Scene scene;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_dali_scene_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.setting));
        ((ActDaliSceneSettingVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActDaliSceneSettingVM) this.mViewModel).sceneId = getIntent().getLongExtra(Constants.SCENE_ID, 0L);
        this.scene = Injection.repo().scene().getSceneBySceneId(((ActDaliSceneSettingVM) this.mViewModel).sceneId);
        ((ActDaliSceneSettingVM) this.mViewModel).floorId = this.scene.getFloorId();
        ((ActDaliSceneSettingVM) this.mViewModel).roomId = this.scene.getRoomId();
        ((ActDaliSceneSettingBinding) this.mViewBinding).tvRoomName.setText(Injection.repo().home().getRoomName(((ActDaliSceneSettingVM) this.mViewModel).floorId, ((ActDaliSceneSettingVM) this.mViewModel).roomId));
        ((ActDaliSceneSettingVM) this.mViewModel).sceneName.setValue(this.scene.getSceneName());
        ((ActDaliSceneSettingVM) this.mViewModel).sceneIconPos.setValue(Integer.valueOf(this.scene.getIconPos()));
        if (this.scene.getSceneContents() != null) {
            initSceneContent(this.scene.getSceneContents());
        } else {
            initSceneContent(new ArrayList());
        }
        ((ActDaliSceneSettingBinding) this.mViewBinding).tvSceneNumber.setText(String.valueOf(this.scene.getSceneNum() == 0 ? 0 : this.scene.getSceneNum() - 1));
        ((ActDaliSceneSettingVM) this.mViewModel).isAddToSmart.setValue(Boolean.valueOf(DaliProHelper.isSceneVisible(this.scene)));
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(this.scene.getMacdeviceid());
        if (deviceByDeviceId != null) {
            ((ActDaliSceneSettingBinding) this.mViewBinding).tvGatewayName.setText(deviceByDeviceId.getDeviceName());
        }
        ((ActDaliSceneSettingBinding) this.mViewBinding).sbAddToSmart.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSetting$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                ActDaliSceneSetting.this.lambda$initView$0(switchButton, z);
            }
        });
        ((ActDaliSceneSettingBinding) this.mViewBinding).sbAddToCommon.setChecked(this.scene.isCommon());
        ((ActDaliSceneSettingBinding) this.mViewBinding).sbAddToCommon.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSetting$$ExternalSyntheticLambda12
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                ActDaliSceneSetting.this.lambda$initView$1(switchButton, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(SwitchButton switchButton, boolean z) {
        if (this.scene.getExtParam(CgdProSceneExtParam.class) != null) {
            CgdProSceneExtParam cgdProSceneExtParam = (CgdProSceneExtParam) this.scene.getExtParam(CgdProSceneExtParam.class);
            cgdProSceneExtParam.setDaliHidden(!z ? 1 : 0);
            ((ActDaliSceneSettingVM) this.mViewModel).updateParamExt(this.scene, cgdProSceneExtParam);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(SwitchButton switchButton, boolean z) {
        setCommon(z);
    }

    private void setCommon(final boolean isCommon) {
        ((ObservableSubscribeProxy) Injection.net().setCommonScene(this.scene.getSceneId(), isCommon).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycle(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSetting$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDaliSceneSetting.this.lambda$setCommon$2(isCommon, obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSetting$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDaliSceneSetting.this.lambda$setCommon$3(isCommon, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCommon$2(boolean z, Object obj) throws Exception {
        this.scene.setCommon(z);
        Injection.repo().scene().saveScene(this.scene);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCommon$3(boolean z, Throwable th) throws Exception {
        ((ActDaliSceneSettingBinding) this.mViewBinding).sbAddToCommon.setCheckedNotByUser(!z);
    }

    private void initSceneContent(List<Scene.SceneContent> list) {
        BaseQuickAdapter<Scene.SceneContent, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Scene.SceneContent, BaseViewHolder>(R.layout.item_cgd_action, list) { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSetting.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Scene.SceneContent sceneContent) {
                LocalDeviceParam localDeviceParam = (LocalDeviceParam) sceneContent.getExecuteCommand(LocalDeviceParam.class);
                Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(localDeviceParam.deviceid);
                helper.setText(R.id.tv_name, deviceByDeviceId.getName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(deviceByDeviceId)).setGone(R.id.tv_action, true).setText(R.id.tv_add, String.valueOf(((CgdProLightExtParam) deviceByDeviceId.getExtParam(CgdProLightExtParam.class)).getDaliAddr()));
                helper.itemView.setBackgroundResource(R.drawable.shape_bg_round_bg_10);
                String lightAction = DaliProHelper.getLightAction(ActDaliSceneSetting.this.activity, localDeviceParam.option, localDeviceParam.instruct, deviceByDeviceId);
                helper.setGone(R.id.group_color, lightAction.startsWith(MqttTopic.MULTI_LEVEL_WILDCARD));
                if (lightAction.startsWith(MqttTopic.MULTI_LEVEL_WILDCARD)) {
                    ((CircleImageView) helper.getView(R.id.civ_color)).setImageDrawable(new ColorDrawable(Color.rgb(Integer.parseInt(lightAction.substring(1, 3), 16), Integer.parseInt(lightAction.substring(3, 5), 16), Integer.parseInt(lightAction.substring(5, 7), 16))));
                    lightAction = lightAction.substring(7);
                }
                helper.setText(R.id.tv_action, lightAction);
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.bindToRecyclerView(((ActDaliSceneSettingBinding) this.mViewBinding).rvDaliScene);
        ((ActDaliSceneSettingBinding) this.mViewBinding).rvDaliScene.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSetting.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(5.0f), 0, ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(8.0f));
            }
        });
        ((ActDaliSceneSettingBinding) this.mViewBinding).rvDaliScene.setLayoutManager(new GridLayoutManager(this, 4));
        ((ActDaliSceneSettingBinding) this.mViewBinding).rvDaliScene.setHasFixedSize(true);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActDaliSceneSettingVM) this.mViewModel).roomPickHelper.startObserve(this, ((ActDaliSceneSettingVM) this.mViewModel).placeId, ((ActDaliSceneSettingVM) this.mViewModel).roomId);
        ((ActDaliSceneSettingVM) this.mViewModel).sceneIconPos.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSetting$$ExternalSyntheticLambda13
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliSceneSetting.this.lambda$startObserve$4((Integer) obj);
            }
        });
        ((ActDaliSceneSettingVM) this.mViewModel).request = Injection.repo().scene().getSceneContent(this, ((ActDaliSceneSettingVM) this.mViewModel).sceneId);
        handleData(((ActDaliSceneSettingVM) this.mViewModel).request, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSetting$$ExternalSyntheticLambda14
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDaliSceneSetting.this.lambda$startObserve$5((List) obj);
            }
        });
        ((ActDaliSceneSettingVM) this.mViewModel).showEditNameDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSetting$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliSceneSetting.this.lambda$startObserve$6((Void) obj);
            }
        });
        ((ActDaliSceneSettingVM) this.mViewModel).changeRoomEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSetting$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliSceneSetting.this.lambda$startObserve$7((Void) obj);
            }
        });
        ((ActDaliSceneSettingVM) this.mViewModel).changeIconEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSetting$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliSceneSetting.this.lambda$startObserve$8((Void) obj);
            }
        });
        ((ActDaliSceneSettingVM) this.mViewModel).editEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSetting$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliSceneSetting.this.lambda$startObserve$9((Void) obj);
            }
        });
        ((ActDaliSceneSettingVM) this.mViewModel).isAddToSmart.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSetting$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliSceneSetting.this.lambda$startObserve$10((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Integer num) {
        ((ActDaliSceneSettingBinding) this.mViewBinding).ivIcon.setImageResource(SceneHelper.getSceneIcon(this, num.intValue()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(List list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        this.scene = (Scene) list.get(0);
        initDataView(((Scene) list.get(0)).getSceneContents());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Void r1) {
        showEditNameDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(Void r1) {
        showEditRoomDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$8(Void r4) {
        NavUtils.destination(ActSelectIcon.class).withInt(Constants.ICON_POSITION, ((ActDaliSceneSettingVM) this.mViewModel).sceneIconPos.getValue().intValue()).withLong(Constants.SCENE_ID, ((ActDaliSceneSettingVM) this.mViewModel).sceneId).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$9(Void r4) {
        NavUtils.destination(ActEditDaliScene.class).withLong(Constants.SCENE_ID, ((ActDaliSceneSettingVM) this.mViewModel).sceneId).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$10(Boolean bool) {
        if (bool.booleanValue() || !this.scene.isCommon()) {
            return;
        }
        setCommon(false);
    }

    private void initDataView(List<Scene.SceneContent> sceneContents) {
        if (sceneContents != null) {
            this.mAdapter.replaceData(sceneContents);
        } else {
            this.mAdapter.replaceData(new ArrayList());
        }
    }

    private void showEditNameDialog() {
        EditDialog.asDefault().setContent(((ActDaliSceneSettingVM) this.mViewModel).sceneName.getValue()).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDaliSceneSetting.this.lambda$showEditNameDialog$13((String) obj);
            }
        }).showDialog(getSupportFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$13(final String str) {
        ((ActDaliSceneSettingVM) this.mViewModel).sceneName.setValue(str);
        ((ObservableSubscribeProxy) Injection.net().updateSceneName(((ActDaliSceneSettingVM) this.mViewModel).sceneId, str, ((ActDaliSceneSettingVM) this.mViewModel).sceneIconPos.getValue().intValue()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSetting$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDaliSceneSetting.this.lambda$showEditNameDialog$11((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSetting$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActDaliSceneSetting.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSetting$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDaliSceneSetting.this.lambda$showEditNameDialog$12(str, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$11(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$12(String str, Object obj) throws Exception {
        Injection.repo().scene().updateSceneName(((ActDaliSceneSettingVM) this.mViewModel).sceneId, str);
    }

    private void showEditRoomDialog() {
        if (((ActDaliSceneSettingVM) this.mViewModel).roomPickHelper.canSetRoom()) {
            RoomSelectDialog.asDefault().setFloorList(((ActDaliSceneSettingVM) this.mViewModel).roomPickHelper.getCurrentFloorNames()).setRoomList(((ActDaliSceneSettingVM) this.mViewModel).roomPickHelper.getCurrentRoomNames()).setSelectFloorPosition(((ActDaliSceneSettingVM) this.mViewModel).roomPickHelper.getSelectFloorPosition()).setSelectRoomPosition(((ActDaliSceneSettingVM) this.mViewModel).roomPickHelper.getSelectRoomPosition()).setSelectListener(new AnonymousClass3()).showDialog(this);
        }
    }

    /* renamed from: com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSetting$3, reason: invalid class name */
    class AnonymousClass3 implements RoomSelectDialog.SelectListener {
        AnonymousClass3() {
        }

        @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
        public void confirm(int floorPosition, int roomPosition) {
            ((ActDaliSceneSettingVM) ActDaliSceneSetting.this.mViewModel).roomPickHelper.setSelectPosition(floorPosition, roomPosition);
            ((ActDaliSceneSettingVM) ActDaliSceneSetting.this.mViewModel).floorId = ((ActDaliSceneSettingVM) ActDaliSceneSetting.this.mViewModel).roomPickHelper.getSelectFloorId();
            ((ActDaliSceneSettingVM) ActDaliSceneSetting.this.mViewModel).roomId = ((ActDaliSceneSettingVM) ActDaliSceneSetting.this.mViewModel).roomPickHelper.getSelectRoomId();
            ((ObservableSubscribeProxy) Injection.net().updateSceneRoom(((ActDaliSceneSettingVM) ActDaliSceneSetting.this.mViewModel).sceneId, ((ActDaliSceneSettingVM) ActDaliSceneSetting.this.mViewModel).floorId, ((ActDaliSceneSettingVM) ActDaliSceneSetting.this.mViewModel).roomId).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSetting$3$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActDaliSceneSetting.AnonymousClass3.this.lambda$confirm$0((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSetting$3$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActDaliSceneSetting.AnonymousClass3.this.lambda$confirm$1();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(ActDaliSceneSetting.this.activity, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSetting$3$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActDaliSceneSetting.AnonymousClass3.this.lambda$confirm$2(obj);
                }
            }, new SmartErrorComsumer());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$confirm$0(Disposable disposable) throws Exception {
            ActDaliSceneSetting.this.showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$confirm$1() throws Exception {
            ActDaliSceneSetting.this.dismissLoadingDialog();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$confirm$2(Object obj) throws Exception {
            Injection.repo().scene().updateSceneRoom(((ActDaliSceneSettingVM) ActDaliSceneSetting.this.mViewModel).sceneId, ((ActDaliSceneSettingVM) ActDaliSceneSetting.this.mViewModel).floorId, ((ActDaliSceneSettingVM) ActDaliSceneSetting.this.mViewModel).roomId);
            ((ActDaliSceneSettingBinding) ActDaliSceneSetting.this.mViewBinding).tvRoomName.setText(Injection.repo().home().getRoomName(((ActDaliSceneSettingVM) ActDaliSceneSetting.this.mViewModel).floorId, ((ActDaliSceneSettingVM) ActDaliSceneSetting.this.mViewModel).roomId));
        }

        @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
        public void onFloorSelect(RoomSelectDialog dialog, int floorPosition) {
            dialog.setRoomList(((ActDaliSceneSettingVM) ActDaliSceneSetting.this.mViewModel).roomPickHelper.getRoomNames(floorPosition));
            dialog.notifyDialog();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            Scene sceneBySceneId = Injection.repo().scene().getSceneBySceneId(((ActDaliSceneSettingVM) this.mViewModel).sceneId);
            if (sceneBySceneId.getSceneContents() != null) {
                initDataView(sceneBySceneId.getSceneContents());
                return;
            }
            return;
        }
        if (3002 != resultCode || data == null) {
            return;
        }
        ((ActDaliSceneSettingVM) this.mViewModel).sceneIconPos.setValue(Integer.valueOf(data.getIntExtra(Constants.ICON_POSITION, 0)));
    }
}