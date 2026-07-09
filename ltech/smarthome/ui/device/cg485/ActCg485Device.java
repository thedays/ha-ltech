package com.ltech.smarthome.ui.device.cg485;

import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActCg485DeviceBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.Rs485ExtParam;
import com.ltech.smarthome.model.device_param.SuperPanelExtParam;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.cg485.ActCg485Device;
import com.ltech.smarthome.ui.device.cg485.Cg485Helper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActCg485Device extends BaseControlActivity<ActCg485DeviceBinding, ActCg485VM> {
    private int commandType;
    private BaseQuickAdapter<Rs485ExtParam.Instruct, BaseViewHolder> mAdapter;
    private List<Rs485ExtParam.Instruct> commandList = new ArrayList();
    private SingleLiveEvent<Void> refreshList = new SingleLiveEvent<>();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.rv_content;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_cg_485_device;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        ((ActCg485VM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        this.commandType = getIntent().getIntExtra(Constants.COMMAND_TYPE, 1);
        initAdapter();
        ((ActCg485DeviceBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485Device$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActCg485Device.this.lambda$initView$0((View) obj);
            }
        }));
        ((ActCg485DeviceBinding) this.mViewBinding).layoutBottom.setVisibility(isOwnerOrManager() ? 0 : 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (view.getId() != R.id.tv_bottom) {
            return;
        }
        if (this.commandType == 2 && Cg485Helper.get485InstructTotal() >= 255) {
            SmartToast.showShort(R.string.max_cmd_tip);
        } else {
            NavUtils.destination(ActEditInstructCmd.class).withInt(Constants.COMMAND_TYPE, this.commandType).navigation(this.activity);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.no_instruct));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActCg485VM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485Device$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCg485Device.this.lambda$startObserve$1((Device) obj);
            }
        });
        this.refreshList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485Device$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCg485Device.this.lambda$startObserve$2((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        ((ActCg485VM) this.mViewModel).extParam = (SuperPanelExtParam) device.getExtParam(SuperPanelExtParam.class);
        if (((ActCg485VM) this.mViewModel).extParam == null) {
            ((ActCg485VM) this.mViewModel).extParam = new SuperPanelExtParam();
        }
        Cg485Helper.extParam = ((ActCg485VM) this.mViewModel).extParam;
        if (device.isSubDevice()) {
            setTitle(device.getDeviceName());
            Cg485Helper.categoryPosition = 0;
        } else {
            setTitle(Cg485Helper.getCategory(this.commandType).getCategoryName());
        }
        this.commandList = Cg485Helper.getCategory(this.commandType).getAction();
        this.refreshList.call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r2) {
        if (this.commandList.size() == 0) {
            showEmpty();
        } else {
            showContent();
            this.mAdapter.replaceData(this.commandList);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        NavUtils.destination(ActCg485SubDeviceSetting.class).withLong(Constants.CONTROL_ID, ((ActCg485VM) this.mViewModel).controlObject.getValue().getId()).withDefaultRequestCode().navigation(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        startObjectObserve();
    }

    private void startObjectObserve() {
        Device deviceById = Injection.repo().device().getDeviceById(((ActCg485VM) this.mViewModel).controlId);
        if (deviceById != null) {
            ((ActCg485VM) this.mViewModel).controlObject.setValue(deviceById);
            Cg485Helper.controlDevice = ((ActCg485VM) this.mViewModel).controlObject.getValue();
        }
    }

    private void initAdapter() {
        this.mAdapter = getInstructAdapter(this.commandType);
        ((ActCg485DeviceBinding) this.mViewBinding).rvContent.setAdapter(this.mAdapter);
        ((ActCg485DeviceBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(this.activity, 2));
        ((ActCg485DeviceBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((ActCg485DeviceBinding) this.mViewBinding).rvContent.addItemDecoration(((ActCg485VM) this.mViewModel).decoration);
    }

    /* renamed from: com.ltech.smarthome.ui.device.cg485.ActCg485Device$1, reason: invalid class name */
    class AnonymousClass1 extends BaseQuickAdapter<Rs485ExtParam.Instruct, BaseViewHolder> {
        AnonymousClass1(int layoutResId, List data) {
            super(layoutResId, data);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(final BaseViewHolder helper, Rs485ExtParam.Instruct instruct) {
            helper.setText(R.id.tv_name, instruct.getActionName()).setText(R.id.tv_cmd, instruct.getCmd());
            helper.getView(R.id.iv_edit).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485Device$1$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ActCg485Device.AnonymousClass1.this.lambda$convert$0(helper, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(BaseViewHolder baseViewHolder, View view) {
            Cg485Helper.instructPosition = baseViewHolder.getBindingAdapterPosition();
            NavUtils.destination(ActInstructSetting.class).withInt(Constants.COMMAND_TYPE, 1).withDefaultRequestCode().navigation(ActCg485Device.this.activity);
        }
    }

    private BaseQuickAdapter<Rs485ExtParam.Instruct, BaseViewHolder> getInstructAdapter(int type) {
        if (this.commandType == 1) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(R.layout.item_ble_to_485, new ArrayList());
            anonymousClass1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485Device$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                    ActCg485Device.this.lambda$getInstructAdapter$4(baseQuickAdapter, view, i);
                }
            });
            return anonymousClass1;
        }
        BaseQuickAdapter<Rs485ExtParam.Instruct, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Rs485ExtParam.Instruct, BaseViewHolder>(R.layout.item_485_to_ble, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485Device.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Rs485ExtParam.Instruct instruct) {
                Cg485Helper.ContentState relateAction = Cg485Helper.getRelateAction(ActCg485Device.this.activity, instruct.getBindAction());
                helper.setImageResource(R.id.iv_icon, relateAction.iconRes).setText(R.id.tv_name, instruct.getActionName()).setText(R.id.tv_cmd, instruct.getCmd());
                if (TextUtils.isEmpty(relateAction.name)) {
                    helper.setText(R.id.tv_action, ActCg485Device.this.getString(R.string.no_bind_object));
                    return;
                }
                helper.setText(R.id.tv_action, relateAction.place + " " + relateAction.name + " " + relateAction.action);
            }
        };
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485Device$$ExternalSyntheticLambda1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActCg485Device.this.lambda$getInstructAdapter$5(baseQuickAdapter2, view, i);
            }
        });
        return baseQuickAdapter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getInstructAdapter$4(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        showLoadingDialog("");
        Cg485Helper.runInstruct(this, this.commandList.get(i).getCmd(), this.commandList.get(i).getDataFormat(), new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485Device$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActCg485Device.this.lambda$getInstructAdapter$3((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getInstructAdapter$3(Boolean bool) {
        if (bool.booleanValue()) {
            showSuccessDialog(getString(R.string.send_to_device_success));
        } else {
            showErrorDialog(getString(R.string.send_to_device_fail));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getInstructAdapter$5(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        Cg485Helper.instructPosition = i;
        NavUtils.destination(ActInstructSetting.class).withInt(Constants.COMMAND_TYPE, 2).withDefaultRequestCode().navigation(this.activity);
    }
}