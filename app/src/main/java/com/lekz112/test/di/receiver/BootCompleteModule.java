package com.lekz112.test.di.receiver;

import android.content.BroadcastReceiver;

import com.lekz112.test.ui.receiver.BootCompleteReceiver;

import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.BroadcastReceiverKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = BootCompleteModule.BootCompleteSubcomponent.class)
public abstract class BootCompleteModule {

    @Binds
    @IntoMap
    @BroadcastReceiverKey(BootCompleteReceiver.class)
    abstract AndroidInjector.Factory<? extends BroadcastReceiver> receiverFactory(
            BootCompleteSubcomponent.Builder builder);


    @Subcomponent
    public interface BootCompleteSubcomponent extends AndroidInjector<BootCompleteReceiver> {

        @Subcomponent.Builder
        abstract class Builder extends AndroidInjector.Builder<BootCompleteReceiver> {
        }
    }

}
