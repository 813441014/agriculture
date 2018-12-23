package com.qpp.common.utils;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

import java.util.Map;

public final class RegulationUtil {
	
	private static JexlEngine jexl = new JexlEngine(); 
	
	static {
		jexl.setDebug(false);
	}

	public static Object invokeMethod(String jexlExp, Map<String, Object> map) {  
        Expression e = jexl.createExpression(jexlExp);  
        JexlContext jc = new MapContext();  
        if(map != null) {
        	for(String key : map.keySet()) {
        		jc.set(key, map.get(key));  
        	}  
        }
        if(null == e.evaluate(jc)){
            return "";  
        }  
        return e.evaluate(jc); 
    }
	
}
