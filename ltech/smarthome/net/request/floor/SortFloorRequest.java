package com.ltech.smarthome.net.request.floor;

import java.util.List;

/* loaded from: classes4.dex */
public class SortFloorRequest {
    private List<FloorSortBean> floors;

    public static final class FloorSortBean {
        public long floorid;
    }

    public SortFloorRequest(List<FloorSortBean> floors) {
        this.floors = floors;
    }
}