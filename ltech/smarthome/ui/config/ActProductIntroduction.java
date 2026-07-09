package com.ltech.smarthome.ui.config;

import android.content.Intent;
import android.os.Build;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import com.alibaba.fastjson.parser.JSONLexer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActProductIntroductionBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.ifly.WebViewActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.TextClickUtil;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.yanzhenjie.permission.runtime.Permission;
import io.netty.util.internal.StringUtil;
import kotlin.text.Typography;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.lang3.ClassUtils;

/* loaded from: classes4.dex */
public class ActProductIntroduction extends BaseNormalActivity<ActProductIntroductionBinding> {
    private static final int REQUEST_ENABLE_BLUETOOTH = 1021;
    private static final int REQUEST_ENABLE_LOCATION = 1022;
    private boolean comeFromSettingPage;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_product_introduction;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setViewByProductId();
        ((ActProductIntroductionBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.config.ActProductIntroduction$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActProductIntroduction.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (view.getId() == R.id.bt_next) {
            check();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void check() {
        if (bleIsOk()) {
            if (!Injection.state().isLocationEnabled(this)) {
                MessageDialog.show(this, getString(R.string.tips), getString(R.string.open_gps_tip)).setCancelable(false).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.config.ActProductIntroduction$$ExternalSyntheticLambda2
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        boolean lambda$check$1;
                        lambda$check$1 = ActProductIntroduction.this.lambda$check$1(baseDialog, view);
                        return lambda$check$1;
                    }
                }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.config.ActProductIntroduction$$ExternalSyntheticLambda3
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        boolean lambda$check$2;
                        lambda$check$2 = ActProductIntroduction.this.lambda$check$2(baseDialog, view);
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
        getPermissionFail();
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, getString(R.string.faq_url)).navigation(this);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private void setViewByProductId() {
        if (ConfigHelper.instance().productInfo == null) {
            return;
        }
        this.comeFromSettingPage = getIntent().getBooleanExtra(Constants.SETTING_PAGE, false);
        final String productId = ConfigHelper.instance().productInfo.getProductId();
        String subProductKey = ConfigHelper.instance().productInfo.getSubProductKey();
        int i = (productId.equals(ProductId.ID_SMART_SWITCH_S1_PRO) || productId.equals(ProductId.ID_SMART_SWITCH_S2_PRO) || productId.equals(ProductId.ID_SMART_SWITCH_S3_PRO) || productId.equals(ProductId.ID_SMART_SWITCH_S6_PRO) || productId.equals(ProductId.ID_SMART_PANEL_G4) || productId.equals(ProductId.ID_SMART_PANEL_G4_PRO)) ? R.string.add_fail_tip_pro : R.string.add_fail_tip_new;
        AppCompatTextView appCompatTextView = ((ActProductIntroductionBinding) this.mViewBinding).tvFailTip;
        ClickableSpan clickableSpan = new ClickableSpan() { // from class: com.ltech.smarthome.ui.config.ActProductIntroduction.1
            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ActProductIntroduction.this.getResources().getColor(R.color.color_text_red));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                if (productId.equals(ProductId.ID_MESH_GATEWAY)) {
                    NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, ActProductIntroduction.this.getString(R.string.faq_url)).navigation(ActProductIntroduction.this);
                } else {
                    NavUtils.destination(ActStepsIntroduction.class).navigation(ActProductIntroduction.this);
                }
            }
        };
        int i2 = productId.equals(ProductId.ID_MESH_GATEWAY) ? R.string.tip_help : R.string.step_by_step_guide;
        if (productId.equals(ProductId.ID_MESH_GATEWAY)) {
            i = R.string.add_fail_tip;
        }
        TextClickUtil.setTextClick(this, appCompatTextView, clickableSpan, i2, i);
        ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.app_str_network_config_ble_default_tip));
        ((ActProductIntroductionBinding) this.mViewBinding).btNext.setText(getString(R.string.ble_search));
        productId.hashCode();
        char c2 = 65535;
        switch (productId.hashCode()) {
            case -2060969856:
                if (productId.equals(ProductId.ID_AS_PANEL_UB8)) {
                    c2 = 0;
                    break;
                }
                break;
            case -1822884084:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB6)) {
                    c2 = 1;
                    break;
                }
                break;
            case -1819630261:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1_PRO)) {
                    c2 = 2;
                    break;
                }
                break;
            case -1817691924:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2_PRO)) {
                    c2 = 3;
                    break;
                }
                break;
            case -1805322688:
                if (productId.equals(ProductId.ID_BLE_LIGHT_DIM)) {
                    c2 = 4;
                    break;
                }
                break;
            case -1805199680:
                if (productId.equals(ProductId.ID_BLE_LIGHT_CT)) {
                    c2 = 5;
                    break;
                }
                break;
            case -1804340546:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGB)) {
                    c2 = 6;
                    break;
                }
                break;
            case -1804278081:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBW)) {
                    c2 = 7;
                    break;
                }
                break;
            case -1803448738:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBWY)) {
                    c2 = '\b';
                    break;
                }
                break;
            case -1796419228:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                    c2 = '\t';
                    break;
                }
                break;
            case -1777527685:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_DIM)) {
                    c2 = '\n';
                    break;
                }
                break;
            case -1777494050:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_CT)) {
                    c2 = 11;
                    break;
                }
                break;
            case -1776694498:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_RGB)) {
                    c2 = '\f';
                    break;
                }
                break;
            case -1776638760:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_RGBW)) {
                    c2 = '\r';
                    break;
                }
                break;
            case -1776570529:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_RGBWY)) {
                    c2 = 14;
                    break;
                }
                break;
            case -1710907378:
                if (productId.equals(ProductId.ID_BLE_KBS)) {
                    c2 = 15;
                    break;
                }
                break;
            case -1698123058:
                if (productId.equals(ProductId.ID_MESH_GATEWAY)) {
                    c2 = 16;
                    break;
                }
                break;
            case -1642166464:
                if (productId.equals(ProductId.ID_BLE_HAM)) {
                    c2 = 17;
                    break;
                }
                break;
            case -1550133760:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB1)) {
                    c2 = 18;
                    break;
                }
                break;
            case -1343252468:
                if (productId.equals(ProductId.ID_RS485_BLE)) {
                    c2 = 19;
                    break;
                }
                break;
            case -1308265372:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_12S)) {
                    c2 = 20;
                    break;
                }
                break;
            case -1287620343:
                if (productId.equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
                    c2 = 21;
                    break;
                }
                break;
            case -1273434493:
                if (productId.equals(ProductId.ID_SENSOR_MR04)) {
                    c2 = 22;
                    break;
                }
                break;
            case -1201890867:
                if (productId.equals(ProductId.ID_SMART_PANEL_GQ_PRO)) {
                    c2 = 23;
                    break;
                }
                break;
            case -1084555505:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                    c2 = 24;
                    break;
                }
                break;
            case -1082613022:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6)) {
                    c2 = 25;
                    break;
                }
                break;
            case -1073881216:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB8)) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                break;
            case -969622016:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4_PRO)) {
                    c2 = 27;
                    break;
                }
                break;
            case -835060954:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                    c2 = 28;
                    break;
                }
                break;
            case -833770237:
                if (productId.equals(ProductId.ID_SMART_SWITCH_SQ)) {
                    c2 = 29;
                    break;
                }
                break;
            case -732569219:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S4)) {
                    c2 = 30;
                    break;
                }
                break;
            case -728269602:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6T)) {
                    c2 = 31;
                    break;
                }
                break;
            case -324427448:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_6S)) {
                    c2 = ' ';
                    break;
                }
                break;
            case -208296259:
                if (productId.equals(ProductId.ID_RC4)) {
                    c2 = '!';
                    break;
                }
                break;
            case -207348713:
                if (productId.equals(ProductId.ID_KEY_SWITCH_1)) {
                    c2 = '\"';
                    break;
                }
                break;
            case -206567420:
                if (productId.equals(ProductId.ID_KEY_SWITCH_2)) {
                    c2 = '#';
                    break;
                }
                break;
            case -206510721:
                if (productId.equals(ProductId.ID_KEY_SWITCH_3)) {
                    c2 = '$';
                    break;
                }
                break;
            case -206454022:
                if (productId.equals(ProductId.ID_KEY_SWITCH_4)) {
                    c2 = '%';
                    break;
                }
                break;
            case 13862565:
                if (productId.equals(ProductId.ID_BLE_RS8)) {
                    c2 = Typography.amp;
                    break;
                }
                break;
            case 70457728:
                if (productId.equals(ProductId.ID_DRY_CONTACT_TO_BLE)) {
                    c2 = '\'';
                    break;
                }
                break;
            case 166485422:
                if (productId.equals(ProductId.ID_BLE_SWITCH)) {
                    c2 = '(';
                    break;
                }
                break;
            case 186184655:
                if (productId.equals(ProductId.ID_SENSOR_MR03)) {
                    c2 = ')';
                    break;
                }
                break;
            case 225641606:
                if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = '*';
                    break;
                }
                break;
            case 294483828:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_4S)) {
                    c2 = '+';
                    break;
                }
                break;
            case 312618751:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6M)) {
                    c2 = StringUtil.COMMA;
                    break;
                }
                break;
            case 353722044:
                if (productId.equals(ProductId.ID_WIFI_CAMERA)) {
                    c2 = Soundex.SILENT_MARKER;
                    break;
                }
                break;
            case 356111630:
                if (productId.equals(ProductId.ID_AS_PANEL_U1S)) {
                    c2 = ClassUtils.PACKAGE_SEPARATOR_CHAR;
                    break;
                }
                break;
            case 356193315:
                if (productId.equals(ProductId.ID_AS_PANEL_U2S)) {
                    c2 = '/';
                    break;
                }
                break;
            case 359647590:
                if (productId.equals(ProductId.ID_SMART_SWITCH_SQ_PRO)) {
                    c2 = '0';
                    break;
                }
                break;
            case 376429092:
                if (productId.equals(ProductId.ID_AS_PANEL_U4S)) {
                    c2 = '1';
                    break;
                }
                break;
            case 376488674:
                if (productId.equals(ProductId.ID_AS_PANEL_U5S)) {
                    c2 = '2';
                    break;
                }
                break;
            case 377377599:
                if (productId.equals(ProductId.ID_BODY_SENSOR)) {
                    c2 = '3';
                    break;
                }
                break;
            case 414687077:
                if (productId.equals(ProductId.ID_BLE_LIGHT_CGD_PRO)) {
                    c2 = '4';
                    break;
                }
                break;
            case 427686243:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4)) {
                    c2 = '5';
                    break;
                }
                break;
            case 439998223:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6D)) {
                    c2 = '6';
                    break;
                }
                break;
            case 534249931:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB2)) {
                    c2 = '7';
                    break;
                }
                break;
            case 612512450:
                if (productId.equals(ProductId.ID_HOME_KIT)) {
                    c2 = '8';
                    break;
                }
                break;
            case 613226983:
                if (productId.equals(ProductId.ID_SMART_SWITCH_SQB)) {
                    c2 = '9';
                    break;
                }
                break;
            case 662799966:
                if (productId.equals(ProductId.ID_BLE_LIGHT_SPI)) {
                    c2 = ':';
                    break;
                }
                break;
            case 811752507:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI)) {
                    c2 = ';';
                    break;
                }
                break;
            case 956710656:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL)) {
                    c2 = Typography.less;
                    break;
                }
                break;
            case 1097035898:
                if (productId.equals(ProductId.ID_SCENE_PANEL_S8)) {
                    c2 = '=';
                    break;
                }
                break;
            case 1181428532:
                if (productId.equals(ProductId.ID_SMART_PANEL_GQ)) {
                    c2 = Typography.greater;
                    break;
                }
                break;
            case 1309050445:
                if (productId.equals(ProductId.ID_SMART_PANEL_GQX)) {
                    c2 = '?';
                    break;
                }
                break;
            case 1479279198:
                if (productId.equals(ProductId.ID_SENSOR_MS03)) {
                    c2 = '@';
                    break;
                }
                break;
            case 1647983530:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6A)) {
                    c2 = 'A';
                    break;
                }
                break;
            case 1786777444:
                if (productId.equals(ProductId.ID_BLE_MUSIC_PLAYER)) {
                    c2 = 'B';
                    break;
                }
                break;
            case 1861788715:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB5)) {
                    c2 = 'C';
                    break;
                }
                break;
            case 1921260107:
                if (productId.equals(ProductId.ID_BLE_DRY_CONTACT)) {
                    c2 = 'D';
                    break;
                }
                break;
            case 1951402182:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = 'E';
                    break;
                }
                break;
            case 1951547293:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = 'F';
                    break;
                }
                break;
            case 1976427583:
                if (productId.equals(ProductId.ID_BLE_CURTAIN)) {
                    c2 = 'G';
                    break;
                }
                break;
            case 2002295507:
                if (productId.equals(ProductId.ID_CENTRE_AIR_GATEWAY)) {
                    c2 = 'H';
                    break;
                }
                break;
            case 2088187733:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4TE)) {
                    c2 = 'I';
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_ub8);
                break;
            case 1:
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_eb6);
                break;
            case 2:
                ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.app_str_network_config_ble_smart_panel_pro_tip));
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_s1_pro_sel);
                break;
            case 3:
                ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.app_str_network_config_ble_smart_panel_pro_tip));
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_s2_pro_sel);
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
            case '(':
            case '4':
                ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.app_str_network_config_ble_light_tip));
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_light_sel);
                break;
            case '\t':
                ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.app_str_network_config_ble_smart_panel_pro_tip));
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_s3_pro_sel);
                break;
            case '\n':
            case 11:
            case '\f':
            case '\r':
            case 14:
                ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_wifi_light_tip));
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_light);
                ((ActProductIntroductionBinding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroductionBinding) this.mViewBinding).btNext.setText(getString(R.string.next));
                break;
            case 15:
                ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.app_str_network_config_ble_light_tip));
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_pic_kbs);
                break;
            case 16:
                if (this.comeFromSettingPage) {
                    ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTitle.setText(getString(R.string.app_change_network_tip_title));
                    ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.app_change_network_wifi_gateway_tip));
                    ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_gateway_pic);
                } else {
                    ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTitle.setText(getString(R.string.add_device_tip_1));
                    ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_wifi_gateway_tip));
                    ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_wifi_gateway);
                }
                ((ActProductIntroductionBinding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroductionBinding) this.mViewBinding).btNext.setText(getString(R.string.next));
                break;
            case 17:
                ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.app_str_network_config_ble_ham_tip));
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_gateway_4);
                break;
            case 18:
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_eb1);
                break;
            case 19:
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.ic_add_cg_link_1);
                break;
            case 20:
            case ' ':
            case '+':
            case ';':
                ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_android_super_panel_tip));
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_superpanel_pic);
                ((ActProductIntroductionBinding) this.mViewBinding).btNext.setText(getString(R.string.scan_qr_code));
                break;
            case 21:
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_cgcurh3);
                break;
            case 22:
            case ')':
            case '@':
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource((productId.equals(ProductId.ID_SENSOR_MR03) || productId.equals(ProductId.ID_SENSOR_MS03)) ? R.mipmap.add_sensor_mr03_pic : R.mipmap.add_sensor_mr04_pic);
                ((ActProductIntroductionBinding) this.mViewBinding).tvFailTip.setVisibility(0);
                break;
            case 23:
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_cqpro);
                break;
            case 24:
                ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.app_str_network_config_ble_smart_panel_pro_tip));
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_s6pro);
                break;
            case 25:
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_s6);
                break;
            case 26:
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_eb8);
                break;
            case 27:
                ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.app_str_network_config_ble_smart_panel_pro_tip));
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(LanguageUtils.isChinese(this) ? R.mipmap.add_g4pro_1 : R.mipmap.add_g4pro_1_en);
                break;
            case 28:
                if (subProductKey.equals("09")) {
                    ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_s1c_sel);
                    break;
                } else if (subProductKey.equals("11")) {
                    ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_s1_sel);
                    break;
                }
                break;
            case 29:
            case '9':
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_sq_2);
                break;
            case 30:
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_s4);
                break;
            case 31:
            case ',':
            case '6':
            case 'A':
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.ic_add_e6d_1);
                break;
            case '!':
                ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_rc_tip));
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_controller);
                break;
            case '\"':
            case '#':
            case '$':
            case '%':
                ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_key_switch_tip));
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_key_switch);
                ((ActProductIntroductionBinding) this.mViewBinding).tvFailTip.setVisibility(0);
                break;
            case '&':
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.ic_add_cg_rs8_1);
                break;
            case '\'':
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_cgtrig_to_ble_4);
                break;
            case '*':
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_s4m_sel);
                break;
            case '-':
                ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.app_str_network_config_wifi_camera_tip));
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_camera_scan);
                ((ActProductIntroductionBinding) this.mViewBinding).tvFailTip.setVisibility(0);
                ((ActProductIntroductionBinding) this.mViewBinding).btNext.setText(getString(R.string.scan_qr_code));
                break;
            case '.':
                ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.app_str_network_config_ble_us_tip));
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_apanel_ub1);
                break;
            case '/':
                ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.app_str_network_config_ble_us_tip));
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_apanel_ub2);
                break;
            case '0':
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_sq_pro);
                break;
            case '1':
                ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.app_str_network_config_ble_us_tip));
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_apanel_ub4);
                break;
            case '2':
                ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.app_str_network_config_ble_us_tip));
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_apanel_ub5);
                break;
            case '3':
                ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_body_sensor_tip));
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_sensor_pic);
                ((ActProductIntroductionBinding) this.mViewBinding).tvFailTip.setVisibility(0);
                break;
            case '5':
                ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.app_str_network_config_ble_smart_panel_pro_tip));
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(LanguageUtils.isChinese(this) ? R.mipmap.add_g4_1 : R.mipmap.add_g4_1_en);
                break;
            case '7':
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_eb2);
                break;
            case '8':
                ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_home_kit_tip));
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_cgkit_3_on);
                ((ActProductIntroductionBinding) this.mViewBinding).tvFailTip.setVisibility(0);
                break;
            case ':':
                ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.app_str_network_config_ble_us_tip));
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_cgspi);
                break;
            case '<':
                ((ActProductIntroductionBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_android_super_panel_tip));
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_super_panel);
                ((ActProductIntroductionBinding) this.mViewBinding).btNext.setText(getString(R.string.scan_qr_code));
                break;
            case '=':
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_s8m_sel);
                break;
            case '>':
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_gq);
                break;
            case '?':
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_gqx);
                break;
            case 'B':
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_cgaudio);
                break;
            case 'C':
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_eb5);
                break;
            case 'D':
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_cgtrig_sel);
                break;
            case 'E':
                if (subProductKey.equals("0B")) {
                    ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_s3c_sel);
                    break;
                } else if (subProductKey.equals("13")) {
                    ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_s3_sel);
                    break;
                }
                break;
            case 'F':
                if (subProductKey.equals("0A")) {
                    ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_s2c_sel);
                    break;
                } else if (subProductKey.equals("12")) {
                    ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_s2_sel);
                    break;
                }
                break;
            case 'G':
                if (ConfigHelper.instance().productInfo.getCurtainType() == 1) {
                    ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_cgcur_pic);
                    break;
                } else if (ConfigHelper.instance().productInfo.getCurtainType() == 2) {
                    ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_cgcur15);
                    break;
                }
                break;
            case 'H':
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_cgair);
                break;
            case 'I':
                ((ActProductIntroductionBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_add_g4te);
                break;
        }
    }

    private void nav() {
        if (ConfigHelper.instance().productInfo == null) {
            return;
        }
        String productId = ConfigHelper.instance().productInfo.getProductId();
        productId.hashCode();
        switch (productId) {
            case "120033108344501":
            case "120033108345901":
            case "120033108351401":
            case "120033108353001":
            case "120033108355901":
            case "120102615405001":
                NavUtils.destination(ActNetConfig.class).withBoolean(Constants.SETTING_PAGE, this.comeFromSettingPage).withString(Constants.PRODUCT_ID, ConfigHelper.instance().productInfo.getProductId()).navigation(this);
                break;
            case "121111911552501":
            case "121052512023201":
            case "120010615085201":
                if (checkPhotoPermission()) {
                    NavUtils.destination(ActQrCodeScan.class).navigation(this);
                    break;
                }
                break;
            default:
                NavUtils.destination(ActMeshNearDevice.class).navigation(this);
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