package agh.bd2.jpa.pojo;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Employee {

	@Id
	@GeneratedValue
	//@Column(columnDefinition="bigint", name="id") // domyślnie ustwione parametry
	private long id;	
	@Column(columnDefinition="nvarchar(50) not null",name="Employee_name")
	private String name;
	@Column(name = "surname", length=50, nullable=false)
	private String surname;
	@Column(columnDefinition="decimal(10,2)")
	private BigDecimal salary;
	
	@ManyToOne
	@JoinColumn(name = "employer")
	private Company employer;
	
	@ManyToMany(mappedBy="employeesResponsible")
	private Set<Task> tasks;
	
	public Employee(String name, String surname, BigDecimal salary, Company employer) {
		this.name = name;
		this.surname = surname;
		this.salary = salary;
		this.employer = employer;
		this.employer.addEmployee(this);
		tasks = new HashSet<>();
	}
	@Override
	public String toString(){
		return name + " " + surname;
	}
	
	public Employee addTask(Task task){
		if (!tasks.contains(task))
			task.addEmployeesResponsible(this);
		return this;
	}
	public Employee removeTask(Task task){
		if (!tasks.contains(task))
			task.removeEmployeesResponsible(this);
		return this;
	}
	public void removeAllTask(EntityManager entityManager){
		Iterator<Task> iterator = tasks.iterator();
		while(iterator.hasNext()){
			Task task = iterator.next();
			task.removeEmployeesResponsible(this);
			entityManager.merge(task);
		}
	}
	public void removeEmployer(){
		if (employer != null){
			employer.removeEmployee(this);
		}
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public BigDecimal getSalary() {
		return salary;
	}
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	public Company getEmployer() {
		return employer;
	}
	public void setEmployer(Company employer) {
		this.employer = employer;
	}
	public Set<Task> getTasks() {
		return tasks;
	}
	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}
	public Employee(){
		tasks = new HashSet<>();
	}
}
