package service;

import domain.Role;

import java.util.List;

public interface RoleService {	
	List<Role> findAll() throws ServiceException;
	Role findById(Long id) throws ServiceException;
	void save(Role role) throws ServiceException;
	void delete(Long id) throws ServiceException;
}
