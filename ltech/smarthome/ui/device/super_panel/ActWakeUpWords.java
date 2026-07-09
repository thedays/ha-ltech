package com.ltech.smarthome.ui.device.super_panel;

import android.os.SystemClock;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseListActivity;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.product.ProductId;
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
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ActWakeUpWords extends BaseListActivity<String> {
    private long deviceId;
    private boolean isSbc = false;
    final int COUNTS1 = 5;
    final long DURATION1 = 5000;
    long[] mHits1 = new long[5];

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_go_1;
    }

    @Override // com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.wake_up_word));
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<String> getList() {
        ArrayList arrayList = new ArrayList();
        Device deviceById = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
        if (deviceById != null) {
            this.deviceId = deviceById.getDeviceId();
            if (deviceById.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI)) {
                this.isSbc = true;
                arrayList.add(getString(R.string.wake_up_word_2));
                return arrayList;
            }
            if (deviceById.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_4S) || deviceById.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_6S) || deviceById.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_12S) || deviceById.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO) || deviceById.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                this.isSbc = true;
                arrayList.add(getString(R.string.wake_up_word_1));
            } else if (deviceById.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL)) {
                arrayList.add(getString(R.string.wake_up_word_2));
                arrayList.add(getString(R.string.wake_up_word_1));
                return arrayList;
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_main, getString(R.string.wake_up_word) + " " + (helper.getAdapterPosition() + 1)).setTextColor(R.id.tv_main, ContextCompat.getColor(this, R.color.color_text_black)).setTextColor(R.id.tv_sub, ContextCompat.getColor(this, R.color.color_text_gray)).setText(R.id.tv_sub, item).setGone(R.id.iv_go, false);
        ((AppCompatTextView) helper.getView(R.id.tv_main)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void titleClick() {
        long[] jArr = this.mHits1;
        System.arraycopy(jArr, 1, jArr, 0, jArr.length - 1);
        long[] jArr2 = this.mHits1;
        jArr2[jArr2.length - 1] = SystemClock.uptimeMillis();
        if (this.mHits1[0] >= SystemClock.uptimeMillis() - 5000) {
            voiceAuthorize();
        }
    }

    private void voiceAuthorize() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("CharSwitch", this.isSbc ? "66BB00C00000F7000105EB" : "66BB00C00000F7000106EB");
            ((ObservableSubscribeProxy) Injection.net().deviceController(this.deviceId, jSONObject.toString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActWakeUpWords$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActWakeUpWords.this.lambda$voiceAuthorize$0((Disposable) obj);
                }
            }).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.super_panel.ActWakeUpWords$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActWakeUpWords.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActWakeUpWords$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SmartToast.showShort(R.string.encrypt_password_open_success);
                }
            }, new SmartErrorComsumer(this) { // from class: com.ltech.smarthome.ui.device.super_panel.ActWakeUpWords.1
                @Override // com.ltech.smarthome.net.SmartErrorComsumer
                protected void action(Throwable throwable) {
                    SmartToast.showShort(R.string.app_str_operation_failure);
                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$voiceAuthorize$0(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.app_str_process));
    }
}