package com.ltech.smarthome.ui.log;

import android.content.Context;
import android.graphics.Rect;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kookong.app.data.AppConst;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActLocalDeviceLogBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes4.dex */
public class ActLocalDeviceLog extends BaseNormalActivity<ActLocalDeviceLogBinding> {
    private static final String TAG = "ActLocalDeviceLog";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    PausableRotateAnimation animation;
    private Device device;
    private BaseQuickAdapter<LogCardMsg, BaseViewHolder> mAdapter;
    private int total;
    private ArrayList<LogCardMsg> logCardList = new ArrayList<>();
    private ArrayList<LogDetailMsg> tempLogDetailMsgs = new ArrayList<>();
    private int current = 1;
    private boolean querying = false;
    private HashMap<String, ArrayList<LogDetailMsg>> queryResultMap = new HashMap<>();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_local_device_log;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.device_log));
        setBackImage(R.mipmap.icon_back);
        initAdapter();
        initAnimation();
        ((ActLocalDeviceLogBinding) this.mViewBinding).tvTip.setVisibility(0);
        ((ActLocalDeviceLogBinding) this.mViewBinding).ivStatus.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.log.ActLocalDeviceLog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (ActLocalDeviceLog.this.querying) {
                    ActLocalDeviceLog.this.pause();
                } else {
                    ActLocalDeviceLog.this.play();
                }
            }
        });
    }

    private void initAdapter() {
        BaseQuickAdapter<LogCardMsg, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<LogCardMsg, BaseViewHolder>(R.layout.act_device_log_item, this.logCardList) { // from class: com.ltech.smarthome.ui.log.ActLocalDeviceLog.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(final BaseViewHolder helper, final LogCardMsg item) {
                helper.setText(R.id.tv_date, TextUtils.isEmpty(item.getDate()) ? this.mContext.getString(R.string.unknow_time) : ActLocalDeviceLog.this.getDateDescription(item.getDate(), helper.itemView.getContext()));
                RecyclerView recyclerView = (RecyclerView) helper.getView(R.id.rv_data);
                recyclerView.setAdapter(new BaseQuickAdapter<LogDetailMsg, BaseViewHolder>(this, R.layout.act_device_log_sub_item, item.detailMsgs) { // from class: com.ltech.smarthome.ui.log.ActLocalDeviceLog.2.1
                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // com.chad.library.adapter.base.BaseQuickAdapter
                    public void convert(BaseViewHolder baseViewHolder, LogDetailMsg logDetailMsg) {
                        baseViewHolder.setText(R.id.tv_time, TextUtils.isEmpty(logDetailMsg.getTime()) ? this.mContext.getString(R.string.unknow_time) : logDetailMsg.getTime());
                        baseViewHolder.setGone(R.id.ll_hor_line, false);
                        baseViewHolder.setGone(R.id.tv_ver_line, true);
                        Log.e(TAG, logDetailMsg.getReadNum() + "__errorCode:" + logDetailMsg.getError() + "_data:" + logDetailMsg.getDate() + "__time:" + logDetailMsg.getTime());
                        if (TextUtils.isEmpty(logDetailMsg.getAction())) {
                            if (!TextUtils.isEmpty(logDetailMsg.getError())) {
                                baseViewHolder.setText(R.id.tv_action, this.mContext.getString(R.string.error_code) + logDetailMsg.getError());
                                baseViewHolder.setGone(R.id.tv_control, false);
                            } else {
                                baseViewHolder.setText(R.id.tv_action, this.mContext.getString(R.string.app_str_unknow_action));
                                baseViewHolder.setGone(R.id.tv_control, false);
                            }
                        } else {
                            baseViewHolder.setText(R.id.tv_action, logDetailMsg.getAction());
                            baseViewHolder.setGone(R.id.tv_control, !TextUtils.isEmpty(logDetailMsg.getControlName()));
                            baseViewHolder.setText(R.id.tv_control, TextUtils.isEmpty(logDetailMsg.getControlName()) ? "" : logDetailMsg.getControlName());
                        }
                        Log.e(TAG, "helper.getBindingAdapterPosition()" + helper.getAdapterPosition() + "__size:" + item.detailMsgs.size());
                        int adapterPosition = baseViewHolder.getAdapterPosition();
                        if (adapterPosition > 0 && !TextUtils.isEmpty(((LogDetailMsg) item.detailMsgs.get(adapterPosition - 1)).getTime()) && TextUtils.isEmpty(logDetailMsg.getTime())) {
                            baseViewHolder.setGone(R.id.ll_hor_line, true);
                        }
                        if (adapterPosition < item.detailMsgs.size() - 1 && TextUtils.isEmpty(((LogDetailMsg) item.detailMsgs.get(adapterPosition + 1)).getTime()) && !TextUtils.isEmpty(logDetailMsg.getTime())) {
                            baseViewHolder.setGone(R.id.tv_ver_line, false);
                        }
                        if (adapterPosition == item.detailMsgs.size() - 1) {
                            baseViewHolder.setGone(R.id.tv_ver_line, false);
                        }
                    }
                });
                recyclerView.setLayoutManager(new LinearLayoutManager(ActLocalDeviceLog.this));
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.bindToRecyclerView(((ActLocalDeviceLogBinding) this.mViewBinding).rvContent);
        ((ActLocalDeviceLogBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActLocalDeviceLogBinding) this.mViewBinding).rvContent.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.log.ActLocalDeviceLog.3
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, ConvertUtils.dp2px(10.0f), 0, ConvertUtils.dp2px(10.0f));
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        this.device = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
        ((ActLocalDeviceLogBinding) this.mViewBinding).tvTip.setText(String.format(getString(R.string.device_log_query_pause), Integer.valueOf(this.current), Integer.valueOf(this.total)));
        pause();
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.log.ActLocalDeviceLog$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ActLocalDeviceLog.this.lambda$startObserve$0();
            }
        }, 100L);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (this.querying) {
            MessageDialog.show(this, getString(R.string.device_log_query_exit_prompt), "", getString(R.string.confirm), getString(R.string.cancel)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.log.ActLocalDeviceLog$$ExternalSyntheticLambda3
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$back$1;
                    lambda$back$1 = ActLocalDeviceLog.this.lambda$back$1(baseDialog, view);
                    return lambda$back$1;
                }
            });
        } else {
            super.back();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$back$1(BaseDialog baseDialog, View view) {
        super.back();
        return false;
    }

    private void queryLog() {
        int i = this.current - 1;
        if (i < 0 || !this.querying) {
            Log.e(TAG, "查询结束------seq:" + i + "__querying:" + this.querying);
            pause();
            return;
        }
        Log.e(TAG, "查询------seq:" + i);
        CmdAssistant.getQueryCmdAssistant(this.device, new int[0]).queryDeviceLog(this, i, new IAction() { // from class: com.ltech.smarthome.ui.log.ActLocalDeviceLog$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLocalDeviceLog.this.lambda$queryLog$2((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryLog$2(ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            if (this.total == 0) {
                int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 20), 16);
                this.total = parseInt;
                this.current = parseInt;
                ((ActLocalDeviceLogBinding) this.mViewBinding).tvTip.setText(String.format(Locale.US, getString(R.string.device_log_querying), Integer.valueOf(this.total - this.current), Integer.valueOf(this.total)));
                queryLog();
                return;
            }
            this.total = Integer.parseInt(responseMsg.getResData().substring(16, 20), 16);
            this.current = Integer.parseInt(responseMsg.getResData().substring(20, 24), 16);
            Log.e(TAG, "reponseMeshData:" + responseMsg.getResData());
            addData(LocalLogHelper.parseLogData(getBaseContext(), responseMsg.getResData().substring(16), this.device.getPlaceId(), null, this.device.getUnicastAddress()));
            return;
        }
        if (responseMsg != null && responseMsg.getStateCode() == 153) {
            pause();
            ((ActLocalDeviceLogBinding) this.mViewBinding).tvEmpty.setText(R.string.product_no_support);
        } else {
            pause();
            showErrorDialog(getString(R.string.search_fail));
        }
    }

    private String parseLogData(int sequence, String data) {
        StringBuilder sb = new StringBuilder();
        int parseInt = Integer.parseInt(data.substring(0, 2), 16);
        int parseInt2 = Integer.parseInt(data.substring(2, 6), 16);
        int parseInt3 = Integer.parseInt(data.substring(6, 8), 16);
        int parseInt4 = Integer.parseInt(data.substring(8, 10), 16);
        int parseInt5 = Integer.parseInt(data.substring(10, 12), 16);
        int parseInt6 = Integer.parseInt(data.substring(12, 14), 16);
        int parseInt7 = Integer.parseInt(data.substring(14, 16), 16);
        sb.append(String.format(Locale.US, "总数:%d  当前:%d  类型:%d\n", Integer.valueOf(this.total), Integer.valueOf(sequence), Integer.valueOf(parseInt)));
        sb.append(String.format(Locale.US, "%d-%02d-%02d %02d:%02d:%02d\n", Integer.valueOf(parseInt2), Integer.valueOf(parseInt3), Integer.valueOf(parseInt4), Integer.valueOf(parseInt5), Integer.valueOf(parseInt6), Integer.valueOf(parseInt7)));
        sb.append("MSG:");
        sb.append(data.substring(16));
        return sb.toString();
    }

    public static class LogDetailMsg {
        private String action;
        private String controlName;
        private String date;
        private String error;
        private boolean isFirstError;
        private int number;
        private int readNum;
        private String time;
        private int total;

        public int getTotal() {
            return this.total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getNumber() {
            return this.number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getReadNum() {
            return this.readNum;
        }

        public void setReadNum(int readNum) {
            this.readNum = readNum;
        }

        public LogDetailMsg() {
        }

        public boolean isFirstError() {
            return this.isFirstError;
        }

        public void setFirstError(boolean firstError) {
            this.isFirstError = firstError;
        }

        public LogDetailMsg(String date, String time, String action, String controlName, String error, boolean isFirstError) {
            this.date = date;
            this.time = time;
            this.action = action;
            this.controlName = controlName;
            this.error = error;
            this.isFirstError = isFirstError;
        }

        public String getTime() {
            return this.time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getAction() {
            return this.action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getControlName() {
            return this.controlName;
        }

        public void setControlName(String controlName) {
            this.controlName = controlName;
        }

        public String getError() {
            return this.error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getDate() {
            return this.date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    public static class LogCardMsg {
        private String date;
        private ArrayList<LogDetailMsg> detailMsgs;

        public ArrayList<LogDetailMsg> getDetailMsgs() {
            return this.detailMsgs;
        }

        public void setDetailMsgs(ArrayList<LogDetailMsg> detailMsgs) {
            this.detailMsgs = detailMsgs;
        }

        public LogCardMsg(String date, ArrayList<LogDetailMsg> detailMsgs) {
            this.date = date;
            this.detailMsgs = detailMsgs;
        }

        public String getDate() {
            return this.date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    private void initAnimation() {
        long j = AppConst.KOOKONG_BRANDID_MIX_STB;
        PausableRotateAnimation pausableRotateAnimation = new PausableRotateAnimation(0.0f, 1000 * 360.0f, 1, 0.5f, 1, 0.5f);
        this.animation = pausableRotateAnimation;
        pausableRotateAnimation.setDuration(j);
        this.animation.setRepeatCount(-1);
        this.animation.setInterpolator(new LinearInterpolator());
        this.animation.setFillAfter(true);
        ((ActLocalDeviceLogBinding) this.mViewBinding).searchImage.setAnimation(this.animation);
    }

    private void setAnimation(boolean play) {
        if (play) {
            this.animation.resume();
        } else {
            this.animation.pause();
        }
    }

    private void addData(LogDetailMsg logDetailMsg) {
        if (logDetailMsg == null) {
            Log.e(TAG, "LogDetailMsg is null");
        } else {
            if (TextUtils.isEmpty(logDetailMsg.getDate()) || !isValidDate(logDetailMsg.getDate())) {
                logDetailMsg.setDate("");
                logDetailMsg.setTime("");
            }
            if (TextUtils.isEmpty(logDetailMsg.getTime()) || !isValidTime(logDetailMsg.getTime())) {
                logDetailMsg.setTime("");
                logDetailMsg.setDate("");
            }
            if (!TextUtils.isEmpty(logDetailMsg.getDate())) {
                int intValue = Integer.valueOf(logDetailMsg.getDate().substring(0, 4)).intValue();
                Log.e(TAG, "year:" + intValue);
                if (intValue < 2020 || intValue > 2100) {
                    logDetailMsg.setTime("");
                    logDetailMsg.setDate("");
                }
            }
            logDetailMsg.setNumber(this.current);
            logDetailMsg.setReadNum(this.current);
            this.tempLogDetailMsgs.add(logDetailMsg);
            if (((ActLocalDeviceLogBinding) this.mViewBinding).tvEmpty.getVisibility() == 0) {
                ((ActLocalDeviceLogBinding) this.mViewBinding).tvEmpty.setVisibility(8);
            }
            ((ActLocalDeviceLogBinding) this.mViewBinding).tvTip.setText(String.format(Locale.US, getString(R.string.device_log_querying), Integer.valueOf(this.total - this.current), Integer.valueOf(this.total)));
            if (this.logCardList.size() > 0) {
                ArrayList<LogCardMsg> arrayList = this.logCardList;
                ArrayList<LogDetailMsg> detailMsgs = arrayList.get(arrayList.size() - 1).getDetailMsgs();
                if (!TextUtils.isEmpty(logDetailMsg.getDate())) {
                    String date = logDetailMsg.getDate();
                    ArrayList<LogCardMsg> arrayList2 = this.logCardList;
                    if (!date.equalsIgnoreCase(arrayList2.get(arrayList2.size() - 1).getDate())) {
                        ArrayList arrayList3 = new ArrayList();
                        arrayList3.add(logDetailMsg);
                        this.logCardList.add(new LogCardMsg(logDetailMsg.getDate(), arrayList3));
                        this.mAdapter.notifyItemInserted(this.logCardList.size() - 1);
                    }
                }
                detailMsgs.add(logDetailMsg);
                this.mAdapter.notifyItemChanged(this.logCardList.size() - 1);
            } else {
                ArrayList arrayList4 = new ArrayList();
                arrayList4.add(logDetailMsg);
                this.logCardList.add(new LogCardMsg(logDetailMsg.getDate(), arrayList4));
                this.mAdapter.notifyItemInserted(this.logCardList.size() - 1);
            }
            Iterator<LogCardMsg> it = this.logCardList.iterator();
            while (it.hasNext()) {
                Log.e(TAG, "cardSize:" + it.next().getDetailMsgs().size());
            }
        }
        queryLog();
    }

    public static class PausableRotateAnimation extends RotateAnimation {
        private long mElapsedAtPause;
        private boolean mPaused;

        public PausableRotateAnimation(float fromDegrees, float toDegrees, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue) {
            super(fromDegrees, toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue);
            this.mElapsedAtPause = 0L;
            this.mPaused = false;
        }

        @Override // android.view.animation.Animation
        public boolean getTransformation(long currentTime, Transformation outTransformation) {
            if (this.mPaused && this.mElapsedAtPause == 0) {
                this.mElapsedAtPause = currentTime - getStartTime();
            }
            if (this.mPaused) {
                setStartTime(currentTime - this.mElapsedAtPause);
            }
            return super.getTransformation(currentTime, outTransformation);
        }

        public void pause() {
            if (this.mPaused) {
                return;
            }
            this.mElapsedAtPause = SystemClock.uptimeMillis() - getStartTime();
            this.mPaused = true;
        }

        public void resume() {
            if (this.mPaused) {
                setStartTime(SystemClock.uptimeMillis() - this.mElapsedAtPause);
                this.mPaused = false;
            }
        }

        public boolean isPaused() {
            return this.mPaused;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pause() {
        this.querying = false;
        AppCompatTextView appCompatTextView = ((ActLocalDeviceLogBinding) this.mViewBinding).tvTip;
        String string = getString(R.string.device_log_query_pause);
        int i = this.total;
        int i2 = this.current;
        appCompatTextView.setText(String.format(string, Integer.valueOf(i - i2 > 0 ? i - i2 : 0), Integer.valueOf(this.total)));
        ((ActLocalDeviceLogBinding) this.mViewBinding).searchSpreadView.setAnimate(false);
        ((ActLocalDeviceLogBinding) this.mViewBinding).ivStatus.setBackgroundResource(R.mipmap.ic_play);
        setAnimation(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void play() {
        this.querying = true;
        ((ActLocalDeviceLogBinding) this.mViewBinding).ivStatus.setBackgroundResource(R.mipmap.ic_stop);
        ((ActLocalDeviceLogBinding) this.mViewBinding).searchSpreadView.setAnimate(true);
        setAnimation(true);
        AppCompatTextView appCompatTextView = ((ActLocalDeviceLogBinding) this.mViewBinding).tvTip;
        String string = getString(R.string.device_log_querying);
        int i = this.total;
        int i2 = this.current;
        appCompatTextView.setText(String.format(string, Integer.valueOf(i - i2 > 0 ? i - i2 : 0), Integer.valueOf(this.total)));
        queryLog();
    }

    private boolean isValidDate(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(dateStr);
        } catch (Exception unused) {
        }
        return dateStr.length() == 8;
    }

    private boolean isValidTime(String timeStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(timeStr);
        } catch (Exception unused) {
        }
        return timeStr.length() == 8;
    }

    private void checkSetDate() {
        Iterator<LogDetailMsg> it = this.tempLogDetailMsgs.iterator();
        String str = "";
        String str2 = "";
        while (it.hasNext()) {
            LogDetailMsg next = it.next();
            if (TextUtils.isEmpty(next.getDate())) {
                next.setDate(str2);
            } else {
                str2 = next.getDate();
            }
        }
        if (this.tempLogDetailMsgs.size() <= 0 || !TextUtils.isEmpty(this.tempLogDetailMsgs.get(0).getDate())) {
            return;
        }
        for (int size = this.tempLogDetailMsgs.size() - 1; size >= 0; size--) {
            LogDetailMsg logDetailMsg = this.tempLogDetailMsgs.get(size);
            if (TextUtils.isEmpty(logDetailMsg.getDate())) {
                logDetailMsg.setDate(str);
            } else {
                str = logDetailMsg.getDate();
            }
        }
    }

    private void sort(ArrayList<LogDetailMsg> logDetailMsgs) {
        Collections.sort(logDetailMsgs, new Comparator<LogDetailMsg>(this) { // from class: com.ltech.smarthome.ui.log.ActLocalDeviceLog.4
            @Override // java.util.Comparator
            public int compare(LogDetailMsg o1, LogDetailMsg o2) {
                if (TextUtils.isEmpty(o1.getTime().replaceAll(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, "")) && TextUtils.isEmpty(o2.getTime().replaceAll(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, ""))) {
                    return o2.getNumber() - o1.getNumber();
                }
                if (TextUtils.isEmpty(o1.getTime().replaceAll(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, "")) && !TextUtils.isEmpty(o2.getTime().replaceAll(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, ""))) {
                    return 1;
                }
                if (TextUtils.isEmpty(o1.getTime().replaceAll(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, "")) || !TextUtils.isEmpty(o2.getTime().replaceAll(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, ""))) {
                    return o2.getTime().replaceAll(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, "").compareTo(o1.getTime().replaceAll(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, ""));
                }
                return -1;
            }
        });
        Collections.sort(this.logCardList, new Comparator<LogCardMsg>(this) { // from class: com.ltech.smarthome.ui.log.ActLocalDeviceLog.5
            @Override // java.util.Comparator
            public int compare(LogCardMsg o1, LogCardMsg o2) {
                if (TextUtils.isEmpty(o1.getDate()) && TextUtils.isEmpty(o2.getDate())) {
                    return 1;
                }
                if (TextUtils.isEmpty(o1.getDate()) && !TextUtils.isEmpty(o2.getDate())) {
                    return 1;
                }
                if (TextUtils.isEmpty(o1.getDate()) || !TextUtils.isEmpty(o2.getDate())) {
                    return o2.getDate().compareTo(o1.getDate());
                }
                return -1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: queryLogFirst, reason: merged with bridge method [inline-methods] */
    public void lambda$startObserve$0() {
        CmdAssistant.getQueryCmdAssistant(this.device, new int[0]).queryDeviceLog(this, 0, new IAction() { // from class: com.ltech.smarthome.ui.log.ActLocalDeviceLog$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLocalDeviceLog.this.lambda$queryLogFirst$3((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryLogFirst$3(ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            this.total = Integer.parseInt(responseMsg.getResData().substring(16, 20), 16);
            ((ActLocalDeviceLogBinding) this.mViewBinding).tvTip.setText(String.format(Locale.US, getString(R.string.device_log_query_pause), 0, Integer.valueOf(this.total)));
            this.current = this.total;
        } else if (responseMsg != null && responseMsg.getStateCode() == 153) {
            pause();
            ((ActLocalDeviceLogBinding) this.mViewBinding).tvEmpty.setText(R.string.product_no_support);
        } else {
            pause();
            showErrorDialog(getString(R.string.search_fail));
        }
    }

    private int checkDate(String dateStr) {
        if (!TextUtils.isEmpty(dateStr) && dateStr.length() == 8) {
            try {
                Date parse = sdf.parse(dateStr);
                if (parse == null) {
                    return -1;
                }
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.set(11, 0);
                calendar.set(12, 0);
                calendar.set(13, 0);
                calendar.set(14, 0);
                Calendar calendar2 = Calendar.getInstance(TimeZone.getDefault());
                calendar2.add(6, -1);
                calendar2.set(11, 0);
                calendar2.set(12, 0);
                calendar2.set(13, 0);
                calendar2.set(14, 0);
                Calendar calendar3 = Calendar.getInstance(TimeZone.getDefault());
                calendar3.setTime(parse);
                calendar3.set(11, 0);
                calendar3.set(12, 0);
                calendar3.set(13, 0);
                calendar3.set(14, 0);
                if (calendar3.equals(calendar)) {
                    return 0;
                }
                return calendar3.equals(calendar2) ? 1 : 2;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public String getDateDescription(String dateStr, Context context) {
        int checkDate = checkDate(dateStr);
        if (checkDate == 0) {
            return context.getString(R.string.today);
        }
        if (checkDate != 1) {
            return checkDate != 2 ? context.getString(R.string.unknow_time) : dateStr;
        }
        return context.getString(R.string.yesterday);
    }
}