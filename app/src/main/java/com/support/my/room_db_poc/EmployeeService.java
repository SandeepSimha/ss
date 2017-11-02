package com.support.my.room_db_poc;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.support.my.room_db_poc.db.AppDatabase;
import com.support.my.room_db_poc.db.entities.Employee;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by chers026 on 10/30/17.
 */

public class EmployeeService extends IntentService {

    private HttpURLConnection urlConnection;
    private AppDatabase dbRoom;
    private ArrayList<Employee> employeeList;

    public EmployeeService() {
        super(EmployeeService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        int syncResult = HttpsURLConnection.HTTP_BAD_GATEWAY;
        ResultReceiver receiver = null;
        Bundle bundle = new Bundle();

        employeeList = new ArrayList<>();
        dbRoom = AppDatabase.getInstance();
        //dbRoom.clearAllTables();

        try {
            assert intent != null;
            receiver = intent.getParcelableExtra("receiver");

            /*urlConnection = (HttpURLConnection) new URL("///").openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("x-Device-Id", "343535");
            urlConnection.setRequestProperty("x-App-Version", "1.1.0");

            syncResult = urlConnection.getResponseCode();
            String response = convertStreamToString(urlConnection.getInputStream());*/

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

            //dbRoom.employeeDao().insertAll(employeeList);
            //Log.d("AppDatabase", "employeeList = " + dbRoom.employeeDao().getAll());

        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            /*IntentFilter filter = new IntentFilter("com.pycitup.BroadcastReceiver");

            MyReceiver myReceiver = new MyReceiver();
            registerReceiver(myReceiver, filter);*/

            Intent result = new Intent();
            result.setAction("com.example.android.threadsample.STATUS");
            result.putExtra("empList", employeeList);
            //sendBroadcast(result);
            LocalBroadcastManager.getInstance(this).sendBroadcast(result);

            /*if (receiver != null) {
                receiver.send(syncResult, bundle);
            }*/
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

    public static String convertStreamToString(InputStream is) {
        try {
            return new java.util.Scanner(is).useDelimiter("\\A").next();
        } catch (java.util.NoSuchElementException e) {
            return "";
        }
    }
}