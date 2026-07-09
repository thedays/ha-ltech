package com.ltech.smarthome.view.dialog;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.databinding.DialogListBinding;
import com.ltech.smarthome.ui.item.GoItem;
import java.util.List;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/* loaded from: classes4.dex */
public class ListDialog extends SmartDialog<DialogListBinding> {
    private GoItem bottomItem;
    private ItemBinding<GoItem> itemBinding;
    private ObservableList<GoItem> mObservableList = new ObservableArrayList();

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_list;
    }

    public static ListDialog asTop(int itemLayout) {
        ListDialog listDialog = new ListDialog();
        listDialog.setItemBinding(ItemBinding.of(40, itemLayout));
        listDialog.setViewConverter(new SmartDialog.ViewConverter<DialogListBinding, ListDialog>() { // from class: com.ltech.smarthome.view.dialog.ListDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(DialogListBinding viewBinding, ListDialog dialog) {
                viewBinding.setList(dialog.mObservableList);
                viewBinding.setItemBinding(dialog.itemBinding);
                viewBinding.setItem(dialog.bottomItem);
            }
        }).setOutCancel(true).setMargin(0).setGravity(48);
        return listDialog;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "list_dialog";
    }

    public void setItemBinding(ItemBinding<GoItem> itemBinding) {
        this.itemBinding = itemBinding;
    }

    public void setItemList(List<GoItem> list) {
        this.mObservableList.clear();
        this.mObservableList.addAll(list);
    }

    public void setBottomItem(GoItem item) {
        this.bottomItem = item;
    }
}