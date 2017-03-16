package org.yeewoe.mopassion.app.contact.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.view.fragment.MopaFragment;
import org.yeewoe.mopassion.app.contact.presenter.ContactFollowerAndFansListPresenter;
import org.yeewoe.mopassion.app.contact.view.iview.IContactFriendView;
import org.yeewoe.mopassion.utils.IntentManager;

import butterknife.OnClick;

/**
 * 联系人-关注和粉丝 TAB
 */
public class ContactFollowerAndFansFragment extends MopaFragment<ContactFollowerAndFansListPresenter> implements IContactFriendView {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ContactFollowerAndFansFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeFragment.
     */
    public static ContactFollowerAndFansFragment newInstance(String param1, String param2) {
        ContactFollowerAndFansFragment fragment = new ContactFollowerAndFansFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected int getViewRootResId() {
        return R.layout.fragment_contact_follower_and_fans;
    }

    @Override protected void onCreateViewInit(Bundle savedInstanceState) {

    }

    @Override protected ContactFollowerAndFansListPresenter getPresenter() {
        return new ContactFollowerAndFansListPresenter(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick(R.id.rl_follower_item) void onFollowerItemClick(View view) {
        IntentManager.Contact.intentToFollowerList(getContext());
    }

    @OnClick(R.id.rl_fans_item) void onFansItemClick(View view) {
        IntentManager.Contact.intentToFansList(getContext());
    }
}
