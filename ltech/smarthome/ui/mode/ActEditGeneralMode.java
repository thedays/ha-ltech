package com.ltech.smarthome.ui.mode;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActEditGeneralModeBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.ltech.smarthome.view.ColorSeekBar;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.SelectColorDialog;
import com.ltech.smarthome.view.dialog.SelectCtDialog;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.bean.GeneralModeInfo;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes4.dex */
public class ActEditGeneralMode extends VMActivity<ActEditGeneralModeBinding, BaseEditGeneralModeVM> {
    private String RgbBrt;
    private String Wy;
    private String WyBrt;
    private String color_num;
    private String details;
    private int initPosition;
    private String loop_num;
    private BaseQuickAdapter<Integer, BaseViewHolder> mAdapter;
    private String mode_type;
    private int number;
    private String speed;

    static /* synthetic */ boolean lambda$showChangeDialog$18(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_edit_general_mode;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.VMActivity
    public BaseEditGeneralModeVM getViewModel() {
        return (BaseEditGeneralModeVM) new ViewModelProvider(this).get(ActCmdEditGeneralModeVM.class);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        showChangeDialog();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showContent() {
        super.showContent();
        setEditString(getString(R.string.save));
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void onViewCreated() {
        String string;
        super.onViewCreated();
        ((BaseEditGeneralModeVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((BaseEditGeneralModeVM) this.mViewModel).zoneNum = getIntent().getIntExtra(Constants.ZONE_NUM, 1);
        ((BaseEditGeneralModeVM) this.mViewModel).modeNum = getIntent().getIntExtra(Constants.MODE_NUM, 1);
        ((BaseEditGeneralModeVM) this.mViewModel).lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, 3);
        this.initPosition = getIntent().getIntExtra(Constants.ICON_POSITION, -1);
        this.number = getIntent().getIntExtra(Constants.MODE_TIME, 0);
        ((BaseEditGeneralModeVM) this.mViewModel).modeName = getIntent().getStringExtra(Constants.MODE_NAME);
        ((BaseEditGeneralModeVM) this.mViewModel).placeId = Injection.repo().home().getSelectPlace().getValue().getPlaceId();
        AppCompatTextView appCompatTextView = ((ActEditGeneralModeBinding) this.mViewBinding).tvTimesNumber;
        if (this.number > 0) {
            string = this.number + getString(R.string.times);
        } else {
            string = getString(R.string.loop_play);
        }
        appCompatTextView.setText(string);
        ((ActEditGeneralModeBinding) this.mViewBinding).tvDeviceName.setText(((BaseEditGeneralModeVM) this.mViewModel).modeName);
        ((ActEditGeneralModeBinding) this.mViewBinding).iv.setImageResource(((BaseEditGeneralModeVM) this.mViewModel).getPicList(this).get(this.initPosition).intValue());
        this.details = getIntent().getStringExtra(Constants.MODE_DETAILS);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.edit_mode));
        initTabView();
        initSpeedView();
        initRgbBrtView();
        int i = ((BaseEditGeneralModeVM) this.mViewModel).lightType;
        if (i == 4) {
            initRgbwView();
        } else {
            if (i != 5) {
                return;
            }
            initRgbwyView();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        test();
        ((BaseEditGeneralModeVM) this.mViewModel).showNameDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode$$ExternalSyntheticLambda16
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEditGeneralMode.this.lambda$startObserve$0((Void) obj);
            }
        });
        ((BaseEditGeneralModeVM) this.mViewModel).showResetDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode$$ExternalSyntheticLambda17
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEditGeneralMode.this.lambda$startObserve$1((Void) obj);
            }
        });
        ((BaseEditGeneralModeVM) this.mViewModel).showIconDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode$$ExternalSyntheticLambda18
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEditGeneralMode.this.lambda$startObserve$2((Void) obj);
            }
        });
        ((BaseEditGeneralModeVM) this.mViewModel).showPlayDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode$$ExternalSyntheticLambda19
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEditGeneralMode.this.lambda$startObserve$3((Void) obj);
            }
        });
        ((BaseEditGeneralModeVM) this.mViewModel).showStopDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEditGeneralMode.this.lambda$startObserve$4((Void) obj);
            }
        });
        ((BaseEditGeneralModeVM) this.mViewModel).showTimeDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEditGeneralMode.this.lambda$startObserve$5((Void) obj);
            }
        });
        Injection.repo().device().getDeviceFromDb(((BaseEditGeneralModeVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEditGeneralMode.this.lambda$startObserve$6((Device) obj);
            }
        });
        ((BaseEditGeneralModeVM) this.mViewModel).paramLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEditGeneralMode.this.lambda$startObserve$7((GeneralModeInfo) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Void r1) {
        showEditNameDialog(((BaseEditGeneralModeVM) this.mViewModel).modeNum);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r1) {
        resetMode(((BaseEditGeneralModeVM) this.mViewModel).modeNum);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        changeModeIcon(((BaseEditGeneralModeVM) this.mViewModel).modeNum);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r1) {
        playBack(((BaseEditGeneralModeVM) this.mViewModel).modeNum);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r1) {
        playStop();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Void r2) {
        showPlayTimeDialog(false, ((BaseEditGeneralModeVM) this.mViewModel).modeNum);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Device device) {
        if (((BaseEditGeneralModeVM) this.mViewModel).controlObject == null) {
            ((BaseEditGeneralModeVM) this.mViewModel).controlObject = device;
            onRetry();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(GeneralModeInfo generalModeInfo) {
        refreshColorList();
        ((ActEditGeneralModeBinding) this.mViewBinding).tabMode.getTabAt(((BaseEditGeneralModeVM) this.mViewModel).getModeTypePosition(generalModeInfo.getModeType())).select();
        ((ActEditGeneralModeBinding) this.mViewBinding).sbSpeed.setProgress(generalModeInfo.getSpeed() - 1);
        ((ActEditGeneralModeBinding) this.mViewBinding).tvSpeed.setText(String.valueOf(generalModeInfo.getSpeed()));
        ((ActEditGeneralModeBinding) this.mViewBinding).sbBrt.setProgress(LightUtils.brt2ProgressHasBelowZero(generalModeInfo.getRgbBrt()));
        ((ActEditGeneralModeBinding) this.mViewBinding).tvBrt.setText(((ActEditGeneralModeBinding) this.mViewBinding).sbBrt.getProgressHasBelowOne());
        if (4 == ((BaseEditGeneralModeVM) this.mViewModel).lightType) {
            ((ActEditGeneralModeBinding) this.mViewBinding).sbW.setProgress(LightUtils.brt2ProgressHasBelowZero(generalModeInfo.getWyBrt()));
            ((ActEditGeneralModeBinding) this.mViewBinding).tvW.setText(((ActEditGeneralModeBinding) this.mViewBinding).sbW.getProgressHasBelowOne());
        } else if (5 == ((BaseEditGeneralModeVM) this.mViewModel).lightType) {
            ((ActEditGeneralModeBinding) this.mViewBinding).sbW.setProgress(LightUtils.brt2ProgressHasBelowZero(generalModeInfo.getWyBrt()));
            ((ActEditGeneralModeBinding) this.mViewBinding).tvW.setText(((ActEditGeneralModeBinding) this.mViewBinding).sbW.getProgressHasBelowOne());
            ((ActEditGeneralModeBinding) this.mViewBinding).csbWyBar.setProgress(LightUtils.c2percent(generalModeInfo.getwOrwy()));
            ((ActEditGeneralModeBinding) this.mViewBinding).tvWy.setText(String.format(Locale.US, "%d", Integer.valueOf(generalModeInfo.getwOrwy())));
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        queryModeInfo();
    }

    private void test() {
        showContent();
        GeneralModeInfo generalModeInfo = new GeneralModeInfo();
        this.color_num = this.details.substring(0, 2);
        this.loop_num = this.details.substring(2, 4);
        this.mode_type = this.details.substring(4, 6);
        this.speed = this.details.substring(6, 8);
        this.RgbBrt = this.details.substring(8, 10);
        this.Wy = this.details.substring(10, 12);
        this.WyBrt = this.details.substring(12, 14);
        String substring = this.details.substring(14, 62);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 8; i++) {
            if (((BaseEditGeneralModeVM) this.mViewModel).lightType == 2) {
                int i2 = i * 6;
                if ("000000".equals(substring.substring(i2, i2 + 6))) {
                    arrayList.add(-16777216);
                } else {
                    int parseInt = Integer.parseInt(substring.substring(i2, i2 + 2), 16);
                    arrayList.add(Integer.valueOf(Color.rgb(ColorArray.CT_COLOR_ARRAY_2[parseInt][0], ColorArray.CT_COLOR_ARRAY_2[parseInt][1], ColorArray.CT_COLOR_ARRAY_2[parseInt][2])));
                }
            } else {
                arrayList.add(Integer.valueOf(Color.parseColor(MqttTopic.MULTI_LEVEL_WILDCARD + substring.substring(i * 6, (i + 1) * 6))));
            }
        }
        this.number = Integer.parseInt(this.color_num, 16);
        generalModeInfo.setColorNum(Integer.parseInt(this.color_num, 16));
        generalModeInfo.setPlayTimes(Integer.parseInt(this.loop_num, 16));
        generalModeInfo.setColorList(arrayList);
        generalModeInfo.setModeType(Integer.parseInt(this.mode_type, 16));
        generalModeInfo.setSpeed(Integer.parseInt(this.speed, 16));
        generalModeInfo.setRgbBrt(Integer.parseInt(this.RgbBrt, 16));
        generalModeInfo.setwOrwy(Integer.parseInt(this.Wy, 16));
        generalModeInfo.setWyBrt(Integer.parseInt(this.WyBrt, 16));
        ((BaseEditGeneralModeVM) this.mViewModel).setModeInfo(generalModeInfo);
    }

    private void queryModeInfo() {
        showLoading();
        ((BaseEditGeneralModeVM) this.mViewModel).queryModeInfo(this, ((BaseEditGeneralModeVM) this.mViewModel).modeNum, new IAction() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode$$ExternalSyntheticLambda10
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEditGeneralMode.this.lambda$queryModeInfo$8((GeneralModeInfo) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryModeInfo$8(GeneralModeInfo generalModeInfo) {
        if (generalModeInfo == null) {
            showError();
        } else {
            showContent();
            ((BaseEditGeneralModeVM) this.mViewModel).setModeInfo(generalModeInfo);
        }
    }

    private void initTabView() {
        ((ActEditGeneralModeBinding) this.mViewBinding).tabMode.addTab(((ActEditGeneralModeBinding) this.mViewBinding).tabMode.newTab().setText(getString(R.string.gradual)));
        ((ActEditGeneralModeBinding) this.mViewBinding).tabMode.addTab(((ActEditGeneralModeBinding) this.mViewBinding).tabMode.newTab().setText(getString(R.string.jump)));
        ((ActEditGeneralModeBinding) this.mViewBinding).tabMode.addTab(((ActEditGeneralModeBinding) this.mViewBinding).tabMode.newTab().setText(getString(R.string.strobe)));
        ((ActEditGeneralModeBinding) this.mViewBinding).tabMode.addTab(((ActEditGeneralModeBinding) this.mViewBinding).tabMode.newTab().setText(getString(R.string.breath)));
        if (((BaseEditGeneralModeVM) this.mViewModel).lightType > 2) {
            ((ActEditGeneralModeBinding) this.mViewBinding).tabMode.addTab(((ActEditGeneralModeBinding) this.mViewBinding).tabMode.newTab().setText(getString(R.string.static_color)));
        }
        ((ActEditGeneralModeBinding) this.mViewBinding).tabMode.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode.1
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                ((BaseEditGeneralModeVM) ActEditGeneralMode.this.mViewModel).setModeType(tab.getPosition());
                ((ActEditGeneralModeBinding) ActEditGeneralMode.this.mViewBinding).tabMode.getTabAt(tab.getPosition()).select();
                StringBuilder sb = new StringBuilder(ActEditGeneralMode.this.details);
                sb.replace(4, 6, "0" + ((BaseEditGeneralModeVM) ActEditGeneralMode.this.mViewModel).getModeInfo().getModeType());
                ActEditGeneralMode.this.details = sb.toString();
                ViewHelpUtil.createTabCustomView(ActEditGeneralMode.this, tab, tab.getText().toString(), true);
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
                ViewHelpUtil.createTabCustomView(ActEditGeneralMode.this, tab, tab.getText().toString(), false);
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
                ViewHelpUtil.createTabCustomView(ActEditGeneralMode.this, tab, tab.getText().toString(), true);
            }
        });
        ((ActEditGeneralModeBinding) this.mViewBinding).tabMode.getTabAt(0).select();
    }

    private void initSpeedView() {
        ((ActEditGeneralModeBinding) this.mViewBinding).sbSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode.2
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    int i = progress + 1;
                    ((BaseEditGeneralModeVM) ActEditGeneralMode.this.mViewModel).setSpeed(i);
                    ((ActEditGeneralModeBinding) ActEditGeneralMode.this.mViewBinding).tvSpeed.setText(String.valueOf(i));
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress() + 1;
                ((BaseEditGeneralModeVM) ActEditGeneralMode.this.mViewModel).setSpeed(progress);
                ((ActEditGeneralModeBinding) ActEditGeneralMode.this.mViewBinding).tvSpeed.setText(String.valueOf(progress));
                StringBuilder sb = new StringBuilder(ActEditGeneralMode.this.details);
                sb.replace(6, 8, "0" + progress);
                ActEditGeneralMode.this.details = sb.toString();
            }
        });
    }

    private void initRgbBrtView() {
        ((ActEditGeneralModeBinding) this.mViewBinding).sbBrt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode.3
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((BaseEditGeneralModeVM) ActEditGeneralMode.this.mViewModel).setRgbBrt(LightUtils.progress2BrtHasBelowOne(progress));
                    ((ActEditGeneralModeBinding) ActEditGeneralMode.this.mViewBinding).tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((BaseEditGeneralModeVM) ActEditGeneralMode.this.mViewModel).setRgbBrt(LightUtils.progress2BrtHasBelowOne(seekBar.getProgress()));
                ((ActEditGeneralModeBinding) ActEditGeneralMode.this.mViewBinding).tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                String addZeroForNum = StringUtils.addZeroForNum(Integer.toHexString(LightUtils.progress2BrtHasBelowOne(seekBar.getProgress())), 2);
                StringBuilder sb = new StringBuilder(ActEditGeneralMode.this.details);
                sb.replace(8, 10, addZeroForNum);
                ActEditGeneralMode.this.details = sb.toString();
            }
        });
    }

    private void initRgbwView() {
        ((ActEditGeneralModeBinding) this.mViewBinding).groupW.setVisibility(0);
        ((ActEditGeneralModeBinding) this.mViewBinding).tvWTip.setText(getString(R.string.w_value));
        ((ActEditGeneralModeBinding) this.mViewBinding).sbW.setIncludeZero(true);
        ((ActEditGeneralModeBinding) this.mViewBinding).sbW.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode.4
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((BaseEditGeneralModeVM) ActEditGeneralMode.this.mViewModel).setW(LightUtils.progress2BrtHasBelowOne(progress));
                    ((ActEditGeneralModeBinding) ActEditGeneralMode.this.mViewBinding).tvW.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((BaseEditGeneralModeVM) ActEditGeneralMode.this.mViewModel).setW(LightUtils.progress2BrtHasBelowOne(seekBar.getProgress()));
                ((ActEditGeneralModeBinding) ActEditGeneralMode.this.mViewBinding).tvW.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                String addZeroForNum = StringUtils.addZeroForNum(Integer.toHexString(LightUtils.progress2BrtHasBelowOne(seekBar.getProgress())), 2);
                StringBuilder sb = new StringBuilder(ActEditGeneralMode.this.details);
                sb.replace(12, 14, addZeroForNum);
                ActEditGeneralMode.this.details = sb.toString();
            }
        });
    }

    private void initRgbwyView() {
        ((ActEditGeneralModeBinding) this.mViewBinding).groupW.setVisibility(0);
        ((ActEditGeneralModeBinding) this.mViewBinding).tvWTip.setText(getString(R.string.wy_brt));
        ((ActEditGeneralModeBinding) this.mViewBinding).sbW.setIncludeZero(true);
        ((ActEditGeneralModeBinding) this.mViewBinding).sbW.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode.5
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((BaseEditGeneralModeVM) ActEditGeneralMode.this.mViewModel).setWyBrt(LightUtils.progress2BrtHasBelowOne(progress));
                    ((ActEditGeneralModeBinding) ActEditGeneralMode.this.mViewBinding).tvW.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((BaseEditGeneralModeVM) ActEditGeneralMode.this.mViewModel).setWyBrt(LightUtils.progress2BrtHasBelowOne(seekBar.getProgress()));
                ((ActEditGeneralModeBinding) ActEditGeneralMode.this.mViewBinding).tvW.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                String addZeroForNum = StringUtils.addZeroForNum(Integer.toHexString(LightUtils.progress2BrtHasBelowOne(seekBar.getProgress())), 2);
                StringBuilder sb = new StringBuilder(ActEditGeneralMode.this.details);
                sb.replace(12, 14, addZeroForNum);
                ActEditGeneralMode.this.details = sb.toString();
            }
        });
        ((ActEditGeneralModeBinding) this.mViewBinding).groupWy.setVisibility(0);
        ((ActEditGeneralModeBinding) this.mViewBinding).tvWyTip.setText(getString(R.string.wy_value));
        ((ActEditGeneralModeBinding) this.mViewBinding).csbWyBar.setOnColorChangedListener(new ColorSeekBar.OnColorChangedListener() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode.6
            @Override // com.ltech.smarthome.view.ColorSeekBar.OnColorChangedListener
            public void onColorChangedStart() {
            }

            @Override // com.ltech.smarthome.view.ColorSeekBar.OnColorChangedListener
            public void onColorChanged(float xProgress, int color, boolean isFromUser) {
                if (isFromUser) {
                    ((BaseEditGeneralModeVM) ActEditGeneralMode.this.mViewModel).setWy(LightUtils.percent2C(xProgress));
                    ((ActEditGeneralModeBinding) ActEditGeneralMode.this.mViewBinding).tvWy.setText(String.format(Locale.US, "%d", Integer.valueOf(LightUtils.percent2C(xProgress))));
                }
            }

            @Override // com.ltech.smarthome.view.ColorSeekBar.OnColorChangedListener
            public void onColorChangedFinish(float xProgress, int color, boolean isFromUser) {
                ((BaseEditGeneralModeVM) ActEditGeneralMode.this.mViewModel).setWy(LightUtils.percent2C(xProgress));
                ((ActEditGeneralModeBinding) ActEditGeneralMode.this.mViewBinding).tvWy.setText(String.format(Locale.US, "%d", Integer.valueOf(LightUtils.percent2C(xProgress))));
                String addZeroForNum = StringUtils.addZeroForNum(Integer.toHexString(LightUtils.percent2C(xProgress)), 2);
                StringBuilder sb = new StringBuilder(ActEditGeneralMode.this.details);
                sb.replace(10, 12, addZeroForNum);
                ActEditGeneralMode.this.details = sb.toString();
            }
        });
    }

    private void refreshColorList() {
        BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter = this.mAdapter;
        if (baseQuickAdapter == null) {
            BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<Integer, BaseViewHolder>(R.layout.item_color, ((BaseEditGeneralModeVM) this.mViewModel).getModeInfo().getColorList()) { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode.7
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, Integer item) {
                    LHomeLog.i(getClass(), "mViewModel.getModeInfo().getModeType=" + ((BaseEditGeneralModeVM) ActEditGeneralMode.this.mViewModel).getModeInfo().getModeType());
                    if (5 == ((BaseEditGeneralModeVM) ActEditGeneralMode.this.mViewModel).getModeInfo().getModeType() && helper.getAdapterPosition() > 0) {
                        helper.setImageDrawable(R.id.civ_color, new ColorDrawable(-7829368));
                    } else {
                        helper.setImageDrawable(R.id.civ_color, new ColorDrawable(item.intValue()));
                    }
                }
            };
            this.mAdapter = baseQuickAdapter2;
            baseQuickAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode$$ExternalSyntheticLambda5
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    ActEditGeneralMode.this.lambda$refreshColorList$9(baseQuickAdapter3, view, i);
                }
            });
            this.mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode$$ExternalSyntheticLambda6
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemLongClickListener
                public final boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    boolean lambda$refreshColorList$10;
                    lambda$refreshColorList$10 = ActEditGeneralMode.this.lambda$refreshColorList$10(baseQuickAdapter3, view, i);
                    return lambda$refreshColorList$10;
                }
            });
            this.mAdapter.bindToRecyclerView(((ActEditGeneralModeBinding) this.mViewBinding).rvColor);
            ((ActEditGeneralModeBinding) this.mViewBinding).rvColor.setHasFixedSize(true);
            ((ActEditGeneralModeBinding) this.mViewBinding).rvColor.setLayoutManager(new GridLayoutManager(this, 8));
            ((ActEditGeneralModeBinding) this.mViewBinding).rvColor.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode.8
                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(ConvertUtils.dp2px(5.0f), 0, ConvertUtils.dp2px(5.0f), 0);
                }
            });
            return;
        }
        baseQuickAdapter.setNewData(((BaseEditGeneralModeVM) this.mViewModel).getModeInfo().getColorList());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshColorList$9(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (5 != ((BaseEditGeneralModeVM) this.mViewModel).getModeInfo().getModeType() || i == 0) {
            if (((BaseEditGeneralModeVM) this.mViewModel).lightType == 2) {
                showSelectCtDialog(i);
            } else {
                showSelectColorDialog(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$refreshColorList$10(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        setColorView(i, -16777216);
        StringBuilder sb = new StringBuilder(this.details);
        int i2 = i * 6;
        sb.replace(i2 + 14, i2 + 20, "000000");
        this.details = sb.toString();
        return false;
    }

    private void showSelectColorDialog(final int position) {
        SelectColorDialog.rgb().setOnDialogCallback(new SelectColorDialog.OnDialogCallback() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode.9
            @Override // com.ltech.smarthome.view.dialog.SelectColorDialog.OnDialogCallback
            public void onColorChanged(int red, int green, int blue, int brt, int w, boolean finish) {
            }

            @Override // com.ltech.smarthome.view.dialog.SelectColorDialog.OnDialogCallback
            public void onSaved(int red, int green, int blue, int brt, int w) {
                ActEditGeneralMode.this.setColorView(position, Color.rgb(red, green, blue));
                String str = StringUtils.addZeroForNum(Integer.toHexString(red), 2) + StringUtils.addZeroForNum(Integer.toHexString(green), 2) + StringUtils.addZeroForNum(Integer.toHexString(blue), 2);
                StringBuilder sb = new StringBuilder(ActEditGeneralMode.this.details);
                int i = position;
                sb.replace((i * 6) + 14, (i * 6) + 20, str);
                ActEditGeneralMode.this.details = sb.toString();
            }
        }).showDialog(this);
    }

    private void showSelectCtDialog(final int position) {
        int i = position * 6;
        SelectCtDialog.asDefault().setWy(Integer.parseInt(this.details.substring(i + 16, i + 18), 16)).setTitle(getString(R.string.choose_color)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.confirm)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEditGeneralMode.this.lambda$showSelectCtDialog$11(position, (Integer) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectCtDialog$11(int i, Integer num) {
        setColorView(i, Color.rgb(ColorArray.CT_COLOR_ARRAY_2[255 - num.intValue()][0], ColorArray.CT_COLOR_ARRAY_2[255 - num.intValue()][1], ColorArray.CT_COLOR_ARRAY_2[255 - num.intValue()][2]));
        String addZeroForNum = StringUtils.addZeroForNum(Integer.toHexString(255 - num.intValue()), 2);
        String addZeroForNum2 = StringUtils.addZeroForNum(Integer.toHexString(num.intValue()), 2);
        StringBuilder sb = new StringBuilder(this.details);
        int i2 = i * 6;
        sb.replace(i2 + 14, i2 + 20, addZeroForNum + addZeroForNum2 + "01");
        this.details = sb.toString();
    }

    public void colorNumber() {
        String addZeroForNum = StringUtils.addZeroForNum(Integer.toHexString(checkColorNum(new StringBuffer(this.details).reverse().toString())), 2);
        StringBuilder sb = new StringBuilder(this.details);
        sb.replace(0, 2, addZeroForNum);
        this.details = sb.toString();
    }

    public int checkColorNum(String s) {
        int i = 8;
        while (s.startsWith("000000")) {
            s = s.substring(6);
            i--;
        }
        return i;
    }

    private void showPlayTimeDialog(boolean playList, int position) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i <= 255; i++) {
            if (i == 0) {
                arrayList.add(getString(R.string.loop_play));
            } else {
                arrayList.add(i + getString(R.string.times));
            }
        }
        TimeSelectDialog.asDefault().setTitle(getString(R.string.set_play_times)).setMinList(arrayList).setSelectMinPosition(playList ? ((BaseEditGeneralModeVM) this.mViewModel).listPlayTime.getValue().intValue() : this.number).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i2, int i3) {
                ActEditGeneralMode.this.lambda$showPlayTimeDialog$12(i2, i3);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPlayTimeDialog$12(int i, int i2) {
        String string;
        AppCompatTextView appCompatTextView = ((ActEditGeneralModeBinding) this.mViewBinding).tvTimesNumber;
        if (i > 0) {
            string = i + getString(R.string.times);
        } else {
            string = getString(R.string.loop_play);
        }
        appCompatTextView.setText(string);
        this.number = i;
        String addZeroForNum = StringUtils.addZeroForNum(Integer.toHexString(i), 2);
        StringBuilder sb = new StringBuilder(this.details);
        sb.replace(2, 4, addZeroForNum);
        this.details = sb.toString();
        Intent intent = new Intent();
        intent.putExtra(Constants.MODE_NUM, i);
        setResult(3007, intent);
    }

    private void changeModeIcon(int position) {
        ((BaseEditGeneralModeVM) this.mViewModel).clickPosition = position;
        NavUtils.destination(ActSelectModeCover.class).withInt(Constants.ICON_POSITION, this.initPosition).withLong(Constants.CONTROL_ID, ((BaseEditGeneralModeVM) this.mViewModel).controlId).withInt(Constants.ZONE_NUM, ((BaseEditGeneralModeVM) this.mViewModel).zoneNum).withInt(Constants.MODE_NUM, position).withDefaultRequestCode().navigation(this);
    }

    private void resetMode(final int position) {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.reset_mode_tip)).setOkButton(getString(R.string.reset), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode$$ExternalSyntheticLambda14
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$resetMode$14;
                lambda$resetMode$14 = ActEditGeneralMode.this.lambda$resetMode$14(position, baseDialog, view);
                return lambda$resetMode$14;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$resetMode$14(int i, BaseDialog baseDialog, View view) {
        ((ObservableSubscribeProxy) Injection.net().deleteMode(((BaseEditGeneralModeVM) this.mViewModel).placeId, ((BaseEditGeneralModeVM) this.mViewModel).lightType, 1, i).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActEditGeneralMode.this.lambda$resetMode$13(obj);
            }
        }, new SmartErrorComsumer());
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resetMode$13(Object obj) throws Exception {
        SmartToast.showShort(getString(R.string.app_str_reset_successful));
        setResult(3017);
        back();
    }

    private void showEditNameDialog(int position) {
        EditDialog.asDefault().setContent(((ActEditGeneralModeBinding) this.mViewBinding).tvDeviceName.getText().toString()).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode$$ExternalSyntheticLambda15
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEditGeneralMode.this.lambda$showEditNameDialog$15((String) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$15(String str) {
        ((BaseEditGeneralModeVM) this.mViewModel).modeName = str;
        ((ActEditGeneralModeBinding) this.mViewBinding).tvDeviceName.setText(str);
        Intent intent = new Intent();
        intent.putExtra(Constants.MODE_NAME, str);
        setResult(3006, intent);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        super.back();
        if (((BaseEditGeneralModeVM) this.mViewModel).controlObject != null) {
            ((BaseEditGeneralModeVM) this.mViewModel).pausePreview(this, ((BaseEditGeneralModeVM) this.mViewModel).modeNum);
            HelpUtils.threadSleep(200);
        }
        super.back();
    }

    private void showChangeDialog() {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.save_change_tip)).setOkButton(getString(R.string.save), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode$$ExternalSyntheticLambda11
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showChangeDialog$17;
                lambda$showChangeDialog$17 = ActEditGeneralMode.this.lambda$showChangeDialog$17(baseDialog, view);
                return lambda$showChangeDialog$17;
            }
        }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode$$ExternalSyntheticLambda12
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return ActEditGeneralMode.lambda$showChangeDialog$18(baseDialog, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showChangeDialog$17(BaseDialog baseDialog, View view) {
        colorNumber();
        ((ObservableSubscribeProxy) Injection.net().addMode(((BaseEditGeneralModeVM) this.mViewModel).modeName, ((BaseEditGeneralModeVM) this.mViewModel).placeId, ((BaseEditGeneralModeVM) this.mViewModel).lightType, 1, ((BaseEditGeneralModeVM) this.mViewModel).modeNum, this.initPosition, this.details, "", this.number).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActEditGeneralMode.this.lambda$showChangeDialog$16(obj);
            }
        }, new SmartErrorComsumer());
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showChangeDialog$16(Object obj) throws Exception {
        back();
    }

    private void playBack(int position) {
        colorNumber();
        test();
        NavUtils.destination(ActAddBleMode.class).withLong(Constants.PLACE_ID, ((BaseEditGeneralModeVM) this.mViewModel).placeId).withInt(Constants.LIGHT_TYPE, ((BaseEditGeneralModeVM) this.mViewModel).lightType).withDefaultRequestCode().navigation(this);
    }

    private void playStop() {
        if (((BaseEditGeneralModeVM) this.mViewModel).controlObject != null) {
            ((BaseEditGeneralModeVM) this.mViewModel).pausePreview(this, ((BaseEditGeneralModeVM) this.mViewModel).modeNum);
            ((ActEditGeneralModeBinding) this.mViewBinding).playBack.setVisibility(0);
            ((ActEditGeneralModeBinding) this.mViewBinding).vPreview2.setVisibility(0);
            ((ActEditGeneralModeBinding) this.mViewBinding).vPreviewOff.setVisibility(8);
            ((ActEditGeneralModeBinding) this.mViewBinding).playBack1.setVisibility(8);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3008 == resultCode && data != null) {
            long longExtra = data.getLongExtra("device_id", -1L);
            ((BaseEditGeneralModeVM) this.mViewModel).controlObject = null;
            Injection.repo().device().getDeviceFromDb(longExtra).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.mode.ActEditGeneralMode$$ExternalSyntheticLambda0
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActEditGeneralMode.this.lambda$onActivityResult$19((Device) obj);
                }
            });
        }
        if (3002 != resultCode || data == null) {
            return;
        }
        this.initPosition = data.getIntExtra(Constants.ICON_POSITION, 0);
        ((ActEditGeneralModeBinding) this.mViewBinding).iv.setImageResource(((BaseEditGeneralModeVM) this.mViewModel).getPicList(this).get(this.initPosition).intValue());
        Intent intent = new Intent();
        intent.putExtra(Constants.ICON_POSITION, this.initPosition);
        setResult(3002, intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onActivityResult$19(Device device) {
        if (((BaseEditGeneralModeVM) this.mViewModel).controlObject == null) {
            ((BaseEditGeneralModeVM) this.mViewModel).controlObject = device;
            showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.send_to_device));
            ((BaseEditGeneralModeVM) this.mViewModel).previewModeInfo(this, ((BaseEditGeneralModeVM) this.mViewModel).modeNum, ((BaseEditGeneralModeVM) this.mViewModel).getModeInfo());
            ((ActEditGeneralModeBinding) this.mViewBinding).playBack.setVisibility(8);
            ((ActEditGeneralModeBinding) this.mViewBinding).vPreview2.setVisibility(8);
            ((ActEditGeneralModeBinding) this.mViewBinding).vPreviewOff.setVisibility(0);
            ((ActEditGeneralModeBinding) this.mViewBinding).playBack1.setVisibility(0);
            dismissLoadingDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setColorView(int position, int color) {
        ((BaseEditGeneralModeVM) this.mViewModel).setColor(position, color);
        refreshColorList();
    }
}