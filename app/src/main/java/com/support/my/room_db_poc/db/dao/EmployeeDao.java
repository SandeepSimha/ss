package com.support.my.room_db_poc.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;

import com.support.my.room_db_poc.db.entities.Employee;

import java.util.List;

/**
 * Created by chers026 on 10/30/17.
 */
@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
@Dao
public interface EmployeeDao {

    @Insert
    void insertAll(List<Employee> entitiesToInsert);

    @Query("SELECT * FROM Employee")
    List<Employee> getAll();

    @Query("DELETE FROM Employee")
    void deleteAll();
}
