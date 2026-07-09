package com.ltech.smarthome.ui.device.e6knob;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActE6PanelBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.E6ExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.E6SelectObjectDialog;
import com.ltech.smarthome.view.dialog.E6TipDialog;
import com.ltech.smarthome.view.dialog.EditNumberDialog;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.StringUtils;
import java.util.Arrays;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes4.dex */
public class ActE6Panel extends BaseControlActivity<ActE6PanelBinding, ActE6PanelVM> {
    private int curPosition;
    private int lightType;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_e6_panel;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        this.lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        ((ActE6PanelBinding) this.mViewBinding).tvType.setText(E6Helper.instance().getTypeName(this, this.lightType));
        ((ActE6PanelBinding) this.mViewBinding).setManagerOrOwner(Boolean.valueOf(isOwnerOrManager()));
        initTab();
        initKeyAdapter();
        ((ActE6PanelBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6Panel$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActE6Panel.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        switch (view.getId()) {
            case R.id.iv_control /* 2131296985 */:
                ((ActE6PanelVM) this.mViewModel).goControl(this.lightType);
                break;
            case R.id.iv_doubt /* 2131297028 */:
                E6TipDialog.asDefault().setType(this.lightType).showDialog(this.activity);
                break;
            case R.id.key_action /* 2131297331 */:
                if (isOwnerOrManager()) {
                    this.curPosition = ((ActE6PanelBinding) this.mViewBinding).tabs.getSelectedTabPosition() + 1;
                    E6Helper.instance().setControlObject(((ActE6PanelVM) this.mViewModel).controlObject.getValue(), this.curPosition);
                    NavUtils.destination(ActSelectE6Action.class).withInt(Constants.LIGHT_TYPE, this.lightType).withString(Constants.PRODUCT_ID, ((ActE6PanelVM) this.mViewModel).productId).withDefaultRequestCode().navigation(this);
                    break;
                }
                break;
            case R.id.key_object /* 2131297332 */:
                int selectedTabPosition = ((ActE6PanelBinding) this.mViewBinding).tabs.getSelectedTabPosition() + 1;
                this.curPosition = selectedTabPosition;
                goSelectObject(selectedTabPosition);
                break;
            case R.id.knob_object /* 2131297333 */:
                this.curPosition = 0;
                goSelectObject(0);
                break;
            case R.id.tv_know /* 2131298730 */:
                ((ActE6PanelBinding) this.mViewBinding).groupGuide.setVisibility(8);
                ((ActE6PanelBinding) this.mViewBinding).layoutBg.setBackgroundColor(getResources().getColor(R.color.white));
                SharedPreferenceUtil.edit().keepShared("need_guide" + ((ActE6PanelVM) this.mViewModel).productId, false);
                break;
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        NavHelper.goSetting(((ActE6PanelVM) this.mViewModel).controlObject.getValue());
    }

    private void goSelectObject(int position) {
        if (isOwnerOrManager()) {
            int[] objectArray = E6Helper.instance().getObjectArray(((ActE6PanelVM) this.mViewModel).extParam, position);
            if (ProductId.ID_KNOB_PANEL_E6D.equals(((ActE6PanelVM) this.mViewModel).productId)) {
                E6SelectObjectDialog.asDefault().setControlType(objectArray[0]).setAddNumber(objectArray[1]).setOnSaveListener(new E6SelectObjectDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6Panel$$ExternalSyntheticLambda4
                    @Override // com.ltech.smarthome.view.dialog.E6SelectObjectDialog.OnSaveListener
                    public final void onSave(int i, int i2) {
                        ActE6Panel.this.lambda$goSelectObject$1(i, i2);
                    }
                }).showDialog(this);
            } else if (ProductId.ID_KNOB_PANEL_E6M.equals(((ActE6PanelVM) this.mViewModel).productId)) {
                EditNumberDialog.asDefault().setTitle(getString(R.string.e6m_add_tip_1)).setRange(1, 512).setErrorTip(getString(R.string.app_str_out_of_range)).setContent(String.valueOf(Math.max(1, Math.min(objectArray[1], 512)))).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6Panel$$ExternalSyntheticLambda5
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActE6Panel.this.lambda$goSelectObject$2((Integer) obj);
                    }
                }).showDialog(this);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$goSelectObject$1(int i, int i2) {
        setObject(StringUtils.demToHex(i, 2) + StringUtils.demToHex(i2, 4));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$goSelectObject$2(Integer num) {
        setObject("00" + StringUtils.demToHex(num.intValue(), 4));
    }

    private void setObject(final String objectInstruct) {
        ((ActE6PanelVM) this.mViewModel).getCmdHelper(this.curPosition).setE6Object(this, objectInstruct, new IAction() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6Panel$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActE6Panel.this.lambda$setObject$3(objectInstruct, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setObject$3(String str, Boolean bool) {
        if (bool.booleanValue()) {
            ReplaceHelper.instance().backupIndexData(this, ((ActE6PanelVM) this.mViewModel).controlObject.getValue().getDeviceId(), UpdateBackDataRequest.CONTROL_OBJECT, ((ActE6PanelVM) this.mViewModel).getCmdHelper(this.curPosition).setE6Object(str), this.curPosition);
            E6ExtParam.E6Action action = ((ActE6PanelVM) this.mViewModel).extParam.getAction(this.curPosition);
            action.setObjectInstruct(str);
            ((ActE6PanelVM) this.mViewModel).extParam.setAction(this.curPosition, action);
            ((ActE6PanelVM) this.mViewModel).uploadData();
        }
    }

    private void initTab() {
        TabLayout.Tab text = ((ActE6PanelBinding) this.mViewBinding).tabs.newTab().setText(R.string.app_str_smart_panel_g4_key1);
        TabLayout.Tab text2 = ((ActE6PanelBinding) this.mViewBinding).tabs.newTab().setText(R.string.app_str_smart_panel_g4_key2);
        TabLayout.Tab text3 = ((ActE6PanelBinding) this.mViewBinding).tabs.newTab().setText(R.string.app_str_smart_panel_g4_key3);
        TabLayout.Tab text4 = ((ActE6PanelBinding) this.mViewBinding).tabs.newTab().setText(R.string.app_str_smart_panel_g4_key4);
        ((ActE6PanelBinding) this.mViewBinding).tabs.addTab(text);
        ((ActE6PanelBinding) this.mViewBinding).tabs.addTab(text2);
        ((ActE6PanelBinding) this.mViewBinding).tabs.addTab(text3);
        ((ActE6PanelBinding) this.mViewBinding).tabs.addTab(text4);
        ((ActE6PanelBinding) this.mViewBinding).tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6Panel.1
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                ActE6Panel.this.showKeyInfo(tab.getPosition() + 1);
            }
        });
    }

    private void initKeyAdapter() {
        BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Integer, BaseViewHolder>(R.layout.item_e6_panel_key, Arrays.asList(0, 1, 2, 3)) { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6Panel.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Integer integer) {
                helper.itemView.setBackgroundResource(HelpUtils.getDrawableResourceArray(ActE6Panel.this.activity, R.array.e6_panel_bg_res)[integer.intValue()]);
            }
        };
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6Panel$$ExternalSyntheticLambda2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActE6Panel.this.lambda$initKeyAdapter$4(baseQuickAdapter2, view, i);
            }
        });
        baseQuickAdapter.bindToRecyclerView(((ActE6PanelBinding) this.mViewBinding).rvKey);
        ((ActE6PanelBinding) this.mViewBinding).rvKey.setLayoutManager(new GridLayoutManager(this, 2));
        ((ActE6PanelBinding) this.mViewBinding).rvKey.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initKeyAdapter$4(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        ((ActE6PanelVM) this.mViewModel).executeCurScene(this, i);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActE6PanelVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActE6PanelVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        if (SharedPreferenceUtil.queryBooleanValue("need_guide" + ((ActE6PanelVM) this.mViewModel).productId, true)) {
            ((ActE6PanelBinding) this.mViewBinding).groupGuide.setVisibility(0);
            ((ActE6PanelBinding) this.mViewBinding).layoutBg.setBackgroundColor(getResources().getColor(R.color.color_bg_1));
        }
        if (ProductId.ID_KNOB_PANEL_E6A.equals(((ActE6PanelVM) this.mViewModel).productId) || ProductId.ID_KNOB_PANEL_E6T.equals(((ActE6PanelVM) this.mViewModel).productId) || ProductId.ID_KNOB_PANEL_E6M.equals(((ActE6PanelVM) this.mViewModel).productId)) {
            ((ActE6PanelBinding) this.mViewBinding).knobObject.setVisibility(8);
            ((ActE6PanelBinding) this.mViewBinding).keyObject.setVisibility(8);
            ((ActE6PanelBinding) this.mViewBinding).layoutKnob.setVisibility(8);
        }
        ((ActE6PanelBinding) this.mViewBinding).tvInstruction.setText(String.format("%s-%s", getString(R.string.sq_instructions), E6Helper.instance().getTypeName(this, this.lightType)));
        E6Helper.instance().initKnobInfoView(this, ((ActE6PanelBinding) this.mViewBinding).rvKnobAction, this.lightType);
        ((ActE6PanelVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6Panel$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActE6Panel.this.lambda$startObserve$5((Device) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Device device) {
        if (device == null) {
            return;
        }
        setTitle(device.getDeviceName());
        ((ActE6PanelVM) this.mViewModel).extParam.fillMapWithString(device.getExtParam());
        if (device.isVirtual()) {
            ((ActE6PanelVM) this.mViewModel).extParam.initActionByType(device);
            device.setExtParam(((ActE6PanelVM) this.mViewModel).extParam.getParamString());
        } else if (this.isFirst) {
            this.isFirst = false;
            queryBindParams();
        }
        if (((ActE6PanelVM) this.mViewModel).extParam.getAction(0) != null) {
            ((ActE6PanelBinding) this.mViewBinding).tvObject.setText(getObjectName(0));
        }
        showKeyInfo(((ActE6PanelBinding) this.mViewBinding).tabs.getSelectedTabPosition() + 1);
    }

    public void queryBindParams() {
        CmdAssistant.getQueryCmdAssistant(((ActE6PanelVM) this.mViewModel).controlObject.getValue(), E6Helper.instance().getQueryZone(((ActE6PanelVM) this.mViewModel).productId)).queryE6BindParam(this, new IAction() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6Panel$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActE6Panel.this.lambda$queryBindParams$6((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryBindParams$6(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getStateCode() != 0) {
            return;
        }
        ((ActE6PanelVM) this.mViewModel).convertParams(responseMsg.getResData());
        if (((ActE6PanelVM) this.mViewModel).extParam.getAction(0) != null) {
            ((ActE6PanelBinding) this.mViewBinding).tvObject.setText(getObjectName(0));
        }
        showKeyInfo(((ActE6PanelBinding) this.mViewBinding).tabs.getSelectedTabPosition() + 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showKeyInfo(int position) {
        if (((ActE6PanelVM) this.mViewModel).extParam.getAction(position) != null) {
            ((ActE6PanelBinding) this.mViewBinding).tvKeyObject.setText(getObjectName(position));
            String actionContent = ((ActE6PanelVM) this.mViewModel).extParam.getAction(position).getActionContent(this, ((ActE6PanelVM) this.mViewModel).controlObject.getValue());
            if (actionContent.startsWith(MqttTopic.MULTI_LEVEL_WILDCARD)) {
                ((ActE6PanelBinding) this.mViewBinding).tvKeyAction.setText(actionContent.substring(7));
                ((ActE6PanelBinding) this.mViewBinding).civColor.setVisibility(0);
                ((ActE6PanelBinding) this.mViewBinding).civColor.setImageDrawable(new ColorDrawable(Color.parseColor(actionContent.substring(0, 7))));
            } else {
                ((ActE6PanelBinding) this.mViewBinding).tvKeyAction.setText(actionContent);
                ((ActE6PanelBinding) this.mViewBinding).civColor.setVisibility(8);
            }
        }
    }

    private String getObjectName(int position) {
        return ((ActE6PanelVM) this.mViewModel).extParam.getAction(position).getObjectName(this, ((ActE6PanelVM) this.mViewModel).productId);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((ActE6PanelVM) this.mViewModel).controlObject.setValue(Injection.repo().device().getDeviceById(((ActE6PanelVM) this.mViewModel).controlId));
    }
}