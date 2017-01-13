package com.blogspot.iserf.dao;

import java.util.List;
import com.blogspot.iserf.model.UserModel;

//CRUD operations
public interface UserDAO {
	
	//Create
	public void save(UserModel user);
	//Read
	public UserModel getById(int id);
	//Update
	public void update(UserModel user);
	//Delete
	public void deleteById(int id);
	//Get All
	public List<UserModel> getAll();
}
