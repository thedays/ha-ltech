package com.ltech.smarthome.ui.config;

import android.content.Intent;
import android.os.Build;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import androidx.core.app.ActivityCompat;
import com.alibaba.fastjson.parser.JSONLexer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActProductIntroduction1Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.ifly.WebViewActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.TextClickUtil;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.yanzhenjie.permission.runtime.Permission;
import kotlin.text.Typography;

/* loaded from: classes4.dex */
public class ActProductIntroduction1 extends BaseNormalActivity<ActProductIntroduction1Binding> {
    private static final int REQUEST_ENABLE_BLUETOOTH = 1021;
    private static final int REQUEST_ENABLE_LOCATION = 1022;
    private boolean comeFromSettingPage;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_product_introduction_1;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.scan_instruction));
        setViewByProductId();
        ((ActProductIntroduction1Binding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.config.ActProductIntroduction1$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActProductIntroduction1.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (view.getId() != R.id.bt_next) {
            return;
        }
        check();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void check() {
        if (bleIsOk()) {
            if (!Injection.state().isLocationEnabled(this)) {
                MessageDialog.show(this, getString(R.string.tips), getString(R.string.open_gps_tip)).setCancelable(false).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.config.ActProductIntroduction1$$ExternalSyntheticLambda2
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        boolean lambda$check$1;
                        lambda$check$1 = ActProductIntroduction1.this.lambda$check$1(baseDialog, view);
                        return lambda$check$1;
                    }
                }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.config.ActProductIntroduction1$$ExternalSyntheticLambda3
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        boolean lambda$check$2;
                        lambda$check$2 = ActProductIntroduction1.this.lambda$check$2(baseDialog, view);
                        return lambda$check$2;
                    }
                });
                return;
            }
            nav();
            setResult(1002);
            ActivityCompat.finishAfterTransition(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$check$1(BaseDialog baseDialog, View view) {
        startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 1022);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$check$2(BaseDialog baseDialog, View view) {
        getGpsPermissionFail();
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, getString(R.string.faq_url)).navigation(this);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private void setViewByProductId() {
        this.comeFromSettingPage = getIntent().getBooleanExtra(Constants.SETTING_PAGE, false);
        TextClickUtil.setTextClick(this, ((ActProductIntroduction1Binding) this.mViewBinding).tvFailTip, new ClickableSpan() { // from class: com.ltech.smarthome.ui.config.ActProductIntroduction1.1
            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ActProductIntroduction1.this.getResources().getColor(R.color.color_text_red));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, ActProductIntroduction1.this.getString(R.string.faq_url)).navigation(ActProductIntroduction1.this);
            }
        }, R.string.tip_help, R.string.add_fail_tip);
        if (ConfigHelper.instance().productInfo == null) {
            return;
        }
        String productId = ConfigHelper.instance().productInfo.getProductId();
        productId.hashCode();
        char c2 = 65535;
        switch (productId.hashCode()) {
            case -1805322688:
                if (productId.equals(ProductId.ID_BLE_LIGHT_DIM)) {
                    c2 = 0;
                    break;
                }
                break;
            case -1805199680:
                if (productId.equals(ProductId.ID_BLE_LIGHT_CT)) {
                    c2 = 1;
                    break;
                }
                break;
            case -1804340546:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGB)) {
                    c2 = 2;
                    break;
                }
                break;
            case -1804278081:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBW)) {
                    c2 = 3;
                    break;
                }
                break;
            case -1803448738:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBWY)) {
                    c2 = 4;
                    break;
                }
                break;
            case -1777527685:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_DIM)) {
                    c2 = 5;
                    break;
                }
                break;
            case -1777494050:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_CT)) {
                    c2 = 6;
                    break;
                }
                break;
            case -1776694498:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_RGB)) {
                    c2 = 7;
                    break;
                }
                break;
            case -1776638760:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_RGBW)) {
                    c2 = '\b';
                    break;
                }
                break;
            case -1776570529:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_RGBWY)) {
                    c2 = '\t';
                    break;
                }
                break;
            case -1698123058:
                if (productId.equals(ProductId.ID_MESH_GATEWAY)) {
                    c2 = '\n';
                    break;
                }
                break;
            case -1309274422:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO)) {
                    c2 = 11;
                    break;
                }
                break;
            case -1308265372:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_12S)) {
                    c2 = '\f';
                    break;
                }
                break;
            case -1287620343:
                if (productId.equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
                    c2 = '\r';
                    break;
                }
                break;
            case -1265646206:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                    c2 = 14;
                    break;
                }
                break;
            case -835060954:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                    c2 = 15;
                    break;
                }
                break;
            case -324427448:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_6S)) {
                    c2 = 16;
                    break;
                }
                break;
            case -208296259:
                if (productId.equals(ProductId.ID_RC4)) {
                    c2 = 17;
                    break;
                }
                break;
            case -207348713:
                if (productId.equals(ProductId.ID_KEY_SWITCH_1)) {
                    c2 = 18;
                    break;
                }
                break;
            case -206567420:
                if (productId.equals(ProductId.ID_KEY_SWITCH_2)) {
                    c2 = 19;
                    break;
                }
                break;
            case -206510721:
                if (productId.equals(ProductId.ID_KEY_SWITCH_3)) {
                    c2 = 20;
                    break;
                }
                break;
            case -206454022:
                if (productId.equals(ProductId.ID_KEY_SWITCH_4)) {
                    c2 = 21;
                    break;
                }
                break;
            case 166485422:
                if (productId.equals(ProductId.ID_BLE_SWITCH)) {
                    c2 = 22;
                    break;
                }
                break;
            case 225641606:
                if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = 23;
                    break;
                }
                break;
            case 294483828:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_4S)) {
                    c2 = 24;
                    break;
                }
                break;
            case 356111630:
                if (productId.equals(ProductId.ID_AS_PANEL_U1S)) {
                    c2 = 25;
                    break;
                }
                break;
            case 356193315:
                if (productId.equals(ProductId.ID_AS_PANEL_U2S)) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                break;
            case 376429092:
                if (productId.equals(ProductId.ID_AS_PANEL_U4S)) {
                    c2 = 27;
                    break;
                }
                break;
            case 376488674:
                if (productId.equals(ProductId.ID_AS_PANEL_U5S)) {
                    c2 = 28;
                    break;
                }
                break;
            case 377377599:
                if (productId.equals(ProductId.ID_BODY_SENSOR)) {
                    c2 = 29;
                    break;
                }
                break;
            case 811752507:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI)) {
                    c2 = 30;
                    break;
                }
                break;
            case 956710656:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL)) {
                    c2 = 31;
                    break;
                }
                break;
            case 1097035898:
                if (productId.equals(ProductId.ID_SCENE_PANEL_S8)) {
                    c2 = ' ';
                    break;
                }
                break;
            case 1786777444:
                if (productId.equals(ProductId.ID_BLE_MUSIC_PLAYER)) {
                    c2 = '!';
                    break;
                }
                break;
            case 1921260107:
                if (productId.equals(ProductId.ID_BLE_DRY_CONTACT)) {
                    c2 = '\"';
                    break;
                }
                break;
            case 1951402182:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = '#';
                    break;
                }
                break;
            case 1951547293:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = '$';
                    break;
                }
                break;
            case 1976427583:
                if (productId.equals(ProductId.ID_BLE_CURTAIN)) {
                    c2 = '%';
                    break;
                }
                break;
            case 2002295507:
                if (productId.equals(ProductId.ID_CENTRE_AIR_GATEWAY)) {
                    c2 = Typography.amp;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_ble_light_tip));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_light);
                ((ActProductIntroduction1Binding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.ble_search));
                break;
            case 5:
            case 6:
            case 7:
            case '\b':
            case '\t':
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_wifi_light_tip));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_light);
                ((ActProductIntroduction1Binding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.next));
                break;
            case '\n':
                if (this.comeFromSettingPage) {
                    ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTitle.setText(getString(R.string.app_change_network_tip_title));
                    ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.app_change_network_wifi_gateway_tip));
                    ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_gateway_pic);
                } else {
                    ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTitle.setText(getString(R.string.add_device_tip_1));
                    ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_wifi_gateway_tip));
                    ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.wifi_gateway_pic_pair);
                }
                ((ActProductIntroduction1Binding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.next));
                break;
            case 11:
            case 31:
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_android_super_panel_tip));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_super_panel);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.scan_qr_code));
                break;
            case '\f':
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_android_super_panel_tip));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_12s_pic);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.scan_qr_code));
                break;
            case '\r':
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.app_str_add_mesh_curtain_guide));
                ((ActProductIntroduction1Binding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.ble_search));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_cgcur15_pic);
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_ble_dry_contact_tip));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_trig_pic);
                ((ActProductIntroduction1Binding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.ble_search));
                break;
            case 14:
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_android_super_panel_tip));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_g4_max);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.scan_qr_code));
                break;
            case 15:
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_switch_panel_s4_tip));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_s1c_pic);
                ((ActProductIntroduction1Binding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.ble_search));
                break;
            case 16:
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_android_super_panel_tip));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_6s_pic);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.scan_qr_code));
                break;
            case 17:
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_rc_tip));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_controller);
                ((ActProductIntroduction1Binding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.ble_search));
                break;
            case 18:
            case 19:
            case 20:
            case 21:
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_key_switch_tip));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_key_switch);
                ((ActProductIntroduction1Binding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.ble_search));
                break;
            case 22:
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.ble_open_close_notice));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_light);
                ((ActProductIntroduction1Binding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.ble_search));
                break;
            case 23:
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_switch_panel_s4_tip));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_s4m_pic);
                ((ActProductIntroduction1Binding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.ble_search));
                break;
            case 24:
            case 30:
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_android_super_panel_tip));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_superpanel_pic);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.scan_qr_code));
                break;
            case 25:
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_as_panel_tip));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_apanel_ub1);
                ((ActProductIntroduction1Binding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.ble_search));
                break;
            case 26:
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_as_panel_tip));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_apanel_ub2);
                ((ActProductIntroduction1Binding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.ble_search));
                break;
            case 27:
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_as_panel_tip));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_apanel_ub4);
                ((ActProductIntroduction1Binding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.ble_search));
                break;
            case 28:
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_as_panel_tip));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_apanel_ub5);
                ((ActProductIntroduction1Binding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.ble_search));
                break;
            case 29:
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_body_sensor_tip));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_sensor_pic);
                ((ActProductIntroduction1Binding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.ble_search));
                break;
            case ' ':
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_switch_panel_s4_tip));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_s8m_pic);
                ((ActProductIntroduction1Binding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.ble_search));
                break;
            case '!':
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_ble_music_player_tip));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_music);
                ((ActProductIntroduction1Binding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.ble_search));
                break;
            case '\"':
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_ble_dry_contact_tip));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_trig_pic);
                ((ActProductIntroduction1Binding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.ble_search));
                break;
            case '#':
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_switch_panel_s4_tip));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_s3c_pic);
                ((ActProductIntroduction1Binding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.ble_search));
                break;
            case '$':
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_switch_panel_s4_tip));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_s2c_pic);
                ((ActProductIntroduction1Binding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.ble_search));
                break;
            case '%':
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.app_str_add_mesh_curtain_guide));
                ((ActProductIntroduction1Binding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.ble_search));
                if (ConfigHelper.instance().productInfo.getCurtainType() == 1) {
                    ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_cgcur_pic);
                } else if (ConfigHelper.instance().productInfo.getCurtainType() == 2) {
                    ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_cgcur15_pic);
                }
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.app_str_add_mesh_curtain_guide));
                ((ActProductIntroduction1Binding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.ble_search));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_cgcur15_pic);
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_ble_dry_contact_tip));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_trig_pic);
                ((ActProductIntroduction1Binding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.ble_search));
                break;
            case '&':
                ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.centre_air_module_notice));
                ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_hvac_pic);
                ((ActProductIntroduction1Binding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.ble_search));
                break;
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private void nav() {
        if (ConfigHelper.instance().productInfo == null) {
            return;
        }
        String productId = ConfigHelper.instance().productInfo.getProductId();
        productId.hashCode();
        char c2 = 65535;
        switch (productId.hashCode()) {
            case -1805322688:
                if (productId.equals(ProductId.ID_BLE_LIGHT_DIM)) {
                    c2 = 0;
                    break;
                }
                break;
            case -1805199680:
                if (productId.equals(ProductId.ID_BLE_LIGHT_CT)) {
                    c2 = 1;
                    break;
                }
                break;
            case -1804340546:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGB)) {
                    c2 = 2;
                    break;
                }
                break;
            case -1804278081:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBW)) {
                    c2 = 3;
                    break;
                }
                break;
            case -1803448738:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBWY)) {
                    c2 = 4;
                    break;
                }
                break;
            case -1777527685:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_DIM)) {
                    c2 = 5;
                    break;
                }
                break;
            case -1777494050:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_CT)) {
                    c2 = 6;
                    break;
                }
                break;
            case -1776694498:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_RGB)) {
                    c2 = 7;
                    break;
                }
                break;
            case -1776638760:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_RGBW)) {
                    c2 = '\b';
                    break;
                }
                break;
            case -1776570529:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_RGBWY)) {
                    c2 = '\t';
                    break;
                }
                break;
            case -1698123058:
                if (productId.equals(ProductId.ID_MESH_GATEWAY)) {
                    c2 = '\n';
                    break;
                }
                break;
            case -1309274422:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO)) {
                    c2 = 11;
                    break;
                }
                break;
            case -1308265372:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_12S)) {
                    c2 = '\f';
                    break;
                }
                break;
            case -1287620343:
                if (productId.equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
                    c2 = '\r';
                    break;
                }
                break;
            case -1265646206:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                    c2 = 14;
                    break;
                }
                break;
            case -852623517:
                if (productId.equals(ProductId.ID_RC4S)) {
                    c2 = 15;
                    break;
                }
                break;
            case -835060954:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                    c2 = 16;
                    break;
                }
                break;
            case -324427448:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_6S)) {
                    c2 = 17;
                    break;
                }
                break;
            case -208296259:
                if (productId.equals(ProductId.ID_RC4)) {
                    c2 = 18;
                    break;
                }
                break;
            case -207348713:
                if (productId.equals(ProductId.ID_KEY_SWITCH_1)) {
                    c2 = 19;
                    break;
                }
                break;
            case -206567420:
                if (productId.equals(ProductId.ID_KEY_SWITCH_2)) {
                    c2 = 20;
                    break;
                }
                break;
            case -206510721:
                if (productId.equals(ProductId.ID_KEY_SWITCH_3)) {
                    c2 = 21;
                    break;
                }
                break;
            case -206454022:
                if (productId.equals(ProductId.ID_KEY_SWITCH_4)) {
                    c2 = 22;
                    break;
                }
                break;
            case 166485422:
                if (productId.equals(ProductId.ID_BLE_SWITCH)) {
                    c2 = 23;
                    break;
                }
                break;
            case 225641606:
                if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = 24;
                    break;
                }
                break;
            case 294483828:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_4S)) {
                    c2 = 25;
                    break;
                }
                break;
            case 356111630:
                if (productId.equals(ProductId.ID_AS_PANEL_U1S)) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                break;
            case 356193315:
                if (productId.equals(ProductId.ID_AS_PANEL_U2S)) {
                    c2 = 27;
                    break;
                }
                break;
            case 376429092:
                if (productId.equals(ProductId.ID_AS_PANEL_U4S)) {
                    c2 = 28;
                    break;
                }
                break;
            case 376488674:
                if (productId.equals(ProductId.ID_AS_PANEL_U5S)) {
                    c2 = 29;
                    break;
                }
                break;
            case 377377599:
                if (productId.equals(ProductId.ID_BODY_SENSOR)) {
                    c2 = 30;
                    break;
                }
                break;
            case 811752507:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI)) {
                    c2 = 31;
                    break;
                }
                break;
            case 956710656:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL)) {
                    c2 = ' ';
                    break;
                }
                break;
            case 1097035898:
                if (productId.equals(ProductId.ID_SCENE_PANEL_S8)) {
                    c2 = '!';
                    break;
                }
                break;
            case 1786777444:
                if (productId.equals(ProductId.ID_BLE_MUSIC_PLAYER)) {
                    c2 = '\"';
                    break;
                }
                break;
            case 1921260107:
                if (productId.equals(ProductId.ID_BLE_DRY_CONTACT)) {
                    c2 = '#';
                    break;
                }
                break;
            case 1951402182:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = '$';
                    break;
                }
                break;
            case 1951547293:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = '%';
                    break;
                }
                break;
            case 1976427583:
                if (productId.equals(ProductId.ID_BLE_CURTAIN)) {
                    c2 = Typography.amp;
                    break;
                }
                break;
            case 2002295507:
                if (productId.equals(ProductId.ID_CENTRE_AIR_GATEWAY)) {
                    c2 = '\'';
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case '\r':
            case 15:
            case 16:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case '!':
            case '\"':
            case '#':
            case '$':
            case '%':
            case '&':
            case '\'':
                NavUtils.destination(ActMeshScan.class).navigation(this);
                break;
            case 5:
            case 6:
            case 7:
            case '\b':
            case '\t':
            case '\n':
                NavUtils.destination(ActNetConfig.class).withBoolean(Constants.SETTING_PAGE, this.comeFromSettingPage).navigation(this);
                break;
            case 11:
            case '\f':
            case 14:
            case 17:
            case 25:
            case 31:
            case ' ':
                if (checkPhotoPermission()) {
                    NavUtils.destination(ActQrCodeScan.class).navigation(this);
                    break;
                }
                break;
        }
    }

    private boolean checkPhotoPermission() {
        int checkSelfPermission;
        if (Build.VERSION.SDK_INT >= 23) {
            checkSelfPermission = checkSelfPermission(Permission.CAMERA);
            if (checkSelfPermission != 0) {
                ActivityCompat.requestPermissions(this, new String[]{Permission.CAMERA}, 6666);
                return false;
            }
        }
        return true;
    }
}