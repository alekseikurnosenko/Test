package com.lekz112.test.di.receiver;

import android.content.BroadcastReceiver;

import com.lekz112.test.ui.receiver.ClearReservationReceiver;

import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.BroadcastReceiverKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = ClearReservationsModule.ClearReservationsSubcomponent.class)
public abstract class ClearReservationsModule {

    @Binds
    @IntoMap
    @BroadcastReceiverKey(ClearReservationReceiver.class)
    abstract AndroidInjector.Factory<? extends BroadcastReceiver> receiverFactory(
            ClearReservationsSubcomponent.Builder builder);


    @Subcomponent
    public interface ClearReservationsSubcomponent extends AndroidInjector<ClearReservationReceiver> {

        @Subcomponent.Builder
        abstract class Builder extends AndroidInjector.Builder<ClearReservationReceiver> {
        }
    }

}