package com.ltech.smarthome.ui.home;

import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.Utils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;
import me.tatarka.bindingcollectionadapter2.collections.MergeObservableList;
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass;

/* loaded from: classes4.dex */
public class ActFloorManageVM extends BaseViewModel {
    public Listing<Floor> mFloorListing;
    public long placeId;
    public ObservableList<Floor> mObservableList = new ObservableArrayList();
    public MergeObservableList<Object> mMergeObservableList = new MergeObservableList().insertList(this.mObservableList).insertItem(ActivityUtils.getTopActivity().getString(R.string.add_floor));
    public OnItemBindClass<Object> itemBinding = new OnItemBindClass().map(Floor.class, new OnItemBind() { // from class: com.ltech.smarthome.ui.home.ActFloorManageVM$$ExternalSyntheticLambda2
        @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
        public final void onItemBind(ItemBinding itemBinding, int i, Object obj) {
            ActFloorManageVM.this.lambda$new$1(itemBinding, i, (Floor) obj);
        }
    }).map(String.class, new OnItemBind() { // from class: com.ltech.smarthome.ui.home.ActFloorManageVM$$ExternalSyntheticLambda3
        @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
        public final void onItemBind(ItemBinding itemBinding, int i, Object obj) {
            ActFloorManageVM.this.lambda$new$3(itemBinding, i, (String) obj);
        }
    });
    public BindingRecyclerViewAdapter.ItemIds<Object> itemIds = new BindingRecyclerViewAdapter.ItemIds() { // from class: com.ltech.smarthome.ui.home.ActFloorManageVM$$ExternalSyntheticLambda4
        @Override // me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter.ItemIds
        public final long getItemId(int i, Object obj) {
            return ActFloorManageVM.lambda$new$4(i, obj);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(ItemBinding itemBinding, int i, final Floor floor) {
        itemBinding.clearExtras().set(40, R.layout.item_floor_manage).bindExtra(10, new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.home.ActFloorManageVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActFloorManageVM.this.lambda$new$0(floor);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Floor floor) {
        navigation(NavUtils.destination(ActEditFloorName.class).withString(Constants.FLOOR_NAME, floor.getFloorName()).withLong(Constants.FLOOR_ID, floor.getFloorId()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(ItemBinding itemBinding, int i, String str) {
        itemBinding.clearExtras().set(40, R.layout.footer_add).bindExtra(81, Integer.valueOf(ContextCompat.getColor(Utils.getApp(), R.color.color_text_red))).bindExtra(10, new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.home.ActFloorManageVM$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActFloorManageVM.this.lambda$new$2();
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2() {
        navigation(NavUtils.destination(ActAddFloor.class).withLong(Constants.PLACE_ID, this.placeId));
    }

    static /* synthetic */ long lambda$new$4(int i, Object obj) {
        return obj instanceof Floor ? ((Floor) obj).getFloorId() : i;
    }
}