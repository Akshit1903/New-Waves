package com.akshit.utils;

import com.akshit.filters.AuthFilter;
import ru.vyarus.dropwizard.guice.module.installer.bundle.GuiceyBootstrap;
import ru.vyarus.dropwizard.guice.module.installer.bundle.GuiceyBundle;

public class MyGuiceOverrideBundle implements GuiceyBundle {

    @Override
    public void initialize(GuiceyBootstrap bootstrap) {
        bootstrap.disableExtensions(AuthFilter.class);
    }
}
