package my.com.myviewset.bean;

import java.io.Serializable;

/**
 * Created by huneng on 2018-07-05.
 */

public class NewLineBean implements Serializable{
    public NewLineBean(String key,String text) {
        this.text = text;
        this.key = key;
    }

    public NewLineBean() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    String text;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    String key;
}
