package com.lekz112.test.ui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lekz112.test.ui.TestApplication;

import org.threeten.bp.Duration;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.AndroidInjection;

import static com.lekz112.test.di.ApplicationModule.CLEAR_RESERVATION;

public class BootCompleteReceiver extends BroadcastReceiver {

    private static final String TAG = BootCompleteReceiver.class.getSimpleName();

    @Inject
    @Named(CLEAR_RESERVATION)
    Duration clearReservationPeriod;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Setting alarm");
        AndroidInjection.inject(this, context);

        TestApplication.scheduleAlarm(context, clearReservationPeriod);
    }

}
