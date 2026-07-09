package com.ltech.smarthome.ui.device.cg485;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActCommandCategoryBinding;
import com.ltech.smarthome.model.device_param.Rs485ExtParam;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.view.dialog.AddCg485CategoryDialog;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class ActCommandCategory extends BaseNormalActivity<ActCommandCategoryBinding> {
    private int commandType;
    private Rs485ExtParam extParam;
    private Rs485ExtParam.Category mCategory;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_command_category;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.command_set));
        setBackImage(R.mipmap.icon_back);
        this.commandType = getIntent().getIntExtra(Constants.COMMAND_TYPE, 1);
        this.extParam = Cg485Helper.extParam;
        this.mCategory = Cg485Helper.getCategory(this.commandType);
        ((ActCommandCategoryBinding) this.mViewBinding).tvName.setText(this.mCategory.getCategoryName());
        ((ActCommandCategoryBinding) this.mViewBinding).cardColor.setCardBackgroundColor(getResources().getIntArray(R.array.instruct_color)[this.mCategory.getColorIdx()]);
        ((ActCommandCategoryBinding) this.mViewBinding).setOwnerOrManager(Boolean.valueOf(isOwnerOrManager()));
        ((ActCommandCategoryBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.cg485.ActCommandCategory$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActCommandCategory.this.lambda$initView$3((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(View view) {
        int id = view.getId();
        if (id == R.id.layout_set_color) {
            AddCg485CategoryDialog.asDefault().setTitle(getString(R.string.select_color)).setSelectPosition(0).setColorMode(true).setOnSaveListener(new AddCg485CategoryDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActCommandCategory$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.view.dialog.AddCg485CategoryDialog.OnSaveListener
                public final void onSave(String str, int i, int i2) {
                    ActCommandCategory.this.lambda$initView$1(str, i, i2);
                }
            }).showDialog(this);
        } else if (id == R.id.layout_set_name) {
            EditDialog.asDefault().setContent(this.mCategory.getCategoryName()).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActCommandCategory$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActCommandCategory.this.lambda$initView$0((String) obj);
                }
            }).showDialog(this);
        } else {
            if (id != R.id.tv_delete) {
                return;
            }
            MessageDialog.show(this, R.string.delete_category, R.string.delete_category_tip).setOkButton(R.string.delete, new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActCommandCategory$$ExternalSyntheticLambda3
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view2) {
                    boolean lambda$initView$2;
                    lambda$initView$2 = ActCommandCategory.this.lambda$initView$2(baseDialog, view2);
                    return lambda$initView$2;
                }
            }).setCancelButton(R.string.cancel);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(String str) {
        this.mCategory.setCategoryName(str);
        ((ActCommandCategoryBinding) this.mViewBinding).tvName.setText(this.mCategory.getCategoryName());
        Cg485Helper.updateParamExt(this, false, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(String str, int i, int i2) {
        this.mCategory.setColorIdx(i2);
        ((ActCommandCategoryBinding) this.mViewBinding).cardColor.setCardBackgroundColor(getResources().getIntArray(R.array.instruct_color)[this.mCategory.getColorIdx()]);
        Cg485Helper.updateParamExt(this, false, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initView$2(BaseDialog baseDialog, View view) {
        deleteCategory();
        return false;
    }

    private void deleteCategory() {
        if (this.commandType == 1) {
            this.extParam.getTo485List().remove(Cg485Helper.categoryPosition);
            Cg485Helper.updateParamExt(this, true, true);
        } else {
            if (this.extParam.getToBleList().get(Cg485Helper.categoryPosition).getAction().size() > 0) {
                ArrayList arrayList = new ArrayList();
                Iterator<Rs485ExtParam.Instruct> it = this.extParam.getToBleList().get(Cg485Helper.categoryPosition).getAction().iterator();
                while (it.hasNext()) {
                    arrayList.add(Integer.valueOf(it.next().getCmdIdx()));
                }
                showLoadingDialog("");
                Cg485Helper.deleteInstruct(this, arrayList, new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActCommandCategory$$ExternalSyntheticLambda0
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActCommandCategory.this.lambda$deleteCategory$4((Boolean) obj);
                    }
                });
                return;
            }
            this.extParam.getToBleList().remove(Cg485Helper.categoryPosition);
            Cg485Helper.updateParamExt(this, true, new boolean[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteCategory$4(Boolean bool) {
        if (bool.booleanValue()) {
            this.extParam.getToBleList().remove(Cg485Helper.categoryPosition);
            Cg485Helper.updateParamExt(this, true, new boolean[0]);
        } else {
            showErrorDialog(getString(R.string.del_fail));
        }
    }
}