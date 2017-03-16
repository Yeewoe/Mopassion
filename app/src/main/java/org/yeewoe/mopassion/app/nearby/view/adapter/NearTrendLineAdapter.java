package org.yeewoe.mopassion.app.nearby.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.adapter.MopaAdapter;
import org.yeewoe.mopassion.app.common.view.widget.ImChatTextView;
import org.yeewoe.mopassion.app.common.view.widget.MopaGenderView;
import org.yeewoe.mopassion.app.common.view.widget.MopaTextView;
import org.yeewoe.mopassion.app.common.view.widget.ThumbPhotoGridLayout;
import org.yeewoe.mopassion.app.nearby.model.TrendLineVo;
import org.yeewoe.mopassion.photo.fresco_lib.HeadThumbSimpleDraweeView;
import org.yeewoe.mopassion.utils.IntentManager;

import butterknife.Bind;

/**
 * Created by wyw on 2016/4/7.
 */
public class NearTrendLineAdapter extends MopaAdapter<TrendLineVo> {
    public NearTrendLineAdapter(Context context) {
        super(context);
    }

    @Override public int getItemLayoutId() {
        return R.layout.item_trend;
    }

    @Override public MopaViewHolder getViewHolder(View view) {
        return new TrendLineViewHolder(view);
    }

    @Override protected void bindViewHolder(MopaViewHolder mopaViewHolder, @NonNull final TrendLineVo data) {
        TrendLineViewHolder viewHolder = (TrendLineViewHolder) mopaViewHolder;
        viewHolder.mTxtUserName.setUser(data.getUser());
        viewHolder.mImgviHead.setImageHead(data.getUser());

        viewHolder.mGenderViewMain.setGender(data.getUser());

        if (!Checks.check(data.getMsg()) && Checks.check(data.getImages())) {
            viewHolder.mTxtTrendContent.setVisibility(View.GONE);
            viewHolder.mTxtTrendContent.setText("");
        } else {
            viewHolder.mTxtTrendContent.setVisibility(View.VISIBLE);
            viewHolder.mTxtTrendContent.setText(data.getMsg());
        }

        viewHolder.mTxtTrendDistance.setText(context.getString(R.string.format_distance, data.getDistance()));
        viewHolder.mTxtTrendTime.setText(data.getTime());
        viewHolder.mThumbGLPhoto.setPhotos(data.getImages());

        viewHolder.mViewItem.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                IntentManager.Trend.intentToTrendDetail(context, data);
            }
        });
    }

    class TrendLineViewHolder extends MopaViewHolder {

        @Bind(R.id.txt_user_name) MopaTextView mTxtUserName;
        @Bind(R.id.imgvi_gender) MopaGenderView mGenderViewMain;
        @Bind(R.id.txt_trend_content) MopaTextView mTxtTrendContent;
        @Bind(R.id.txt_trend_distance) MopaTextView mTxtTrendDistance;
        @Bind(R.id.txt_trend_time) MopaTextView mTxtTrendTime;
        @Bind(R.id.iv_trend_photo) HeadThumbSimpleDraweeView mImgviHead;
        @Bind(R.id.thumbGL_photo) ThumbPhotoGridLayout mThumbGLPhoto;

        public TrendLineViewHolder(View convertView) {
            super(convertView);

        }
    }
}
