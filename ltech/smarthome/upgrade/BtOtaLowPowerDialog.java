package com.ltech.smarthome.upgrade;

import android.app.Activity;
import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.databinding.ActBtOtaLowPowerBinding;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;

/* loaded from: classes4.dex */
public class BtOtaLowPowerDialog extends SmartDialog<ActBtOtaLowPowerBinding> {
    private String address;
    private long controlId;
    private String productId;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.act_bt_ota_low_power;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "BtOtaLowPowerDialog";
    }

    public static BtOtaLowPowerDialog as(final Activity activity) {
        BtOtaLowPowerDialog btOtaLowPowerDialog = new BtOtaLowPowerDialog();
        btOtaLowPowerDialog.setViewConverter(new SmartDialog.ViewConverter<ActBtOtaLowPowerBinding, BtOtaLowPowerDialog>() { // from class: com.ltech.smarthome.upgrade.BtOtaLowPowerDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(ActBtOtaLowPowerBinding viewBinding, final BtOtaLowPowerDialog dialog) {
                String string = dialog.getString(R.string.app_str_low_power_bt_ota_tip1);
                String str = dialog.productId;
                str.hashCode();
                char c2 = 65535;
                switch (str.hashCode()) {
                    case -961541705:
                        if (str.equals(ProductId.ID_SMART_PANEL_S6B)) {
                            c2 = 0;
                            break;
                        }
                        break;
                    case -852623517:
                        if (str.equals(ProductId.ID_RC4S)) {
                            c2 = 1;
                            break;
                        }
                        break;
                    case -208296259:
                        if (str.equals(ProductId.ID_RC4)) {
                            c2 = 2;
                            break;
                        }
                        break;
                    case 377377599:
                        if (str.equals(ProductId.ID_BODY_SENSOR)) {
                            c2 = 3;
                            break;
                        }
                        break;
                    case 613226983:
                        if (str.equals(ProductId.ID_SMART_SWITCH_SQB)) {
                            c2 = 4;
                            break;
                        }
                        break;
                }
                int i = R.mipmap.ps_pic_clickfast;
                switch (c2) {
                    case 0:
                        string = dialog.getString(R.string.app_str_low_power_bt_ota_tip5);
                        break;
                    case 1:
                        string = dialog.getString(R.string.app_str_low_power_bt_ota_tip3);
                        i = R.mipmap.rc4sble_2;
                        break;
                    case 2:
                        string = dialog.getString(R.string.app_str_low_power_bt_ota_tip3);
                        i = R.mipmap.rc4_pic_clickfast;
                        break;
                    case 3:
                        string = dialog.getString(R.string.app_str_low_power_bt_ota_tip2);
                        i = R.mipmap.hs_pic_clickfast;
                        break;
                    case 4:
                        string = dialog.getString(R.string.app_str_low_power_bt_ota_tip4);
                        i = R.mipmap.pic_click_fast;
                        break;
                }
                viewBinding.tvTitle.setText(string);
                viewBinding.ivBg.setImageResource(i);
                viewBinding.tvOk.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.upgrade.BtOtaLowPowerDialog.1.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        dialog.dismissDialog();
                        NavUtils.destination(ActBtOtaSingle.class).withLong(Constants.CONTROL_ID, dialog.controlId).withString(Constants.PRODUCT_ID, dialog.productId).withString("DEVICE_ADDRESS", dialog.address).navigation(activity);
                    }
                });
            }
        }).setOutCancel(true).setMargin(0).setGravity(80);
        return btOtaLowPowerDialog;
    }

    public BtOtaLowPowerDialog setProductId(String id) {
        this.productId = id;
        return this;
    }

    public BtOtaLowPowerDialog setControlId(long id) {
        this.controlId = id;
        return this;
    }

    public BtOtaLowPowerDialog setAddress(String address) {
        this.address = address;
        return this;
    }
}