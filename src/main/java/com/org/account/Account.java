package com.org.account;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Account {
	private String name;
	private Double balance;

	public Account() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Account(String name, Double balance) {
		super();
		this.name = name;
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "[Name = " + name + ", Current Balance = EU " + balance + "]";
	}
	
	

}
