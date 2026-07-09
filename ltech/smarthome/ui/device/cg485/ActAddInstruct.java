package com.ltech.smarthome.ui.device.cg485;

import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActAddInstructBinding;
import com.ltech.smarthome.model.device_param.Rs485ExtParam;
import com.ltech.smarthome.ui.device.cg485.ActAddInstruct;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.RadioImageTextView;
import com.ltech.smarthome.view.dialog.EditCmdCopyDialog;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.LearnInstructDialog;
import com.ltech.smarthome.view.dialog.SelectListWithIconDialog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class ActAddInstruct extends BaseNormalActivity<ActAddInstructBinding> {
    private static final int TYPE_ADD_CONTENT = 2;
    private static final int TYPE_ADD_TITLE = 1;
    private String lastInstructStr;
    private MultipleItemRvAdapter<Rs485ExtParam.Instruct, BaseViewHolder> mAdapter;
    private SingleLiveEvent<Void> refreshList = new SingleLiveEvent<>();
    private List<Rs485ExtParam.Instruct> instructList = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_add_instruct;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.add_instruct));
        initAdapter();
        initData();
        ((ActAddInstructBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.cg485.ActAddInstruct$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActAddInstruct.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        int id = view.getId();
        if (id == R.id.tv_add_instruct) {
            addData(null);
            return;
        }
        if (id != R.id.tv_save) {
            return;
        }
        for (Rs485ExtParam.Instruct instruct : this.instructList) {
            if (instruct.getRecordType() != 0) {
                if (TextUtils.isEmpty(instruct.getActionName())) {
                    SmartToast.showShort(R.string.input_name);
                    return;
                } else if (TextUtils.isEmpty(instruct.getCmd())) {
                    SmartToast.showShort(R.string.cmd_empty_tip);
                    return;
                } else {
                    if (Cg485Helper.isCmdRepeat(1, instruct.getCmd(), instruct.getDataFormat())) {
                        SmartToast.showShort(R.string.cmd_repeat_tip);
                        return;
                    }
                    Cg485Helper.getCategory(1).getAction().add(instruct);
                }
            }
        }
        this.lastInstructStr = GsonUtils.toJson(this.instructList.get(1));
        Cg485Helper.updateParamExt(this, true, true);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        this.refreshList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.cg485.ActAddInstruct$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddInstruct.this.lambda$startObserve$1((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r2) {
        this.mAdapter.replaceData(this.instructList);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (!GsonUtils.toJson(this.instructList.get(1)).equals(this.lastInstructStr) || this.instructList.size() > 2) {
            showNeedSaveDialog();
        } else {
            super.back();
        }
    }

    private void initData() {
        this.instructList.add(new Rs485ExtParam.Instruct(0));
        this.instructList.add(new Rs485ExtParam.Instruct());
        this.lastInstructStr = GsonUtils.toJson(new Rs485ExtParam.Instruct());
        this.refreshList.call();
    }

    private void initAdapter() {
        MultipleItemRvAdapter<Rs485ExtParam.Instruct, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<Rs485ExtParam.Instruct, BaseViewHolder>(new ArrayList()) { // from class: com.ltech.smarthome.ui.device.cg485.ActAddInstruct.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(Rs485ExtParam.Instruct instruct) {
                return instruct.getRecordType() == 0 ? 1 : 2;
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new BaseItemProvider<Rs485ExtParam.Instruct, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.cg485.ActAddInstruct.1.1
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_add_instruct_title;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 1;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, Rs485ExtParam.Instruct item, int position) {
                        helper.setText(R.id.tv_name, ActAddInstruct.this.getString(R.string.str_instruct_num, new Object[]{Integer.valueOf((position / 2) + 1)})).addOnClickListener(R.id.tv_copy, R.id.tv_delete);
                    }
                });
                this.mProviderDelegate.registerProvider(new AnonymousClass2(this));
            }

            /* renamed from: com.ltech.smarthome.ui.device.cg485.ActAddInstruct$1$2, reason: invalid class name */
            class AnonymousClass2 extends BaseItemProvider<Rs485ExtParam.Instruct, BaseViewHolder> {
                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int layout() {
                    return R.layout.item_add_instruct_content;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int viewType() {
                    return 2;
                }

                AnonymousClass2(final AnonymousClass1 this$1) {
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void convert(final BaseViewHolder helper, final Rs485ExtParam.Instruct item, int position) {
                    helper.setText(R.id.et_name, item.getActionName()).setText(R.id.tv_instruct, item.getCmd()).setGone(R.id.layout_data_format, item.getRecordType() == 1).addOnClickListener(R.id.layout_name, R.id.layout_input);
                    final RadioImageTextView radioImageTextView = (RadioImageTextView) helper.getView(R.id.radio_input);
                    final RadioImageTextView radioImageTextView2 = (RadioImageTextView) helper.getView(R.id.radio_library);
                    final RadioImageTextView radioImageTextView3 = (RadioImageTextView) helper.getView(R.id.radio_learn);
                    final AppCompatTextView appCompatTextView = (AppCompatTextView) helper.getView(R.id.tv_input);
                    final AppCompatTextView appCompatTextView2 = (AppCompatTextView) helper.getView(R.id.tv_instruct);
                    final RadioImageTextView radioImageTextView4 = (RadioImageTextView) helper.getView(R.id.radio_hex);
                    final RadioImageTextView radioImageTextView5 = (RadioImageTextView) helper.getView(R.id.radio_ascii);
                    radioImageTextView.setEnable(true);
                    radioImageTextView2.setEnable(true);
                    radioImageTextView3.setEnable(true);
                    radioImageTextView.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActAddInstruct$1$2$$ExternalSyntheticLambda0
                        @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
                        public final void onCheckChanged(RadioImageTextView radioImageTextView6, boolean z) {
                            ActAddInstruct.AnonymousClass1.AnonymousClass2.this.lambda$convert$0(item, helper, radioImageTextView, radioImageTextView2, radioImageTextView3, appCompatTextView, appCompatTextView2, radioImageTextView4, radioImageTextView5, radioImageTextView6, z);
                        }
                    });
                    radioImageTextView2.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActAddInstruct$1$2$$ExternalSyntheticLambda1
                        @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
                        public final void onCheckChanged(RadioImageTextView radioImageTextView6, boolean z) {
                            ActAddInstruct.AnonymousClass1.AnonymousClass2.this.lambda$convert$1(item, helper, radioImageTextView, radioImageTextView2, radioImageTextView3, appCompatTextView, appCompatTextView2, radioImageTextView6, z);
                        }
                    });
                    radioImageTextView3.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActAddInstruct$1$2$$ExternalSyntheticLambda2
                        @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
                        public final void onCheckChanged(RadioImageTextView radioImageTextView6, boolean z) {
                            ActAddInstruct.AnonymousClass1.AnonymousClass2.this.lambda$convert$2(item, helper, radioImageTextView, radioImageTextView2, radioImageTextView3, appCompatTextView, appCompatTextView2, radioImageTextView6, z);
                        }
                    });
                    if (item.getRecordType() == 1) {
                        radioImageTextView.setCheck(true);
                    } else if (item.getRecordType() == 2) {
                        radioImageTextView2.setCheck(true);
                    } else {
                        radioImageTextView3.setCheck(true);
                    }
                    switchInputType(item.getRecordType(), radioImageTextView, radioImageTextView2, radioImageTextView3, appCompatTextView, appCompatTextView2);
                    radioImageTextView2.setVisibility(8);
                    radioImageTextView4.setEnable(true);
                    radioImageTextView5.setEnable(true);
                    radioImageTextView4.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActAddInstruct$1$2$$ExternalSyntheticLambda3
                        @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
                        public final void onCheckChanged(RadioImageTextView radioImageTextView6, boolean z) {
                            ActAddInstruct.AnonymousClass1.AnonymousClass2.this.lambda$convert$3(item, appCompatTextView2, radioImageTextView4, radioImageTextView5, radioImageTextView6, z);
                        }
                    });
                    radioImageTextView5.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActAddInstruct$1$2$$ExternalSyntheticLambda4
                        @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
                        public final void onCheckChanged(RadioImageTextView radioImageTextView6, boolean z) {
                            ActAddInstruct.AnonymousClass1.AnonymousClass2.this.lambda$convert$4(item, appCompatTextView2, radioImageTextView4, radioImageTextView5, radioImageTextView6, z);
                        }
                    });
                    switchDataFormat(item.getDataFormat(), radioImageTextView4, radioImageTextView5);
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$convert$0(Rs485ExtParam.Instruct instruct, BaseViewHolder baseViewHolder, RadioImageTextView radioImageTextView, RadioImageTextView radioImageTextView2, RadioImageTextView radioImageTextView3, AppCompatTextView appCompatTextView, AppCompatTextView appCompatTextView2, RadioImageTextView radioImageTextView4, RadioImageTextView radioImageTextView5, RadioImageTextView radioImageTextView6, boolean z) {
                    instruct.setRecordType(1);
                    baseViewHolder.setGone(R.id.layout_data_format, true);
                    switchInputType(instruct.getRecordType(), radioImageTextView, radioImageTextView2, radioImageTextView3, appCompatTextView, appCompatTextView2);
                    switchDataFormat(instruct.getDataFormat(), radioImageTextView4, radioImageTextView5);
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$convert$1(Rs485ExtParam.Instruct instruct, BaseViewHolder baseViewHolder, RadioImageTextView radioImageTextView, RadioImageTextView radioImageTextView2, RadioImageTextView radioImageTextView3, AppCompatTextView appCompatTextView, AppCompatTextView appCompatTextView2, RadioImageTextView radioImageTextView4, boolean z) {
                    instruct.setRecordType(2);
                    baseViewHolder.setGone(R.id.layout_data_format, false);
                    switchInputType(instruct.getRecordType(), radioImageTextView, radioImageTextView2, radioImageTextView3, appCompatTextView, appCompatTextView2);
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$convert$2(Rs485ExtParam.Instruct instruct, BaseViewHolder baseViewHolder, RadioImageTextView radioImageTextView, RadioImageTextView radioImageTextView2, RadioImageTextView radioImageTextView3, AppCompatTextView appCompatTextView, AppCompatTextView appCompatTextView2, RadioImageTextView radioImageTextView4, boolean z) {
                    instruct.setRecordType(3);
                    baseViewHolder.setGone(R.id.layout_data_format, false);
                    switchInputType(instruct.getRecordType(), radioImageTextView, radioImageTextView2, radioImageTextView3, appCompatTextView, appCompatTextView2);
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$convert$3(Rs485ExtParam.Instruct instruct, AppCompatTextView appCompatTextView, RadioImageTextView radioImageTextView, RadioImageTextView radioImageTextView2, RadioImageTextView radioImageTextView3, boolean z) {
                    if (instruct.getDataFormat() != 1) {
                        appCompatTextView.setText("");
                        instruct.setCmd("");
                        instruct.setDataFormat(1);
                        switchDataFormat(instruct.getDataFormat(), radioImageTextView, radioImageTextView2);
                    }
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$convert$4(Rs485ExtParam.Instruct instruct, AppCompatTextView appCompatTextView, RadioImageTextView radioImageTextView, RadioImageTextView radioImageTextView2, RadioImageTextView radioImageTextView3, boolean z) {
                    if (instruct.getDataFormat() != 2) {
                        appCompatTextView.setText("");
                        instruct.setCmd("");
                        instruct.setDataFormat(2);
                        switchDataFormat(instruct.getDataFormat(), radioImageTextView, radioImageTextView2);
                    }
                }

                private void switchInputType(int editType, RadioImageTextView radioInput, RadioImageTextView radioLibrary, RadioImageTextView radioLearn, AppCompatTextView tvInput, AppCompatTextView tvInstruct) {
                    radioInput.setCheck(editType == 1);
                    radioLibrary.setCheck(editType == 2);
                    radioLearn.setCheck(editType == 3);
                    if (editType == 1) {
                        tvInput.setText(R.string.manual_input);
                        tvInstruct.setHint(R.string.please_input_instruct);
                    } else if (editType == 2) {
                        tvInput.setText(R.string.choose_library);
                        tvInstruct.setHint(R.string.choose_library_tip);
                    } else {
                        tvInput.setText(R.string.learn_instruct);
                        tvInstruct.setHint(R.string.learn_instruct_tip);
                    }
                }

                private void switchDataFormat(int dataFormat, RadioImageTextView radioHex, RadioImageTextView radioAscii) {
                    radioHex.setCheck(dataFormat == 1);
                    radioAscii.setCheck(dataFormat == 2);
                }
            }
        };
        this.mAdapter = multipleItemRvAdapter;
        multipleItemRvAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActAddInstruct$$ExternalSyntheticLambda2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActAddInstruct.this.lambda$initAdapter$6(baseQuickAdapter, view, i);
            }
        });
        this.mAdapter.finishInitialize();
        this.mAdapter.bindToRecyclerView(((ActAddInstructBinding) this.mViewBinding).rvContent);
        ((ActAddInstructBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActAddInstructBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAdapter$6(BaseQuickAdapter baseQuickAdapter, View view, final int i) {
        switch (view.getId()) {
            case R.id.layout_input /* 2131297497 */:
                int recordType = this.mAdapter.getData().get(i).getRecordType();
                if (recordType != 2) {
                    if (recordType == 3) {
                        LearnInstructDialog.asDefault().setConfirmString(getString(R.string.confirm)).setCancelString(getString(R.string.cancel)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActAddInstruct$$ExternalSyntheticLambda4
                            @Override // com.ltech.smarthome.base.IAction
                            public final void act(Object obj) {
                                ActAddInstruct.this.lambda$initAdapter$3(i, (String) obj);
                            }
                        }).showDialog(this);
                        break;
                    } else {
                        EditCmdCopyDialog.asDefault().setTitle(getString(R.string.input_instruct)).setContent(this.instructList.get(i).getCmd()).setHexFormat(this.instructList.get(i).getDataFormat() == 1).setContentTip(this.instructList.get(i).getDataFormat() == 1 ? getString(R.string.input_hex_tip) : "").setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.save)).setInputEmptyTip(getString(R.string.please_input_instruct)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActAddInstruct$$ExternalSyntheticLambda5
                            @Override // com.ltech.smarthome.base.IAction
                            public final void act(Object obj) {
                                ActAddInstruct.this.lambda$initAdapter$4(i, (String) obj);
                            }
                        }).showDialog(this);
                        break;
                    }
                } else {
                    final List<String> asList = Arrays.asList(Cg485Helper.library);
                    SelectListWithIconDialog.asDefault(true).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.confirm)).setIconRes(R.mipmap.icon_copy).setSelectPosition(-1).setSelectList(asList).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActAddInstruct$$ExternalSyntheticLambda3
                        @Override // com.ltech.smarthome.base.IAction
                        public final void act(Object obj) {
                            ActAddInstruct.this.lambda$initAdapter$2(i, asList, (Integer) obj);
                        }
                    }).showDialog(this);
                    break;
                }
            case R.id.layout_name /* 2131297551 */:
                EditDialog.asDefault().setContent(this.instructList.get(i).getActionName()).setTitle(getString(R.string.edit_name)).setHint(getString(R.string.input_name)).setInputEmptyTip(getString(R.string.input_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActAddInstruct$$ExternalSyntheticLambda6
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActAddInstruct.this.lambda$initAdapter$5(i, (String) obj);
                    }
                }).showDialog(this);
                break;
            case R.id.tv_copy /* 2131298543 */:
                addData(this.mAdapter.getData().get(i + 1));
                break;
            case R.id.tv_delete /* 2131298575 */:
                if (this.instructList.size() == 2) {
                    SmartToast.showShort(R.string.cmd_least_one);
                    break;
                } else {
                    this.instructList.remove(i + 1);
                    this.instructList.remove(i);
                    this.refreshList.call();
                    break;
                }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAdapter$2(int i, List list, Integer num) {
        this.instructList.get(i).setDataFormat(1);
        this.instructList.get(i).setCmd((String) list.get(num.intValue()));
        this.mAdapter.notifyItemChanged(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAdapter$3(int i, String str) {
        this.instructList.get(i).setDataFormat(1);
        this.instructList.get(i).setCmd(str);
        this.mAdapter.notifyItemChanged(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAdapter$4(int i, String str) {
        if (this.instructList.get(i).getDataFormat() == 1) {
            str = Cg485Helper.getInputCmd(str);
        }
        this.instructList.get(i).setCmd(str);
        this.mAdapter.notifyItemChanged(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAdapter$5(int i, String str) {
        this.instructList.get(i).setActionName(str);
        this.mAdapter.notifyItemChanged(i);
    }

    private void addData(Rs485ExtParam.Instruct instruct) {
        this.instructList.add(new Rs485ExtParam.Instruct(0));
        if (instruct == null) {
            this.instructList.add(new Rs485ExtParam.Instruct());
        } else {
            Rs485ExtParam.Instruct instruct2 = new Rs485ExtParam.Instruct();
            instruct2.setRecordType(instruct.getRecordType());
            instruct2.setDataFormat(instruct.getDataFormat());
            instruct2.setCmd(instruct.getCmd());
            this.instructList.add(instruct2);
        }
        this.refreshList.call();
    }
}