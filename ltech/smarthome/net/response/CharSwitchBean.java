package com.ltech.smarthome.net.response;

/* loaded from: classes4.dex */
public class CharSwitchBean {
    private String CharSwitch;

    public String getCharSwitch() {
        return this.CharSwitch;
    }

    public void setCharSwitch(String CharSwitch) {
        this.CharSwitch = CharSwitch;
    }

    public CharSwitchBean(String charSwitch) {
        this.CharSwitch = charSwitch;
    }

    public CharSwitchBean(String address, int type, int number) {
        StringBuffer stringBuffer = new StringBuffer();
        if (type == 1) {
            int i = (int) (number * 2.55d);
            stringBuffer.append("66bb59c00000f30008").append(address).append("c60001dd00").append(Integer.toHexString(i)).append("eb66bbc00000f30008");
            stringBuffer.append(address).append("c60001dd00").append(Integer.toHexString(i)).append("eb");
        }
        this.CharSwitch = stringBuffer.toString();
    }
}