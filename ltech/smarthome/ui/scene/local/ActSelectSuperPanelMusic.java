package com.ltech.smarthome.ui.scene.local;

import android.content.Intent;
import android.view.View;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatImageView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActSelectSuperPanelMusicBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.SuperPanelBleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.api.ApiConstants;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NameRepository;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.SwitchButton;
import com.smart.product_agreement.param.SuperPanelCmdParam;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class ActSelectSuperPanelMusic extends BaseNormalActivity<ActSelectSuperPanelMusicBinding> {
    private Device device;
    private String songId;
    private String songName;
    private int selectPosition = -1;
    private List<Integer> songList = new ArrayList();
    private int onlineMusicSelectPosition = 0;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_super_panel_music;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        setTitle(getString(R.string.choose_action));
        Device deviceById = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
        this.device = deviceById;
        SuperPanelBleParam superPanelBleParam = (SuperPanelBleParam) deviceById.getParam(SuperPanelBleParam.class);
        boolean isDuringPayment = (superPanelBleParam.getMusicDeadline() == null || superPanelBleParam.getMusicDeadline().isEmpty()) ? false : isDuringPayment(superPanelBleParam.getMusicDeadline());
        if (this.device != null && isDuringPayment && ApiConstants.isChinaNode() && ProductRepository.isDcaSuperPanel(this.device.getProductId()) && !ProductId.ID_ANDROID_SUPER_PANEL_G4MAX.equals(this.device.getProductId())) {
            ((ActSelectSuperPanelMusicBinding) this.mViewBinding).layoutCollectMusic.setVisibility(0);
            ((ActSelectSuperPanelMusicBinding) this.mViewBinding).layoutOnlineMusic.setVisibility(0);
        }
        ((ActSelectSuperPanelMusicBinding) this.mViewBinding).sbDiyVolume.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectSuperPanelMusic$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                ActSelectSuperPanelMusic.this.lambda$initView$0(switchButton, z);
            }
        });
        ((ActSelectSuperPanelMusicBinding) this.mViewBinding).sbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectSuperPanelMusic.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ((ActSelectSuperPanelMusicBinding) ActSelectSuperPanelMusic.this.mViewBinding).tvVolume.setText(String.format(Locale.US, "%d%s", Integer.valueOf(seekBar.getProgress()), "%"));
            }
        });
        ((ActSelectSuperPanelMusicBinding) this.mViewBinding).setDiyVolume(false);
        ((ActSelectSuperPanelMusicBinding) this.mViewBinding).sbLoopMode.setChecked(true);
        ((ActSelectSuperPanelMusicBinding) this.mViewBinding).tvVolume.setText(String.format(Locale.US, "%d%s", Integer.valueOf(((ActSelectSuperPanelMusicBinding) this.mViewBinding).sbVolume.getProgress()), "%"));
        ((ActSelectSuperPanelMusicBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectSuperPanelMusic$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActSelectSuperPanelMusic.this.lambda$initView$1((View) obj);
            }
        }));
        String stringExtra = getIntent().getStringExtra(Constants.SCENE_INSTRUCT);
        String stringExtra2 = getIntent().getStringExtra(Constants.SCENE_OPTION_VALUE);
        if (stringExtra == null || stringExtra.isEmpty()) {
            return;
        }
        setCurState(stringExtra2, stringExtra);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(SwitchButton switchButton, boolean z) {
        ((ActSelectSuperPanelMusicBinding) this.mViewBinding).setDiyVolume(Boolean.valueOf(z));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view) {
        switch (view.getId()) {
            case R.id.layout_collect_music /* 2131297399 */:
                this.selectPosition = 3;
                NavUtils.destination(ActSelectSuperPanelCollectSong.class).withString("device_sn", this.device.getDevicesn()).withDefaultRequestCode().navigation(this);
                break;
            case R.id.layout_local_music /* 2131297525 */:
                this.selectPosition = 2;
                NavUtils.destination(ActSelectSuperPanelLocalSong.class).withBoolean(Constants.IS_LOCAL_SCENE, true).withIntArray(Constants.SCENE_LOCAL_SONG_LIST, toIntArray(this.songList)).withBoolean(Constants.SUPER_PANEL_NEW_MUSIC, SceneHelper.instance().isSupportNewMusic(this.device)).withString(Constants.MAC_ADDRESS, this.device.getWifiMac()).withDefaultRequestCode().navigation(this);
                break;
            case R.id.layout_online_music /* 2131297560 */:
                this.selectPosition = 4;
                NavUtils.destination(ActSelectSuperPanelOnlineZone.class).withBoolean(Constants.IS_LOCAL_SCENE, true).withInt(Constants.SCENE_ONLINE_MUSIC_POSITION, this.onlineMusicSelectPosition).withDefaultRequestCode().navigation(this);
                break;
            case R.id.layout_play_pause /* 2131297576 */:
                this.selectPosition = 5;
                break;
            case R.id.layout_play_start /* 2131297577 */:
                this.selectPosition = 1;
                break;
        }
        int i = this.selectPosition;
        if (i == 2 || i == 4 || i == 3) {
            return;
        }
        changeView();
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00bc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void setCurState(java.lang.String r13, java.lang.String r14) {
        /*
            Method dump skipped, instructions count: 630
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.scene.local.ActSelectSuperPanelMusic.setCurState(java.lang.String, java.lang.String):void");
    }

    private int[] toIntArray(List<Integer> list) {
        int size = list.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = list.get(i).intValue();
        }
        return iArr;
    }

    private void changeView() {
        AppCompatImageView appCompatImageView = ((ActSelectSuperPanelMusicBinding) this.mViewBinding).ivPlayStart;
        int i = this.selectPosition;
        int i2 = R.color.transparent;
        appCompatImageView.setImageResource(i == 1 ? R.mipmap.ic_tick_sel : R.color.transparent);
        AppCompatImageView appCompatImageView2 = ((ActSelectSuperPanelMusicBinding) this.mViewBinding).ivLocalMusic;
        int i3 = this.selectPosition;
        int i4 = R.mipmap.icon_more;
        appCompatImageView2.setImageResource(i3 == 2 ? R.mipmap.ic_tick_sel : R.mipmap.icon_more);
        ((ActSelectSuperPanelMusicBinding) this.mViewBinding).ivCollectMusic.setImageResource(this.selectPosition == 3 ? R.mipmap.ic_tick_sel : R.mipmap.icon_more);
        AppCompatImageView appCompatImageView3 = ((ActSelectSuperPanelMusicBinding) this.mViewBinding).ivOnlineMusic;
        if (this.selectPosition == 4) {
            i4 = R.mipmap.ic_tick_sel;
        }
        appCompatImageView3.setImageResource(i4);
        AppCompatImageView appCompatImageView4 = ((ActSelectSuperPanelMusicBinding) this.mViewBinding).ivPlayPause;
        if (this.selectPosition == 5) {
            i2 = R.mipmap.ic_tick_sel;
        }
        appCompatImageView4.setImageResource(i2);
        ((ActSelectSuperPanelMusicBinding) this.mViewBinding).layoutVolume.setVisibility(this.selectPosition != 5 ? 0 : 8);
        ((ActSelectSuperPanelMusicBinding) this.mViewBinding).layoutBar.setVisibility((this.selectPosition == 5 || !((ActSelectSuperPanelMusicBinding) this.mViewBinding).getDiyVolume().booleanValue()) ? 8 : 0);
        ((ActSelectSuperPanelMusicBinding) this.mViewBinding).layoutLoop.setVisibility(this.selectPosition == 5 ? 8 : 0);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (this.selectPosition == -1) {
            SmartToast.showShort(R.string.please_choose);
            return;
        }
        SuperPanelCmdParam superPanelCmdParam = new SuperPanelCmdParam();
        int i = this.selectPosition;
        if (i == 1) {
            superPanelCmdParam.setCmdType(3);
            superPanelCmdParam.setStart(true);
            superPanelCmdParam.addExtParam(SceneHelper.OPTION_VALUE, "0");
        } else if (i == 2) {
            if (SceneHelper.instance().isSupportNewMusic(this.device)) {
                superPanelCmdParam.setCmdType(4);
            } else {
                superPanelCmdParam.setCmdType(2);
            }
            superPanelCmdParam.setPlayList(this.songList);
            StringBuilder sb = new StringBuilder("2");
            for (Integer num : this.songList) {
                sb.append(ProductId.SPLIT);
                sb.append(num);
            }
            superPanelCmdParam.addExtParam(SceneHelper.OPTION_VALUE, sb.toString());
            superPanelCmdParam.addExtParam(SceneHelper.SONG_NAMES, ((ActSelectSuperPanelMusicBinding) this.mViewBinding).tvLocalMusic.getText().toString());
        } else if (i == 4) {
            superPanelCmdParam.setCmdType(5);
            superPanelCmdParam.setZoneId(this.songList.get(0).intValue() + 3);
            superPanelCmdParam.addExtParam(SceneHelper.OPTION_VALUE, "3_" + superPanelCmdParam.getZoneId());
        } else if (i == 3) {
            superPanelCmdParam.setCmdType(6);
            superPanelCmdParam.setSongId(this.songId);
            superPanelCmdParam.addExtParam(SceneHelper.OPTION_VALUE, this.songName);
        } else {
            superPanelCmdParam.setCmdType(3);
            superPanelCmdParam.setStart(false);
            superPanelCmdParam.addExtParam(SceneHelper.OPTION_VALUE, "1");
        }
        superPanelCmdParam.setMode(((ActSelectSuperPanelMusicBinding) this.mViewBinding).sbLoopMode.isChecked() ? 1 : 0);
        superPanelCmdParam.setSound(((ActSelectSuperPanelMusicBinding) this.mViewBinding).getDiyVolume().booleanValue() ? ((ActSelectSuperPanelMusicBinding) this.mViewBinding).sbVolume.getProgress() : 255);
        superPanelCmdParam.addExtParam(SceneHelper.OPTION, "3");
        SceneHelper.instance().cmdParam = superPanelCmdParam;
        setResult(3001);
        finishActivity();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3019 && data != null) {
            ArrayList<Integer> integerArrayListExtra = data.getIntegerArrayListExtra(Constants.SELECT_SONG_ID);
            this.songList = integerArrayListExtra;
            if (integerArrayListExtra.size() > 0) {
                ((ActSelectSuperPanelMusicBinding) this.mViewBinding).tvLocalMusic.setText(data.getStringExtra(Constants.SELECT_SONG_NAME));
                changeView();
                return;
            }
            return;
        }
        if (resultCode == 3020 && data != null) {
            ArrayList<Integer> integerArrayListExtra2 = data.getIntegerArrayListExtra(Constants.SELECT_SONG_ID);
            this.songList = integerArrayListExtra2;
            if (integerArrayListExtra2.size() > 0) {
                String[] onlineZoneName = NameRepository.getOnlineZoneName(this);
                this.onlineMusicSelectPosition = this.songList.get(0).intValue();
                ((ActSelectSuperPanelMusicBinding) this.mViewBinding).tvOnlineMusic.setText(onlineZoneName[this.songList.get(0).intValue()]);
                changeView();
                return;
            }
            return;
        }
        if (resultCode != 3022 || data == null) {
            return;
        }
        this.songName = data.getStringExtra(Constants.SELECT_SONG_NAME);
        this.songId = data.getStringExtra(Constants.SELECT_SONG_ID);
        if (this.songName.isEmpty()) {
            return;
        }
        ((ActSelectSuperPanelMusicBinding) this.mViewBinding).tvCollect.setText(this.songName);
        changeView();
    }

    private boolean isDuringPayment(String time) {
        try {
            return new SimpleDateFormat("yyyyMMddhhmmss").parse(time).getTime() - System.currentTimeMillis() > 0;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}