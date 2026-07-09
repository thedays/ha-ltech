package com.ltech.smarthome.ui.device.super_panel;

import android.content.Intent;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSuperPanelVoiceControlRangeRoleBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSuperPanelVoiceControlRangeRole extends VMActivity<ActSuperPanelVoiceControlRangeRoleBinding, ActSuperPanelVoiceControlRangeVM> {
    private List<Long> deviceIdList = new ArrayList();
    private List<Long> groupIdList = new ArrayList();
    private List<Long> sceneIdList = new ArrayList();
    public FtDeviceAndScene selectFt;
    private int selectPos;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_super_panel_voice_control_range_role;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((ActSuperPanelVoiceControlRangeRoleBinding) this.mViewBinding).ivBack.setImageResource(R.mipmap.icon_back);
        ((ActSuperPanelVoiceControlRangeRoleBinding) this.mViewBinding).ivEdit.setImageResource(R.mipmap.ic_edit_black);
        long[] longArrayExtra = getIntent().getLongArrayExtra(Constants.DEVICE_ID_ARRAY);
        if (longArrayExtra != null) {
            for (long j : longArrayExtra) {
                this.deviceIdList.add(Long.valueOf(j));
            }
        }
        long[] longArrayExtra2 = getIntent().getLongArrayExtra(Constants.GROUP_ID_ARRAY);
        if (longArrayExtra2 != null) {
            for (long j2 : longArrayExtra2) {
                this.groupIdList.add(Long.valueOf(j2));
            }
        }
        long[] longArrayExtra3 = getIntent().getLongArrayExtra(Constants.SCENE_ID_ARRAY);
        if (longArrayExtra3 != null) {
            for (long j3 : longArrayExtra3) {
                this.sceneIdList.add(Long.valueOf(j3));
            }
        }
        ((ActSuperPanelVoiceControlRangeVM) this.mViewModel).initDeviceAndSceneFtList(getIntent().getLongExtra(Constants.PLACE_ID, -1L));
        initViewpage();
    }

    private void initViewpage() {
        ((ActSuperPanelVoiceControlRangeRoleBinding) this.mViewBinding).viewpager.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelVoiceControlRangeRole.1
            @Override // androidx.viewpager2.adapter.FragmentStateAdapter
            public Fragment createFragment(int position) {
                return ((ActSuperPanelVoiceControlRangeVM) ActSuperPanelVoiceControlRangeRole.this.mViewModel).deviceAndSceneFtList.get(position);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return ((ActSuperPanelVoiceControlRangeVM) ActSuperPanelVoiceControlRangeRole.this.mViewModel).deviceAndSceneFtList.size();
            }
        });
        ((ActSuperPanelVoiceControlRangeRoleBinding) this.mViewBinding).viewpager.setOffscreenPageLimit(-1);
        new TabLayoutMediator(((ActSuperPanelVoiceControlRangeRoleBinding) this.mViewBinding).tabTitle, ((ActSuperPanelVoiceControlRangeRoleBinding) this.mViewBinding).viewpager, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelVoiceControlRangeRole$$ExternalSyntheticLambda0
            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
            public final void onConfigureTab(TabLayout.Tab tab, int i) {
                ActSuperPanelVoiceControlRangeRole.this.lambda$initViewpage$0(tab, i);
            }
        }).attach();
        ((ActSuperPanelVoiceControlRangeRoleBinding) this.mViewBinding).viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelVoiceControlRangeRole.2
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int position) {
                ActSuperPanelVoiceControlRangeRole actSuperPanelVoiceControlRangeRole = ActSuperPanelVoiceControlRangeRole.this;
                actSuperPanelVoiceControlRangeRole.selectFt = ((ActSuperPanelVoiceControlRangeVM) actSuperPanelVoiceControlRangeRole.mViewModel).deviceAndSceneFtList.get(position);
                ActSuperPanelVoiceControlRangeRole.this.selectPos = position;
                if (position == 0) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.addAll(ActSuperPanelVoiceControlRangeRole.this.deviceIdList);
                    arrayList.addAll(ActSuperPanelVoiceControlRangeRole.this.groupIdList);
                    ActSuperPanelVoiceControlRangeRole.this.selectFt.replaceDeviceAndGroupData(arrayList);
                    return;
                }
                if (ActSuperPanelVoiceControlRangeRole.this.selectFt != null) {
                    ActSuperPanelVoiceControlRangeRole.this.selectFt.replaceSceneData(ActSuperPanelVoiceControlRangeRole.this.sceneIdList);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViewpage$0(TabLayout.Tab tab, int i) {
        tab.setText(getResources().getStringArray(R.array.device_scene_tab)[i]);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActSuperPanelVoiceControlRangeVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        Injection.repo().device().getDeviceFromDb(((ActSuperPanelVoiceControlRangeVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelVoiceControlRangeRole$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanelVoiceControlRangeRole.this.lambda$startObserve$1((Device) obj);
            }
        });
        ((ActSuperPanelVoiceControlRangeVM) this.mViewModel).backEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelVoiceControlRangeRole$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanelVoiceControlRangeRole.this.lambda$startObserve$2((Void) obj);
            }
        });
        ((ActSuperPanelVoiceControlRangeVM) this.mViewModel).editEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelVoiceControlRangeRole$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanelVoiceControlRangeRole.this.lambda$startObserve$3((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        ((ActSuperPanelVoiceControlRangeVM) this.mViewModel).controlDevice.setValue(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        finishActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r1) {
        nav();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        long[] jArr = new long[this.deviceIdList.size()];
        int size = this.deviceIdList.size();
        for (int i = 0; i < size; i++) {
            jArr[i] = this.deviceIdList.get(i).longValue();
        }
        long[] jArr2 = new long[this.groupIdList.size()];
        int size2 = this.groupIdList.size();
        for (int i2 = 0; i2 < size2; i2++) {
            jArr2[i2] = this.groupIdList.get(i2).longValue();
        }
        long[] jArr3 = new long[this.sceneIdList.size()];
        int size3 = this.sceneIdList.size();
        for (int i3 = 0; i3 < size3; i3++) {
            jArr3[i3] = this.sceneIdList.get(i3).longValue();
        }
        Intent intent = new Intent();
        intent.putExtra(Constants.DEVICE_ID_ARRAY, jArr);
        intent.putExtra(Constants.GROUP_ID_ARRAY, jArr2);
        intent.putExtra(Constants.SCENE_ID_ARRAY, jArr3);
        setResult(-1, intent);
        super.back();
    }

    public void nav() {
        long[] jArr = new long[this.deviceIdList.size()];
        int size = this.deviceIdList.size();
        for (int i = 0; i < size; i++) {
            jArr[i] = this.deviceIdList.get(i).longValue();
        }
        long[] jArr2 = new long[this.groupIdList.size()];
        int size2 = this.groupIdList.size();
        for (int i2 = 0; i2 < size2; i2++) {
            jArr2[i2] = this.groupIdList.get(i2).longValue();
        }
        long[] jArr3 = new long[this.sceneIdList.size()];
        int size3 = this.sceneIdList.size();
        for (int i3 = 0; i3 < size3; i3++) {
            jArr3[i3] = this.sceneIdList.get(i3).longValue();
        }
        NavUtils.destination(ActVoiceControlRangeSelectRole.class).withLong(Constants.CONTROL_ID, ((ActSuperPanelVoiceControlRangeVM) this.mViewModel).controlId).withLong("device_id", ((ActSuperPanelVoiceControlRangeVM) this.mViewModel).controlDevice.getValue().getDeviceId()).withLong(Constants.PLACE_ID, ((ActSuperPanelVoiceControlRangeVM) this.mViewModel).controlDevice.getValue().getPlaceId()).withInt(Constants.SELECT_TYPE, this.selectPos).withLongArray(Constants.DEVICE_ID_ARRAY, jArr).withLongArray(Constants.GROUP_ID_ARRAY, jArr2).withLongArray(Constants.SCENE_ID_ARRAY, jArr3).withDefaultRequestCode().navigation(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1 || data == null) {
            return;
        }
        this.deviceIdList.clear();
        long[] longArrayExtra = data.getLongArrayExtra(Constants.DEVICE_ID_ARRAY);
        if (longArrayExtra != null) {
            for (long j : longArrayExtra) {
                this.deviceIdList.add(Long.valueOf(j));
            }
        }
        this.groupIdList.clear();
        long[] longArrayExtra2 = data.getLongArrayExtra(Constants.GROUP_ID_ARRAY);
        if (longArrayExtra2 != null) {
            for (long j2 : longArrayExtra2) {
                this.groupIdList.add(Long.valueOf(j2));
            }
        }
        this.sceneIdList.clear();
        long[] longArrayExtra3 = data.getLongArrayExtra(Constants.SCENE_ID_ARRAY);
        if (longArrayExtra3 != null) {
            for (long j3 : longArrayExtra3) {
                this.sceneIdList.add(Long.valueOf(j3));
            }
        }
        if (this.selectPos == 0) {
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(this.deviceIdList);
            arrayList.addAll(this.groupIdList);
            this.selectFt.replaceDeviceAndGroupData(arrayList);
            return;
        }
        FtDeviceAndScene ftDeviceAndScene = this.selectFt;
        if (ftDeviceAndScene != null) {
            ftDeviceAndScene.replaceSceneData(this.sceneIdList);
        }
    }
}