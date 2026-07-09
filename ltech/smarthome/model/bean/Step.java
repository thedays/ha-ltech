package com.ltech.smarthome.model.bean;

/* loaded from: classes4.dex */
public class Step {
    private int countDown;
    private int img;
    private String title;
    private int type;

    public Step(String title, int type) {
        this.type = type;
        this.title = title;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getImg() {
        return this.img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCountDown() {
        return this.countDown;
    }

    public void setCountDown(int countDown) {
        this.countDown = countDown;
    }
}