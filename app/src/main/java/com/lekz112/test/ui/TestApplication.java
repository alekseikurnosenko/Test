package com.lekz112.test.ui;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.lekz112.test.di.ApplicationComponent;
import com.lekz112.test.di.ApplicationModule;
import com.lekz112.test.di.DaggerApplicationComponent;
import com.lekz112.test.di.ServiceModule;
import com.lekz112.test.service.network.ReservationService;
import com.lekz112.test.service.network.RestReservationService;
import com.lekz112.test.service.network.StubReservationService;
import com.lekz112.test.ui.receiver.ClearReservationReceiver;

import org.threeten.bp.Duration;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasDispatchingActivityInjector;
import dagger.android.HasDispatchingBroadcastReceiverInjector;

public class TestApplication extends Application implements HasDispatchingActivityInjector,
        HasDispatchingBroadcastReceiverInjector {

    private final static String TAG = TestApplication.class.getSimpleName();

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;
    @Inject
    DispatchingAndroidInjector<BroadcastReceiver> receiverDispatchingAndroidInjector;

    public static void scheduleAlarm(Context context, Duration clearReservationPeriod) {
        Intent clearIntent = new Intent(context, ClearReservationReceiver.class);
        clearIntent.setPackage(context.getPackageName());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, clearIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        long timeToTrigger = clearReservationPeriod.plusMillis(System.currentTimeMillis()).toMillis();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            manager.set(AlarmManager.RTC_WAKEUP, timeToTrigger, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            manager.setExact(AlarmManager.RTC_WAKEUP, timeToTrigger, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeToTrigger, pendingIntent);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
        buildComponent().inject(this);
        //buildStubComponent().inject(this);
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public DispatchingAndroidInjector<BroadcastReceiver> broadcastReceiverInjector() {
        return receiverDispatchingAndroidInjector;
    }

    // TODO: Override during instrumentation testing
    protected ApplicationComponent buildComponent() {
        return DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    private ApplicationComponent buildStubComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .serviceModule(new ServiceModule() {
                    @Override
                    public ReservationService networkService(RestReservationService restNetworkService) {
                        return new StubReservationService();
                    }
                })
                .build();
    }

}
