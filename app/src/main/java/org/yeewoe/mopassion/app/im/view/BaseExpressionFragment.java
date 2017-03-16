package org.yeewoe.mopassion.app.im.view;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;

import com.viewpagerindicator.CirclePageIndicator;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.presenter.MopaPresenter;
import org.yeewoe.mopassion.app.common.view.fragment.MopaFragment;
import org.yeewoe.mopassion.emotion.EmotionUtil;
import org.yeewoe.mopassion.emotion.mopa.BaseEmotionParser;
import org.yeewoe.mopassion.emotion.mopa.EmotionParserFactory;
import org.yeewoe.mopassion.photo.fresco_lib.MopaSimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Zem聊天表情网格显示界面
 *
 * @author crjoe
 */
public abstract class BaseExpressionFragment extends MopaFragment {

    public static final String TAG_DEL = "del";
    public static final int DEFAUL_ROW = 3;
    public static final int DEFAULT_COLUMN = 7;
    /**
     * Listener
     */
    BaseExpressionFragment.OnChatAppendExpressListener mListener;
    ExpressionClickListener mClickListener;
    private ViewPager mPager;
    private LayoutInflater mInflater;
    protected List<String> mExpressNoList;
    private CirclePageIndicator mCirclePageIndicator;
    protected BaseEmotionParser emotionParser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO 看是否能在外层传递表情库名字
        emotionParser = EmotionParserFactory.build(buildExpressionName());

        mExpressNoList = new ArrayList<>();
        mInflater = LayoutInflater.from(getActivity());
        initEmojiNum();

    }

    protected abstract String buildExpressionName();

    private int buildPageCount() {
        if (!isDynamic()) {
            return buildColumnCount() * DEFAUL_ROW - 1;
        } else {
            return buildColumnCount() * (DEFAUL_ROW - 1);
        }
    }

    protected int buildColumnCount() {
        return DEFAULT_COLUMN;
    }

    protected boolean isDynamic() {
        return false;
    }

    @NonNull protected String getExpressionSourcePrefix() {
        return emotionParser.getPrevName();
    }

    public void initEmojiNum() {
        List<String> noArray = emotionParser.getNoArray();
        for (String s : noArray) {
            mExpressNoList.add(getExpressionSourcePrefix() + s);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (OnChatAppendExpressListener) activity;
        mClickListener = new ExpressionClickListener(mListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.view_expression_all, container,
                false);
        ExpressionPagerAdapter adapter = new ExpressionPagerAdapter();
        mPager = (ViewPager) mView.findViewById(R.id.express_pager);
        mPager.getLayoutParams().height = AttachHeightCompute
                .getAllExpressHeight(getResources());
        mPager.setAdapter(adapter);
        mCirclePageIndicator = (CirclePageIndicator) mView
                .findViewById(R.id.express_pager_indicator);
        mCirclePageIndicator.setStrokeWidth(0);
        mCirclePageIndicator.setViewPager(mPager);

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

    public class ExpressionClickListener implements OnClickListener {

        private OnChatAppendExpressListener mAppendExpressListener;

        ExpressionClickListener(OnChatAppendExpressListener mAppendExpressListener) {
            this.mAppendExpressListener = mAppendExpressListener;
        }

        @Override
        public void onClick(View arg0) {
            if (arg0.getTag() == null) {
                return;
            }
            Log.i("AllExpressionFragment", String.valueOf(arg0.getTag()));
            if (!isDynamic()) {
                mAppendExpressListener.onChatAppendExpress(arg0.getTag().toString());
            } else {
                mAppendExpressListener.onChatAppendDynamic(arg0.getTag().toString());
            }
        }

    }

    private class ExpressionPagerAdapter extends PagerAdapter {

        private int mIndex = 0;
        private SparseArray<List<String>> mSparseArray;

        public ExpressionPagerAdapter() {
            mSparseArray = new SparseArray<>();
            for (int i = 0; i < getCount(); i++) {
                if (mIndex < mExpressNoList.size() - buildPageCount()) {
                    mSparseArray.append(i,
                            mExpressNoList.subList(mIndex, mIndex + buildPageCount()));
                    mIndex += buildPageCount();
                } else {
                    mSparseArray.append(i,
                            mExpressNoList.subList(mIndex, mExpressNoList.size() - 1));
                    mIndex = mExpressNoList.size() - 1;
                }
            }

        }

        @Override
        public int getCount() {
            return mExpressNoList.size() / buildPageCount()
                    + (mExpressNoList.size() % buildPageCount() == 0 ? 0 : 1);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = mInflater.inflate(R.layout.view_expression_table, null);
            TableRow mTableRow1 = (TableRow) view.findViewById(R.id.tableRow1);
            TableRow mTableRow2 = (TableRow) view.findViewById(R.id.tableRow2);
            TableRow mTableRow3 = (TableRow) view.findViewById(R.id.tableRow3);
            if (isDynamic()) {
                mTableRow3.setVisibility(View.GONE);
            } else {
                mTableRow3.setVisibility(View.VISIBLE);
            }
            int count = 0;
            List<String> tempList = mSparseArray.get(position);
            int size = tempList.size();

            for (int i = 0; i < buildColumnCount(); i++) {
                initColume(mTableRow1, count, tempList);
                count++;
            }

            for (int i = 0; i < buildColumnCount(); i++) {
                initColume(mTableRow2, count, tempList);
                count++;
            }
            if (!isDynamic()) {
                for (int i = 0; i < buildColumnCount(); i++) {
                    initColume(mTableRow3, count, tempList);
                    count++;
                }
            }

            container.addView(view);
            return view;
        }

        public void initColume(TableRow mRow, int count, List<String> tempList) {
            View mView = mInflater.inflate(R.layout.view_img_frame, null);

            if (!isDynamic() && count == buildPageCount()) {
                TableRow.LayoutParams params = new TableRow.LayoutParams();
                params.weight = 1;
                params.gravity = Gravity.CENTER;
                mView.setLayoutParams(params);
                Drawable mDrawable = getResources().getDrawable(
                        R.drawable.emotion_del);
                ImageView mImageView = (ImageView) mView
                        .findViewById(R.id.img_express);
                mImageView.setBackgroundDrawable(mDrawable);
                mRow.addView(mView);
                mView.setTag(TAG_DEL);
            } else {
                TableRow.LayoutParams params = new TableRow.LayoutParams();
                params.weight = 1;
                params.gravity = Gravity.CENTER;
                mView.setLayoutParams(params);
                if (count < tempList.size()) {
                    if (!isDynamic()) {
                        Drawable mDrawable = EmotionUtil.parseToDrawable(getContext(), tempList.get(count));
                        if (mDrawable == null) {
                            return;
                        }
                        ImageView mImageView = (ImageView) mView.findViewById(R.id.img_express);
                        mView.findViewById(R.id.img_dynamic_express).setVisibility(View.GONE);
                        mImageView.setVisibility(View.VISIBLE);
                        mImageView.setImageDrawable(mDrawable);
                    } else {
                        MopaSimpleDraweeView mImageView = (MopaSimpleDraweeView) mView
                                .findViewById(R.id.img_dynamic_express);
                        mImageView.setAutoPlayAnimations(true);
                        mView.findViewById(R.id.img_express).setVisibility(View.GONE);
                        mImageView.setVisibility(View.VISIBLE);
                        int drawableId = EmotionUtil.parseToDrawableId(tempList.get(count));
                        if (drawableId > 0) {
                            mImageView.setDraweeViewResId(drawableId);
                        }
                    }
                    mView.setTag(tempList.get(count));
                }
                mRow.addView(mView);
            }
            mView.setOnClickListener(mClickListener);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }

    public interface OnChatAppendExpressListener {
        void onChatAppendExpress(String expressRes);

        void onChatDeleteExpress();

        void onChatAppendDynamic(String expressRes);
    }

}
