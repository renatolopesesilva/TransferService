package com.org.account.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.org.account.Account;
import com.org.account.exception.AbstractAccountException;
import com.org.account.exception.AccountWithDrawException;
import com.org.account.exception.InvalidAccountException;
import com.org.account.exception.InvalidValueException;

@Service
public class AccountService {

	private static Map<String, Account> ACCOUNTS = new LinkedHashMap<String, Account>();

	public AccountService() {

	}

	/**
	 * Create new Account.
	 * 
	 * @param name
	 * @param initialValue
	 * @return
	 * @throws AbstractAccountException
	 */
	public Account createNewAccount(String name, Double initialValue) throws AbstractAccountException {
		if (findaccount(name) != null) {
			throw new InvalidAccountException("Account name already exists! Name: " + name);
		}

		Account newAcc = new Account(name, initialValue);
		addNewAccount(newAcc);
		return newAcc;

	}

	/**
	 * Deposit a value in account.
	 * 
	 * @param acc
	 * @param value
	 * @return
	 * @throws InvalidValueException
	 */
	public Account deposit(String name, Double value) throws AbstractAccountException {

		Account acc = findaccount(name);

		validateAccounts(new String[] { name });

		validateValue(value);

		acc.setBalance(acc.getBalance() + value);
		return acc;
	}

	/**
	 * Withdraw value from an account.
	 * 
	 * @param acc
	 * @param value
	 * @return
	 * @throws AbstractAccountException
	 */
	public Account withdraw(String name, Double value) throws AbstractAccountException {

		Account acc = findaccount(name);
		validateAccounts(new String[] { name });

		validateValue(value);

		if (acc.getBalance() - value < 0) {
			throw new AccountWithDrawException("Not possible to perform Withdraw. Not enough money." + acc.toString());
		}

		acc.setBalance(acc.getBalance() - value);
		return acc;
	}

	/**
	 * Validate value, if it is lower than 0, it's not valid.
	 * 
	 * @param value
	 * @throws InvalidValueException
	 */
	private void validateValue(Double value) throws InvalidValueException {
		if (value < 0) {
			throw new InvalidValueException("Value must be Positive");
		}
	}

	/**
	 * Search an account and Return if it exists, if not return null.
	 * 
	 * @param name
	 * @return
	 */
	public Account findaccount(String name) {
		return ACCOUNTS.get(name);
	}

	/**
	 * Add new account in Map.
	 * 
	 * @param acc
	 */
	private void addNewAccount(Account acc) {
		ACCOUNTS.put(acc.getName(), acc);
	}

	/**
	 * Create Test account with initial balance.
	 */
	public List<Account> createTestData() {
		ACCOUNTS.put("Jim", new Account("Jim", 100.0));
		ACCOUNTS.put("Tom", new Account("Tom", 5.1));
		ACCOUNTS.put("Kim", new Account("Kim", 112.43));
		ACCOUNTS.put("Ash", new Account("Ash", 1233.57));
		ACCOUNTS.put("Patty", new Account("Patty", 53451.54));
		ACCOUNTS.put("Julia", new Account("Julia", 1.1));

		return new ArrayList<Account>(ACCOUNTS.values());
	}

	/**
	 * Return a list with all available accounts.
	 * 
	 * @return
	 */
	public List<Account> allAccount() {
		return new ArrayList<Account>(ACCOUNTS.values());
	}

	/**
	 * Transfer money from one account to another. Value can't be less than 0.
	 * Both accounts must exists. Account can't be with negative value
	 * (overdraw).
	 * 
	 * @param nameFrom
	 * @param nameTo
	 * @param value
	 * @return
	 * @throws AbstractAccountException
	 */
	public Object transfer(String nameFrom, String nameTo, Double value) throws AbstractAccountException {
		validateValue(value);

		validateAccounts(new String[] { nameFrom, nameTo });

		// Remove value from an account, and check if it has enough balance, if
		// not
		// return an error
		Account accFrom = withdraw(nameFrom, value);

		// Add money to destination account
		Account accTo = deposit(nameTo, value);

		StringBuilder msg = new StringBuilder();

		msg.append("Transfer sucefully registered.");
		msg.append(" | From Account : ").append(nameFrom).append(" Current Balance : EU ").append(accFrom.getBalance());
		msg.append(" | To Account : ").append(nameTo).append(" Current Balance : EU ").append(accTo.getBalance());
		return new JSONPObject("response", msg.toString());
	}

	/**
	 * Validate if account exists
	 * 
	 * @param strings
	 * @throws InvalidAccountException
	 */
	public boolean validateAccounts(String[] strings) throws InvalidAccountException {
		for (String acc : strings) {
			if (findaccount(acc) == null) {
				throw new InvalidAccountException("Account does not exists " + acc);
			}
		}
		return true;
	}
}