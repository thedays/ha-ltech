package com.ltech.smarthome.ui.device.ir;

import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActAddIrKeyBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.DiyIrParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.device.AddDeviceResponse;
import com.ltech.smarthome.ui.config.ConfigHelper;
import com.ltech.smarthome.ui.control.ActHome;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActAddIrDiy extends BaseNormalActivity<ActAddIrKeyBinding> {
    private String cmd;
    private Device mDevice;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_add_ir_key;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.edit_name));
        setBackString(getString(R.string.cancel));
        setEditString(getString(R.string.save));
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.on_off));
        arrayList.add(getString(R.string.ir_menu));
        arrayList.add(getString(R.string.ir_back));
        arrayList.add(getString(R.string.ir_signal));
        arrayList.add(getString(R.string.ir_home_page));
        arrayList.add(getString(R.string.ir_timing));
        arrayList.add(getString(R.string.ir_mode));
        arrayList.add(getString(R.string.ir_exit));
        arrayList.add(getString(R.string.ir_volume_up));
        arrayList.add(getString(R.string.ir_volume_down));
        arrayList.add(getString(R.string.ir_channel_up));
        arrayList.add(getString(R.string.ir_channel_down));
        final BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(this, R.layout.item_room_name, arrayList) { // from class: com.ltech.smarthome.ui.device.ir.ActAddIrDiy.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_name, item);
            }
        };
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.ir.ActAddIrDiy$$ExternalSyntheticLambda7
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActAddIrDiy.this.lambda$initView$0(baseQuickAdapter, baseQuickAdapter2, view, i);
            }
        });
        baseQuickAdapter.bindToRecyclerView(((ActAddIrKeyBinding) this.mViewBinding).rvContent);
        ((ActAddIrKeyBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((ActAddIrKeyBinding) this.mViewBinding).rvContent.setLayoutManager(new FlexboxLayoutManager(this));
        ((DefaultItemAnimator) ((ActAddIrKeyBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
        ((ActAddIrKeyBinding) this.mViewBinding).etKeyName.setText((CharSequence) baseQuickAdapter.getData().get(i));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        String stringExtra = getIntent().getStringExtra(Constants.LEARN_CMD);
        this.cmd = stringExtra;
        if (TextUtils.isEmpty(stringExtra)) {
            this.cmd = "";
        }
        long longExtra = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        if (-1 != longExtra) {
            Injection.repo().device().getDeviceFromDb(longExtra).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.ActAddIrDiy$$ExternalSyntheticLambda6
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActAddIrDiy.this.lambda$startObserve$1((Device) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        this.mDevice = device;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (TextUtils.isEmpty(((ActAddIrKeyBinding) this.mViewBinding).etKeyName.getText())) {
            SmartToast.showShort(R.string.input_key_name);
        } else if (this.mDevice != null) {
            addDiyIrKey(((ActAddIrKeyBinding) this.mViewBinding).etKeyName.getText().toString());
        } else {
            showEditDialog(ConfigHelper.instance().productInfo.getDefaultName(this));
        }
    }

    private void showEditDialog(String content) {
        EditDialog.asDefault().setContent(content).setTitle(getString(R.string.device_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.ir.ActAddIrDiy$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActAddIrDiy.this.lambda$showEditDialog$2((String) obj);
            }
        }).showDialog(getSupportFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditDialog$2(String str) {
        if (TextUtils.isEmpty(str)) {
            SmartToast.showShort(getString(R.string.input_name));
        } else {
            addDiyIrDevice(str, ((ActAddIrKeyBinding) this.mViewBinding).etKeyName.getText().toString());
        }
    }

    public void addDiyIrKey(String keyName) {
        final DiyIrParam createDiyParam = createDiyParam((DiyIrParam) this.mDevice.getParam(DiyIrParam.class), keyName);
        ((ObservableSubscribeProxy) Injection.net().updateParam(this.mDevice.getDeviceId(), GsonUtils.toJson(createDiyParam)).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.ir.ActAddIrDiy$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddIrDiy.this.lambda$addDiyIrKey$3((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActAddIrDiy$$ExternalSyntheticLambda1(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.ir.ActAddIrDiy$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddIrDiy.this.lambda$addDiyIrKey$4(createDiyParam, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addDiyIrKey$3(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addDiyIrKey$4(DiyIrParam diyIrParam, Object obj) throws Exception {
        this.mDevice.setParam(diyIrParam);
        Injection.repo().device().saveDevice(this.mDevice);
        SmartToast.showShort(R.string.save_success);
        finishActivity();
    }

    private DiyIrParam createDiyParam(DiyIrParam param, String keyName) {
        List<DiyIrParam.DiyIrKey> arrayList = new ArrayList<>();
        if (param != null) {
            arrayList = param.getDiyIrKeyList();
        }
        DiyIrParam.DiyIrKey diyIrKey = new DiyIrParam.DiyIrKey();
        diyIrKey.setKeyName(keyName);
        diyIrKey.setKeyData(this.cmd);
        arrayList.add(diyIrKey);
        DiyIrParam diyIrParam = new DiyIrParam(arrayList);
        diyIrParam.setUnicastAddress(ConfigHelper.instance().unicastAddress);
        return diyIrParam;
    }

    public void addDiyIrDevice(String deviceName, String keyName) {
        ConfigHelper.instance().subProductName = ConfigHelper.instance().productInfo.getDefaultName(ActivityUtils.getTopActivity());
        ConfigHelper.instance().deviceName = deviceName;
        ConfigHelper.instance().subManufacturerName = "LTECH";
        ConfigHelper.instance().param = createDiyParam(null, keyName);
        ConfigHelper.instance().codeLibrary = GsonUtils.toJson(new HashMap());
        ((ObservableSubscribeProxy) Injection.net().addDevice(ConfigHelper.instance().irProductData()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.ir.ActAddIrDiy$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddIrDiy.this.lambda$addDiyIrDevice$5((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActAddIrDiy$$ExternalSyntheticLambda1(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.ir.ActAddIrDiy$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddIrDiy.this.lambda$addDiyIrDevice$6((AddDeviceResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addDiyIrDevice$5(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.adding));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addDiyIrDevice$6(AddDeviceResponse addDeviceResponse) throws Exception {
        ConfigHelper.instance().addDevice(addDeviceResponse, ConfigHelper.instance().productInfo.getProductId());
        NavUtils.destination(ActHome.class).navigation(this);
    }
}