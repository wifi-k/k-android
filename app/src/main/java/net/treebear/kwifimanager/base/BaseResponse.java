package net.treebear.kwifimanager.base;

/**
 * @author Tinlone
 * @date 2018/3/26.
 */

public class BaseResponse<T> {

    private int code;
    private T data;
    private String msg;
    private Object count;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getCount() {
        return count;
    }

    public void setCount(Object count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", data='" + data + '\'' +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                '}';
    }
}
