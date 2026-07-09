package com.ltech.smarthome.ui.control;

import android.view.View;
import androidx.fragment.app.Fragment;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.extra.TabContentdefault;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class FtIntelligenceVM extends BaseViewModel {
    public boolean groupControl;
    public Fragment selectFragment;
    private List<TabContentdefault> tabContentList;
    public int selectPosition = 0;
    public long deviceId = -1;
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.control.FtIntelligenceVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            FtIntelligenceVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.iv_add /* 2131296933 */:
                ((IIntelligence) this.tabContentList.get(this.selectPosition).getFragment()).goAdd();
                break;
            case R.id.iv_back /* 2131296947 */:
                ActivityUtils.getTopActivity().finish();
                break;
            case R.id.iv_search /* 2131297228 */:
                ((IIntelligence) this.tabContentList.get(this.selectPosition).getFragment()).goSearch();
                break;
            case R.id.iv_sort /* 2131297260 */:
                ((IIntelligence) this.tabContentList.get(this.selectPosition).getFragment()).goSort();
                break;
        }
    }

    public void initTabList() {
        List<TabContentdefault> list = this.tabContentList;
        if (list == null) {
            this.tabContentList = new ArrayList();
        } else {
            list.clear();
        }
        this.tabContentList.add(new TabContentdefault(R.string.app_scene, FtScene.newInstance(this.deviceId, this.groupControl)));
        this.tabContentList.add(new TabContentdefault(R.string.automation, FtAutomation.newInstance(this.deviceId, this.groupControl)));
    }

    public void initTabList(String productId) {
        List<TabContentdefault> list = this.tabContentList;
        if (list == null) {
            this.tabContentList = new ArrayList();
        } else {
            list.clear();
        }
        if (!onlyShowAutomation(productId)) {
            this.tabContentList.add(new TabContentdefault(R.string.app_scene, FtScene.newInstance(this.deviceId, this.groupControl)));
        }
        this.tabContentList.add(new TabContentdefault(R.string.automation, FtAutomation.newInstance(this.deviceId, this.groupControl)));
    }

    public boolean onlyShowAutomation(String productId) {
        productId.hashCode();
        switch (productId) {
            case "4122243156296320":
            case "3503908278750336":
            case "3508084028410496":
            case "3537619681035968":
            case "3503908725640320":
            case "3763962108692992":
            case "3503907950824576":
                return true;
            default:
                return false;
        }
    }

    public List<TabContentdefault> getTabContentList() {
        return this.tabContentList;
    }
}