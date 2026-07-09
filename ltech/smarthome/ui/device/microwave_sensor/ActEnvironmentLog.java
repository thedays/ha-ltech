package com.ltech.smarthome.ui.device.microwave_sensor;

import android.graphics.Color;
import android.widget.RadioGroup;
import androidx.core.internal.view.SupportMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActEnvironmentLogBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.response.log.DeviceLog;
import com.ltech.smarthome.ui.log.LogHelper;
import com.ltech.smarthome.utils.DateUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/* loaded from: classes4.dex */
public class ActEnvironmentLog extends BaseNormalActivity<ActEnvironmentLogBinding> {
    private LineDataSet dataSet;
    private Device device;
    private long deviceId;
    private long lastPointTime;
    private LineChart lineChart;
    private BaseQuickAdapter<DeviceLog, BaseViewHolder> mAdapter;
    private int type = 0;
    private final int TYPE_LUX = 0;
    private final int TYPE_CT = 1;
    private final int[] rangeLux = {0, 100, 300};
    private final int[] colorLux = {Color.parseColor("#2C6ED5"), Color.parseColor("#FF8300"), Color.parseColor("#FFDF0B")};
    private final int[] rangeCt = {0, 6000, 6500};
    private final int[] colorCt = {Color.parseColor("#FF4B5A"), Color.parseColor("#FFDF0B"), Color.parseColor("#1B2E9C")};
    private LinkedHashMap<Long, Integer> values = new LinkedHashMap<>();
    private List<DeviceLog> luxLogList = new ArrayList();
    private List<DeviceLog> ctLogList = new ArrayList();
    private List<Long> timeArray = new ArrayList();
    private int lastPointTag = -1;
    private LogHelper.DataReceiveListener listener = new LogHelper.DataReceiveListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActEnvironmentLog.2
        @Override // com.ltech.smarthome.ui.log.LogHelper.DataReceiveListener
        public void onReceiveError() {
        }

        @Override // com.ltech.smarthome.ui.log.LogHelper.DataReceiveListener
        public void onDataReceive(List<DeviceLog> list) {
            if (list.isEmpty()) {
                ((ActEnvironmentLogBinding) ActEnvironmentLog.this.mViewBinding).rvContent.setVisibility(8);
                ActEnvironmentLog.this.dismissLoadingDialog();
                return;
            }
            ((ActEnvironmentLogBinding) ActEnvironmentLog.this.mViewBinding).rvContent.setVisibility(0);
            ActEnvironmentLog.this.mAdapter.replaceData(list);
            if (ActEnvironmentLog.this.type == 0 && ActEnvironmentLog.this.luxLogList.isEmpty()) {
                ActEnvironmentLog.this.luxLogList.addAll(list);
            }
            if (ActEnvironmentLog.this.type == 1 && ActEnvironmentLog.this.ctLogList.isEmpty()) {
                ActEnvironmentLog.this.ctLogList.addAll(list);
            }
            for (DeviceLog deviceLog : list) {
                Date stringToDate = DateUtils.stringToDate(deviceLog.getCreatetime(), "yyyy-MM-dd HH:mm:ss");
                if (stringToDate != null) {
                    ActEnvironmentLog.this.values.put(Long.valueOf(stringToDate.getTime() / 1000), Integer.valueOf(Integer.parseInt(deviceLog.getPropertyvalue(), 16)));
                }
            }
            ActEnvironmentLog.this.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActEnvironmentLog.2.1
                @Override // java.lang.Runnable
                public void run() {
                    ActEnvironmentLog.this.changeChartData(ActEnvironmentLog.this.type);
                    ActEnvironmentLog.this.dismissLoadingDialog();
                }
            }, 1000L);
        }
    };

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_environment_log;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.environment_record));
        setBackImage(R.mipmap.icon_back);
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
        this.device = Injection.repo().device().getDeviceByDeviceId(this.deviceId);
        ((ActEnvironmentLogBinding) this.mViewBinding).layoutTab.setVisibility(ProductId.ID_SENSOR_HSD.equals(this.device.getProductId()) ? 8 : 0);
        initAdapter();
        this.timeArray = getTimeArray();
        initChart(this.type);
        changeChartData(this.type);
        ((ActEnvironmentLogBinding) this.mViewBinding).layoutTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActEnvironmentLog$$ExternalSyntheticLambda0
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public final void onCheckedChanged(RadioGroup radioGroup, int i) {
                ActEnvironmentLog.this.lambda$initView$0(radioGroup, i);
            }
        });
        queryLog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(RadioGroup radioGroup, int i) {
        if (i == R.id.radio_ct) {
            this.type = 1;
        } else if (i == R.id.radio_lux) {
            this.type = 0;
        }
        this.values.clear();
        initChart(this.type);
        changeChartData(this.type);
        queryLog();
    }

    private void initAdapter() {
        BaseQuickAdapter<DeviceLog, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<DeviceLog, BaseViewHolder>(R.layout.item_device_log, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActEnvironmentLog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, DeviceLog deviceLog) {
                int bindingAdapterPosition = helper.getBindingAdapterPosition();
                int parseInt = Integer.parseInt(deviceLog.getPropertyvalue(), 16);
                BaseViewHolder text = helper.setGone(R.id.time_line, bindingAdapterPosition != getItemCount() - 1).setText(R.id.tv_time, deviceLog.getShortCreatetime());
                StringBuilder sb = new StringBuilder();
                ActEnvironmentLog actEnvironmentLog = ActEnvironmentLog.this;
                sb.append(actEnvironmentLog.getValueString(actEnvironmentLog.type, parseInt));
                sb.append(" ");
                sb.append(parseInt);
                sb.append(ActEnvironmentLog.this.type == 0 ? "lux" : "K");
                text.setText(R.id.tv_log, sb.toString());
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.bindToRecyclerView(((ActEnvironmentLogBinding) this.mViewBinding).rvContent);
        ((ActEnvironmentLogBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
    }

    private void queryLog() {
        this.timeArray = getTimeArray();
        if (this.type == 0) {
            if (this.luxLogList.isEmpty()) {
                showLoadingDialog("");
                LogHelper.instance().queryPropertyLog(this.deviceId, this.type != 0 ? "env_CT" : "env_Lux", this.listener);
                return;
            } else {
                refreshChartData(this.luxLogList);
                return;
            }
        }
        if (this.ctLogList.isEmpty()) {
            showLoadingDialog("");
            LogHelper.instance().queryPropertyLog(this.deviceId, this.type != 0 ? "env_CT" : "env_Lux", this.listener);
        } else {
            refreshChartData(this.ctLogList);
        }
    }

    private List<Long> getTimeArray() {
        this.timeArray.clear();
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        long j = currentTimeMillis - (currentTimeMillis % 3600);
        this.timeArray.add(Long.valueOf(j - 82800));
        this.timeArray.add(Long.valueOf(j - 61200));
        this.timeArray.add(Long.valueOf(j - 39600));
        this.timeArray.add(Long.valueOf(j - 18000));
        this.timeArray.add(Long.valueOf(j));
        return this.timeArray;
    }

    private void initChart(int type) {
        ((ActEnvironmentLogBinding) this.mViewBinding).tvUnit.setText(type == 0 ? R.string.lux_unit : R.string.ct_unit);
        LineChart lineChart = ((ActEnvironmentLogBinding) this.mViewBinding).lineChart;
        this.lineChart = lineChart;
        lineChart.getDescription().setEnabled(false);
        this.lineChart.setTouchEnabled(false);
        this.lineChart.setScaleXEnabled(true);
        this.lineChart.setScaleYEnabled(true);
        Legend legend = this.lineChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        this.lineChart.setExtraTopOffset(SizeUtils.dp2px(6.0f));
        this.lineChart.setExtraBottomOffset(SizeUtils.dp2px(3.0f));
        legend.setDirection(Legend.LegendDirection.RIGHT_TO_LEFT);
        legend.setTextSize(14.0f);
        legend.setXEntrySpace(SizeUtils.dp2px(5.0f));
        legend.setFormToTextSpace(SizeUtils.dp2px(2.0f));
        ArrayList arrayList = new ArrayList();
        if (type == 0) {
            arrayList.add(new LegendEntry(getString(R.string.lux_bright), Legend.LegendForm.SQUARE, 12.0f, 6.0f, null, this.colorLux[2]));
            arrayList.add(new LegendEntry(getString(R.string.lux_suitable), Legend.LegendForm.SQUARE, 12.0f, 6.0f, null, this.colorLux[1]));
            arrayList.add(new LegendEntry(getString(R.string.lux_dark), Legend.LegendForm.SQUARE, 12.0f, 6.0f, null, this.colorLux[0]));
        } else {
            arrayList.add(new LegendEntry(getString(R.string.ct_cold), Legend.LegendForm.SQUARE, 12.0f, 6.0f, null, this.colorCt[2]));
            arrayList.add(new LegendEntry(getString(R.string.ct_white), Legend.LegendForm.SQUARE, 12.0f, 6.0f, null, this.colorCt[1]));
            arrayList.add(new LegendEntry(getString(R.string.ct_warm), Legend.LegendForm.SQUARE, 12.0f, 6.0f, null, this.colorCt[0]));
        }
        legend.setCustom(arrayList);
        this.lineChart.getAxisRight().setEnabled(false);
        YAxis axisLeft = this.lineChart.getAxisLeft();
        axisLeft.setDrawGridLines(true);
        axisLeft.setDrawAxisLine(false);
        axisLeft.setDrawLabels(true);
        axisLeft.enableGridDashedLine(5.0f, 2.0f, 0.0f);
        if (type == 0) {
            axisLeft.setAxisMaximum(1000.0f);
            axisLeft.setAxisMinimum(0.0f);
            axisLeft.setGranularity(250.0f);
        } else {
            axisLeft.setAxisMaximum(10000.0f);
            axisLeft.setAxisMinimum(1000.0f);
            axisLeft.setGranularity(2250.0f);
        }
        axisLeft.setLabelCount(5, true);
        axisLeft.setValueFormatter(new ValueFormatter(this) { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActEnvironmentLog.3
            @Override // com.github.mikephil.charting.formatter.ValueFormatter
            public String getFormattedValue(float value) {
                return ((int) value) + "";
            }
        });
        XAxis xAxis = this.lineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawLabels(true);
        xAxis.setAxisMinimum(this.timeArray.get(0).longValue());
        xAxis.setAxisMaximum((System.currentTimeMillis() / 1000.0f) + 200.0f);
        xAxis.setYOffset(SizeUtils.dp2px(2.0f));
        xAxis.setLabelCount(5, true);
        xAxis.setValueFormatter(new ValueFormatter() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActEnvironmentLog.4
            @Override // com.github.mikephil.charting.formatter.ValueFormatter
            public String getFormattedValue(float value) {
                long j = (long) value;
                long j2 = j - (j % 3600);
                Iterator it = ActEnvironmentLog.this.timeArray.iterator();
                while (it.hasNext()) {
                    long longValue = ((Long) it.next()).longValue();
                    if (j2 == longValue || Math.abs(j2 - longValue) == 3600) {
                        return DateUtils.dateToString(new Date(longValue * 1000), "HH:00");
                    }
                }
                return "";
            }
        });
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeChartData(int type) {
        List<Entry> arrayList = new ArrayList<>();
        ArrayList arrayList2 = new ArrayList();
        if (!this.values.isEmpty()) {
            arrayList = getEntryList(type, this.values);
            int[] iArr = type == 0 ? this.colorLux : this.colorCt;
            if (arrayList.size() > 1) {
                int i = 0;
                while (i < arrayList.size() - 1) {
                    int valueTag = getValueTag(type, (int) arrayList.get(i).getY());
                    i++;
                    arrayList2.add(Integer.valueOf(iArr[Math.max(valueTag, getValueTag(type, (int) arrayList.get(i).getY()))]));
                }
            } else {
                arrayList2.add(Integer.valueOf(iArr[getValueTag(type, (int) arrayList.get(0).getY())]));
            }
        } else {
            arrayList.add(new Entry(0.0f, 0.0f));
            arrayList2.add(Integer.valueOf(SupportMenu.CATEGORY_MASK));
        }
        LineDataSet lineDataSet = new LineDataSet(arrayList, "");
        this.dataSet = lineDataSet;
        lineDataSet.setMode(LineDataSet.Mode.LINEAR);
        this.dataSet.setColors(arrayList2);
        this.dataSet.setDrawValues(false);
        this.dataSet.setDrawCircles(false);
        this.lineChart.setData(new LineData(this.dataSet));
        this.lineChart.invalidate();
    }

    private void refreshChartData(List<DeviceLog> list) {
        ((ActEnvironmentLogBinding) this.mViewBinding).rvContent.setVisibility(0);
        this.mAdapter.replaceData(list);
        for (DeviceLog deviceLog : list) {
            Date stringToDate = DateUtils.stringToDate(deviceLog.getCreatetime(), "yyyy-MM-dd HH:mm:ss");
            if (stringToDate != null) {
                this.values.put(Long.valueOf(stringToDate.getTime() / 1000), Integer.valueOf(Integer.parseInt(deviceLog.getPropertyvalue(), 16)));
            }
        }
        changeChartData(this.type);
    }

    private int getValueTag(int type, int value) {
        if (type == 0) {
            int[] iArr = this.rangeLux;
            if (value > iArr[2]) {
                return 2;
            }
            return value > iArr[1] ? 1 : 0;
        }
        int[] iArr2 = this.rangeCt;
        if (value > iArr2[2]) {
            return 2;
        }
        return value > iArr2[1] ? 1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getValueString(int type, int value) {
        if (type == 0) {
            int[] iArr = this.rangeLux;
            if (value >= iArr[2]) {
                return getString(R.string.lux_bright);
            }
            if (value >= iArr[1]) {
                return getString(R.string.lux_suitable);
            }
            return getString(R.string.lux_dark);
        }
        int[] iArr2 = this.rangeCt;
        if (value >= iArr2[2]) {
            return getString(R.string.ct_cold);
        }
        if (value >= iArr2[1]) {
            return getString(R.string.ct_white);
        }
        return getString(R.string.ct_warm);
    }

    private List<Entry> getEntryList(int type, LinkedHashMap<Long, Integer> values) {
        ArrayList arrayList = new ArrayList();
        int[] iArr = type == 0 ? this.rangeLux : this.rangeCt;
        int i = -1;
        this.lastPointTag = -1;
        for (Long l : values.keySet()) {
            int intValue = values.get(l).intValue();
            if (intValue >= 0) {
                int valueTag = getValueTag(type, intValue);
                int i2 = this.lastPointTag;
                if (i2 != i && valueTag != i2) {
                    long longValue = (this.lastPointTime + l.longValue()) / 2;
                    if (valueTag == 0) {
                        int i3 = this.lastPointTag;
                        if (i3 == 1) {
                            arrayList.add(new Entry(longValue, iArr[1]));
                        } else if (i3 == 2) {
                            arrayList.add(new Entry(longValue, iArr[2]));
                            arrayList.add(new Entry((longValue + l.longValue()) / 2.0f, iArr[1]));
                        }
                    } else if (valueTag == 1) {
                        int i4 = this.lastPointTag;
                        if (i4 == 0) {
                            arrayList.add(new Entry(longValue, iArr[1]));
                        } else if (i4 == 2) {
                            arrayList.add(new Entry((longValue + l.longValue()) / 2.0f, iArr[2]));
                        }
                    } else {
                        int i5 = this.lastPointTag;
                        if (i5 == 0) {
                            arrayList.add(new Entry(longValue, iArr[1]));
                            arrayList.add(new Entry((longValue + l.longValue()) / 2.0f, iArr[2]));
                        } else if (i5 == 1) {
                            arrayList.add(new Entry(longValue, iArr[1]));
                        }
                    }
                }
                this.lastPointTag = valueTag;
                this.lastPointTime = l.longValue();
                arrayList.add(new Entry(l.longValue(), intValue));
                i = -1;
            }
        }
        return arrayList;
    }
}