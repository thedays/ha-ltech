package com.ltech.smarthome.net.request.music;

import java.util.List;

/* loaded from: classes4.dex */
public class SongRequest {
    private List<Long> songids;
    private List<Sort> songs;

    public static class Sort {
        private long songid;
        private String sorting;

        public Sort(long songid, String sorting) {
            this.songid = songid;
            this.sorting = sorting;
        }
    }

    public List<Sort> getSongs() {
        return this.songs;
    }

    public void setSongs(List<Sort> songs) {
        this.songs = songs;
    }

    public List<Long> getSongids() {
        return this.songids;
    }

    public void setSongids(List<Long> songids) {
        this.songids = songids;
    }
}