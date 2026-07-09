package com.ltech.smarthome.ui.device.rs8;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActRs8AddressWriteBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.utils.Constants;

/* loaded from: classes4.dex */
public class ActRs8AddressWrite extends VMActivity<ActRs8AddressWriteBinding, ActRs8VM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_rs8_address_write;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.add_cg_rs8_write_address));
        ((ActRs8AddressWriteBinding) this.mViewBinding).rdbtnSingle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8AddressWrite.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b2) {
                if (b2) {
                    ((ActRs8VM) ActRs8AddressWrite.this.mViewModel).addressType = 1;
                    ((ActRs8VM) ActRs8AddressWrite.this.mViewModel).isGroup.setValue(false);
                    ((ActRs8AddressWriteBinding) ActRs8AddressWrite.this.mViewBinding).iv.setImageResource(R.mipmap.img_curtainmotors);
                }
            }
        });
        ((ActRs8AddressWriteBinding) this.mViewBinding).rdbtnGroup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8AddressWrite.2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b2) {
                if (b2) {
                    ((ActRs8VM) ActRs8AddressWrite.this.mViewModel).addressType = 2;
                    ((ActRs8VM) ActRs8AddressWrite.this.mViewModel).address.setValue("");
                    ((ActRs8VM) ActRs8AddressWrite.this.mViewModel).isGroup.setValue(true);
                    ((ActRs8AddressWriteBinding) ActRs8AddressWrite.this.mViewBinding).iv.setImageResource(R.mipmap.img_curgroup);
                }
            }
        });
        ((ActRs8AddressWriteBinding) this.mViewBinding).editAddress.addTextChangedListener(new TextWatcher() { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8AddressWrite.3
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                ((ActRs8VM) ActRs8AddressWrite.this.mViewModel).address.setValue(editable.toString());
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActRs8VM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActRs8VM) this.mViewModel).placeId = Injection.repo().home().getSelPlace().getPlaceId();
        ((ActRs8VM) this.mViewModel).categoryId = getIntent().getLongExtra(Constants.CATEGORY_ID, -1L);
        ((ActRs8VM) this.mViewModel).brandId = getIntent().getLongExtra(Constants.BRAND_ID, -1L);
        ((ActRs8VM) this.mViewModel).img = getIntent().getStringExtra("image");
        ((ActRs8VM) this.mViewModel).deviceName = getIntent().getStringExtra("device_name");
        ((ActRs8VM) this.mViewModel).device = Injection.repo().device().getDeviceById(((ActRs8VM) this.mViewModel).controlId);
        ((ActRs8AddressWriteBinding) this.mViewBinding).tvTip.setText(getIntent().getStringExtra(Constants.TIP));
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            finishActivity();
        }
    }
}