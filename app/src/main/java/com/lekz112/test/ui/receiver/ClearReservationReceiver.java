package com.lekz112.test.ui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lekz112.test.service.network.ReservationService;
import com.lekz112.test.ui.TestApplication;

import org.threeten.bp.Duration;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.AndroidInjection;

import static com.lekz112.test.di.ApplicationModule.CLEAR_RESERVATION;

public class ClearReservationReceiver extends BroadcastReceiver {

    private static final String TAG = ClearReservationReceiver.class.getSimpleName();

    @Inject
    @Named(CLEAR_RESERVATION)
    Duration clearReservationPeriod;
    @Inject
    ReservationService reservationService;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Clearing reservations");
        AndroidInjection.inject(this, context);
        reservationService.clearReservations().subscribe();

        TestApplication.scheduleAlarm(context, clearReservationPeriod);
    }

}
