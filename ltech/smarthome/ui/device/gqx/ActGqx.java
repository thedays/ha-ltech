package com.ltech.smarthome.ui.device.gqx;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.blankj.utilcode.util.ActivityUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActGqxBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.singleton.ComboCmdHelper;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectBleDeviceAndGroupNew;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene;
import com.ltech.smarthome.ui.scene.ActSelectCgdPro;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActGqx extends BaseControlActivity<ActGqxBinding, ActGqxVM> {
    static /* synthetic */ boolean lambda$showFailBindDialog$6(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_gqx;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        ((ActGqxVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        initViewPager();
        ((ActGqxVM) this.mViewModel).refreshRelateInfoList();
    }

    private void initViewPager() {
        ((ActGqxVM) this.mViewModel).initTabList();
        ((ActGqxBinding) this.mViewBinding).vp.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.device.gqx.ActGqx.1
            @Override // androidx.viewpager2.adapter.FragmentStateAdapter
            public Fragment createFragment(int position) {
                return ((ActGqxVM) ActGqx.this.mViewModel).tabs.get(position).getFragment();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return ((ActGqxVM) ActGqx.this.mViewModel).tabs.size();
            }
        });
        ((ActGqxBinding) this.mViewBinding).tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqx.2
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                ActGqx actGqx = ActGqx.this;
                BaseNormalActivity baseNormalActivity = actGqx.activity;
                ActGqx actGqx2 = ActGqx.this;
                actGqx.createTabCustomView(baseNormalActivity, tab, actGqx2.getString(((ActGqxVM) actGqx2.mViewModel).tabs.get(tab.getPosition()).getTitleRes()), true);
                ((ActGqxVM) ActGqx.this.mViewModel).index.setValue(Integer.valueOf(tab.getPosition()));
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
                ActGqx actGqx = ActGqx.this;
                BaseNormalActivity baseNormalActivity = actGqx.activity;
                ActGqx actGqx2 = ActGqx.this;
                actGqx.createTabCustomView(baseNormalActivity, tab, actGqx2.getString(((ActGqxVM) actGqx2.mViewModel).tabs.get(tab.getPosition()).getTitleRes()), false);
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
                ActGqx actGqx = ActGqx.this;
                BaseNormalActivity baseNormalActivity = actGqx.activity;
                ActGqx actGqx2 = ActGqx.this;
                actGqx.createTabCustomView(baseNormalActivity, tab, actGqx2.getString(((ActGqxVM) actGqx2.mViewModel).tabs.get(tab.getPosition()).getTitleRes()), true);
            }
        });
        new TabLayoutMediator(((ActGqxBinding) this.mViewBinding).tabLayout, ((ActGqxBinding) this.mViewBinding).vp, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqx$$ExternalSyntheticLambda5
            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
            public final void onConfigureTab(TabLayout.Tab tab, int i) {
                ActGqx.this.lambda$initViewPager$0(tab, i);
            }
        }).attach();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViewPager$0(TabLayout.Tab tab, int i) {
        createTabCustomView(this.activity, tab, getString(((ActGqxVM) this.mViewModel).tabs.get(i).getTitleRes()), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createTabCustomView(Context context, TabLayout.Tab tab, String text, boolean select) {
        View customView = tab.getCustomView();
        if (customView == null) {
            customView = LayoutInflater.from(context).inflate(R.layout.tab_text, (ViewGroup) null);
            tab.setCustomView(customView);
        }
        AppCompatTextView appCompatTextView = (AppCompatTextView) customView.findViewById(R.id.tv_tab);
        appCompatTextView.setText(text);
        if (select) {
            appCompatTextView.setTextColor(ContextCompat.getColor(context, R.color.color_text_black));
            appCompatTextView.getPaint().setFakeBoldText(true);
            if (LanguageUtils.isRussian(context)) {
                appCompatTextView.setTextSize(15.0f);
                return;
            } else {
                appCompatTextView.setTextSize(16.0f);
                return;
            }
        }
        appCompatTextView.setTextColor(ContextCompat.getColor(context, R.color.color_text_gray));
        appCompatTextView.getPaint().setFakeBoldText(false);
        if (LanguageUtils.isRussian(context)) {
            appCompatTextView.setTextSize(15.0f);
        } else {
            appCompatTextView.setTextSize(16.0f);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavHelper.goSetting(((ActGqxVM) this.mViewModel).device);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActGqxVM) this.mViewModel).bindEvent.observe(this, new Observer<Object>() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqx.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(Object o) {
                if (Injection.repo().home().getSelectPlace().getValue().isMember()) {
                    ActGqx.this.showNoPermissionDialog();
                } else if (((ActGqxVM) ActGqx.this.mViewModel).isBind()) {
                    ActGqx.this.showUnbindDialog();
                } else {
                    ActGqx.this.showBindDialog();
                }
            }
        });
        ((ActGqxVM) this.mViewModel).controlObject.observe(this, new Observer<Object>() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqx.4
            @Override // androidx.lifecycle.Observer
            public void onChanged(Object o) {
                if (o instanceof Device) {
                    Device device = (Device) o;
                    ((ActGqxVM) ActGqx.this.mViewModel).productId = device.getProductId();
                    ActGqx.this.setTitle(device.getDeviceName());
                    ((ActGqxBinding) ActGqx.this.mViewBinding).bg.setImageResource(ProductId.ID_SMART_PANEL_GQ.equalsIgnoreCase(device.getProductId()) ? R.mipmap.bg_gq : R.mipmap.bg_gqx);
                }
            }
        });
        ((ActGqxVM) this.mViewModel).index.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqx.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer integer) {
                ((ActGqxBinding) ActGqx.this.mViewBinding).vp.setCurrentItem(integer.intValue());
            }
        });
        ((ActGqxVM) this.mViewModel).noPermissionBindEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqx.6
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActGqx.this.showNoPermissionDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showBindDialog() {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.device));
        arrayList.add(getString(R.string.local_scene));
        arrayList.add(getString(R.string.dali_scene));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqx$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActGqx.this.lambda$showBindDialog$1((Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBindDialog$1(Integer num) {
        int intValue = num.intValue();
        if (intValue == 0) {
            NavUtils.destination(ActSmartPanelSelectBleDeviceAndGroupNew.class).withLong(Constants.PLACE_ID, ((ActGqxVM) this.mViewModel).getCurPlace().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActGqxVM) this.mViewModel).controlId).withInt(Constants.RELATED_POSITION, ((ActGqxVM) this.mViewModel).getSelIndex()).withString(Constants.PRODUCT_ID, ((ActGqxVM) this.mViewModel).productId).withBoolean(Constants.GROUP_CONTROL, false).withBoolean(Constants.IS_GQ, true).withInt(Constants.GROUP_RELATE, 1).withInt(Constants.LIGHT_TYPE, ((ActGqxVM) this.mViewModel).controlType).withDefaultRequestCode().navigation(this);
        } else if (intValue == 1) {
            NavUtils.destination(ActSmartPanelSelectScene.class).withLong(Constants.PLACE_ID, ((ActGqxVM) this.mViewModel).getCurPlace().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActGqxVM) this.mViewModel).controlId).withInt(Constants.RELATED_POSITION, ((ActGqxVM) this.mViewModel).getSelIndex()).withBoolean(Constants.IS_GQ, true).withBoolean(Constants.GROUP_CONTROL, false).withDefaultRequestCode().navigation(this);
        } else {
            if (intValue != 2) {
                return;
            }
            NavUtils.destination(ActSelectCgdPro.class).withLong(Constants.PLACE_ID, ((ActGqxVM) this.mViewModel).getCurPlace().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActGqxVM) this.mViewModel).controlId).withBoolean(Constants.GROUP_CONTROL, false).withBoolean(Constants.IS_GQ, true).withInt(Constants.RELATED_POSITION, ((ActGqxVM) this.mViewModel).getSelIndex()).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showUnbindDialog() {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.reset_relate));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqx$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActGqx.this.lambda$showUnbindDialog$3((Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnbindDialog$3(Integer num) {
        ImageTipDialog.asDefault().setTitle(getString(R.string.gqx_click_tip)).setConfirmString(getString(R.string.get_it)).setImageRes(ProductId.ID_SMART_PANEL_GQ.equals(((ActGqxVM) this.mViewModel).productId) ? R.mipmap.gq_pic_click1 : R.mipmap.gqx_pic_click1).setCallback(new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqx$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
            public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                ActGqx.this.lambda$showUnbindDialog$2(imageTipDialog);
            }
        }).showDialog(this.activity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnbindDialog$2(ImageTipDialog imageTipDialog) {
        imageTipDialog.dismissDialog();
        unbindData();
    }

    private void unbindData() {
        showLoadingDialog("");
        ComboCmdHelper.getInstance().unSubscribeAllCmd(this, ((ActGqxVM) this.mViewModel).controlObject.getValue(), ((ActGqxVM) this.mViewModel).getSelIndex(), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqx.7
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                ActGqx.this.dismissLoadingDialog();
                if (responseMsg != null && responseMsg.getStateCode() == 0) {
                    ((ActGqxVM) ActGqx.this.mViewModel).unBindData();
                } else {
                    ActGqx.this.showFailBindDialog(null, 2);
                    ((ActGqxVM) ActGqx.this.mViewModel).uiEvent.setValue(true);
                }
            }
        });
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseControlActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 3001 || data == null) {
            return;
        }
        bindData(data);
    }

    private void bindData(final Intent data) {
        ImageTipDialog.asDefault().setTitle(getString(R.string.gqx_click_tip)).setConfirmString(getString(R.string.get_it)).setImageRes(ProductId.ID_SMART_PANEL_GQ.equals(((ActGqxVM) this.mViewModel).productId) ? R.mipmap.gq_pic_click1 : R.mipmap.gqx_pic_click1).setCallback(new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqx$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
            public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                ActGqx.this.lambda$bindData$4(data, imageTipDialog);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:6:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:9:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$bindData$4(final android.content.Intent r13, final com.ltech.smarthome.view.dialog.ImageTipDialog r14) {
        /*
            r12 = this;
            java.lang.String r0 = "RELATE_ID"
            r1 = -1
            long r0 = r13.getLongExtra(r0, r1)
            java.lang.String r2 = "group_relate"
            r3 = -1
            int r2 = r13.getIntExtra(r2, r3)
            r3 = 2
            java.lang.String r4 = ""
            if (r2 != r3) goto L32
            com.ltech.smarthome.model.Repository r2 = com.ltech.smarthome.model.Injection.repo()
            com.ltech.smarthome.model.repo.ifun.IGroup r2 = r2.group()
            com.ltech.smarthome.model.bean.Group r0 = r2.getGroupById(r0)
            r1 = r0
            com.ltech.smarthome.model.bean.Group r1 = (com.ltech.smarthome.model.bean.Group) r1
            java.lang.String r1 = r0.getName()
            com.ltech.smarthome.model.IComboCmd r2 = com.ltech.smarthome.singleton.ComboCmdHelper.getInstance()
            java.util.List r2 = r2.getDefaultComboCmdActions(r0)
        L2f:
            r8 = r0
            r9 = r2
            goto L78
        L32:
            r3 = 1
            if (r2 != r3) goto L51
            com.ltech.smarthome.model.Repository r2 = com.ltech.smarthome.model.Injection.repo()
            com.ltech.smarthome.model.repo.ifun.IDevice r2 = r2.device()
            com.ltech.smarthome.model.bean.Device r0 = r2.getDeviceById(r0)
            r1 = r0
            com.ltech.smarthome.model.bean.Device r1 = (com.ltech.smarthome.model.bean.Device) r1
            java.lang.String r1 = r0.getName()
            com.ltech.smarthome.model.IComboCmd r2 = com.ltech.smarthome.singleton.ComboCmdHelper.getInstance()
            java.util.List r2 = r2.getDefaultComboCmdActions(r0)
            goto L2f
        L51:
            r3 = 3
            if (r2 != r3) goto L74
            com.ltech.smarthome.model.Repository r2 = com.ltech.smarthome.model.Injection.repo()
            com.ltech.smarthome.model.repo.ifun.IScene r2 = r2.scene()
            com.ltech.smarthome.model.bean.Scene r0 = r2.getSceneBySceneId(r0)
            r1 = r0
            com.ltech.smarthome.model.bean.Scene r1 = (com.ltech.smarthome.model.bean.Scene) r1
            java.lang.String r1 = r0.getName()
            com.ltech.smarthome.model.IComboCmd r2 = com.ltech.smarthome.singleton.ComboCmdHelper.getInstance()
            int r3 = r0.getSceneNum()
            java.util.List r2 = r2.getSceneDefaultComboCmdActions(r0, r3)
            goto L2f
        L74:
            r0 = 0
            r8 = r0
            r9 = r8
            r1 = r4
        L78:
            if (r9 == 0) goto L9c
            r12.showLoadingDialog(r4)
            com.ltech.smarthome.model.IComboCmd r5 = com.ltech.smarthome.singleton.ComboCmdHelper.getInstance()
            VM extends com.ltech.smarthome.base.BaseViewModel r0 = r12.mViewModel
            com.ltech.smarthome.ui.device.gqx.ActGqxVM r0 = (com.ltech.smarthome.ui.device.gqx.ActGqxVM) r0
            com.ltech.smarthome.base.SingleLiveEvent<java.lang.Object> r0 = r0.controlObject
            java.lang.Object r7 = r0.getValue()
            VM extends com.ltech.smarthome.base.BaseViewModel r0 = r12.mViewModel
            com.ltech.smarthome.ui.device.gqx.ActGqxVM r0 = (com.ltech.smarthome.ui.device.gqx.ActGqxVM) r0
            int r10 = r0.getSelIndex()
            com.ltech.smarthome.ui.device.gqx.ActGqx$8 r11 = new com.ltech.smarthome.ui.device.gqx.ActGqx$8
            r11.<init>()
            r6 = r12
            r5.subscribeCmd(r6, r7, r8, r9, r10, r11)
        L9c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.gqx.ActGqx.lambda$bindData$4(android.content.Intent, com.ltech.smarthome.view.dialog.ImageTipDialog):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showFailBindDialog(final Intent data, final int type) {
        MessageDialog.show(this, getString(R.string.app_str_operation_failure), getString(R.string.app_str_binding_fail_tip)).setCancelable(false).setOkButton(getString(R.string.go_continue), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqx$$ExternalSyntheticLambda1
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showFailBindDialog$5;
                lambda$showFailBindDialog$5 = ActGqx.this.lambda$showFailBindDialog$5(type, data, baseDialog, view);
                return lambda$showFailBindDialog$5;
            }
        }).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqx$$ExternalSyntheticLambda2
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return ActGqx.lambda$showFailBindDialog$6(baseDialog, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showFailBindDialog$5(int i, Intent intent, BaseDialog baseDialog, View view) {
        if (i == 1) {
            bindData(intent);
            return false;
        }
        if (i != 2) {
            return false;
        }
        unbindData();
        return false;
    }
}