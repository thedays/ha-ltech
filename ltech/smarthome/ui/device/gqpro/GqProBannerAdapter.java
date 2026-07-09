package com.ltech.smarthome.ui.device.gqpro;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.util.BannerUtils;
import java.util.List;

/* loaded from: classes4.dex */
public class GqProBannerAdapter extends BannerAdapter<Data, BannerHolder> {
    public GqProBannerAdapter(List<Data> mDatas) {
        super(mDatas);
    }

    public void updateData(List<Data> data) {
        this.mDatas.clear();
        this.mDatas.addAll(data);
        notifyDataSetChanged();
    }

    @Override // com.youth.banner.holder.IViewHolder
    public BannerHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new BannerHolder(BannerUtils.getView(parent, R.layout.item_gq_pro_banner));
    }

    @Override // com.youth.banner.holder.IViewHolder
    public void onBindView(BannerHolder holder, Data data, int position, int size) {
        holder.imageView.setBackgroundResource(data.color);
        holder.textView.setText(data.name);
    }

    public static class Data {
        private int color;
        private String name;

        public Data(int color, String name) {
            this.color = color;
            this.name = name;
        }

        public int getColor() {
            return this.color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class BannerHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public BannerHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.iv);
            this.textView = (TextView) view.findViewById(R.id.tv);
        }
    }
}