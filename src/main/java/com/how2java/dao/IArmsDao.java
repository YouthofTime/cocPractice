package com.how2java.dao;

import com.how2java.enity.arms;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface IArmsDao {

    // 根据士兵查询对应的兵种
    @Select("select * from arms where id=#{aid}")
    arms findByAid(Integer aid);
}
