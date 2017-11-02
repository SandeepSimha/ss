package com.support.my.room_db_poc.db;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.WorkerThread;

import com.support.my.room_db_poc.RoomApplication;
import com.support.my.room_db_poc.db.dao.EmployeeDao;
import com.support.my.room_db_poc.db.entities.Employee;

/**
 * Created by chers026 on 10/30/17.
 */

@android.arch.persistence.room.Database(entities = {Employee.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public static AppDatabase getInstance() {
        if (instance == null || !instance.isOpen()) {
            instance = Room.databaseBuilder(RoomApplication.getInstance(), AppDatabase.class, "RoomDBPoc").build();
        }
        return instance;
    }

    @WorkerThread
    public void clearAllTables() {
        instance.employeeDao().deleteAll();
    }

    public abstract EmployeeDao employeeDao();
}
