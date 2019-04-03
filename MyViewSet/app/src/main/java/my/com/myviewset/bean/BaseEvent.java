package my.com.myviewset.bean;

/**
 * 事件基类
 *
 * @author cong
 * @date 2017/6/28
 */

public class BaseEvent {

    protected int what;
    protected Object data;

    public BaseEvent(){}

    public BaseEvent(int what){
        this(what, null);
    }

    public BaseEvent(int what, Object data){
        this.what = what;
        this.data = data;
    }

    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
