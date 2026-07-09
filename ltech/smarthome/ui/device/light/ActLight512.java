package com.ltech.smarthome.ui.device.light;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActChannel512Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.Channel512Param;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.model.scene_param.CharSwitch;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.light.ActLight512VM;
import com.ltech.smarthome.ui.device.microwave_sensor.WaveSensorHelper;
import com.ltech.smarthome.ui.device.setting.ActDmx512Setting;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.ScrollRecyclerView;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.param.LightCmdParam;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActLight512 extends BaseControlActivity<ActChannel512Binding, ActLight512VM> implements ISelectAction {
    private BaseQuickAdapter<ActLight512VM.Channel, MyViewHolder> mAdapter;
    private final Handler mHandler = new Handler(new Handler.Callback() { // from class: com.ltech.smarthome.ui.device.light.ActLight512.3
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message msg) {
            int i = msg.what;
            if (i != 1) {
                if (i != 2) {
                    return false;
                }
                ((ActChannel512Binding) ActLight512.this.mViewBinding).rv.setScrollEnabled(true);
                return false;
            }
            Bundle data = msg.getData();
            if (data == null) {
                return false;
            }
            ActLight512.this.sendChannel(data.getInt("p"), data.getInt("v"), true);
            return false;
        }
    });

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_channel_512;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void lambda$edit$1(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        initRv();
        ((ActLight512VM) this.mViewModel).isEdit = getIntent().getBooleanExtra(Constants.IS_EDIT, false);
        ((ActLight512VM) this.mViewModel).isWaveSensorAction = getIntent().getBooleanExtra(Constants.WAVE_SENSOR_ACTION, false);
        if (((ActLight512VM) this.mViewModel).isWaveSensorAction || ((ActLight512VM) this.mViewModel).isEdit) {
            if (!TextUtils.isEmpty(SceneHelper.instance().selectInstruct)) {
                ((ActLight512VM) this.mViewModel).instruct = getRealCmdData(SceneHelper.instance().selectInstruct).substring(4);
            }
            setEditString(getString(R.string.save));
        } else {
            setEditImage(R.mipmap.ic_setting);
        }
        ((ActLight512VM) this.mViewModel).isLocal = getIntent().getBooleanExtra(Constants.IS_LOCAL_SCENE, false);
        ((ActLight512VM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        Device deviceById = Injection.repo().device().getDeviceById(((ActLight512VM) this.mViewModel).controlId);
        if (deviceById != null) {
            ((ActLight512VM) this.mViewModel).controlObject.setValue(deviceById);
        }
    }

    private void initRv() {
        ((ActChannel512Binding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(this));
        ScrollRecyclerView scrollRecyclerView = ((ActChannel512Binding) this.mViewBinding).rv;
        BaseQuickAdapter<ActLight512VM.Channel, MyViewHolder> baseQuickAdapter = new BaseQuickAdapter<ActLight512VM.Channel, MyViewHolder>(R.layout.item_channel_512) { // from class: com.ltech.smarthome.ui.device.light.ActLight512.1
            private boolean canEdit = true;
            private boolean sbMove = false;

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(final MyViewHolder baseViewHolder, final ActLight512VM.Channel channel) {
                String str;
                final LightBrtBar lightBrtBar;
                final int layoutPosition = baseViewHolder.getLayoutPosition();
                final EditText editText = (EditText) baseViewHolder.getView(R.id.tv2);
                if (layoutPosition == 0) {
                    LightBrtBar lightBrtBar2 = (LightBrtBar) baseViewHolder.getView(R.id.sb_brt_all);
                    baseViewHolder.setGone(R.id.sb_brt, false);
                    baseViewHolder.setVisible(R.id.tv_sub, false);
                    baseViewHolder.setGone(R.id.iv, false);
                    baseViewHolder.setText(R.id.tv, channel.getName());
                    baseViewHolder.setText(R.id.tv2, channel.getValue() + "");
                    lightBrtBar2.setProgress(channel.getValue());
                    lightBrtBar = lightBrtBar2;
                } else {
                    LightBrtBar lightBrtBar3 = (LightBrtBar) baseViewHolder.getView(R.id.sb_brt);
                    baseViewHolder.setGone(R.id.sb_brt_all, false);
                    baseViewHolder.setVisible(R.id.tv_sub, true);
                    baseViewHolder.setVisible(R.id.iv, true);
                    if (channel.getAddress() >= 100) {
                        str = "";
                    } else {
                        str = "00".substring(0, 3 - String.valueOf(channel.getAddress()).length());
                    }
                    baseViewHolder.setText(R.id.tv_sub, str + channel.getAddress());
                    baseViewHolder.setText(R.id.tv, channel.getName());
                    baseViewHolder.setText(R.id.tv2, channel.getValue() + "");
                    lightBrtBar3.setProgress(channel.getValue());
                    baseViewHolder.addOnClickListener(R.id.tv, R.id.iv);
                    lightBrtBar = lightBrtBar3;
                }
                lightBrtBar.setVisibility(0);
                lightBrtBar.setIncludeZero(true);
                baseViewHolder.getView(R.id.iv2).setOnClickListener(new View.OnClickListener(this) { // from class: com.ltech.smarthome.ui.device.light.ActLight512.1.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        if (editText.hasFocus()) {
                            return;
                        }
                        editText.requestFocus();
                        KeyboardUtils.showSoftInput(editText);
                    }
                });
                baseViewHolder.textWatcher = new MyTextWatcher(baseViewHolder.getLayoutPosition()) { // from class: com.ltech.smarthome.ui.device.light.ActLight512.1.2
                    @Override // android.text.TextWatcher
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (AnonymousClass1.this.canEdit && editText.hasFocus()) {
                            editText.setSelection(s.length());
                            ((ActChannel512Binding) ActLight512.this.mViewBinding).rv.setScrollEnabled(false);
                            if (TextUtils.isEmpty(s.toString())) {
                                editText.setText("0");
                                return;
                            }
                            if (s.toString().startsWith("0") && s.length() > 1) {
                                editText.setText(s.toString().replaceFirst("0", ""));
                            } else if (Integer.parseInt(s.toString()) > 255) {
                                editText.setText("255");
                            }
                        }
                    }

                    @Override // android.text.TextWatcher
                    public void afterTextChanged(Editable s) {
                        if (AnonymousClass1.this.canEdit && editText.hasFocus()) {
                            if (!TextUtils.isEmpty(s.toString())) {
                                int parseInt = Integer.parseInt(s.toString());
                                lightBrtBar.setProgress(parseInt);
                                channel.setValue(parseInt);
                                if (layoutPosition == 0) {
                                    ((ActLight512VM) ActLight512.this.mViewModel).all = parseInt;
                                    ActLight512.this.changeOtherChannel(channel);
                                }
                                Message message = new Message();
                                message.what = 1;
                                Bundle bundle = new Bundle();
                                bundle.putInt("v", parseInt);
                                bundle.putInt("p", layoutPosition);
                                message.setData(bundle);
                                ActLight512.this.mHandler.removeMessages(1);
                                ActLight512.this.mHandler.sendMessageDelayed(message, 1000L);
                            }
                            if (AnonymousClass1.this.sbMove) {
                                return;
                            }
                            ((ActChannel512Binding) ActLight512.this.mViewBinding).rv.setScrollEnabled(true);
                        }
                    }
                };
                editText.addTextChangedListener(baseViewHolder.textWatcher);
                editText.setOnEditorActionListener(new TextView.OnEditorActionListener(this) { // from class: com.ltech.smarthome.ui.device.light.ActLight512.1.3
                    @Override // android.widget.TextView.OnEditorActionListener
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        editText.clearFocus();
                        return false;
                    }
                });
                lightBrtBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.device.light.ActLight512.1.4
                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            ActLight512.this.mHandler.removeMessages(2);
                            AnonymousClass1.this.sbMove = true;
                            ((ActChannel512Binding) ActLight512.this.mViewBinding).rv.setScrollEnabled(false);
                            channel.setValue(progress);
                            baseViewHolder.setText(R.id.tv2, channel.getValue() + "");
                            if (layoutPosition == 0) {
                                ((ActLight512VM) ActLight512.this.mViewModel).all = progress;
                                ActLight512.this.changeOtherChannel(channel);
                            }
                            ActLight512.this.sendChannel(layoutPosition, progress, false);
                        }
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        AnonymousClass1.this.canEdit = false;
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        ActLight512.this.mHandler.removeMessages(2);
                        ActLight512.this.mHandler.sendEmptyMessageDelayed(2, 500L);
                        AnonymousClass1.this.sbMove = false;
                        if (layoutPosition == 0) {
                            ActLight512.this.sendChannel(layoutPosition, seekBar.getProgress(), true);
                        } else {
                            ActLight512.this.sendChannel(layoutPosition, seekBar.getProgress(), true);
                        }
                        AnonymousClass1.this.canEdit = true;
                    }
                });
            }
        };
        this.mAdapter = baseQuickAdapter;
        scrollRecyclerView.setAdapter(baseQuickAdapter);
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActLight512.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter2, View view, final int i) {
                EditDialog.asDefault().setContent(((ActLight512VM) ActLight512.this.mViewModel).getChannelNames().get(i - 1)).setTitle(ActLight512.this.getString(R.string.edit_name)).setConfirmAction(new IAction<String>() { // from class: com.ltech.smarthome.ui.device.light.ActLight512.2.1
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(String s) {
                        ((ActLight512VM) ActLight512.this.mViewModel).getChannelNames().set(i - 1, s);
                        ActLight512VM.Channel channel = (ActLight512VM.Channel) ActLight512.this.mAdapter.getData().get(i);
                        channel.setName(s);
                        ActLight512.this.mAdapter.setData(i, channel);
                        ((ActLight512VM) ActLight512.this.mViewModel).updateName();
                    }
                }).showDialog(ActLight512.this.getSupportFragmentManager());
            }
        });
    }

    public static class MyViewHolder extends BaseViewHolder {
        EditText editText;
        MyTextWatcher textWatcher;

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static abstract class MyTextWatcher implements TextWatcher {
        private final int position;

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public MyTextWatcher(int position) {
            this.position = position;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeOtherChannel(final ActLight512VM.Channel c2) {
        ((ActChannel512Binding) this.mViewBinding).rv.post(new Runnable() { // from class: com.ltech.smarthome.ui.device.light.ActLight512.4
            @Override // java.lang.Runnable
            public void run() {
                int i = 0;
                for (ActLight512VM.Channel channel : ActLight512.this.mAdapter.getData()) {
                    if (i != 0) {
                        channel.setValue(c2.getValue());
                        ActLight512.this.mAdapter.setData(i, channel);
                    }
                    i++;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendChannel(int pos, int progress, boolean b2) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (ActLight512VM.Channel channel : this.mAdapter.getData()) {
            if (i > 0) {
                arrayList.add(Integer.valueOf(channel.getValue()));
            }
            i++;
        }
        ((ActLight512VM) this.mViewModel).sendChannel(pos, progress, b2);
        if ((((ActLight512VM) this.mViewModel).isWaveSensorAction || ((ActLight512VM) this.mViewModel).isEdit) && b2) {
            ((ActLight512VM) this.mViewModel).selectChannelState(arrayList);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActLight512VM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActLight512$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActLight512.this.lambda$startObserve$0(obj);
            }
        });
        ((ActLight512VM) this.mViewModel).data.observe(this, new Observer<List<ActLight512VM.Channel>>() { // from class: com.ltech.smarthome.ui.device.light.ActLight512.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<ActLight512VM.Channel> channels) {
                ActLight512.this.mAdapter.setNewData(channels);
            }
        });
        MessageManager.getInstance().setDmxChannelCallBack(new MessageManager.DmxChannelCallBack() { // from class: com.ltech.smarthome.ui.device.light.ActLight512.6
            @Override // com.smart.message.MessageManager.DmxChannelCallBack
            public void onDataReceive(ResponseMsg msg) {
                int i;
                int i2;
                if (msg == null || msg.getResData() == null) {
                    return;
                }
                ((ActLight512VM) ActLight512.this.mViewModel).setFirstAddress(Integer.parseInt(msg.getResData().substring(14, 18), 16));
                ((ActLight512VM) ActLight512.this.mViewModel).setChannelNum(Integer.parseInt(msg.getResData().substring(18, 20), 16));
                if (msg.getResData().length() >= 32) {
                    ArrayList arrayList = new ArrayList();
                    String substring = msg.getResData().substring(20);
                    for (int i3 = 0; i3 < ((ActLight512VM) ActLight512.this.mViewModel).getChannelNum(); i3++) {
                        if ((((ActLight512VM) ActLight512.this.mViewModel).isWaveSensorAction || ((ActLight512VM) ActLight512.this.mViewModel).isEdit) && !StringUtils.isEmpty(((ActLight512VM) ActLight512.this.mViewModel).instruct) && ((ActLight512VM) ActLight512.this.mViewModel).instruct.length() >= (i2 = (i = i3 * 2) + 2)) {
                            arrayList.add(Integer.valueOf(Integer.parseInt(((ActLight512VM) ActLight512.this.mViewModel).instruct.substring(i, i2), 16)));
                        } else {
                            int i4 = i3 * 2;
                            arrayList.add(Integer.valueOf(Integer.parseInt(substring.substring(i4, i4 + 2), 16)));
                        }
                    }
                    ((ActLight512VM) ActLight512.this.mViewModel).setChannelData(arrayList);
                    if (((ActLight512VM) ActLight512.this.mViewModel).isWaveSensorAction || ((ActLight512VM) ActLight512.this.mViewModel).isEdit) {
                        ((ActLight512VM) ActLight512.this.mViewModel).selectChannelState(arrayList);
                    }
                }
                ActLight512.this.setFirstAddress();
                ((ActLight512VM) ActLight512.this.mViewModel).showData();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Object obj) {
        Device device = (Device) obj;
        setTitle(device.getDeviceName());
        Channel512Param channel512Param = (Channel512Param) device.getExtParam(Channel512Param.class);
        if (channel512Param != null) {
            ((ActLight512VM) this.mViewModel).setChannelNum(channel512Param.getChannelCount());
            ((ActLight512VM) this.mViewModel).setFirstAddress(channel512Param.getChannelHead());
            ((ActLight512VM) this.mViewModel).setChannelNames(channel512Param.getChannelNames());
        } else {
            ((ActLight512VM) this.mViewModel).setChannelNum(6);
            ((ActLight512VM) this.mViewModel).setFirstAddress(1);
            ((ActLight512VM) this.mViewModel).setChannelNames(null);
        }
        setFirstAddress();
        ((ActLight512VM) this.mViewModel).showData();
        if (((ActLight512VM) this.mViewModel).isWaveSensorAction || ((ActLight512VM) this.mViewModel).isEdit) {
            ((ActLight512VM) this.mViewModel).selectChannelState(((ActLight512VM) this.mViewModel).getChannelData());
        }
        ((ActLight512VM) this.mViewModel).queryChannel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFirstAddress() {
        String str;
        if (((ActLight512VM) this.mViewModel).getFirstAddress() < 100) {
            str = "00".substring(0, 3 - String.valueOf(((ActLight512VM) this.mViewModel).getFirstAddress()).length());
        } else {
            str = "";
        }
        ((ActChannel512Binding) this.mViewBinding).tvSub.setText(getString(R.string.app_str_first_address) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + (str + ((ActLight512VM) this.mViewModel).getFirstAddress()) + " " + getString(R.string.app_str_channel_num) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + ((ActLight512VM) this.mViewModel).getChannelNum());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        Object value = ((ActLight512VM) this.mViewModel).controlObject.getValue();
        if (value instanceof Device) {
            Device device = (Device) value;
            if (((ActLight512VM) this.mViewModel).isEdit) {
                if (((ActLight512VM) this.mViewModel).isLocal) {
                    SceneHelper.instance().maskType = MaskType.LOCAL;
                    lambda$edit$2(this);
                    return;
                }
                SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLight512$$ExternalSyntheticLambda0
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActLight512.this.lambda$edit$1((Boolean) obj);
                    }
                });
                return;
            }
            if (((ActLight512VM) this.mViewModel).isWaveSensorAction) {
                WaveSensorHelper.instance().setSensorRelateBleParam();
                if (WaveSensorHelper.instance().isChangeAll) {
                    WaveSensorHelper.instance().setDataType = 2;
                    WaveSensorHelper.instance().setSensorRelateBleParam();
                    LightCmdParam lightCmdParam = new LightCmdParam();
                    lightCmdParam.setZoneNum(1);
                    lightCmdParam.setCmdType(1);
                    lightCmdParam.setOn(false);
                    lightCmdParam.addExtParam(SceneHelper.OPTION, "4");
                    WaveSensorHelper.instance().cmdParam = lightCmdParam;
                    WaveSensorHelper.instance().setDataType = 3;
                    WaveSensorHelper.instance().setSensorRelateBleParam();
                    return;
                }
                return;
            }
            NavUtils.destination(ActDmx512Setting.class).withLong("device_id", device.getDeviceId()).withLong(Constants.PLACE_ID, device.getPlaceId()).withInt(Constants.ZONE_NUM, ((ActLight512VM) this.mViewModel).getChannelNum()).withInt(Constants.ADDRESS, ((ActLight512VM) this.mViewModel).getFirstAddress()).withDefaultRequestCode().navigation(this);
        }
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseControlActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            ((ActLight512VM) this.mViewModel).queryChannel();
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 0) {
            View currentFocus = getCurrentFocus();
            if (currentFocus instanceof EditText) {
                Rect rect = new Rect();
                currentFocus.getGlobalVisibleRect(rect);
                if (!rect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    currentFocus.clearFocus();
                    ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public static String getRealCmdData(String cmd) {
        CharSwitch charSwitch;
        if (!cmd.startsWith("{") || !cmd.endsWith("}") || (charSwitch = (CharSwitch) GsonUtils.fromJson(cmd, CharSwitch.class)) == null) {
            return cmd;
        }
        String substring = charSwitch.getCharSwitch().substring(22);
        return substring.substring(0, 2) + substring.substring(6, substring.length() - 2);
    }
}