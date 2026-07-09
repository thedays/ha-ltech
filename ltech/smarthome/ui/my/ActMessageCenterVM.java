package com.ltech.smarthome.ui.my;

import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.base.BaseViewModel;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActMessageCenterVM extends BaseViewModel {
    public int messageType;
    public List<TabContent> tabContents;

    public void initTabList() {
        List<TabContent> list = this.tabContents;
        if (list == null) {
            this.tabContents = new ArrayList();
        } else {
            list.clear();
        }
        this.tabContents.add(new TabContent(R.string.app_str_notice, new FtMessageNotice()));
        this.tabContents.add(new TabContent(R.string.app_str_home, new FtMessageHome()));
    }

    public static final class TabContent {
        private BaseNormalFragment fragment;
        private int titleRes;

        public TabContent(int titleRes, BaseNormalFragment fragment) {
            this.titleRes = titleRes;
            this.fragment = fragment;
        }

        public int getTitleRes() {
            return this.titleRes;
        }

        public BaseNormalFragment getFragment() {
            return this.fragment;
        }
    }
}