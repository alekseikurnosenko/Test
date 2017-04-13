package com.lekz112.test.di.main.list;

import com.lekz112.test.ui.main.list.CustomersController;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent
public interface ListControllerSubcomponent extends AndroidInjector<CustomersController> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<CustomersController> {
    }
}
