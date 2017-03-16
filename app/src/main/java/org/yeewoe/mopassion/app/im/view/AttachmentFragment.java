package org.yeewoe.mopassion.app.im.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import org.yeewoe.commonutils.common.utils.DensityUtil;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.presenter.MopaPresenter;
import org.yeewoe.mopassion.app.common.view.fragment.MopaFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author wyw
 *         聊天界面内点击附件按钮弹出窗口。
 */
public class AttachmentFragment extends MopaFragment {

    public static final String TAG = "AttachmentFragment";
    private static final String ADJUST_TO_KEYBORAD = "ADJUST_TO_KEYBORAD";

    OnChatAttachTypeChooseListener listener;
    private ViewPager mPager;
    private LayoutInflater mInflater;
    private CirclePageIndicator mIndicator;
    private boolean mHidePhone;
    private boolean mHideMission;
    private boolean mDisabledPhone;

    private List<AttachWrapper> items;
    private ExecutorService service;
    private AttachmentPagerAdapter mAdapter;

    /**
     *
     * @param hidePhone  是否隐藏电话
     * @param hideMission 是否隐藏任务
     * @param disabledPhone 是否让 电话不可用。前提是 电话没有隐藏，如果隐藏了 此参数 无效
     * @return
     */
    public static AttachmentFragment newInstance(boolean hidePhone,boolean hideMission,boolean disabledPhone) {
        Log.i(TAG,"getInstance:"+disabledPhone);
        try {
            AttachmentFragment fragment = new AttachmentFragment();
            Bundle mBundle = new Bundle();
            mBundle.putBoolean("hidePhone", hidePhone);
            mBundle.putBoolean("hideMission",hideMission);
            mBundle.putBoolean("disabledPhone",disabledPhone);
            fragment.setArguments(mBundle);
            return fragment;
        } catch (StackOverflowError e) {
            return new AttachmentFragment();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHidePhone = getArguments().getBoolean("hidePhone");
        mHideMission = getArguments().getBoolean("hideMission");
        if(!mDisabledPhone) {
            mDisabledPhone = getArguments().getBoolean("disabledPhone");
        }
        Log.i(TAG," onCreate:"+mDisabledPhone);
    }

    public void initAttachWrapper() {
        items = new ArrayList<>();
        AttachWrapper wrapper1 = new AttachWrapper();
        wrapper1.attachType = AttachType.PICTURE;
        wrapper1.drawableSid = R.drawable.expand_img;
        wrapper1.attachName = getString(R.string.picture);
        items.add(wrapper1);

        AttachWrapper wrapper2 = new AttachWrapper();
        wrapper2.attachType = AttachType.CAMERA;
        wrapper2.drawableSid = R.drawable.expand_photo;
        wrapper2.attachName = getString(R.string.camera);
        items.add(wrapper2);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (OnChatAttachTypeChooseListener) activity;
    }

    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.view_expression_all, container,
                false);
        mView.findViewById(R.id.express_pager).getLayoutParams().height = (int) DensityUtil.dp2Pixel(getContext().getResources(), 205);
        initAttachWrapper();
        init(mView);
        return mView;
    }

    @Override
    protected int getViewRootResId() {
        return 0;
    }

    @Override
    protected void onCreateViewInit(Bundle savedInstanceState) {

    }

    @Override
    protected MopaPresenter getPresenter() {
        return null;
    }

    public void computeViewsHeight(ViewPager mPager) {

        mPager.getLayoutParams().height = AttachHeightCompute.getAttachHeight(getResources());

    }

    public void init(View view) {

        mPager = (ViewPager) view.findViewById(R.id.express_pager);
        computeViewsHeight(mPager);
        mIndicator = (CirclePageIndicator) view
                .findViewById(R.id.express_pager_indicator);
        mAdapter = new AttachmentPagerAdapter();
        mInflater = LayoutInflater.from(getActivity());
        mPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mPager);
    }

    public enum AttachType {
        PICTURE, CAMERA
    }

    public interface OnChatAttachTypeChooseListener {
        public void onTypeChoose(AttachType mAttachType);
    }

    private class AttachmentPagerAdapter extends PagerAdapter implements
            OnClickListener {

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mInflater.inflate(R.layout.view_attach_item, null);
            if (position == 1) {
                view.setVisibility(View.INVISIBLE);
                return view;
            }
            List<LinearLayout> layous = new ArrayList<>();
            LinearLayout mItem1 = (LinearLayout) view
                    .findViewById(R.id.item_row1);
            layous.add(mItem1);
            LinearLayout mItem2 = (LinearLayout) view
                    .findViewById(R.id.item_row2);
            layous.add(mItem2);
            LinearLayout mItem3 = (LinearLayout) view
                    .findViewById(R.id.item_row3);
            layous.add(mItem3);
            LinearLayout mItem4 = (LinearLayout) view
                    .findViewById(R.id.item_row4);
            layous.add(mItem4);
            LinearLayout mItem5 = (LinearLayout) view
                    .findViewById(R.id.item_row5);
            layous.add(mItem5);
            LinearLayout mItem6 = (LinearLayout) view
                    .findViewById(R.id.item_row6);
            layous.add(mItem6);
            LinearLayout mItem7 = (LinearLayout) view
                    .findViewById(R.id.item_row7);
            layous.add(mItem7);
            LinearLayout mItem8 = (LinearLayout) view
                    .findViewById(R.id.item_row8);
            layous.add(mItem8);

            List<ImageView> imgs = new ArrayList<>();
            imgs.add((ImageView)view.findViewById(R.id.img_enable_row1));
            imgs.add((ImageView)view.findViewById(R.id.img_enable_row2));
            imgs.add((ImageView)view.findViewById(R.id.img_enable_row3));
            imgs.add((ImageView)view.findViewById(R.id.img_enable_row4));
            imgs.add((ImageView)view.findViewById(R.id.img_enable_row5));
            imgs.add((ImageView)view.findViewById(R.id.img_enable_row6));
            imgs.add((ImageView)view.findViewById(R.id.img_enable_row7));
            imgs.add((ImageView)view.findViewById(R.id.img_enable_row8));

            for(int i=0;i<layous.size();i++) {
                LinearLayout layout = layous.get(i);
                ImageView img = imgs.get(i);
                if(items!=null && i<items.size()) {
                    try {
                        AttachWrapper wrapper = items.get(i);
                        bindData(wrapper, layout);
                        img.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    img.setVisibility(View.VISIBLE);
                    goneItem(layout);
                }
            }

            ((ViewPager) container).addView(view);

            return view;
        }

        public void bindData(final AttachWrapper wrapper,LinearLayout layout) {

            ImageView mImg = (ImageView) layout.findViewById(R.id.img_item);
            TextView mTxt = (TextView) layout.findViewById(R.id.txt_item);
            mImg.setImageResource(wrapper.drawableSid);
            mTxt.setText(wrapper.attachName);
            try {
                if(wrapper.disabled) {
                    ViewCompat.setAlpha(mImg,0.4f);
                    ViewCompat.setAlpha(mTxt,0.4f);
                }
            } catch (Exception |Error e) {

            }
            layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!wrapper.disabled) {
                        listener.onTypeChoose(wrapper.attachType);
                    }
                }
            });

        }

        public void goneItem(LinearLayout layout) {
            ImageView mImg = (ImageView) layout.findViewById(R.id.img_item);
            TextView mTxt = (TextView) layout.findViewById(R.id.txt_item);
            mImg.setVisibility(View.GONE);
            mTxt.setVisibility(View.GONE);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void onClick(View arg0) {
            switch (arg0.getId()) {
                case R.id.frame_row1:
                    listener.onTypeChoose(AttachType.PICTURE);
                    break;
                case R.id.frame_row2:
                    listener.onTypeChoose(AttachType.CAMERA);
                    break;
            }
        }

    }

    private static class AttachWrapper {

        public AttachType attachType;
        public String attachName;
        public int drawableSid;
        public boolean disabled;

    }

}
