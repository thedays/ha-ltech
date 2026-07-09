package com.ltech.smarthome.ui.device.central.tepanel;

import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.FragmentUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActTePanelBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.G4teBleParam;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.message.base.BaseCmdParam;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActTePanel extends BaseControlActivity<ActTePanelBinding, ActTePanelVM> implements ISelectAction {
    private String sceneInstruct;
    private int subType;
    private List<Fragment> fragmentList = new ArrayList();
    private MutableLiveData<Integer> chooseTabEvent = new MutableLiveData<>(0);

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_te_panel;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$edit$8(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        ((ActTePanelVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActTePanelVM) this.mViewModel).selectAction = getIntent().getBooleanExtra(Constants.SELECT_ACTION, false);
        int intExtra = getIntent().getIntExtra(Constants.SUB_TYPE, 1);
        this.subType = intExtra;
        initFragmentList(intExtra);
        ((ActTePanelBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.central.tepanel.ActTePanel$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActTePanel.this.lambda$initView$0((View) obj);
            }
        }));
        ((ActTePanelBinding) this.mViewBinding).title.ivTip.setVisibility(8);
        if (((ActTePanelVM) this.mViewModel).selectAction) {
            ((ActTePanelBinding) this.mViewBinding).refreshLayout.setEnableRefresh(false);
            return;
        }
        ((ActTePanelBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((ActTePanelBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((ActTePanelBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((ActTePanelBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.device.central.tepanel.ActTePanel$$ExternalSyntheticLambda4
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ActTePanel.this.lambda$initView$1(refreshLayout);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        int id = view.getId();
        if (id == R.id.tv_ac) {
            ((ActTePanelVM) this.mViewModel).isAcControl = true;
            this.chooseTabEvent.setValue(0);
        } else if (id == R.id.tv_air) {
            ((ActTePanelVM) this.mViewModel).isAcControl = false;
            this.chooseTabEvent.setValue(1);
        }
        ((ActTePanelVM) this.mViewModel).g4teParam.setValue(((ActTePanelVM) this.mViewModel).g4teParam.getValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(RefreshLayout refreshLayout) {
        if (((ActTePanelVM) this.mViewModel).controlObject.getValue() != null) {
            startQuery();
        }
        ((ActTePanelBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActTePanelVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.tepanel.ActTePanel$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActTePanel.this.lambda$startObserve$2((Device) obj);
            }
        });
        if (((ActTePanelVM) this.mViewModel).selectAction) {
            setEditString(getString(R.string.save));
        } else {
            setEditImage(R.mipmap.ic_setting);
            ((ActTePanelVM) this.mViewModel).initDataListener(Injection.repo().device().getDeviceById(((ActTePanelVM) this.mViewModel).controlId));
        }
        this.chooseTabEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.tepanel.ActTePanel$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActTePanel.this.lambda$startObserve$3((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Device device) {
        if (device == null) {
            return;
        }
        setTitle(device.getName());
        G4teBleParam g4teBleParam = (G4teBleParam) device.getParam(G4teBleParam.class);
        if (g4teBleParam.getSubType() == 1) {
            ((ActTePanelBinding) this.mViewBinding).layoutTab.setVisibility(8);
        } else if (g4teBleParam.getSubType() == 2) {
            ((ActTePanelVM) this.mViewModel).isAcControl = false;
            ((ActTePanelBinding) this.mViewBinding).layoutTab.setVisibility(8);
        }
        if (((ActTePanelVM) this.mViewModel).selectAction) {
            if (!TextUtils.isEmpty(SceneHelper.instance().selectInstruct)) {
                String cmdData = SceneHelper.getCmdData(SceneHelper.instance().selectInstruct);
                this.sceneInstruct = cmdData;
                if (cmdData.length() >= 24 && "08".equals(this.sceneInstruct.substring(2, 4))) {
                    String substring = this.sceneInstruct.substring(10);
                    this.sceneInstruct = substring;
                    g4teBleParam.on = Integer.valueOf(substring.substring(0, 2), 16).intValue();
                    g4teBleParam.temperature = Integer.valueOf(this.sceneInstruct.substring(2, 4), 16).intValue();
                    g4teBleParam.mode = Integer.valueOf(this.sceneInstruct.substring(4, 6), 16).intValue();
                    g4teBleParam.speed = Integer.valueOf(this.sceneInstruct.substring(6, 8), 16).intValue();
                    g4teBleParam.direction = Integer.valueOf(this.sceneInstruct.substring(8, 10), 16).intValue();
                    g4teBleParam.timeOn = Integer.valueOf(this.sceneInstruct.substring(10, 12), 16).intValue();
                    g4teBleParam.timeValue = Integer.valueOf(this.sceneInstruct.substring(12, 14), 16).intValue();
                    ((ActTePanelVM) this.mViewModel).isAcControl = true;
                } else if (this.sceneInstruct.length() >= 22 && "0D".equalsIgnoreCase(this.sceneInstruct.substring(2, 4))) {
                    String substring2 = this.sceneInstruct.substring(10);
                    this.sceneInstruct = substring2;
                    g4teBleParam.airOn = Integer.valueOf(substring2.substring(0, 2), 16).intValue();
                    g4teBleParam.airSpeed = Integer.valueOf(this.sceneInstruct.substring(6, 8), 16).intValue();
                    g4teBleParam.airTimeOn = Integer.valueOf(this.sceneInstruct.substring(8, 10), 16).intValue();
                    g4teBleParam.airTimeValue = Integer.valueOf(this.sceneInstruct.substring(10, 12), 16).intValue();
                    ((ActTePanelVM) this.mViewModel).isAcControl = false;
                }
            }
        } else {
            if (this.subType == 3) {
                ((ActTePanelVM) this.mViewModel).isAcControl = SharedPreferenceUtil.queryBooleanValue(String.valueOf(device.getDeviceId()), true);
            }
            startQuery();
        }
        if (this.subType == 3) {
            this.chooseTabEvent.setValue(Integer.valueOf(!((ActTePanelVM) this.mViewModel).isAcControl ? 1 : 0));
        }
        ((ActTePanelVM) this.mViewModel).g4teParam.setValue(g4teBleParam);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Integer num) {
        AppCompatTextView appCompatTextView = ((ActTePanelBinding) this.mViewBinding).tvAc;
        int intValue = num.intValue();
        int i = R.drawable.shape_white_round_bg_10;
        appCompatTextView.setBackgroundResource(intValue == 0 ? R.drawable.shape_white_round_bg_10 : 0);
        ((ActTePanelBinding) this.mViewBinding).tvAc.setTypeface(num.intValue() == 0 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
        AppCompatTextView appCompatTextView2 = ((ActTePanelBinding) this.mViewBinding).tvAir;
        if (num.intValue() != 1) {
            i = 0;
        }
        appCompatTextView2.setBackgroundResource(i);
        ((ActTePanelBinding) this.mViewBinding).tvAir.setTypeface(num.intValue() == 1 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
        FragmentUtils.showHide(num.intValue(), this.fragmentList);
        if (((ActTePanelVM) this.mViewModel).controlObject.getValue() != null) {
            SharedPreferenceUtil.edit().keepShared(String.valueOf(((ActTePanelVM) this.mViewModel).controlObject.getValue().getDeviceId()), num.intValue() == 0);
        }
    }

    private void startQuery() {
        ((ActTePanelVM) this.mViewModel).queryOver = false;
        ((ActTePanelVM) this.mViewModel).queryG4teState();
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.central.tepanel.ActTePanel$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ActTePanel.this.lambda$startQuery$4();
            }
        }, 5000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startQuery$4() {
        if (((ActTePanelVM) this.mViewModel).queryOver) {
            return;
        }
        Device value = ((ActTePanelVM) this.mViewModel).controlObject.getValue();
        value.getDeviceState().setOnlineState(2);
        Injection.repo().device().saveDevice(value);
        ((ActTePanelVM) this.mViewModel).offLine.setValue(true);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (((ActTePanelVM) this.mViewModel).selectAction) {
            ((ActTePanelVM) this.mViewModel).controlObject.setValue((Device) SceneHelper.instance().controlObject);
        } else {
            ((ActTePanelVM) this.mViewModel).controlObject.setValue(Injection.repo().device().getDeviceById(((ActTePanelVM) this.mViewModel).controlId));
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        Device value;
        super.onPause();
        if (((ActTePanelVM) this.mViewModel).selectAction || (value = ((ActTePanelVM) this.mViewModel).controlObject.getValue()) == null) {
            return;
        }
        value.setParam(((ActTePanelVM) this.mViewModel).g4teParam.getValue());
        Injection.repo().device().saveDevice(value);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        String string;
        if (((ActTePanelVM) this.mViewModel).selectAction) {
            if (SceneHelper.instance().baseCmd == null) {
                SmartToast.showShort(R.string.please_choose);
                return;
            }
            SceneHelper.instance().cmdParam = new BaseCmdParam();
            SceneHelper.instance().cmdParam.addExtParam(SceneHelper.OPTION, "");
            boolean z = ((ActTePanelVM) this.mViewModel).isAcControl;
            int i = R.string.app_zdy_status;
            if (z) {
                if (!((ActTePanelVM) this.mViewModel).powerOn.getValue().booleanValue()) {
                    i = R.string.off_1;
                }
                string = getString(i);
            } else {
                if (!((ActTePanelVM) this.mViewModel).airPowerOn.getValue().booleanValue()) {
                    i = R.string.off_1;
                }
                string = getString(i);
            }
            SceneHelper.instance().cmdParam.addExtParam(SceneHelper.OPTION_VALUE, string);
            saveAction(this, getIntent().getBooleanExtra(Constants.IS_LOCAL_SCENE, false));
            return;
        }
        NavHelper.goSetting(((ActTePanelVM) this.mViewModel).controlObject.getValue());
    }

    public void initFragmentList(int subType) {
        this.fragmentList.clear();
        if (subType != 2) {
            this.fragmentList.add(new FtAc());
        }
        if (subType != 1) {
            this.fragmentList.add(new FtAir());
        }
        FragmentUtils.add(getSupportFragmentManager(), this.fragmentList, R.id.fragment_container, 0);
    }
}