package org.yeewoe.mopassion.app.common.view.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.yeewoe.mopassion.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wyw on 2016/8/14.
 */
public class MopaNearByFilterPopupWindow extends PopupWindow {

    public interface OnItemClickListener {
        void menSeekMen();

        void menSeekWomen();

        void womenSeekMen();

        void womenSeekWomen();
    }

    private Context mContext;
    private OnItemClickListener onItemClickListener;
    @Bind(R.id.txt_men_seek_men) TextView mTxtMenSeekMen;
    @Bind(R.id.txt_men_seek_women) TextView mTxtMenSeekWomen;
    @Bind(R.id.txt_women_seek_men) TextView mTxtWomenSeekMen;
    @Bind(R.id.txt_women_seek_women) TextView mTxtWomenSeekWomen;

    public MopaNearByFilterPopupWindow(Context context) {
        super(LayoutInflater.from(context).inflate(R.layout.popup_nearby_filter, null), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mContext = context;
//        View inflate = LayoutInflater.from(mContext).inflate(R.layout.popup_nearby_filter, null);
//        setContentView(inflate);
        ButterKnife.bind(this, getContentView());

        setOutsideTouchable(true);
    }

    public int getWidth() {
        return mContext.getResources().getDimensionPixelOffset(R.dimen.nearby_filter_popup_width);
    }

    @OnClick(R.id.txt_men_seek_men) public void menSeekMen(View view) {
        if (onItemClickListener != null) {
            mTxtMenSeekMen.setBackgroundResource(R.drawable.bg_nearby_filter_item);
            mTxtMenSeekWomen.setBackgroundResource(0);
            mTxtWomenSeekMen.setBackgroundResource(0);
            mTxtWomenSeekWomen.setBackgroundResource(0);
            dismiss();
            onItemClickListener.menSeekMen();
        }
    }

    @OnClick(R.id.txt_men_seek_women) public void menSeekWomen(View view) {
        if (onItemClickListener != null) {
            mTxtMenSeekMen.setBackgroundResource(0);
            mTxtMenSeekWomen.setBackgroundResource(R.drawable.bg_nearby_filter_item);
            mTxtWomenSeekMen.setBackgroundResource(0);
            mTxtWomenSeekWomen.setBackgroundResource(0);
            dismiss();
            onItemClickListener.menSeekWomen();
        }
    }

    @OnClick(R.id.txt_women_seek_men) public void womenSeekMen(View view) {
        if (onItemClickListener != null) {
            mTxtMenSeekMen.setBackgroundResource(0);
            mTxtMenSeekWomen.setBackgroundResource(0);
            mTxtWomenSeekMen.setBackgroundResource(R.drawable.bg_nearby_filter_item);
            mTxtWomenSeekWomen.setBackgroundResource(0);
            dismiss();
            onItemClickListener.womenSeekMen();
        }
    }

    @OnClick(R.id.txt_women_seek_women) public void womenSeekWomen(View view) {
        if (onItemClickListener != null) {
            mTxtMenSeekMen.setBackgroundResource(0);
            mTxtMenSeekWomen.setBackgroundResource(0);
            mTxtWomenSeekMen.setBackgroundResource(0);
            mTxtWomenSeekWomen.setBackgroundResource(R.drawable.bg_nearby_filter_item);
            dismiss();
            onItemClickListener.womenSeekWomen();
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
