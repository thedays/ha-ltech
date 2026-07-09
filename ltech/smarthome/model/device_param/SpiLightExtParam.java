package com.ltech.smarthome.model.device_param;

import com.ltech.smarthome.base.BaseExtParam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class SpiLightExtParam extends BaseExtParam {
    private int icLine;
    private int icType;
    private int pixel;
    private List<PlayList> playlistModes;

    public int getIcType() {
        return this.icType;
    }

    public void setIcType(int icType) {
        this.icType = icType;
    }

    public int getIcLine() {
        return this.icLine;
    }

    public void setIcLine(int icLine) {
        this.icLine = icLine;
    }

    public int getPixel() {
        return this.pixel;
    }

    public void setPixel(int pixel) {
        this.pixel = pixel;
    }

    public List<PlayList> getPlayList() {
        List<PlayList> list = this.playlistModes;
        if (list == null || list.size() == 0) {
            ArrayList arrayList = new ArrayList();
            this.playlistModes = arrayList;
            arrayList.add(new PlayList(1, Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16), ""));
        }
        return this.playlistModes;
    }

    public void setPlayList(List<PlayList> playList) {
        this.playlistModes = playList;
    }

    public static class PlayList {
        public List<Integer> modes;
        public String name;
        public int playListId;

        public PlayList(int playListId, List<Integer> modes, String name) {
            this.playListId = playListId;
            this.modes = modes;
            this.name = name;
        }

        public int getPlayListId() {
            return this.playListId;
        }

        public void setPlayListId(int playListId) {
            this.playListId = playListId;
        }

        public List<Integer> getModes() {
            return this.modes;
        }

        public void setModes(List<Integer> modes) {
            this.modes = modes;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}