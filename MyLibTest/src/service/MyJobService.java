package service;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : MyJobService
 * @Description : 잡서비스(롤리팝 21버전 이상부터 지원)
 * @since 2015-06-29.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {


    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(conf.Log.LOG_NAME, this.getClass() + " @@ onStartJob ");
        Toast.makeText(this, "작업 스케줄러", Toast.LENGTH_LONG).show();
        new JobServiceTask(this).execute(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }


    private static class JobServiceTask extends AsyncTask<JobParameters, Void, JobParameters> {

        private final MyJobService myJobService;

        public JobServiceTask(MyJobService jobService) {
            myJobService = jobService;
        }

        @Override
        protected JobParameters doInBackground(JobParameters... params) {
            Log.d(conf.Log.LOG_NAME, this.getClass() + " @@ doInBackground no");
            SystemClock.sleep(5000);
            return params[0];
        }

        @Override
        protected void onPostExecute(JobParameters jobParameters) {
            if (Build.VERSION.SDK_INT > 21) {
                Log.d(conf.Log.LOG_NAME, this.getClass() + " @@  onPostExecute");
                        myJobService.jobFinished(jobParameters, false);
            }else{
                Log.d(conf.Log.LOG_NAME, this.getClass() + " @@  onPostExecute no");
            }
        }
    }
}
