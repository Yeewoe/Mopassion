package org.yeewoe.mopassion.app.auth.presenter;

import org.greenrobot.eventbus.EventBus;
import org.yeewoe.mopassion.app.auth.view.iview.IRegisterNickView;
import org.yeewoe.mopassion.app.auth.view.iview.IRegisterPasswordView;
import org.yeewoe.mopassion.app.auth.view.iview.IRegisterView;
import org.yeewoe.mopassion.app.common.presenter.MopaPresenter;
import org.yeewoe.mopassion.event.KeyValueChangeEvent;
import org.yeewoe.mopassion.event.TabChangeEvent;

/**
 * Created by acc on 2016/3/29.
 */
public class RegisterPasswordPresenter extends MopaPresenter {
    private IRegisterPasswordView view;
    public RegisterPasswordPresenter(IRegisterPasswordView view) {
        this.view = view;
    }

    public void next(String password) {
        KeyValueChangeEvent keyValueChangeEvent = new KeyValueChangeEvent();
        keyValueChangeEvent.put(IRegisterView.DATA_KEY_PASSWORD, password);
        EventBus.getDefault().post(keyValueChangeEvent);
        EventBus.getDefault().post(new TabChangeEvent(2));
    }

    @Override
    public void onDestroy() {

    }

}
