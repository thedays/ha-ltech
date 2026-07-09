package com.ltech.smarthome.ui.device.super_panel;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.super_panel.SetSuperPanelResponse;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSelectDeviceGroupForSuperPanel extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> implements ISelectAction {
    private boolean isGq;
    private TextView okTv;
    private List<Long> selectDeviceIdList = new ArrayList();
    private List<Long> selectGroupIdList = new ArrayList();
    private Device superPanel;

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDaliSubDeviceGroup(Role role) {
        return true;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$edit$1(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_device));
        setEditString(getString(R.string.app_str_select_all));
        this.superPanel = Injection.repo().device().getDeviceByDeviceId(getIntent().getLongExtra("device_id", -1L));
        this.isGq = getIntent().getBooleanExtra(Constants.IS_GQ, false);
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.setVisibility(0);
        ((ActSelect3Binding) this.mViewBinding).layoutSort.setVisibility(8);
        ((ActSelect3Binding) this.mViewBinding).layoutSortAndType.setVisibility(0);
        TextView textView = new TextView(this);
        this.okTv = textView;
        textView.setTextColor(getResources().getColor(R.color.color_text_red));
        this.okTv.setTextSize(14.0f);
        this.okTv.setBackgroundColor(getResources().getColor(R.color.white));
        this.okTv.setGravity(17);
        this.okTv.setText(String.format(getResources().getString(R.string.app_str_select_finish), 0));
        ((ActSelect3Binding) this.mViewBinding).footerView.addView(this.okTv, new RelativeLayout.LayoutParams(-1, ConvertUtils.dp2px(60.0f)));
        this.okTv.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSelectDeviceGroupForSuperPanel.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ActSelectDeviceGroupForSuperPanel.this.selectDeviceIdList.clear();
                ActSelectDeviceGroupForSuperPanel.this.selectGroupIdList.clear();
                for (int i = 0; i < ActSelectDeviceGroupForSuperPanel.this.selectRoleIds.size(); i++) {
                    Role roleByRoleId = ((FtDeviceGroupVM) ActSelectDeviceGroupForSuperPanel.this.mViewModel).getRoleByRoleId(ActSelectDeviceGroupForSuperPanel.this.selectRoleIds.get(i).longValue());
                    if (roleByRoleId instanceof Device) {
                        ActSelectDeviceGroupForSuperPanel.this.selectDeviceIdList.add(ActSelectDeviceGroupForSuperPanel.this.selectRoleIds.get(i));
                    } else if (roleByRoleId instanceof Group) {
                        ActSelectDeviceGroupForSuperPanel.this.selectGroupIdList.add(ActSelectDeviceGroupForSuperPanel.this.selectRoleIds.get(i));
                    }
                }
                ActSelectDeviceGroupForSuperPanel.this.syncDeviceAndGroup();
            }
        });
        long[] longArrayExtra = getIntent().getLongArrayExtra(Constants.DEVICE_ID_ARRAY);
        if (longArrayExtra != null) {
            for (long j : longArrayExtra) {
                this.selectRoleIds.add(Long.valueOf(j));
            }
        }
        long[] longArrayExtra2 = getIntent().getLongArrayExtra(Constants.GROUP_ID_ARRAY);
        if (longArrayExtra2 != null) {
            for (long j2 : longArrayExtra2) {
                this.selectRoleIds.add(Long.valueOf(j2));
            }
        }
    }

    public void syncDeviceAndGroup() {
        ((ObservableSubscribeProxy) Injection.net().setSuperPanelDeviceAndGroup(getIntent().getLongExtra("device_id", -1L), this.selectDeviceIdList, this.selectGroupIdList).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSelectDeviceGroupForSuperPanel$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDeviceGroupForSuperPanel.this.lambda$syncDeviceAndGroup$0((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSelectDeviceGroupForSuperPanel$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSelectDeviceGroupForSuperPanel.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSelectDeviceGroupForSuperPanel$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDeviceGroupForSuperPanel.this.lambda$syncDeviceAndGroup$1((SetSuperPanelResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncDeviceAndGroup$0(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncDeviceAndGroup$1(SetSuperPanelResponse setSuperPanelResponse) throws Exception {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (setSuperPanelResponse.getDevices() != null) {
            Iterator<SetSuperPanelResponse.DevicesBean> it = setSuperPanelResponse.getDevices().iterator();
            while (it.hasNext()) {
                arrayList.add(Long.valueOf(it.next().getDeviceid()));
            }
        }
        if (setSuperPanelResponse.getGroups() != null) {
            Iterator<SetSuperPanelResponse.GroupsBean> it2 = setSuperPanelResponse.getGroups().iterator();
            while (it2.hasNext()) {
                arrayList2.add(Long.valueOf(it2.next().getGroupid()));
            }
        }
        Injection.repo().device().setSuperPanelDeviceAndGroup(setSuperPanelResponse.getInfo().getPanelid(), arrayList, arrayList2);
        SmartToast.showShort(getString(R.string.save_success));
        setResult(-1);
        finishActivity();
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void changeSelectCount(int integer) {
        this.okTv.setText(String.format(getResources().getString(R.string.app_str_select_finish), Integer.valueOf(integer)));
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterGroup(Group group) {
        return ProductRepository.filterGroupForSuperPanel(group, this.superPanel);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        return ProductRepository.filterDeviceForSuperPanel(device, this.superPanel);
    }
}