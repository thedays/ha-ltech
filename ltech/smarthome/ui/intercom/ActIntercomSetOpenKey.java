package com.ltech.smarthome.ui.intercom;

import android.content.Intent;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActIntercomSetOpenKeyBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/* loaded from: classes4.dex */
public class ActIntercomSetOpenKey extends VMActivity<ActIntercomSetOpenKeyBinding, ActIntercomSettingVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_intercom_set_open_key;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.unlock_key));
        ((ActIntercomSettingVM) this.mViewModel).key = getIntent().getStringExtra(Constants.INTERCOM_KEY);
        ((ActIntercomSettingVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        if (((ActIntercomSettingVM) this.mViewModel).key != null && !((ActIntercomSettingVM) this.mViewModel).key.isEmpty()) {
            ((ActIntercomSettingVM) this.mViewModel).isExistKey.setValue(true);
            ((ActIntercomSetOpenKeyBinding) this.mViewBinding).tvKey.setText(((ActIntercomSettingVM) this.mViewModel).key);
        } else {
            ((ActIntercomSettingVM) this.mViewModel).isExistKey.setValue(false);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActIntercomSettingVM) this.mViewModel).isExistKey.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSetOpenKey$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIntercomSetOpenKey.this.lambda$startObserve$0((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Boolean bool) {
        ((ActIntercomSetOpenKeyBinding) this.mViewBinding).layoutSetKey.setVisibility(!bool.booleanValue() ? 0 : 8);
        ((ActIntercomSetOpenKeyBinding) this.mViewBinding).layoutShowKey.setVisibility(bool.booleanValue() ? 0 : 8);
        setEditString(getString(bool.booleanValue() ? R.string.change_key : R.string.save));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        Intent intent = new Intent();
        intent.putExtra(Constants.OPEN_KEY, ((ActIntercomSettingVM) this.mViewModel).key);
        setResult(-1, intent);
        super.back();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        ((ActIntercomSetOpenKeyBinding) this.mViewBinding).tvErrorTip.setVisibility(8);
        ((ActIntercomSetOpenKeyBinding) this.mViewBinding).etInputKey.setTextColor(getResources().getColor(R.color.color_text_black));
        if (Boolean.TRUE.equals(((ActIntercomSettingVM) this.mViewModel).isExistKey.getValue())) {
            ((ActIntercomSetOpenKeyBinding) this.mViewBinding).etInputKey.setText(((ActIntercomSetOpenKeyBinding) this.mViewBinding).tvKey.getText().toString());
            ((ActIntercomSettingVM) this.mViewModel).isExistKey.setValue(false);
        } else if (((ActIntercomSetOpenKeyBinding) this.mViewBinding).etInputKey.getText().toString().length() > 3 && !isAllDigitsSame(((ActIntercomSetOpenKeyBinding) this.mViewBinding).etInputKey.getText().toString()) && !isIncreasingSequence(((ActIntercomSetOpenKeyBinding) this.mViewBinding).etInputKey.getText().toString()) && !isDecreasingSequence(((ActIntercomSetOpenKeyBinding) this.mViewBinding).etInputKey.getText().toString())) {
            ((ObservableSubscribeProxy) Injection.net().setPrivateKey(((ActIntercomSetOpenKeyBinding) this.mViewBinding).etInputKey.getText().toString(), ((ActIntercomSettingVM) this.mViewModel).placeId).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSetOpenKey$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActIntercomSetOpenKey.this.lambda$edit$1((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSetOpenKey$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActIntercomSetOpenKey.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSetOpenKey$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActIntercomSetOpenKey.this.lambda$edit$2(obj);
                }
            }, new SmartErrorComsumer());
        } else {
            ((ActIntercomSetOpenKeyBinding) this.mViewBinding).etInputKey.setTextColor(getResources().getColor(R.color.auto_num_color));
            ((ActIntercomSetOpenKeyBinding) this.mViewBinding).tvErrorTip.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$1(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$2(Object obj) throws Exception {
        SmartToast.showCenterShort(ActivityUtils.getTopActivity().getString(R.string.save_success));
        ((ActIntercomSettingVM) this.mViewModel).isExistKey.setValue(true);
        ((ActIntercomSettingVM) this.mViewModel).key = ((ActIntercomSetOpenKeyBinding) this.mViewBinding).etInputKey.getText().toString();
        ((ActIntercomSetOpenKeyBinding) this.mViewBinding).tvKey.setText(((ActIntercomSettingVM) this.mViewModel).key);
    }

    private boolean isAllDigitsSame(String input) {
        char charAt = input.charAt(0);
        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i) != charAt) {
                return false;
            }
        }
        return true;
    }

    private boolean isIncreasingSequence(String input) {
        int i = 0;
        while (i < input.length() - 1) {
            int numericValue = Character.getNumericValue(input.charAt(i));
            i++;
            if (Character.getNumericValue(input.charAt(i)) != numericValue + 1) {
                return false;
            }
        }
        return true;
    }

    private boolean isDecreasingSequence(String input) {
        int i = 0;
        while (i < input.length() - 1) {
            int numericValue = Character.getNumericValue(input.charAt(i));
            i++;
            if (Character.getNumericValue(input.charAt(i)) != numericValue - 1) {
                return false;
            }
        }
        return true;
    }
}