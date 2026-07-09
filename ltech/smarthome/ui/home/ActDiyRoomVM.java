package com.ltech.smarthome.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.room.AddRoomResponse;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

/* loaded from: classes4.dex */
public class ActDiyRoomVM extends BaseViewModel {
    public FlexboxLayoutManager flexboxLayoutManager;
    public String[] nameArray;
    public ObservableArrayList<String> recommendList;
    public ObservableField<String> roomName = new ObservableField<>();
    public long floorId = -1;
    public long placeId = -1;
    public ItemBinding<String> itemBinding = ItemBinding.of(new OnItemBind() { // from class: com.ltech.smarthome.ui.home.ActDiyRoomVM$$ExternalSyntheticLambda4
        @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
        public final void onItemBind(ItemBinding itemBinding, int i, Object obj) {
            ActDiyRoomVM.this.lambda$new$1(itemBinding, i, (String) obj);
        }
    });

    public ActDiyRoomVM() {
        ObservableArrayList<String> observableArrayList = new ObservableArrayList<>();
        this.recommendList = observableArrayList;
        observableArrayList.add(ActivityUtils.getTopActivity().getString(R.string.recommand_name_1));
        this.recommendList.add(ActivityUtils.getTopActivity().getString(R.string.recommand_name_2));
        this.recommendList.add(ActivityUtils.getTopActivity().getString(R.string.recommand_name_3));
        this.recommendList.add(ActivityUtils.getTopActivity().getString(R.string.recommand_name_4));
        this.recommendList.add(ActivityUtils.getTopActivity().getString(R.string.recommand_name_5));
        this.recommendList.add(ActivityUtils.getTopActivity().getString(R.string.recommand_name_6));
        this.recommendList.add(ActivityUtils.getTopActivity().getString(R.string.recommand_name_7));
        this.flexboxLayoutManager = new FlexboxLayoutManager(ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(ItemBinding itemBinding, int i, final String str) {
        itemBinding.clearExtras().set(40, R.layout.item_room_name).bindExtra(10, new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.home.ActDiyRoomVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActDiyRoomVM.this.lambda$new$0(str);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(String str) {
        this.roomName.set(str);
    }

    public void createRoom(Context context) {
        String str = this.roomName.get();
        if (str == null || TextUtils.isEmpty(str.trim())) {
            SmartToast.showShort(R.string.input_room_name);
            return;
        }
        String[] strArr = this.nameArray;
        if (strArr != null) {
            for (String str2 : strArr) {
                if (str2.equals(str)) {
                    SmartToast.showShort(R.string.name_repeat);
                    return;
                }
            }
        }
        if (-1 == this.floorId) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.ROOM_NAME, str);
            finishActivity(1001, bundle);
            return;
        }
        sendToServer(context, str);
    }

    private void sendToServer(final Context context, String roomName) {
        ((ObservableSubscribeProxy) Injection.net().addRoom(this.floorId, roomName).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActDiyRoomVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDiyRoomVM.this.lambda$sendToServer$2(context, (Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.home.ActDiyRoomVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActDiyRoomVM.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActDiyRoomVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDiyRoomVM.this.lambda$sendToServer$3((AddRoomResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendToServer$2(Context context, Disposable disposable) throws Exception {
        showLoadingDialog(context.getString(R.string.creating));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendToServer$3(AddRoomResponse addRoomResponse) throws Exception {
        Room room = new Room();
        room.setPlaceId(this.placeId);
        room.setFloorId(addRoomResponse.getFloorid());
        room.setRoomName(addRoomResponse.getRoomname());
        room.setRoomId(addRoomResponse.getRoomid());
        room.setIndex(addRoomResponse.getRoomindex());
        Injection.repo().home().saveRoom(room);
        finishActivity();
    }
}