package com.ltech.smarthome.ui.share;

import android.content.Context;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.placeuser.InvitationPlaceUserRequest;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActAddPlaceUserVM extends BaseViewModel {
    public Listing<Place> placeListing;
    public MutableLiveData<String> account = new MutableLiveData<>("");
    public MutableLiveData<Place> place = new MutableLiveData<>();
    public MutableLiveData<Boolean> shareManagerPermission = new MutableLiveData<>(true);
    public SingleLiveEvent<Void> showInvitationSendDialogEvent = new SingleLiveEvent<>();
    public BindingCommand<View> clickCommand = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.share.ActAddPlaceUserVM$$ExternalSyntheticLambda3
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActAddPlaceUserVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.layout_device /* 2131297427 */:
                NavUtils.destination(ActSelectDeviceForPermission.class).withLong(Constants.PLACE_ID, this.place.getValue().getPlaceId()).navigation(ActivityUtils.getTopActivity());
                break;
            case R.id.layout_manager /* 2131297531 */:
                if (this.place.getValue().isOwner()) {
                    this.shareManagerPermission.setValue(true);
                    break;
                }
                break;
            case R.id.layout_member /* 2131297534 */:
                this.shareManagerPermission.setValue(false);
                break;
            case R.id.layout_room /* 2131297603 */:
                NavUtils.destination(ActSelectRoomForPermission.class).withLong(Constants.PLACE_ID, this.place.getValue().getPlaceId()).navigation(ActivityUtils.getTopActivity());
                break;
            case R.id.layout_scene /* 2131297611 */:
                NavUtils.destination(ActSelectSceneForPermission.class).withLong(Constants.PLACE_ID, this.place.getValue().getPlaceId()).navigation(ActivityUtils.getTopActivity());
                break;
            case R.id.light_group_permission /* 2131297705 */:
                NavUtils.destination(ActSelectGroupForPermission.class).withLong(Constants.PLACE_ID, this.place.getValue().getPlaceId()).navigation(ActivityUtils.getTopActivity());
                break;
        }
    }

    public boolean checkAccountValidity(String input) {
        return RegexUtils.isEmail(input) || RegexUtils.isMobileExact(input);
    }

    public void sendInvitation(final Context context) {
        LHomeLog.i(getClass(), "sendInvitation: account-" + this.account.getValue());
        if (!checkAccountValidity(this.account.getValue())) {
            SmartToast.showShort(R.string.tip_input_right_phone_mail);
        } else {
            ((ObservableSubscribeProxy) Injection.net().invitePlaceUser(initInvitationPlaceUserRequest()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.share.ActAddPlaceUserVM$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActAddPlaceUserVM.this.lambda$sendInvitation$1(context, (Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.share.ActAddPlaceUserVM$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActAddPlaceUserVM.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.share.ActAddPlaceUserVM$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActAddPlaceUserVM.this.lambda$sendInvitation$2(obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendInvitation$1(Context context, Disposable disposable) throws Exception {
        showLoadingDialog(context.getString(R.string.adding));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendInvitation$2(Object obj) throws Exception {
        LHomeLog.i(getClass(), "sendInvitation: response enter");
        this.showInvitationSendDialogEvent.call();
    }

    private InvitationPlaceUserRequest initInvitationPlaceUserRequest() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        if (PlaceShareHelper.getInstance().roomIds != null) {
            Iterator<Long> it = PlaceShareHelper.getInstance().roomIds.iterator();
            while (it.hasNext()) {
                arrayList.add(new InvitationPlaceUserRequest.RoomId(it.next().longValue()));
            }
        }
        if (PlaceShareHelper.getInstance().deviceIds != null) {
            Iterator<Long> it2 = PlaceShareHelper.getInstance().deviceIds.iterator();
            while (it2.hasNext()) {
                arrayList2.add(new InvitationPlaceUserRequest.DeviceId(it2.next().longValue()));
            }
        }
        if (PlaceShareHelper.getInstance().groupIds != null) {
            Iterator<Long> it3 = PlaceShareHelper.getInstance().groupIds.iterator();
            while (it3.hasNext()) {
                arrayList3.add(new InvitationPlaceUserRequest.GroupId(it3.next().longValue()));
            }
        }
        if (PlaceShareHelper.getInstance().sceneIds != null) {
            Iterator<Long> it4 = PlaceShareHelper.getInstance().sceneIds.iterator();
            while (it4.hasNext()) {
                arrayList4.add(new InvitationPlaceUserRequest.SceneId(it4.next().longValue()));
            }
        }
        return new InvitationPlaceUserRequest(this.place.getValue().getPlaceId(), 0L, RegexUtils.isMobileExact(this.account.getValue()) ? this.account.getValue() : "", RegexUtils.isEmail(this.account.getValue()) ? this.account.getValue() : "", this.shareManagerPermission.getValue().booleanValue() ? 2 : 3, arrayList4.isEmpty() ? null : arrayList4, arrayList.isEmpty() ? null : arrayList, arrayList2.isEmpty() ? null : arrayList2, arrayList3.isEmpty() ? null : arrayList3, 0L);
    }
}