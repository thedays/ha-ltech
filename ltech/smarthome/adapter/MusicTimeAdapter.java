package com.ltech.smarthome.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.BaseAdapter;

/* loaded from: classes3.dex */
public final class MusicTimeAdapter extends MyAdapter<String> {
    public MusicTimeAdapter(Context context) {
        super(context);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends BaseAdapter.ViewHolder {
        private final TextView mPickerView;

        ViewHolder() {
            super(MusicTimeAdapter.this, R.layout.item_music_time);
            this.mPickerView = (TextView) findViewById(R.id.tv_picker_name);
        }

        @Override // com.ltech.smarthome.adapter.BaseAdapter.ViewHolder
        public void onBindView(int position) {
            this.mPickerView.setText(MusicTimeAdapter.this.getItem(position));
        }
    }
}