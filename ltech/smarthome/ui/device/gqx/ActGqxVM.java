package com.ltech.smarthome.ui.device.gqx;

import android.text.TextUtils;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.extra.TabContentdefault;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.device.KeyInfoResponse;
import com.ltech.smarthome.singleton.ComboCmdHelper;
import com.ltech.smarthome.ui.device.gqx.ActGqxVM;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.param.BleNetworkCmdParam;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActGqxVM extends BaseViewModel {
    public long controlId;
    public int controlType;
    public Device device;
    public String productId;
    public SingleLiveEvent<Object> controlObject = new SingleLiveEvent<>();
    public List<TabContentdefault> tabs = new ArrayList();
    public MutableLiveData<Integer> index = new MutableLiveData<>(0);
    public SingleLiveEvent<Object> bindEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> noPermissionBindEvent = new SingleLiveEvent<>();
    public MutableLiveData<String> keyName1 = new MutableLiveData<>(StringUtils.getString(R.string.no_bind));
    public MutableLiveData<String> keyName2 = new MutableLiveData<>(StringUtils.getString(R.string.no_bind));
    public MutableLiveData<String> keyName3 = new MutableLiveData<>(StringUtils.getString(R.string.no_bind));
    public MutableLiveData<String> keyName4 = new MutableLiveData<>(StringUtils.getString(R.string.no_bind));
    public MutableLiveData<Object> uiEvent = new MutableLiveData<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxVM$$ExternalSyntheticLambda4
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActGqxVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_bind_1 /* 2131297367 */:
                if (Injection.repo().home().getSelectPlace().getValue().isMember()) {
                    this.noPermissionBindEvent.call();
                    break;
                }
                break;
            case R.id.layout_bind_2 /* 2131297368 */:
                if (Injection.repo().home().getSelectPlace().getValue().isMember()) {
                    this.noPermissionBindEvent.call();
                    break;
                } else {
                    showDeviceKnobActionDialog(ComboCmdHelper.getInstance().getRelateObject(getSelIndex() + 1));
                    break;
                }
            case R.id.layout_bind_3 /* 2131297369 */:
                if (Injection.repo().home().getSelectPlace().getValue().isMember()) {
                    this.noPermissionBindEvent.call();
                    break;
                } else {
                    showDeviceLongClickActionDialog(ComboCmdHelper.getInstance().getRelateObject(getSelIndex() + 1));
                    break;
                }
            default:
                switch (id) {
                    case R.id.tv_btn1 /* 2131298497 */:
                        if (this.index.getValue() != null && this.index.getValue().intValue() == 0) {
                            this.bindEvent.call();
                            break;
                        } else {
                            this.index.setValue(0);
                            break;
                        }
                        break;
                    case R.id.tv_btn2 /* 2131298498 */:
                        if (this.index.getValue() != null && this.index.getValue().intValue() == 1) {
                            this.bindEvent.call();
                            break;
                        } else {
                            this.index.setValue(1);
                            break;
                        }
                        break;
                    case R.id.tv_btn3 /* 2131298499 */:
                        if (this.index.getValue() != null && this.index.getValue().intValue() == 2) {
                            this.bindEvent.call();
                            break;
                        } else {
                            this.index.setValue(2);
                            break;
                        }
                        break;
                    case R.id.tv_btn4 /* 2131298500 */:
                        if (this.index.getValue() != null && this.index.getValue().intValue() == 3) {
                            this.bindEvent.call();
                            break;
                        } else {
                            this.index.setValue(3);
                            break;
                        }
                        break;
                }
        }
    }

    public void initTabList() {
        this.tabs.clear();
        this.tabs.add(new TabContentdefault(R.string.gqx_main_b1, FtGqxAction.create(0)));
        this.tabs.add(new TabContentdefault(R.string.gqx_main_b2, FtGqxAction.create(1)));
        this.tabs.add(new TabContentdefault(R.string.gqx_main_b3, FtGqxAction.create(2)));
        this.tabs.add(new TabContentdefault(R.string.gqx_main_b4, FtGqxAction.create(3)));
    }

    public Place getCurPlace() {
        return Injection.repo().home().getSelPlace();
    }

    public void refreshRelateInfoList() {
        Device deviceById = Injection.repo().device().getDeviceById(this.controlId);
        this.device = deviceById;
        this.controlObject.setValue(deviceById);
        ((ObservableSubscribeProxy) Injection.net().getKeyInfo(this.device.getDeviceId()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxVM$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGqxVM.this.lambda$refreshRelateInfoList$1((Disposable) obj);
            }
        }).doFinally(new ActGqxVM$$ExternalSyntheticLambda7(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxVM$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGqxVM.this.lambda$refreshRelateInfoList$2((KeyInfoResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshRelateInfoList$1(Disposable disposable) throws Exception {
        showLoadingDialog("");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshRelateInfoList$2(KeyInfoResponse keyInfoResponse) throws Exception {
        ComboCmdHelper.getInstance().setKeyData(keyInfoResponse.getRows());
        Object relateObject = ComboCmdHelper.getInstance().getRelateObject(1);
        String name = relateObject instanceof Role ? ((Role) relateObject).getName() : "";
        Object relateObject2 = ComboCmdHelper.getInstance().getRelateObject(2);
        String name2 = relateObject2 instanceof Role ? ((Role) relateObject2).getName() : "";
        Object relateObject3 = ComboCmdHelper.getInstance().getRelateObject(3);
        String name3 = relateObject3 instanceof Role ? ((Role) relateObject3).getName() : "";
        Object relateObject4 = ComboCmdHelper.getInstance().getRelateObject(4);
        String name4 = relateObject4 instanceof Role ? ((Role) relateObject4).getName() : "";
        MutableLiveData<String> mutableLiveData = this.keyName1;
        if (name == null || TextUtils.isEmpty(name)) {
            name = StringUtils.getString(R.string.no_bind);
        }
        mutableLiveData.postValue(name);
        MutableLiveData<String> mutableLiveData2 = this.keyName2;
        if (name2 == null || TextUtils.isEmpty(name2)) {
            name2 = StringUtils.getString(R.string.no_bind);
        }
        mutableLiveData2.postValue(name2);
        MutableLiveData<String> mutableLiveData3 = this.keyName3;
        if (name3 == null || TextUtils.isEmpty(name3)) {
            name3 = StringUtils.getString(R.string.no_bind);
        }
        mutableLiveData3.postValue(name3);
        MutableLiveData<String> mutableLiveData4 = this.keyName4;
        if (name4 == null || TextUtils.isEmpty(name4)) {
            name4 = StringUtils.getString(R.string.no_bind);
        }
        mutableLiveData4.postValue(name4);
        this.uiEvent.postValue(true);
    }

    public int getSelIndex() {
        if (this.index.getValue() != null) {
            return this.index.getValue().intValue();
        }
        return 0;
    }

    public boolean isBind() {
        Object relateObject = ComboCmdHelper.getInstance().getRelateObject(getSelIndex() + 1);
        return (this.index.getValue() == null || ComboCmdHelper.getInstance().getKeysByZone(getSelIndex() + 1).isEmpty() || ComboCmdHelper.getInstance().getKeysByZone(getSelIndex() + 1).get(0).getObjectId() == 0 || TextUtils.isEmpty(relateObject instanceof Role ? ((Role) relateObject).getName() : "")) ? false : true;
    }

    public void uploadData(final String name) {
        ((ObservableSubscribeProxy) Injection.net().bindKeyInfo(this.device.getDeviceId(), ComboCmdHelper.getInstance().getKeysByZone(getSelIndex() + 1)).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGqxVM.this.lambda$uploadData$3((Disposable) obj);
            }
        }).doFinally(new ActGqxVM$$ExternalSyntheticLambda7(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGqxVM.this.lambda$uploadData$4(name, (List) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$3(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$4(String str, List list) throws Exception {
        int selIndex = getSelIndex();
        if (selIndex == 0) {
            this.keyName1.setValue(str);
        } else if (selIndex == 1) {
            this.keyName2.setValue(str);
        } else if (selIndex == 2) {
            this.keyName3.setValue(str);
        } else if (selIndex == 3) {
            this.keyName4.setValue(str);
        }
        ComboCmdHelper.getInstance().setKeyData(getSelIndex() + 1, list);
        this.uiEvent.setValue(true);
    }

    public void unBindData() {
        ((ObservableSubscribeProxy) Injection.net().unbindKeyInfo(this.device.getDeviceId(), getSelIndex() + 1).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxVM$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGqxVM.this.lambda$unBindData$5((Disposable) obj);
            }
        }).doFinally(new ActGqxVM$$ExternalSyntheticLambda7(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxVM$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGqxVM.this.lambda$unBindData$6(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unBindData$5(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unBindData$6(Object obj) throws Exception {
        int selIndex = getSelIndex();
        if (selIndex == 0) {
            this.keyName1.setValue(getContext().getString(R.string.no_bind));
        } else if (selIndex == 1) {
            this.keyName2.setValue(getContext().getString(R.string.no_bind));
        } else if (selIndex == 2) {
            this.keyName3.setValue(getContext().getString(R.string.no_bind));
        } else if (selIndex == 3) {
            this.keyName4.setValue(getContext().getString(R.string.no_bind));
        }
        this.uiEvent.setValue(true);
        showSuccessTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
    }

    public void uploadData(int type) {
        Observable<Object> bindKeyInfo;
        dismissLoadingDialog();
        if (type == 0) {
            bindKeyInfo = Injection.net().bindKeyInfo(this.device.getDeviceId(), ComboCmdHelper.getInstance().getKeyShort(getSelIndex() + 1));
        } else if (type == 1) {
            bindKeyInfo = Injection.net().bindKeyInfo(this.device.getDeviceId(), ComboCmdHelper.getInstance().getKeyKnob(getSelIndex() + 1));
        } else {
            bindKeyInfo = type == 2 ? Injection.net().bindKeyInfo(this.device.getDeviceId(), ComboCmdHelper.getInstance().getKeyLong(getSelIndex() + 1)) : null;
        }
        if (bindKeyInfo != null) {
            ((ObservableSubscribeProxy) bindKeyInfo.compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxVM$$ExternalSyntheticLambda11
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActGqxVM.this.lambda$uploadData$7((Disposable) obj);
                }
            }).doFinally(new ActGqxVM$$ExternalSyntheticLambda7(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxVM$$ExternalSyntheticLambda12
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActGqxVM.this.lambda$uploadData$8(obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$7(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$8(Object obj) throws Exception {
        showSuccessTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
    }

    public void showDeviceShortClickActionDialog(final Object o) {
        final List<ComboCmdHelper.Action> dimShortClickActionList;
        int lightColorType = ProductRepository.getLightColorType(o);
        if (lightColorType == 1) {
            dimShortClickActionList = ComboCmdHelper.getInstance().getDimShortClickActionList();
        } else if (lightColorType == 2) {
            dimShortClickActionList = ComboCmdHelper.getInstance().getCtShortClickActionList();
        } else if (lightColorType == 3) {
            dimShortClickActionList = ComboCmdHelper.getInstance().getRgbShortClickActionList();
        } else if (lightColorType == 4) {
            dimShortClickActionList = ComboCmdHelper.getInstance().getRgbwShortClickActionList();
        } else {
            if (lightColorType != 5) {
                if (lightColorType == 17) {
                    dimShortClickActionList = ComboCmdHelper.getInstance().getSpiShortClickActionList();
                } else if (lightColorType != 20) {
                    dimShortClickActionList = null;
                }
            }
            dimShortClickActionList = ComboCmdHelper.getInstance().getRgbcwShortClickActionList();
        }
        if (dimShortClickActionList != null) {
            ArrayList arrayList = new ArrayList();
            SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(ActivityUtils.getTopActivity().getString(R.string.select_single_action)).setCancelString(ActivityUtils.getTopActivity().getString(R.string.cancel)).setSelectPosition(-1);
            Iterator<ComboCmdHelper.Action> it = dimShortClickActionList.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getName());
            }
            selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxVM$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActGqxVM.this.lambda$showDeviceShortClickActionDialog$9(dimShortClickActionList, o, (Integer) obj);
                }
            }).setSelectList(arrayList);
            selectPosition.showDialog((FragmentActivity) ActivityUtils.getTopActivity());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDeviceShortClickActionDialog$9(List list, Object obj, Integer num) {
        showActionSelectView(obj, ((ComboCmdHelper.Action) list.get(num.intValue())).getNum(), 0);
    }

    private void showDeviceLongClickActionDialog(final Object o) {
        final List<ComboCmdHelper.Action> dimLongClickActionList;
        int lightColorType = ProductRepository.getLightColorType(o);
        if (lightColorType == 1) {
            dimLongClickActionList = ComboCmdHelper.getInstance().getDimLongClickActionList();
        } else if (lightColorType == 2) {
            dimLongClickActionList = ComboCmdHelper.getInstance().getCtLongClickActionList();
        } else if (lightColorType == 3) {
            dimLongClickActionList = ComboCmdHelper.getInstance().getRgbLongClickActionList();
        } else if (lightColorType == 4) {
            dimLongClickActionList = ComboCmdHelper.getInstance().getRgbwLongClickActionList();
        } else {
            if (lightColorType != 5) {
                if (lightColorType == 16) {
                    dimLongClickActionList = ComboCmdHelper.getInstance().getDreamCurtainLongClickActionList();
                } else if (lightColorType == 17) {
                    dimLongClickActionList = ComboCmdHelper.getInstance().getSpiLongClickActionList();
                } else if (lightColorType != 20) {
                    switch (lightColorType) {
                        case 101:
                            dimLongClickActionList = ComboCmdHelper.getInstance().getDaliDimLongClickActionList();
                            break;
                        case 102:
                            dimLongClickActionList = ComboCmdHelper.getInstance().getDaliCtLongClickActionList();
                            break;
                        case 103:
                            dimLongClickActionList = ComboCmdHelper.getInstance().getDaliRgbLongClickActionList();
                            break;
                        case 104:
                            dimLongClickActionList = ComboCmdHelper.getInstance().getDaliRgbwLongClickActionList();
                            break;
                        case 105:
                            dimLongClickActionList = ComboCmdHelper.getInstance().getDaliRgbcwLongClickActionList();
                            break;
                        default:
                            dimLongClickActionList = null;
                            break;
                    }
                }
            }
            dimLongClickActionList = ComboCmdHelper.getInstance().getRgbcwLongClickActionList();
        }
        if (dimLongClickActionList != null) {
            ArrayList arrayList = new ArrayList();
            SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(ActivityUtils.getTopActivity().getString(R.string.select_single_action)).setCancelString(ActivityUtils.getTopActivity().getString(R.string.cancel)).setSelectPosition(-1);
            Iterator<ComboCmdHelper.Action> it = dimLongClickActionList.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getName());
            }
            selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxVM$$ExternalSyntheticLambda5
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActGqxVM.this.lambda$showDeviceLongClickActionDialog$10(dimLongClickActionList, o, (Integer) obj);
                }
            }).setSelectList(arrayList);
            selectPosition.showDialog((FragmentActivity) ActivityUtils.getTopActivity());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDeviceLongClickActionDialog$10(List list, Object obj, Integer num) {
        showActionSelectView(obj, ((ComboCmdHelper.Action) list.get(num.intValue())).getNum(), 1);
    }

    private void showDeviceKnobActionDialog(final Object o) {
        final List<ComboCmdHelper.Action> ctKnobActionList;
        int lightColorType = ProductRepository.getLightColorType(o);
        if (lightColorType == 2) {
            ctKnobActionList = ComboCmdHelper.getInstance().getCtKnobActionList();
        } else if (lightColorType == 3) {
            ctKnobActionList = ComboCmdHelper.getInstance().getRgbKnobActionList();
        } else if (lightColorType == 4) {
            ctKnobActionList = ComboCmdHelper.getInstance().getRgbwKnobActionList();
        } else {
            if (lightColorType != 5) {
                if (lightColorType == 16) {
                    ctKnobActionList = ComboCmdHelper.getInstance().getDreamCurtainKnobActionList();
                } else if (lightColorType == 17) {
                    ctKnobActionList = ComboCmdHelper.getInstance().getSpiKnobActionList();
                } else if (lightColorType != 20) {
                    ctKnobActionList = lightColorType != 10007 ? null : ComboCmdHelper.getInstance().getMusicPlayerKnobActionList();
                }
            }
            ctKnobActionList = ComboCmdHelper.getInstance().getRgbcwKnobActionList();
        }
        if (ctKnobActionList != null) {
            ArrayList arrayList = new ArrayList();
            SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(ActivityUtils.getTopActivity().getString(R.string.select_single_action)).setCancelString(ActivityUtils.getTopActivity().getString(R.string.cancel)).setSelectPosition(-1);
            Iterator<ComboCmdHelper.Action> it = ctKnobActionList.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getName());
            }
            selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxVM$$ExternalSyntheticLambda3
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActGqxVM.this.lambda$showDeviceKnobActionDialog$11(ctKnobActionList, o, (Integer) obj);
                }
            }).setSelectList(arrayList);
            selectPosition.showDialog((FragmentActivity) ActivityUtils.getTopActivity());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDeviceKnobActionDialog$11(List list, Object obj, Integer num) {
        showActionSelectView(obj, ((ComboCmdHelper.Action) list.get(num.intValue())).getNum(), 2);
    }

    /* renamed from: com.ltech.smarthome.ui.device.gqx.ActGqxVM$1, reason: invalid class name */
    class AnonymousClass1 implements IAction<List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction>> {
        final /* synthetic */ Object val$relateObject;
        final /* synthetic */ int val$type;

        AnonymousClass1(final int val$type, final Object val$relateObject) {
            this.val$type = val$type;
            this.val$relateObject = val$relateObject;
        }

        @Override // com.ltech.smarthome.base.IAction
        public void act(final List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> childActions) {
            ImageTipDialog imageRes = ImageTipDialog.asDefault().setTitle(ActGqxVM.this.getContext().getString(R.string.gqx_click_tip)).setConfirmString(ActGqxVM.this.getContext().getString(R.string.get_it)).setImageRes(ProductId.ID_SMART_PANEL_GQ.equals(ActGqxVM.this.productId) ? R.mipmap.gq_pic_click1 : R.mipmap.gqx_pic_click1);
            final int i = this.val$type;
            final Object obj = this.val$relateObject;
            imageRes.setCallback(new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxVM$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                    ActGqxVM.AnonymousClass1.this.lambda$act$0(i, obj, childActions, imageTipDialog);
                }
            }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$act$0(int i, Object obj, List list, ImageTipDialog imageTipDialog) {
            imageTipDialog.dismissDialog();
            if (i == 0) {
                ActGqxVM.this.subscribeShortClickCmd(obj, list);
            } else if (i == 1) {
                ActGqxVM.this.subscribeLongClickCmd(obj, list);
            } else if (i == 2) {
                ActGqxVM.this.subscribeKnobCmd(obj, list);
            }
        }
    }

    private void showActionSelectView(Object relateObject, int num, int type) {
        ComboCmdHelper.getInstance().selectActionByIndex(num, relateObject, new AnonymousClass1(type, relateObject));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void subscribeShortClickCmd(Object relateObject, List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> childActions) {
        showLoadingDialog("");
        ComboCmdHelper.getInstance().subscribeShortClickCmd(ActivityUtils.getTopActivity(), this.controlObject.getValue(), relateObject, childActions, getSelIndex(), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxVM.2
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                ActGqxVM.this.dismissLoadingDialog();
                ActGqxVM.this.uiEvent.postValue(true);
                if (responseMsg != null) {
                    ActGqxVM.this.uploadData(0);
                } else {
                    ActGqxVM actGqxVM = ActGqxVM.this;
                    actGqxVM.showErrorTipDialog(actGqxVM.getContext().getString(R.string.bind_fail));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void subscribeLongClickCmd(Object relateObject, List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> childActions) {
        showLoadingDialog("");
        ComboCmdHelper.getInstance().subscribeLongClickCmd(ActivityUtils.getTopActivity(), this.controlObject.getValue(), relateObject, childActions, getSelIndex(), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxVM.3
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                ActGqxVM.this.dismissLoadingDialog();
                ActGqxVM.this.uiEvent.postValue(true);
                if (responseMsg != null) {
                    ActGqxVM.this.uploadData(2);
                } else {
                    ActGqxVM actGqxVM = ActGqxVM.this;
                    actGqxVM.showErrorTipDialog(actGqxVM.getContext().getString(R.string.bind_fail));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void subscribeKnobCmd(Object relateObject, List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> childActions) {
        showLoadingDialog("");
        ComboCmdHelper.getInstance().subscribeKnobCmd(ActivityUtils.getTopActivity(), this.controlObject.getValue(), relateObject, childActions, getSelIndex(), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxVM.4
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                ActGqxVM.this.dismissLoadingDialog();
                ActGqxVM.this.uiEvent.postValue(true);
                if (responseMsg != null) {
                    ActGqxVM.this.uploadData(1);
                } else {
                    ActGqxVM actGqxVM = ActGqxVM.this;
                    actGqxVM.showErrorTipDialog(actGqxVM.getContext().getString(R.string.bind_fail));
                }
            }
        });
    }
}