package org.yeewoe.mopassion.db.dao;

import org.yeewoe.mopassion.db.po.Message;
import org.yeewoe.mopassion.db.po.SendStatus;

import java.sql.SQLException;
import java.util.List;

/**
 * Message基类DAO
 * Created by wyw on 2016/4/1.
 */
public abstract class MessageDao extends BaseDaoImpl<Message> {
    /**
     * 批量插入更新
     */
    public abstract void batchInsertOrUpdate(List<Message> messages);

    /**
     * 删除本地的一条消息
     * @throws SQLException
     */
    public abstract void delete(int id) throws SQLException;

    /**
     * 删除本地的一条消息
     * @throws SQLException
     */
    public abstract void delete(long sid) throws SQLException;

    /**
     * 逻辑删除本地的一条消息
     * @throws SQLException
     */
    public abstract void deleteLogically(long sid) throws SQLException;

    /**
     * 查询自己的所有聊天记录
     * @throws SQLException
     */
    public abstract List<Message> queryAll() throws SQLException;

    /**
     * 查询与某人的聊天记录
     * @throws SQLException
     */
    public abstract List<Message> queryAll(long toUid) throws SQLException;

    /**
     * 查询最后一条聊天记录
     * @throws SQLException
     */
    public abstract Message queryLast() throws SQLException;

    /**
     * 更新发送状态
     * @throws SQLException
     */
    public abstract void updateStatus(long serverId, SendStatus sendStatus) throws SQLException;

    /**
     * 更新voice的buffer路径
     * @param id
     * @param buffer
     */
    public abstract void updateVoiceBuffer(int id, String buffer) throws SQLException;
}
