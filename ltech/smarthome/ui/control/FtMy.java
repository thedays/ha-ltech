package com.ltech.smarthome.ui.control;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.databinding.FtMyBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.User;
import com.ltech.smarthome.ui.config.ActMeshScanProxy;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import java.util.List;

/* loaded from: classes4.dex */
public class FtMy extends VMFragment<FtMyBinding, FtMyVM> {
    private int roleType;

    static /* synthetic */ boolean lambda$showNoPermissionDialog$2(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_my;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        Injection.repo().home().getSelectPlace().observe(this, new Observer() { // from class: com.ltech.smarthome.ui.control.FtMy$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtMy.this.lambda$startObserve$0((Place) obj);
            }
        });
        ((FtMyVM) this.mViewModel).showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.control.FtMy$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtMy.this.lambda$startObserve$1((Void) obj);
            }
        });
        ((FtMyVM) this.mViewModel).needBleEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.control.FtMy.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                if (FtMy.this.bleIsOk()) {
                    NavUtils.destination(ActMeshScanProxy.class).withLong(Constants.CONTROL_ID, 9999L).withLong(Constants.PLACE_ID, ((FtMyVM) FtMy.this.mViewModel).getCurPlace().getPlaceId()).navigation(FtMy.this.getActivity());
                }
            }
        });
        Injection.repo().user().getUserInfo(this).data().observe(this, new Observer<Resource<List<User>>>() { // from class: com.ltech.smarthome.ui.control.FtMy.2
            @Override // androidx.lifecycle.Observer
            public void onChanged(Resource<List<User>> listResource) {
                if (listResource.getData().isEmpty()) {
                    return;
                }
                ((FtMyVM) FtMy.this.mViewModel).user.setValue(listResource.getData().get(0));
                ((FtMyBinding) FtMy.this.mViewBinding).tvSub.setText(String.format(FtMy.this.getString(R.string.app_str_home_and_device), Integer.valueOf(listResource.getData().get(0).getPlacetotal()), Integer.valueOf(listResource.getData().get(0).getDevicetotal())));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Place place) {
        if (place == null || this.roleType == place.getRoleType()) {
            return;
        }
        this.roleType = place.getRoleType();
        ((FtMyVM) this.mViewModel).mObservableList.clear();
        ((FtMyVM) this.mViewModel).initMyList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r1) {
        showNoPermissionDialog();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        User userByDb = Injection.repo().user().getUserByDb();
        if (userByDb != null) {
            ((FtMyVM) this.mViewModel).user.setValue(userByDb);
            ((FtMyBinding) this.mViewBinding).tvSub.setText(String.format(getString(R.string.app_str_home_and_device), Integer.valueOf(userByDb.getPlacetotal()), Integer.valueOf(userByDb.getDevicetotal())));
        }
        Injection.repo().user().getUserInfo(this).refresh();
    }

    private void showNoPermissionDialog() {
        MessageDialog.show((AppCompatActivity) getActivity(), getString(R.string.app_member_no_permission), getString(R.string.app_member_no_permission_tip)).setOkButton(getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.control.FtMy$$ExternalSyntheticLambda2
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return FtMy.lambda$showNoPermissionDialog$2(baseDialog, view);
            }
        });
    }
}