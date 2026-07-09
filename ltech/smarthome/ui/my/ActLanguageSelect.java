package com.ltech.smarthome.ui.my;

import android.app.Activity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import anet.channel.util.StringUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.justalk.cloud.lemon.MtcUeConstants;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseSingleSelectActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.control.ActHome;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.NameRepository;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.ResponseMsg;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ActLanguageSelect extends BaseSingleSelectActivity<String> {
    private long controlId;
    private boolean groupControl;
    private int initPosition;

    static /* synthetic */ void lambda$save$3(Object obj) throws Exception {
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select;
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        setTitle(getString(R.string.language_setting));
        ((ActSelectBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected void setUpData() {
        this.controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        int i = getIntent().getIntExtra(Constants.LANGUAGE_TPYE, 0) == 2 ? 1 : 0;
        this.groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        if (this.controlId != -1) {
            this.initPosition = i;
        } else {
            String currentLocaleLanguage = LanguageUtils.getCurrentLocaleLanguage(this);
            if (StringUtils.isNotBlank(currentLocaleLanguage)) {
                this.initPosition = 0;
                if (currentLocaleLanguage.contains("TW") || currentLocaleLanguage.contains("HK")) {
                    this.initPosition = 1;
                } else if (currentLocaleLanguage.contains("en")) {
                    this.initPosition = 2;
                } else if (currentLocaleLanguage.contains("ko") || currentLocaleLanguage.contains("KR")) {
                    this.initPosition = 3;
                } else if (currentLocaleLanguage.contains("ru") || currentLocaleLanguage.contains("RU")) {
                    this.initPosition = 4;
                } else if (currentLocaleLanguage.contains("vi") || currentLocaleLanguage.contains("VI")) {
                    this.initPosition = 5;
                }
            } else {
                this.initPosition = -1;
            }
        }
        this.selectPosition = this.initPosition;
        super.setUpData();
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<String> getList() {
        if (this.controlId != -1) {
            return Arrays.asList(NameRepository.getPanelLanguageActionName(this));
        }
        return Arrays.asList(NameRepository.getLanguageActionName(this));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, item).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity
    protected void save() {
        final Role deviceById;
        if (this.controlId != -1) {
            if (this.groupControl) {
                deviceById = Injection.repo().group().getGroupById(this.controlId);
            } else {
                deviceById = Injection.repo().device().getDeviceById(this.controlId);
            }
            showLoadingDialog(getString(R.string.saving));
            CmdAssistant.getDeviceAssistant(deviceById, new int[0]).setPanelScreenLanguage(this, this.selectPosition + 1, new IAction() { // from class: com.ltech.smarthome.ui.my.ActLanguageSelect$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActLanguageSelect.this.lambda$save$2(deviceById, (ResponseMsg) obj);
                }
            });
            return;
        }
        if (this.selectPosition != this.initPosition) {
            Locale locale = Locale.SIMPLIFIED_CHINESE;
            if (this.selectPosition == 1) {
                locale = Locale.TRADITIONAL_CHINESE;
            } else if (this.selectPosition == 2) {
                locale = Locale.ENGLISH;
            } else if (this.selectPosition == 3) {
                locale = Locale.KOREA;
            } else if (this.selectPosition == 4) {
                locale = new Locale("ru", "RU");
            } else if (this.selectPosition == 5) {
                locale = new Locale("vi", "VI");
            }
            LanguageUtils.applyLanguage(getApplicationContext(), locale);
            LanguageUtils.applyLanguage(this, locale, (Class<? extends Activity>) ActHome.class);
            ((ObservableSubscribeProxy) Injection.net().setLanguage(locale.toString().contains(MtcUeConstants.MTC_UE_AUTHCODE_IN_CHN) ? 2 : 1).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.my.ActLanguageSelect$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActLanguageSelect.lambda$save$3(obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$2(Role role, ResponseMsg responseMsg) {
        dismissLoadingDialog();
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            if (!this.groupControl) {
                final Device device = (Device) role;
                try {
                    JSONObject jSONObject = device.getExtParam() != null ? new JSONObject(device.getExtParam()) : new JSONObject();
                    jSONObject.put("switchScreenLanguage", this.selectPosition + 1);
                    device.setExtParam(jSONObject.toString());
                    ((ObservableSubscribeProxy) Injection.net().updateParamExt(device.getDeviceId(), jSONObject.toString()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.my.ActLanguageSelect$$ExternalSyntheticLambda0
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) {
                            ActLanguageSelect.this.lambda$save$0(device, obj);
                        }
                    }, new SmartErrorComsumer());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ReplaceHelper.instance().backupData(this, device.getDeviceId(), UpdateBackDataRequest.LANGUAGE, CmdAssistant.getDeviceAssistant(device, new int[0]).setPanelScreenLanguage(this.selectPosition + 1));
                return;
            }
            final Group group = (Group) role;
            try {
                JSONObject jSONObject2 = group.getExtParam() != null ? new JSONObject(group.getExtParam()) : new JSONObject();
                jSONObject2.put("switchScreenLanguage", this.selectPosition + 1);
                group.setExtParam(jSONObject2.toString());
                ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(group.getGroupId(), jSONObject2.toString()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.my.ActLanguageSelect$$ExternalSyntheticLambda1
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActLanguageSelect.this.lambda$save$1(group, obj);
                    }
                }, new SmartErrorComsumer());
                return;
            } catch (JSONException e2) {
                e2.printStackTrace();
                return;
            }
        }
        showErrorDialog(getString(R.string.save_fail));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$0(Device device, Object obj) throws Exception {
        Injection.repo().device().saveDevice(device);
        SmartToast.showCenterShort(getString(R.string.app_str_setting_success));
        finishActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$1(Group group, Object obj) throws Exception {
        Injection.repo().group().saveGroup(group);
        SmartToast.showCenterShort(getString(R.string.app_str_setting_success));
        finishActivity();
    }
}