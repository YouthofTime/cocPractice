package com.how2java.enity;

import java.io.Serializable;

/**
 * status:0表示用户名已经被占用,1表示注册成功,0表示登录失败(账号,密码错误),1表示登录成功
 * msg:注册不成功,登录不成功,返回对应消息
 * data:保留数据,方便以后cookie的使用
 */
public class StatusJudge implements Serializable {
    private Integer status;// 状态
    private String meg;// 消息
    private Object data;// 数据

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMeg() {
        return meg;
    }

    public void setMeg(String meg) {
        this.meg = meg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
