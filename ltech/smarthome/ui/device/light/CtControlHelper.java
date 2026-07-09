package com.ltech.smarthome.ui.device.light;

import androidx.appcompat.widget.AppCompatTextView;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.utils.LightUtils;

/* loaded from: classes4.dex */
public class CtControlHelper {
    int kMax;
    int kMin;
    int mMaxkelvin;
    int mMinkelvin;
    private Object object;
    private OnCtChangedListener onCtChangedListener;
    private RangeSeekBar rangeSeekbar;
    private AppCompatTextView tvK;

    public interface OnCtChangedListener {
        void onRangeChanged(int v, boolean finish);
    }

    public CtControlHelper(RangeSeekBar rangeSeekbar, AppCompatTextView tvK, OnCtChangedListener listener) {
        this.kMin = 2700;
        this.kMax = 6500;
        this.mMaxkelvin = 0;
        this.mMinkelvin = 0;
        new CtControlHelper(rangeSeekbar, tvK, null, listener);
    }

    public CtControlHelper(RangeSeekBar rangeSeekbar, final AppCompatTextView tvK, Object role, OnCtChangedListener listener) {
        this.kMin = 2700;
        this.kMax = 6500;
        this.mMaxkelvin = 0;
        this.mMinkelvin = 0;
        this.onCtChangedListener = listener;
        this.object = role;
        this.rangeSeekbar = rangeSeekbar;
        this.tvK = tvK;
        if (role instanceof Device) {
            Device device = (Device) role;
            this.mMinkelvin = device.getMinkelvin();
            this.mMaxkelvin = device.getMaxkelvin();
            if (ProductRepository.isCgdPro(device)) {
                this.mMinkelvin = 1000;
                this.mMaxkelvin = 10000;
            }
        } else if (role instanceof Group) {
            Group group = (Group) role;
            this.mMinkelvin = group.getMinkelvin();
            this.mMaxkelvin = group.getMaxkelvin();
        }
        if (role == null) {
            this.mMinkelvin = 1000;
            this.mMaxkelvin = 10000;
        }
        rangeSeekbar.setSeekBarMode(1);
        int i = this.mMinkelvin;
        if (this.mMaxkelvin + i == 0) {
            this.mMinkelvin = 2700;
            this.mMaxkelvin = 6500;
            rangeSeekbar.setRange(0.0f, 255.0f);
        } else {
            this.kMin = 1000;
            this.kMax = 10000;
            float ctK2Y = 255 - LightUtils.ctK2Y(i, 10000, 1000);
            rangeSeekbar.setRange(ctK2Y, (float) Math.max(ctK2Y + 0.1d, 255 - LightUtils.ctK2Y(this.mMaxkelvin, this.kMax, this.kMin)));
        }
        rangeSeekbar.setOnRangeChangedListener(new OnRangeChangedListener() { // from class: com.ltech.smarthome.ui.device.light.CtControlHelper.1
            @Override // com.jaygoo.widget.OnRangeChangedListener
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {
            }

            @Override // com.jaygoo.widget.OnRangeChangedListener
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                if (isFromUser) {
                    int stepK = LightUtils.getStepK((int) leftValue, 1);
                    if (CtControlHelper.this.mMinkelvin + CtControlHelper.this.mMaxkelvin > 0) {
                        tvK.setText(LightUtils.ctY2K(255 - stepK, CtControlHelper.this.kMax, CtControlHelper.this.kMin) + "K");
                    } else {
                        tvK.setText(String.valueOf(stepK));
                    }
                    if (CtControlHelper.this.onCtChangedListener != null) {
                        CtControlHelper.this.onCtChangedListener.onRangeChanged(stepK, false);
                    }
                }
            }

            @Override // com.jaygoo.widget.OnRangeChangedListener
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
                int stepK = LightUtils.getStepK((int) view.getLeftSeekBar().getProgress(), 1);
                if (CtControlHelper.this.mMinkelvin + CtControlHelper.this.mMaxkelvin > 0) {
                    tvK.setText(LightUtils.ctY2K(255 - stepK, CtControlHelper.this.kMax, CtControlHelper.this.kMin) + "K");
                } else {
                    tvK.setText(String.valueOf(stepK));
                }
                if (CtControlHelper.this.onCtChangedListener != null) {
                    CtControlHelper.this.onCtChangedListener.onRangeChanged(stepK, true);
                }
            }
        });
        initProgress();
    }

    public int getK(int progress) {
        return LightUtils.ctY2K(255 - progress, this.kMax, this.kMin);
    }

    public int getYOf255(int progress) {
        float minProgress = this.rangeSeekbar.getMinProgress();
        return (int) (((progress - minProgress) / (this.rangeSeekbar.getMaxProgress() - minProgress)) * 255.0f);
    }

    private void initProgress() {
        int i;
        int wy;
        Object obj = this.object;
        if (obj instanceof Device) {
            wy = ((Device) obj).getDeviceState().getWy();
        } else if (obj instanceof Group) {
            wy = ((Group) obj).getGroupState().getWy();
        } else {
            i = 0;
            setProgress(i);
        }
        i = 255 - wy;
        setProgress(i);
    }

    public int getKMax() {
        return this.kMax;
    }

    public int getkMin() {
        return this.kMin;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setProgress(int r5) {
        /*
            r4 = this;
            float r0 = (float) r5
            com.jaygoo.widget.RangeSeekBar r1 = r4.rangeSeekbar
            float r1 = r1.getMaxProgress()
            int r1 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r1 <= 0) goto L13
            com.jaygoo.widget.RangeSeekBar r5 = r4.rangeSeekbar
            float r5 = r5.getMaxProgress()
        L11:
            int r5 = (int) r5
            goto L24
        L13:
            com.jaygoo.widget.RangeSeekBar r1 = r4.rangeSeekbar
            float r1 = r1.getMinProgress()
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 >= 0) goto L24
            com.jaygoo.widget.RangeSeekBar r5 = r4.rangeSeekbar
            float r5 = r5.getMinProgress()
            goto L11
        L24:
            com.jaygoo.widget.RangeSeekBar r0 = r4.rangeSeekbar
            float r1 = (float) r5
            r0.setProgress(r1)
            int r0 = r4.mMinkelvin
            int r1 = r4.mMaxkelvin
            int r0 = r0 + r1
            if (r0 <= 0) goto L52
            androidx.appcompat.widget.AppCompatTextView r0 = r4.tvK
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            int r5 = 255 - r5
            int r2 = r4.kMax
            int r3 = r4.kMin
            int r5 = com.ltech.smarthome.utils.LightUtils.ctY2K(r5, r2, r3)
            r1.append(r5)
            java.lang.String r5 = "K"
            r1.append(r5)
            java.lang.String r5 = r1.toString()
            r0.setText(r5)
            return
        L52:
            androidx.appcompat.widget.AppCompatTextView r0 = r4.tvK
            java.lang.String r5 = java.lang.String.valueOf(r5)
            r0.setText(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.light.CtControlHelper.setProgress(int):void");
    }

    public int getProgress() {
        return LightUtils.getStepK((int) this.rangeSeekbar.getLeftSeekBar().getProgress(), 1);
    }
}