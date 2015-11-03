package com.kiki.View.Activity.TestA;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.kiki.View.service.MyJobService;
import com.kiki.android.Utils.KLog;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : JobInfoActivity
 * @Description : jobInfo 와 jbo scheduler 이용 클래스
 * @since 2015-06-29.
 */
public class JobInfoActivity extends Activity {

    private final int JOB_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.jobinfo_main_layout);

        if(Build.VERSION.SDK_INT > 21 ){
            KLog.d(this.getClass().getSimpleName(), this.getClass() + " @@ JobInfo 실행 ");
            JobInfo job = new JobInfo.Builder(JOB_ID, new ComponentName(this, MyJobService.class))
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                    .setOverrideDeadline(5000)
                    .setRequiresCharging(true)
                    .build();

            JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            jobScheduler.schedule(job);
        }else{
            KLog.d(this.getClass().getSimpleName(),this.getClass() + " @@ JobInfo 실행 안되요~ ");
        }


    }
}
