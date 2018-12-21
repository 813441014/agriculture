package com.qpp.eureka.controller;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;


/** 
* @author 作者 owen E-mail: 624191343@qq.com
* @version 创建时间：2017年11月28日 下午21:52:43 
* 类说明 
*/
@RestController
public class EurekaManageController {
	
	@Resource
	private ApplicationInfoManager applicationInfoManager ;
	

	//手工启停标识
	public static boolean upOrDown = true;

	
	@RequestMapping(value = "/resume", method = RequestMethod.POST)
	public void up( ) {
		ArrayList arrayList=new ArrayList();
		applicationInfoManager.getInfo().setStatus(InstanceStatus.UP);
		upOrDown = true ;
	}  
 
	
	@RequestMapping(value = "/pause", method = RequestMethod.POST)
	public void down( ) {  
		applicationInfoManager.getInfo().setStatus(InstanceStatus.DOWN);
		upOrDown = false ;
	}  

}
