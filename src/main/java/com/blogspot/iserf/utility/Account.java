package com.blogspot.iserf.utility;

public class Account {

	private int accountId;
	private int userId;
	private double balance;
	private int numberOfTransaction;
	
	public double getNumberOfTransaction() {
		return numberOfTransaction;
	}
	public void setNumberOfTransaction(int numberOfTransaction) {
		this.numberOfTransaction = numberOfTransaction;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	


}
