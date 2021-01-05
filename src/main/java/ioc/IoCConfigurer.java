package ioc;

import web.ActionFactory;

import java.util.HashMap;
import java.util.Map;

public class IoCConfigurer {
	public static void configure() throws IoCException {
		/* registration of actions */
		IoCContainer.registerFactory("web.Action", "web.ActionFactory");

		Map<String, String> facultyActionsDependencies = new HashMap<>();
		facultyActionsDependencies.put("service.FacultyService", "setFacultyService");
		ActionFactory.registerAction("/faculty/index", "web.faculty.FacultyIndexActionImpl");
		DIContainer.registerClass("web.faculty.FacultyIndexActionImpl", facultyActionsDependencies);
		ActionFactory.registerAction("/faculty/edit", "web.faculty.FacultyEditActionImpl");
		DIContainer.registerClass("web.faculty.FacultyEditActionImpl", facultyActionsDependencies);
		ActionFactory.registerAction("/faculty/save", "web.faculty.FacultySaveActionImpl");
		DIContainer.registerClass("web.faculty.FacultySaveActionImpl", facultyActionsDependencies);
		ActionFactory.registerAction("/faculty/delete", "web.faculty.FacultyDeleteActionImpl");
		DIContainer.registerClass("web.faculty.FacultyDeleteActionImpl", facultyActionsDependencies);
		
		Map<String, String> chairActionsDependencies1 = new HashMap<>();
		chairActionsDependencies1.put("service.ChairService", "setChairService");
		Map<String, String> chairActionsDependencies2 = new HashMap<>();
		chairActionsDependencies2.put("service.ChairService", "setChairService");
		chairActionsDependencies2.put("service.FacultyService", "setFacultyService");

		ActionFactory.registerAction("/chair/index", "web.chair.ChairIndexActionImpl");
		DIContainer.registerClass("web.chair.ChairIndexActionImpl", chairActionsDependencies2);
		ActionFactory.registerAction("/chair/edit", "web.chair.ChairEditActionImpl");
		DIContainer.registerClass("web.chair.ChairEditActionImpl", chairActionsDependencies2);
		ActionFactory.registerAction("/chair/save", "web.chair.ChairSaveActionImpl");
		DIContainer.registerClass("web.chair.ChairSaveActionImpl", chairActionsDependencies2);
		ActionFactory.registerAction("/chair/delete", "web.chair.ChairDeleteActionImpl");
		DIContainer.registerClass("web.chair.ChairDeleteActionImpl", chairActionsDependencies1);
		
		Map<String, String> userActionsDependencies1 = new HashMap<>();
		userActionsDependencies1.put("service.UserService", "setUserService");
		Map<String, String> userActionsDependencies2 = new HashMap<>();
		userActionsDependencies2.put("service.UserService", "setUserService");
		userActionsDependencies2.put("service.RoleService", "setRoleService");
		
		ActionFactory.registerAction("/user/index", "web.user.UserIndexActionImpl");
		DIContainer.registerClass("web.user.UserIndexActionImpl", userActionsDependencies1);
		ActionFactory.registerAction("/user/edit", "web.user.UserEditActionImpl");
		DIContainer.registerClass("web.user.UserEditActionImpl", userActionsDependencies2);
		ActionFactory.registerAction("/user/save", "web.user.UserSaveActionImpl");
		DIContainer.registerClass("web.user.UserSaveActionImpl", userActionsDependencies2);
		ActionFactory.registerAction("/user/delete", "web.user.UserDeleteActionImpl");
		DIContainer.registerClass("web.user.UserDeleteActionImpl", userActionsDependencies1);
		
		Map<String, String> authActionsDependencies = new HashMap<>();
		authActionsDependencies.put("service.UserService", "setUserService");
		ActionFactory.registerAction("/login", "web.auth.LoginActionImpl");
		DIContainer.registerClass("web.auth.LoginActionImpl", authActionsDependencies);
		ActionFactory.registerAction("/logout", "web.auth.LogoutActionImpl");
		
		/* registration of factory for connections */
		IoCContainer.registerFactory("java.sql.Connection", "pool.ConnectionFactory");
		
		Map<String, String> transactionDependencies = new HashMap<>();
		transactionDependencies.put("java.sql.Connection", "setConnection");
		IoCContainer.registerClass("transaction.Transaction", "transaction.TransactionImpl");
		DIContainer.registerClass("transaction.TransactionImpl", transactionDependencies);

		/* registration of DAO */
		Map<String, String> daoDependencies = new HashMap<>();
		daoDependencies.put("java.sql.Connection", "setConnection");
		
		//IoCContainer.registerClass("dao.FacultyDao", "dao.fake.FacultyDaoFakeImpl");
		//IoCContainer.registerClass("dao.ChairDao", "dao.fake.ChairDaoFakeImpl");
		
		IoCContainer.registerClass("dao.FacultyDao", "dao.pgsql.FacultyPostgreSqlDaoImpl");
		DIContainer.registerClass("dao.pgsql.FacultyPostgreSqlDaoImpl", daoDependencies);
		IoCContainer.registerClass("dao.ChairDao", "dao.pgsql.ChairPostgreSqlDaoImpl");
		DIContainer.registerClass("dao.pgsql.ChairPostgreSqlDaoImpl", daoDependencies);
		IoCContainer.registerClass("dao.UserDao", "dao.pgsql.UserPostgreSqlDaoImpl");
		DIContainer.registerClass("dao.pgsql.UserPostgreSqlDaoImpl", daoDependencies);
		IoCContainer.registerClass("dao.RoleDao", "dao.pgsql.RolePostgreSqlDaoImpl");
		DIContainer.registerClass("dao.pgsql.RolePostgreSqlDaoImpl", daoDependencies);

		Map<String, String> facultyServiceDependencies = new HashMap<>();
		facultyServiceDependencies.put("transaction.Transaction", "setTransaction");
		facultyServiceDependencies.put("dao.FacultyDao", "setFacultyDao");	
		IoCContainer.registerClass("service.FacultyService", "service.FacultyServiceImpl");
		DIContainer.registerClass("service.FacultyServiceImpl", facultyServiceDependencies);

		Map<String, String> chairServiceDependencies = new HashMap<>();
		chairServiceDependencies.put("transaction.Transaction", "setTransaction");
		chairServiceDependencies.put("dao.FacultyDao", "setFacultyDao");
		chairServiceDependencies.put("dao.ChairDao", "setChairDao");	
		IoCContainer.registerClass("service.ChairService", "service.ChairServiceImpl");
		DIContainer.registerClass("service.ChairServiceImpl", chairServiceDependencies);
		
		Map<String, String> userServiceDependencies = new HashMap<>();
		userServiceDependencies.put("transaction.Transaction", "setTransaction");
		userServiceDependencies.put("dao.UserDao", "setUserDao");
		IoCContainer.registerClass("service.UserService", "service.UserServiceImpl");
		DIContainer.registerClass("service.UserServiceImpl", userServiceDependencies);
		
		Map<String, String> roleServiceDependencies = new HashMap<>();
		roleServiceDependencies.put("transaction.Transaction", "setTransaction");
		roleServiceDependencies.put("dao.RoleDao", "setRoleDao");
		IoCContainer.registerClass("service.RoleService", "service.RoleServiceImpl");
		DIContainer.registerClass("service.RoleServiceImpl", roleServiceDependencies);
	}
}