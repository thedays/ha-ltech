package com.ltech.smarthome.ui.control;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.ResourceEmptyLiveData;
import com.ltech.smarthome.model.bean.Automation;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.ui.automation.ActAddAutomation;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.view.SwitchButton;
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
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.functions.Function1;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

/* loaded from: classes4.dex */
public class FtAutomationVM extends BaseViewModel {
    public long place;
    public Listing<Automation> request;
    public List<Automation> allData = new ArrayList();
    public SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();
    public LiveData<Resource<List<Automation>>> automationLiveData = Transformations.switchMap(Injection.repo().home().getSelectPlace(), new Function1() { // from class: com.ltech.smarthome.ui.control.FtAutomationVM$$ExternalSyntheticLambda5
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            LiveData lambda$new$0;
            lambda$new$0 = FtAutomationVM.this.lambda$new$0((Place) obj);
            return lambda$new$0;
        }
    });
    public MutableLiveData<List<Automation>> refreshData = new MutableLiveData<>();
    public RecyclerView.ItemDecoration decoration = new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.control.FtAutomationVM.1
        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0, 0, 0, ConvertUtils.dp2px(16.0f));
        }
    };
    public BindingRecyclerViewAdapter.ItemIds<Automation> itemIds = new BindingRecyclerViewAdapter.ItemIds() { // from class: com.ltech.smarthome.ui.control.FtAutomationVM$$ExternalSyntheticLambda6
        @Override // me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter.ItemIds
        public final long getItemId(int i, Object obj) {
            long automationId;
            automationId = ((Automation) obj).getAutomationId();
            return automationId;
        }
    };
    public ItemBinding<Automation> itemBinding = ItemBinding.of(new OnItemBind() { // from class: com.ltech.smarthome.ui.control.FtAutomationVM$$ExternalSyntheticLambda7
        @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
        public final void onItemBind(ItemBinding itemBinding, int i, Object obj) {
            FtAutomationVM.this.lambda$new$4(itemBinding, i, (Automation) obj);
        }
    });
    public ObservableList<Automation> automationList = new ObservableArrayList();

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$new$0(Place place) {
        if (place == null) {
            return ResourceEmptyLiveData.create();
        }
        Listing<Automation> automationList = Injection.repo().auto().getAutomationList(getLifecycleOwner(), place.getPlaceId());
        this.request = automationList;
        return automationList.data();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$4(ItemBinding itemBinding, final int i, final Automation automation) {
        itemBinding.clearExtras().set(40, R.layout.item_automation).bindExtra(86, Integer.valueOf(automation.getAutomationType() == 2 ? R.drawable.shape_red_bt_5 : R.drawable.shape_blue_bt_5)).bindExtra(4, Integer.valueOf(SceneHelper.getAutomationPic(ActivityUtils.getTopActivity(), automation.getPicIndex()))).bindExtra(85, ActivityUtils.getTopActivity().getString(automation.getAutomationType() == 2 ? R.string.type_local : R.string.type_cloud)).bindExtra(8, new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.control.FtAutomationVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                FtAutomationVM.this.lambda$new$2(i, switchButton, z);
            }
        }).bindExtra(10, new BindingCommand(new BindingConsumer() { // from class: com.ltech.smarthome.ui.control.FtAutomationVM$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                FtAutomationVM.this.lambda$new$3(automation, (View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(int i, SwitchButton switchButton, boolean z) {
        setAutomationEnable(i, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(Automation automation, View view) {
        if (view.getId() != R.id.layout_item_bg) {
            return;
        }
        if (getCurrentPlace().isOwner() || getCurrentPlace().isManager()) {
            Injection.limiter().reset(Injection.keyCreator().automationKey(automation.getAutomationId()));
            navigation(NavUtils.destination(ActAddAutomation.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withInt(Constants.ICON_POSITION, automation.getPicIndex()).withString(Constants.AUTOMATION_NAME, automation.getName()).withLong(Constants.AUTOMATION_ID, automation.getAutomationId()));
        } else {
            this.showNoPermissionDialogEvent.call();
        }
    }

    public String getAutomationName(Context context) {
        if (this.automationList.isEmpty()) {
            return context.getString(R.string.automation) + 1;
        }
        String str = "";
        boolean z = true;
        int i = 0;
        while (z) {
            i++;
            str = context.getString(R.string.automation) + i;
            Iterator<Automation> it = this.automationList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                if (it.next().getName().equals(str)) {
                    z = true;
                    break;
                }
            }
        }
        return str;
    }

    private void setAutomationEnable(int position, boolean enable) {
        setAutomationEnable(position, this.automationList.get(position), enable);
    }

    public void setAutomationEnable(final int position, final Automation automation, final boolean enable) {
        ((ObservableSubscribeProxy) Injection.net().enableAutomation(automation.getAutomationId(), enable).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtAutomationVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtAutomationVM.this.lambda$setAutomationEnable$5((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtAutomationVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtAutomationVM.this.lambda$setAutomationEnable$6(automation, enable, obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.control.FtAutomationVM$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtAutomationVM.this.lambda$setAutomationEnable$7(position, automation, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAutomationEnable$5(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAutomationEnable$6(Automation automation, boolean z, Object obj) throws Exception {
        automation.setEnable(z);
        Injection.repo().auto().saveAutomation(automation);
        showSuccessTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAutomationEnable$7(int i, Automation automation, Throwable th) throws Exception {
        this.automationList.set(i, automation);
        showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    public void search(final String keyword) {
        ((ObservableSubscribeProxy) Observable.create(new ObservableOnSubscribe<List<Automation>>() { // from class: com.ltech.smarthome.ui.control.FtAutomationVM.3
            boolean inSearchMode = false;

            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<List<Automation>> emitter) throws Exception {
                ArrayList arrayList = new ArrayList();
                List<Automation> automationListByPlaceId = Injection.repo().auto().getAutomationListByPlaceId(FtAutomationVM.this.place);
                boolean z = keyword.length() > 0;
                this.inSearchMode = z;
                if (z) {
                    for (Automation automation : automationListByPlaceId) {
                        if (automation.getName().toLowerCase().contains(keyword.toLowerCase())) {
                            arrayList.add(automation);
                        }
                    }
                }
                emitter.onNext(arrayList);
                emitter.onComplete();
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new DisposableObserver<List<Automation>>() { // from class: com.ltech.smarthome.ui.control.FtAutomationVM.2
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onNext(List<Automation> items) {
                FtAutomationVM.this.refreshData.setValue(items);
            }
        });
    }
}