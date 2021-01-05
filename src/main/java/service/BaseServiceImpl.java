package service;

import transaction.Transaction;

abstract public class BaseServiceImpl {
	
	protected Transaction transaction;
	
	protected Transaction getTransaction() {
		return transaction;
	}
	
	protected void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

}
