package com.ltech.smarthome.ui.device.trig;

import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActBleCurtainMotorTypeSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.device.curtain_motor.CurtainMotorInfoExtParam;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActBleCurtainMotorTypeSetting extends BaseNormalActivity<ActBleCurtainMotorTypeSettingBinding> {
    private BaseQuickAdapter<GoItem, BaseViewHolder> adapter;
    private CurtainMotorInfoExtParam curtainExtParam;
    private Device device;
    private Group group;
    private boolean isGroupControl;
    protected int selectPosition = -1;
    public long controlId = -1;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_ble_curtain_motor_type_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        setTitle(getString(R.string.app_str_curtain_open_dir));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        ArrayList arrayList = new ArrayList();
        arrayList.clear();
        arrayList.add(new GoItem().setImageRes(R.mipmap.cgcur_style_1).setSubText(getString(R.string.app_curtain_open_two_way)));
        arrayList.add(new GoItem().setImageRes(R.mipmap.cgcur_style_2).setSubText(getString(R.string.app_curtain_open_one_way)));
        BaseQuickAdapter<GoItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<GoItem, BaseViewHolder>(R.layout.item_curtain_currainmotortype_select, arrayList) { // from class: com.ltech.smarthome.ui.device.trig.ActBleCurtainMotorTypeSetting.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, GoItem item) {
                helper.setImageResource(R.id.iv_icon, item.getImageRes()).setText(R.id.tv_main, item.getSubText()).setImageResource(R.id.iv_select, ActBleCurtainMotorTypeSetting.this.selectPosition == helper.getBindingAdapterPosition() ? R.mipmap.trig_sel_red : 0);
            }
        };
        this.adapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.trig.ActBleCurtainMotorTypeSetting.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ActBleCurtainMotorTypeSetting.this.selectPosition = position;
                adapter.notifyDataSetChanged();
            }
        });
        this.adapter.bindToRecyclerView(((ActBleCurtainMotorTypeSettingBinding) this.mViewBinding).rvContent);
        ((ActBleCurtainMotorTypeSettingBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this, 1, false));
        ((ActBleCurtainMotorTypeSettingBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        this.controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        this.isGroupControl = booleanExtra;
        if (booleanExtra) {
            this.group = Injection.repo().group().getGroupById(this.controlId);
        } else {
            this.device = Injection.repo().device().getDeviceById(this.controlId);
        }
        this.curtainExtParam = new CurtainMotorInfoExtParam();
        if (this.isGroupControl) {
            if (this.group.getExtParam() != null && !TextUtils.isEmpty(this.group.getExtParam())) {
                this.curtainExtParam.fillMapWithString(this.group.getExtParam());
                this.selectPosition = this.curtainExtParam.getOpenType();
            } else {
                this.selectPosition = 0;
            }
        } else if (this.device.getExtParam() != null && !TextUtils.isEmpty(this.device.getExtParam())) {
            this.curtainExtParam.fillMapWithString(this.device.getExtParam());
            this.selectPosition = this.curtainExtParam.getOpenType();
        } else {
            this.selectPosition = 0;
        }
        this.adapter.notifyDataSetChanged();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        this.curtainExtParam.setOpenType(this.selectPosition);
        if (this.isGroupControl) {
            ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(this.group.getGroupId(), this.curtainExtParam.getParamMapString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.trig.ActBleCurtainMotorTypeSetting$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActBleCurtainMotorTypeSetting.this.lambda$edit$0((Disposable) obj);
                }
            }).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.trig.ActBleCurtainMotorTypeSetting$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActBleCurtainMotorTypeSetting.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.trig.ActBleCurtainMotorTypeSetting$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActBleCurtainMotorTypeSetting.this.lambda$edit$1(obj);
                }
            }, new SmartErrorComsumer());
        } else {
            ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.device.getDeviceId(), this.curtainExtParam.getParamMapString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.trig.ActBleCurtainMotorTypeSetting$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActBleCurtainMotorTypeSetting.this.lambda$edit$2((Disposable) obj);
                }
            }).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.trig.ActBleCurtainMotorTypeSetting$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActBleCurtainMotorTypeSetting.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.trig.ActBleCurtainMotorTypeSetting$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActBleCurtainMotorTypeSetting.this.lambda$edit$3(obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$0(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$1(Object obj) throws Exception {
        this.group.setExtParam(this.curtainExtParam.getParamMapString());
        Injection.repo().group().saveGroup(this.group);
        showSuccessDialog(getString(R.string.save_success));
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$2(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$3(Object obj) throws Exception {
        this.device.setExtParam(this.curtainExtParam.getParamMapString());
        Injection.repo().device().saveDevice(this.device);
        showSuccessDialog(getString(R.string.save_success));
        finish();
    }
}