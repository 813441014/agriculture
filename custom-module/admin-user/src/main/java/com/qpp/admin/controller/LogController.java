package com.qpp.admin.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qpp.admin.entity.system.SysLog;
import com.qpp.admin.mapper.log.SysLogMapper;
import com.qpp.basic.base.BaseController;
import com.qpp.basic.exception.MyException;
import com.qpp.basic.util.JsonUtil;
import com.qpp.basic.util.ReType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * @author qipengpai
 *
 * 日志监控
 */
@Slf4j
@Controller
@RequestMapping(value = "/log")
public class LogController  extends BaseController {
    @Autowired
    private SysLogMapper logMapper;

    @GetMapping(value = "showLog")
    public String showMenu(Model model){
        return "/system/log/logList";
    }

    /**
     * 日志监控
     * @param sysLog
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "showLogList")
    @ResponseBody
    public String showLog(SysLog sysLog, String page, String limit){
        List<SysLog> tList=null;
        Page<SysLog> tPage= PageHelper.startPage(Integer.valueOf(page),Integer.valueOf(limit));
        try{
            tList=logMapper.selectListByPage(sysLog);
        }catch (MyException e){
            log.error("[LogController]{showLog} -> error",e);
        }
        ReType reType=new ReType(tPage.getTotal(),tList);
        return JSON.toJSONString(reType);
    }

    /**
     * 删除log
     * @param
     * @return
     */
    @PostMapping(value = "del")
    @ResponseBody
    public JsonUtil del(String[] ids){
        JsonUtil j=new JsonUtil();
        String msg="删除成功";
        try{
            for(String id:ids)
                logMapper.deleteByPrimaryKey(Integer.valueOf(id));
        }catch (MyException e){
            log.error("[LogController]{showLog} -> error",e);
        }
        j.setMsg(msg);
        return j;
    }


}
