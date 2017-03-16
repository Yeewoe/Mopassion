package org.yeewoe.mopassion.asynctask;

import org.yeewoe.commonutils.android.async.MyAsyncTask;
import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopassion.app.common.adapter.MopaAdapter;
import org.yeewoe.mopassion.app.common.model.BaseLineVo;
import org.yeewoe.mopassion.app.common.view.interfaces.ITimberView;
import org.yeewoe.mopassion.constants.AppConstants;

/**
 * 列表使用的task
 * Created by wyw on 2016/4/9.
 */
public abstract class LineAsyncTask<T extends BaseLineVo> extends MyAsyncTask<Object, Integer, Callback.CallbackInfo<T>> {

    private boolean isRefresh;
    private MopaAdapter<T> adapter;
    private final ITimberView view;

    public LineAsyncTask(ITimberView iTimberView, MopaAdapter<T> adapter) {
        this.adapter = adapter;
        this.view = iTimberView;
    }

    /**
     * 该方法不要继承或随意修改。请继承{@link #doInBackgroundAfterInit(Object, long, int)}方法扩展
     */
    @Override protected final Callback.CallbackInfo<T> doInBackground(Object... params) {
        long prev = AppConstants.PREV_HOME;
        int count = AppConstants.COUNT_ALL;
        Object param = null;
        if (params != null) {
            if (params.length >= 1 && params[0] instanceof Boolean) {
                isRefresh = (boolean) params[0];
            }
            if (params.length >= 2) {
                param = params[1];
            }
            if (params.length >= 3) {
                prev = (long) params[2];
            }
            if (params.length >= 4) {
                count = (int) params[3];
            }
        }
        return doInBackgroundAfterInit(param, prev, count);
    }

    protected Callback.CallbackInfo<T> doInBackgroundAfterInit(Object param, long prev, int count) {
        return null;
    }

    @Override protected final void onPostExecute(Callback.CallbackInfo<T> tCallbackInfo) {
        if (!tCallbackInfo.bError) {
            if (isRefresh) {
                adapter.setAll(tCallbackInfo.mTs);
            } else {
                adapter.addAll(tCallbackInfo.mTs);
            }
        } else {
            view.showError(tCallbackInfo.errorCode);
        }
        onPostExecuteAfterInit(tCallbackInfo);
    }

    protected void onPostExecuteAfterInit(Callback.CallbackInfo<T> tCallbackInfo) {
    }
}
