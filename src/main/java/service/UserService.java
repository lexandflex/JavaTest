package service;

import domain.User;
import util.UserSortState;

import java.util.List;

public interface UserService {	
	List<User> findAll(UserSortState sortState) throws ServiceException;
	User findById(Long id) throws ServiceException;
	User find(String login, String password) throws ServiceException;
	void save(User user) throws ServiceException;
	void delete(Long id) throws ServiceException;
}
