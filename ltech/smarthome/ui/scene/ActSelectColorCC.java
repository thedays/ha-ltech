package com.ltech.smarthome.ui.scene;

import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActSelectColorCcBinding;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.light.ActColorCCLightVM;
import com.ltech.smarthome.ui.device.light.FtColorCCT;
import com.ltech.smarthome.ui.device.light.FtColorCircle;
import com.ltech.smarthome.ui.device.light.FtColorHSL;
import com.ltech.smarthome.ui.device.light.FtColorPushrod;
import com.ltech.smarthome.ui.device.light.FtColorXXY;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.smart.message.utils.LHomeLog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectColorCC extends VMActivity<ActSelectColorCcBinding, ActColorCCLightVM> {
    private int currentTab;
    private MutableLiveData<Integer> chooseTabEvent = new MutableLiveData<>(0);
    private List<BaseNormalFragment> fragmentList = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_color_cc;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.select_color));
        setEditString(getString(R.string.confirm));
        ((ActColorCCLightVM) this.mViewModel).selectAction = true;
        ((ActColorCCLightVM) this.mViewModel).isLocalScene = getIntent().getBooleanExtra(Constants.IS_LOCAL_SCENE, false);
        ((ActColorCCLightVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActColorCCLightVM) this.mViewModel).isWaveSensorAction = getIntent().getBooleanExtra(Constants.WAVE_SENSOR_ACTION, false);
        if (!((ActColorCCLightVM) this.mViewModel).isWaveSensorAction && !TextUtils.isEmpty(SceneHelper.instance().selectInstruct)) {
            ((ActColorCCLightVM) this.mViewModel).sceneInstruct = SceneHelper.getLightCmdData(SceneHelper.instance().selectInstruct);
            if (((ActColorCCLightVM) this.mViewModel).sceneInstruct.length() >= 16) {
                ((ActColorCCLightVM) this.mViewModel).rgbBrt = Integer.parseInt(((ActColorCCLightVM) this.mViewModel).sceneInstruct.substring(2, 4), 16);
                ((ActColorCCLightVM) this.mViewModel).red = Integer.parseInt(((ActColorCCLightVM) this.mViewModel).sceneInstruct.substring(4, 6), 16);
                ((ActColorCCLightVM) this.mViewModel).green = Integer.parseInt(((ActColorCCLightVM) this.mViewModel).sceneInstruct.substring(6, 8), 16);
                ((ActColorCCLightVM) this.mViewModel).blue = Integer.parseInt(((ActColorCCLightVM) this.mViewModel).sceneInstruct.substring(8, 10), 16);
                ((ActColorCCLightVM) this.mViewModel).wyBrt = Integer.parseInt(((ActColorCCLightVM) this.mViewModel).sceneInstruct.substring(10, 12), 16);
                ((ActColorCCLightVM) this.mViewModel).f6271c = Integer.parseInt(((ActColorCCLightVM) this.mViewModel).sceneInstruct.substring(12, 14), 16);
                ((ActColorCCLightVM) this.mViewModel).w = Integer.parseInt(((ActColorCCLightVM) this.mViewModel).sceneInstruct.substring(14, 16), 16);
                ((ActColorCCLightVM) this.mViewModel).totalBrt = ((ActColorCCLightVM) this.mViewModel).rgbBrt;
            } else if (((ActColorCCLightVM) this.mViewModel).sceneInstruct.length() >= 14 && "DA".equalsIgnoreCase(((ActColorCCLightVM) this.mViewModel).sceneInstruct.substring(0, 2))) {
                ((ActColorCCLightVM) this.mViewModel).red = Integer.parseInt(((ActColorCCLightVM) this.mViewModel).sceneInstruct.substring(2, 4), 16);
                ((ActColorCCLightVM) this.mViewModel).green = Integer.parseInt(((ActColorCCLightVM) this.mViewModel).sceneInstruct.substring(4, 6), 16);
                ((ActColorCCLightVM) this.mViewModel).blue = Integer.parseInt(((ActColorCCLightVM) this.mViewModel).sceneInstruct.substring(6, 8), 16);
                ((ActColorCCLightVM) this.mViewModel).f6271c = Integer.parseInt(((ActColorCCLightVM) this.mViewModel).sceneInstruct.substring(8, 10), 16);
                ((ActColorCCLightVM) this.mViewModel).w = Integer.parseInt(((ActColorCCLightVM) this.mViewModel).sceneInstruct.substring(10, 12), 16);
                ((ActColorCCLightVM) this.mViewModel).setTotalBrt(Integer.parseInt(((ActColorCCLightVM) this.mViewModel).sceneInstruct.substring(12, 14), 16));
            } else if (((ActColorCCLightVM) this.mViewModel).sceneInstruct.length() == 8 && "01".equalsIgnoreCase(((ActColorCCLightVM) this.mViewModel).sceneInstruct.substring(0, 2))) {
                ((ActColorCCLightVM) this.mViewModel).totalK = Integer.parseInt(((ActColorCCLightVM) this.mViewModel).sceneInstruct.substring(2, 6), 16);
                ((ActColorCCLightVM) this.mViewModel).setTotalBrt(Integer.parseInt(((ActColorCCLightVM) this.mViewModel).sceneInstruct.substring(6, 8), 16));
            }
        }
        ((ActColorCCLightVM) this.mViewModel).refreshObject.setValue(true);
        initFragmentList();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, this.fragmentList.get(0)).commit();
        ((ActSelectColorCcBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.scene.ActSelectColorCC$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActSelectColorCC.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        switch (view.getId()) {
            case R.id.tv_cct /* 2131298506 */:
                this.chooseTabEvent.setValue(2);
                break;
            case R.id.tv_general /* 2131298674 */:
                this.chooseTabEvent.setValue(0);
                ((ActColorCCLightVM) this.mViewModel).refreshObject.setValue(true);
                break;
            case R.id.tv_gray /* 2131298676 */:
                this.chooseTabEvent.setValue(1);
                ((ActColorCCLightVM) this.mViewModel).refreshObject.setValue(true);
                break;
            case R.id.tv_hsl /* 2131298693 */:
                this.chooseTabEvent.setValue(3);
                break;
            case R.id.tv_xyy /* 2131299113 */:
                this.chooseTabEvent.setValue(4);
                break;
        }
    }

    public void initFragmentList() {
        this.fragmentList.clear();
        this.fragmentList.add(new FtColorCircle());
        this.fragmentList.add(new FtColorPushrod());
        this.fragmentList.add(new FtColorCCT());
        this.fragmentList.add(new FtColorHSL());
        this.fragmentList.add(new FtColorXXY());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        this.chooseTabEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.ActSelectColorCC$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectColorCC.this.lambda$startObserve$1((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Integer num) {
        AppCompatTextView appCompatTextView = ((ActSelectColorCcBinding) this.mViewBinding).tvGeneral;
        int intValue = num.intValue();
        int i = R.drawable.shape_white_round_bg_10;
        appCompatTextView.setBackgroundResource(intValue == 0 ? R.drawable.shape_white_round_bg_10 : 0);
        ((ActSelectColorCcBinding) this.mViewBinding).tvGeneral.setTypeface(num.intValue() == 0 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
        ((ActSelectColorCcBinding) this.mViewBinding).tvGray.setBackgroundResource(num.intValue() == 1 ? R.drawable.shape_white_round_bg_10 : 0);
        ((ActSelectColorCcBinding) this.mViewBinding).tvGray.setTypeface(num.intValue() == 1 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
        ((ActSelectColorCcBinding) this.mViewBinding).tvCct.setBackgroundResource(num.intValue() == 2 ? R.drawable.shape_white_round_bg_10 : 0);
        ((ActSelectColorCcBinding) this.mViewBinding).tvCct.setTypeface(num.intValue() == 2 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
        ((ActSelectColorCcBinding) this.mViewBinding).tvHsl.setBackgroundResource(num.intValue() == 3 ? R.drawable.shape_white_round_bg_10 : 0);
        ((ActSelectColorCcBinding) this.mViewBinding).tvHsl.setTypeface(num.intValue() == 3 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
        AppCompatTextView appCompatTextView2 = ((ActSelectColorCcBinding) this.mViewBinding).tvXyy;
        if (num.intValue() != 4) {
            i = 0;
        }
        appCompatTextView2.setBackgroundResource(i);
        ((ActSelectColorCcBinding) this.mViewBinding).tvXyy.setTypeface(num.intValue() == 4 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
        ((ActSelectColorCcBinding) this.mViewBinding).line1.setVisibility((num.intValue() == 0 || num.intValue() == 1) ? 8 : 0);
        ((ActSelectColorCcBinding) this.mViewBinding).line2.setVisibility((num.intValue() == 1 || num.intValue() == 2) ? 8 : 0);
        ((ActSelectColorCcBinding) this.mViewBinding).line3.setVisibility((num.intValue() == 2 || num.intValue() == 3) ? 8 : 0);
        ((ActSelectColorCcBinding) this.mViewBinding).line4.setVisibility((num.intValue() == 3 || num.intValue() == 4) ? 8 : 0);
        changeFragment(num.intValue());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        LHomeLog.i(getClass(), "选择颜色save ");
        if (ProductId.ID_SMART_PANEL_S6B.equals(((ActColorCCLightVM) this.mViewModel).productId) && RelateInfoUtils.needShowTipDialog()) {
            RelateInfoUtils.showImageTipDialog(getString(R.string.s6b_click_tip), R.mipmap.pic_click_tip_s6b, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.scene.ActSelectColorCC$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                    ActSelectColorCC.this.lambda$edit$2(imageTipDialog);
                }
            });
        } else {
            showLoadingDialog(getString(R.string.subscribing));
            ((ActColorCCLightVM) this.mViewModel).saveData(this.currentTab);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$2(ImageTipDialog imageTipDialog) {
        ((ActColorCCLightVM) this.mViewModel).saveData(this.currentTab);
        imageTipDialog.dismissDialog();
    }

    private void changeFragment(int tabPosition) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.hide(this.fragmentList.get(this.currentTab));
        this.currentTab = tabPosition;
        if (this.fragmentList.get(tabPosition).isAdded()) {
            beginTransaction.show(this.fragmentList.get(this.currentTab));
        } else {
            beginTransaction.add(R.id.fragment_container, this.fragmentList.get(this.currentTab));
        }
        beginTransaction.commit();
    }
}