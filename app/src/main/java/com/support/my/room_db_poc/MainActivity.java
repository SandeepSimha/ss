package com.support.my.room_db_poc;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.JobIntentService;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.support.my.room_db_poc.databinding.MyActivityBinding;

public class MainActivity extends AppCompatActivity {

    //private GenericReceiver receiver;
    static final int PICK_CONTACT_REQUEST = 1;  // The request code

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_layout);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new FirstLevelFragment());
        transaction.addToBackStack(null);
        transaction.commit();

        //Before binding
        /*setContentView(R.layout.activity_main);*/

        //After Data binding
        //MyActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.my_activity);
        //binding.textViewName.setText("Manager");

        //receiver = new GenericReceiver(new Handler());
        //receiver.setReceiver(this);


        /*Intent intent = new Intent(Intent.ACTION_SYNC, null, this, EmployeeService.class);
        //intent.putExtra("receiver", receiver);
        startService(intent);*/

       /* Intent intent = new Intent(Intent.ACTION_SYNC, null, this, MyJobService.class);
        MyJobService.enqueueWork(this, intent);*/

        ComponentName serviceComponent = new ComponentName(this, MyJobIntentService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(1 * 1000); // wait at least
        builder.setOverrideDeadline(3 * 1000); // maximum delay

        JobScheduler jobScheduler = (JobScheduler) this.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter("com.example.android.threadsample.STATUS");
        //registerReceiver(dataInitializationReceiver, intentFilter);
        LocalBroadcastManager.getInstance(this).registerReceiver(dataInitializationReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //unregisterReceiver(dataInitializationReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(dataInitializationReceiver);
    }

    private final BroadcastReceiver dataInitializationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Toast.makeText(getApplicationContext(), "Action: " + intent.getData(), Toast.LENGTH_SHORT).show();
            //pickContact();

            //userPermission();
        }
    };

    private void userPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    private void pickContact() {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK);
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);// Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)

                Log.e("onActivityResult", "DATA: " + data.getData());

                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                cursor.moveToFirst();
                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);
                cursor.close();

                Log.e("onActivityResult", "Phone number: " + number);

            }
        }
    }
}
