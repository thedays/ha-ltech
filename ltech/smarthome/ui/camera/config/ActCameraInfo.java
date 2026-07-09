package com.ltech.smarthome.ui.camera.config;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActProductIntroductionBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.response.camera.GetTokenResponse;
import com.ltech.smarthome.ui.camera.EZManager;
import com.ltech.smarthome.ui.config.ActNetConfig;
import com.ltech.smarthome.ui.config.ConfigHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.ThreadPoolManager;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActCameraInfo extends BaseNormalActivity<ActProductIntroductionBinding> {
    private static final int MSG_ADD_CAMERA_FAIL = 12;
    private static final int MSG_ADD_CAMERA_SUCCESS = 10;
    private static final int MSG_CAMERA_ALREADY_ADD = 2;
    private static final int MSG_CAMERA_NEED_ADD = 1;
    private static final int MSG_QUERY_CAMERA_INFO = 13;
    private boolean hasAdded;
    private boolean isOnline;
    private MessageHandler mHandler;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_product_introduction;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        this.mHandler = new MessageHandler();
        ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.app_str_network_config_wifi_camera_tip_1));
        ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_camera_ezviz);
        ((ActProductIntroductionBinding) this.mViewBinding).tvFailTip.setVisibility(0);
        ((ActProductIntroductionBinding) this.mViewBinding).tvFailTip.setText("");
        ((ActProductIntroductionBinding) this.mViewBinding).btNext.setText(getString(R.string.next));
        ((ActProductIntroductionBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.camera.config.ActCameraInfo$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActCameraInfo.this.lambda$initView$0((View) obj);
            }
        }));
        ((ObservableSubscribeProxy) Injection.net().getPlaceToken(ConfigHelper.instance().placeId, true).delaySubscription(200L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.camera.config.ActCameraInfo$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActCameraInfo.this.lambda$initView$1((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.camera.config.ActCameraInfo$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActCameraInfo.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.camera.config.ActCameraInfo$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActCameraInfo.this.lambda$initView$2((GetTokenResponse) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (!this.hasAdded) {
            ConfigHelper.instance().productInfo = ProductRepository.getProductInfoByPid(ProductId.ID_WIFI_CAMERA);
            NavUtils.destination(ActNetConfig.class).withBoolean(Constants.SETTING_PAGE, false).withString(Constants.PRODUCT_ID, ProductId.ID_WIFI_CAMERA).navigation(this);
            return;
        }
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.query_camera_info));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(GetTokenResponse getTokenResponse) throws Exception {
        EZManager.instance().setAccesstoken(getTokenResponse.getAccesstoken());
        this.mHandler.sendEmptyMessage(13);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        ConfigHelper.instance().productInfo = ProductRepository.getProductInfoByPid(ProductId.ID_WIFI_CAMERA);
        NavUtils.destination(ActNetConfig.class).withBoolean(Constants.SETTING_PAGE, false).withString(Constants.PRODUCT_ID, ProductId.ID_WIFI_CAMERA).navigation(this);
    }

    class MessageHandler extends Handler {
        MessageHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.obj != null) {
                ActCameraInfo.this.isOnline = ((Boolean) msg.obj).booleanValue();
            }
            int i = msg.what;
            if (i == 1) {
                ActCameraInfo.this.dismissLoadingDialog();
                ViewDataBinding unused = ActCameraInfo.this.mViewBinding;
                ActCameraInfo.this.hasAdded = false;
            } else if (i != 2) {
                if (i != 13) {
                    return;
                }
                ActCameraInfo.this.queryCamera();
            } else {
                ActCameraInfo.this.dismissLoadingDialog();
                if (ActCameraInfo.this.mViewBinding != null) {
                    AppCompatTextView appCompatTextView = ((ActProductIntroductionBinding) ActCameraInfo.this.mViewBinding).tvFailTip;
                    ActCameraInfo actCameraInfo = ActCameraInfo.this;
                    appCompatTextView.setText(actCameraInfo.getString(actCameraInfo.isOnline ? R.string.scan_tips_120020_or_120022 : R.string.scan_tips_120024_or_120029));
                }
                ActCameraInfo.this.hasAdded = true;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queryCamera() {
        ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.ltech.smarthome.ui.camera.config.ActCameraInfo.1
            @Override // java.lang.Runnable
            public void run() {
                EZManager.instance().queryDeviceInfo(new EZManager.IQueryDeviceListener() { // from class: com.ltech.smarthome.ui.camera.config.ActCameraInfo.1.1
                    @Override // com.ltech.smarthome.ui.camera.EZManager.IQueryDeviceListener
                    public void onFail() {
                    }

                    @Override // com.ltech.smarthome.ui.camera.EZManager.IQueryDeviceListener
                    public void onNeedAdd(boolean online) {
                        Message message = new Message();
                        message.what = 1;
                        message.obj = Boolean.valueOf(online);
                        ActCameraInfo.this.mHandler.sendMessage(message);
                    }

                    @Override // com.ltech.smarthome.ui.camera.EZManager.IQueryDeviceListener
                    public void onAlreadyAddedBySelf(boolean online) {
                        Message message = new Message();
                        message.what = 2;
                        message.obj = Boolean.valueOf(online);
                        ActCameraInfo.this.mHandler.sendMessage(message);
                    }

                    @Override // com.ltech.smarthome.ui.camera.EZManager.IQueryDeviceListener
                    public void onAlreadyAddedByOther(boolean online) {
                        Message message = new Message();
                        message.what = 2;
                        message.obj = Boolean.valueOf(online);
                        ActCameraInfo.this.mHandler.sendMessage(message);
                    }
                });
            }
        });
    }
}