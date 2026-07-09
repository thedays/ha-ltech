package com.ltech.smarthome.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import java.util.List;

/* loaded from: classes4.dex */
public class DeviceManagerSpinnerAdapter extends BaseAdapter {
    private Context mContext;
    private List<Object> mObjectList;
    private int selectPosition = -1;

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    public DeviceManagerSpinnerAdapter(Context context, List<Object> objectList) {
        this.mObjectList = objectList;
        this.mContext = context;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mObjectList.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        return this.mObjectList.get(position);
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.item_select_device_manage, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_icon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.getPaint().setFakeBoldText(true);
        viewHolder.textView.setTextSize(16.0f);
        viewHolder.imageView.setVisibility(0);
        if (this.mObjectList.get(position) instanceof Floor) {
            viewHolder.textView.setText(((Floor) this.mObjectList.get(position)).getFloorName());
            return convertView;
        }
        if (this.mObjectList.get(position) instanceof Room) {
            viewHolder.textView.setText(((Room) this.mObjectList.get(position)).getRoomName());
        }
        return convertView;
    }

    @Override // android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.item_select_device_manage, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.getPaint().setFakeBoldText(true);
        viewHolder.textView.setTextSize(14.0f);
        viewHolder.textView.setTextColor(ContextCompat.getColor(this.mContext, this.selectPosition == position ? R.color.color_text_red : R.color.color_text_black));
        if (this.mObjectList.get(position) instanceof Floor) {
            viewHolder.textView.setText(((Floor) this.mObjectList.get(position)).getFloorName());
            return convertView;
        }
        if (this.mObjectList.get(position) instanceof Room) {
            viewHolder.textView.setText(((Room) this.mObjectList.get(position)).getRoomName());
        }
        return convertView;
    }

    static final class ViewHolder {
        ImageView imageView;
        TextView textView;

        ViewHolder() {
        }
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }
}