package com.blogspot.iserf.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TransactionDisplay extends  Transaction{

	protected String accountOwner;
	protected int userId;


	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAccountOwner() {
		return accountOwner;
	}

	public void setAccountOwner(String name) {
		this.accountOwner = name;
	}




}
