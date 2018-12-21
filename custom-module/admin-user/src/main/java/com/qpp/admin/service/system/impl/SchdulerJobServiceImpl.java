package com.qpp.admin.service.system.impl;


import com.qpp.admin.entity.system.SchdulerJob;
import com.qpp.admin.mapper.system.SchdulerJobMapper;
import com.qpp.admin.service.system.SchdulerJobService;
import com.qpp.basic.base.BaseMapper;
import com.qpp.basic.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName SchdulerJobServiceImpl
 * @Description TODO
 * @Author bamboo
 * @Date 2018/7/3 10:11
 * @Version 1.0
 **/
@Service
public class SchdulerJobServiceImpl extends BaseServiceImpl<SchdulerJob, String> implements SchdulerJobService {

    @Autowired
    private SchdulerJobMapper schdulerJobMapper;

    @Override
    public BaseMapper<SchdulerJob, String> getMappser() {
        return schdulerJobMapper;
    }
}
