package com.akshit;

import com.akshit.db.entities.UserEntity;
import com.akshit.utils.AppModule;
import com.akshit.utils.TemplateHealthCheck;
import com.akshit.utils.exceptions.NewWavesExceptionMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.jakarta.rs.base.JsonParseExceptionMapper;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class App extends Application<AppConfiguration> {
    private final SwaggerBundle<AppConfiguration> swaggerBundle = new SwaggerBundle<AppConfiguration>() {
        @Override
        protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(AppConfiguration configuration) {
            return configuration.getSwagger();
        }
    };
    private final HibernateBundle<AppConfiguration> hibernate = new HibernateBundle<AppConfiguration>(
            UserEntity.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(AppConfiguration configuration) {
            return configuration.getDatabase();
        }
    };


    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        super.initialize(bootstrap);
        final GuiceBundle guiceBundle = GuiceBundle.builder()
                .enableAutoConfig(getClass().getPackage().getName()) // scans your project package
                .modules(new AppModule(hibernate)) // your custom Guice module
                .build();

        bootstrap.addBundle(swaggerBundle);
        bootstrap.addBundle(hibernate);
        bootstrap.addBundle(guiceBundle);
    }

    @Override
    public void run(AppConfiguration appConfiguration, Environment environment) {
        environment.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        environment.jersey().register(new NewWavesExceptionMapper());
        TemplateHealthCheck healthCheck = new TemplateHealthCheck("%s");
        environment.healthChecks().register("template", healthCheck);
    }
}