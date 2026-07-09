package com.ltech.smarthome.base;

import android.os.Handler;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.blankj.utilcode.util.ThreadUtils;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.utils.NavUtils;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/* loaded from: classes3.dex */
public abstract class VMActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseNormalActivity<V> {
    protected VM mViewModel;

    protected int getViewModelId() {
        return 92;
    }

    public void refreshData() {
    }

    protected boolean useVMStateEvent() {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onViewCreated() {
        super.onViewCreated();
        initVM();
        registerUIChangeCallback();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(this.mViewModel);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public Handler getMainHandler() {
        return ThreadUtils.getMainHandler();
    }

    private void initVM() {
        if (this.mViewModel == null) {
            this.mViewModel = getViewModel();
        }
        if (this.mViewModel != null) {
            this.mViewBinding.setVariable(getViewModelId(), this.mViewModel);
            getLifecycle().addObserver(this.mViewModel);
        }
    }

    protected VM getViewModel() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        return (VM) new ViewModelProvider(this).get(genericSuperclass instanceof ParameterizedType ? (Class) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[1] : null);
    }

    private void registerUIChangeCallback() {
        this.mViewModel.getUC().getShowLoadingDialogEvent().observe(this, new Observer() { // from class: com.ltech.smarthome.base.VMActivity$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VMActivity.this.showLoadingDialog((String) obj);
            }
        });
        this.mViewModel.getUC().getDismissLoadingDialogEvent().observe(this, new Observer() { // from class: com.ltech.smarthome.base.VMActivity$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VMActivity.this.lambda$registerUIChangeCallback$0((Integer) obj);
            }
        });
        this.mViewModel.getUC().getShowErrorialogEvent().observe(this, new Observer() { // from class: com.ltech.smarthome.base.VMActivity$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VMActivity.this.showErrorDialog((String) obj);
            }
        });
        this.mViewModel.getUC().getShowSuccessDialogEvent().observe(this, new Observer() { // from class: com.ltech.smarthome.base.VMActivity$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VMActivity.this.showSuccessDialog((String) obj);
            }
        });
        this.mViewModel.getUC().getStartActivityEvent().observe(this, new Observer() { // from class: com.ltech.smarthome.base.VMActivity$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VMActivity.this.lambda$registerUIChangeCallback$1((Map) obj);
            }
        });
        this.mViewModel.getUC().getFinishEvent().observe(this, new Observer() { // from class: com.ltech.smarthome.base.VMActivity$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VMActivity.this.lambda$registerUIChangeCallback$2((Map) obj);
            }
        });
        this.mViewModel.getUC().getOnBackPressedEvent().observe(this, new Observer() { // from class: com.ltech.smarthome.base.VMActivity$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VMActivity.this.lambda$registerUIChangeCallback$3((Void) obj);
            }
        });
        if (useVMStateEvent()) {
            this.mViewModel.getUC().getStateEvent().observe(this, new Observer() { // from class: com.ltech.smarthome.base.VMActivity$$ExternalSyntheticLambda7
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    VMActivity.this.lambda$registerUIChangeCallback$4((Integer) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerUIChangeCallback$0(Integer num) {
        dismissLoadingDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerUIChangeCallback$1(Map map) {
        NavUtils.Builder builder = (NavUtils.Builder) map.get(BaseViewModel.ParameterField.INTENT_BUILDER);
        if (builder != null) {
            builder.navigation(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerUIChangeCallback$2(Map map) {
        finishActivity((NavUtils.Builder) map.get(BaseViewModel.ParameterField.INTENT_BUILDER));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerUIChangeCallback$3(Void r1) {
        onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerUIChangeCallback$4(Integer num) {
        int intValue = num.intValue();
        if (intValue == 0) {
            showLoading();
            return;
        }
        if (intValue == 2) {
            showEmpty();
        } else if (intValue == 3) {
            showError();
        } else {
            showContent();
        }
    }
}