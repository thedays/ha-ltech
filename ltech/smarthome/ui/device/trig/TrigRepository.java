package com.ltech.smarthome.ui.device.trig;

import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class TrigRepository {
    public static List<TrigItem> getDefaultSceneItemList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new TrigItem(R.mipmap.trig_pic_1, ActivityUtils.getTopActivity().getString(R.string.app_str_trig_scene1), 1, "CH1"));
        arrayList.add(new TrigItem(R.mipmap.trig_pic_2, ActivityUtils.getTopActivity().getString(R.string.app_str_trig_scene2), 1, "CH2"));
        arrayList.add(new TrigItem(R.mipmap.trig_pic_3, ActivityUtils.getTopActivity().getString(R.string.app_str_trig_scene3), 1, "CH1、2"));
        arrayList.add(new TrigItem(R.mipmap.trig_pic_4, ActivityUtils.getTopActivity().getString(R.string.app_str_trig_scene4), 1, "CH3"));
        arrayList.add(new TrigItem(R.mipmap.trig_pic_5, ActivityUtils.getTopActivity().getString(R.string.app_str_trig_scene5), 1, "CH1、3"));
        arrayList.add(new TrigItem(R.mipmap.trig_pic_6, ActivityUtils.getTopActivity().getString(R.string.app_str_trig_scene6), 1, "CH2、3"));
        arrayList.add(new TrigItem(R.mipmap.trig_pic_7, ActivityUtils.getTopActivity().getString(R.string.app_str_trig_scene7), 1, "CH1、2、3"));
        arrayList.add(new TrigItem(R.mipmap.trig_pic_8, ActivityUtils.getTopActivity().getString(R.string.app_str_trig_scene8), 1, "CH4"));
        arrayList.add(new TrigItem(R.mipmap.trig_pic_9, ActivityUtils.getTopActivity().getString(R.string.app_str_trig_scene9), 1, "CH1、4"));
        arrayList.add(new TrigItem(R.mipmap.trig_pic_10, ActivityUtils.getTopActivity().getString(R.string.app_str_trig_scene10), 1, "CH2、4"));
        arrayList.add(new TrigItem(R.mipmap.trig_pic_11, ActivityUtils.getTopActivity().getString(R.string.app_str_trig_scene11), 1, "CH1、2、4"));
        arrayList.add(new TrigItem(R.mipmap.trig_pic_12, ActivityUtils.getTopActivity().getString(R.string.app_str_trig_scene12), 1, "CH3、4"));
        arrayList.add(new TrigItem(R.mipmap.trig_pic_13, ActivityUtils.getTopActivity().getString(R.string.app_str_trig_scene13), 1, "CH1、3、4"));
        arrayList.add(new TrigItem(R.mipmap.trig_pic_14, ActivityUtils.getTopActivity().getString(R.string.app_str_trig_scene14), 1, "CH2、3、4"));
        arrayList.add(new TrigItem(R.mipmap.trig_pic_15, ActivityUtils.getTopActivity().getString(R.string.app_str_trig_scene15), 2, "CH1、2、3、4"));
        return arrayList;
    }

    public static List<TrigItem> getDefaultScene8ItemList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new TrigItem(R.mipmap.trig_pic_1, ActivityUtils.getTopActivity().getString(R.string.app_str_trig_scene1), 1, "CH1 ON"));
        arrayList.add(new TrigItem(R.mipmap.trig_pic_1, ActivityUtils.getTopActivity().getString(R.string.app_str_trig_scene1), 1, "CH1 OFF"));
        arrayList.add(new TrigItem(R.mipmap.trig_pic_2, ActivityUtils.getTopActivity().getString(R.string.app_str_trig_scene2), 1, "CH2 ON"));
        arrayList.add(new TrigItem(R.mipmap.trig_pic_2, ActivityUtils.getTopActivity().getString(R.string.app_str_trig_scene2), 1, "CH2 OFF"));
        arrayList.add(new TrigItem(R.mipmap.trig_pic_4, ActivityUtils.getTopActivity().getString(R.string.app_str_trig_scene4), 1, "CH3 ON"));
        arrayList.add(new TrigItem(R.mipmap.trig_pic_4, ActivityUtils.getTopActivity().getString(R.string.app_str_trig_scene4), 1, "CH3 OFF"));
        arrayList.add(new TrigItem(R.mipmap.trig_pic_8, ActivityUtils.getTopActivity().getString(R.string.app_str_trig_scene8), 1, "CH4 ON"));
        arrayList.add(new TrigItem(R.mipmap.trig_pic_8, ActivityUtils.getTopActivity().getString(R.string.app_str_trig_scene8), 1, "CH4 OFF"));
        return arrayList;
    }

    public static List<TrigItem> getDefaultCurtainChannelItemList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new TrigItem(R.mipmap.trig_pic_1, ActivityUtils.getTopActivity().getString(R.string.app_str_channel) + "1", 1));
        arrayList.add(new TrigItem(R.mipmap.trig_pic_2, ActivityUtils.getTopActivity().getString(R.string.app_str_channel) + "2", 2));
        arrayList.add(new TrigItem(R.mipmap.trig_pic_4, ActivityUtils.getTopActivity().getString(R.string.app_str_channel) + "3", 4));
        arrayList.add(new TrigItem(R.mipmap.trig_pic_8, ActivityUtils.getTopActivity().getString(R.string.app_str_channel) + "4", 8));
        return arrayList;
    }

    public static List<TrigItem> getDreamCurtainChannelItemList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new TrigItem(R.mipmap.icon_open_3, ActivityUtils.getTopActivity().getString(R.string.open_curtain), 1));
        arrayList.add(new TrigItem(R.mipmap.icon_close_4, ActivityUtils.getTopActivity().getString(R.string.close_curtain), 2));
        arrayList.add(new TrigItem(R.mipmap.cgcur_icon_stop, ActivityUtils.getTopActivity().getString(R.string.preview_stop), 3));
        arrayList.add(new TrigItem(R.mipmap.trig_cur_icon_left, ActivityUtils.getTopActivity().getString(R.string.turn_left), 4));
        arrayList.add(new TrigItem(R.mipmap.trig_cur_icon_right, ActivityUtils.getTopActivity().getString(R.string.turn_right), 8));
        arrayList.add(new TrigItem(R.mipmap.trig_cur_icon_stop, ActivityUtils.getTopActivity().getString(R.string.turn_stop), 12));
        return arrayList;
    }

    public static List<TrigItem> getDefaultCurtainItemList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new TrigItem(R.mipmap.icon_open_3, ActivityUtils.getTopActivity().getString(R.string.open_curtain), 1));
        arrayList.add(new TrigItem(R.mipmap.cgcur_icon_stop, ActivityUtils.getTopActivity().getString(R.string.preview_stop), 2));
        arrayList.add(new TrigItem(R.mipmap.icon_close_4, ActivityUtils.getTopActivity().getString(R.string.close_curtain), 4));
        return arrayList;
    }

    public static List<TrigItem> getDefaultCurtain2ItemList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new TrigItem(R.mipmap.icon_open, ActivityUtils.getTopActivity().getString(R.string.open_curtain), 1));
        arrayList.add(new TrigItem(R.mipmap.icon_close, ActivityUtils.getTopActivity().getString(R.string.close_curtain), 4));
        return arrayList;
    }

    public static List<TrigItem> getUpDownCurtainItemList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new TrigItem(R.mipmap.icon_open_2, ActivityUtils.getTopActivity().getString(R.string.open_curtain), 1));
        arrayList.add(new TrigItem(R.mipmap.cgcur_icon_stop, ActivityUtils.getTopActivity().getString(R.string.preview_stop), 2));
        arrayList.add(new TrigItem(R.mipmap.icon_close_2, ActivityUtils.getTopActivity().getString(R.string.close_curtain), 4));
        return arrayList;
    }

    public static List<TrigItem> getDefaultDreamCurtainItemList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new TrigItem(R.mipmap.icon_open_3, ActivityUtils.getTopActivity().getString(R.string.open_curtain), 1));
        arrayList.add(new TrigItem(R.mipmap.cgcur_icon_stop, ActivityUtils.getTopActivity().getString(R.string.preview_stop), 3));
        arrayList.add(new TrigItem(R.mipmap.icon_close_4, ActivityUtils.getTopActivity().getString(R.string.close_curtain), 2));
        arrayList.add(new TrigItem(R.mipmap.trig_cur_icon_left, ActivityUtils.getTopActivity().getString(R.string.turn_left), 4));
        arrayList.add(new TrigItem(R.mipmap.trig_cur_icon_stop, ActivityUtils.getTopActivity().getString(R.string.turn_stop), 12));
        arrayList.add(new TrigItem(R.mipmap.trig_cur_icon_right, ActivityUtils.getTopActivity().getString(R.string.turn_right), 8));
        return arrayList;
    }

    public static List<TrigItem> getDefaultDreamCurtainItemList2() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new TrigItem(R.mipmap.icon_open_3, ActivityUtils.getTopActivity().getString(R.string.open_curtain), 1));
        arrayList.add(new TrigItem(R.mipmap.cgcur_icon_stop, ActivityUtils.getTopActivity().getString(R.string.preview_stop), 3));
        arrayList.add(new TrigItem(R.mipmap.icon_close_4, ActivityUtils.getTopActivity().getString(R.string.close_curtain), 2));
        arrayList.add(new TrigItem(R.mipmap.icon_close_4, ActivityUtils.getTopActivity().getString(R.string.open_stop_close_stop_curtain), 9));
        arrayList.add(new TrigItem(R.mipmap.trig_cur_icon_left, ActivityUtils.getTopActivity().getString(R.string.turn_left), 4));
        arrayList.add(new TrigItem(R.mipmap.trig_cur_icon_stop, ActivityUtils.getTopActivity().getString(R.string.turn_stop), 12));
        arrayList.add(new TrigItem(R.mipmap.trig_cur_icon_right, ActivityUtils.getTopActivity().getString(R.string.turn_right), 8));
        arrayList.add(new TrigItem(R.mipmap.trig_cur_icon_right, ActivityUtils.getTopActivity().getString(R.string.turn_left_stop_right_stop), 36));
        return arrayList;
    }

    public static List<TrigItem> getDefaultCurtainOpenDirItemList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new TrigItem(R.mipmap.trig_cur_style_1, ActivityUtils.getTopActivity().getString(R.string.app_str_curtain_run_dir_left_to_right), 0));
        arrayList.add(new TrigItem(R.mipmap.trig_cur_style_2, ActivityUtils.getTopActivity().getString(R.string.app_str_curtain_run_left), 2));
        arrayList.add(new TrigItem(R.mipmap.trig_cur_style_3, ActivityUtils.getTopActivity().getString(R.string.app_str_curtain_run_up_to_down), 1));
        return arrayList;
    }

    public static class TrigItem {
        public String action;
        public int bgRes;
        public int channel;
        public String name;
        public int spanCount;

        public TrigItem(int bgRes, String name, int spanCount) {
            this.bgRes = bgRes;
            this.name = name;
            this.spanCount = spanCount;
            this.channel = -1;
        }

        public TrigItem(int bgRes, String name, int spanCount, String action) {
            this.bgRes = bgRes;
            this.name = name;
            this.spanCount = spanCount;
            this.action = action;
        }
    }
}