package com.how2java.dao;

import com.how2java.enity.Soldier;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISolderDao {
    // 根据用户id查询所有士兵
    @Select("select * from soldier where uid=#{uid}")
    @ResultMap("soldierMap")
    List <Soldier> findSoldierByUid(Integer uid);


    @Select("select * from soldier limit #{start},#{count}")
    @ResultMap("soldierMap")
    List<Soldier> findPartSoldier(@Param("start")Integer start, @Param("count")Integer count);


    // 总兵数
    @Select("select count(*) from soldier where uid=#{uid}")
    int total(Integer uid);
    // 主世界兵数目
    @Select("select count(*) from soldier where uid=#{uid} and aid in (3,4,5) ")
    int listMain(Integer uid);

    // 夜世界兵数目
    @Select("select count(*) from soldier where uid=#{uid} and aid in (1,2)")
    int listNight(Integer uid);

    // 根据士兵的id查询士兵,加条件啊吐了
    @Select("select * from soldier where id=#{id}")
    @Results(id="soldierMap",value = {
            @Result(id=true,column = "id",property = "id"),
            @Result(column = "uid",property = "uid"),
            @Result(column = "aid",property = "aid"),
            @Result(column = "name",property = "name"),
            @Result(column = "damageSecond",property = "damageSecond"),
            @Result(column = "hp",property = "hp"),
            @Result(column = "level",property = "level"),
            @Result(column = "holyWater",property = "holyWater"),
            @Result(property = "arm",column = "aid",
            one=@One(select = "com.how2java.dao.IArmsDao.findByAid",fetchType = FetchType.EAGER ) )
    })
    Soldier findById(Integer id);

    // 根据士兵名字,用户id查询
    @ResultMap("soldierMap")
    @Select("select * from soldier where uid=#{uid} and name=#{name}")
    Soldier findByUidAndName(@Param("uid") Integer uid,@Param("name")String name);

    // 添加士兵
    @Insert("insert into soldier(name,uid,damageSecond,hp,level,aid,holyWater) " +
            "values(#{name},#{uid},#{damageSecond},#{hp},#{level},#{aid},#{holyWater});")
    void add(Soldier soldier);

    // 删除士兵
    @Delete("delete from soldier where id=#{id} and uid=#{uid}")
    void delete(@Param("id")Integer id,@Param("uid")Integer uid);

    // 升级士兵等级
    @Update("update soldier set damageSecond=#{damageSecond},hp=#{hp},level=#{level},holyWater=#{holyWater} where id=#{id}")
    void update(Soldier soldier);


}
