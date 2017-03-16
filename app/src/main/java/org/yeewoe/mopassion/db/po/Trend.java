package org.yeewoe.mopassion.db.po;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.yeewoe.mopassion.db.core.DbConstants;

import java.util.List;

/**
 * 动态PO
 * Created by wyw on 2016/4/5.
 */
@DatabaseTable(tableName = DbConstants.TableInfo.Trend.TABLE_NAME)
public class Trend extends BasePo {

    @DatabaseField(columnName = DbConstants.TableInfo.Trend.JSON_CONTENTS)
    private String jsonContents;
    private List<Media> contents;

    @DatabaseField(columnName = DbConstants.TableInfo.Trend.TREND_VIEW)
    private int trendView;

    @DatabaseField(columnName = DbConstants.TableInfo.Trend.JSON_POSITION)
    private String jsonPosition;
    private MapPosition position;

    public String getJsonContents() {
        return jsonContents;
    }

    public void setJsonContents(String jsonContents) {
        this.jsonContents = jsonContents;
    }

    public List<Media> getContents() {
        return contents;
    }

    public void setContents(List<Media> contents) {
        this.contents = contents;
    }

    public int getTrendView() {
        return trendView;
    }

    public void setTrendView(int trendView) {
        this.trendView = trendView;
    }

    public String getJsonPosition() {
        return jsonPosition;
    }

    public void setJsonPosition(String jsonPosition) {
        this.jsonPosition = jsonPosition;
    }

    public MapPosition getPosition() {
        return position;
    }

    public void setPosition(MapPosition position) {
        this.position = position;
    }

    @Override public String toString() {
        return "Trend{" +
                "contents=" + contents +
                ", position=" + position +
                "} " + super.toString();
    }
}
