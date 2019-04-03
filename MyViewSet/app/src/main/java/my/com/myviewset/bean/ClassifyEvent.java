package my.com.myviewset.bean;



/**
 * 分类页面的事件
 */

public class ClassifyEvent extends BaseEvent {

    public static final int ALL_CLASSIFY_GRID_CLICK_EVENT = 3;


    public ClassifyEvent() {
    }

    public ClassifyEvent(int what) {
        super(what);
    }

    public ClassifyEvent(int what, Object data) {
        super(what, data);
    }
}
