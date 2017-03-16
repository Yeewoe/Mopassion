package org.yeewoe.mopassion.db.dao;

import android.support.annotation.NonNull;

import org.yeewoe.mopassion.db.po.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * User基类DAO
 * Created by wyw on 2016/4/1.
 */
public abstract class UserDao extends BaseDaoImpl<User> {

    /**
     * 批量插入更新
     * @throws SQLException
     */
    public abstract void batchInsertOrUpdate(List<User> userList) throws SQLException;

    /**
     * 查询某个人
     */
    public abstract User query(long sid) throws SQLException;

    /**
     * 查询我的所有人
     * @throws SQLException
     */
    @NonNull public abstract List<User> query() throws SQLException;

    /**
     * 批量查询
     */
    @NonNull public abstract List<User> query(List<Long> sids) throws SQLException;
}
