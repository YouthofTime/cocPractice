package com.how2java.controller;
import com.how2java.dao.ISolderDao;
import com.how2java.enity.Soldier;
import com.how2java.enity.StatusJudge;
import com.how2java.enity.User;
import com.how2java.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
@RequestMapping("/coc1")
@SessionAttributes ({"username","start","holyWater","blackWater"})
public class CocController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ISolderDao solderDao;
    // 如果是用户为1的要特殊处理
    /**
     *
     * @param modelMap
     * @param name 添加士兵的名字
     * @return
     */
    @RequestMapping("/add")
    public @ResponseBody StatusJudge add(ModelMap modelMap, String name){
        StatusJudge judge=new StatusJudge();
        // 一.查询数据库中是否已经有该兵种了(区别用户)
        String uname= (String) modelMap.get("username");
        User user=userService.findByName(uname);
        Soldier soldier=solderDao.findByUidAndName(user.getId(),name);// 没查到就返回null
        if(soldier!=null){  // 已经有了,添加失败
            judge.setStatus(0);
            judge.setMeg("您已经拥有该士兵");
            return judge;
        }
        // 二.没有
        // 1.设置添加士兵的等级,名字,所属用户
        soldier=new Soldier();
        soldier.setLevel(1);
        soldier.setName(name);
        soldier.setUid(user.getId());

        // 2.根据主用户的该士兵的信息,设置当前用户士兵(每秒伤害,血量,兵种)
        Soldier mainsSoldier=solderDao.findByUidAndName(1,name); // 主用户有没有该兵种
        if(mainsSoldier==null)
        {
            judge.setStatus(1);
            judge.setMeg("没有该兵种");
            return judge;
        }
        int mlevle=mainsSoldier.getLevel();
        int damageSecond=mainsSoldier.getDamageSecond()/mlevle;
        int hp=mainsSoldier.getHp()/mlevle;
        int aid=mainsSoldier.getAid();

        // 3.判断当前士兵是否要进行资源消耗的修改
        soldier.setHolyWater(mainsSoldier.getHolyWater()); // 假如有3000圣水
        if(aid==4||aid==5){  // 需要
            String holyWater=mainsSoldier.getHolyWater();
            // 找到最后一个数字的位置
            char[]hs=holyWater.toCharArray();
            int i=0;
            for(;i<hs.length;i++){
                char c=hs[i];
                if(!Character.isDigit(c)) // 不是数字
                    break;
            }
            // 此时i的位置为第一个不是数字的下标
            // 分割字符串
            String digitstr=holyWater.substring(0,i);
            int digit=Integer.parseInt(digitstr);// 转换为数字
            digit=digit/mlevle;
            String extend=holyWater.substring(i);
            // 重新拼接
            holyWater=digit+extend;
            soldier.setHolyWater(holyWater); // 设置资源消耗,3000/mlvle
        }
        soldier.setDamageSecond(damageSecond);
        soldier.setHp(hp);
        soldier.setAid(aid);
        // 4.保存到数据库中
        solderDao.add(soldier);
        judge.setStatus(2); // 成功
//        Cookie cookie=new Cookie("uid",user.getId().toString());
//        cookie.setMaxAge(3600);
//        response.addCookie(cookie); // 没必要
        //return "redirect:/user/success";
        return judge;
    }

    /**
     *
     * @param modelMap
     * @param id     传递过来要删除的用户id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(ModelMap modelMap, String id, HttpServletRequest request){
        // 通过用户名查询到用户
        String uname= (String) modelMap.get("username");
        User user=userService.findByName(uname);
        // 翻页位置
        Integer start=(Integer) modelMap.get("start");
        Integer aid=Integer.parseInt(request.getParameter("aid"));
        // 删除
        System.out.println(id);
        solderDao.delete(Integer.parseInt(id),user.getId());
        return "redirect:/user/success?start="+start+"&aid="+aid;
    }

    /**
     * 升级
     * @param id
     * @return
     */
    @RequestMapping("/upgrade")
    public @ResponseBody StatusJudge upgrade(String id){

        StatusJudge judge=new StatusJudge();
        // 通过id查询到士兵
        Soldier soldier=solderDao.findById(Integer.parseInt(id));
        // 兵种当前可升的最高级
        Soldier maxSoldier=solderDao.findByUidAndName(1,soldier.getName());
        int maxlevel=maxSoldier.getLevel();
        if(soldier.getLevel()==maxlevel){ // 已经达到最高级
            judge.setStatus(0); // 失败
            judge.setMeg("当前兵种已经达到最高等级");
            return judge;
        }
        // 升级
        upgrade(soldier);
        System.out.println(soldier);
        // 更新数据
        solderDao.update(soldier);
        judge.setStatus(1);// 成功
        return judge;
        // 重定向跳转
        //return "redirect:/user/success?start="+start;
    }
    // 用户升级
    public void upgrade(Soldier soldier){
        int level=soldier.getLevel();
        int damageSecond=soldier.getDamageSecond()+soldier.getDamageSecond()/level;
        int hp=soldier.getHp()+soldier.getHp()/level;

        //  主世界兵种,限时兵种资源消耗升级
        if(soldier.getAid()==4||soldier.getAid()==5){
            String holyWater=soldier.getHolyWater();
            // 找到最后一个数字的位置
            char[]hs=holyWater.toCharArray();
            int i=0;
            for(;i<hs.length;i++){
                char c=hs[i];
                if(!Character.isDigit(c)) // 不是数字
                    break;
            }
            // 此时i的位置为第一个不是数字的下标
            // 分割字符串
            String digitstr=holyWater.substring(0,i);
            int digit=Integer.parseInt(digitstr);// 转换为数字
            digit=digit+digit/level;
            String extend=holyWater.substring(i);
            // 重新拼接
            holyWater=digit+extend;
            soldier.setHolyWater(holyWater);
        }
        soldier.setDamageSecond(damageSecond);
        soldier.setHp(hp);
        soldier.setLevel(level+1);
    }


    // 收集圣水
    @RequestMapping("/collect")
    public String collectHoly(ModelMap modelMap) throws ParseException {
        // 翻页位置
        Integer start=(Integer)modelMap.get("start");

        // 添加到session域
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sstart="2020-8-18 13:07:00";
        Date dstart=format.parse(sstart);
        long sumHolyWater=0; // 圣水总量
        long sumBlackWater=0; // 黑油总量
        Date now=new Date();
        sumHolyWater=(now.getTime()-dstart.getTime())/1000;
        sumBlackWater=(now.getTime()-dstart.getTime())/60000;
        modelMap.addAttribute("holyWater",sumHolyWater);
        modelMap.addAttribute("blackWater",sumBlackWater);
        return "redirect:/user/success?start="+start;
    }
}
