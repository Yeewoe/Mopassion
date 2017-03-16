package org.yeewoe.mopassion.db.dao;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.commonutils.common.utils.JsonUtil;
import org.yeewoe.mopassion.db.core.DaoFactory;
import org.yeewoe.mopassion.db.core.DbConstants;
import org.yeewoe.mopassion.db.po.Media;
import org.yeewoe.mopassion.db.po.User;
import org.yeewoe.mopassion.db.utils.DaoUtils;
import org.yeewoe.mopassion.utils.LogCore;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * User DAO实现类
 * Created by wyw on 2016/4/1.
 */
public class UserDaoImpl extends UserDao {
    @Override
    public int update(Dao<?, Integer> dao, User user, User exist) throws SQLException {
        if (user == null) {
            LogCore.interfaceI("[update]user == null!!!");
            return -1;
        }

        if (exist == null) {
            LogCore.interfaceI("[update]exist == null!!!");
            return -1;
        }

        Dao<User, Integer> userDao = (Dao<User, Integer>) dao;
        userToJson(user);
        return update(user, exist, userDao);
    }


    @Override
    public int insert(Dao<?, Integer> dao, User user) throws SQLException {
        if (user == null) {
            LogCore.interfaceI("[insert]user == null!!!");
            return -1;
        }
        Dao<User, Integer> userDao = (Dao<User, Integer>) dao;
        userToJson(user);
        return insert(user, userDao);
    }


    @Override
    public void batchInsertOrUpdate(List<User> users) throws SQLException {
        BatchTaskOptimize batchTask = new BatchTaskOptimize<User>() {
            @Override
            public boolean batchHandle(List<User> objs) {
                final List<User> cs = objs;
                try {
                    DaoFactory.getDao(User.class).callBatchTasks(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            for (User c : cs) {
                                if (c != null) {
                                    try {
                                        insertOrUpdate(c, c.getSid());
                                    } catch (SQLException e) {
                                        LogCore.interfaceI("insertOrUpdate", e);
                                    }
                                }
                            }
                            return true;
                        }
                    });
                } catch (Exception e) {
                    LogCore.interfaceI("callBatchTasks", e);
                }
                return true;
            }
        };
        batchTask.splitObjectsAndBatchHandle(users);
    }

    @Override
    public User query(long sid) throws SQLException {
        if (sid <= 0) {
            throw new IllegalArgumentException(
                    "serverId must > 0; serverId = " + sid);
        }
        Dao<?, Integer> dao = DaoFactory.getDao(User.class);
        QueryBuilder<?, Integer> builder = dao.queryBuilder();

        Where<?, Integer> where = builder.where();
        where.eq(DbConstants.TableInfo.Base.SID, sid);
        DaoUtils.setAndWhere(where);

        List<User> ts = (List<User>) builder.query();
        jsonToUser(ts);
        if (ts != null && ts.size() >= 1) {
            return ts.get(0);
        } else {
            return null;
        }
    }

    @Override @NonNull public List<User> query() throws SQLException {
        Dao<User, Integer> dao = (Dao<User, Integer>) DaoFactory.getDao(User.class);
        QueryBuilder<User, Integer> query = dao.queryBuilder();
        Where<User, Integer> where = query.where();
        DaoUtils.setAndWhere(where);
        DaoUtils.setAndExist(where);
        List<User> result = query.query();
        jsonToUser(result);
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }

    @Override @NonNull public List<User> query(List<Long> sids) throws SQLException {
        Dao<User, Integer> dao = (Dao<User, Integer>) DaoFactory.getDao(User.class);
        QueryBuilder<User, Integer> query = dao.queryBuilder();
        Where<User, Integer> where = query.where();
        where.in(DbConstants.TableInfo.Base.SID, sids);
        DaoUtils.setAndWhere(where);
        DaoUtils.setAndExist(where);
        List<User> result = query.query();
        jsonToUser(result);
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }


    private void jsonToUser(List<User> users) {
        if (Checks.check(users)) {
            for (User user : users) {
                jsonToUser(user);
            }
        }
    }

    private void jsonToUser(User user) {
        if (user != null) {
            String jsonHeads = user.getJsonHeads();
            user.setHeads((List<Media>) new Gson().fromJson(jsonHeads, new TypeToken<List<Media>>() {}.getType()));
        }
    }

    private void userToJson(User user) {
        if (user != null) {
            List<Media> heads = user.getHeads();
            user.setJsonHeads(JsonUtil.toJson(heads));
        }
    }
}
