package com.lekz112.test.ui.main;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.lekz112.test.R;
import com.lekz112.test.di.util.HasDispatchingControllerInjector;
import com.lekz112.test.ui.main.list.CustomersController;
import com.lekz112.test.ui.util.Titleable;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;

public class MainActivity extends AppCompatActivity implements HasDispatchingControllerInjector {

    @Bind(R.id.main_container)
    ViewGroup mainContainer;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Inject
    DispatchingAndroidInjector<Controller> controllerInjector;

    private Router router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        // TODO: normally we would also have navigation drawer and as a result, menu button that could double as back
        // when showing detail fragment

        // TODO: consider adding tablet support - showing two controllers side-by-side

        router = Conductor.attachRouter(this, mainContainer, savedInstanceState);
        router.addChangeListener(new ControllerChangeHandler.ControllerChangeListener() {
            @Override
            public void onChangeStarted(@Nullable Controller to, @Nullable Controller from, boolean isPush,
                                        @NonNull ViewGroup container, @NonNull ControllerChangeHandler handler) {

            }

            @Override
            public void onChangeCompleted(@Nullable Controller to, @Nullable Controller from, boolean isPush,
                                          @NonNull ViewGroup container, @NonNull ControllerChangeHandler handler) {
                if (to instanceof Titleable) {
                    Titleable titleable = (Titleable) to;
                    toolbar.setTitle(titleable.getTitle());
                }
            }
        });
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(new CustomersController()));
        }
    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed();
        }
    }

    @Override
    public DispatchingAndroidInjector<Controller> controllerInjector() {
        return controllerInjector;
    }
}
