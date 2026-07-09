package com.ltech.smarthome.model.extra;

import com.ltech.smarthome.base.BaseNormalFragment;

/* loaded from: classes4.dex */
public class TabContentdefault {
    private BaseNormalFragment fragment;
    private String title;
    private int titleRes;

    public TabContentdefault(int titleRes, BaseNormalFragment fragment) {
        this.titleRes = titleRes;
        this.fragment = fragment;
    }

    public TabContentdefault(String title, BaseNormalFragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public int getTitleRes() {
        return this.titleRes;
    }

    public BaseNormalFragment getFragment() {
        return this.fragment;
    }

    public String getTitle() {
        return this.title;
    }
}