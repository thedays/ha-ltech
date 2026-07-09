package com.ltech.smarthome.ui.control;

import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Place;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActHomeVM extends BaseViewModel {
    public Listing<Place> placeList;
    private List<TabContent> tabContentList;

    public void initTabList() {
        List<TabContent> list = this.tabContentList;
        if (list == null) {
            this.tabContentList = new ArrayList();
        } else {
            list.clear();
        }
        this.tabContentList.add(new TabContent(R.string.room, R.mipmap.ic_tab_room_sel, R.mipmap.ic_tab_room_default, new FtRoom()));
        this.tabContentList.add(new TabContent(R.string.light_group, R.mipmap.ic_tab_group_sel, R.mipmap.ic_tab_group_default, new FtMy()));
        this.tabContentList.add(new TabContent(R.string.intelligent, R.mipmap.ic_tab_smart_sel, R.mipmap.ic_tab_smart_default, new FtIntelligence()));
        this.tabContentList.add(new TabContent(R.string.my, R.mipmap.ic_tab_my_sel, R.mipmap.ic_tab_my_default, new FtMy()));
    }

    public List<TabContent> getTabContentList() {
        return this.tabContentList;
    }

    public static final class TabContent {
        private BaseNormalFragment fragment;
        private int selectIconRes;
        private int titleRes;
        private int unSelectIconRes;

        public TabContent(int titleRes, int selectIconRes, int unSelectIconRes, BaseNormalFragment fragment) {
            this.titleRes = titleRes;
            this.selectIconRes = selectIconRes;
            this.unSelectIconRes = unSelectIconRes;
            this.fragment = fragment;
        }

        public int getTitleRes() {
            return this.titleRes;
        }

        public int getSelectIconRes() {
            return this.selectIconRes;
        }

        public int getUnSelectIconRes() {
            return this.unSelectIconRes;
        }

        public BaseNormalFragment getFragment() {
            return this.fragment;
        }
    }
}