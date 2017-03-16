package org.yeewoe.mopassion.app.common.view.interfaces;

/**
 * View接口，用于有TAB切换功能的页面
 * Created by wyw on 2016/3/26.
 */
public interface ITabView extends IMopaView {
    void changeTab(int position);
    void changeToPre();
    void changeToNext();
}
