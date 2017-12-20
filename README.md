## TransferService

To initialize the service:

Download file `/targer/accounts-1.0.jar` and execute:

> java -jar accounts-1.0.jar

I'll initilize a service that listens por `8080`

###### Available services:

 - GET: /createTestAccounts
 - GET: /getAllAccounts
 - POST: /newAccount - Parameters: [name,initialBalance]
 - POST: /deposit - Parameters: [name,value] 
 - POST: /withdraw - Parameters: [name,value] 
 - POST: /transfer - Parameters: [from,to,value]

###### Examples 

Executing the service `GET` `http://localhots:8080/createTestAccounts` it'll create some test accounts with an initial balance, that are the following:

> name:"Jim",  balance":100.0

> name:"Tom",  balance":5.1

> name:"Kim",  balance":112.43

> name:"Ash",  balance":1233.57

> name:"Patty",balance":53451.54

> name:"Julia",balance":1.1

To create new accounts execute a `POST` that calls `http://localhots:8080/newAccount` adding the parameters:
- name
- initialBalance

Parameters Example:
- name:"Renato"
- initialBalance:10.25

To execute a transfer between accounts execute a `POST` that calls `http://localhots:8080/transfer` adding the parameters:
- from
- to 
- value

Parameters Example:
- from: Jim
- to: Kim
- value: 5.1

Validation:
- Both accounts must exist
- Value can't be negative
- An account can't be overdrawn

