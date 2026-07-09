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
import com.ltech.smarthome.databinding.ActTrigToBleBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.TrigToBleExtParam;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.scene.ActSelectCgdPro;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.ui.select.ActSelectAction;
import com.ltech.smarthome.ui.select.ActTrigSelectLocalScene;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.TbUtils;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.message.ResponseMsg;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActTrigToBle extends BaseControlActivity<ActTrigToBleBinding, ActTrigToBleVM> {
    private String[] chArray1 = {"CH1 ON", "CH1 OFF", "CH2 ON", "CH2 OFF", "CH3 ON", "CH3 OFF", "CH4 ON", "CH4 OFF"};
    private String[] chArray2 = {"CH1 ON", "CH2 ON", "CH3 ON", "CH4 ON"};
    private String[] chArray3 = {"CH1", "CH2", "CH1、2", "CH3", "CH1、3", "CH2、3", "CH1、2、3", "CH4", "CH1、4", "CH2、4", "CH1、2、4", "CH3、4", "CH1、3、4", "CH2、3、4", "CH1、2、3、4"};
    BaseQuickAdapter<TbUtils.ContentState, BaseViewHolder> mAdapter;
    private TrigToBleExtParam param;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_trig_to_ble;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        ((ActTrigToBleVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActTrigToBleVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActTrigToBleVM) this.mViewModel).subType = getIntent().getIntExtra(Constants.SUB_TYPE, 3);
        BaseQuickAdapter<TbUtils.ContentState, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<TbUtils.ContentState, BaseViewHolder>(R.layout.item_trig_to_ble, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.trig.ActTrigToBle.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, TbUtils.ContentState item) {
                String str = item.action;
                int i = R.mipmap.ic_device_none;
                if (str != null) {
                    if (!TextUtils.isEmpty(item.action)) {
                        i = item.iconRes;
                    }
                    helper.setImageResource(R.id.iv_icon, i).setText(R.id.tv_num, (helper.getLayoutPosition() + 1) + "").setText(R.id.tv_action, item.state).setText(R.id.tv_name, !TextUtils.isEmpty(item.name) ? item.name : ActTrigToBle.this.getString(R.string.no_bind_object));
                } else {
                    helper.setImageResource(R.id.iv_icon, R.mipmap.ic_device_none).setText(R.id.tv_action, R.string.none).setText(R.id.tv_num, (helper.getLayoutPosition() + 1) + "").setText(R.id.tv_name, !TextUtils.isEmpty(item.name) ? item.name : ActTrigToBle.this.getString(R.string.no_bind_object));
                }
                if (((ActTrigToBleVM) ActTrigToBle.this.mViewModel).subType != 1) {
                    if (((ActTrigToBleVM) ActTrigToBle.this.mViewModel).subType != 2) {
                        if (((ActTrigToBleVM) ActTrigToBle.this.mViewModel).subType == 3) {
                            helper.setText(R.id.tv_sub, ActTrigToBle.this.chArray3[helper.getLayoutPosition()]);
                            return;
                        }
                        return;
                    }
                    helper.setText(R.id.tv_sub, ActTrigToBle.this.chArray2[helper.getLayoutPosition()]);
                    return;
                }
                helper.setText(R.id.tv_sub, ActTrigToBle.this.chArray1[helper.getLayoutPosition()]);
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigToBle$$ExternalSyntheticLambda6
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActTrigToBle.this.lambda$initView$0(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActTrigToBleBinding) this.mViewBinding).rvMode);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigToBle.2
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int position) {
                return (position == ActTrigToBle.this.mAdapter.getItemCount() - 1 && ((ActTrigToBleVM) ActTrigToBle.this.mViewModel).subType == 3) ? 2 : 1;
            }
        });
        ((ActTrigToBleBinding) this.mViewBinding).rvMode.setLayoutManager(gridLayoutManager);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        ((ActTrigToBleVM) this.mViewModel).selectChannel = i + 1;
        TbUtils.ContentState item = this.mAdapter.getItem(i);
        if (item != null) {
            if (item.action != null && !TextUtils.isEmpty(item.action)) {
                showUnbindDialog(i);
                return;
            } else {
                showAddDialog();
                return;
            }
        }
        showAddDialog();
    }

    private void showUnbindDialog(final int position) {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.reset_relate));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigToBle$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActTrigToBle.this.lambda$showUnbindDialog$1(position, (Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnbindDialog$1(int i, Integer num) {
        showLoadingDialog("");
        ((ActTrigToBleVM) this.mViewModel).cancelTrigData(i + 1, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigToBle.3
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                ActTrigToBle.this.dismissLoadingDialog();
                if (responseMsg != null) {
                    ((ActTrigToBleVM) ActTrigToBle.this.mViewModel).actionContent.get(((ActTrigToBleVM) ActTrigToBle.this.mViewModel).selectChannel - 1).setActiontype(0);
                    ((ActTrigToBleVM) ActTrigToBle.this.mViewModel).actionContent.get(((ActTrigToBleVM) ActTrigToBle.this.mViewModel).selectChannel - 1).setCustomerName("");
                    ActTrigToBle.this.updateParamExt();
                } else {
                    ActTrigToBle actTrigToBle = ActTrigToBle.this;
                    actTrigToBle.showErrorDialog(actTrigToBle.getString(R.string.save_fail));
                }
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        if (((ActTrigToBleVM) this.mViewModel).controlId != -1) {
            ((ActTrigToBleVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(((ActTrigToBleVM) this.mViewModel).controlId));
        }
        ((ActTrigToBleVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigToBle$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActTrigToBle.this.lambda$startObserve$2((Device) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Device device) {
        if (device != null) {
            setTitle(device.getDeviceName());
            TrigToBleExtParam trigToBleExtParam = new TrigToBleExtParam();
            this.param = trigToBleExtParam;
            trigToBleExtParam.fillMapWithString(device.getExtParam());
            ArrayList arrayList = new ArrayList();
            int i = ((ActTrigToBleVM) this.mViewModel).subType;
            int i2 = i != 1 ? i != 2 ? 15 : 4 : 8;
            for (int i3 = 1; i3 <= i2; i3++) {
                TrigToBleExtParam trigToBleExtParam2 = this.param;
                if (trigToBleExtParam2 != null && trigToBleExtParam2.getRelateInfo(i3) != null) {
                    arrayList.add(this.param.getRelateInfo(i3));
                } else {
                    arrayList.add(new TrigToBleExtParam.TrigRelateInfo());
                }
            }
            ((ActTrigToBleVM) this.mViewModel).actionContent = arrayList;
            this.mAdapter.replaceData(TbUtils.getContentStates(this, arrayList));
            ((ActTrigToBleVM) this.mViewModel).setTrigType(((ActTrigToBleVM) this.mViewModel).subType);
            if (((ActTrigToBleVM) this.mViewModel).subType - 1 == 0) {
                ((ActTrigToBleBinding) this.mViewBinding).tvBindTitle.setVisibility(0);
                ((ActTrigToBleBinding) this.mViewBinding).ivBg.setVisibility(0);
                ((ActTrigToBleBinding) this.mViewBinding).ivBg.setImageResource(R.mipmap.trig_pic_8switch);
            } else if (((ActTrigToBleVM) this.mViewModel).subType - 1 == 1) {
                ((ActTrigToBleBinding) this.mViewBinding).tvBindTitle.setVisibility(0);
                ((ActTrigToBleBinding) this.mViewBinding).ivBg.setVisibility(0);
                ((ActTrigToBleBinding) this.mViewBinding).ivBg.setImageResource(R.mipmap.trig_pic_4switch);
            }
        }
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
        NavHelper.goSetting(((ActTrigToBleVM) this.mViewModel).controlDevice.getValue());
    }

    private void showAddDialog() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.device));
        arrayList.add(getString(R.string.local_scene));
        arrayList.add(getString(R.string.dali_scene));
        if (!TextUtils.isEmpty(this.mAdapter.getItem(((ActTrigToBleVM) this.mViewModel).selectChannel - 1).action)) {
            arrayList.add(getString(R.string.rename));
        } else {
            arrayList.add(getString(R.string.customize_name));
        }
        SceneHelper.instance().reset();
        SceneHelper.instance().bindingType = 4;
        SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigToBle$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActTrigToBle.this.lambda$showAddDialog$4((Integer) obj);
            }
        }).setSelectList(arrayList).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAddDialog$4(Integer num) {
        int intValue = num.intValue();
        if (intValue == 1) {
            NavUtils.destination(ActTrigSelectLocalScene.class).withLong(Constants.PLACE_ID, ((ActTrigToBleVM) this.mViewModel).placeId).withLong(Constants.CONTROL_ID, ((ActTrigToBleVM) this.mViewModel).controlId).withBoolean(Constants.SELECT_ACTION, true).withDefaultRequestCode().navigation(this.activity);
            return;
        }
        if (intValue == 2) {
            NavUtils.destination(ActSelectCgdPro.class).withLong(Constants.PLACE_ID, ((ActTrigToBleVM) this.mViewModel).placeId).withLong(Constants.CONTROL_ID, ((ActTrigToBleVM) this.mViewModel).controlId).withDefaultRequestCode().navigation(this);
        } else if (intValue == 3) {
            EditDialog.asDefault().setContent(((ActTrigToBleVM) this.mViewModel).actionContent.get(((ActTrigToBleVM) this.mViewModel).selectChannel - 1).getCustomerName()).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigToBle$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActTrigToBle.this.lambda$showAddDialog$3((String) obj);
                }
            }).showDialog(this);
        } else {
            NavUtils.destination(ActSelectAction.class).withLong(Constants.PLACE_ID, ((ActTrigToBleVM) this.mViewModel).placeId).withLong(Constants.CONTROL_ID, ((ActTrigToBleVM) this.mViewModel).controlId).withBoolean(Constants.SELECT_ACTION, true).withDefaultRequestCode().navigation(this.activity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAddDialog$3(String str) {
        ((ActTrigToBleVM) this.mViewModel).actionContent.get(((ActTrigToBleVM) this.mViewModel).selectChannel - 1).setCustomerName(str);
        updateParamExt();
    }

    public void updateParamExt() {
        final Device value = ((ActTrigToBleVM) this.mViewModel).controlDevice.getValue();
        this.param = new TrigToBleExtParam();
        for (int i = 0; i < ((ActTrigToBleVM) this.mViewModel).actionContent.size(); i++) {
            TrigToBleExtParam.TrigRelateInfo trigRelateInfo = ((ActTrigToBleVM) this.mViewModel).actionContent.get(i);
            if (trigRelateInfo.getActiontype() != 0 || !TextUtils.isEmpty(trigRelateInfo.getCustomerName())) {
                this.param.setRelateInfo(i + 1, ((ActTrigToBleVM) this.mViewModel).actionContent.get(i));
            }
        }
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(value.getDeviceId(), this.param.getRelateParamMapString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigToBle$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActTrigToBle.this.lambda$updateParamExt$5((Disposable) obj);
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigToBle$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActTrigToBle.this.lambda$updateParamExt$6(value, obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigToBle$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActTrigToBle.this.lambda$updateParamExt$7((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateParamExt$5(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateParamExt$6(Device device, Object obj) throws Exception {
        device.setExtParam(this.param.getRelateParamMapString());
        Injection.repo().device().saveDevice(device);
        this.mAdapter.setData(((ActTrigToBleVM) this.mViewModel).selectChannel - 1, TbUtils.getContentState(this, ((ActTrigToBleVM) this.mViewModel).actionContent.get(((ActTrigToBleVM) this.mViewModel).selectChannel - 1)));
        showSuccessDialog(getString(R.string.save_success));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateParamExt$7(Throwable th) throws Exception {
        showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseControlActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            List<Scene.SceneContent> convert2LocalSceneContents = SceneHelper.instance().convert2LocalSceneContents();
            if (convert2LocalSceneContents.isEmpty()) {
                return;
            }
            TrigToBleExtParam.TrigRelateInfo trigRelateInfo = new TrigToBleExtParam.TrigRelateInfo();
            trigRelateInfo.setActiontype(convert2LocalSceneContents.get(0).getActionType());
            trigRelateInfo.setExecutecommand(convert2LocalSceneContents.get(0).getExecuteCommand());
            final TbUtils.ContentState contentState = TbUtils.getContentState(this, trigRelateInfo);
            ((ActTrigToBleVM) this.mViewModel).actionContent.set(((ActTrigToBleVM) this.mViewModel).selectChannel - 1, trigRelateInfo);
            showLoadingDialog(getString(R.string.saving));
            ((ActTrigToBleVM) this.mViewModel).setTrigData(((ActTrigToBleVM) this.mViewModel).selectChannel, contentState.action, contentState.address, false, contentState.zone, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigToBle.4
                @Override // com.ltech.smarthome.base.IAction
                public void act(ResponseMsg responseMsg) {
                    ActTrigToBle.this.dismissLoadingDialog();
                    if (responseMsg != null) {
                        ActTrigToBle.this.mAdapter.setData(((ActTrigToBleVM) ActTrigToBle.this.mViewModel).selectChannel - 1, contentState);
                    } else {
                        ActTrigToBle actTrigToBle = ActTrigToBle.this;
                        actTrigToBle.showErrorDialog(actTrigToBle.getString(R.string.save_fail));
                    }
                }
            });
        }
    }
}