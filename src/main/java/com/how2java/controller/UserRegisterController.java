package com.how2java.controller;

import com.how2java.dao.ISolderDao;
import com.how2java.enity.StatusJudge;
import com.how2java.enity.User;
import com.how2java.enity.Soldier;
import com.how2java.exception.SysException;
import com.how2java.service.impl.UserServiceImpl;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/user")
@SessionAttributes ({"username","next","up","start","soldiers"})
public class UserRegisterController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ISolderDao solderDao;

    /**
     * 注册
     * 跳转页面交给前端了
     * @param user 注册用户
     * @return  返回是否注册成功的类(通过类的status属性判断)
     * @throws SysException
     */
    @RequestMapping("/register")
    public @ResponseBody StatusJudge register(@RequestBody User user) throws SysException {
        StatusJudge judge=null;
        try {
            judge=userService.register(user);
        }
        catch (Exception e){
            throw new SysException("联系管理员");
        }
        return judge;
    }

    /**
     *
     * @param user 需要登录的用户
     * @param response
     * @return  返回是否登录成功的类(通过类的status属性判断)
     * @throws SysException
     */
    @RequestMapping("/login")
    public @ResponseBody StatusJudge login(@RequestBody User user, HttpServletResponse response)throws  SysException{
        StatusJudge judge=null;
        try {
            judge=userService.login(user);
            // 添加cookie(前端那里不生效啊那个方法)
            if(judge.getData()!=null)
            {
                Integer uid= (Integer) judge.getData();
                Cookie cookie=new Cookie("uid",uid.toString());
                cookie.setMaxAge(24*60*60);
                response.addCookie(cookie); // 新用户登录会替换之前的值
            }

        }
        catch (Exception e){
            throw new SysException("联系管理员");
        }
        return judge;
    }

    /**
     * 快速登录,免登录
     * @param request
     * @return
     */
    @RequestMapping("/quickLogin")
    public @ResponseBody StatusJudge checklogin(HttpServletRequest request){
        StatusJudge judge=new StatusJudge();

        // 获得cookies
        Cookie[] cookies=request.getCookies();
        // 判断uid是否为空
        // 不为空表明已经登录过
        boolean flag=false;
        if(cookies!=null){
            for(Cookie cookie:cookies)
                if(cookie.getName().equals("uid")) {
                    flag = true;
                }
        }
        if(flag==true){  // cookie中有uid
            judge.setMeg("登录成功");
            judge.setStatus(1);
            return judge;
        }
        else {    // cookie中没有uid
            judge.setMeg("没有登陆过,请选择登录");
            judge.setStatus(0);
            return judge;
        }
    }

    /**
     * 登录成功后添加session,跳转
     * @param model
     * @param request
     * @return 登录成功跳转到COC.jsp,如果没有该用户跳转到登录页面
     */
    @RequestMapping("/success")
    public String  success(Model model, HttpServletRequest request){
        Integer uid=0; //用户id值
        // 根据cookie值,获得uid
        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies)
                if(cookie.getName().equals("uid")){
                    uid=Integer.parseInt(cookie.getValue());
                }
        }
        // 添加用户名,士兵信息到session中
        User us=userService.findByid(uid); // 懒加载不会进行查询士兵的信息
        if(us!=null) {
            String  username = us.getName(); // 用户名
            /* 分页功能实现*/
            int start=0;
            int count=4;
            try{
                start=Integer.parseInt(request.getParameter("start")); // 方法参数那里好像貌似可以不提供的吧
            }
            catch (Exception e){
                // 浏览器没有传参数start
            }
            int next=start+count;
            int up=start-count;


            // 主夜世界兵种数目
            int msize=solderDao.listMain(uid);
            msize=msize/count*count;
            int nsize=solderDao.listNight(uid);
            nsize=nsize/count*count;

            next=next>msize?msize:next;// 大的放后面(但是这里默认主世界的兵种多了)
            up=up<0?0:up;


            /*主夜世界兵种*/
            List <Soldier> smain = userService.findPartSoldier(uid,start,count); // 士兵信息(分页) 主
            List<Soldier> snight=userService.findPartSoldierNight(uid,start,count);
            /*根据前端传过来的world决定显示主世界还是夜世界*/
            List<Soldier> soldiers=smain;
            int aid=0;// 翻页时候进行判断(aid是判断属于哪个世界的)
            try{
                aid=Integer.parseInt(request.getParameter("aid"));
            }
            catch (Exception e){

            }

            int world=0;// 主夜世界图片切换时进行判断

            try{
                world=Integer.parseInt(request.getParameter("world"));
            }
            catch (Exception e){

            }
            if(aid==1||aid==2){
                world=2;
            }
            if(world==2){
                soldiers=snight;
                next=next>nsize?nsize:next;
            }

            // 添加到session域中solders
            model.addAttribute("soldiers",soldiers);
            model.addAttribute("username",username);
            model.addAttribute("next",next);
            model.addAttribute("up",up);
            model.addAttribute("start",start);
            // 跳转到coc.jsp
            return "/COC";
        }
        // 没有该用户,跳转到登录页面
        return "redirect:/login.jsp";
    }
    // 退出
    @RequestMapping("/back")
    public String back(SessionStatus status, HttpServletRequest request, HttpServletResponse response){
        // 清空session,
        status.setComplete();
        // 清空cookie(将响应时间设置为0)
        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies)
                if(cookie.getName().equals("uid")){
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
        }
        return "redirect:/login.jsp";
    }

}
