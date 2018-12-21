package com.qpp.eureka.filter;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.ClientFilter;

public class IpCilentFilter extends ClientFilter {
    @Override
    public ClientResponse handle(ClientRequest clientRequest) throws ClientHandlerException {
        //响应对象的处理
    	
    	if ("127.0.0.1:1112".equals(clientRequest.getURI().getAuthority())){
    		return null ;
    	}
    	
        ClientResponse response = this.getNext().handle(clientRequest);
        return response;
    }
}
 