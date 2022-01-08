package com.how2java.enity;

import java.util.List;

// 兵种
public class arms {
    private Integer id;
    private String name;
    private List <Soldier> soliders; //一个兵种有很多种类士兵

    @Override
    public String toString() {
        return "arms{" +
                "id=" + id +
                ", name='" + name +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List <Soldier> getSoliders() {
        return soliders;
    }

    public void setSoliders(List <Soldier> soliders) {
        this.soliders = soliders;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
