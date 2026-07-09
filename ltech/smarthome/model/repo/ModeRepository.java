package com.ltech.smarthome.model.repo;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.WrapperResource;
import com.ltech.smarthome.model.bean.ModeContent;
import com.ltech.smarthome.model.repo.ifun.IMode;
import com.ltech.smarthome.model.repo.ifun.IUser;
import com.ltech.smarthome.net.response.mode.ListModeResponse;
import com.ltech.smarthome.singleton.KeyCreator;
import com.ltech.smarthome.singleton.RateLimiter;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.QueryBuilder;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes4.dex */
public class ModeRepository extends BaseRepository implements IMode {
    public ModeRepository(BoxStore boxStore, RateLimiter limiter, KeyCreator keyCreator, IUser user) {
        super(boxStore, limiter, keyCreator, user);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IMode
    public Listing<ModeContent> getModeList(final LifecycleOwner owner, final long placeId, final int deviceType, final int modeType) {
        return new WrapperResource<ModeContent, ListModeResponse>() { // from class: com.ltech.smarthome.model.repo.ModeRepository.1
            @Override // com.ltech.smarthome.model.WrapperResource
            protected boolean shouldFetch() {
                return true;
            }

            @Override // com.ltech.smarthome.model.WrapperResource
            protected QueryBuilder<ModeContent> getDbQueryBuilder() {
                return ModeRepository.this.mBoxBuilderFactory.queryModeList(Injection.repo().user().getUserId(), deviceType, modeType);
            }

            @Override // com.ltech.smarthome.model.WrapperResource
            protected void netCall(Observer<ListModeResponse> observer) {
                ((ObservableSubscribeProxy) Injection.net().getModeList(placeId, deviceType, modeType).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY)))).subscribe(observer);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.model.WrapperResource
            public void saveDataFromNet(ListModeResponse response) {
                ModeRepository.this.saveMode(response, deviceType, modeType);
            }
        }.toListing();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IMode
    public void getAllModeFromNet(LifecycleOwner owner) {
        ((ObservableSubscribeProxy) Injection.net().getModeList(0L, 0, 0).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.model.repo.ModeRepository$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ModeRepository.this.lambda$getAllModeFromNet$0((ListModeResponse) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getAllModeFromNet$0(ListModeResponse listModeResponse) throws Exception {
        saveMode(listModeResponse, 0, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveMode(final ListModeResponse response, final int deviceType, final int modeType) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.ModeRepository$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ModeRepository.this.lambda$saveMode$1(deviceType, modeType, response);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveMode$1(int i, int i2, ListModeResponse listModeResponse) {
        Box boxFor = this.mBoxStore.boxFor(ModeContent.class);
        List<ModeContent> find = this.mBoxBuilderFactory.queryModeList(Injection.repo().user().getUserId(), i, i2).build().find();
        if (listModeResponse.getTotal() > 0) {
            ArrayList arrayList = new ArrayList(listModeResponse.getTotal());
            for (ListModeResponse.RowsBean rowsBean : listModeResponse.getRows()) {
                ModeContent modeContent = new ModeContent();
                modeContent.setLightModeId(rowsBean.getLightmodeid());
                modeContent.setPlaceId(rowsBean.getPlaceid());
                modeContent.setUserId(rowsBean.getUserid());
                modeContent.setDeviceType(rowsBean.getDevicetype());
                modeContent.setModeName(rowsBean.getModename());
                modeContent.setModeIndex(rowsBean.getModeindex());
                modeContent.setModeType(rowsBean.getModetype());
                modeContent.setContent(rowsBean.getContent());
                modeContent.setPlayTimes(rowsBean.getPlaytimes());
                modeContent.setPicIndex(rowsBean.getPicindex());
                arrayList.add(modeContent);
            }
            boxFor.put((Collection) arrayList);
            find.removeAll(arrayList);
        }
        boxFor.remove((Collection) find);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IMode
    public List<ModeContent> getModeListFromDb(int deviceType, int modeType) {
        return this.mBoxBuilderFactory.queryModeList(Injection.repo().user().getUserId(), deviceType, modeType).build().find();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IMode
    public ModeContent getModeById(long modeId) {
        return this.mBoxBuilderFactory.queryModeById(Injection.repo().user().getUserId(), modeId).build().findFirst();
    }
}