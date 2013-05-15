package com.github.mybatis.dao;

import com.github.commons.DataPage;

public class UserDao extends BaseDao implements IDao {

    public DataPage<User> getPageUserList(User user, int page, int pageSize) {

        String sql = "select count(1) from user";
        DataPage<User> dp = new DataPage<User>(1, page, pageSize);
        return null;
    }

    public int getTotalUserCount() {

        return this.countForObject(new User());
    }

    @Override
    public String getNameSpace() {
        // TODO Auto-generated method stub
        return this.getClass().getName();
    }

}
