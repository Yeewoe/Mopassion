package org.yeewoe.mopassion.app.nearby.presenter;

import android.app.Activity;
import android.content.Intent;

import org.yeewoe.commonutils.common.utils.CatchUtil;
import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopassion.app.common.presenter.FormPresenter;
import org.yeewoe.mopassion.app.location.manager.LocationPointInfo;
import org.yeewoe.mopassion.app.location.manager.MopaLocationClient;
import org.yeewoe.mopassion.app.maintab.view.MainTabActivity;
import org.yeewoe.mopassion.app.nearby.model.TrendLineVo;
import org.yeewoe.mopassion.app.nearby.model.TrendPublishParam;
import org.yeewoe.mopassion.app.nearby.model.TrendService;
import org.yeewoe.mopassion.app.nearby.view.iview.ITrendPublishView;
import org.yeewoe.mopassion.app.nearby.view.iview.ITrendReviewView;
import org.yeewoe.mopassion.constants.TrendConstants;
import org.yeewoe.mopassion.db.po.Trend;
import org.yeewoe.mopassion.mangers.UserDataMananger;

import java.io.File;

/**
 * Created by wyw on 2016/4/8.
 */
public class TrendReviewPresenter extends FormPresenter<TrendPublishParam> implements MopaLocationClient.NotifyLocationListener {
    private final ITrendReviewView view;
    private TrendService trendService;

    public TrendReviewPresenter(ITrendReviewView view) {
        super(view);
        this.view = view;
        this.trendService = new TrendService();
    }

    @Override public void onDestroy() {
    }

    @Override public void submit(final TrendPublishParam param) {
        view.showLoading();
        trendService.createTrend(param.toPo(), param.fileList, new Callback() {
            @Override public <T> void callback(final CallbackInfo<T> info) {
                if (info.bError) {
                    view.showError(info.errorCode);
                } else {
                    view.runOnUiThread(new Runnable() {
                        @Override public void run() {
                            view.dismissLoading();
                            Intent intent = new Intent(view.getActivity(), MainTabActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            CatchUtil.startActivity(view.getActivity(), intent);
//                            Intent intent = view.getIntent();
//
//                            intent.putExtra(TrendConstants.EXTRA_TREND_PUBLISHED,
//                                    TrendLineVo.Convertor.convert((Trend) info.mT, UserDataMananger.getInstance().getMeList()));
//                            view.setResult(Activity.RESULT_OK, intent);
//                            view.finish();
                        }
                    });
                }
            }
        });

    }

    @Override public void cancelSubmit() {
        onDestroy();
    }
}
