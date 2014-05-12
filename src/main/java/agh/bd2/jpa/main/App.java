package agh.bd2.jpa.main;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import sun.util.resources.CalendarData_pl;
import agh.bd2.jpa.pojo.Employee;
import agh.bd2.jpa.pojo.Company;
import agh.bd2.jpa.pojo.ForumPost;
import agh.bd2.jpa.pojo.ForumThread;
import agh.bd2.jpa.pojo.Task;
import agh.bd2.jpa.pojo.ForumUser;

public class App {
	private static EntityManagerFactory entityManagerFactory;
	
	public static void setUp(){
		entityManagerFactory = Persistence.createEntityManagerFactory( "agh.bd2.jpa.Hibernate_UNIT" );
	//	entityManagerFactory = Persistence.createEntityManagerFactory( "agh.bd2.jpa.TopLink_UNIT" );
	}
	
    public static void main( String[] args )  {
    	setUp();
    	EntityManager entityManager = entityManagerFactory.createEntityManager();
    	
    	Company company = new Company("Company");
    	Employee employee1 = new Employee("Employee1", "Surname", new BigDecimal( 5000 ), company); 
    	Employee employee2 = new Employee("Employee2", "Surname", new BigDecimal( 5000 ), company); 
    	Employee employee3 = new Employee("Employee3", "Surname", new BigDecimal( 5000 ), company); 
    	Task task1 = new Task("task1");
    	Task task2 = new Task("task2");
    	task1.addEmployeesResponsible(employee1).addEmployeesResponsible(employee2).addEmployeesResponsible(employee3);
    	task2.addEmployeesResponsible(employee1);
    	
    	Calendar date1 = Calendar.getInstance();
    	date1.set(1992, 6, 5);
    	Calendar date2 = Calendar.getInstance();
    	date2.set(2012, 10, 10);
    	Calendar date3 = Calendar.getInstance();
    	
		ForumUser user = new ForumUser("jan","Kielce",date1);
    	ForumThread thread = new ForumThread("O dziwach i dźwigłach", date2, user);
    	ForumPost post = new ForumPost(thread, "Był sobie raz jeden pewien miś", date3, user);
    	
    	entityManager.getTransaction().begin();
    	entityManager.persist(post);
    	entityManager.persist(thread);
    	entityManager.persist(user);
    	entityManager.persist(company);   // save employer and all employees in db thanks to CascadeType.All 
    	entityManager.persist(task1);
    	entityManager.persist(task2);
     	entityManager.getTransaction().commit();
    	
    	
    	List<Employee> employees = entityManager
    			.createQuery("select empl from Employee empl",Employee.class)
    			.getResultList();    //get object by query 
    	
    	entityManager.getTransaction().begin();
	    for (Employee employee : employees){
	    	employee.setSalary(new BigDecimal( 6000 ));
	    	entityManager.merge(employee);		// update object in db
    	}
    	entityManager.getTransaction().commit();
    	
    	 	
    	System.out.println("delete employee");
    	Employee employee = entityManager.find(Employee.class, 1L);		// get object by primary key
    	
//    	entityManager.getTransaction().begin();
//    	employee.removeAllTask(entityManager); // drop fk
//    	employee.removeEmployer();
//    	entityManager.remove(employee); // remove object
//    	entityManager.getTransaction().commit();   	
    
    	entityManager.close();
    	entityManagerFactory.close();
    }
}
