package com.blogspot.iserf;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
	private String name;
	private String password;
    private ArrayList<HashMap<String, String>> citizenData; 
	private int userId;
	
    public ArrayList<HashMap<String, String>> getCitizenData() {
		return citizenData;
	}
    public ArrayList<String> getCitizenData2() {
    	ArrayList<String> poks = new ArrayList<String>();
    	poks.add("Berta");
    	poks.add("Laura");
    	poks.add("Francheska");
    	poks.add("Limona");
		return poks;
	}
    
    public HashMap<String, String> getHashmap() {
    	HashMap<String, String> hashmap = new HashMap<String, String>();
    	hashmap.put("fistitem","Artem");
    	hashmap.put("second","Grisha");
    	hashmap.put("third","Minder");
		return hashmap;
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
	

}
