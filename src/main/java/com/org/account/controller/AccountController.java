package com.org.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.org.account.Account;
import com.org.account.exception.AbstractAccountException;
import com.org.account.exception.InvalidAccountException;
import com.org.account.service.AccountService;

@RestController
public class AccountController {

	@Autowired
	private AccountService service;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public String hello() {
		StringBuilder sb = new StringBuilder();
		sb.append("Hello, welcome to Transfer Service!");
		sb.append("Available services:");
		sb.append("\n   GET:  /createTestAccounts");
		sb.append("\n   GET:  /getAllAccounts");
		sb.append("\n   GET:  /accountInfo -  Parameters: [name]");
		sb.append("\n   POST: /newAccount  -  Parameters: [name,initialBalance]");
		sb.append("\n   POST: /deposit     -  Parameters: [name,value]");
		sb.append("\n   POST: /withdraw    -  Parameters: [name,value]");
		sb.append("\n   POST: /transfer    -  Parameters: [from,to,value]");
		return sb.toString();
	}

	/**
	 * Call service to create Test account.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/createTestAccounts", method = RequestMethod.GET, produces = "application/json")
	public List<Account> createTestAccounts() {
		return service.createTestData();
	}

	/**
	 * Call service to get all available account.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getAllAccounts", method = RequestMethod.GET, produces = "application/json")
	public List<Account> getAllAccounts() {
		return service.allAccount();
	}

	/**
	 * Get specific account information.
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/accountInfo", method = RequestMethod.POST, produces = "application/json")
	public Account getAccountInfo(@RequestParam("name") String name) {
		return service.findaccount(name);
	}

	/**
	 * Create a new account with an initial balance.
	 * 
	 * @param name
	 * @param initialBalance
	 * @return
	 * @throws AbstractAccountException
	 */
	@RequestMapping(value = "/newAccount", method = RequestMethod.POST, produces = "application/json")
	public Account newAccount(@RequestParam("name") String name, @RequestParam("initialBalance") Double initialBalance)
			throws AbstractAccountException {
		return this.service.createNewAccount(name, initialBalance);
	}

	/**
	 * Add money to a account.
	 * 
	 * @param name
	 * @param value
	 * @param operation
	 * @return
	 * @throws AbstractAccountException
	 */
	@RequestMapping(value = "/deposit", method = RequestMethod.POST, produces = "application/json")
	public Account deposit(@RequestParam("name") String name, @RequestParam("value") Double value)
			throws AbstractAccountException {

		Account acc = service.findaccount(name);
		if (acc != null) {
			return this.service.deposit(acc, value);
		} else {
			throw new InvalidAccountException("Account does not exists " + acc);
		}
	}

	/**
	 * Withdraw money from an account.
	 * 
	 * @param name
	 * @param value
	 * @param operation
	 * @return
	 * @throws AbstractAccountException
	 */
	@RequestMapping(value = "/withdraw", method = RequestMethod.POST, produces = "application/json")
	public Account withdraw(@RequestParam("name") String name, @RequestParam("value") Double value)
			throws AbstractAccountException {
		Account acc = service.findaccount(name);
		if (acc != null) {
			return this.service.withdraw(acc, value);
		} else {
			throw new InvalidAccountException("Account does not exists " + acc);
		}
	}

	/**
	 * Transfer money [value] from one account [From] to another [To].
	 * 
	 * @param from
	 * @param to
	 * @param value
	 * @return
	 * @throws AbstractAccountException
	 */
	@RequestMapping(value = "/transfer", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity transfer(@RequestParam("from") String from, @RequestParam("to") String to,
			@RequestParam("value") Double value) throws AbstractAccountException {
		return new ResponseEntity<Object>(this.service.transfer(from, to, value), HttpStatus.OK);
	}

}
