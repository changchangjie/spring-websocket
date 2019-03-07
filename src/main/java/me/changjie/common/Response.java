package me.changjie.common;

/**
 * Created by ChangJie on 2019-03-07.
 */
public class Response {

    private String result = "100";

    private String message;

    private Object data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
