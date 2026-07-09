package com.ltech.smarthome.ui.home;

import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActEditNameBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.home.ActEditRoomName;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActEditRoomName extends BaseNormalActivity<ActEditNameBinding> {
    private long mRoomId;
    private MutableLiveData<String> mRoomName = new MutableLiveData<>();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_edit_name;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        this.mRoomId = getIntent().getLongExtra(Constants.ROOM_ID, 0L);
        this.mRoomName.setValue(getIntent().getStringExtra(Constants.ROOM_NAME));
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.edit_room));
        setEditString(getString(R.string.finish));
        ((ActEditNameBinding) this.mViewBinding).setDeleteTip(getString(R.string.delete_room));
        ((ActEditNameBinding) this.mViewBinding).setNameTip(getString(R.string.room_name));
        ((ActEditNameBinding) this.mViewBinding).setName(this.mRoomName);
        ((ActEditNameBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.home.ActEditRoomName$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActEditRoomName.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        int id = view.getId();
        if (id == R.id.iv_clear) {
            this.mRoomName.setValue("");
        } else {
            if (id != R.id.tv_delete) {
                return;
            }
            deleteRoom();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (TextUtils.isEmpty(this.mRoomName.getValue())) {
            SmartToast.showShort(getString(R.string.input_name));
        } else {
            ((ObservableSubscribeProxy) Injection.net().updateRoom(this.mRoomId, this.mRoomName.getValue()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActEditRoomName$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActEditRoomName.this.lambda$edit$1((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new ActEditRoomName$$ExternalSyntheticLambda2(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActEditRoomName$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActEditRoomName.this.lambda$edit$2(obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$1(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$2(Object obj) throws Exception {
        Injection.repo().home().updateRoomName(this.mRoomId, this.mRoomName.getValue());
        finishActivity();
    }

    private void deleteRoom() {
        int i;
        List<Role> roleListByRoomIdFromDb = Injection.repo().role().getRoleListByRoomIdFromDb(-1L, -1L, this.mRoomId);
        List<Scene> sceneListByRoomId = Injection.repo().scene().getSceneListByRoomId(-1L, -1L, this.mRoomId, new boolean[0]);
        int size = roleListByRoomIdFromDb.size();
        int i2 = R.string.app_delete_room_name_hint;
        if (size > 0 && sceneListByRoomId.size() > 0) {
            i = R.string.app_delete_room_tip_3;
        } else if (roleListByRoomIdFromDb.size() > 0) {
            i = R.string.app_delete_room_tip_1;
        } else if (sceneListByRoomId.size() > 0) {
            i = R.string.app_delete_room_tip_2;
        } else {
            i = R.string.app_delete_room_name_hint;
            i2 = R.string.tips;
        }
        MessageDialog.show(this, getString(i2), getString(i)).setOkButton(getString(R.string.delete), new AnonymousClass2()).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener(this) { // from class: com.ltech.smarthome.ui.home.ActEditRoomName.1
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                return false;
            }
        }).setCancelable(false);
    }

    /* renamed from: com.ltech.smarthome.ui.home.ActEditRoomName$2, reason: invalid class name */
    class AnonymousClass2 implements OnDialogButtonClickListener {
        AnonymousClass2() {
        }

        @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
        public boolean onClick(BaseDialog baseDialog, View v) {
            ((ObservableSubscribeProxy) Injection.net().deleteRoom(ActEditRoomName.this.mRoomId).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActEditRoomName$2$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActEditRoomName.AnonymousClass2.this.lambda$onClick$0((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new ActEditRoomName$$ExternalSyntheticLambda2(ActEditRoomName.this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(ActEditRoomName.this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActEditRoomName$2$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActEditRoomName.AnonymousClass2.this.lambda$onClick$1(obj);
                }
            }, new SmartErrorComsumer());
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClick$0(Disposable disposable) throws Exception {
            ActEditRoomName actEditRoomName = ActEditRoomName.this;
            actEditRoomName.showLoadingDialog(actEditRoomName.getString(R.string.removing));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClick$1(Object obj) throws Exception {
            Injection.repo().home().removeRoom(ActEditRoomName.this.mRoomId);
            ActEditRoomName.this.finishActivity();
        }
    }
}