package com.ltech.smarthome.ui.device.super_panel.music.manager;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.MiguRadioBean;
import com.ltech.smarthome.model.bean.MusicInfo;
import com.ltech.smarthome.model.bean.RadioInfo;
import com.ltech.smarthome.model.bean.RadiosInfo;
import com.ltech.smarthome.model.bean.SongListItemInfo;
import com.ltech.smarthome.net.response.music.MusicListResponse;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.rich.czlylibary.bean.BatchMusicinfoResult;
import com.rich.czlylibary.bean.MusicinfoResult;
import com.rich.czlylibary.bean.QuerySheetMusicInfo;
import com.rich.czlylibary.bean.Result;
import com.rich.czlylibary.bean.SearchSongsForVoiceBox;
import com.rich.czlylibary.bean.SongNewVoiceBox;
import com.rich.czlylibary.sdk.HttpClientManager;
import com.rich.czlylibary.sdk.MiguCzlySDK;
import com.rich.czlylibary.sdk.MiguResultCallback;
import com.rich.czlylibary.sdk.ResultCallback;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class MiguManager {
    private static final int MAX_SONG = 40;
    private static MiguManager instance = null;
    private static final long validity = 86400000;
    Application context;
    public List<SongListItemInfo> localSongs;
    private HashMap<String, Map<String, MiguRadioBean.RadiosDTO>> miguRadioMap;
    private List<String> categorys = new ArrayList();
    private List<RadioInfo> radiolist = new ArrayList();
    private HashMap<String, MiguRadioBean.RadiosDTO> miguSongMap = new HashMap<>();
    public Map<String, List<SongListItemInfo>> localSongListMap = new HashMap();

    public static MiguManager getInstance() {
        if (instance == null) {
            synchronized (MiguManager.class) {
                if (instance == null) {
                    instance = new MiguManager();
                }
            }
        }
        return instance;
    }

    public void init(final Application context) {
        this.context = context;
        initMiguSdk();
    }

    private String getProcessName(Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.pid == Process.myPid() && runningAppProcessInfo.processName != null) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }

    public void getCategory(final CategoryInterface categoryInterface) {
        if (System.currentTimeMillis() - SharedPreferenceUtil.queryLongValue("reqLastTimeKey") < 86400000) {
            try {
                final String queryValue = SharedPreferenceUtil.queryValue("miguSonglist");
                if (queryValue != null) {
                    LHomeLog.e("getMiguRadioInfo", MiguManager.class, "获取歌曲列表从本地记录");
                    new Thread(new Runnable() { // from class: com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager.1
                        @Override // java.lang.Runnable
                        public void run() {
                            MiguManager.this.parseJsonSongList(queryValue, categoryInterface);
                        }
                    }).start();
                    return;
                }
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        HttpClientManager.getMiguRadioInfo(new MiguResultCallback<String>() { // from class: com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager.2
            @Override // com.rich.czlylibary.sdk.MiguResultCallback
            public void onFinish() {
            }

            @Override // com.rich.czlylibary.sdk.MiguResultCallback
            public void onStart() {
            }

            @Override // com.rich.czlylibary.sdk.MiguResultCallback
            public void onSuccess(String s) {
                LHomeLog.e("getMiguRadioInfo", MiguManager.class, "____onSuccess=" + s);
                if (TextUtils.isEmpty(s) || s.contains("\"resCode\":\"999029\"")) {
                    categoryInterface.onFail(0);
                    return;
                }
                if (s.contains("\"resCode\":\"999030\"") || s.contains("\"resCode\":\"999022\"")) {
                    return;
                }
                LHomeLog.e("getMiguRadioInfo", MiguManager.class, "____onSuccess=" + s + Thread.currentThread().getName());
                SharedPreferenceUtil.edit().keepShared("miguSonglist", s);
                SharedPreferenceUtil.edit().keepShared("reqLastTimeKey", System.currentTimeMillis());
                MiguManager.this.parseJsonSongList(s, categoryInterface);
            }

            @Override // com.rich.czlylibary.sdk.MiguResultCallback
            public void onFailed(String resCode, String errorMsg) {
                LHomeLog.e("getMiguRadioInfo", MiguManager.class, "____onFailed__resCode=" + resCode + "__errorMsg=" + errorMsg);
                categoryInterface.onFail(0);
            }
        });
    }

    public void getLocalMusicList(final String mac, final IAction<List<SongListItemInfo>> iAction) {
        ((ObservableSubscribeProxy) Injection.net().getLocalMusicMusic(mac).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<MusicListResponse>() { // from class: com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager.3
            @Override // io.reactivex.functions.Consumer
            public void accept(MusicListResponse musicListResponse) throws Exception {
                ArrayList arrayList = new ArrayList();
                if (musicListResponse != null && musicListResponse.getRows() != null) {
                    for (MusicInfo musicInfo : musicListResponse.getRows()) {
                        SongListItemInfo songListItemInfo = new SongListItemInfo();
                        songListItemInfo.setName(musicInfo.getMusicname());
                        songListItemInfo.setSinger(!StringUtils.isEmpty(musicInfo.getSingername()) ? musicInfo.getSingername() : ActivityUtils.getTopActivity().getString(R.string.unknown_singer));
                        songListItemInfo.setSongId(musicInfo.getMusicid());
                        songListItemInfo.setSongid(musicInfo.getSongid());
                        songListItemInfo.setSorting(musicInfo.getSorting());
                        arrayList.add(songListItemInfo);
                    }
                    Collections.sort(arrayList, new Comparator<SongListItemInfo>(this) { // from class: com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager.3.1
                        @Override // java.util.Comparator
                        public int compare(SongListItemInfo o1, SongListItemInfo o2) {
                            return o1.getSorting() - o2.getSorting();
                        }
                    });
                }
                MiguManager.this.localSongs = arrayList;
                MiguManager.this.localSongListMap.put(mac, arrayList);
                IAction iAction2 = iAction;
                if (iAction2 != null) {
                    iAction2.act(arrayList);
                }
            }
        });
    }

    public int getSongListSize(String categoryName) {
        List<String> arrayList = new ArrayList<>();
        if (this.miguSongMap.containsKey(categoryName)) {
            arrayList = this.miguSongMap.get(categoryName).getSongIds();
        }
        return arrayList.size();
    }

    public int getSongListSize(String categoryName, String menuName) {
        int i;
        int i2 = 0;
        while (true) {
            if (i2 >= this.radiolist.size()) {
                i2 = -1;
                i = -1;
                break;
            }
            if (this.radiolist.get(i2).getColumnName().equalsIgnoreCase(categoryName)) {
                i = -1;
                for (int i3 = 0; i3 < this.radiolist.get(i2).getRadios().size(); i3++) {
                    if (this.radiolist.get(i2).getRadios().get(i3).getRadioName().equalsIgnoreCase(menuName)) {
                        i = i3;
                    }
                }
            } else {
                i2++;
            }
        }
        if (i2 == -1 || i == -1) {
            return 0;
        }
        return this.radiolist.get(i2).getRadios().get(i).getSongIds().size();
    }

    public void getSongList(String categoryName, int page, final SongListInterface songListInterface) {
        List<String> arrayList = new ArrayList<>();
        if (this.miguSongMap.containsKey(categoryName)) {
            arrayList = this.miguSongMap.get(categoryName).getSongIds();
        }
        if (arrayList.size() == 0) {
            return;
        }
        getSongList((ArrayList<String>) arrayList, page, songListInterface);
    }

    public void getCollectList(int page, final SongListInterface collectListInterface) {
        HttpClientManager.findMusicCollectionPage((page - 1) * 20, page * 20, "0", "M", new ResultCallback<QuerySheetMusicInfo>(this) { // from class: com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager.4
            @Override // com.rich.czlylibary.sdk.ResultCallback
            public void onFinish() {
            }

            @Override // com.rich.czlylibary.sdk.ResultCallback
            public void onStart() {
            }

            @Override // com.rich.czlylibary.sdk.ResultCallback
            public void onSuccess(QuerySheetMusicInfo querySheetMusicInfo) {
                ArrayList<com.rich.czlylibary.bean.MusicInfo> musicInfos = querySheetMusicInfo.getMusicInfos();
                if (musicInfos == null || musicInfos.size() == 0) {
                    collectListInterface.onFail();
                    return;
                }
                ArrayList arrayList = new ArrayList();
                Iterator<com.rich.czlylibary.bean.MusicInfo> it = musicInfos.iterator();
                while (it.hasNext()) {
                    com.rich.czlylibary.bean.MusicInfo next = it.next();
                    LHomeLog.e("findMusicCollectionPage", MiguManager.class, "musicInfo=" + next.toString());
                    SongListItemInfo songListItemInfo = new SongListItemInfo();
                    songListItemInfo.setSongId(next.getMusicId());
                    songListItemInfo.setPicUrl(next.getPicUrl());
                    songListItemInfo.setName(next.getMusicName());
                    songListItemInfo.setSinger(next.getSingerName());
                    songListItemInfo.setIsCollection(next.getIsCollection());
                    songListItemInfo.setAlbumNames(next.getAlbumNames());
                    if (!TextUtils.isEmpty(next.getLength())) {
                        songListItemInfo.setLength(next.getLength());
                    }
                    arrayList.add(songListItemInfo);
                }
                if (arrayList.size() == 0) {
                    collectListInterface.onFail();
                } else {
                    collectListInterface.onSuccess(arrayList);
                }
            }

            @Override // com.rich.czlylibary.sdk.ResultCallback
            public void onFailed(String s, String s1) {
                collectListInterface.onFail();
            }
        });
    }

    public void getCollectAllList(int page, final SongListInterface collectListInterface) {
        getCollect(page, collectListInterface, new ArrayList());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCollect(final int page, final SongListInterface collectListInterface, final List<SongListItemInfo> songListItemInfos) {
        HttpClientManager.findMusicCollectionPage((page - 1) * 20, page * 20, "0", "M", new ResultCallback<QuerySheetMusicInfo>() { // from class: com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager.5
            @Override // com.rich.czlylibary.sdk.ResultCallback
            public void onFinish() {
            }

            @Override // com.rich.czlylibary.sdk.ResultCallback
            public void onStart() {
            }

            @Override // com.rich.czlylibary.sdk.ResultCallback
            public void onSuccess(QuerySheetMusicInfo querySheetMusicInfo) {
                ArrayList<com.rich.czlylibary.bean.MusicInfo> musicInfos = querySheetMusicInfo.getMusicInfos();
                if (musicInfos == null || musicInfos.size() == 0) {
                    collectListInterface.onFail();
                    return;
                }
                Iterator<com.rich.czlylibary.bean.MusicInfo> it = musicInfos.iterator();
                while (it.hasNext()) {
                    com.rich.czlylibary.bean.MusicInfo next = it.next();
                    LHomeLog.e("findMusicCollectionPage", MiguManager.class, "musicInfo=" + next.toString());
                    SongListItemInfo songListItemInfo = new SongListItemInfo();
                    songListItemInfo.setSongId(next.getMusicId());
                    songListItemInfo.setPicUrl(next.getPicUrl());
                    songListItemInfo.setName(next.getMusicName());
                    songListItemInfo.setSinger(next.getSingerName());
                    songListItemInfo.setIsCollection(next.getIsCollection());
                    songListItemInfo.setAlbumNames(next.getAlbumNames());
                    if (!TextUtils.isEmpty(next.getLength())) {
                        songListItemInfo.setLength(next.getLength());
                    }
                    songListItemInfos.add(songListItemInfo);
                }
                if (songListItemInfos.size() == 0) {
                    collectListInterface.onFail();
                } else if (querySheetMusicInfo.getCount() == 20) {
                    MiguManager.this.getCollect(page + 1, collectListInterface, songListItemInfos);
                } else {
                    collectListInterface.onSuccess(songListItemInfos);
                }
            }

            @Override // com.rich.czlylibary.sdk.ResultCallback
            public void onFailed(String s, String s1) {
                collectListInterface.onFail();
            }
        });
    }

    public void getSongList(String categoryName, String menuName, int page, final SongListInterface songListInterface) {
        int i;
        int i2 = 0;
        while (true) {
            if (i2 >= this.radiolist.size()) {
                i = -1;
                i2 = -1;
                break;
            } else if (this.radiolist.get(i2).getColumnName().equalsIgnoreCase(categoryName)) {
                i = -1;
                for (int i3 = 0; i3 < this.radiolist.get(i2).getRadios().size(); i3++) {
                    if (this.radiolist.get(i2).getRadios().get(i3).getRadioName().equalsIgnoreCase(menuName)) {
                        i = i3;
                    }
                }
            } else {
                i2++;
            }
        }
        if (i2 == -1 || i == -1) {
            return;
        }
        getSongList(this.radiolist.get(i2).getRadios().get(i).getSongIds(), page, songListInterface);
    }

    public void getSongList(ArrayList<String> songIdsAll, int page, final SongListInterface songListInterface) {
        List<String> subList;
        int min = Math.min(songIdsAll.size(), 40);
        int i = (page - 1) * 20;
        if (min <= i) {
            return;
        }
        new ArrayList();
        int i2 = page * 20;
        if (min < i2) {
            subList = songIdsAll.subList(i, min);
        } else {
            subList = songIdsAll.subList(i, i2);
        }
        Iterator<String> it = subList.iterator();
        while (it.hasNext()) {
            LHomeLog.e("musicInfo", MiguManager.class, "songIdsPage=" + it.next());
        }
        if (subList.size() > 0) {
            HttpClientManager.batchFindMusicInfoByid(subList, "M", new ResultCallback<BatchMusicinfoResult>(this) { // from class: com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager.6
                @Override // com.rich.czlylibary.sdk.ResultCallback
                public void onFinish() {
                }

                @Override // com.rich.czlylibary.sdk.ResultCallback
                public void onStart() {
                }

                /* JADX WARN: Code restructure failed: missing block: B:14:0x00a6, code lost:
                
                    com.smart.message.utils.LHomeLog.e("musicInfo", com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager.class, "未找到歌曲");
                 */
                @Override // com.rich.czlylibary.sdk.ResultCallback
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public void onSuccess(com.rich.czlylibary.bean.BatchMusicinfoResult r10) {
                    /*
                        r9 = this;
                        java.util.ArrayList r0 = new java.util.ArrayList
                        r0.<init>()
                        java.util.List r10 = r10.getMusicInfos()
                        java.lang.Class<com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager> r1 = com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager.class
                        java.lang.StringBuilder r2 = new java.lang.StringBuilder
                        java.lang.String r3 = "musicInfo="
                        r2.<init>(r3)
                        int r4 = r10.size()
                        r2.append(r4)
                        java.lang.String r2 = r2.toString()
                        java.lang.String r4 = "musicInfo"
                        com.smart.message.utils.LHomeLog.e(r4, r1, r2)
                        java.util.Iterator r1 = r10.iterator()
                    L26:
                        boolean r2 = r1.hasNext()
                        if (r2 == 0) goto Lba
                        java.lang.Object r2 = r1.next()
                        com.rich.czlylibary.bean.MusicInfo r2 = (com.rich.czlylibary.bean.MusicInfo) r2
                        if (r2 == 0) goto La4
                        java.lang.String r5 = "0"
                        java.lang.String r6 = r2.getIsCpAuth()
                        boolean r5 = r5.equalsIgnoreCase(r6)
                        if (r5 != 0) goto La4
                        java.lang.String r5 = "1"
                        java.lang.String r6 = r2.getVIP()
                        boolean r5 = r5.equalsIgnoreCase(r6)
                        if (r5 != 0) goto L59
                        java.lang.String r5 = "2"
                        java.lang.String r6 = r2.getVIP()
                        boolean r5 = r5.equalsIgnoreCase(r6)
                        if (r5 != 0) goto L59
                        goto La4
                    L59:
                        com.ltech.smarthome.model.bean.SongListItemInfo r5 = new com.ltech.smarthome.model.bean.SongListItemInfo
                        r5.<init>()
                        java.lang.Class<com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager> r6 = com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager.class
                        java.lang.StringBuilder r7 = new java.lang.StringBuilder
                        r7.<init>(r3)
                        java.lang.String r8 = r2.toString()
                        r7.append(r8)
                        java.lang.String r7 = r7.toString()
                        com.smart.message.utils.LHomeLog.e(r4, r6, r7)
                        java.lang.String r6 = r2.getMusicName()
                        r5.setName(r6)
                        java.lang.String r6 = r2.getSingerName()
                        r5.setSinger(r6)
                        java.lang.String r6 = r2.getIsCollection()
                        r5.setIsCollection(r6)
                        java.lang.String r6 = r2.getMusicId()
                        r5.setSongId(r6)
                        java.lang.String r6 = r2.getLength()
                        boolean r6 = android.text.TextUtils.isEmpty(r6)
                        if (r6 != 0) goto La0
                        java.lang.String r2 = r2.getLength()
                        r5.setLength(r2)
                    La0:
                        r0.add(r5)
                        goto L26
                    La4:
                        if (r2 != 0) goto Lae
                        java.lang.Class<com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager> r2 = com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager.class
                        java.lang.String r5 = "未找到歌曲"
                        com.smart.message.utils.LHomeLog.e(r4, r2, r5)
                        goto Lb5
                    Lae:
                        java.lang.Class<com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager> r2 = com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager.class
                        java.lang.String r5 = "当前歌曲未授权，不能播放"
                        com.smart.message.utils.LHomeLog.e(r4, r2, r5)
                    Lb5:
                        r1.remove()
                        goto L26
                    Lba:
                        java.lang.Class<com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager> r1 = com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager.class
                        java.lang.StringBuilder r2 = new java.lang.StringBuilder
                        java.lang.String r3 = "musicInfo.123="
                        r2.<init>(r3)
                        int r3 = r10.size()
                        r2.append(r3)
                        java.lang.String r2 = r2.toString()
                        com.smart.message.utils.LHomeLog.e(r4, r1, r2)
                        com.ltech.smarthome.ui.device.super_panel.music.manager.SongListInterface r1 = r2
                        r1.onSuccess(r0)
                        java.util.LinkedList r0 = new java.util.LinkedList
                        r0.<init>()
                        r0.addAll(r10)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager.AnonymousClass6.onSuccess(com.rich.czlylibary.bean.BatchMusicinfoResult):void");
                }

                @Override // com.rich.czlylibary.sdk.ResultCallback
                public void onFailed(String resCode, String errorMsg) {
                    LHomeLog.e("onFailed", MiguManager.class, "resCode=" + resCode + "__errorMsg=" + errorMsg);
                    songListInterface.onFail();
                }
            });
        } else {
            songListInterface.onFail();
        }
    }

    public List<String> getSongMenu(int categoryPos) {
        ArrayList arrayList = new ArrayList();
        Iterator<RadiosInfo> it = this.radiolist.get(categoryPos).getRadios().iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getRadioName());
        }
        return arrayList;
    }

    public void searchSongInfo(String keySearch, final SearchSongInfoInterface searchSongInfoInterface) {
        HttpClientManager.searchSongsForVoiceBox(keySearch, 1, 40, 2, 1, 1, 1, 0, "001000", 0, "01", 0, null, new ResultCallback<SearchSongsForVoiceBox>(this) { // from class: com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager.7
            @Override // com.rich.czlylibary.sdk.ResultCallback
            public void onFinish() {
            }

            @Override // com.rich.czlylibary.sdk.ResultCallback
            public void onStart() {
            }

            @Override // com.rich.czlylibary.sdk.ResultCallback
            public void onSuccess(SearchSongsForVoiceBox searchSongsForVoiceBox) {
                List<SongNewVoiceBox> result = searchSongsForVoiceBox.getSearchVoiceBox().getData().getResult();
                if (result == null || result.size() == 0) {
                    LHomeLog.e("songNewVoiceBoxes", MiguManager.class, "未找到歌曲");
                    searchSongInfoInterface.onFail();
                } else {
                    searchSongInfoInterface.onSuccess(result);
                }
            }

            @Override // com.rich.czlylibary.sdk.ResultCallback
            public void onFailed(String s, String s1) {
                LHomeLog.e("searchSongsForVoiceBox", MiguManager.class, "onFailed=" + s + "___" + s1);
                searchSongInfoInterface.onFail();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parseJsonSongList(String json, CategoryInterface categoryInterface) {
        this.categorys.clear();
        try {
            JSONArray jSONArray = new JSONArray(json);
            int i = 0;
            while (i < jSONArray.length()) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String string = jSONObject.getString("columnName");
                this.categorys.add(string);
                LHomeLog.e("getMiguRadioInfo", MiguManager.class, "电台分类=" + string);
                RadioInfo radioInfo = new RadioInfo();
                radioInfo.setColumnName(string);
                radioInfo.setColumnId(jSONObject.getString("columnId"));
                JSONArray jSONArray2 = jSONObject.getJSONArray("radios");
                ArrayList<RadiosInfo> arrayList = new ArrayList<>();
                int i2 = 0;
                while (i2 < jSONArray2.length()) {
                    RadiosInfo radiosInfo = new RadiosInfo();
                    radiosInfo.setRadioName(jSONArray2.getJSONObject(i2).getString("radioName"));
                    radiosInfo.setRadioPic(jSONArray2.getJSONObject(i2).getString("radioPic"));
                    radiosInfo.setRadioId(jSONArray2.getJSONObject(i2).getString("radioId"));
                    JSONArray jSONArray3 = jSONArray2.getJSONObject(i2).getJSONArray("songIds");
                    ArrayList<String> arrayList2 = new ArrayList<>();
                    StringBuilder sb = new StringBuilder();
                    JSONArray jSONArray4 = jSONArray;
                    sb.append("电台栏目=");
                    sb.append(jSONArray2.getJSONObject(i2).getString("radioName"));
                    sb.append("__songIds=");
                    sb.append(jSONArray3.length());
                    LHomeLog.e("getMiguRadioInfo", MiguManager.class, sb.toString());
                    for (int i3 = 0; i3 < jSONArray3.length(); i3++) {
                        arrayList2.add(jSONArray3.getString(i3));
                    }
                    radiosInfo.setSongIds(arrayList2);
                    arrayList.add(radiosInfo);
                    i2++;
                    jSONArray = jSONArray4;
                }
                radioInfo.setRadios(arrayList);
                this.radiolist.add(radioInfo);
                i++;
                jSONArray = jSONArray;
            }
            this.miguRadioMap = new HashMap<>();
            for (MiguRadioBean miguRadioBean : (List) GsonUtils.fromJson(json, new TypeToken<List<MiguRadioBean>>(this) { // from class: com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager.8
            }.getType())) {
                HashMap hashMap = new HashMap();
                LHomeLog.e("MiguRadioBean", MiguManager.class, "bean=" + miguRadioBean.toString());
                for (MiguRadioBean.RadiosDTO radiosDTO : miguRadioBean.getRadios()) {
                    hashMap.put(radiosDTO.getRadioName(), radiosDTO);
                    LHomeLog.e("RadiosDTO", MiguManager.class, "radiosDTO=" + radiosDTO.toString());
                }
                this.miguRadioMap.put(miguRadioBean.getColumnName(), hashMap);
            }
            categoryInterface.onSuccess(this.categorys);
        } catch (JSONException e) {
            e.printStackTrace();
            categoryInterface.onFail(0);
            LHomeLog.e("getMiguRadioInfo", MiguManager.class, "JSONException=" + e.getMessage());
        } catch (Exception e2) {
            LHomeLog.e("getMiguRadioInfo", MiguManager.class, "Exception=" + e2.getMessage());
            categoryInterface.onFail(0);
        }
    }

    public void setCollect(String musicId, final boolean isCollect, final SongInterface songInterface) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(musicId);
        if (isCollect) {
            HttpClientManager.addMusicCollection(arrayList, "0", new ResultCallback<Result>(this) { // from class: com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager.9
                @Override // com.rich.czlylibary.sdk.ResultCallback
                public void onFinish() {
                }

                @Override // com.rich.czlylibary.sdk.ResultCallback
                public void onStart() {
                }

                @Override // com.rich.czlylibary.sdk.ResultCallback
                public void onSuccess(Result result) {
                    songInterface.onSuccess();
                }

                @Override // com.rich.czlylibary.sdk.ResultCallback
                public void onFailed(String s, String s1) {
                    songInterface.onFail();
                }
            });
        } else {
            HttpClientManager.cancelMusicCollection(arrayList, "0", new ResultCallback<Result>(this) { // from class: com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager.10
                @Override // com.rich.czlylibary.sdk.ResultCallback
                public void onFinish() {
                }

                @Override // com.rich.czlylibary.sdk.ResultCallback
                public void onStart() {
                }

                @Override // com.rich.czlylibary.sdk.ResultCallback
                public void onSuccess(Result result) {
                    songInterface.onSuccess();
                }

                @Override // com.rich.czlylibary.sdk.ResultCallback
                public void onFailed(String resCode, String errorMsg) {
                    songInterface.onFail();
                }
            });
        }
    }

    private void initMiguSdk() {
        String processName = getProcessName(this.context);
        if (processName == null || !this.context.getApplicationContext().getPackageName().equals(processName)) {
            return;
        }
        MiguCzlySDK.getInstance().init(this.context);
        LHomeLog.e("MiguCzlySDK", MiguManager.class, "init MiguCzly");
    }

    public void getRankSongs() {
        HttpClientManager.getMiguRankInfo(new MiguResultCallback<String>() { // from class: com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager.11
            @Override // com.rich.czlylibary.sdk.MiguResultCallback
            public void onFailed(String s, String s1) {
            }

            @Override // com.rich.czlylibary.sdk.MiguResultCallback
            public void onFinish() {
            }

            @Override // com.rich.czlylibary.sdk.MiguResultCallback
            public void onStart() {
            }

            @Override // com.rich.czlylibary.sdk.MiguResultCallback
            public void onSuccess(String s) {
                MiguManager.this.parseJsonSongList(s);
            }
        });
    }

    public void getNewSongs(final SongInterface rankInterface) {
        HttpClientManager.getMiguNewSongInfo(new MiguResultCallback<String>() { // from class: com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager.12
            @Override // com.rich.czlylibary.sdk.MiguResultCallback
            public void onFinish() {
            }

            @Override // com.rich.czlylibary.sdk.MiguResultCallback
            public void onStart() {
            }

            @Override // com.rich.czlylibary.sdk.MiguResultCallback
            public void onSuccess(String s) {
                LHomeLog.e("NEW_SONG", MiguManager.class, s);
                MiguManager.this.parseJsonSongList(s);
                rankInterface.onSuccess();
            }

            @Override // com.rich.czlylibary.sdk.MiguResultCallback
            public void onFailed(String s, String s1) {
                rankInterface.onFail();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parseJsonSongList(String json) {
        try {
            JSONArray jSONArray = new JSONArray(json);
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String string = jSONObject.getString("columnName");
                MiguRadioBean.RadiosDTO radiosDTO = new MiguRadioBean.RadiosDTO();
                radiosDTO.setRadioName(string);
                radiosDTO.setRadioId(jSONObject.getString("columnId"));
                ArrayList arrayList = new ArrayList();
                JSONArray jSONArray2 = jSONObject.getJSONArray("songIds");
                for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                    arrayList.add(jSONArray2.getString(i2));
                }
                radiosDTO.setSongIds(arrayList);
                this.miguSongMap.put(string, radiosDTO);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            SharedPreferenceUtil.edit().keepShared("reqLastTimeKey", 0L);
            LHomeLog.e("getMiguRadioInfo", MiguManager.class, "JSONException=" + e.getMessage());
        } catch (Exception e2) {
            LHomeLog.e("getMiguRadioInfo", MiguManager.class, "Exception=" + e2.getMessage());
            SharedPreferenceUtil.edit().keepShared("reqLastTimeKey", 0L);
        }
    }

    public MiguRadioBean.RadiosDTO getRadioMenuData(String cateName, String menuName) {
        Map<String, MiguRadioBean.RadiosDTO> map;
        HashMap<String, Map<String, MiguRadioBean.RadiosDTO>> hashMap = this.miguRadioMap;
        if (hashMap == null || !hashMap.containsKey(cateName) || (map = this.miguRadioMap.get(cateName)) == null || !map.containsKey(menuName)) {
            return null;
        }
        return map.get(menuName);
    }

    public void findMusicInfoById(String id, final SingleSongInterface singleSongInterface) {
        HttpClientManager.findMusicInfoByid(id, "m", new ResultCallback<MusicinfoResult>(this) { // from class: com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager.13
            @Override // com.rich.czlylibary.sdk.ResultCallback
            public void onFinish() {
            }

            @Override // com.rich.czlylibary.sdk.ResultCallback
            public void onStart() {
            }

            @Override // com.rich.czlylibary.sdk.ResultCallback
            public void onSuccess(MusicinfoResult musicinfoResult) {
                singleSongInterface.onSuccess(musicinfoResult.getMusicInfo());
            }

            @Override // com.rich.czlylibary.sdk.ResultCallback
            public void onFailed(String s, String s1) {
                singleSongInterface.onFail();
            }
        });
    }
}