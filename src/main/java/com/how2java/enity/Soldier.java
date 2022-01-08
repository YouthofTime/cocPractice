package com.how2java.enity;

import java.util.List;

public class Soldier {
    private Integer id;
    private String name;
    private Integer uid;
    private Integer aid;
    private Integer damageSecond;
    private String holyWater;
    private Integer hp;
    private Integer level;

    private List <User> users;// 和user是多对多
    private arms arm;// 和arms是一对多

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getHolyWater() {
        return holyWater;
    }

    public void setHolyWater(String holyWater) {
        this.holyWater = holyWater;
    }

    public arms getArm() {
        return arm;
    }

    public void setArm(arms arm) {
        this.arm = arm;
    }


    @Override
    public String toString() {
        return "Soldier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", uid=" + uid +
                ", aid=" + aid +
                ", damageSecond=" + damageSecond +
                ", holyWater='" + holyWater + '\'' +
                ", hp=" + hp +
                ", level=" + level +
                ", users=" + users +
                ", arm=" + arm +
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

    public List <User> getUsers() {
        return users;
    }

    public void setUsers(List <User> users) {
        this.users = users;
    }

    public Integer getDamageSecond() {
        return damageSecond;
    }

    public void setDamageSecond(Integer damageSecond) {
        this.damageSecond = damageSecond;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
