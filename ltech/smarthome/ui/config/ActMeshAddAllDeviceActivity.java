package com.ltech.smarthome.ui.config;

import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActivityActMeshAddAllDeviceBinding;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.config.ActMeshScanVM;
import com.ltech.smarthome.ui.home.ActDeviceManage;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.MediaUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.view.CircularProgressView;
import com.ltech.smarthome.view.SignalView;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActMeshAddAllDeviceActivity extends VMActivity<ActivityActMeshAddAllDeviceBinding, ActMeshScanVM> {
    private List<ActMeshScanVM.ScanItem> batchAddList = new ArrayList();
    private BaseQuickAdapter<ActMeshScanVM.ScanItem, BaseViewHolder> deviceAdapter;
    public long placeId;
    private int successfulNum;

    /* JADX INFO: Access modifiers changed from: private */
    public int getSignal(int rssi) {
        if (rssi <= -127) {
            return 0;
        }
        if (rssi <= -80) {
            return 1;
        }
        if (rssi <= -60) {
            return 2;
        }
        return rssi <= -40 ? 3 : 4;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.activity_act_mesh_add_all_device;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.app_str_batch_add));
        setEditString(getString(R.string.app_str_batch_finish));
        ((ActMeshScanVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActMeshScanVM) this.mViewModel).roomPickHelper.startObserve(this, ((ActMeshScanVM) this.mViewModel).placeId, 0L);
        ((ActivityActMeshAddAllDeviceBinding) this.mViewBinding).btNext.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.config.ActMeshAddAllDeviceActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActMeshAddAllDeviceActivity.this.lambda$initView$0(view);
            }
        });
        this.batchAddList.clear();
        for (ActMeshScanVM.ScanItem scanItem : ConfigHelper.instance().scanListCache) {
            if (ProductRepository.canAutoAdd(scanItem.productInfo)) {
                this.batchAddList.add(scanItem);
            }
        }
        initAdapter(this.batchAddList);
        ((ActMeshScanVM) this.mViewModel).setScanItems(this.batchAddList);
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActMeshAddAllDeviceActivity$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                ActMeshAddAllDeviceActivity.this.lambda$initView$1();
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        ((ActMeshScanVM) this.mViewModel).addFinish = true;
        setResult(5008);
        finishActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1() {
        ((ActMeshScanVM) this.mViewModel).addFinish = false;
        ((ActMeshScanVM) this.mViewModel).addSingleDevice = false;
        if (this.batchAddList.size() > 0) {
            getWindow().addFlags(128);
            ((ActMeshScanVM) this.mViewModel).startAddDeviceAll(true);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (((ActMeshScanVM) this.mViewModel).addFinish) {
            super.back();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (this.batchAddList.size() == 0) {
            finish();
        } else if (!((ActMeshScanVM) this.mViewModel).addFinish) {
            MessageDialog.show(this, getString(R.string.tips), getString(R.string.add_not_finish_tip)).setOkButton(getString(R.string.add_continue)).setCancelButton(getString(R.string.add_cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.config.ActMeshAddAllDeviceActivity$$ExternalSyntheticLambda2
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$edit$2;
                    lambda$edit$2 = ActMeshAddAllDeviceActivity.this.lambda$edit$2(baseDialog, view);
                    return lambda$edit$2;
                }
            }).setCancelable(false);
        } else {
            setResult(5007);
            finishActivity();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$edit$2(BaseDialog baseDialog, View view) {
        ((ActMeshScanVM) this.mViewModel).addFinish = true;
        setResult(5008);
        finishActivity();
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActMeshScanVM) this.mViewModel).updateStateLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.config.ActMeshAddAllDeviceActivity$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMeshAddAllDeviceActivity.this.lambda$startObserve$3((ActMeshScanVM.StateParam) obj);
            }
        });
        ((ActMeshScanVM) this.mViewModel).showFinishDialog.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.config.ActMeshAddAllDeviceActivity$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMeshAddAllDeviceActivity.this.lambda$startObserve$4((Void) obj);
            }
        });
        ((ActMeshScanVM) this.mViewModel).numSuccessDevice.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.config.ActMeshAddAllDeviceActivity.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer integer) {
                ActMeshAddAllDeviceActivity actMeshAddAllDeviceActivity = ActMeshAddAllDeviceActivity.this;
                actMeshAddAllDeviceActivity.successfulNum = ((ActMeshScanVM) actMeshAddAllDeviceActivity.mViewModel).numSuccessDevice.getValue().intValue();
                ((ActivityActMeshAddAllDeviceBinding) ActMeshAddAllDeviceActivity.this.mViewBinding).tvNum.setText(String.valueOf(((ActMeshScanVM) ActMeshAddAllDeviceActivity.this.mViewModel).numSuccessDevice.getValue()));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(ActMeshScanVM.StateParam stateParam) {
        ActMeshScanVM.ScanItem item = this.deviceAdapter.getItem(stateParam.pos);
        if (stateParam == null || item == null) {
            return;
        }
        if (stateParam.state == ActMeshScanVM.StateParam.STATE_ALL_SUCCESS) {
            MediaUtils.get(this).playMedia(R.raw.all_add_success);
        } else if (stateParam.state == ActMeshScanVM.StateParam.STATE_SUCCESS) {
            MediaUtils.get(this).playMedia(R.raw.add_success);
        }
        item.extData = stateParam.state;
        item.progress = stateParam.progress;
        this.deviceAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r2) {
        if (((ActMeshScanVM) this.mViewModel).addFinish) {
            return;
        }
        ((ActMeshScanVM) this.mViewModel).addFinish = true;
        getWindow().clearFlags(128);
        showDialogNum();
    }

    private void initAdapter(List<ActMeshScanVM.ScanItem> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        BaseQuickAdapter<ActMeshScanVM.ScanItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ActMeshScanVM.ScanItem, BaseViewHolder>(R.layout.item_mesh_scan_all_device, list) { // from class: com.ltech.smarthome.ui.config.ActMeshAddAllDeviceActivity.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, ActMeshScanVM.ScanItem item) {
                helper.setImageResource(R.id.iv_icon, item.productInfo.getDefaultIconRes());
                helper.setText(R.id.tv_name, item.name);
                ((SignalView) helper.getView(R.id.signalView)).setCurValue(ActMeshAddAllDeviceActivity.this.getSignal(item.bluetoothDevice.getRssi()));
                helper.setGone(R.id.iv_upgrade_waiting, false);
                helper.setGone(R.id.iv_upgrade_success, false);
                helper.setGone(R.id.iv_upgrade_fail, false);
                if (item.extData == ActMeshScanVM.StateParam.STATE_SUCCESS) {
                    helper.setGone(R.id.iv_upgrade_success, true);
                    return;
                }
                if (item.extData == ActMeshScanVM.StateParam.STATE_FAILED) {
                    helper.setGone(R.id.iv_upgrade_fail, true);
                } else if (item.extData == ActMeshScanVM.StateParam.STATE_LOADING) {
                    helper.setGone(R.id.iv_upgrade_waiting, true);
                    ((CircularProgressView) helper.getView(R.id.iv_upgrade_waiting)).setProgress(item.progress);
                }
            }
        };
        this.deviceAdapter = baseQuickAdapter;
        baseQuickAdapter.bindToRecyclerView(((ActivityActMeshAddAllDeviceBinding) this.mViewBinding).rvContent);
        ((ActivityActMeshAddAllDeviceBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
    }

    public void showDialogNum() {
        int i = this.successfulNum;
        MessageDialog.show(this, getString(R.string.add_finish_success) + i + "  " + getString(R.string.add_finish_fai) + (this.batchAddList.size() - i), getString(R.string.add_finish_tip)).setOkButton(getString(R.string.go_to_setting), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.config.ActMeshAddAllDeviceActivity$$ExternalSyntheticLambda0
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showDialogNum$5;
                lambda$showDialogNum$5 = ActMeshAddAllDeviceActivity.this.lambda$showDialogNum$5(baseDialog, view);
                return lambda$showDialogNum$5;
            }
        }).setCancelButton(getString(R.string.add_continue), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.config.ActMeshAddAllDeviceActivity$$ExternalSyntheticLambda1
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showDialogNum$6;
                lambda$showDialogNum$6 = ActMeshAddAllDeviceActivity.this.lambda$showDialogNum$6(baseDialog, view);
                return lambda$showDialogNum$6;
            }
        }).setCancelable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showDialogNum$5(BaseDialog baseDialog, View view) {
        setResult(5007);
        finishActivity();
        NavUtils.destination(ActDeviceManage.class).withLong(Constants.PLACE_ID, ((ActMeshScanVM) this.mViewModel).placeId).withBoolean(Constants.SHOW_UNCONFIG_ROOM, true).navigation(this);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showDialogNum$6(BaseDialog baseDialog, View view) {
        setResult(5008);
        finishActivity();
        return false;
    }
}