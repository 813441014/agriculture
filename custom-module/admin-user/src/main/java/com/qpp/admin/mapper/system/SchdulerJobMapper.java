package com.qpp.admin.mapper.system;

import com.qpp.admin.entity.system.SchdulerJob;
import com.qpp.basic.base.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchdulerJobMapper extends BaseMapper<SchdulerJob, String> {
    int deleteByPrimaryKey(String id);

    int insert(SchdulerJob record);

    int insertSelective(SchdulerJob record);

    SchdulerJob selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SchdulerJob record);

    int updateByPrimaryKey(SchdulerJob record);

    @Override
    @Select("SELECT * FROM t_schduler_job")
    List<SchdulerJob> selectListByPage(SchdulerJob record);

}