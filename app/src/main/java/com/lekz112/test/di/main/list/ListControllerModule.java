package com.lekz112.test.di.main.list;

import com.bluelinelabs.conductor.Controller;
import com.lekz112.test.di.util.ControllerKey;
import com.lekz112.test.ui.main.list.CustomersController;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = ListControllerSubcomponent.class)
public abstract class ListControllerModule {

    @Binds
    @IntoMap
    @ControllerKey(CustomersController.class)
    abstract AndroidInjector.Factory<? extends Controller> controllerFactory(
            ListControllerSubcomponent.Builder builder);
}
