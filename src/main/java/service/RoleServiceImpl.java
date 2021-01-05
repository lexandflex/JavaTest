package service;

import dao.DaoException;
import dao.RoleDao;
import domain.Role;

import java.util.List;

public class RoleServiceImpl extends BaseServiceImpl implements RoleService {
	private RoleDao roleDao;
	
	@Override
	public List<Role> findAll() throws ServiceException {
		try {
			return roleDao.readAll();
		} catch(DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Role findById(Long id) throws ServiceException {
		try {
			return roleDao.read(id);
		} catch(DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void save(Role role) throws ServiceException {
		try {
			getTransaction().transactionStart();
			if(role.getId() != null) {
				roleDao.update(role);
			} else {
				roleDao.create(role);
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
			roleDao.delete(id);
			getTransaction().transactionCommit();
		} catch(DaoException e) {
			getTransaction().transactionRollback();
			throw new ServiceException(e);
		}
	}
	
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
}
