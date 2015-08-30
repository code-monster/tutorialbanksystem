package com.blogspot.iserf.utility;

public class Money {
	
	private double put;
	private double get;
	private int accountId;
	
	public Money(int accountId){
		this.accountId = accountId;
	}
	
	public Money(){

	}
	
	public double getPut() {
		return put;
	}

	public void setPut(double put) {
		this.put = put;
	}

	public double getGet() {
		return get;
	}

	public void setGet(double get) {
		this.get = get;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}



}
