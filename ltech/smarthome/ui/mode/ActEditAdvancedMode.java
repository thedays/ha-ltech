package com.ltech.smarthome.ui.mode;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import com.ltech.smarthome.databinding.ActEditAdvancedModeBinding;
import com.ltech.smarthome.databinding.ItemAdvancedModeAddBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.MultiColorView;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.MsTimeSelectDialog;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.bean.AdvancedModeInfo;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActEditAdvancedMode extends VMActivity<ActEditAdvancedModeBinding, BaseEditAdvancedModeVM> {
    private String B;
    private String ChangeTime;
    private String G;
    private String Rg;
    private String RgbBrt;
    private String StopTime;
    private String Wy;
    private String WyBrt;
    private String color_num;
    private CountDownTimer countDownTimer;
    private String details;
    private int initPosition;
    private String loop_num;
    private BaseItemDraggableAdapter<AdvancedModeInfo.ContentItem, BaseViewHolder> mAdapter;
    private int number;
    private boolean previewing;
    private int start;
    private int totalTime;

    static /* synthetic */ boolean lambda$showChangeDialog$17(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_edit_advanced_mode;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.VMActivity
    public BaseEditAdvancedModeVM getViewModel() {
        return (BaseEditAdvancedModeVM) new ViewModelProvider(this).get(ActCmdEditAdvancedModeVM.class);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showContent() {
        super.showContent();
        setEditString(getString(R.string.save));
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void onViewCreated() {
        String string;
        super.onViewCreated();
        ((BaseEditAdvancedModeVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((BaseEditAdvancedModeVM) this.mViewModel).zoneNum = getIntent().getIntExtra(Constants.ZONE_NUM, 1);
        ((BaseEditAdvancedModeVM) this.mViewModel).modeNum = getIntent().getIntExtra(Constants.MODE_NUM, 0);
        ((BaseEditAdvancedModeVM) this.mViewModel).lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, 3);
        this.initPosition = getIntent().getIntExtra(Constants.ICON_POSITION, -1);
        this.number = getIntent().getIntExtra(Constants.MODE_TIME, 0);
        ((BaseEditAdvancedModeVM) this.mViewModel).modeName = getIntent().getStringExtra(Constants.MODE_NAME);
        AppCompatTextView appCompatTextView = ((ActEditAdvancedModeBinding) this.mViewBinding).tvTimesNumber;
        if (this.number > 0) {
            string = this.number + getString(R.string.times);
        } else {
            string = getString(R.string.loop_play);
        }
        appCompatTextView.setText(string);
        ((ActEditAdvancedModeBinding) this.mViewBinding).tvDeviceName.setText(((BaseEditAdvancedModeVM) this.mViewModel).modeName);
        ((ActEditAdvancedModeBinding) this.mViewBinding).iv.setImageResource(((BaseEditAdvancedModeVM) this.mViewModel).getPicList(this).get(this.initPosition).intValue());
        this.details = getIntent().getStringExtra(Constants.MODE_DETAILS);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.edit_mode));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        test();
        ((BaseEditAdvancedModeVM) this.mViewModel).showNameDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.mode.ActEditAdvancedMode$$ExternalSyntheticLambda14
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEditAdvancedMode.this.lambda$startObserve$0((Void) obj);
            }
        });
        ((BaseEditAdvancedModeVM) this.mViewModel).showResetDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.mode.ActEditAdvancedMode$$ExternalSyntheticLambda15
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEditAdvancedMode.this.lambda$startObserve$1((Void) obj);
            }
        });
        ((BaseEditAdvancedModeVM) this.mViewModel).showIconDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.mode.ActEditAdvancedMode$$ExternalSyntheticLambda16
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEditAdvancedMode.this.lambda$startObserve$2((Void) obj);
            }
        });
        ((BaseEditAdvancedModeVM) this.mViewModel).showPlayDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.mode.ActEditAdvancedMode$$ExternalSyntheticLambda17
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEditAdvancedMode.this.lambda$startObserve$3((Void) obj);
            }
        });
        ((BaseEditAdvancedModeVM) this.mViewModel).showStopDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.mode.ActEditAdvancedMode$$ExternalSyntheticLambda18
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEditAdvancedMode.this.lambda$startObserve$4((Void) obj);
            }
        });
        ((BaseEditAdvancedModeVM) this.mViewModel).showTimeDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.mode.ActEditAdvancedMode$$ExternalSyntheticLambda19
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEditAdvancedMode.this.lambda$startObserve$5((Void) obj);
            }
        });
        Injection.repo().device().getDeviceFromDb(((BaseEditAdvancedModeVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.mode.ActEditAdvancedMode$$ExternalSyntheticLambda20
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEditAdvancedMode.this.lambda$startObserve$6((Device) obj);
            }
        });
        ((BaseEditAdvancedModeVM) this.mViewModel).paramLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.mode.ActEditAdvancedMode$$ExternalSyntheticLambda21
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEditAdvancedMode.this.lambda$startObserve$7((AdvancedModeInfo) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Void r1) {
        playStop();
        showEditNameDialog(((BaseEditAdvancedModeVM) this.mViewModel).modeNum);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r1) {
        playStop();
        resetMode(((BaseEditAdvancedModeVM) this.mViewModel).modeNum);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        playStop();
        changeModeIcon(((BaseEditAdvancedModeVM) this.mViewModel).modeNum);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r1) {
        playStop();
        playBack(((BaseEditAdvancedModeVM) this.mViewModel).modeNum);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r1) {
        playStop();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Void r2) {
        playStop();
        showPlayTimeDialog(false, ((BaseEditAdvancedModeVM) this.mViewModel).modeNum);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Device device) {
        if (((BaseEditAdvancedModeVM) this.mViewModel).controlObject == null) {
            ((BaseEditAdvancedModeVM) this.mViewModel).controlObject = device;
            onRetry();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(AdvancedModeInfo advancedModeInfo) {
        refreshContentList();
        setTotalTimeView(advancedModeInfo);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        queryModeInfo();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        showChangeDialog();
    }

    private void test() {
        showContent();
        AdvancedModeInfo advancedModeInfo = new AdvancedModeInfo();
        ArrayList arrayList = new ArrayList();
        int i = 0;
        int i2 = 2;
        this.color_num = this.details.substring(0, 2);
        String substring = this.details.substring(2, 4);
        this.loop_num = substring;
        int i3 = 16;
        this.number = Integer.parseInt(substring, 16);
        int parseInt = Integer.parseInt(this.color_num, 16);
        String substring2 = this.details.substring(4);
        while (i < parseInt) {
            AdvancedModeInfo.ContentItem contentItem = new AdvancedModeInfo.ContentItem();
            int i4 = i * 20;
            int i5 = i4 + 16;
            int i6 = i4 + 17;
            String substring3 = substring2.substring(i5, i6);
            if (substring3.startsWith("4")) {
                contentItem.setCTimeUnit(i2);
            }
            if (substring3.startsWith("8")) {
                contentItem.setCTimeUnit(3);
            }
            if (substring3.startsWith("0")) {
                contentItem.setCTimeUnit(1);
            }
            String substring4 = substring2.substring(i6, i4 + 20);
            this.ChangeTime = substring4;
            contentItem.setCTimeNum(Integer.parseInt(substring4, i3));
            int i7 = i4 + 12;
            int i8 = i4 + 13;
            String substring5 = substring2.substring(i7, i8);
            if (substring5.startsWith("4")) {
                contentItem.setTimeUnit(2);
            }
            if (substring5.startsWith("8")) {
                contentItem.setTimeUnit(3);
            }
            if (substring5.startsWith("0")) {
                contentItem.setTimeUnit(1);
            }
            int i9 = i4 + 2;
            this.Rg = substring2.substring(i4, i9);
            int i10 = i4 + 4;
            this.G = substring2.substring(i9, i10);
            int i11 = i4 + 6;
            this.B = substring2.substring(i10, i11);
            int i12 = i4 + 8;
            this.WyBrt = substring2.substring(i11, i12);
            int i13 = i4 + 10;
            this.Wy = substring2.substring(i12, i13);
            this.RgbBrt = substring2.substring(i13, i7);
            this.StopTime = substring2.substring(i8, i5);
            i3 = 16;
            contentItem.setRed(Integer.parseInt(this.Rg, 16));
            contentItem.setGreen(Integer.parseInt(this.G, 16));
            contentItem.setBlue(Integer.parseInt(this.B, 16));
            contentItem.setWyBrt(Integer.parseInt(this.WyBrt, 16));
            contentItem.setwOrWy(Integer.parseInt(this.Wy, 16));
            contentItem.setRgbBrt(Integer.parseInt(this.RgbBrt, 16));
            contentItem.setTimeNum(Integer.parseInt(this.StopTime, 16));
            contentItem.setC(Integer.valueOf(this.Wy, 16).intValue());
            arrayList.add(contentItem);
            i++;
            i2 = 2;
        }
        advancedModeInfo.setInfoList(arrayList);
        ((BaseEditAdvancedModeVM) this.mViewModel).setModeInfo(advancedModeInfo);
    }

    private void queryModeInfo() {
        showLoading();
        ((BaseEditAdvancedModeVM) this.mViewModel).queryModeInfo(this, ((BaseEditAdvancedModeVM) this.mViewModel).modeNum, new IAction() { // from class: com.ltech.smarthome.ui.mode.ActEditAdvancedMode$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEditAdvancedMode.this.lambda$queryModeInfo$8((String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryModeInfo$8(String str) {
        if (str == null) {
            showError();
            return;
        }
        showContent();
        this.details = str;
        test();
    }

    private void refreshContentList() {
        BaseItemDraggableAdapter<AdvancedModeInfo.ContentItem, BaseViewHolder> baseItemDraggableAdapter = this.mAdapter;
        if (baseItemDraggableAdapter == null) {
            SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() { // from class: com.ltech.smarthome.ui.mode.ActEditAdvancedMode$$ExternalSyntheticLambda4
                @Override // com.yanzhenjie.recyclerview.SwipeMenuCreator
                public final void onCreateMenu(SwipeMenu swipeMenu, SwipeMenu swipeMenu2, int i) {
                    ActEditAdvancedMode.this.lambda$refreshContentList$9(swipeMenu, swipeMenu2, i);
                }
            };
            ((ActEditAdvancedModeBinding) this.mViewBinding).rvContent.setFocusable(false);
            ((ActEditAdvancedModeBinding) this.mViewBinding).rvContent.setSwipeMenuCreator(swipeMenuCreator);
            ((ActEditAdvancedModeBinding) this.mViewBinding).rvContent.setOnItemMenuClickListener(new OnItemMenuClickListener() { // from class: com.ltech.smarthome.ui.mode.ActEditAdvancedMode$$ExternalSyntheticLambda5
                @Override // com.yanzhenjie.recyclerview.OnItemMenuClickListener
                public final void onItemClick(SwipeMenuBridge swipeMenuBridge, int i) {
                    ActEditAdvancedMode.this.lambda$refreshContentList$10(swipeMenuBridge, i);
                }
            });
            BaseItemDraggableAdapter<AdvancedModeInfo.ContentItem, BaseViewHolder> baseItemDraggableAdapter2 = new BaseItemDraggableAdapter<AdvancedModeInfo.ContentItem, BaseViewHolder>(R.layout.item_advanced_mode_color_time, ((BaseEditAdvancedModeVM) this.mViewModel).getModeInfo().getInfoList()) { // from class: com.ltech.smarthome.ui.mode.ActEditAdvancedMode.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, AdvancedModeInfo.ContentItem item) {
                    helper.setVisible(R.id.line_top, helper.getAdapterPosition() != 0).setVisible(R.id.line_bottom, helper.getAdapterPosition() != ActEditAdvancedMode.this.mAdapter.getData().size() - 1);
                    helper.setText(R.id.tv_time_tip1, R.string.gradual_time_1).setText(R.id.tv_time1, AdvancedModeHelper.getTimeString(item.getCTime())).setVisible(R.id.tv_num1, false).setVisible(R.id.multiColorView1, false).setVisible(R.id.iv_time1, true).setImageResource(R.id.iv_time1, R.mipmap.icon_fade_time).addOnClickListener(R.id.view261);
                    helper.setText(R.id.tv_time_tip, R.string.stay_time).setText(R.id.tv_time, AdvancedModeHelper.getTimeString(item.getTime())).setVisible(R.id.tv_num, true).setVisible(R.id.multiColorView, true).setVisible(R.id.iv_time, false).addOnClickListener(R.id.view26);
                    MultiColorView multiColorView = (MultiColorView) helper.getView(R.id.multiColorView);
                    int i = ((BaseEditAdvancedModeVM) ActEditAdvancedMode.this.mViewModel).lightType;
                    if (i == 1) {
                        helper.setGone(R.id.tv_num, true);
                        helper.setText(R.id.tv_num, LightUtils.getProgressHasBelowOne(LightUtils.brt2ProgressHasBelowZero(item.getWyBrt())));
                        return;
                    }
                    if (i == 2) {
                        helper.setGone(R.id.tv_num, false);
                        multiColorView.setColorArray(new int[]{Color.rgb(ColorArray.CT_COLOR_ARRAY[item.getC()][0], ColorArray.CT_COLOR_ARRAY[item.getC()][1], ColorArray.CT_COLOR_ARRAY[item.getC()][2])});
                        return;
                    }
                    if (i == 3) {
                        helper.setGone(R.id.tv_num, false);
                        multiColorView.setColorArray(new int[]{Color.rgb(item.getRed(), item.getGreen(), item.getBlue())});
                    } else if (i == 4) {
                        helper.setGone(R.id.tv_num, false);
                        multiColorView.setColorArray(new int[]{LightUtils.getHsvColor(item.getRed(), item.getGreen(), item.getBlue(), item.getwOrWy())});
                    } else {
                        if (i != 5) {
                            return;
                        }
                        helper.setGone(R.id.tv_num, false);
                        multiColorView.setColorArray(new int[]{Color.rgb(item.getRed(), item.getGreen(), item.getBlue()), Color.rgb((ColorArray.CT_COLOR_ARRAY[item.getwOrWy()][0] * item.getWyBrt()) / 255, (ColorArray.CT_COLOR_ARRAY[item.getwOrWy()][1] * item.getWyBrt()) / 255, (ColorArray.CT_COLOR_ARRAY[item.getwOrWy()][2] * item.getWyBrt()) / 255)});
                    }
                }
            };
            this.mAdapter = baseItemDraggableAdapter2;
            baseItemDraggableAdapter2.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.mode.ActEditAdvancedMode$$ExternalSyntheticLambda6
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
                public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                    ActEditAdvancedMode.this.lambda$refreshContentList$11(baseQuickAdapter, view, i);
                }
            });
            this.mAdapter.setOnItemDragListener(new OnItemDragListener() { // from class: com.ltech.smarthome.ui.mode.ActEditAdvancedMode.2
                @Override // com.chad.library.adapter.base.listener.OnItemDragListener
                public void onItemDragMoving(RecyclerView.ViewHolder viewHolder, int from, RecyclerView.ViewHolder viewHolder1, int to) {
                }

                @Override // com.chad.library.adapter.base.listener.OnItemDragListener
                public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int from) {
                    ActEditAdvancedMode.this.start = from;
                }

                @Override // com.chad.library.adapter.base.listener.OnItemDragListener
                public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int to) {
                    ActEditAdvancedMode.this.playStop();
                    ActEditAdvancedMode.this.mAdapter.notifyItemRangeChanged(Math.min(ActEditAdvancedMode.this.start, to), Math.abs(to - ActEditAdvancedMode.this.start) + 1);
                }
            });
            this.mAdapter.bindToRecyclerView(((ActEditAdvancedModeBinding) this.mViewBinding).rvContent);
            ((ActEditAdvancedModeBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
            ((ActEditAdvancedModeBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
            ((DefaultItemAnimator) ((ActEditAdvancedModeBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
            ItemAdvancedModeAddBinding itemAdvancedModeAddBinding = (ItemAdvancedModeAddBinding) DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_advanced_mode_add, (ViewGroup) ((ActEditAdvancedModeBinding) this.mViewBinding).rvContent.getParent(), false);
            itemAdvancedModeAddBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.mode.ActEditAdvancedMode$$ExternalSyntheticLambda7
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    ActEditAdvancedMode.this.lambda$refreshContentList$12((View) obj);
                }
            }));
            ((ActEditAdvancedModeBinding) this.mViewBinding).rvContent.addFooterView(itemAdvancedModeAddBinding.getRoot());
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemDragAndSwipeCallback(this.mAdapter));
            itemTouchHelper.attachToRecyclerView(((ActEditAdvancedModeBinding) this.mViewBinding).rvContent);
            this.mAdapter.enableDragItem(itemTouchHelper);
            return;
        }
        baseItemDraggableAdapter.setNewData(((BaseEditAdvancedModeVM) this.mViewModel).getModeInfo().getInfoList());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshContentList$9(SwipeMenu swipeMenu, SwipeMenu swipeMenu2, int i) {
        swipeMenu2.addMenuItem(new SwipeMenuItem(this).setTextSize(14).setWidth(SizeUtils.dp2px(60.0f)).setHeight(SizeUtils.dp2px(125.0f)).setText(R.string.delete).setTextColor(-1).setBackgroundColorResource(R.color.color_text_red));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshContentList$10(SwipeMenuBridge swipeMenuBridge, int i) {
        swipeMenuBridge.closeMenu();
        if (-1 == swipeMenuBridge.getDirection() && swipeMenuBridge.getPosition() == 0) {
            playStop();
            ((BaseEditAdvancedModeVM) this.mViewModel).getModeInfo().getInfoList().remove(i);
            ((BaseEditAdvancedModeVM) this.mViewModel).paramLiveData.setValue(((BaseEditAdvancedModeVM) this.mViewModel).getModeInfo());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshContentList$11(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        playStop();
        switch (view.getId()) {
            case R.id.view26 /* 2131299189 */:
                addColor(i);
                break;
            case R.id.view261 /* 2131299190 */:
                showGradualTimeDialog(true, i);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshContentList$12(View view) {
        addColor(-1);
    }

    private void addColor(int position) {
        NavUtils.Builder destination;
        if (this.mAdapter.getData().size() >= 8 && position == -1) {
            SmartToast.showShort("最多输入8组数据");
            return;
        }
        int i = ((BaseEditAdvancedModeVM) this.mViewModel).lightType;
        if (i == 1 || i == 2) {
            destination = NavUtils.destination(ActAddModeCtDim.class);
        } else {
            destination = (i == 3 || i == 4 || i == 5) ? NavUtils.destination(ActAddModeColor.class) : null;
        }
        if (destination != null) {
            ((BaseEditAdvancedModeVM) this.mViewModel).clickPosition = position;
            destination.withInt(Constants.LIGHT_TYPE, ((BaseEditAdvancedModeVM) this.mViewModel).lightType).withParcelable(Constants.MODE_ITEM_INFO, position != -1 ? this.mAdapter.getData().get(position) : null).withDefaultRequestCode().navigation(this);
        }
    }

    private void showGradualTimeDialog(final boolean edit, final int position) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        for (int i = 0; i < 25; i++) {
            if (i == 0) {
                arrayList4.add("000");
            } else {
                int i2 = i * 40;
                if (i2 < 100) {
                    arrayList4.add("0" + i2);
                } else {
                    arrayList4.add(Integer.toString(i2));
                }
            }
        }
        for (int i3 = 0; i3 < 60; i3++) {
            if (i3 < 10) {
                arrayList2.add("0" + i3);
                arrayList3.add("0" + i3);
            } else {
                arrayList2.add(Integer.toString(i3));
                arrayList3.add(Integer.toString(i3));
            }
        }
        for (int i4 = 0; i4 < 24; i4++) {
            if (i4 < 10) {
                arrayList.add("0" + i4);
            } else {
                arrayList.add(Integer.toString(i4));
            }
        }
        MsTimeSelectDialog.asDefault().setTitle(getString(R.string.add_gradual_time)).setHourList(arrayList).setMinList(arrayList2).setSecList(arrayList3).setMsList(arrayList4).withUnit(true).setHourUnit(getString(R.string.hour)).setMinUnit(getString(R.string.min)).setSecUnit(getString(R.string.sec)).setMsUnit(getString(R.string.ms)).setSelectHourPosition(edit ? AdvancedModeHelper.getHourPos(this.mAdapter.getData().get(position).getCTime()) : 0).setSelectMinPosition(edit ? AdvancedModeHelper.getMinPos(this.mAdapter.getData().get(position).getCTime()) : 0).setSelectSecPosition(edit ? AdvancedModeHelper.getSecPos(this.mAdapter.getData().get(position).getCTime()) : 0).setSelectMsPosition(edit ? AdvancedModeHelper.getMsPos(this.mAdapter.getData().get(position).getCTime()) : 0).setSelectListener(new MsTimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.mode.ActEditAdvancedMode$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.dialog.MsTimeSelectDialog.SelectListener
            public final void confirm(int i5, int i6, int i7, int i8) {
                ActEditAdvancedMode.this.lambda$showGradualTimeDialog$13(edit, position, i5, i6, i7, i8);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showGradualTimeDialog$13(boolean z, int i, int i2, int i3, int i4, int i5) {
        int calculateTotalTime = AdvancedModeHelper.calculateTotalTime(i2, i3, i4, i5);
        if (z) {
            ((BaseEditAdvancedModeVM) this.mViewModel).getModeInfo().getInfoList().get(i).setCTime(calculateTotalTime);
            ((BaseEditAdvancedModeVM) this.mViewModel).paramLiveData.setValue(((BaseEditAdvancedModeVM) this.mViewModel).getModeInfo());
        }
    }

    private void setTotalTimeView(AdvancedModeInfo advancedModeInfo) {
        this.totalTime = 0;
        for (AdvancedModeInfo.ContentItem contentItem : advancedModeInfo.getInfoList()) {
            this.totalTime += contentItem.getTime() + contentItem.getCTime();
        }
        ((ActEditAdvancedModeBinding) this.mViewBinding).tvTotalTime.setText(AdvancedModeHelper.getTotalTimeString(this.totalTime));
        ((ActEditAdvancedModeBinding) this.mViewBinding).tvTotalTime1.setText(AdvancedModeHelper.getTotalTimeString(this.totalTime));
        if (LanguageUtils.isRussian(this)) {
            ((ActEditAdvancedModeBinding) this.mViewBinding).tvTotalTime.setVisibility(8);
        }
    }

    private void showPlayTimeDialog(boolean playList, int position) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i <= 255; i++) {
            if (i == 0) {
                arrayList.add(getString(R.string.loop_play));
            } else {
                arrayList.add(i + getString(R.string.times));
            }
        }
        TimeSelectDialog.asDefault().setTitle(getString(R.string.set_play_times)).setMinList(arrayList).setSelectMinPosition(playList ? ((BaseEditAdvancedModeVM) this.mViewModel).listPlayTime.getValue().intValue() : this.number).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.mode.ActEditAdvancedMode$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i2, int i3) {
                ActEditAdvancedMode.this.lambda$showPlayTimeDialog$14(i2, i3);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPlayTimeDialog$14(int i, int i2) {
        String string;
        this.number = i;
        String addZeroForNum = StringUtils.addZeroForNum(Integer.toHexString(i), 2);
        StringBuilder sb = new StringBuilder(this.details);
        sb.replace(2, 4, addZeroForNum);
        this.details = sb.toString();
        AppCompatTextView appCompatTextView = ((ActEditAdvancedModeBinding) this.mViewBinding).tvTimesNumber;
        if (i > 0) {
            string = i + getString(R.string.times);
        } else {
            string = getString(R.string.loop_play);
        }
        appCompatTextView.setText(string);
        Intent intent = new Intent();
        intent.putExtra(Constants.MODE_NUM, i);
        setResult(3007, intent);
    }

    private void changeModeIcon(int position) {
        ((BaseEditAdvancedModeVM) this.mViewModel).clickPosition = position;
        NavUtils.destination(ActSelectModeCover.class).withInt(Constants.ICON_POSITION, this.initPosition).withLong(Constants.CONTROL_ID, ((BaseEditAdvancedModeVM) this.mViewModel).controlId).withInt(Constants.ZONE_NUM, ((BaseEditAdvancedModeVM) this.mViewModel).zoneNum).withInt(Constants.MODE_NUM, position).withDefaultRequestCode().navigation(this);
    }

    private void playBack(int position) {
        detailsList();
        test();
        NavUtils.destination(ActAddBleMode.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withInt(Constants.LIGHT_TYPE, ((BaseEditAdvancedModeVM) this.mViewModel).lightType).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playStop() {
        if (this.previewing) {
            if (((BaseEditAdvancedModeVM) this.mViewModel).controlObject != null) {
                ((BaseEditAdvancedModeVM) this.mViewModel).pausePreview(this, ((BaseEditAdvancedModeVM) this.mViewModel).modeNum, ((BaseEditAdvancedModeVM) this.mViewModel).getModeInfo().getInfoList());
                ((ActEditAdvancedModeBinding) this.mViewBinding).tvPreview.setVisibility(0);
                ((ActEditAdvancedModeBinding) this.mViewBinding).vPreview3.setVisibility(0);
                ((ActEditAdvancedModeBinding) this.mViewBinding).vPreview3Off.setVisibility(8);
                ((ActEditAdvancedModeBinding) this.mViewBinding).tvPreview1.setVisibility(8);
            }
            CountDownTimer countDownTimer = this.countDownTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
                this.countDownTimer = null;
                this.previewing = false;
            }
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (((BaseEditAdvancedModeVM) this.mViewModel).controlObject != null) {
            ((BaseEditAdvancedModeVM) this.mViewModel).pausePreview(this, ((BaseEditAdvancedModeVM) this.mViewModel).modeNum, ((BaseEditAdvancedModeVM) this.mViewModel).getModeInfo().getInfoList());
            HelpUtils.threadSleep(200);
        }
        CountDownTimer countDownTimer = this.countDownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.countDownTimer = null;
        }
        super.back();
    }

    public void detailsList() {
        String addZeroForNum = StringUtils.addZeroForNum(Integer.toHexString(((BaseEditAdvancedModeVM) this.mViewModel).getModeInfo().getInfoList().size()), 2);
        StringBuilder sb = new StringBuilder(this.details);
        sb.replace(0, 2, addZeroForNum);
        this.details = sb.toString();
        for (int i = 0; i < ((BaseEditAdvancedModeVM) this.mViewModel).getModeInfo().getInfoList().size(); i++) {
            int c2 = ((BaseEditAdvancedModeVM) this.mViewModel).getModeInfo().getInfoList().get(i).getC();
            int c3 = 255 - ((BaseEditAdvancedModeVM) this.mViewModel).getModeInfo().getInfoList().get(i).getC();
            StringUtils.addZeroForNum(Integer.toHexString(c2), 2);
            StringUtils.addZeroForNum(Integer.toHexString(c3), 2);
            int red = ((BaseEditAdvancedModeVM) this.mViewModel).getModeInfo().getInfoList().get(i).getRed();
            int blue = ((BaseEditAdvancedModeVM) this.mViewModel).getModeInfo().getInfoList().get(i).getBlue();
            int green = ((BaseEditAdvancedModeVM) this.mViewModel).getModeInfo().getInfoList().get(i).getGreen();
            int i2 = ((BaseEditAdvancedModeVM) this.mViewModel).getModeInfo().getInfoList().get(i).getwOrWy();
            int wyBrt = ((BaseEditAdvancedModeVM) this.mViewModel).getModeInfo().getInfoList().get(i).getWyBrt();
            int rgbBrt = ((BaseEditAdvancedModeVM) this.mViewModel).getModeInfo().getInfoList().get(i).getRgbBrt();
            int changeTime = ((BaseEditAdvancedModeVM) this.mViewModel).getModeInfo().getInfoList().get(i).getChangeTime();
            String str = StringUtils.addZeroForNum(Integer.toHexString(red), 2) + StringUtils.addZeroForNum(Integer.toHexString(green), 2) + StringUtils.addZeroForNum(Integer.toHexString(blue), 2) + StringUtils.addZeroForNum(Integer.toHexString(wyBrt), 2) + StringUtils.addZeroForNum(Integer.toHexString(i2), 2) + StringUtils.addZeroForNum(Integer.toHexString(rgbBrt), 2) + StringUtils.addZeroForNum(Integer.toHexString(changeTime), 4);
            StringBuilder sb2 = new StringBuilder(this.details);
            int i3 = i * 20;
            int i4 = i3 + 20;
            sb2.replace(i3 + 4, i4, str);
            this.details = sb2.toString();
            String addZeroForNum2 = StringUtils.addZeroForNum(Integer.toHexString(((BaseEditAdvancedModeVM) this.mViewModel).getModeInfo().getInfoList().get(i).getCChangeTime()), 4);
            StringBuilder sb3 = new StringBuilder(this.details);
            sb3.replace(i4, i3 + 24, addZeroForNum2);
            this.details = sb3.toString();
        }
    }

    private void showChangeDialog() {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.save_change_tip)).setOkButton(getString(R.string.save), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.mode.ActEditAdvancedMode$$ExternalSyntheticLambda12
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showChangeDialog$16;
                lambda$showChangeDialog$16 = ActEditAdvancedMode.this.lambda$showChangeDialog$16(baseDialog, view);
                return lambda$showChangeDialog$16;
            }
        }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.mode.ActEditAdvancedMode$$ExternalSyntheticLambda13
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return ActEditAdvancedMode.lambda$showChangeDialog$17(baseDialog, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showChangeDialog$16(BaseDialog baseDialog, View view) {
        detailsList();
        ((ObservableSubscribeProxy) Injection.net().addMode(((BaseEditAdvancedModeVM) this.mViewModel).modeName, Injection.repo().home().getSelectPlace().getValue().getPlaceId(), ((BaseEditAdvancedModeVM) this.mViewModel).lightType, 2, ((BaseEditAdvancedModeVM) this.mViewModel).modeNum, this.initPosition, this.details, "", this.number).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.mode.ActEditAdvancedMode$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActEditAdvancedMode.this.lambda$showChangeDialog$15(obj);
            }
        }, new SmartErrorComsumer());
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showChangeDialog$15(Object obj) throws Exception {
        back();
    }

    private void resetMode(final int position) {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.reset_mode_tip)).setOkButton(getString(R.string.reset), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.mode.ActEditAdvancedMode$$ExternalSyntheticLambda2
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$resetMode$19;
                lambda$resetMode$19 = ActEditAdvancedMode.this.lambda$resetMode$19(position, baseDialog, view);
                return lambda$resetMode$19;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$resetMode$19(int i, BaseDialog baseDialog, View view) {
        ((ObservableSubscribeProxy) Injection.net().deleteMode(Injection.repo().home().getSelectPlace().getValue().getPlaceId(), ((BaseEditAdvancedModeVM) this.mViewModel).lightType, 2, i).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.mode.ActEditAdvancedMode$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActEditAdvancedMode.this.lambda$resetMode$18(obj);
            }
        }, new SmartErrorComsumer());
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resetMode$18(Object obj) throws Exception {
        SmartToast.showShort(getString(R.string.app_str_reset_successful));
        Injection.limiter().reset(Injection.keyCreator().modeKey(Injection.repo().home().getSelectPlace().getValue().getPlaceId()));
        setResult(3017);
        back();
    }

    private void showEditNameDialog(int position) {
        EditDialog.asDefault().setContent(((ActEditAdvancedModeBinding) this.mViewBinding).tvDeviceName.getText().toString()).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.mode.ActEditAdvancedMode$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEditAdvancedMode.this.lambda$showEditNameDialog$20((String) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$20(String str) {
        ((BaseEditAdvancedModeVM) this.mViewModel).modeName = str;
        ((ActEditAdvancedModeBinding) this.mViewBinding).tvDeviceName.setText(str);
        Intent intent = new Intent();
        intent.putExtra(Constants.MODE_NAME, str);
        setResult(3006, intent);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3008 == resultCode && data != null) {
            long longExtra = data.getLongExtra("device_id", -1L);
            ((BaseEditAdvancedModeVM) this.mViewModel).controlObject = null;
            Injection.repo().device().getDeviceFromDb(longExtra).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.mode.ActEditAdvancedMode$$ExternalSyntheticLambda9
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActEditAdvancedMode.this.lambda$onActivityResult$21((Device) obj);
                }
            });
        }
        if (resultCode == 6001 && data != null) {
            if (((BaseEditAdvancedModeVM) this.mViewModel).clickPosition != -1) {
                ((BaseEditAdvancedModeVM) this.mViewModel).getModeInfo().getInfoList().set(((BaseEditAdvancedModeVM) this.mViewModel).clickPosition, (AdvancedModeInfo.ContentItem) data.getParcelableExtra(Constants.MODE_ITEM_INFO));
                ((BaseEditAdvancedModeVM) this.mViewModel).paramLiveData.setValue(((BaseEditAdvancedModeVM) this.mViewModel).getModeInfo());
            } else {
                int calculateTotalTime = AdvancedModeHelper.calculateTotalTime(0, 0, 0, 0);
                new AdvancedModeInfo.ContentItem();
                AdvancedModeInfo.ContentItem contentItem = (AdvancedModeInfo.ContentItem) data.getParcelableExtra(Constants.MODE_ITEM_INFO);
                contentItem.setCTime(calculateTotalTime);
                ((BaseEditAdvancedModeVM) this.mViewModel).getModeInfo().getInfoList().add(contentItem);
            }
            ((BaseEditAdvancedModeVM) this.mViewModel).paramLiveData.setValue(((BaseEditAdvancedModeVM) this.mViewModel).getModeInfo());
        }
        if (3002 == resultCode && data != null) {
            this.initPosition = data.getIntExtra(Constants.ICON_POSITION, 0);
            ((ActEditAdvancedModeBinding) this.mViewBinding).iv.setImageResource(((BaseEditAdvancedModeVM) this.mViewModel).getPicList(this).get(this.initPosition).intValue());
            Intent intent = new Intent();
            intent.putExtra(Constants.ICON_POSITION, this.initPosition);
            setResult(3002, intent);
        }
        if (resultCode != 6002 || data == null) {
            return;
        }
        this.details = data.getStringExtra(Constants.MODE_DATA);
        test();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onActivityResult$21(Device device) {
        if (((BaseEditAdvancedModeVM) this.mViewModel).controlObject == null) {
            ((BaseEditAdvancedModeVM) this.mViewModel).controlObject = device;
            showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.send_to_device));
            ((BaseEditAdvancedModeVM) this.mViewModel).previewModeInfo(this, ((BaseEditAdvancedModeVM) this.mViewModel).modeNum, this.number, ((BaseEditAdvancedModeVM) this.mViewModel).getModeInfo().getInfoList());
            ((ActEditAdvancedModeBinding) this.mViewBinding).tvPreview.setVisibility(8);
            ((ActEditAdvancedModeBinding) this.mViewBinding).vPreview3.setVisibility(8);
            ((ActEditAdvancedModeBinding) this.mViewBinding).vPreview3Off.setVisibility(0);
            ((ActEditAdvancedModeBinding) this.mViewBinding).tvPreview1.setVisibility(0);
            PreviewCountDownTimer previewCountDownTimer = new PreviewCountDownTimer(this.totalTime, 1L);
            this.countDownTimer = previewCountDownTimer;
            previewCountDownTimer.start();
            this.previewing = true;
            dismissLoadingDialog();
        }
    }

    private class PreviewCountDownTimer extends CountDownTimer {
        public PreviewCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override // android.os.CountDownTimer
        public void onTick(long millisUntilFinished) {
            if (ActEditAdvancedMode.this.mViewBinding != null) {
                ((ActEditAdvancedModeBinding) ActEditAdvancedMode.this.mViewBinding).tvTotalTime1.setText(AdvancedModeHelper.getTotalTimeString((int) millisUntilFinished));
            }
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            if (ActEditAdvancedMode.this.mViewBinding != null) {
                ((ActEditAdvancedModeBinding) ActEditAdvancedMode.this.mViewBinding).tvTotalTime1.setText(AdvancedModeHelper.getTotalTimeString(ActEditAdvancedMode.this.totalTime));
            }
            ActEditAdvancedMode.this.playStop();
        }
    }
}