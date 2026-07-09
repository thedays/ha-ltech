package com.ltech.smarthome.ui.group;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.BaseSortActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSortGroup extends BaseSortActivity<Group> {
    private Listing<Group> groupListing;
    private long placeId;

    @Override // com.ltech.smarthome.base.BaseSortActivity
    protected int itemLayout() {
        return R.layout.item_sort;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        this.placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        Listing<Group> groupList = Injection.repo().group().getGroupList(this, this.placeId);
        this.groupListing = groupList;
        handleData(groupList, new IAction() { // from class: com.ltech.smarthome.ui.group.ActSortGroup$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSortGroup.this.lambda$startObserve$0((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(List list) {
        if (this.dataList.isEmpty()) {
            setDataList(list);
        }
    }

    @Override // com.ltech.smarthome.base.BaseSortActivity
    protected List<Group> getItemList() {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseSortActivity
    public void convertView(BaseViewHolder helper, Group item) {
        helper.setText(R.id.tv_name, item.getGroupName());
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseSortActivity
    protected void saveData() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.dataList.size(); i++) {
            LHomeLog.i(getClass(), " groupid ->>" + ((Group) this.dataList.get(i)).getGroupId());
            arrayList.add(Long.valueOf(((Group) this.dataList.get(i)).getGroupId()));
        }
        ((ObservableSubscribeProxy) Injection.net().sortGroup(arrayList).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSortGroup$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSortGroup.this.lambda$saveData$1((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.group.ActSortGroup$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSortGroup.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSortGroup$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSortGroup.this.lambda$saveData$2(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveData$1(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveData$2(Object obj) throws Exception {
        Injection.repo().group().sortGroup(this.dataList);
        finishActivity();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        this.groupListing.retry();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.no_group));
    }
}