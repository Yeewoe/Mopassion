package org.yeewoe.mopassion.app.nearby.model;

import android.support.annotation.NonNull;

import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopanet.callback.CallbackUtils;
import org.yeewoe.mopanet.protos.PBTrdAddRsp;
import org.yeewoe.mopanet.protos.PBTrdGetRsp;
import org.yeewoe.mopanet.protos.PBTrdNearRsp;
import org.yeewoe.mopanet.protos.PBTrdPullUserRsp;
import org.yeewoe.mopanet.protos.PBTrends;
import org.yeewoe.mopassion.app.common.service.BaseService;
import org.yeewoe.mopassion.app.common.service.CommonService;
import org.yeewoe.mopassion.app.file.model.UploadCallback;
import org.yeewoe.mopassion.app.file.service.PicBatchManager;
import org.yeewoe.mopassion.constants.TrendConstants;
import org.yeewoe.mopassion.db.dao.DaoManager;
import org.yeewoe.mopassion.db.po.MapPosition;
import org.yeewoe.mopassion.db.po.Trend;
import org.yeewoe.mopassion.utils.LogCore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 动态业务接口
 * Created by wyw on 2016/4/5.
 */
public class TrendService extends BaseService {

    private static final String CLASS_NAME = "TrendService";

    private static final int COUNT_NEAR_TREND_REQUEST = 2000;

    private CommonService commonService;
    private List<Long> bufferTrendIds = new ArrayList<>();

    public TrendService() {
        commonService = new CommonService();
    }


    /**
     * 发布动态
     */
    public void createTrend(final Trend trend, List<File> fileList, final Callback callback) {
        final String METHOD_NAME = "createTrend";
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "trend=" + trend);
        PicBatchManager.getInstance().call(fileList, new UploadCallback() {
            @Override public void progress(String key, double percent) {

            }

            @Override public void success(String key) {

            }

            @Override public void fail(String key, int errorCode) {

            }

            @Override public void cancel(String key) {

            }
        }, new Callback() {
            @Override public <T> void callback(CallbackInfo<T> info) {
                if (info.bError) {
                    CallbackUtils.errorCallback(callback, info.errorCode);
                    return;
                }

                TrendProtobufNet.add(trendToPb(trend), new Callback() {
                    @Override public <T> void callback(CallbackInfo<T> info) {
                        if (info.bError) {
                            CallbackUtils.errorCallback(callback, info.errorCode);
                            return;
                        }

                        PBTrdAddRsp pbTrdAddRsp = (PBTrdAddRsp) info.mT;
                        trend.setSid(pbTrdAddRsp.id);
                        logInterfaceReturn(CLASS_NAME, METHOD_NAME, trend);
                        CallbackUtils.callbackInfoObject(callback, trend);
                        // 暂时不保存数据库，这里要考虑本地查询出来的排序
//                try {
//                    DaoManager.getTrendDao().insertOrUpdate(trend, trend.getSid());
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
                    }
                });
            }
        });
    }

    /**
     * 查询附近的动态列表
     */
    public Callback.CallbackInfo<Trend> getNearTrendLine(MapPosition address,int purpose,
                                                         long min, long max, int startIndex, int count) {
        final String METHOD_NAME = "getNearTrendLine";
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "address=" + address + ", min=" + min + ", max=" + max + ", startIndex=" + startIndex + ", count=" + count);
        final Callback.CallbackInfo<Trend> result = new Callback.CallbackInfo<>();
        if (startIndex <= 0) {
            // 首页需要请求trend id 列表
            TrendProtobufNet.getNear(address, purpose, min, max, COUNT_NEAR_TREND_REQUEST, new Callback() {
                @Override public <T> void callback(CallbackInfo<T> info) {
                    LogCore.d("www-1.1");
                    if (info.bError) {
                        CallbackUtils.buildErrorCallbackInfo(result, info);
                        return;
                    }

                    PBTrdNearRsp pbTrdNearRsp = (PBTrdNearRsp) info.mT;
                    bufferTrendIds = pbTrdNearRsp.ids;

                }
            });
        }

        LogCore.d("www-1.2");
        if (!result.bError && Checks.check(bufferTrendIds)) {
            int end = startIndex + count;
            final List<Long> tempTrendIds = bufferTrendIds.subList(startIndex, bufferTrendIds.size() <= end ? bufferTrendIds.size() : end);
            LogCore.d("www-1.2.1");
            logRun(CLASS_NAME, METHOD_NAME, "tempTrendIds=" + tempTrendIds);
            // TODO 这里可以考虑利用缓存
            TrendProtobufNet.get(tempTrendIds, new Callback() {
                @Override public <T> void callback(CallbackInfo<T> info) {
                    LogCore.d("www-1.3");
                    if (info.bError) {
                        CallbackUtils.buildErrorCallbackInfo(result, info);
                        return;
                    }

                    PBTrdGetRsp pbTrdGetRsp = (PBTrdGetRsp) info.mT;
                    List<PBTrends> pbTrends = pbTrdGetRsp.trends;
                    List<Trend> trends = pbToTrend(pbTrends);
                    trends = sort(trends, tempTrendIds);
                    DaoManager.getTrendDao().batchInsertOrUpdate(trends);
                    result.mTs = trends;
                }
            });
        }

        LogCore.d("www-1.4");
        logInterfaceReturn(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    private List<Trend> sort(List<Trend> trends, List<Long> trendIds) {
        if (trendIds == null || trends == null) {
            return new ArrayList<>();
        }

        List<Trend> result = new ArrayList<>();

        for (Long trendId : trendIds) {
            for (Trend trend : trends) {
                if (trendId == trend.getSid()) {
                    result.add(trend);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 查询指定人的动态列表
     */
    public Callback.CallbackInfo<Trend> getUserTrendLine(long uid, long startSid, int count) {
        final String METHOD_NAME = "getUserTrendLine";
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "uid=" + uid + ", startSid=" + startSid + ", count=" + count);
        if (uid <= 0) {
            return (Callback.CallbackInfo<Trend>) CallbackUtils.buildInvalidCallbackInfo();
        }

        final Callback.CallbackInfo<Trend> result = new Callback.CallbackInfo<>();

        TrendProtobufNet.getUserTrends(uid, startSid, count, new Callback() {
            @Override public <T> void callback(CallbackInfo<T> info) {
                if (info.bError) {
                    CallbackUtils.buildErrorCallbackInfo(result, info);
                    return ;
                }
                PBTrdPullUserRsp rsp = (PBTrdPullUserRsp) info.mT;
                List<PBTrends> pbTrends = rsp.trends;
                List<Trend> trends = pbToTrend(pbTrends);
                DaoManager.getTrendDao().batchInsertOrUpdate(trends);
                result.mTs = trends;
            }
        });

        logInterfaceReturn(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    @NonNull private List<Trend> pbToTrend(List<PBTrends> pbTrendses) {
        if (pbTrendses == null) {
            return new ArrayList<>();
        }
        List<Trend> result = new ArrayList<>();
        for (PBTrends pbTrend : pbTrendses) {
            if (pbTrend != null) {
                result.add(pbToTrend(pbTrend));
            }
        }
        return result;

    }

    @NonNull private Trend pbToTrend(@NonNull PBTrends pbTrend) {
        Trend result = new Trend();
        if (pbTrend.id != null) {
            result.setSid(pbTrend.id);
        }
        if (pbTrend.uid != null) {
            result.setCreateBy(pbTrend.uid);
        }
        if (pbTrend.view != null) {
            result.setTrendView(pbTrend.view);
        }
        result.setContents(commonService.pbToMedia(pbTrend.contents));
        if (pbTrend.pubtime != null) {
            result.setCreateTime(pbTrend.pubtime);
        }
        if (pbTrend.pubpostion != null) {
            result.setPosition(commonService.pbToMapPosition(pbTrend.pubpostion));
        }

        return result;
    }

    private PBTrends trendToPb(Trend trend) {
        PBTrends.Builder builder = new PBTrends.Builder();
        if (trend.getSid() > 0) {
            builder.id(trend.getSid());
        }
        if (trend.getCreateBy() > 0) {
            builder.uid(trend.getCreateBy());
        }
        if (trend.getTrendView() >= TrendConstants.TREND_VIEW_PUBLIC) {
            builder.view(trend.getTrendView());
        }
        //TODO @zjm mock   begin
        Random r = new Random();
        Integer purpose = r.nextInt(4) + 1;
        builder.purpose(purpose);
        // end

        builder.contents(commonService.mediaToPb(trend.getContents()));
        builder.pubpostion(commonService.mapPositionToPb(trend.getPosition()));
        return builder.build();
    }

}
