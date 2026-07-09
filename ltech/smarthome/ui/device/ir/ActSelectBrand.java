package com.ltech.smarthome.ui.device.ir;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.SectionEntity;
import com.hzy.tvmao.KookongSDK;
import com.hzy.tvmao.interf.IRequestResult;
import com.kookong.app.data.BrandList;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.databinding.ActSelectBrandBinding;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.config.ConfigHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectBrand extends BaseNormalActivity<ActSelectBrandBinding> {
    public static final String INIT_KEY = "F10757DAC9D7D74492FDA99C0B3C5BA8";
    public static String[] LETTERS = {ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "B", "C", "D", ExifInterface.LONGITUDE_EAST, "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", ExifInterface.LATITUDE_SOUTH, ExifInterface.GPS_DIRECTION_TRUE, "U", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, ExifInterface.LONGITUDE_WEST, "X", "Y", "Z"};
    private int brandType;
    private BaseSectionQuickAdapter<BrandSection, BaseViewHolder> mAdapter;
    private List<BrandList.Brand> mBrandList = new ArrayList();
    private List<BrandSection> mBrandSectionList = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_brand;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.select_brand));
        setBackImage(R.mipmap.icon_back);
        BaseSectionQuickAdapter<BrandSection, BaseViewHolder> baseSectionQuickAdapter = new BaseSectionQuickAdapter<BrandSection, BaseViewHolder>(R.layout.item_go_1, R.layout.item_text, this.mBrandSectionList) { // from class: com.ltech.smarthome.ui.device.ir.ActSelectBrand.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, BrandSection item) {
                boolean isChinese = ActSelectBrand.this.isChinese();
                BrandList.Brand brand = (BrandList.Brand) item.t;
                helper.setText(R.id.tv_main, isChinese ? brand.cname : brand.ename);
                ((TextView) helper.getView(R.id.tv_main)).getPaint().setFakeBoldText(true);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseSectionQuickAdapter
            public void convertHead(BaseViewHolder helper, BrandSection item) {
                helper.setText(R.id.tv_name, item.header).setTextColor(R.id.tv_name, ContextCompat.getColor(this.mContext, R.color.color_text_gray));
            }
        };
        this.mAdapter = baseSectionQuickAdapter;
        baseSectionQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.ir.ActSelectBrand$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSelectBrand.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
        ((ActSelectBrandBinding) this.mViewBinding).rvContent.setAdapter(this.mAdapter);
        ((ActSelectBrandBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectBrandBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActSelectBrandBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
        ((ActSelectBrandBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingAction() { // from class: com.ltech.smarthome.ui.device.ir.ActSelectBrand$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectBrand.this.lambda$initView$1();
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        NavUtils.Builder irNavBuilder;
        BrandSection brandSection = (BrandSection) this.mAdapter.getData().get(i);
        if (brandSection.isHeader || (irNavBuilder = NavHelper.getIrNavBuilder(ConfigHelper.instance().productInfo.getProductId())) == null) {
            return;
        }
        NavUtils.Builder withInt = irNavBuilder.withInt(Constants.BRAND_ID, ((BrandList.Brand) brandSection.t).brandId);
        boolean isChinese = isChinese();
        BrandList.Brand brand = (BrandList.Brand) brandSection.t;
        withInt.withString(Constants.BRAND_NAME, isChinese ? brand.cname : brand.ename);
        irNavBuilder.navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1() {
        filterList(((ActSelectBrandBinding) this.mViewBinding).etSearch.getText().toString());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.brandType = Integer.parseInt(ProductId.CC.getControlType(ConfigHelper.instance().productInfo.getProductId()));
        KookongSDK.init(getApplicationContext(), INIT_KEY, ConfigHelper.instance().mac.replaceAll(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, "").toUpperCase());
        getBrand();
    }

    private void getBrand() {
        showLoading();
        KookongSDK.getBrandListFromNet(this.brandType, new IRequestResult<BrandList>() { // from class: com.ltech.smarthome.ui.device.ir.ActSelectBrand.2
            @Override // com.hzy.tvmao.interf.IRequestResult
            public void onSuccess(String s, BrandList brandList) {
                ActSelectBrand.this.showContent();
                ActSelectBrand.this.mBrandList.clear();
                ActSelectBrand.this.mBrandList.addAll(brandList.brandList);
                ActSelectBrand.this.setSectionList(brandList.brandList, true);
            }

            @Override // com.hzy.tvmao.interf.IRequestResult
            public void onFail(Integer integer, String s) {
                ActSelectBrand.this.showError();
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        getBrand();
    }

    private void filterList(String s) {
        if (TextUtils.isEmpty(s)) {
            setSectionList(new ArrayList(this.mBrandList), true);
            return;
        }
        ArrayList arrayList = new ArrayList();
        if (isA_Z(s)) {
            String upperCase = s.toUpperCase();
            if (isChinese()) {
                for (BrandList.Brand brand : this.mBrandList) {
                    if (brand.pinyin.toUpperCase().contains(upperCase)) {
                        arrayList.add(brand);
                    }
                }
            } else {
                for (BrandList.Brand brand2 : this.mBrandList) {
                    if (brand2.ename.toUpperCase().contains(upperCase)) {
                        arrayList.add(brand2);
                    }
                }
            }
        } else {
            for (BrandList.Brand brand3 : this.mBrandList) {
                if (brand3.cname.contains(s)) {
                    arrayList.add(brand3);
                }
            }
        }
        setSectionList(arrayList, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSectionList(List<BrandList.Brand> brandList, boolean commondBrand) {
        this.mBrandSectionList.clear();
        if (brandList.isEmpty()) {
            this.mAdapter.replaceData(this.mBrandSectionList);
            return;
        }
        Collections.sort(brandList, new Comparator() { // from class: com.ltech.smarthome.ui.device.ir.ActSelectBrand$$ExternalSyntheticLambda2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int lambda$setSectionList$2;
                lambda$setSectionList$2 = ActSelectBrand.this.lambda$setSectionList$2((BrandList.Brand) obj, (BrandList.Brand) obj2);
                return lambda$setSectionList$2;
            }
        });
        if (commondBrand) {
            this.mBrandSectionList.add(new BrandSection(true, getString(R.string.commom_brand)));
            for (int i = 0; i < 5; i++) {
                this.mBrandSectionList.add(new BrandSection(this.mBrandList.get(i)));
            }
        }
        ArrayList arrayList = new ArrayList();
        if (isChinese()) {
            for (String str : LETTERS) {
                arrayList.clear();
                for (BrandList.Brand brand : brandList) {
                    if (str.equalsIgnoreCase(brand.initial)) {
                        arrayList.add(brand);
                    }
                }
                if (!arrayList.isEmpty()) {
                    this.mBrandSectionList.add(new BrandSection(true, str));
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        this.mBrandSectionList.add(new BrandSection((BrandList.Brand) it.next()));
                    }
                }
            }
        } else {
            for (String str2 : LETTERS) {
                arrayList.clear();
                for (BrandList.Brand brand2 : brandList) {
                    if (str2.equalsIgnoreCase(brand2.ename.substring(0, 1))) {
                        arrayList.add(brand2);
                    }
                }
                if (!arrayList.isEmpty()) {
                    this.mBrandSectionList.add(new BrandSection(true, str2));
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        this.mBrandSectionList.add(new BrandSection((BrandList.Brand) it2.next()));
                    }
                }
            }
        }
        this.mAdapter.replaceData(this.mBrandSectionList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$setSectionList$2(BrandList.Brand brand, BrandList.Brand brand2) {
        if (isChinese()) {
            return brand.initial.compareTo(brand2.initial);
        }
        return brand.ename.compareTo(brand2.ename);
    }

    public static final class BrandSection extends SectionEntity<BrandList.Brand> {
        public BrandSection(boolean isHeader, String header) {
            super(isHeader, header);
        }

        public BrandSection(BrandList.Brand brand) {
            super(brand);
        }
    }

    private boolean isA_Z(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!getString(R.string.a_z).contains(s.charAt(i) + "")) {
                return false;
            }
        }
        return true;
    }

    public boolean isChinese() {
        return LanguageUtils.isChinese(this);
    }
}