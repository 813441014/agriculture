package com.qpp.admin.mapper.log;

import com.qpp.admin.entity.system.SysLog;
import com.qpp.common.annotation.datasource.DataSource;
import com.qpp.common.constant.enums.DataSourceType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);

    @DataSource(DataSourceType.LOG)
    List<SysLog> selectListByPage(SysLog sysLog);
}