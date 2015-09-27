package com.blogspot.iserf.model;

public class Transaction {

	private int transactionId;
	private int accountId;
	private double money;
	private String operation;
	private String date;
	
	public Transaction(int accountId){
		this.accountId = accountId;
	}
	
	public Transaction(){

	}
	
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}


}
