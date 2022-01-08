package com.how2java.service;

import com.how2java.enity.StatusJudge;
import com.how2java.enity.User;
import com.how2java.enity.Soldier;

import java.util.List;

public interface IUserService {

    /**
     * 登录判断
     * 1.状态为2表示登录失败,3表示登录成功
     * @param user
     * @return
     */
    StatusJudge login(User user);

    /**
     * 注册判断
     * 1.状态为0表示用户名已经存在,1表示注册成功
     * @return
     */
    StatusJudge register(User user);

    User findByid(Integer uid);

    /*分页查询*/
    List<Soldier> findPartSoldier(Integer uid, Integer start, Integer count); // 主世界

    List<Soldier> findPartSoldierNight(Integer uid, Integer start, Integer count); // 夜世界

    User findALLSoldier(Integer id);
    User findByName(String name);
}
