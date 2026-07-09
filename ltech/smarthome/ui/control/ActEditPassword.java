package com.ltech.smarthome.ui.control;

import android.text.Editable;
import android.text.TextWatcher;
import androidx.core.content.ContextCompat;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActEditNumberBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.nfc.ActAddVirtualDevice;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;

/* loaded from: classes4.dex */
public class ActEditPassword extends BaseNormalActivity<ActEditNumberBinding> {
    private static final String PWD = "888888";
    private boolean pwdCorrect;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_edit_number;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.enter_password));
        setEditString(getString(R.string.confirm));
        ((ActEditNumberBinding) this.mViewBinding).title.tvEdit.setTextColor(ContextCompat.getColor(this, R.color.color_text_gray));
        ((ActEditNumberBinding) this.mViewBinding).etContent.addTextChangedListener(new TextWatcher() { // from class: com.ltech.smarthome.ui.control.ActEditPassword.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 6) {
                    ((ActEditNumberBinding) ActEditPassword.this.mViewBinding).tvTip.setVisibility(8);
                    if (!ActEditPassword.PWD.equals(s.toString())) {
                        ((ActEditNumberBinding) ActEditPassword.this.mViewBinding).tvError.setVisibility(0);
                        return;
                    }
                    ActEditPassword.this.pwdCorrect = true;
                    ((ActEditNumberBinding) ActEditPassword.this.mViewBinding).title.tvEdit.setTextColor(ContextCompat.getColor(ActEditPassword.this.activity, R.color.color_text_black));
                    ((ActEditNumberBinding) ActEditPassword.this.mViewBinding).tvError.setVisibility(8);
                    return;
                }
                ((ActEditNumberBinding) ActEditPassword.this.mViewBinding).tvTip.setVisibility(0);
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (this.pwdCorrect) {
            finishActivity();
            NavUtils.destination(ActAddVirtualDevice.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).navigation(this);
        }
    }
}