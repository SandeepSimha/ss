package com.support.my.room_db_poc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.support.my.room_db_poc.db.entities.Employee;

import java.io.IOException;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chers026 on 11/5/17.
 */

public class RxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getEmployeeObservable()
                .subscribeOn(Schedulers.io())//hey RxJava do thios job on this thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Employee>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Employee employee) {
                        //After Api call success like PostExceute

                        Log.e("RxActivity", "onNext = " + employee);
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("RxActivity", "onError = " + e);

                    }

                    @Override
                    public void onComplete() {
                        //An observable completed with out an error @onCompleted() get called.

                    }
                });
    }

    @Nullable
    private Employee getEmployee() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://gist.githubusercontent.com/SandeepSimha/7020de3ab1b678a38915fafae1be009d/raw/2292fc15c3771869ac137a44eb70c9f3f445f0c9/batani")
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            Log.e("RxActivity", "Response = " + response.body().charStream());
        }
        return null;
    }

    public Observable<Employee> getEmployeeObservable() {
        return Observable.defer(new Callable<ObservableSource<? extends Employee>>() {
            @Override
            public ObservableSource<? extends Employee> call() throws Exception {
                return Observable.just(getEmployee());
            }
        });
    }
}