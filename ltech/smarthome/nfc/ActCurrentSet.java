package com.ltech.smarthome.nfc;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.ltech.nfc.source.SourceHelper;
import com.ltech.nfc.source.SourceModel;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActCurrentSetBinding;
import com.ltech.smarthome.utils.Constants;

/* loaded from: classes4.dex */
public class ActCurrentSet extends BaseNormalActivity<ActCurrentSetBinding> {
    private static final int COUNT_INTERVAL = 100;
    private static final int COUNT_MAX = 200000;
    public int current;
    private View.OnTouchListener listener = new View.OnTouchListener() { // from class: com.ltech.smarthome.nfc.ActCurrentSet$$ExternalSyntheticLambda1
        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            boolean lambda$new$1;
            lambda$new$1 = ActCurrentSet.this.lambda$new$1(view, motionEvent);
            return lambda$new$1;
        }
    };
    private MyCount myCount;
    private SourceModel sourceModel;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected boolean isNeedAddMarginTopEqualStatusBar() {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_current_set;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        showAsDialog(0.0f);
        this.sourceModel = SourceHelper.sourceModel;
        ((ActCurrentSetBinding) this.mViewBinding).tvTitle.setText(this.sourceModel.sourceName);
        ((ActCurrentSetBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.nfc.ActCurrentSet$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActCurrentSet.this.lambda$initView$0((View) obj);
            }
        }));
        ((ActCurrentSetBinding) this.mViewBinding).tvSetTitle.setText(getString(R.string.current_range, new Object[]{Integer.valueOf(this.sourceModel.IOutMin), Integer.valueOf(this.sourceModel.IOutMax)}));
        ((ActCurrentSetBinding) this.mViewBinding).ivMinus.setOnTouchListener(this.listener);
        ((ActCurrentSetBinding) this.mViewBinding).ivPlus.setOnTouchListener(this.listener);
        ((ActCurrentSetBinding) this.mViewBinding).tvCurrent.addTextChangedListener(new TextWatcher() { // from class: com.ltech.smarthome.nfc.ActCurrentSet.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    return;
                }
                if (s.length() <= 5) {
                    ActCurrentSet.this.current = Integer.parseInt(s.toString());
                } else {
                    ActCurrentSet.this.current = SourceHelper.sourceModel.IOutMax;
                }
                ActCurrentSet actCurrentSet = ActCurrentSet.this;
                actCurrentSet.changeCurrent(actCurrentSet.current);
            }
        });
        int intExtra = getIntent().getIntExtra(Constants.CURRENT, this.sourceModel.IOutMin);
        this.current = intExtra;
        changeCurrent(intExtra);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        int id = view.getId();
        if (id == R.id.tv_cancel) {
            finish();
        } else {
            if (id != R.id.tv_confirm) {
                return;
            }
            setResult(5011, new Intent().putExtra(Constants.CURRENT, new int[]{this.current}));
            finishActivity();
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent ev) {
        InputMethodManager inputMethodManager;
        if (ev.getAction() == 0) {
            View currentFocus = getCurrentFocus();
            if (isShouldHideInput(currentFocus, ev) && (inputMethodManager = (InputMethodManager) getSystemService("input_method")) != null) {
                inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                if (currentFocus == ((ActCurrentSetBinding) this.mViewBinding).tvCurrent) {
                    ((ActCurrentSetBinding) this.mViewBinding).tvCurrent.clearFocus();
                    int outputCurrent = SourceHelper.sourceModel.getOutputCurrent(this.current);
                    this.current = outputCurrent;
                    changeCurrent(outputCurrent);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$1(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0 || action == 2) {
            if (this.myCount == null) {
                MyCount myCount = new MyCount(200000L, 100L, view == ((ActCurrentSetBinding) this.mViewBinding).ivPlus);
                this.myCount = myCount;
                myCount.start();
                return false;
            }
        } else {
            MyCount myCount2 = this.myCount;
            if (myCount2 != null) {
                myCount2.onFinish();
            }
        }
        return false;
    }

    private class MyCount extends CountDownTimer {
        private final boolean plus;

        public MyCount(long millisInFuture, long countDownInterval, boolean plus) {
            super(millisInFuture, countDownInterval);
            this.plus = plus;
        }

        @Override // android.os.CountDownTimer
        public void onTick(long millisUntilFinished) {
            if (this.plus) {
                ActCurrentSet.this.current = SourceHelper.sourceModel.getOutputCurrent(ActCurrentSet.this.current + 1);
            } else {
                ActCurrentSet.this.current = SourceHelper.sourceModel.getOutputCurrent(ActCurrentSet.this.current - 1);
            }
            ActCurrentSet actCurrentSet = ActCurrentSet.this;
            actCurrentSet.changeCurrent(actCurrentSet.current);
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            cancel();
            ActCurrentSet.this.myCount = null;
        }
    }

    public void changeCurrent(int current) {
        ((ActCurrentSetBinding) this.mViewBinding).setCurrent(String.valueOf(current));
        ((ActCurrentSetBinding) this.mViewBinding).ivMinus.setEnabled(current > this.sourceModel.IOutMin);
        ((ActCurrentSetBinding) this.mViewBinding).tvOutputParam.setText(this.sourceModel.getOutputParam(current));
        ((ActCurrentSetBinding) this.mViewBinding).ivPlus.setEnabled(current < this.sourceModel.IOutMax);
    }
}