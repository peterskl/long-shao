package com.app.kekoo.common.basebean;

import java.io.Serializable;

/**
 * des:封装服务器返回数据
 */
public class BaseRespose<T> implements Serializable {
    public int ret;

    public T data;

    public String msg;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean success() {
        return 200 == ret;
    }

    @Override
    public String toString() {
        return "BaseRespose{" +
                "status='" + ret + '\'' +
                ", message=" + data +"msg"+msg+
                '}';
    }
}
