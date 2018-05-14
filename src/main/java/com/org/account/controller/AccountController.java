package com.org.account.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.org.account.exception.AbstractAccountException;
import com.org.account.service.AccountService;

@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;

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
	public ResponseEntity<?> createTestAccounts() {
		return new ResponseEntity<Object>(accountService.createTestData(), HttpStatus.OK);
	}

	/**
	 * Call service to get all available account.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getAllAccounts", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllAccounts() {
		return new ResponseEntity<Object>(accountService.allAccount(), HttpStatus.OK);
	}

	/**
	 * Get specific account information.
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/accountInfo", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> getAccountInfo(@RequestParam("name") String name) {
		return new ResponseEntity<Object>(accountService.findaccount(name), HttpStatus.OK);
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
	public ResponseEntity<?> newAccount(@RequestParam("name") String name,
			@RequestParam("initialBalance") Double initialBalance) throws AbstractAccountException {
		return new ResponseEntity<Object>(this.accountService.createNewAccount(name, initialBalance), HttpStatus.OK);
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
	public ResponseEntity<?> deposit(@RequestParam("name") String name, @RequestParam("value") Double value)
			throws AbstractAccountException {
		return new ResponseEntity<Object>(this.accountService.deposit(name, value), HttpStatus.OK);
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
	public ResponseEntity<?> withdraw(@RequestParam("name") String name, @RequestParam("value") Double value)
			throws AbstractAccountException {
		return new ResponseEntity<Object>(this.accountService.withdraw(name, value), HttpStatus.OK);

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
	public ResponseEntity<?> transfer(@RequestParam("from") String from, @RequestParam("to") String to,
			@RequestParam("value") Double value) throws AbstractAccountException {
		return new ResponseEntity<Object>(this.accountService.transfer(from, to, value), HttpStatus.OK);
	}

}
