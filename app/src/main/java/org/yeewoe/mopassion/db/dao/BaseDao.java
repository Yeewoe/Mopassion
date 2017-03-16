package org.yeewoe.mopassion.db.dao;

import org.yeewoe.mopassion.db.po.BasePo;

import java.sql.SQLException;

/**
 * Base dao
 *
 * @param <T>
 * @author wyw
 */
public interface BaseDao<T extends BasePo> {

    /**
     * 保存或者更新数据
     * 适用于: 表里面有Data_own列和serverId列的情况;
     * 备注不适用于: 联系人表(联系人表是共用的)
     *
     * @param t T
     * @param serverId long
     * @return  long
     * @throws SQLException
     */
    int insertOrUpdate(T t, long serverId) throws SQLException;

    /**
     * 根据serverId查询
     * 适用于: 表里面有Data_own列和serverId列的情况;
     * 备注不适用于: 联系人表(联系人表是共用的)
     *
     * @param clz Class
     * @param serverId long
     * @return T
     * @throws SQLException
     */
    T queryByServerId(Class<? extends Object> clz, long serverId) throws SQLException;

    /**
     * 根据id查询
     * 适用于: 表里面有Data_own列和id列的情况;
     *
     * @param clz Class
     * @param id int
     * @return T
     * @throws SQLException
     */
    T queryById(Class<? extends Object> clz, int id) throws SQLException;

    /**
     * 保存或者更新数据
     * 适用于: 表里面有Data_own列和id列的情况;
     * 备注不适用于: 联系人表(联系人表是共用的)
     *
     * @param t T
     * @param id int
     * @return long
     * @throws SQLException
     */
    long insertOrUpdate(T t, int id) throws SQLException;

    /**
     * 根据serverId 删除数据
     * 适用于: 表里面有Data_own列和id列的情况;
     * 备注不适用于: 联系人表(联系人表是共用的)
     *
     * @param clz   Class     目标对象
     * @param serverId 服务端Id
     * @return -1 : 表示参数错误或者，删除失败
     * 0 : 表示删除失败
     * 大于0 : 表示删除成功
     * @throws SQLException
     */
    int deleteByServerId(Class<? extends Object> clz, long serverId) throws SQLException;

    /**
     * 根据Id删除数据
     *
     * @param clz Class
     * @param id int
     * @return int
     * @throws SQLException
     */
    int deleteById(Class<? extends Object> clz, int id) throws SQLException;
}
