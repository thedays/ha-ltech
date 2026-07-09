package com.ltech.smarthome.ui.home;

import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActHomeQrCodeBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.utils.Constants;
import java.util.List;

/* loaded from: classes4.dex */
public class ActHomeQrCode extends BaseNormalActivity<ActHomeQrCodeBinding> {
    private Listing<Place> placeListing;
    private int qrCodewidth;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_home_qr_code;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.home_qr_code));
        setBackImage(R.mipmap.icon_back);
        Listing<Place> place = Injection.repo().home().getPlace(this, getIntent().getLongExtra(Constants.PLACE_ID, 0L));
        this.placeListing = place;
        handleData(place, new IAction() { // from class: com.ltech.smarthome.ui.home.ActHomeQrCode$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActHomeQrCode.this.lambda$initView$0((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(List list) {
        ((ActHomeQrCodeBinding) this.mViewBinding).tvHomeName.setText(getString(R.string.qr_code_home_name, new Object[]{((Place) list.get(0)).getPlaceName()}));
        Glide.with((FragmentActivity) this).load(((Place) list.get(0)).getQrCode()).into(((ActHomeQrCodeBinding) this.mViewBinding).ivQrCode);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        this.placeListing.retry();
    }
}