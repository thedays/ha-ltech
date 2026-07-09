package com.ltech.smarthome.ui.device.ir;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzy.tvmao.KookongSDK;
import com.hzy.tvmao.interf.IRequestResult;
import com.kookong.app.data.SpList;
import com.ltech.imageclip.fragment.ActivityResultHelper;
import com.ltech.imageclip.util.FileUtil;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActSelectOperatorsBinding;
import com.ltech.smarthome.model.extra.CityList;
import com.ltech.smarthome.ui.config.ConfigHelper;
import com.ltech.smarthome.ui.permission.ActGetLocationPermission;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.view.dialog.CityPickerDialog;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActSelectOperators extends BaseNormalActivity<ActSelectOperatorsBinding> {
    private BaseQuickAdapter<SpList.Sp, BaseViewHolder> mAdapter;
    private AMapLocationClient mlocationClient;
    private String province = "";
    private String city = "";
    private String district = "";
    private int areaId = -1;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_operators;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        stopLocate();
        super.onDestroy();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        if (TextUtils.isEmpty(this.province) && TextUtils.isEmpty(this.city) && TextUtils.isEmpty(this.district)) {
            startLocate();
        } else {
            getAreaId();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.select_operators));
        ActivityResultHelper.init(this).startActivityForResult(ActGetLocationPermission.class, new ActivityResultHelper.Callback() { // from class: com.ltech.smarthome.ui.device.ir.ActSelectOperators$$ExternalSyntheticLambda0
            @Override // com.ltech.imageclip.fragment.ActivityResultHelper.Callback
            public final void onActivityResult(int i, Intent intent) {
                ActSelectOperators.this.lambda$initView$0(i, intent);
            }
        });
        ((ActSelectOperatorsBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.ir.ActSelectOperators$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActSelectOperators.this.lambda$initView$2((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(int i, Intent intent) {
        if (100 == i) {
            setView();
            KookongSDK.init(getApplicationContext(), ActSelectBrand.INIT_KEY, ConfigHelper.instance().mac.replaceAll(Constants.COLON_SEPARATOR, "").toUpperCase());
            startLocate();
            return;
        }
        back();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(View view) {
        CityPickerDialog.asDefault().setCityList((CityList) GsonUtils.fromJson(FileUtil.readAssets(this, "city.txt"), CityList.class)).setSelectListener(new CityPickerDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.ir.ActSelectOperators$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.view.dialog.CityPickerDialog.SelectListener
            public final void onSelect(String str, String str2, String str3) {
                ActSelectOperators.this.lambda$initView$1(str, str2, str3);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str3)) {
            this.province = "";
            this.city = str;
            this.district = str2;
        } else {
            this.province = str;
            this.city = str2;
            this.district = str3;
        }
        refreshLocationInfo();
        getAreaId();
    }

    private void setView() {
        BaseQuickAdapter<SpList.Sp, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<SpList.Sp, BaseViewHolder>(this, R.layout.item_go_1, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.ir.ActSelectOperators.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, SpList.Sp item) {
                helper.setText(R.id.tv_main, item.spName).setImageResource(R.id.iv_go, R.mipmap.icon_more);
                ((TextView) helper.getView(R.id.tv_main)).getPaint().setFakeBoldText(true);
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.ir.ActSelectOperators$$ExternalSyntheticLambda3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActSelectOperators.this.lambda$setView$3(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActSelectOperatorsBinding) this.mViewBinding).rvContent);
        ((ActSelectOperatorsBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectOperatorsBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActSelectOperatorsBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setView$3(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        SpList.Sp sp = this.mAdapter.getData().get(i);
        if (1 == sp.type) {
            NavUtils.destination(ActSelectIPTVBrand.class).withInt(com.ltech.smarthome.utils.Constants.SP_ID, sp.spId).navigation(this);
            return;
        }
        NavUtils.Builder irNavBuilder = NavHelper.getIrNavBuilder(ConfigHelper.instance().productInfo.getProductId());
        if (irNavBuilder != null) {
            irNavBuilder.withInt(com.ltech.smarthome.utils.Constants.SP_ID, sp.spId).withInt(com.ltech.smarthome.utils.Constants.AREA_ID, this.areaId).withString(com.ltech.smarthome.utils.Constants.BRAND_NAME, sp.spName);
            irNavBuilder.navigation(this);
        }
    }

    private void startLocate() {
        showLoading();
        stopLocate();
        try {
            AMapLocationClient.updatePrivacyShow(this.activity, true, true);
            AMapLocationClient.updatePrivacyAgree(this.activity, true);
            this.mlocationClient = new AMapLocationClient(this);
            AMapLocationClientOption aMapLocationClientOption = new AMapLocationClientOption();
            this.mlocationClient.setLocationListener(new AMapLocationListener() { // from class: com.ltech.smarthome.ui.device.ir.ActSelectOperators.2
                @Override // com.amap.api.location.AMapLocationListener
                public void onLocationChanged(AMapLocation aMapLocation) {
                    if (aMapLocation == null || aMapLocation.getCity() == null) {
                        ActSelectOperators.this.showError();
                        return;
                    }
                    ActSelectOperators.this.stopLocate();
                    ActSelectOperators actSelectOperators = ActSelectOperators.this;
                    actSelectOperators.province = TextUtils.isEmpty(actSelectOperators.mlocationClient.getLastKnownLocation().getProvince()) ? "" : ActSelectOperators.this.mlocationClient.getLastKnownLocation().getProvince();
                    ActSelectOperators actSelectOperators2 = ActSelectOperators.this;
                    actSelectOperators2.city = TextUtils.isEmpty(actSelectOperators2.mlocationClient.getLastKnownLocation().getCity()) ? "" : ActSelectOperators.this.mlocationClient.getLastKnownLocation().getCity();
                    ActSelectOperators actSelectOperators3 = ActSelectOperators.this;
                    actSelectOperators3.district = TextUtils.isEmpty(actSelectOperators3.mlocationClient.getLastKnownLocation().getDistrict()) ? "" : ActSelectOperators.this.mlocationClient.getLastKnownLocation().getDistrict();
                    ActSelectOperators.this.refreshLocationInfo();
                    ActSelectOperators.this.getAreaId();
                }
            });
            aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            aMapLocationClientOption.setOnceLocation(true);
            this.mlocationClient.setLocationOption(aMapLocationClientOption);
            this.mlocationClient.startLocation();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopLocate() {
        AMapLocationClient aMapLocationClient = this.mlocationClient;
        if (aMapLocationClient != null) {
            aMapLocationClient.onDestroy();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getAreaId() {
        KookongSDK.getAreaId(this.province, this.city, this.district, new IRequestResult<Integer>() { // from class: com.ltech.smarthome.ui.device.ir.ActSelectOperators.3
            @Override // com.hzy.tvmao.interf.IRequestResult
            public void onSuccess(String s, Integer integer) {
                ActSelectOperators.this.getOperators(integer.intValue());
            }

            @Override // com.hzy.tvmao.interf.IRequestResult
            public void onFail(Integer integer, String s) {
                ActSelectOperators.this.showError();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getOperators(int areaId) {
        this.areaId = areaId;
        KookongSDK.getOperaters(areaId, new IRequestResult<SpList>() { // from class: com.ltech.smarthome.ui.device.ir.ActSelectOperators.4
            @Override // com.hzy.tvmao.interf.IRequestResult
            public void onSuccess(String s, SpList spList) {
                ActSelectOperators.this.showContent();
                ActSelectOperators.this.mAdapter.setNewData(spList.spList);
            }

            @Override // com.hzy.tvmao.interf.IRequestResult
            public void onFail(Integer integer, String s) {
                ActSelectOperators.this.showError();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshLocationInfo() {
        ((ActSelectOperatorsBinding) this.mViewBinding).tvLocation.setText(String.format("%s:%s%s%s", getString(R.string.cur_zone), this.province, this.city, this.district));
    }
}