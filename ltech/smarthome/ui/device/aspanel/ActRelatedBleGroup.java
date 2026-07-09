package com.ltech.smarthome.ui.device.aspanel;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseSelectGroupActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class ActRelatedBleGroup extends BaseSelectGroupActivity {
    private Device controlDevice;
    private int lightType;
    private boolean needReset;
    private String productId;
    private RelatedInfoExtParam.RelateInfo relateInfo;
    private RelateInfoAssistant relateInfoAssistant;
    private int relatePosition;
    private int selectPosition = -1;
    private int lastSelectPosition = -1;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select_with_icon;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showContent() {
        super.showContent();
        setEditString(getString(R.string.save));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, Group item) {
        helper.setText(R.id.tv_name, item.getGroupName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item)).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseSelectGroupActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.aspanel.ActRelatedBleGroup$$ExternalSyntheticLambda6
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActRelatedBleGroup.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int i2 = this.selectPosition;
        if (i2 != i) {
            this.lastSelectPosition = i2;
            this.selectPosition = i;
            if (i != -1) {
                baseQuickAdapter.notifyItemChanged(i);
            }
            int i3 = this.lastSelectPosition;
            if (i3 != -1) {
                baseQuickAdapter.notifyItemChanged(i3);
            }
        }
        this.relateInfo = RelatedInfoExtParam.RelateInfo.RelatedGroupInfo(((Group) this.mAdapter.getData().get(i)).getGroupId());
    }

    @Override // com.ltech.smarthome.base.BaseSelectGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        long longExtra = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        this.lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        this.relatePosition = getIntent().getIntExtra(Constants.RELATED_POSITION, -1);
        this.needReset = getIntent().getBooleanExtra(Constants.NEED_RESET, false);
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        LHomeLog.i(getClass(), "productId=" + this.productId);
        handleData(Transformations.switchMap(Injection.repo().device().getDeviceFromDb(longExtra), new Function1() { // from class: com.ltech.smarthome.ui.device.aspanel.ActRelatedBleGroup$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$1;
                lambda$startObserve$1 = ActRelatedBleGroup.this.lambda$startObserve$1((Device) obj);
                return lambda$startObserve$1;
            }
        }), new IAction() { // from class: com.ltech.smarthome.ui.device.aspanel.ActRelatedBleGroup$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActRelatedBleGroup.this.lambda$startObserve$2((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$startObserve$1(Device device) {
        if (this.relateInfoAssistant == null) {
            this.controlDevice = device;
            this.relateInfoAssistant = new RelateInfoAssistant(device);
        }
        return this.mRequest.data();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Group group = (Group) it.next();
            if ("3".equalsIgnoreCase(group.getModuleType())) {
                if (this.lightType == -1) {
                    arrayList.add(group);
                } else {
                    int parseInt = Integer.parseInt(group.getControlType());
                    if (ProductId.ID_AS_PANEL_U4S.equals(this.productId)) {
                        if (parseInt == 3 || parseInt == 4) {
                            arrayList.add(group);
                        }
                    } else if (parseInt == this.lightType) {
                        arrayList.add(group);
                    }
                }
            }
        }
        if (arrayList.isEmpty()) {
            showEmpty();
        } else {
            this.mAdapter.setNewData(arrayList);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (this.selectPosition == -1) {
            SmartToast.showShort(R.string.please_choose);
            return;
        }
        if (Injection.state().isConnectOuterNet()) {
            if (this.needReset) {
                this.relateInfoAssistant.resetRelateInfo(this.relatePosition, this.relateInfo);
            } else {
                this.relateInfoAssistant.setRelateInfo(this.relatePosition, this.relateInfo);
            }
            Group group = (Group) this.mAdapter.getData().get(this.selectPosition);
            int groupAddress = group.getGroupAddress();
            int publicationAddress = ((BleParam) this.controlDevice.getParam(BleParam.class)).getPublicationAddress();
            showLoadingDialog(getString(R.string.subscribing), 8000);
            CmdAssistant.getSettingCmdAssistant(null, new int[0]).subscribe(this, groupAddress, publicationAddress, ProductRepository.getGroupAgreementId(group.getModuleType(), group.getControlType()), 1 << this.relatePosition, 0, new IAction() { // from class: com.ltech.smarthome.ui.device.aspanel.ActRelatedBleGroup$$ExternalSyntheticLambda5
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActRelatedBleGroup.this.lambda$edit$3((Boolean) obj);
                }
            });
            return;
        }
        SmartToast.showShort(R.string.no_network);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$3(Boolean bool) {
        if (bool.booleanValue()) {
            uploadData();
        } else {
            dismissLoadingDialog();
            showFailDialog();
        }
    }

    private void uploadData() {
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.controlDevice.getDeviceId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.aspanel.ActRelatedBleGroup$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActRelatedBleGroup.this.lambda$uploadData$4((Disposable) obj);
            }
        }).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.aspanel.ActRelatedBleGroup$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActRelatedBleGroup.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.aspanel.ActRelatedBleGroup$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActRelatedBleGroup.this.lambda$uploadData$5(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$4(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$5(Object obj) throws Exception {
        this.controlDevice.setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().device().saveDevice(this.controlDevice);
        SmartToast.showShort(R.string.save_success);
        setResult(5004);
        finishActivity();
    }

    private void showFailDialog() {
        runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.device.aspanel.ActRelatedBleGroup.1
            @Override // java.lang.Runnable
            public void run() {
                ViewHelpUtil.showBindFailDialog(ActRelatedBleGroup.this);
            }
        });
    }
}