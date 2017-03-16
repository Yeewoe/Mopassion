package org.yeewoe.mopassion.app.common.view.acitivty;

import android.support.annotation.Nullable;
import android.view.View;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.adapter.MopaAdapter;
import org.yeewoe.mopassion.app.common.presenter.LinePresenter;
import org.yeewoe.mopassion.app.common.view.widget.MopaListView;
import org.yeewoe.mopassion.app.common.view.widget.MopaLoadMoreListViewContainer;
import org.yeewoe.mopassion.app.common.view.widget.MopaPtrFrameLayout;

import butterknife.Bind;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 列表基类Activity
 * Created by wyw on 2016/5/7.
 */
public abstract class MopaListViewActivity<T extends LinePresenter> extends MopaActivity<T> implements PtrHandler, LoadMoreHandler, MopaAdapter.OnAdapterChangeListener {
    protected MopaAdapter mAdapterMain;
    @Bind(R.id.mopa_listview) public MopaListView mListViewMain;
    @Bind(R.id.mopa_ptr) public MopaPtrFrameLayout mPtrMain;
    @Nullable @Bind(R.id.mopa_loadmore) public MopaLoadMoreListViewContainer mLoadMoreMain;

    @Override protected void initSupperView() {
        mPtrMain.setLastUpdateTimeRelateObject(this);
        mPtrMain.setPtrHandler(this);
        if (mLoadMoreMain != null) {
            mLoadMoreMain.setLoadMoreHandler(this);
        }
        mAdapterMain = getAdapter();
        mAdapterMain.setOnAdapterChangeListener(this);
        mListViewMain.setAdapter(mAdapterMain);
        mAdapterMain.notifyDataSetChanged();
    }

    @Override protected void bindData() {
        mPtrMain.postDelayed(new Runnable() {
            @Override public void run() {
                mPtrMain.autoRefresh();
            }
        }, 100);
    }

    protected abstract MopaAdapter getAdapter();

    /**
     * 如果请求有参数，需要重写
     */
    protected Object getLoadParam() {
        return null;
    }

    @Override public final boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, mListViewMain, header);
    }

    @Override public final void onRefreshBegin(PtrFrameLayout frame) {
        mPresenter.loadFirstPage(getLoadParam());
    }

    @Override public final void onLoadMore(LoadMoreContainer loadMoreContainer) {
        mPresenter.loadNextPage(getLoadParam());
    }

    @Override public final void afterSetAll() {
        mPtrMain.refreshComplete();
        if (mLoadMoreMain != null) {
            mLoadMoreMain.loadMoreFinish(false, true);
        }
    }

    @Override public final void afterAddAll(boolean emptyResult, boolean hasMore) {
        if (mLoadMoreMain != null) {
            mLoadMoreMain.loadMoreFinish(emptyResult, hasMore);
        }
    }

    @Override public void onResume() {
        super.onResume();
        mPtrMain.refreshComplete();
    }
}

