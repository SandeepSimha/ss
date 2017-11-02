package com.support.my.room_db_poc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.support.v4.content.LocalBroadcastManager;

import com.support.my.room_db_poc.db.entities.Employee;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Example implementation of a JobIntentService.
 */

public class MyJobService extends JobIntentService {

    /**
     * Unique job ID for this service.
     */
    static final int JOB_ID = 1000;

    /**
     * Convenience method for enqueuing work in to this service.
     */
    static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, MyJobService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        int syncResult = HttpsURLConnection.HTTP_BAD_GATEWAY;
        Bundle bundle = new Bundle();

        ArrayList<Employee> employeeList = new ArrayList<>();

        try {
            //get Response of Employee List.
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray = jsonObject.getJSONArray("Employees");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo_inside = jsonArray.getJSONObject(i);

                Employee employee = Employee
                        .getBuilder()
                        .setUserId(jo_inside.getString("userId"))
                        .setJobTitleName(jo_inside.getString("jobTitleName"))
                        .setFirstName(jo_inside.getString("firstName"))
                        .setLastName(jo_inside.getString("lastName"))
                        .setPreferredFullName(jo_inside.getString("preferredFullName"))
                        .setEmployeeCode(jo_inside.getString("employeeCode"))
                        .setRegion(jo_inside.getString("region"))
                        .setPhoneNumber(jo_inside.getString("phoneNumber"))
                        .setEmailAddress(jo_inside.getString("emailAddress"))
                        .build();

                employeeList.add(employee);
            }
            bundle.putParcelableArrayList("Model", employeeList);

        } catch (Exception ignored) {

        } finally {
            Intent result = new Intent();
            result.setAction("com.support.my.room_db_poc.db.STATUS");
            result.putExtra("empList", employeeList);
            LocalBroadcastManager.getInstance(this).sendBroadcast(result);
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getApplication().getAssets().open("employee.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}