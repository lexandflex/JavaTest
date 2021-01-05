package transaction;

import service.ServiceException;

@SuppressWarnings("serial")
public class TransactionException extends ServiceException {
	public TransactionException(Throwable cause) {
		super(cause);
	}
}
