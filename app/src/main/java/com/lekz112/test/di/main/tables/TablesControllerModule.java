package com.lekz112.test.di.main.tables;

import com.bluelinelabs.conductor.Controller;
import com.lekz112.test.di.util.ControllerKey;
import com.lekz112.test.ui.main.tables.TablesController;

import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = TablesControllerModule.TablesControllerSubcomponent.class)
public abstract class TablesControllerModule {

    @Binds
    @IntoMap
    @ControllerKey(TablesController.class)
    abstract AndroidInjector.Factory<? extends Controller> controllerFactory(
            TablesControllerSubcomponent.Builder builder);

    @Subcomponent
    public interface TablesControllerSubcomponent extends AndroidInjector<TablesController> {

        @Subcomponent.Builder
        abstract class Builder extends AndroidInjector.Builder<TablesController> {
        }
    }
}
