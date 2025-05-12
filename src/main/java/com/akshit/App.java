package com.akshit;

import com.akshit.db.entities.StoredUser;
import com.akshit.exceptions.NewWavesExceptionMapper;
import com.akshit.filters.AuthFilter;
import com.akshit.utils.AppModule;
import com.akshit.utils.MyGuiceOverrideBundle;
import com.akshit.utils.TemplateHealthCheck;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.google.inject.Injector;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class App extends Application<AppConfiguration> {

    private final SwaggerBundle<AppConfiguration> swaggerBundle = new SwaggerBundle<>() {
        @Override
        protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(AppConfiguration configuration) {
            return configuration.getSwagger();
        }
    };
    private final HibernateBundle<AppConfiguration> hibernate = new HibernateBundle<>(
            StoredUser.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(AppConfiguration configuration) {
            return configuration.getDatabase();
        }
    };

    final GuiceBundle guiceBundle = GuiceBundle.builder()
            .enableAutoConfig(getClass().getPackage()
                    .getName()) // scans your project package
            .modules(new AppModule(hibernate)) // your custom Guice module
            .bundles(new MyGuiceOverrideBundle())
            .build();


    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addBundle(swaggerBundle);
        bootstrap.addBundle(hibernate);
        bootstrap.addBundle(guiceBundle);
    }

    @Override
    public void run(AppConfiguration appConfiguration,
                    Environment environment) {
        Injector injector = guiceBundle.getInjector();
        environment.getObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        environment.jersey()
                .register(new NewWavesExceptionMapper());
        environment.jersey()
                .register(injector.getInstance(AuthFilter.class));

        TemplateHealthCheck healthCheck = new TemplateHealthCheck("%s");
        environment.healthChecks()
                .register("template", healthCheck);
    }
}