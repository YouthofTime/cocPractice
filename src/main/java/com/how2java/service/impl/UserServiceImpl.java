package com.how2java.service.impl;

import com.how2java.dao.IUserDao;
import com.how2java.enity.StatusJudge;
import com.how2java.enity.User;
import com.how2java.enity.Soldier;
import com.how2java.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {


    @Autowired
    private IUserDao userDao;// 注入对象
    // 登录
    public StatusJudge login(User user) {
        StatusJudge sjude=new StatusJudge();
        // 判断用户是否存在
        User userexist=userDao.findByName(user.getName());
        if(userexist==null){ // 不存在,登录失败
            sjude.setStatus(0);
            sjude.setMeg("用户名错误");
            return sjude;
        }
        // 用户存在,匹配密码
        if(!user.getPassword().equals(userexist.getPassword())){// 密码错误
            sjude.setStatus(0);
            sjude.setMeg("密码错误");
            return sjude;
        }
        // 登录成功
        sjude.setStatus(1);
        sjude.setMeg("登录成功");
        sjude.setData(userexist.getId()); //唯一标识id

        return sjude;
    }
    // 注册(保存到cookie是登录做的事情)
    public StatusJudge register(User user) {
        StatusJudge sjude=new StatusJudge();
        // 判断用户名是否存在,按照名字
        User userexist=userDao.findByName(user.getName());
        if(userexist!=null){ //存在
            // 设置状态
            sjude.setStatus(0);
            sjude.setMeg("用户已经存在");
            return sjude;
        }
        // 不存在,保存用户
        userDao.saveUser(user);
        // 设置状态,信息
        sjude.setStatus(1);
        sjude.setMeg("注册成功");
        return sjude;
    }

    @Override
    public User findByid(Integer uid) {
        return userDao.findAllSolider(uid);
    }
    // 全部兵种
    @Override
    public User findALLSoldier(Integer id) {
        return userDao.findAllSolider(id);
    }
    // 主世界兵种分页
    @Override
    public List <Soldier> findPartSoldier(Integer uid, Integer start, Integer count) {
        return userDao.findPartSolider(uid,start,count);
    }

    // 夜世界兵种分页
    @Override
    public List <Soldier> findPartSoldierNight(Integer uid, Integer start, Integer count) {
        return userDao.findPartSoliderNight(uid,start,count);
    }

    @Override
    public User findByName(String name) {
        return userDao.findByName(name);
    }
}
