package com.ltech.smarthome.ui.camera.play;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.FtCameraRecordBinding;
import com.ltech.smarthome.view.dialog.CalendarDialog;
import com.smart.message.utils.LHomeLog;
import com.videogo.constant.IntentConsts;
import com.videogo.openapi.EZConstants;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceRecordFile;
import com.videogo.openapi.bean.resp.CloudPartInfoFile;
import com.videogo.ui.playback.CloudPartInfoFileEx;
import com.videogo.ui.querylist.ClickedListItem;
import com.videogo.ui.querylist.QueryDeviceRecordFilesAsyncTask;
import com.videogo.ui.querylist.QueryPlayBackListTaskCallback;
import com.videogo.ui.querylist.SectionListAdapter;
import com.videogo.ui.querylist.StandardArrayAdapter;
import com.videogo.util.Utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/* loaded from: classes4.dex */
public class FtCameraRecord extends BaseNormalFragment<FtCameraRecordBinding> implements QueryPlayBackListTaskCallback, SectionListAdapter.OnHikItemClickListener {
    private ActCameraPlay activity;
    private EZCameraInfo ezCameraInfo;
    private boolean isToday;
    private StandardArrayAdapter mDeviceRecordsAdapter;
    private SectionListAdapter mSectionAdapterForLocal;
    private QueryDeviceRecordFilesAsyncTask queryDeviceRecordFilesAsyncTask;
    public MutableLiveData<Date> queryDate = new MutableLiveData<>();
    private EZConstants.EZVideoRecordType recordType = EZConstants.EZVideoRecordType.EZ_VIDEO_RECORD_TYPE_ALL;

    @Override // com.videogo.ui.querylist.SectionListAdapter.OnHikItemClickListener
    public void onHikMoreClickListener(boolean isExpand) {
    }

    @Override // com.videogo.ui.querylist.SectionListAdapter.OnHikItemClickListener
    public void onSelectedChangeListener(int total) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_camera_record;
    }

    @Override // com.videogo.ui.querylist.QueryPlayBackListTaskCallback
    public void queryException() {
    }

    @Override // com.videogo.ui.querylist.QueryPlayBackListTaskCallback
    public void queryHasNoData() {
    }

    @Override // com.videogo.ui.querylist.QueryPlayBackListTaskCallback
    public void queryLocalException() {
    }

    @Override // com.videogo.ui.querylist.QueryPlayBackListTaskCallback
    public void querySuccessFromCloud(List<CloudPartInfoFileEx> cloudPartInfoFileExs, int queryMLocalStatus, List<CloudPartInfoFile> cloudPartInfoFile) {
    }

    public static FtCameraRecord newInstance(EZCameraInfo cameraInfo) {
        FtCameraRecord ftCameraRecord = new FtCameraRecord();
        Bundle bundle = new Bundle();
        bundle.putParcelable(IntentConsts.EXTRA_CAMERA_INFO, cameraInfo);
        ftCameraRecord.setArguments(bundle);
        return ftCameraRecord;
    }

    public static FtCameraRecord newInstance(EZCameraInfo cameraInfo, boolean isPlaybackPage) {
        FtCameraRecord ftCameraRecord = new FtCameraRecord();
        Bundle bundle = new Bundle();
        bundle.putParcelable(IntentConsts.EXTRA_CAMERA_INFO, cameraInfo);
        ftCameraRecord.setArguments(bundle);
        return ftCameraRecord;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.ezCameraInfo = (EZCameraInfo) arguments.getParcelable(IntentConsts.EXTRA_CAMERA_INFO);
        }
        this.queryDate.setValue(new Date());
        ((FtCameraRecordBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.camera.play.FtCameraRecord$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                FtCameraRecord.this.lambda$initView$1((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view) {
        int id = view.getId();
        if (id == R.id.iv_left) {
            MutableLiveData<Date> mutableLiveData = this.queryDate;
            mutableLiveData.setValue(getDayAfter(mutableLiveData.getValue(), -1));
        } else if (id != R.id.iv_right) {
            if (id != R.id.tv_date) {
                return;
            }
            CalendarDialog.asDefault().setTitle(ActivityUtils.getTopActivity().getString(R.string.select_date)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.camera.play.FtCameraRecord$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    FtCameraRecord.this.lambda$initView$0((Date) obj);
                }
            }).showDialog(this.activity);
        } else {
            if (this.isToday) {
                return;
            }
            MutableLiveData<Date> mutableLiveData2 = this.queryDate;
            mutableLiveData2.setValue(getDayAfter(mutableLiveData2.getValue(), 1));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(Date date) {
        this.queryDate.setValue(date);
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (ActCameraPlay) getActivity();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        super.startObserve();
        this.queryDate.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.camera.play.FtCameraRecord$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtCameraRecord.this.lambda$startObserve$2((Date) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Date date) {
        ((FtCameraRecordBinding) this.mViewBinding).setDate(getDateString(date));
        startQueryDeviceRecordFiles();
    }

    public String getDateString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        String format2 = simpleDateFormat.format(date);
        if (format2.equals(format)) {
            this.isToday = true;
            return format2 + ActivityUtils.getTopActivity().getResources().getString(R.string.today);
        }
        this.isToday = false;
        return format2;
    }

    public Date getDayAfter(Date date, int dayAfter) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        try {
            gregorianCalendar.setTime(date);
            gregorianCalendar.add(5, dayAfter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gregorianCalendar.getTime();
    }

    @Override // com.videogo.ui.querylist.QueryPlayBackListTaskCallback
    public void queryOnlyHasLocalFile() {
        hasShowListViewLine(false);
        stopQueryTask();
        QueryDeviceRecordFilesAsyncTask queryDeviceRecordFilesAsyncTask = new QueryDeviceRecordFilesAsyncTask(this.ezCameraInfo.getDeviceSerial(), this.ezCameraInfo.getCameraNo(), this.recordType, this);
        this.queryDeviceRecordFilesAsyncTask = queryDeviceRecordFilesAsyncTask;
        queryDeviceRecordFilesAsyncTask.setQueryDate(this.queryDate.getValue());
        this.queryDeviceRecordFilesAsyncTask.setOnlyHasLocal(true);
        this.queryDeviceRecordFilesAsyncTask.execute(String.valueOf(0));
    }

    @Override // com.videogo.ui.querylist.QueryPlayBackListTaskCallback
    public void queryOnlyLocalNoData() {
        LHomeLog.i(FtCameraRecord.class, "queryOnlyLocalNoData");
        ((FtCameraRecordBinding) this.mViewBinding).layoutLoad.setVisibility(8);
        ((FtCameraRecordBinding) this.mViewBinding).layoutEmpty.setVisibility(0);
    }

    @Override // com.videogo.ui.querylist.QueryPlayBackListTaskCallback
    public void querySuccessFromDevice(List<CloudPartInfoFileEx> cloudPartInfoFileExs, int position, List<CloudPartInfoFile> cloudPartInfoFile) {
        ((FtCameraRecordBinding) this.mViewBinding).layoutLoad.setVisibility(0);
        ((FtCameraRecordBinding) this.mViewBinding).layoutEmpty.setVisibility(8);
        hasShowListViewLine(true);
        ((FtCameraRecordBinding) this.mViewBinding).listRecord.setVisibility(0);
        StandardArrayAdapter standardArrayAdapter = this.mDeviceRecordsAdapter;
        if (standardArrayAdapter != null) {
            standardArrayAdapter.clear();
            this.mDeviceRecordsAdapter.addLocalFileExAll(cloudPartInfoFileExs);
            this.mDeviceRecordsAdapter.notifyDataSetChanged();
            int size = this.mDeviceRecordsAdapter.getCloudFileEx().size() - 2;
            ((FtCameraRecordBinding) this.mViewBinding).listRecord.smoothScrollToPositionFromTop(size > 0 ? size : 0, 100, 500);
            return;
        }
        this.mDeviceRecordsAdapter = new StandardArrayAdapter(getActivity(), R.id.layout, cloudPartInfoFileExs);
        this.mSectionAdapterForLocal = new SectionListAdapter(getActivity(), getLayoutInflater(), this.mDeviceRecordsAdapter, this.ezCameraInfo.getDeviceSerial(), null);
        ((FtCameraRecordBinding) this.mViewBinding).listRecord.setAdapter((ListAdapter) this.mSectionAdapterForLocal);
        ((FtCameraRecordBinding) this.mViewBinding).listRecord.setOnScrollListener(this.mSectionAdapterForLocal);
        ((FtCameraRecordBinding) this.mViewBinding).listRecord.setPinnedHeaderView(getLayoutInflater().inflate(R.layout.list_section, (ViewGroup) ((FtCameraRecordBinding) this.mViewBinding).listRecord, false));
        ((FtCameraRecordBinding) this.mViewBinding).listRecord.startAnimation();
        this.mSectionAdapterForLocal.setOnHikItemClickListener(this);
    }

    @Override // com.videogo.ui.querylist.QueryPlayBackListTaskCallback
    public void queryLocalNoData() {
        LHomeLog.i(FtCameraRecord.class, "queryLocalNoData");
        ((FtCameraRecordBinding) this.mViewBinding).layoutLoad.setVisibility(8);
        ((FtCameraRecordBinding) this.mViewBinding).layoutEmpty.setVisibility(0);
    }

    @Override // com.videogo.ui.querylist.QueryPlayBackListTaskCallback
    public void queryTaskOver(int type, int queryMode, int queryErrorCode, String detail) {
        if (type == 1) {
            if (this.activity.isRecordPage()) {
                ((BaseNormalActivity) ActivityUtils.getTopActivity()).dismissLoadingDialog();
            }
            LHomeLog.i(FtCameraRecord.class, "queryTaskOver: TYPE_LOCAL");
            this.queryDeviceRecordFilesAsyncTask = null;
        }
    }

    private void startQueryDeviceRecordFiles() {
        hasShowListViewLine(false);
        if (this.activity.isRecordPage()) {
            ((BaseNormalActivity) ActivityUtils.getTopActivity()).showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.ez_query_device_data));
        }
        stopQueryTask();
        QueryDeviceRecordFilesAsyncTask queryDeviceRecordFilesAsyncTask = new QueryDeviceRecordFilesAsyncTask(this.ezCameraInfo.getDeviceSerial(), this.ezCameraInfo.getCameraNo(), this.recordType, this);
        this.queryDeviceRecordFilesAsyncTask = queryDeviceRecordFilesAsyncTask;
        queryDeviceRecordFilesAsyncTask.setQueryDate(this.queryDate.getValue());
        this.queryDeviceRecordFilesAsyncTask.setOnlyHasLocal(true);
        this.queryDeviceRecordFilesAsyncTask.execute(String.valueOf(100000));
    }

    private void stopQueryTask() {
        QueryDeviceRecordFilesAsyncTask queryDeviceRecordFilesAsyncTask = this.queryDeviceRecordFilesAsyncTask;
        if (queryDeviceRecordFilesAsyncTask != null) {
            queryDeviceRecordFilesAsyncTask.cancel(true);
            this.queryDeviceRecordFilesAsyncTask.setAbort(true);
            this.queryDeviceRecordFilesAsyncTask = null;
        }
    }

    private void hasShowListViewLine(boolean isShow) {
        if (this.mViewBinding != 0) {
            if (isShow) {
                ((FtCameraRecordBinding) this.mViewBinding).listviewLine.setVisibility(0);
            } else {
                ((FtCameraRecordBinding) this.mViewBinding).listviewLine.setVisibility(4);
            }
        }
    }

    @Override // com.videogo.ui.querylist.SectionListAdapter.OnHikItemClickListener
    public void onHikItemClickListener(CloudPartInfoFile cloudFile, ClickedListItem clickedListItem) {
        this.activity.timeBucketUIInit(clickedListItem.getBeginTime(), clickedListItem.getEndTime());
        this.activity.currentClickItemFile = clickedListItem;
        this.activity.currentDeviceRecordInfo = null;
        if (cloudFile.isCloud()) {
            return;
        }
        this.activity.currentDeviceRecordInfo = new EZDeviceRecordFile();
        convertCloudPartInfoFile2EZDeviceRecordFile(this.activity.currentDeviceRecordInfo, cloudFile);
        this.mSectionAdapterForLocal.setSelection(cloudFile.getPosition());
        ((FtCameraRecordBinding) this.mViewBinding).listRecord.smoothScrollToPositionFromTop(clickedListItem.getPosition(), 100, 500);
        this.activity.startPlayback();
    }

    public void convertCloudPartInfoFile2EZDeviceRecordFile(EZDeviceRecordFile dst, CloudPartInfoFile src) {
        dst.setStartTime(Utils.convert14Calender(src.getStartTime()));
        dst.setStopTime(Utils.convert14Calender(src.getEndTime()));
    }
}