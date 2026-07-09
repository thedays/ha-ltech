package com.ltech.smarthome.ui.control;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.ResourceEmptyLiveData;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.ui.newselect.FtRoomSceneVM;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class FtSceneVM extends FtRoomSceneVM {
    public MutableLiveData<List<Scene>> refreshData = new MutableLiveData<>();
    public List<Scene> allData = new ArrayList();
    public SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();
    public LiveData<Resource<List<Scene>>> sceneLiveData = Transformations.switchMap(Injection.repo().home().getSelectPlace(), new Function1() { // from class: com.ltech.smarthome.ui.control.FtSceneVM$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            LiveData lambda$new$0;
            lambda$new$0 = FtSceneVM.this.lambda$new$0((Place) obj);
            return lambda$new$0;
        }
    });
    public RecyclerView.ItemDecoration decoration = new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.control.FtSceneVM.1
        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(ConvertUtils.dp2px(8.0f), 0, ConvertUtils.dp2px(8.0f), ConvertUtils.dp2px(16.0f));
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$new$0(Place place) {
        if (place == null) {
            return ResourceEmptyLiveData.create();
        }
        this.request = Injection.repo().scene().getSceneList(getLifecycleOwner(), place.getPlaceId(), 3);
        return this.request.data();
    }

    public void executeLocalScene(Context context, int sceneId) {
        CmdAssistant.getSceneCmdAssistant(null, new int[0]).executeLocalScene(context, sceneId);
        showSuccessTipDialog(ActivityUtils.getTopActivity().getString(R.string.execute_success));
    }

    public void executeCloudScene(Scene scene) {
        ((ObservableSubscribeProxy) Injection.net().executeScene(scene.getSceneId()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtSceneVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtSceneVM.this.lambda$executeCloudScene$1((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtSceneVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtSceneVM.this.lambda$executeCloudScene$2(obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.control.FtSceneVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtSceneVM.this.lambda$executeCloudScene$3((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$executeCloudScene$1(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.executing));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$executeCloudScene$2(Object obj) throws Exception {
        showSuccessTipDialog(ActivityUtils.getTopActivity().getString(R.string.execute_success));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$executeCloudScene$3(Throwable th) throws Exception {
        showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.execute_fail));
    }

    public void search(final String keyword) {
        ((ObservableSubscribeProxy) Observable.create(new ObservableOnSubscribe<List<Scene>>() { // from class: com.ltech.smarthome.ui.control.FtSceneVM.3
            boolean inSearchMode = false;

            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<List<Scene>> emitter) throws Exception {
                ArrayList arrayList = new ArrayList();
                this.inSearchMode = keyword.length() > 0;
                List<Scene> allSceneByPlaceId = Injection.repo().scene().getAllSceneByPlaceId(FtSceneVM.this.placeId.getValue().longValue());
                if (this.inSearchMode) {
                    for (Scene scene : allSceneByPlaceId) {
                        if (scene.getSceneName().toLowerCase().contains(keyword.toLowerCase())) {
                            arrayList.add(scene);
                        }
                    }
                }
                emitter.onNext(arrayList);
                emitter.onComplete();
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new DisposableObserver<List<Scene>>() { // from class: com.ltech.smarthome.ui.control.FtSceneVM.2
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onNext(List<Scene> items) {
                FtSceneVM.this.refreshData.setValue(items);
            }
        });
    }

    public String getSceneName(Context context, boolean isLocal) {
        String str = "";
        boolean z = true;
        int i = 0;
        while (z) {
            i++;
            StringBuilder sb = new StringBuilder();
            sb.append(context.getString(isLocal ? R.string.local_scene : R.string.cloud_scene));
            sb.append(i);
            str = sb.toString();
            z = Injection.repo().scene().isSceneNameExist(Injection.repo().home().getSelectPlace().getValue().getPlaceId(), str);
        }
        return str;
    }

    public Role getRoleByRoleId(long roleId) {
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(roleId);
        return deviceByDeviceId != null ? deviceByDeviceId : Injection.repo().group().getGroupByGroupId(roleId);
    }
}