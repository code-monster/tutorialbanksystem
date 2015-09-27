package com.blogspot.iserf.model;

import java.util.ArrayList;

public class AccountList <Account> extends ArrayList {

private double totalMoney = 0;

public double getTotalMoney() {
	return totalMoney;
}

public void setTotalMoney(double totalMoney) {
	this.totalMoney = totalMoney;
}
	
	
	
}
