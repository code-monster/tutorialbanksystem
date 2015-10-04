package com.blogspot.iserf.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.blogspot.iserf.utility.DB;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class Transaction {

	private int transactionId;
	private int accountId;
  
    @NotNull
    @Min(1)
	private double money;
    
    @Size(min=2, max=30)
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
