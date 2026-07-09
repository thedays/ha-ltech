package com.ltech.smarthome.common;

/* loaded from: classes3.dex */
public class ListItem {
    private String flag;
    private int key;
    private boolean selected;
    private String subTitle;
    private String title;
    private String value;

    public ListItem(int key, String title, String subTitle, String flag) {
        this.key = key;
        this.title = title;
        this.subTitle = subTitle;
        this.flag = flag;
    }

    public ListItem(int key, String title, String subTitle) {
        this.key = key;
        this.title = title;
        this.subTitle = subTitle;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}