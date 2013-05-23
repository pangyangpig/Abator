package com.github.abator.dao.impl;

import org.springframework.dao.DataAccessException;

public class UserDaoImpl extends com.github.mybatis.dao.BaseDao implements com.github.abator.dao.UserDao {

    public final String NAME_SPACE = this.getClass().getName();

    public String getNameSpace() {
        return NAME_SPACE;
    }

    @Override
    public <T> T find(String keyId) throws DataAccessException {

        return null;
    }

}