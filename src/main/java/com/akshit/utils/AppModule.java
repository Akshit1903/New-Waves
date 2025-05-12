package com.akshit.utils;

import com.akshit.AppConfiguration;
import com.akshit.db.dao.UserDAO;
import com.akshit.services.GcpAccessTokenService;
import com.akshit.services.UserService;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.dropwizard.hibernate.HibernateBundle;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AppModule extends AbstractModule {

    HibernateBundle<AppConfiguration> hibernate;

    @Provides
    @Singleton
    UserDAO getUserDAO() {
        return new UserDAO(hibernate.getSessionFactory());
    }

    @Provides
    @Singleton
    UserService getUserService(UserDAO userDAO) {
        return new UserService(userDAO);
    }

    @Provides
    @Singleton
    GcpAccessTokenService getGcpTokenService() {
        return new GcpAccessTokenService();
    }

}
