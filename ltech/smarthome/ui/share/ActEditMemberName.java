package com.ltech.smarthome.ui.share;

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
import com.ltech.smarthome.net.SmartErrorComsumer;
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

/* loaded from: classes4.dex */
public class ActEditMemberName extends BaseNormalActivity<ActEditNameBinding> {
    private long mPlaceUserId;
    private int mRoleType;
    private MutableLiveData<String> mUserNickName = new MutableLiveData<>();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_edit_name;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        this.mPlaceUserId = getIntent().getLongExtra(Constants.PLACE_USER_ID, 0L);
        this.mRoleType = getIntent().getIntExtra(Constants.USER_ROLE_TYPE, 0);
        this.mUserNickName.setValue(getIntent().getStringExtra(Constants.MEMBER_NICK_NAME));
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.change_name));
        setEditString(getString(R.string.save));
        ((ActEditNameBinding) this.mViewBinding).setNameTip(getString(R.string.share_nick_name));
        ((ActEditNameBinding) this.mViewBinding).setName(this.mUserNickName);
        ((ActEditNameBinding) this.mViewBinding).etName.setHint(R.string.input_nick_name);
        ((ActEditNameBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.share.ActEditMemberName$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActEditMemberName.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (view.getId() != R.id.iv_clear) {
            return;
        }
        this.mUserNickName.setValue("");
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (TextUtils.isEmpty(this.mUserNickName.getValue())) {
            SmartToast.showShort(getString(R.string.input_nick_name));
        } else {
            ((ObservableSubscribeProxy) Injection.net().updatePlaceUser(this.mPlaceUserId, this.mRoleType, this.mUserNickName.getValue()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.share.ActEditMemberName$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActEditMemberName.this.lambda$edit$1((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.share.ActEditMemberName$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActEditMemberName.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.share.ActEditMemberName$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActEditMemberName.this.lambda$edit$2(obj);
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
        setResult(1002);
        finishActivity();
    }
}