package org.yeewoe.mopassion.app.contact.presenter;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopassion.app.common.adapter.MopaAdapter;
import org.yeewoe.mopassion.app.common.presenter.LinePresenter;
import org.yeewoe.mopassion.app.contact.model.UserLineVo;
import org.yeewoe.mopassion.app.contact.view.iview.IContactFriendView;
import org.yeewoe.mopassion.asynctask.LineAsyncTask;
import org.yeewoe.mopassion.db.po.User;
import org.yeewoe.mopassion.mangers.UserDataMananger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * Created by wyw on 2016/4/9.
 */
public class ContactTestListPresenter extends LinePresenter<UserLineVo> {
    private final IContactFriendView view;

    /**
     * @param firstPageCount 首页条数
     * @param nextPageCount  分页条数
     */
    public ContactTestListPresenter(IContactFriendView view, MopaAdapter<UserLineVo> adapter, int firstPageCount, int nextPageCount) {
        super(adapter, firstPageCount, nextPageCount);
        this.view = view;
    }

    @Override protected LineAsyncTask<UserLineVo> initAsyncTask() {
        return new LineAsyncTask<UserLineVo>(view, adapter) {
            @Override protected Callback.CallbackInfo<UserLineVo> doInBackgroundAfterInit(Object param, long prev, int count) {

                // TODO 好友的ID列表暂时是写死的，这里后面需要通过接口获取
                Set<Long> userIds = new HashSet<>();
                long meUid = UserDataMananger.getInstance().getMeUid();
                for (long i = 100; i < 200; ++i) {
                    if (i != meUid) { // 不显示自己
                        userIds.add(i);
                    }
                }

                List<User> users = userService.get(userIds, true, true);
                Callback.CallbackInfo<UserLineVo> result = new Callback.CallbackInfo<>();
                result.mTs = UserLineVo.Convert.convert(users);
                return result;
            }
        };
    }
}
