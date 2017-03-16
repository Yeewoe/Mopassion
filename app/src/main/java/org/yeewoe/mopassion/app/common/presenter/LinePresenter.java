package org.yeewoe.mopassion.app.common.presenter;

import org.yeewoe.mopassion.app.common.adapter.MopaAdapter;
import org.yeewoe.mopassion.app.common.model.BaseLineVo;
import org.yeewoe.mopassion.app.common.service.UserService;
import org.yeewoe.mopassion.app.common.view.interfaces.IActivityView;
import org.yeewoe.mopassion.asynctask.LineAsyncTask;
import org.yeewoe.mopassion.constants.AppConstants;

/**
 * 列表类型的Presenter基类 <br />
 * 封装了列表逻辑，只需实现{@link LinePresenter#initAsyncTask()}即可
 * 目前提供逻辑有：
 * 1.刷新列表
 * 2.列表分页
 * 3.追加
 * 4.数据自动销毁
 *
 * Created by wyw on 2016/4/4.
 */
public abstract class LinePresenter<T extends BaseLineVo> extends MopaPresenter {

    private int firstPageCount;
    private int nextPageCount;

    protected UserService userService;
    protected MopaAdapter<T> adapter;
    protected LineAsyncTask<T> asyncTask;


    /**
     * @param firstPageCount 首页条数
     * @param nextPageCount 分页条数
     */
    public LinePresenter(MopaAdapter<T> adapter, int firstPageCount, int nextPageCount) {
        init(adapter, firstPageCount, nextPageCount);
    }

    /**
     * @param firstPageCount 首页条数
     * @param nextPageCount 分页条数
     */
    public LinePresenter(IActivityView activityView, MopaAdapter<T> adapter, int firstPageCount, int nextPageCount) {
        super(activityView);
        init(adapter, firstPageCount, nextPageCount);
    }

    private void init(MopaAdapter<T> adapter, int firstPageCount, int nextPageCount) {
        this.userService = new UserService();
        this.adapter = adapter;
        this.firstPageCount = firstPageCount;
        this.nextPageCount = nextPageCount;
    }

    @Override public void onDestroy() {
        if (asyncTask != null) {
            asyncTask.cancel(true);
        }
    }

    protected abstract LineAsyncTask<T> initAsyncTask();

    /**
     * 新增到第一条
     */
    public void addLast(T data) {
       adapter.add(data);
    }

    /**
     * 刷新其中一条
     */
    public void update(T data) {
        adapter.update(data);
    }

    /**
     * 加载首页
     */
    public void loadFirstPage(Object param) {
        final long prev = AppConstants.PREV_HOME;
        final int count = firstPageCount;

        loadData(param, prev, count, true);
    }

    /**
     * 加载下一页
     */
    public void loadNextPage(Object param) {
        if (!isPageMode()) {
            adapter.end();
            return ;
        }

        final long prev = adapter.getStartId();
        final int count = nextPageCount;

        loadData(param, prev, count, false);
    }

    /**
     * 加载当前页
     */
    public void loadAllCurrentPage(Object param) {
        final long prev = AppConstants.PREV_HOME;
        int count = adapter.getCount();
        if (count == 0) {
            count = firstPageCount;
        }

        loadData(param, prev, count, true);
    }

    /**
     * 是否采用分页模式，默认为true，若为false，则分页始终返回empty状态
     */
    public boolean isPageMode() {
        return true;
    }

    private void loadData(Object param, final long prev, final int count, final boolean isRefresh) {
        // TODO 看看这里是否需要停掉之前的task
//        if (asyncTask != null) {
//            asyncTask.cancel(true);
//        }
        asyncTask = initAsyncTask();
        if (asyncTask != null) {
            asyncTask.execute(isRefresh, param, prev, count);
        }
    }


}
