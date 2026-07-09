package com.ltech.smarthome.utils.selectedCountryLib.demo;

import android.content.Context;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;
import m.framework.utils.Hashon;

/* loaded from: classes4.dex */
public class SearchEngine {
    private static final String DB_FILE = "smssdk_pydb";
    private static HashMap<String, Object> hanzi2Pinyin;
    private boolean caseSensitive;
    private ArrayList<SearchIndex> index;

    public static void prepare(final Context context, final Runnable afterPrepare) {
        Runnable runnable = new Runnable() { // from class: com.ltech.smarthome.utils.selectedCountryLib.demo.SearchEngine.1
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x0060 -> B:19:0x006b). Please report as a decompilation issue!!! */
            @Override // java.lang.Runnable
            public void run() {
                synchronized (SearchEngine.DB_FILE) {
                    if (SearchEngine.hanzi2Pinyin == null || SearchEngine.hanzi2Pinyin.size() <= 0) {
                        try {
                            int identifier = context.getResources().getIdentifier(SearchEngine.DB_FILE, "raw", context.getPackageName());
                            if (identifier <= 0) {
                                SearchEngine.hanzi2Pinyin = new HashMap();
                            } else {
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(context.getResources().openRawResource(identifier))));
                                String readLine = bufferedReader.readLine();
                                bufferedReader.close();
                                SearchEngine.hanzi2Pinyin = new Hashon().fromJson(readLine);
                            }
                        } catch (Throwable th) {
                            th.printStackTrace();
                            SearchEngine.hanzi2Pinyin = new HashMap();
                        }
                    }
                    Runnable runnable2 = afterPrepare;
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }
            }
        };
        if (afterPrepare != null) {
            new Thread(runnable).start();
        } else {
            runnable.run();
        }
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public void setIndex(ArrayList<String> index) {
        this.index = new ArrayList<>();
        Iterator<String> it = index.iterator();
        while (it.hasNext()) {
            this.index.add(new SearchIndex(it.next(), hanzi2Pinyin));
        }
    }

    public ArrayList<String> match(String token) {
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<SearchIndex> arrayList2 = this.index;
        if (arrayList2 != null) {
            Iterator<SearchIndex> it = arrayList2.iterator();
            while (it.hasNext()) {
                SearchIndex next = it.next();
                if (next.match(token, this.caseSensitive)) {
                    arrayList.add(next.getText());
                }
            }
        }
        return arrayList;
    }

    private static class SearchIndex {
        private String text;
        private ArrayList<String> pinyin = new ArrayList<>();
        private ArrayList<String> firstLatters = new ArrayList<>();

        public SearchIndex(String text, HashMap<String, Object> hanzi2Pinyin) {
            this.text = text;
            createPinyinList(hanzi2Pinyin);
        }

        private void createPinyinList(HashMap<String, Object> hanzi2Pinyin) {
            if (hanzi2Pinyin == null || hanzi2Pinyin.size() <= 0) {
                return;
            }
            char[] charArray = this.text.toCharArray();
            ArrayList<String[]> arrayList = new ArrayList<>();
            for (char c2 : charArray) {
                ArrayList arrayList2 = (ArrayList) hanzi2Pinyin.get(String.valueOf(c2));
                int size = arrayList2 == null ? 0 : arrayList2.size();
                String[] strArr = new String[size];
                for (int i = 0; i < size; i++) {
                    String str = (String) ((HashMap) arrayList2.get(i)).get("yin");
                    if ("none".equals(str)) {
                        str = "";
                    }
                    strArr[i] = str;
                }
                arrayList.add(strArr);
            }
            HashSet<String> hashSet = new HashSet<>();
            HashSet<String> hashSet2 = new HashSet<>();
            toPinyinArray("", "", hashSet, hashSet2, arrayList);
            this.pinyin.addAll(hashSet);
            this.firstLatters.addAll(hashSet2);
        }

        private void toPinyinArray(String base, String firstLatter, HashSet<String> pyRes, HashSet<String> flRes, ArrayList<String[]> pys) {
            String str;
            String str2;
            HashSet<String> hashSet;
            HashSet<String> hashSet2;
            if (pys.size() > 0) {
                String[] strArr = pys.get(0);
                ArrayList<String[]> arrayList = new ArrayList<>();
                arrayList.addAll(pys);
                arrayList.remove(0);
                int length = strArr.length;
                int i = 0;
                while (i < length) {
                    String str3 = strArr[i];
                    if (str3.length() > 0) {
                        hashSet = pyRes;
                        hashSet2 = flRes;
                        toPinyinArray(base + str3, firstLatter + str3.charAt(0), hashSet, hashSet2, arrayList);
                        str = base;
                        str2 = firstLatter;
                    } else {
                        str = base;
                        str2 = firstLatter;
                        hashSet = pyRes;
                        hashSet2 = flRes;
                        toPinyinArray(str, str2, hashSet, hashSet2, arrayList);
                    }
                    i++;
                    base = str;
                    firstLatter = str2;
                    pyRes = hashSet;
                    flRes = hashSet2;
                }
                return;
            }
            pyRes.add(base);
            flRes.add(firstLatter);
        }

        public String getText() {
            return this.text;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean match(String token, boolean caseSensitive) {
            if (token == null || token.trim().length() <= 0) {
                return true;
            }
            if (!caseSensitive) {
                token = token.toLowerCase();
            }
            String str = this.text;
            if (str != null && str.toLowerCase().contains(token)) {
                return true;
            }
            Iterator<String> it = this.pinyin.iterator();
            while (it.hasNext()) {
                if (it.next().contains(token)) {
                    return true;
                }
            }
            Iterator<String> it2 = this.firstLatters.iterator();
            while (it2.hasNext()) {
                if (it2.next().contains(token)) {
                    return true;
                }
            }
            return false;
        }

        public String toString() {
            HashMap hashMap = new HashMap();
            hashMap.put("text", this.text);
            hashMap.put("pinyin", this.pinyin);
            hashMap.put("firstLatters", this.firstLatters);
            return hashMap.toString();
        }
    }
}