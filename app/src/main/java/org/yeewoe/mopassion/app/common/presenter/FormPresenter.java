package org.yeewoe.mopassion.app.common.presenter;

import org.yeewoe.mopassion.app.common.view.interfaces.IActivityView;

/**
 * 表单类型的Presenter基类
 * 目前提供逻辑有：
 * 1.表单提交
 * 2.表单取消
 *
 * Created by wyw on 2016/4/8.
 */
public abstract class FormPresenter<T> extends MopaPresenter {
    public FormPresenter() {
    }

    public FormPresenter(IActivityView view) {
        super(view);
    }

    public abstract void submit(T param);

    public abstract void cancelSubmit();
}
