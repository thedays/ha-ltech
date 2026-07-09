package com.ltech.smarthome.model.bean;

import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import io.objectbox.converter.PropertyConverter;
import java.util.Map;

/* loaded from: classes4.dex */
public class KValue {
    private String color;
    private String value;

    public KValue() {
    }

    public KValue(int value, String color) {
        this.value = String.valueOf(value);
        this.color = color;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public static class Converter implements PropertyConverter<Map<String, KValue>, String> {
        @Override // io.objectbox.converter.PropertyConverter
        public Map<String, KValue> convertToEntityProperty(String databaseValue) {
            return (Map) GsonUtils.fromJson(databaseValue, new TypeToken<Map<String, KValue>>(this) { // from class: com.ltech.smarthome.model.bean.KValue.Converter.1
            }.getType());
        }

        @Override // io.objectbox.converter.PropertyConverter
        public String convertToDatabaseValue(Map<String, KValue> entityProperty) {
            return GsonUtils.toJson(entityProperty, new TypeToken<Map<String, KValue>>(this) { // from class: com.ltech.smarthome.model.bean.KValue.Converter.2
            }.getType());
        }
    }
}