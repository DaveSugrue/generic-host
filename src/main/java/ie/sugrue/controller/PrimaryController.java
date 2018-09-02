package ie.sugrue.controller;

import org.springframework.web.bind.annotation.CrossOrigin;

//@CrossOrigin(origins = "http://desktop:5000", maxAge = 3600)
//@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@CrossOrigin(origins = "*", maxAge = 3600)
public class PrimaryController {

	// ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
	// MySQLUserRepositoryImpl mySQLUserRepositoryImpl = (MySQLUserRepositoryImpl) context.getBean("MySQLUserRepositoryImpl");

}
