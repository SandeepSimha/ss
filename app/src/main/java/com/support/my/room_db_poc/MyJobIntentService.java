package com.support.my.room_db_poc;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by chers026 on 11/1/17.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobIntentService extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        //do my work
        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, EmployeeService.class);
        startService(intent);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
