package org.yeewoe.mopassion.app.common.view.fragment;

import android.os.Bundle;
import android.view.View;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.presenter.LinePresenter;
import org.yeewoe.mopassion.app.common.view.widget.MopaGridView;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by wyw on 2016/7/16.
 */
public abstract class MopaGridViewFragment<T extends LinePresenter> extends MopaListViewFragment<T> {
    @Bind(R.id.mopa_gridview) public MopaGridView mGridViewMain;

    @Override protected void setAdapter() {
        mGridViewMain.setAdapter(mAdapterMain);
    }

    @Override public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, mGridViewMain, header);
    }

    @Override protected final void onListViewCreateViewInit(Bundle savedInstanceState) {
        onGridViewCreateViewInit(savedInstanceState);
    }

    protected abstract void onGridViewCreateViewInit(Bundle savedInstanceState);
}
