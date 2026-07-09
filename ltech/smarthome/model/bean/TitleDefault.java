package com.ltech.smarthome.model.bean;

import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes4.dex */
public class TitleDefault {
    private BindingCommand backAction;
    private int backImageRes;
    private String backString;
    private BindingCommand editAction;
    private int editImageRes;
    private String editString;
    private String subTitle;
    private String title;
    private BindingCommand titleAction;

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

    public int getBackImageRes() {
        return this.backImageRes;
    }

    public void setBackImageRes(int backImageRes) {
        this.backImageRes = backImageRes;
    }

    public BindingCommand getBackAction() {
        return this.backAction;
    }

    public void setBackAction(BindingCommand backAction) {
        this.backAction = backAction;
    }

    public String getEditString() {
        return this.editString;
    }

    public void setEditString(String editString) {
        this.editString = editString;
    }

    public BindingCommand getEditAction() {
        return this.editAction;
    }

    public void setEditAction(BindingCommand editAction) {
        this.editAction = editAction;
    }

    public void setTitleAction(BindingCommand editAction) {
        this.titleAction = editAction;
    }

    public BindingCommand getTitleAction() {
        return this.titleAction;
    }

    public String getBackString() {
        return this.backString;
    }

    public void setBackString(String backString) {
        this.backString = backString;
    }

    public int getEditImageRes() {
        return this.editImageRes;
    }

    public void setEditImageRes(int editImageRes) {
        this.editImageRes = editImageRes;
    }
}