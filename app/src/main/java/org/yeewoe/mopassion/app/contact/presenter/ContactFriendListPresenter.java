package org.yeewoe.mopassion.app.contact.presenter;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopassion.app.common.adapter.MopaAdapter;
import org.yeewoe.mopassion.app.common.presenter.LinePresenter;
import org.yeewoe.mopassion.app.contact.model.UserLineVo;
import org.yeewoe.mopassion.app.contact.view.iview.IContactFriendView;
import org.yeewoe.mopassion.asynctask.LineAsyncTask;
import org.yeewoe.mopassion.db.po.User;
import org.yeewoe.mopassion.mangers.UserDataMananger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * Created by wyw on 2016/4/9.
 */
public class ContactFriendListPresenter extends LinePresenter<UserLineVo> {
    private final IContactFriendView view;

    /**
     * @param firstPageCount 首页条数
     * @param nextPageCount  分页条数
     */
    public ContactFriendListPresenter(IContactFriendView view, MopaAdapter<UserLineVo> adapter, int firstPageCount, int nextPageCount) {
        super(adapter, firstPageCount, nextPageCount);
        this.view = view;
    }

    @Override protected LineAsyncTask<UserLineVo> initAsyncTask() {
        return new LineAsyncTask<UserLineVo>(view, adapter) {
            @Override protected Callback.CallbackInfo<UserLineVo> doInBackgroundAfterInit(Object param, long prev, int count) {
                Callback.CallbackInfo<User> resultInfo = userService.getFriendLineNet();
                Callback.CallbackInfo<UserLineVo> result = new Callback.CallbackInfo<>();
                if (resultInfo.bError) {
                    result.bError = true;
                    result.errorCode = resultInfo.errorCode;
                } else {
                    List<User> users = resultInfo.mTs;
                    result.mTs = UserLineVo.Convert.convert(users);
                }
                return result;
            }
        };
    }
}
