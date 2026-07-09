package com.ltech.smarthome.utils.selectedCountryLib.demo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.utils.selectedCountryLib.demo.GroupListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes4.dex */
public class CountryAdapter extends GroupListView.GroupAdapter {
    private Context context;
    private ArrayList<ArrayList<String[]>> countries;
    private HashMap<Character, ArrayList<String[]>> rawData;
    private SearchEngine sEngine;
    private ArrayList<String> titles;

    public CountryAdapter(Context context, GroupListView view) {
        super(view);
        this.context = context;
        this.rawData = CountryUtils.getGroupedCountryList(context);
        initSearchEngine();
        search(null);
    }

    private void initSearchEngine() {
        this.sEngine = new SearchEngine();
        ArrayList<String> arrayList = new ArrayList<>();
        Iterator<Map.Entry<Character, ArrayList<String[]>>> it = this.rawData.entrySet().iterator();
        while (it.hasNext()) {
            Iterator<String[]> it2 = it.next().getValue().iterator();
            while (it2.hasNext()) {
                arrayList.add(it2.next()[0]);
            }
        }
        this.sEngine.setIndex(arrayList);
    }

    public void search(String token) {
        boolean z;
        ArrayList<String> match = this.sEngine.match(token);
        if (match == null || match.size() <= 0) {
            match = new ArrayList<>();
            z = true;
        } else {
            z = false;
        }
        HashMap hashMap = new HashMap();
        Iterator<String> it = match.iterator();
        while (it.hasNext()) {
            String next = it.next();
            hashMap.put(next, next);
        }
        this.titles = new ArrayList<>();
        this.countries = new ArrayList<>();
        for (Map.Entry<Character, ArrayList<String[]>> entry : this.rawData.entrySet()) {
            ArrayList<String[]> value = entry.getValue();
            ArrayList<String[]> arrayList = new ArrayList<>();
            Iterator<String[]> it2 = value.iterator();
            while (it2.hasNext()) {
                String[] next2 = it2.next();
                if (z || hashMap.containsKey(next2[0])) {
                    arrayList.add(next2);
                }
            }
            if (arrayList.size() > 0) {
                this.titles.add(String.valueOf(entry.getKey()));
                this.countries.add(arrayList);
            }
        }
    }

    @Override // com.ltech.smarthome.utils.selectedCountryLib.demo.GroupListView.GroupAdapter
    public int getGroupCount() {
        ArrayList<String> arrayList = this.titles;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    @Override // com.ltech.smarthome.utils.selectedCountryLib.demo.GroupListView.GroupAdapter
    public int getCount(int group) {
        ArrayList<String[]> arrayList;
        ArrayList<ArrayList<String[]>> arrayList2 = this.countries;
        if (arrayList2 == null || (arrayList = arrayList2.get(group)) == null) {
            return 0;
        }
        return arrayList.size();
    }

    @Override // com.ltech.smarthome.utils.selectedCountryLib.demo.GroupListView.GroupAdapter
    public String getGroupTitle(int group) {
        if (this.titles.size() != 0) {
            return this.titles.get(group);
        }
        return null;
    }

    @Override // com.ltech.smarthome.utils.selectedCountryLib.demo.GroupListView.GroupAdapter
    public String[] getItem(int group, int position) {
        if (this.countries.size() != 0) {
            try {
                return this.countries.get(group).get(position);
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void setlocalItem2Frist(String token) {
        boolean z;
        ArrayList<String> match = this.sEngine.match(token);
        if (match == null || match.size() <= 0) {
            match = new ArrayList<>();
            z = true;
        } else {
            z = false;
        }
        HashMap hashMap = new HashMap();
        Iterator<String> it = match.iterator();
        while (it.hasNext()) {
            String next = it.next();
            hashMap.put(next, next);
        }
        Iterator<Map.Entry<Character, ArrayList<String[]>>> it2 = this.rawData.entrySet().iterator();
        while (it2.hasNext()) {
            ArrayList<String[]> value = it2.next().getValue();
            ArrayList<String[]> arrayList = new ArrayList<>();
            Iterator<String[]> it3 = value.iterator();
            while (it3.hasNext()) {
                String[] next2 = it3.next();
                if (z || hashMap.containsKey(next2[0])) {
                    arrayList.add(next2);
                }
            }
            if (arrayList.size() > 0) {
                this.titles.add(0, this.context.getString(R.string.current_locat));
                this.countries.add(0, arrayList);
            }
        }
        notifyDataSetChanged();
    }

    @Override // com.ltech.smarthome.utils.selectedCountryLib.demo.GroupListView.GroupAdapter
    public View getTitleView(int i, View view, ViewGroup viewGroup) {
        View view2 = view;
        if (view == null) {
            LinearLayout linearLayout = new LinearLayout(viewGroup.getContext());
            linearLayout.setOrientation(1);
            linearLayout.setBackgroundColor(-1);
            TextView textView = new TextView(viewGroup.getContext());
            textView.setTextSize(1, 11.0f);
            textView.setTextColor(viewGroup.getContext().getResources().getColor(R.color.smssdk_lv_title_color));
            int dipToPx = dipToPx(viewGroup.getContext(), 6);
            textView.setPadding(0, dipToPx, 0, dipToPx);
            textView.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
            linearLayout.addView(textView);
            View view3 = new View(viewGroup.getContext());
            view3.setBackgroundColor(-1842205);
            linearLayout.addView(view3, new LinearLayout.LayoutParams(-1, 1));
            view2 = linearLayout;
        }
        ((TextView) ((LinearLayout) view2).getChildAt(0)).setText(getGroupTitle(i));
        return view2;
    }

    @Override // com.ltech.smarthome.utils.selectedCountryLib.demo.GroupListView.GroupAdapter
    public void onGroupChange(View titleView, String title) {
        ((TextView) ((LinearLayout) titleView).getChildAt(0)).setText(title);
    }

    @Override // com.ltech.smarthome.utils.selectedCountryLib.demo.GroupListView.GroupAdapter
    public View getView(int i, int i2, View view, ViewGroup viewGroup) {
        View view2 = view;
        if (view == null) {
            LinearLayout linearLayout = new LinearLayout(viewGroup.getContext());
            linearLayout.setBackgroundColor(-1);
            TextView textView = new TextView(viewGroup.getContext());
            textView.setTextSize(2, 16.0f);
            int dipToPx = dipToPx(viewGroup.getContext(), 16);
            textView.setPadding(0, dipToPx, 0, dipToPx);
            linearLayout.addView(textView, new LinearLayout.LayoutParams(-1, -2));
            view2 = linearLayout;
        }
        String[] item = getItem(i, i2);
        if (item != null) {
            TextView textView2 = (TextView) ((LinearLayout) view2).getChildAt(0);
            textView2.setText(item[0]);
            if (this.titles.get(i).equals(this.context.getString(R.string.current_locat))) {
                textView2.setTextColor(viewGroup.getContext().getResources().getColor(R.color.lit_col_1));
                Drawable drawable = this.context.getResources().getDrawable(R.mipmap.location_ic);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                textView2.setCompoundDrawables(drawable, null, null, null);
                return view2;
            }
            textView2.setTextColor(viewGroup.getContext().getResources().getColor(R.color.smssdk_lv_tv_color));
            textView2.setCompoundDrawables(null, null, null, null);
        }
        return view2;
    }

    private int dipToPx(Context context, int dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}