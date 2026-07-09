package com.ltech.smarthome.ui.my;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.push.AppNoticeListResponse;
import com.ltech.smarthome.push.AppNotice;
import com.ltech.smarthome.push.PushContentParamKey;
import com.ltech.smarthome.utils.RxUtils;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;
import me.tatarka.bindingcollectionadapter2.collections.MergeObservableList;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class FtMessageNoticeVM extends BaseViewModel {
    public List<AppNotice> appNoticeList = new ArrayList();
    public MutableLiveData<Boolean> updateDataList = new MutableLiveData<>();
    public SingleLiveEvent<Void> loadMoreDataFinishEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> loadTotalDataEvent = new SingleLiveEvent<>();
    public ObservableList<AppNotice> mAppNoticeList = new ObservableArrayList();
    public MergeObservableList<Object> dataList = new MergeObservableList().insertList(this.mAppNoticeList);
    public int pageNum = 1;
    public boolean isLoadTotal = false;
    public final OnItemBind<Object> itemBinding = new OnItemBind<Object>(this) { // from class: com.ltech.smarthome.ui.my.FtMessageNoticeVM.1
        @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
        public void onItemBind(ItemBinding itemBinding, int position, Object item) {
            int color;
            if (String.class.equals(item.getClass())) {
                itemBinding.set(30, R.layout.item_message_data_footer);
                return;
            }
            if (AppNotice.class.equals(item.getClass())) {
                AppNotice appNotice = (AppNotice) item;
                ItemBinding bindExtra = itemBinding.set(3, R.layout.item_message_notice).bindExtra(13, appNotice.getContent());
                if (appNotice.getMessagetype() == 24) {
                    color = ActivityUtils.getTopActivity().getResources().getColor(R.color.state_abnormal_text);
                } else {
                    color = ActivityUtils.getTopActivity().getResources().getColor(R.color.color_text_gray);
                }
                bindExtra.bindExtra(81, Integer.valueOf(color)).bindExtra(15, appNotice.getCreateTime());
            }
        }
    };

    public void getAppNoticeList() {
        ((ObservableSubscribeProxy) Injection.net().getAppNoticeList(1).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.my.FtMessageNoticeVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtMessageNoticeVM.this.lambda$getAppNoticeList$0((AppNoticeListResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getAppNoticeList$0(AppNoticeListResponse appNoticeListResponse) throws Exception {
        LHomeLog.i(getClass(), "res -->" + appNoticeListResponse);
        handleData(appNoticeListResponse);
    }

    public void getMoreAppNoticeList(int pagenum) {
        ((ObservableSubscribeProxy) Injection.net().getAppNoticeList(pagenum).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.my.FtMessageNoticeVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtMessageNoticeVM.this.lambda$getMoreAppNoticeList$1((AppNoticeListResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getMoreAppNoticeList$1(AppNoticeListResponse appNoticeListResponse) throws Exception {
        LHomeLog.i(getClass(), "res -->" + appNoticeListResponse);
        handleMoreData(appNoticeListResponse);
    }

    private void handleData(AppNoticeListResponse response) {
        this.mAppNoticeList.clear();
        if (response.getRows().size() == 0 || response.getTotal() == 0) {
            this.updateDataList.setValue(false);
            return;
        }
        if (response.getTotal() > response.getRows().size()) {
            this.isLoadTotal = false;
            this.loadTotalDataEvent.postValue(false);
        } else {
            this.loadTotalDataEvent.postValue(true);
            this.isLoadTotal = true;
        }
        for (int i = 0; i < response.getRows().size(); i++) {
            AppNoticeListResponse.RowData rowData = response.getRows().get(i);
            LHomeLog.i(getClass(), rowData.getContent());
            AppNotice appNotice = new AppNotice(rowData.getCreatetime(), rowData.getContent());
            if (rowData.getParam() != null && !rowData.getParam().isEmpty()) {
                try {
                    appNotice.setMessagetype(new JSONObject(rowData.getParam()).getInt(PushContentParamKey.MESSAGE_TYPE));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            this.appNoticeList.add(appNotice);
            this.mAppNoticeList.add(appNotice);
        }
        if (this.mAppNoticeList.isEmpty()) {
            showEmpty();
        }
        this.updateDataList.setValue(true);
    }

    private void handleMoreData(AppNoticeListResponse response) {
        if (response.getRows().size() == 0 || response.getTotal() == 0) {
            showEmpty();
            this.loadMoreDataFinishEvent.call();
            return;
        }
        if (response.getTotal() > ((this.pageNum - 1) * 20) + response.getRows().size()) {
            this.isLoadTotal = false;
            this.loadTotalDataEvent.postValue(false);
        } else {
            this.isLoadTotal = true;
            this.loadTotalDataEvent.postValue(true);
        }
        for (int i = 0; i < response.getRows().size(); i++) {
            AppNoticeListResponse.RowData rowData = response.getRows().get(i);
            LHomeLog.i(getClass(), rowData.getContent());
            AppNotice appNotice = new AppNotice(rowData.getCreatetime(), rowData.getContent());
            this.appNoticeList.add(appNotice);
            this.mAppNoticeList.add(appNotice);
        }
        if (this.mAppNoticeList.isEmpty()) {
            showEmpty();
        }
        this.loadMoreDataFinishEvent.call();
    }
}