package com.ltech.smarthome.view.dialog;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.BaseAdapter;
import com.ltech.smarthome.adapter.MusicTimeAdapter;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.databinding.DialogMusicTimeBinding;
import com.ltech.smarthome.ui.device.super_panel.music.countDownTimer.CountDownTimerManager;
import com.ltech.smarthome.view.dialog.MusicScheduledTimeDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class MusicScheduledTimeDialog extends SmartDialog<DialogMusicTimeBinding> {
    private OnTimeClickCallback onTimeClickCallback;
    private CountDownTimerManager scheduledCountDownTimerManager;
    private int scheduledSetTime;
    private int scheduledTimeLeft;
    private List<String> timeList = new ArrayList();
    private int[] array = {10, 20, 30, 60};

    public interface OnTimeClickCallback {
        void onTimeClick(int time);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_music_time;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.MusicScheduledTimeDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogMusicTimeBinding, MusicScheduledTimeDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogMusicTimeBinding viewBinding, final MusicScheduledTimeDialog dialog) {
            dialog.initRv(viewBinding, dialog);
            viewBinding.tvCancel.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.view.dialog.MusicScheduledTimeDialog$1$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MusicScheduledTimeDialog.AnonymousClass1.lambda$convertView$0(MusicScheduledTimeDialog.this, view);
                }
            });
        }

        static /* synthetic */ void lambda$convertView$0(MusicScheduledTimeDialog musicScheduledTimeDialog, View view) {
            if (musicScheduledTimeDialog.onTimeClickCallback != null) {
                musicScheduledTimeDialog.onTimeClickCallback.onTimeClick(0);
                musicScheduledTimeDialog.dismissDialog();
            }
        }
    }

    public static MusicScheduledTimeDialog asDefault() {
        return (MusicScheduledTimeDialog) new MusicScheduledTimeDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setGravity(80);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRv(final DialogMusicTimeBinding viewBinding, final MusicScheduledTimeDialog dialog) {
        MusicTimeAdapter musicTimeAdapter = new MusicTimeAdapter(getContext());
        musicTimeAdapter.setData(this.timeList);
        viewBinding.rvTime.setLayoutManager(new LinearLayoutManager(getContext()));
        musicTimeAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.MusicScheduledTimeDialog$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.adapter.BaseAdapter.OnItemClickListener
            public final void onItemClick(RecyclerView recyclerView, View view, int i) {
                MusicScheduledTimeDialog.this.lambda$initRv$0(dialog, recyclerView, view, i);
            }
        });
        viewBinding.rvTime.setAdapter(musicTimeAdapter);
        if (this.scheduledSetTime == 0) {
            viewBinding.tvSetTime.setText(getString(R.string.music_timed_off_not_set));
            return;
        }
        CountDownTimerManager countDownTimerManager = new CountDownTimerManager(this.scheduledSetTime * 1000, 1000L, new CountDownTimerManager.OnCountDownTimerListener() { // from class: com.ltech.smarthome.view.dialog.MusicScheduledTimeDialog.2
            @Override // com.ltech.smarthome.ui.device.super_panel.music.countDownTimer.CountDownTimerManager.OnCountDownTimerListener
            public void onFinish() {
            }

            @Override // com.ltech.smarthome.ui.device.super_panel.music.countDownTimer.CountDownTimerManager.OnCountDownTimerListener
            public void onTick(long millisUntilFinished) {
                MusicScheduledTimeDialog.this.scheduledTimeLeft--;
                if (MusicScheduledTimeDialog.this.scheduledTimeLeft >= 0) {
                    viewBinding.tvSetTime.setText(MusicScheduledTimeDialog.this.getString(R.string.music_timed_off_set, String.valueOf(MusicScheduledTimeDialog.this.scheduledTimeLeft / 60), String.valueOf(MusicScheduledTimeDialog.this.scheduledTimeLeft % 60)));
                }
            }
        });
        this.scheduledCountDownTimerManager = countDownTimerManager;
        countDownTimerManager.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$0(MusicScheduledTimeDialog musicScheduledTimeDialog, RecyclerView recyclerView, View view, int i) {
        if (this.onTimeClickCallback != null) {
            musicScheduledTimeDialog.dismissDialog();
            this.onTimeClickCallback.onTimeClick(this.array[i]);
        }
    }

    public MusicScheduledTimeDialog setScheduledSetTime(int scheduledSetTime) {
        this.scheduledSetTime = scheduledSetTime;
        return this;
    }

    public MusicScheduledTimeDialog setScheduledTimeLeft(int scheduledTimeLeft) {
        this.scheduledTimeLeft = scheduledTimeLeft;
        return this;
    }

    public MusicScheduledTimeDialog setMinList(List<String> list) {
        this.timeList.clear();
        this.timeList.addAll(list);
        return this;
    }

    public MusicScheduledTimeDialog setOnTimeClickCallback(OnTimeClickCallback onTimeClickCallback) {
        this.onTimeClickCallback = onTimeClickCallback;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "music_time_dialog";
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        CountDownTimerManager countDownTimerManager = this.scheduledCountDownTimerManager;
        if (countDownTimerManager != null) {
            countDownTimerManager.cancel();
        }
    }
}