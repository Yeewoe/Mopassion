package org.yeewoe.mopassion.app.common.view.interfaces;

import org.yeewoe.mopassion.app.common.model.BaseVo;

/**
 * 详情页面使用View
 * Created by wyw on 2016/4/26.
 */
public interface IDetailView<T extends BaseVo> extends IActivityView {
    void initDetail(T data);
}
