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
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;
import me.tatarka.bindingcollectionadapter2.collections.MergeObservableList;
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass;

/* loaded from: classes4.dex */
public class ActHomeManageVM extends BaseViewModel {
    public Listing<Place> mPlaceListing;
    public ObservableList<Place> mObservableList = new ObservableArrayList();
    public MergeObservableList<Object> mMergeObservableList = new MergeObservableList().insertList(this.mObservableList).insertItem(ActivityUtils.getTopActivity().getString(R.string.create_home));
    public OnItemBindClass<Object> itemBinding = new OnItemBindClass().map(Place.class, new OnItemBind() { // from class: com.ltech.smarthome.ui.home.ActHomeManageVM$$ExternalSyntheticLambda2
        @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
        public final void onItemBind(ItemBinding itemBinding, int i, Object obj) {
            ActHomeManageVM.this.lambda$new$1(itemBinding, i, (Place) obj);
        }
    }).map(String.class, new OnItemBind() { // from class: com.ltech.smarthome.ui.home.ActHomeManageVM$$ExternalSyntheticLambda3
        @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
        public final void onItemBind(ItemBinding itemBinding, int i, Object obj) {
            ActHomeManageVM.this.lambda$new$3(itemBinding, i, (String) obj);
        }
    });
    public BindingRecyclerViewAdapter.ItemIds<Object> itemIds = new BindingRecyclerViewAdapter.ItemIds() { // from class: com.ltech.smarthome.ui.home.ActHomeManageVM$$ExternalSyntheticLambda4
        @Override // me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter.ItemIds
        public final long getItemId(int i, Object obj) {
            return ActHomeManageVM.lambda$new$4(i, obj);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(ItemBinding itemBinding, int i, final Place place) {
        itemBinding.clearExtras().set(40, R.layout.item_home_manage).bindExtra(78, String.format(getContext().getString(R.string.app_str_room_and_device), Integer.valueOf(place.getRoomtotal()), Integer.valueOf(place.getDevicetotal()))).bindExtra(10, new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.home.ActHomeManageVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActHomeManageVM.this.lambda$new$0(place);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Place place) {
        Injection.limiter().reset(Injection.keyCreator().placeUserKey(place.getPlaceId()));
        navigation(NavUtils.destination(ActHomeSetting.class).withLong(Constants.PLACE_ID, place.getPlaceId()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(ItemBinding itemBinding, int i, String str) {
        itemBinding.clearExtras().set(40, R.layout.footer_add).bindExtra(81, Integer.valueOf(ContextCompat.getColor(Utils.getApp(), R.color.color_text_red))).bindExtra(10, new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.home.ActHomeManageVM$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActHomeManageVM.this.lambda$new$2();
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2() {
        navigation(NavUtils.destination(ActCreateHome.class));
    }

    static /* synthetic */ long lambda$new$4(int i, Object obj) {
        return obj instanceof Place ? ((Place) obj).getPlaceId() : i;
    }
}