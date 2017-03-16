package org.yeewoe.mopassion.app.nearby.view.iview;

import org.yeewoe.mopassion.app.common.view.interfaces.IActivityView;
import org.yeewoe.mopassion.app.location.manager.LocationPointInfo;

import java.io.File;

/**
 * Created by wyw on 2016/4/8.
 */
public interface ITrendPublishView extends IActivityView {
    void setSite(LocationPointInfo site);

    void addPic(File pic);
}
