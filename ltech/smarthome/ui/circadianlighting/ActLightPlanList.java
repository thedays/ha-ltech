package com.ltech.smarthome.ui.circadianlighting;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActLightPlanListBinding;
import com.ltech.smarthome.net.response.mode.ListModeResponse;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.NavUtils;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class ActLightPlanList extends VMActivity<ActLightPlanListBinding, ActLightPlanListVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_light_plan_list;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.app_light_plan_edit));
        setBackImage(R.mipmap.icon_back);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActLightPlanListVM) this.mViewModel).setControlId(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
        ((ActLightPlanListVM) this.mViewModel).setGroupControl(getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false));
        ((ActLightPlanListVM) this.mViewModel).setCurPlanId(getIntent().getIntExtra(Constants.LIGHT_RHYTHMS_PLAN_ID_CUR, 1));
        ((ActLightPlanListVM) this.mViewModel).setIsOn(getIntent().getBooleanExtra(Constants.LIGHT_RHYTHMS_ON_OFF, false));
        ((ActLightPlanListVM) this.mViewModel).setRepeat(getIntent().getIntExtra(Constants.LIGHT_RHYTHMS_REPEAT, 128));
        ((ActLightPlanListVM) this.mViewModel).setBatch(getIntent().getBooleanExtra(Constants.LIGHT_CIRCADIAN_LIGHTING_BATCH, false));
        ((ActLightPlanListVM) this.mViewModel).initList((List) GsonUtils.fromJson(getIntent().getStringExtra(Constants.SELECTED_LIST), new TypeToken<List<ListModeResponse.RowsBean>>(this) { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanList.1
        }.getType()));
        showRequestFailInviteRecord();
        if (((ActLightPlanListVM) this.mViewModel).getBatch()) {
            ((ActLightPlanListBinding) this.mViewBinding).tvTip.setVisibility(8);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            if (data == null) {
                ((ActLightPlanListVM) this.mViewModel).syncList();
                return;
            }
            Bundle extras = getIntent().getExtras();
            Intent intent = new Intent();
            intent.putExtra("data", extras.getString("data"));
            intent.putExtra("index", extras.getInt("index"));
            setResult(-1, data);
            finish();
        }
    }

    public void showRequestFailInviteRecord() {
        Pattern compile;
        String string = getString(R.string.app_str_circadian_lighting_batch_tip);
        SpannableString spannableString = new SpannableString(string);
        if (LanguageUtils.isChinese(this.activity)) {
            spannableString.setSpan(new ForegroundColorSpan(-16776961), string.length() - 25, string.length() - 12, 33);
            compile = Pattern.compile("我的>节律照明");
        } else {
            spannableString.setSpan(new ForegroundColorSpan(-16776961), string.length() - 40, string.length() - 20, 33);
            compile = Pattern.compile("Me>Circadian lighting");
        }
        Matcher matcher = compile.matcher(string);
        while (matcher.find()) {
            spannableString.setSpan(new ClickableSpan() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanList.2
                @Override // android.text.style.ClickableSpan
                public void onClick(View view) {
                    NavUtils.destination(ActLightPlanBatchSet.class).navigation(ActLightPlanList.this.activity);
                }
            }, matcher.start(), matcher.end(), 33);
            ((ActLightPlanListBinding) this.mViewBinding).tvTip.setText(spannableString);
            ((ActLightPlanListBinding) this.mViewBinding).tvTip.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
}