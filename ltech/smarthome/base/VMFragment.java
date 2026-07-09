package com.ltech.smarthome.base;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.utils.NavUtils;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/* loaded from: classes3.dex */
public abstract class VMFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseNormalFragment<V> {
    protected VM mViewModel;

    protected int getViewModelId() {
        return 92;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    public void refreshData() {
    }

    protected boolean shareVM() {
        return false;
    }

    protected boolean useVMStateEvent() {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void onViewCreated() {
        super.onViewCreated();
        initVM();
        registerUIChangeCallback();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        getLifecycle().removeObserver(this.mViewModel);
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
        Class cls = genericSuperclass instanceof ParameterizedType ? (Class) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[1] : null;
        if (shareVM()) {
            return (VM) new ViewModelProvider(getActivity()).get(cls);
        }
        return (VM) new ViewModelProvider(this).get(cls);
    }

    private void registerUIChangeCallback() {
        this.mViewModel.getUC().getShowLoadingDialogEvent().observe(getViewLifecycleOwner(), new Observer() { // from class: com.ltech.smarthome.base.VMFragment$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VMFragment.this.showLoadingDialog((String) obj);
            }
        });
        this.mViewModel.getUC().getDismissLoadingDialogEvent().observe(getViewLifecycleOwner(), new Observer() { // from class: com.ltech.smarthome.base.VMFragment$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VMFragment.this.lambda$registerUIChangeCallback$0((Integer) obj);
            }
        });
        this.mViewModel.getUC().getShowErrorialogEvent().observe(this, new Observer() { // from class: com.ltech.smarthome.base.VMFragment$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VMFragment.this.showErrorDialog((String) obj);
            }
        });
        this.mViewModel.getUC().getShowSuccessDialogEvent().observe(this, new Observer() { // from class: com.ltech.smarthome.base.VMFragment$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VMFragment.this.showSuccessDialog((String) obj);
            }
        });
        this.mViewModel.getUC().getStartActivityEvent().observe(getViewLifecycleOwner(), new Observer() { // from class: com.ltech.smarthome.base.VMFragment$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VMFragment.this.lambda$registerUIChangeCallback$1((Map) obj);
            }
        });
        this.mViewModel.getUC().getFinishEvent().observe(getViewLifecycleOwner(), new Observer() { // from class: com.ltech.smarthome.base.VMFragment$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VMFragment.this.lambda$registerUIChangeCallback$2((Map) obj);
            }
        });
        this.mViewModel.getUC().getOnBackPressedEvent().observe(getViewLifecycleOwner(), new Observer() { // from class: com.ltech.smarthome.base.VMFragment$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VMFragment.this.lambda$registerUIChangeCallback$3((Void) obj);
            }
        });
        if (useVMStateEvent()) {
            this.mViewModel.getUC().getStateEvent().observe(getViewLifecycleOwner(), new Observer() { // from class: com.ltech.smarthome.base.VMFragment$$ExternalSyntheticLambda7
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    VMFragment.this.lambda$registerUIChangeCallback$4((Integer) obj);
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
        ((BaseNormalActivity) getActivity()).finishActivity((NavUtils.Builder) map.get(BaseViewModel.ParameterField.INTENT_BUILDER));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerUIChangeCallback$3(Void r1) {
        getActivity().onBackPressed();
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