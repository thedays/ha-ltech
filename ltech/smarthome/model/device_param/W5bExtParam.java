package com.ltech.smarthome.model.device_param;

import com.google.gson.annotations.SerializedName;
import com.ltech.smarthome.base.BaseExtParam;
import com.ltech.smarthome.ui.device.e6knob.E6Helper$$ExternalSyntheticBackport0;
import com.sun.jna.platform.win32.WinError;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes4.dex */
public class W5bExtParam extends BaseExtParam {

    @SerializedName("cctPoints")
    private Map<String, List<Point>> cctPoints = new HashMap();

    public Map<String, List<Point>> getCctPoints() {
        return this.cctPoints;
    }

    public void setCctPoints(Map<String, List<Point>> cctPoints) {
        this.cctPoints = cctPoints;
    }

    public static class Point {
        private float duv;
        private transient int isDefault;
        private String name;

        public Point(String name, float duv, int isDefault) {
            this.name = name;
            this.duv = Float.parseFloat(String.format(Locale.US, "%.3f", Float.valueOf(duv)));
            this.isDefault = isDefault;
        }

        public boolean equals(Object obj) {
            return super.equals(obj);
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getDuv() {
            return this.duv;
        }

        public void setDuv(float duv) {
            this.duv = duv;
        }

        public int getIsDefault() {
            return this.isDefault;
        }

        public void setIsDefault(int isDefault) {
            this.isDefault = isDefault;
        }
    }

    public void addPoint(int k, Point point) {
        List<Point> list = this.cctPoints.containsKey(String.valueOf(k)) ? this.cctPoints.get(String.valueOf(k)) : null;
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(point);
        this.cctPoints.put(String.valueOf(k), list);
    }

    public void removePoint(int k, Point point) {
        List<Point> list = this.cctPoints.get(String.valueOf(k));
        if (list != null) {
            list.remove(point);
            this.cctPoints.put(String.valueOf(k), list);
        }
    }

    public List<Point> getPointList(int k) {
        List<Point> arrayList = new ArrayList<>();
        if (this.cctPoints.containsKey(String.valueOf(k))) {
            arrayList = this.cctPoints.get(String.valueOf(k));
        }
        return addDefaultList(arrayList, k);
    }

    private List<Point> addDefaultList(List<Point> list, int k) {
        List m2 = E6Helper$$ExternalSyntheticBackport0.m(new Integer[]{Integer.valueOf(WinError.ERROR_INVALID_PRIORITY), 2200, 2700, 3150, 3500, 3600, 4000, 5000, 6000, 6500, 8000});
        ArrayList arrayList = new ArrayList(list);
        if (m2.contains(Integer.valueOf(k))) {
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(new Point("1", -0.015f, 1));
            arrayList2.add(new Point("2", 0.005f, 1));
            arrayList2.add(new Point("3", 0.02f, 1));
            arrayList.addAll(0, arrayList2);
        }
        return arrayList;
    }
}