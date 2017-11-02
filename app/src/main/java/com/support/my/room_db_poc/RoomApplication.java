package com.support.my.room_db_poc;

import android.app.Application;

/**
 * Created by chers026 on 10/30/17.
 */

public class RoomApplication extends Application {

    private static RoomApplication INSTANCE = null;

    public RoomApplication() {
        //
    }

    public static RoomApplication getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RoomApplication();
        }
        return INSTANCE;
    }
}
