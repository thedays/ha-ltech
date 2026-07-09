package com.ltech.smarthome.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.WrapperResource;
import com.ltech.smarthome.net.SmartErrorComsumer;
import io.objectbox.android.ObjectBoxLiveData;
import io.objectbox.query.QueryBuilder;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class WrapperResource<ResultType, ResponseType> {
    private SingleLiveEvent<Void> refresh = new SingleLiveEvent<>();
    private MediatorLiveData<Resource<List<ResultType>>> result;

    protected void fetchFail() {
    }

    protected abstract QueryBuilder<ResultType> getDbQueryBuilder();

    protected abstract void netCall(Observer<ResponseType> observer);

    protected abstract void saveDataFromNet(ResponseType response);

    protected abstract boolean shouldFetch();

    public WrapperResource() {
        MediatorLiveData<Resource<List<ResultType>>> mediatorLiveData = new MediatorLiveData<>();
        this.result = mediatorLiveData;
        mediatorLiveData.addSource(this.refresh, new androidx.lifecycle.Observer() { // from class: com.ltech.smarthome.model.WrapperResource$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                WrapperResource.this.lambda$new$1((Void) obj);
            }
        });
        this.refresh.call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(Void r3) {
        ObjectBoxLiveData<ResultType> loadFromDb = loadFromDb();
        if (shouldFetch()) {
            fetchFromNet(loadFromDb);
        } else {
            this.result.addSource(loadFromDb, new androidx.lifecycle.Observer() { // from class: com.ltech.smarthome.model.WrapperResource$$ExternalSyntheticLambda0
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    WrapperResource.this.lambda$new$0((List) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(List list) {
        setValue(Resource.success(list));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setValue(Resource<List<ResultType>> newValue) {
        if (this.result.getValue() != newValue) {
            this.result.postValue(newValue);
        }
    }

    /* renamed from: com.ltech.smarthome.model.WrapperResource$1, reason: invalid class name */
    class AnonymousClass1 implements Observer<ResponseType> {
        final /* synthetic */ ObjectBoxLiveData val$dbsource;

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable d2) {
        }

        AnonymousClass1(final ObjectBoxLiveData val$dbsource) {
            this.val$dbsource = val$dbsource;
        }

        @Override // io.reactivex.Observer
        public void onNext(ResponseType responseType) {
            WrapperResource.this.saveDataFromNet(responseType);
        }

        @Override // io.reactivex.Observer
        public void onError(final Throwable e) {
            WrapperResource.this.fetchFail();
            WrapperResource.this.result.addSource(this.val$dbsource, new androidx.lifecycle.Observer() { // from class: com.ltech.smarthome.model.WrapperResource$1$$ExternalSyntheticLambda1
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    WrapperResource.AnonymousClass1.this.lambda$onError$0(e, (List) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$0(Throwable th, List list) {
            if (list != null) {
                WrapperResource.this.setValue(Resource.success(list));
            }
            new SmartErrorComsumer(this) { // from class: com.ltech.smarthome.model.WrapperResource.1.1
                @Override // com.ltech.smarthome.net.SmartErrorComsumer
                protected void action(Throwable throwable) {
                }
            }.accept(th);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onComplete$1(List list) {
            WrapperResource.this.setValue(Resource.success(list));
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            WrapperResource.this.result.addSource(this.val$dbsource, new androidx.lifecycle.Observer() { // from class: com.ltech.smarthome.model.WrapperResource$1$$ExternalSyntheticLambda0
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    WrapperResource.AnonymousClass1.this.lambda$onComplete$1((List) obj);
                }
            });
        }
    }

    private void fetchFromNet(ObjectBoxLiveData<ResultType> dbsource) {
        netCall(new AnonymousClass1(dbsource));
    }

    private ObjectBoxLiveData<ResultType> loadFromDb() {
        return new ObjectBoxLiveData<>(getDbQueryBuilder().build());
    }

    public LiveData<Resource<List<ResultType>>> asLiveData() {
        return this.result;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$toListing$2() {
        this.refresh.call();
    }

    public Listing<ResultType> toListing() {
        return new Listing<>(asLiveData(), new Runnable() { // from class: com.ltech.smarthome.model.WrapperResource$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                WrapperResource.this.lambda$toListing$2();
            }
        });
    }

    private boolean isConnectOuterNet() {
        return Injection.state().isConnectOuterNet();
    }
}