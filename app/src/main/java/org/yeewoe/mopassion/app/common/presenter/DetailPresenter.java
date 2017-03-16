package org.yeewoe.mopassion.app.common.presenter;

import org.yeewoe.mopassion.app.common.view.interfaces.IDetailView;

/**
 * Created by wyw on 2016/4/26.
 */
public abstract class DetailPresenter extends MopaPresenter {
    // TODO 重构
    protected IDetailView detailView;

    public abstract void loadLocal(int id);

    public abstract void loadLocal(long sid);

    public abstract void loadNet(long sid);
}
