package com.blogspot.iserf.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SendMoney  extends  Transaction{

	@NotNull
	@Min(1)
	protected int sendToAccountId;


	public int getSendToAccountId() {
		return sendToAccountId;
	}

	public void setSendToAccountId(int sendToAccountId) {
		this.sendToAccountId = sendToAccountId;
	}

	public SendMoney(int accountId){
		this.accountId = accountId;
		this.add = false;
	}

	public SendMoney(){

	}

}
