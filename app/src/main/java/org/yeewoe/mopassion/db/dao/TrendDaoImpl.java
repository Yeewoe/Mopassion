package org.yeewoe.mopassion.db.dao;

import com.j256.ormlite.dao.Dao;

import org.yeewoe.mopassion.db.core.DaoFactory;
import org.yeewoe.mopassion.db.po.Trend;
import org.yeewoe.mopassion.utils.LogCore;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * 动态DAO 实现类
 * Created by wyw on 2016/4/5.
 */
public class TrendDaoImpl extends TrendDao {
    @Override public int update(Dao<?, Integer> dao, Trend trend, Trend exist) throws SQLException {
        if (trend == null) {
            LogCore.interfaceI("[update]trend == null!!!");
            return -1;
        }

        if (exist == null) {
            LogCore.interfaceI("[update]exist == null!!!");
            return -1;
        }

        Dao<Trend, Integer> trendDao = (Dao<Trend, Integer>) dao;
        return update(trend, exist, trendDao);
    }

    @Override public int insert(Dao<?, Integer> dao, Trend trend) throws SQLException {
        if (trend == null) {
            LogCore.interfaceI("[insert]trend == null!!!");
            return -1;
        }
        Dao<Trend, Integer> trendDao = (Dao<Trend, Integer>) dao;
        return insert(trend, trendDao);
    }

    @Override public void batchInsertOrUpdate(List<Trend> trends) {
        BatchTaskOptimize batchTask = new BatchTaskOptimize<Trend>() {
            @Override
            public boolean batchHandle(List<Trend> objs) {
                final List<Trend> cs = objs;
                try {
                    DaoFactory.getDao(Trend.class).callBatchTasks(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            for (Trend c : cs) {
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
        batchTask.splitObjectsAndBatchHandle(trends);
    }
}
