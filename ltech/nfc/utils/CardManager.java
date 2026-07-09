package com.ltech.nfc.utils;

import android.content.IntentFilter;
import android.nfc.tech.IsoDep;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;

/* loaded from: classes3.dex */
public final class CardManager {
    public static IntentFilter[] FILTERS;
    public static String[][] TECHLISTS;

    static {
        try {
            TECHLISTS = new String[][]{new String[]{IsoDep.class.getName()}, new String[]{NfcV.class.getName()}, new String[]{NfcF.class.getName()}};
            FILTERS = new IntentFilter[]{new IntentFilter("android.nfc.action.TECH_DISCOVERED", "*/*")};
        } catch (Exception unused) {
        }
    }
}