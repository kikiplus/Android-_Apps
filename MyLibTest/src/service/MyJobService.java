package service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.Toast;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name :
 * @Description :
 * @since 2015-06-29.
 */
public class MyJobService extends JobService {


    @Override
    public boolean onStartJob(JobParameters params) {
        Toast.makeText(this,"작업 스케줄러",Toast.LENGTH_LONG).show();
        new JobServiceTask(this).execute(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    private static class JobServiceTask extends AsyncTask<JobParameters, Void, JobParameters>{

        private final MyJobService myJobService;

        public JobServiceTask(MyJobService jobService){
            myJobService = jobService;
        }

        @Override
        protected JobParameters doInBackground(JobParameters... params) {
            SystemClock.sleep(5000);
            System.out.println("5초 뒤 실행");
            return params[0];
        }

        @Override
        protected void onPostExecute(JobParameters jobParameters) {
            myJobService.jobFinished(jobParameters, false);
        }
    }
}
