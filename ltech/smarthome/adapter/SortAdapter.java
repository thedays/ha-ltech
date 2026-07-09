package com.ltech.smarthome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.net.response.user.CountryInfoResponse;
import com.ltech.smarthome.utils.LanguageUtils;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes3.dex */
public class SortAdapter extends android.widget.BaseAdapter implements SectionIndexer {
    private List<CountryInfoResponse> list;
    private Context mContext;

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    @Override // android.widget.SectionIndexer
    public Object[] getSections() {
        return null;
    }

    public SortAdapter(Context mContext, List<CountryInfoResponse> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public void updateListView(List<CountryInfoResponse> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.list.size();
    }

    @Override // android.widget.Adapter
    public CountryInfoResponse getItem(int position) {
        return this.list.get(position);
    }

    @Override // android.widget.Adapter
    public View getView(final int position, View view, ViewGroup arg2) {
        View view2;
        ViewHolder viewHolder;
        CountryInfoResponse countryInfoResponse = this.list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view2 = LayoutInflater.from(this.mContext).inflate(R.layout.sort_item, (ViewGroup) null);
            viewHolder.tvTitle = (TextView) view2.findViewById(R.id.title);
            viewHolder.tvLetter = (TextView) view2.findViewById(R.id.catalog);
            view2.setTag(viewHolder);
        } else {
            view2 = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        if (position == getPositionForSection(getSectionForPosition(position))) {
            viewHolder.tvLetter.setVisibility(0);
            viewHolder.tvLetter.setText(countryInfoResponse.getSortLetters());
        } else {
            viewHolder.tvLetter.setVisibility(8);
        }
        viewHolder.tvTitle.setText(LanguageUtils.isChinese(this.mContext) ? this.list.get(position).getCountry() : this.list.get(position).getCountry_en());
        return view2;
    }

    static final class ViewHolder {
        TextView tvLetter;
        TextView tvTitle;

        ViewHolder() {
        }
    }

    @Override // android.widget.SectionIndexer
    public int getSectionForPosition(int position) {
        return this.list.get(position).getSortLetters().charAt(0);
    }

    @Override // android.widget.SectionIndexer
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            if (this.list.get(i).getSortLetters().toUpperCase().charAt(0) == section) {
                return i;
            }
        }
        return -1;
    }

    private String getAlpha(String str) {
        String upperCase = str.trim().substring(0, 1).toUpperCase();
        return upperCase.matches("[A-Z]") ? upperCase : MqttTopic.MULTI_LEVEL_WILDCARD;
    }
}