package com.ltech.smarthome.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public class CountryListAdapter extends BaseExpandableListAdapter {
    private Activity activity;
    private String[][] child;
    private String[] group;
    private LayoutInflater layoutInflater;

    @Override // android.widget.ExpandableListAdapter
    public long getChildId(int groupPosition, int childPosition) {
        return 0L;
    }

    @Override // android.widget.ExpandableListAdapter
    public long getGroupId(int groupPosition) {
        return 0L;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean hasStableIds() {
        return false;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public CountryListAdapter(Activity activity, String[] group, String[][] child) {
        this.activity = activity;
        this.group = group;
        this.child = child;
        this.layoutInflater = activity.getLayoutInflater();
    }

    @Override // android.widget.ExpandableListAdapter
    public int getGroupCount() {
        return this.group.length;
    }

    @Override // android.widget.ExpandableListAdapter
    public int getChildrenCount(int groupPosition) {
        return this.child[groupPosition].length;
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getGroup(int groupPosition) {
        return this.group[groupPosition];
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getChild(int groupPosition, int childPosition) {
        return this.child[groupPosition][childPosition];
    }

    @Override // android.widget.ExpandableListAdapter
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewGroupHolder viewGroupHolder;
        if (convertView == null) {
            viewGroupHolder = new ViewGroupHolder();
            convertView = this.layoutInflater.inflate(R.layout.act_country_group_item, (ViewGroup) null);
            viewGroupHolder.tvGroupView = (TextView) convertView.findViewById(R.id.tv_key);
            convertView.setTag(viewGroupHolder);
        } else {
            viewGroupHolder = (ViewGroupHolder) convertView.getTag();
        }
        viewGroupHolder.tvGroupView.setText(this.group[groupPosition]);
        return convertView;
    }

    @Override // android.widget.ExpandableListAdapter
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            View inflate = this.layoutInflater.inflate(R.layout.act_country_child_item, (ViewGroup) null);
            ViewChildHolder viewChildHolder = new ViewChildHolder();
            viewChildHolder.tvChildView = (TextView) inflate.findViewById(R.id.tv_country);
            inflate.setTag(viewChildHolder);
            return inflate;
        }
        return convertView;
    }

    static class ViewGroupHolder {
        TextView tvGroupView;

        ViewGroupHolder() {
        }
    }

    static class ViewChildHolder {
        TextView tvChildView;

        ViewChildHolder() {
        }
    }
}