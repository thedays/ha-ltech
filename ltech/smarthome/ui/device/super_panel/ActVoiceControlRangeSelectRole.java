package com.ltech.smarthome.ui.device.super_panel;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActVoiceControlRangeSelectRole extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> {
    private long deviceId;
    private Floor lastFloor;
    private TextView okTv;
    private int lastRoomPosition = -1;
    private int showType = 0;
    private boolean isFirst = true;
    private boolean isChangeData = false;
    private List<Long> initDeviceIdList = new ArrayList();
    private List<Long> initGroupIdList = new ArrayList();
    private List<Long> initSceneIdList = new ArrayList();
    private List<Long> selectDeviceIdList = new ArrayList();
    private List<Long> selectGroupIdList = new ArrayList();
    private List<Long> selectSceneIdList = new ArrayList();
    private List<Long> changedDeviceIdList = new ArrayList();
    private List<Long> changedGroupIdList = new ArrayList();
    private List<Long> changedSceneIdList = new ArrayList();

    static /* synthetic */ boolean lambda$back$2(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((ActSelect3Binding) this.mViewBinding).setTitleGone(true);
        ((ActSelect3Binding) this.mViewBinding).layoutTab.setVisibility(0);
        ((ActSelect3Binding) this.mViewBinding).layoutSort.setVisibility(8);
        ((ActSelect3Binding) this.mViewBinding).layoutSortAndType.setVisibility(0);
        ((ActSelect3Binding) this.mViewBinding).ivBack.setImageResource(R.mipmap.icon_back);
        ((ActSelect3Binding) this.mViewBinding).tvEdit.setText(getString(R.string.app_str_select_all));
        ((FtDeviceGroupVM) this.mViewModel).needFloorRoomMemory = false;
        this.includeScene = true;
        this.useLayoutTabTitle = true;
        ((ActSelect3Binding) this.mViewBinding).ivSearch.setVisibility(0);
        this.listType = 1;
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
        long[] longArrayExtra = getIntent().getLongArrayExtra(Constants.DEVICE_ID_ARRAY);
        if (longArrayExtra != null) {
            for (long j : longArrayExtra) {
                this.selectDeviceIdList.add(Long.valueOf(j));
                this.initDeviceIdList.add(Long.valueOf(j));
            }
        }
        long[] longArrayExtra2 = getIntent().getLongArrayExtra(Constants.GROUP_ID_ARRAY);
        if (longArrayExtra2 != null) {
            for (long j2 : longArrayExtra2) {
                this.selectGroupIdList.add(Long.valueOf(j2));
                this.initGroupIdList.add(Long.valueOf(j2));
            }
        }
        long[] longArrayExtra3 = getIntent().getLongArrayExtra(Constants.SCENE_ID_ARRAY);
        if (longArrayExtra3 != null) {
            for (long j3 : longArrayExtra3) {
                this.selectSceneIdList.add(Long.valueOf(j3));
                this.initSceneIdList.add(Long.valueOf(j3));
            }
        }
        ((ActSelect3Binding) this.mViewBinding).layoutTab.setVisibility(0);
        TabLayout.Tab text = ((ActSelect3Binding) this.mViewBinding).tabTitle.newTab().setText(R.string.device);
        ViewHelpUtil.createTabCustomView(this, text, getString(R.string.device), true);
        TabLayout.Tab text2 = ((ActSelect3Binding) this.mViewBinding).tabTitle.newTab().setText(R.string.scenes);
        ViewHelpUtil.createTabCustomView(this, text2, getString(R.string.scenes), false);
        ((ActSelect3Binding) this.mViewBinding).tabTitle.addTab(text);
        ((ActSelect3Binding) this.mViewBinding).tabTitle.addTab(text2);
        ((ActSelect3Binding) this.mViewBinding).tabTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActVoiceControlRangeSelectRole.1
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                ViewHelpUtil.createTabCustomView(ActVoiceControlRangeSelectRole.this.activity, tab, tab.getText().toString(), true);
                ActVoiceControlRangeSelectRole.this.showType = tab.getPosition();
                if (ActVoiceControlRangeSelectRole.this.showType == 0) {
                    ((ActSelect3Binding) ActVoiceControlRangeSelectRole.this.mViewBinding).layoutSortAndType.setVisibility(0);
                    ((ActSelect3Binding) ActVoiceControlRangeSelectRole.this.mViewBinding).layoutSceneSortAndType.setVisibility(8);
                    ActVoiceControlRangeSelectRole actVoiceControlRangeSelectRole = ActVoiceControlRangeSelectRole.this;
                    actVoiceControlRangeSelectRole.showSortText(actVoiceControlRangeSelectRole.selectSortType);
                    ActVoiceControlRangeSelectRole.this.listType = 1;
                } else {
                    ((ActSelect3Binding) ActVoiceControlRangeSelectRole.this.mViewBinding).layoutSortAndType.setVisibility(8);
                    ((ActSelect3Binding) ActVoiceControlRangeSelectRole.this.mViewBinding).layoutSceneSortAndType.setVisibility(0);
                    ActVoiceControlRangeSelectRole actVoiceControlRangeSelectRole2 = ActVoiceControlRangeSelectRole.this;
                    actVoiceControlRangeSelectRole2.showSortSceneText(actVoiceControlRangeSelectRole2.selectSortSceneType);
                    ActVoiceControlRangeSelectRole.this.listType = 7;
                }
                if (ActVoiceControlRangeSelectRole.this.selectFt != null) {
                    ActVoiceControlRangeSelectRole.this.selectFt.getData();
                }
                int i = ((FtDeviceGroupVM) ActVoiceControlRangeSelectRole.this.mViewModel).roomPosition;
                if (ActVoiceControlRangeSelectRole.this.lastFloor != null) {
                    Floor floor = ActVoiceControlRangeSelectRole.this.lastFloor;
                    ((FtDeviceGroupVM) ActVoiceControlRangeSelectRole.this.mViewModel).setFloorPosition(floor, ActVoiceControlRangeSelectRole.this.mFloorList);
                    ActVoiceControlRangeSelectRole actVoiceControlRangeSelectRole3 = ActVoiceControlRangeSelectRole.this;
                    actVoiceControlRangeSelectRole3.lastFloor = ((FtDeviceGroupVM) actVoiceControlRangeSelectRole3.mViewModel).getCurFloor();
                    ((FtDeviceGroupVM) ActVoiceControlRangeSelectRole.this.mViewModel).selectFloor.setValue(floor);
                } else {
                    ActVoiceControlRangeSelectRole actVoiceControlRangeSelectRole4 = ActVoiceControlRangeSelectRole.this;
                    actVoiceControlRangeSelectRole4.lastFloor = ((FtDeviceGroupVM) actVoiceControlRangeSelectRole4.mViewModel).getCurFloor();
                    ((FtDeviceGroupVM) ActVoiceControlRangeSelectRole.this.mViewModel).selectFloor.setValue(ActVoiceControlRangeSelectRole.this.mFloorList.get(0));
                }
                ActVoiceControlRangeSelectRole.this.lastRoomPosition = i;
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
                ViewHelpUtil.createTabCustomView(ActVoiceControlRangeSelectRole.this.activity, tab, tab.getText().toString(), false);
            }
        });
        View view = new View(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, ConvertUtils.dp2px(74.0f));
        view.setBackgroundColor(getResources().getColor(R.color.white));
        ((ActSelect3Binding) this.mViewBinding).footerView.addView(view, layoutParams);
        TextView textView = new TextView(this);
        this.okTv = textView;
        textView.setTextColor(getResources().getColor(R.color.color_text_red));
        this.okTv.setTextSize(14.0f);
        this.okTv.setBackground(getDrawable(R.drawable.shape_red_bt_22));
        this.okTv.setGravity(17);
        this.okTv.setText(String.format(getResources().getString(R.string.voice_call_str_save_num), 0));
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, ConvertUtils.dp2px(44.0f));
        layoutParams2.leftMargin = Utils.dip2px(this, 30.0f);
        layoutParams2.rightMargin = Utils.dip2px(this, 30.0f);
        layoutParams2.topMargin = Utils.dip2px(this, 15.0f);
        ((ActSelect3Binding) this.mViewBinding).footerView.addView(this.okTv, layoutParams2);
        this.okTv.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActVoiceControlRangeSelectRole$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ActVoiceControlRangeSelectRole.this.lambda$initView$0(view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        this.selectDeviceIdList.clear();
        this.selectGroupIdList.clear();
        this.selectSceneIdList.clear();
        for (int i = 0; i < this.selectRoleIds.size(); i++) {
            Role roleByRoleIdIncludeScene = ((FtDeviceGroupVM) this.mViewModel).getRoleByRoleIdIncludeScene(this.selectRoleIds.get(i).longValue());
            if (roleByRoleIdIncludeScene instanceof Device) {
                this.selectDeviceIdList.add(this.selectRoleIds.get(i));
            } else if (roleByRoleIdIncludeScene instanceof Group) {
                this.selectGroupIdList.add(this.selectRoleIds.get(i));
            } else if (roleByRoleIdIncludeScene instanceof Scene) {
                this.selectSceneIdList.add(this.selectRoleIds.get(i));
            }
        }
        syncData();
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void initData() {
        super.initData();
        if (this.isFirst) {
            this.isFirst = false;
            this.selectRoleIds.addAll(this.selectDeviceIdList);
            this.selectRoleIds.addAll(this.selectGroupIdList);
            this.selectRoleIds.addAll(this.selectSceneIdList);
            getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.super_panel.ActVoiceControlRangeSelectRole.2
                @Override // java.lang.Runnable
                public void run() {
                    ((ActSelect3Binding) ActVoiceControlRangeSelectRole.this.mViewBinding).tabTitle.getTabAt(ActVoiceControlRangeSelectRole.this.getIntent().getIntExtra(Constants.SELECT_TYPE, 0)).select();
                }
            }, 50L);
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (this.isChangeData) {
            long[] jArr = new long[this.changedDeviceIdList.size()];
            int size = this.changedDeviceIdList.size();
            for (int i = 0; i < size; i++) {
                jArr[i] = this.changedDeviceIdList.get(i).longValue();
            }
            long[] jArr2 = new long[this.changedGroupIdList.size()];
            int size2 = this.changedGroupIdList.size();
            for (int i2 = 0; i2 < size2; i2++) {
                jArr2[i2] = this.changedGroupIdList.get(i2).longValue();
            }
            long[] jArr3 = new long[this.changedSceneIdList.size()];
            int size3 = this.changedSceneIdList.size();
            for (int i3 = 0; i3 < size3; i3++) {
                jArr3[i3] = this.changedSceneIdList.get(i3).longValue();
            }
            Intent intent = new Intent();
            intent.putExtra(Constants.DEVICE_ID_ARRAY, jArr);
            intent.putExtra(Constants.GROUP_ID_ARRAY, jArr2);
            intent.putExtra(Constants.SCENE_ID_ARRAY, jArr3);
            setResult(-1, intent);
            super.back();
            return;
        }
        if (needShowConfirmDialog()) {
            MessageDialog.show(this, "", getString(R.string.need_save_action)).setOkButton(getString(R.string.ir_exit), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActVoiceControlRangeSelectRole$$ExternalSyntheticLambda3
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$back$1;
                    lambda$back$1 = ActVoiceControlRangeSelectRole.this.lambda$back$1(baseDialog, view);
                    return lambda$back$1;
                }
            }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActVoiceControlRangeSelectRole$$ExternalSyntheticLambda4
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    return ActVoiceControlRangeSelectRole.lambda$back$2(baseDialog, view);
                }
            }).setCancelable(false);
        } else {
            super.back();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$back$1(BaseDialog baseDialog, View view) {
        super.back();
        return false;
    }

    private boolean needShowConfirmDialog() {
        this.selectDeviceIdList.clear();
        this.selectGroupIdList.clear();
        this.selectSceneIdList.clear();
        for (int i = 0; i < this.selectRoleIds.size(); i++) {
            Role roleByRoleIdIncludeScene = ((FtDeviceGroupVM) this.mViewModel).getRoleByRoleIdIncludeScene(this.selectRoleIds.get(i).longValue());
            if (roleByRoleIdIncludeScene instanceof Device) {
                this.selectDeviceIdList.add(this.selectRoleIds.get(i));
            } else if (roleByRoleIdIncludeScene instanceof Group) {
                this.selectGroupIdList.add(this.selectRoleIds.get(i));
            } else if (roleByRoleIdIncludeScene instanceof Scene) {
                this.selectSceneIdList.add(this.selectRoleIds.get(i));
            }
        }
        return (this.initDeviceIdList.equals(this.selectDeviceIdList) && this.initGroupIdList.equals(this.selectGroupIdList) && this.initSceneIdList.equals(this.selectSceneIdList)) ? false : true;
    }

    public void syncData() {
        ((ObservableSubscribeProxy) Injection.net().setSuperPanelVoiceControlRange(this.deviceId, 3, this.placeId, this.selectDeviceIdList, this.selectGroupIdList, this.selectSceneIdList).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActVoiceControlRangeSelectRole$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActVoiceControlRangeSelectRole.this.lambda$syncData$3((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.super_panel.ActVoiceControlRangeSelectRole$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActVoiceControlRangeSelectRole.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActVoiceControlRangeSelectRole$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActVoiceControlRangeSelectRole.this.lambda$syncData$4(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncData$3(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncData$4(Object obj) throws Exception {
        this.changedDeviceIdList = new ArrayList(this.selectDeviceIdList);
        this.changedGroupIdList = new ArrayList(this.selectGroupIdList);
        this.changedSceneIdList = new ArrayList(this.selectSceneIdList);
        this.isChangeData = true;
        SmartToast.showShort(getString(R.string.save_success));
        finishActivity();
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected int getFloorIndex(List<Floor> floorList) {
        int i = this.lastRoomPosition;
        return i == -1 ? super.getFloorIndex(floorList) : i;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected int getRoomIndex(List<Room> roomList) {
        int i = this.lastRoomPosition;
        if (i == -1) {
            return 0;
        }
        return i;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void changeSelectCount(int selectCount) {
        if (this.selectAll) {
            ((ActSelect3Binding) this.mViewBinding).tvEdit.setText(getString(R.string.app_str_cancel_select_all));
        } else {
            ((ActSelect3Binding) this.mViewBinding).tvEdit.setText(getString(R.string.app_str_select_all));
        }
        if (selectCount == 0) {
            this.okTv.setEnabled(false);
            this.okTv.setAlpha(0.5f);
        } else {
            this.okTv.setEnabled(true);
            this.okTv.setAlpha(1.0f);
        }
        this.okTv.setText(String.format(getResources().getString(R.string.voice_call_str_save_num), Integer.valueOf(selectCount)));
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        return this.showType == 0;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterGroup(Group group) {
        return this.showType == 0;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterScene(Scene scene) {
        return this.showType == 1;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean checkSceneType(Scene scene) {
        return this.selectSceneType == scene.getSceneType() || this.selectSceneType == TYPE_SCENE_ALL;
    }
}