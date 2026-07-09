package com.ltech.smarthome.ui.intercom;

import android.content.res.Resources;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActOpenDoorLogDetailBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.response.intercom.GetOpenDoorLogResponse;
import com.ltech.smarthome.utils.Constants;

/* loaded from: classes4.dex */
public class ActOpenDoorLogDetail extends VMActivity<ActOpenDoorLogDetailBinding, FtLogVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_open_door_log_detail;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.open_door_detail));
        setBackImage(R.mipmap.icon_back);
        String stringExtra = getIntent().getStringExtra(Constants.INTERCOM_TIME);
        if (stringExtra != null) {
            setViewData(Injection.intercom().getOpenDoorList(stringExtra));
        }
    }

    private void setViewData(GetOpenDoorLogResponse.OpenDoorBean data) {
        Resources resources;
        int i;
        String string;
        Glide.with((FragmentActivity) this).load(data.getPicUrl()).apply((BaseRequestOptions<?>) RequestOptions.bitmapTransform(new RoundedCorners(10))).error(R.mipmap.pic_load_error).into(((ActOpenDoorLogDetailBinding) this.mViewBinding).ivSipPic);
        ((ActOpenDoorLogDetailBinding) this.mViewBinding).tvAccessControl.setText(data.getLocation());
        ((ActOpenDoorLogDetailBinding) this.mViewBinding).tvStatus.setText(getString(data.getResponse().equalsIgnoreCase("0") ? R.string.open_success : R.string.not_open));
        AppCompatTextView appCompatTextView = ((ActOpenDoorLogDetailBinding) this.mViewBinding).tvStatus;
        if (data.getResponse().equalsIgnoreCase("0")) {
            resources = getResources();
            i = R.color.sensor_blue;
        } else {
            resources = getResources();
            i = R.color.color_text_red;
        }
        appCompatTextView.setTextColor(resources.getColor(i));
        ((ActOpenDoorLogDetailBinding) this.mViewBinding).tvOpenDoorPerson.setText(data.getInitiator());
        ((ActOpenDoorLogDetailBinding) this.mViewBinding).tvOpenDoorTime.setText(data.getCaptureTime());
        int parseInt = Integer.parseInt(data.getCaptureType());
        if (parseInt != 101) {
            if (parseInt != 103) {
                switch (parseInt) {
                    case 0:
                        string = getString(R.string.open_way_remote);
                        break;
                    case 1:
                        string = getString(R.string.open_way_temp);
                        break;
                    case 2:
                        string = getString(R.string.open_way_secret);
                        break;
                    case 3:
                        string = getString(R.string.open_way_rf);
                        break;
                    case 4:
                        string = getString(R.string.open_way_face);
                        break;
                    case 5:
                    case 6:
                        break;
                    default:
                        string = "";
                        break;
                }
            }
            string = getString(R.string.open_way_remote);
        } else {
            string = getString(R.string.open_way_ble);
        }
        ((ActOpenDoorLogDetailBinding) this.mViewBinding).tvOpenDoorWay.setText(string);
    }
}