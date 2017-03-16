package org.yeewoe.mopassion.app.contact.view;

import android.content.Context;
import android.os.Bundle;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.adapter.MopaAdapter;
import org.yeewoe.mopassion.app.common.view.fragment.MopaFragment;
import org.yeewoe.mopassion.app.common.view.fragment.MopaListViewFragment;
import org.yeewoe.mopassion.app.common.view.widget.MopaListView;
import org.yeewoe.mopassion.app.contact.presenter.ContactFriendListPresenter;
import org.yeewoe.mopassion.app.contact.view.adapter.UserLineAdapter;
import org.yeewoe.mopassion.app.contact.view.iview.IContactFriendView;
import org.yeewoe.mopassion.constants.ContactConstants;

import butterknife.Bind;

/**
 * 联系人-朋友 TAB
 */
public class ContactFriendFragment extends MopaListViewFragment<ContactFriendListPresenter> implements IContactFriendView {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ContactFriendFragment() {
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
    public static ContactFriendFragment newInstance(String param1, String param2) {
        ContactFriendFragment fragment = new ContactFriendFragment();
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
        return R.layout.fragment_contact_friend;
    }

    @Override protected void onListViewCreateViewInit(Bundle savedInstanceState) {

    }

    @Override protected MopaAdapter getAdapter() {
        return new UserLineAdapter(getContext());
    }

    @Override protected ContactFriendListPresenter getPresenter() {
        return new ContactFriendListPresenter(this, mAdapterMain, ContactConstants.COUNT_USER_LIST_FIRST_PAGE, ContactConstants.COUNT_USER_LIST_NEXT_PAGE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
