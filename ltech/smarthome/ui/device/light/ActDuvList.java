package com.ltech.smarthome.ui.device.light;

import android.content.Intent;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActDuvListBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.ModeContent;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.qrcode.ActQrCodeScanDuv;
import com.ltech.smarthome.ui.qrcode.ActShareDuvList;
import com.ltech.smarthome.ui.qrcode.ShareHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.ltech.smarthome.view.popup.SingleSelectPopup;
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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActDuvList extends BaseNormalActivity<ActDuvListBinding> {
    private Device device;
    private boolean editMode;
    private BaseItemDraggableAdapter<ModeContent, BaseViewHolder> mAdapter;
    private List<ModeContent> modeList = new ArrayList();
    private Listing<ModeContent> request;

    static /* synthetic */ void lambda$setDuvCalibration$11(Boolean bool) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_duv_list;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        setTitle(getString(R.string.duv_setting));
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_add);
        this.device = Injection.repo().device().getDeviceByDeviceId(getIntent().getLongExtra("device_id", -1L));
        ((ActDuvListBinding) this.mViewBinding).title.ivSearch.getLayoutParams().width = SizeUtils.dp2px(40.0f);
        ((ActDuvListBinding) this.mViewBinding).title.ivSearch.getLayoutParams().height = SizeUtils.dp2px(40.0f);
        ((ActDuvListBinding) this.mViewBinding).title.ivSearch.setVisibility(0);
        ((ActDuvListBinding) this.mViewBinding).title.ivSearch.setImageResource(R.mipmap.ic_edit_duv);
        ((ActDuvListBinding) this.mViewBinding).title.ivSearch.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActDuvList$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActDuvList.this.lambda$initView$0(view);
            }
        });
        ((ActDuvListBinding) this.mViewBinding).tvShare.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActDuvList$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActDuvList.this.lambda$initView$1(view);
            }
        });
        initAdapter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        this.editMode = true;
        initViewMode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view) {
        ShareHelper shareHelper = ShareHelper.INSTANCE;
        List<ModeContent> list = this.modeList;
        shareHelper.convert(list.subList(1, list.size()));
        NavUtils.destination(ActShareDuvList.class).withLong("device_id", this.device.getDeviceId()).navigation(ActivityUtils.getTopActivity());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        Listing<ModeContent> modeList = Injection.repo().mode().getModeList(this, Injection.repo().home().getSelPlace().getPlaceId(), 20, 4);
        this.request = modeList;
        handleData(modeList, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActDuvList$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDuvList.this.lambda$startObserve$2((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(List list) {
        this.modeList.clear();
        this.modeList.addAll(list);
        addDefaultDuv();
        if (list.isEmpty()) {
            this.editMode = false;
        }
        initViewMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        super.showContent();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (this.editMode) {
            this.editMode = false;
            initViewMode();
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.create_duv));
        arrayList.add(getString(R.string.import_duv));
        final SingleSelectPopup apply = SingleSelectPopup.create(this).setData(arrayList).apply();
        apply.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActDuvList$$ExternalSyntheticLambda2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActDuvList.this.lambda$edit$3(apply, baseQuickAdapter, view, i);
            }
        });
        apply.showAtAnchorView(((ActDuvListBinding) this.mViewBinding).title.ivEdit, 2, 1, 130, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$3(SingleSelectPopup singleSelectPopup, BaseQuickAdapter baseQuickAdapter, View view, int i) {
        singleSelectPopup.dismiss();
        if (i == 0) {
            createNewDuv();
        } else if (i == 1 && checkPhotoPermission(6666)) {
            goScanDuv();
        }
    }

    private void addDefaultDuv() {
        if (this.modeList.isEmpty() || !"0000".equals(this.modeList.get(0).getContent())) {
            ModeContent modeContent = new ModeContent();
            modeContent.setModeName(getString(R.string.mode_default));
            modeContent.setDeviceType(20);
            modeContent.setContent("0000");
            this.modeList.add(0, modeContent);
        }
    }

    private void createNewDuv() {
        final List<String> asList = Arrays.asList(getResources().getStringArray(R.array.brand_rgbcw));
        SelectListDialog.asDefault(false).setTitle(getString(R.string.choose_brand)).setCancelString(getString(R.string.cancel)).setSelectList(asList).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActDuvList$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDuvList.this.lambda$createNewDuv$4(asList, (Integer) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createNewDuv$4(List list, Integer num) {
        String str;
        if (num.intValue() == list.size() - 1) {
            str = "";
        } else {
            str = (String) list.get(num.intValue());
        }
        NavUtils.destination(ActAddDuv.class).withLong("device_id", this.device.getDeviceId()).withString(Constants.MODE_NAME, str).withInt(Constants.MODE_NUM, ShareHelper.INSTANCE.getModeIndex(this.modeList)).withDefaultRequestCode().navigation(this);
    }

    private void goScanDuv() {
        ShareHelper shareHelper = ShareHelper.INSTANCE;
        List<ModeContent> list = this.modeList;
        shareHelper.setDuvList(list.subList(1, list.size()));
        NavUtils.destination(ActQrCodeScanDuv.class).withDefaultRequestCode().navigation(this);
    }

    private void initAdapter() {
        BaseItemDraggableAdapter<ModeContent, BaseViewHolder> baseItemDraggableAdapter = new BaseItemDraggableAdapter<ModeContent, BaseViewHolder>(R.layout.item_rgbcw_duv, this.modeList) { // from class: com.ltech.smarthome.ui.device.light.ActDuvList.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, ModeContent item) {
                helper.setText(R.id.tv_name, item.getModeName()).setImageResource(R.id.iv_select, R.mipmap.ic_delete).setGone(R.id.iv_select, ActDuvList.this.editMode).setGone(R.id.iv_sort, false).setGone(R.id.iv_apply, !ActDuvList.this.editMode).setGone(R.id.view_divider, helper.getBindingAdapterPosition() != getItemCount() - 1).addOnClickListener(R.id.iv_select).addOnClickListener(R.id.iv_apply);
            }
        };
        this.mAdapter = baseItemDraggableAdapter;
        baseItemDraggableAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActDuvList$$ExternalSyntheticLambda11
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActDuvList.this.lambda$initAdapter$9(baseQuickAdapter, view, i);
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActDuvList$$ExternalSyntheticLambda12
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActDuvList.this.lambda$initAdapter$10(baseQuickAdapter, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActDuvListBinding) this.mViewBinding).rvContent);
        ((ActDuvListBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAdapter$9(BaseQuickAdapter baseQuickAdapter, View view, final int i) {
        if (view.getId() == R.id.iv_select) {
            MessageDialog.show(this, R.string.delete_duv, R.string.delete_data_no_recover).setOkButton(R.string.confirm, new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActDuvList$$ExternalSyntheticLambda0
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view2) {
                    boolean lambda$initAdapter$7;
                    lambda$initAdapter$7 = ActDuvList.this.lambda$initAdapter$7(i, baseDialog, view2);
                    return lambda$initAdapter$7;
                }
            }).setCancelButton(R.string.cancel);
        } else if (view.getId() == R.id.iv_apply) {
            MessageDialog.show(this, getString(R.string.apply_duv_tip), "").setOkButton(R.string.confirm, new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActDuvList$$ExternalSyntheticLambda4
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view2) {
                    boolean lambda$initAdapter$8;
                    lambda$initAdapter$8 = ActDuvList.this.lambda$initAdapter$8(i, baseDialog, view2);
                    return lambda$initAdapter$8;
                }
            }).setCancelButton(R.string.cancel);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initAdapter$7(final int i, BaseDialog baseDialog, View view) {
        ((ObservableSubscribeProxy) Injection.net().deleteMode(Injection.repo().home().getSelectPlace().getValue().getPlaceId(), 20, 4, this.mAdapter.getData().get(i).getModeIndex()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.light.ActDuvList$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDuvList.this.lambda$initAdapter$5((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.light.ActDuvList$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActDuvList.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.light.ActDuvList$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDuvList.this.lambda$initAdapter$6(i, obj);
            }
        }, new SmartErrorComsumer());
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAdapter$5(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.removing));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAdapter$6(int i, Object obj) throws Exception {
        SmartToast.showShort(getString(R.string.delete_success));
        this.modeList.remove(i);
        this.mAdapter.notifyItemRemoved(i);
        this.request.refresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initAdapter$8(int i, BaseDialog baseDialog, View view) {
        setDuvCalibration(this.mAdapter.getData().get(i).getContent());
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAdapter$10(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (this.editMode || i == 0) {
            return;
        }
        NavUtils.destination(ActAddDuv.class).withLong("device_id", this.device.getDeviceId()).withLong(Constants.MODE_ID, this.mAdapter.getData().get(i).getLightModeId()).withLong(Constants.MODE_NUM, this.mAdapter.getData().get(i).getModeIndex()).withDefaultRequestCode().navigation(this);
    }

    private void initViewMode() {
        if (this.editMode) {
            this.modeList.remove(0);
            ((ActDuvListBinding) this.mViewBinding).title.ivSearch.setVisibility(8);
            ((ActDuvListBinding) this.mViewBinding).layoutBottom.setVisibility(8);
            setEditImage(0);
            setEditString(getString(R.string.finish));
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemDragAndSwipeCallback(this.mAdapter));
            itemTouchHelper.attachToRecyclerView(((ActDuvListBinding) this.mViewBinding).rvContent);
            this.mAdapter.enableDragItem(itemTouchHelper, R.id.iv_sort, false);
        } else {
            addDefaultDuv();
            if (this.modeList.size() > 1) {
                ((ActDuvListBinding) this.mViewBinding).title.ivSearch.setVisibility(0);
                ((ActDuvListBinding) this.mViewBinding).layoutBottom.setVisibility(0);
            } else {
                ((ActDuvListBinding) this.mViewBinding).title.ivSearch.setVisibility(8);
                ((ActDuvListBinding) this.mViewBinding).layoutBottom.setVisibility(8);
            }
            setEditImage(R.mipmap.ic_add);
            setEditString("");
            this.mAdapter.disableDragItem();
        }
        this.mAdapter.replaceData(this.modeList);
    }

    private void setDuvCalibration(String duv) {
        showLoadingDialog(getString(R.string.saving));
        CmdAssistant.getLightCmdAssistant(this.device, new int[0]).setDuvCalibration(this, duv, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActDuvList$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDuvList.lambda$setDuvCalibration$11((Boolean) obj);
            }
        });
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            this.request.refresh();
        }
    }
}