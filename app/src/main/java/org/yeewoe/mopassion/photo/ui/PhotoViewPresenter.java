package org.yeewoe.mopassion.photo.ui;

import org.yeewoe.mopassion.app.common.presenter.MopaPresenter;

/**
 * Created by wyw on 2016/4/20.
 */
public class PhotoViewPresenter extends MopaPresenter {

    private final IPhotoView view;

    public PhotoViewPresenter(IPhotoView view) {
        this.view = view;
    }

    @Override public void onDestroy() {

    }
}
