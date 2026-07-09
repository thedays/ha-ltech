package com.ltech.smarthome.ui.device.microwave_sensor.test;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActTestPrepareBinding;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.TextClickUtil;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActTestPrepare extends VMActivity<ActTestPrepareBinding, ActWaveSensorTestVM> {
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;
    private int testMode;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_test_prepare;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        ((ActWaveSensorTestVM) this.mViewModel).sensitivity = getIntent().getIntExtra(Constants.SELECT_POSITION, -1);
        if (((ActWaveSensorTestVM) this.mViewModel).sensitivity == -1) {
            ((ActWaveSensorTestVM) this.mViewModel).getSensitivity();
        }
        int intExtra = getIntent().getIntExtra(Constants.TEST_MODE, 1);
        this.testMode = intExtra;
        initByTestMode(intExtra);
        ((ActTestPrepareBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.ActTestPrepare$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActTestPrepare.this.lambda$initView$0((View) obj);
            }
        }));
        initTableView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        setTestMode();
    }

    private void setTestMode() {
        showLoadingDialog("");
        ((ActWaveSensorTestVM) this.mViewModel).getCmdHelper().setTestMode(this, 1, this.testMode, ((ActWaveSensorTestVM) this.mViewModel).sensitivity, new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.ActTestPrepare$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActTestPrepare.this.lambda$setTestMode$1((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setTestMode$1(Boolean bool) {
        dismissLoadingDialog();
        if (bool.booleanValue()) {
            goTestStep();
        } else {
            SmartToast.showCenterShort(getString(R.string.execute_fail));
        }
    }

    private void initByTestMode(int mode) {
        int i;
        int i2;
        if (mode == 1) {
            i = R.string.test_mode_1;
            i2 = R.string.test_prepare_tip_1;
        } else if (mode != 2) {
            i = 0;
            if (mode == 3) {
                i2 = 0;
                i = R.string.test_mode_3;
            } else if (mode != 4) {
                i2 = 0;
            } else {
                i = R.string.test_mode;
                i2 = R.string.test_prepare_tip_2_hsd;
            }
        } else {
            i = R.string.test_mode_2;
            i2 = R.string.test_prepare_tip_2;
        }
        setTitle(getString(i));
        ((ActTestPrepareBinding) this.mViewBinding).tvConfigTip.setText(i2);
        setTipText();
    }

    private void setTipText() {
        int i = this.testMode;
        int i2 = i == 1 ? R.string.test_prepare_tip_1 : i == 4 ? R.string.test_prepare_tip_2_hsd : R.string.test_prepare_tip_2;
        if (i != 4) {
            String str = getResources().getStringArray(R.array.wave_sensor_sensitivity)[((ActWaveSensorTestVM) this.mViewModel).sensitivity];
            TextClickUtil.setTextClick(this, ((ActTestPrepareBinding) this.mViewBinding).tvConfigTip, new ClickableSpan() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.ActTestPrepare.1
                @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(ActTestPrepare.this.getResources().getColor(R.color.color_text_blue));
                    ds.setUnderlineText(true);
                    ds.clearShadowLayer();
                }

                @Override // android.text.style.ClickableSpan
                public void onClick(View view) {
                    ((ActWaveSensorTestVM) ActTestPrepare.this.mViewModel).goSensitivitySetting();
                }
            }, str, getString(i2, new Object[]{str}));
        }
    }

    private void initTableView() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.install_height));
        arrayList.add(getString(R.string.install_scope));
        arrayList.add(getString(R.string.install_radius));
        int i = this.testMode;
        if (i == 1) {
            arrayList.add("2.5");
            arrayList.add("9.6×9.6");
            arrayList.add("6.8");
            arrayList.add("3.0");
            arrayList.add("10.2×10.2");
            arrayList.add("7.2");
            arrayList.add("4.0");
            arrayList.add("11.3×11.3");
            arrayList.add("8.0");
        } else if (i == 2) {
            arrayList.add("2.5");
            arrayList.add("2.8×2.8");
            arrayList.add("2.0");
            arrayList.add("3.0");
            arrayList.add("3.4×3.4");
            arrayList.add("2.4");
            arrayList.add("4.0");
            arrayList.add("4.2×4.2");
            arrayList.add("3.0");
        } else if (i == 4) {
            arrayList.add("2.5");
            arrayList.add("2.8×2.8");
            arrayList.add("2.8");
            arrayList.add("3.0");
            arrayList.add("3.4×3.4");
            arrayList.add("4.2");
            arrayList.add("4.0");
            arrayList.add("4.2×4.2");
            arrayList.add(DispatchConstants.VER_CODE);
        }
        BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(this, R.layout.item_text_36, arrayList) { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.ActTestPrepare.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_name, item);
                if (helper.getBindingAdapterPosition() < 3) {
                    helper.setBackgroundColor(R.id.tv_name, Color.parseColor("#DBE8FC"));
                    ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                } else if (helper.getBindingAdapterPosition() < 6) {
                    helper.setBackgroundColor(R.id.tv_name, Color.parseColor("#EFF5FF"));
                } else if (helper.getBindingAdapterPosition() < 9) {
                    helper.setBackgroundColor(R.id.tv_name, Color.parseColor("#F7FAFF"));
                } else {
                    helper.setBackgroundColor(R.id.tv_name, Color.parseColor("#EFF5FF"));
                }
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.bindToRecyclerView(((ActTestPrepareBinding) this.mViewBinding).rvContent);
        ((ActTestPrepareBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(this, this, 3) { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.ActTestPrepare.3
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean canScrollVertically() {
                return false;
            }
        });
        ((ActTestPrepareBinding) this.mViewBinding).rvContent.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.ActTestPrepare.4
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(0.5f), 0, ConvertUtils.dp2px(0.5f), 0);
            }
        });
    }

    private void goTestStep() {
        NavUtils.destination(ActTestStep.class).withInt(Constants.TEST_MODE, this.testMode).withInt(Constants.SENSITIVITY, ((ActWaveSensorTestVM) this.mViewModel).sensitivity).withDefaultRequestCode().navigation(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 5006) {
            finishActivity();
        } else {
            if (resultCode != 5007 || data == null) {
                return;
            }
            ((ActWaveSensorTestVM) this.mViewModel).sensitivity = data.getIntExtra(Constants.SENSITIVITY, ((ActWaveSensorTestVM) this.mViewModel).sensitivity);
            setTipText();
        }
    }
}