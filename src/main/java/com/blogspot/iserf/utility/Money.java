package com.blogspot.iserf.utility;

public class Money {
	
	private double increase;
	private double reduce;
	private int accountId;
	
	public Money(int accountId){
		this.accountId = accountId;
	}
	
	public Money(){

	}
	
	public double getIncrease() {
		return increase;
	}

	public void setIncrease(double increase) {
		this.increase = increase;
	}

	public double getReduce() {
		return reduce;
	}

	public void setReduce(double reduce) {
		this.reduce = reduce;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}



}
