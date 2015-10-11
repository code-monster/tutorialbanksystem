package com.blogspot.iserf.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Transaction {

	protected int transactionId;
	protected int accountId;
	protected boolean add;
  
    @NotNull
    @Min(1)
	protected double money;

	@Size(min = 3, max=30, message = "Please enter at least 3 characters")
	protected String operation;
	protected String date;
	
	public Transaction(int accountId, boolean add){
		this.accountId = accountId;
		this.add = add;
	}
	
	public Transaction(){

	}

	public Transaction(SendMoney sendMoney) {
		accountId = sendMoney.getSendToAccountId();
		add  = true;
		money = sendMoney.getMoney();
		operation = "received money from account Id = " + sendMoney.getAccountId();
		date = sendMoney.getDate();

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

	public boolean isAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}
}
