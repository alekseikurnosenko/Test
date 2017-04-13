package com.lekz112.test.di.util;

import com.bluelinelabs.conductor.Controller;

import android.app.Activity;

import dagger.android.DispatchingAndroidInjector;

import static dagger.internal.Preconditions.checkNotNull;

public class ControllerInjection {

    public static void inject(Controller controller) {
        checkNotNull(controller, "controller is null");
        Activity activity = controller.getActivity();
        if (!(activity instanceof HasDispatchingControllerInjector)) {
            throw new RuntimeException(
                    String.format(
                            "%s does not implement %s",
                            activity.getClass().getCanonicalName(),
                            HasDispatchingControllerInjector.class.getCanonicalName()));
        }

        DispatchingAndroidInjector<Controller> controllerInjector =
                ((HasDispatchingControllerInjector) activity).controllerInjector();
        checkNotNull(
                controllerInjector,
                "%s.controllerInjector() returned null",
                activity.getClass().getCanonicalName());

        controllerInjector.inject(controller);
    }

}
