package transaction;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionImpl implements Transaction {
	
	private Connection connection;
	
	@Override
	public void transactionStart() throws TransactionException {
		try {
			connection.setAutoCommit(false);
		}
		catch(SQLException e) {
			throw new TransactionException(e);
		}
	}
	
	@Override
	public void transactionCommit() throws TransactionException {
		try {
			connection.commit();
			connection.setAutoCommit(true);
		}
		catch(SQLException e) {
			throw new TransactionException(e);
		}
	}
	
	@Override
	public void transactionRollback() throws TransactionException {
		try {
			connection.rollback();
			connection.setAutoCommit(true);
		}
		catch(SQLException e) {
			throw new TransactionException(e);
		}
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	protected Connection getConnection() {
		return connection;
	}

}
