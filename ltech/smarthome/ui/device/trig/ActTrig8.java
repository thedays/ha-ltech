package com.ltech.smarthome.ui.device.trig;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActTrigBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.TrigToBleExtParam;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.trig.TrigRepository;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActTrig8 extends BaseControlActivity<ActTrigBinding, ActTrigVM> {
    private byte[] mStateArray;
    private TrigToBleExtParam param;
    BaseQuickAdapter<TrigRepository.TrigItem, BaseViewHolder> sceneAdapter;
    private List<TrigRepository.TrigItem> trigItems = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_trig;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        ((ActTrigVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        this.trigItems = TrigRepository.getDefaultScene8ItemList();
        BaseQuickAdapter<TrigRepository.TrigItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<TrigRepository.TrigItem, BaseViewHolder>(R.layout.item_trig_to_ble, this.trigItems) { // from class: com.ltech.smarthome.ui.device.trig.ActTrig8.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, TrigRepository.TrigItem item) {
                helper.setGone(R.id.iv_icon, false).setText(R.id.tv_action, item.action).setText(R.id.tv_num, (helper.getLayoutPosition() + 1) + "").setText(R.id.tv_name, item.name).addOnClickListener(R.id.iv_device_more).setGone(R.id.iv_device_more, true);
                if (ActTrig8.this.mStateArray == null || ActTrig8.this.mStateArray.length <= 0) {
                    return;
                }
                int adapterPosition = helper.getAdapterPosition() / 2;
                int adapterPosition2 = helper.getAdapterPosition() % 2;
                int i = R.drawable.shape_circle_blue;
                if (adapterPosition2 == 0) {
                    helper.getView(R.id.bg).setSelected(ActTrig8.this.mStateArray[adapterPosition] == 1);
                    helper.setTextColor(R.id.tv_name, ActTrig8.this.mStateArray[adapterPosition] != 1 ? ActTrig8.this.getResources().getColor(R.color.color_text_black) : -11695118);
                    helper.setTextColor(R.id.tv_action, ActTrig8.this.mStateArray[adapterPosition] != 1 ? ActTrig8.this.getResources().getColor(R.color.color_text_gray) : -7295266);
                    if (ActTrig8.this.mStateArray[adapterPosition] != 1) {
                        i = R.drawable.shape_circle_gray;
                    }
                    helper.setBackgroundRes(R.id.tv_num, i);
                    return;
                }
                if (helper.getAdapterPosition() % 2 == 1) {
                    helper.getView(R.id.bg).setSelected(ActTrig8.this.mStateArray[adapterPosition] == 0);
                    helper.setTextColor(R.id.tv_name, ActTrig8.this.mStateArray[adapterPosition] != 0 ? ActTrig8.this.getResources().getColor(R.color.color_text_black) : -11695118);
                    helper.setTextColor(R.id.tv_action, ActTrig8.this.mStateArray[adapterPosition] != 0 ? ActTrig8.this.getResources().getColor(R.color.color_text_gray) : -7295266);
                    if (ActTrig8.this.mStateArray[adapterPosition] != 0) {
                        i = R.drawable.shape_circle_gray;
                    }
                    helper.setBackgroundRes(R.id.tv_num, i);
                }
            }
        };
        this.sceneAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.trig.ActTrig8$$ExternalSyntheticLambda1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActTrig8.this.lambda$initView$0(baseQuickAdapter2, view, i);
            }
        });
        this.sceneAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.device.trig.ActTrig8.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ActTrig8.this.showEditNameDialog(position);
            }
        });
        this.sceneAdapter.bindToRecyclerView(((ActTrigBinding) this.mViewBinding).rvMode);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.ltech.smarthome.ui.device.trig.ActTrig8.3
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int position) {
                return ActTrig8.this.sceneAdapter.getData().get(position).spanCount;
            }
        });
        ((ActTrigBinding) this.mViewBinding).rvMode.clearAnimation();
        ((ActTrigBinding) this.mViewBinding).rvMode.setItemAnimator(null);
        ((ActTrigBinding) this.mViewBinding).rvMode.setLayoutManager(gridLayoutManager);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        onItemClick(i);
    }

    private void onItemClick(int position) {
        ((ActTrigVM) this.mViewModel).setTrigOpenCloseValue(position + 1);
    }

    protected void showEditNameDialog(final int pos) {
        EditDialog.asDefault().setContent(this.trigItems.get(pos).name).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.trig.ActTrig8$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActTrig8.this.lambda$showEditNameDialog$1(pos, (String) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$1(int i, String str) {
        updateParamExt(i + 1, str);
    }

    public void updateParamExt(final int pos, final String name) {
        final Device value = ((ActTrigVM) this.mViewModel).controlDevice.getValue();
        TrigToBleExtParam trigToBleExtParam = this.param;
        if (trigToBleExtParam != null) {
            trigToBleExtParam.getRelateInfo(pos).setCustomerName(name);
        }
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(value.getDeviceId(), this.param.getRelateParamMapString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.trig.ActTrig8$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActTrig8.this.lambda$updateParamExt$2((Disposable) obj);
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.trig.ActTrig8$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActTrig8.this.lambda$updateParamExt$3(value, pos, name, obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.device.trig.ActTrig8$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActTrig8.this.lambda$updateParamExt$4((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateParamExt$2(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateParamExt$3(Device device, int i, String str, Object obj) throws Exception {
        device.setExtParam(this.param.getRelateParamMapString());
        Injection.repo().device().saveDevice(device);
        int i2 = i - 1;
        this.trigItems.get(i2).name = str;
        this.sceneAdapter.notifyItemChanged(i2);
        showSuccessDialog(getString(R.string.save_success));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateParamExt$4(Throwable th) throws Exception {
        showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        if (((ActTrigVM) this.mViewModel).controlId != -1) {
            Device deviceById = Injection.repo().device().getDeviceById(((ActTrigVM) this.mViewModel).controlId);
            try {
                if (((ActTrigVM) this.mViewModel).controlDevice.getValue() == null) {
                    ((ActTrigVM) this.mViewModel).controlDevice.setValue(deviceById);
                } else if (!HelpUtils.compareObject(((ActTrigVM) this.mViewModel).controlDevice.getValue(), deviceById)) {
                    ((ActTrigVM) this.mViewModel).controlDevice.setValue(deviceById);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            ((ActTrigVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.trig.ActTrig8$$ExternalSyntheticLambda2
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActTrig8.this.lambda$startObserve$6((Device) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(final Device device) {
        String customerName;
        if (device != null) {
            setTitle(device.getDeviceName());
            TrigToBleExtParam trigToBleExtParam = new TrigToBleExtParam();
            this.param = trigToBleExtParam;
            trigToBleExtParam.fillMapWithString(device.getExtParam(), 8);
            for (int i = 0; i <= this.trigItems.size(); i++) {
                TrigToBleExtParam trigToBleExtParam2 = this.param;
                if (trigToBleExtParam2 != null) {
                    int i2 = i + 1;
                    if (trigToBleExtParam2.getRelateInfo(i2) != null && (customerName = this.param.getRelateInfo(i2).getCustomerName()) != null && !TextUtils.isEmpty(customerName)) {
                        this.trigItems.get(i).name = customerName;
                        this.sceneAdapter.notifyItemChanged(i);
                    }
                }
            }
            ((ActTrigVM) this.mViewModel).setTrigType(3);
            ((ActTrigVM) this.mViewModel).queryTrigState(device);
            MessageManager.getInstance().setTrigBleStatusCallBack(new MessageManager.TrigBleStatusCallBack() { // from class: com.ltech.smarthome.ui.device.trig.ActTrig8$$ExternalSyntheticLambda3
                @Override // com.smart.message.MessageManager.TrigBleStatusCallBack
                public final void onDataReceive(ResponseMsg responseMsg) {
                    ActTrig8.this.lambda$startObserve$5(device, responseMsg);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Device device, ResponseMsg responseMsg) {
        BleParam bleParam = (BleParam) device.getParam(BleParam.class);
        int unicastAddress = bleParam != null ? bleParam.getUnicastAddress() : 0;
        responseMsg.getResData().length();
        if (unicastAddress == Integer.parseInt(responseMsg.getResData().substring(6, 10), 16)) {
            this.mStateArray = getByteArray(Integer.parseInt(responseMsg.getResData().substring(12, 14), 16));
            this.sceneAdapter.notifyDataSetChanged();
        }
    }

    public static byte[] getByteArray(int b2) {
        byte[] bArr = new byte[4];
        for (int i = 0; i < 4; i++) {
            bArr[i] = (byte) (b2 & 1);
            b2 = (byte) (b2 >> 1);
        }
        return bArr;
    }

    @Override // android.app.Activity
    protected void onRestart() {
        super.onRestart();
        ((ActTrigVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(((ActTrigVM) this.mViewModel).controlId));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        super.back();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavHelper.goSetting(((ActTrigVM) this.mViewModel).controlDevice.getValue());
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseControlActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 3005) {
            ((ActTrigVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(((ActTrigVM) this.mViewModel).controlId));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}