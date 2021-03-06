package com.blogspot.iserf.model;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class Breadcrumbs extends ArrayList<Link> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4287941703292165564L;
	private HttpServletRequest request;
	
	public Breadcrumbs(HttpServletRequest request){
		this.request = request;	
		this.add(new Link("home", getRootURLWithContextPath(request)));
		
	}
	
	
	private  String getRootURLWithContextPath(HttpServletRequest request) {
		   return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}
	
	// 
	public void add(String name, String urlPart){
		
		this.add(new Link(name, getRootURLWithContextPath(request)+urlPart));
	}
	
	
	// add last (current) page
	public void add(String name){
		
		this.add(new Link(name, null));
	}
	
}
