package com.ltech.smarthome.ui.config;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActStepsIntroductionBinding;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.DeviceDeleteStepsHelper;
import java.util.List;

/* loaded from: classes4.dex */
public class ActStepsDelete extends BaseNormalActivity<ActStepsIntroductionBinding> implements OnStepsIntroductionListener {
    private List<Fragment> fragments;
    private int mCurPostion = 0;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_steps_introduction;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setViewByProductId();
        initViewPager();
    }

    private void initViewPager() {
        ((ActStepsIntroductionBinding) this.mViewBinding).viewpager.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.config.ActStepsDelete.1
            @Override // androidx.viewpager2.adapter.FragmentStateAdapter
            public Fragment createFragment(int position) {
                return (Fragment) ActStepsDelete.this.fragments.get(position);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return ActStepsDelete.this.fragments.size();
            }
        });
        ((ActStepsIntroductionBinding) this.mViewBinding).viewpager.setUserInputEnabled(false);
        ((ActStepsIntroductionBinding) this.mViewBinding).viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.ltech.smarthome.ui.config.ActStepsDelete.2
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                ActStepsDelete.this.mCurPostion = position;
            }
        });
    }

    private void setViewByProductId() {
        String stringExtra;
        stringExtra = getIntent().getStringExtra(Constants.PRODUCT_ID);
        stringExtra.hashCode();
        switch (stringExtra) {
            case "3503908278750336":
            case "3508084028410496":
            case "3503908725640320":
            case "3503907950824576":
                this.fragments = rcBSteps(stringExtra);
                break;
            case "4057094887997440":
                this.fragments = rc4SSteps();
                break;
            case "120042314364601":
                this.fragments = rc4Steps();
                break;
            case "120042314375001":
            case "120042314380701":
            case "120042314382401":
            case "120042314384101":
                this.fragments = swichSteps();
                break;
            case "120042616112401":
                this.fragments = bodySensorSteps();
                break;
            case "3721596935046208":
            case "3959367613661440":
                this.fragments = gqSteps(stringExtra);
                break;
        }
    }

    private List<Fragment> bodySensorSteps() {
        return new DeviceDeleteStepsHelper().withNormalStep(getString(R.string.app_str_ble_del), R.mipmap.bg_del_1).withNormalStep(getString(R.string.app_str_ble_switch_body_sensor_del), R.mipmap.pic_add_hs_4).getData();
    }

    private List<Fragment> swichSteps() {
        return new DeviceDeleteStepsHelper().withNormalStep(getString(R.string.app_str_ble_del), R.mipmap.bg_del_1).withNormalStep(getString(R.string.app_str_ble_switch_del), R.mipmap.pic_add_ps_2).getData();
    }

    private List<Fragment> rc4Steps() {
        return new DeviceDeleteStepsHelper().withNormalStep(getString(R.string.app_str_ble_del), R.mipmap.bg_del_1).withNormalStep(getString(R.string.app_str_ble_switch_rc4_del), R.mipmap.bg_del_rc4).getData();
    }

    private List<Fragment> rc4SSteps() {
        return new DeviceDeleteStepsHelper().withNormalStep(getString(R.string.app_str_ble_del), R.mipmap.bg_del_1).withNormalStep(getString(R.string.app_str_ble_switch_rc4s_del), R.mipmap.rc4sble_2).getData();
    }

    private List<Fragment> gqSteps(String productId) {
        return new DeviceDeleteStepsHelper().withNormalStep(getString(R.string.app_str_ble_del), R.mipmap.bg_del_1).withNormalStep(getString(R.string.gqx_click_tip), ProductId.ID_SMART_PANEL_GQ.equals(productId) ? R.mipmap.gq_pic_click1 : R.mipmap.gqx_pic_click1).getData();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private List<Fragment> rcBSteps(String productId) {
        productId.hashCode();
        int i = 0;
        char c2 = 65535;
        switch (productId.hashCode()) {
            case -1281119909:
                if (productId.equals(ProductId.ID_RC_B2)) {
                    c2 = 0;
                    break;
                }
                break;
            case -1213322926:
                if (productId.equals(ProductId.ID_RC_B8)) {
                    c2 = 1;
                    break;
                }
                break;
            case -249671171:
                if (productId.equals(ProductId.ID_RC_B5)) {
                    c2 = 2;
                    break;
                }
                break;
            case 1473345811:
                if (productId.equals(ProductId.ID_RC_B1)) {
                    c2 = 3;
                    break;
                }
                break;
        }
        int i2 = R.string.rc_b2_del_tip;
        switch (c2) {
            case 0:
                i = R.mipmap.pic_click_tip_rc_b2;
                break;
            case 1:
                i = R.mipmap.pic_click_tip_rc_b8;
                i2 = R.string.rc_b8_del_tip;
                break;
            case 2:
                i = R.mipmap.pic_click_tip_rc_b5;
                break;
            case 3:
                i = R.mipmap.pic_click_tip_rc_b1;
                i2 = R.string.rc_b8_del_tip;
                break;
            default:
                i2 = R.string.rc_b8_del_tip;
                break;
        }
        return new DeviceDeleteStepsHelper().withNormalStep(getString(R.string.app_str_ble_del), R.mipmap.bg_del_1).withNormalStep(getString(i2), i).getData();
    }

    @Override // com.ltech.smarthome.ui.config.OnStepsIntroductionListener
    public void next() {
        if (this.mCurPostion == this.fragments.size() - 1) {
            return;
        }
        this.mCurPostion++;
        ((ActStepsIntroductionBinding) this.mViewBinding).viewpager.setCurrentItem(this.mCurPostion, true);
    }

    @Override // com.ltech.smarthome.ui.config.OnStepsIntroductionListener
    public void previous() {
        int i = this.mCurPostion;
        if (i == 0) {
            return;
        }
        this.mCurPostion = i - 1;
        ((ActStepsIntroductionBinding) this.mViewBinding).viewpager.setCurrentItem(this.mCurPostion, true);
    }
}