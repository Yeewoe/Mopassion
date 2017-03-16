package org.yeewoe.mopassion.event;

/**
 * TAB切换事件
 * Created by wyw on 2016/3/23.
 */
public class TabChangeEvent {

    public int position;

    public TabChangeEvent(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "TabChangeEvent{" +
                "position=" + position +
                '}';
    }
}
