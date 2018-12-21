package com.qpp.admin.service.system.impl;

import com.qpp.admin.entity.system.SysJob;
import com.qpp.admin.mapper.system.SysJobMapper;
import com.qpp.admin.service.system.JobService;
import com.qpp.basic.base.BaseMapper;
import com.qpp.basic.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author langmingsheng
 */
@Service
public class JobServiceImpl extends BaseServiceImpl<SysJob,String> implements JobService {

  @Autowired
  SysJobMapper jobMapper;
  @Override
  public BaseMapper<SysJob, String> getMappser() {
    return jobMapper;
  }
}
