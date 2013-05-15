package com.github.abator.dao;

import com.github.abator.BaseTestCase;
import com.github.abator.dao.impl.UserDaoImpl;

public class UserDaoTest extends BaseTestCase {

    public void test_count() {

        UserDaoImpl userDao = this.session.getMapper(UserDaoImpl.class);

    }
}
