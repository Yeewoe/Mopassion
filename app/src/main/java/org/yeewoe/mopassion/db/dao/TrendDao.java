package org.yeewoe.mopassion.db.dao;

import org.yeewoe.mopassion.db.po.Trend;

import java.util.List;

/**
 * 动态DAO基类
 * Created by wyw on 2016/4/5.
 */
public abstract class TrendDao extends BaseDaoImpl<Trend> {
    public abstract void batchInsertOrUpdate(List<Trend> trends);
}
