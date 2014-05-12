package agh.bd2.jpa.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Task {

	@Id
	@GeneratedValue
	private long id;
	private String name;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable( name="Task_Employee",	joinColumns={@JoinColumn(name="taskID")},inverseJoinColumns={@JoinColumn(name="employeeID")})
	private Set<Employee> employeesResponsible;
	
	public Task(){
		employeesResponsible = new HashSet<>();
	}
	public Task(String name){
		this.name = name;
		employeesResponsible = new HashSet<>();
	}
	
	public Task addEmployeesResponsible(Employee e){
		employeesResponsible.add(e);
		e.getTasks().add(this);
		return this;
	}
	
	public synchronized Task removeEmployeesResponsible(Employee e){
		employeesResponsible.remove(e);
		e.getTasks().remove(this);
		return this;
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
	public Set<Employee> getEmployeesResponsible() {
		return employeesResponsible;
	}
	public void setEmployeesResponsible(Set<Employee> employeesResponsible) {
		this.employeesResponsible = employeesResponsible;
	}
	
}
