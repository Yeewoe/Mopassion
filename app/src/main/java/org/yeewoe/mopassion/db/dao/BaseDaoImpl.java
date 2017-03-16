package org.yeewoe.mopassion.db.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

import org.yeewoe.mopassion.mangers.UserDataMananger;
import org.yeewoe.mopassion.db.core.DaoFactory;
import org.yeewoe.mopassion.db.core.DbConstants;
import org.yeewoe.mopassion.db.po.BasePo;
import org.yeewoe.mopassion.db.utils.DaoUtils;

import java.sql.SQLException;
import java.util.List;

public abstract class BaseDaoImpl<T extends BasePo> implements BaseDao<T> {

    @SuppressWarnings("unchecked")
    @Override
    public T queryByServerId(Class<? extends Object> clz, long sid) throws SQLException {
        if (clz == null) {
            throw new IllegalArgumentException("T is null");
        }
        if (sid <= 0) {
            throw new IllegalArgumentException(
                    "serverId must > 0; serverId = " + sid);
        }
        Dao<?, Integer> dao = DaoFactory.getDao(clz);
        QueryBuilder<?, Integer> builder = dao.queryBuilder();

        Where<?, Integer> where = builder.where();
        where.eq(DbConstants.TableInfo.Base.SID, sid);
        DaoUtils.setAndWhere(where);

        List<T> ts = (List<T>) builder.query();
        if (ts != null && ts.size() >= 1) {
            return ts.get(0);
        } else {
            return null;
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public T queryById(Class<? extends Object> clz, int id) throws SQLException {

        if (clz == null) {
            throw new IllegalArgumentException("T is null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "id must > 0; id = " + id);
        }

        Dao<?, Integer> dao = DaoFactory.getDao(clz);

        return (T) dao.queryForId(id);
    }

    @Override
    public int insertOrUpdate(T t, long sid) throws SQLException {
        if (t == null) {
            throw new IllegalArgumentException("T is null");
        }

        if (sid < 0) {
            throw new IllegalArgumentException(
                    "sid must > 0; sid = " + sid);
        }

        Dao<?, Integer> dao = DaoFactory.getDao(t.getClass());
        if (sid == 0) {
            //sid 为0,表示新增,
            //备注: 主键必须保证为0
            int id = insert(dao, t);
            t.setId(id);
            return id;
        } else {
            T exist = queryByServerId(t.getClass(), sid);
            if (exist == null) {
                //insert
                int id = insert(dao, t);
                t.setId(id);
                return id;
            } else {
                //update
                int id = update(dao, t, exist);
                t.setId(id);
                return id;
            }
        }
    }

    @Override
    public long insertOrUpdate(T t, int id) throws SQLException {
        if (t == null) {
            throw new IllegalArgumentException("T is null");
        }

        if (id < 0) {
            throw new IllegalArgumentException(
                    "serverId must >= 0; serverId = " + id);
        }

        Dao<?, Integer> dao = DaoFactory.getDao(t.getClass());
        if (id == 0) {
            return insert(dao, t);
        }

        T exist = queryById(t.getClass(), id);
        if (exist == null) {
            //insert
            return insert(dao, t);
        } else {
            //update
            return update(dao, t, exist);
        }
    }

    /**
     * 更新方法
     * 备注, t 中必须包含id
     *
     * @param dao
     * @param t
     * @param exist
     * @return
     * @throws SQLException
     */
    public abstract int update(Dao<?, Integer> dao, T t, T exist) throws SQLException;

    /**
     * 保存方法
     * 备注: t 中必须确保id = 0 ;
     *
     * @param dao
     * @param t
     * @return
     * @throws SQLException
     */
    public abstract int insert(Dao<?, Integer> dao, T t) throws SQLException;

    @Override
    public int deleteByServerId(Class<? extends Object> clz, long sid) throws SQLException {
        if (clz == null) {
            throw new IllegalArgumentException("T is null");
        }
        if (sid <= 0) {
            throw new IllegalArgumentException(
                    "sid must > 0; sid = " + sid);
        }
        Dao<?, Integer> dao = DaoFactory.getDao(clz);
        DeleteBuilder<?, Integer> builder = dao.deleteBuilder();
        Where<?, Integer> where = builder.where();
        where.eq(DbConstants.TableInfo.Base.SID, sid);
        DaoUtils.setAndWhere(where);

        return builder.delete();
    }

    ;

    @Override
    public int deleteById(Class<? extends Object> clz, int id) throws SQLException {
        if (clz == null) {
            throw new IllegalArgumentException("T is null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "id must > 0; id = " + id);
        }

        Dao<?, Integer> dao = DaoFactory.getDao(clz);

        return dao.deleteById(id);
    }

    public void deleteLogicallyByServerId(Class<? extends Object> clz, long sid) throws SQLException {
        if (clz == null) {
            throw new IllegalArgumentException("T is null");
        }
        if (sid <= 0) {
            throw new IllegalArgumentException(
                    "sid must > 0; sid = " + sid);
        }
        Dao<?, Integer> dao = DaoFactory.getDao(clz);
        UpdateBuilder<?, Integer> builder = dao.updateBuilder();
        Where<?, Integer> where = builder.where();
        where.eq(DbConstants.TableInfo.Base.SID, sid);
        DaoUtils.setAndWhere(where);

        builder.updateColumnValue(DbConstants.TableInfo.Base.IS_DELETED, true);
    }

    protected  <T extends BasePo> int update(T message, T exist, Dao<T, Integer> momentDao) throws SQLException {
        long meUid = UserDataMananger.getInstance().getMeUid();
        if (meUid > 0) {
            message.setOwnerId(meUid);
        }
        message.setId(exist.getId());
        momentDao.update(message);
        return message.getId();
    }

    protected <T extends BasePo> int insert(T message, Dao<T, Integer> messageDao) throws SQLException {
        long meUid = UserDataMananger.getInstance().getMeUid();
        if (meUid > 0) {
            message.setOwnerId(meUid);
        }
        return messageDao.createIfNotExists(message).getId();
    }

}
