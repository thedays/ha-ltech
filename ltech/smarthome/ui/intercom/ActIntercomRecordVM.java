package com.ltech.smarthome.ui.intercom;

import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.base.BaseViewModel;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActIntercomRecordVM extends BaseViewModel {
    public List<BaseNormalFragment> fragmentList = new ArrayList();

    public void initFragmentList() {
        this.fragmentList.clear();
        this.fragmentList.add(new FtOpenDoorLog());
    }
}