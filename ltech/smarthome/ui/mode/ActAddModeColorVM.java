package com.ltech.smarthome.ui.mode;

import android.content.Context;
import com.google.common.primitives.Ints;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.smart.product_agreement.bean.AdvancedModeInfo;
import java.util.List;

/* loaded from: classes4.dex */
public class ActAddModeColorVM extends BaseViewModel {
    public AdvancedModeInfo.ContentItem contentItem;
    private int hourPos;
    public int lightType;
    private int minPos;
    private int msPos;
    private int secPos;

    public List<Integer> getColorList(Context context) {
        return Ints.asList(context.getResources().getIntArray(R.array.static_default_color));
    }

    public void setRed(int red) {
        this.contentItem.setRed(red);
    }

    public int getRed() {
        return this.contentItem.getRed();
    }

    public void setGreen(int green) {
        this.contentItem.setGreen(green);
    }

    public int getGreen() {
        return this.contentItem.getGreen();
    }

    public void setBlue(int blue) {
        this.contentItem.setBlue(blue);
    }

    public int getBlue() {
        return this.contentItem.getBlue();
    }

    public void setWyBrt(int wyBrt) {
        this.contentItem.setWyBrt(wyBrt);
    }

    public int getWyBrt() {
        return this.contentItem.getWyBrt();
    }

    public void setRgbBrt(int brt) {
        this.contentItem.setRgbBrt(brt);
    }

    public int getRgbBrt() {
        return this.contentItem.getRgbBrt();
    }

    public void setwOrWy(int wOrWy) {
        this.contentItem.setwOrWy(wOrWy);
    }

    public int getwOrWy() {
        return this.contentItem.getwOrWy();
    }

    public int getHourPos() {
        return this.hourPos;
    }

    public void setHourPos(int hourPos) {
        this.hourPos = hourPos;
    }

    public int getMinPos() {
        return this.minPos;
    }

    public void setMinPos(int minPos) {
        this.minPos = minPos;
    }

    public int getSecPos() {
        return this.secPos;
    }

    public void setSecPos(int secPos) {
        this.secPos = secPos;
    }

    public int getMsPos() {
        return this.msPos;
    }

    public void setMsPos(int msPos) {
        this.msPos = msPos;
    }

    public int getC() {
        return this.contentItem.getC();
    }

    public void setC(int c2) {
        this.contentItem.setC(c2);
    }
}