package com.ltech.smarthome.ui.camera.play;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.ui.control.ActHomeVM;
import com.videogo.openapi.EZPlayer;
import com.videogo.openapi.bean.EZCameraInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActCameraPlayVM extends BaseViewModel {
    public long controlId;
    public EZPlayer ezPlayer;
    public EZPlayer ezPlayer2;
    public List<ActHomeVM.TabContent> tabContentList;
    public MutableLiveData<Device> controlDevice = new MutableLiveData<>();
    public List<BaseNormalFragment> fragmentList = new ArrayList();

    public void initTabList(EZCameraInfo cameraInfo, boolean supportPtz, boolean supportTalk) {
        List<ActHomeVM.TabContent> list = this.tabContentList;
        if (list == null) {
            this.tabContentList = new ArrayList();
        } else {
            list.clear();
        }
        this.fragmentList.clear();
        if (supportPtz) {
            this.fragmentList.add(FtCameraPtz.newInstance(cameraInfo));
        }
        this.fragmentList.add(FtCameraRecord.newInstance(cameraInfo));
        if (supportTalk) {
            this.fragmentList.add(new FtCameraTalk());
        }
        if (supportPtz) {
            this.tabContentList.add(new ActHomeVM.TabContent(R.string.ptz, R.mipmap.icon_camera_tab_yuntai_sel, R.mipmap.icon_camera_tab_yuntai_default, this.fragmentList.get(0)));
            this.tabContentList.add(new ActHomeVM.TabContent(R.string.playback, R.mipmap.icon_camera_tab_playback_sel, R.mipmap.icon_camera_tab_playback_default, this.fragmentList.get(1)));
            if (supportTalk) {
                this.tabContentList.add(new ActHomeVM.TabContent(R.string.talk_intercom, R.mipmap.icon_camera_tab_intercom_sel, R.mipmap.icon_camera_tab_intercom_default, this.fragmentList.get(2)));
            }
        } else {
            this.tabContentList.add(new ActHomeVM.TabContent(R.string.playback, R.mipmap.icon_camera_tab_playback_sel, R.mipmap.icon_camera_tab_playback_default, this.fragmentList.get(0)));
            if (supportTalk) {
                this.tabContentList.add(new ActHomeVM.TabContent(R.string.talk_intercom, R.mipmap.icon_camera_tab_intercom_sel, R.mipmap.icon_camera_tab_intercom_default, this.fragmentList.get(1)));
            }
        }
        this.tabContentList.add(new ActHomeVM.TabContent(R.string.capture, R.mipmap.icon_camera_tab_screenshot_default, R.mipmap.icon_camera_tab_screenshot_default, null));
        this.tabContentList.add(new ActHomeVM.TabContent(R.string.record, R.mipmap.icon_camera_tab_video_sel, R.mipmap.icon_camera_tab_video_default, null));
    }

    public int getPlaybackTabIndex() {
        for (int i = 0; i < this.fragmentList.size(); i++) {
            if (this.fragmentList.get(i) instanceof FtCameraRecord) {
                return i;
            }
        }
        return -1;
    }

    public int getTalkTabIndex() {
        for (int i = 0; i < this.fragmentList.size(); i++) {
            if (this.fragmentList.get(i) instanceof FtCameraTalk) {
                return i;
            }
        }
        return -1;
    }

    public void changeTab(TabLayout.Tab tab, int position, boolean select) {
        ActHomeVM.TabContent tabContent = this.tabContentList.get(position);
        View customView = tab.getCustomView();
        if (customView == null) {
            customView = LayoutInflater.from(ActivityUtils.getTopActivity()).inflate(R.layout.layout_tab_view, (ViewGroup) null);
            tab.setCustomView(customView);
        }
        AppCompatTextView appCompatTextView = (AppCompatTextView) customView.findViewById(R.id.tv_tab);
        AppCompatImageView appCompatImageView = (AppCompatImageView) customView.findViewById(R.id.iv_tab);
        appCompatTextView.setText(tabContent.getTitleRes());
        if (select) {
            appCompatImageView.setImageResource(tabContent.getSelectIconRes());
            appCompatTextView.setTextColor(ContextCompat.getColor(ActivityUtils.getTopActivity(), R.color.color_blue));
        } else {
            appCompatImageView.setImageResource(tabContent.getUnSelectIconRes());
            appCompatTextView.setTextColor(ContextCompat.getColor(ActivityUtils.getTopActivity(), R.color.color_text_gray));
        }
    }
}