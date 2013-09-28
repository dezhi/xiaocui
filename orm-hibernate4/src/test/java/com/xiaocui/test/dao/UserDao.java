package com.xiaocui.test.dao;

import org.springframework.stereotype.Repository;

import com.xiaocui.dao.hibernate4.BaseDao;
import com.xiaocui.test.vo.User;

@Repository("userDao")
public class UserDao extends BaseDao<User> implements IUserDao {

}
