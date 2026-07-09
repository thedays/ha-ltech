package com.ltech.smarthome.ui.login;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.SortAdapter;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSelectCountryBinding;
import com.ltech.smarthome.net.SmartHomeRetrofit;
import com.ltech.smarthome.net.api.ApiConstants;
import com.ltech.smarthome.net.response.user.CountryInfoResponse;
import com.ltech.smarthome.singleton.Singleton;
import com.ltech.smarthome.utils.CharacterParser;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.PinyinComparator;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.view.SideBar;
import com.smart.message.utils.LHomeLog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes4.dex */
public class ActSelectCountry extends VMActivity<ActSelectCountryBinding, ActSelectCountryVM> {
    public static final int RESULT_CODE_TO_REGISTER = 100;
    public static final String SELECT_COUNTRY_EN_KEY = "country_en";
    public static final String SELECT_COUNTRY_KEY = "country";
    SortAdapter adapter;
    CharacterParser characterParser = CharacterParser.getInstance();
    PinyinComparator pinyinComparator = new PinyinComparator();
    private List<CountryInfoResponse> sourceData;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected boolean isSupportExit() {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_country;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.select_area));
        ((ActSelectCountryVM) this.mViewModel).getCountry();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActSelectCountryVM) this.mViewModel).showRegDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.ActSelectCountry$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectCountry.this.lambda$startObserve$0((Void) obj);
            }
        });
        ((ActSelectCountryBinding) this.mViewBinding).sidrbar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() { // from class: com.ltech.smarthome.ui.login.ActSelectCountry.1
            @Override // com.ltech.smarthome.view.SideBar.OnTouchingLetterChangedListener
            public void onTouchingLetterChanged(String s) {
                int positionForSection;
                if (ActSelectCountry.this.adapter == null || (positionForSection = ActSelectCountry.this.adapter.getPositionForSection(s.charAt(0))) == -1) {
                    return;
                }
                ((ActSelectCountryBinding) ActSelectCountry.this.mViewBinding).countryLvcountry.setSelection(positionForSection);
            }
        });
        ((ActSelectCountryBinding) this.mViewBinding).countryLvcountry.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.ltech.smarthome.ui.login.ActSelectCountry.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CountryInfoResponse item = ActSelectCountry.this.adapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra(ActSelectCountry.SELECT_COUNTRY_KEY, item.getCountry());
                intent.putExtra(ActSelectCountry.SELECT_COUNTRY_EN_KEY, item.getCountry_en());
                ActSelectCountry.this.save(item);
                Singleton.removeSingleton(SmartHomeRetrofit.class);
                ActSelectCountry.this.setResult(100, intent);
                ActSelectCountry.this.finishActivity();
            }
        });
        ((ActSelectCountryBinding) this.mViewBinding).searchBadge.addTextChangedListener(new TextWatcher() { // from class: com.ltech.smarthome.ui.login.ActSelectCountry.3
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (LanguageUtils.isChinese(ActSelectCountry.this.activity)) {
                    ActSelectCountry.this.filterData(s.toString());
                } else {
                    ActSelectCountry.this.filterDataEn(s.toString().toLowerCase());
                }
            }
        });
        ((ActSelectCountryVM) this.mViewModel).showDataListEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.ActSelectCountry$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectCountry.this.lambda$startObserve$1((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Void r1) {
        NavUtils.destination(ActRegister.class).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(List list) {
        if (LanguageUtils.isChinese(this)) {
            filledData(list);
        } else {
            filledDataEn(list);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void save(CountryInfoResponse countryInfoResponse) {
        ApiConstants.SERVER_URL = countryInfoResponse.getApp_domain();
        LHomeLog.i(getClass(), "save:ApiConstants.SERVER_URL=" + ApiConstants.SERVER_URL);
        SharedPreferenceUtil.edit().keepShared(Constants.APP_DOMAIN_KEY, countryInfoResponse.getApp_domain().replace(ApiConstants.REST_URL, "")).apply();
        SharedPreferenceUtil.edit().keepShared(Constants.WEB_DOMAIN_KEY, countryInfoResponse.getWeb_domain()).apply();
        SharedPreferenceUtil.edit().keepShared(Constants.WEB_URL_KEY, countryInfoResponse.getWeb_url()).apply();
        SharedPreferenceUtil.edit().keepShared(Constants.IOT_INSTANCE, countryInfoResponse.getIotInstanceId());
        SharedPreferenceUtil.edit().keepShared(Constants.IOT_PORT, countryInfoResponse.getPort());
        SharedPreferenceUtil.edit().keepShared(SELECT_COUNTRY_KEY, countryInfoResponse.getCountry()).apply();
        SharedPreferenceUtil.edit().keepShared(SELECT_COUNTRY_EN_KEY, countryInfoResponse.getCountry_en()).apply();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (2001 == resultCode || 2002 == resultCode) {
            back();
        }
    }

    private void filledData(List<CountryInfoResponse> date) {
        for (int i = 0; i < date.size(); i++) {
            String upperCase = this.characterParser.getSelling(date.get(i).getCountry()).substring(0, 1).toUpperCase();
            if (upperCase.matches("[A-Z]")) {
                date.get(i).setSortLetters(upperCase.toUpperCase());
            } else {
                date.get(i).setSortLetters(MqttTopic.MULTI_LEVEL_WILDCARD);
            }
        }
        this.sourceData = date;
        Collections.sort(date, this.pinyinComparator);
        this.adapter = new SortAdapter(this, this.sourceData);
        ((ActSelectCountryBinding) this.mViewBinding).countryLvcountry.setAdapter((ListAdapter) this.adapter);
    }

    private void filledDataEn(List<CountryInfoResponse> data) {
        for (int i = 0; i < data.size(); i++) {
            String upperCase = data.get(i).getCountry_en().substring(0, 1).toUpperCase();
            if (upperCase.matches("[A-Z]")) {
                data.get(i).setSortLetters(upperCase.toUpperCase());
            } else {
                data.get(i).setSortLetters(MqttTopic.MULTI_LEVEL_WILDCARD);
            }
        }
        this.sourceData = data;
        Collections.sort(data, new Comparator<CountryInfoResponse>(this) { // from class: com.ltech.smarthome.ui.login.ActSelectCountry.4
            @Override // java.util.Comparator
            public int compare(CountryInfoResponse o1, CountryInfoResponse o2) {
                return o1.getCountry_en().compareTo(o2.getCountry_en());
            }
        });
        this.adapter = new SortAdapter(this, this.sourceData);
        ((ActSelectCountryBinding) this.mViewBinding).countryLvcountry.setAdapter((ListAdapter) this.adapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void filterData(String filterStr) {
        List<CountryInfoResponse> arrayList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {
            arrayList = this.sourceData;
        } else {
            List<CountryInfoResponse> list = this.sourceData;
            if (list != null) {
                for (CountryInfoResponse countryInfoResponse : list) {
                    String country = countryInfoResponse.getCountry();
                    if (country.contains(filterStr.toString()) || this.characterParser.getSelling(country).startsWith(filterStr.toString())) {
                        arrayList.add(countryInfoResponse);
                    }
                }
            }
        }
        PinyinComparator pinyinComparator = this.pinyinComparator;
        if (pinyinComparator == null || this.sourceData == null) {
            return;
        }
        Collections.sort(arrayList, pinyinComparator);
        SortAdapter sortAdapter = this.adapter;
        if (sortAdapter != null) {
            sortAdapter.updateListView(arrayList);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void filterDataEn(String filterStr) {
        List<CountryInfoResponse> arrayList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {
            arrayList = this.sourceData;
        } else {
            List<CountryInfoResponse> list = this.sourceData;
            if (list != null) {
                for (CountryInfoResponse countryInfoResponse : list) {
                    String country_en = countryInfoResponse.getCountry_en();
                    if (country_en.toLowerCase().contains(filterStr.toString()) || this.characterParser.getSelling(country_en).toLowerCase().startsWith(filterStr.toString())) {
                        arrayList.add(countryInfoResponse);
                    }
                }
            }
        }
        if (arrayList != null) {
            Collections.sort(arrayList, new Comparator<CountryInfoResponse>(this) { // from class: com.ltech.smarthome.ui.login.ActSelectCountry.5
                @Override // java.util.Comparator
                public int compare(CountryInfoResponse o1, CountryInfoResponse o2) {
                    return o1.getCountry_en().compareTo(o2.getCountry_en());
                }
            });
            SortAdapter sortAdapter = this.adapter;
            if (sortAdapter != null) {
                sortAdapter.updateListView(arrayList);
            }
        }
    }
}