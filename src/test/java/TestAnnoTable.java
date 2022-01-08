import com.how2java.dao.ISolderDao;
import com.how2java.enity.User;
import com.how2java.enity.Soldier;
import com.how2java.service.IUserService;
import com.how2java.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith ( SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations ={"classpath:applicationContext.xml"})
public class TestAnnoTable {
    @Autowired
    private IUserService userService;

    @Autowired
    private ISolderDao solderDao;

    @Test
    public void test(){
        User user=new User();
        user.setId(1);
        User user1=userService.findByid(1);
        System.out.println("查询成功");
        System.out.println(user1.getName());
        System.out.println("主世界");
       // 分页查询
        List <Soldier> soldiers=userService.findPartSoldier(1,0,5); // 主
        for(Soldier soldier:soldiers) {
            System.out.println(soldier);
            System.out.println(soldier.getArm().getName());// 兵种名称
        }
        // 夜世界
        System.out.println("夜世界");
        List<Soldier> ns=userService.findPartSoldierNight(1,0,5);
        for(Soldier soldier:ns) {
            System.out.println(soldier);
            System.out.println(soldier.getArm().getName());// 兵种名称
        }
    }
    @Test
    public void testSOldierAdd(){
        Soldier soldier=new Soldier();

        soldier.setName("野猪飞骑");
        soldier.setUid(4);
        soldier.setDamageSecond(320);
        soldier.setHp(40);
        soldier.setLevel(4);
        soldier.setAid(2);
        soldier.setHolyWater("0");
        solderDao.add(soldier);
        System.out.println("添加成功");
    }

    @Test
    public void testSoldierDelete(){

        System.out.println(1+(int)(Math.random()*5));
    }

    @Test
    public void testfind(){
        Soldier soldier=solderDao.findById(88);
        System.out.println(soldier);

        Soldier soldier1=solderDao.findByUidAndName(1,"弓箭女皇");
        System.out.println(soldier1);
    }
}
