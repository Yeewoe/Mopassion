package org.yeewoe.mopassion.app.nearby.view;

import android.os.Bundle;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.adapter.MopaAdapter;
import org.yeewoe.mopassion.app.common.view.fragment.MopaGridViewFragment;
import org.yeewoe.mopassion.app.maintab.view.apdaters.MeSpaceAdapter;
import org.yeewoe.mopassion.app.maintab.view.iview.IMeSpaceView;
import org.yeewoe.mopassion.app.nearby.presenter.UserSpacePresenter;
import org.yeewoe.mopassion.mangers.UserDataMananger;
import org.yeewoe.mopassion.template.media.TMediaImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acc on 2016/7/27.
 */
public class UserSpaceFragment extends MopaGridViewFragment<UserSpacePresenter> implements IMeSpaceView {

    private static final String ARG_PHOTO_LIST = "key_uid";
    private ArrayList<TMediaImage> mPhotoList;

    public UserSpaceFragment() {

    }

    public static UserSpaceFragment newInstantce(ArrayList<TMediaImage> photoList) {
        UserSpaceFragment fragment = new UserSpaceFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PHOTO_LIST, photoList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override protected MopaAdapter getAdapter() {
        return new MeSpaceAdapter(getContext());
    }

    @Override protected int getViewRootResId() {
        return R.layout.common_image_wall;
    }

    @Override protected UserSpacePresenter getPresenter() {
        return new UserSpacePresenter(this, mAdapterMain);
    }

    @Override protected void onGridViewCreateViewInit(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mPhotoList = arguments.getParcelableArrayList(ARG_PHOTO_LIST);
        }
        if (mPhotoList == null) {
            mPhotoList = new ArrayList<>();
        }
    }

    @Override protected Object getLoadParam() {
        return mPhotoList;
    }
}
