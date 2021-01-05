package service;

import dao.DaoException;
import dao.UserDao;
import domain.User;
import util.UserSortState;

import java.util.Comparator;
import java.util.List;

public class UserServiceImpl extends BaseServiceImpl implements UserService {
	private UserDao userDao;
	
	@Override
	public List<User> findAll(UserSortState sortState) throws ServiceException {
		try {
			List<User> users = userDao.readAll();
			if(sortState != null)
				sort(users, sortState);
			return users;
		} catch(DaoException e) {
			throw new ServiceException(e);
		}
	}
	
	private void sort(List<User> users, UserSortState sortState) {
		switch(sortState) {
		case LoginAsc:
			users.sort(Comparator.comparing(User::getLogin, (s1, s2) -> {
				return s1.compareTo(s2);
			}));
			break;
		case LoginDesc:
			users.sort(Comparator.comparing(User::getLogin, (s1, s2) -> {
				return s2.compareTo(s1);
			}));
			break;
		case RoleAsc:
			users.sort(Comparator.comparing(User::getRole, (s1, s2) -> {
				return s1.getName().compareTo(s2.getName());
			}));
			break;
		case RoleDesc:
			users.sort(Comparator.comparing(User::getRole, (s1, s2) -> {
				return s2.getName().compareTo(s1.getName());
			}));
			break;				
		default:
			break;			
		}
	}

	@Override
	public User findById(Long id) throws ServiceException {
		try {
			return userDao.read(id);
		} catch(DaoException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public User find(String login, String password) throws ServiceException {
		try {
			return userDao.find(login, password);
		} catch(DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void save(User user) throws ServiceException {
		try {
			getTransaction().transactionStart();
			if(user.getId() != null) {
				userDao.update(user);
			} else {
				userDao.create(user);
			}
			getTransaction().transactionCommit();
		} catch(DaoException e) {
			getTransaction().transactionRollback();
			throw new ServiceException(e);
		}
	}

	@Override
	public void delete(Long id) throws ServiceException {
		try {
			getTransaction().transactionStart();
			userDao.delete(id);
			getTransaction().transactionCommit();
		} catch(DaoException e) {
			getTransaction().transactionRollback();
			throw new ServiceException(e);
		}
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
