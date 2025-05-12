package com.akshit.db.dao;

import com.akshit.db.entities.StoredUser;
import org.hibernate.SessionFactory;

public class UserDAO extends BaseDAO<StoredUser> {

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory, StoredUser.class);
    }

}
