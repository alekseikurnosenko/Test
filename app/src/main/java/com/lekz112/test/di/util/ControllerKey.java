package com.lekz112.test.di.util;

import com.bluelinelabs.conductor.Controller;

import java.lang.annotation.Target;

import dagger.MapKey;

import static java.lang.annotation.ElementType.METHOD;


@MapKey
@Target(METHOD)
public @interface ControllerKey {
    Class<? extends Controller> value();
}

