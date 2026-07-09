package com.ltech.smarthome.view.dialog;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogCityPickerBinding;
import com.ltech.smarthome.model.extra.CityList;
import com.ltech.smarthome.view.MyTimePickerView;
import com.ltech.smarthome.view.dialog.CityPickerDialog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class CityPickerDialog extends SmartDialog<DialogCityPickerBinding> {
    private CityList cityList;
    private SelectListener mSelectListener;
    private List<String> provinces = new ArrayList();
    private ArrayMap<String, List<String>> cities = new ArrayMap<>();
    private ArrayMap<String, List<String>> districts = new ArrayMap<>();
    private int provincePosition = 0;
    private int cityPosition = 0;
    private int districtPosition = 0;

    public interface SelectListener {
        void onSelect(String province, String city, String district);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_city_picker;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.CityPickerDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogCityPickerBinding, CityPickerDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogCityPickerBinding viewBinding, final CityPickerDialog dialog) {
            viewBinding.pickViewProvince.setData(dialog.provinces);
            viewBinding.pickViewProvince.setSelectedPosition(dialog.provincePosition);
            viewBinding.pickViewCity.setData((List) dialog.cities.get(dialog.provinces.get(dialog.provincePosition)));
            viewBinding.pickViewCity.setSelectedPosition(dialog.cityPosition);
            viewBinding.pickViewDistrict.setData((List) dialog.districts.get(((List) dialog.cities.get(dialog.provinces.get(dialog.provincePosition))).get(dialog.cityPosition)));
            viewBinding.pickViewDistrict.setSelectedPosition(dialog.districtPosition);
            viewBinding.pickViewProvince.setMainTextColor(ContextCompat.getColor(dialog.getContext(), R.color.color_text_black));
            viewBinding.pickViewCity.setMainTextColor(ContextCompat.getColor(dialog.getContext(), R.color.color_text_black));
            viewBinding.pickViewDistrict.setMainTextColor(ContextCompat.getColor(dialog.getContext(), R.color.color_text_black));
            viewBinding.pickViewProvince.setOnSelectListener(new MyTimePickerView.onSelectListener() { // from class: com.ltech.smarthome.view.dialog.CityPickerDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.view.MyTimePickerView.onSelectListener
                public final void onSelect(int i, String str) {
                    CityPickerDialog.AnonymousClass1.lambda$convertView$0(CityPickerDialog.this, i, str);
                }
            });
            viewBinding.pickViewCity.setOnSelectListener(new MyTimePickerView.onSelectListener() { // from class: com.ltech.smarthome.view.dialog.CityPickerDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.view.MyTimePickerView.onSelectListener
                public final void onSelect(int i, String str) {
                    CityPickerDialog.AnonymousClass1.lambda$convertView$1(CityPickerDialog.this, i, str);
                }
            });
            viewBinding.pickViewDistrict.setOnSelectListener(new MyTimePickerView.onSelectListener() { // from class: com.ltech.smarthome.view.dialog.CityPickerDialog$1$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.view.MyTimePickerView.onSelectListener
                public final void onSelect(int i, String str) {
                    CityPickerDialog.this.districtPosition = i;
                }
            });
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.CityPickerDialog$1$$ExternalSyntheticLambda3
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    CityPickerDialog.AnonymousClass1.lambda$convertView$3(CityPickerDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(CityPickerDialog cityPickerDialog, int i, String str) {
            cityPickerDialog.provincePosition = i;
            cityPickerDialog.cityPosition = 0;
            cityPickerDialog.districtPosition = 0;
            cityPickerDialog.notifyDialog();
        }

        static /* synthetic */ void lambda$convertView$1(CityPickerDialog cityPickerDialog, int i, String str) {
            cityPickerDialog.cityPosition = i;
            cityPickerDialog.districtPosition = 0;
            cityPickerDialog.notifyDialog();
        }

        static /* synthetic */ void lambda$convertView$3(CityPickerDialog cityPickerDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                cityPickerDialog.dismissDialog();
                return;
            }
            if (id != R.id.tv_confirm) {
                return;
            }
            cityPickerDialog.dismissDialog();
            if (cityPickerDialog.mSelectListener != null) {
                String str = null;
                String str2 = !cityPickerDialog.provinces.isEmpty() ? (String) cityPickerDialog.provinces.get(cityPickerDialog.provincePosition) : null;
                if (TextUtils.isEmpty(str2)) {
                    str2 = "";
                }
                List list = (List) cityPickerDialog.cities.get(str2);
                String str3 = (list == null || list.isEmpty()) ? null : (String) list.get(cityPickerDialog.cityPosition);
                if (TextUtils.isEmpty(str3)) {
                    str3 = "";
                }
                List list2 = (List) cityPickerDialog.districts.get(str3);
                if (list2 != null && !list2.isEmpty()) {
                    str = (String) list2.get(cityPickerDialog.districtPosition);
                }
                cityPickerDialog.mSelectListener.onSelect(str2, str3, TextUtils.isEmpty(str) ? "" : str);
            }
        }
    }

    public static CityPickerDialog asDefault() {
        return (CityPickerDialog) new CityPickerDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setGravity(80);
    }

    public CityPickerDialog setCityList(CityList cityList) {
        this.cityList = cityList;
        initList();
        return this;
    }

    private void initList() {
        this.provinces.clear();
        this.cities.clear();
        this.districts.clear();
        for (CityList.CitylistBean citylistBean : this.cityList.getCitylist()) {
            this.provinces.add(citylistBean.getP());
            ArrayList arrayList = new ArrayList();
            for (CityList.CitylistBean.CBean cBean : citylistBean.getC()) {
                arrayList.add(cBean.getN());
                ArrayList arrayList2 = new ArrayList();
                if (cBean.getA() != null) {
                    Iterator<CityList.CitylistBean.CBean.ABean> it = cBean.getA().iterator();
                    while (it.hasNext()) {
                        arrayList2.add(it.next().getS());
                    }
                }
                this.districts.put(cBean.getN(), arrayList2);
            }
            this.cities.put(citylistBean.getP(), arrayList);
        }
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "city_picker_dialog";
    }

    public CityPickerDialog setSelectListener(SelectListener selectListener) {
        this.mSelectListener = selectListener;
        return this;
    }
}