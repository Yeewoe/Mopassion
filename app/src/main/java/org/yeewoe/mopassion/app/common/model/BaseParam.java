package org.yeewoe.mopassion.app.common.model;

import org.yeewoe.mopassion.db.po.BasePo;
import org.yeewoe.mopassion.mangers.UserDataMananger;

/**
 * 公共参数
 * Created by wyw on 2016/4/8.
 */
public abstract class BaseParam<T extends BasePo> {
    public abstract T toPo();

    /**
     * 公共Po初始化代码
     */
    protected void initPo(T po) {
        po.setSid(System.currentTimeMillis()); // Sid 设置为当前时间戳
        po.setCreateBy(UserDataMananger.getInstance().getMeUid()); // 创建人为自己
    }
}
