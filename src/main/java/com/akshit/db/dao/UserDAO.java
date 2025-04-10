package com.akshit.db.dao;

import com.akshit.db.entities.UserEntity;
import org.hibernate.SessionFactory;

public class UserDAO extends BaseDAO<UserEntity> {
    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory, UserEntity.class);
    }

}
