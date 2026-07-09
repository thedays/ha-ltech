package com.ltech.smarthome.ui.item;

import android.view.View;

/* loaded from: classes4.dex */
public class ValueItem {
    private View.OnClickListener clickListener;
    private String name;
    private String subName;
    private String value;

    public ValueItem(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public ValueItem(String name, String value, View.OnClickListener clickListener) {
        this.name = name;
        this.value = value;
        this.clickListener = clickListener;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return this.subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public View.OnClickListener getClickListener() {
        return this.clickListener;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }
}