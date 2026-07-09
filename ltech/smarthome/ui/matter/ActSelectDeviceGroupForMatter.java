package com.ltech.smarthome.ui.matter;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSelectDeviceGroupForMatter extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> implements ISelectAction {
    private TextView okTv;
    private Device superPanel;
    private boolean waitSync;
    private List<Long> selectDeviceIdList = new ArrayList();
    private List<Long> selectGroupIdList = new ArrayList();
    private List<Integer> selectDeviceSortList = new ArrayList();
    private List<Integer> selectGroupSortList = new ArrayList();
    private final Handler handle = new Handler(new Handler.Callback() { // from class: com.ltech.smarthome.ui.matter.ActSelectDeviceGroupForMatter.4
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                ActSelectDeviceGroupForMatter.this.handle.removeMessages(1);
                ActSelectDeviceGroupForMatter.this.handle.sendEmptyMessageDelayed(1, 10000L);
                CmdAssistant.getQueryCmdAssistant(ActSelectDeviceGroupForMatter.this.superPanel, new int[0]).queryPanelState(ActSelectDeviceGroupForMatter.this.activity, new IAction<ResponseMsg>(this) { // from class: com.ltech.smarthome.ui.matter.ActSelectDeviceGroupForMatter.4.1
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(ResponseMsg msg2) {
                    }
                }, 0);
            }
            return false;
        }
    });

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDaliSubDeviceGroup(Role role) {
        return true;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean isMatter() {
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
        this.okTv.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.matter.ActSelectDeviceGroupForMatter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (ActSelectDeviceGroupForMatter.this.isMatter() && ActSelectDeviceGroupForMatter.this.matterRealSelectedNum > ActSelectDeviceGroupForMatter.this.selMax) {
                    SmartToast.showCenterShort(ActSelectDeviceGroupForMatter.this.getString(R.string.app_str_dev_exceeds_the_limit));
                    return;
                }
                ActSelectDeviceGroupForMatter.this.selectDeviceIdList.clear();
                ActSelectDeviceGroupForMatter.this.selectGroupIdList.clear();
                for (int i = 0; i < ActSelectDeviceGroupForMatter.this.selectRoleIds.size(); i++) {
                    Role roleByRoleId = ((FtDeviceGroupVM) ActSelectDeviceGroupForMatter.this.mViewModel).getRoleByRoleId(ActSelectDeviceGroupForMatter.this.selectRoleIds.get(i).longValue());
                    if (roleByRoleId instanceof Device) {
                        ActSelectDeviceGroupForMatter.this.selectDeviceIdList.add(ActSelectDeviceGroupForMatter.this.selectRoleIds.get(i));
                        ActSelectDeviceGroupForMatter.this.selectDeviceSortList.add(Integer.valueOf(((Device) roleByRoleId).getIndex()));
                    } else if (roleByRoleId instanceof Group) {
                        ActSelectDeviceGroupForMatter.this.selectGroupIdList.add(ActSelectDeviceGroupForMatter.this.selectRoleIds.get(i));
                        ActSelectDeviceGroupForMatter.this.selectGroupSortList.add(Integer.valueOf(((Group) roleByRoleId).getIndex()));
                    }
                }
                ActSelectDeviceGroupForMatter.this.syncDeviceAndGroup();
            }
        });
        long[] longArrayExtra = getIntent().getLongArrayExtra(Constants.DEVICE_ID_ARRAY);
        if (longArrayExtra != null) {
            ArrayList arrayList = new ArrayList();
            for (long j : longArrayExtra) {
                this.selectRoleIds.add(Long.valueOf(j));
                arrayList.add(Long.valueOf(j));
            }
            if (!arrayList.isEmpty()) {
                Iterator<Device> it = Injection.repo().device().getDevicesByIds(arrayList).iterator();
                while (it.hasNext()) {
                    this.matterRealSelectedNum += ProductRepository.getRelayCount(ProductRepository.getLightColorType((Object) it.next()));
                }
            }
        }
        long[] longArrayExtra2 = getIntent().getLongArrayExtra(Constants.GROUP_ID_ARRAY);
        if (longArrayExtra2 != null) {
            ArrayList arrayList2 = new ArrayList();
            for (long j2 : longArrayExtra2) {
                this.selectRoleIds.add(Long.valueOf(j2));
                arrayList2.add(Long.valueOf(j2));
            }
            if (arrayList2.isEmpty()) {
                return;
            }
            Iterator<Group> it2 = Injection.repo().group().getGroupsByIds(arrayList2).iterator();
            while (it2.hasNext()) {
                this.matterRealSelectedNum += ProductRepository.getRelayCount(ProductRepository.getLightColorType((Object) it2.next()));
            }
        }
    }

    public void syncDeviceAndGroup() {
        showLoadingDialog("");
        ((ObservableSubscribeProxy) Injection.net().syncCgKitSyncDeviceByMatter(getIntent().getLongExtra("device_id", -1L), getIntent().getStringExtra(Constants.MAC_ADDRESS), this.selectDeviceIdList, this.selectGroupIdList, this.selectDeviceSortList, this.selectGroupSortList).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.matter.ActSelectDeviceGroupForMatter$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDeviceGroupForMatter.this.lambda$syncDeviceAndGroup$0(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncDeviceAndGroup$0(Object obj) throws Exception {
        noticeSyncMatter();
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void changeSelectCount(int integer) {
        this.okTv.setText(String.format(getResources().getString(R.string.app_str_select_finish), Integer.valueOf(integer)));
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterGroup(Group group) {
        return ProductRepository.filterGroupForMatter(group, this.superPanel);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        return ProductRepository.filterDeviceForMatter(device, this.superPanel);
    }

    public void noticeSyncMatter() {
        this.handle.removeMessages(1);
        this.waitSync = false;
        CmdAssistant.getSettingCmdAssistant(this.superPanel, new int[0]).syncMatter(ActivityUtils.getTopActivity(), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.matter.ActSelectDeviceGroupForMatter.2
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                if (responseMsg == null) {
                    SmartToast.showShort(ActSelectDeviceGroupForMatter.this.getString(R.string.save_fail));
                    ActSelectDeviceGroupForMatter.this.dismissLoadingDialog();
                } else {
                    ActSelectDeviceGroupForMatter.this.waitSync = true;
                    ActSelectDeviceGroupForMatter.this.handle.sendEmptyMessage(1);
                }
            }
        });
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        MessageManager.getInstance().setCgKitCallBack(new MessageManager.CgKitCallBack() { // from class: com.ltech.smarthome.ui.matter.ActSelectDeviceGroupForMatter.3
            @Override // com.smart.message.MessageManager.CgKitCallBack
            public void onDataReceive(ResponseMsg msg) {
                if (ActSelectDeviceGroupForMatter.this.superPanel == null || msg == null) {
                    return;
                }
                if (Integer.parseInt(msg.getResData().substring(6, 10), 16) == ((BleParam) ActSelectDeviceGroupForMatter.this.superPanel.getParam(BleParam.class)).getUnicastAddress()) {
                    ActSelectDeviceGroupForMatter.this.checkState(msg);
                }
            }
        });
    }

    public void checkState(ResponseMsg msg) {
        int parseInt = Integer.parseInt(msg.getResData().substring(18, 20), 16);
        if ((parseInt == 36 || parseInt == 0) && this.waitSync) {
            this.handle.removeMessages(1);
            this.waitSync = false;
            dismissLoadingDialog();
            SmartToast.showShort(getString(R.string.save_success));
            setResult(-1);
            finishActivity();
        }
    }
}