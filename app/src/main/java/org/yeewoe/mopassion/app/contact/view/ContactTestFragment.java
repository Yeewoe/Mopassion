package org.yeewoe.mopassion.app.contact.view;

import android.content.Context;
import android.os.Bundle;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.view.fragment.MopaFragment;
import org.yeewoe.mopassion.app.common.view.widget.MopaListView;
import org.yeewoe.mopassion.app.contact.presenter.ContactTestListPresenter;
import org.yeewoe.mopassion.app.contact.view.adapter.UserLineAdapter;
import org.yeewoe.mopassion.app.contact.view.iview.IContactFriendView;
import org.yeewoe.mopassion.constants.ContactConstants;

import butterknife.Bind;

/**
 * 联系人-Test TAB
 */
public class ContactTestFragment extends MopaFragment<ContactTestListPresenter> implements IContactFriendView {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private UserLineAdapter mAdapterMain;
    @Bind(R.id.listview_main) MopaListView mListViewMain;

    public ContactTestFragment() {
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
    public static ContactTestFragment newInstance(String param1, String param2) {
        ContactTestFragment fragment = new ContactTestFragment();
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
        return R.layout.fragment_contact_test;
    }

    @Override
    protected void onCreateViewInit(Bundle savedInstanceState) {
        mAdapterMain = new UserLineAdapter(getContext());
        mListViewMain.setAdapter(mAdapterMain);
        mAdapterMain.notifyDataSetChanged();
    }

    @Override protected ContactTestListPresenter getPresenter() {
        return new ContactTestListPresenter(this, mAdapterMain, ContactConstants.COUNT_USER_LIST_FIRST_PAGE, ContactConstants.COUNT_USER_LIST_NEXT_PAGE);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override protected void onBind() {
        mPresenter.loadFirstPage(null);
    }
}
