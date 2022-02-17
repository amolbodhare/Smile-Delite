package com.smile.delite.other;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.os.Handler;
import android.util.Log;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;

import org.json.JSONArray;

import java.util.ArrayList;

public class JobSchedulerHelper extends JobService
{
    //private DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        if (jobParameters.getJobId() == 314749)
            uploadData(jobParameters);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }

    private void uploadData(final JobParameters jobParameters) {
        /*JSONArray visitorJsonArray = new JSONArray();
        final ArrayList<Visitor> visitorList = databaseHelper.getVisitorsWithStatus0();

        // Visitor v;
        for (Visitor v : visitorList) {

            //v = visitorList.get(i);

            Json json = new Json();

            json.addInt(Visitor.COLUMN_ID, v.getId());
            json.addString(Visitor.COLUMN_USER_TYPE, v.getUsertype());
            json.addInt(Visitor.COLUMN_USER_ID, v.getUserid());
            json.addInt(Visitor.COLUMN_USER_ASSIGN_ID, v.getUserassignid());
            json.addInt(Visitor.COLUMN_USER_ASSIGN_UNIQUE_CODE, v.getUserassignuniquecode());
            json.addInt(Visitor.COLUMN_EVENT_ID, v.getEventid());
            json.addInt(Visitor.COLUMN_EVENT_DAY_ID, v.getEventdayid());
            json.addInt(Visitor.COLUMN_EVENT_DAY_HALL_ID, v.getEventdayhallid());
            json.addInt(Visitor.COLUMN_EVENT_DAY_HALLTIME_ID, v.getEventdayhalltimeid());
            json.addString(Visitor.COLUMN_BARCODE, v.getBarcode());
            json.addBool(Visitor.COLUMN_SYNCSTATUS, v.isSyncstatus());
            json.addString(Visitor.COLUMN_TIMESTAMP, v.getTimestamp());

            visitorJsonArray.put(json);
        }

        Json json = new Json();

        if (visitorJsonArray.length() == 0) {
            restartJobScheduler(jobParameters);
            return;
        }
        json.addJSONArray("data_array", visitorJsonArray);

        RequestModel requestModel = RequestModel.newRequestModel("uploadreport");
        requestModel.addJSON(P.data, json);

        Api.newApi(this, P.baseUrl).addJson(requestModel)
                .setMethod(Api.POST)
                .onError(new Api.OnErrorListener() {
                    @Override
                    public void onError() {
                        restartJobScheduler(jobParameters);
                        H.log("ErrorIsIn", "JobSchedulerHelper");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {
                        if (json.getInt(P.status) == 1) {
                            if (databaseHelper.changeStatusTo1(visitorList) > 0) {
                                restartJobScheduler(jobParameters);
                            }
                        } else {
                            restartJobScheduler(jobParameters);
                            //H.showMessage(JobSchedulerHelper.this, json.getString(P.msg));
                        }

                    }
                })
                .run("hitUploadReport");*/
    }

    Handler handler = new Handler();
    Runnable runnable;

    private void restartJobScheduler(final JobParameters jobParameters) {
        if (runnable != null)
            handler.removeCallbacks(runnable);
        runnable = new Runnable() {
            @Override
            public void run() {
                jobFinished(jobParameters, false);
                ComponentName componentName = new ComponentName(JobSchedulerHelper.this, JobSchedulerHelper.class);
                JobInfo info = new JobInfo.Builder(314749, componentName)
                        //.setRequiresCharging(true)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .setPersisted(true)
                        //.setPeriodic(321)
                        .build();

                JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                scheduler.cancelAll();

                int resultCode = scheduler.schedule(info);
                if (resultCode == JobScheduler.RESULT_SUCCESS) {
                    Log.e("startJobScheduler", "Job scheduled");
                } else {
                    Log.e("startJobScheduler", "Job scheduling failed");
                }
            }
        };
        handler.postDelayed(runnable, 9876);
    }
}
