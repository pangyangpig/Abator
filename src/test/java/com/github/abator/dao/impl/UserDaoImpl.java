package com.github.abator.dao.impl;


public class UserDaoImpl extends com.github.mybatis.dao.BaseDao implements com.github.abator.dao.UserDao {

    public final String NAME_SPACE = this.getClass().getName();
    public String getNameSpace() {
        return NAME_SPACE;
    }

}