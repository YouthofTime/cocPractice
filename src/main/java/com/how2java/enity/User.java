package com.how2java.enity;

import java.io.Serializable;
import java.util.List;

// 用户表
public class User implements Serializable {
    private Integer id;
    private String name;
    private String password;

    private List <Soldier> solders; //一个用户可以拥有很多兵

    public List <Soldier> getSolders() {
        return solders;
    }

    public void setSolders(List <Soldier> solders) {
        this.solders = solders;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
