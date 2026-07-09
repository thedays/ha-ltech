package com.ltech.smarthome.ui.circadianlighting;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.mode.ListModeResponse;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/* loaded from: classes4.dex */
public class ActLightPlanListVM extends BaseViewModel {
    private boolean batch;
    private long controlId;
    private boolean groupControl;
    private int mCurPlanId;
    private boolean on;
    private int repeat;
    public ObservableList<GoItem> mObservableList = new ObservableArrayList();
    private Map<Integer, String> syncData = new HashMap();
    private Map<Integer, String> nameData = new HashMap();
    public ItemBinding<GoItem> itemBinding = ItemBinding.of(40, R.layout.item_go);

    public void initList(List<ListModeResponse.RowsBean> list) {
        this.syncData.clear();
        this.nameData.clear();
        if (list.size() > 0) {
            for (ListModeResponse.RowsBean rowsBean : list) {
                this.syncData.put(Integer.valueOf(rowsBean.getModeindex()), rowsBean.getContent());
                this.nameData.put(Integer.valueOf(rowsBean.getModeindex()), rowsBean.getModename());
            }
        }
        final NavUtils.Builder withRequestCode = NavUtils.destination(ActLightPlanDetail.class).withBoolean(Constants.LIGHT_RHYTHMS_ON_OFF, this.on).withInt(Constants.LIGHT_RHYTHMS_REPEAT, this.repeat).withLong(Constants.CONTROL_ID, this.controlId).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withInt(Constants.LIGHT_RHYTHMS_PLAN_ID_CUR, this.mCurPlanId).withRequestCode(5009);
        if (this.batch) {
            withRequestCode.withBoolean(Constants.LIGHT_CIRCADIAN_LIGHTING_BATCH, true);
        }
        this.mObservableList.add(new GoItem().setMainText((!this.nameData.containsKey(1) || this.nameData.get(1) == null) ? ActivityUtils.getTopActivity().getString(R.string.app_light_plan_mode1) : this.nameData.get(1)).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanListVM$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActLightPlanListVM.this.lambda$initList$0(withRequestCode);
            }
        })));
        this.mObservableList.add(new GoItem().setMainText((!this.nameData.containsKey(2) || this.nameData.get(2) == null) ? ActivityUtils.getTopActivity().getString(R.string.app_light_plan_mode2) : this.nameData.get(2)).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanListVM$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActLightPlanListVM.this.lambda$initList$1(withRequestCode);
            }
        })));
        this.mObservableList.add(new GoItem().setMainText((!this.nameData.containsKey(3) || this.nameData.get(3) == null) ? ActivityUtils.getTopActivity().getString(R.string.app_light_plan_mode3) : this.nameData.get(3)).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanListVM$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActLightPlanListVM.this.lambda$initList$2(withRequestCode);
            }
        })));
        this.mObservableList.add(new GoItem().setMainText((!this.nameData.containsKey(4) || this.nameData.get(4) == null) ? ActivityUtils.getTopActivity().getString(R.string.app_light_plan_mode4) : this.nameData.get(4)).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanListVM$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActLightPlanListVM.this.lambda$initList$3(withRequestCode);
            }
        })));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initList$0(NavUtils.Builder builder) {
        navigation(builder.withString(Constants.LIGHT_RHYTHMS_ONLINE_PLAN, GsonUtils.toJson(this.syncData)).withString(Constants.MODE_NAME, this.mObservableList.get(0).getMainText()).withInt(Constants.LIGHT_RHYTHMS_PLAN_ID, 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initList$1(NavUtils.Builder builder) {
        navigation(builder.withString(Constants.LIGHT_RHYTHMS_ONLINE_PLAN, GsonUtils.toJson(this.syncData)).withString(Constants.MODE_NAME, this.mObservableList.get(1).getMainText()).withInt(Constants.LIGHT_RHYTHMS_PLAN_ID, 2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initList$2(NavUtils.Builder builder) {
        navigation(builder.withString(Constants.LIGHT_RHYTHMS_ONLINE_PLAN, GsonUtils.toJson(this.syncData)).withString(Constants.MODE_NAME, this.mObservableList.get(2).getMainText()).withInt(Constants.LIGHT_RHYTHMS_PLAN_ID, 3));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initList$3(NavUtils.Builder builder) {
        navigation(builder.withString(Constants.LIGHT_RHYTHMS_ONLINE_PLAN, GsonUtils.toJson(this.syncData)).withString(Constants.MODE_NAME, this.mObservableList.get(3).getMainText()).withInt(Constants.LIGHT_RHYTHMS_PLAN_ID, 4));
    }

    public void setCurPlanId(int id) {
        this.mCurPlanId = id;
    }

    public void setControlId(long id) {
        this.controlId = id;
    }

    public void setGroupControl(boolean b2) {
        this.groupControl = b2;
    }

    public void setIsOn(boolean on) {
        this.on = on;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public void syncList() {
        Place value = Injection.repo().home().getSelectPlace().getValue();
        if (value != null) {
            ((ObservableSubscribeProxy) Injection.net().getModeList(value.getPlaceId(), 2, 3).delaySubscription(200L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanListVM$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActLightPlanListVM.this.lambda$syncList$4((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanListVM$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActLightPlanListVM.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanListVM$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActLightPlanListVM.this.lambda$syncList$5((ListModeResponse) obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncList$4(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.mode_adding));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncList$5(ListModeResponse listModeResponse) throws Exception {
        this.syncData.clear();
        if (listModeResponse.getTotal() > 0) {
            for (ListModeResponse.RowsBean rowsBean : listModeResponse.getRows()) {
                this.syncData.put(Integer.valueOf(rowsBean.getModeindex()), rowsBean.getContent());
                this.nameData.put(Integer.valueOf(rowsBean.getModeindex()), rowsBean.getModename());
                int modeindex = rowsBean.getModeindex() - 1;
                GoItem goItem = this.mObservableList.get(modeindex);
                goItem.setMainText(rowsBean.getModename());
                this.mObservableList.set(modeindex, goItem);
            }
        }
    }

    public void setBatch(boolean batch) {
        this.batch = batch;
    }

    public boolean getBatch() {
        return this.batch;
    }
}