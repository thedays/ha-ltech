package com.ltech.smarthome.ui.matter;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelectSceneBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity;
import com.ltech.smarthome.ui.newselect.FtRoomSceneVM;
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
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSelectSceneForMatter extends BaseRoomSceneActivity<ActSelectSceneBinding, FtRoomSceneVM> implements ISelectAction {
    private Device superPanel;
    private boolean waitSync;
    private List<Long> selectSceneIdList = new ArrayList();
    private List<Integer> selectSceneSortList = new ArrayList();
    private final Handler handle = new Handler(new Handler.Callback() { // from class: com.ltech.smarthome.ui.matter.ActSelectSceneForMatter.3
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                ActSelectSceneForMatter.this.handle.removeMessages(1);
                ActSelectSceneForMatter.this.handle.sendEmptyMessageDelayed(1, 10000L);
                CmdAssistant.getQueryCmdAssistant(ActSelectSceneForMatter.this.superPanel, new int[0]).queryPanelState(ActSelectSceneForMatter.this.activity, new IAction<ResponseMsg>(this) { // from class: com.ltech.smarthome.ui.matter.ActSelectSceneForMatter.3.1
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(ResponseMsg msg2) {
                    }
                }, 0);
            }
            return false;
        }
    });

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity
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

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_device));
        setEditString(getString(R.string.app_str_select_all));
        this.listType = 2;
        this.allowMultiSelect = true;
        this.superPanel = Injection.repo().device().getDeviceByDeviceId(getIntent().getLongExtra("device_id", -1L));
        ((ActSelectSceneBinding) this.mViewBinding).title.ivSearch.setVisibility(0);
        ((ActSelectSceneBinding) this.mViewBinding).layoutSort.setVisibility(8);
        ((ActSelectSceneBinding) this.mViewBinding).layoutSortAndType.setVisibility(0);
        ((ActSelectSceneBinding) this.mViewBinding).setBottomTip(String.format(getResources().getString(R.string.app_str_select_finish), 0));
        ((ActSelectSceneBinding) this.mViewBinding).tvBottom.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.matter.ActSelectSceneForMatter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActSelectSceneForMatter.this.lambda$initView$0(view);
            }
        });
        long[] longArrayExtra = getIntent().getLongArrayExtra(Constants.SCENE_ID_ARRAY);
        if (longArrayExtra != null) {
            for (long j : longArrayExtra) {
                this.selectSceneList.add(Injection.repo().scene().getSceneBySceneId(j));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (isMatter() && this.selectSceneList.size() > this.selMax) {
            SmartToast.showCenterShort(getString(R.string.app_str_dev_exceeds_the_limit));
            return;
        }
        this.selectSceneIdList.clear();
        for (int i = 0; i < this.selectSceneList.size(); i++) {
            this.selectSceneIdList.add(Long.valueOf(this.selectSceneList.get(i).getSceneId()));
            this.selectSceneSortList.add(Integer.valueOf(this.selectSceneList.get(i).getIndex()));
        }
        syncScene();
    }

    public void syncScene() {
        showLoadingDialog("");
        ((ObservableSubscribeProxy) Injection.net().syncCgKitSyncSceneByMatter(getIntent().getLongExtra("device_id", -1L), getIntent().getStringExtra(Constants.MAC_ADDRESS), this.selectSceneIdList, this.selectSceneSortList).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.matter.ActSelectSceneForMatter$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectSceneForMatter.this.lambda$syncScene$1(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncScene$1(Object obj) throws Exception {
        noticeSyncMatter();
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity
    protected void changeSelectCount(int integer) {
        ((ActSelectSceneBinding) this.mViewBinding).setBottomTip(String.format(getResources().getString(R.string.app_str_select_finish), Integer.valueOf(integer)));
    }

    public void noticeSyncMatter() {
        this.handle.removeMessages(1);
        this.waitSync = false;
        CmdAssistant.getSettingCmdAssistant(this.superPanel, new int[0]).sceneSyncMatter(ActivityUtils.getTopActivity(), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.matter.ActSelectSceneForMatter.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                if (responseMsg == null) {
                    SmartToast.showShort(ActSelectSceneForMatter.this.getString(R.string.save_fail));
                    ActSelectSceneForMatter.this.dismissLoadingDialog();
                } else {
                    ActSelectSceneForMatter.this.waitSync = true;
                    ActSelectSceneForMatter.this.handle.sendEmptyMessage(1);
                }
            }
        });
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        MessageManager.getInstance().setCgKitCallBack(new MessageManager.CgKitCallBack() { // from class: com.ltech.smarthome.ui.matter.ActSelectSceneForMatter.2
            @Override // com.smart.message.MessageManager.CgKitCallBack
            public void onDataReceive(ResponseMsg msg) {
                if (ActSelectSceneForMatter.this.superPanel == null || msg == null) {
                    return;
                }
                if (Integer.parseInt(msg.getResData().substring(6, 10), 16) == ((BleParam) ActSelectSceneForMatter.this.superPanel.getParam(BleParam.class)).getUnicastAddress()) {
                    ActSelectSceneForMatter.this.checkState(msg);
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

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity
    protected boolean filterScene(Scene scene) {
        return scene.getSceneType() == 2;
    }
}