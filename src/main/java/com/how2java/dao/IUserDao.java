package com.how2java.dao;

import com.how2java.enity.User;
import com.how2java.enity.Soldier;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserDao {

    @Select("select * from user")
    @Results(id="userMap",value = {
            @Result(id=true,column = "id",property = "id"),
            @Result(column = "name",property = "name"),
            @Result(column = "password",property = "password"),
            @Result(property = "solders",column="id",
                    many =@Many(select = "com.how2java.dao.ISolderDao.findSoldierByUid",
                            fetchType = FetchType.LAZY))
    })
    List<User> findAll();
    // 保存用户
    @Insert("insert into user(name,password) values(#{name},#{password});")
    @SelectKey(statement = "select last_insert_id()",keyProperty = "id",keyColumn = "id",before = false,resultType =Integer.class )
    void saveUser(User user);

    // 根据用户名查询
    @Select("select * from user where name=#{name};")
    User findByName(String name);

    // 根据用户id查询所有兵种
    @Select("select * from user where id=#{id}")
    @ResultMap("userMap")
    User findAllSolider(Integer id);

    // 根据id查询所有兵种,进行分页处理(主世界)
    @Select("SELECT s.*,a.* FROM soldier s JOIN USER u\n" +
            "ON s.`uid`=u.`id`\n" +
            "JOIN arms a\n" +
            "ON s.`aid`=a.`id`\n" +
            "WHERE uid=#{id}\n" +
            "and aid in (3,4,5)\n" +
            "order by s.id desc\n"+
            "LIMIT #{start},#{count};")
    @Results(id="soldierMap",value = {
            @Result(id=true,column = "id",property = "id"),
            @Result(column = "name",property = "name"),
            @Result(column = "damageSecond",property = "damageSecond"),
            @Result(column = "hp",property = "hp"),
            @Result(column = "level",property = "level"),
            @Result(column = "holyWater",property = "holyWater"),
            @Result(property = "arm",column = "aid",
                    one=@One(select = "com.how2java.dao.IArmsDao.findByAid",fetchType = FetchType.EAGER ) )
    })
    List<Soldier> findPartSolider(@Param("id")Integer id, @Param("start") int start, @Param("count")int count);
    // 根据id查询所有兵种,进行分页处理(夜世界)
    @Select("SELECT s.*,a.* FROM soldier s JOIN USER u\n" +
            "ON s.`uid`=u.`id`\n" +
            "JOIN arms a\n" +
            "ON s.`aid`=a.`id`\n" +
            "WHERE uid=#{id}\n" +
            "and aid in (1,2)\n"+
            " order by s.id desc\n"+
            "LIMIT #{start},#{count};")
    @ResultMap("soldierMap")
    List<Soldier> findPartSoliderNight(@Param("id")Integer id, @Param("start") int start, @Param("count")int count);

}
