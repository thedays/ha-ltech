package com.ltech.smarthome.ui.device.cg485;

import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActCg485OldBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.Rs485ExtParam;
import com.ltech.smarthome.model.device_param.SuperPanelExtParam;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.cg485.ActCg485Old;
import com.ltech.smarthome.ui.device.cg485.Cg485Helper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.AddCg485CategoryDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActCg485Old extends BaseControlActivity<ActCg485OldBinding, ActCg485VM> {
    private static final int TYPE_485_TO_BT = 2;
    private static final int TYPE_BT_TO_485 = 1;
    private BaseQuickAdapter<Rs485ExtParam.Category, BaseViewHolder> mAdapter;
    private int curType = 1;
    private List<Rs485ExtParam.Category> commandList = new ArrayList();
    private SingleLiveEvent<Void> refreshList = new SingleLiveEvent<>();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.rv_content;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_cg_485_old;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        ((ActCg485VM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        initAdapter();
        ((ActCg485OldBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485Old$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActCg485Old.this.lambda$initView$1((View) obj);
            }
        }));
        ((ActCg485OldBinding) this.mViewBinding).tvBle.setTypeface(Typeface.defaultFromStyle(1));
        ((ActCg485OldBinding) this.mViewBinding).layoutBottom.setVisibility(isOwnerOrManager() ? 0 : 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view) {
        int id = view.getId();
        if (id == R.id.tv_485) {
            this.curType = 2;
            changeTab();
        } else if (id == R.id.tv_ble) {
            this.curType = 1;
            changeTab();
        } else {
            if (id != R.id.tv_bottom) {
                return;
            }
            AddCg485CategoryDialog.asDefault().setSelectPosition(0).setType(this.curType).setOnSaveListener(new AddCg485CategoryDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485Old$$ExternalSyntheticLambda5
                @Override // com.ltech.smarthome.view.dialog.AddCg485CategoryDialog.OnSaveListener
                public final void onSave(String str, int i, int i2) {
                    ActCg485Old.this.lambda$initView$0(str, i, i2);
                }
            }).showDialog(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(String str, int i, int i2) {
        Rs485ExtParam.Category category = new Rs485ExtParam.Category();
        category.setCategoryName(str);
        category.setType(i);
        category.setColorIdx(i2);
        Cg485Helper.addCategory(i, category);
        if (i == 1) {
            NavUtils.destination(ActAddInstruct.class).navigation(this);
        } else {
            NavUtils.destination(ActEditInstructCmd.class).withInt(Constants.COMMAND_TYPE, i).navigation(this);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.no_instruct));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActCg485VM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485Old$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCg485Old.this.lambda$startObserve$2((Device) obj);
            }
        });
        this.refreshList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485Old$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCg485Old.this.lambda$startObserve$3((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Device device) {
        setTitle(device.getDeviceName());
        ((ActCg485VM) this.mViewModel).extParam = (SuperPanelExtParam) device.getExtParam(SuperPanelExtParam.class);
        if (((ActCg485VM) this.mViewModel).extParam == null) {
            ((ActCg485VM) this.mViewModel).extParam = new SuperPanelExtParam();
        }
        Cg485Helper.extParam = ((ActCg485VM) this.mViewModel).extParam;
        if (this.curType == 1) {
            this.commandList = ((ActCg485VM) this.mViewModel).extParam.getTo485List();
        } else {
            this.commandList = ((ActCg485VM) this.mViewModel).extParam.getToBleList();
        }
        this.refreshList.call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r2) {
        if (this.commandList.size() == 0) {
            showEmpty();
        } else {
            showContent();
            this.mAdapter.replaceData(this.commandList);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        NavHelper.goSetting(((ActCg485VM) this.mViewModel).controlObject.getValue());
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        startObjectObserve();
    }

    private void startObjectObserve() {
        Device deviceById = Injection.repo().device().getDeviceById(((ActCg485VM) this.mViewModel).controlId);
        if (deviceById != null) {
            ((ActCg485VM) this.mViewModel).controlObject.setValue(deviceById);
            Cg485Helper.controlDevice = ((ActCg485VM) this.mViewModel).controlObject.getValue();
        }
    }

    private void changeTab() {
        AppCompatTextView appCompatTextView = ((ActCg485OldBinding) this.mViewBinding).tvBle;
        int i = this.curType;
        int i2 = R.drawable.shape_white_round_bg_10;
        appCompatTextView.setBackgroundResource(i == 1 ? R.drawable.shape_white_round_bg_10 : 0);
        ((ActCg485OldBinding) this.mViewBinding).tvBle.setTypeface(this.curType == 1 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
        AppCompatTextView appCompatTextView2 = ((ActCg485OldBinding) this.mViewBinding).tv485;
        if (this.curType != 2) {
            i2 = 0;
        }
        appCompatTextView2.setBackgroundResource(i2);
        ((ActCg485OldBinding) this.mViewBinding).tv485.setTypeface(this.curType == 2 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
        if (this.curType == 1) {
            this.commandList = ((ActCg485VM) this.mViewModel).extParam.getTo485List();
        } else {
            this.commandList = ((ActCg485VM) this.mViewModel).extParam.getToBleList();
        }
        this.refreshList.call();
    }

    /* renamed from: com.ltech.smarthome.ui.device.cg485.ActCg485Old$1, reason: invalid class name */
    class AnonymousClass1 extends BaseQuickAdapter<Rs485ExtParam.Category, BaseViewHolder> {
        AnonymousClass1(int layoutResId, List data) {
            super(layoutResId, data);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(final BaseViewHolder helper, Rs485ExtParam.Category item) {
            int[] intArray = ActCg485Old.this.getResources().getIntArray(R.array.instruct_color);
            ((CardView) helper.getView(R.id.card_title)).setCardBackgroundColor(intArray[item.getColorIdx()]);
            helper.setText(R.id.tv_name, item.getCategoryName()).setTextColor(R.id.tv_name, intArray[item.getColorIdx()]).setGone(R.id.rv_content, item.getAction().size() != 0);
            RecyclerView recyclerView = (RecyclerView) helper.getView(R.id.rv_content);
            BaseQuickAdapter instructAdapter = ActCg485Old.this.getInstructAdapter(item.getType(), helper.getBindingAdapterPosition());
            recyclerView.setAdapter(instructAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(ActCg485Old.this.activity, 2));
            recyclerView.setHasFixedSize(true);
            if (recyclerView.getItemDecorationCount() == 0) {
                recyclerView.addItemDecoration(((ActCg485VM) ActCg485Old.this.mViewModel).decoration);
            }
            recyclerView.setOnTouchListener(new View.OnTouchListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485Old$1$$ExternalSyntheticLambda0
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    boolean lambda$convert$0;
                    lambda$convert$0 = ActCg485Old.AnonymousClass1.this.lambda$convert$0(helper, view, motionEvent);
                    return lambda$convert$0;
                }
            });
            instructAdapter.replaceData(item.getAction());
            helper.setGone(R.id.tv_add, ActCg485Old.this.isOwnerOrManager());
            helper.getView(R.id.tv_add).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485Old$1$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ActCg485Old.AnonymousClass1.this.lambda$convert$1(helper, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$convert$0(BaseViewHolder baseViewHolder, View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() != 1) {
                return false;
            }
            Cg485Helper.categoryPosition = baseViewHolder.getBindingAdapterPosition();
            NavUtils.destination(ActCommandCategory.class).withInt(Constants.COMMAND_TYPE, ActCg485Old.this.curType).withDefaultRequestCode().navigation(ActCg485Old.this.activity);
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$1(BaseViewHolder baseViewHolder, View view) {
            Cg485Helper.categoryPosition = baseViewHolder.getBindingAdapterPosition();
            if (ActCg485Old.this.curType == 1) {
                NavUtils.destination(ActAddInstruct.class).withInt(Constants.COMMAND_TYPE, ActCg485Old.this.curType).navigation(ActCg485Old.this.activity);
            } else if (Cg485Helper.get485InstructTotal() < 255) {
                NavUtils.destination(ActEditInstructCmd.class).withInt(Constants.COMMAND_TYPE, ActCg485Old.this.curType).navigation(ActCg485Old.this.activity);
            } else {
                SmartToast.showShort(R.string.max_cmd_tip);
            }
        }
    }

    private void initAdapter() {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(R.layout.item_command, new ArrayList());
        this.mAdapter = anonymousClass1;
        anonymousClass1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485Old$$ExternalSyntheticLambda6
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActCg485Old.this.lambda$initAdapter$4(baseQuickAdapter, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActCg485OldBinding) this.mViewBinding).rvContent);
        ((ActCg485OldBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActCg485OldBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((ActCg485OldBinding) this.mViewBinding).rvContent.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485Old.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(SizeUtils.dp2px(5.0f), SizeUtils.dp2px(5.0f), SizeUtils.dp2px(5.0f), SizeUtils.dp2px(5.0f));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAdapter$4(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        Cg485Helper.categoryPosition = i;
        NavUtils.destination(ActCommandCategory.class).withInt(Constants.COMMAND_TYPE, this.curType).withDefaultRequestCode().navigation(this.activity);
    }

    /* renamed from: com.ltech.smarthome.ui.device.cg485.ActCg485Old$3, reason: invalid class name */
    class AnonymousClass3 extends BaseQuickAdapter<Rs485ExtParam.Instruct, BaseViewHolder> {
        final /* synthetic */ int val$categoryPos;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(int layoutResId, List data, final int val$categoryPos) {
            super(layoutResId, data);
            this.val$categoryPos = val$categoryPos;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(final BaseViewHolder helper, Rs485ExtParam.Instruct instruct) {
            helper.setText(R.id.tv_name, instruct.getActionName()).setText(R.id.tv_cmd, instruct.getCmd());
            View view = helper.getView(R.id.iv_edit);
            final int i = this.val$categoryPos;
            view.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485Old$3$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    ActCg485Old.AnonymousClass3.this.lambda$convert$0(i, helper, view2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(int i, BaseViewHolder baseViewHolder, View view) {
            Cg485Helper.categoryPosition = i;
            Cg485Helper.instructPosition = baseViewHolder.getBindingAdapterPosition();
            NavUtils.destination(ActInstructSetting.class).withInt(Constants.COMMAND_TYPE, 1).withDefaultRequestCode().navigation(ActCg485Old.this.activity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BaseQuickAdapter<Rs485ExtParam.Instruct, BaseViewHolder> getInstructAdapter(int type, final int categoryPos) {
        if (type == 1) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(R.layout.item_ble_to_485, new ArrayList(), categoryPos);
            anonymousClass3.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485Old$$ExternalSyntheticLambda2
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                    ActCg485Old.this.lambda$getInstructAdapter$6(categoryPos, baseQuickAdapter, view, i);
                }
            });
            return anonymousClass3;
        }
        BaseQuickAdapter<Rs485ExtParam.Instruct, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Rs485ExtParam.Instruct, BaseViewHolder>(R.layout.item_485_to_ble, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485Old.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Rs485ExtParam.Instruct instruct) {
                Cg485Helper.ContentState relateAction = Cg485Helper.getRelateAction(ActCg485Old.this.activity, instruct.getBindAction());
                if (relateAction.rgbColor != Integer.MIN_VALUE) {
                    helper.getView(R.id.civ_color).setVisibility(0);
                    ((CircleImageView) helper.getView(R.id.civ_color)).setImageDrawable(new ColorDrawable(relateAction.rgbColor));
                } else {
                    helper.getView(R.id.civ_color).setVisibility(8);
                }
                helper.setImageResource(R.id.iv_icon, relateAction.iconRes).setText(R.id.tv_name, TextUtils.isEmpty(relateAction.name) ? ActCg485Old.this.getString(R.string.no_bind_object) : relateAction.name).setText(R.id.tv_action, relateAction.action).setText(R.id.tv_cmd, instruct.getCmd());
            }
        };
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485Old$$ExternalSyntheticLambda3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActCg485Old.this.lambda$getInstructAdapter$7(categoryPos, baseQuickAdapter2, view, i);
            }
        });
        return baseQuickAdapter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getInstructAdapter$6(int i, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        showLoadingDialog("");
        Cg485Helper.runInstruct(this, this.commandList.get(i).getAction().get(i2).getCmd(), this.commandList.get(i).getAction().get(i2).getDataFormat(), new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485Old$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActCg485Old.this.lambda$getInstructAdapter$5((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getInstructAdapter$5(Boolean bool) {
        if (bool.booleanValue()) {
            showSuccessDialog(getString(R.string.send_to_device_success));
        } else {
            showErrorDialog(getString(R.string.send_to_device_fail));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getInstructAdapter$7(int i, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        Cg485Helper.categoryPosition = i;
        Cg485Helper.instructPosition = i2;
        NavUtils.destination(ActInstructSetting.class).withInt(Constants.COMMAND_TYPE, 2).withDefaultRequestCode().navigation(this.activity);
    }
}