package com.blogspot.iserf.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TransactionDisplay extends  Transaction{

	protected String accountOwnerFirstname;
	protected String accountOwnerLastname;
	protected int userId;

	public String getAccountOwnerFirstname() {
		return accountOwnerFirstname;
	}

	public void setAccountOwnerFirstname(String accountOwnerFirstname) {
		this.accountOwnerFirstname = accountOwnerFirstname;
	}


	public String getAccountOwnerLastname() {
		return accountOwnerLastname;
	}

	public void setAccountOwnerLastname(String accountOwnerLastname) {
		this.accountOwnerLastname = accountOwnerLastname;
	}


	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}





}
