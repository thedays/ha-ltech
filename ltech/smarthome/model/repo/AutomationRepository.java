package com.ltech.smarthome.model.repo;

import androidx.lifecycle.LifecycleOwner;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.WrapperResource;
import com.ltech.smarthome.model.bean.Automation;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.AutomationRepository;
import com.ltech.smarthome.model.repo.ifun.IAutomation;
import com.ltech.smarthome.model.repo.ifun.IUser;
import com.ltech.smarthome.net.response.automation.DetailAutomationResponse;
import com.ltech.smarthome.net.response.automation.ListAutomationResponse;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public final class AutomationRepository extends BaseRepository implements IAutomation {
    public AutomationRepository(BoxStore boxStore, RateLimiter limiter, KeyCreator keyCreator, IUser user) {
        super(boxStore, limiter, keyCreator, user);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IAutomation
    public Listing<Automation> getAutomationList(LifecycleOwner owner, long placeId) {
        return getAutomationList(owner, placeId, 3);
    }

    /* renamed from: com.ltech.smarthome.model.repo.AutomationRepository$1, reason: invalid class name */
    class AnonymousClass1 extends WrapperResource<Automation, ListAutomationResponse> {
        final /* synthetic */ int val$automationType;
        final /* synthetic */ LifecycleOwner val$owner;
        final /* synthetic */ long val$placeId;

        AnonymousClass1(final long val$placeId, final int val$automationType, final LifecycleOwner val$owner) {
            this.val$placeId = val$placeId;
            this.val$automationType = val$automationType;
            this.val$owner = val$owner;
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected boolean shouldFetch() {
            return AutomationRepository.this.mLimiter.shouldFetch(AutomationRepository.this.mKeyCreator.automationListKey(this.val$placeId));
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void fetchFail() {
            AutomationRepository.this.mLimiter.reset(AutomationRepository.this.mKeyCreator.automationListKey(this.val$placeId));
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected QueryBuilder<Automation> getDbQueryBuilder() {
            if (this.val$automationType == 3) {
                return AutomationRepository.this.mBoxBuilderFactory.queryAutomationList(AutomationRepository.this.mUser.getUserId(), this.val$placeId);
            }
            return AutomationRepository.this.mBoxBuilderFactory.queryAutomationList(AutomationRepository.this.mUser.getUserId(), this.val$placeId, this.val$automationType);
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void netCall(Observer<ListAutomationResponse> observer) {
            ((ObservableSubscribeProxy) Injection.net().listAutomation(this.val$placeId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.val$owner)))).subscribe(observer);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.model.WrapperResource
        public void saveDataFromNet(final ListAutomationResponse response) {
            AutomationRepository.this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.AutomationRepository$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AutomationRepository.AnonymousClass1.this.lambda$saveDataFromNet$0(response);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$saveDataFromNet$0(ListAutomationResponse listAutomationResponse) {
            Box boxFor = AutomationRepository.this.mBoxStore.boxFor(Automation.class);
            List<Automation> find = getDbQueryBuilder().build().find();
            if (listAutomationResponse.getTotal() > 0) {
                ArrayList arrayList = new ArrayList(listAutomationResponse.getTotal());
                for (ListAutomationResponse.RowsBean rowsBean : listAutomationResponse.getRows()) {
                    Automation automation = new Automation();
                    automation.setCurrentUserId(AutomationRepository.this.mUser.getUserId());
                    automation.setUserId(rowsBean.getUserid());
                    automation.setPlaceId(rowsBean.getPlaceid());
                    automation.setAutomationId(rowsBean.getAutomationid());
                    automation.setName(rowsBean.getAutomationname());
                    automation.setStartTime(rowsBean.getStarttime());
                    automation.setEndTime(rowsBean.getEndtime());
                    automation.setWeeks(rowsBean.getWeeks());
                    automation.setTimeZone(rowsBean.getTimezone());
                    automation.setConditionType(rowsBean.getConditiontype());
                    automation.setEnable(rowsBean.getEnable());
                    automation.setPicIndex(rowsBean.getPicindex());
                    automation.setIndex(rowsBean.getSort());
                    automation.setAutomationType(rowsBean.getAutomationtype());
                    automation.setGatewayDeviceId(rowsBean.getGatewaydeviceid());
                    automation.setCycleindex(rowsBean.getCycleindex());
                    automation.setIntervaltime(rowsBean.getIntervaltime());
                    Iterator<Automation> it = find.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            Automation next = it.next();
                            if (next.equals(automation)) {
                                automation.setId(next.getId());
                                automation.setActions(next.getActions());
                                automation.setConditions(next.getConditions());
                                break;
                            }
                        }
                    }
                    arrayList.add(automation);
                }
                boxFor.put((Collection) arrayList);
                find.removeAll(arrayList);
            }
            boxFor.remove((Collection) find);
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IAutomation
    public Listing<Automation> getAutomationList(LifecycleOwner owner, long placeId, int automationType) {
        return new AnonymousClass1(placeId, automationType, owner).toListing();
    }

    /* renamed from: com.ltech.smarthome.model.repo.AutomationRepository$2, reason: invalid class name */
    class AnonymousClass2 extends WrapperResource<Automation, DetailAutomationResponse> {
        final /* synthetic */ long val$automationId;
        final /* synthetic */ LifecycleOwner val$owner;

        AnonymousClass2(final long val$automationId, final LifecycleOwner val$owner) {
            this.val$automationId = val$automationId;
            this.val$owner = val$owner;
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected boolean shouldFetch() {
            return AutomationRepository.this.mLimiter.shouldFetch(AutomationRepository.this.mKeyCreator.automationKey(this.val$automationId));
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void fetchFail() {
            AutomationRepository.this.mLimiter.reset(AutomationRepository.this.mKeyCreator.automationKey(this.val$automationId));
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected QueryBuilder<Automation> getDbQueryBuilder() {
            return AutomationRepository.this.mBoxBuilderFactory.queryAutomation(AutomationRepository.this.mUser.getUserId(), this.val$automationId);
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void netCall(Observer<DetailAutomationResponse> observer) {
            if (this.val$automationId == 0) {
                return;
            }
            ((ObservableSubscribeProxy) Injection.net().detailAutomation(this.val$automationId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.val$owner)))).subscribe(observer);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.model.WrapperResource
        public void saveDataFromNet(final DetailAutomationResponse response) {
            AutomationRepository.this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.AutomationRepository$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AutomationRepository.AnonymousClass2.this.lambda$saveDataFromNet$0(response);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$saveDataFromNet$0(DetailAutomationResponse detailAutomationResponse) {
            Box boxFor = AutomationRepository.this.mBoxStore.boxFor(Automation.class);
            Automation findFirst = getDbQueryBuilder().build().findFirst();
            if (findFirst == null) {
                findFirst = new Automation();
            }
            findFirst.setCurrentUserId(AutomationRepository.this.mUser.getUserId());
            DetailAutomationResponse.AutomationinfoBean automationinfo = detailAutomationResponse.getAutomationinfo();
            findFirst.setUserId(automationinfo.getUserid());
            findFirst.setPlaceId(automationinfo.getPlaceid());
            findFirst.setAutomationId(automationinfo.getAutomationid());
            findFirst.setIndex(automationinfo.getSort());
            findFirst.setEnable(automationinfo.getEnable());
            findFirst.setStartTime(automationinfo.getStarttime());
            findFirst.setEndTime(automationinfo.getEndtime());
            findFirst.setTimeZone(automationinfo.getTimezone());
            findFirst.setConditionType(automationinfo.getConditiontype());
            findFirst.setPicIndex(automationinfo.getPicindex());
            findFirst.setName(automationinfo.getAutomationname());
            findFirst.setWeeks(automationinfo.getWeeks());
            ArrayList arrayList = new ArrayList();
            for (DetailAutomationResponse.ConditionsBean conditionsBean : detailAutomationResponse.getConditions()) {
                Automation.Condition condition = new Automation.Condition();
                condition.setParamtype(conditionsBean.getParamtype());
                if (conditionsBean.getParams() != null) {
                    condition.setParams(conditionsBean.getParams().replaceAll("null_", ProductId.SPLIT).replaceAll(TmpConstant.GROUP_ROLE_UNKNOWN, "\"\""));
                }
                condition.setEventtype(conditionsBean.getEventtype());
                arrayList.add(condition);
            }
            findFirst.setConditions(arrayList);
            ArrayList arrayList2 = new ArrayList();
            for (DetailAutomationResponse.ActionsBean actionsBean : detailAutomationResponse.getActions()) {
                Automation.Action action = new Automation.Action();
                action.setActiontype(actionsBean.getActiontype());
                action.setActiondelays(actionsBean.getActiondelays());
                if (actionsBean.getParams() != null) {
                    action.setParams(actionsBean.getParams().replaceAll("null_", ProductId.SPLIT).replaceAll(TmpConstant.GROUP_ROLE_UNKNOWN, "\"\""));
                }
                arrayList2.add(action);
            }
            findFirst.setActions(arrayList2);
            boxFor.put((Box) findFirst);
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IAutomation
    public Listing<Automation> getAutomation(LifecycleOwner owner, long automationId) {
        return new AnonymousClass2(automationId, owner).toListing();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IAutomation
    public List<Automation> getAutomationListFromNet(ListAutomationResponse response) {
        ArrayList arrayList = new ArrayList();
        if (response.getTotal() > 0) {
            arrayList = new ArrayList(response.getTotal());
            for (ListAutomationResponse.RowsBean rowsBean : response.getRows()) {
                if (rowsBean != null) {
                    Automation automation = new Automation();
                    automation.setCurrentUserId(this.mUser.getUserId());
                    automation.setUserId(rowsBean.getUserid());
                    automation.setPlaceId(rowsBean.getPlaceid());
                    automation.setAutomationId(rowsBean.getAutomationid());
                    automation.setName(rowsBean.getAutomationname());
                    automation.setStartTime(rowsBean.getStarttime());
                    automation.setEndTime(rowsBean.getEndtime());
                    automation.setWeeks(rowsBean.getWeeks());
                    automation.setTimeZone(rowsBean.getTimezone());
                    automation.setConditionType(rowsBean.getConditiontype());
                    automation.setEnable(rowsBean.getEnable());
                    automation.setPicIndex(rowsBean.getPicindex());
                    automation.setIndex(rowsBean.getSort());
                    automation.setAutomationType(rowsBean.getAutomationtype());
                    automation.setGatewayDeviceId(rowsBean.getGatewaydeviceid());
                    arrayList.add(automation);
                }
            }
        }
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IAutomation
    public List<Automation> getAutomationListByPlaceId(long placeId) {
        return this.mBoxBuilderFactory.queryAutomationList(this.mUser.getUserId(), placeId).build().find();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IAutomation
    public void saveAutomation(final Automation automation) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.AutomationRepository$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                AutomationRepository.this.lambda$saveAutomation$0(automation);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveAutomation$0(Automation automation) {
        automation.setCurrentUserId(this.mUser.getUserId());
        this.mBoxStore.boxFor(Automation.class).put((Box) automation);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IAutomation
    public void sortAutomation(final List<Automation> automationList) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.AutomationRepository$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AutomationRepository.this.lambda$sortAutomation$1(automationList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sortAutomation$1(List list) {
        int size = list.size();
        int i = 0;
        while (i < size) {
            Automation automation = (Automation) list.get(i);
            i++;
            automation.setIndex(i);
        }
        this.mBoxStore.boxFor(Automation.class).put((Collection) list);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IAutomation
    public void removeAutomation(final long automationId) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.AutomationRepository$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AutomationRepository.this.lambda$removeAutomation$2(automationId);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeAutomation$2(long j) {
        List<Automation> find = this.mBoxBuilderFactory.queryAutomation(this.mUser.getUserId(), j).build().find();
        if (find.isEmpty()) {
            return;
        }
        this.mBoxStore.boxFor(Automation.class).remove((Collection) find);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IAutomation
    public boolean isAutomationNameExist(long place, String name) {
        return !this.mBoxBuilderFactory.queryAutomationByName(this.mUser.getUserId(), place, name).build().find().isEmpty();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IAutomation
    public Automation getAutomationById(long automationId) {
        return this.mBoxBuilderFactory.queryAutomation(this.mUser.getUserId(), automationId).build().findFirst();
    }
}