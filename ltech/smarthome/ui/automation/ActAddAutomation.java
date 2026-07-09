package com.ltech.smarthome.ui.automation;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActAddAutomationBinding;
import com.ltech.smarthome.databinding.ItemTextAddBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.ResourceEmptyLiveData;
import com.ltech.smarthome.model.auto.DeviceConditionParam;
import com.ltech.smarthome.model.bean.Automation;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.model.scene_param.AutomationParam;
import com.ltech.smarthome.ui.automation.ActAddAutomation;
import com.ltech.smarthome.ui.automation.ActAddAutomationVM;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.CenterTipDialog;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.ltech.smarthome.view.dialog.SelectLoopTimesDialog;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import com.ltech.smarthome.view.dialog.TimeSelectWithMsDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.LHomeLog;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class ActAddAutomation extends VMActivity<ActAddAutomationBinding, ActAddAutomationVM> {
    private BaseItemDraggableAdapter<Automation.Action, BaseViewHolder> mActionAdapter;
    private ItemTextAddBinding mActionTextAddBinding;
    private BaseItemDraggableAdapter<Automation.Condition, BaseViewHolder> mConditionAdapter;
    private ItemTextAddBinding mConditionTextAddBinding;
    private BaseItemDraggableAdapter<Automation.Condition, BaseViewHolder> mExecConditionAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_add_automation;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        ((ActAddAutomationVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActAddAutomationVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActAddAutomationVM) this.mViewModel).automationId = getIntent().getLongExtra(Constants.AUTOMATION_ID, 0L);
        ((ActAddAutomationVM) this.mViewModel).automationName.setValue(getIntent().getStringExtra(Constants.AUTOMATION_NAME));
        ((ActAddAutomationVM) this.mViewModel).isEdit = ((ActAddAutomationVM) this.mViewModel).automationId > 0;
        if (((ActAddAutomationVM) this.mViewModel).isEdit) {
            setTitle(getString(R.string.edit_automation));
            ((ActAddAutomationVM) this.mViewModel).picIndex.setValue(Integer.valueOf(getIntent().getIntExtra(Constants.ICON_POSITION, 0)));
            ((ActAddAutomationBinding) this.mViewBinding).tvRemove.setVisibility(0);
        } else {
            setTitle(getString(R.string.create_automation));
            ((ActAddAutomationVM) this.mViewModel).isLocal = true;
        }
        ((ActAddAutomationBinding) this.mViewBinding).radioType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda20
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public final void onCheckedChanged(RadioGroup radioGroup, int i) {
                ActAddAutomation.this.lambda$initView$0(radioGroup, i);
            }
        });
        ((ActAddAutomationVM) this.mViewModel).isExpand.setValue(true);
        initExecConditionRv();
        initConditionRv();
        initActionRv();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(RadioGroup radioGroup, int i) {
        if (i == R.id.radio_local) {
            ((ActAddAutomationVM) this.mViewModel).isLocal = true;
            ((ActAddAutomationBinding) this.mViewBinding).layoutSelectGateway.setVisibility(0);
            ((ActAddAutomationBinding) this.mViewBinding).layoutTimes.setVisibility(0);
            ((ActAddAutomationBinding) this.mViewBinding).layoutExpand.setVisibility(0);
        } else {
            ((ActAddAutomationVM) this.mViewModel).isLocal = false;
            ((ActAddAutomationBinding) this.mViewBinding).layoutSelectGateway.setVisibility(8);
            ((ActAddAutomationBinding) this.mViewBinding).layoutTimes.setVisibility(8);
            ((ActAddAutomationBinding) this.mViewBinding).layoutInterval.setVisibility(8);
            ((ActAddAutomationBinding) this.mViewBinding).layoutExpand.setVisibility(8);
        }
        this.mExecConditionAdapter.notifyDataSetChanged();
        this.mConditionAdapter.notifyDataSetChanged();
        this.mActionAdapter.notifyDataSetChanged();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        if (LanguageUtils.isRussian(this.activity)) {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) ((ActAddAutomationBinding) this.mViewBinding).title.tvTitle.getLayoutParams();
            layoutParams.endToStart = ((ActAddAutomationBinding) this.mViewBinding).title.tvEdit.getId();
            ((ActAddAutomationBinding) this.mViewBinding).title.tvTitle.setLayoutParams(layoutParams);
        }
        ((ActAddAutomationVM) this.mViewModel).showEditNameDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddAutomation.this.lambda$startObserve$1((Void) obj);
            }
        });
        ((ActAddAutomationVM) this.mViewModel).showSelectConditionTypeEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda9
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddAutomation.this.lambda$startObserve$2((Void) obj);
            }
        });
        ((ActAddAutomationVM) this.mViewModel).showDelDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda10
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddAutomation.this.lambda$startObserve$3((Void) obj);
            }
        });
        ((ActAddAutomationVM) this.mViewModel).picIndex.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda12
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddAutomation.this.lambda$startObserve$4((Integer) obj);
            }
        });
        ((ActAddAutomationVM) this.mViewModel).gatewayId.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda13
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddAutomation.this.lambda$startObserve$5((Long) obj);
            }
        });
        showLoadingDialog("");
        ((ActAddAutomationVM) this.mViewModel).request = Injection.repo().scene().getSceneList(this, ((ActAddAutomationVM) this.mViewModel).placeId, new boolean[0]);
        ((ActAddAutomationVM) this.mViewModel).sceneList.addSource(((ActAddAutomationVM) this.mViewModel).request.data(), new Observer() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda14
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddAutomation.this.lambda$startObserve$7((Resource) obj);
            }
        });
        ((ActAddAutomationVM) this.mViewModel).automationList.addSource(Transformations.switchMap(((ActAddAutomationVM) this.mViewModel).sceneList, new Function1() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$8;
                lambda$startObserve$8 = ActAddAutomation.this.lambda$startObserve$8((List) obj);
                return lambda$startObserve$8;
            }
        }), new Observer() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda16
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddAutomation.this.lambda$startObserve$10((Resource) obj);
            }
        });
        if (((ActAddAutomationVM) this.mViewModel).isEdit) {
            handleData(Transformations.switchMap(((ActAddAutomationVM) this.mViewModel).automationList, new Function1() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda17
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LiveData lambda$startObserve$11;
                    lambda$startObserve$11 = ActAddAutomation.this.lambda$startObserve$11((List) obj);
                    return lambda$startObserve$11;
                }
            }), new IAction() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda18
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActAddAutomation.this.lambda$startObserve$12((List) obj);
                }
            });
        } else {
            ((ActAddAutomationVM) this.mViewModel).automationList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda5
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActAddAutomation.this.lambda$startObserve$13((List) obj);
                }
            });
        }
        ((ActAddAutomationVM) this.mViewModel).noSetActionDeviceEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddAutomation.this.lambda$startObserve$14((List) obj);
            }
        });
        ((ActAddAutomationVM) this.mViewModel).showErrorEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddAutomation.this.lambda$startObserve$15((List) obj);
            }
        });
        ((ActAddAutomationVM) this.mViewModel).showIntervalDialogEvent.observe(this, new AnonymousClass3());
        ((ActAddAutomationVM) this.mViewModel).showTimesDialogEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation.4
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                SelectLoopTimesDialog.asDefault().setTimes(((ActAddAutomationVM) ActAddAutomation.this.mViewModel).cycleIndex.getValue().intValue()).setOnSaveListener(new SelectLoopTimesDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation.4.1
                    @Override // com.ltech.smarthome.view.dialog.SelectLoopTimesDialog.OnSaveListener
                    public void cancel() {
                    }

                    @Override // com.ltech.smarthome.view.dialog.SelectLoopTimesDialog.OnSaveListener
                    public boolean onSave(int times) {
                        ((ActAddAutomationVM) ActAddAutomation.this.mViewModel).cycleIndex.setValue(Integer.valueOf(times));
                        return true;
                    }
                }).showDialog(ActAddAutomation.this.activity);
            }
        });
        ((ActAddAutomationVM) this.mViewModel).intervalTime.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer integer) {
                ((ActAddAutomationVM) ActAddAutomation.this.mViewModel).intervalTimeStr.setValue(ActAddAutomation.this.getIntervalTimeStr(integer.intValue()));
            }
        });
        ((ActAddAutomationVM) this.mViewModel).cycleIndex.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddAutomation.this.lambda$startObserve$16((Integer) obj);
            }
        });
        ((ActAddAutomationVM) this.mViewModel).showStatusConditionTipDialogEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation.6
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                MessageDialog.show(ActAddAutomation.this.activity, ActAddAutomation.this.getString(R.string.tips), ActAddAutomation.this.getString(R.string.add_status_condition_tip), ActAddAutomation.this.getString(R.string.ok)).show();
            }
        });
        ((ActAddAutomationVM) this.mViewModel).showAddStatusConditionEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation.7
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                if (((ActAddAutomationVM) ActAddAutomation.this.mViewModel).statusConditionList != null) {
                    ((ActAddAutomationVM) ActAddAutomation.this.mViewModel).editPosition = -1;
                    SceneHelper.instance().conditionParam = null;
                    NavUtils.destination(ActSelectAutoCondition.class).withLong(Constants.PLACE_ID, ((ActAddAutomationVM) ActAddAutomation.this.mViewModel).placeId).withString(Constants.AUTOMATION_NAME, ((ActAddAutomationVM) ActAddAutomation.this.mViewModel).automationName.getValue()).withBoolean(Constants.IS_LOCAL_AUTOMATION, ((ActAddAutomationVM) ActAddAutomation.this.mViewModel).isLocal).withBoolean(Constants.STATE_CONDITION, true).withDefaultRequestCode().navigation(ActAddAutomation.this.activity);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r1) {
        showEditDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        showConditionTypeDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r1) {
        showDelDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Integer num) {
        ((ActAddAutomationBinding) this.mViewBinding).layoutBg.setBackgroundResource(SceneHelper.getAutomationPic(this, num.intValue()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Long l) {
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(l.longValue());
        if (deviceByDeviceId != null) {
            ((ActAddAutomationBinding) this.mViewBinding).tvGateway.setText(deviceByDeviceId.getDeviceName());
            ((ActAddAutomationBinding) this.mViewBinding).tvOffline.setVisibility(deviceByDeviceId.isOnline() ? 8 : 0);
        } else if (l.longValue() != 0) {
            ((ActAddAutomationVM) this.mViewModel).gatewayId.setValue(0L);
        } else {
            ((ActAddAutomationBinding) this.mViewBinding).tvGateway.setText(getString(R.string.please_choose));
            ((ActAddAutomationBinding) this.mViewBinding).tvOffline.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(Resource resource) {
        handleResource(resource, new IAction() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActAddAutomation.this.lambda$startObserve$6((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(List list) {
        ((ActAddAutomationVM) this.mViewModel).sceneList.setValue(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$startObserve$8(List list) {
        if (list == null) {
            return ResourceEmptyLiveData.create();
        }
        return Injection.repo().auto().getAutomationList(this, ((ActAddAutomationVM) this.mViewModel).placeId).data();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$10(Resource resource) {
        handleResource(resource, new IAction() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActAddAutomation.this.lambda$startObserve$9((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$9(List list) {
        List<Automation> value = ((ActAddAutomationVM) this.mViewModel).automationList.getValue();
        if (value == null || value.size() != list.size()) {
            ((ActAddAutomationVM) this.mViewModel).automationList.setValue(list);
            if (((ActAddAutomationVM) this.mViewModel).controlId != -1) {
                ((ActAddAutomationVM) this.mViewModel).automationName.setValue(getAutomationName());
                return;
            }
            return;
        }
        showContent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$startObserve$11(List list) {
        if (list == null) {
            return ResourceEmptyLiveData.create();
        }
        return Injection.repo().auto().getAutomation(this, ((ActAddAutomationVM) this.mViewModel).automationId).data();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$12(List list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        initDataView((Automation) list.get(0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$13(List list) {
        initDataView(null);
        showContent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$14(List list) {
        View inflate = LayoutInflater.from(this.activity).inflate(R.layout.dialog_custom_list, (ViewGroup) null);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.activity));
        recyclerView.setAdapter(new BaseQuickAdapter<ActAddAutomationVM.NoActionDevice, BaseViewHolder>(this, R.layout.item_scene_no_action_tip, list) { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, ActAddAutomationVM.NoActionDevice item) {
                helper.setText(R.id.tv_content, item.getName());
                helper.setText(R.id.tv_room, item.getRoom());
            }
        });
        MessageDialog.show(this.activity, ActivityUtils.getTopActivity().getString(R.string.app_str_local_scene_device_no_action), "").setOkButton(getString(R.string.ok)).setCustomView(inflate).setCancelable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$15(List list) {
        View inflate = LayoutInflater.from(this.activity).inflate(R.layout.dialog_custom_list, (ViewGroup) null);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.activity));
        recyclerView.setAdapter(new BaseQuickAdapter<ActAddAutomationVM.NoActionDevice, BaseViewHolder>(this, R.layout.item_scene_no_action_tip, list) { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, ActAddAutomationVM.NoActionDevice item) {
                helper.setText(R.id.tv_content, item.getName()).setText(R.id.tv_room, item.getRoom()).setGone(R.id.tv_room, !TextUtils.isEmpty(item.getRoom()));
            }
        });
        MessageDialog.show(this.activity, ActivityUtils.getTopActivity().getString(R.string.save_fail), "").setOkButton(getString(R.string.ok)).setCustomView(inflate).setCancelable(false);
    }

    /* renamed from: com.ltech.smarthome.ui.automation.ActAddAutomation$3, reason: invalid class name */
    class AnonymousClass3 implements Observer<Void> {
        AnonymousClass3() {
        }

        @Override // androidx.lifecycle.Observer
        public void onChanged(Void unused) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < 60; i++) {
                arrayList.add(i < 10 ? "0" + i : String.valueOf(i));
            }
            ArrayList arrayList2 = new ArrayList();
            for (int i2 = 0; i2 < 60; i2++) {
                arrayList2.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
            }
            ArrayList arrayList3 = new ArrayList();
            arrayList3.add("000");
            for (int i3 = 1; i3 < 10; i3++) {
                arrayList3.add(String.valueOf(i3 * 100));
            }
            long intValue = ((ActAddAutomationVM) ActAddAutomation.this.mViewModel).intervalTime.getValue().intValue();
            int i4 = (int) (intValue / 60000);
            long j = intValue % 60000;
            TimeSelectWithMsDialog.asDefault().setTitle(ActAddAutomation.this.getString(R.string.app_str_loop_interval)).setMinList(arrayList).setSecList(arrayList2).setMsList(arrayList3).withUnit(true).setSelectMinPosition(i4).setSelectSecPosition((int) (j / 1000)).setSelectMsPosition((int) ((j % 1000) / 100)).setSelectListener(new TimeSelectWithMsDialog.SelectListener() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$3$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.view.dialog.TimeSelectWithMsDialog.SelectListener
                public final void confirm(int i5, int i6, int i7) {
                    ActAddAutomation.AnonymousClass3.this.lambda$onChanged$0(i5, i6, i7);
                }
            }).showDialog(ActAddAutomation.this.activity);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onChanged$0(int i, int i2, int i3) {
            int i4 = (((i * 60) + i2) * 1000) + (i3 * 100);
            if (i4 >= 500) {
                ((ActAddAutomationVM) ActAddAutomation.this.mViewModel).intervalTime.setValue(Integer.valueOf(i4));
            } else {
                SmartToast.showCenterShort(ActAddAutomation.this.getString(R.string.app_str_automation_loop_time_interval_tip));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$16(Integer num) {
        if (num.intValue() == -1) {
            ((ActAddAutomationBinding) this.mViewBinding).tvTimes.setText(getString(R.string.app_str_loop_times_infinite));
            return;
        }
        String str = num + getString(R.string.times);
        if (LanguageUtils.isEnglish(this)) {
            StringBuilder sb = new StringBuilder();
            sb.append(num);
            sb.append(" ");
            sb.append(num.intValue() == 1 ? "time" : "times");
            str = sb.toString();
        }
        ((ActAddAutomationBinding) this.mViewBinding).tvTimes.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        initDataView(null);
        showContent();
    }

    private String getAutomationName() {
        String str = "";
        boolean z = true;
        int i = 0;
        while (z) {
            i++;
            str = getString(R.string.automation) + i;
            z = Injection.repo().auto().isAutomationNameExist(Injection.repo().home().getSelectPlace().getValue().getPlaceId(), str);
        }
        return str;
    }

    private void initDataView(Automation automation) {
        List<Device> devicesByIds;
        if (((ActAddAutomationVM) this.mViewModel).actionList == null || ((ActAddAutomationVM) this.mViewModel).conditionList == null) {
            if (automation != null) {
                ((ActAddAutomationVM) this.mViewModel).automation = automation;
                ((ActAddAutomationVM) this.mViewModel).actionList = automation.getActions();
                this.mActionAdapter.setNewData(((ActAddAutomationVM) this.mViewModel).actionList);
                ((ActAddAutomationVM) this.mViewModel).conditionList = new ArrayList();
                ((ActAddAutomationVM) this.mViewModel).statusConditionList = new ArrayList();
                for (Automation.Condition condition : automation.getConditions()) {
                    if (condition.getEventtype() == 2) {
                        ((ActAddAutomationVM) this.mViewModel).statusConditionList.add(condition);
                    } else {
                        ((ActAddAutomationVM) this.mViewModel).conditionList.add(condition);
                    }
                }
                this.mExecConditionAdapter.setNewData(((ActAddAutomationVM) this.mViewModel).conditionList);
                this.mConditionAdapter.setNewData(((ActAddAutomationVM) this.mViewModel).statusConditionList);
                ((ActAddAutomationVM) this.mViewModel).gatewayId.setValue(Long.valueOf(automation.getGatewayDeviceId()));
                ((ActAddAutomationVM) this.mViewModel).cycleIndex.setValue(Integer.valueOf(automation.getCycleindex()));
                ((ActAddAutomationVM) this.mViewModel).intervalTime.setValue(Integer.valueOf(automation.getIntervaltime()));
                setConditionType(automation.getConditionType());
                setAutomationType(automation.getAutomationType());
                ((ActAddAutomationVM) this.mViewModel).timeZone = automation.getTimeZone();
                setPeriod(automation.getStartTime(), automation.getEndTime(), automation.getWeeks());
            } else {
                ((ActAddAutomationVM) this.mViewModel).cycleIndex.setValue(1);
                ((ActAddAutomationVM) this.mViewModel).intervalTime.setValue(500);
                ((ActAddAutomationVM) this.mViewModel).actionList = new ArrayList();
                this.mActionAdapter.setNewData(((ActAddAutomationVM) this.mViewModel).actionList);
                ((ActAddAutomationVM) this.mViewModel).conditionList = new ArrayList();
                ((ActAddAutomationVM) this.mViewModel).statusConditionList = new ArrayList();
                if (((ActAddAutomationVM) this.mViewModel).controlId != -1) {
                    if (!getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false)) {
                        ((ActAddAutomationVM) this.mViewModel).conditionList.add(createDeviceCondition(Injection.repo().device().getDeviceById(((ActAddAutomationVM) this.mViewModel).controlId)));
                    } else {
                        Group groupById = Injection.repo().group().getGroupById(((ActAddAutomationVM) this.mViewModel).controlId);
                        if (groupById != null && groupById.getDeviceIds() != null && (devicesByIds = Injection.repo().device().getDevicesByIds(groupById.getDeviceIds())) != null) {
                            Iterator<Device> it = devicesByIds.iterator();
                            while (it.hasNext()) {
                                ((ActAddAutomationVM) this.mViewModel).conditionList.add(createDeviceCondition(it.next()));
                            }
                        }
                    }
                }
                this.mExecConditionAdapter.setNewData(((ActAddAutomationVM) this.mViewModel).conditionList);
                this.mConditionAdapter.setNewData(((ActAddAutomationVM) this.mViewModel).statusConditionList);
                setConditionType(1);
                setPeriod("00:00", "23:59", "1,2,3,4,5,6,7");
            }
            if (!this.mExecConditionAdapter.getData().isEmpty()) {
                if (((ActAddAutomationVM) this.mViewModel).statusConditionList.isEmpty()) {
                    ((ActAddAutomationBinding) this.mViewBinding).layoutStatusConditionBg.setVisibility(0);
                    ((ActAddAutomationBinding) this.mViewBinding).tvConditionType.setVisibility(8);
                    ((ActAddAutomationBinding) this.mViewBinding).rvCondition.setVisibility(8);
                } else {
                    ((ActAddAutomationBinding) this.mViewBinding).layoutStatusConditionBg.setVisibility(8);
                    ((ActAddAutomationBinding) this.mViewBinding).tvConditionType.setVisibility(0);
                    ((ActAddAutomationBinding) this.mViewBinding).rvCondition.setVisibility(0);
                }
            } else {
                ((ActAddAutomationBinding) this.mViewBinding).layoutStatusConditionBg.setVisibility(8);
                ((ActAddAutomationBinding) this.mViewBinding).tvConditionType.setVisibility(8);
                ((ActAddAutomationBinding) this.mViewBinding).rvCondition.setVisibility(8);
            }
            dismissLoadingDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getIntervalTimeStr(int milliseconds) {
        StringBuilder sb = new StringBuilder();
        long j = milliseconds / 60000;
        if (j > 0) {
            sb.append(j);
            sb.append(getString(R.string.min));
        }
        long j2 = milliseconds % 60000;
        long j3 = j2 / 1000;
        if (j3 > 0) {
            sb.append(j3);
            sb.append(getString(R.string.sec));
        }
        long j4 = j2 % 1000;
        if (j4 > 0) {
            sb.append(j4);
            sb.append(getString(R.string.ms));
        }
        return sb.toString();
    }

    private Automation.Condition createDeviceCondition(Device device) {
        DeviceConditionParam deviceConditionParam;
        deviceConditionParam = new DeviceConditionParam();
        deviceConditionParam.productid = device.getProductId();
        deviceConditionParam.deviceid = device.getDeviceId();
        String productId = device.getProductId();
        productId.hashCode();
        switch (productId) {
            case "121120911474901":
            case "121061709483801":
            case "3842335314313472":
                deviceConditionParam.type = 2;
                deviceConditionParam.index = 1;
                break;
            case "122051609304501":
            case "122111615282701":
            case "122110709484501":
                deviceConditionParam.type = 4;
                deviceConditionParam.index = 1;
                break;
            case "4304746736451584":
                deviceConditionParam.type = 2;
                deviceConditionParam.index = 1;
                deviceConditionParam.value = 65535;
                break;
            case "3763962108692992":
                deviceConditionParam.type = 1;
                deviceConditionParam.index = 1;
                break;
            default:
                int intExtra = getIntent().getIntExtra(Constants.RELATED_POSITION, 0);
                deviceConditionParam.type = 1;
                deviceConditionParam.index = (intExtra * 2) + 1;
                break;
        }
        deviceConditionParam.floorRoom = device.getFloorName() + device.getRoomName();
        deviceConditionParam.deviceName = device.getDeviceName();
        LHomeLog.i(getClass(), "DeviceConditionParam=" + deviceConditionParam.toString());
        SceneHelper.instance().conditionParam = deviceConditionParam;
        SceneHelper.instance().conditionParamType = 8;
        return SceneHelper.instance().convert2AutomationCondition();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (getTitleBar().getEditString().equals(getString(R.string.save))) {
            if (((ActAddAutomationVM) this.mViewModel).isLocal && ((ActAddAutomationVM) this.mViewModel).gatewayId.getValue().longValue() == 0) {
                SmartToast.showShort(getString(R.string.please_choose_gateway));
                return;
            }
            if (!((ActAddAutomationVM) this.mViewModel).hasAction()) {
                SmartToast.showShort(getString(R.string.app_str_please_add_action));
                return;
            }
            if (((ActAddAutomationVM) this.mViewModel).conditionList.size() == 0) {
                SmartToast.showShort(getString(R.string.app_str_please_add_condition));
                return;
            } else {
                if (((ActAddAutomationVM) this.mViewModel).checkError(this)) {
                    if (((ActAddAutomationVM) this.mViewModel).isEdit) {
                        ((ActAddAutomationVM) this.mViewModel).editAutomation();
                        return;
                    } else {
                        ((ActAddAutomationVM) this.mViewModel).addAutomation();
                        return;
                    }
                }
                return;
            }
        }
        if (getTitleBar().getEditString().equals(getString(R.string.finish))) {
            setEditString(getString(R.string.save));
            ((ActAddAutomationBinding) this.mViewBinding).tvName.setEnabled(true);
            ((ActAddAutomationBinding) this.mViewBinding).ivChangePic.setEnabled(true);
            ((ActAddAutomationBinding) this.mViewBinding).tvConditionType.setEnabled(true);
            this.mConditionTextAddBinding.rlAdd.setEnabled(true);
            this.mActionTextAddBinding.rlAdd.setEnabled(true);
            ((ActAddAutomationBinding) this.mViewBinding).goItem.layoutItemBg.setEnabled(true);
            ((ActAddAutomationBinding) this.mViewBinding).tvRemove.setEnabled(true);
            ((ActAddAutomationBinding) this.mViewBinding).rvAction.setSwipeItemMenuEnabled(true);
            ((ActAddAutomationBinding) this.mViewBinding).rvCondition.setSwipeItemMenuEnabled(true);
            this.mActionAdapter.disableDragItem();
            this.mActionAdapter.notifyDataSetChanged();
            this.mConditionAdapter.notifyDataSetChanged();
            this.mExecConditionAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void showContent() {
        super.showContent();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        ((ActAddAutomationVM) this.mViewModel).request.retry();
    }

    private void showEditDialog() {
        EditDialog.asDefault().setContent(((ActAddAutomationVM) this.mViewModel).automationName.getValue()).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActAddAutomation.this.lambda$showEditDialog$17((String) obj);
            }
        }).showDialog(getSupportFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditDialog$17(String str) {
        ((ActAddAutomationVM) this.mViewModel).automationName.setValue(str);
    }

    private void showConditionTypeDialog() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.one_condition));
        arrayList.add(getString(R.string.all_condition));
        SelectListDialog.asDefault(false).setTitle(getString(R.string.choose_condition_type)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActAddAutomation.this.lambda$showConditionTypeDialog$18((Integer) obj);
            }
        }).setSelectList(arrayList).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showConditionTypeDialog$18(Integer num) {
        if (num.intValue() == 1) {
            setConditionType(2);
        } else {
            setConditionType(1);
        }
    }

    private void initExecConditionRv() {
        ((ActAddAutomationBinding) this.mViewBinding).rvExecCondition.setSwipeMenuCreator(new SwipeMenuCreator() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda22
            @Override // com.yanzhenjie.recyclerview.SwipeMenuCreator
            public final void onCreateMenu(SwipeMenu swipeMenu, SwipeMenu swipeMenu2, int i) {
                ActAddAutomation.this.lambda$initExecConditionRv$19(swipeMenu, swipeMenu2, i);
            }
        });
        ((ActAddAutomationBinding) this.mViewBinding).rvExecCondition.setOnItemMenuClickListener(new OnItemMenuClickListener() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda26
            @Override // com.yanzhenjie.recyclerview.OnItemMenuClickListener
            public final void onItemClick(SwipeMenuBridge swipeMenuBridge, int i) {
                ActAddAutomation.this.lambda$initExecConditionRv$20(swipeMenuBridge, i);
            }
        });
        AnonymousClass8 anonymousClass8 = new AnonymousClass8(R.layout.item_auto_condition, new ArrayList());
        this.mExecConditionAdapter = anonymousClass8;
        anonymousClass8.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda27
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActAddAutomation.this.lambda$initExecConditionRv$21(baseQuickAdapter, view, i);
            }
        });
        this.mExecConditionAdapter.bindToRecyclerView(((ActAddAutomationBinding) this.mViewBinding).rvExecCondition);
        ((ActAddAutomationBinding) this.mViewBinding).rvExecCondition.setLayoutManager(new LinearLayoutManager(this));
        ((DefaultItemAnimator) ((ActAddAutomationBinding) this.mViewBinding).rvExecCondition.getItemAnimator()).setSupportsChangeAnimations(false);
        ItemTextAddBinding itemTextAddBinding = (ItemTextAddBinding) DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_text_add, (ViewGroup) ((ActAddAutomationBinding) this.mViewBinding).rvExecCondition.getParent(), false);
        this.mConditionTextAddBinding = itemTextAddBinding;
        itemTextAddBinding.setImageRes(Integer.valueOf(R.mipmap.ic_add_red));
        this.mConditionTextAddBinding.setItem(getString(R.string.add_condition));
        this.mConditionTextAddBinding.setTextColor(Integer.valueOf(ContextCompat.getColor(this, R.color.color_text_black)));
        this.mConditionTextAddBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda28
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActAddAutomation.this.lambda$initExecConditionRv$22((View) obj);
            }
        }));
        ((ActAddAutomationBinding) this.mViewBinding).rvExecCondition.addFooterView(this.mConditionTextAddBinding.getRoot());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initExecConditionRv$19(SwipeMenu swipeMenu, SwipeMenu swipeMenu2, int i) {
        swipeMenu2.addMenuItem(new SwipeMenuItem(this).setTextSize(14).setWidth(SizeUtils.dp2px(60.0f)).setHeight(-1).setText(R.string.delete).setTextColor(-1).setBackgroundColorResource(R.color.color_text_red));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initExecConditionRv$20(SwipeMenuBridge swipeMenuBridge, int i) {
        swipeMenuBridge.closeMenu();
        if (-1 == swipeMenuBridge.getDirection() && swipeMenuBridge.getPosition() == 0) {
            this.mExecConditionAdapter.remove(i);
        }
    }

    /* renamed from: com.ltech.smarthome.ui.automation.ActAddAutomation$8, reason: invalid class name */
    class AnonymousClass8 extends BaseItemDraggableAdapter<Automation.Condition, BaseViewHolder> {
        AnonymousClass8(int layoutResId, List data) {
            super(layoutResId, data);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(BaseViewHolder helper, Automation.Condition item) {
            ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
            final ActAddAutomationVM.DisplayState conditionState = ((ActAddAutomationVM) ActAddAutomation.this.mViewModel).getConditionState(this.mContext, item);
            if (conditionState != null) {
                helper.setImageResource(R.id.iv_icon, conditionState.iconRes).setText(R.id.tv_name, conditionState.name).setText(R.id.tv_content, conditionState.state);
                if (!TextUtils.isEmpty(conditionState.errorTip)) {
                    helper.setGone(R.id.tv_condition_tip, true);
                    helper.setText(R.id.tv_condition_tip, conditionState.errorTip);
                } else {
                    helper.setGone(R.id.tv_condition_tip, false);
                }
                if (!TextUtils.isEmpty(conditionState.hint)) {
                    helper.setGone(R.id.iv_hint, true);
                    helper.getView(R.id.iv_hint).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$8$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            ActAddAutomation.AnonymousClass8.this.lambda$convert$0(conditionState, view);
                        }
                    });
                } else {
                    helper.setGone(R.id.iv_hint, false);
                }
            }
            if (ActAddAutomation.this.getTitleBar().getEditString().equals(ActAddAutomation.this.getString(R.string.save))) {
                helper.setEnabled(R.id.layout_item_bg, true);
                helper.setGone(R.id.iv_go, true);
            } else {
                helper.setEnabled(R.id.layout_item_bg, false);
                helper.setGone(R.id.iv_go, false);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(ActAddAutomationVM.DisplayState displayState, View view) {
            CenterTipDialog.asDefault().setTitle(ActAddAutomation.this.getString(R.string.tips)).setMessageString(displayState.hint).setCancelString(ActAddAutomation.this.getString(R.string.ok)).showDialog(ActAddAutomation.this.activity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initExecConditionRv$21(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        ((ActAddAutomationVM) this.mViewModel).editPosition = i;
        SceneHelper.instance().reset();
        SceneHelper.instance().automationCondition = true;
        ((ActAddAutomationVM) this.mViewModel).editCondition(this.mExecConditionAdapter.getData().get(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initExecConditionRv$22(View view) {
        if (((ActAddAutomationVM) this.mViewModel).conditionList != null) {
            ((ActAddAutomationVM) this.mViewModel).editPosition = -1;
            SceneHelper.instance().reset();
            SceneHelper.instance().automationCondition = true;
            NavUtils.destination(ActSelectAutoCondition.class).withLong(Constants.PLACE_ID, ((ActAddAutomationVM) this.mViewModel).placeId).withString(Constants.AUTOMATION_NAME, ((ActAddAutomationVM) this.mViewModel).automationName.getValue()).withBoolean(Constants.IS_LOCAL_AUTOMATION, ((ActAddAutomationVM) this.mViewModel).isLocal).withDefaultRequestCode().navigation(this);
        }
    }

    private void initConditionRv() {
        ((ActAddAutomationBinding) this.mViewBinding).rvCondition.setSwipeMenuCreator(new SwipeMenuCreator() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda29
            @Override // com.yanzhenjie.recyclerview.SwipeMenuCreator
            public final void onCreateMenu(SwipeMenu swipeMenu, SwipeMenu swipeMenu2, int i) {
                ActAddAutomation.this.lambda$initConditionRv$23(swipeMenu, swipeMenu2, i);
            }
        });
        ((ActAddAutomationBinding) this.mViewBinding).rvCondition.setOnItemMenuClickListener(new OnItemMenuClickListener() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda30
            @Override // com.yanzhenjie.recyclerview.OnItemMenuClickListener
            public final void onItemClick(SwipeMenuBridge swipeMenuBridge, int i) {
                ActAddAutomation.this.lambda$initConditionRv$24(swipeMenuBridge, i);
            }
        });
        AnonymousClass9 anonymousClass9 = new AnonymousClass9(R.layout.item_auto_condition, new ArrayList());
        this.mConditionAdapter = anonymousClass9;
        anonymousClass9.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda31
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActAddAutomation.this.lambda$initConditionRv$25(baseQuickAdapter, view, i);
            }
        });
        this.mConditionAdapter.bindToRecyclerView(((ActAddAutomationBinding) this.mViewBinding).rvCondition);
        ((ActAddAutomationBinding) this.mViewBinding).rvCondition.setLayoutManager(new LinearLayoutManager(this));
        ((DefaultItemAnimator) ((ActAddAutomationBinding) this.mViewBinding).rvCondition.getItemAnimator()).setSupportsChangeAnimations(false);
        ItemTextAddBinding itemTextAddBinding = (ItemTextAddBinding) DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_text_add, (ViewGroup) ((ActAddAutomationBinding) this.mViewBinding).rvCondition.getParent(), false);
        this.mConditionTextAddBinding = itemTextAddBinding;
        itemTextAddBinding.setImageRes(Integer.valueOf(R.mipmap.ic_add_red));
        this.mConditionTextAddBinding.setItem(getString(R.string.add_condition));
        this.mConditionTextAddBinding.setTextColor(Integer.valueOf(ContextCompat.getColor(this, R.color.color_text_black)));
        this.mConditionTextAddBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda32
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActAddAutomation.this.lambda$initConditionRv$26((View) obj);
            }
        }));
        ((ActAddAutomationBinding) this.mViewBinding).rvCondition.addFooterView(this.mConditionTextAddBinding.getRoot());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initConditionRv$23(SwipeMenu swipeMenu, SwipeMenu swipeMenu2, int i) {
        swipeMenu2.addMenuItem(new SwipeMenuItem(this).setTextSize(14).setWidth(SizeUtils.dp2px(60.0f)).setHeight(-1).setText(R.string.delete).setTextColor(-1).setBackgroundColorResource(R.color.color_text_red));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initConditionRv$24(SwipeMenuBridge swipeMenuBridge, int i) {
        swipeMenuBridge.closeMenu();
        if (-1 == swipeMenuBridge.getDirection() && swipeMenuBridge.getPosition() == 0) {
            this.mConditionAdapter.remove(i);
        }
    }

    /* renamed from: com.ltech.smarthome.ui.automation.ActAddAutomation$9, reason: invalid class name */
    class AnonymousClass9 extends BaseItemDraggableAdapter<Automation.Condition, BaseViewHolder> {
        AnonymousClass9(int layoutResId, List data) {
            super(layoutResId, data);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(BaseViewHolder helper, Automation.Condition item) {
            ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
            final ActAddAutomationVM.DisplayState statusConditionState = ((ActAddAutomationVM) ActAddAutomation.this.mViewModel).getStatusConditionState(this.mContext, item);
            if (statusConditionState != null) {
                helper.setImageResource(R.id.iv_icon, statusConditionState.iconRes).setText(R.id.tv_name, statusConditionState.name).setText(R.id.tv_content, statusConditionState.state);
                if (!TextUtils.isEmpty(statusConditionState.errorTip)) {
                    helper.setGone(R.id.tv_condition_tip, true);
                    helper.setText(R.id.tv_condition_tip, statusConditionState.errorTip);
                } else {
                    helper.setGone(R.id.tv_condition_tip, false);
                }
                if (!TextUtils.isEmpty(statusConditionState.hint)) {
                    helper.setGone(R.id.iv_hint, true);
                    helper.getView(R.id.iv_hint).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$9$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            ActAddAutomation.AnonymousClass9.this.lambda$convert$0(statusConditionState, view);
                        }
                    });
                } else {
                    helper.setGone(R.id.iv_hint, false);
                }
            }
            if (ActAddAutomation.this.getTitleBar().getEditString().equals(ActAddAutomation.this.getString(R.string.save))) {
                helper.setEnabled(R.id.layout_item_bg, true);
                helper.setGone(R.id.iv_go, true);
            } else {
                helper.setEnabled(R.id.layout_item_bg, false);
                helper.setGone(R.id.iv_go, false);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(ActAddAutomationVM.DisplayState displayState, View view) {
            CenterTipDialog.asDefault().setTitle(ActAddAutomation.this.getString(R.string.tips)).setMessageString(displayState.hint).setCancelString(ActAddAutomation.this.getString(R.string.ok)).showDialog(ActAddAutomation.this.activity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initConditionRv$25(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        ((ActAddAutomationVM) this.mViewModel).editPosition = i;
        SceneHelper.instance().reset();
        SceneHelper.instance().automationCondition = true;
        ((ActAddAutomationVM) this.mViewModel).editCondition(this.mConditionAdapter.getData().get(i), true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initConditionRv$26(View view) {
        ((ActAddAutomationVM) this.mViewModel).showAddStatusConditionEvent.call();
    }

    private void showDelDialog() {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.tip_remove_automation)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda19
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showDelDialog$27;
                lambda$showDelDialog$27 = ActAddAutomation.this.lambda$showDelDialog$27(baseDialog, view);
                return lambda$showDelDialog$27;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showDelDialog$27(BaseDialog baseDialog, View view) {
        ((ActAddAutomationVM) this.mViewModel).removeAutomation(((ActAddAutomationVM) this.mViewModel).automationId);
        return false;
    }

    private void initActionRv() {
        ((ActAddAutomationBinding) this.mViewBinding).rvAction.setSwipeMenuCreator(new SwipeMenuCreator() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda21
            @Override // com.yanzhenjie.recyclerview.SwipeMenuCreator
            public final void onCreateMenu(SwipeMenu swipeMenu, SwipeMenu swipeMenu2, int i) {
                ActAddAutomation.this.lambda$initActionRv$28(swipeMenu, swipeMenu2, i);
            }
        });
        ((ActAddAutomationBinding) this.mViewBinding).rvAction.setOnItemMenuClickListener(new OnItemMenuClickListener() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda23
            @Override // com.yanzhenjie.recyclerview.OnItemMenuClickListener
            public final void onItemClick(SwipeMenuBridge swipeMenuBridge, int i) {
                ActAddAutomation.this.lambda$initActionRv$29(swipeMenuBridge, i);
            }
        });
        BaseItemDraggableAdapter<Automation.Action, BaseViewHolder> baseItemDraggableAdapter = new BaseItemDraggableAdapter<Automation.Action, BaseViewHolder>(R.layout.item_scene_action, new ArrayList()) { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation.10
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Automation.Action item) {
                ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
                ((AppCompatTextView) helper.getView(R.id.tv_delay_time)).getPaint().setFakeBoldText(true);
                if (helper.getBindingAdapterPosition() == ActAddAutomation.this.mActionAdapter.getData().size() - 1) {
                    helper.getView(R.id.tv_delay_time).setVisibility(8);
                } else {
                    helper.getView(R.id.tv_delay_time).setVisibility(0);
                }
                helper.setText(R.id.tv_delay_time, ActAddAutomation.this.getString(R.string.delay) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + item.getActiondelays() + ActAddAutomation.this.getString(R.string.sec)).addOnClickListener(R.id.v_action).addOnClickListener(R.id.tv_delay_time);
                if (ActAddAutomation.this.getTitleBar().getEditString().equals(ActAddAutomation.this.getString(R.string.save))) {
                    helper.setGone(R.id.ivDrag, false);
                    helper.setEnabled(R.id.v_action, true);
                    helper.setEnabled(R.id.tv_delay_time, true);
                } else {
                    helper.setGone(R.id.ivDrag, true);
                    helper.setEnabled(R.id.v_action, false);
                    helper.setEnabled(R.id.tv_delay_time, false);
                }
                ActAddAutomationVM.DisplayState actionState = ((ActAddAutomationVM) ActAddAutomation.this.mViewModel).getActionState(this.mContext, item);
                if (actionState != null) {
                    helper.setImageResource(R.id.iv_icon, actionState.iconRes).setText(R.id.tv_device_name, actionState.name).setText(R.id.tv_location, actionState.location).setText(R.id.tv_state, actionState.state).setGone(R.id.layout_state, !MaskType.isAppNoticeAction(item.getActiontype())).setGone(R.id.tv_virtual, actionState.isVirtual);
                    if (actionState.rgbColor != Integer.MIN_VALUE) {
                        helper.getView(R.id.civ_color).setVisibility(0);
                        ((CircleImageView) helper.getView(R.id.civ_color)).setImageDrawable(new ColorDrawable(actionState.rgbColor));
                    } else {
                        helper.getView(R.id.civ_color).setVisibility(8);
                    }
                    if (!TextUtils.isEmpty(actionState.errorTip)) {
                        helper.setGone(R.id.tv_action_tip, true);
                        helper.setText(R.id.tv_action_tip, actionState.errorTip);
                        if (actionState.errorTip.equals(ActAddAutomation.this.getString(R.string.not_support_local_tip))) {
                            helper.setEnabled(R.id.v_action, MaskType.isAutomationAction(item.getActiontype()));
                            return;
                        }
                        return;
                    }
                    helper.setGone(R.id.tv_action_tip, false);
                    return;
                }
                helper.setImageResource(R.id.iv_icon, R.color.transparent).setText(R.id.tv_device_name, "").setText(R.id.tv_state, "");
            }
        };
        this.mActionAdapter = baseItemDraggableAdapter;
        baseItemDraggableAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda24
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActAddAutomation.this.lambda$initActionRv$30(baseQuickAdapter, view, i);
            }
        });
        this.mActionAdapter.setOnItemDragListener(new OnItemDragListener() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation.11
            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragMoving(RecyclerView.ViewHolder viewHolder, int i, RecyclerView.ViewHolder viewHolder1, int i1) {
            }

            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int i) {
            }

            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int i) {
                ActAddAutomation.this.mActionAdapter.notifyDataSetChanged();
            }
        });
        this.mActionAdapter.bindToRecyclerView(((ActAddAutomationBinding) this.mViewBinding).rvAction);
        ((ActAddAutomationBinding) this.mViewBinding).rvAction.setLayoutManager(new LinearLayoutManager(this));
        ((DefaultItemAnimator) ((ActAddAutomationBinding) this.mViewBinding).rvAction.getItemAnimator()).setSupportsChangeAnimations(false);
        ItemTextAddBinding itemTextAddBinding = (ItemTextAddBinding) DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_text_add, (ViewGroup) ((ActAddAutomationBinding) this.mViewBinding).rvAction.getParent(), false);
        this.mActionTextAddBinding = itemTextAddBinding;
        itemTextAddBinding.setImageRes(Integer.valueOf(R.mipmap.ic_add_red));
        this.mActionTextAddBinding.setItem(getString(R.string.add_action));
        this.mActionTextAddBinding.setTextColor(Integer.valueOf(ContextCompat.getColor(this, R.color.color_text_black)));
        this.mActionTextAddBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda25
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActAddAutomation.this.lambda$initActionRv$31((View) obj);
            }
        }));
        ((ActAddAutomationBinding) this.mViewBinding).rvAction.addFooterView(this.mActionTextAddBinding.getRoot());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionRv$28(SwipeMenu swipeMenu, SwipeMenu swipeMenu2, int i) {
        swipeMenu2.addMenuItem(new SwipeMenuItem(this).setTextSize(14).setWidth(SizeUtils.dp2px(60.0f)).setHeight(-1).setText(R.string.more).setTextColor(-1).setBackgroundColorResource(R.color.color_light_gray));
        swipeMenu2.addMenuItem(new SwipeMenuItem(this).setTextSize(14).setWidth(SizeUtils.dp2px(60.0f)).setHeight(-1).setText(R.string.delete).setTextColor(-1).setBackgroundColorResource(R.color.color_text_red));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionRv$29(SwipeMenuBridge swipeMenuBridge, int i) {
        swipeMenuBridge.closeMenu();
        if (-1 == swipeMenuBridge.getDirection()) {
            int position = swipeMenuBridge.getPosition();
            if (position != 0) {
                if (position != 1) {
                    return;
                }
                this.mActionAdapter.remove(i);
                BaseItemDraggableAdapter<Automation.Action, BaseViewHolder> baseItemDraggableAdapter = this.mActionAdapter;
                baseItemDraggableAdapter.notifyItemChanged(baseItemDraggableAdapter.getData().size() - 1);
                return;
            }
            setEditString(getString(R.string.finish));
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemDragAndSwipeCallback(this.mActionAdapter));
            itemTouchHelper.attachToRecyclerView(((ActAddAutomationBinding) this.mViewBinding).rvAction);
            this.mActionAdapter.enableDragItem(itemTouchHelper, R.id.ivDrag, false);
            ((ActAddAutomationBinding) this.mViewBinding).tvName.setEnabled(false);
            ((ActAddAutomationBinding) this.mViewBinding).ivChangePic.setEnabled(false);
            ((ActAddAutomationBinding) this.mViewBinding).tvConditionType.setEnabled(false);
            this.mConditionTextAddBinding.rlAdd.setEnabled(false);
            this.mActionTextAddBinding.rlAdd.setEnabled(false);
            ((ActAddAutomationBinding) this.mViewBinding).goItem.layoutItemBg.setEnabled(false);
            ((ActAddAutomationBinding) this.mViewBinding).tvRemove.setEnabled(false);
            this.mActionAdapter.notifyDataSetChanged();
            this.mConditionAdapter.notifyDataSetChanged();
            this.mExecConditionAdapter.notifyDataSetChanged();
            ((ActAddAutomationBinding) this.mViewBinding).rvAction.setSwipeItemMenuEnabled(false);
            ((ActAddAutomationBinding) this.mViewBinding).rvCondition.setSwipeItemMenuEnabled(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionRv$30(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int id = view.getId();
        if (id == R.id.tv_delay_time) {
            editTimeSpace(i, this.mActionAdapter.getData().get(i).getActiondelays());
        } else {
            if (id != R.id.v_action) {
                return;
            }
            ((ActAddAutomationVM) this.mViewModel).editPosition = i;
            SceneHelper.instance().reset();
            ((ActAddAutomationVM) this.mViewModel).editAction(this.mActionAdapter.getData().get(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionRv$31(View view) {
        if (((ActAddAutomationVM) this.mViewModel).actionList != null) {
            ((ActAddAutomationVM) this.mViewModel).editPosition = -1;
            SceneHelper.instance().reset();
            SceneHelper.instance().automationAction = true;
            SceneHelper.instance().cacheObject.put(SceneHelper.CACHE_SELECT_AUTOMATION, ((ActAddAutomationVM) this.mViewModel).getCurActionAutomation());
            NavUtils.destination(ActSelectAutoAction.class).withLong(Constants.PLACE_ID, ((ActAddAutomationVM) this.mViewModel).placeId).withString(Constants.AUTOMATION_NAME, ((ActAddAutomationVM) this.mViewModel).automationName.getValue()).withBoolean(Constants.IS_LOCAL_AUTOMATION, ((ActAddAutomationVM) this.mViewModel).isLocal).withDefaultRequestCode().navigation(this);
        }
    }

    private void setPeriod(String startTime, String endTime, String weeks) {
        ((ActAddAutomationVM) this.mViewModel).startTime = startTime;
        ((ActAddAutomationVM) this.mViewModel).endTime = endTime;
        ((ActAddAutomationVM) this.mViewModel).weeks = weeks;
        GoItem goItem = ((ActAddAutomationVM) this.mViewModel).effectPeriod;
        String str = ((ActAddAutomationVM) this.mViewModel).startTime;
        goItem.setSubText(String.format("%s - %s%s", str, getString(isNextDay(((ActAddAutomationVM) this.mViewModel).startTime, ((ActAddAutomationVM) this.mViewModel).endTime, com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR) ? R.string.next_day : R.string.this_day), " " + ((ActAddAutomationVM) this.mViewModel).endTime));
        ((ActAddAutomationVM) this.mViewModel).effectTimeLiveData.setValue(((ActAddAutomationVM) this.mViewModel).effectPeriod);
    }

    public boolean isNextDay(String start, String end, String regex) {
        String[] split = start.split(regex);
        String[] split2 = end.split(regex);
        return split.length == 2 && split2.length == 2 && (Integer.parseInt(split[0]) * 60) + Integer.parseInt(split[1]) >= (Integer.parseInt(split2[0]) * 60) + Integer.parseInt(split2[1]);
    }

    private void setConditionType(int conditionType) {
        ((ActAddAutomationVM) this.mViewModel).conditionType = conditionType;
        int i = ((ActAddAutomationVM) this.mViewModel).conditionType;
        if (i == 1) {
            ((ActAddAutomationBinding) this.mViewBinding).tvConditionType.setText(getString(R.string.condition_one_tip) + "  v");
            return;
        }
        if (i != 2) {
            return;
        }
        ((ActAddAutomationBinding) this.mViewBinding).tvConditionType.setText(getString(R.string.condition_all_tip) + "  v");
    }

    private void setAutomationType(int automationType) {
        if (automationType == 2) {
            ((ActAddAutomationVM) this.mViewModel).isLocal = true;
            ((ActAddAutomationBinding) this.mViewBinding).radioLocal.setChecked(true);
            ((ActAddAutomationBinding) this.mViewBinding).layoutInterval.setVisibility(0);
            ((ActAddAutomationBinding) this.mViewBinding).layoutTimes.setVisibility(0);
            return;
        }
        ((ActAddAutomationVM) this.mViewModel).isLocal = false;
        ((ActAddAutomationBinding) this.mViewBinding).radioCloud.setChecked(true);
        ((ActAddAutomationBinding) this.mViewBinding).layoutInterval.setVisibility(8);
        ((ActAddAutomationBinding) this.mViewBinding).layoutTimes.setVisibility(8);
    }

    private void editTimeSpace(final int position, int sec) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 60; i++) {
            arrayList.add(i < 10 ? "0" + i : String.valueOf(i));
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = 0; i2 < 60; i2++) {
            arrayList2.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
        }
        TimeSelectDialog.asDefault().setTitle(getString(R.string.delay_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(true).supportRightAway(false).setMinUnit(getString(R.string.min)).setSecUnit(getString(R.string.sec)).setSelectMinPosition(sec / 60).setSelectSecPosition(sec % 60).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomation$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i3, int i4) {
                ActAddAutomation.this.lambda$editTimeSpace$32(position, i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$editTimeSpace$32(int i, int i2, int i3) {
        if (i2 == 0 && i3 == 0) {
            ((ActAddAutomationVM) this.mViewModel).actionList.get(i).setActiondelays((i2 * 60) + 1);
        } else {
            ((ActAddAutomationVM) this.mViewModel).actionList.get(i).setActiondelays((i2 * 60) + i3);
        }
        this.mActionAdapter.notifyItemChanged(i);
    }

    private void savaAction() {
        List<Automation.Action> convert2AutomationAction = SceneHelper.instance().convert2AutomationAction();
        if (convert2AutomationAction.isEmpty()) {
            return;
        }
        if (MaskType.isAutomationAction(convert2AutomationAction.get(0).getActiontype())) {
            int i = 0;
            while (i < ((ActAddAutomationVM) this.mViewModel).actionList.size()) {
                if (MaskType.isAutomationAction(((ActAddAutomationVM) this.mViewModel).actionList.get(i).getActiontype())) {
                    Iterator<Automation.Action> it = convert2AutomationAction.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            Automation.Action next = it.next();
                            if (((AutomationParam) next.getParams(AutomationParam.class)).equals(((ActAddAutomationVM) this.mViewModel).actionList.get(i).getParams(AutomationParam.class))) {
                                this.mActionAdapter.setData(i, next);
                                break;
                            }
                        } else {
                            this.mActionAdapter.remove(i);
                            i--;
                            break;
                        }
                    }
                }
                i++;
            }
            for (Automation.Action action : convert2AutomationAction) {
                int i2 = 0;
                while (true) {
                    if (i2 < ((ActAddAutomationVM) this.mViewModel).actionList.size()) {
                        if (((AutomationParam) action.getParams(AutomationParam.class)).equals(((ActAddAutomationVM) this.mViewModel).actionList.get(i2).getParams(AutomationParam.class))) {
                            break;
                        } else {
                            i2++;
                        }
                    } else {
                        this.mActionAdapter.addData((BaseItemDraggableAdapter<Automation.Action, BaseViewHolder>) action);
                        break;
                    }
                }
            }
            this.mActionAdapter.notifyDataSetChanged();
            return;
        }
        if (MaskType.isSceneAction(convert2AutomationAction.get(0).getActiontype())) {
            if (((ActAddAutomationVM) this.mViewModel).editPosition == -1) {
                this.mActionAdapter.addData(convert2AutomationAction);
            } else {
                this.mActionAdapter.remove(((ActAddAutomationVM) this.mViewModel).editPosition);
                this.mActionAdapter.addData(((ActAddAutomationVM) this.mViewModel).editPosition, convert2AutomationAction);
            }
            this.mActionAdapter.notifyDataSetChanged();
            return;
        }
        if (((ActAddAutomationVM) this.mViewModel).editPosition == -1) {
            this.mActionAdapter.addData(convert2AutomationAction);
            this.mActionAdapter.notifyDataSetChanged();
            return;
        }
        convert2AutomationAction.get(0).setActiondelays(this.mActionAdapter.getData().get(((ActAddAutomationVM) this.mViewModel).editPosition).getActiondelays());
        if (SceneHelper.instance().isCgdProAction) {
            this.mActionAdapter.remove(((ActAddAutomationVM) this.mViewModel).editPosition);
            this.mActionAdapter.addData(((ActAddAutomationVM) this.mViewModel).editPosition, convert2AutomationAction);
        } else {
            this.mActionAdapter.setData(((ActAddAutomationVM) this.mViewModel).editPosition, convert2AutomationAction.get(0));
        }
    }

    private void saveCondition() {
        Automation.Condition convert2AutomationCondition = SceneHelper.instance().convert2AutomationCondition();
        if (((ActAddAutomationVM) this.mViewModel).editPosition == -1) {
            this.mExecConditionAdapter.addData((BaseItemDraggableAdapter<Automation.Condition, BaseViewHolder>) convert2AutomationCondition);
        } else {
            this.mExecConditionAdapter.setData(((ActAddAutomationVM) this.mViewModel).editPosition, convert2AutomationCondition);
        }
        ((ActAddAutomationBinding) this.mViewBinding).tvConditionExecType.setText(getString(R.string.condition_exec_tip));
        if (!this.mExecConditionAdapter.getData().isEmpty()) {
            if (((ActAddAutomationVM) this.mViewModel).statusConditionList.isEmpty()) {
                ((ActAddAutomationBinding) this.mViewBinding).layoutStatusConditionBg.setVisibility(0);
                ((ActAddAutomationBinding) this.mViewBinding).tvConditionType.setVisibility(8);
                ((ActAddAutomationBinding) this.mViewBinding).rvCondition.setVisibility(8);
                return;
            } else {
                ((ActAddAutomationBinding) this.mViewBinding).layoutStatusConditionBg.setVisibility(8);
                ((ActAddAutomationBinding) this.mViewBinding).tvConditionType.setVisibility(0);
                ((ActAddAutomationBinding) this.mViewBinding).rvCondition.setVisibility(0);
                return;
            }
        }
        ((ActAddAutomationBinding) this.mViewBinding).layoutStatusConditionBg.setVisibility(8);
        ((ActAddAutomationBinding) this.mViewBinding).tvConditionType.setVisibility(8);
        ((ActAddAutomationBinding) this.mViewBinding).rvCondition.setVisibility(8);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            savaAction();
            return;
        }
        if (3003 == resultCode) {
            saveCondition();
            return;
        }
        if (5013 == resultCode) {
            saveStatusCondition();
            return;
        }
        if (3004 == resultCode) {
            if (data != null) {
                setPeriod(data.getStringExtra(Constants.START_TIME), data.getStringExtra(Constants.END_TIME), data.getStringExtra(Constants.WEEKS));
            }
        } else if (3002 == resultCode) {
            if (data != null) {
                ((ActAddAutomationVM) this.mViewModel).picIndex.setValue(Integer.valueOf(data.getIntExtra(Constants.ICON_POSITION, 0)));
            }
        } else {
            if (3018 != resultCode || data == null) {
                return;
            }
            ((ActAddAutomationVM) this.mViewModel).gatewayId.setValue(Long.valueOf(data.getLongExtra("device_id", -1L)));
        }
    }

    private void saveStatusCondition() {
        Automation.Condition convert2AutomationCondition = SceneHelper.instance().convert2AutomationCondition();
        if (((ActAddAutomationVM) this.mViewModel).editPosition == -1) {
            this.mConditionAdapter.addData((BaseItemDraggableAdapter<Automation.Condition, BaseViewHolder>) convert2AutomationCondition);
        } else {
            this.mConditionAdapter.setData(((ActAddAutomationVM) this.mViewModel).editPosition, convert2AutomationCondition);
        }
        int i = ((ActAddAutomationVM) this.mViewModel).conditionType;
        if (i == 1) {
            ((ActAddAutomationBinding) this.mViewBinding).tvConditionType.setText(getString(R.string.condition_one_tip) + "  v");
        } else if (i == 2) {
            ((ActAddAutomationBinding) this.mViewBinding).tvConditionType.setText(getString(R.string.condition_all_tip) + "  v");
        }
        ((ActAddAutomationBinding) this.mViewBinding).layoutStatusConditionBg.setVisibility(8);
        ((ActAddAutomationBinding) this.mViewBinding).tvConditionType.setVisibility(0);
        ((ActAddAutomationBinding) this.mViewBinding).rvCondition.setVisibility(0);
    }
}