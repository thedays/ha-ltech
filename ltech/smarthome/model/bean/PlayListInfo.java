package com.ltech.smarthome.model.bean;

import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import io.objectbox.converter.PropertyConverter;
import java.util.List;

/* loaded from: classes4.dex */
public class PlayListInfo {
    private long deviceId;
    private long id;
    private List<Integer> list;
    private String name;
    private int num;
    private int songCount;
    private int totalCount;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSongCount() {
        return this.songCount;
    }

    public void setSongCount(int songCount) {
        this.songCount = songCount;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<Integer> getList() {
        return this.list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public long getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public String toString() {
        return "PlayListInfo{id=" + this.id + ", num=" + this.num + ", songCount=" + this.songCount + ", name='" + this.name + "', totalCount=" + this.totalCount + ", list=" + this.list + ", deviceId=" + this.deviceId + '}';
    }

    public static class ListConverter implements PropertyConverter<List<Integer>, String> {
        @Override // io.objectbox.converter.PropertyConverter
        public List<Integer> convertToEntityProperty(String databaseValue) {
            return (List) GsonUtils.fromJson(databaseValue, new TypeToken<List<Integer>>(this) { // from class: com.ltech.smarthome.model.bean.PlayListInfo.ListConverter.1
            }.getType());
        }

        @Override // io.objectbox.converter.PropertyConverter
        public String convertToDatabaseValue(List<Integer> entityProperty) {
            return GsonUtils.toJson(entityProperty, new TypeToken<List<Integer>>(this) { // from class: com.ltech.smarthome.model.bean.PlayListInfo.ListConverter.2
            }.getType());
        }
    }
}