package com.blogspot.iserf;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
	private String name;
	private String password;
    private ArrayList<HashMap<String, String>> citizenData; 
	private int userId;
	private String pageTitle;
	
    public ArrayList<HashMap<String, String>> getCitizenData() {
		return citizenData;
	}

    
	public void setCitizenData(ArrayList<HashMap<String, String>> citizenData) {
		this.citizenData = citizenData;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getPageTitle() {
		return pageTitle;
	}


	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	

}
