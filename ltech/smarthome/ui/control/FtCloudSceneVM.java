package com.ltech.smarthome.ui.control;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.ResourceEmptyLiveData;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.observers.DisposableObserver;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class FtCloudSceneVM extends BaseViewModel {
    public long place;
    public Listing<Scene> request;
    public MutableLiveData<List<Scene>> refreshData = new MutableLiveData<>();
    public List<Scene> allData = new ArrayList();
    public boolean isLocal = false;
    public SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();
    public LiveData<Resource<List<Scene>>> sceneLiveData = Transformations.switchMap(Injection.repo().home().getSelectPlace(), new Function1() { // from class: com.ltech.smarthome.ui.control.FtCloudSceneVM$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            LiveData lambda$new$0;
            lambda$new$0 = FtCloudSceneVM.this.lambda$new$0((Place) obj);
            return lambda$new$0;
        }
    });
    public RecyclerView.ItemDecoration decoration = new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.control.FtCloudSceneVM.1
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
        Listing<Scene> sceneList = Injection.repo().scene().getSceneList(getLifecycleOwner(), place.getPlaceId(), 1);
        this.request = sceneList;
        return sceneList.data();
    }

    public String getSceneName(Context context) {
        String str = "";
        boolean z = true;
        int i = 0;
        while (z) {
            i++;
            str = context.getString(R.string.cloud_scene) + i;
            z = Injection.repo().scene().isSceneNameExist(Injection.repo().home().getSelectPlace().getValue().getPlaceId(), str);
        }
        return str;
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    public void search(final String keyword) {
        ((ObservableSubscribeProxy) Observable.create(new ObservableOnSubscribe<List<Scene>>() { // from class: com.ltech.smarthome.ui.control.FtCloudSceneVM.3
            boolean inSearchMode = false;

            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<List<Scene>> emitter) throws Exception {
                ArrayList arrayList = new ArrayList();
                this.inSearchMode = keyword.length() > 0;
                List<Scene> sceneListByPlaceId = Injection.repo().scene().getSceneListByPlaceId(FtCloudSceneVM.this.place, FtCloudSceneVM.this.isLocal);
                if (this.inSearchMode) {
                    for (Scene scene : sceneListByPlaceId) {
                        if (scene.getSceneName().toLowerCase().contains(keyword.toLowerCase())) {
                            arrayList.add(scene);
                        }
                    }
                }
                emitter.onNext(arrayList);
                emitter.onComplete();
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new DisposableObserver<List<Scene>>() { // from class: com.ltech.smarthome.ui.control.FtCloudSceneVM.2
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onNext(List<Scene> items) {
                FtCloudSceneVM.this.refreshData.setValue(items);
            }
        });
    }
}