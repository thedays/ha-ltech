package com.ltech.smarthome.ui.device.dalipro;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.CgdProSceneExtParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.utils.RoomPickHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActDaliSceneSettingVM extends BaseViewModel {
    public long floorId;
    public long placeId;
    public Listing<Scene> request;
    public long roomId;
    public long sceneId;
    public RoomPickHelper roomPickHelper = new RoomPickHelper();
    public MutableLiveData<String> sceneName = new MutableLiveData<>("");
    public MutableLiveData<Integer> sceneIconPos = new MutableLiveData<>(0);
    public MutableLiveData<Boolean> isAddToSmart = new MutableLiveData<>();
    public SingleLiveEvent<Void> showEditNameDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> changeRoomEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> changeIconEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> editEvent = new SingleLiveEvent<>();
    public BindingCommand<View> clickCommand = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSettingVM$$ExternalSyntheticLambda1
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActDaliSceneSettingVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.btn_edit /* 2131296491 */:
                this.editEvent.call();
                break;
            case R.id.layout_change_icon /* 2131297387 */:
                this.changeIconEvent.call();
                break;
            case R.id.layout_change_room /* 2131297392 */:
                this.changeRoomEvent.call();
                break;
            case R.id.layout_scene_name /* 2131297615 */:
                this.showEditNameDialogEvent.call();
                break;
        }
    }

    public void updateParamExt(final Scene scene, final CgdProSceneExtParam extParam) {
        ((ObservableSubscribeProxy) Injection.net().updateSceneExtParam(scene.getSceneId(), GsonUtils.toJson(extParam)).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSettingVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDaliSceneSettingVM.this.lambda$updateParamExt$1(scene, extParam, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateParamExt$1(Scene scene, CgdProSceneExtParam cgdProSceneExtParam, Object obj) throws Exception {
        scene.setExtParam(cgdProSceneExtParam);
        Injection.repo().scene().saveScene(scene);
        this.isAddToSmart.setValue(Boolean.valueOf(!cgdProSceneExtParam.isDaliHidden()));
    }
}