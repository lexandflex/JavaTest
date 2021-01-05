package transaction;

public interface Transaction {	
	void transactionStart() throws TransactionException;
	void transactionCommit() throws TransactionException;
	void transactionRollback() throws TransactionException;
}
