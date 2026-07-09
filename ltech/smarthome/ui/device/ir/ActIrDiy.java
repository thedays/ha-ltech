package com.ltech.smarthome.ui.device.ir;

import android.graphics.Rect;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActIrDiyBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.BaseIrParam;
import com.ltech.smarthome.model.device_param.DiyIrParam;
import com.ltech.smarthome.model.device_param.IrParam;
import com.ltech.smarthome.ui.config.ConfigHelper;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.IrLearnDialog;
import com.smart.message.DataPackage;
import com.smart.message.ResponseMsg;
import com.smart.message.base.IMessageListener;
import com.smart.message.base.IReceiveListener;
import com.smart.product_agreement.extra.Emitter;
import com.smart.product_agreement.param.MeshGatewayParam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActIrDiy extends BaseControlActivity<ActIrDiyBinding, ActIrDiyVM> implements ISelectAction {
    private IrLearnDialog learnDialog;
    private BaseQuickAdapter<DiyIrParam.DiyIrKey, BaseViewHolder> mAdapter;
    private Runnable refreshDialog = new Runnable() { // from class: com.ltech.smarthome.ui.device.ir.ActIrDiy.3
        @Override // java.lang.Runnable
        public void run() {
            if (ActIrDiy.this.learnDialog != null) {
                ActIrDiy.this.learnDialog.reduce();
                ActIrDiy.this.getMainHandler().postDelayed(ActIrDiy.this.refreshDialog, 1000L);
            }
        }
    };

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_ir_diy;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void lambda$edit$7(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.ir_diy));
        setBackImage(R.mipmap.icon_back);
        BaseQuickAdapter<DiyIrParam.DiyIrKey, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<DiyIrParam.DiyIrKey, BaseViewHolder>(R.layout.item_ir_diy_fun, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.ir.ActIrDiy.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, DiyIrParam.DiyIrKey item) {
                if (((ActIrDiyVM) ActIrDiy.this.mViewModel).selectAction || helper.getAdapterPosition() < ActIrDiy.this.mAdapter.getData().size() - 1) {
                    helper.setText(R.id.tv_name, item.getKeyName()).setGone(R.id.tv_name, true).setGone(R.id.iv_add, false);
                } else {
                    helper.setGone(R.id.tv_name, false).setGone(R.id.iv_add, true);
                }
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.ir.ActIrDiy$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActIrDiy.this.lambda$initView$0(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() { // from class: com.ltech.smarthome.ui.device.ir.ActIrDiy$$ExternalSyntheticLambda1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemLongClickListener
            public final boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                boolean lambda$initView$1;
                lambda$initView$1 = ActIrDiy.this.lambda$initView$1(baseQuickAdapter2, view, i);
                return lambda$initView$1;
            }
        });
        this.mAdapter.bindToRecyclerView(((ActIrDiyBinding) this.mViewBinding).rvContent);
        ((ActIrDiyBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(this, 3));
        ((ActIrDiyBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActIrDiyBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
        ((ActIrDiyBinding) this.mViewBinding).rvContent.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.ir.ActIrDiy.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(8.0f), 0, ConvertUtils.dp2px(8.0f), ConvertUtils.dp2px(16.0f));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (((ActIrDiyVM) this.mViewModel).selectAction || i < this.mAdapter.getData().size() - 1) {
            ((ActIrDiyVM) this.mViewModel).sendCmd(this.mAdapter.getData().get(i));
            ((ActIrDiyVM) this.mViewModel).clickAnimate(view);
        } else {
            learnIr(-1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initView$1(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (!((ActIrDiyVM) this.mViewModel).selectAction && i < this.mAdapter.getData().size() - 1) {
            learnIr(i);
        }
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActIrDiyVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActIrDiyVM) this.mViewModel).selectAction = getIntent().getBooleanExtra(Constants.SELECT_ACTION, false);
        if (((ActIrDiyVM) this.mViewModel).selectAction) {
            ((ActIrDiyVM) this.mViewModel).controlDevice.setValue((Device) SceneHelper.instance().controlObject);
            ((ActIrDiyVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.ActIrDiy$$ExternalSyntheticLambda4
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActIrDiy.this.lambda$startObserve$2((Device) obj);
                }
            });
        } else if (((ActIrDiyVM) this.mViewModel).controlId != -1) {
            Injection.repo().device().getDeviceFromDb(((ActIrDiyVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.ActIrDiy$$ExternalSyntheticLambda5
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActIrDiy.this.lambda$startObserve$3((Device) obj);
                }
            });
            ((ActIrDiyVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.ActIrDiy$$ExternalSyntheticLambda6
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActIrDiy.this.lambda$startObserve$4((Device) obj);
                }
            });
        } else {
            Device device = new Device();
            device.setProductId(ConfigHelper.instance().productInfo.getProductId());
            device.setMacdeviceid(ConfigHelper.instance().macdeviceid);
            device.setMacfalg(2);
            if (ConfigHelper.instance().unicastAddress != 0) {
                IrParam irParam = new IrParam();
                irParam.setUnicastAddress(ConfigHelper.instance().unicastAddress);
                device.setParam(irParam);
            }
            ((ActIrDiyVM) this.mViewModel).controlDevice.setValue(device);
            ((ActIrDiyBinding) this.mViewBinding).cardviewAdd.setVisibility(0);
            ((ActIrDiyVM) this.mViewModel).learnIrEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.ActIrDiy$$ExternalSyntheticLambda7
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActIrDiy.this.lambda$startObserve$5((Void) obj);
                }
            });
        }
        ((ActIrDiyVM) this.mViewModel).sendStateLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.ActIrDiy$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIrDiy.this.lambda$startObserve$6((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Device device) {
        if (device != null) {
            setTitle(device.getDeviceName());
            refreshKey((DiyIrParam) device.getParam(DiyIrParam.class), false);
            setEditString(getString(R.string.save));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Device device) {
        ((ActIrDiyVM) this.mViewModel).controlDevice.setValue(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Device device) {
        if (device != null) {
            setTitle(device.getDeviceName());
            refreshKey((DiyIrParam) device.getParam(DiyIrParam.class), true);
            setEditImage(R.mipmap.ic_setting);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Void r1) {
        learnIr(-1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Boolean bool) {
        if (bool.booleanValue()) {
            ((ActIrDiyBinding) this.mViewBinding).title.ivTip.setBackgroundResource(R.drawable.shape_circle_red);
        } else {
            ((ActIrDiyBinding) this.mViewBinding).title.ivTip.setBackgroundResource(R.drawable.shape_circle_gray);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (((ActIrDiyVM) this.mViewModel).selectAction) {
            if (((ActIrDiyVM) this.mViewModel).mIrCmdParam == null) {
                SmartToast.showShort(R.string.please_choose);
                return;
            }
            ((ActIrDiyVM) this.mViewModel).mIrCmdParam.addExtParam(SceneHelper.OPTION_VALUE, ((ActIrDiyVM) this.mViewModel).cmdName);
            ((ActIrDiyVM) this.mViewModel).mIrCmdParam.addExtParam(SceneHelper.OPTION, "0");
            SceneHelper.instance().cmdParam = ((ActIrDiyVM) this.mViewModel).mIrCmdParam;
            SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.device.ir.ActIrDiy$$ExternalSyntheticLambda3
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActIrDiy.this.lambda$edit$7((Boolean) obj);
                }
            });
            return;
        }
        NavHelper.goSetting(((ActIrDiyVM) this.mViewModel).controlDevice.getValue());
    }

    private void refreshKey(DiyIrParam diyIrParam, boolean needAdd) {
        if (diyIrParam == null) {
            return;
        }
        List<DiyIrParam.DiyIrKey> diyIrKeyList = diyIrParam.getDiyIrKeyList();
        if (needAdd) {
            diyIrKeyList.add(new DiyIrParam.DiyIrKey());
        }
        this.mAdapter.setNewData(diyIrKeyList);
    }

    private void showIrLearnDialog() {
        IrLearnDialog asDefault = IrLearnDialog.asDefault();
        this.learnDialog = asDefault;
        asDefault.setTotal(30);
        this.learnDialog.setDismissRunnable(new Runnable() { // from class: com.ltech.smarthome.ui.device.ir.ActIrDiy$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                ActIrDiy.this.lambda$showIrLearnDialog$8();
            }
        });
        getMainHandler().postDelayed(this.refreshDialog, 1000L);
        this.learnDialog.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showIrLearnDialog$8() {
        Injection.message().removeDataPacket(((ActIrDiyVM) this.mViewModel).cmdIndex);
        dismissIrLearnDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissIrLearnDialog() {
        IrLearnDialog irLearnDialog = this.learnDialog;
        if (irLearnDialog != null) {
            irLearnDialog.dismissDialog();
            this.learnDialog = null;
        }
    }

    public void learnIr(final int position) {
        BaseIrParam baseIrParam;
        Device value = ((ActIrDiyVM) this.mViewModel).controlDevice.getValue();
        final boolean z = (value == null || value.getParam() == null || (baseIrParam = (BaseIrParam) value.getParam(BaseIrParam.class)) == null || baseIrParam.getUnicastAddress() <= 0) ? false : true;
        MeshGatewayParam meshGatewayParam = new MeshGatewayParam();
        meshGatewayParam.setCmdType(4);
        if (z) {
            meshGatewayParam.setId(getId());
        }
        ((ActIrDiyVM) this.mViewModel).cmdIndex = Injection.message().create(ActivityUtils.getTopActivity()).cmdParam(meshGatewayParam).control(((ActIrDiyVM) this.mViewModel).controlDevice.getValue()).sendTimes(1).resendTimes(0).timeOutTime(30000).emitter(z ? Emitter.MIX_BLE_IOT : Emitter.IOT).persistent(5000).beforeSendListener(new IMessageListener() { // from class: com.ltech.smarthome.ui.device.ir.ActIrDiy$$ExternalSyntheticLambda2
            @Override // com.smart.message.base.IMessageListener
            public final void act(DataPackage dataPackage) {
                ActIrDiy.this.lambda$learnIr$9(dataPackage);
            }
        }).receiveListener(new IReceiveListener() { // from class: com.ltech.smarthome.ui.device.ir.ActIrDiy.4
            @Override // com.smart.message.base.IReceiveListener
            public void onSuccess(ResponseMsg msg) {
                if (msg.getResData().length() > 4) {
                    ((ActIrDiyVM) ActIrDiy.this.mViewModel).cmdBuilder.append(msg.getResData());
                    if ((!(z && msg.getResData().length() == 18) && msg.getResData().length() <= 30) || ActIrDiy.this.learnDialog == null) {
                        return;
                    }
                    ActIrDiy.this.dismissIrLearnDialog();
                    Injection.message().removeDataPacket(((ActIrDiyVM) ActIrDiy.this.mViewModel).cmdIndex);
                    if (position >= 0) {
                        ((ActIrDiyVM) ActIrDiy.this.mViewModel).changeKeyData(position, z ? msg.getResData().substring(16, 18) : ((ActIrDiyVM) ActIrDiy.this.mViewModel).cmdBuilder.toString());
                    } else {
                        NavUtils.destination(ActAddIrDiy.class).withLong(Constants.CONTROL_ID, ((ActIrDiyVM) ActIrDiy.this.mViewModel).controlDevice != null ? ((ActIrDiyVM) ActIrDiy.this.mViewModel).controlDevice.getValue().getId() : -1L).withString(Constants.LEARN_CMD, z ? msg.getResData().substring(16, 18) : ((ActIrDiyVM) ActIrDiy.this.mViewModel).cmdBuilder.toString()).navigation(ActIrDiy.this);
                    }
                }
            }

            @Override // com.smart.message.base.IReceiveListener
            public void onTimeout() {
                ActIrDiy.this.dismissIrLearnDialog();
                SmartToast.showShort(R.string.learn_ir_fail);
            }
        }).enqueue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$learnIr$9(DataPackage dataPackage) {
        ((ActIrDiyVM) this.mViewModel).cmdBuilder.setLength(0);
        showIrLearnDialog();
    }

    private int getId() {
        DiyIrParam diyIrParam;
        Device value = ((ActIrDiyVM) this.mViewModel).controlDevice.getValue();
        if (value == null || (diyIrParam = (DiyIrParam) value.getParam(DiyIrParam.class)) == null || diyIrParam.getDiyIrKeyList() == null || diyIrParam.getDiyIrKeyList().size() <= 0) {
            return 1;
        }
        List<DiyIrParam.DiyIrKey> diyIrKeyList = diyIrParam.getDiyIrKeyList();
        Collections.sort(diyIrKeyList, new Comparator<DiyIrParam.DiyIrKey>(this) { // from class: com.ltech.smarthome.ui.device.ir.ActIrDiy.5
            @Override // java.util.Comparator
            public int compare(DiyIrParam.DiyIrKey t1, DiyIrParam.DiyIrKey t2) {
                return t1.getKeyData().compareTo(t2.getKeyData());
            }
        });
        return Integer.parseInt(diyIrKeyList.get(diyIrKeyList.size() - 1).getKeyData(), 16) + 1;
    }
}