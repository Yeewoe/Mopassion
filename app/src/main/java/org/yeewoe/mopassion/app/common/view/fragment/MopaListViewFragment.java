package org.yeewoe.mopassion.app.common.view.fragment;

import android.os.Bundle;
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
import in.srain.cube.views.loadmore.LoadMoreContainerBase;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by wyw on 2016/4/26.
 */
public abstract class MopaListViewFragment<T extends LinePresenter> extends MopaFragment<T> implements PtrHandler, LoadMoreHandler, MopaAdapter.OnAdapterChangeListener {

    protected MopaAdapter mAdapterMain;
    @Nullable @Bind(R.id.mopa_listview) public MopaListView mListViewMain;
    @Bind(R.id.mopa_ptr) public MopaPtrFrameLayout mPtrMain;
    @Nullable @Bind(R.id.mopa_loadmore) public LoadMoreContainerBase mLoadMoreMain;

    @Override final protected void onCreateViewInit(Bundle savedInstanceState) {
        mPtrMain.setLastUpdateTimeRelateObject(this);
        mPtrMain.setPtrHandler(this);
        if (mLoadMoreMain != null) {
            mLoadMoreMain.setLoadMoreHandler(this);
        }
        mAdapterMain = getAdapter();
        mAdapterMain.setOnAdapterChangeListener(this);
        setAdapter();
        mAdapterMain.notifyDataSetChanged();
        onListViewCreateViewInit(savedInstanceState);
    }

    protected void setAdapter() {
        if (mListViewMain != null) {
            mListViewMain.setAdapter(mAdapterMain);
        }
    }

    /**
     * onCreateViewInit之后调用
     */
    protected abstract void onListViewCreateViewInit(Bundle savedInstanceState);

    protected abstract MopaAdapter getAdapter();

    /**
     * 如果请求有参数，需要重写
     */
    protected Object getLoadParam() {
        return null;
    }

    @Override protected void onBind() {
        mPtrMain.postDelayed(new Runnable() {
            @Override public void run() {
                mPtrMain.autoRefresh();
            }
        }, 100);
    }

    @Override public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
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
