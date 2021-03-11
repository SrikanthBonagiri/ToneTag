package com.tonetag.resourse;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/dialogflow")
public class DialogService extends Application {
	
	private Set<Object> obj = new HashSet<Object>();
	
	public DialogService() {
		this.obj.add(new DialogResource());
	}
	
	@Override
	public Set<Object> getSingletons() {
		// TODO Auto-generated method stub
		return obj;
	}

}
