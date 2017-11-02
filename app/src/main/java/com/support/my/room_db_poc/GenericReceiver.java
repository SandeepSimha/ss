package com.support.my.room_db_poc;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by chers026 on 10/30/17.
 */

public class GenericReceiver extends ResultReceiver {
    private Receiver receiver;

    public GenericReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        receiver.onReceiveResult(resultCode, resultData);
    }

    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }
}
