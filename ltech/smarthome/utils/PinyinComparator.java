package com.ltech.smarthome.utils;

import com.ltech.smarthome.net.response.user.CountryInfoResponse;
import java.util.Comparator;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes4.dex */
public class PinyinComparator implements Comparator<CountryInfoResponse> {
    @Override // java.util.Comparator
    public int compare(CountryInfoResponse o1, CountryInfoResponse o2) {
        if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals(MqttTopic.MULTI_LEVEL_WILDCARD)) {
            return -1;
        }
        if (o1.getSortLetters().equals(MqttTopic.MULTI_LEVEL_WILDCARD) || o2.getSortLetters().equals("@")) {
            return 1;
        }
        return o1.getSortLetters().compareTo(o2.getSortLetters());
    }
}